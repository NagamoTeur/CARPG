package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.ThrowProjectileAction;
import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import com.cerbon.bosses_of_mass_destruction.projectile.comet.CometProjectile;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class CometAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final Function<Vec3, ProjectileThrower> cometThrower;
   public static final int cometThrowDelay = 60;
   public static final int cometParticleSummonDelay = 15;
   public static final int cometThrowCooldown = 80;

   public CometAction(LichEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel, LichConfig lichConfig) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
      this.cometThrower = offset -> new ProjectileThrower(
            () -> {
               CometProjectile projectile = new CometProjectile(
                  entity,
                  entity.f_19853_,
                  vec3 -> entity.f_19853_
                        .m_46511_(
                           entity,
                           vec3.f_82479_,
                           vec3.f_82480_,
                           vec3.f_82481_,
                           lichConfig.comet.explosionStrength,
                           VanillaCopiesServer.getEntityDestructionType(entity.f_19853_)
                        ),
                  Collections.singletonList(MinionAction.summonEntityType)
               );
               MobUtils.setPos(projectile, MobUtils.eyePos(entity).m_82549_(offset));
               return new ProjectileThrower.ProjectileData(projectile, 1.6F, 0.0F, 0.2);
            }
         );
   }

   @Override
   public int perform() {
      Entity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      } else {
         this.performCometThrow(((ServerPlayer)target).m_9236_());
         return 80;
      }
   }

   private void performCometThrow(ServerLevel serverLevel) {
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> BMDUtils.playSound(serverLevel, this.entity.m_20182_(), (SoundEvent)BMDSounds.COMET_PREPARE.get(), SoundSource.HOSTILE, 3.0F, 64.0, null),
               10,
               1,
               this.shouldCancel
            )
         );
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         new ThrowProjectileAction(this.entity, this.cometThrower.apply(getCometLaunchOffset())).perform();
         BMDUtils.playSound(serverLevel, this.entity.m_20182_(), (SoundEvent)BMDSounds.COMET_SHOOT.get(), SoundSource.HOSTILE, 3.0F, 64.0, null);
      }, 60, 1, this.shouldCancel));
   }

   public static Vec3 getCometLaunchOffset() {
      return VecUtils.yAxis.m_82490_(2.0);
   }
}
