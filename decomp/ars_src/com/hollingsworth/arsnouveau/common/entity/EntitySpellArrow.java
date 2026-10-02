package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class EntitySpellArrow extends Arrow {
   public SpellResolver spellResolver;
   public int pierceLeft;
   BlockPos lastPosHit;
   Entity lastEntityHit;
   public static final EntityDataAccessor<Integer> RED = SynchedEntityData.m_135353_(EntitySpellArrow.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> GREEN = SynchedEntityData.m_135353_(EntitySpellArrow.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> BLUE = SynchedEntityData.m_135353_(EntitySpellArrow.class, EntityDataSerializers.f_135028_);

   public EntitySpellArrow(EntityType<? extends Arrow> type, Level worldIn) {
      super(type, worldIn);
      this.setDefaultColors();
   }

   public EntitySpellArrow(Level worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
      this.setDefaultColors();
   }

   public EntitySpellArrow(Level worldIn, LivingEntity shooter) {
      super(worldIn, shooter);
      this.setDefaultColors();
   }

   public void setDefaultColors() {
      this.setColors(ParticleColor.defaultParticleColor());
   }

   public void setColors(ParticleColor color) {
      ParticleColor.IntWrapper wrapper = color.toWrapper();
      this.f_19804_.m_135381_(RED, wrapper.r);
      this.f_19804_.m_135381_(GREEN, wrapper.g);
      this.f_19804_.m_135381_(BLUE, wrapper.b);
   }

   public void m_8119_() {
      boolean isNoClip = this.m_36797_();
      Vec3 vector3d = this.m_20184_();
      if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
         float f = Mth.m_14116_((float)vector3d.m_165925_());
         this.f_19857_ = (float)(Mth.m_14136_(vector3d.f_82479_, vector3d.f_82481_) * 180.0F / (float)Math.PI);
         this.f_19858_ = (float)(Mth.m_14136_(vector3d.f_82480_, (double)f) * 180.0F / (float)Math.PI);
         this.f_19859_ = this.f_19857_;
         this.f_19860_ = this.f_19858_;
      }

      BlockPos blockpos = this.m_20183_();
      BlockState blockstate = this.f_19853_.m_8055_(blockpos);
      if (this.f_36706_ > 0) {
         this.f_36706_--;
      }

      if (this.m_20070_()) {
         this.m_20095_();
      }

      this.f_36704_ = 0;
      Vec3 vector3d2 = this.m_20182_();
      Vec3 vector3d3 = vector3d2.m_82549_(vector3d);
      HitResult raytraceresult = this.f_19853_.m_45547_(new ClipContext(vector3d2, vector3d3, Block.COLLIDER, Fluid.NONE, this));
      if (raytraceresult.m_6662_() != Type.MISS) {
         vector3d3 = raytraceresult.m_82450_();
      }

      while (!this.m_213877_()) {
         EntityHitResult entityraytraceresult = this.m_6351_(vector3d2, vector3d3);
         if (entityraytraceresult != null) {
            raytraceresult = entityraytraceresult;
         }

         if (raytraceresult instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.m_82443_();
            Entity entity1 = this.m_37282_();
            if (entity.f_19794_) {
               raytraceresult = null;
               entityraytraceresult = null;
            } else if (entity instanceof Player player1 && entity1 instanceof Player player2 && !player2.m_7099_(player1)) {
               raytraceresult = null;
               entityraytraceresult = null;
            }
         }

         if (raytraceresult != null && raytraceresult.m_6662_() != Type.MISS && !isNoClip && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.m_6532_(raytraceresult);
            this.f_19812_ = true;
         }

         if (entityraytraceresult == null || this.m_36796_() <= 0) {
            break;
         }

         raytraceresult = null;
      }

      vector3d = this.m_20184_();
      double d3 = vector3d.f_82479_;
      double d4 = vector3d.f_82480_;
      double d0 = vector3d.f_82481_;
      if (this.m_36792_()) {
         for (int i = 0; i < 4; i++) {
            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123797_,
                  this.m_20185_() + d3 * (double)i / 4.0,
                  this.m_20186_() + d4 * (double)i / 4.0,
                  this.m_20189_() + d0 * (double)i / 4.0,
                  -d3,
                  -d4 + 0.2,
                  -d0
               );
         }
      }

      double d5 = this.m_20185_() + d3;
      double d1 = this.m_20186_() + d4;
      double d2 = this.m_20189_() + d0;
      float f1 = Mth.m_14116_((float)vector3d.m_165925_());
      if (isNoClip) {
         this.f_19857_ = (float)(Mth.m_14136_(-d3, -d0) * 180.0F / (float)Math.PI);
      } else {
         this.f_19857_ = (float)(Mth.m_14136_(d3, d0) * 180.0F / (float)Math.PI);
      }

      this.f_19858_ = (float)(Mth.m_14136_(d4, (double)f1) * 180.0F / (float)Math.PI);
      this.f_19858_ = m_37273_(this.f_19860_, this.f_19858_);
      this.f_19857_ = m_37273_(this.f_19859_, this.f_19857_);
      float f2 = 0.99F;
      float f3 = 0.05F;
      if (this.m_20069_()) {
         for (int j = 0; j < 4; j++) {
            float f4 = 0.25F;
            this.f_19853_.m_7106_(ParticleTypes.f_123795_, d5 - d3 * 0.25, d1 - d4 * 0.25, d2 - d0 * 0.25, d3, d4, d0);
         }

         f2 = this.m_6882_();
      }

      this.m_20256_(vector3d.m_82490_((double)f2));
      if (!this.m_20068_() && !isNoClip) {
         Vec3 vector3d4 = this.m_20184_();
         this.m_20334_(vector3d4.f_82479_, vector3d4.f_82480_ - 0.05F, vector3d4.f_82481_);
      }

      this.m_6034_(d5, d1, d2);
      this.m_20101_();
      if (this.f_19853_.f_46443_ && this.f_19797_ > 1) {
         double deltaX = this.m_20185_() - this.f_19790_;
         double deltaY = this.m_20186_() - this.f_19791_;
         double deltaZ = this.m_20189_() - this.f_19792_;
         double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 8.0);
         int counter = 0;

         for (double j = 0.0; j < dist; j++) {
            double coeff = j / dist;
            counter += this.f_19853_.f_46441_.m_188503_(3);
            if (counter
                  % (
                     ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_() == 0
                        ? 1
                        : 2 * ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_()
                  )
               == 0) {
               this.f_19853_
                  .m_7106_(
                     GlowParticleData.createData(
                        new ParticleColor(
                           (Integer)this.f_19804_.m_135370_(RED), (Integer)this.f_19804_.m_135370_(GREEN), (Integer)this.f_19804_.m_135370_(BLUE)
                        )
                     ),
                     (double)((float)(this.f_19854_ + deltaX * coeff)),
                     (double)((float)(this.f_19855_ + deltaY * coeff)),
                     (double)((float)(this.f_19856_ + deltaZ * coeff)),
                     (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
                     (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
                     (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F))
                  );
            }
         }
      }
   }

   protected void attemptRemoval() {
      if (!this.f_19853_.f_46443_) {
         this.pierceLeft--;
         if (this.pierceLeft < 0) {
            this.f_19853_.m_7605_(this, (byte)3);
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   public byte m_36796_() {
      return 12;
   }

   protected void m_5790_(EntityHitResult p_213868_1_) {
      super.m_5790_(p_213868_1_);
      Entity entity = p_213868_1_.m_82443_();
      float f = (float)this.m_20184_().m_82553_();
      int i = Mth.m_14165_(Mth.m_14008_((double)f * this.m_36789_(), 0.0, 2.147483647E9));
      if (this.m_36792_()) {
         long j = (long)this.f_19796_.m_188503_(i / 2 + 2);
         i = (int)Math.min(j + (long)i, 2147483647L);
      }

      Entity entity1 = this.m_37282_();
      DamageSource damagesource;
      if (entity1 == null) {
         damagesource = DamageSource.m_19346_(this, this);
      } else {
         damagesource = DamageSource.m_19346_(this, entity1);
         if (entity1 instanceof LivingEntity) {
            ((LivingEntity)entity1).m_21335_(entity);
         }
      }

      boolean flag = entity.m_6095_() == EntityType.f_20566_;
      int k = entity.m_20094_();
      if (this.m_6060_() && !flag) {
         entity.m_20254_(5);
      }

      if (entity.m_6469_(damagesource, (float)i)) {
         if (flag) {
            return;
         }

         if (entity instanceof LivingEntity livingentity) {
            if (!this.f_19853_.f_46443_ && this.m_36796_() <= 0) {
               livingentity.m_21317_(livingentity.m_21234_() + 1);
            }

            if (this.f_36699_ > 0) {
               Vec3 vector3d = this.m_20184_().m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_((double)this.f_36699_ * 0.6);
               if (vector3d.m_82556_() > 0.0) {
                  livingentity.m_5997_(vector3d.f_82479_, 0.1, vector3d.f_82481_);
               }
            }

            if (!this.f_19853_.f_46443_ && entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity);
            }

            this.m_7761_(livingentity);
         }
      } else {
         entity.m_7311_(k);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(RED, 0);
      this.f_19804_.m_135372_(GREEN, 0);
      this.f_19804_.m_135372_(BLUE, 0);
   }

   protected void m_6532_(HitResult result) {
      if (this.spellResolver != null) {
         this.spellResolver.onResolveEffect(this.f_19853_, result);
      }

      Type raytraceresult$type = result.m_6662_();
      if (raytraceresult$type == Type.ENTITY) {
         if (this.spellResolver != null) {
            this.spellResolver.onResolveEffect(this.f_19853_, result);
         }

         this.m_5790_((EntityHitResult)result);
         this.attemptRemoval();
         this.lastEntityHit = ((EntityHitResult)result).m_82443_();
      } else if (raytraceresult$type == Type.BLOCK && !((BlockHitResult)result).m_82425_().equals(this.lastPosHit)) {
         if (this.spellResolver != null) {
            this.spellResolver.onResolveEffect(this.f_19853_, result);
         }

         this.m_8060_((BlockHitResult)result);
         this.lastPosHit = ((BlockHitResult)result).m_82425_();
         this.attemptRemoval();
      }
   }

   protected void m_8060_(BlockHitResult p_230299_1_) {
      BlockState blockstate = this.f_19853_.m_8055_(p_230299_1_.m_82425_());
      blockstate.m_60669_(this.f_19853_, blockstate, p_230299_1_, this);
      this.m_5496_(this.m_36784_(), 1.0F, 1.2F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_SPELL_ARROW.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntitySpellArrow(SpawnEntity packet, Level world) {
      super((EntityType)ModEntities.ENTITY_SPELL_ARROW.get(), world);
   }
}
