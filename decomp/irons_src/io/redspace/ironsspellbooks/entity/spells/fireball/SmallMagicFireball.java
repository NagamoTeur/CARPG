package io.redspace.ironsspellbooks.entity.spells.fireball;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class SmallMagicFireball extends AbstractMagicProjectile implements IEntityAdditionalSpawnData {
   @Nullable
   Entity cachedHomingTarget;
   @Nullable
   UUID homingTargetUUID;

   public SmallMagicFireball(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.m_20242_(true);
   }

   public SmallMagicFireball(Level pLevel, LivingEntity pShooter) {
      this((EntityType<? extends Projectile>)EntityRegistry.SMALL_FIREBALL_PROJECTILE.get(), pLevel);
      this.m_5602_(pShooter);
   }

   public void shoot(Vec3 rotation, float inaccuracy) {
      double speed = rotation.m_82553_();
      Vec3 offset = Utils.getRandomVec3(1.0).m_82541_().m_82490_((double)inaccuracy);
      Vec3 motion = rotation.m_82541_().m_82549_(offset).m_82541_().m_82490_(speed);
      super.shoot(motion);
   }

   @Nullable
   public Entity getHomingTarget() {
      if (this.cachedHomingTarget != null && !this.cachedHomingTarget.m_213877_()) {
         return this.cachedHomingTarget;
      } else if (this.homingTargetUUID != null && this.f_19853_ instanceof ServerLevel) {
         this.cachedHomingTarget = ((ServerLevel)this.f_19853_).m_8791_(this.homingTargetUUID);
         return this.cachedHomingTarget;
      } else {
         return null;
      }
   }

   public void setHomingTarget(LivingEntity entity) {
      this.homingTargetUUID = entity.m_20148_();
      this.cachedHomingTarget = entity;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      Entity homingTarget = this.getHomingTarget();
      if (homingTarget != null && !this.doHomingTowards(homingTarget)) {
         this.homingTargetUUID = null;
         this.cachedHomingTarget = null;
      }
   }

   private boolean doHomingTowards(Entity entity) {
      if (entity.m_213877_()) {
         return false;
      } else {
         Vec3 motion = this.m_20184_();
         double speed = this.m_20184_().m_82553_();
         Vec3 delta = entity.m_20191_().m_82399_().m_82546_(this.m_20182_()).m_82549_(entity.m_20184_());
         float f = 0.08F;
         Vec3 newMotion = new Vec3(
               Mth.m_14139_((double)f, motion.f_82479_, delta.f_82479_),
               Mth.m_14139_((double)f, motion.f_82480_, delta.f_82480_),
               Mth.m_14139_((double)f, motion.f_82481_, delta.f_82481_)
            )
            .m_82541_()
            .m_82490_(speed);
         this.m_20256_(newMotion);
         return this.f_19797_ <= 10 || !(newMotion.m_82526_(delta) < 0.0);
      }
   }

   @Override
   public void trailParticles() {
      Vec3 vec3 = this.m_20184_();
      double d0 = this.m_20185_() - vec3.f_82479_;
      double d1 = this.m_20186_() - vec3.f_82480_;
      double d2 = this.m_20189_() - vec3.f_82481_;
      int count = Mth.m_14045_((int)(vec3.m_82556_() * 4.0), 1, 5);

      for (int i = 0; i < count; i++) {
         Vec3 random = Utils.getRandomVec3(0.1);
         float f = (float)i / (float)count;
         double x = Mth.m_14139_((double)f, d0, this.m_20185_());
         double y = Mth.m_14139_((double)f, d1, this.m_20186_());
         double z = Mth.m_14139_((double)f, d2, this.m_20189_());
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
      return 1.85F;
   }

   @Override
   public Optional<SoundEvent> getImpactSound() {
      return Optional.empty();
   }

   @Override
   protected void m_5790_(EntityHitResult pResult) {
      if (!this.f_19853_.f_46443_) {
         Entity target = pResult.m_82443_();
         Entity owner = this.m_37282_();
         DamageSources.applyDamage(target, this.damage, ((AbstractSpell)SpellRegistry.BLAZE_STORM_SPELL.get()).getDamageSource(this, owner));
         if (target.m_20148_().equals(this.homingTargetUUID)) {
            target.f_19802_ = 0;
         }
      }
   }

   protected void m_8060_(BlockHitResult pResult) {
      super.m_8060_(pResult);
      if (!this.f_19853_.f_46443_ && (Boolean)ServerConfigs.SPELL_GREIFING.get()) {
         BlockPos blockpos = pResult.m_82425_().m_121945_(pResult.m_82434_());
         if (this.f_19853_.m_46859_(blockpos)) {
            this.f_19853_.m_46597_(blockpos, BaseFireBlock.m_49245_(this.f_19853_, blockpos));
         }
      }
   }

   @Override
   protected void m_6532_(HitResult pResult) {
      super.m_6532_(pResult);
      if (!this.f_19853_.f_46443_) {
         this.m_146870_();
      }
   }

   @Override
   protected void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      if (this.homingTargetUUID != null) {
         tag.m_128362_("homingTarget", this.homingTargetUUID);
      }
   }

   @Override
   protected void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128425_("homingTarget", 11)) {
         this.homingTargetUUID = tag.m_128342_("homingTarget");
      }
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      IronsSpellbooks.LOGGER.debug("Smallmagicfireball.writespawndata: {}", this.homingTargetUUID);
      Entity owner = this.m_37282_();
      buffer.writeInt(owner == null ? 0 : owner.m_19879_());
      Entity homingTarget = this.getHomingTarget();
      buffer.writeInt(homingTarget == null ? 0 : homingTarget.m_19879_());
   }

   public void readSpawnData(FriendlyByteBuf additionalData) {
      Entity owner = this.f_19853_.m_6815_(additionalData.readInt());
      if (owner != null) {
         this.m_5602_(owner);
      }

      Entity homingTarget = this.f_19853_.m_6815_(additionalData.readInt());
      if (homingTarget != null) {
         this.cachedHomingTarget = homingTarget;
         this.homingTargetUUID = homingTarget.m_20148_();
      }

      IronsSpellbooks.LOGGER.debug("Smallmagicfireball.readSpawnData: {}", this.homingTargetUUID);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
