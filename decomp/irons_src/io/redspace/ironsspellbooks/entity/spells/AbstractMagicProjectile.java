package io.redspace.ironsspellbooks.entity.spells;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;

public abstract class AbstractMagicProjectile extends Projectile implements AntiMagicSusceptible {
   protected static final int EXPIRE_TIME = 300;
   protected float damage;
   protected float explosionRadius;

   public abstract void trailParticles();

   public abstract void impactParticles(double var1, double var3, double var5);

   public abstract float getSpeed();

   public abstract Optional<SoundEvent> getImpactSound();

   public AbstractMagicProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public void shoot(Vec3 rotation) {
      this.m_20256_(rotation.m_82490_((double)this.getSpeed()));
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public float getDamage() {
      return this.damage;
   }

   public float getExplosionRadius() {
      return this.explosionRadius;
   }

   public void setExplosionRadius(float explosionRadius) {
      this.explosionRadius = explosionRadius;
   }

   protected boolean m_5603_(Entity pTarget) {
      return super.m_5603_(pTarget) && pTarget != this.m_37282_();
   }

   public void m_6043_() {
      if (this.f_19853_ instanceof ServerLevel serverLevel && !serverLevel.m_7726_().f_8325_.m_143145_().m_183913_(this.m_146902_().m_45588_())) {
         this.m_146870_();
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ > 300) {
         this.m_146870_();
      } else {
         if (this.f_19853_.f_46443_) {
            this.trailParticles();
         }

         this.handleHitDetection();
         this.travel();
      }
   }

   public void handleHitDetection() {
      HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
      if (hitresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
         this.m_6532_(hitresult);
      }
   }

   public void travel() {
      this.m_146884_(this.m_20182_().m_82549_(this.m_20184_()));
      ProjectileUtil.m_37284_(this, 1.0F);
      if (!this.m_20068_()) {
         Vec3 vec34 = this.m_20184_();
         this.m_20334_(vec34.f_82479_, vec34.f_82480_ - 0.05F, vec34.f_82481_);
      }
   }

   protected void m_6532_(HitResult hitresult) {
      super.m_6532_(hitresult);
      if (!this.f_19853_.f_46443_) {
         this.impactParticles(this.m_20185_(), this.m_20186_(), this.m_20189_());
         this.getImpactSound().ifPresent(this::doImpactSound);
      }
   }

   public boolean m_142391_() {
      return super.m_142391_() && !Objects.equals(this.m_146911_(), RemovalReason.UNLOADED_TO_CHUNK);
   }

   protected void doImpactSound(SoundEvent sound) {
      this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), sound, SoundSource.NEUTRAL, 2.0F, 0.9F + Utils.random.m_188501_() * 0.2F);
   }

   protected void m_8097_() {
   }

   @Override
   public void onAntiMagic(MagicData playerMagicData) {
      this.impactParticles(this.m_20185_(), this.m_20186_(), this.m_20189_());
      this.m_146870_();
   }

   protected void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128350_("Damage", this.getDamage());
      if (this.explosionRadius != 0.0F) {
         tag.m_128350_("ExplosionRadius", this.explosionRadius);
      }

      tag.m_128405_("Age", this.f_19797_);
   }

   protected void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.damage = tag.m_128457_("Damage");
      if (tag.m_128441_("ExplosionRadius")) {
         this.explosionRadius = tag.m_128457_("ExplosionRadius");
      }

      this.f_19797_ = tag.m_128451_("Age");
   }

   protected void m_5790_(EntityHitResult pResult) {
      super.m_5790_(pResult);
      if (!this.shouldPierceShields() && (pResult.m_82443_() instanceof ShieldPart || pResult.m_82443_() instanceof AbstractShieldEntity)) {
         this.m_8060_(new BlockHitResult(pResult.m_82443_().m_20182_(), Direction.m_122364_((double)this.m_146908_()), pResult.m_82443_().m_20183_(), false));
      }
   }

   public boolean m_6060_() {
      return false;
   }

   protected boolean shouldPierceShields() {
      return false;
   }
}
