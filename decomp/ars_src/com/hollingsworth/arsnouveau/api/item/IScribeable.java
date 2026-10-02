package com.hollingsworth.arsnouveau.api.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IScribeable {
   boolean onScribe(Level var1, BlockPos var2, Player var3, InteractionHand var4, ItemStack var5);
}
