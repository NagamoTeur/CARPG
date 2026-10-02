package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.common.block.tile.IAnimationListener;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.WildenSummon;
import com.hollingsworth.arsnouveau.setup.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class WildenHunter extends Monster implements IAnimatable, IAnimationListener {
   public static final EntityDataAccessor<String> ANIM_STATE = SynchedEntityData.m_135353_(WildenHunter.class, EntityDataSerializers.f_135030_);
   AnimationFactory manager = GeckoLibUtil.createFactory(this);
   public int ramCooldown = 0;
   public int summonCooldown = 0;
   AnimationController<WildenHunter> controller;
   AnimationController<WildenHunter> runController;
   AnimationController<WildenHunter> idleController;

   public WildenHunter(EntityType<? extends Monster> type, Level worldIn) {
      super(type, worldIn);
   }

   public WildenHunter(Level worldIn) {
      this((EntityType<? extends Monster>)ModEntities.WILDEN_HUNTER.get(), worldIn);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(3, new WildenSummon(this));
      this.f_21345_.m_25352_(4, new LeapAtTargetGoal(this, 0.3F));
      this.f_21345_.m_25352_(5, new MeleeAttackGoal(this, 2.0, true));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      if ((Boolean)Config.HUNTER_ATTACK_ANIMALS.get()) {
         this.f_21346_
            .m_25352_(
               3,
               new NearestAttackableTargetGoal(
                  this, Animal.class, 10, true, false, entity -> !(entity instanceof SummonWolf) || !((SummonWolf)entity).isWildenSummon
               )
            );
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ANIM_STATE, WildenHunter.Animations.IDLE.name());
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return SoundEvents.f_12621_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12618_;
   }

   public int m_213860_() {
      return 5;
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public void m_5496_(SoundEvent soundIn, float volume, float pitch) {
      super.m_5496_(soundIn, volume, pitch - 0.5F);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12619_;
   }

   public static Builder getModdedAttributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.25)
         .m_22268_(Attributes.f_22278_, 0.6F)
         .m_22268_(Attributes.f_22282_, 1.5)
         .m_22268_(Attributes.f_22281_, 4.5)
         .m_22268_(Attributes.f_22284_, 2.0);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         if (this.ramCooldown > 0) {
            this.ramCooldown--;
         }

         if (this.summonCooldown > 0) {
            this.summonCooldown--;
         }
      }
   }

   @Override
   public void startAnimation(int arg) {
      try {
         if (this.controller == null) {
            return;
         }

         if (arg == WildenHunter.Animations.HOWL.ordinal()) {
            this.controller.markNeedsReload();
            this.controller.setAnimation(new AnimationBuilder().addAnimation("howl_master").addAnimation("idle"));
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   private PlayState attackPredicate(AnimationEvent<?> event) {
      return PlayState.CONTINUE;
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      this.controller = new AnimationController<>(this, "attackController", 1.0F, this::attackPredicate);
      this.runController = new AnimationController<>(this, "runController", 1.0F, this::runPredicate);
      this.idleController = new AnimationController<>(this, "idleController", 1.0F, this::idlePredicate);
      animationData.addAnimationController(this.controller);
      animationData.addAnimationController(this.runController);
      animationData.addAnimationController(this.idleController);
   }

   private <T extends IAnimatable> PlayState runPredicate(AnimationEvent<T> tAnimationEvent) {
      if (((String)this.m_20088_().m_135370_(ANIM_STATE)).equals(WildenHunter.Animations.HOWL.name())) {
         return PlayState.STOP;
      } else if (tAnimationEvent.isMoving()) {
         tAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   private <T extends IAnimatable> PlayState idlePredicate(AnimationEvent<T> tAnimationEvent) {
      if (((String)this.m_20088_().m_135370_(ANIM_STATE)).equals(WildenHunter.Animations.HOWL.name())) {
         return PlayState.STOP;
      } else if (tAnimationEvent.isMoving()) {
         return PlayState.STOP;
      } else {
         tAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
         return PlayState.CONTINUE;
      }
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   public boolean m_20223_(CompoundTag pCompound) {
      pCompound.m_128405_("summonCooldown", this.summonCooldown);
      return super.m_20223_(pCompound);
   }

   public void m_20258_(CompoundTag pCompound) {
      super.m_20258_(pCompound);
      this.summonCooldown = pCompound.m_128451_("summonCooldown");
   }

   public static enum Animations {
      ATTACK,
      RAM,
      HOWL,
      IDLE;
   }
}
