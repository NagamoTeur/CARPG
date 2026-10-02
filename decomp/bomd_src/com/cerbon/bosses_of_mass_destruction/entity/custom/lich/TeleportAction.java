package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.ModRandom;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.ISpawnPredicate;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobEntitySpawnPredicate;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobPlacementLogic;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.RangedSpawnPosition;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;

public class TeleportAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   public static final double tooFarFromTargetDistance = 35.0;
   public static final double tooCloseToTargetDistance = 20.0;
   public static final int teleportCooldown = 80;
   public static final int teleportStartSoundDelay = 10;
   public static final int teleportDelay = 40;
   public static final int beginTeleportParticleDelay = 15;
   public static final int teleportParticleDuration = 10;

   public TeleportAction(LichEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      } else {
         this.performTeleport((ServerPlayer)target);
         return 80;
      }
   }

   public void performTeleport(ServerPlayer target) {
      MobEntitySpawnPredicate spawnPredicate = new MobEntitySpawnPredicate(target.f_19853_);
      ISpawnPredicate entitySpawnPredicate = (pos, e) -> spawnPredicate.canSpawn(pos, e) && this.entity.inLineOfSight(target);
      this.teleport(target, entitySpawnPredicate, spawnPredicate);
   }

   private void teleport(ServerPlayer target, ISpawnPredicate spawnPredicate, ISpawnPredicate backupPredicate) {
      MobPlacementLogic mobPlacementLogic = this.buildTeleportLogic(target, target.m_20182_(), spawnPredicate);
      boolean success = mobPlacementLogic.tryPlacement(100);
      if (!success) {
         Vec3 safePos = VecUtils.asVec3(this.entity.f_19853_.m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(target.m_20182_())));
         this.buildTeleportLogic(target, safePos, backupPredicate).tryPlacement(100);
      }
   }

   private MobPlacementLogic buildTeleportLogic(ServerPlayer target, Vec3 spawnPos, ISpawnPredicate spawnPredicate) {
      return new MobPlacementLogic(
         new RangedSpawnPosition(spawnPos, 20.0, 35.0, new ModRandom()),
         () -> this.entity,
         spawnPredicate,
         (pos, e) -> this.eventScheduler.addEvent(new TimedEvent(() -> {
               BMDUtils.playSound(target.m_9236_(), this.entity.m_20182_(), (SoundEvent)BMDSounds.TELEPORT_PREPARE.get(), SoundSource.HOSTILE, 3.0F, 64.0, null);
               this.entity.collides = false;
               this.eventScheduler.addEvent(new TimedEvent(() -> {
                  e.m_6021_(pos.f_82479_, pos.f_82480_, pos.f_82481_);
                  e.f_19853_.m_7605_(e, (byte)10);
                  BMDUtils.playSound(target.m_9236_(), this.entity.m_20182_(), (SoundEvent)BMDSounds.LICH_TELEPORT.get(), SoundSource.HOSTILE, 2.0F, 64.0, null);
                  this.entity.collides = true;
               }, 30));
            }, 10, 1, this.shouldCancel))
      );
   }
}
