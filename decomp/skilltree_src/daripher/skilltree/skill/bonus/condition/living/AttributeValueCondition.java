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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public final class AttributeValueCondition implements LivingCondition {
   private Attribute attribute;
   private float min;
   private float max;

   public AttributeValueCondition(Attribute attribute, float min, float max) {
      this.attribute = attribute;
      this.min = min;
      this.max = max;
   }

   @Override
   public boolean met(LivingEntity living) {
      float value = (float)living.m_21133_(this.attribute);
      if (this.min == -1.0F) {
         return value <= this.max;
      } else {
         return this.max == -1.0F ? value >= this.min : value <= this.max && value >= this.min;
      }
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      MutableComponent attributeDescription = Component.m_237115_(this.attribute.m_22087_());
      MutableComponent targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      String min = ItemStack.f_41584_.format((double)this.min);
      String max = ItemStack.f_41584_.format((double)this.max);
      if (this.min == -1.0F) {
         return Component.m_237110_(key + ".max", new Object[]{bonusTooltip, targetDescription, max, attributeDescription});
      } else {
         return this.max == -1.0F
            ? Component.m_237110_(key + ".min", new Object[]{bonusTooltip, targetDescription, min, attributeDescription})
            : Component.m_237110_(key + ".range", new Object[]{bonusTooltip, targetDescription, min, max, attributeDescription});
      }
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.ATTRIBUTE_VALUE.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
      editor.addLabel(0, 0, "Attribute", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.attribute).setResponder(attribute -> this.selectAttribute(consumer, attribute));
      editor.increaseHeight(19);
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

   private void selectAttribute(Consumer<LivingCondition> consumer, Attribute attribute) {
      this.setAttribute(attribute);
      consumer.accept(this);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         AttributeValueCondition that = (AttributeValueCondition)o;
         return this.min == that.min && this.max == that.max;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.min, this.max);
   }

   public void setAttribute(Attribute attribute) {
      this.attribute = attribute;
   }

   public void setMin(float min) {
      this.min = min;
   }

   public void setMax(float max) {
      this.max = max;
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         Attribute attribute = SerializationHelper.deserializeAttribute(json);
         float min = json.has("min") ? json.get("min").getAsFloat() : -1.0F;
         float max = json.has("max") ? json.get("max").getAsFloat() : -1.0F;
         return new AttributeValueCondition(attribute, min, max);
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (condition instanceof AttributeValueCondition aCondition) {
            SerializationHelper.serializeAttribute(json, aCondition.attribute);
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
         Attribute attribute = SerializationHelper.deserializeAttribute(tag);
         float min = tag.m_128441_("min") ? tag.m_128457_("min") : -1.0F;
         float max = tag.m_128441_("max") ? tag.m_128457_("max") : -1.0F;
         return new AttributeValueCondition(attribute, min, max);
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (condition instanceof AttributeValueCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeAttribute(tag, aCondition.attribute);
            if (aCondition.min != -1.0F) {
               tag.m_128350_("min", aCondition.min);
            }

            if (aCondition.max != -1.0F) {
               tag.m_128350_("max", aCondition.max);
            }

            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new AttributeValueCondition(NetworkHelper.readAttribute(buf), buf.readFloat(), buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (condition instanceof AttributeValueCondition aCondition) {
            NetworkHelper.writeAttribute(buf, aCondition.attribute);
            buf.writeFloat(aCondition.min);
            buf.writeFloat(aCondition.max);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new AttributeValueCondition(Attributes.f_22284_, 5.0F, -1.0F);
      }
   }
}
