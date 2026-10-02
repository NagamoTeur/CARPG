package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.AI.MobAIFindWater;
import com.github.L_Ender.cataclysm.entity.AI.MobAILeaveWater;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.ISemiAquatic;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.GroundPathNavigatorWide;
import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.mojang.serialization.Codec;
import java.util.EnumSet;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;

public class Coralssus_Entity extends Internal_Animation_Monster implements VariantHolder<Coralssus_Entity.Variant>, ISemiAquatic {
   private static final EntityDataAccessor<Integer> MOISTNESS = SynchedEntityData.m_135353_(Coralssus_Entity.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.m_135353_(Coralssus_Entity.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Boolean> RIGHT = SynchedEntityData.m_135353_(Coralssus_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> CORALSSUS_SWIM = SynchedEntityData.m_135353_(Coralssus_Entity.class, EntityDataSerializers.f_135035_);
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState angryAnimationState = new AnimationState();
   public AnimationState nantaAnimationState = new AnimationState();
   public AnimationState rightfistAnimationState = new AnimationState();
   public AnimationState leftfistAnimationState = new AnimationState();
   public AnimationState jumpingprepareAnimationState = new AnimationState();
   public AnimationState jumpingAnimationState = new AnimationState();
   public AnimationState jumpingendAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   private int nanta_cooldown = 0;
   public static final int NANTA_COOLDOWN = 160;
   private int moistureAttackTime = 0;
   private int jump_cooldown = 0;
   public static final int JUMP_COOLDOWN = 160;
   private boolean isLandNavigator;
   boolean searchingForLand;

   public Coralssus_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 35;
      this.f_21342_ = new Coralssus_Entity.CoralssusMoveControl(this, 2.5F);
      this.switchNavigator(false);
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
   }

   public float getStepHeight() {
      return 1.25F;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(6, new Coralssus_Entity.CoralssusswimUpGoal(this, 1.0, this.f_19853_.m_5736_()));
      this.f_21345_.m_25352_(4, new MobAIFindWater(this, 1.0));
      this.f_21345_.m_25352_(4, new MobAILeaveWater(this));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(2, new InternalMoveGoal(this, false, 1.0));
      this.f_21345_
         .m_25352_(
            1,
            new InternalAttackGoal(this, 0, 1, 2, 40, 17, 6.0F) {
               @Override
               public boolean m_8036_() {
                  return super.m_8036_()
                     && Coralssus_Entity.this.m_217043_().m_188501_() * 100.0F < 16.0F
                     && Coralssus_Entity.this.nanta_cooldown <= 0
                     && !Coralssus_Entity.this.getSwim();
               }

               @Override
               public void m_8056_() {
                  super.m_8056_();
                  Coralssus_Entity.this.m_5496_((SoundEvent)ModSounds.CORALSSUS_ROAR.get(), 1.0F, 1.0F + Coralssus_Entity.this.m_217043_().m_188501_() * 0.1F);
               }
            }
         );
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 2, 2, 0, 60, 60) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Coralssus_Entity.this.nanta_cooldown = 160;
         }
      });
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 3, 0, 30, 8, 4.5F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Coralssus_Entity.this.getIsRight();
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Coralssus_Entity.this.setRight(!Coralssus_Entity.this.getIsRight());
         }
      });
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 4, 0, 30, 8, 4.5F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && !Coralssus_Entity.this.getIsRight();
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Coralssus_Entity.this.setRight(!Coralssus_Entity.this.getIsRight());
         }
      });
      this.f_21345_.m_25352_(1, new Coralssus_Entity.Coralssus_JumpPrepareAttackGoal(this, 0, 5, 6, 20, 10, 6.5F, 10.0F, 16.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && !Coralssus_Entity.this.getSwim();
         }
      });
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 6, 6, 7, 100, 100));
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 7, 7, 0, 20, 0) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Coralssus_Entity.this.jump_cooldown = 160;
         }
      });
   }

   public static Builder coralssus() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 10.0)
         .m_22268_(Attributes.f_22276_, 160.0)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "nanta") {
         return this.nantaAnimationState;
      } else if (input == "angry") {
         return this.angryAnimationState;
      } else if (input == "right_fist") {
         return this.rightfistAnimationState;
      } else if (input == "left_fist") {
         return this.leftfistAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "jumping_prepare") {
         return this.jumpingprepareAnimationState;
      } else if (input == "jumping") {
         return this.jumpingAnimationState;
      } else if (input == "jumping_end") {
         return this.jumpingendAnimationState;
      } else {
         return input == "death" ? this.deathAnimationState : new AnimationState();
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(MOISTNESS, 40000);
      this.f_19804_.m_135372_(VARIANT, Coralssus_Entity.Variant.FIRE.id);
      this.f_19804_.m_135372_(RIGHT, false);
      this.f_19804_.m_135372_(CORALSSUS_SWIM, false);
   }

   public boolean isSponge() {
      String s = ChatFormatting.m_126649_(this.m_7755_().getString());
      return s != null && s.toLowerCase().contains("squarepants") && this.getVariant() == Coralssus_Entity.Variant.HORN;
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_
   ) {
      this.setVariant(Coralssus_Entity.Variant.byId(this.f_19796_.m_188503_(3)));
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
               this.angryAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.nantaAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.rightfistAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.leftfistAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.jumpingprepareAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.jumpingAnimationState.m_216982_(this.f_19797_);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.jumpingendAnimationState.m_216982_(this.f_19797_);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.deathAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.angryAnimationState.m_216973_();
      this.nantaAnimationState.m_216973_();
      this.rightfistAnimationState.m_216973_();
      this.leftfistAnimationState.m_216973_();
      this.jumpingprepareAnimationState.m_216973_();
      this.jumpingAnimationState.m_216973_();
      this.jumpingendAnimationState.m_216973_();
      this.deathAnimationState.m_216973_();
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(8);
   }

   @Override
   public int deathtimer() {
      return 30;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant().id);
      compound.m_128405_("Moisture", this.getMoistness());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(Coralssus_Entity.Variant.byId(compound.m_128451_("Variant")));
      this.setMoistness(compound.m_128451_("Moisture"));
   }

   public int getMoistness() {
      return (Integer)this.f_19804_.m_135370_(MOISTNESS);
   }

   public void setMoistness(int p_211137_1_) {
      this.f_19804_.m_135381_(MOISTNESS, p_211137_1_);
   }

   public Coralssus_Entity.Variant getVariant() {
      return Coralssus_Entity.Variant.byId((Integer)this.f_19804_.m_135370_(VARIANT));
   }

   public void setVariant(Coralssus_Entity.Variant p_262578_) {
      this.f_19804_.m_135381_(VARIANT, p_262578_.id);
   }

   public void setRight(boolean right) {
      this.f_19804_.m_135381_(RIGHT, right);
   }

   public boolean getIsRight() {
      return (Boolean)this.f_19804_.m_135370_(RIGHT);
   }

   boolean wantsToSwim() {
      if (this.searchingForLand) {
         return true;
      } else {
         LivingEntity livingentity = this.m_5448_();
         return livingentity != null && livingentity.m_20069_();
      }
   }

   public void m_7023_(Vec3 p_32394_) {
      if (this.m_6142_() && this.m_20069_() && this.wantsToSwim()) {
         this.m_19920_(0.01F, p_32394_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9));
      } else {
         super.m_7023_(p_32394_);
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.m_20069_() && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!this.m_20069_() && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (this.m_21525_()) {
         this.m_20301_(this.m_6062_());
      } else if (this.m_20071_()) {
         this.setMoistness(6000);
      } else {
         int dry = this.f_19853_.m_46461_() ? 2 : 1;
         this.setMoistness(this.getMoistness() - dry);
         if (this.getMoistness() <= 0 && this.moistureAttackTime-- <= 0) {
            this.m_6469_(DamageSource.f_19324_, this.f_19796_.m_188503_(2) == 0 ? 1.0F : 0.0F);
            this.moistureAttackTime = 20;
         }
      }

      boolean flag1 = this.canInFluidType(this.getEyeInFluidType());
      if (flag1) {
         if (this.f_19853_.m_45756_(this, this.m_20191_()) && !this.getSwim()) {
            this.setSwim(true);
         }
      } else if (this.f_19853_.m_45756_(this, this.m_20191_()) && this.getSwim()) {
         this.setSwim(false);
      }

      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, !this.isMoving() && this.getAttackState() == 0, this.f_19797_);
      }

      if (this.nanta_cooldown > 0) {
         this.nanta_cooldown--;
      }

      if (this.jump_cooldown > 0) {
         this.jump_cooldown--;
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
      float f1 = (float)Math.cos(Math.toRadians((double)(this.m_146908_() + 90.0F)));
      float f2 = (float)Math.sin(Math.toRadians((double)(this.m_146908_() + 90.0F)));
      if (this.getAttackState() == 2) {
         if (this.attackTicks == 6 || this.attackTicks == 16 || this.attackTicks == 26 || this.attackTicks == 36 || this.attackTicks == 46) {
            this.m_5997_((double)f1 * 0.5, 0.0, (double)f2 * 0.5);
         }

         if (this.attackTicks == 5 || this.attackTicks == 30) {
            this.EarthQuake(3.5F, 2, 60);
            this.Makeparticle(0.5F, 3.15F, 0.2F);
         }

         if (this.attackTicks == 17) {
            this.EarthQuake(3.5F, 2, 60);
            this.Makeparticle(0.5F, 3.15F, -0.2F);
         }

         if (this.attackTicks == 42) {
            this.EarthQuake(3.5F, 2, 60);
            this.Makeparticle(0.5F, 3.15F, -0.2F);
            this.BlockBreaking();
         }
      }

      if (this.getAttackState() == 3 && this.attackTicks == 12) {
         this.EarthQuake(3.5F, 2, 0);
         this.Makeparticle(0.5F, 2.8F, 0.2F);
      }

      if (this.getAttackState() == 4 && this.attackTicks == 12) {
         this.EarthQuake(3.5F, 2, 0);
         this.Makeparticle(0.5F, 2.8F, -0.2F);
      }

      if (this.getAttackState() == 6 && (this.m_20096_() || !this.m_146900_().m_60819_().m_76178_())) {
         this.setAttackState(7);
      }

      if (this.getAttackState() == 7 && this.attackTicks == 3) {
         this.EarthQuake(4.5F, 5, 120);
         this.Makeparticle(0.5F, 3.1F, -0.4F);
         this.Makeparticle(0.5F, 3.1F, 0.4F);
      }
   }

   private void EarthQuake(float grow, int damage, int shieldbreakticks) {
      ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 10.0F, 0.15F, 0, 20);
      this.m_5496_(SoundEvents.f_11913_, 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_((double)grow))) {
         if (!this.m_7307_(entity) && !(entity instanceof Coralssus_Entity) && entity != this) {
            this.launch(entity, true);
            DamageSource damagesource = DamageSource.m_19370_(this);
            entity.m_6469_(damagesource, (float)this.m_21133_(Attributes.f_22281_) + (float)this.f_19796_.m_188503_(damage));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }
         }
      }
   }

   private void BlockBreaking() {
      boolean flag = false;
      if (!this.f_19853_.f_46443_ && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
         AABB aabb = this.m_20191_().m_82377_(1.5, 1.5, 1.5);

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
               && blockstate.m_204336_(ModTag.CORALSSUS_BREAK)
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
   }

   private void launch(LivingEntity e, boolean huge) {
      double d0 = e.m_20185_() - this.m_20185_();
      double d1 = e.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      float f = huge ? 2.0F : 0.5F;
      e.m_5997_(d0 / d2 * (double)f, huge ? 0.5 : 0.2F, d1 / d2 * (double)f);
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

   public void m_7332_(Entity p_289537_) {
      super.m_7332_(p_289537_);
      float f = 0.5F;
      Vec3 vec3 = new Vec3(0.0, 0.0, (double)f).m_82524_(-this.f_20883_ * (float) (Math.PI / 180.0));
      p_289537_.m_6034_(this.m_20185_() + vec3.f_82479_, this.m_20227_(0.75) + p_289537_.m_6049_() + 0.0, this.m_20189_() + vec3.f_82481_);
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
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

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.CORALSSUS_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.CORALSSUS_DEATH.get();
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.CORALSSUS_AMBIENT.get();
   }

   private boolean canInFluidType(FluidType type) {
      ForgeMod.WATER_TYPE.get();
      return type.canSwim(this.self());
   }

   public boolean m_6067_() {
      return this.getSwim();
   }

   public void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21344_ = new GroundPathNavigatorWide(this, this.f_19853_);
         this.isLandNavigator = true;
      } else {
         this.f_21344_ = new SemiAquaticPathNavigator(this, this.f_19853_);
         this.isLandNavigator = false;
      }
   }

   public boolean getSwim() {
      return (Boolean)this.f_19804_.m_135370_(CORALSSUS_SWIM);
   }

   public void setSwim(boolean swim) {
      this.f_19804_.m_135381_(CORALSSUS_SWIM, swim);
   }

   public boolean m_6063_() {
      return !this.m_6069_();
   }

   public boolean m_6040_() {
      return true;
   }

   @Override
   public boolean shouldEnterWater() {
      return this.getMoistness() < 300;
   }

   @Override
   public boolean shouldLeaveWater() {
      return this.m_5448_() != null && !this.m_5448_().m_20069_();
   }

   @Override
   public boolean shouldStopMoving() {
      return false;
   }

   @Override
   public int getWaterSearchRange() {
      return 32;
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
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

   protected boolean closeToNextPos() {
      Path path = this.m_21573_().m_26570_();
      if (path != null) {
         BlockPos blockpos = path.m_77406_();
         if (blockpos != null) {
            double d0 = this.m_20275_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_());
            if (d0 < 4.0) {
               return true;
            }
         }
      }

      return false;
   }

   public void setSearchingForLand(boolean p_32399_) {
      this.searchingForLand = p_32399_;
   }

   static class CoralssusMoveControl extends MoveControl {
      private final Coralssus_Entity drowned;
      private final float speedMulti;

      public CoralssusMoveControl(Coralssus_Entity p_32433_, float speedMulti) {
         super(p_32433_);
         this.drowned = p_32433_;
         this.speedMulti = speedMulti;
      }

      public void m_8126_() {
         LivingEntity livingentity = this.drowned.m_5448_();
         if (this.drowned.wantsToSwim() && this.drowned.m_20069_()) {
            if (livingentity != null && livingentity.m_20186_() > this.drowned.m_20186_() || this.drowned.searchingForLand) {
               this.drowned.m_20256_(this.drowned.m_20184_().m_82520_(0.0, 0.002, 0.0));
            }

            if (this.f_24981_ != Operation.MOVE_TO || this.drowned.m_21573_().m_26571_()) {
               this.drowned.m_7910_(0.0F);
               return;
            }

            double d0 = this.f_24975_ - this.drowned.m_20185_();
            double d1 = this.f_24976_ - this.drowned.m_20186_();
            double d2 = this.f_24977_ - this.drowned.m_20189_();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 /= d3;
            float f = (float)(Mth.m_14136_(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
            this.drowned.m_146922_(this.m_24991_(this.drowned.m_146908_(), f, 90.0F));
            this.drowned.f_20883_ = this.drowned.m_146908_();
            float f1 = (float)(this.f_24978_ * (double)this.speedMulti * this.drowned.m_21133_(Attributes.f_22279_));
            float f2 = Mth.m_14179_(0.125F, this.drowned.m_6113_(), f1);
            this.drowned.m_7910_(f2);
            this.drowned.m_20256_(this.drowned.m_20184_().m_82520_((double)f2 * d0 * 0.005, (double)f2 * d1 * 0.1, (double)f2 * d2 * 0.005));
         } else {
            if (!this.drowned.m_20096_()) {
               this.drowned.m_20256_(this.drowned.m_20184_().m_82520_(0.0, -0.008, 0.0));
            }

            super.m_8126_();
         }
      }
   }

   class Coralssus_JumpPrepareAttackGoal extends InternalAttackGoal {
      private final float attackminrange;
      private final float random;

      public Coralssus_JumpPrepareAttackGoal(
         Coralssus_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange,
         float random
      ) {
         super(entity, getattackstate, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.attackminrange = attackminrange;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return super.m_8036_()
            && target != null
            && this.entity.m_20270_(target) > this.attackminrange
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && Coralssus_Entity.this.jump_cooldown <= 0;
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks == 13) {
            if (target != null) {
               this.entity.m_21563_().m_24960_(target, 60.0F, 30.0F);
               Vec3 vec3 = new Vec3(
                     target.m_20185_() - this.entity.m_20185_(), target.m_20186_() - this.entity.m_20186_(), target.m_20189_() - this.entity.m_20189_()
                  )
                  .m_82541_();
               this.entity.m_20256_(this.entity.m_20184_().m_82520_(vec3.f_82479_ * 0.8, 1.0, vec3.f_82481_ * 0.8));
            } else {
               this.entity.m_20256_(this.entity.m_20184_().m_82520_(0.0, 1.0, 0.0));
            }
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class CoralssusswimUpGoal extends Goal {
      private final Coralssus_Entity drowned;
      private final double speedModifier;
      private final int seaLevel;
      private boolean stuck;

      public CoralssusswimUpGoal(Coralssus_Entity p_32440_, double p_32441_, int p_32442_) {
         this.drowned = p_32440_;
         this.speedModifier = p_32441_;
         this.seaLevel = p_32442_;
      }

      public boolean m_8036_() {
         return (this.drowned.f_19853_.m_46471_() || this.drowned.m_20069_()) && this.drowned.m_20186_() < (double)(this.seaLevel - 2);
      }

      public boolean m_8045_() {
         return this.m_8036_() && !this.stuck;
      }

      public void m_8037_() {
         if (this.drowned.m_20186_() < (double)(this.seaLevel - 1) && (this.drowned.m_21573_().m_26571_() || this.drowned.closeToNextPos())) {
            Vec3 vec3 = DefaultRandomPos.m_148412_(
               this.drowned, 4, 8, new Vec3(this.drowned.m_20185_(), (double)(this.seaLevel - 1), this.drowned.m_20189_()), (float) (Math.PI / 2)
            );
            if (vec3 == null) {
               this.stuck = true;
               return;
            }

            this.drowned.m_21573_().m_26519_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, this.speedModifier);
         }
      }

      public void m_8056_() {
         this.drowned.setSearchingForLand(true);
         this.stuck = false;
      }

      public void m_8041_() {
         this.drowned.setSearchingForLand(false);
      }
   }

   public static enum Variant implements StringRepresentable {
      FIRE(0, "fire"),
      HORN(1, "horn"),
      TUBE(2, "tube");

      private static final IntFunction<Coralssus_Entity.Variant> BY_ID = ByIdMap.sparse(Coralssus_Entity.Variant::id, values(), FIRE);
      public static final Codec<Coralssus_Entity.Variant> CODEC = StringRepresentable.m_216439_(Coralssus_Entity.Variant::values);
      final int id;
      private final String name;

      private Variant(int p_262657_, String p_262679_) {
         this.id = p_262657_;
         this.name = p_262679_;
      }

      public String m_7912_() {
         return this.name;
      }

      public int id() {
         return this.id;
      }

      public static Coralssus_Entity.Variant byId(int p_262665_) {
         return BY_ID.apply(p_262665_);
      }
   }
}
