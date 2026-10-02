package net.xylonity.knightquest.common.entity.boss.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;

public class NethermanTeleport2PlayerGoal extends Goal {
   private final NethermanEntity netherman;
   private int idleTime;

   public NethermanTeleport2PlayerGoal(NethermanEntity netherman) {
      this.netherman = netherman;
   }

   public boolean m_8036_() {
      return this.netherman.m_5448_() == null
         && (
            this.netherman.getPhase() == 1 && this.netherman.f_19797_ > 100
               || this.netherman.getPhase() == 2 && this.netherman.getCounterSwitchPhase2() == 130
               || this.netherman.getPhase() == 3 && this.netherman.getCounterSwitchPhase3() == 160
         );
   }

   public void m_8056_() {
      this.idleTime = 70;
   }

   public void m_8041_() {
      this.idleTime = 0;
   }

   public boolean m_183429_() {
      return true;
   }

   private boolean isValidTeleportPosition(BlockPos pos) {
      if (this.netherman.f_19853_.m_8055_(pos).m_60734_() != Blocks.f_50016_) {
         return false;
      } else {
         BlockPos blockBelow = pos.m_7495_();
         BlockState stateBelow = this.netherman.f_19853_.m_8055_(blockBelow);
         if (stateBelow.m_60734_() != Blocks.f_50016_ && stateBelow.m_60734_() != Blocks.f_49991_ && stateBelow.m_60734_() != Blocks.f_49990_) {
            AABB boundingBox = new AABB(
               (double)pos.m_123341_() - 0.5,
               (double)pos.m_123342_(),
               (double)pos.m_123343_() - 0.5,
               (double)pos.m_123341_() + 0.5,
               (double)((float)pos.m_123342_() + this.netherman.m_20206_()),
               (double)pos.m_123343_() + 0.5
            );
            return this.netherman.f_19853_.m_45756_(this.netherman, boundingBox);
         } else {
            return false;
         }
      }
   }

   private void teleportNearPlayer() {
      Player closestPlayer = this.netherman.f_19853_.m_45930_(this.netherman, 60.0);
      if (closestPlayer != null && !closestPlayer.m_7500_() && !closestPlayer.m_5833_()) {
         RandomSource random = this.netherman.m_217043_();

         for (int attempt = 0; attempt < 50; attempt++) {
            double angle = random.m_188500_() * 2.0 * Math.PI;
            double distance = 3.0 + random.m_188500_() * 7.0;
            double x = closestPlayer.m_20185_() + Math.cos(angle) * distance;
            double z = closestPlayer.m_20189_() + Math.sin(angle) * distance;
            double y = closestPlayer.m_20186_() + (random.m_188500_() - 0.5) * 2.0;
            BlockPos targetPos = new BlockPos((int)x, (int)y, (int)z);
            if (this.isValidTeleportPosition(targetPos)) {
               this.netherman
                  .f_19853_
                  .m_6263_(
                     null,
                     this.netherman.m_20185_(),
                     this.netherman.m_20186_(),
                     this.netherman.m_20189_(),
                     SoundEvents.f_11852_,
                     this.netherman.m_5720_(),
                     1.0F,
                     1.0F
                  );
               this.netherman.m_6021_(x, y, z);
               this.netherman.f_19853_.m_6263_(null, x, y, z, SoundEvents.f_11852_, this.netherman.m_5720_(), 1.0F, 1.0F);
               return;
            }
         }
      }
   }

   public void m_8037_() {
      if (this.idleTime > 0) {
         this.idleTime--;
      }

      if (this.idleTime == 0) {
         this.teleportNearPlayer();
         this.idleTime = 70;
      }
   }
}
