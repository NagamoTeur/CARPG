package net.xylonity.knightquest.common.entity.boss.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;
import net.xylonity.knightquest.common.entity.boss.NethermanProjectileChargeEntity;
import net.xylonity.knightquest.registry.KnightQuestEntities;

public class NethermanProjectileChargesGoal extends Goal {
   private final NethermanEntity netherman;
   public int chargeTime;

   public NethermanProjectileChargesGoal(NethermanEntity netherman) {
      this.netherman = netherman;
   }

   public boolean m_8036_() {
      return this.netherman.m_5448_() != null && this.netherman.getPhase() == 3 && this.netherman.getCounterSwitchPhase3() == 160;
   }

   public void m_8056_() {
      this.chargeTime = 300;
   }

   public void m_8041_() {
      this.chargeTime = 0;
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

   private boolean isBetterPosition(BlockPos pos, BlockPos bestPos) {
      double currentDistance = this.netherman.m_20182_().m_82554_(Vec3.m_82512_(pos));
      double bestDistance = this.netherman.m_20182_().m_82554_(Vec3.m_82512_(bestPos));
      return currentDistance < bestDistance;
   }

   private void teleportAroundTarget() {
      BlockPos bestPos = null;
      Entity target = this.netherman.m_5448_();
      RandomSource random = this.netherman.m_217043_();

      for (int attempt = 0; attempt < 50; attempt++) {
         double angle = random.m_188500_() * 2.0 * Math.PI;
         double distance = 5.0 + random.m_188500_() * 15.0;

         assert target != null;

         double x = target.m_20185_() + Math.cos(angle) * distance;
         double z = target.m_20189_() + Math.sin(angle) * distance;
         double y = target.m_20186_() + (random.m_188500_() - 0.5) * 2.0;
         BlockPos targetPos = new BlockPos((int)x, (int)y, (int)z);
         if (this.isValidTeleportPosition(targetPos)) {
            BlockPos blockPos = targetPos.m_6630_(this.netherman.m_217043_().m_216339_(1, 10));
            NethermanProjectileChargeEntity entity = (NethermanProjectileChargeEntity)((EntityType)KnightQuestEntities.NETHERMAN_PROJECTILE_CHARGE.get())
               .m_20615_(this.netherman.f_19853_);
            if (entity != null) {
               entity.m_20035_(blockPos, 0.0F, 0.0F);
               this.netherman.f_19853_.m_7967_(entity);
            }

            for (Player player : this.netherman.f_19853_.m_6907_()) {
               if (player instanceof ServerPlayer) {
                  ServerPlayer serverPlayer = (ServerPlayer)player;

                  for (int u = 0; u < 20; u++) {
                     double speed = 0.1 + this.netherman.m_217043_().m_188500_() * 0.2;
                     double ux = this.netherman.m_20185_() + (this.netherman.m_217043_().m_188500_() - 0.5) * 0.2;
                     double uy = this.netherman.m_20186_() + (double)this.netherman.m_20192_() + (this.netherman.m_217043_().m_188500_() - 0.5) * 0.2;
                     double uz = this.netherman.m_20189_() + (this.netherman.m_217043_().m_188500_() - 0.5) * 0.2;
                     Vec3 look = this.netherman.m_20154_();
                     double vx = look.f_82479_ * speed;
                     double vy = look.f_82480_ * speed;
                     double vz = look.f_82481_ * speed;
                     serverPlayer.f_8906_
                        .m_9829_(new ClientboundLevelParticlesPacket(ParticleTypes.f_123762_, true, ux, uy, uz, (float)vx, (float)vy, (float)vz, 0.3F, 4));
                  }
               }
            }

            this.netherman.f_19853_.m_214171_(GameEvent.f_238175_, this.netherman.m_20182_(), Context.m_223717_(this.netherman));
            this.netherman
               .f_19853_
               .m_6263_(
                  null, this.netherman.f_19854_, this.netherman.f_19855_, this.netherman.f_19856_, SoundEvents.f_11852_, this.netherman.m_5720_(), 1.0F, 1.0F
               );
            this.netherman.m_5496_(SoundEvents.f_11852_, 1.0F, 1.0F);
            this.netherman.m_6021_(x, y, z);
            return;
         }

         if (bestPos == null || this.isBetterPosition(targetPos, bestPos)) {
            bestPos = targetPos;
         }
      }
   }

   public void m_8037_() {
      LivingEntity livingentity = this.netherman.m_5448_();
      if (livingentity != null) {
         if (livingentity.m_20280_(this.netherman) < 4096.0 && this.netherman.m_142582_(livingentity)) {
            if (this.chargeTime > 0) {
               this.chargeTime--;
            }

            if (this.chargeTime < 20 && this.chargeTime % 2 == 0) {
               this.teleportAroundTarget();
            }

            if (this.chargeTime == 0) {
               this.chargeTime = 300;
            }
         } else {
            this.chargeTime = 300;
         }
      } else {
         this.chargeTime = 300;
      }
   }
}
