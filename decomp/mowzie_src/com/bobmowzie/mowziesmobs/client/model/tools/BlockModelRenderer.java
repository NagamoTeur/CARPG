package com.bobmowzie.mowziesmobs.client.model.tools;

import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockModelRenderer extends AdvancedModelRenderer {
   private BlockState blockState;

   public BlockModelRenderer(AdvancedModelBase model) {
      super(model);
      this.setBlockState(Blocks.f_50493_.m_49966_());
   }

   public void setBlockState(BlockState blockState) {
      this.blockState = blockState;
   }

   public BlockState getBlockState() {
      return this.blockState;
   }
}
