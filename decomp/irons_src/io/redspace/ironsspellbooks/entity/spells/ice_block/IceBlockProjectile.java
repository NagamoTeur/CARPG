package io.redspace.ironsspellbooks.entity.spells.ice_block;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class IceBlockProjectile extends AbstractMagicProjectile implements IAnimatable {
   private UUID targetUUID;
   private Entity cachedTarget;
   private List<Entity> victims;
   int airTime;
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public IceBlockProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.victims = new ArrayList<>();
      this.m_20242_(true);
   }

   public IceBlockProjectile(Level pLevel, LivingEntity owner, LivingEntity target) {
      this((EntityType<? extends Projectile>)EntityRegistry.ICE_BLOCK_PROJECTILE.get(), pLevel);
      this.m_5602_(owner);
      this.setTarget(target);
   }

   public void setAirTime(int airTimeInTicks) {
      this.airTime = airTimeInTicks;
   }

   public void setTarget(@Nullable Entity pOwner) {
      if (pOwner != null) {
         this.targetUUID = pOwner.m_20148_();
         this.cachedTarget = pOwner;
      }
   }

   @Nullable
   public Entity getTarget() {
      if (this.cachedTarget != null && !this.cachedTarget.m_213877_()) {
         return this.cachedTarget;
      } else if (this.targetUUID != null && this.f_19853_ instanceof ServerLevel) {
         this.cachedTarget = ((ServerLevel)this.f_19853_).m_8791_(this.targetUUID);
         return this.cachedTarget;
      } else {
         return null;
      }
   }

   @Override
   protected void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      if (this.targetUUID != null) {
         tag.m_128362_("Target", this.targetUUID);
      }

      if (this.airTime > 0) {
         tag.m_128405_("airTime", this.airTime);
      }
   }

   @Override
   protected void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128403_("Target")) {
         this.targetUUID = tag.m_128342_("Target");
      }

      if (tag.m_128441_("airTime")) {
         this.airTime = tag.m_128451_("airTime");
      }
   }

   @Override
   public void trailParticles() {
      for (int i = 0; i < 1; i++) {
         Vec3 random = new Vec3(Utils.getRandomScaled((double)(this.m_20205_() * 0.5F)), 0.0, Utils.getRandomScaled((double)(this.m_20205_() * 0.5F)));
         this.f_19853_.m_7106_(ParticleTypes.f_175821_, this.m_20185_() + random.f_82479_, this.m_20186_(), this.m_20189_() + random.f_82481_, 0.0, -0.05, 0.0);
      }
   }

   private void doFallingDamage(Entity target) {
      if (!this.f_19853_.f_46443_) {
         if (this.m_5603_(target) && !this.victims.contains(target)) {
            boolean flag = DamageSources.applyDamage(
               target, this.getDamage() / 2.0F, ((AbstractSpell)SpellRegistry.ICE_BLOCK_SPELL.get()).getDamageSource(this, this.m_37282_())
            );
            if (flag) {
               this.victims.add(target);
               target.f_19802_ = 0;
            }
         }
      }
   }

   private void doImpactDamage() {
      float explosionRadius = 3.5F;
      this.f_19853_.m_45933_(this, this.m_20191_().m_82400_((double)explosionRadius)).forEach(entity -> {
         if (this.m_5603_(entity)) {
            double distance = entity.m_20238_(this.m_20182_());
            if (distance < (double)(explosionRadius * explosionRadius)) {
               double p = 1.0 - Math.pow(Math.sqrt(distance) / (double)explosionRadius, 3.0);
               float damage = (float)((double)this.damage * p);
               DamageSources.applyDamage(entity, damage, ((AbstractSpell)SpellRegistry.ICE_BLOCK_SPELL.get()).getDamageSource(this, this.m_37282_()));
            }
         }
      });
   }

   @Override
   public void m_8119_() {
      this.f_19803_ = false;
      this.f_19854_ = this.m_20185_();
      this.f_19855_ = this.m_20186_();
      this.f_19856_ = this.m_20189_();
      this.f_19790_ = this.m_20185_();
      this.f_19791_ = this.m_20186_();
      this.f_19792_ = this.m_20189_();
      this.f_19859_ = this.m_146908_();
      this.f_19860_ = this.m_146909_();
      if (!this.f_19853_.f_46443_) {
         if (this.airTime <= 0) {
            if (this.m_20096_()) {
               this.doImpactDamage();
               this.m_5496_((SoundEvent)SoundRegistry.ICE_BLOCK_IMPACT.get(), 2.5F, 0.8F + this.f_19796_.m_188501_() * 0.4F);
               this.impactParticles(this.m_20185_(), this.m_20186_(), this.m_20189_());
               this.m_146870_();
            } else {
               this.f_19853_.m_45933_(this, this.m_20191_().m_82400_(0.35)).forEach(this::doFallingDamage);
            }
         }

         if (this.airTime-- > 0) {
            boolean tooHigh = false;
            this.m_20256_(this.m_20184_().m_82542_(0.95F, 0.75, 0.95F));
            if (this.getTarget() != null) {
               Entity target = this.getTarget();
               Vec3 diff = target.m_20182_().m_82546_(this.m_20182_());
               if (diff.m_165925_() > 1.0) {
                  this.m_20256_(this.m_20184_().m_82549_(diff.m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_(0.025F)));
               }

               if (this.m_20186_() - target.m_20186_() > 3.5) {
                  tooHigh = true;
               }
            } else if (this.airTime % 3 == 0) {
               HitResult ground = Utils.raycastForBlock(this.f_19853_, this.m_20182_(), this.m_20182_().m_82492_(0.0, 3.5, 0.0), Fluid.ANY);
               if (ground.m_6662_() == Type.MISS) {
                  tooHigh = true;
               } else if (Math.abs(this.m_20182_().f_82480_ - ground.m_82450_().f_82480_) < 4.0) {
               }
            }

            if (tooHigh) {
               this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
            } else {
               this.m_20256_(this.m_20184_().m_82520_(0.0, 0.01, 0.0));
            }

            if (this.airTime == 0) {
               this.m_20334_(0.0, 0.5, 0.0);
            }
         } else {
            this.m_20334_(0.0, this.m_20184_().f_82480_ - 0.15, 0.0);
         }
      } else {
         this.trailParticles();
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
   }

   public void m_146926_(float pXRot) {
   }

   public void m_146922_(float pYRot) {
   }

   public boolean m_5829_() {
      return true;
   }

   @Override
   protected boolean m_5603_(Entity pTarget) {
      return pTarget != this.m_37282_() && super.m_5603_(pTarget);
   }

   @Override
   public void impactParticles(double x, double y, double z) {
      MagicManager.spawnParticles(this.f_19853_, ParticleTypes.f_175821_, x, y, z, 50, 0.8, 0.1, 0.8, 0.2, false);
      MagicManager.spawnParticles(this.f_19853_, ParticleHelper.SNOWFLAKE, x, y, z, 25, 0.5, 0.1, 0.5, 0.3, false);
   }

   @Override
   public float getSpeed() {
      return 0.0F;
   }

   @Override
   public Optional<SoundEvent> getImpactSound() {
      return Optional.empty();
   }

   public void registerControllers(AnimationData data) {
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }
}
