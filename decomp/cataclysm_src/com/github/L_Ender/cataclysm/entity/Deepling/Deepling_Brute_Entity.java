package com.github.L_Ender.cataclysm.entity.Deepling;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.ThrownCoral_Bardiche_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Deepling_Brute_Entity extends AbstractDeepling {
   boolean searchingForLand;
   public static final Animation DEEPLING_BRUTE_TRIDENT_THROW = Animation.create(45);
   public static final Animation DEEPLING_BRUTE_MELEE = Animation.create(20);
   private int SpinAttackTicks;
   private static final EntityDataAccessor<Boolean> SPINATTACK = SynchedEntityData.m_135353_(Deepling_Brute_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDimensions SWIMMING_SIZE = new EntityDimensions(1.3F, 0.7F, false);

   public Deepling_Brute_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21342_ = new Deepling_Brute_Entity.DeeplingMoveControl(this, 2.0F);
      this.switchNavigator(false);
      this.f_21364_ = 15;
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(2, new Deepling_Brute_Entity.DeeplingBruteTridentShoot(this, 1.0, 15.0F));
      this.f_21345_.m_25352_(5, new Deepling_Brute_Entity.DeeplingGoToBeachGoal(this, 1.0));
      this.f_21345_.m_25352_(6, new Deepling_Brute_Entity.DeeplingSwimUpGoal(this, 1.0, this.f_19853_.m_5736_()));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21345_.m_25352_(3, new Deepling_Brute_Entity.AnimationMeleeAttackGoal(this, 1.1F, false));
   }

   public static Builder deeplingbrute() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22279_, 0.29F)
         .m_22268_(Attributes.f_22281_, 5.0)
         .m_22268_(Attributes.f_22276_, 60.0)
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22284_, 8.0)
         .m_22268_(Attributes.f_22278_, 0.35);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SPINATTACK, false);
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Moisture", this.getMoistness());
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setMoistness(compound.m_128451_("Moisture"));
   }

   protected void m_213945_(RandomSource p_219154_, DifficultyInstance p_219155_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModItems.CORAL_BARDICHE.get()));
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.DEEPLING_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.DEEPLING_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.DEEPLING_DEATH.get();
   }

   protected void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ModSounds.DEEPLING_IDLE.get(), 0.15F, 0.6F);
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return ModEntities.rollSpawn(CMConfig.DeeplingBruteSpawnRolls, this.m_217043_(), spawnReasonIn);
   }

   @Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_, @Nullable CompoundTag p_34092_
   ) {
      SpawnGroupData spawngroupdata = super.m_6518_(p_34088_, p_34089_, p_34090_, p_34091_, p_34092_);
      RandomSource randomsource = p_34088_.m_213780_();
      this.m_213945_(randomsource, p_34089_);
      return spawngroupdata;
   }

   public boolean m_6914_(LevelReader p_32829_) {
      return p_32829_.m_45784_(this);
   }

   public static boolean candeeplingSpawn(
      EntityType<Deepling_Brute_Entity> p_223364_0_, LevelAccessor p_223364_1_, MobSpawnType reason, BlockPos p_223364_3_, RandomSource p_223364_4_
   ) {
      return p_223364_1_.m_46791_() != Difficulty.PEACEFUL
         && (reason == MobSpawnType.SPAWNER || p_223364_1_.m_6425_(p_223364_3_).m_205070_(FluidTags.f_13131_));
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, DEEPLING_BRUTE_TRIDENT_THROW, DEEPLING_BRUTE_MELEE};
   }

   public boolean getSpinAttack() {
      return (Boolean)this.f_19804_.m_135370_(SPINATTACK);
   }

   public void setSpinAttack(boolean p_211137_1_) {
      this.f_19804_.m_135381_(SPINATTACK, p_211137_1_);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      LivingEntity target = this.m_5448_();
      if (this.m_6084_()) {
         if (this.getAnimation() == DEEPLING_BRUTE_TRIDENT_THROW && target != null && this.getAnimationTick() == 11) {
            if (this.m_20070_() && !this.m_20159_()) {
               double f1 = target.m_20185_() - this.m_20185_();
               double f2 = target.m_20186_() - this.m_20186_();
               double f3 = target.m_20189_() - this.m_20189_();
               double f4 = (double)Mth.m_14116_((float)(f1 * f1 + f2 * f2 + f3 * f3));
               float f5 = 2.0F;
               f1 *= (double)f5 / f4;
               f2 *= (double)f5 / f4;
               f3 *= (double)f5 / f4;
               this.m_5997_(f1, f2, f3);
               this.startAutoSpinAttack(20);
               if (this.m_20096_()) {
                  this.m_6478_(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0));
               }

               this.m_5496_(SoundEvents.f_12519_, 1.0F, 1.0F);
            } else {
               ThrownCoral_Bardiche_Entity throwntrident = new ThrownCoral_Bardiche_Entity(
                  this.f_19853_, this, new ItemStack((ItemLike)ModItems.CORAL_BARDICHE.get())
               );
               double p0 = target.m_20185_() - this.m_20185_();
               double p1 = target.m_20227_(0.3333333333333333) - throwntrident.m_20186_();
               double p2 = target.m_20189_() - this.m_20189_();
               double p3 = Math.sqrt(p0 * p0 + p2 * p2);
               throwntrident.m_6686_(p0, p1 + p3 * 0.2F, p2, 1.6F, (float)(14 - this.f_19853_.m_46791_().m_19028_() * 4));
               this.m_5496_(SoundEvents.f_11821_, 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
               this.f_19853_.m_7967_(throwntrident);
            }
         }

         if (this.getAnimation() == DEEPLING_BRUTE_MELEE && this.getAnimationTick() == 5) {
            this.m_5496_((SoundEvent)ModSounds.DEEPLING_SWING.get(), 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
            if (target != null && this.m_20270_(target) < 3.0F) {
               float damage = (float)this.m_21133_(Attributes.f_22281_);
               target.m_6469_(DamageSource.m_19370_(this), damage);
            }
         }
      }

      AABB aabb = this.m_20191_();
      if (this.SpinAttackTicks > 0) {
         this.SpinAttackTicks--;
         this.m_21071_(aabb, this.m_20191_());
      }
   }

   public void startAutoSpinAttack(int p_204080_) {
      this.SpinAttackTicks = p_204080_;
      if (!this.f_19853_.f_46443_) {
         this.setSpinAttack(true);
      }
   }

   protected void m_21071_(AABB p_21072_, AABB p_21073_) {
      AABB aabb = p_21072_.m_82367_(p_21073_);
      List<Entity> list = this.f_19853_.m_45933_(this, aabb);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (entity instanceof LivingEntity) {
               entity.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_));
               this.SpinAttackTicks = 0;
               this.m_20256_(this.m_20184_().m_82490_(-0.2));
               break;
            }
         }
      } else if (this.f_19862_) {
         this.SpinAttackTicks = 0;
      }

      if (!this.f_19853_.f_46443_ && this.SpinAttackTicks <= 0) {
         this.setSpinAttack(false);
      }
   }

   protected float m_6431_(Pose poseIn, EntityDimensions sizeIn) {
      return sizeIn.f_20378_ * 0.9F;
   }

   boolean wantsToSwim() {
      if (this.searchingForLand) {
         return true;
      } else {
         LivingEntity livingentity = this.m_5448_();
         return livingentity != null && livingentity.m_20069_();
      }
   }

   @Override
   public AABB getSwimmingBox() {
      return new AABB(this.m_20185_() - 1.3F, this.m_20186_(), this.m_20189_() - 1.3F, this.m_20185_() + 1.3F, this.m_20186_() + 0.7F, this.m_20189_() + 1.3F);
   }

   @Override
   public AABB getNormalBox() {
      return new AABB(this.m_20185_() - 0.7F, this.m_20186_(), this.m_20189_() - 0.7F, this.m_20185_() + 0.7F, this.m_20186_() + 2.6F, this.m_20189_() + 0.7F);
   }

   @Override
   public EntityDimensions getSwimmingSize() {
      return SWIMMING_SIZE;
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

   static class AnimationMeleeAttackGoal extends MeleeAttackGoal {
      protected final Deepling_Brute_Entity f_25540_;

      public AnimationMeleeAttackGoal(Deepling_Brute_Entity p_25552_, double p_25553_, boolean p_25554_) {
         super(p_25552_, p_25553_, p_25554_);
         this.f_25540_ = p_25552_;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      protected double m_6639_(LivingEntity p_25556_) {
         return (double)(this.f_25540_.m_20205_() * 2.5F * this.f_25540_.m_20205_() * 2.5F + p_25556_.m_20205_());
      }

      protected void m_6739_(LivingEntity p_25557_, double p_25558_) {
         double d0 = this.m_6639_(p_25557_);
         if (p_25558_ <= d0 && this.f_25540_.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            this.f_25540_.setAnimation(Deepling_Brute_Entity.DEEPLING_BRUTE_MELEE);
         }
      }
   }

   static class DeeplingBruteTridentShoot extends Goal {
      private final Deepling_Brute_Entity mob;
      private final double moveSpeedAmp;
      private int attackCooldown;
      private final float maxAttackDistance;
      private int attackTime = -1;
      private int seeTime;
      private boolean strafingClockwise;
      private boolean strafingBackwards;
      private int strafingTime = -1;

      public DeeplingBruteTridentShoot(Deepling_Brute_Entity mob, double moveSpeedAmpIn, float maxAttackDistanceIn) {
         this.mob = mob;
         this.moveSpeedAmp = moveSpeedAmpIn;
         this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity livingentity = this.mob.m_5448_();
         return livingentity != null
            && livingentity.m_6084_()
            && this.mob.m_21205_().m_150930_((Item)ModItems.CORAL_BARDICHE.get())
            && this.mob.m_20280_(livingentity) >= 36.0;
      }

      public boolean m_8045_() {
         return this.m_8036_() || !this.mob.m_21573_().m_26571_();
      }

      public void m_8056_() {
         super.m_8056_();
         this.mob.m_21561_(true);
         this.mob.m_6672_(InteractionHand.MAIN_HAND);
      }

      public void m_8041_() {
         super.m_8041_();
         this.mob.m_21561_(false);
         this.seeTime = 0;
         this.attackTime = -1;
         this.mob.m_5810_();
      }

      public void m_8037_() {
         LivingEntity livingentity = this.mob.m_5448_();
         if (livingentity != null) {
            double d0 = this.mob.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
            boolean flag = this.mob.m_142582_(livingentity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
               this.seeTime = 0;
            }

            if (flag) {
               this.seeTime++;
            } else {
               this.seeTime--;
            }

            if (!(d0 > (double)this.maxAttackDistance) && this.seeTime >= 20) {
               this.mob.m_21573_().m_26573_();
               this.strafingTime++;
            } else {
               this.mob.m_21573_().m_5624_(livingentity, this.moveSpeedAmp);
               this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
               if ((double)this.mob.m_217043_().m_188501_() < 0.3) {
                  this.strafingClockwise = !this.strafingClockwise;
               }

               if ((double)this.mob.m_217043_().m_188501_() < 0.3) {
                  this.strafingBackwards = !this.strafingBackwards;
               }

               this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
               if (d0 > (double)(this.maxAttackDistance * 0.75F)) {
                  this.strafingBackwards = false;
               } else if (d0 < (double)(this.maxAttackDistance * 0.25F)) {
                  this.strafingBackwards = true;
               }

               this.mob.m_21566_().m_24988_(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
               this.mob.m_21391_(livingentity, 30.0F, 30.0F);
            } else {
               this.mob.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
            }

            if (!flag && this.seeTime < -60) {
               this.mob.m_5810_();
            } else if (flag && this.mob.getAnimation() != Deepling_Brute_Entity.DEEPLING_BRUTE_TRIDENT_THROW) {
               this.mob.setAnimation(Deepling_Brute_Entity.DEEPLING_BRUTE_TRIDENT_THROW);
               this.attackTime = this.attackCooldown;
            }
         }
      }
   }

   static class DeeplingGoToBeachGoal extends MoveToBlockGoal {
      private final Deepling_Brute_Entity drowned;

      public DeeplingGoToBeachGoal(Deepling_Brute_Entity p_32409_, double p_32410_) {
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

   static class DeeplingMoveControl extends MoveControl {
      private final Deepling_Brute_Entity drowned;
      private final float speedMulti;

      public DeeplingMoveControl(Deepling_Brute_Entity p_32433_, float speedMulti) {
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

   static class DeeplingSwimUpGoal extends Goal {
      private final Deepling_Brute_Entity drowned;
      private final double speedModifier;
      private final int seaLevel;
      private boolean stuck;

      public DeeplingSwimUpGoal(Deepling_Brute_Entity p_32440_, double p_32441_, int p_32442_) {
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
}
