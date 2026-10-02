package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TemporaryTile extends MirrorWeaveTile implements ITickable {
   public int tickDuration;

   public TemporaryTile(BlockPos pos, BlockState state) {
      this((BlockEntityType)BlockRegistry.TEMPORARY_TILE.get(), pos, state);
   }

   public TemporaryTile(BlockEntityType type, BlockPos pos, BlockState state) {
      super(type, pos, state);
      this.mimicState = this.getDefaultBlockState();
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_) {
         this.tickDuration--;
         if (this.tickDuration <= 0) {
            this.f_58857_.m_7731_(this.f_58858_, Blocks.f_50016_.m_49966_(), 2);
            this.f_58857_.m_46672_(this.f_58858_, this.f_58857_.m_8055_(this.f_58858_).m_60734_());

            for (Direction d : Direction.values()) {
               this.f_58857_.m_46672_(this.f_58858_.m_121945_(d), this.m_58900_().m_60734_());
            }
         }
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128405_("tickDuration", this.tickDuration);
   }

   @Override
   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      this.tickDuration = pTag.m_128451_("tickDuration");
   }

   @Override
   public BlockState getDefaultBlockState() {
      return Blocks.f_50016_.m_49966_();
   }
}
