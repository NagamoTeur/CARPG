package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PunchAction implements IActionWithCooldown {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final GauntletConfig mobConfig;
   private final Supplier<Boolean> cancelAction;
   private final ServerLevel serverLevel;
   private double previousSpeed = 0.0;

   public PunchAction(GauntletEntity entity, EventScheduler eventScheduler, GauntletConfig mobConfig, Supplier<Boolean> cancelAction, ServerLevel serverLevel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.mobConfig = mobConfig;
      this.cancelAction = cancelAction;
      this.serverLevel = serverLevel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.m_5448_();
      if (target == null) {
         return 40;
      } else {
         Vec3 targetPos = MobUtils.eyePos(this.entity).m_82549_(MathUtils.unNormedDirection(MobUtils.eyePos(this.entity), target.m_20182_()).m_82490_(1.2));
         int accelerateStartTime = 16;
         int unclenchTime = 56;
         BlockPos breakBoundCenter = new BlockPos(this.entity.m_20182_().m_82549_(this.entity.m_20154_()));
         AABB breakBounds = new AABB(breakBoundCenter.m_121996_(new BlockPos(1, 1, 1)), breakBoundCenter.m_7918_(1, 2, 1));
         VanillaCopiesServer.destroyBlocks(this.entity, breakBounds);
         this.entity.m_5997_(0.0, 0.7, 0.0);
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> BMDUtils.playSound(
                        this.serverLevel,
                        this.entity.m_20182_(),
                        (SoundEvent)BMDSounds.GAUNTLET_CLINK.get(),
                        SoundSource.HOSTILE,
                        2.0F,
                        BMDUtils.randomPitch(this.entity.m_217043_()) * 0.8F,
                        32.0,
                        null
                     ),
                  12,
                  1,
                  this.cancelAction
               )
            );
         AtomicReference<Double> velocityStack = new AtomicReference<>(0.6);
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            accelerateTowardsTarget(this.entity, targetPos, velocityStack.get());
            velocityStack.set(0.32);
         }, accelerateStartTime, 15, () -> this.entity.m_20182_().m_82557_(targetPos) < 9.0 || this.cancelAction.get()));
         this.eventScheduler.addEvent(new TimedEvent(this::whilePunchActive, accelerateStartTime, unclenchTime - accelerateStartTime, this.cancelAction));
         int closeFistAnimationTime = 7;
         this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setClosedFistHitbox, closeFistAnimationTime, 1, this.cancelAction));
         this.eventScheduler.addEvent(new TimedEvent(() -> this.entity.f_19853_.m_7605_(this.entity, (byte)5), unclenchTime, 1, this.cancelAction));
         this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setOpenHandHitbox, unclenchTime + 8));
         return 80;
      }
   }

   private void whilePunchActive() {
      this.testBlockPhysicalImpact();
      this.testEntityImpact();
      this.previousSpeed = this.entity.m_20184_().m_82553_();
   }

   private void testBlockPhysicalImpact() {
      if ((this.entity.f_19862_ || this.entity.f_19863_) && this.previousSpeed > 0.55F) {
         Vec3 pos = this.entity.m_20182_();
         this.entity
            .f_19853_
            .m_46511_(
               this.entity,
               pos.f_82479_,
               pos.f_82480_,
               pos.f_82481_,
               (float)(this.previousSpeed * this.mobConfig.normalPunchExplosionMultiplier),
               VanillaCopiesServer.getEntityDestructionType(this.entity.f_19853_)
            );
      }
   }

   private void testEntityImpact() {
      for (LivingEntity target : this.entity.f_19853_.m_6443_(LivingEntity.class, this.entity.m_20191_(), livingEntity -> livingEntity != this.entity)) {
         this.entity.m_7327_(target);
         BMDUtils.addDeltaMovement(target, this.entity.m_20184_().m_82490_(0.5));
      }
   }

   public static void accelerateTowardsTarget(Entity entity, Vec3 target, double velocity) {
      Vec3 dir = MathUtils.unNormedDirection(MobUtils.eyePos(entity), target).m_82541_();
      Vec3 velocityCorrection = VecUtils.planeProject(entity.m_20184_(), dir);
      BMDUtils.addDeltaMovement(entity, dir.m_82546_(velocityCorrection).m_82490_(velocity));
   }
}
