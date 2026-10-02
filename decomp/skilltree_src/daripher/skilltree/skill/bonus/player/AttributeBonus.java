package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.living.LivingCondition;
import daripher.skilltree.skill.bonus.condition.living.NoneLivingCondition;
import daripher.skilltree.skill.bonus.multiplier.LivingMultiplier;
import daripher.skilltree.skill.bonus.multiplier.NoneLivingMultiplier;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.common.CuriosHelper.SlotAttributeWrapper;

public final class AttributeBonus implements SkillBonus<AttributeBonus>, SkillBonus.Ticking {
   private Attribute attribute;
   private AttributeModifier modifier;
   @Nonnull
   private LivingMultiplier playerMultiplier = NoneLivingMultiplier.INSTANCE;
   @Nonnull
   private LivingCondition playerCondition = NoneLivingCondition.INSTANCE;

   public AttributeBonus(Attribute attribute, AttributeModifier modifier) {
      this.attribute = attribute;
      this.modifier = modifier;
   }

   public AttributeBonus(Attribute attribute, String name, float amount, Operation operation) {
      this.attribute = attribute;
      this.modifier = new AttributeModifier(UUID.randomUUID(), name, (double)amount, operation);
   }

   @Override
   public void onSkillLearned(ServerPlayer player, boolean firstTime) {
      if (this.playerCondition == NoneLivingCondition.INSTANCE && this.playerMultiplier == NoneLivingMultiplier.INSTANCE) {
         if (this.attribute instanceof SlotAttributeWrapper wrapper) {
            if (firstTime) {
               CuriosApi.getSlotHelper().growSlotType(wrapper.identifier, (int)this.modifier.m_22218_(), player);
            }
         } else {
            AttributeInstance instance = player.m_21051_(this.attribute);
            if (instance == null) {
               SkillTreeMod.LOGGER.error("Attempting to add attribute modifier to attribute {}, which is not present for player", this.attribute);
            } else {
               if (!instance.m_22109_(this.modifier)) {
                  this.applyAttributeModifier(instance, this.modifier, player);
               }
            }
         }
      }
   }

   @Override
   public void onSkillRemoved(ServerPlayer player) {
      if (this.attribute instanceof SlotAttributeWrapper wrapper) {
         CuriosApi.getSlotHelper().shrinkSlotType(wrapper.identifier, (int)this.modifier.m_22218_(), player);
      } else {
         AttributeInstance instance = player.m_21051_(this.attribute);
         if (instance == null) {
            SkillTreeMod.LOGGER.error("Attempting to remove attribute modifier from attribute {}, which is not present for player", this.attribute);
         } else {
            instance.m_22120_(this.modifier.m_22209_());
         }
      }
   }

   @Override
   public void tick(ServerPlayer player) {
      if (this.playerCondition != NoneLivingCondition.INSTANCE || this.playerMultiplier != NoneLivingMultiplier.INSTANCE) {
         if (this.playerCondition != NoneLivingCondition.INSTANCE && !this.playerCondition.met(player)) {
            this.onSkillRemoved(player);
         } else if (this.playerMultiplier != NoneLivingMultiplier.INSTANCE && this.playerMultiplier.getValue(player) == 0.0F) {
            this.onSkillRemoved(player);
         } else {
            this.applyDynamicAttributeBonus(player);
         }
      }
   }

   private void applyDynamicAttributeBonus(ServerPlayer player) {
      AttributeInstance instance = player.m_21051_(this.attribute);
      if (instance != null) {
         AttributeModifier oldModifier = instance.m_22111_(this.modifier.m_22209_());
         double value = this.modifier.m_22218_();
         value *= (double)this.playerMultiplier.getValue(player);
         if (oldModifier == null || oldModifier.m_22218_() != value) {
            AttributeModifier dynamicModifier = new AttributeModifier(this.modifier.m_22209_(), "DynamicBonus", value, this.modifier.m_22217_());
            this.applyAttributeModifier(instance, dynamicModifier, player);
         }
      }
   }

   private void applyAttributeModifier(AttributeInstance instance, AttributeModifier modifier, Player player) {
      float healthPercentage = player.m_21223_() / player.m_21233_();
      if (instance.m_22111_(modifier.m_22209_()) != null) {
         instance.m_22120_(modifier.m_22209_());
      }

      instance.m_22118_(modifier);
      if (this.attribute == Attributes.f_22276_) {
         player.m_21153_(player.m_21233_() * healthPercentage);
      }
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.ATTRIBUTE.get();
   }

   public AttributeBonus copy() {
      AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), this.modifier.m_22214_(), this.modifier.m_22218_(), this.modifier.m_22217_());
      AttributeBonus bonus = new AttributeBonus(this.attribute, modifier);
      bonus.playerMultiplier = this.playerMultiplier;
      bonus.playerCondition = this.playerCondition;
      return bonus;
   }

   public AttributeBonus multiply(double multiplier) {
      this.modifier = new AttributeModifier(this.modifier.m_22209_(), this.modifier.m_22214_(), this.modifier.m_22218_() * multiplier, this.modifier.m_22217_());
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      if (other instanceof AttributeBonus otherBonus) {
         if (otherBonus.attribute != this.attribute) {
            return false;
         } else if (!Objects.equals(otherBonus.playerMultiplier, this.playerMultiplier)) {
            return false;
         } else {
            return !Objects.equals(otherBonus.playerCondition, this.playerCondition) ? false : otherBonus.modifier.m_22217_() == this.modifier.m_22217_();
         }
      } else {
         return false;
      }
   }

   @Override
   public SkillBonus<AttributeBonus> merge(SkillBonus<?> other) {
      if (other instanceof AttributeBonus otherBonus) {
         AttributeModifier mergedModifier = new AttributeModifier(
            this.modifier.m_22209_(), "Merged", this.modifier.m_22218_() + otherBonus.modifier.m_22218_(), this.modifier.m_22217_()
         );
         AttributeBonus mergedBonus = new AttributeBonus(this.attribute, mergedModifier);
         mergedBonus.playerMultiplier = this.playerMultiplier;
         mergedBonus.playerCondition = this.playerCondition;
         return mergedBonus;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      float visibleAmount = (float)this.modifier.m_22218_();
      if (this.modifier.m_22217_() == Operation.ADDITION && this.attribute.equals(Attributes.f_22278_)) {
         visibleAmount *= 10.0F;
      }

      MutableComponent tooltip = TooltipHelper.getSkillBonusTooltip(this.attribute.m_22087_(), (double)visibleAmount, this.modifier.m_22217_());
      tooltip = this.playerMultiplier.getTooltip(tooltip, SkillBonus.Target.PLAYER);
      tooltip = this.playerCondition.getTooltip(tooltip, "you");
      return tooltip.m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public void gatherInfo(Consumer<MutableComponent> consumer) {
      SkillBonus.super.gatherInfo(consumer);
      TooltipHelper.consumeTranslated(this.attribute.m_22087_() + ".info", consumer);
   }

   @Override
   public boolean isPositive() {
      return this.modifier.m_22218_() > 0.0;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<AttributeBonus> consumer) {
      editor.addLabel(0, 0, "Attribute", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.attribute).setResponder(attribute -> this.selectAttribute(consumer, attribute));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Amount", ChatFormatting.GOLD);
      editor.addLabel(55, 0, "Operation", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, this.modifier.m_22218_()).setNumericResponder(value -> this.selectAmount(consumer, value));
      editor.addOperationSelection(55, 0, 145, this.modifier.m_22217_()).setResponder(operation -> this.selectOperation(consumer, operation));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Player Condition", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.playerCondition)
         .setResponder(condition -> this.selectPlayerCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addPlayerConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Player Multiplier", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.playerMultiplier)
         .setResponder(multiplier -> this.selectPlayerMultiplier(editor, consumer, multiplier))
         .setMenuInitFunc(() -> this.addPlayerMultiplierWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void selectPlayerMultiplier(SkillTreeEditor editor, Consumer<AttributeBonus> consumer, LivingMultiplier multiplier) {
      this.setMultiplier(multiplier);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectPlayerCondition(SkillTreeEditor editor, Consumer<AttributeBonus> consumer, LivingCondition condition) {
      this.setCondition(condition);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectOperation(Consumer<AttributeBonus> consumer, Operation operation) {
      this.setOperation(operation);
      consumer.accept(this.copy());
   }

   private void selectAmount(Consumer<AttributeBonus> consumer, Double value) {
      this.setAmount(value);
      consumer.accept(this.copy());
   }

   private void selectAttribute(Consumer<AttributeBonus> consumer, Attribute attribute) {
      this.setAttribute(attribute);
      consumer.accept(this.copy());
   }

   private void addPlayerConditionWidgets(SkillTreeEditor editor, Consumer<AttributeBonus> consumer) {
      this.playerCondition.addEditorWidgets(editor, c -> {
         this.setCondition(c);
         consumer.accept(this.copy());
      });
   }

   private void addPlayerMultiplierWidgets(SkillTreeEditor editor, Consumer<AttributeBonus> consumer) {
      this.playerMultiplier.addEditorWidgets(editor, m -> {
         this.setMultiplier(m);
         consumer.accept(this.copy());
      });
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public AttributeModifier getModifier() {
      return this.modifier;
   }

   public void setAttribute(Attribute attribute) {
      this.attribute = attribute;
   }

   public void setAmount(double amount) {
      this.modifier = new AttributeModifier(this.modifier.m_22209_(), this.modifier.m_22214_(), amount, this.modifier.m_22217_());
   }

   public void setUUID(UUID id) {
      this.modifier = new AttributeModifier(id, this.modifier.m_22214_(), this.modifier.m_22218_(), this.modifier.m_22217_());
   }

   public void setOperation(Operation operation) {
      this.modifier = new AttributeModifier(this.modifier.m_22209_(), this.modifier.m_22214_(), this.modifier.m_22218_(), operation);
   }

   public SkillBonus<?> setCondition(LivingCondition condition) {
      this.playerCondition = condition;
      return this;
   }

   public SkillBonus<?> setMultiplier(LivingMultiplier multiplier) {
      this.playerMultiplier = multiplier;
      return this;
   }

   public boolean hasCondition() {
      return this.playerCondition != NoneLivingCondition.INSTANCE;
   }

   public boolean hasMultiplier() {
      return this.playerMultiplier != NoneLivingMultiplier.INSTANCE;
   }

   public static class Serializer implements SkillBonus.Serializer {
      public AttributeBonus deserialize(JsonObject json) throws JsonParseException {
         Attribute attribute = SerializationHelper.deserializeAttribute(json);
         AttributeModifier modifier = SerializationHelper.deserializeAttributeModifier(json);
         AttributeBonus bonus = new AttributeBonus(attribute, modifier);
         bonus.playerMultiplier = SerializationHelper.deserializeLivingMultiplier(json, "player_multiplier");
         bonus.playerCondition = SerializationHelper.deserializeLivingCondition(json, "player_condition");
         return bonus;
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof AttributeBonus aBonus) {
            SerializationHelper.serializeAttribute(json, aBonus.attribute);
            SerializationHelper.serializeAttributeModifier(json, aBonus.modifier);
            SerializationHelper.serializeLivingMultiplier(json, aBonus.playerMultiplier, "player_multiplier");
            SerializationHelper.serializeLivingCondition(json, aBonus.playerCondition, "player_condition");
         } else {
            throw new IllegalArgumentException();
         }
      }

      public AttributeBonus deserialize(CompoundTag tag) {
         Attribute attribute = SerializationHelper.deserializeAttribute(tag);
         AttributeModifier modifier = SerializationHelper.deserializeAttributeModifier(tag);
         AttributeBonus bonus = new AttributeBonus(attribute, modifier);
         bonus.playerMultiplier = SerializationHelper.deserializeLivingMultiplier(tag, "player_multiplier");
         bonus.playerCondition = SerializationHelper.deserializeLivingCondition(tag, "player_condition");
         return bonus;
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof AttributeBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeAttribute(tag, aBonus.attribute);
            SerializationHelper.serializeAttributeModifier(tag, aBonus.modifier);
            SerializationHelper.serializeLivingMultiplier(tag, aBonus.playerMultiplier, "player_multiplier");
            SerializationHelper.serializeLivingCondition(tag, aBonus.playerCondition, "player_condition");
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public AttributeBonus deserialize(FriendlyByteBuf buf) {
         Attribute attribute = NetworkHelper.readAttribute(buf);
         AttributeModifier modifier = NetworkHelper.readAttributeModifier(buf);
         AttributeBonus bonus = new AttributeBonus(attribute, modifier);
         bonus.playerMultiplier = NetworkHelper.readLivingMultiplier(buf);
         bonus.playerCondition = NetworkHelper.readLivingCondition(buf);
         return bonus;
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof AttributeBonus aBonus) {
            NetworkHelper.writeAttribute(buf, aBonus.attribute);
            NetworkHelper.writeAttributeModifier(buf, aBonus.modifier);
            NetworkHelper.writeLivingMultiplier(buf, aBonus.playerMultiplier);
            NetworkHelper.writeLivingCondition(buf, aBonus.playerCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new AttributeBonus(Attributes.f_22284_, new AttributeModifier(UUID.randomUUID(), "Skill", 1.0, Operation.ADDITION));
      }
   }
}
