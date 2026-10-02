package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ArcanePedestalTile extends SingleItemTile implements Container {
   public float frames;
   public boolean hasSignal;

   public ArcanePedestalTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public ArcanePedestalTile(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)BlockRegistry.ARCANE_PEDESTAL_TILE.get(), pos, state);
   }

   @Override
   public int m_6643_() {
      return 1;
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128379_("hasSignal", this.hasSignal);
   }

   @Override
   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.hasSignal = compound.m_128471_("hasSignal");
   }
}
