package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class Abyss_Mine_Entity extends Entity {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   private int lifeTicks = 800;
   private boolean clientSideAttackStarted;
   private LivingEntity caster;
   private UUID casterUuid;
   private static final EntityDataAccessor<Boolean> ACTIVATE = SynchedEntityData.m_135353_(Abyss_Mine_Entity.class, EntityDataSerializers.f_135035_);
   public float activateProgress;
   public float prevactivateProgress;
   public int time;

   public Abyss_Mine_Entity(EntityType<? extends Abyss_Mine_Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public Abyss_Mine_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, LivingEntity casterIn) {
      this((EntityType<? extends Abyss_Mine_Entity>)ModEntities.ABYSS_MINE.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.m_6034_(x, y, z);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(ACTIVATE, false);
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
      this.warmupDelayTicks = compound.m_128451_("Warmup");
      if (compound.m_128403_("Owner")) {
         this.casterUuid = compound.m_128342_("Owner");
      }
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.m_128362_("Owner", this.casterUuid);
      }
   }

   public void m_8119_() {
      super.m_8119_();
      this.time++;
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

            for (int i = 0; i < 2; i++) {
               double d0 = this.m_20185_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
               double d1 = this.m_20186_() + (double)(this.m_20206_() * 1.0F / 2.0F);
               double d2 = this.m_20189_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
               double d3 = this.f_19796_.m_188583_() * 0.3;
               double d4 = this.f_19796_.m_188583_() * 0.3;
               double d5 = this.f_19796_.m_188583_() * 0.3;
               this.f_19853_.m_7106_(new LightningParticle.OrbData(102, 26, 204), d0, d1, d2, d3, d4, d5);
            }

            if (this.lifeTicks == 14) {
               this.setActivate(true);
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -10 && this.isActivate()) {
            this.setActivate(false);
         }

         if (this.warmupDelayTicks < -20) {
            for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.2, 0.0, 0.2))) {
               this.explode(livingentity);
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

   public boolean isActivate() {
      return (Boolean)this.f_19804_.m_135370_(ACTIVATE);
   }

   public void setActivate(boolean Activate) {
      this.f_19804_.m_135381_(ACTIVATE, Activate);
   }

   private void explode(LivingEntity livingentity) {
      LivingEntity Caster = this.getCaster();
      if (livingentity.m_6084_()) {
         if (Caster != null) {
            if (!Caster.m_7307_(livingentity) && livingentity != Caster && livingentity.m_6084_()) {
               this.f_19853_.m_46511_(Caster, this.m_20185_(), this.m_20227_(0.0625), this.m_20189_(), 1.0F, BlockInteraction.NONE);
               livingentity.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get(), 200, 0));
               this.m_142687_(RemovalReason.DISCARDED);
            }
         } else {
            this.f_19853_.m_46511_(this, this.m_20185_(), this.m_20227_(0.0625), this.m_20189_(), 1.0F, BlockInteraction.NONE);
            livingentity.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get(), 200, 0));
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      super.m_7822_(id);
      if (id == 4) {
         this.clientSideAttackStarted = true;
      }
   }

   public float m_213856_() {
      return 1.0F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
