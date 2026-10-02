package com.github.L_Ender.cataclysm.entity.Deepling;

import com.github.L_Ender.cataclysm.entity.AI.AnimalAIRandomSwimming;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.etc.AquaticMoveController;
import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.entity.projectile.Lionfish_Spike_Entity;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Lionfish_Entity extends Monster implements IAnimatedEntity {
   public static final Animation LIONFISH_BITE = Animation.create(19);
   private int animationTick;
   private Animation currentAnimation;
   public float prevOnLandProgress;
   public float onLandProgress;
   public float LayerBrightness;
   public float oLayerBrightness;
   public int LayerTicks;

   public Lionfish_Entity(EntityType<? extends Monster> monster, Level level) {
      super(monster, level);
      this.f_21364_ = 5;
      this.f_21342_ = new AquaticMoveController(this, 1.0F, 15.0F);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.m_21441_(BlockPathTypes.WATER_BORDER, 0.0F);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new SemiAquaticPathNavigator(this, worldIn);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12289_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12292_;
   }

   protected SoundEvent m_7975_(DamageSource p_29628_) {
      return SoundEvents.f_12294_;
   }

   protected SoundEvent getFlopSound() {
      return SoundEvents.f_12293_;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_11938_;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new TryFindWaterGoal(this));
      this.f_21345_.m_25352_(2, new Lionfish_Entity.AnimationMeleeAttackGoal(this, 1.0, false));
      this.f_21345_.m_25352_(3, new AnimalAIRandomSwimming(this, 1.0, 12, 5));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[]{AbstractDeepling.class, Lionfish_Entity.class}));
      this.f_21346_.m_25352_(2, new EntityAINearestTarget3D(this, Player.class, true));
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public static Builder lionfish() {
      return Monster.m_33035_().m_22268_(Attributes.f_22281_, 2.0).m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22276_, 12.0);
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   protected float m_6431_(Pose poseIn, EntityDimensions sizeIn) {
      return sizeIn.f_20378_ * 0.45F;
   }

   public boolean m_6469_(DamageSource p_32820_, float p_32821_) {
      if (this.f_19853_.f_46443_) {
         return false;
      } else {
         if (!p_32820_.m_19387_()
            && !p_32820_.m_19385_().equals("thorns")
            && p_32820_.m_7640_() instanceof LivingEntity livingentity
            && livingentity.m_6469_(DamageSource.m_19335_(this), 1.0F)) {
            livingentity.m_147207_(new MobEffectInstance(MobEffects.f_19614_, 40, 0), this);
         }

         return super.m_6469_(p_32820_, p_32821_);
      }
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevOnLandProgress = this.onLandProgress;
      if (!this.m_20069_() && this.onLandProgress < 5.0F) {
         this.onLandProgress++;
      }

      if (this.m_20069_() && this.onLandProgress > 0.0F) {
         this.onLandProgress--;
      }

      if (!this.m_20069_() && this.m_20096_() && this.f_19863_) {
         this.m_20256_(
            this.m_20184_()
               .m_82520_((double)((this.f_19796_.m_188501_() * 2.0F - 1.0F) * 0.05F), 0.4F, (double)((this.f_19796_.m_188501_() * 2.0F - 1.0F) * 0.05F))
         );
         this.m_6853_(false);
         this.f_19812_ = true;
         this.m_5496_(this.getFlopSound(), this.m_6121_(), this.m_6100_());
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.f_19853_.f_46443_) {
         this.LayerTicks++;
         this.LayerBrightness = this.LayerBrightness + (0.0F - this.LayerBrightness) * 0.8F;
      }

      if (this.m_6084_()) {
         LivingEntity target = this.m_5448_();
         if (this.getAnimation() == LIONFISH_BITE && this.getAnimationTick() == 7) {
            this.m_5496_(SoundEvents.f_12228_, 0.4F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
            if (target != null) {
               float damage = (float)this.m_21133_(Attributes.f_22281_);
               target.m_6469_(DamageSource.m_19370_(this), damage);
            }
         }
      }
   }

   protected void handleAirSupply(int p_30344_) {
      if (this.m_6084_() && !this.m_20072_()) {
         this.m_20301_(p_30344_ - 1);
         if (this.m_20146_() == -20) {
            this.m_20301_(0);
            this.m_6469_(DamageSource.f_19312_, 0.01F);
         }
      } else {
         this.m_20301_(1000);
      }
   }

   public void m_6075_() {
      int i = this.m_20146_();
      super.m_6075_();
      this.handleAirSupply(i);
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_THE_LEVIATHAN) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);
      int shardCount = 6 + this.f_19796_.m_188503_(2);
      if (!this.f_19853_.f_46443_) {
         for (int i = 0; i < shardCount; i++) {
            float f = (float)(i + 1) / (float)shardCount * 360.0F;
            Lionfish_Spike_Entity shard = new Lionfish_Spike_Entity(this.f_19853_, this);
            shard.m_6686_(
               (double)(this.f_19796_.m_188501_() * 0.4F * 2.0F - 0.4F),
               (double)(this.f_19796_.m_188501_() * 0.25F + 0.1F),
               (double)(this.f_19796_.m_188501_() * 0.4F * 2.0F - 0.4F),
               0.35F,
               1.0F
            );
            this.f_19853_.m_7967_(shard);
         }
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   public boolean m_6063_() {
      return false;
   }

   public boolean m_6914_(LevelReader worldIn) {
      return worldIn.m_45784_(this);
   }

   public void m_7023_(Vec3 travelVector) {
      if (this.m_6142_() && this.m_20069_()) {
         this.m_19920_(this.m_6113_(), travelVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9));
         if (this.m_5448_() == null) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
         }
      } else {
         super.m_7023_(travelVector);
      }
   }

   public boolean m_6785_(double p_219457_) {
      return !this.isLeashedToAngler();
   }

   private boolean isLeashedToAngler() {
      return this.m_21524_() instanceof Deepling_Angler_Entity;
   }

   public boolean m_6040_() {
      return true;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int i) {
      this.animationTick = i;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{LIONFISH_BITE, NO_ANIMATION};
   }

   static class AnimationMeleeAttackGoal extends MeleeAttackGoal {
      protected final Lionfish_Entity f_25540_;

      public AnimationMeleeAttackGoal(Lionfish_Entity p_25552_, double p_25553_, boolean p_25554_) {
         super(p_25552_, p_25553_, p_25554_);
         this.f_25540_ = p_25552_;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      protected void m_6739_(LivingEntity p_25557_, double p_25558_) {
         double d0 = this.m_6639_(p_25557_);
         if (p_25558_ <= d0 && this.f_25540_.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            this.f_25540_.setAnimation(Lionfish_Entity.LIONFISH_BITE);
         }
      }
   }
}
