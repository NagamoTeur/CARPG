package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Goals.Royal_DraugrAttackGoal;
import com.github.L_Ender.cataclysm.entity.etc.IShieldEntity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;

public class Royal_Draugr_Entity extends Monster implements IShieldEntity {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState attackAnimationState = new AnimationState();
   public AnimationState attack2AnimationState = new AnimationState();
   private int shieldCooldownTime = 0;

   public Royal_Draugr_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 7;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new Royal_DraugrAttackGoal(this, 1.0, true));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, SnowGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
   }

   public static Builder royal_draugr() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.27F)
         .m_22268_(Attributes.f_22281_, 5.0)
         .m_22268_(Attributes.f_22276_, 30.0)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22278_, 0.1);
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

   public boolean m_6469_(DamageSource source, float damage) {
      Entity entity = source.m_7640_();
      if (damage > 0.0F && this.canBlockDamageSource(source)) {
         this.m_7909_(damage);
         if (!source.m_19360_() && entity instanceof LivingEntity) {
            this.m_6728_((LivingEntity)entity);
         }

         this.m_5496_(SoundEvents.f_12346_, 1.0F, 0.8F + this.f_19853_.f_46441_.m_188501_() * 0.4F);
         return false;
      } else {
         return super.m_6469_(source, damage);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   @Override
   public int getShieldCooldownTime() {
      return this.shieldCooldownTime;
   }

   @Override
   public void setShieldCooldownTime(int shieldCooldownTime) {
      this.shieldCooldownTime = shieldCooldownTime;
   }

   @Override
   public boolean isShieldDisabled() {
      return this.shieldCooldownTime > 0;
   }

   @Override
   public void disableShield(boolean guaranteeDisable) {
      float f = 0.25F + (float)EnchantmentHelper.m_44926_(this) * 0.05F;
      if (guaranteeDisable) {
         f += 0.75F;
      }

      if (this.f_19796_.m_188501_() < f) {
         this.shieldCooldownTime = 100;
         this.m_5810_();
         this.f_19853_.m_7605_(this, (byte)30);
         this.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + this.f_19853_.f_46441_.m_188501_() * 0.4F);
      }
   }

   protected void m_7909_(float p_36383_) {
      if (this.f_20935_.canPerformAction(ToolActions.SHIELD_BLOCK) && p_36383_ >= 3.0F) {
         int i = 1 + Mth.m_14143_(p_36383_);
         InteractionHand interactionhand = this.m_7655_();
         this.f_20935_.m_41622_(i, this, p_219739_ -> p_219739_.m_21190_(interactionhand));
         if (this.f_20935_.m_41619_()) {
            this.m_5810_();
            if (interactionhand == InteractionHand.MAIN_HAND) {
               this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            } else {
               this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
            }

            this.f_20935_ = ItemStack.f_41583_;
            this.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + this.f_19853_.f_46441_.m_188501_() * 0.4F);
         }
      }
   }

   public boolean m_21254_() {
      return false;
   }

   protected void m_6728_(LivingEntity p_36295_) {
      super.m_6728_(p_36295_);
      if (p_36295_.m_21205_().canDisableShield(this.f_20935_, this, p_36295_)) {
         this.disableShield(true);
      }
   }

   public boolean isDraugrBlocking() {
      if (this.m_6117_() && !this.f_20935_.m_41619_()) {
         Item item = this.f_20935_.m_41720_();
         return !this.f_20935_.canPerformAction(ToolActions.SHIELD_BLOCK) ? false : item.m_8105_(this.f_20935_) - this.f_20936_ >= 5;
      } else {
         return false;
      }
   }

   private boolean canBlockDamageSource(DamageSource damageSourceIn) {
      Entity entity = damageSourceIn.m_7640_();
      boolean flag = false;
      if (entity instanceof AbstractArrow abstractarrow && abstractarrow.m_36796_() > 0) {
         flag = true;
      }

      if (!damageSourceIn.m_19376_() && this.isDraugrBlocking() && !flag) {
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
      if (this.isDraugrBlocking()) {
         this.m_5810_();
         this.setShieldCooldownTime(30);
      }

      return super.m_7327_(p_219472_);
   }

   protected void m_7472_(DamageSource p_33574_, int p_33575_, boolean p_33576_) {
      super.m_7472_(p_33574_, p_33575_, p_33576_);
      if (p_33574_.m_7639_() instanceof Creeper creeper && creeper.m_32313_()) {
         creeper.m_32314_();
         this.m_19998_((ItemLike)ModItems.DRAUGR_HEAD.get());
      }
   }

   @Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_, @Nullable CompoundTag p_34092_
   ) {
      SpawnGroupData spawngroupdata = super.m_6518_(p_34088_, p_34089_, p_34090_, p_34091_, p_34092_);
      RandomSource randomsource = p_34088_.m_213780_();
      this.m_213945_(randomsource, p_34089_);
      this.m_213946_(randomsource, p_34089_);
      this.m_8061_(EquipmentSlot.OFFHAND, this.createSpawnShiled());
      return spawngroupdata;
   }

   protected void m_213945_(RandomSource p_219059_, DifficultyInstance p_219060_) {
      this.m_8061_(EquipmentSlot.MAINHAND, this.createSpawnWeapon());
   }

   private ItemStack createSpawnWeapon() {
      return this.f_19796_.m_188499_() ? new ItemStack((ItemLike)ModItems.BLACK_STEEL_AXE.get()) : new ItemStack((ItemLike)ModItems.BLACK_STEEL_SWORD.get());
   }

   private ItemStack createSpawnShiled() {
      return new ItemStack((ItemLike)ModItems.BLACK_STEEL_TARGE.get());
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

      if (this.shieldCooldownTime > 0) {
         this.shieldCooldownTime--;
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
}
