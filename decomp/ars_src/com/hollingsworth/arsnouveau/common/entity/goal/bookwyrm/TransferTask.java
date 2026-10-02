package com.hollingsworth.arsnouveau.common.entity.goal.bookwyrm;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class TransferTask {
   public Vec3 from;
   public Vec3 to;
   public ItemStack stack;
   public long gameTime;

   public TransferTask(BlockPos from, BlockPos to, ItemStack stack, long gameTime) {
      this.from = new Vec3((double)from.m_123341_() + 0.5, (double)from.m_123342_(), (double)from.m_123343_() + 0.5);
      this.to = new Vec3((double)to.m_123341_() + 0.5, (double)to.m_123342_(), (double)to.m_123343_() + 0.5);
      this.stack = stack;
      this.gameTime = gameTime;
   }
}
