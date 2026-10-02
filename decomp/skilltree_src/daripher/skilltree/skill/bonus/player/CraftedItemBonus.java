package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemDurabilityBonus;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class CraftedItemBonus implements SkillBonus<CraftedItemBonus> {
   @Nonnull
   private ItemBonus<?> bonus;
   @Nonnull
   private ItemCondition itemCondition;

   public CraftedItemBonus(@NotNull ItemCondition itemCondition, @NotNull ItemBonus<?> bonus) {
      this.itemCondition = itemCondition;
      this.bonus = bonus;
   }

   public void itemCrafted(ItemStack stack) {
      if (this.itemCondition.met(stack)) {
         ItemHelper.addItemBonus(stack, this.bonus.copy());
      }
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.CRAFTED_ITEM_BONUS.get();
   }

   public CraftedItemBonus copy() {
      return new CraftedItemBonus(this.itemCondition, this.bonus.copy());
   }

   public CraftedItemBonus multiply(double multiplier) {
      this.bonus = this.bonus.copy().multiply(multiplier);
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      if (other instanceof CraftedItemBonus otherBonus) {
         return !Objects.equals(otherBonus.itemCondition, this.itemCondition) ? false : otherBonus.bonus.canMerge(this.bonus);
      } else {
         return false;
      }
   }

   @Override
   public SkillBonus<CraftedItemBonus> merge(SkillBonus<?> other) {
      if (other instanceof CraftedItemBonus otherBonus) {
         return new CraftedItemBonus(this.itemCondition, otherBonus.bonus.merge(this.bonus));
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      Component itemDescription = this.itemCondition.getTooltip("plural.type");
      Component bonusDescription = this.bonus.getTooltip().m_130948_(TooltipHelper.getItemBonusStyle(this.isPositive()));
      return Component.m_237110_(this.getDescriptionId(), new Object[]{itemDescription, bonusDescription})
         .m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.bonus.isPositive();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<CraftedItemBonus> consumer) {
      editor.addLabel(0, 0, "Item Condition", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.itemCondition)
         .setResponder(condition -> this.selectItemCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addItemConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Item Bonus", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.bonus)
         .setResponder(bonus -> this.selectItemBonus(editor, consumer, bonus))
         .setMenuInitFunc(() -> this.addItemBonusWidgets(editor, index, consumer));
      editor.increaseHeight(19);
   }

   private void selectItemBonus(SkillTreeEditor editor, Consumer<CraftedItemBonus> consumer, ItemBonus bonus) {
      this.setBonus(bonus);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectItemCondition(SkillTreeEditor editor, Consumer<CraftedItemBonus> consumer, ItemCondition condition) {
      this.setItemCondition(condition);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void addItemConditionWidgets(SkillTreeEditor editor, Consumer<CraftedItemBonus> consumer) {
      this.itemCondition.addEditorWidgets(editor, c -> {
         this.setItemCondition(c);
         consumer.accept(this.copy());
      });
   }

   private void addItemBonusWidgets(SkillTreeEditor editor, int index, Consumer<CraftedItemBonus> consumer) {
      this.bonus.addEditorWidgets(editor, index, b -> {
         this.setBonus(b);
         consumer.accept(this.copy());
      });
   }

   public void setItemCondition(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   public void setBonus(@Nonnull ItemBonus<?> bonus) {
      this.bonus = bonus;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         CraftedItemBonus that = (CraftedItemBonus)obj;
         return !Objects.equals(this.itemCondition, that.itemCondition) ? false : Objects.equals(this.bonus, that.bonus);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.itemCondition, this.bonus);
   }

   public static class Serializer implements SkillBonus.Serializer {
      public CraftedItemBonus deserialize(JsonObject json) throws JsonParseException {
         ItemCondition condition = SerializationHelper.deserializeItemCondition(json);
         ItemBonus<?> bonus = SerializationHelper.deserializeItemBonus(json);
         return new CraftedItemBonus(condition, bonus);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof CraftedItemBonus aBonus) {
            SerializationHelper.serializeItemCondition(json, aBonus.itemCondition);
            SerializationHelper.serializeItemBonus(json, aBonus.bonus);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public CraftedItemBonus deserialize(CompoundTag tag) {
         ItemCondition condition = SerializationHelper.deserializeItemCondition(tag);
         ItemBonus<?> bonus = SerializationHelper.deserializeItemBonus(tag);
         return new CraftedItemBonus(condition, bonus);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof CraftedItemBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aBonus.itemCondition);
            SerializationHelper.serializeItemBonus(tag, aBonus.bonus);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public CraftedItemBonus deserialize(FriendlyByteBuf buf) {
         ItemCondition condition = NetworkHelper.readItemCondition(buf);
         ItemBonus<?> bonus = NetworkHelper.readItemBonus(buf);
         return new CraftedItemBonus(condition, bonus);
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof CraftedItemBonus aBonus) {
            NetworkHelper.writeItemCondition(buf, aBonus.itemCondition);
            NetworkHelper.writeItemBonus(buf, aBonus.bonus);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), new ItemDurabilityBonus(100.0F, Operation.ADDITION));
      }
   }
}
