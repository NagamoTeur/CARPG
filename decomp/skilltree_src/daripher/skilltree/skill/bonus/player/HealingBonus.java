package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.EventListenerBonus;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.event.AttackEventListener;
import daripher.skilltree.skill.bonus.event.SkillEventListener;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public final class HealingBonus implements EventListenerBonus<HealingBonus> {
   private float chance;
   private float amount;
   private SkillEventListener eventListener;

   public HealingBonus(float chance, float amount, SkillEventListener eventListener) {
      this.chance = chance;
      this.amount = amount;
      this.eventListener = eventListener;
   }

   public HealingBonus(float chance, float amount) {
      this(chance, amount, new AttackEventListener().setTarget(SkillBonus.Target.PLAYER));
   }

   @Override
   public void applyEffect(LivingEntity target) {
      if (target.m_217043_().m_188501_() < this.chance) {
         if (target.m_21223_() < target.m_21233_() && target instanceof Player player) {
            player.m_36324_().m_38703_(this.amount / 2.0F);
         }

         target.m_5634_(this.amount);
      }
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.HEALING.get();
   }

   public HealingBonus copy() {
      return new HealingBonus(this.chance, this.amount, this.eventListener);
   }

   public HealingBonus multiply(double multiplier) {
      if (this.chance == 1.0F) {
         this.amount *= (float)multiplier;
      } else {
         this.chance *= (float)multiplier;
      }

      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      if (other instanceof HealingBonus otherBonus) {
         return otherBonus.amount != this.amount ? false : Objects.equals(otherBonus.eventListener, this.eventListener);
      } else {
         return false;
      }
   }

   public HealingBonus merge(SkillBonus<?> other) {
      if (other instanceof HealingBonus otherBonus) {
         return otherBonus.chance == 1.0F && this.chance == 1.0F
            ? new HealingBonus(this.chance, otherBonus.amount + this.amount, this.eventListener)
            : new HealingBonus(otherBonus.chance + this.chance, this.amount, this.eventListener);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      String targetDescription = this.eventListener.getTarget().name().toLowerCase();
      String bonusDescription = this.getDescriptionId() + "." + targetDescription;
      if (this.chance < 1.0F) {
         bonusDescription = bonusDescription + ".chance";
      }

      String amountDescription = TooltipHelper.formatNumber((double)this.amount);
      MutableComponent tooltip = Component.m_237110_(bonusDescription, new Object[]{amountDescription});
      if (this.chance < 1.0F) {
         tooltip = TooltipHelper.getSkillBonusTooltip(tooltip, (double)this.chance, Operation.MULTIPLY_BASE);
      }

      tooltip = this.eventListener.getTooltip(tooltip);
      return tooltip.m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.chance > 0.0F ^ this.eventListener.getTarget() == SkillBonus.Target.ENEMY;
   }

   @Override
   public SkillEventListener getEventListener() {
      return this.eventListener;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<EventListenerBonus<HealingBonus>> consumer) {
      editor.addLabel(0, 0, "Chance", ChatFormatting.GOLD);
      editor.addLabel(110, 0, "Amount", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 90, 14, (double)this.chance).setNumericResponder(value -> this.selectChance(consumer, value));
      editor.addNumericTextField(110, 0, 90, 14, (double)this.amount).setNumericResponder(value -> this.selectAmount(consumer, value));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Event", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.eventListener)
         .setResponder(eventListener -> this.selectEventListener(editor, consumer, eventListener))
         .setMenuInitFunc(() -> this.addEventListenerWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void addEventListenerWidgets(SkillTreeEditor editor, Consumer<EventListenerBonus<HealingBonus>> consumer) {
      this.eventListener.addEditorWidgets(editor, eventListener -> {
         this.setEventListener(eventListener);
         consumer.accept(this.copy());
      });
   }

   private void selectEventListener(SkillTreeEditor editor, Consumer<EventListenerBonus<HealingBonus>> consumer, SkillEventListener eventListener) {
      this.setEventListener(eventListener);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectAmount(Consumer<EventListenerBonus<HealingBonus>> consumer, Double value) {
      this.setAmount(value.intValue());
      consumer.accept(this.copy());
   }

   private void selectChance(Consumer<EventListenerBonus<HealingBonus>> consumer, Double value) {
      this.setChance(value.floatValue());
      consumer.accept(this.copy());
   }

   public void setEventListener(SkillEventListener eventListener) {
      this.eventListener = eventListener;
   }

   public void setChance(float chance) {
      this.chance = chance;
   }

   public void setAmount(int amount) {
      this.amount = (float)amount;
   }

   public static class Serializer implements SkillBonus.Serializer {
      public HealingBonus deserialize(JsonObject json) throws JsonParseException {
         float chance = SerializationHelper.getElement(json, "chance").getAsFloat();
         float amount = SerializationHelper.getElement(json, "amount").getAsFloat();
         HealingBonus bonus = new HealingBonus(chance, amount);
         bonus.eventListener = SerializationHelper.deserializeEventListener(json);
         return bonus;
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof HealingBonus aBonus) {
            json.addProperty("chance", aBonus.chance);
            json.addProperty("amount", aBonus.amount);
            SerializationHelper.serializeEventListener(json, aBonus.eventListener);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public HealingBonus deserialize(CompoundTag tag) {
         float chance = tag.m_128457_("chance");
         float amount = tag.m_128457_("amount");
         HealingBonus bonus = new HealingBonus(chance, amount);
         bonus.eventListener = SerializationHelper.deserializeEventListener(tag);
         return bonus;
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof HealingBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("chance", aBonus.chance);
            tag.m_128350_("amount", aBonus.amount);
            SerializationHelper.serializeEventListener(tag, aBonus.eventListener);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public HealingBonus deserialize(FriendlyByteBuf buf) {
         float amount = buf.readFloat();
         float duration = buf.readFloat();
         HealingBonus bonus = new HealingBonus(amount, duration);
         bonus.eventListener = NetworkHelper.readEventListener(buf);
         return bonus;
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof HealingBonus aBonus) {
            buf.writeFloat(aBonus.chance);
            buf.writeFloat(aBonus.amount);
            NetworkHelper.writeEventListener(buf, aBonus.eventListener);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new HealingBonus(0.05F, 5.0F);
      }
   }
}
