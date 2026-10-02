package com.hollingsworth.arsnouveau.api.enchanting_apparatus;

import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public interface IEnchantingRecipe extends Recipe<EnchantingApparatusTile> {
   boolean isMatch(List<ItemStack> var1, ItemStack var2, EnchantingApparatusTile var3, @Nullable Player var4);

   ItemStack getResult(List<ItemStack> var1, ItemStack var2, EnchantingApparatusTile var3);

   default boolean consumesSource() {
      return this.getSourceCost() > 0;
   }

   int getSourceCost();
}
