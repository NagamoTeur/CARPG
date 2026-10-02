package daripher.skilltree.skill.bonus.condition.living;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTLivingConditions;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public final class EffectAmountCondition implements LivingCondition {
   private int min;
   private int max;

   public EffectAmountCondition(int min, int max) {
      this.min = min;
      this.max = max;
   }

   @Override
   public boolean met(LivingEntity living) {
      int effects = living.m_21220_().size();
      if (this.min == -1) {
         return effects <= this.max;
      } else {
         return this.max == -1 ? effects >= this.min : effects <= this.max && effects >= this.min;
      }
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      MutableComponent targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      if (this.min == -1) {
         return Component.m_237110_(key + ".max", new Object[]{bonusTooltip, targetDescription, this.max});
      } else if (this.max == -1) {
         return this.min == 1
            ? Component.m_237110_(key + ".min.1", new Object[]{bonusTooltip, targetDescription, this.min})
            : Component.m_237110_(key + ".min", new Object[]{bonusTooltip, targetDescription, this.min});
      } else {
         return Component.m_237110_(key + ".range", new Object[]{bonusTooltip, targetDescription, this.min, this.max});
      }
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.EFFECT_AMOUNT.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
      editor.addLabel(0, 0, "Min", ChatFormatting.GREEN);
      editor.addLabel(55, 0, "Max", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.min).setNumericResponder(value -> this.selectMinimum(consumer, value));
      editor.addNumericTextField(55, 0, 50, 14, (double)this.max).setNumericResponder(value -> this.selectMaximum(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectMaximum(Consumer<LivingCondition> consumer, Double value) {
      this.setMax(value.intValue());
      consumer.accept(this);
   }

   private void selectMinimum(Consumer<LivingCondition> consumer, Double value) {
      this.setMin(value.intValue());
      consumer.accept(this);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EffectAmountCondition that = (EffectAmountCondition)o;
         return this.min == that.min && this.max == that.max;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.min, this.max);
   }

   public void setMin(int min) {
      this.min = min;
   }

   public void setMax(int max) {
      this.max = max;
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         int min = json.has("min") ? json.get("min").getAsInt() : -1;
         int max = json.has("max") ? json.get("max").getAsInt() : -1;
         return new EffectAmountCondition(min, max);
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (condition instanceof EffectAmountCondition aCondition) {
            if (aCondition.min != -1) {
               json.addProperty("min", aCondition.min);
            }

            if (aCondition.max != -1) {
               json.addProperty("max", aCondition.max);
            }
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(CompoundTag tag) {
         int min = tag.m_128441_("Min") ? tag.m_128451_("Min") : -1;
         int max = tag.m_128441_("Max") ? tag.m_128451_("Max") : -1;
         return new EffectAmountCondition(min, max);
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (condition instanceof EffectAmountCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            if (aCondition.min != -1) {
               tag.m_128405_("Min", aCondition.min);
            }

            if (aCondition.max != -1) {
               tag.m_128405_("Max", aCondition.max);
            }

            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new EffectAmountCondition(buf.readInt(), buf.readInt());
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (condition instanceof EffectAmountCondition aCondition) {
            buf.writeInt(aCondition.min);
            buf.writeInt(aCondition.max);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new EffectAmountCondition(1, -1);
      }
   }
}
