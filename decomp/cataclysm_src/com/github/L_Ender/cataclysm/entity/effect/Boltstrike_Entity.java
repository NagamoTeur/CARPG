package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class Boltstrike_Entity extends Entity {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   public int lifeTicks;
   private boolean clientSideAttackStarted;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Boltstrike_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.m_135353_(Boltstrike_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> R = SynchedEntityData.m_135353_(Boltstrike_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> G = SynchedEntityData.m_135353_(Boltstrike_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> B = SynchedEntityData.m_135353_(Boltstrike_Entity.class, EntityDataSerializers.f_135028_);
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;

   public Boltstrike_Entity(EntityType<? extends Boltstrike_Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
      this.f_19811_ = true;
   }

   public Boltstrike_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, float damage, LivingEntity casterIn) {
      this((EntityType<? extends Boltstrike_Entity>)ModEntities.BOLT_STRIKE.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.setDamage(damage);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.m_6034_(x, y, z);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
      this.f_19804_.m_135372_(R, 0);
      this.f_19804_.m_135372_(G, 0);
      this.f_19804_.m_135372_(B, 0);
   }

   public int getR() {
      return (Integer)this.f_19804_.m_135370_(R);
   }

   public void setR(int r) {
      this.f_19804_.m_135381_(R, r);
   }

   public int getG() {
      return (Integer)this.f_19804_.m_135370_(G);
   }

   public void setG(int g) {
      this.f_19804_.m_135381_(G, g);
   }

   public int getB() {
      return (Integer)this.f_19804_.m_135370_(B);
   }

   public void setB(int b) {
      this.f_19804_.m_135381_(B, b);
   }

   public void setCaster(@Nullable LivingEntity p_190549_1_) {
      this.owner = p_190549_1_;
      this.ownerUUID = p_190549_1_ == null ? null : p_190549_1_.m_20148_();
   }

   @Nullable
   public LivingEntity getCaster() {
      if (this.owner == null && this.ownerUUID != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.ownerUUID);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public boolean m_6783_(double p_20869_) {
      double d0 = 64.0 * m_20150_();
      return p_20869_ < d0 * d0;
   }

   public Vec3 getAnglePosition(float p_20347_, double height, double Maxradius, double Minradius) {
      double angle = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
      double radius = Minradius + this.f_19796_.m_188500_() * (Maxradius - Minradius);
      double randomX = radius * Math.cos(angle);
      double randomZ = radius * Math.sin(angle);
      return this.m_20318_(p_20347_).m_82520_(randomX, height, randomZ);
   }

   public float getAnimationProgress(float partialTicks) {
      if (!this.clientSideAttackStarted) {
         return 0.0F;
      } else {
         int i = this.lifeTicks;
         if (i >= 12 && i < 18) {
            return 0.3F;
         } else {
            return i >= 18 ? Math.max(0.3F - ((float)(i - 18) + partialTicks) / 20.0F, 0.0F) : 0.0F;
         }
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks++;
            if (this.lifeTicks < 24 && this.lifeTicks > 12) {
               this.smolder(6);
            }

            if (this.lifeTicks == 12 && !this.m_20067_()) {
               this.f_19853_
                  .m_7785_(
                     this.m_20185_(),
                     this.m_20186_(),
                     this.m_20189_(),
                     (SoundEvent)ModSounds.EMP_ACTIVATED.get(),
                     this.m_5720_(),
                     1.0F,
                     this.f_19796_.m_188501_() * 0.2F + 0.85F,
                     false
                  );
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -12) {
            this.damageEntityLivingBaseNearby(1.0);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 30);
         }

         if (!this.sentSpikeEvent) {
            this.f_19853_.m_7605_(this, (byte)4);
            this.sentSpikeEvent = true;
         }

         if (++this.lifeTicks > 22) {
            this.m_146870_();
         }
      }
   }

   public void damageEntityLivingBaseNearby(double radius) {
      AABB region = new AABB(
         this.m_20185_() - radius,
         this.m_20186_() - 0.5,
         this.m_20189_() - radius,
         this.m_20185_() + radius,
         (double)(this.f_19853_.m_151558_() + 20),
         this.m_20189_() + radius
      );

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, region)) {
         this.damage(entity);
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getCaster();
      if (Hitentity.m_6084_() && !Hitentity.m_20147_() && Hitentity != livingentity) {
         if (livingentity == null) {
            Hitentity.m_6469_(DamageSource.f_19319_, this.getDamage());
         } else {
            if (livingentity.m_7307_(Hitentity)) {
               return;
            }

            Hitentity.m_6469_(DamageSource.m_19367_(this, livingentity), this.getDamage());
         }
      }
   }

   public void m_7822_(byte p_36935_) {
      super.m_7822_(p_36935_);
      if (p_36935_ == 4) {
         this.clientSideAttackStarted = true;
      }
   }

   private void smolder(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 1.5F;
         float yaw = (float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI);
         float r = this.f_19796_.m_188501_() * 0.7F;
         float x = r * Mth.m_14089_(yaw);
         float z = r * Mth.m_14031_(yaw);
         float motionY = this.f_19796_.m_188501_() * 0.8F;
         float motionX = 1.5F * Mth.m_14089_(yaw);
         float motionZ = 1.5F * Mth.m_14031_(yaw);
         this.f_19853_
            .m_7106_(
               new LightningParticle.OrbData(this.getR(), this.getG(), this.getB()),
               this.m_20185_() + (double)x,
               this.m_20186_() + 0.1,
               this.m_20189_() + (double)z,
               (double)motionX,
               (double)motionY,
               (double)motionZ
            );
      }
   }

   public void m_7380_(CompoundTag compound) {
      if (this.ownerUUID != null) {
         compound.m_128362_("Owner", this.ownerUUID);
      }

      compound.m_128350_("damage", this.getDamage());
      compound.m_128405_("r", this.getR());
      compound.m_128405_("g", this.getG());
      compound.m_128405_("b", this.getB());
   }

   public void m_7378_(CompoundTag compound) {
      if (compound.m_128403_("Owner")) {
         this.ownerUUID = compound.m_128342_("Owner");
      }

      this.setDamage(compound.m_128457_("damage"));
      this.setR(compound.m_128451_("r"));
      this.setG(compound.m_128451_("g"));
      this.setB(compound.m_128451_("b"));
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
