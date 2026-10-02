package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RelayWarpTile extends RelaySplitterTile {
   public RelayWarpTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public RelayWarpTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.RELAY_WARP_TILE, pos, state);
   }

   @Override
   public void createParticles(BlockPos from, BlockPos to) {
      if (this.f_58857_.m_7702_(to) instanceof RelayWarpTile) {
         ParticleUtil.spawnTouchPacket(this.f_58857_, this.m_58899_(), new ParticleColor.IntWrapper(220, 50, 220));
         ParticleUtil.spawnTouchPacket(this.f_58857_, to, new ParticleColor.IntWrapper(220, 50, 220));
      } else {
         super.createParticles(from, to);
      }
   }

   @Override
   public int transferSource(ISourceTile from, ISourceTile to, int fromTransferRate) {
      if (to instanceof RelayWarpTile toWarp) {
         double adjustedDist = BlockUtil.distanceFrom(toWarp.f_58858_, this.f_58858_) - 30.0;
         double probLoss = adjustedDist / 100.0;
         if (adjustedDist > 0.0 && (double)this.f_58857_.m_213780_().m_188501_() < probLoss) {
            int transferRate = this.getTransferRate(from, to, fromTransferRate);
            if (transferRate == 0) {
               return 0;
            }

            from.removeSource(transferRate);
            int lossyTransfer = Math.max(1, (int)((double)transferRate * 0.7));
            to.addSource(lossyTransfer);
            return lossyTransfer;
         }
      }

      return super.transferSource(from, to, fromTransferRate);
   }

   @Override
   public boolean closeEnough(BlockPos pos) {
      return this.f_58857_.m_7702_(pos) instanceof RelayWarpTile || super.closeEnough(pos);
   }
}
