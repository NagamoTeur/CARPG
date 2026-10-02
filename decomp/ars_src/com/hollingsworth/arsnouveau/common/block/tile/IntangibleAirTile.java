package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IntangibleAirTile extends ModdedTile implements ITickable {
   public int duration;
   public int maxLength;
   public int stateID;

   public IntangibleAirTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.INTANGIBLE_AIR_TYPE, pos, state);
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_) {
         this.duration++;
         if (this.duration > this.maxLength) {
            this.f_58857_.m_46597_(this.f_58858_, Block.m_49803_(this.stateID));
         }

         this.f_58857_.m_7260_(this.f_58858_, this.f_58857_.m_8055_(this.f_58858_), this.f_58857_.m_8055_(this.f_58858_), 2);
      }
   }

   public void m_142466_(CompoundTag nbt) {
      this.stateID = nbt.m_128451_("state_id");
      this.duration = nbt.m_128451_("duration");
      this.maxLength = nbt.m_128451_("max_length");
      super.m_142466_(nbt);
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      tag.m_128405_("state_id", this.stateID);
      tag.m_128405_("duration", this.duration);
      tag.m_128405_("max_length", this.maxLength);
   }
}
