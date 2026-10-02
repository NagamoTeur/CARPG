package daripher.skilltree.skill.bonus.multiplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.init.PSTLivingMultipliers;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class GemsAmountMultiplier implements LivingMultiplier {
   @Nonnull
   private ItemCondition itemCondition;

   public GemsAmountMultiplier(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   @Override
   public float getValue(LivingEntity entity) {
      return (float)this.getGems(PlayerHelper.getAllEquipment(entity).filter(this.itemCondition::met));
   }

   private int getGems(Stream<ItemStack> items) {
      return items.map(ItemHelper::getGemsAmount).reduce(Integer::sum).orElse(0);
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, SkillBonus.Target target) {
      Component itemDescription = this.itemCondition.getTooltip("where");
      return Component.m_237110_(this.getDescriptionId(target), new Object[]{bonusTooltip, itemDescription});
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingMultiplier> consumer) {
      editor.addLabel(0, 0, "Item Condition", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.itemCondition)
         .setResponder(condition -> this.selectItemCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addItemConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void addItemConditionWidgets(SkillTreeEditor editor, Consumer<LivingMultiplier> consumer) {
      this.itemCondition.addEditorWidgets(editor, condition -> {
         this.setItemCondition(condition);
         consumer.accept(this);
      });
   }

   private void selectItemCondition(SkillTreeEditor editor, Consumer<LivingMultiplier> consumer, ItemCondition condition) {
      this.setItemCondition(condition);
      consumer.accept(this);
      editor.rebuildWidgets();
   }

   @Override
   public LivingMultiplier.Serializer getSerializer() {
      return (LivingMultiplier.Serializer)PSTLivingMultipliers.GEMS_AMOUNT.get();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         GemsAmountMultiplier that = (GemsAmountMultiplier)o;
         return Objects.equals(this.itemCondition, that.itemCondition);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.itemCondition);
   }

   public void setItemCondition(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   @Override
   public String toString() {
      return "GemsAmountMultiplier[itemCondition=" + this.itemCondition + "]";
   }

   public static class Serializer implements LivingMultiplier.Serializer {
      public LivingMultiplier deserialize(JsonObject json) throws JsonParseException {
         ItemCondition itemCondition = SerializationHelper.deserializeItemCondition(json);
         return new GemsAmountMultiplier(itemCondition);
      }

      public void serialize(JsonObject json, LivingMultiplier multiplier) {
         if (multiplier instanceof GemsAmountMultiplier aMultiplier) {
            SerializationHelper.serializeItemCondition(json, aMultiplier.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(CompoundTag tag) {
         ItemCondition itemCondition = SerializationHelper.deserializeItemCondition(tag);
         return new GemsAmountMultiplier(itemCondition);
      }

      public CompoundTag serialize(LivingMultiplier multiplier) {
         if (multiplier instanceof GemsAmountMultiplier aMultiplier) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aMultiplier.itemCondition);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(FriendlyByteBuf buf) {
         return new GemsAmountMultiplier(NetworkHelper.readItemCondition(buf));
      }

      public void serialize(FriendlyByteBuf buf, LivingMultiplier multiplier) {
         if (multiplier instanceof GemsAmountMultiplier aMultiplier) {
            NetworkHelper.writeItemCondition(buf, aMultiplier.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingMultiplier createDefaultInstance() {
         return new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.ANY));
      }
   }
}
