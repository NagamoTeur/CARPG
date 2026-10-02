package io.redspace.ironsspellbooks.entity.spells.fireball;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.network.spell.ClientboundFieryExplosionParticles;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class MagicFireball extends AbstractMagicProjectile {
   public MagicFireball(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.m_20242_(true);
   }

   public MagicFireball(Level pLevel, LivingEntity pShooter) {
      this((EntityType<? extends Projectile>)EntityRegistry.MAGIC_FIREBALL.get(), pLevel);
      this.m_5602_(pShooter);
   }

   @Override
   public void trailParticles() {
      Vec3 vec3 = this.m_20184_();
      double d0 = this.m_20185_() - vec3.f_82479_;
      double d1 = this.m_20186_() - vec3.f_82480_;
      double d2 = this.m_20189_() - vec3.f_82481_;
      int count = Mth.m_14045_((int)(vec3.m_82556_() * 4.0), 1, 4);

      for (int i = 0; i < count; i++) {
         Vec3 random = Utils.getRandomVec3(0.25);
         float f = (float)i / (float)count;
         double x = Mth.m_14139_((double)f, d0, this.m_20185_());
         double y = Mth.m_14139_((double)f, d1, this.m_20186_());
         double z = Mth.m_14139_((double)f, d2, this.m_20189_());
         this.f_19853_
            .m_7106_(
               ParticleTypes.f_123755_,
               x - random.f_82479_,
               y + 0.5 - random.f_82480_,
               z - random.f_82481_,
               random.f_82479_ * 0.5,
               random.f_82480_ * 0.5,
               random.f_82481_ * 0.5
            );
         this.f_19853_
            .m_7106_(
               ParticleHelper.EMBERS,
               x - random.f_82479_,
               y + 0.5 - random.f_82480_,
               z - random.f_82481_,
               random.f_82479_ * 0.5,
               random.f_82480_ * 0.5,
               random.f_82481_ * 0.5
            );
      }
   }

   @Override
   public void impactParticles(double x, double y, double z) {
   }

   @Override
   public float getSpeed() {
      return 1.15F;
   }

   @Override
   public Optional<SoundEvent> getImpactSound() {
      return Optional.of(SoundEvents.f_11913_);
   }

   @Override
   protected void m_6532_(HitResult hitResult) {
      if (!this.f_19853_.f_46443_) {
         this.impactParticles(this.f_19790_, this.f_19791_, this.f_19792_);
         float explosionRadius = this.getExplosionRadius();
         float explosionRadiusSqr = explosionRadius * explosionRadius;
         List<Entity> entities = this.f_19853_.m_45933_(this, this.m_20191_().m_82400_((double)explosionRadius));
         Vec3 losPoint = Utils.raycastForBlock(this.f_19853_, this.m_20182_(), this.m_20182_().m_82520_(0.0, 2.0, 0.0), Fluid.NONE).m_82450_();

         for (Entity entity : entities) {
            double distanceSqr = entity.m_20238_(hitResult.m_82450_());
            if (distanceSqr < (double)explosionRadiusSqr
               && this.m_5603_(entity)
               && Utils.hasLineOfSight(this.f_19853_, losPoint, entity.m_20191_().m_82399_(), true)) {
               double p = 1.0 - distanceSqr / (double)explosionRadiusSqr;
               float damage = (float)((double)this.damage * p);
               DamageSources.applyDamage(entity, damage, ((AbstractSpell)SpellRegistry.FIREBALL_SPELL.get()).getDamageSource(this, this.m_37282_()));
            }
         }

         if ((Boolean)ServerConfigs.SPELL_GREIFING.get()) {
            Explosion explosion = new Explosion(
               this.f_19853_,
               null,
               ((AbstractSpell)SpellRegistry.FIREBALL_SPELL.get()).getDamageSource(this, this.m_37282_()),
               null,
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               this.getExplosionRadius() / 2.0F,
               true,
               BlockInteraction.DESTROY
            );
            if (!ForgeEventFactory.onExplosionStart(this.f_19853_, explosion)) {
               explosion.m_46061_();
               explosion.m_46075_(false);
            }
         }

         Messages.sendToPlayersTrackingEntity(
            new ClientboundFieryExplosionParticles(new Vec3(this.m_20185_(), this.m_20186_() + 0.15F, this.m_20189_()), this.getExplosionRadius()), this
         );
         this.m_5496_(SoundEvents.f_11913_, 4.0F, (1.0F + (this.f_19853_.f_46441_.m_188501_() - this.f_19853_.f_46441_.m_188501_()) * 0.2F) * 0.7F);
         this.m_146870_();
      }
   }
}
