package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RelayCollectorTile extends RelayTile {
   public RelayCollectorTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.RELAY_COLLECTOR_TILE, pos, state);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.disabled) {
         if (!this.f_58857_.f_46443_ && this.f_58857_.m_46467_() % 20L == 0L && this.getSource() <= this.getMaxSource()) {
            for (ISpecialSourceProvider provider : SourceUtil.canTakeSource(this.m_58899_(), this.f_58857_, 5)) {
               if (this.getSource() >= this.getMaxSource()) {
                  break;
               }

               if ((this.getToPos() == null || !this.f_58857_.m_46749_(this.getToPos()) || this.f_58857_.m_7702_(this.getToPos()) != provider.getSource())
                  && (
                     this.getFromPos() == null
                        || !this.f_58857_.m_46749_(this.getFromPos())
                        || this.f_58857_.m_7702_(this.getFromPos()) != provider.getSource()
                  )) {
                  int transferred = this.transferSource(provider.getSource(), this);
                  if (transferred > 0) {
                     ParticleUtil.spawnFollowProjectile(this.f_58857_, provider.getCurrentPos(), this.f_58858_);
                  }
               }
            }
         }
      }
   }
}
