package com.github.L_Ender.cataclysm.entity.AnimationMonster;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ancient_Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class Koboleton_Entity extends Animation_Monster {
   public static final Animation COBOLETON_ATTACK = Animation.create(19);
   private static final EntityDataAccessor<Boolean> IS_ANGRY = SynchedEntityData.m_135353_(Koboleton_Entity.class, EntityDataSerializers.f_135035_);
   public float angryProgress;
   public float prevangryProgress;

   public Koboleton_Entity(EntityType entity, Level world) {
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
      return new Animation[]{NO_ANIMATION, COBOLETON_ATTACK};
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new Koboleton_Entity.AnimationMeleeAttackGoal(this, 1.0, false));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder koboleton() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.4F)
         .m_22268_(Attributes.f_22281_, 3.0)
         .m_22268_(Attributes.f_22276_, 25.0)
         .m_22268_(Attributes.f_22284_, 0.0)
         .m_22268_(Attributes.f_22278_, 0.25);
   }

   public boolean m_6469_(DamageSource source, float damage) {
      return super.m_6469_(source, damage);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.KOBOLETON_AMBIENT.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.KOBOLETON_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.KOBOLETON_DEATH.get();
   }

   protected void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ModSounds.KOBOLETON_STEP.get(), 0.15F, 0.6F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_ANGRY, false);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   protected void m_213945_(RandomSource p_219154_, DifficultyInstance p_219155_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModItems.KHOPESH.get()));
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

   public void setIsAngry(boolean isAngry) {
      this.f_19804_.m_135381_(IS_ANGRY, isAngry);
   }

   public boolean getIsAngry() {
      return (Boolean)this.f_19804_.m_135370_(IS_ANGRY);
   }

   public void m_8119_() {
      super.m_8119_();
      AnimationHandler.INSTANCE.updateAnimations(this);
      this.prevangryProgress = this.angryProgress;
      if (this.getIsAngry() && this.angryProgress < 10.0F) {
         this.angryProgress++;
      }

      if (!this.getIsAngry() && this.angryProgress > 0.0F) {
         this.angryProgress--;
      }

      LivingEntity target = this.m_5448_();
      if (this.m_6084_() && this.getAnimation() == COBOLETON_ATTACK && this.getAnimationTick() == 11) {
         this.m_5496_(SoundEvents.f_12317_, 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
         if (target != null && this.m_20270_(target) < this.m_20205_() * 2.5F * this.m_20205_() * 2.5F + target.m_20205_()) {
            float damage = (float)this.m_21133_(Attributes.f_22281_);
            target.m_6469_(DamageSource.m_19370_(this), damage);
            ItemStack offhand = target.m_21206_();
            ItemStack mainhand = target.m_21205_();
            Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(target, stack -> stack.m_150930_((Item)ModItems.STICKY_GLOVES.get()));
            if ((double)(this.f_19796_.m_188501_() * 100.0F) <= CMConfig.CauseKoboletontoDropItemInHandPercent && slot.isEmpty()) {
               if (!offhand.m_41619_()) {
                  if (!offhand.m_204117_(ModTag.STICKY_ITEM)) {
                     int i = offhand.m_41613_();
                     ItemStack offhandCopy = offhand.m_41777_();
                     offhandCopy.m_41764_(1);
                     this.koboletonstealdrop(offhandCopy, target);
                     target.m_8061_(EquipmentSlot.OFFHAND, offhand.m_41620_(i - 1));
                  }
               } else if (!mainhand.m_41619_() && !mainhand.m_204117_(ModTag.STICKY_ITEM)) {
                  int i = mainhand.m_41613_();
                  ItemStack mainhandCopy = mainhand.m_41777_();
                  mainhandCopy.m_41764_(1);
                  this.koboletonstealdrop(mainhandCopy, target);
                  target.m_8061_(EquipmentSlot.MAINHAND, mainhand.m_41620_(i - 1));
               }
            }
         }
      }
   }

   public ItemStack copyWithCound(ItemStack stack, int count) {
      ItemStack copy = stack.m_41777_();
      copy.m_41764_(count);
      return copy;
   }

   private ItemEntity koboletonstealdrop(ItemStack p_36179_, LivingEntity target) {
      if (p_36179_.m_41619_()) {
         return null;
      } else if (this.f_19853_.f_46443_) {
         return null;
      } else {
         double d0 = target.m_20188_() - 0.3F;
         ItemEntity itementity = new ItemEntity(target.f_19853_, target.m_20185_(), d0, target.m_20189_(), p_36179_);
         itementity.m_32060_();
         itementity.m_32064_();
         float f8 = Mth.m_14031_(target.m_146909_() * (float) (Math.PI / 180.0));
         float f2 = Mth.m_14089_(target.m_146909_() * (float) (Math.PI / 180.0));
         float f3 = Mth.m_14031_(target.m_146908_() * (float) (Math.PI / 180.0));
         float f4 = Mth.m_14089_(target.m_146908_() * (float) (Math.PI / 180.0));
         float f5 = target.m_217043_().m_188501_() * (float) (Math.PI * 2);
         float f6 = 0.02F * target.m_217043_().m_188501_();
         itementity.m_20334_(
            (double)(-f3 * f2 * 0.3F) + Math.cos((double)f5) * (double)f6,
            (double)(-f8 * 0.3F + 0.1F + (target.m_217043_().m_188501_() - target.m_217043_().m_188501_()) * 0.1F),
            (double)(f4 * f2 * 0.3F) + Math.sin((double)f5) * (double)f6
         );
         this.f_19853_.m_7967_(itementity);
         return itementity;
      }
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return ModEntities.rollSpawn(CMConfig.KoboletonSpawnRolls, this.m_217043_(), spawnReasonIn);
   }

   public void m_6710_(@Nullable LivingEntity p_32537_) {
      this.setIsAngry(p_32537_ != null);
      super.m_6710_(p_32537_);
   }

   public static boolean checkKoboletonSpawnRules(
      EntityType<Koboleton_Entity> p_218997_, ServerLevelAccessor p_218998_, MobSpawnType p_218999_, BlockPos p_219000_, RandomSource p_219001_
   ) {
      return m_219013_(p_218997_, p_218998_, p_218999_, p_219000_, p_219001_) && (p_218999_ == MobSpawnType.SPAWNER || p_218998_.m_45527_(p_219000_));
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !(entityIn instanceof Koboleton_Entity) && !(entityIn instanceof Ancient_Ancient_Remnant_Entity)
            ? false
            : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   static class AnimationMeleeAttackGoal extends MeleeAttackGoal {
      protected final Koboleton_Entity f_25540_;

      public AnimationMeleeAttackGoal(Koboleton_Entity p_25552_, double p_25553_, boolean p_25554_) {
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
            this.f_25540_.setAnimation(Koboleton_Entity.COBOLETON_ATTACK);
         }
      }
   }
}
