package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.enchantment.EnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.NoneEnchantmentCondition;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class EnchantmentAmplificationBonus implements SkillBonus<EnchantmentAmplificationBonus> {
   @Nonnull
   private EnchantmentCondition condition;
   private float chance;

   public EnchantmentAmplificationBonus(@Nonnull EnchantmentCondition condition, float chance) {
      this.condition = condition;
      this.chance = chance;
   }

   public EnchantmentAmplificationBonus(float chance) {
      this(NoneEnchantmentCondition.INSTANCE, chance);
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.ENCHANTMENT_AMPLIFICATION.get();
   }

   public EnchantmentAmplificationBonus copy() {
      return new EnchantmentAmplificationBonus(this.condition, this.chance);
   }

   public EnchantmentAmplificationBonus multiply(double multiplier) {
      return new EnchantmentAmplificationBonus(this.condition, (float)((double)this.getChance() * multiplier));
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      return other instanceof EnchantmentAmplificationBonus otherBonus ? Objects.equals(otherBonus.condition, this.condition) : false;
   }

   @Override
   public SkillBonus<EnchantmentAmplificationBonus> merge(SkillBonus<?> other) {
      if (other instanceof EnchantmentAmplificationBonus otherBonus) {
         return new EnchantmentAmplificationBonus(this.condition, otherBonus.chance + this.chance);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      MutableComponent enchantmentDescription = Component.m_237115_(this.condition.getDescriptionId());
      MutableComponent bonusDescription = TooltipHelper.getSkillBonusTooltip(this.getDescriptionId() + ".bonus", (double)this.chance, Operation.MULTIPLY_BASE)
         .m_130948_(TooltipHelper.getItemBonusStyle(this.isPositive()));
      return Component.m_237110_(this.getDescriptionId(), new Object[]{enchantmentDescription, bonusDescription})
         .m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.chance > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<EnchantmentAmplificationBonus> consumer) {
      editor.addLabel(0, 0, "Chance", ChatFormatting.GOLD);
      editor.addLabel(55, 0, "Enchantment Condition", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.chance).setNumericResponder(value -> this.selectChance(consumer, value));
      editor.addSelectionMenu(55, 0, 145, this.condition).setResponder(condition -> this.selectEnchantmentCondition(consumer, condition));
      editor.increaseHeight(19);
   }

   private void selectEnchantmentCondition(Consumer<EnchantmentAmplificationBonus> consumer, EnchantmentCondition condition) {
      this.setCondition(condition);
      consumer.accept(this.copy());
   }

   private void selectChance(Consumer<EnchantmentAmplificationBonus> consumer, Double value) {
      this.setChance(value.floatValue());
      consumer.accept(this.copy());
   }

   public void setCondition(@Nonnull EnchantmentCondition condition) {
      this.condition = condition;
   }

   public void setChance(float chance) {
      this.chance = chance;
   }

   @Nonnull
   public EnchantmentCondition getCondition() {
      return this.condition;
   }

   public float getChance() {
      return this.chance;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         EnchantmentAmplificationBonus that = (EnchantmentAmplificationBonus)obj;
         return !Objects.equals(this.condition, that.condition) ? false : this.chance == that.chance;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.condition, this.chance);
   }

   public static class Serializer implements SkillBonus.Serializer {
      public EnchantmentAmplificationBonus deserialize(JsonObject json) throws JsonParseException {
         EnchantmentCondition condition = SerializationHelper.deserializeEnchantmentCondition(json);
         float multiplier = SerializationHelper.getElement(json, "chance").getAsFloat();
         return new EnchantmentAmplificationBonus(condition, multiplier);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof EnchantmentAmplificationBonus aBonus) {
            SerializationHelper.serializeEnchantmentCondition(json, aBonus.condition);
            json.addProperty("chance", aBonus.chance);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public EnchantmentAmplificationBonus deserialize(CompoundTag tag) {
         EnchantmentCondition condition = SerializationHelper.deserializeEnchantmentCondition(tag);
         float multiplier = tag.m_128457_("chance");
         return new EnchantmentAmplificationBonus(condition, multiplier);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof EnchantmentAmplificationBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeEnchantmentCondition(tag, aBonus.condition);
            tag.m_128350_("chance", aBonus.chance);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public EnchantmentAmplificationBonus deserialize(FriendlyByteBuf buf) {
         return new EnchantmentAmplificationBonus(NetworkHelper.readEnchantmentCondition(buf), buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof EnchantmentAmplificationBonus aBonus) {
            NetworkHelper.writeEnchantmentCondition(buf, aBonus.condition);
            buf.writeFloat(aBonus.chance);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new EnchantmentAmplificationBonus(0.1F);
      }
   }
}
