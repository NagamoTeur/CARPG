package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.common.entity.goal.stalker.DiveAttackGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.stalker.FlyHelper;
import com.hollingsworth.arsnouveau.common.entity.goal.stalker.StartFlightGoal;
import com.hollingsworth.arsnouveau.setup.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class WildenStalker extends Monster implements IAnimatable {
   int leapCooldown;
   public Vec3 orbitOffset = Vec3.f_82478_;
   public BlockPos orbitPosition = BlockPos.f_121853_;
   public static final EntityDataAccessor<Boolean> isFlying = SynchedEntityData.m_135353_(WildenStalker.class, EntityDataSerializers.f_135035_);
   public int timeFlying;
   AnimationController<WildenStalker> flyController;
   AnimationController<WildenStalker> groundController;
   AnimationController<WildenStalker> idleController;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public WildenStalker(EntityType<? extends Monster> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new FlyHelper(this);
   }

   public WildenStalker(Level worldIn) {
      this((EntityType<? extends Monster>)ModEntities.WILDEN_STALKER.get(), worldIn);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new StartFlightGoal(this));
      this.f_21345_.m_25352_(1, new DiveAttackGoal(this));
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(4, new LeapAtTargetGoal(this, 0.3F));
      this.f_21345_.m_25352_(5, new MeleeAttackGoal(this, 1.2F, true));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      if ((Boolean)Config.STALKER_ATTACK_ANIMALS.get()) {
         this.f_21346_
            .m_25352_(
               3,
               new NearestAttackableTargetGoal(
                  this, Animal.class, 10, true, false, entity -> !(entity instanceof SummonWolf) || !((SummonWolf)entity).isWildenSummon
               )
            );
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         if (this.leapCooldown > 0) {
            this.leapCooldown--;
         }

         if (this.isFlying() && this.m_20096_()) {
            this.setFlying(false);
         }

         if (this.isFlying()) {
            this.timeFlying++;
         } else {
            this.timeFlying = 0;
         }
      }
   }

   public boolean m_7327_(Entity entityIn) {
      if (!this.f_19853_.f_46443_ && entityIn instanceof LivingEntity && this.f_19853_.m_46791_() == Difficulty.HARD) {
         ((LivingEntity)entityIn).m_7292_(new MobEffectInstance(MobEffects.f_19613_, 40, 0));
      }

      return super.m_7327_(entityIn);
   }

   protected int m_5639_(float distance, float damageMultiplier) {
      return 0;
   }

   public int getLeapCooldown() {
      return this.leapCooldown;
   }

   public void setLeapCooldown(int leapCooldown) {
      this.leapCooldown = leapCooldown;
   }

   public int m_213860_() {
      return 8;
   }

   protected float m_6121_() {
      return 0.4F;
   }

   private PlayState flyPredicate(AnimationEvent<?> event) {
      if (this.isFlying()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   private PlayState groundPredicate(AnimationEvent<?> e) {
      if (this.isFlying()) {
         return PlayState.STOP;
      } else if (e.isMoving()) {
         e.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      this.flyController = new AnimationController<>(this, "flyController", 1.0F, this::flyPredicate);
      animationData.addAnimationController(this.flyController);
      this.groundController = new AnimationController<>(this, "groundController", 1.0F, this::groundPredicate);
      animationData.addAnimationController(this.groundController);
      this.idleController = new AnimationController<>(this, "idleController", 1.0F, this::idlePredicate);
      animationData.addAnimationController(this.idleController);
   }

   private <T extends IAnimatable> PlayState idlePredicate(AnimationEvent<T> tAnimationEvent) {
      if (!tAnimationEvent.isMoving() && !this.isFlying()) {
         tAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
      if (!this.isFlying()) {
         super.m_7840_(y, onGroundIn, state, pos);
      }
   }

   public void m_7023_(Vec3 travelVector) {
      if (!this.isFlying()) {
         super.m_7023_(travelVector);
      } else {
         if (this.m_20069_()) {
            this.m_19920_(0.02F, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.8F));
         } else if (this.m_20077_()) {
            this.m_19920_(0.02F, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.5));
         } else {
            BlockPos ground = new BlockPos(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_());
            float f = 0.91F;
            if (this.f_19861_) {
               f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.f_19861_) {
               f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
            }

            this.m_19920_(this.f_19861_ ? 0.1F * f1 : 0.02F, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_((double)f));
         }

         this.m_21043_(this, false);
      }
   }

   public static Builder getModdedAttributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 15.0)
         .m_22268_(Attributes.f_22279_, 0.25)
         .m_22268_(Attributes.f_22282_, 0.7)
         .m_22268_(Attributes.f_22281_, 2.5);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(isFlying, false);
   }

   public boolean isFlying() {
      return (Boolean)this.f_19804_.m_135370_(isFlying);
   }

   public void setFlying(boolean flying) {
      this.f_19804_.m_135381_(isFlying, flying);
   }

   public void m_20258_(CompoundTag pCompound) {
      super.m_20258_(pCompound);
      this.setFlying(pCompound.m_128471_("isFlying"));
   }

   public boolean m_20223_(CompoundTag pCompound) {
      pCompound.m_128379_("isFlying", this.isFlying());
      return super.m_20223_(pCompound);
   }

   public static enum Animations {
      ATTACK,
      DIVE,
      FLY;
   }
}
