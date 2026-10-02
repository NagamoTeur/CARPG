package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.RainbowParticleColor;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class ColoredProjectile extends Projectile {
   public static final EntityDataAccessor<Integer> RED = SynchedEntityData.m_135353_(ColoredProjectile.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> GREEN = SynchedEntityData.m_135353_(ColoredProjectile.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> BLUE = SynchedEntityData.m_135353_(ColoredProjectile.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<CompoundTag> PARTICLE_TAG = SynchedEntityData.m_135353_(ColoredProjectile.class, EntityDataSerializers.f_135042_);
   public int rainbowStartTick = 0;

   public ColoredProjectile(EntityType<? extends ColoredProjectile> type, Level worldIn) {
      super(type, worldIn);
      this.rainbowStartTick = this.f_19853_.f_46441_.m_188503_(1536);
   }

   public ColoredProjectile(EntityType<? extends ColoredProjectile> type, Level worldIn, double x, double y, double z) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
   }

   public ColoredProjectile(EntityType<? extends ColoredProjectile> type, Level worldIn, LivingEntity shooter) {
      this(type, worldIn);
      this.m_5602_(shooter);
   }

   public ParticleColor getParticleColor() {
      CompoundTag tag = (CompoundTag)this.f_19804_.m_135370_(PARTICLE_TAG);
      return ParticleColorRegistry.from((CompoundTag)this.f_19804_.m_135370_(PARTICLE_TAG)).transition(this.f_19797_ * 50);
   }

   public boolean isRainbow() {
      return this.getParticleColor() instanceof RainbowParticleColor;
   }

   public ParticleColor.IntWrapper getParticleColorWrapper() {
      return new ParticleColor.IntWrapper(
         (Integer)this.f_19804_.m_135370_(RED), (Integer)this.f_19804_.m_135370_(GREEN), (Integer)this.f_19804_.m_135370_(BLUE)
      );
   }

   public void setColor(ParticleColor colors) {
      ParticleColor.IntWrapper wrapper = colors.toWrapper();
      this.f_19804_.m_135381_(RED, wrapper.r);
      this.f_19804_.m_135381_(GREEN, wrapper.g);
      this.f_19804_.m_135381_(BLUE, wrapper.b);
      this.f_19804_.m_135381_(PARTICLE_TAG, colors.serialize());
   }

   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
      this.f_19804_.m_135381_(RED, compound.m_128451_("red"));
      this.f_19804_.m_135381_(GREEN, compound.m_128451_("green"));
      this.f_19804_.m_135381_(BLUE, compound.m_128451_("blue"));
      this.f_19804_.m_135381_(PARTICLE_TAG, compound.m_128469_("particle"));
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("red", (Integer)this.f_19804_.m_135370_(RED));
      compound.m_128405_("green", (Integer)this.f_19804_.m_135370_(GREEN));
      compound.m_128405_("blue", (Integer)this.f_19804_.m_135370_(BLUE));
      compound.m_128365_("particle", (Tag)this.f_19804_.m_135370_(PARTICLE_TAG));
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(RED, 255);
      this.f_19804_.m_135372_(GREEN, 25);
      this.f_19804_.m_135372_(BLUE, 180);
      this.f_19804_.m_135372_(PARTICLE_TAG, new ParticleColor(255, 25, 180).serialize());
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
      return ProjectileUtil.m_37304_(this.f_19853_, this, pStartVec, pEndVec, this.m_20191_().m_82369_(this.m_20184_()).m_82400_(1.0), x$0 -> this.m_5603_(x$0));
   }
}
