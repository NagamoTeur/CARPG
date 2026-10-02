package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;

public final class AllAttributesBonus implements SkillBonus<AllAttributesBonus>, SkillBonus.Ticking {
   private static final Set<Attribute> AFFECTED_ATTRIBUTES = new HashSet<>();
   private AttributeModifier modifier;
   @Nonnull
   private LivingMultiplier playerMultiplier = NoneLivingMultiplier.INSTANCE;
   @Nonnull
   private LivingCondition playerCondition = NoneLivingCondition.INSTANCE;

   public AllAttributesBonus(AttributeModifier modifier) {
      this.modifier = modifier;
   }

   @Override
   public void onSkillLearned(ServerPlayer player, boolean firstTime) {
      if (this.playerCondition == NoneLivingCondition.INSTANCE && this.playerMultiplier == NoneLivingMultiplier.INSTANCE) {
         getAffectedAttributes()
            .stream()
            .<AttributeInstance>map(player::m_21051_)
            .filter(Objects::nonNull)
            .filter(a -> !a.m_22109_(this.modifier))
            .forEach(a -> this.applyAttributeModifier(a, this.modifier, player));
      }
   }

   @Override
   public void onSkillRemoved(ServerPlayer player) {
      getAffectedAttributes()
         .stream()
         .<AttributeInstance>map(player::m_21051_)
         .filter(Objects::nonNull)
         .filter(a -> !a.m_22109_(this.modifier))
         .forEach(a -> a.m_22120_(this.modifier.m_22209_()));
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
      getAffectedAttributes().stream().<AttributeInstance>map(player::m_21051_).filter(Objects::nonNull).forEach(playerAttribute -> {
         AttributeModifier oldModifier = playerAttribute.m_22111_(this.modifier.m_22209_());
         double value = this.modifier.m_22218_();
         value *= (double)this.playerMultiplier.getValue(player);
         if (oldModifier != null) {
            if (oldModifier.m_22218_() == value) {
               return;
            }

            playerAttribute.m_22120_(this.modifier.m_22209_());
         }

         AttributeModifier dynamicModifier = new AttributeModifier(this.modifier.m_22209_(), "Dynamic", value, this.modifier.m_22217_());
         this.applyAttributeModifier(playerAttribute, dynamicModifier, player);
         if (playerAttribute.m_22099_() == Attributes.f_22276_) {
            player.m_21153_(player.m_21223_());
         }
      });
   }

   private void applyAttributeModifier(AttributeInstance instance, AttributeModifier modifier, Player player) {
      float healthPercentage = player.m_21223_() / player.m_21233_();
      instance.m_22118_(modifier);
      if (getAffectedAttributes().contains(Attributes.f_22276_)) {
         player.m_21153_(player.m_21233_() * healthPercentage);
      }
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.ALL_ATTRIBUTES.get();
   }

   public AllAttributesBonus copy() {
      AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), this.modifier.m_22214_(), this.modifier.m_22218_(), this.modifier.m_22217_());
      AllAttributesBonus bonus = new AllAttributesBonus(modifier);
      bonus.playerMultiplier = this.playerMultiplier;
      bonus.playerCondition = this.playerCondition;
      return bonus;
   }

   public AllAttributesBonus multiply(double multiplier) {
      this.modifier = new AttributeModifier(this.modifier.m_22209_(), this.modifier.m_22214_(), this.modifier.m_22218_() * multiplier, this.modifier.m_22217_());
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      if (other instanceof AllAttributesBonus otherBonus) {
         if (!Objects.equals(otherBonus.playerMultiplier, this.playerMultiplier)) {
            return false;
         } else {
            return !Objects.equals(otherBonus.playerCondition, this.playerCondition) ? false : otherBonus.modifier.m_22217_() == this.modifier.m_22217_();
         }
      } else {
         return false;
      }
   }

   @Override
   public SkillBonus<AllAttributesBonus> merge(SkillBonus<?> other) {
      if (other instanceof AllAttributesBonus otherBonus) {
         AttributeModifier mergedModifier = new AttributeModifier(
            this.modifier.m_22209_(), "Merged", this.modifier.m_22218_() + otherBonus.modifier.m_22218_(), this.modifier.m_22217_()
         );
         AllAttributesBonus mergedBonus = new AllAttributesBonus(mergedModifier);
         mergedBonus.playerMultiplier = this.playerMultiplier;
         mergedBonus.playerCondition = this.playerCondition;
         return mergedBonus;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      MutableComponent tooltip = TooltipHelper.getSkillBonusTooltip(this.getDescriptionId(), this.modifier.m_22218_(), this.modifier.m_22217_());
      tooltip = this.playerMultiplier.getTooltip(tooltip, SkillBonus.Target.PLAYER);
      tooltip = this.playerCondition.getTooltip(tooltip, "you");
      return tooltip.m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.modifier.m_22218_() > 0.0;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<AllAttributesBonus> consumer) {
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

   private void selectPlayerMultiplier(SkillTreeEditor editor, Consumer<AllAttributesBonus> consumer, LivingMultiplier multiplier) {
      this.setMultiplier(multiplier);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectPlayerCondition(SkillTreeEditor editor, Consumer<AllAttributesBonus> consumer, LivingCondition condition) {
      this.setCondition(condition);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectOperation(Consumer<AllAttributesBonus> consumer, Operation operation) {
      this.setOperation(operation);
      consumer.accept(this.copy());
   }

   private void selectAmount(Consumer<AllAttributesBonus> consumer, Double value) {
      this.setAmount(value);
      consumer.accept(this.copy());
   }

   private void addPlayerMultiplierWidgets(SkillTreeEditor editor, Consumer<AllAttributesBonus> consumer) {
      this.playerMultiplier.addEditorWidgets(editor, multiplier -> {
         this.setMultiplier(multiplier);
         consumer.accept(this.copy());
      });
   }

   private void addPlayerConditionWidgets(SkillTreeEditor editor, Consumer<AllAttributesBonus> consumer) {
      this.playerCondition.addEditorWidgets(editor, condition -> {
         this.setCondition(condition);
         consumer.accept(this.copy());
      });
   }

   public void setAmount(double amount) {
      this.modifier = new AttributeModifier(this.modifier.m_22209_(), this.modifier.m_22214_(), amount, this.modifier.m_22217_());
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

   private static Set<Attribute> getAffectedAttributes() {
      if (AFFECTED_ATTRIBUTES.isEmpty()) {
         ForgeRegistries.ATTRIBUTES
            .getValues()
            .stream()
            .filter(((AttributeSupplier)ForgeHooks.getAttributesView().get(EntityType.f_20532_))::m_22258_)
            .forEach(AFFECTED_ATTRIBUTES::add);
      }

      return AFFECTED_ATTRIBUTES;
   }

   public static class Serializer implements SkillBonus.Serializer {
      public AllAttributesBonus deserialize(JsonObject json) throws JsonParseException {
         AttributeModifier modifier = SerializationHelper.deserializeAttributeModifier(json);
         AllAttributesBonus bonus = new AllAttributesBonus(modifier);
         bonus.playerMultiplier = SerializationHelper.deserializeLivingMultiplier(json, "player_multiplier");
         bonus.playerCondition = SerializationHelper.deserializeLivingCondition(json, "player_condition");
         return bonus;
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof AllAttributesBonus aBonus) {
            SerializationHelper.serializeAttributeModifier(json, aBonus.modifier);
            SerializationHelper.serializeLivingMultiplier(json, aBonus.playerMultiplier, "player_multiplier");
            SerializationHelper.serializeLivingCondition(json, aBonus.playerCondition, "player_condition");
         } else {
            throw new IllegalArgumentException();
         }
      }

      public AllAttributesBonus deserialize(CompoundTag tag) {
         AttributeModifier modifier = SerializationHelper.deserializeAttributeModifier(tag);
         AllAttributesBonus bonus = new AllAttributesBonus(modifier);
         bonus.playerMultiplier = SerializationHelper.deserializeLivingMultiplier(tag, "player_multiplier");
         bonus.playerCondition = SerializationHelper.deserializeLivingCondition(tag, "player_condition");
         return bonus;
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof AllAttributesBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeAttributeModifier(tag, aBonus.modifier);
            SerializationHelper.serializeLivingMultiplier(tag, aBonus.playerMultiplier, "player_multiplier");
            SerializationHelper.serializeLivingCondition(tag, aBonus.playerCondition, "player_condition");
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public AllAttributesBonus deserialize(FriendlyByteBuf buf) {
         AttributeModifier modifier = NetworkHelper.readAttributeModifier(buf);
         AllAttributesBonus bonus = new AllAttributesBonus(modifier);
         bonus.playerMultiplier = NetworkHelper.readLivingMultiplier(buf);
         bonus.playerCondition = NetworkHelper.readLivingCondition(buf);
         return bonus;
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof AllAttributesBonus aBonus) {
            NetworkHelper.writeAttributeModifier(buf, aBonus.modifier);
            NetworkHelper.writeLivingMultiplier(buf, aBonus.playerMultiplier);
            NetworkHelper.writeLivingCondition(buf, aBonus.playerCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new AllAttributesBonus(new AttributeModifier(UUID.randomUUID(), "Skill", 0.05, Operation.MULTIPLY_BASE));
      }
   }
}
