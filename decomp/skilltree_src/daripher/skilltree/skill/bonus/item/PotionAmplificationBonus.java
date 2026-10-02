package daripher.skilltree.skill.bonus.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTItemBonuses;
import daripher.skilltree.item.ItemHelper;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;

public final class PotionAmplificationBonus implements ItemBonus<PotionAmplificationBonus> {
   private float chance;

   public PotionAmplificationBonus(float chance) {
      this.chance = chance;
   }

   @Override
   public void itemCrafted(ItemStack stack) {
      if (this.chance % 1.0F != 0.0F) {
         ItemHelper.removeItemBonus(stack, this);
         Random random = new Random();
         if (random.nextFloat() < this.chance % 1.0F) {
            ItemHelper.addItemBonus(stack, new PotionAmplificationBonus((float)(1 + (int)this.chance)));
         } else {
            ItemHelper.addItemBonus(stack, new PotionAmplificationBonus((float)((int)this.chance)));
         }
      }
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof PotionAmplificationBonus otherBonus ? this.chance == otherBonus.chance : false;
   }

   public PotionAmplificationBonus merge(ItemBonus<?> other) {
      if (other instanceof PotionAmplificationBonus otherBonus) {
         return new PotionAmplificationBonus(this.chance + otherBonus.chance);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public PotionAmplificationBonus copy() {
      return new PotionAmplificationBonus(this.chance);
   }

   public PotionAmplificationBonus multiply(double multiplier) {
      return new PotionAmplificationBonus((float)(multiplier * multiplier));
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.POTION_AMPLIFICATION.get();
   }

   @Override
   public MutableComponent getTooltip() {
      return TooltipHelper.getSkillBonusTooltip(this.getDescriptionId(), (double)this.chance, Operation.MULTIPLY_BASE);
   }

   @Override
   public boolean isPositive() {
      return this.chance > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      editor.addLabel(0, 0, "Chance", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.getChance()).setNumericResponder(value -> this.selectChance(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectChance(Consumer<ItemBonus<?>> consumer, Double value) {
      this.setChance(value.floatValue());
      consumer.accept(this);
   }

   public void setChance(float chance) {
      this.chance = chance;
   }

   public float getChance() {
      return this.chance;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         PotionAmplificationBonus that = (PotionAmplificationBonus)obj;
         return Float.floatToIntBits(this.chance) == Float.floatToIntBits(that.chance);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.chance);
   }

   public static class Serializer implements ItemBonus.Serializer {
      public ItemBonus<?> deserialize(JsonObject json) throws JsonParseException {
         float chance = json.get("chance").getAsFloat();
         return new PotionAmplificationBonus(chance);
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof PotionAmplificationBonus aBonus) {
            json.addProperty("chance", aBonus.chance);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         float chance = tag.m_128457_("chance");
         return new PotionAmplificationBonus(chance);
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof PotionAmplificationBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("chance", aBonus.chance);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new PotionAmplificationBonus(buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof PotionAmplificationBonus aBonus) {
            buf.writeFloat(aBonus.chance);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new PotionAmplificationBonus(0.1F);
      }
   }
}
