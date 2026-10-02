package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.damagesource.UnshieldableDamageSource;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.SendDeltaMovementS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BurstAction implements IActionWithCooldown {
   private final LivingEntity entity;
   private final EventScheduler eventScheduler;
   private final List<Vec3> circlePoints;
   public static final int burstDelay = 30;

   public BurstAction(LivingEntity entity) {
      this.entity = entity;
      this.eventScheduler = BMDCapabilities.getLevelEventScheduler(entity.f_19853_);
      this.circlePoints = MathUtils.buildBlockCircle(7.0);
   }

   @Override
   public int perform() {
      this.placeRifts();
      return 80;
   }

   private void placeRifts() {
      Level level = this.entity.f_19853_;
      RiftBurst riftBurst = new RiftBurst(
         this.entity,
         (ServerLevel)level,
         (ParticleOptions)BMDParticles.OBSIDILITH_BURST_INDICATOR.get(),
         (ParticleOptions)BMDParticles.OBSIDILITH_BURST.get(),
         30,
         this.eventScheduler,
         this::damageEntity
      );
      BMDUtils.playSound(
         (ServerLevel)level, this.entity.m_20182_(), (SoundEvent)BMDSounds.OBSIDILITH_PREPARE_ATTACK.get(), SoundSource.HOSTILE, 3.0F, 0.7F, 64.0, null
      );
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> BMDUtils.playSound(
                     (ServerLevel)level, this.entity.m_20182_(), (SoundEvent)BMDSounds.OBSIDILITH_BURST.get(), SoundSource.HOSTILE, 1.2F, 64.0, null
                  ),
               30,
               1,
               () -> !this.entity.m_6084_()
            )
         );

      for (Vec3 point : this.circlePoints) {
         riftBurst.tryPlaceRift(this.entity.m_20182_().m_82549_(point));
      }
   }

   private void damageEntity(LivingEntity livingEntity) {
      float damage = (float)this.entity.m_21133_(Attributes.f_22281_);
      if (livingEntity instanceof ServerPlayer serverPlayer) {
         BMDPacketHandler.sendToPlayer(
            new SendDeltaMovementS2CPacket(new Vec3(livingEntity.m_20184_().f_82479_, 1.3, livingEntity.m_20184_().f_82481_)), serverPlayer
         );
      }

      livingEntity.m_6469_(new UnshieldableDamageSource(this.entity), damage);
   }
}
