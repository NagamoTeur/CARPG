package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.function.Function;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ObsidilithEffectHandler {
   private final LivingEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder burstParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(BMDColors.ORANGE)
      .colorVariation(0.2);
   private final ClientParticleBuilder waveParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(BMDColors.RED)
      .colorVariation(0.2);
   private final ClientParticleBuilder spikeParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(BMDColors.COMET_BLUE)
      .colorVariation(0.2);
   private final ClientParticleBuilder anvilParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.ENCHANT.get())
      .color(BMDColors.ENDER_PURPLE)
      .colorVariation(0.2);
   private final ClientParticleBuilder teleportFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.DOWNSPARKLE.get())
      .color(BMDColors.ENDER_PURPLE)
      .brightness(15728880)
      .age(() -> RandomUtils.range(25, 30))
      .colorVariation(0.2);
   private static final ClientParticleBuilder deathParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.DOWNSPARKLE.get())
      .color((Function<Float, Vec3>)(age -> MathUtils.lerpVec(age, BMDColors.ENDER_PURPLE, BMDColors.WHITE)))
      .colorVariation(0.2)
      .brightness(15728880)
      .age(RandomUtils.range(35, 40))
      .scale(f -> (float)(Math.sin((double)f.floatValue() * Math.PI * 0.5) + 1.0) * 0.1F);

   public ObsidilithEffectHandler(LivingEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   public void handleStatus(byte status) {
      switch (status) {
         case 3:
            this.deathEffect();
         case 4:
         default:
            break;
         case 5:
            this.burstEffect();
            break;
         case 6:
            this.waveEffect();
            break;
         case 7:
            this.spikeEffect();
            break;
         case 8:
            this.anvilEffect();
      }
   }

   private void burstEffect() {
      Vec3 entityPos = MobUtils.eyePos(this.entity);

      for (int i = 0; i <= 50; i++) {
         Vec3 pos = entityPos.m_82549_(RandomUtils.randVec().m_82541_().m_82490_(3.0));
         Vec3 vel = MathUtils.unNormedDirection(pos, entityPos).m_82537_(VecUtils.yAxis).m_82490_(0.1);
         this.burstParticleFactory.build(pos, vel);
      }
   }

   private void waveEffect() {
      Vec3 entityPos = this.entity.m_20182_();

      for (int i = 0; i <= 50; i++) {
         Vec3 randomYOffset = VecUtils.yAxis.m_82490_(this.entity.m_217043_().m_188500_());
         Vec3 randomYVel = VecUtils.yAxis.m_82490_(this.entity.m_217043_().m_188500_());
         Vec3 pos = entityPos.m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82541_().m_82490_(3.0)).m_82549_(randomYOffset);
         this.waveParticleFactory
            .continuousVelocity(
               simpleParticle -> MathUtils.unNormedDirection(simpleParticle.getPos(), entityPos)
                     .m_82537_(VecUtils.yAxis)
                     .m_82548_()
                     .m_82549_(randomYVel)
                     .m_82490_(0.1)
            )
            .build(pos, Vec3.f_82478_);
      }
   }

   private void spikeEffect() {
      Vec3 entityPos = this.entity.m_20182_();

      for (int i = 0; i <= 50; i++) {
         Vec3 randomYOffset = VecUtils.yAxis.m_82490_(this.entity.m_217043_().m_188500_());
         Vec3 randomYVel = VecUtils.yAxis.m_82490_(this.entity.m_217043_().m_188500_());
         Vec3 pos = entityPos.m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82541_().m_82490_(3.0)).m_82549_(randomYOffset);
         this.spikeParticleFactory
            .continuousVelocity(
               simpleParticle -> MathUtils.unNormedDirection(simpleParticle.getPos(), entityPos).m_82537_(VecUtils.yAxis).m_82549_(randomYVel).m_82490_(0.1)
            )
            .build(pos, Vec3.f_82478_);
      }
   }

   private void anvilEffect() {
      Vec3 entityPos = MobUtils.eyePos(this.entity);

      for (int i = 0; i <= 50; i++) {
         Vec3 pos = entityPos.m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82541_().m_82542_(3.0, 3.0, 3.0));
         this.anvilParticleFactory
            .continuousVelocity(
               simpleParticle -> MathUtils.unNormedDirection(simpleParticle.getPos(), entityPos)
                     .m_82537_(VecUtils.yAxis)
                     .m_82549_(VecUtils.yAxis.m_82542_(0.4, 0.4, 0.4))
                     .m_82542_(0.1, 0.1, 0.1)
            )
            .build(pos, Vec3.f_82478_);
      }

      this.eventScheduler.addEvent(new TimedEvent(() -> {
         Vec3 particlePos = this.entity.m_20182_().m_82549_(RandomUtils.randVec().m_82490_(3.0));
         Vec3 vel = this.entity.m_20184_().m_82490_(0.7);
         this.teleportFactory.build(particlePos, vel);
      }, 0, 80, () -> !this.entity.m_6084_()));
   }

   private void deathEffect() {
      Vec3 entityPos = VecUtils.asVec3(this.entity.m_20183_()).m_82520_(0.5, 0.5, 0.5);
      spawnPillarParticles(entityPos, this.eventScheduler);
   }

   public static void spawnPillarParticles(Vec3 entityPos, EventScheduler eventScheduler) {
      for (int i = 0; i <= 15; i++) {
         int i1 = i;
         eventScheduler.addEvent(
            new TimedEvent(
               () -> MathUtils.circleCallback(
                     3.0,
                     30,
                     VecUtils.yAxis,
                     vec3 -> deathParticleFactory.build(entityPos.m_82549_(vec3).m_82549_(VecUtils.yAxis.m_82490_((double)i1)), Vec3.f_82478_)
                  ),
               i * 5
            )
         );
      }
   }
}
