package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.HistoricalData;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventSeries;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class LaserAction implements IActionWithCooldown {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> cancelAction;
   private final ServerLevel serverLevel;
   public static final int laserLagTicks = 8;

   public LaserAction(GauntletEntity entity, EventScheduler eventScheduler, Supplier<Boolean> cancelAction, ServerLevel serverLevel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.cancelAction = cancelAction;
      this.serverLevel = serverLevel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.m_5448_();
      if (target == null) {
         return 40;
      } else {
         HistoricalData<Vec3> laserRenderPositions = new HistoricalData<>(Vec3.f_82478_, 8);
         BMDUtils.playSound(
            this.serverLevel, this.entity.m_20182_(), (SoundEvent)BMDSounds.GAUNTLET_LASER_CHARGE.get(), SoundSource.HOSTILE, 3.0F, 1.0F, 64.0, null
         );
         TimedEvent sendStartToClient = new TimedEvent(
            () -> this.entity.m_20088_().m_135381_(GauntletEntity.laserTarget, target.m_19879_()), 25, 1, this.cancelAction
         );
         TimedEvent applyLaser = new TimedEvent(() -> {
            laserRenderPositions.set(target.m_20191_().m_82399_());
            if (laserRenderPositions.getSize() == 8) {
               this.applyLaser(laserRenderPositions);
            }
         }, 0, 60, this.cancelAction);
         TimedEvent stop = new TimedEvent(() -> {
            laserRenderPositions.clear();
            this.entity.m_20088_().m_135381_(GauntletEntity.laserTarget, 0);
            this.entity.f_19853_.m_7605_(this.entity, (byte)9);
         }, 0);
         this.eventScheduler.addEvent(new EventSeries(sendStartToClient, applyLaser, stop));
         return 120;
      }
   }

   private void applyLaser(HistoricalData<Vec3> laserRenderPositions) {
      Vec3 targetLaserPos = laserRenderPositions.getAll().get(0);
      Vec3 extendedLaserPos = extendLaser(this.entity, targetLaserPos);
      BlockHitResult result = this.entity
         .f_19853_
         .m_45547_(new ClipContext(MobUtils.eyePos(this.entity), extendedLaserPos, Block.COLLIDER, Fluid.NONE, this.entity));
      if (result.m_6662_() == Type.BLOCK) {
         if (this.entity.f_19797_ % 2 == 0) {
            VanillaCopiesServer.destroyBlocks(this.entity, new AABB(result.m_82450_(), result.m_82450_()).m_82400_(0.1));
         }

         this.applyLaserToEntities(result.m_82450_());
      } else {
         this.applyLaserToEntities(extendedLaserPos);
      }
   }

   private void applyLaserToEntities(Vec3 laserTargetPos) {
      for (LivingEntity hitEntity : BMDUtils.findEntitiesInLine(this.entity.f_19853_, MobUtils.eyePos(this.entity), laserTargetPos, this.entity)
         .stream()
         .filter(LivingEntity.class::isInstance)
         .map(entity1 -> (LivingEntity)entity1)
         .toList()) {
         double originalAttack = this.entity.m_21133_(Attributes.f_22281_);
         Objects.requireNonNull(this.entity.m_21051_(Attributes.f_22281_)).m_22100_(originalAttack * 0.75);
         this.entity.m_7327_(hitEntity);
         Objects.requireNonNull(this.entity.m_21051_(Attributes.f_22281_)).m_22100_(originalAttack);
      }
   }

   public static Vec3 extendLaser(Entity entity, Vec3 laserTargetPos) {
      return MathUtils.unNormedDirection(MobUtils.eyePos(entity), laserTargetPos).m_82541_().m_82490_(30.0).m_82549_(MobUtils.eyePos(entity));
   }
}
