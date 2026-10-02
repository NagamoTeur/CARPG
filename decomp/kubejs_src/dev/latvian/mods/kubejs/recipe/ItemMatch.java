package dev.latvian.mods.kubejs.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public interface ItemMatch extends ReplacementMatch {
   boolean contains(ItemStack var1);

   boolean contains(Ingredient var1);

   @Deprecated(
      forRemoval = true
   )
   default boolean contains(Block block) {
      Item item = block.m_5456_();
      return item != Items.f_41852_ && this.contains(item.m_7968_());
   }

   default boolean contains(ItemLike itemLike) {
      Item item = itemLike.m_5456_();
      return item != Items.f_41852_ && this.contains(item.m_7968_());
   }

   default boolean containsAny(ItemLike... itemLikes) {
      for (ItemLike item : itemLikes) {
         if (this.contains(item)) {
            return true;
         }
      }

      return false;
   }

   default boolean containsAny(Iterable<ItemLike> itemLikes) {
      for (ItemLike item : itemLikes) {
         if (this.contains(item)) {
            return true;
         }
      }

      return false;
   }
}
