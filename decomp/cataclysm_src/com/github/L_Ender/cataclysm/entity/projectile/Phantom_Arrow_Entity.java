package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.client.particle.TrackLightningParticle;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class Phantom_Arrow_Entity extends AbstractArrow {
   private static final EntityDataAccessor<Integer> TRANSPARENCY = SynchedEntityData.m_135353_(Phantom_Arrow_Entity.class, EntityDataSerializers.f_135028_);
   @Nullable
   private Entity finalTarget;
   @Nullable
   private UUID targetId;
   private boolean stopSeeking;

   public Phantom_Arrow_Entity(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public Phantom_Arrow_Entity(EntityType type, double x, double y, double z, Level worldIn) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
   }

   public Phantom_Arrow_Entity(Level worldIn, LivingEntity shooter, LivingEntity finalTarget) {
      this((EntityType)ModEntities.PHANTOM_ARROW.get(), shooter.m_20185_(), shooter.m_20188_() - 0.1F, shooter.m_20189_(), worldIn);
      this.m_5602_(shooter);
      this.finalTarget = finalTarget;
      if (shooter instanceof Player) {
         this.f_36705_ = Pickup.ALLOWED;
      }
   }

   public Phantom_Arrow_Entity(Level worldIn, LivingEntity shooter) {
      this((EntityType)ModEntities.PHANTOM_ARROW.get(), shooter.m_20185_(), shooter.m_20188_() - 0.1F, shooter.m_20189_(), worldIn);
      this.m_5602_(shooter);
      if (shooter instanceof Player) {
         this.f_36705_ = Pickup.ALLOWED;
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(TRANSPARENCY, 0);
   }

   public Phantom_Arrow_Entity(SpawnEntity spawnEntity, Level world) {
      this((EntityType)ModEntities.PHANTOM_ARROW.get(), world);
   }

   public int getTransparency() {
      return (Integer)this.f_19804_.m_135370_(TRANSPARENCY);
   }

   public void setTransparency(int trans) {
      this.f_19804_.m_135381_(TRANSPARENCY, trans);
   }

   public void m_7380_(CompoundTag p_37357_) {
      super.m_7380_(p_37357_);
      if (this.finalTarget != null) {
         p_37357_.m_128362_("Target", this.finalTarget.m_20148_());
      }
   }

   public void m_7378_(CompoundTag p_37353_) {
      super.m_7378_(p_37353_);
      if (p_37353_.m_128403_("Target")) {
         this.targetId = p_37353_.m_128342_("Target");
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         if (this.finalTarget == null && this.targetId != null) {
            this.finalTarget = ((ServerLevel)this.f_19853_).m_8791_(this.targetId);
            if (this.finalTarget == null) {
               this.targetId = null;
            }
         }

         this.setTransparency(this.f_36697_);
         if (!this.f_36703_
            && !this.stopSeeking
            && (this.finalTarget != null && this.finalTarget.m_6084_() || this.finalTarget instanceof Player && !this.finalTarget.m_5833_())) {
            float sqrt = (float)this.m_20184_().m_82553_();
            if (sqrt > 1.25F && this.f_19797_ > 2 && this.finalTarget != null) {
               Vec3 arcVec = this.finalTarget.m_20182_().m_82520_(0.0, (double)(0.65F * this.finalTarget.m_20206_()), 0.0).m_82546_(this.m_20182_());
               if (arcVec.m_82553_() > (double)this.finalTarget.m_20205_()) {
                  this.m_20256_(this.m_20184_().m_82490_(0.625).m_82549_(arcVec.m_82541_().m_82490_(0.4775F)));
               }
            }
         }
      } else {
         Vec3 center = this.m_20182_().m_82549_(this.m_20184_());
         Vec3 vec3 = center.m_82549_(
            new Vec3((double)(this.f_19796_.m_188501_() - 0.5F), (double)(this.f_19796_.m_188501_() - 0.5F), (double)(this.f_19796_.m_188501_() - 0.5F))
         );
         this.f_19853_
            .m_7106_(
               new TrackLightningParticle.OrbData(26, 107, 89), center.f_82479_, center.f_82480_, center.f_82481_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_
            );
         Vec3 vec31 = this.m_20184_();
         double d5 = vec31.f_82479_;
         double d6 = vec31.f_82480_;
         double d1 = vec31.f_82481_;

         for (int i = 0; i < 2; i++) {
            this.f_19853_
               .m_7106_(
                  (ParticleOptions)ModParticle.CURSED_FLAME.get(),
                  this.m_20185_() + d5 * (double)i / 4.0,
                  this.m_20186_() + d6 * (double)i / 4.0,
                  this.m_20189_() + d1 * (double)i / 4.0,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }
   }

   protected void m_6901_() {
      this.f_36697_++;
      if (this.f_36697_ >= 200) {
         this.m_146870_();
      }
   }

   protected void m_5790_(EntityHitResult p_37573_) {
      Entity entity = p_37573_.m_82443_();
      float f = (float)this.m_20184_().m_82553_();
      Entity entity1 = this.m_37282_();
      DamageSource damagesource = CMDamageTypes.causeMaledictioSagittaDamage(this, (Entity)(entity1 == null ? this : entity1));
      boolean flag = entity.m_6095_() == EntityType.f_20566_;
      this.stopSeeking = true;
      if (this.m_6060_() && !flag) {
         entity.m_20254_(5);
      }

      if (entity.m_6469_(damagesource, (float)this.m_36789_())) {
         if (flag) {
            return;
         }

         entity.f_19802_ = 0;
         if (entity instanceof LivingEntity livingentity1) {
            if (entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity1, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity1);
            }

            this.m_7761_(livingentity1);
         }
      } else {
         this.m_20256_(this.m_20184_().m_82490_(-0.1));
         this.m_146922_(this.m_146908_() + 180.0F);
         this.f_19859_ += 180.0F;
         if (!this.f_19853_.f_46443_ && this.m_20184_().m_82556_() < 1.0E-7 && this.f_36705_ == Pickup.ALLOWED) {
            this.m_5552_(this.m_7941_(), 0.1F);
         }

         this.m_146870_();
      }

      this.m_5496_(SoundEvents.f_11685_, 1.0F, 1.2F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
   }

   protected void m_7761_(LivingEntity entity) {
   }

   protected void m_8060_(BlockHitResult result) {
      super.m_8060_(result);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected ItemStack m_7941_() {
      return new ItemStack(Items.f_42412_);
   }

   protected void m_6532_(HitResult hit) {
      super.m_6532_(hit);
   }
}
