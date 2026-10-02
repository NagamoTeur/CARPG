package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.common.block.ITickable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SummoningTile extends ModdedTile implements ITickable {
   public int tickCounter;
   public boolean converted;
   public static final BooleanProperty CONVERTED = BooleanProperty.m_61465_("converted");
   public boolean isOff;

   public SummoningTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_) {
         if (!this.converted) {
            this.convertedEffect();
         }
      }
   }

   public void convertedEffect() {
      this.tickCounter++;
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.converted = compound.m_128471_("converted");
      this.isOff = compound.m_128471_("off");
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      tag.m_128379_("converted", this.converted);
      tag.m_128379_("off", this.isOff);
   }
}
