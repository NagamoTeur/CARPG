package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Draugr_Entity extends Monster {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState attackAnimationState = new AnimationState();
   public AnimationState attack2AnimationState = new AnimationState();

   public Draugr_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 5;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new Draugr_Entity.DraugrMeleeAttackGoal());
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, SnowGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
   }

   public static Builder draugr() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.25)
         .m_22268_(Attributes.f_22281_, 4.0)
         .m_22268_(Attributes.f_22276_, 28.0)
         .m_22268_(Attributes.f_22284_, 3.0)
         .m_22268_(Attributes.f_22278_, 0.05);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "attack") {
         return this.attackAnimationState;
      } else if (input == "attack2") {
         return this.attack2AnimationState;
      } else {
         return input == "idle" ? this.idleAnimationState : new AnimationState();
      }
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public void stopAllAnimationStates() {
      this.attackAnimationState.m_216973_();
      this.attack2AnimationState.m_216973_();
   }

   public HumanoidArm m_5737_() {
      return HumanoidArm.LEFT;
   }

   public void m_7822_(byte p_219360_) {
      if (p_219360_ == 4) {
         if (this.f_19796_.m_188499_()) {
            this.attackAnimationState.m_216977_(this.f_19797_);
         } else {
            this.attack2AnimationState.m_216977_(this.f_19797_);
         }
      } else {
         super.m_7822_(p_219360_);
      }
   }

   public boolean m_7327_(Entity p_219472_) {
      this.f_19853_.m_7605_(this, (byte)4);
      return super.m_7327_(p_219472_);
   }

   @Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_, @Nullable CompoundTag p_34092_
   ) {
      SpawnGroupData spawngroupdata = super.m_6518_(p_34088_, p_34089_, p_34090_, p_34091_, p_34092_);
      this.m_8061_(EquipmentSlot.MAINHAND, this.createSpawnWeapon());
      return spawngroupdata;
   }

   private ItemStack createSpawnWeapon() {
      return this.f_19796_.m_188499_() ? new ItemStack((ItemLike)ModItems.BLACK_STEEL_AXE.get()) : new ItemStack((ItemLike)ModItems.BLACK_STEEL_SWORD.get());
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, true, this.f_19797_);
      }
   }

   public void m_8107_() {
      super.m_8107_();
   }

   public void animateWhen(AnimationState state, boolean p_252220_, int p_249486_) {
      if (p_252220_) {
         state.m_216982_(p_249486_);
      } else {
         state.m_216973_();
      }
   }

   protected void m_7472_(DamageSource p_33574_, int p_33575_, boolean p_33576_) {
      super.m_7472_(p_33574_, p_33575_, p_33576_);
      if (p_33574_.m_7639_() instanceof Creeper creeper && creeper.m_32313_()) {
         creeper.m_32314_();
         this.m_19998_((ItemLike)ModItems.DRAUGR_HEAD.get());
      }
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
      return (SoundEvent)ModSounds.DRAUGR_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.DRAUGR_DEATH.get();
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.DRAUGR_IDLE.get();
   }

   class DraugrMeleeAttackGoal extends MeleeAttackGoal {
      public DraugrMeleeAttackGoal() {
         super(Draugr_Entity.this, 1.0, true);
      }

      protected double m_6639_(LivingEntity p_33377_) {
         float f = Draugr_Entity.this.m_20205_();
         return (double)(f * 2.25F * f * 2.25F + p_33377_.m_20205_());
      }
   }
}
