package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar;

import com.github.L_Ender.cataclysm.blocks.PointedIcicleBlock;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Internal_Animation_Monster;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Axe_Blade_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.google.common.collect.UnmodifiableIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Aptrgangr_Entity extends Internal_Animation_Monster implements IHoldEntity {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState swingrightAnimationState = new AnimationState();
   public AnimationState smashAnimationState = new AnimationState();
   public AnimationState chargestartAnimationState = new AnimationState();
   public AnimationState chargeAnimationState = new AnimationState();
   public AnimationState chargeendAnimationState = new AnimationState();
   public AnimationState chargehitAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   private int earthquake_cooldown = 0;
   public static final int EARTHQUAKE_COOLDOWN = 80;
   private boolean chubu = false;
   private int charge_cooldown = 0;
   public static final int CHARGE_COOLDOWN = 160;
   public static final int NATURE_HEAL_COOLDOWN = 60;
   private int timeWithoutTarget;

   public Aptrgangr_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 35;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.AptrgangrHealthMultiplier, CMConfig.AptrgangrDamageMultiplier);
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
      this.f_21346_.m_25352_(2, new EntityAINearestTarget3D(this, Player.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, SnowGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.f_21345_.m_25352_(4, new InternalMoveGoal(this, false, 1.0));
      this.f_21345_.m_25352_(3, new InternalAttackGoal(this, 0, 1, 0, 40, 15, 4.5F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Aptrgangr_Entity.this.m_217043_().m_188501_() * 100.0F < 22.0F;
         }
      });
      this.f_21345_.m_25352_(3, new InternalAttackGoal(this, 0, 2, 0, 40, 10, 6.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Aptrgangr_Entity.this.m_217043_().m_188501_() * 100.0F < 16.0F && Aptrgangr_Entity.this.earthquake_cooldown <= 0;
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Aptrgangr_Entity.this.earthquake_cooldown = 80;
         }
      });
      this.f_21345_
         .m_25352_(
            3,
            new InternalAttackGoal(this, 0, 2, 0, 40, 10, 12.0F) {
               @Override
               public boolean m_8036_() {
                  LivingEntity target = this.entity.m_5448_();
                  return super.m_8036_()
                     && target != null
                     && Aptrgangr_Entity.this.m_217043_().m_188501_() * 100.0F < 22.0F
                     && this.entity.m_20270_(target) > 6.0F
                     && Aptrgangr_Entity.this.earthquake_cooldown <= 0;
               }

               @Override
               public void m_8041_() {
                  super.m_8041_();
                  Aptrgangr_Entity.this.earthquake_cooldown = 80;
               }
            }
         );
      this.f_21345_.m_25352_(3, new InternalAttackGoal(this, 0, 3, 4, 24, 24, 15.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Aptrgangr_Entity.this.m_217043_().m_188501_() * 100.0F < 8.0F && Aptrgangr_Entity.this.charge_cooldown <= 0;
         }
      });
      this.f_21345_
         .m_25352_(
            2,
            new InternalStateGoal(this, 4, 4, 5, 40, 0) {
               @Override
               public void m_8037_() {
                  if (this.entity.m_20096_()) {
                     Vec3 vector3d = this.entity.m_20184_();
                     float f = this.entity.m_146908_() * (float) (Math.PI / 180.0);
                     Vec3 vector3d1 = new Vec3((double)(-Mth.m_14031_(f)), this.entity.m_20184_().f_82480_, (double)Mth.m_14089_(f))
                        .m_82490_(1.0)
                        .m_82549_(vector3d.m_82490_(0.5));
                     this.entity.m_20334_(vector3d1.f_82479_, this.entity.m_20184_().f_82480_, vector3d1.f_82481_);
                  }
               }

               @Override
               public boolean m_8045_() {
                  return super.m_8045_() && !Aptrgangr_Entity.this.chubu;
               }

               @Override
               public void m_8041_() {
                  if (Aptrgangr_Entity.this.chubu) {
                     this.entity.setAttackState(6);
                     Aptrgangr_Entity.this.chubu = false;
                  } else {
                     super.m_8041_();
                  }
               }
            }
         );
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 5, 5, 0, 23, 0) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Aptrgangr_Entity.this.charge_cooldown = 160;
         }
      });
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 6, 6, 0, 18, 0) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Aptrgangr_Entity.this.charge_cooldown = 160;
         }
      });
   }

   public static Builder aptrgangr() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 18.0)
         .m_22268_(Attributes.f_22276_, 160.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   public boolean m_6469_(DamageSource source, float damage) {
      return !this.m_20197_().isEmpty() && this.getAttackState() == 4 && !source.m_19378_() ? false : super.m_6469_(source, damage);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "swing_right") {
         return this.swingrightAnimationState;
      } else if (input == "smash") {
         return this.smashAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "charge_start") {
         return this.chargestartAnimationState;
      } else if (input == "charge") {
         return this.chargeAnimationState;
      } else if (input == "charge_end") {
         return this.chargeendAnimationState;
      } else if (input == "charge_hit") {
         return this.chargehitAnimationState;
      } else {
         return input == "death" ? this.deathAnimationState : new AnimationState();
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
               this.swingrightAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.smashAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.chargestartAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.chargeAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.chargeendAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.chargehitAnimationState.m_216982_(this.f_19797_);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.deathAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.swingrightAnimationState.m_216973_();
      this.smashAnimationState.m_216973_();
      this.chargestartAnimationState.m_216973_();
      this.chargeAnimationState.m_216973_();
      this.chargeendAnimationState.m_216973_();
      this.chargehitAnimationState.m_216973_();
      this.deathAnimationState.m_216973_();
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(7);
   }

   protected void m_7472_(DamageSource p_33574_, int p_33575_, boolean p_33576_) {
      super.m_7472_(p_33574_, p_33575_, p_33576_);
      if (p_33574_.m_7639_() instanceof Creeper creeper && creeper.m_32313_()) {
         creeper.m_32314_();
         this.m_19998_((ItemLike)ModItems.APTRGANGR_HEAD.get());
      }
   }

   @Override
   public int deathtimer() {
      return 60;
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
      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, !this.isMoving() && this.getAttackState() == 0, this.f_19797_);
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

      if (this.earthquake_cooldown > 0) {
         this.earthquake_cooldown--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (!this.m_20197_().isEmpty() && ((Entity)this.m_20197_().get(0)).m_6144_() && this.getAttackState() == 4) {
         ((Entity)this.m_20197_().get(0)).m_20260_(false);
      }
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
      if (this.getAttackState() == 1 && this.attackTicks == 15) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.7F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.06F, 0, 20);
         this.AreaAttack(5.75F, 5.75F, 120.0F, 1.0F, 120, true);
      }

      if (this.getAttackState() == 2) {
         if (this.attackTicks == 11) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.7F);
         }

         if (this.attackTicks == 15) {
            this.AreaAttack(6.5F, 6.5F, 60.0F, 1.0F, 120, false);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.15F, 0, 20);
            this.m_5496_(SoundEvents.f_12600_, 1.0F, 0.8F);
            this.Makeparticle(0.6F, 5.0F, 0.0F);
            double theta = (double)this.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            double vecZ = Math.sin(theta);
            int numberOfSkulls = 5;
            float angleStep = 30.0F;

            for (int i = 0; i < numberOfSkulls; i++) {
               float angle = this.f_20883_ + (float)(i - numberOfSkulls / 2) * angleStep;
               float rad = (float)Math.toRadians((double)angle);
               double dx = -Math.sin((double)rad);
               double dz = Math.cos((double)rad);
               Axe_Blade_Entity witherskull = new Axe_Blade_Entity(this, dx, 0.0, dz, this.f_19853_, (float)CMConfig.AptrgangrAxeBladeDamage, angle);
               double spawnX = this.m_20185_() + vecX * 5.0;
               double spawnY = this.m_20227_(0.15);
               double spawnZ = this.m_20189_() + vecZ * 5.0;
               witherskull.m_6034_(spawnX, spawnY, spawnZ);
               this.f_19853_.m_7967_(witherskull);
            }
         }
      }

      if (this.getAttackState() == 4) {
         this.ChargeGrab(0.0, 0.0, 0.5, 0.1F, 0, true);
         if (this.f_19862_) {
            this.chubu = true;
            if (!this.f_19853_.f_46443_) {
               this.Icicle_Crash();
            }
         }

         if (this.f_19853_.f_46443_) {
            double x = this.m_20185_();
            double y = this.m_20186_() + (double)(this.m_20206_() / 2.0F);
            double z = this.m_20189_();
            float yaw = (float)Math.toRadians((double)(-this.m_146908_()));
            float yaw2 = (float)Math.toRadians((double)(-this.m_146908_() + 180.0F));
            float pitch = (float)Math.toRadians((double)(-this.m_146909_()));
            this.f_19853_
               .m_7106_(
                  new RingParticle.RingData(yaw, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                  x,
                  y,
                  z,
                  0.0,
                  0.0,
                  0.0
               );
            this.f_19853_
               .m_7106_(
                  new RingParticle.RingData(yaw2, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                  x,
                  y,
                  z,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      if (this.getAttackState() == 5 && this.attackTicks == 4) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.7F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.06F, 0, 20);
         this.UpperAreaAttack(6.5F, 6.5F, 60.0F, 1.0F, 120, true);
      }

      if (this.getAttackState() == 6 && this.attackTicks == 1) {
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 20);
         this.m_5496_(SoundEvents.f_12600_, 1.0F, 0.9F);
      }
   }

   private void Makeparticle(float size, float vec, float math) {
      if (this.f_19853_.f_46443_) {
         float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
         float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)(size * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(size * Mth.m_14089_(angle));
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            if (block.m_60799_() != RenderShape.INVISIBLE) {
               this.f_19853_
                  .m_7106_(
                     new BlockParticleOption(ParticleTypes.f_123794_, block),
                     this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }

         this.f_19853_
            .m_7106_(
               new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 30, 1.0F, 1.0F, 1.0F, 1.0F, 20.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
               this.m_20185_() + (double)vec * vecX + (double)(f * math),
               this.m_20186_() + 0.2F,
               this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback) {
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
            && !(entityHit instanceof Aptrgangr_Entity)
            && entityHit != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean hurt = entityHit.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            double d0 = entityHit.m_20185_() - this.m_20185_();
            double d1 = entityHit.m_20189_() - this.m_20189_();
            double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
            if (hurt && knockback) {
               entityHit.m_5997_(d0 / d2 * 2.75, 0.15, d1 / d2 * 2.75);
            }
         }
      }
   }

   private void UpperAreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback) {
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
            && !(entityHit instanceof Aptrgangr_Entity)
            && entityHit != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean hurt = entityHit.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            double d0 = entityHit.m_20185_() - this.m_20185_();
            double d1 = entityHit.m_20189_() - this.m_20189_();
            double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
            if (hurt && knockback) {
               entityHit.m_20256_(entityHit.m_20184_().m_82520_(0.0, 0.8F, 0.0));
            }
         }
      }
   }

   private void ChargeGrab(double inflateXZ, double inflateY, double range, float damage, int shieldbreakticks, boolean maledictio) {
      double yaw = Math.toRadians((double)(this.m_146908_() + 90.0F));
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.m_20191_().m_82377_(inflateXZ, inflateY, inflateXZ).m_82363_(xExpand, 0.0, zExpand);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, attackRange)) {
         if (!this.m_7307_(entity) && entity != this && this.m_20197_().isEmpty()) {
            DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, damage);
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag && !entity.m_6095_().m_204039_(ModTag.IGNIS_CANT_POKE) && entity.m_6084_()) {
               if (entity.m_6144_()) {
                  entity.m_20260_(false);
               }

               if (!this.f_19853_.f_46443_) {
                  entity.m_7998_(this, true);
               }
            }
         }
      }
   }

   private void Icicle_Crash() {
      if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
         BlockPos ceil = this.m_20183_().m_7918_(0, 5, 0);

         while (
            (!this.f_19853_.m_8055_(ceil).m_60767_().m_76333_() || this.f_19853_.m_8055_(ceil).m_60734_() == ModBlocks.POINTED_ICICLE.get())
               && ceil.m_123342_() < this.f_19853_.m_151558_()
         ) {
            ceil = ceil.m_7494_();
         }

         int i = 8;
         int j = 8;
         int k = 8;

         for (BlockPos blockpos1 : BlockPos.m_121940_(ceil.m_7918_(-8, -8, -8), ceil.m_7918_(8, 8, 8))) {
            if (this.f_19853_.m_8055_(blockpos1).m_60734_() instanceof Fallable) {
               if (this.isHangingIcicle(blockpos1)) {
                  while (this.isHangingIcicle(blockpos1.m_7494_()) && blockpos1.m_123342_() < this.f_19853_.m_151558_()) {
                     blockpos1 = blockpos1.m_7494_();
                  }

                  if (this.isHangingIcicle(blockpos1)) {
                     Vec3 vec3 = Vec3.m_82539_(blockpos1);
                     FallingBlockEntity.m_201971_(this.f_19853_, new BlockPos(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_), this.f_19853_.m_8055_(blockpos1));
                  }
               } else {
                  this.f_19853_.m_186460_(blockpos1, this.f_19853_.m_8055_(blockpos1).m_60734_(), 2);
               }
            }
         }
      }
   }

   private boolean isHangingIcicle(BlockPos pos) {
      return this.f_19853_.m_8055_(pos).m_60734_() instanceof PointedIcicleBlock
         && this.f_19853_.m_8055_(pos).m_61143_(PointedIcicleBlock.TIP_DIRECTION) == Direction.DOWN;
   }

   public void m_7023_(Vec3 travelVector) {
      super.m_7023_(travelVector);
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   public boolean canRiderInteract() {
      return true;
   }

   public void m_7332_(Entity p_20312_) {
      this.m_19956_(p_20312_, Entity::m_6034_);
   }

   public void m_19956_(Entity passenger, MoveFunction moveFunc) {
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      double px = this.m_20185_() + 0.7F * vecX;
      double pz = this.m_20189_() + 0.7F * vecZ;
      double y = this.m_20186_() + passenger.m_6049_() + 0.6;
      if (this.m_20363_(passenger)) {
         if (this.getAttackState() == 6) {
            if (this.attackTicks == 1) {
               if (passenger instanceof LivingEntity living) {
                  DamageSource damagesource = CMDamageTypes.causeMaledictioDamage(this);
                  boolean flag = living.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * 1.5));
                  if (flag) {
                     this.m_5496_(SoundEvents.f_12600_, 1.0F, 0.9F);
                  }
               }

               passenger.m_8127_();
            }
         } else if (this.getAttackState() == 5) {
            if (this.attackTicks == 1) {
               passenger.m_8127_();
            }
         } else if (this.getAttackState() != 4) {
            passenger.m_8127_();
         }

         moveFunc.m_20372_(passenger, px, y, pz);
      }
   }

   public Vec3 m_7688_(LivingEntity p_29487_) {
      Direction direction = this.m_6374_();
      if (direction.m_122434_() == Axis.Y) {
         return super.m_7688_(p_29487_);
      } else {
         int[][] aint = DismountHelper.m_38467_(direction);
         BlockPos blockpos = this.m_20183_();
         MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();
         UnmodifiableIterator var6 = p_29487_.m_7431_().iterator();

         while (var6.hasNext()) {
            Pose pose = (Pose)var6.next();
            AABB aabb = p_29487_.m_21270_(pose);

            for (int[] aint1 : aint) {
               blockpos$mutableblockpos.m_122178_(blockpos.m_123341_() + aint1[0], blockpos.m_123342_(), blockpos.m_123343_() + aint1[1]);
               double d0 = this.f_19853_.m_45573_(blockpos$mutableblockpos);
               if (DismountHelper.m_38439_(d0)) {
                  Vec3 vec3 = Vec3.m_82514_(blockpos$mutableblockpos, d0);
                  if (DismountHelper.m_38456_(this.f_19853_, p_29487_, aabb.m_82383_(vec3))) {
                     p_29487_.m_20124_(pose);
                     return vec3;
                  }
               }
            }
         }

         return super.m_7688_(p_29487_);
      }
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_MALEDICTUS) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.APTRGANGR_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.APTRGANGR_DEATH.get();
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.APTRGANGR_IDLE.get();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
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

   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }
}
