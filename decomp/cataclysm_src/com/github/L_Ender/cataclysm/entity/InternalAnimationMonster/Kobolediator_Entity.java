package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

public class Kobolediator_Entity extends Internal_Animation_Monster {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState sleepAnimationState = new AnimationState();
   public AnimationState awakeAnimationState = new AnimationState();
   public AnimationState sword1AnimationState = new AnimationState();
   public AnimationState sword2AnimationState = new AnimationState();
   public AnimationState chargeprepareAnimationState = new AnimationState();
   public AnimationState chargeAnimationState = new AnimationState();
   public AnimationState chargeendAnimationState = new AnimationState();
   public AnimationState blockAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   private int earthquake_cooldown = 0;
   public static final int EARTHQUAKE_COOLDOWN = 80;
   private int charge_cooldown = 0;
   public static final int CHARGE_COOLDOWN = 160;

   public Kobolediator_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 35;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.KobolediatorHealthMultiplier, CMConfig.KobolediatorDamageMultiplier);
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
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 3, 0, 50, 15, 12.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Kobolediator_Entity.this.m_217043_().m_188501_() * 100.0F < 16.0F && Kobolediator_Entity.this.earthquake_cooldown <= 0;
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Kobolediator_Entity.this.earthquake_cooldown = 80;
         }
      });
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 4, 0, 100, 64, 8.0F));
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 5, 6, 40, 30, 15.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Kobolediator_Entity.this.m_217043_().m_188501_() * 100.0F < 9.0F && Kobolediator_Entity.this.charge_cooldown <= 0;
         }
      });
      this.f_21345_
         .m_25352_(
            1,
            new InternalStateGoal(this, 6, 6, 7, 30, 0) {
               @Override
               public void m_8037_() {
                  if (this.entity.m_20096_()) {
                     Vec3 vector3d = this.entity.m_20184_();
                     float f = this.entity.m_146908_() * (float) (Math.PI / 180.0);
                     Vec3 vector3d1 = new Vec3((double)(-Mth.m_14031_(f)), this.entity.m_20184_().f_82480_, (double)Mth.m_14089_(f))
                        .m_82490_(0.7)
                        .m_82549_(vector3d.m_82490_(0.5));
                     this.entity.m_20334_(vector3d1.f_82479_, this.entity.m_20184_().f_82480_, vector3d1.f_82481_);
                  }
               }
            }
         );
      this.f_21345_.m_25352_(0, new InternalAttackGoal(this, 6, 7, 0, 40, 40, 5.0F) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Kobolediator_Entity.this.charge_cooldown = 160;
         }
      });
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 7, 7, 0, 40, 40) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Kobolediator_Entity.this.charge_cooldown = 160;
         }
      });
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 1, 1, 0, 0, 0) {
         @Override
         public void m_8037_() {
            this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         }
      });
      this.f_21345_.m_25352_(0, new InternalAttackGoal(this, 1, 2, 0, 70, 0, 18.0F));
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 9, 9, 0, 18, 0, false));
   }

   public static Builder kobolediator() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 14.0)
         .m_22268_(Attributes.f_22276_, 180.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_6469_(DamageSource source, float damage) {
      Entity entity = source.m_7640_();
      if (entity instanceof Poison_Dart_Entity) {
         return false;
      } else if (this.canBlockDamageSource(source)) {
         if (entity instanceof AbstractArrow) {
            float f = 170.0F + this.f_19796_.m_188501_() * 80.0F;
            entity.m_20256_(entity.m_20184_().m_82490_(1.5));
            entity.m_146922_(entity.m_146908_() + f);
            entity.f_19864_ = true;
         }

         if (this.getAttackState() == 0) {
            this.setAttackState(9);
            this.m_5496_(SoundEvents.f_11668_, 1.0F, 2.0F);
         }

         return false;
      } else {
         return this.isSleep() && !source.m_19378_() ? false : super.m_6469_(source, damage);
      }
   }

   private boolean canBlockDamageSource(DamageSource damageSourceIn) {
      boolean flag = false;
      if (!this.m_21525_() && damageSourceIn.m_19360_() && !flag && (this.getAttackState() == 0 || this.getAttackState() == 9)) {
         Vec3 vector3d2 = damageSourceIn.m_7270_();
         if (vector3d2 != null) {
            Vec3 vector3d = this.m_20252_(1.0F);
            Vec3 vector3d1 = vector3d2.m_82505_(this.m_20182_()).m_82541_();
            vector3d1 = new Vec3(vector3d1.f_82479_, 0.0, vector3d1.f_82481_);
            return vector3d1.m_82526_(vector3d) < 0.0;
         }
      }

      return false;
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "sleep") {
         return this.sleepAnimationState;
      } else if (input == "awake") {
         return this.awakeAnimationState;
      } else if (input == "sword1") {
         return this.sword1AnimationState;
      } else if (input == "sword2") {
         return this.sword2AnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "charge_prepare") {
         return this.chargeprepareAnimationState;
      } else if (input == "charge") {
         return this.chargeAnimationState;
      } else if (input == "charge_end") {
         return this.chargeendAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else {
         return input == "block" ? this.blockAnimationState : new AnimationState();
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
   }

   public boolean isSleep() {
      return this.getAttackState() == 1 || this.getAttackState() == 2;
   }

   public void setSleep(boolean sleep) {
      this.setAttackState(sleep ? 1 : 0);
   }

   public boolean m_142066_() {
      return !this.isSleep() && super.m_142066_();
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_
   ) {
      this.setSleep(true);
      return super.m_6518_(p_29678_, p_29679_, p_29680_, p_29681_, p_29682_);
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.sleepAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.awakeAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.sword1AnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.sword2AnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.chargeprepareAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.chargeAnimationState.m_216982_(this.f_19797_);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.chargeendAnimationState.m_216982_(this.f_19797_);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.deathAnimationState.m_216982_(this.f_19797_);
               break;
            case 9:
               this.stopAllAnimationStates();
               this.blockAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.sleepAnimationState.m_216973_();
      this.awakeAnimationState.m_216973_();
      this.sword1AnimationState.m_216973_();
      this.sword2AnimationState.m_216973_();
      this.chargeprepareAnimationState.m_216973_();
      this.chargeAnimationState.m_216973_();
      this.blockAnimationState.m_216973_();
      this.chargeendAnimationState.m_216973_();
      this.deathAnimationState.m_216973_();
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(8);
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("is_Sleep", this.isSleep());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setSleep(compound.m_128471_("is_Sleep"));
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, !this.isMoving() && this.getAttackState() == 0, this.f_19797_);
      }

      if (this.earthquake_cooldown > 0) {
         this.earthquake_cooldown--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
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
      if (this.getAttackState() == 2 && this.attackTicks == 12) {
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.06F, 0, 20);
      }

      if (this.getAttackState() == 3) {
         if (this.attackTicks == 20) {
            this.AreaAttack(10.0F, 6.0F, 60.0F, 1.0F, 120);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 20);
            this.Makeparticle(0.5F, 9.0F, 1.8F);
         }

         for (int l = 19; l <= 28; l += 2) {
            if (this.attackTicks == l) {
               int d = l - 17;
               int d2 = l - 16;
               float ds = (float)((d + d2) / 2);
               this.StompDamage(0.25F, d, 5, 1.05F, 2.0F, -0.2F, 0, 1.0F);
               this.StompDamage(0.25F, d2, 5, 1.05F, 2.0F, -0.2F, 0, 1.0F);
               this.Stompsound(ds, -0.2F);
            }
         }
      }

      if (this.getAttackState() == 4) {
         if (this.attackTicks == 18) {
            this.AreaAttack(9.0F, 6.0F, 270.0F, 1.0F, 0);
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 10);
         }

         if (this.attackTicks == 36) {
            this.AreaAttack(9.0F, 6.0F, 270.0F, 1.0F, 0);
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 10);
         }

         if (this.attackTicks == 65) {
            this.AreaAttack(10.0F, 6.0F, 45.0F, 1.25F, 120);
            this.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 20);
            this.Makeparticle(0.5F, 9.0F, 1.2F);
         }
      }

      if (this.getAttackState() == 6) {
         if (!this.f_19853_.f_46443_) {
            if (CMConfig.KobolediatorBlockBreaking) {
               this.ChargeBlockBreaking();
            } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
               this.ChargeBlockBreaking();
            }
         }

         if (this.f_19797_ % 4 == 0) {
            for (LivingEntity Lentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(0.5))) {
               if (!this.m_7307_(Lentity) && !(Lentity instanceof Kobolediator_Entity) && Lentity != this) {
                  boolean flag = Lentity.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_) * 0.4F);
                  if (flag && Lentity.m_20096_()) {
                     double d0 = Lentity.m_20185_() - this.m_20185_();
                     double d1 = Lentity.m_20189_() - this.m_20189_();
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                     float f = 1.5F;
                     Lentity.m_5997_(d0 / d2 * (double)f, 0.5, d1 / d2 * (double)f);
                  }
               }
            }
         }
      }

      if (this.getAttackState() == 7 && this.attackTicks == 5) {
         this.AreaAttack(9.0F, 6.0F, 200.0F, 1.25F, 120);
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 10);
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

   private void ChargeBlockBreaking() {
      boolean flag = false;
      AABB aabb = this.m_20191_().m_82377_(0.5, 0.2, 0.5);

      for (BlockPos blockpos : BlockPos.m_121976_(
         Mth.m_14107_(aabb.f_82288_),
         Mth.m_14107_(this.m_20186_()),
         Mth.m_14107_(aabb.f_82290_),
         Mth.m_14107_(aabb.f_82291_),
         Mth.m_14107_(aabb.f_82292_),
         Mth.m_14107_(aabb.f_82293_)
      )) {
         BlockState blockstate = this.f_19853_.m_8055_(blockpos);
         if (!blockstate.m_60795_()
            && blockstate.canEntityDestroy(this.f_19853_, blockpos, this)
            && !blockstate.m_204336_(ModTag.REMNANT_IMMUNE)
            && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
            if (this.f_19796_.m_188503_(6) == 0 && !blockstate.m_155947_()) {
               Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                  this.f_19853_, (double)blockpos.m_123341_() + 0.5, (double)blockpos.m_123342_() + 0.5, (double)blockpos.m_123343_() + 0.5, blockstate, 20
               );
               flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
               fallingBlockEntity.m_20256_(
                  fallingBlockEntity.m_20184_()
                     .m_82549_(
                        this.m_20182_()
                           .m_82546_(fallingBlockEntity.m_20182_())
                           .m_82542_(
                              (-1.2 + this.f_19796_.m_188500_()) / 3.0, 0.2 + this.m_217043_().m_188583_() * 0.15, (-1.2 + this.f_19796_.m_188500_()) / 3.0
                           )
                     )
               );
               this.f_19853_.m_7967_(fallingBlockEntity);
            } else {
               flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
            }
         }
      }
   }

   private void Stompsound(float distance, float math) {
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      this.f_19853_
         .m_6263_(
            (Player)null,
            this.m_20185_() + (double)distance * vecX + (double)(f * math),
            this.m_20186_(),
            this.m_20189_() + (double)distance * vecZ + (double)(f1 * math),
            (SoundEvent)ModSounds.REMNANT_STOMP.get(),
            this.m_5720_(),
            0.6F,
            1.0F
         );
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
            && !(entityHit instanceof Kobolediator_Entity)
            && entityHit != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            entityHit.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }
         }
      }
   }

   private void StompDamage(float spreadarc, int distance, int height, float mxy, float vec, float math, int shieldbreakticks, float damage) {
      double perpFacing = (double)this.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int hitY = Mth.m_14107_(this.m_20191_().f_82289_ - 0.5);
      double spread = Math.PI * (double)spreadarc;
      int arcLen = Mth.m_14165_((double)distance * spread);
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));

      for (int i = 0; i < arcLen; i++) {
         double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = this.m_20185_() + vx * (double)distance + (double)vec * Math.cos((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0) + (double)(f * math);
         double pz = this.m_20189_() + vz * (double)distance + (double)vec * Math.sin((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0 + (double)(f1 * math));
         float factor = 1.0F - (float)distance / 12.0F;
         int hitX = Mth.m_14107_(px);
         int hitZ = Mth.m_14107_(pz);
         BlockPos pos = new BlockPos(hitX, hitY + height, hitZ);
         BlockState block = this.f_19853_.m_8055_(pos);
         int maxDepth = 30;

         for (int depthCount = 0; depthCount < maxDepth && block.m_60799_() != RenderShape.MODEL; depthCount++) {
            pos = pos.m_7495_();
            block = this.f_19853_.m_8055_(pos);
         }

         if (block.m_60799_() != RenderShape.MODEL) {
            block = Blocks.f_50016_.m_49966_();
         }

         this.spawnBlocks(hitX, hitY + height, hitZ, (int)(this.m_20186_() - (double)height), block, px, pz, mxy, vx, vz, factor, shieldbreakticks, damage);
      }
   }

   private void spawnBlocks(
      int hitX,
      int hitY,
      int hitZ,
      int lowestYCheck,
      BlockState blockState,
      double px,
      double pz,
      float mxy,
      double vx,
      double vz,
      float factor,
      int shieldbreakticks,
      float damage
   ) {
      BlockPos blockpos = new BlockPos(hitX, hitY, hitZ);
      BlockState block = this.f_19853_.m_8055_(blockpos);
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = this.f_19853_.m_8055_(blockpos1);
         if (blockstate.m_60783_(this.f_19853_, blockpos1, Direction.UP)) {
            if (!this.f_19853_.m_46859_(blockpos)) {
               BlockState blockstate1 = this.f_19853_.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(this.f_19853_, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14143_((float)lowestYCheck) - 1);

      Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
         this.f_19853_, (double)hitX + 0.5, (double)blockpos.m_123342_() + d0 + 0.5, (double)hitZ + 0.5, blockState, 10
      );
      fallingBlockEntity.m_5997_(0.0, 0.2 + this.m_217043_().m_188583_() * 0.04, 0.0);
      this.f_19853_.m_7967_(fallingBlockEntity);
      AABB selection = new AABB(
         px - 0.5, (double)blockpos.m_123342_() + d0 - 1.0, pz - 0.5, px + 0.5, (double)blockpos.m_123342_() + d0 + (double)mxy, pz + 0.5
      );

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, selection)) {
         if (!this.m_7307_(entity) && !(entity instanceof Kobolediator_Entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag) {
               double magnitude = -4.0;
               double x = vx * (double)(1.0F - factor) * magnitude;
               double y = 0.0;
               if (entity.m_20096_()) {
                  y += 0.15;
               }

               double z = vz * (double)(1.0F - factor) * magnitude;
               entity.m_20256_(entity.m_20184_().m_82520_(x, y, z));
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
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_ANCIENT_REMNANT) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.KOBOLEDIATOR_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.KOBOLEDIATOR_DEATH.get();
   }

   protected SoundEvent m_7515_() {
      return this.isSleep() ? super.m_7515_() : (SoundEvent)ModSounds.KOBOLEDIATOR_AMBIENT.get();
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
