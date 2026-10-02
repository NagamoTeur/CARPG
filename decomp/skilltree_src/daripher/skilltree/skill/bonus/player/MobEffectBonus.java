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
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class MobEffectBonus implements EventListenerBonus<MobEffectBonus> {
   private MobEffectInstance effect;
   private SkillEventListener eventListener;
   private float chance;

   public MobEffectBonus(float chance, MobEffectInstance effect, SkillEventListener eventListener) {
      this.chance = chance;
      this.effect = effect;
      this.eventListener = eventListener;
   }

   public MobEffectBonus(float chance, MobEffectInstance effect) {
      this(chance, effect, new AttackEventListener());
   }

   @Override
   public void applyEffect(LivingEntity target) {
      if (target.m_217043_().m_188501_() < this.chance) {
         target.m_7292_(new MobEffectInstance(this.effect));
      }
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.MOB_EFFECT.get();
   }

   public MobEffectBonus copy() {
      return new MobEffectBonus(this.chance, this.effect, this.eventListener);
   }

   public MobEffectBonus multiply(double multiplier) {
      this.chance *= (float)multiplier;
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      if (other instanceof MobEffectBonus otherBonus) {
         return !Objects.equals(otherBonus.effect, this.effect) ? false : Objects.equals(otherBonus.eventListener, this.eventListener);
      } else {
         return false;
      }
   }

   @Override
   public SkillBonus<EventListenerBonus<MobEffectBonus>> merge(SkillBonus<?> other) {
      if (other instanceof MobEffectBonus otherBonus) {
         return new MobEffectBonus(otherBonus.chance + this.chance, this.effect, this.eventListener);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      Component effectDescription = TooltipHelper.getEffectInstanceTooltip(this.effect);
      int duration = this.effect.m_19557_();
      String targetDescription = this.eventListener.getTarget().name().toLowerCase();
      String bonusDescription = this.getDescriptionId() + "." + targetDescription;
      if (this.chance < 1.0F) {
         bonusDescription = bonusDescription + ".chance";
      }

      MutableComponent tooltip;
      if (duration > 0) {
         Component durationDescription = Component.m_237110_(this.getDescriptionId() + ".duration", new Object[]{StringUtil.m_14404_(duration)});
         tooltip = Component.m_237110_(bonusDescription, new Object[]{effectDescription, durationDescription});
      } else {
         tooltip = Component.m_237110_(bonusDescription, new Object[]{effectDescription, ""});
      }

      if (this.chance < 1.0F) {
         tooltip = TooltipHelper.getSkillBonusTooltip(tooltip, (double)this.chance, Operation.MULTIPLY_BASE);
      }

      tooltip = this.eventListener.getTooltip(tooltip);
      return tooltip.m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public void gatherInfo(Consumer<MutableComponent> consumer) {
      TooltipHelper.consumeTranslated(this.effect.m_19576_() + ".info", consumer);
   }

   @Override
   public boolean isPositive() {
      return this.chance > 0.0F ^ this.eventListener.getTarget() == SkillBonus.Target.PLAYER ^ this.effect.m_19544_().m_19483_() != MobEffectCategory.HARMFUL;
   }

   @Override
   public SkillEventListener getEventListener() {
      return this.eventListener;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<EventListenerBonus<MobEffectBonus>> consumer) {
      editor.addLabel(0, 0, "Effect", ChatFormatting.GOLD);
      editor.addLabel(150, 0, "Chance", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 145, this.effect.m_19544_()).setResponder(effect -> this.selectEffect(consumer, effect));
      editor.addNumericTextField(150, 0, 50, 14, (double)this.chance).setNumericResponder(value -> this.selectChance(consumer, value));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Duration", ChatFormatting.GOLD);
      editor.addLabel(55, 0, "Amplifier", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.effect.m_19557_())
         .setNumericFilter(value -> value >= -1.0)
         .setNumericResponder(value -> this.selectDuration(consumer, value));
      editor.addNumericTextField(55, 0, 50, 14, (double)this.effect.m_19564_())
         .setNumericFilter(value -> value >= 0.0)
         .setNumericResponder(value -> this.selectAmplifier(consumer, value));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Event", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.eventListener)
         .setResponder(eventListener -> this.selectEventListener(editor, consumer, eventListener))
         .setMenuInitFunc(() -> this.addEventListenerWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void addEventListenerWidgets(SkillTreeEditor editor, Consumer<EventListenerBonus<MobEffectBonus>> consumer) {
      this.eventListener.addEditorWidgets(editor, eventListener -> {
         this.setEventListener(eventListener);
         consumer.accept(this.copy());
      });
   }

   private void selectEventListener(SkillTreeEditor editor, Consumer<EventListenerBonus<MobEffectBonus>> consumer, SkillEventListener eventListener) {
      this.setEventListener(eventListener);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectAmplifier(Consumer<EventListenerBonus<MobEffectBonus>> consumer, Double value) {
      this.setAmplifier(value.intValue());
      consumer.accept(this.copy());
   }

   private void selectDuration(Consumer<EventListenerBonus<MobEffectBonus>> consumer, Double value) {
      this.setDuration(value.intValue());
      consumer.accept(this.copy());
   }

   private void selectChance(Consumer<EventListenerBonus<MobEffectBonus>> consumer, Double value) {
      this.setChance(value.floatValue());
      consumer.accept(this.copy());
   }

   private void selectEffect(Consumer<EventListenerBonus<MobEffectBonus>> consumer, MobEffect effect) {
      this.setEffect(effect);
      consumer.accept(this);
   }

   public void setChance(float chance) {
      this.chance = chance;
   }

   public void setEffect(MobEffect effect) {
      this.effect = new MobEffectInstance(effect, this.effect.m_19557_(), this.effect.m_19564_());
   }

   public void setDuration(int duration) {
      this.effect = new MobEffectInstance(this.effect.m_19544_(), duration, this.effect.m_19564_());
   }

   public void setAmplifier(int amplifier) {
      this.effect = new MobEffectInstance(this.effect.m_19544_(), this.effect.m_19557_(), amplifier);
   }

   public void setEventListener(SkillEventListener eventListener) {
      this.eventListener = eventListener;
   }

   public static class Serializer implements SkillBonus.Serializer {
      public MobEffectBonus deserialize(JsonObject json) throws JsonParseException {
         float chance = SerializationHelper.getElement(json, "chance").getAsFloat();
         MobEffectInstance effect = SerializationHelper.deserializeEffectInstance(json);
         MobEffectBonus bonus = new MobEffectBonus(chance, effect);
         bonus.eventListener = SerializationHelper.deserializeEventListener(json);
         return bonus;
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof MobEffectBonus aBonus) {
            json.addProperty("chance", aBonus.chance);
            SerializationHelper.serializeEffectInstance(json, aBonus.effect);
            SerializationHelper.serializeEventListener(json, aBonus.eventListener);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public MobEffectBonus deserialize(CompoundTag tag) {
         float chance = tag.m_128457_("chance");
         MobEffectInstance effect = SerializationHelper.deserializeEffectInstance(tag);
         MobEffectBonus bonus = new MobEffectBonus(chance, effect);
         bonus.eventListener = SerializationHelper.deserializeEventListener(tag);
         return bonus;
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof MobEffectBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("chance", aBonus.chance);
            SerializationHelper.serializeEffectInstance(tag, aBonus.effect);
            SerializationHelper.serializeEventListener(tag, aBonus.eventListener);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public MobEffectBonus deserialize(FriendlyByteBuf buf) {
         float amount = buf.readFloat();
         MobEffectInstance effect = NetworkHelper.readEffectInstance(buf);
         MobEffectBonus bonus = new MobEffectBonus(amount, effect);
         bonus.eventListener = NetworkHelper.readEventListener(buf);
         return bonus;
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof MobEffectBonus aBonus) {
            buf.writeFloat(aBonus.chance);
            NetworkHelper.writeEffectInstance(buf, aBonus.effect);
            NetworkHelper.writeEventListener(buf, aBonus.eventListener);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new MobEffectBonus(0.05F, new MobEffectInstance(MobEffects.f_19614_, 100));
      }
   }
}
