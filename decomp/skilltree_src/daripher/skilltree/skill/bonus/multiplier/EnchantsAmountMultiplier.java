package daripher.skilltree.skill.bonus.multiplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.init.PSTLivingMultipliers;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import java.util.Map;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public final class EnchantsAmountMultiplier implements LivingMultiplier {
   @Nonnull
   private ItemCondition itemCondition;

   public EnchantsAmountMultiplier(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   @Override
   public float getValue(LivingEntity entity) {
      return (float)this.getEnchants(PlayerHelper.getAllEquipment(entity).filter(this.itemCondition::met));
   }

   private int getEnchants(Stream<ItemStack> items) {
      return items.<Map>map(EnchantmentHelper::m_44831_).map(Map::size).reduce(Integer::sum).orElse(0);
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
      return (LivingMultiplier.Serializer)PSTLivingMultipliers.ENCHANTS_AMOUNT.get();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EnchantsAmountMultiplier that = (EnchantsAmountMultiplier)o;
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

   public static class Serializer implements LivingMultiplier.Serializer {
      public LivingMultiplier deserialize(JsonObject json) throws JsonParseException {
         ItemCondition itemCondition = SerializationHelper.deserializeItemCondition(json);
         return new EnchantsAmountMultiplier(itemCondition);
      }

      public void serialize(JsonObject json, LivingMultiplier multiplier) {
         if (multiplier instanceof EnchantsAmountMultiplier aMultiplier) {
            SerializationHelper.serializeItemCondition(json, aMultiplier.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(CompoundTag tag) {
         ItemCondition itemCondition = SerializationHelper.deserializeItemCondition(tag);
         return new EnchantsAmountMultiplier(itemCondition);
      }

      public CompoundTag serialize(LivingMultiplier multiplier) {
         if (multiplier instanceof EnchantsAmountMultiplier aMultiplier) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aMultiplier.itemCondition);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(FriendlyByteBuf buf) {
         ItemCondition itemCondition = NetworkHelper.readItemCondition(buf);
         return new EnchantsAmountMultiplier(itemCondition);
      }

      public void serialize(FriendlyByteBuf buf, LivingMultiplier multiplier) {
         if (multiplier instanceof EnchantsAmountMultiplier aMultiplier) {
            NetworkHelper.writeItemCondition(buf, aMultiplier.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingMultiplier createDefaultInstance() {
         return new EnchantsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.ANY));
      }
   }
}
