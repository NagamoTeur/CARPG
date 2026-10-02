package com.github.L_Ender.cataclysm.entity.Deepling;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AI.CmAttackGoal;
import com.github.L_Ender.cataclysm.entity.AI.MobAIFindWater;
import com.github.L_Ender.cataclysm.entity.AI.MobAILeaveWater;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAnimationGoal1;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.ISemiAquatic;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.GroundPathNavigatorWide;
import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.world.data.CMWorldData;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

public class Coral_Golem_Entity extends LLibrary_Monster implements ISemiAquatic {
   private boolean isLandNavigator;
   boolean searchingForLand;
   public static final Animation CORAL_GOLEM_LEAP = Animation.create(100);
   public static final Animation CORAL_GOLEM_SMASH = Animation.create(23);
   public static final Animation CORAL_GOLEM_LEFT_SMASH = Animation.create(36);
   public static final Animation CORAL_GOLEM_RIGHT_SMASH = Animation.create(36);
   public static final int LEAP_ATTACK_COOLDOWN = 160;
   private int leap_attack_cooldown = 0;
   private static final EntityDataAccessor<Boolean> GOLEMSWIM = SynchedEntityData.m_135353_(Coral_Golem_Entity.class, EntityDataSerializers.f_135035_);

   public Coral_Golem_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 15;
      this.f_21342_ = new Coral_Golem_Entity.GolemMoveControl(this, 2.0F);
      this.switchNavigator(false);
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public float getStepHeight() {
      return 1.5F;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, CORAL_GOLEM_SMASH, CORAL_GOLEM_LEFT_SMASH, CORAL_GOLEM_RIGHT_SMASH, CORAL_GOLEM_LEAP};
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(5, new Coral_Golem_Entity.GolemGoToBeachGoal(this, 1.0));
      this.f_21345_.m_25352_(6, new Coral_Golem_Entity.GolemSwimUpGoal(this, 1.0, this.f_19853_.m_5736_()));
      this.f_21345_.m_25352_(4, new MobAIFindWater(this, 1.0));
      this.f_21345_.m_25352_(4, new MobAILeaveWater(this));
      this.f_21345_.m_25352_(0, new AttackAnimationGoal1<>(this, CORAL_GOLEM_LEFT_SMASH, 16, true));
      this.f_21345_.m_25352_(0, new AttackAnimationGoal1<>(this, CORAL_GOLEM_RIGHT_SMASH, 16, true));
      this.f_21345_.m_25352_(0, new Coral_Golem_Entity.Leap(this, CORAL_GOLEM_LEAP));
      this.f_21345_.m_25352_(0, new SimpleAnimationGoal<>(this, CORAL_GOLEM_SMASH));
      this.f_21345_.m_25352_(2, new CmAttackGoal(this, 1.0));
      this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder coralgolem() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 11.0)
         .m_22268_(Attributes.f_22276_, 110.0)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22278_, 0.8);
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public boolean m_6469_(DamageSource source, float damage) {
      return super.m_6469_(source, damage);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(GOLEMSWIM, false);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      if (ModEntities.rollSpawn(CMConfig.CoralgolemSpawnRolls, this.m_217043_(), spawnReasonIn)
         && worldIn instanceof ServerLevel serverLevel
         && super.m_5545_(worldIn, spawnReasonIn)) {
         CMWorldData data = CMWorldData.get(serverLevel, Level.f_46428_);
         return data != null && data.isLeviathanDefeatedOnce();
      }

      return false;
   }

   public boolean m_6914_(LevelReader p_32829_) {
      return p_32829_.m_45784_(this);
   }

   public static boolean cangolemSpawn(
      EntityType<Coral_Golem_Entity> p_223364_0_, LevelAccessor p_223364_1_, MobSpawnType reason, BlockPos p_223364_3_, RandomSource p_223364_4_
   ) {
      return p_223364_1_.m_46791_() != Difficulty.PEACEFUL
         && (reason == MobSpawnType.SPAWNER || p_223364_1_.m_6425_(p_223364_3_).m_205070_(FluidTags.f_13131_));
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

      boolean flag1 = this.canInFluidType(this.getEyeInFluidType());
      if (flag1) {
         if (this.f_19853_.m_45756_(this, this.m_20191_()) && !this.getSwim()) {
            this.setSwim(true);
         }
      } else if (this.f_19853_.m_45756_(this, this.m_20191_()) && this.getSwim()) {
         this.setSwim(false);
      }

      if (this.leap_attack_cooldown > 0) {
         this.leap_attack_cooldown--;
      }

      LivingEntity target = this.m_5448_();
      if (this.m_6084_() && target != null && target.m_6084_()) {
         if (!this.getSwim()
            && this.leap_attack_cooldown <= 0
            && !this.m_21525_()
            && this.getAnimation() == NO_ANIMATION
            && target.m_20096_()
            && this.f_19796_.m_188503_(25) == 0
            && this.m_20270_(target) <= 15.0F) {
            this.leap_attack_cooldown = 160;
            this.setAnimation(CORAL_GOLEM_LEAP);
         } else if (this.m_20270_(target) < 3.75F && !this.m_21525_() && this.getAnimation() == NO_ANIMATION) {
            Animation animation = getRandomAttack(this.f_19796_);
            this.setAnimation(animation);
         }
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAnimation() == CORAL_GOLEM_RIGHT_SMASH && this.getAnimationTick() == 16) {
         this.EarthQuake(3.0F, 2);
         this.Makeparticle(2.0F, -0.5F);
      }

      if (this.getAnimation() == CORAL_GOLEM_LEFT_SMASH && this.getAnimationTick() == 16) {
         this.EarthQuake(3.0F, 2);
         this.Makeparticle(2.0F, 0.5F);
      }

      if (this.getAnimation() == CORAL_GOLEM_SMASH && this.getAnimationTick() == 2) {
         this.EarthQuake(4.0F, 4);
         this.Makeparticle(2.25F, 1.25F);
         this.Makeparticle(2.25F, -1.25F);
      }
   }

   private void Makeparticle(float vec, float math) {
      if (this.f_19853_.f_46443_) {
         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.07;
            float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
            float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)(0.75F * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(0.75F * Mth.m_14089_(angle));
            double theta = (double)this.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            double vecZ = Math.sin(theta);
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
      }
   }

   public void m_7332_(Entity p_289537_) {
      super.m_7332_(p_289537_);
      float radius = 0.5F;
      float angle = (float) (Math.PI / 180.0) * this.f_20883_;
      double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      p_289537_.m_6034_(this.m_20185_() + extraX, this.m_20227_(0.65) + p_289537_.m_6049_() + 0.0, this.m_20189_() + extraZ);
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   private void EarthQuake(float grow, int damage) {
      ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 10.0F, 0.15F, 0, 20);
      this.m_5496_(SoundEvents.f_11913_, 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_((double)grow))) {
         if (!this.m_7307_(entity) && !(entity instanceof Coral_Golem_Entity) && entity != this) {
            entity.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_) + (float)this.f_19796_.m_188503_(damage));
            this.launch(entity, true);
         }
      }
   }

   private static Animation getRandomAttack(RandomSource rand) {
      switch (rand.m_188503_(2)) {
         case 0:
            return CORAL_GOLEM_RIGHT_SMASH;
         case 1:
            return CORAL_GOLEM_LEFT_SMASH;
         default:
            return CORAL_GOLEM_RIGHT_SMASH;
      }
   }

   private void launch(LivingEntity e, boolean huge) {
      double d0 = e.m_20185_() - this.m_20185_();
      double d1 = e.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      float f = huge ? 2.0F : 0.5F;
      e.m_5997_(d0 / d2 * (double)f, huge ? 0.5 : 0.2F, d1 / d2 * (double)f);
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

   protected void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_(SoundEvents.f_12010_, 1.0F, 1.0F);
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.CORAL_GOLEM_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.CORAL_GOLEM_DEATH.get();
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
      return (Boolean)this.f_19804_.m_135370_(GOLEMSWIM);
   }

   public void setSwim(boolean swim) {
      this.f_19804_.m_135381_(GOLEMSWIM, swim);
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public boolean m_6063_() {
      return !this.m_6069_();
   }

   public boolean m_6040_() {
      return true;
   }

   @Override
   public boolean shouldEnterWater() {
      return false;
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

   static class GolemGoToBeachGoal extends MoveToBlockGoal {
      private final Coral_Golem_Entity drowned;

      public GolemGoToBeachGoal(Coral_Golem_Entity p_32409_, double p_32410_) {
         super(p_32409_, p_32410_, 8, 2);
         this.drowned = p_32409_;
      }

      public boolean m_8036_() {
         return super.m_8036_()
            && this.drowned.f_19853_.m_46471_()
            && this.drowned.m_20069_()
            && this.drowned.m_20186_() >= (double)(this.drowned.f_19853_.m_5736_() - 3);
      }

      public boolean m_8045_() {
         return super.m_8045_();
      }

      protected boolean m_6465_(LevelReader p_32413_, BlockPos p_32414_) {
         BlockPos blockpos = p_32414_.m_7494_();
         return p_32413_.m_46859_(blockpos) && p_32413_.m_46859_(blockpos.m_7494_())
            ? p_32413_.m_8055_(p_32414_).m_60634_(p_32413_, p_32414_, this.drowned)
            : false;
      }

      public void m_8056_() {
         this.drowned.setSearchingForLand(false);
         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
      }
   }

   static class GolemMoveControl extends MoveControl {
      private final Coral_Golem_Entity drowned;
      private final float speedMulti;

      public GolemMoveControl(Coral_Golem_Entity p_32433_, float speedMulti) {
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

   static class GolemSwimUpGoal extends Goal {
      private final Coral_Golem_Entity drowned;
      private final double speedModifier;
      private final int seaLevel;
      private boolean stuck;

      public GolemSwimUpGoal(Coral_Golem_Entity p_32440_, double p_32441_, int p_32442_) {
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

   static class Leap extends SimpleAnimationGoal<Coral_Golem_Entity> {
      private final Coral_Golem_Entity drowned;

      public Leap(Coral_Golem_Entity entity, Animation animation) {
         super(entity, animation);
         this.drowned = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = this.drowned.m_5448_();
         if (target != null) {
            this.drowned.m_21391_(target, 30.0F, 30.0F);
            this.drowned.m_21563_().m_24960_(target, 30.0F, 30.0F);
            if (this.drowned.getAnimationTick() == 22) {
               double d0 = target.m_20185_() - this.drowned.m_20185_();
               double d1 = target.m_20186_() - this.drowned.m_20186_();
               double d2 = target.m_20189_() - this.drowned.m_20189_();
               this.drowned.m_20334_(d0 * 0.15, 0.75 + Mth.m_14008_(d1 * 0.05, 0.0, 10.0), d2 * 0.15);
            }
         } else if (this.drowned.getAnimationTick() == 22) {
            this.drowned.m_20334_(0.0, 0.75, 0.0);
         }

         if (this.drowned.getAnimationTick() > 22 && this.drowned.m_20096_()) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.drowned, Coral_Golem_Entity.CORAL_GOLEM_SMASH);
         }
      }
   }
}
