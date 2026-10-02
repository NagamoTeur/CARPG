package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventSeries;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.NetworkedHitboxManager;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.SpikeS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SpikeWaveAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final List<Vec3> firstCirclePoints = MathUtils.buildBlockCircle(7.0);
   private final double secondRadius = 14.0;
   private final List<Vec3> secondCirclePoints;
   private final double thirdRadius = 21.0;
   private final List<Vec3> thirdCirclePoints;
   public static final int indicatorDelay = 30;

   public SpikeWaveAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
      this.secondCirclePoints = MathUtils.buildBlockCircle(14.0);
      this.secondCirclePoints.removeAll(this.firstCirclePoints);
      this.thirdCirclePoints = MathUtils.buildBlockCircle(21.0);
      this.thirdCirclePoints.removeAll(this.secondCirclePoints);
      this.thirdCirclePoints.removeAll(this.firstCirclePoints);
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      } else {
         this.placeRifts((ServerPlayer)target);
         return 120;
      }
   }

   private void placeRifts(ServerPlayer target) {
      int firstBurstDelay = 20;
      int secondBurstDelay = 45;
      int thirdBurstDelay = 70;
      ServerLevel level = target.m_9236_();
      this.eventScheduler
         .addEvent(
            new EventSeries(
               new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.SpikeWave1.getId()), 20, 1, this.shouldCancel),
               new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.SpikeWave2.getId()), 26, 1, this.shouldCancel),
               new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.SpikeWave3.getId()), 26, 1, this.shouldCancel),
               new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Idle.getId()), 26, 1, () -> false)
            )
         );
      Spikes spikeGenerator = new Spikes(
         this.entity, level, (ParticleOptions)BMDParticles.VOID_BLOSSOM_SPIKE_WAVE_INDICATOR.get(), 30, this.eventScheduler, this.shouldCancel
      );
      this.createSpikeWave(
         () -> {
            this.createBurst(spikeGenerator, this.firstCirclePoints);
            BMDUtils.playSound(level, this.entity.m_20182_(), (SoundEvent)BMDSounds.SPIKE_WAVE_INDICATOR.get(), SoundSource.HOSTILE, 2.0F, 0.7F, 64.0, null);
         },
         () -> BMDUtils.playSound(level, this.entity.m_20182_(), (SoundEvent)BMDSounds.VOID_BLOSSOM_SPIKE.get(), SoundSource.HOSTILE, 1.2F, 64.0, null),
         firstBurstDelay
      );
      this.createSpikeWave(
         () -> {
            this.createBurst(spikeGenerator, this.secondCirclePoints);
            this.playSoundsInRadius(level, 14.0, (SoundEvent)BMDSounds.SPIKE_WAVE_INDICATOR.get(), 2.0F, 0.7F);
         },
         () -> this.playSoundsInRadius(level, 14.0, (SoundEvent)BMDSounds.VOID_BLOSSOM_SPIKE.get(), 1.2F, BMDUtils.randomPitch(this.entity.m_217043_())),
         secondBurstDelay
      );
      this.createSpikeWave(
         () -> {
            this.createBurst(spikeGenerator, this.thirdCirclePoints);
            this.playSoundsInRadius(level, 21.0, (SoundEvent)BMDSounds.SPIKE_WAVE_INDICATOR.get(), 2.0F, 0.7F);
         },
         () -> this.playSoundsInRadius(level, 21.0, (SoundEvent)BMDSounds.VOID_BLOSSOM_SPIKE.get(), 1.2F, BMDUtils.randomPitch(this.entity.m_217043_())),
         thirdBurstDelay
      );
   }

   private void createSpikeWave(Runnable indicationStageHandler, Runnable spikeStageHandler, int burstDelay) {
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         indicationStageHandler.run();
         this.eventScheduler.addEvent(new TimedEvent(spikeStageHandler, 30, 1, this.shouldCancel));
      }, burstDelay, 1, this.shouldCancel));
   }

   private void playSoundsInRadius(ServerLevel level, double radius, SoundEvent soundEvent, float volume, float pitch) {
      for (Direction dir : Plane.HORIZONTAL) {
         Vec3 pos = this.entity.m_20182_().m_82549_(new Vec3(dir.m_122432_()).m_82490_(radius));
         BMDUtils.playSound(level, pos, soundEvent, SoundSource.HOSTILE, volume, pitch, 64.0, null);
      }
   }

   private void createBurst(Spikes spikesGenerator, List<Vec3> positions) {
      List<BlockPos> placedPositions = positions.stream()
         .flatMap(vec3 -> spikesGenerator.tryPlaceRift(this.entity.m_20182_().m_82549_(vec3)).stream())
         .toList();
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> BMDPacketHandler.sendToAllPlayersTrackingChunk(
                     new SpikeS2CPacket(this.entity.m_19879_(), placedPositions), (ServerLevel)this.entity.f_19853_, this.entity.m_20182_()
                  ),
               30,
               1,
               this.shouldCancel
            )
         );
   }
}
