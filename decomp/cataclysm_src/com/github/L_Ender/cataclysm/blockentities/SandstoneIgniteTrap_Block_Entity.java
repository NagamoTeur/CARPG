package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.Sandstone_Ignite_Trap;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SandstoneIgniteTrap_Block_Entity extends BlockEntity {
   public int tickCount;
   private final RandomSource random = RandomSource.m_216327_();

   public SandstoneIgniteTrap_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.SANDSTONE_IGNITE_TRAP.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, SandstoneIgniteTrap_Block_Entity entity) {
      entity.tick(level, pos);
   }

   public void tick(Level level, BlockPos pos) {
      boolean LIT = false;
      if (this.m_58900_().m_60734_() instanceof Sandstone_Ignite_Trap) {
         LIT = (Boolean)this.m_58900_().m_61143_(Sandstone_Ignite_Trap.LIT);
      }

      if (LIT) {
         this.tickCount++;
         double spread = Math.PI * 2;
         int arcLen = Mth.m_14165_(2.0 * spread);
         if (level.m_5776_()) {
            for (int j = 0; j < arcLen; j++) {
               float f2 = this.random.m_188501_() * (float) (Math.PI * 2);
               double d0 = (double)((float)pos.m_123341_() + 0.5F) + (double)Mth.m_14089_(f2) * 0.35 * 0.9;
               double d2 = (double)pos.m_7494_().m_123342_();
               double d4 = (double)((float)pos.m_123343_() + 0.5F) + (double)Mth.m_14031_(f2) * 0.35 * 0.9;
               level.m_7106_(ParticleTypes.f_123744_, d0, d2, d4, 0.0, 0.5, 0.0);
            }
         } else if (this.tickCount % 5 == 0) {
            for (LivingEntity entity : level.m_45976_(LivingEntity.class, new AABB(pos.m_7918_(-1, 0, -1), pos.m_7918_(1, 6, 1)))) {
               if (!entity.m_5825_()) {
                  entity.m_6469_(DamageSource.f_19305_, 5.0F);
                  entity.m_20254_(5);
               }
            }
         }
      } else {
         this.tickCount = 0;
      }
   }
}
