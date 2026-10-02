package daripher.skilltree.skill.bonus.condition.living;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTLivingConditions;
import daripher.skilltree.network.NetworkHelper;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public final class HasEffectCondition implements LivingCondition {
   private MobEffect effect;
   private int amplifier;

   public HasEffectCondition(MobEffect effect) {
      this(effect, 0);
   }

   public HasEffectCondition(MobEffect effect, int amplifier) {
      this.effect = effect;
      this.amplifier = amplifier;
   }

   @Override
   public boolean met(LivingEntity living) {
      if (this.amplifier == 0) {
         return living.m_21023_(this.effect);
      } else {
         MobEffectInstance effect = living.m_21124_(this.effect);
         return effect != null && effect.m_19564_() >= this.amplifier;
      }
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      Component targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      Component effectDescription = this.effect.m_19482_();
      if (this.amplifier == 0) {
         return Component.m_237110_(key, new Object[]{bonusTooltip, targetDescription, effectDescription});
      } else {
         Component amplifierDescription = Component.m_237115_("potion.potency." + this.amplifier);
         Component var7 = Component.m_237110_("potion.withAmplifier", new Object[]{effectDescription, amplifierDescription});
         return Component.m_237110_(key + ".amplifier", new Object[]{bonusTooltip, targetDescription, var7});
      }
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.HAS_EFFECT.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
      editor.addLabel(0, 0, "Effect", ChatFormatting.GREEN);
      editor.addLabel(150, 0, "Level", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 145, this.effect).setResponder(effect -> this.selectEffect(consumer, effect));
      editor.addNumericTextField(150, 0, 50, 14, (double)this.amplifier)
         .setNumericFilter(value -> value >= 0.0 && value == (double)value.intValue())
         .setNumericResponder(value -> this.selectAmplifier(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectAmplifier(Consumer<LivingCondition> consumer, Double value) {
      this.setAmplifier(value.intValue());
      consumer.accept(this);
   }

   private void selectEffect(Consumer<LivingCondition> consumer, MobEffect effect) {
      this.setEffect(effect);
      consumer.accept(this);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         HasEffectCondition that = (HasEffectCondition)o;
         return this.amplifier == that.amplifier && Objects.equals(this.effect, that.effect);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.effect, this.amplifier);
   }

   public void setEffect(MobEffect effect) {
      this.effect = effect;
   }

   public void setAmplifier(int amplifier) {
      this.amplifier = amplifier;
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         MobEffect effect = SerializationHelper.deserializeEffect(json);
         int amplifier = !json.has("amplifier") ? 0 : json.get("amplifier").getAsInt();
         return new HasEffectCondition(effect, amplifier);
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (condition instanceof HasEffectCondition aCondition) {
            SerializationHelper.serializeEffect(json, aCondition.effect);
            json.addProperty("amplifier", aCondition.amplifier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(CompoundTag tag) {
         MobEffect effect = SerializationHelper.deserializeEffect(tag);
         int amplifier = !tag.m_128441_("amplifier") ? 0 : tag.m_128451_("amplifier");
         return new HasEffectCondition(effect, amplifier);
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (condition instanceof HasEffectCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeEffect(tag, aCondition.effect);
            tag.m_128405_("amplifier", aCondition.amplifier);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new HasEffectCondition(NetworkHelper.readEffect(buf), buf.readInt());
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (condition instanceof HasEffectCondition aCondition) {
            NetworkHelper.writeEffect(buf, aCondition.effect);
            buf.writeInt(aCondition.amplifier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new HasEffectCondition(MobEffects.f_19614_);
      }
   }
}
