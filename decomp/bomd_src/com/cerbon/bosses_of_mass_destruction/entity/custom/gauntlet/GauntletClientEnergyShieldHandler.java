package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.Event;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IDataAccessorHandler;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.phys.Vec3;

public class GauntletClientEnergyShieldHandler implements IDataAccessorHandler {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private float energizedRenderAlpha = 0.0F;
   private final ClientParticleBuilder energizedParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
      .brightness(15728880)
      .color(BMDColors.LASER_RED)
      .colorVariation(0.2)
      .scale(0.25F);

   public GauntletClientEnergyShieldHandler(GauntletEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   public float getRenderAlpha() {
      return this.energizedRenderAlpha;
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
      if ((Boolean)this.entity.m_20088_().m_135370_(GauntletEntity.isEnergized) && this.entity.f_19853_.m_5776_()) {
         this.eventScheduler.addEvent(new TimedEvent(() -> this.energizedRenderAlpha += 0.1F, 0, 10, () -> false));
         this.eventScheduler
            .addEvent(
               new Event(
                  () -> true, this::spawnParticles, () -> !this.entity.m_6084_() || !(Boolean)this.entity.m_20088_().m_135370_(GauntletEntity.isEnergized)
               )
            );
      } else {
         this.energizedRenderAlpha = 0.0F;
      }
   }

   private void spawnParticles() {
      Vec3 look = this.entity.m_20154_();
      Vec3 cross = look.m_82537_(VecUtils.yAxis);
      Vec3 rotatedOffset = VecUtils.rotateVector(cross, look, (double)RandomUtils.range(0, 359));
      Vec3 particlePos = MobUtils.eyePos(this.entity).m_82549_(rotatedOffset);
      Vec3 particleVel = VecUtils.rotateVector(rotatedOffset, look, 90.0).m_82490_(0.1);
      this.energizedParticles.build(particlePos, particleVel);
   }

   public void initDataTracker() {
      this.entity.m_20088_().m_135372_(GauntletEntity.isEnergized, false);
   }
}
