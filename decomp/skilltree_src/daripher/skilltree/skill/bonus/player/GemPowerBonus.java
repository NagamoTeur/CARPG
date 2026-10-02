package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.item.NoneItemCondition;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class GemPowerBonus implements SkillBonus<GemPowerBonus> {
   @Nonnull
   private ItemCondition itemCondition;
   private float multiplier;

   public GemPowerBonus(@Nonnull ItemCondition itemCondition, float multiplier) {
      this.itemCondition = itemCondition;
      this.multiplier = multiplier;
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.GEM_POWER.get();
   }

   public GemPowerBonus copy() {
      return new GemPowerBonus(this.itemCondition, this.multiplier);
   }

   public GemPowerBonus multiply(double multiplier) {
      return new GemPowerBonus(this.itemCondition, (float)((double)this.getMultiplier() * multiplier));
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      return other instanceof GemPowerBonus otherBonus ? Objects.equals(otherBonus.itemCondition, this.itemCondition) : false;
   }

   @Override
   public SkillBonus<GemPowerBonus> merge(SkillBonus<?> other) {
      if (other instanceof GemPowerBonus otherBonus) {
         return new GemPowerBonus(this.itemCondition, otherBonus.multiplier + this.multiplier);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      Component itemDescription = this.itemCondition.getTooltip("crafted");
      Operation operation = Operation.MULTIPLY_BASE;
      Component bonusDescription = Component.m_237115_(this.getDescriptionId() + ".bonus");
      bonusDescription = TooltipHelper.getSkillBonusTooltip(bonusDescription, (double)this.multiplier, operation)
         .m_130948_(TooltipHelper.getItemBonusStyle(this.isPositive()));
      return Component.m_237110_(this.getDescriptionId(), new Object[]{itemDescription, bonusDescription})
         .m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.multiplier > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<GemPowerBonus> consumer) {
      editor.addLabel(0, 0, "Multiplier", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.multiplier).setNumericResponder(value -> this.selectMultiplier(consumer, value));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Item Condition", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.itemCondition)
         .setResponder(condition -> this.selectItemCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addItemConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void addItemConditionWidgets(SkillTreeEditor editor, Consumer<GemPowerBonus> consumer) {
      this.itemCondition.addEditorWidgets(editor, c -> {
         this.setItemCondition(c);
         consumer.accept(this.copy());
      });
   }

   private void selectItemCondition(SkillTreeEditor editor, Consumer<GemPowerBonus> consumer, ItemCondition condition) {
      this.setItemCondition(condition);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectMultiplier(Consumer<GemPowerBonus> consumer, Double value) {
      this.setMultiplier(value.floatValue());
      consumer.accept(this.copy());
   }

   public void setItemCondition(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   public void setMultiplier(float multiplier) {
      this.multiplier = multiplier;
   }

   @Nonnull
   public ItemCondition getItemCondition() {
      return this.itemCondition;
   }

   public float getMultiplier() {
      return this.multiplier;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         GemPowerBonus that = (GemPowerBonus)obj;
         return !Objects.equals(this.itemCondition, that.itemCondition) ? false : this.multiplier == that.multiplier;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.itemCondition, this.multiplier);
   }

   public static class Serializer implements SkillBonus.Serializer {
      public GemPowerBonus deserialize(JsonObject json) throws JsonParseException {
         ItemCondition condition = SerializationHelper.deserializeItemCondition(json);
         float multiplier = SerializationHelper.getElement(json, "multiplier").getAsFloat();
         return new GemPowerBonus(condition, multiplier);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof GemPowerBonus aBonus) {
            SerializationHelper.serializeItemCondition(json, aBonus.itemCondition);
            json.addProperty("multiplier", aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public GemPowerBonus deserialize(CompoundTag tag) {
         ItemCondition condition = SerializationHelper.deserializeItemCondition(tag);
         float multiplier = tag.m_128457_("multiplier");
         return new GemPowerBonus(condition, multiplier);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof GemPowerBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aBonus.itemCondition);
            tag.m_128350_("multiplier", aBonus.multiplier);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public GemPowerBonus deserialize(FriendlyByteBuf buf) {
         return new GemPowerBonus(NetworkHelper.readItemCondition(buf), buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof GemPowerBonus aBonus) {
            NetworkHelper.writeItemCondition(buf, aBonus.itemCondition);
            buf.writeFloat(aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new GemPowerBonus(NoneItemCondition.INSTANCE, 0.1F);
      }
   }
}
