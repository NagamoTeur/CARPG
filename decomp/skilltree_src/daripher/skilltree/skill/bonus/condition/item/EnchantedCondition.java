package daripher.skilltree.skill.bonus.condition.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTItemConditions;
import daripher.skilltree.network.NetworkHelper;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.Tags.Items;

public final class EnchantedCondition implements ItemCondition {
   private ItemCondition itemCondition;

   public EnchantedCondition(ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   @Override
   public boolean met(ItemStack stack) {
      return !EnchantmentHelper.m_44831_(stack).isEmpty() && this.itemCondition.met(stack);
   }

   @Override
   public Component getTooltip() {
      return Component.m_237110_(this.getDescriptionId(), new Object[]{this.itemCondition.getTooltip("type")});
   }

   @Override
   public Component getTooltip(String type) {
      return Component.m_237110_(this.getDescriptionId(), new Object[]{this.itemCondition.getTooltip(type + ".type")});
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EnchantedCondition that = (EnchantedCondition)o;
         return this.itemCondition.equals(that.itemCondition);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.itemCondition);
   }

   @Override
   public ItemCondition.Serializer getSerializer() {
      return (ItemCondition.Serializer)PSTItemConditions.ENCHANTED.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<ItemCondition> consumer) {
      editor.addLabel(0, 0, "Inner Item Condition", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.itemCondition)
         .setResponder(condition -> this.selectItemCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addItemConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void addItemConditionWidgets(SkillTreeEditor editor, Consumer<ItemCondition> consumer) {
      this.itemCondition.addEditorWidgets(editor, condition -> {
         this.setItemCondition(condition);
         consumer.accept(this);
      });
   }

   private void selectItemCondition(SkillTreeEditor editor, Consumer<ItemCondition> consumer, ItemCondition condition) {
      this.setItemCondition(condition);
      consumer.accept(this);
      editor.rebuildWidgets();
   }

   public void setItemCondition(ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   public static class Serializer implements ItemCondition.Serializer {
      public ItemCondition deserialize(JsonObject json) throws JsonParseException {
         return new EnchantedCondition(SerializationHelper.deserializeItemCondition(json));
      }

      public void serialize(JsonObject json, ItemCondition condition) {
         if (condition instanceof EnchantedCondition aCondition) {
            SerializationHelper.serializeItemCondition(json, aCondition.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(CompoundTag tag) {
         return new EnchantedCondition(SerializationHelper.deserializeItemCondition(tag));
      }

      public CompoundTag serialize(ItemCondition condition) {
         if (condition instanceof EnchantedCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aCondition.itemCondition);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(FriendlyByteBuf buf) {
         return new EnchantedCondition(NetworkHelper.readItemCondition(buf));
      }

      public void serialize(FriendlyByteBuf buf, ItemCondition condition) {
         if (condition instanceof EnchantedCondition aCondition) {
            NetworkHelper.writeItemCondition(buf, aCondition.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemCondition createDefaultInstance() {
         return new EnchantedCondition(new ItemTagCondition(Items.TOOLS_SWORDS.f_203868_()));
      }
   }
}
