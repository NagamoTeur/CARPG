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

public final class ItemSocketsBonus implements ItemBonus<ItemSocketsBonus> {
   private int amount;

   public ItemSocketsBonus(int amount) {
      this.amount = amount;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof ItemSocketsBonus;
   }

   public ItemSocketsBonus merge(ItemBonus<?> other) {
      if (other instanceof ItemSocketsBonus otherBonus) {
         return new ItemSocketsBonus(this.amount + otherBonus.amount);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public ItemSocketsBonus copy() {
      return new ItemSocketsBonus(this.amount);
   }

   public ItemSocketsBonus multiply(double multiplier) {
      return new ItemSocketsBonus((int)((double)this.amount * multiplier));
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.SOCKETS.get();
   }

   @Override
   public MutableComponent getTooltip() {
      return Component.m_237110_(this.getDescriptionId(), new Object[]{this.amount});
   }

   @Override
   public boolean isPositive() {
      return this.amount > 0;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      editor.addLabel(0, 0, "Amount", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.getAmount()).setNumericResponder(value -> this.selectAmount(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectAmount(Consumer<ItemBonus<?>> consumer, Double value) {
      this.setAmount(value.intValue());
      consumer.accept(this);
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public int getAmount() {
      return this.amount;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ItemSocketsBonus that = (ItemSocketsBonus)obj;
         return this.amount == that.amount;
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
         int amount = SerializationHelper.getElement(json, "amount").getAsInt();
         return new ItemSocketsBonus(amount);
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof ItemSocketsBonus aBonus) {
            json.addProperty("amount", aBonus.amount);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         return new ItemSocketsBonus(tag.m_128451_("amount"));
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof ItemSocketsBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128405_("amount", aBonus.amount);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new ItemSocketsBonus(buf.readInt());
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof ItemSocketsBonus aBonus) {
            buf.writeInt(aBonus.amount);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new ItemSocketsBonus(1);
      }
   }
}
