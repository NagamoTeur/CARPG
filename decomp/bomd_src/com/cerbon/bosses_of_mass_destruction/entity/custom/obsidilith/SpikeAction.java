package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.damagesource.UnshieldableDamageSource;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class SpikeAction implements IActionWithCooldown {
   private final Mob entity;
   private final EventScheduler eventScheduler;
   private final List<Vec3> circlePoints;

   public SpikeAction(Mob entity) {
      this.entity = entity;
      this.eventScheduler = BMDCapabilities.getLevelEventScheduler(entity.f_19853_);
      this.circlePoints = MathUtils.buildBlockCircle(2.0);
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      } else {
         this.placeSpikes((ServerPlayer)target);
         return 100;
      }
   }

   private void placeSpikes(ServerPlayer target) {
      int riftTime = 20;
      RiftBurst riftBurst = new RiftBurst(
         this.entity,
         target.m_9236_(),
         (ParticleOptions)BMDParticles.OBSIDILITH_SPIKE_INDICATOR.get(),
         (ParticleOptions)BMDParticles.OBSIDILITH_SPIKE.get(),
         riftTime,
         this.eventScheduler,
         this::damageEntity
      );
      BMDUtils.playSound(
         target.m_9236_(), this.entity.m_20182_(), (SoundEvent)BMDSounds.OBSIDILITH_PREPARE_ATTACK.get(), SoundSource.HOSTILE, 3.0F, 1.2F, 64.0, null
      );

      for (int i = 0; i < 3; i++) {
         int timeBetweenRifts = 30;
         int initialDelay = 30;
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> {
                     Vec3 placement = ObsidilithUtils.approximatePlayerNextPosition(BMDCapabilities.getPlayerPositions(target), target.m_20182_());
                     BMDUtils.playSound(target.m_9236_(), placement, (SoundEvent)BMDSounds.SPIKE_INDICATOR.get(), SoundSource.HOSTILE, 1.0F, 32.0, null);
                     this.eventScheduler
                        .addEvent(
                           new TimedEvent(
                              () -> BMDUtils.playSound(target.m_9236_(), placement, (SoundEvent)BMDSounds.SPIKE.get(), SoundSource.HOSTILE, 1.2F, 32.0, null),
                              riftTime,
                              1,
                              () -> !this.entity.m_6084_()
                           )
                        );

                     for (Vec3 point : this.circlePoints) {
                        riftBurst.tryPlaceRift(placement.m_82549_(point));
                     }
                  },
                  initialDelay + i * timeBetweenRifts,
                  1,
                  () -> !this.entity.m_6084_()
               )
            );
      }
   }

   private void damageEntity(LivingEntity entity) {
      float damage = (float)this.entity.m_21133_(Attributes.f_22281_);
      entity.m_6469_(new UnshieldableDamageSource(this.entity), damage);
      entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 120, 2));
   }
}
