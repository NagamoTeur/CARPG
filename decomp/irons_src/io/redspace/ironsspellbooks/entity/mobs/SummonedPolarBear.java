package io.redspace.ironsspellbooks.entity.mobs;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.effect.SummonTimer;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericCopyOwnerTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericFollowOwnerGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtTargetGoal;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SummonedPolarBear extends PolarBear implements MagicSummon {
   protected LivingEntity cachedSummoner;
   protected UUID summonerUUID;

   public SummonedPolarBear(EntityType<? extends PolarBear> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.f_19793_ = 1.0F;
      this.f_21364_ = 0;
   }

   public SummonedPolarBear(Level pLevel, LivingEntity owner) {
      this((EntityType<? extends PolarBear>)EntityRegistry.SUMMONED_POLAR_BEAR.get(), pLevel);
      this.setSummoner(owner);
   }

   public void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new SummonedPolarBear.PolarBearMeleeAttackGoal());
      this.f_21345_.m_25352_(7, new GenericFollowOwnerGoal(this, this::getSummoner, 0.9F, 15.0F, 5.0F, false, 25.0F));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(4, new GenericHurtByTargetGoal(this, entity -> entity == this.getSummoner()).setAlertOthers());
   }

   public InteractionResult m_6071_(Player pPlayer, InteractionHand pHand) {
      if (this.m_20160_()) {
         return super.m_6071_(pPlayer, pHand);
      } else {
         if (pPlayer == this.getSummoner()) {
            this.doPlayerRide(pPlayer);
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      }
   }

   @Nullable
   public Entity m_6688_() {
      return this.m_146895_();
   }

   protected void doPlayerRide(Player pPlayer) {
      this.m_29567_(false);
      if (!this.f_19853_.f_46443_) {
         pPlayer.m_146922_(this.m_146908_());
         pPlayer.m_146926_(this.m_146909_());
         pPlayer.m_20329_(this);
      }
   }

   @Override
   public LivingEntity getSummoner() {
      return OwnerHelper.getAndCacheOwner(this.f_19853_, this.cachedSummoner, this.summonerUUID);
   }

   public void setSummoner(@Nullable LivingEntity owner) {
      if (owner != null) {
         this.summonerUUID = owner.m_20148_();
         this.cachedSummoner = owner;
      }
   }

   public void m_6667_(DamageSource pDamageSource) {
      this.onDeathHelper();
      super.m_6667_(pDamageSource);
   }

   public void onRemovedFromWorld() {
      this.onRemovedHelper(this, (SummonTimer)MobEffectRegistry.POLAR_BEAR_TIMER.get());
      super.onRemovedFromWorld();
   }

   public void m_7378_(CompoundTag compoundTag) {
      super.m_7378_(compoundTag);
      this.summonerUUID = OwnerHelper.deserializeOwner(compoundTag);
   }

   public void m_7380_(CompoundTag compoundTag) {
      super.m_7380_(compoundTag);
      OwnerHelper.serializeOwner(compoundTag, this.summonerUUID);
   }

   public boolean m_7327_(Entity pEntity) {
      return Utils.doMeleeAttack(this, pEntity, ((AbstractSpell)SpellRegistry.SUMMON_POLAR_BEAR_SPELL.get()).getDamageSource(this, this.getSummoner()));
   }

   public boolean m_7307_(Entity pEntity) {
      return super.m_7307_(pEntity) || this.isAlliedHelper(pEntity);
   }

   @Override
   public void onUnSummon() {
      if (!this.f_19853_.f_46443_) {
         MagicManager.spawnParticles(this.f_19853_, ParticleTypes.f_123759_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 25, 0.4, 0.8, 0.4, 0.03, false);
         this.m_146870_();
      }
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return this.shouldIgnoreDamage(pSource) ? false : super.m_6469_(pSource, pAmount);
   }

   public static Builder m_29560_() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 30.0)
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.3)
         .m_22268_(Attributes.f_22281_, 6.0);
   }

   public void m_7023_(Vec3 pTravelVector) {
      if (this.m_20160_() && this.m_6688_() instanceof LivingEntity livingEntity) {
         this.f_19859_ = this.m_146908_();
         this.m_146922_(livingEntity.m_146908_());
         this.m_146926_(livingEntity.m_146909_());
         this.m_19915_(this.m_146908_(), this.m_146909_());
         this.f_20883_ = this.f_19859_;
         this.f_20885_ = this.m_146908_();
         float f = livingEntity.f_20900_ * 0.5F;
         float f1 = livingEntity.f_20902_;
         if (this.m_6109_()) {
            this.m_7910_((float)this.m_21133_(Attributes.f_22279_) * 0.55F);
            super.m_7023_(new Vec3((double)f, pTravelVector.f_82480_, (double)f1));
         }
      } else {
         super.m_7023_(pTravelVector);
      }
   }

   class PolarBearMeleeAttackGoal extends MeleeAttackGoal {
      public PolarBearMeleeAttackGoal() {
         super(SummonedPolarBear.this, 1.25, true);
      }

      protected void m_6739_(LivingEntity pEnemy, double pDistToEnemySqr) {
         double d0 = this.m_6639_(pEnemy);
         if (pDistToEnemySqr <= d0 && this.m_25564_()) {
            this.m_25563_();
            this.f_25540_.m_7327_(pEnemy);
            SummonedPolarBear.this.m_29567_(false);
         } else if (pDistToEnemySqr <= d0 * 2.0) {
            if (this.m_25564_()) {
               SummonedPolarBear.this.m_29567_(false);
               this.m_25563_();
            }

            if (this.m_25565_() <= 10) {
               SummonedPolarBear.this.m_29567_(true);
               SummonedPolarBear.this.m_29561_();
            }
         } else {
            this.m_25563_();
            SummonedPolarBear.this.m_29567_(false);
         }
      }

      public void m_8041_() {
         SummonedPolarBear.this.m_29567_(false);
         super.m_8041_();
      }
   }
}
