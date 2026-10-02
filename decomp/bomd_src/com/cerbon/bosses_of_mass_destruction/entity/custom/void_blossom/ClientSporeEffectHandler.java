package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ClientSporeEffectHandler implements IEntityEventHandler {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder projectileParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.OBSIDILITH_BURST.get())
      .color(BMDColors.GREEN)
      .colorVariation(0.4)
      .scale(0.5F)
      .brightness(15728880);

   public ClientSporeEffectHandler(VoidBlossomEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (status == 7) {
         this.eventScheduler.addEvent(new TimedEvent(this::spawnParticles, 25, 15, () -> false));
      }
   }

   private void spawnParticles() {
      Vec3 pos = this.entity.m_146892_().m_82549_(RandomUtils.randVec().m_82490_(3.0)).m_82546_(this.entity.m_20156_().m_82490_(2.0));
      Vec3 vel = VecUtils.yAxis.m_82490_(0.1);
      this.projectileParticles.build(pos, vel);
   }
}
