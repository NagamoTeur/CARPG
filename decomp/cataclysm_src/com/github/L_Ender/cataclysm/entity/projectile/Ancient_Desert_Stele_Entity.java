package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

public class Ancient_Desert_Stele_Entity extends Projectile {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   private int lifeTicks = 70;
   private LivingEntity caster;
   private UUID casterUuid;
   private static final EntityDataAccessor<Boolean> ACTIVATE = SynchedEntityData.m_135353_(Ancient_Desert_Stele_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Ancient_Desert_Stele_Entity.class, EntityDataSerializers.f_135029_);

   public Ancient_Desert_Stele_Entity(EntityType<? extends Ancient_Desert_Stele_Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public Ancient_Desert_Stele_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, float damage, LivingEntity casterIn) {
      this((EntityType<? extends Ancient_Desert_Stele_Entity>)ModEntities.ANCIENT_DESERT_STELE.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.setDamage(damage);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.m_6034_(x, y, z);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(ACTIVATE, false);
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
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
      this.warmupDelayTicks = compound.m_128451_("Warmup");
      if (compound.m_128403_("Owner")) {
         this.casterUuid = compound.m_128342_("Owner");
      }

      this.setDamage(compound.m_128457_("damage"));
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.m_128362_("Owner", this.casterUuid);
      }

      compound.m_128350_("damage", this.getDamage());
   }

   public void m_8119_() {
      super.m_8119_();
      HitResult hitresult = ProjectileUtil.m_37294_(this, x$0 -> this.m_5603_(x$0));
      boolean flag = false;
      if (hitresult.m_6662_() == Type.BLOCK) {
         BlockPos blockpos = ((BlockHitResult)hitresult).m_82425_();
         BlockState blockstate = this.f_19853_.m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50142_)) {
            this.m_20221_(blockpos);
            flag = true;
         } else if (blockstate.m_60713_(Blocks.f_50446_)) {
            BlockEntity blockentity = this.f_19853_.m_7702_(blockpos);
            if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.m_59940_(this)) {
               TheEndGatewayBlockEntity.m_155828_(this.f_19853_, blockpos, blockstate, this, (TheEndGatewayBlockEntity)blockentity);
            }

            flag = true;
         }
      }

      if (hitresult.m_6662_() != Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
         this.m_6532_(hitresult);
      }

      this.m_20101_();
      if (this.f_19853_.f_46443_) {
         this.lifeTicks--;
      } else if (--this.warmupDelayTicks < 0) {
         if (!this.isActivate()) {
            this.setActivate(true);
         }

         if (--this.lifeTicks < 0) {
            this.m_146870_();
         }
      }

      Vec3 vec3 = this.m_20184_();
      double d2 = this.m_20185_() + vec3.f_82479_;
      double d0 = this.m_20186_() + vec3.f_82480_;
      double d1 = this.m_20189_() + vec3.f_82481_;
      if (this.isActivate()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.98));
         Vec3 vec31 = this.m_20184_();
         this.m_20334_(vec31.f_82479_, vec31.f_82480_ - 0.03, vec31.f_82481_);
      }

      this.m_6034_(d2, d0, d1);
   }

   protected void m_6532_(HitResult ray) {
      super.m_6532_(ray);
      BlockState state = Blocks.f_50062_.m_49966_();
      SoundType soundtype = state.getSoundType(this.f_19853_, this.m_20183_(), null);
      this.m_5496_(soundtype.m_56775_(), (soundtype.m_56773_() + 1.0F) / 2.0F, soundtype.m_56774_() * 0.8F);
      if (!this.f_19853_.f_46443_) {
         ((ServerLevel)this.f_19853_)
            .m_8767_(
               new BlockParticleOption(ParticleTypes.f_123794_, state),
               this.m_20185_(),
               this.m_20186_() + (double)this.m_20206_() / 2.0,
               this.m_20189_(),
               256,
               (double)this.m_20205_() / 2.0,
               (double)this.m_20206_() / 2.0,
               (double)this.m_20205_() / 2.0,
               1.0
            );
         this.m_146870_();
      }
   }

   protected void m_5790_(EntityHitResult p_213868_1_) {
      LivingEntity shooter = this.getCaster();
      Entity entity = p_213868_1_.m_82443_();
      boolean flag = false;
      if (shooter != null) {
         if (shooter != entity) {
            flag = entity.m_6469_(DamageSource.m_19340_(this, shooter), this.getDamage());
            if (flag) {
               this.m_19970_(shooter, entity);
            }
         }
      } else {
         flag = entity.m_6469_(DamageSource.f_19319_, this.getDamage());
      }

      if (flag && entity instanceof LivingEntity) {
         MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTCURSE_OF_DESERT.get(), 200, 0);
         ((LivingEntity)entity).m_7292_(effectinstance);
      }
   }

   protected void m_8060_(BlockHitResult p_230299_1_) {
   }

   public boolean isActivate() {
      return (Boolean)this.f_19804_.m_135370_(ACTIVATE);
   }

   public void setActivate(boolean Activate) {
      this.f_19804_.m_135381_(ACTIVATE, Activate);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
