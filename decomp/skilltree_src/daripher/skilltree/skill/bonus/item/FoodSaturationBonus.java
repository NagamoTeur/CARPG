package daripher.skilltree.skill.bonus.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTItemBonuses;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class FoodSaturationBonus implements ItemBonus<FoodSaturationBonus> {
   private float multiplier;

   public FoodSaturationBonus(float multiplier) {
      this.multiplier = multiplier;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof FoodSaturationBonus;
   }

   public FoodSaturationBonus merge(ItemBonus<?> other) {
      if (other instanceof FoodSaturationBonus otherBonus) {
         return new FoodSaturationBonus(this.multiplier + otherBonus.multiplier);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public FoodSaturationBonus copy() {
      return new FoodSaturationBonus(this.multiplier);
   }

   public FoodSaturationBonus multiply(double multiplier) {
      return new FoodSaturationBonus((float)(multiplier * multiplier));
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.FOOD_SATURATION.get();
   }

   @Override
   public MutableComponent getTooltip() {
      return TooltipHelper.getSkillBonusTooltip(this.getDescriptionId(), (double)this.multiplier, Operation.MULTIPLY_BASE);
   }

   @Override
   public boolean isPositive() {
      return this.multiplier > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      editor.addLabel(0, 0, "Multiplier", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 90, 14, (double)this.getMultiplier()).setNumericResponder(value -> this.selectMultiplier(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectMultiplier(Consumer<ItemBonus<?>> consumer, Double value) {
      this.setMultiplier(value.floatValue());
      consumer.accept(this);
   }

   public void setMultiplier(float multiplier) {
      this.multiplier = multiplier;
   }

   public float getMultiplier() {
      return this.multiplier;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         FoodSaturationBonus that = (FoodSaturationBonus)obj;
         return Float.floatToIntBits(this.multiplier) == Float.floatToIntBits(that.multiplier);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.multiplier);
   }

   public static class Serializer implements ItemBonus.Serializer {
      public ItemBonus<?> deserialize(JsonObject json) throws JsonParseException {
         float multiplier = SerializationHelper.getElement(json, "multiplier").getAsFloat();
         return new FoodSaturationBonus(multiplier);
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof FoodSaturationBonus aBonus) {
            json.addProperty("multiplier", aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         float multiplier = tag.m_128457_("multiplier");
         return new FoodSaturationBonus(multiplier);
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof FoodSaturationBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("multiplier", aBonus.multiplier);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new FoodSaturationBonus(buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof FoodSaturationBonus aBonus) {
            buf.writeFloat(aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new FoodSaturationBonus(0.1F);
      }
   }
}
