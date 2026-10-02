package daripher.skilltree.skill.bonus.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTItemBonuses;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public final class FoodHealingBonus implements ItemBonus<FoodHealingBonus> {
   private float amount;

   public FoodHealingBonus(float amount) {
      this.amount = amount;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof FoodHealingBonus;
   }

   public FoodHealingBonus merge(ItemBonus<?> other) {
      if (other instanceof FoodHealingBonus otherBonus) {
         return new FoodHealingBonus(this.amount + otherBonus.amount);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public FoodHealingBonus copy() {
      return new FoodHealingBonus(this.amount);
   }

   public FoodHealingBonus multiply(double multiplier) {
      return new FoodHealingBonus((float)(multiplier * multiplier));
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.FOOD_HEALING.get();
   }

   @Override
   public MutableComponent getTooltip() {
      double visibleAmount = (double)this.amount;
      if (this.amount < 0.0F) {
         visibleAmount *= -1.0;
      }

      String amountDescription = ItemStack.f_41584_.format(visibleAmount);
      return Component.m_237110_(this.getDescriptionId(), new Object[]{amountDescription});
   }

   @Override
   public boolean isPositive() {
      return this.amount > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      editor.addLabel(0, 0, "Amount", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 90, 14, (double)this.getAmount()).setNumericResponder(value -> this.selectAmount(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectAmount(Consumer<ItemBonus<?>> consumer, Double value) {
      this.setAmount(value.floatValue());
      consumer.accept(this);
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         FoodHealingBonus that = (FoodHealingBonus)obj;
         return Float.floatToIntBits(this.amount) == Float.floatToIntBits(that.amount);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.amount);
   }

   public static class Serializer implements ItemBonus.Serializer {
      public ItemBonus<?> deserialize(JsonObject json) throws JsonParseException {
         float multiplier = SerializationHelper.getElement(json, "amount").getAsFloat();
         return new FoodHealingBonus(multiplier);
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof FoodHealingBonus aBonus) {
            json.addProperty("amount", aBonus.amount);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         float multiplier = tag.m_128457_("amount");
         return new FoodHealingBonus(multiplier);
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof FoodHealingBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("amount", aBonus.amount);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new FoodHealingBonus(buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof FoodHealingBonus aBonus) {
            buf.writeFloat(aBonus.amount);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new FoodHealingBonus(2.0F);
      }
   }
}
