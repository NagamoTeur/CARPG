package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import com.cerbon.bosses_of_mass_destruction.projectile.comet.CometProjectile;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class CometRageAction implements IActionWithCooldown {
   private final LichEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final Function<Vec3, ProjectileThrower> cometThrower;
   public static final int numCometsDuringRage = 6;
   public static final int initialRageCometDelay = 60;
   public static final int delayBetweenRageComets = 30;
   public static final int rageCometsMoveDuration = 240;

   public CometRageAction(LichEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel, LichConfig lichConfig) {
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
      LivingEntity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 240;
      } else {
         this.performCometThrow((ServerPlayer)target);
         return 240;
      }
   }

   private void performCometThrow(ServerPlayer target) {
      List<Vec3> offsets = getRageCometOffsets(this.entity);

      for (int i = 0; i < offsets.size(); i++) {
         Vec3 offset = offsets.get(i);
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            Vec3 targetPos = target.m_20191_().m_82399_();
            this.cometThrower.apply(offset).throwProjectile(targetPos);
            BMDUtils.playSound(target.m_9236_(), this.entity.m_20182_(), (SoundEvent)BMDSounds.COMET_SHOOT.get(), SoundSource.HOSTILE, 3.0F, 64.0, null);
         }, 60 + i * 30, 1, this.shouldCancel));
      }

      BMDUtils.playSound(target.m_9236_(), this.entity.m_20182_(), (SoundEvent)BMDSounds.RAGE_PREPARE.get(), SoundSource.HOSTILE, 1.0F, 64.0, null);
   }

   public static List<Vec3> getRageCometOffsets(LichEntity entity) {
      List<Vec3> offsets = new ArrayList<>();
      MathUtils.circleCallback(3.0, 6, entity.m_20154_(), offsets::add);
      return offsets;
   }
}
