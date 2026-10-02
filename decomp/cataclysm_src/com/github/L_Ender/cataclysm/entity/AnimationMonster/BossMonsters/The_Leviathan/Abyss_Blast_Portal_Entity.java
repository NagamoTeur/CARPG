package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class Abyss_Blast_Portal_Entity extends Entity {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   private int lifeTicks = 260;
   private int laserdurations = 160;
   private boolean clientSideAttackStarted;
   private LivingEntity caster;
   private UUID casterUuid;
   public float activateProgress;
   public float prevactivateProgress;
   private static final EntityDataAccessor<Boolean> ACTIVATE = SynchedEntityData.m_135353_(Abyss_Blast_Portal_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Abyss_Blast_Portal_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.m_135353_(Abyss_Blast_Portal_Entity.class, EntityDataSerializers.f_135029_);

   public Abyss_Blast_Portal_Entity(EntityType<? extends Entity> type, Level level) {
      super(type, level);
   }

   public Abyss_Blast_Portal_Entity(
      Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, float damage, float hpdamage, LivingEntity casterIn
   ) {
      this((EntityType<? extends Entity>)ModEntities.ABYSS_BLAST_PORTAL.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.setDamage(damage);
      this.setHpDamage(hpdamage);
      this.m_6034_(x, y, z);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
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

   public void m_8119_() {
      super.m_8119_();
      this.prevactivateProgress = this.activateProgress;
      if (this.isActivate() && this.activateProgress > 0.0F) {
         this.activateProgress--;
      }

      if (this.f_19853_.f_46443_) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks--;
            if (!this.isActivate() && this.activateProgress < 10.0F) {
               this.activateProgress++;
            }

            if (this.lifeTicks == 14) {
               this.setActivate(true);
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -10 && this.isActivate()) {
            this.setActivate(false);
         }

         if (this.warmupDelayTicks == -22) {
            if (this.caster != null) {
               Portal_Abyss_Blast_Entity DeathBeam1 = new Portal_Abyss_Blast_Entity(
                  (EntityType<? extends Portal_Abyss_Blast_Entity>)ModEntities.PORTAL_ABYSS_BLAST.get(),
                  this.f_19853_,
                  this.getCaster(),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (float)((double)(this.m_146908_() - 90.0F) * Math.PI / 180.0),
                  (float) (Math.PI / 2),
                  this.laserdurations,
                  90.0F,
                  this.getDamage(),
                  this.getHpDamage()
               );
               this.f_19853_.m_7967_(DeathBeam1);
            } else {
               Portal_Abyss_Blast_Entity DeathBeam2 = new Portal_Abyss_Blast_Entity(
                  (EntityType<? extends Portal_Abyss_Blast_Entity>)ModEntities.PORTAL_ABYSS_BLAST.get(),
                  this.f_19853_,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (float)((double)(this.m_146908_() - 90.0F) * Math.PI / 180.0),
                  (float) (Math.PI / 2),
                  this.laserdurations,
                  90.0F,
                  this.getDamage(),
                  this.getHpDamage()
               );
               this.f_19853_.m_7967_(DeathBeam2);
            }
         }

         if (!this.sentSpikeEvent) {
            this.f_19853_.m_7605_(this, (byte)4);
            this.clientSideAttackStarted = true;
            this.sentSpikeEvent = true;
         }

         if (--this.lifeTicks < 0) {
            this.m_146870_();
         }
      }
   }

   public boolean m_6783_(double p_36837_) {
      double d0 = this.m_20191_().m_82309_() * 10.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return p_36837_ < d0 * d0;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(ACTIVATE, false);
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
      this.f_19804_.m_135372_(HPDAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public float getHpDamage() {
      return (Float)this.f_19804_.m_135370_(HPDAMAGE);
   }

   public void setHpDamage(float damage) {
      this.f_19804_.m_135381_(HPDAMAGE, damage);
   }

   public boolean isActivate() {
      return (Boolean)this.f_19804_.m_135370_(ACTIVATE);
   }

   public void setActivate(boolean Activate) {
      this.f_19804_.m_135381_(ACTIVATE, Activate);
   }

   protected void m_7378_(CompoundTag compound) {
      this.warmupDelayTicks = compound.m_128451_("Warmup");
      if (compound.m_128403_("Owner")) {
         this.casterUuid = compound.m_128342_("Owner");
      }

      this.setDamage(compound.m_128457_("damage"));
      this.setHpDamage(compound.m_128457_("Hpdamage"));
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.m_128362_("Owner", this.casterUuid);
      }

      compound.m_128350_("damage", this.getDamage());
      compound.m_128350_("Hpdamage", this.getHpDamage());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      super.m_7822_(id);
      if (id == 4) {
         this.clientSideAttackStarted = true;
      }
   }

   public PushReaction m_7752_() {
      return PushReaction.IGNORE;
   }
}
