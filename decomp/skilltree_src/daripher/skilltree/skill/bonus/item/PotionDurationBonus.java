package daripher.skilltree.skill.bonus.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTItemBonuses;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class PotionDurationBonus implements ItemBonus<PotionDurationBonus> {
   private float multiplier;

   public PotionDurationBonus(float multiplier) {
      this.multiplier = multiplier;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof PotionDurationBonus otherBonus ? this.multiplier == otherBonus.multiplier : false;
   }

   public PotionDurationBonus merge(ItemBonus<?> other) {
      if (other instanceof PotionDurationBonus otherBonus) {
         return new PotionDurationBonus(this.multiplier + otherBonus.multiplier);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public PotionDurationBonus copy() {
      return new PotionDurationBonus(this.multiplier);
   }

   public PotionDurationBonus multiply(double multiplier) {
      return new PotionDurationBonus((float)(multiplier * multiplier));
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.POTION_DURATION.get();
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
      editor.addNumericTextField(0, 0, 50, 14, (double)this.getMultiplier()).setNumericResponder(value -> this.selectMultiplier(consumer, value));
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
         PotionDurationBonus that = (PotionDurationBonus)obj;
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
         float multiplier = json.get("multiplier").getAsFloat();
         return new PotionDurationBonus(multiplier);
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof PotionDurationBonus aBonus) {
            json.addProperty("multiplier", aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         float multiplier = tag.m_128457_("multiplier");
         return new PotionDurationBonus(multiplier);
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof PotionDurationBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("multiplier", aBonus.multiplier);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new PotionDurationBonus(buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof PotionDurationBonus aBonus) {
            buf.writeFloat(aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new PotionDurationBonus(0.1F);
      }
   }
}
