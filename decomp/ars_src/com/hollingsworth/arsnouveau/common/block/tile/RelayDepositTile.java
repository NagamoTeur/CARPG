package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RelayDepositTile extends RelayTile {
   public RelayDepositTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.RELAY_DEPOSIT_TILE, pos, state);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.disabled) {
         if (!this.f_58857_.f_46443_ && this.f_58857_.m_46467_() % 20L == 0L && this.getSource() > 0) {
            for (ISpecialSourceProvider provider : SourceUtil.canGiveSource(this.f_58858_, this.f_58857_, 5)) {
               if (this.getSource() <= 0) {
                  break;
               }

               if ((this.getToPos() == null || !this.f_58857_.m_46749_(this.getToPos()) || this.f_58857_.m_7702_(this.getToPos()) != provider.getSource())
                  && (
                     this.getFromPos() == null
                        || !this.f_58857_.m_46749_(this.getFromPos())
                        || this.f_58857_.m_7702_(this.getFromPos()) != provider.getSource()
                  )
                  && !(provider.getSource() instanceof RelayTile)) {
                  this.transferSource(this, provider.getSource());
                  ParticleUtil.spawnFollowProjectile(this.f_58857_, this.f_58858_, provider.getCurrentPos());
               }
            }
         }
      }
   }
}
