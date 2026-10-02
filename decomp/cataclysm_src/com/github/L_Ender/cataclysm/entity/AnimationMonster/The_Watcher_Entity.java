package com.github.L_Ender.cataclysm.entity.AnimationMonster;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.The_Prowler_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;

public class The_Watcher_Entity extends LLibrary_Monster {
   public static final Animation WATCHER_BITE = Animation.create(22);
   public static final Animation WATCHER_SHOT = Animation.create(55);
   public static final Animation WATCHER_EXTRA_SHOT = Animation.create(17);

   public The_Watcher_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 8;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
   }

   public float getStepHeight() {
      return 1.25F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, WATCHER_BITE, WATCHER_EXTRA_SHOT, WATCHER_SHOT};
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new The_Watcher_Entity.WatcherMoveGoal(this, false, 1.0));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(0, new The_Watcher_Entity.ShotPrepare(this, WATCHER_SHOT));
      this.f_21345_.m_25352_(0, new The_Watcher_Entity.Shot(this, WATCHER_EXTRA_SHOT));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder the_watcher() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 5.0)
         .m_22268_(Attributes.f_22276_, 25.0)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22278_, 0.5);
   }

   public boolean m_6469_(DamageSource source, float damage) {
      if ("cataclysm.emp".equals(source.m_19385_())) {
         super.m_6469_(source, 1000.0F);
         return true;
      } else {
         return super.m_6469_(source, damage);
      }
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      AnimationHandler.INSTANCE.updateAnimations(this);
      this.m_146922_(this.f_20883_);
      LivingEntity target = this.m_5448_();
      if (this.getAnimation() == WATCHER_BITE && this.getAnimationTick() == 13 && target != null && this.m_20270_(target) < 3.0F && this.m_142582_(target)) {
         float damage = (float)((int)this.m_21133_(Attributes.f_22281_));
         target.m_6469_(DamageSource.m_19370_(this), damage);
      }

      if (this.getAnimation() == WATCHER_EXTRA_SHOT && this.getAnimationTick() == 9) {
         if (!this.m_20067_()) {
            this.m_5496_((SoundEvent)ModSounds.HARBINGER_LASER.get(), 1.0F, 1.0F);
         }

         if (target != null && target.m_6084_()) {
            double d0 = this.m_20185_();
            double d1 = this.m_20186_() + (double)(this.m_20206_() * 1.0F / 2.0F);
            double d2 = this.m_20189_();
            double d3 = target.m_20185_() - d0;
            double d4 = target.m_20186_() + (double)(target.m_20206_() * 1.0F / 2.0F) - d1;
            double d5 = target.m_20189_() - d2;
            Laser_Beam_Entity laserBeam = new Laser_Beam_Entity(this.f_19853_, this);
            laserBeam.m_6686_(d3, d4, d5, 1.0F, 1.0F);
            laserBeam.setDamage((float)CMConfig.HarbingerLaserdamage);
            laserBeam.m_20343_(d0, d1, d2);
            this.f_19853_.m_7967_(laserBeam);
         }
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !(entityIn instanceof The_Watcher_Entity) && !(entityIn instanceof The_Harbinger_Entity) && !(entityIn instanceof The_Prowler_Entity)
            ? false
            : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.WATCHER_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.WATCHER_DEATH.get();
   }

   static class Shot extends SimpleAnimationGoal<The_Watcher_Entity> {
      public Shot(The_Watcher_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 7 && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (target != null && this.entity.getAnimationTick() == 11 && this.entity.m_217043_().m_188501_() * 100.0F < 60.0F) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Watcher_Entity.WATCHER_EXTRA_SHOT);
         }
      }
   }

   static class ShotPrepare extends SimpleAnimationGoal<The_Watcher_Entity> {
      public ShotPrepare(The_Watcher_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         super.m_8041_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 45) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Watcher_Entity.WATCHER_EXTRA_SHOT);
            }
         }
      }
   }

   static class WatcherMoveGoal extends Goal {
      private final The_Watcher_Entity watcher;
      private final boolean followingTargetEvenIfNotSeen;
      private Path path;
      private int delayCounter;
      protected final double moveSpeed;

      public WatcherMoveGoal(The_Watcher_Entity boss, boolean followingTargetEvenIfNotSeen, double moveSpeed) {
         this.watcher = boss;
         this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
         this.moveSpeed = moveSpeed;
         this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean m_8036_() {
         LivingEntity target = this.watcher.m_5448_();
         return target != null && target.m_6084_();
      }

      public void m_8041_() {
         this.watcher.m_21573_().m_26573_();
         LivingEntity livingentity = this.watcher.m_5448_();
         if (!EntitySelector.f_20406_.test(livingentity)) {
            this.watcher.m_6710_((LivingEntity)null);
         }

         this.watcher.m_21561_(false);
         this.watcher.m_21573_().m_26573_();
      }

      public boolean m_8045_() {
         LivingEntity target = this.watcher.m_5448_();
         if (target == null) {
            return false;
         } else if (!target.m_6084_()) {
            return false;
         } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.watcher.m_21573_().m_26571_();
         } else {
            return !this.watcher.m_21444_(target.m_20183_()) ? false : !(target instanceof Player) || !target.m_5833_() && !((Player)target).m_7500_();
         }
      }

      public void m_8056_() {
         this.watcher.m_21573_().m_26536_(this.path, this.moveSpeed);
         this.watcher.m_21561_(true);
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         LivingEntity target = this.watcher.m_5448_();
         if (target != null) {
            this.watcher.m_21563_().m_24960_(target, 30.0F, 30.0F);
            double distSq = this.watcher.m_20275_(target.m_20185_(), target.m_20191_().f_82289_, target.m_20189_());
            if (--this.delayCounter <= 0) {
               this.delayCounter = 4 + this.watcher.m_217043_().m_188503_(7);
               if (distSq > Math.pow(this.watcher.m_21051_(Attributes.f_22277_).m_22135_(), 2.0)) {
                  if (!this.watcher.m_21691_() && !this.watcher.m_21573_().m_5624_(target, 1.0)) {
                     this.delayCounter += 5;
                  }
               } else {
                  this.watcher.m_21573_().m_5624_(target, this.moveSpeed);
               }
            }

            if (target.m_6084_() && this.watcher.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
               if (this.watcher.m_20270_(target) < 1.5F) {
                  this.watcher.setAnimation(The_Watcher_Entity.WATCHER_BITE);
               } else if (this.watcher.m_217043_().m_188501_() * 100.0F < 24.0F && (double)this.watcher.m_20270_(target) >= 6.0) {
                  this.watcher.setAnimation(The_Watcher_Entity.WATCHER_SHOT);
               }
            }
         }
      }
   }
}
