package com.aqutheseal.celestisynth.common.entity.skill;

import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.base.EffectControllerEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SkillCastBreezebreakerTornado extends EffectControllerEntity {
   public SkillCastBreezebreakerTornado(EntityType<?> entityType, Level level) {
      super(entityType, level);
   }

   @Override
   public Item getCorrespondingItem() {
      return (Item)CSItems.BREEZEBREAKER.get();
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      UUID ownerUuid = this.getOwnerUuid();
      Player ownerPlayer = ownerUuid == null ? null : this.m_9236_().m_46003_(ownerUuid);
      this.setAngleX(this.getAngleX() + this.getAddAngleX());
      this.setAngleY(this.getAngleY() + this.getAddAngleY());
      this.setAngleZ(this.getAngleZ() + this.getAddAngleZ());
      double newX = this.m_20185_() + (double)this.getAngleX();
      double newY = this.m_20186_() + (double)this.getAngleY();
      double newZ = this.m_20189_() + (double)this.getAngleZ();
      BlockPos newPos = new BlockPos((int)newX, (int)newY, (int)newZ);
      double range = 6.0;

      for (Entity entityBatch : this.f_19853_
         .m_45976_(Entity.class, new AABB(newX + range, newY + range * 2.0, newZ + range, newX - range, newY - range, newZ - range))) {
         if (entityBatch instanceof LivingEntity target && target != ownerPlayer && target.m_6084_()) {
            this.fromInterfaceWeapon().hurtNoKB(ownerPlayer, target, (float)((Double)CSConfigManager.COMMON.breezebreakerShiftSkillDmg.get()).doubleValue());
            target.m_20256_(target.m_20184_().m_82520_(0.0, 0.05 - target.m_21051_(Attributes.f_22278_).m_22135_() * 0.001, 0.0));
         }

         if (entityBatch instanceof Projectile projectile) {
            projectile.m_142687_(RemovalReason.DISCARDED);
         }
      }

      for (int yLevel = -1; yLevel < 6; yLevel++) {
         if (yLevel == -1 || yLevel == 0 || yLevel == 1) {
            CSEffectEntity.createInstance(
               ownerPlayer,
               this,
               (CSVisualType)CSVisualTypes.SOLARIS_AIR_FLAT.get(),
               (double)this.getAngleX(),
               (double)(this.getAngleY() + (float)yLevel),
               (double)this.getAngleZ()
            );
         }

         if (yLevel == 2 || yLevel == 3) {
            CSEffectEntity.createInstance(
               ownerPlayer,
               this,
               (CSVisualType)CSVisualTypes.SOLARIS_AIR_MEDIUM_FLAT.get(),
               (double)this.getAngleX(),
               (double)(this.getAngleY() + (float)yLevel),
               (double)this.getAngleZ()
            );
         }

         if (yLevel == 4 || yLevel == 5) {
            CSEffectEntity.createInstance(
               ownerPlayer,
               this,
               (CSVisualType)CSVisualTypes.SOLARIS_AIR_LARGE_FLAT.get(),
               (double)this.getAngleX(),
               (double)(this.getAngleY() + (float)yLevel),
               (double)this.getAngleZ()
            );
         }
      }

      if (this.f_19797_ % 20 == 0) {
         this.f_19853_
            .m_6263_(
               this.f_19853_.m_46003_(this.getOwnerUuid()),
               (double)this.getAngleX(),
               (double)this.getAngleY(),
               (double)this.getAngleZ(),
               (SoundEvent)CSSoundEvents.CS_WHIRLWIND.get(),
               SoundSource.HOSTILE,
               0.1F,
               0.5F + this.f_19796_.m_188501_()
            );
      }

      int radius = 2;

      for (int sx = -radius; sx <= radius; sx++) {
         for (int sy = -radius; sy <= radius; sy++) {
            for (int sz = -radius; sz <= radius; sz++) {
               if (this.m_9236_().m_8055_(newPos.m_7918_(sx, sy, sz)).m_204336_(BlockTags.f_198158_)) {
                  this.m_9236_().m_46953_(newPos.m_7918_(sx, sy, sz), false, ownerPlayer);
               }
            }
         }
      }

      if (this.f_19797_ == 100 || !this.m_9236_().m_8055_(newPos).m_60795_()) {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }
}
