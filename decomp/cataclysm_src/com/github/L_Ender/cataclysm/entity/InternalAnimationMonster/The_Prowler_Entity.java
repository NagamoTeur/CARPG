package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class The_Prowler_Entity extends Internal_Animation_Monster {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState stunAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public AnimationState laserAnimationState = new AnimationState();
   public AnimationState spinAnimationState = new AnimationState();
   public AnimationState meleeAnimationState = new AnimationState();
   public AnimationState strongAttackAnimationState = new AnimationState();
   public AnimationState pierceAnimationState = new AnimationState();
   public static final int SPIN_COOLDOWN = 80;
   public static final int LASER_COOLDOWN = 200;
   private int spin_cooldown = 0;
   private int laser_cooldown = 100;
   public static final int NATURE_HEAL_COOLDOWN = 60;
   private int timeWithoutTarget;

   public The_Prowler_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 20;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.ProwlerHealthMultiplier, CMConfig.ProwlerDamageMultiplier);
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

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new InternalMoveGoal(this, false, 1.0));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 1, 1, 0, 60, 0));
      this.f_21345_.m_25352_(1, new The_Prowler_Entity.Lasershoot(this, 0, 2, 0, 90, 20, 8.0F, 20, 100.0F));
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 4, 0, 50, 22, 4.75F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && this.entity.m_217043_().m_188501_() * 100.0F < 26.0F && The_Prowler_Entity.this.spin_cooldown <= 0;
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            The_Prowler_Entity.this.spin_cooldown = 80;
         }
      });
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 5, 0, 50, 38, 5.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && this.entity.m_217043_().m_188501_() * 100.0F < 20.0F;
         }
      });
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 6, 0, 55, 45, 6.0F) {
         @Override
         public boolean m_8036_() {
            LivingEntity target = this.entity.m_5448_();
            return super.m_8036_() && this.entity.m_217043_().m_188501_() * 100.0F < 20.0F && target != null && (double)this.entity.m_20270_(target) >= 2.75;
         }
      });
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 7, 0, 80, 38, 4.25F) {
         @Override
         public boolean m_8036_() {
            LivingEntity target = this.entity.m_5448_();
            return super.m_8036_() && this.entity.m_217043_().m_188501_() * 100.0F < 24.0F && target != null;
         }
      });
   }

   public static Builder the_prowler() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 14.0)
         .m_22268_(Attributes.f_22276_, 160.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 0.95);
   }

   public boolean m_6469_(DamageSource source, float damage) {
      if (source.f_19326_.equals("cataclysm.emp") && this.getAttackState() != 1) {
         this.setAttackState(1);
      }

      double range = this.calculateRange(source);
      return range > CMConfig.ProwlerLongRangelimit * CMConfig.ProwlerLongRangelimit ? false : super.m_6469_(source, damage);
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

   public AnimationState getAnimationState(String input) {
      if (input == "stun") {
         return this.stunAnimationState;
      } else if (input == "laser") {
         return this.laserAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else if (input == "spin") {
         return this.spinAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "melee") {
         return this.meleeAnimationState;
      } else if (input == "strong_attack") {
         return this.strongAttackAnimationState;
      } else {
         return input == "pierce" ? this.pierceAnimationState : new AnimationState();
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.stunAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.laserAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.deathAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.spinAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.meleeAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.strongAttackAnimationState.m_216982_(this.f_19797_);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.pierceAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.laserAnimationState.m_216973_();
      this.stunAnimationState.m_216973_();
      this.spinAnimationState.m_216973_();
      this.meleeAnimationState.m_216973_();
      this.strongAttackAnimationState.m_216973_();
      this.deathAnimationState.m_216973_();
      this.pierceAnimationState.m_216973_();
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(3);
   }

   @Override
   public int deathtimer() {
      return 40;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, this.getAttackState() != 1, this.f_19797_);
      } else {
         if (this.timeWithoutTarget > 0) {
            this.timeWithoutTarget--;
         }

         LivingEntity target = this.m_5448_();
         if (target != null) {
            this.timeWithoutTarget = 60;
         }

         if (this.timeWithoutTarget <= 0 && !this.m_21525_() && this.f_19797_ % 20 == 0) {
            this.m_5634_(this.m_21233_() / 10.0F);
         }
      }

      if (this.laser_cooldown > 0) {
         this.laser_cooldown--;
      }

      if (this.spin_cooldown > 0) {
         this.spin_cooldown--;
      }
   }

   public void animateWhen(AnimationState state, boolean p_252220_, int p_249486_) {
      if (p_252220_) {
         state.m_216982_(p_249486_);
      } else {
         state.m_216973_();
      }
   }

   public void m_8107_() {
      super.m_8107_();
      LivingEntity target = this.m_5448_();
      if (this.getAttackState() == 2 && this.attackTicks == 38) {
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.DEATH_LASER.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.2F, 0, 10);
      }

      if (this.getAttackState() == 1 && this.f_19853_.f_46443_) {
         for (int i = 0; i < 2; i++) {
            this.f_19853_.m_7106_(ParticleTypes.f_123755_, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), 0.0, 0.0, 0.0);
         }
      }

      if (this.getAttackState() == 4) {
         if (this.attackTicks == 23 || this.attackTicks == 32) {
            this.AreaAttack(6.0F, 6.0F, 180.0F, 1.0F);
            this.f_19853_
               .m_6263_(
                  null,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)ModSounds.PROWLER_SAW_SPIN_ATTACK.get(),
                  SoundSource.HOSTILE,
                  1.5F,
                  1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F)
               );
         }

         if (this.attackTicks == 23) {
            this.f_19853_
               .m_6263_(
                  null,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)ModSounds.PROWLER_SAW_SPIN_ATTACK.get(),
                  SoundSource.HOSTILE,
                  1.5F,
                  1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F)
               );
         }
      }

      if (this.getAttackState() == 5) {
         if (this.attackTicks == 27) {
            this.f_19853_
               .m_6263_(
                  null,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)ModSounds.PROWLER_SAW_SPIN_ATTACK.get(),
                  SoundSource.HOSTILE,
                  1.5F,
                  1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F)
               );
         }

         if (this.attackTicks == 20 || this.attackTicks == 26 || this.attackTicks == 32 || this.attackTicks == 38 || this.attackTicks == 44) {
            this.AreaAttack(5.4F, 5.5F, 110.0F, 0.5F);
         }
      }

      float f1 = (float)Math.cos(Math.toRadians((double)(this.m_146908_() + 90.0F)));
      float f2 = (float)Math.sin(Math.toRadians((double)(this.m_146908_() + 90.0F)));
      if (this.getAttackState() == 6) {
         if (this.attackTicks == 18) {
            this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
         }

         if (this.attackTicks == 17) {
            this.f_19853_
               .m_6263_(
                  null,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)ModSounds.PROWLER_SAW_SPIN_ATTACK.get(),
                  SoundSource.HOSTILE,
                  1.5F,
                  1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F)
               );
         }

         if (this.attackTicks == 25) {
            this.AreaAttack(5.5F, 5.5F, 70.0F, 1.5F);
         }
      }

      if (this.getAttackState() == 7) {
         if (target != null) {
            if (this.attackTicks == 12) {
               this.Missilelaunch(2.0F, 0.5F, target);
            }

            if (this.attackTicks == 15) {
               this.Missilelaunch(2.3F, 0.5F, target);
            }

            if (this.attackTicks == 18) {
               this.Missilelaunch(2.6F, 0.5F, target);
            }
         }

         if (this.attackTicks == 18) {
            this.f_19853_
               .m_6263_(
                  null,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)ModSounds.PROWLER_SAW_ATTACK.get(),
                  SoundSource.HOSTILE,
                  1.5F,
                  1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F)
               );
         }

         if (this.attackTicks == 25 || this.attackTicks == 32 || this.attackTicks == 40) {
            this.AreaAttack(5.5F, 5.5F, 60.0F, 0.5F);
         }

         if (this.attackTicks == 64) {
            this.AreaAttack(5.5F, 5.5F, 140.0F, 1.0F);
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage) {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, (double)height, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.f_20883_ % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
               + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
         );
         if ((
               entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && !this.m_7307_(entityHit)
            && !(entityHit instanceof The_Prowler_Entity)
            && entityHit != this) {
            entityHit.m_6469_(CMDamageTypes.causeShredderDamage(this), (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
         }
      }
   }

   private void Missilelaunch(float y, float math, LivingEntity target) {
      if (!this.m_20067_()) {
         this.f_19853_
            .m_6263_(
               null,
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               (SoundEvent)ModSounds.ROCKET_LAUNCH.get(),
               SoundSource.HOSTILE,
               1.5F,
               1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F)
            );
      }

      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      double d0 = this.m_20185_() + 0.5 * vecX + (double)(f * math);
      double d1 = this.m_20186_() + (double)y;
      double d2 = this.m_20189_() + 0.5 * vecZ + (double)(f1 * math);
      Wither_Homing_Missile_Entity laserBeam = new Wither_Homing_Missile_Entity(this.f_19853_, this, target);
      laserBeam.m_20343_(d0, d1, d2);
      this.f_19853_.m_7967_(laserBeam);
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_THE_HARBINGER) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.PROWLER_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.PROWLER_DEATH.get();
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.PROWLER_IDLE.get();
   }

   public boolean m_7301_(MobEffectInstance p_34192_) {
      return p_34192_.m_19544_() != ModEffect.EFFECTSTUN.get() && p_34192_.m_19544_() != ModEffect.EFFECTABYSSAL_CURSE.get() && super.m_7301_(p_34192_);
   }

   public boolean m_6785_(double p_21542_) {
      return false;
   }

   protected boolean m_8028_() {
      return false;
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   static class Lasershoot extends InternalAttackGoal {
      private final The_Prowler_Entity entity;
      private final int attackshot;
      private final float random;

      public Lasershoot(
         The_Prowler_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return super.m_8036_()
            && target != null
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.m_21574_().m_148306_(target)
            && this.entity.laser_cooldown <= 0;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.laser_cooldown = 200;
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         super.m_8037_();
         if (this.entity.attackTicks == this.attackshot) {
            Death_Laser_Beam_Entity DeathBeam = new Death_Laser_Beam_Entity(
               (EntityType<? extends Death_Laser_Beam_Entity>)ModEntities.DEATH_LASER_BEAM.get(),
               this.entity.f_19853_,
               this.entity,
               this.entity.m_20185_(),
               this.entity.m_20186_() + 1.8,
               this.entity.m_20189_(),
               (float)((double)(this.entity.f_20885_ + 90.0F) * Math.PI / 180.0),
               (float)((double)(-this.entity.m_146909_()) * Math.PI / 180.0),
               28,
               (float)CMConfig.DeathLaserdamage,
               (float)CMConfig.DeathLaserHpdamage
            );
            this.entity.f_19853_.m_7967_(DeathBeam);
         }

         if (this.entity.attackTicks >= this.attackshot && target != null) {
            this.entity.m_21563_().m_24950_(target.m_20185_(), target.m_20186_() + (double)(target.m_20206_() / 2.0F), target.m_20189_(), 2.0F, 90.0F);
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }
}
