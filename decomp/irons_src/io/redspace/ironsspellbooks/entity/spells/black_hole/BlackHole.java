package io.redspace.ironsspellbooks.entity.spells.black_hole;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlackHole extends Projectile implements AntiMagicSusceptible {
   private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(BlackHole.class, EntityDataSerializers.f_135029_);
   List<Entity> trackingEntities = new ArrayList<>();
   private int soundTick;
   private float damage;
   private static final int loopSoundDurationInTicks = 320;

   public BlackHole(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public BlackHole(Level pLevel, LivingEntity owner) {
      this((EntityType<? extends Projectile>)EntityRegistry.BLACK_HOLE.get(), pLevel);
      this.m_5602_(owner);
   }

   @Override
   public void onAntiMagic(MagicData playerMagicData) {
   }

   public void m_6210_() {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      super.m_6210_();
      this.m_6034_(d0, d1, d2);
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public float getDamage() {
      return this.damage;
   }

   public EntityDimensions m_6972_(Pose pPose) {
      return EntityDimensions.m_20395_(this.getRadius() * 2.0F, this.getRadius() * 2.0F);
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(DATA_RADIUS, 5.0F);
   }

   public void m_7350_(EntityDataAccessor<?> pKey) {
      if (DATA_RADIUS.equals(pKey)) {
         this.m_6210_();
         if (this.getRadius() < 0.1F) {
            this.m_146870_();
         }
      }

      super.m_7350_(pKey);
   }

   public void setRadius(float pRadius) {
      if (!this.f_19853_.f_46443_) {
         this.m_20088_().m_135381_(DATA_RADIUS, Math.min(pRadius, 48.0F));
      }
   }

   public float getRadius() {
      return (Float)this.m_20088_().m_135370_(DATA_RADIUS);
   }

   protected void m_7380_(CompoundTag pCompound) {
      pCompound.m_128350_("Radius", this.getRadius());
      pCompound.m_128405_("Age", this.f_19797_);
      pCompound.m_128350_("Damage", this.getDamage());
      super.m_7380_(pCompound);
   }

   protected void m_7378_(CompoundTag pCompound) {
      this.f_19797_ = pCompound.m_128451_("Age");
      this.damage = pCompound.m_128457_("Damage");
      if (this.damage == 0.0F) {
         this.damage = 1.0F;
      }

      if (pCompound.m_128451_("Radius") > 0) {
         this.setRadius(pCompound.m_128457_("Radius"));
      }

      super.m_7378_(pCompound);
   }

   public void m_8119_() {
      super.m_8119_();
      int update = Math.max((int)(this.getRadius() / 2.0F), 2);
      if (this.f_19797_ % update == 0) {
         this.updateTrackingEntities();
      }

      AABB bb = this.m_20191_();
      float radius = (float)bb.m_82362_();
      boolean hitTick = this.f_19797_ % 10 == 0;

      for (Entity entity : this.trackingEntities) {
         if (entity != this.m_37282_() && !DamageSources.isFriendlyFireBetween(this.m_37282_(), entity)) {
            Vec3 center = bb.m_82399_();
            float distance = (float)center.m_82554_(entity.m_20182_());
            if (!(distance > radius)) {
               float f = 1.0F - distance / radius;
               float scale = f * f * f * f * 0.25F;
               Vec3 diff = center.m_82546_(entity.m_20182_()).m_82490_((double)scale);
               entity.m_5997_(diff.f_82479_, diff.f_82480_, diff.f_82481_);
               if (hitTick && distance < 9.0F && this.m_5603_(entity)) {
                  DamageSources.applyDamage(entity, this.damage, ((AbstractSpell)SpellRegistry.BLACK_HOLE_SPELL.get()).getDamageSource(this, this.m_37282_()));
               }

               entity.f_19789_ = 0.0F;
            }
         }
      }

      if (!this.f_19853_.f_46443_) {
         if (this.f_19797_ > 640) {
            this.m_146870_();
            this.m_5496_((SoundEvent)SoundRegistry.BLACK_HOLE_CAST.get(), this.getRadius() / 2.0F, 1.0F);
            MagicManager.spawnParticles(
               this.f_19853_,
               ParticleHelper.UNSTABLE_ENDER,
               this.m_20185_(),
               this.m_20186_() + (double)this.getRadius(),
               this.m_20189_(),
               200,
               1.0,
               1.0,
               1.0,
               1.0,
               true
            );
         } else if ((this.f_19797_ - 1) % 320 == 0) {
            this.m_5496_((SoundEvent)SoundRegistry.BLACK_HOLE_LOOP.get(), this.getRadius() / 3.0F, 1.0F);
         }
      }
   }

   private void updateTrackingEntities() {
      this.trackingEntities = this.f_19853_.m_45933_(this, this.m_20191_().m_82400_(1.0));
   }

   public boolean m_6051_() {
      return false;
   }
}
