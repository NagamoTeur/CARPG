package com.aqutheseal.celestisynth.common.entity.skill;

import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.base.EffectControllerEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SkillCastPoltergeistWard extends EffectControllerEntity {
   public SkillCastPoltergeistWard(EntityType<?> type, Level level) {
      super(type, level);
   }

   @Override
   public Item getCorrespondingItem() {
      return (Item)CSItems.POLTERGEIST.get();
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      UUID ownerUuid = this.getOwnerUuid();
      Player ownerPlayer = ownerUuid == null ? null : this.m_9236_().m_46003_(ownerUuid);
      double range = 8.5;
      List<Entity> surroundingEntities = this.f_19853_
         .m_45976_(
            Entity.class,
            new AABB(
               this.m_20185_() + range,
               this.m_20186_() + range / 2.0,
               this.m_20189_() + range,
               this.m_20185_() - range,
               this.m_20186_() - range / 2.0,
               this.m_20189_() - range
            )
         );
      if (this.f_19797_ == 1) {
         CSEffectEntity.createInstance(ownerPlayer, this, (CSVisualType)CSVisualTypes.POLTERGEIST_WARD_SUMMON.get(), 0.0, 0.25, 0.0);
         CSEffectEntity.createInstance(ownerPlayer, this, (CSVisualType)CSVisualTypes.POLTERGEIST_WARD.get(), 0.0, 2.0, 0.0);
         CSEffectEntity.createInstance(ownerPlayer, this, (CSVisualType)CSVisualTypes.POLTERGEIST_WARD_GROUND.get(), 0.0, 0.65, 0.0);
      }

      if (this.f_19797_ % 20 == 0) {
         CSEffectEntity.createInstance(ownerPlayer, this, (CSVisualType)CSVisualTypes.POLTERGEIST_WARD_ABSORB.get(), 0.0, -1.0, 0.0);
         this.f_19853_.m_5594_(ownerPlayer, ownerPlayer.m_20183_(), SoundEvents.f_11880_, SoundSource.BLOCKS, 0.5F, 0.5F);

         for (Entity entityBatch : surroundingEntities) {
            if (entityBatch instanceof LivingEntity) {
               LivingEntity target = (LivingEntity)entityBatch;
               if (entityBatch != ownerPlayer) {
                  target.m_6469_(DamageSource.m_19367_(ownerPlayer, ownerPlayer), 1.5F);
                  target.m_20334_(
                     (this.m_20185_() - target.m_20185_()) / 4.0, (this.m_20186_() - target.m_20186_()) / 4.0, (this.m_20189_() - target.m_20189_()) / 4.0
                  );
                  target.f_19864_ = true;
               }
            }
         }
      }

      if (this.f_19797_ >= 100) {
         int amount = 125;
         float expansionMultiplier = 1.0F;

         for (int i = 0; i < amount; i++) {
            float offX = (-0.5F + this.f_19796_.m_188501_()) * expansionMultiplier;
            float offY = (-0.5F + this.f_19796_.m_188501_()) * expansionMultiplier;
            float offZ = (-0.5F + this.f_19796_.m_188501_()) * expansionMultiplier;
            ParticleUtil.sendParticles(
               this.f_19853_, ParticleTypes.f_123810_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0, (double)offX, (double)offY, (double)offZ
            );
         }

         for (Entity entityBatchx : surroundingEntities) {
            if (entityBatchx instanceof CSEffectEntity) {
               CSEffectEntity effect = (CSEffectEntity)entityBatchx;
               if (effect.getToFollow() == this) {
                  effect.m_142687_(RemovalReason.DISCARDED);
               }
            }
         }

         this.m_142687_(RemovalReason.DISCARDED);
      }
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
