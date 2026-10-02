package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventSeries;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithUtils;
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
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SpikeAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final List<Vec3> circlePoints = MathUtils.buildBlockCircle(2.0);
   public static final int indicatorDelay = 20;

   public SpikeAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
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
         this.placeSpikes((ServerPlayer)target);
         return 150;
      }
   }

   private void placeSpikes(ServerPlayer target) {
      Spikes riftBurst = new Spikes(
         this.entity, target.m_9236_(), (ParticleOptions)BMDParticles.VOID_BLOSSOM_SPIKE_INDICATOR.get(), 20, this.eventScheduler, this.shouldCancel
      );
      BMDUtils.playSound(target.m_9236_(), this.entity.m_20182_(), (SoundEvent)BMDSounds.VOID_BLOSSOM_BURROW.get(), SoundSource.HOSTILE, 1.5F, 32.0, null);
      this.eventScheduler
         .addEvent(
            new EventSeries(
               new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Spike.getId()), 20, 1, this.shouldCancel),
               new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Idle.getId()), 100)
            )
         );

      for (int i = 0; i < 3; i++) {
         int timeBetweenRifts = 30;
         int initialDelay = 30;
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> {
                     Vec3 placement = ObsidilithUtils.approximatePlayerNextPosition(BMDCapabilities.getPlayerPositions(target), target.m_20182_());
                     BMDUtils.playSound(target.m_9236_(), placement, (SoundEvent)BMDSounds.VOID_SPIKE_INDICATOR.get(), SoundSource.HOSTILE, 1.0F, 32.0, null);
                     List<BlockPos> successfulSpikes = this.circlePoints
                        .stream()
                        .flatMap(point -> riftBurst.tryPlaceRift(placement.m_82549_(point)).stream())
                        .toList();
                     this.eventScheduler
                        .addEvent(
                           new TimedEvent(
                              () -> {
                                 BMDUtils.playSound(
                                    target.m_9236_(), placement, (SoundEvent)BMDSounds.VOID_BLOSSOM_SPIKE.get(), SoundSource.HOSTILE, 1.2F, 32.0, null
                                 );
                                 BMDPacketHandler.sendToAllPlayersTrackingChunk(
                                    new SpikeS2CPacket(this.entity.m_19879_(), successfulSpikes), (ServerLevel)this.entity.f_19853_, this.entity.m_20182_()
                                 );
                              },
                              20,
                              1,
                              this.shouldCancel
                           )
                        );
                  },
                  initialDelay + i * timeBetweenRifts,
                  1,
                  this.shouldCancel
               )
            );
      }
   }
}
