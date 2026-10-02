package daripher.skilltree.skill.bonus.condition.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTItemConditions;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags.Items;

public class ItemTagCondition implements ItemCondition {
   private ResourceLocation tagId;

   public ItemTagCondition(ResourceLocation tagId) {
      this.tagId = tagId;
   }

   @Override
   public boolean met(ItemStack stack) {
      return stack.m_204117_(ItemTags.create(this.tagId));
   }

   @Override
   public String getDescriptionId() {
      return "item_tag.%s".formatted(this.tagId.toString());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ItemTagCondition that = (ItemTagCondition)o;
         return Objects.equals(this.tagId, that.tagId);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.tagId);
   }

   @Override
   public ItemCondition.Serializer getSerializer() {
      return (ItemCondition.Serializer)PSTItemConditions.TAG.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<ItemCondition> consumer) {
      editor.addLabel(0, 0, "Tag", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addTextField(0, 0, 200, 14, this.tagId.toString()).setSoftFilter(ResourceLocation::m_135830_).m_94151_(text -> this.selectTagId(consumer, text));
      editor.increaseHeight(19);
   }

   private void selectTagId(Consumer<ItemCondition> consumer, String text) {
      this.setTagId(new ResourceLocation(text));
      consumer.accept(this);
   }

   public void setTagId(ResourceLocation tagId) {
      this.tagId = tagId;
   }

   public static class Serializer implements ItemCondition.Serializer {
      public ItemCondition deserialize(JsonObject json) throws JsonParseException {
         ResourceLocation tagId = new ResourceLocation(json.get("tag_id").getAsString());
         return new ItemTagCondition(tagId);
      }

      public void serialize(JsonObject json, ItemCondition condition) {
         if (condition instanceof ItemTagCondition aCondition) {
            json.addProperty("tag_id", aCondition.tagId.toString());
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(CompoundTag tag) {
         ResourceLocation tagId = new ResourceLocation(tag.m_128461_("tag_id"));
         return new ItemTagCondition(tagId);
      }

      public CompoundTag serialize(ItemCondition condition) {
         if (condition instanceof ItemTagCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            tag.m_128359_("tag_id", aCondition.tagId.toString());
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(FriendlyByteBuf buf) {
         ResourceLocation tagId = new ResourceLocation(buf.m_130277_());
         return new ItemTagCondition(tagId);
      }

      public void serialize(FriendlyByteBuf buf, ItemCondition condition) {
         if (condition instanceof ItemTagCondition aCondition) {
            buf.m_130070_(aCondition.tagId.toString());
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemCondition createDefaultInstance() {
         return new ItemTagCondition(Items.ARMORS.f_203868_());
      }
   }
}
