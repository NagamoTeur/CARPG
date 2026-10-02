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

public class ClientDeathEffectHandler implements IEntityEventHandler {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder deathParticle = new ClientParticleBuilder((ParticleOptions)BMDParticles.FLUFF.get())
      .color(BMDColors.DARK_GREY)
      .colorVariation(0.1)
      .age(20, 30)
      .scale(0.3F);

   public ClientDeathEffectHandler(VoidBlossomEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (status == 3) {
         int delay = 3;
         Vec3 fallDirection = VecUtils.planeProject(this.entity.m_20156_(), VecUtils.yAxis).m_82524_(180.0F);
         Vec3 originPos = this.entity.m_20182_().m_82549_(VecUtils.yAxis.m_82490_(2.0));
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            Vec3 pos = originPos.m_82549_(RandomUtils.randVec().m_82490_(5.0)).m_82549_(fallDirection.m_82490_(RandomUtils.randomDouble(6.0) + 6.0));
            Vec3 vel = RandomUtils.randVec().m_82549_(VecUtils.yAxis).m_82490_(0.05);
            this.deathParticle.build(pos, vel);
         }, delay, 70 - delay, () -> false));
      }
   }
}
