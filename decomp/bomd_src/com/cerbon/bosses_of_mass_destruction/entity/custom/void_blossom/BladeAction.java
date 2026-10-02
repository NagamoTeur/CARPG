package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventSeries;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.NetworkedHitboxManager;
import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import com.cerbon.bosses_of_mass_destruction.projectile.PetalBladeProjectile;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BladeAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;

   public BladeAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      } else {
         this.eventScheduler
            .addEvent(
               new EventSeries(
                  new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Petal.getId()), 10, 1, this.shouldCancel),
                  new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Idle.getId()), 90)
               )
            );
         Runnable thrower = () -> {
            Vec3 eyePos = target.m_20191_().m_82399_();
            Vec3 dir = this.entity.m_146892_().m_82546_(eyePos);
            Vec3 left = dir.m_82537_(VecUtils.yAxis).m_82541_();
            double rotation = (double)RandomUtils.randSign() * 20.0;
            Vec3 angled = VecUtils.rotateVector(left, dir, rotation);
            Vec3 lineStart = eyePos.m_82549_(angled.m_82490_(7.0));
            Vec3 lineEnd = eyePos.m_82549_(angled.m_82490_(-7.0));
            ProjectileThrower projectileThrower = new ProjectileThrower(() -> {
               PetalBladeProjectile projectile = new PetalBladeProjectile(this.entity, this.entity.f_19853_, livingEntity -> {
               }, List.of(this.entity.m_6095_()), (float)rotation);
               projectile.m_146884_(this.entity.m_146892_().m_82549_(VecUtils.yAxis));
               projectile.m_20242_(true);
               return new ProjectileThrower.ProjectileData(projectile, 0.9F, 0.0F, 0.0);
            });
            BMDUtils.playSound(
               ((ServerPlayer)target).m_9236_(),
               this.entity.m_20182_(),
               (SoundEvent)BMDSounds.PETAL_BLADE.get(),
               SoundSource.HOSTILE,
               3.0F,
               BMDUtils.randomPitch(target.m_217043_()),
               64.0,
               null
            );
            MathUtils.lineCallback(lineStart, lineEnd, 11, (vec3, integer) -> projectileThrower.throwProjectile(vec3));
         };
         this.eventScheduler.addEvent(new TimedEvent(thrower, 28, 1, this.shouldCancel));
         this.eventScheduler.addEvent(new TimedEvent(thrower, 52, 1, this.shouldCancel));
         this.eventScheduler.addEvent(new TimedEvent(thrower, 75, 1, this.shouldCancel));
         return 120;
      }
   }
}
