package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.network.NetworkHooks;

public class Ashen_Breath_Entity extends Entity {
   private static final int RANGE = 7;
   private static final int ARC = 45;
   private LivingEntity caster;
   private UUID casterUuid;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Ashen_Breath_Entity.class, EntityDataSerializers.f_135029_);

   public Ashen_Breath_Entity(EntityType<? extends Ashen_Breath_Entity> type, Level world) {
      super(type, world);
   }

   public Ashen_Breath_Entity(EntityType<? extends Ashen_Breath_Entity> type, Level world, float damage, LivingEntity caster) {
      super(type, world);
      this.setCaster(caster);
      this.setDamage(damage);
   }

   public PushReaction m_7752_() {
      return PushReaction.IGNORE;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.caster != null && !this.caster.m_6084_()) {
         this.m_146870_();
      }

      if (this.caster != null) {
         this.m_146922_(this.caster.f_20885_);
      }

      float yaw = (float)Math.toRadians((double)(-this.m_146908_()));
      float pitch = (float)Math.toRadians((double)(-this.m_146909_()));
      float spread = 0.25F;
      float speed = 0.56F;
      float xComp = (float)(Math.sin((double)yaw) * Math.cos((double)pitch));
      float yComp = (float)Math.sin((double)pitch);
      float zComp = (float)(Math.cos((double)yaw) * Math.cos((double)pitch));
      double theta = (double)this.m_146908_() * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      double vec = 0.9;
      if (this.f_19853_.f_46443_) {
         for (int i = 0; i < 80; i++) {
            double xSpeed = (double)(speed * xComp)
               + (double)(spread * 1.0F * (this.f_19796_.m_188501_() * 2.0F - 1.0F)) * Math.sqrt((double)(1.0F - xComp * xComp));
            double ySpeed = (double)(speed * yComp)
               + (double)(spread * 1.0F * (this.f_19796_.m_188501_() * 2.0F - 1.0F)) * Math.sqrt((double)(1.0F - yComp * yComp));
            double zSpeed = (double)(speed * zComp)
               + (double)(spread * 1.0F * (this.f_19796_.m_188501_() * 2.0F - 1.0F)) * Math.sqrt((double)(1.0F - zComp * zComp));
            this.f_19853_.m_7106_(ParticleTypes.f_123762_, this.m_20185_() + vec * vecX, this.m_20186_(), this.m_20189_() + vec * vecZ, xSpeed, ySpeed, zSpeed);
         }

         for (int i = 0; i < 2; i++) {
            double xSpeed = (double)(speed * xComp)
               + (double)spread * 0.7 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F) * Math.sqrt((double)(1.0F - xComp * xComp));
            double ySpeed = (double)(speed * yComp)
               + (double)spread * 0.7 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F) * Math.sqrt((double)(1.0F - yComp * yComp));
            double zSpeed = (double)(speed * zComp)
               + (double)spread * 0.7 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F) * Math.sqrt((double)(1.0F - zComp * zComp));
            this.f_19853_.m_7106_(ParticleTypes.f_123744_, this.m_20185_() + vec * vecX, this.m_20186_(), this.m_20189_() + vec * vecZ, xSpeed, ySpeed, zSpeed);
         }
      }

      if (this.f_19797_ > 2 && this.caster != null) {
         this.hitEntities();
      }

      if (this.f_19797_ > 25) {
         this.m_146870_();
      }
   }

   public void hitEntities() {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby(7.0, 7.0, 7.0, 7.0)) {
         float entityHitYaw = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingYaw = this.m_146908_() % 360.0F;
         if (entityHitYaw < 0.0F) {
            entityHitYaw += 360.0F;
         }

         if (entityAttackingYaw < 0.0F) {
            entityAttackingYaw += 360.0F;
         }

         float entityRelativeYaw = entityHitYaw - entityAttackingYaw;
         float xzDistance = (float)Math.sqrt(
            (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
               + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
         );
         double hitY = entityHit.m_20186_() + (double)entityHit.m_20206_() / 2.0;
         float entityHitPitch = (float)(Math.atan2(hitY - this.m_20186_(), (double)xzDistance) * (180.0 / Math.PI) % 360.0);
         float entityAttackingPitch = -this.m_146909_() % 360.0F;
         if (entityHitPitch < 0.0F) {
            entityHitPitch += 360.0F;
         }

         if (entityAttackingPitch < 0.0F) {
            entityAttackingPitch += 360.0F;
         }

         float entityRelativePitch = entityHitPitch - entityAttackingPitch;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
               + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
               + (hitY - this.m_20186_()) * (hitY - this.m_20186_())
         );
         int distance = this.f_19797_ / 2;
         boolean inRange = entityHitDistance <= (float)distance + 1.0F;
         boolean yawCheck = entityRelativeYaw <= 22.5F && entityRelativeYaw >= -22.5F || entityRelativeYaw >= 337.5F || entityRelativeYaw <= -337.5F;
         boolean pitchCheck = entityRelativePitch <= 22.5F && entityRelativePitch >= -22.5F || entityRelativePitch >= 337.5F || entityRelativePitch <= -337.5F;
         boolean CloseCheck = this.caster instanceof Ignited_Revenant_Entity && entityHitDistance <= 2.0F;
         if ((inRange && yawCheck && pitchCheck || CloseCheck) && this.f_19797_ % 3 == 0 && !this.m_7307_(entityHit) && entityHit != this.caster) {
            boolean flag = entityHit.m_6469_(DamageSource.m_19367_(this, this.caster), this.getDamage());
            if (flag) {
               MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.f_19610_, 60, 0, false, false, true);
               entityHit.m_7292_(effectinstance);
            }
         }
      }
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(DAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public void setCaster(@Nullable LivingEntity p_190549_1_) {
      this.caster = p_190549_1_;
      this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.m_20148_();
   }

   @Nullable
   public LivingEntity getCaster() {
      if (this.caster == null && this.casterUuid != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.casterUuid);
         if (entity instanceof LivingEntity) {
            this.caster = (LivingEntity)entity;
         }
      }

      return this.caster;
   }

   protected void m_7378_(CompoundTag compound) {
      if (compound.m_128403_("Owner")) {
         this.casterUuid = compound.m_128342_("Owner");
      }

      this.setDamage(compound.m_128457_("damage"));
   }

   protected void m_7380_(CompoundTag compound) {
      if (this.casterUuid != null) {
         compound.m_128362_("Owner", this.casterUuid);
      }

      compound.m_128350_("damage", this.getDamage());
   }

   public boolean m_6087_() {
      return false;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_7334_(Entity entityIn) {
   }

   public boolean m_5829_() {
      return false;
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.f_19853_
         .m_6443_(
            entityClass,
            this.m_20191_().m_82377_(dX, dY, dZ),
            e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F) && e.m_20186_() <= this.m_20186_() + dY
         );
   }

   public boolean m_6094_() {
      return false;
   }
}
