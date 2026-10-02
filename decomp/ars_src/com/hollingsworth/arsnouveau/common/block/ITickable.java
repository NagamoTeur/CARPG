package com.hollingsworth.arsnouveau.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ITickable {
   default void tick(Level level, BlockState state, BlockPos pos) {
      this.tick();
   }

   default void tick() {
   }
}
