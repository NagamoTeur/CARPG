package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.particle.ParticleFactories;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LichParticleHandler implements IEntityEventHandler, IEntityTick<Level> {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final ClientParticleBuilder summonMissileParticleBuilder;
   private final ClientParticleBuilder teleportParticleBuilder;
   private final ClientParticleBuilder summonCometParticleBuilder;
   private final ClientParticleBuilder flameRingFactory;
   private final ClientParticleBuilder minionSummonParticleBuilder;
   private final ClientParticleBuilder thresholdParticleBuilder;
   private final ClientParticleBuilder summonRingFactory;
   private final ClientParticleBuilder summonRingCompleteFactory;
   private final ClientParticleBuilder deathParticleFactory;
   private final ClientParticleBuilder idleParticles;

   public LichParticleHandler(LichEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.summonMissileParticleBuilder = ParticleFactories.soulFlame().age(2).colorVariation(0.5);
      this.teleportParticleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.DISAPPEARING_SWIRL.get())
         .color(BMDColors.TELEPORT_PURPLE)
         .age(10, 15)
         .brightness(15728880);
      this.summonCometParticleBuilder = ParticleFactories.cometTrail().colorVariation(0.5);
      this.flameRingFactory = ParticleFactories.soulFlame()
         .color((Function<Float, Vec3>)(t -> MathUtils.lerpVec(t, BMDColors.WHITE, BMDColors.WHITE.m_82542_(0.5, 0.5, 0.5))))
         .age(0, 7);
      this.minionSummonParticleBuilder = ParticleFactories.soulFlame().color(BMDColors.WHITE);
      this.thresholdParticleBuilder = ParticleFactories.soulFlame().age(20).scale(0.5F);
      this.summonRingFactory = ParticleFactories.soulFlame().color(LichUtils.blueColorFade).colorVariation(0.5).age(10);
      this.summonRingCompleteFactory = ParticleFactories.soulFlame().color(BMDColors.WHITE).age(20, 30);
      this.deathParticleFactory = ParticleFactories.soulFlame().color(LichUtils.blueColorFade).age(40, 80).colorVariation(0.5).scale(t -> 0.5F - t * 0.3F);
      this.idleParticles = ParticleFactories.soulFlame().color(LichUtils.blueColorFade).age(30, 40).colorVariation(0.5).scale(t -> 0.25F - t * 0.1F);
   }

   @Override
   public void tick(Level level) {
      if (this.entity.m_217043_().m_188500_() > 0.9) {
         this.idleParticles.build(this.entity.m_20182_().m_82546_(VecUtils.yAxis).m_82549_(RandomUtils.randVec().m_82490_(0.2)), this.entity.m_20184_());
      }
   }

   @Override
   public void handleEntityEvent(byte status) {
      switch (status) {
         case 3:
            this.deathEffect();
         case 4:
         default:
            break;
         case 5:
            this.cometEffect();
            break;
         case 6:
            this.volleyEffect();
            break;
         case 7:
            this.minionEffect();
            break;
         case 8:
            this.minionRageEffect();
            break;
         case 9:
            this.teleportEffect();
            break;
         case 10:
            this.endTeleportEffect();
            break;
         case 11:
            this.volleyRageEffect();
            break;
         case 12:
            this.cometRageEffect();
            break;
         case 13:
            this.hpThresholdEffect();
      }
   }

   private void cometEffect() {
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> this.summonCometParticleBuilder.build(MobUtils.eyePos(this.entity).m_82549_(CometAction.getCometLaunchOffset()), Vec3.f_82478_),
               15,
               45,
               this::shouldCancelParticles
            )
         );
   }

   private void volleyEffect() {
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         for (Vec3 offset : VolleyAction.getMissileLaunchOffsets(this.entity)) {
            this.summonMissileParticleBuilder.build(MobUtils.eyePos(this.entity).m_82549_(offset), Vec3.f_82478_);
         }
      }, 16, 30, this::shouldCancelParticles));
   }

   private void minionEffect() {
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> this.minionSummonParticleBuilder
                     .build(
                        MobUtils.eyePos(this.entity)
                           .m_82549_(VecUtils.yAxis.m_82490_(1.0))
                           .m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82541_().m_82490_(this.entity.m_217043_().m_188583_())),
                        VecUtils.yAxis.m_82490_(RandomUtils.randomDouble(0.2))
                     ),
               40,
               30,
               this::shouldCancelParticles
            )
         );
   }

   private void minionRageEffect() {
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         this.animatedParticleMagicCircle(3.0, 30, 12, 0.0F);
         this.animatedParticleMagicCircle(6.0, 60, 24, 120.0F);
         this.animatedParticleMagicCircle(9.0, 90, 36, 240.0F);
      }, 10, 1, this::shouldCancelParticles));
   }

   private void teleportEffect() {
      this.eventScheduler.addEvent(new TimedEvent(this::spawnTeleportParticles, 15, 10, this::shouldCancelParticles));
   }

   private void endTeleportEffect() {
      this.eventScheduler.addEvent(new TimedEvent(this::spawnTeleportParticles, 1, 10, this::shouldCancelParticles));
   }

   private void cometRageEffect() {
      int numComets = CometRageAction.getRageCometOffsets(this.entity).size();

      for (int i = 0; i < numComets; i++) {
         int i1 = i;
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            Vec3 cometOffset = CometRageAction.getRageCometOffsets(this.entity).get(i1);
            this.summonCometParticleBuilder.build(cometOffset.m_82549_(MobUtils.eyePos(this.entity)), Vec3.f_82478_);
         }, i * 30, 60, this::shouldCancelParticles));
      }

      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> MathUtils.circleCallback(
                     3.0, 72, this.entity.m_20154_(), vec3 -> this.flameRingFactory.build(vec3.m_82549_(MobUtils.eyePos(this.entity)), Vec3.f_82478_)
                  ),
               0,
               240,
               this::shouldCancelParticles
            )
         );
   }

   private void volleyRageEffect() {
      int numVolleys = VolleyRageAction.getRageMissileVolleys(this.entity).size();

      for (int i = 0; i < numVolleys; i++) {
         int i1 = i;
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            for (Vec3 offset : VolleyRageAction.getRageMissileVolleys(this.entity).get(i1)) {
               this.summonMissileParticleBuilder.build(MobUtils.eyePos(this.entity).m_82549_(offset), Vec3.f_82478_);
            }
         }, 30 + i1 * 30, 30, this::shouldCancelParticles));
      }
   }

   private void hpThresholdEffect() {
      for (int i = 0; i < 20; i++) {
         this.thresholdParticleBuilder.build(MobUtils.eyePos(this.entity), RandomUtils.randVec());
      }
   }

   private void deathEffect() {
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         for (int i = 0; i <= 4; i++) {
            this.deathParticleFactory.build(MobUtils.eyePos(this.entity), RandomUtils.randVec());
         }
      }, 0, 10, () -> false));
   }

   private void spawnTeleportParticles() {
      this.teleportParticleBuilder.build(MobUtils.eyePos(this.entity).m_82549_(RandomUtils.randVec().m_82490_(3.0)), Vec3.f_82478_);
   }

   private void animatedParticleMagicCircle(double radius, int points, int time, float rotationDegrees) {
      Vec3 spellPos = this.entity.m_20182_();
      List<Vec3> circlePoints = MathUtils.circlePoints(radius, points, this.entity.m_20154_()).stream().toList();
      float timeScale = (float)time / (float)points;
      IntStream.range(0, circlePoints.size()).forEach(index -> {
         Vec3 off = circlePoints.get(index);
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            off.m_82524_(rotationDegrees);
            this.summonRingFactory.build(off.m_82549_(spellPos), Vec3.f_82478_);
         }, (int)((float)index * timeScale)));
      });
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> circlePoints.stream().peek(vec3 -> this.summonRingCompleteFactory.build(vec3.m_82549_(spellPos), Vec3.f_82478_)),
               (int)((float)points * timeScale)
            )
         );
   }

   private boolean shouldCancelParticles() {
      return !this.entity.m_6084_();
   }
}
