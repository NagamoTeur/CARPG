package daripher.skilltree.skill.bonus.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTItemBonuses;
import daripher.skilltree.network.NetworkHelper;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class ItemDurabilityBonus implements ItemBonus<ItemDurabilityBonus> {
   private float amount;
   private Operation operation;

   public ItemDurabilityBonus(float amount, Operation operation) {
      this.amount = amount;
      this.operation = operation;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof ItemDurabilityBonus otherBonus ? this.operation == otherBonus.operation : false;
   }

   public ItemDurabilityBonus merge(ItemBonus<?> other) {
      if (other instanceof ItemDurabilityBonus otherBonus) {
         return new ItemDurabilityBonus(this.amount + otherBonus.amount, this.operation);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public ItemDurabilityBonus copy() {
      return new ItemDurabilityBonus(this.amount, this.operation);
   }

   public ItemDurabilityBonus multiply(double multiplier) {
      return new ItemDurabilityBonus((float)((double)this.amount * multiplier), this.operation);
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.DURABILITY.get();
   }

   @Override
   public MutableComponent getTooltip() {
      return TooltipHelper.getSkillBonusTooltip(this.getDescriptionId(), (double)this.amount, this.operation);
   }

   @Override
   public boolean isPositive() {
      return this.amount > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      editor.addLabel(0, 0, "Amount", ChatFormatting.GREEN);
      editor.addLabel(55, 0, "Operation", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.amount).setNumericResponder(value -> this.selectAmount(consumer, value));
      editor.addOperationSelection(55, 0, 145, this.operation).setResponder(operation -> this.selectOperation(consumer, operation));
      editor.increaseHeight(19);
   }

   private void selectOperation(Consumer<ItemBonus<?>> consumer, Operation operation) {
      this.setOperation(operation);
      consumer.accept(this);
   }

   private void selectAmount(Consumer<ItemBonus<?>> consumer, Double value) {
      this.setAmount(value.floatValue());
      consumer.accept(this);
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public void setOperation(Operation operation) {
      this.operation = operation;
   }

   public float getAmount() {
      return this.amount;
   }

   public Operation getOperation() {
      return this.operation;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ItemDurabilityBonus that = (ItemDurabilityBonus)obj;
         return Float.floatToIntBits(this.amount) == Float.floatToIntBits(that.amount) && Objects.equals(this.operation, that.operation);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.amount, this.operation);
   }

   public static class Serializer implements ItemBonus.Serializer {
      public ItemBonus<?> deserialize(JsonObject json) throws JsonParseException {
         float amount = SerializationHelper.getElement(json, "amount").getAsFloat();
         Operation operation = SerializationHelper.deserializeOperation(json);
         return new ItemDurabilityBonus(amount, operation);
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof ItemDurabilityBonus aBonus) {
            json.addProperty("amount", aBonus.amount);
            SerializationHelper.serializeOperation(json, aBonus.operation);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         float amount = tag.m_128457_("amount");
         Operation operation = SerializationHelper.deserializeOperation(tag);
         return new ItemDurabilityBonus(amount, operation);
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof ItemDurabilityBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("amount", aBonus.amount);
            SerializationHelper.serializeOperation(tag, aBonus.operation);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new ItemDurabilityBonus(buf.readFloat(), NetworkHelper.readOperation(buf));
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof ItemDurabilityBonus aBonus) {
            buf.writeFloat(aBonus.amount);
            NetworkHelper.writeOperation(buf, aBonus.operation);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new ItemDurabilityBonus(100.0F, Operation.ADDITION);
      }
   }
}
