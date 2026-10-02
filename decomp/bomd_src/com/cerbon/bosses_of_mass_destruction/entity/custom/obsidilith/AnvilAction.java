package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.Event;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AnvilAction implements IActionWithCooldown {
   private final Mob actor;
   private final float explosionPower;
   private final EventScheduler eventScheduler;
   private final List<Vec3> circlePoints;

   public AnvilAction(Mob actor, float explosionPower) {
      this.actor = actor;
      this.explosionPower = explosionPower;
      this.eventScheduler = BMDCapabilities.getLevelEventScheduler(actor.f_19853_);
      this.circlePoints = MathUtils.buildBlockCircle(2.0);
   }

   @Override
   public int perform() {
      LivingEntity target = this.actor.m_5448_();
      Level level = this.actor.f_19853_;
      if (!(level instanceof ServerLevel)) {
         return 80;
      } else {
         this.performAttack(target, (ServerLevel)level);
         return 80;
      }
   }

   private void performAttack(LivingEntity target, ServerLevel level) {
      BMDUtils.playSound(level, this.actor.m_20182_(), (SoundEvent)BMDSounds.OBSIDILITH_PREPARE_ATTACK.get(), SoundSource.HOSTILE, 3.0F, 1.0F, 64.0, null);
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> {
                  Vec3 targetPos = target.m_20182_();
                  Vec3 teleportPos = targetPos.m_82549_(VecUtils.yAxis.m_82490_(24.0));
                  Vec3 originalPos = this.actor.m_20182_();
                  this.actor.m_7678_(teleportPos.f_82479_, teleportPos.f_82480_, teleportPos.f_82481_, this.actor.m_146908_(), this.actor.m_146909_());
                  BMDUtils.playSound(level, teleportPos, (SoundEvent)BMDSounds.OBSIDILITH_TELEPORT.get(), SoundSource.HOSTILE, 3.0F, 64.0, null);

                  for (Vec3 pos : this.circlePoints) {
                     BlockPos particlePos = BMDUtils.findGroundBelow(this.actor.f_19853_, new BlockPos(pos.m_82549_(targetPos)).m_6630_(3), pos1 -> true)
                        .m_7494_();
                     if (particlePos.m_123342_() != 0) {
                        BMDUtils.spawnParticle(
                           level,
                           (ParticleOptions)BMDParticles.OBSIDILITH_ANVIL_INDICATOR.get(),
                           VecUtils.asVec3(particlePos).m_82549_(new Vec3(0.5, 0.1, 0.5)),
                           Vec3.f_82478_,
                           0,
                           0.0
                        );
                     }
                  }

                  this.eventScheduler
                     .addEvent(
                        new TimedEvent(
                           () -> {
                              Supplier<Boolean> shouldLand = () -> this.actor.m_20096_() || this.actor.m_20186_() < 0.0;
                              Supplier<Boolean> shouldCancelLand = () -> !this.actor.m_6084_() || shouldLand.get();
                              this.eventScheduler
                                 .addEvent(
                                    new Event(
                                       shouldLand,
                                       () -> {
                                          this.actor
                                             .f_19853_
                                             .m_46511_(
                                                this.actor,
                                                this.actor.m_20185_(),
                                                this.actor.m_20186_(),
                                                this.actor.m_20189_(),
                                                this.explosionPower,
                                                VanillaCopiesServer.getEntityDestructionType(this.actor.f_19853_)
                                             );
                                          this.eventScheduler
                                             .addEvent(
                                                new TimedEvent(
                                                   () -> {
                                                      this.actor
                                                         .m_7678_(
                                                            originalPos.f_82479_,
                                                            originalPos.f_82480_,
                                                            originalPos.f_82481_,
                                                            this.actor.m_146908_(),
                                                            this.actor.m_146909_()
                                                         );
                                                      BMDUtils.playSound(
                                                         level,
                                                         this.actor.m_20182_(),
                                                         (SoundEvent)BMDSounds.OBSIDILITH_TELEPORT.get(),
                                                         SoundSource.HOSTILE,
                                                         1.0F,
                                                         64.0,
                                                         null
                                                      );
                                                   },
                                                   20,
                                                   1,
                                                   () -> !this.actor.m_6084_()
                                                )
                                             );
                                       },
                                       shouldCancelLand
                                    )
                                 );
                           },
                           1,
                           1,
                           () -> !this.actor.m_6084_()
                        )
                     );
               },
               20,
               1,
               () -> !this.actor.m_6084_()
            )
         );
   }
}
