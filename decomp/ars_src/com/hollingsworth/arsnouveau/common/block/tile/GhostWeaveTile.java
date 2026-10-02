package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.common.event.timed.GhostweaveVisibilityEvent;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GhostWeaveTile extends MirrorWeaveTile {
   private boolean invisible;

   public GhostWeaveTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.GHOST_WEAVE_TILE, pos, state);
   }

   public void setVisibility(boolean invisible) {
      if (this.invisible != invisible) {
         int ticks = 1;

         for (Direction d : Direction.values()) {
            BlockPos offset = this.m_58899_().m_121945_(d);
            BlockEntity var9 = this.f_58857_.m_7702_(offset);
            if (var9 instanceof GhostWeaveTile) {
               GhostWeaveTile neighbor = (GhostWeaveTile)var9;
               if (this.isInvisible() == neighbor.isInvisible()) {
                  EventQueue.getServerInstance().addEvent(new GhostweaveVisibilityEvent(neighbor, ++ticks, invisible));
               }
            }
         }

         this.invisible = invisible;
         this.updateBlock();
      }
   }

   public boolean isInvisible() {
      return this.invisible;
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128379_("invisible", this.invisible);
   }

   @Override
   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      this.invisible = pTag.m_128471_("invisible");
   }

   @Override
   public BlockState getDefaultBlockState() {
      return BlockRegistry.GHOST_WEAVE.m_49966_();
   }
}
