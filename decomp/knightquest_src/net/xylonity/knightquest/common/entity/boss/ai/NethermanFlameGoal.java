package net.xylonity.knightquest.common.entity.boss.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;
import net.xylonity.knightquest.config.values.KQConfigValues;

public class NethermanFlameGoal extends Goal {
   private final NethermanEntity netherman;
   public int chargeTime;

   public NethermanFlameGoal(NethermanEntity netherman) {
      this.netherman = netherman;
   }

   public boolean m_8036_() {
      return this.netherman.m_5448_() != null && this.netherman.getPhase() == 1;
   }

   public void m_8056_() {
      this.chargeTime = 200;
   }

   public void m_8041_() {
      this.chargeTime = 0;
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      LivingEntity livingentity = this.netherman.m_5448_();
      if (livingentity != null) {
         if (livingentity.m_20280_(this.netherman) < 4096.0 && this.netherman.m_142582_(livingentity)) {
            Level level = this.netherman.f_19853_;
            if (this.chargeTime > 0) {
               this.chargeTime--;
            }

            if (this.chargeTime == 35) {
               this.teleportToRandomLocationAroundTarget(this.netherman.m_5448_());
            }

            if (this.chargeTime == 30) {
               this.netherman.setNoMovement(true);
               this.netherman.setIsDoingFlameAttack(true);
            }

            if (this.chargeTime == 20 && !this.netherman.m_20067_()) {
               level.m_5594_(null, this.netherman.m_20097_(), SoundEvents.f_11934_, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if (this.chargeTime == 10) {
               Vec3 look = this.netherman.m_20154_();
               double offsetX = look.f_82479_ * 0.5;
               double offsetY = look.f_82480_ * 0.5;
               double offsetZ = look.f_82481_ * 0.5;

               for (Player player : this.netherman.f_19853_.m_6907_()) {
                  if (player instanceof ServerPlayer) {
                     ServerPlayer serverPlayer = (ServerPlayer)player;

                     for (int u = 0; u < 20; u++) {
                        double speed = 0.1 + this.netherman.m_217043_().m_188500_() * 0.2;
                        double x = this.netherman.m_20185_() + offsetX + (this.netherman.m_217043_().m_188500_() - 0.5) * 0.2;
                        double y = this.netherman.m_20186_()
                           + (double)this.netherman.m_20192_()
                           + offsetY
                           + (this.netherman.m_217043_().m_188500_() - 0.5) * 0.2;
                        double z = this.netherman.m_20189_() + offsetZ + (this.netherman.m_217043_().m_188500_() - 0.5) * 0.2;
                        double vx = look.f_82479_ * speed;
                        double vy = look.f_82480_ * speed;
                        double vz = look.f_82481_ * speed;
                        serverPlayer.f_8906_
                           .m_9829_(
                              new ClientboundLevelParticlesPacket(ParticleTypes.f_123744_, true, x, y, z, (float)vx, (float)vy + 1.0F, (float)vz, 0.2F, 2)
                           );
                     }
                  }
               }

               if (!this.netherman.m_20067_()) {
                  level.m_5594_(null, this.netherman.m_20097_(), SoundEvents.f_11874_, SoundSource.BLOCKS, 1.0F, 1.0F);
               }
            }

            if (this.chargeTime == 5) {
               for (Player playerx : this.netherman.f_19853_.m_6907_()) {
                  if (playerx instanceof ServerPlayer) {
                     ServerPlayer serverPlayer = (ServerPlayer)playerx;

                     for (int u = 0; u < 20; u++) {
                        double speed = 0.1 + this.netherman.m_217043_().m_188500_() * 0.2;
                        double x = this.netherman.m_5448_().m_20185_() + (this.netherman.m_5448_().m_217043_().m_188500_() - 0.5) * 0.2;
                        double y = this.netherman.m_5448_().m_20186_()
                           + (double)this.netherman.m_5448_().m_20192_()
                           + (this.netherman.m_217043_().m_188500_() - 0.5) * 0.2;
                        double z = this.netherman.m_5448_().m_20189_() + (this.netherman.m_5448_().m_217043_().m_188500_() - 0.5) * 0.2;
                        Vec3 look = this.netherman.m_5448_().m_20154_();
                        double vx = look.f_82479_ * speed;
                        double vy = look.f_82480_ * speed;
                        double vz = look.f_82481_ * speed;
                        serverPlayer.f_8906_
                           .m_9829_(new ClientboundLevelParticlesPacket(ParticleTypes.f_123744_, true, x, y, z, (float)vx, (float)vy, (float)vz, 0.2F, 2));
                     }
                  }
               }

               this.netherman
                  .m_5448_()
                  .m_7311_(this.netherman.m_5448_().m_217043_().m_216339_(KQConfigValues.FIRE_ATTACK_MIN_TIME, KQConfigValues.FIRE_ATTACK_MAX_TIME) * 20);
            }

            if (this.chargeTime == 0) {
               this.chargeTime = 200;
               this.teleportToRandomLocationAroundTarget(this.netherman.m_5448_());
            }
         } else {
            this.chargeTime = 200;
         }
      } else {
         this.chargeTime = 200;
      }
   }

   private void teleportToRandomLocationAroundTarget(LivingEntity target) {
      boolean teleported = false;
      this.netherman.f_19853_.m_214171_(GameEvent.f_238175_, this.netherman.m_20182_(), Context.m_223717_(this.netherman));
      this.netherman
         .f_19853_
         .m_6263_(null, this.netherman.f_19854_, this.netherman.f_19855_, this.netherman.f_19856_, SoundEvents.f_11852_, this.netherman.m_5720_(), 1.0F, 1.0F);
      this.netherman.m_5496_(SoundEvents.f_11852_, 1.0F, 1.0F);

      for (int i = 0; i < 32 && !teleported; i++) {
         double angle = this.netherman.m_217043_().m_188500_() * 2.0 * Math.PI;
         double distance = 10.0 + this.netherman.m_217043_().m_188500_() * 20.0;
         double dx = target.m_20185_() + distance * Math.cos(angle);
         double dz = target.m_20189_() + distance * Math.sin(angle);
         double dy = (double)this.netherman
            .f_19853_
            .m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos((int)dx, (int)this.netherman.m_20186_(), (int)dz))
            .m_123342_();
         if (this.netherman.m_20984_(dx, dy, dz, true)) {
            teleported = true;
         }
      }

      if (!teleported) {
         this.netherman.m_6021_(target.m_20185_(), target.m_20186_(), target.m_20189_());
      }
   }
}
