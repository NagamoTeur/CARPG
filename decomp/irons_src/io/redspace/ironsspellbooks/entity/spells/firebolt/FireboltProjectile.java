package io.redspace.ironsspellbooks.entity.spells.firebolt;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Optional;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class FireboltProjectile extends AbstractMagicProjectile {
   public FireboltProjectile(EntityType<? extends FireboltProjectile> entityType, Level level) {
      super(entityType, level);
      this.m_20242_(true);
   }

   public FireboltProjectile(Level levelIn, LivingEntity shooter) {
      this((EntityType<? extends FireboltProjectile>)EntityRegistry.FIREBOLT_PROJECTILE.get(), levelIn);
      this.m_5602_(shooter);
   }

   @Override
   public float getSpeed() {
      return 1.75F;
   }

   @Override
   public Optional<SoundEvent> getImpactSound() {
      return Optional.of(SoundEvents.f_11928_);
   }

   @Override
   protected void doImpactSound(SoundEvent sound) {
      this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), sound, SoundSource.NEUTRAL, 2.0F, 1.2F + Utils.random.m_188501_() * 0.2F);
   }

   protected void m_8060_(BlockHitResult blockHitResult) {
      super.m_8060_(blockHitResult);
      this.m_146870_();
   }

   @Override
   protected void m_5790_(EntityHitResult entityHitResult) {
      super.m_5790_(entityHitResult);
      Entity target = entityHitResult.m_82443_();
      DamageSources.applyDamage(target, this.getDamage(), ((AbstractSpell)SpellRegistry.FIREBOLT_SPELL.get()).getDamageSource(this, this.m_37282_()));
      this.m_146870_();
   }

   @Override
   public void impactParticles(double x, double y, double z) {
      MagicManager.spawnParticles(this.f_19853_, ParticleTypes.f_123756_, x, y, z, 5, 0.1, 0.1, 0.1, 0.25, true);
   }

   @Override
   public void trailParticles() {
      for (int i = 0; i < 1; i++) {
         float yHeading = -((float)(Mth.m_14136_(this.m_20184_().f_82481_, this.m_20184_().f_82479_) * 180.0F / (float)Math.PI) + 90.0F);
         float radius = 0.25F;
         int steps = 6;

         for (int j = 0; j < steps; j++) {
            float offset = 1.0F / (float)steps * (float)i;
            double radians = (double)(((float)this.f_19797_ + offset) / 7.5F * 360.0F * (float) (Math.PI / 180.0));
            Vec3 swirl = new Vec3(Math.cos(radians) * (double)radius, Math.sin(radians) * (double)radius, 0.0).m_82524_(yHeading * (float) (Math.PI / 180.0));
            double x = this.m_20185_() + swirl.f_82479_;
            double y = this.m_20186_() + swirl.f_82480_ + (double)(this.m_20206_() / 2.0F);
            double z = this.m_20189_() + swirl.f_82481_;
            Vec3 jitter = Utils.getRandomVec3(0.05F);
            this.f_19853_.m_7106_(ParticleHelper.EMBERS, x, y, z, jitter.f_82479_, jitter.f_82480_, jitter.f_82481_);
         }
      }
   }
}
