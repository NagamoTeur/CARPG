package daripher.skilltree.skill.bonus.multiplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTLivingMultipliers;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
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

public final class AttributeValueMultiplier implements LivingMultiplier {
   private Attribute attribute;
   private float divisor;

   public AttributeValueMultiplier(Attribute attribute) {
      this(attribute, 1.0F);
   }

   public AttributeValueMultiplier(Attribute attribute, float divisor) {
      this.attribute = attribute;
      this.divisor = divisor;
   }

   @Override
   public float getValue(LivingEntity entity) {
      return (float)((int)(entity.m_21133_(this.attribute) / (double)this.divisor));
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, SkillBonus.Target target) {
      Component attributeDescription = Component.m_237115_(this.attribute.m_22087_());
      String multiplierDescription = this.getDescriptionId(target);
      String divisorDescription = TooltipHelper.formatNumber((double)this.divisor);
      return Component.m_237110_(multiplierDescription, new Object[]{bonusTooltip, divisorDescription, attributeDescription});
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingMultiplier> consumer) {
      editor.addLabel(0, 0, "Attribute", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.attribute).setResponder(attribute -> this.selectAttribute(consumer, attribute));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Divisor", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.divisor)
         .setNumericFilter(value -> value > 0.0)
         .setNumericResponder(value -> this.selectDivisor(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectDivisor(Consumer<LivingMultiplier> consumer, Double value) {
      this.setDivisor(value.floatValue());
      consumer.accept(this);
   }

   private void selectAttribute(Consumer<LivingMultiplier> consumer, Attribute attribute) {
      this.setAttribute(attribute);
      consumer.accept(this);
   }

   @Override
   public LivingMultiplier.Serializer getSerializer() {
      return (LivingMultiplier.Serializer)PSTLivingMultipliers.ATTRIBUTE_VALUE.get();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         AttributeValueMultiplier that = (AttributeValueMultiplier)o;
         return Float.compare(this.divisor, that.divisor) == 0 && Objects.equals(this.attribute, that.attribute);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.attribute, this.divisor);
   }

   public void setAttribute(Attribute attribute) {
      this.attribute = attribute;
   }

   public void setDivisor(float divisor) {
      this.divisor = divisor;
   }

   public static class Serializer implements LivingMultiplier.Serializer {
      public LivingMultiplier deserialize(JsonObject json) throws JsonParseException {
         Attribute attribute = SerializationHelper.deserializeAttribute(json);
         float divisor = !json.has("divisor") ? 1.0F : json.get("divisor").getAsFloat();
         return new AttributeValueMultiplier(attribute, divisor);
      }

      public void serialize(JsonObject json, LivingMultiplier multiplier) {
         if (multiplier instanceof AttributeValueMultiplier aMultiplier) {
            SerializationHelper.serializeAttribute(json, aMultiplier.attribute);
            json.addProperty("divisor", aMultiplier.divisor);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(CompoundTag tag) {
         Attribute attribute = SerializationHelper.deserializeAttribute(tag);
         float divisor = !tag.m_128441_("divisor") ? 1.0F : tag.m_128457_("divisor");
         return new AttributeValueMultiplier(attribute, divisor);
      }

      public CompoundTag serialize(LivingMultiplier multiplier) {
         if (multiplier instanceof AttributeValueMultiplier aMultiplier) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeAttribute(tag, aMultiplier.attribute);
            tag.m_128350_("divisor", aMultiplier.divisor);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(FriendlyByteBuf buf) {
         Attribute attribute = NetworkHelper.readAttribute(buf);
         float divisor = buf.readFloat();
         return new AttributeValueMultiplier(attribute, divisor);
      }

      public void serialize(FriendlyByteBuf buf, LivingMultiplier multiplier) {
         if (multiplier instanceof AttributeValueMultiplier aMultiplier) {
            NetworkHelper.writeAttribute(buf, aMultiplier.attribute);
            buf.writeFloat(aMultiplier.divisor);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingMultiplier createDefaultInstance() {
         return new AttributeValueMultiplier(Attributes.f_22284_, 1.0F);
      }
   }
}
