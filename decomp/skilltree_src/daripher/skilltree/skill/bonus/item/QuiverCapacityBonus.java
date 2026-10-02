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

public final class QuiverCapacityBonus implements ItemBonus<QuiverCapacityBonus> {
   private float amount;
   private Operation operation;

   public QuiverCapacityBonus(float amount, Operation operation) {
      this.amount = amount;
      this.operation = operation;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof QuiverCapacityBonus otherBonus ? this.operation == otherBonus.operation : false;
   }

   public QuiverCapacityBonus merge(ItemBonus<?> other) {
      if (other instanceof QuiverCapacityBonus otherBonus) {
         return new QuiverCapacityBonus(this.amount + otherBonus.amount, this.operation);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public QuiverCapacityBonus copy() {
      return new QuiverCapacityBonus(this.amount, this.operation);
   }

   public QuiverCapacityBonus multiply(double multiplier) {
      return new QuiverCapacityBonus((float)((double)this.amount * multiplier), this.operation);
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.QUIVER_CAPACITY.get();
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
         QuiverCapacityBonus that = (QuiverCapacityBonus)obj;
         return !Objects.equals(this.operation, that.operation) ? false : this.amount == that.amount;
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
         float amount = json.get("chance").getAsFloat();
         Operation operation = SerializationHelper.deserializeOperation(json);
         return new QuiverCapacityBonus(amount, operation);
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof QuiverCapacityBonus aBonus) {
            json.addProperty("chance", aBonus.amount);
            SerializationHelper.serializeOperation(json, aBonus.operation);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         float amount = tag.m_128457_("chance");
         Operation operation = SerializationHelper.deserializeOperation(tag);
         return new QuiverCapacityBonus(amount, operation);
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof QuiverCapacityBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("chance", aBonus.amount);
            SerializationHelper.serializeOperation(tag, aBonus.operation);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new QuiverCapacityBonus(buf.readFloat(), NetworkHelper.readOperation(buf));
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof QuiverCapacityBonus aBonus) {
            buf.writeFloat(aBonus.amount);
            NetworkHelper.writeOperation(buf, aBonus.operation);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new QuiverCapacityBonus(100.0F, Operation.ADDITION);
      }
   }
}
