package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.goal.ConditionalMeleeGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.ConditionalWaterAvoidingGoal;
import com.hollingsworth.arsnouveau.setup.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class WildenGuardian extends Monster implements IAnimatable {
   AnimationFactory manager = GeckoLibUtil.createFactory(this);
   public int armorCooldown;
   public int armorTimeRemaining;
   public static final EntityDataAccessor<Boolean> IS_ARMORED = SynchedEntityData.m_135353_(WildenGuardian.class, EntityDataSerializers.f_135035_);
   AnimationController<WildenGuardian> controller;
   AnimationController<WildenGuardian> runController;
   AnimationController<WildenGuardian> idleController;

   public WildenGuardian(EntityType<? extends Monster> type, Level worldIn) {
      super(type, worldIn);
   }

   public WildenGuardian(Level worldIn) {
      this((EntityType<? extends Monster>)ModEntities.WILDEN_GUARDIAN.get(), worldIn);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(5, new ConditionalMeleeGoal(this, 1.2, true, () -> !this.isArmored()));
      this.f_21345_.m_25352_(8, new ConditionalWaterAvoidingGoal(this, 1.0, () -> !this.isArmored()));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      if ((Boolean)Config.GUARDIAN_ATTACK_ANIMALS.get()) {
         this.f_21346_
            .m_25352_(
               3,
               new NearestAttackableTargetGoal(
                  this, Animal.class, 10, true, false, entity -> !(entity instanceof SummonWolf) || !((SummonWolf)entity).isWildenSummon
               )
            );
      }
   }

   public boolean m_6914_(LevelReader pLevel) {
      return pLevel.m_45784_(this);
   }

   public void m_7350_(EntityDataAccessor<?> key) {
      super.m_7350_(key);
   }

   public boolean isArmored() {
      return (Boolean)this.f_19804_.m_135370_(IS_ARMORED);
   }

   public void setArmored(boolean isArmored) {
      this.f_19804_.m_135381_(IS_ARMORED, isArmored);
   }

   protected void m_6475_(DamageSource damageSrc, float damageAmount) {
      if (!this.f_19853_.f_46443_ && this.armorCooldown == 0) {
         this.setArmored(true);
         this.armorCooldown = 200;
         this.armorTimeRemaining = 100;
         this.f_21344_.m_26573_();
      }

      if (!this.f_19853_.f_46443_ && this.isArmored() && !damageSrc.m_19376_()) {
         damageAmount = (float)((double)damageAmount * 0.75);
         if (damageSrc.m_7639_() != null && BlockUtil.distanceFrom(damageSrc.m_7639_().f_19825_, this.f_19825_) <= 2.0 && !damageSrc.f_19326_.equals("thorns")) {
            damageSrc.m_7639_().m_6469_(DamageSource.m_19335_(this), 3.0F);
         }
      }

      super.m_6475_(damageSrc, damageAmount);
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return pSource == DamageSource.f_19312_ ? false : super.m_6469_(pSource, pAmount);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         if (this.armorTimeRemaining > 0) {
            this.armorTimeRemaining--;
         }

         if (this.armorTimeRemaining == 0 && this.isArmored()) {
            this.setArmored(false);
            this.explodeSpikes();
         }

         if (this.armorCooldown > 0) {
            this.armorCooldown--;
         }
      }

      if (this.isArmored() && !this.f_19853_.f_46443_) {
         this.m_21573_().m_26573_();
      }
   }

   public void explodeSpikes() {
      for (int i = 0; i < 20; i++) {
         EntityChimeraProjectile entity = new EntityChimeraProjectile(this.f_19853_);
         entity.m_37251_(
            this,
            (float)this.f_19853_.f_46441_.m_188503_(360),
            (float)this.f_19853_.f_46441_.m_188503_(360),
            0.0F,
            (float)(1.0 + ParticleUtil.inRange(0.0, 0.5)),
            1.0F
         );
         entity.m_6034_(this.f_19825_.f_82479_, this.f_19825_.f_82480_ + 1.0, this.f_19825_.f_82481_);
         this.f_19853_.m_7967_(entity);
      }

      if (this.m_5448_() != null) {
         EntityChimeraProjectile abstractarrowentity = new EntityChimeraProjectile(this.f_19853_);
         abstractarrowentity.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
         double d0 = this.m_5448_().m_20185_() - this.m_20185_();
         double d1 = this.m_5448_().m_20227_(0.3333333333333333) - abstractarrowentity.m_20186_();
         double d2 = this.m_5448_().m_20189_() - this.m_20189_();
         double d3 = (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2));
         abstractarrowentity.m_6686_(d0, d1 + d3 * 0.2F, d2, 1.6F, 1.0F);
         this.f_19853_.m_7967_(abstractarrowentity);
      }
   }

   private <T extends IAnimatable> PlayState runPredicate(AnimationEvent<T> tAnimationEvent) {
      if (this.isArmored()) {
         return PlayState.STOP;
      } else if (tAnimationEvent.isMoving()) {
         tAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   private <T extends IAnimatable> PlayState idlePredicate(AnimationEvent<T> tAnimationEvent) {
      if (this.isArmored()) {
         return PlayState.STOP;
      } else if (tAnimationEvent.isMoving()) {
         return PlayState.STOP;
      } else {
         tAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
         return PlayState.CONTINUE;
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_ARMORED, false);
   }

   private PlayState defendPredicate(AnimationEvent<?> event) {
      if (this.isArmored()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("defending"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      this.controller = new AnimationController<>(this, "attackController", 1.0F, this::defendPredicate);
      this.runController = new AnimationController<>(this, "runController", 1.0F, this::runPredicate);
      this.idleController = new AnimationController<>(this, "idleController", 1.0F, this::idlePredicate);
      animationData.addAnimationController(this.controller);
      animationData.addAnimationController(this.runController);
      animationData.addAnimationController(this.idleController);
   }

   public int getAttackDuration() {
      return 80;
   }

   public boolean m_20223_(CompoundTag compound) {
      compound.m_128405_("armorCooldown", this.armorCooldown);
      compound.m_128405_("armorTimeRemaining", this.armorTimeRemaining);
      return super.m_20223_(compound);
   }

   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
      this.armorCooldown = compound.m_128451_("armorCooldown");
      this.armorTimeRemaining = compound.m_128451_("armorTimeRemaining");
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   public static Builder getModdedAttributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 25.0)
         .m_22268_(Attributes.f_22279_, 0.25)
         .m_22268_(Attributes.f_22278_, 0.6F)
         .m_22268_(Attributes.f_22282_, 1.0)
         .m_22268_(Attributes.f_22281_, 4.5)
         .m_22268_(Attributes.f_22284_, 2.0);
   }

   protected float m_6108_() {
      return 0.98F;
   }
}
