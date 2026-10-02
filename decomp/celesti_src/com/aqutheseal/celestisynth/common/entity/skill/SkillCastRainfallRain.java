package com.aqutheseal.celestisynth.common.entity.skill;

import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.base.EffectControllerEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.entity.projectile.RainfallArrow;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SkillCastRainfallRain extends EffectControllerEntity {
   public BlockPos targetPos = null;

   public SkillCastRainfallRain(EntityType<?> type, Level level) {
      super(type, level);
   }

   @Override
   public Item getCorrespondingItem() {
      return (Item)CSItems.RAINFALL_SERENITY.get();
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.targetPos == null) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      UUID ownerUuid = this.getOwnerUuid();
      Player ownerPlayer = ownerUuid == null ? null : this.m_9236_().m_46003_(ownerUuid);
      if (this.f_19797_ == 1) {
         CSEffectEntity.createInstance(ownerPlayer, this, (CSVisualType)CSVisualTypes.RAINFALL_RAIN.get(), 0.0, 0.0, 0.0);
      }

      if (this.f_19797_ >= 20 && this.f_19797_ <= 40 && this.f_19797_ % 2 == 0) {
         this.f_19853_
            .m_6263_(
               null,
               (double)this.targetPos.m_123341_(),
               (double)this.targetPos.m_123342_(),
               (double)this.targetPos.m_123343_(),
               (SoundEvent)CSSoundEvents.CS_LASER_SHOOT.get(),
               SoundSource.PLAYERS,
               0.4F,
               0.5F + this.f_19796_.m_188501_()
            );
         double rx = (double)(-5 + this.f_19796_.m_188503_(10));
         double rz = (double)(-5 + this.f_19796_.m_188503_(10));
         BlockPos floor = this.getFloor((double)this.targetPos.m_123341_() + rx, (double)this.targetPos.m_123343_() + rz);
         double finalDistX = (double)floor.m_123341_() - this.m_20185_();
         double finalDistZ = (double)floor.m_123343_() - this.m_20189_();
         double finalDistY = (double)floor.m_123342_() - this.m_20186_();
         if (!this.f_19853_.f_46443_) {
            RainfallArrow rainfallArrow = new RainfallArrow(this.f_19853_, ownerPlayer);
            rainfallArrow.m_5602_(ownerPlayer);
            rainfallArrow.m_20219_(this.m_20182_());
            rainfallArrow.f_36705_ = Pickup.CREATIVE_ONLY;
            rainfallArrow.setOrigin(this.m_20183_());
            rainfallArrow.m_36767_((byte)3);
            rainfallArrow.m_36781_((Double)CSConfigManager.COMMON.rainfallSerenityQuasarArrowDmg.get());
            rainfallArrow.setImbueQuasar(false);
            rainfallArrow.m_6686_(finalDistX, finalDistY, finalDistZ, 3.0F, 0.0F);
            this.f_19853_.m_7967_(rainfallArrow);
            Vec3 from = new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_());
            Vec3 to = new Vec3((double)floor.m_123341_(), (double)floor.m_123342_(), (double)floor.m_123343_());
            rainfallArrow.createLaser(from, to, false, false);
         }
      }

      if (this.f_19797_ >= 50) {
         int amount = 125;
         float expansionMultiplier = 1.0F;

         for (int i = 0; i < amount; i++) {
            float offX = (-0.5F + this.f_19796_.m_188501_()) * expansionMultiplier;
            float offY = (-0.5F + this.f_19796_.m_188501_()) * expansionMultiplier;
            float offZ = (-0.5F + this.f_19796_.m_188501_()) * expansionMultiplier;
            ParticleUtil.sendParticles(
               this.f_19853_, ParticleTypes.f_235898_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0, (double)offX, (double)offY, (double)offZ
            );
         }

         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public BlockPos getFloor(double x, double z) {
      MutableBlockPos mutablePos = new MutableBlockPos(x, this.m_20186_(), z);

      do {
         mutablePos.m_122173_(Direction.DOWN);
      } while (
         mutablePos.m_123342_() > this.f_19853_.m_141937_() && this.f_19853_.m_8055_(mutablePos).m_60647_(this.f_19853_, mutablePos, PathComputationType.LAND)
      );

      return new BlockPos(mutablePos.m_123341_(), mutablePos.m_123342_(), mutablePos.m_123343_());
   }

   public void m_142687_(RemovalReason pReason) {
      double range = 12.0;

      for (Entity entityBatch : this.f_19853_
         .m_45976_(
            Entity.class,
            new AABB(
               this.m_20185_() + range,
               this.m_20186_() + range,
               this.m_20189_() + range,
               this.m_20185_() - range,
               this.m_20186_() - range,
               this.m_20189_() - range
            )
         )) {
         if (entityBatch instanceof CSEffectEntity) {
            CSEffectEntity effect = (CSEffectEntity)entityBatch;
            if (effect.getToFollow() == this) {
               effect.m_142687_(RemovalReason.DISCARDED);
            }
         }
      }

      super.m_142687_(pReason);
   }
}
