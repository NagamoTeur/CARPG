package com.cerbon.bosses_of_mass_destruction.projectile;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import com.cerbon.bosses_of_mass_destruction.projectile.util.ExemptEntities;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class PetalBladeProjectile extends BaseThrownItemProjectile {
   private Consumer<LivingEntity> entityHit;
   public static final EntityDataAccessor<Float> renderRotation = SynchedEntityData.m_135353_(GauntletEntity.class, EntityDataSerializers.f_135029_);

   public PetalBladeProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
      this.m_20088_().m_135372_(renderRotation, 0.0F);
   }

   public PetalBladeProjectile(LivingEntity livingEntity, Level level, Consumer<LivingEntity> entityHit, List<EntityType<?>> exemptEntities, float rotation) {
      super((EntityType<? extends ThrowableItemProjectile>)BMDEntities.PETAL_BLADE.get(), livingEntity, level, new ExemptEntities(exemptEntities));
      this.entityHit = entityHit;
      this.m_20088_().m_135372_(renderRotation, rotation);
   }

   @Override
   public void entityHit(EntityHitResult entityHitResult) {
      Entity entity = entityHitResult.m_82443_();
      Entity owner = this.m_37282_();
      if (owner instanceof LivingEntity livingEntity) {
         entity.m_6469_(DamageSource.m_19361_(this, owner), (float)livingEntity.m_21133_(Attributes.f_22281_));
         if (entity instanceof LivingEntity && this.entityHit != null) {
            this.entityHit.accept((LivingEntity)entity);
         }
      }

      this.m_146870_();
   }

   protected void m_8060_(@NotNull BlockHitResult result) {
      super.m_8060_(result);
      this.m_146870_();
   }
}
