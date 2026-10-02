package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.ObsidianExplosionTrapBricks;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ObsidianExplosionTrapBricks_Block_Entity extends BlockEntity {
   public int tickCount;

   public ObsidianExplosionTrapBricks_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.OBSIDIAN_EXPLOSION_TRAP_BRICKS.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, ObsidianExplosionTrapBricks_Block_Entity entity) {
      entity.tick();
   }

   public void tick() {
      boolean LIT = false;
      if (this.m_58900_().m_60734_() instanceof ObsidianExplosionTrapBricks) {
         LIT = (Boolean)this.m_58900_().m_61143_(ObsidianExplosionTrapBricks.LIT);
      }

      if (LIT) {
         this.tickCount++;
         float x = (float)this.m_58899_().m_123341_() + 0.5F;
         float y = (float)this.m_58899_().m_123342_();
         float z = (float)this.m_58899_().m_123343_() + 0.5F;
         float f = 5.0F;
         if (this.tickCount < 80) {
            for (LivingEntity inRange : this.f_58857_
               .m_45976_(
                  LivingEntity.class,
                  new AABB(
                     (double)x - (double)f, (double)y - (double)f, (double)z - (double)f, (double)x + (double)f, (double)y + (double)f, (double)z + (double)f
                  )
               )) {
               if ((!(inRange instanceof Player) || !((Player)inRange).m_150110_().f_35934_) && !inRange.m_6095_().m_204039_(ModTag.TRAP_BLOCK_NOT_DETECTED)) {
                  Vec3 diff = inRange.m_20182_().m_82546_(Vec3.m_82512_(this.m_58899_().m_7918_(0, 0, 0)));
                  diff = diff.m_82541_().m_82490_(0.06);
                  inRange.m_20256_(inRange.m_20184_().m_82546_(diff));
               }
            }

            if (this.f_58857_.f_46443_) {
               for (int i = 0; i < 3; i++) {
                  int j = this.f_58857_.f_46441_.m_188503_(2) * 2 - 1;
                  int k = this.f_58857_.f_46441_.m_188503_(2) * 2 - 1;
                  double d0 = (double)this.f_58858_.m_123341_() + 0.5 + 0.25 * (double)j;
                  double d1 = (double)((float)this.f_58858_.m_123342_() + this.f_58857_.f_46441_.m_188501_());
                  double d2 = (double)this.f_58858_.m_123343_() + 0.5 + 0.25 * (double)k;
                  double d3 = (double)(this.f_58857_.f_46441_.m_188501_() * (float)j);
                  double d4 = ((double)this.f_58857_.f_46441_.m_188501_() - 0.5) * 0.125;
                  double d5 = (double)(this.f_58857_.f_46441_.m_188501_() * (float)k);
                  this.f_58857_.m_7106_(ParticleTypes.f_123760_, d0, d1, d2, d3, d4, d5);
               }
            }
         }

         if (this.tickCount == 80 && !this.f_58857_.f_46443_) {
            this.f_58857_.m_46511_(null, (double)x, (double)(y + 1.0F), (double)z, 3.0F, BlockInteraction.NONE);
         }
      } else {
         this.tickCount = 0;
      }
   }
}
