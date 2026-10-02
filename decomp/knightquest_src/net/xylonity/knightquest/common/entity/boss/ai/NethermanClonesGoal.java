package net.xylonity.knightquest.common.entity.boss.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.common.entity.boss.NethermanCloneEntity;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;
import net.xylonity.knightquest.config.values.KQConfigValues;
import net.xylonity.knightquest.registry.KnightQuestEntities;

public class NethermanClonesGoal extends Goal {
   private final NethermanEntity netherman;
   public int chargeTime;

   public NethermanClonesGoal(NethermanEntity netherman) {
      this.netherman = netherman;
   }

   public boolean m_8036_() {
      return this.netherman.m_5448_() != null && this.netherman.getPhase() == 2 && this.netherman.getCounterSwitchPhase2() == 130;
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

   public void m_8037_() {
      LivingEntity livingentity = this.netherman.m_5448_();
      if (livingentity != null) {
         if (livingentity.m_20280_(this.netherman) < 4096.0 && this.netherman.m_142582_(livingentity)) {
            if (this.chargeTime > 0) {
               this.chargeTime--;
            }

            if (this.chargeTime == 35) {
               this.teleportToRandomLocationAroundTarget(this.netherman.m_5448_());
            }

            if (this.chargeTime == 30) {
               this.netherman.setNoMovement(true);
               this.netherman.setIsDoingSpecialAttack2(true);
            }

            if (this.chargeTime == 20 && !this.netherman.m_20067_()) {
               this.netherman.f_19853_.m_5594_(null, this.netherman.m_20097_(), SoundEvents.f_144200_, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if (this.chargeTime == 10) {
               Vec3 position = this.netherman.m_20182_();
               this.netherman.f_19853_.m_5594_(null, this.netherman.m_20183_(), SoundEvents.f_11868_, SoundSource.BLOCKS, 1.0F, 1.0F);

               for (int i = 0; i < KQConfigValues.MAX_NETHERMAN_CLONES; i++) {
                  double angle = this.netherman.m_217043_().m_188500_() * 2.0 * Math.PI;
                  double distance = this.netherman.m_217043_().m_188500_() * 30.0;
                  double xOffset = Math.cos(angle) * distance;
                  double zOffset = Math.sin(angle) * distance;
                  Vec3 spawnPos = position.m_82520_(xOffset, 0.0, zOffset);
                  Vec3 safeSpawnPos = this.findNearestSafePosition(spawnPos, this.netherman.f_19853_);
                  NethermanCloneEntity nethermanClone = (NethermanCloneEntity)((EntityType)KnightQuestEntities.NETHERMAN_CLONE.get())
                     .m_20615_(this.netherman.f_19853_);
                  if (nethermanClone != null) {
                     nethermanClone.m_6027_(safeSpawnPos.f_82479_, safeSpawnPos.f_82480_, safeSpawnPos.f_82481_);
                     this.netherman.f_19853_.m_7967_(nethermanClone);

                     for (Player player : this.netherman.f_19853_.m_6907_()) {
                        if (player instanceof ServerPlayer) {
                           ServerPlayer serverPlayer = (ServerPlayer)player;

                           for (int u = 0; u < 20; u++) {
                              serverPlayer.f_8906_
                                 .m_9829_(
                                    new ClientboundLevelParticlesPacket(
                                       ParticleTypes.f_175821_,
                                       true,
                                       nethermanClone.m_20208_(0.5),
                                       nethermanClone.m_20187_() - 0.25,
                                       nethermanClone.m_20262_(0.5),
                                       (float)((nethermanClone.m_217043_().m_188500_() - 0.5) * 2.0),
                                       (float)(-nethermanClone.m_217043_().m_188500_()),
                                       0.2F,
                                       0.0F,
                                       2
                                    )
                                 );
                           }
                        }
                     }

                     this.netherman.f_19853_.m_5594_(null, nethermanClone.m_20183_(), SoundEvents.f_11852_, SoundSource.BLOCKS, 1.0F, 1.0F);
                  }
               }
            }

            if (this.chargeTime == 0) {
               this.chargeTime = 300;
               this.teleportToRandomLocationAroundTarget(this.netherman.m_5448_());
            }
         } else {
            this.chargeTime = 300;
         }
      } else {
         this.chargeTime = 300;
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

   private Vec3 findNearestSafePosition(Vec3 pos, Level level) {
      double x = pos.f_82479_;
      double z = pos.f_82481_;
      double y = pos.f_82480_;

      for (int offset = 0; offset < 256; offset++) {
         double up = y + (double)offset;
         double down = y - (double)offset;
         if (this.isSafePosition(new Vec3(x, up, z), level)) {
            return new Vec3(x, up, z);
         }

         if (this.isSafePosition(new Vec3(x, down, z), level)) {
            return new Vec3(x, down, z);
         }
      }

      return pos;
   }

   private boolean isSafePosition(Vec3 pos, Level level) {
      NethermanCloneEntity test = (NethermanCloneEntity)((EntityType)KnightQuestEntities.NETHERMAN_CLONE.get()).m_20615_(level);
      if (test != null) {
         test.m_6027_(pos.f_82479_, pos.f_82480_, pos.f_82481_);
         return level.m_45786_(test);
      } else {
         return false;
      }
   }
}
