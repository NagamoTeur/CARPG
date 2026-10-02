package com.github.L_Ender.cataclysm.entity.Deepling;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.Abyss_Mark_Entity;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Deepling_Warlock_Entity extends AbstractDeepling {
   public static final Animation DEEPLING_MELEE = Animation.create(20);
   public static final Animation DEEPLING_MAGIC = Animation.create(90);
   boolean searchingForLand;
   private int lightcooldown = 200;
   public static final int LIGHT_COOLDOWN = 400;
   private static final EntityDimensions SWIMMING_SIZE = new EntityDimensions(1.15F, 0.6F, false);

   public Deepling_Warlock_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.switchNavigator(false);
      this.f_21364_ = 10;
   }

   @Override
   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new Deepling_Warlock_Entity.MagicTrackingGoal(this, DEEPLING_MAGIC));
      this.f_21345_.m_25352_(2, new Deepling_Warlock_Entity.DeeplingMagicGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21345_.m_25352_(3, new Deepling_Warlock_Entity.AnimationMeleeAttackGoal(this, 1.0, false));
   }

   public static Builder deeplingwarlock() {
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
      if (ModEntities.rollSpawn(CMConfig.DeeplingWarlockSpawnRolls, this.m_217043_(), spawnReasonIn) && worldIn instanceof ServerLevel serverLevel) {
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
      EntityType<Deepling_Warlock_Entity> p_223364_0_, LevelAccessor p_223364_1_, MobSpawnType reason, BlockPos p_223364_3_, RandomSource p_223364_4_
   ) {
      return p_223364_1_.m_46791_() != Difficulty.PEACEFUL
         && (reason == MobSpawnType.SPAWNER || p_223364_1_.m_6425_(p_223364_3_).m_205070_(FluidTags.f_13131_));
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, DEEPLING_MELEE, DEEPLING_MAGIC};
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      LivingEntity target = this.m_5448_();
      if (this.lightcooldown > 0) {
         this.lightcooldown--;
      }

      if (this.m_6084_() && this.getAnimation() == DEEPLING_MELEE && this.getAnimationTick() == 5) {
         this.m_5496_((SoundEvent)ModSounds.DEEPLING_SWING.get(), 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
         if (target != null && this.m_20270_(target) < 3.0F) {
            float damage = (float)this.m_21133_(Attributes.f_22281_);
            target.m_6469_(DamageSource.m_19370_(this), damage);
         }
      }
   }

   protected float m_6431_(Pose poseIn, EntityDimensions sizeIn) {
      return sizeIn.f_20378_ * 0.9F;
   }

   @Override
   public EntityDimensions getSwimmingSize() {
      return SWIMMING_SIZE;
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

   static class AnimationMeleeAttackGoal extends MeleeAttackGoal {
      protected final Deepling_Warlock_Entity f_25540_;

      public AnimationMeleeAttackGoal(Deepling_Warlock_Entity p_25552_, double p_25553_, boolean p_25554_) {
         super(p_25552_, p_25553_, p_25554_);
         this.f_25540_ = p_25552_;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      protected void m_6739_(LivingEntity p_25557_, double p_25558_) {
         double d0 = this.m_6639_(p_25557_);
         if (p_25558_ <= d0 && this.f_25540_.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            this.f_25540_.setAnimation(Deepling_Warlock_Entity.DEEPLING_MELEE);
         }
      }
   }

   static class DeeplingMagicGoal extends Goal {
      private final Deepling_Warlock_Entity angler;

      public DeeplingMagicGoal(Deepling_Warlock_Entity angler) {
         this.angler = angler;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity target = this.angler.m_5448_();
         return this.angler.lightcooldown <= 0
            && this.angler.getAnimation() == IAnimatedEntity.NO_ANIMATION
            && target != null
            && this.angler.m_20280_(target) >= 25.0
            && target.m_6084_()
            && this.angler.m_217043_().m_188501_() * 100.0F < 12.0F;
      }

      public void m_8056_() {
         super.m_8056_();
         this.angler.setAnimation(Deepling_Warlock_Entity.DEEPLING_MAGIC);
         this.angler.lightcooldown = 400;
         this.angler.f_21344_.m_26573_();
      }

      public void m_8041_() {
         super.m_8041_();
      }
   }

   static class MagicTrackingGoal extends SimpleAnimationGoal<Deepling_Warlock_Entity> {
      private final Deepling_Warlock_Entity warlock;

      public MagicTrackingGoal(Deepling_Warlock_Entity entity, Animation animation) {
         super(entity, animation);
         this.warlock = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         LivingEntity target = this.warlock.m_5448_();
         if (target != null) {
            this.warlock.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.warlock.m_5448_();
         if (target != null) {
            this.warlock.m_21563_().m_24960_(target, 30.0F, 30.0F);
            if (this.warlock.getAnimationTick() == 18) {
               double sx = this.warlock.m_20185_();
               double sy = this.warlock.m_20186_();
               double sz = this.warlock.m_20189_();
               Abyss_Mark_Entity fireball = new Abyss_Mark_Entity(
                  this.warlock.f_19853_, sx, sy, sz, 80, (float)CMConfig.AbyssBlastdamage, (float)CMConfig.AbyssBlastHpdamage, this.warlock.m_20148_(), target
               );
               this.warlock.f_19853_.m_7967_(fireball);
            }
         }
      }
   }
}
