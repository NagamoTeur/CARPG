package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GauntletBlindnessIndicatorParticles implements IEntityEventHandler {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder particleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.EYE_OPEN.get())
      .brightness(15728880)
      .color((Function<Float, Vec3>)(f -> MathUtils.lerpVec(f, BMDColors.WHITE, BMDColors.LASER_RED)));
   private final ClientParticleBuilder gauntletParticleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.SOUL_FLAME.get())
      .color(BMDColors.DARK_RED)
      .brightness(15728880)
      .scale(0.25F)
      .age(20);

   public GauntletBlindnessIndicatorParticles(GauntletEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   public void handlePlayerEffects(List<Player> players) {
      for (Player player : players) {
         this.spawnRotatingParticles(player, this.particleBuilder);
      }
   }

   private void spawnRotatingParticles(Entity player, ClientParticleBuilder particleBuilder) {
      for (int i = 0; i <= 20; i++) {
         int i1 = i;
         int startingRotation = new Random().nextInt(360);
         particleBuilder.continuousPosition(simpleParticle -> this.calculatePositions(player, i1, simpleParticle.getAge(), startingRotation))
            .build(this.calculatePositions(player, i1, 0, startingRotation), Vec3.f_82478_);
      }
   }

   private Vec3 calculatePositions(Entity player, int i, int age, int startingRotation) {
      return player.m_20182_()
         .m_82549_(VecUtils.yAxis.m_82490_((double)i * 0.1))
         .m_82549_(VecUtils.xAxis.m_82524_((float)Math.toRadians((double)(age * 2 + startingRotation))));
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (status == 11) {
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            for (int i = 0; i <= 3; i++) {
               Vec3 particlePos = MobUtils.eyePos(this.entity).m_82549_(RandomUtils.randVec().m_82541_().m_82490_(2.0));
               Vec3 particleVel = RandomUtils.randVec().m_82490_(0.05).m_82549_(VecUtils.yAxis.m_82490_(0.05));
               this.gauntletParticleBuilder.build(particlePos, particleVel);
            }
         }, 0, 10, () -> false));
      }
   }
}
