package io.redspace.ironsspellbooks.block.alchemist_cauldron;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface AlchemistCauldronInteraction {
   @Nullable
   ItemStack interact(AlchemistCauldronTile var1, BlockState var2, Level var3, BlockPos var4, ItemStack var5);
}
