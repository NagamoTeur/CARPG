package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.cataclysm.world.data.CMWorldData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class Ignited_Berserker_Entity extends Internal_Animation_Monster {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState xslashAnimationState = new AnimationState();
   public AnimationState mixerstartAnimationState = new AnimationState();
   public AnimationState mixeridleAnimationState = new AnimationState();
   public AnimationState mixerfinishAnimationState = new AnimationState();
   public AnimationState sworddanceleftAnimationState = new AnimationState();
   public AnimationState sworddancerightAnimationState = new AnimationState();
   private int sword_dance_cooldown = 0;
   public static final int SWORD_DANCE_COOLDOWN = 40;
   private int spin_cooldown = 0;
   public static final int SPIN_COOLDOWN = 80;

   public Ignited_Berserker_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 20;
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

   protected void m_8099_() {
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(2, new InternalMoveGoal(this, false, 1.0));
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 1, 0, 50, 15, 3.6F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Ignited_Berserker_Entity.this.m_217043_().m_188501_() * 100.0F < 12.0F;
         }
      });
      this.f_21345_
         .m_25352_(
            1,
            new InternalAttackGoal(this, 0, 5, 0, 50, 50, 4.5F) {
               @Override
               public boolean m_8036_() {
                  return super.m_8036_()
                     && Ignited_Berserker_Entity.this.m_217043_().m_188501_() * 100.0F < 16.0F
                     && Ignited_Berserker_Entity.this.sword_dance_cooldown <= 0;
               }

               @Override
               public void m_8041_() {
                  super.m_8041_();
                  Ignited_Berserker_Entity.this.sword_dance_cooldown = 40;
               }
            }
         );
      this.f_21345_
         .m_25352_(
            1,
            new InternalAttackGoal(this, 0, 6, 0, 55, 55, 4.5F) {
               @Override
               public boolean m_8036_() {
                  return super.m_8036_()
                     && Ignited_Berserker_Entity.this.m_217043_().m_188501_() * 100.0F < 16.0F
                     && Ignited_Berserker_Entity.this.sword_dance_cooldown <= 0;
               }

               @Override
               public void m_8041_() {
                  super.m_8041_();
                  Ignited_Berserker_Entity.this.sword_dance_cooldown = 40;
               }
            }
         );
      this.f_21345_
         .m_25352_(
            1,
            new InternalAttackGoal(this, 0, 2, 3, 30, 25, 8.0F) {
               @Override
               public boolean m_8036_() {
                  return super.m_8036_()
                     && Ignited_Berserker_Entity.this.m_217043_().m_188501_() * 100.0F < 12.0F
                     && Ignited_Berserker_Entity.this.spin_cooldown <= 0;
               }
            }
         );
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 3, 3, 4, 90, 0, false));
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 4, 4, 0, 40, 0) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Ignited_Berserker_Entity.this.spin_cooldown = 80;
         }
      });
   }

   public static Builder ignited_berserker() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.32F)
         .m_22268_(Attributes.f_22281_, 7.5)
         .m_22268_(Attributes.f_22276_, 65.0)
         .m_22268_(Attributes.f_22284_, 8.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "x_slash") {
         return this.xslashAnimationState;
      } else if (input == "mixer_start") {
         return this.mixerstartAnimationState;
      } else if (input == "mixer_idle") {
         return this.mixeridleAnimationState;
      } else if (input == "mixer_finish") {
         return this.mixerfinishAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "sword_dance_left") {
         return this.sworddanceleftAnimationState;
      } else {
         return input == "sword_dance_right" ? this.sworddancerightAnimationState : new AnimationState();
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.xslashAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.mixerstartAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.mixeridleAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.mixerfinishAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.sworddanceleftAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.sworddancerightAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.xslashAnimationState.m_216973_();
      this.mixerstartAnimationState.m_216973_();
      this.mixeridleAnimationState.m_216973_();
      this.mixerfinishAnimationState.m_216973_();
      this.sworddanceleftAnimationState.m_216973_();
      this.sworddancerightAnimationState.m_216973_();
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(0);
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      if (ModEntities.rollSpawn(CMConfig.IgnitedBerserkerSpawnRolls, this.m_217043_(), spawnReasonIn) && worldIn instanceof ServerLevel serverLevel) {
         CMWorldData data = CMWorldData.get(serverLevel, Level.f_46429_);
         return data != null && data.isIgnisDefeatedOnce();
      } else {
         return false;
      }
   }

   @Override
   public int deathtimer() {
      return 20;
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
      if (!this.m_20096_() && this.m_20184_().f_82480_ < 0.0) {
         this.m_20256_(this.m_20184_().m_82542_(1.0, 0.6, 1.0));
      }

      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, !this.isMoving() && this.getAttackState() == 0, this.f_19797_);
      }

      if (this.sword_dance_cooldown > 0) {
         this.sword_dance_cooldown--;
      }

      if (this.spin_cooldown > 0) {
         this.spin_cooldown--;
      }

      float dis = this.m_20205_() * 0.75F;
      this.repelEntities(dis, this.m_20206_(), dis, dis);
   }

   public boolean isMoving() {
      return this.f_20924_ > 1.0E-5F;
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
      float dis = this.m_20205_();
      float f1 = (float)Math.cos(Math.toRadians((double)(this.m_146908_() + 90.0F)));
      float f2 = (float)Math.sin(Math.toRadians((double)(this.m_146908_() + 90.0F)));
      if (this.getAttackState() == 1) {
         if (this.attackTicks == 17) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 4.35F, dis * 4.35F, 45.0F, 1.25F, 60);
         }

         if (this.attackTicks == 35) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 3.6F, dis * 3.6F, 200.0F, 1.25F, 0);
         }
      }

      if (this.getAttackState() == 3 && this.f_19797_ % 4 == 0) {
         for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(1.0))) {
            if (!this.m_7307_(entity) && !(entity instanceof Ignited_Berserker_Entity) && entity != this) {
               boolean flag = entity.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_));
               if (flag) {
                  double d0 = entity.m_20185_() - this.m_20185_();
                  double d1 = entity.m_20189_() - this.m_20189_();
                  double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                  entity.m_5997_(d0 / d2 * 0.5, 0.1, d1 / d2 * 0.5);
               }
            }
         }
      }

      if (this.getAttackState() == 5) {
         if (this.attackTicks == 11 || this.attackTicks == 18 || this.attackTicks == 23 || this.attackTicks == 28) {
            this.m_5997_((double)f1 * 0.45, 0.0, (double)f2 * 0.45);
         }

         if (this.attackTicks == 13) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 4.5F, dis * 4.5F, 50.0F, 1.0F, 0);
         }

         if (this.attackTicks == 20) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 3.5F, dis * 3.5F, 60.0F, 1.0F, 0);
         }

         if (this.attackTicks == 25) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 4.5F, dis * 4.5F, 60.0F, 1.0F, 0);
         }

         if (this.attackTicks == 30) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 3.25F, dis * 3.25F, 60.0F, 1.0F, 0);
         }
      }

      if (this.getAttackState() == 6) {
         if (this.attackTicks == 15 || this.attackTicks == 123 || this.attackTicks == 26 || this.attackTicks == 33) {
            this.m_5997_((double)f1 * 0.45, 0.0, (double)f2 * 0.45);
         }

         if (this.attackTicks == 17) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 4.5F, dis * 4.5F, 40.0F, 1.0F, 0);
         }

         if (this.attackTicks == 25) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 3.25F, dis * 3.25F, 55.0F, 1.0F, 0);
         }

         if (this.attackTicks == 28) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 5.0F, dis * 5.0F, 60.0F, 1.0F, 0);
         }

         if (this.attackTicks == 35) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.2F);
            this.AreaAttack(dis * 3.5F, dis * 3.5F, 40.0F, 1.0F, 0);
         }
      }
   }

   private void AreaSwordAttack(float range, float height, float degree, float arc, float damage) {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, (double)height, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - (double)degree) % 360.0
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
            && entityHit != this) {
            boolean flag = entityHit.m_6469_(CMDamageTypes.causeSwordDanceDamage(this), (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (flag) {
               MobEffectInstance effectinstance1 = entityHit.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               int i = 1;
               if (effectinstance1 != null) {
                  i += effectinstance1.m_19564_();
                  entityHit.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               } else {
                  i--;
               }

               i = Mth.m_14045_(i, 0, 1);
               MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 120, i, false, true, true);
               entityHit.m_7292_(effectinstance);
               this.m_5634_((float)(3 * (i + 1)));
               entityHit.f_19802_ = 0;
            }
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks) {
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
            && !(entityHit instanceof Ignited_Berserker_Entity)
            && entityHit != this) {
            DamageSource damagesource = CMDamageTypes.causeSwordDanceDamage(this);
            boolean flag = entityHit.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               MobEffectInstance effectinstance1 = entityHit.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               int i = 1;
               if (effectinstance1 != null) {
                  i += effectinstance1.m_19564_();
                  entityHit.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               } else {
                  i--;
               }

               i = Mth.m_14045_(i, 0, 1);
               MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 100, i, false, true, true);
               entityHit.m_7292_(effectinstance);
               this.m_5634_(2.0F * (float)(i + 1));
               entityHit.f_19802_ = 0;
            }
         }
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_IGNIS) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.REVENANT_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.REVENANT_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.REVENANT_DEATH.get();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }
}
