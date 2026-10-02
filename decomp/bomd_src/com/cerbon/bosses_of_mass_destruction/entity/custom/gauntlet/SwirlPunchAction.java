package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.Vec3;

public class SwirlPunchAction implements IActionWithCooldown {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final GauntletConfig mobConfig;
   private final Supplier<Boolean> cancelAction;
   private final ServerLevel serverLevel;
   private double previousSpeed = 0.0;

   public SwirlPunchAction(
      GauntletEntity entity, EventScheduler eventScheduler, GauntletConfig mobConfig, Supplier<Boolean> cancelAction, ServerLevel serverLevel
   ) {
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
         Vec3 targetDirection = MathUtils.unNormedDirection(MobUtils.eyePos(this.entity), target.m_20191_().m_82399_());
         Vec3 targetPos = MobUtils.eyePos(this.entity).m_82549_(targetDirection.m_82490_(1.2));
         int accelerateStartTime = 30;
         int unclenchTime = 60;
         int closeFistAnimationTime = 7;
         this.entity.m_5997_(0.0, 0.7, 0.0);
         BMDUtils.playSound(
            this.serverLevel, this.entity.m_20182_(), (SoundEvent)BMDSounds.GAUNTLET_SPIN_PUNCH.get(), SoundSource.HOSTILE, 2.0F, 1.0F, 64.0, null
         );
         this.entity.m_20088_().m_135381_(GauntletEntity.isEnergized, true);
         this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setClosedFistHitbox, closeFistAnimationTime, 1, this.cancelAction));
         AtomicReference<Double> velocityStack = new AtomicReference<>(0.6);
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            PunchAction.accelerateTowardsTarget(this.entity, targetPos, velocityStack.get());
            velocityStack.set(0.4);
         }, accelerateStartTime, 15, () -> this.entity.m_20182_().m_82557_(targetPos) < 9.0 || this.cancelAction.get()));
         this.eventScheduler.addEvent(new TimedEvent(this::whilePunchActive, accelerateStartTime, unclenchTime - accelerateStartTime, this.cancelAction));
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            this.entity.hitboxHelper.setOpenHandHitbox();
            this.entity.m_20088_().m_135381_(GauntletEntity.isEnergized, false);
         }, unclenchTime));
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
         BlockInteraction flag = VanillaCopiesServer.getEntityDestructionType(this.entity.f_19853_);
         if ((Boolean)this.entity.m_20088_().m_135370_(GauntletEntity.isEnergized)) {
            this.entity.f_19853_.m_46518_(this.entity, pos.f_82479_, pos.f_82480_, pos.f_82481_, (float)this.mobConfig.energizedPunchExplosionSize, true, flag);
            this.entity.m_20088_().m_135381_(GauntletEntity.isEnergized, false);
         } else {
            this.entity
               .f_19853_
               .m_46511_(
                  this.entity, pos.f_82479_, pos.f_82480_, pos.f_82481_, (float)(this.previousSpeed * this.mobConfig.normalPunchExplosionMultiplier), flag
               );
         }
      }
   }

   private void testEntityImpact() {
      for (LivingEntity target : this.entity.f_19853_.m_6443_(LivingEntity.class, this.entity.m_20191_(), livingEntity -> livingEntity != this.entity)) {
         this.entity.m_7327_(target);
         BMDUtils.addDeltaMovement(target, this.entity.m_20184_().m_82490_(0.5));
      }
   }
}
