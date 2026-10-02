package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.level.block.state.BlockState;

public class TempLightTile extends LightTile {
   int age;
   public double lengthModifier;

   public TempLightTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.T_LIGHT_TILE, pos, state);
   }

   @Override
   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.age = nbt.m_128451_("age");
      this.lengthModifier = nbt.m_128459_("modifier");
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128347_("modifier", this.lengthModifier);
      tag.m_128365_("age", IntTag.m_128679_(this.age));
   }

   @Override
   public void tick() {
      super.tick();
      if (this.f_58857_ != null && !this.f_58857_.f_46443_) {
         this.age++;
         if ((double)this.age > 300.0 + 100.0 * this.lengthModifier) {
            this.f_58857_.m_46961_(this.m_58899_(), false);
            this.f_58857_.m_46747_(this.m_58899_());
         }
      }
   }
}
