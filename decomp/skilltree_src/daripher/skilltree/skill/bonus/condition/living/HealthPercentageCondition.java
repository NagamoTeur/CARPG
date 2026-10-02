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
import net.minecraft.world.item.ItemStack;

public final class HealthPercentageCondition implements LivingCondition {
   private float min;
   private float max;

   public HealthPercentageCondition(float min, float max) {
      this.min = min;
      this.max = max;
   }

   @Override
   public boolean met(LivingEntity living) {
      float percentage = living.m_21223_() / living.m_21233_();
      if (this.min == -1.0F) {
         return percentage <= this.max;
      } else {
         return this.max == -1.0F ? percentage >= this.min : percentage <= this.max && percentage >= this.min;
      }
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      MutableComponent targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      String min = ItemStack.f_41584_.format((double)(this.min * 100.0F));
      String max = ItemStack.f_41584_.format((double)(this.max * 100.0F));
      if (this.min == -1.0F) {
         return Component.m_237110_(key + ".max", new Object[]{bonusTooltip, targetDescription, max});
      } else {
         return this.max == -1.0F
            ? Component.m_237110_(key + ".min", new Object[]{bonusTooltip, targetDescription, min})
            : Component.m_237110_(key + ".range", new Object[]{bonusTooltip, targetDescription, min, max});
      }
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.HEALTH_PERCENTAGE.get();
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
      this.setMax(value.floatValue());
      consumer.accept(this);
   }

   private void selectMinimum(Consumer<LivingCondition> consumer, Double value) {
      this.setMin(value.floatValue());
      consumer.accept(this);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         HealthPercentageCondition that = (HealthPercentageCondition)o;
         return Float.compare(this.min, that.min) == 0 && Float.compare(this.max, that.max) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.min, this.max);
   }

   public void setMin(float min) {
      this.min = min;
   }

   public void setMax(float max) {
      this.max = max;
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         float min = json.has("min") ? json.get("min").getAsFloat() : -1.0F;
         float max = json.has("max") ? json.get("max").getAsFloat() : -1.0F;
         return new HealthPercentageCondition(min, max);
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (condition instanceof HealthPercentageCondition aCondition) {
            if (aCondition.min != -1.0F) {
               json.addProperty("min", aCondition.min);
            }

            if (aCondition.max != -1.0F) {
               json.addProperty("max", aCondition.max);
            }
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(CompoundTag tag) {
         float min = tag.m_128441_("Min") ? tag.m_128457_("Min") : -1.0F;
         float max = tag.m_128441_("Max") ? tag.m_128457_("Max") : -1.0F;
         return new HealthPercentageCondition(min, max);
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (condition instanceof HealthPercentageCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            if (aCondition.min != -1.0F) {
               tag.m_128350_("Min", aCondition.min);
            }

            if (aCondition.max != -1.0F) {
               tag.m_128350_("Max", aCondition.max);
            }

            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new HealthPercentageCondition(buf.readFloat(), buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (condition instanceof HealthPercentageCondition aCondition) {
            buf.writeFloat(aCondition.min);
            buf.writeFloat(aCondition.max);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new HealthPercentageCondition(-1.0F, 0.5F);
      }
   }
}
