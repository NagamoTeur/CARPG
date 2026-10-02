package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.CreativeSourceJarTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeSourceJar extends SourceJar {
   public CreativeSourceJar() {
      super(TickableModBlock.defaultProperties().m_60955_(), "creative_source_jar");
      this.m_49959_((BlockState)this.m_49966_().m_61124_(SourceJar.fill, 11));
   }

   @Override
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new CreativeSourceJarTile(pos, state);
   }
}
