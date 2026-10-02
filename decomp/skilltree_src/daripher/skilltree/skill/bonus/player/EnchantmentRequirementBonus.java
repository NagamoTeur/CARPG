package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class EnchantmentRequirementBonus implements SkillBonus<EnchantmentRequirementBonus> {
   private float multiplier;

   public EnchantmentRequirementBonus(float multiplier) {
      this.multiplier = multiplier;
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.ENCHANTMENT_REQUIREMENT.get();
   }

   public EnchantmentRequirementBonus copy() {
      return new EnchantmentRequirementBonus(this.multiplier);
   }

   public EnchantmentRequirementBonus multiply(double multiplier) {
      return new EnchantmentRequirementBonus((float)((double)this.getMultiplier() * multiplier));
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      return other instanceof EnchantmentRequirementBonus;
   }

   @Override
   public SkillBonus<EnchantmentRequirementBonus> merge(SkillBonus<?> other) {
      if (other instanceof EnchantmentRequirementBonus otherBonus) {
         return new EnchantmentRequirementBonus(otherBonus.multiplier + this.multiplier);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      MutableComponent bonusDescription = TooltipHelper.getSkillBonusTooltip(
            this.getDescriptionId() + ".bonus", (double)this.multiplier, Operation.MULTIPLY_BASE
         )
         .m_130948_(TooltipHelper.getItemBonusStyle(this.isPositive()));
      return Component.m_237110_(this.getDescriptionId(), new Object[]{bonusDescription}).m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.multiplier < 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<EnchantmentRequirementBonus> consumer) {
      editor.addLabel(0, 0, "Multiplier", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.multiplier).setNumericResponder(value -> this.selectMultiplier(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectMultiplier(Consumer<EnchantmentRequirementBonus> consumer, Double value) {
      this.setMultiplier(value.floatValue());
      consumer.accept(this.copy());
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
         EnchantmentRequirementBonus that = (EnchantmentRequirementBonus)obj;
         return Float.floatToIntBits(this.multiplier) == Float.floatToIntBits(that.multiplier);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.multiplier);
   }

   public static class Serializer implements SkillBonus.Serializer {
      public EnchantmentRequirementBonus deserialize(JsonObject json) throws JsonParseException {
         float multiplier = SerializationHelper.getElement(json, "multiplier").getAsFloat();
         return new EnchantmentRequirementBonus(multiplier);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof EnchantmentRequirementBonus aBonus) {
            json.addProperty("multiplier", aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public EnchantmentRequirementBonus deserialize(CompoundTag tag) {
         float multiplier = tag.m_128457_("multiplier");
         return new EnchantmentRequirementBonus(multiplier);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof EnchantmentRequirementBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("multiplier", aBonus.multiplier);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public EnchantmentRequirementBonus deserialize(FriendlyByteBuf buf) {
         return new EnchantmentRequirementBonus(buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof EnchantmentRequirementBonus aBonus) {
            buf.writeFloat(aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new EnchantmentRequirementBonus(-0.05F);
      }
   }
}
