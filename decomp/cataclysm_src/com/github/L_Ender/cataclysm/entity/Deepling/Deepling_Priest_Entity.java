package com.github.L_Ender.cataclysm.entity.Deepling;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.world.data.CMWorldData;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Deepling_Priest_Entity extends AbstractDeepling {
   boolean searchingForLand;
   public static final Animation DEEPLING_MELEE = Animation.create(20);
   public static final Animation DEEPLING_BLIND = Animation.create(57);
   private int lightcooldown = 200;
   public static final int LIGHT_COOLDOWN = 200;
   private static final EntityDimensions SWIMMING_SIZE = new EntityDimensions(1.15F, 0.6F, false);

   public Deepling_Priest_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.switchNavigator(false);
      this.f_21364_ = 10;
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(2, new Deepling_Priest_Entity.DeeplingLightGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21345_.m_25352_(3, new Deepling_Priest_Entity.AnimationMeleeAttackGoal(this, 1.0, false));
   }

   public static Builder deeplingpriest() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22279_, 0.27F)
         .m_22268_(Attributes.f_22281_, 4.0)
         .m_22268_(Attributes.f_22276_, 45.0)
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22278_, 0.25);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new WaterBoundPathNavigation(this, worldIn);
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   protected void m_213945_(RandomSource p_219154_, DifficultyInstance p_219155_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModItems.ATHAME.get()));
      this.m_21409_(EquipmentSlot.MAINHAND, 0.0F);
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
      if (ModEntities.rollSpawn(CMConfig.DeeplingPriestSpawnRolls, this.m_217043_(), spawnReasonIn) && worldIn instanceof ServerLevel serverLevel) {
         CMWorldData data = CMWorldData.get(serverLevel, Level.f_46428_);
         return data != null && data.isLeviathanDefeatedOnce();
      } else {
         return false;
      }
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
      EntityType<Deepling_Priest_Entity> p_223364_0_, LevelAccessor p_223364_1_, MobSpawnType reason, BlockPos p_223364_3_, RandomSource p_223364_4_
   ) {
      return p_223364_1_.m_46791_() != Difficulty.PEACEFUL
         && (reason == MobSpawnType.SPAWNER || p_223364_1_.m_6425_(p_223364_3_).m_205070_(FluidTags.f_13131_));
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, DEEPLING_MELEE, DEEPLING_BLIND};
   }

   private boolean isEntityLookingAt(LivingEntity looker, LivingEntity seen, double degree) {
      degree *= 1.0 + (double)looker.m_20270_(seen) * 0.1;
      Vec3 Vector3d = looker.m_20252_(1.0F).m_82541_();
      Vec3 Vector3d1 = new Vec3(
         seen.m_20185_() - looker.m_20185_(),
         seen.m_20191_().f_82289_ + (double)seen.m_20192_() - (looker.m_20186_() + (double)looker.m_20192_()),
         seen.m_20189_() - looker.m_20189_()
      );
      double d0 = Vector3d1.m_82553_();
      Vector3d1 = Vector3d1.m_82541_();
      double d1 = Vector3d.m_82526_(Vector3d1);
      return d1 > 1.0 - degree / d0 && looker.m_142582_(seen);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      LivingEntity target = this.m_5448_();
      if (this.lightcooldown > 0) {
         this.lightcooldown--;
      }

      if (this.m_6084_()) {
         if (this.getAnimation() == DEEPLING_MELEE && this.getAnimationTick() == 5) {
            this.m_5496_((SoundEvent)ModSounds.DEEPLING_SWING.get(), 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
            if (target != null && this.m_20270_(target) < 3.0F) {
               float damage = (float)this.m_21133_(Attributes.f_22281_);
               target.m_6469_(DamageSource.m_19370_(this), damage);
            }
         }

         if (this.getAnimation() == DEEPLING_BLIND) {
            if (this.getAnimationTick() == 18) {
               this.m_5496_((SoundEvent)ModSounds.DEEPLING_LIGHT.get(), 0.2F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
            }

            if (this.getAnimationTick() > 18 && this.getAnimationTick() < 47) {
               for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(7.0))) {
                  if (!this.m_7307_(entity) && entity != this && this.isEntityLookingAt(entity, this, 0.6)) {
                     boolean flag = entity.m_6469_(DamageSource.m_19367_(this, this), (float)this.m_21133_(Attributes.f_22281_) * 0.7F);
                     if (flag) {
                        entity.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 80));
                     }
                  }
               }
            }
         }
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

   static class AnimationMeleeAttackGoal extends MeleeAttackGoal {
      protected final Deepling_Priest_Entity f_25540_;

      public AnimationMeleeAttackGoal(Deepling_Priest_Entity p_25552_, double p_25553_, boolean p_25554_) {
         super(p_25552_, p_25553_, p_25554_);
         this.f_25540_ = p_25552_;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      protected void m_6739_(LivingEntity p_25557_, double p_25558_) {
         double d0 = this.m_6639_(p_25557_);
         if (p_25558_ <= d0 && this.f_25540_.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            this.f_25540_.setAnimation(Deepling_Priest_Entity.DEEPLING_MELEE);
         }
      }
   }

   static class DeeplingLightGoal extends Goal {
      private final Deepling_Priest_Entity angler;

      public DeeplingLightGoal(Deepling_Priest_Entity angler) {
         this.angler = angler;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity target = this.angler.m_5448_();
         return this.angler.lightcooldown <= 0
            && this.angler.getAnimation() == IAnimatedEntity.NO_ANIMATION
            && target != null
            && this.angler.m_20280_(target) <= 64.0
            && target.m_6084_()
            && this.angler.m_217043_().m_188501_() * 100.0F < 12.0F;
      }

      public void m_8056_() {
         super.m_8056_();
         this.angler.setAnimation(Deepling_Priest_Entity.DEEPLING_BLIND);
         this.angler.lightcooldown = 200;
         this.angler.f_21344_.m_26573_();
      }

      public void m_8041_() {
         super.m_8041_();
      }
   }
}
