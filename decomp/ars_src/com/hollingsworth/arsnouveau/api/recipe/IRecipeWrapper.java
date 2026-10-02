package com.hollingsworth.arsnouveau.api.recipe;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IRecipeWrapper {
   @Nullable
   IRecipeWrapper.InstructionsForRecipe canCraft(Map<Item, Integer> var1, Level var2, BlockPos var3);

   public static record InstructionsForRecipe(SingleRecipe recipe, List<ItemStack> itemsNeeded) {
   }
}
