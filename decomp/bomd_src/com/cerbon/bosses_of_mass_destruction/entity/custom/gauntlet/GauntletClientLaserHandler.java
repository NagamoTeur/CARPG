package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.HistoricalData;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IDataAccessorHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class GauntletClientLaserHandler implements IEntityTick<Level>, IDataAccessorHandler, IEntityEventHandler {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private LivingEntity cachedBeamTarget;
   private final HistoricalData<Pair<Vec3, Vec3>> laserRenderPositions = new HistoricalData<>(Pair.of(Vec3.f_82478_, Vec3.f_82478_), 8);
   public final ClientParticleBuilder laserChargeParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
      .brightness(15728880)
      .color(BMDColors.LASER_RED)
      .colorVariation(0.2);

   public GauntletClientLaserHandler(GauntletEntity entity, EventScheduler eventScheduler) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
   }

   @Override
   public void tick(Level level) {
      LivingEntity beamTarget = this.getBeamTarget();
      if (beamTarget != null) {
         Vec3 centerBoxOffset = beamTarget.m_20191_().m_82399_().m_82546_(beamTarget.m_20182_());
         this.laserRenderPositions.set(Pair.of(beamTarget.m_20182_().m_82549_(centerBoxOffset), MobUtils.lastRenderPos(beamTarget).m_82549_(centerBoxOffset)));
      } else {
         this.laserRenderPositions.clear();
      }
   }

   public boolean shouldRenderLaser() {
      return this.laserRenderPositions.getSize() > 1;
   }

   public Pair<Vec3, Vec3> getLaserRenderPos() {
      Pair<Vec3, Vec3> laserPos = this.laserRenderPositions.getAll().get(0);
      Vec3 newPos = LaserAction.extendLaser(this.entity, (Vec3)laserPos.getFirst());
      Vec3 prevPos = LaserAction.extendLaser(this.entity, (Vec3)laserPos.getSecond());
      BlockHitResult newResult = this.entity.f_19853_.m_45547_(new ClipContext(MobUtils.eyePos(this.entity), newPos, Block.COLLIDER, Fluid.NONE, this.entity));
      BlockHitResult prevResult = this.entity
         .f_19853_
         .m_45547_(new ClipContext(MobUtils.eyePos(this.entity), prevPos, Block.COLLIDER, Fluid.NONE, this.entity));
      Vec3 colNewPos = newResult.m_6662_() == Type.BLOCK ? newResult.m_82450_() : newPos;
      Vec3 colPrevPos = prevResult.m_6662_() == Type.BLOCK ? prevResult.m_82450_() : prevPos;
      return Pair.of(colNewPos, colPrevPos);
   }

   private LivingEntity getBeamTarget() {
      if (!this.hasBeamTarget()) {
         return null;
      } else if (this.entity.f_19853_.m_5776_()) {
         if (this.cachedBeamTarget != null) {
            return this.cachedBeamTarget;
         } else if (this.entity.f_19853_.m_6815_((Integer)this.entity.m_20088_().m_135370_(GauntletEntity.laserTarget)) instanceof LivingEntity livingEntity) {
            this.cachedBeamTarget = livingEntity;
            return this.cachedBeamTarget;
         } else {
            return null;
         }
      } else {
         return this.entity.m_5448_();
      }
   }

   private boolean hasBeamTarget() {
      return (Integer)this.entity.m_20088_().m_135370_(GauntletEntity.laserTarget) != 0;
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
      if (GauntletEntity.laserTarget == data) {
         this.cachedBeamTarget = null;
      }
   }

   public void initDataTracker() {
      this.entity.m_20088_().m_135372_(GauntletEntity.laserTarget, 0);
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (status == 8) {
         this.eventScheduler.addEvent(new TimedEvent(() -> {
            Vec3 lookVec = this.entity.m_20154_();

            for (int i = 0; i <= 1; i++) {
               Vec3 circularOffset = VecUtils.rotateVector(lookVec.m_82537_(VecUtils.yAxis), lookVec, (double)RandomUtils.range(0, 359));
               Vec3 velocity = circularOffset.m_82541_().m_82548_().m_82490_(0.07).m_82549_(this.entity.m_20184_().m_82490_(1.2));
               Vec3 position = MobUtils.eyePos(this.entity).m_82549_(circularOffset).m_82549_(lookVec.m_82490_(0.5));
               this.laserChargeParticles.build(position, velocity);
            }
         }, 0, 85, () -> false));
      }
   }
}
