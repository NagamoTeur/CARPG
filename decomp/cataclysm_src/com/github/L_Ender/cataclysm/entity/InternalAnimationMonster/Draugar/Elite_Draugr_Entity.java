package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Internal_Animation_Monster;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Goals.Elite_DraugrAttackGoal;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Elite_Draugr_Entity extends Internal_Animation_Monster implements CrossbowAttackMob {
   private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.m_135353_(
      Elite_Draugr_Entity.class, EntityDataSerializers.f_135035_
   );
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState ReloadAnimationState = new AnimationState();
   public AnimationState attackAnimationState = new AnimationState();
   public AnimationState attack2AnimationState = new AnimationState();
   public AnimationState swingAnimationState = new AnimationState();
   public AnimationState Shoot1AnimationState = new AnimationState();
   public AnimationState Shoot2AnimationState = new AnimationState();

   public Elite_Draugr_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 5;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new Elite_Draugr_Entity.CrossBowReloadGoal(this, 0, 1, 1, 30, 15, 12.0F));
      this.f_21345_.m_25352_(0, new Elite_Draugr_Entity.ReloadedGoal(this, 1, 0, 20, 20.0F));
      this.f_21345_.m_25352_(0, new Elite_Draugr_Entity.CrossBowShootGoal(this, 0, 4, 0, 23, 15, 12.0F));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(4, new Elite_DraugrAttackGoal(this, 1.0, 12.0F, true));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, SnowGolem.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
   }

   public static Builder elite_draugr() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.27F)
         .m_22268_(Attributes.f_22281_, 5.0)
         .m_22268_(Attributes.f_22276_, 32.0)
         .m_22268_(Attributes.f_22284_, 3.0)
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
      } else if (input == "re_load") {
         return this.ReloadAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "swing") {
         return this.swingAnimationState;
      } else if (input == "shoot") {
         return this.Shoot1AnimationState;
      } else {
         return input == "shoot2" ? this.Shoot2AnimationState : new AnimationState();
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.ReloadAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.Shoot1AnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.swingAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.Shoot2AnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_CHARGING_CROSSBOW, false);
   }

   public void stopAllAnimationStates() {
      this.attackAnimationState.m_216973_();
      this.attack2AnimationState.m_216973_();
      this.ReloadAnimationState.m_216973_();
      this.swingAnimationState.m_216973_();
      this.Shoot1AnimationState.m_216973_();
      this.Shoot2AnimationState.m_216973_();
   }

   public HumanoidArm m_5737_() {
      return HumanoidArm.LEFT;
   }

   @Override
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
      return new ItemStack(Items.f_42717_);
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
         this.idleAnimationState.m_216982_(this.f_19797_);
      }
   }

   public void m_8107_() {
      super.m_8107_();
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

   public boolean isChargingCrossbow() {
      return (Boolean)this.f_19804_.m_135370_(IS_CHARGING_CROSSBOW);
   }

   public void m_6136_(boolean p_33302_) {
      this.f_19804_.m_135381_(IS_CHARGING_CROSSBOW, p_33302_);
   }

   public void m_5811_(LivingEntity p_32328_, ItemStack p_32329_, Projectile p_32330_, float p_32331_) {
      this.m_32322_(this, p_32328_, p_32330_, p_32331_, 1.6F);
   }

   public void m_5847_() {
      this.f_20891_ = 0;
   }

   public void m_6504_(LivingEntity p_33317_, float p_33318_) {
      this.m_32336_(this, 1.6F);
   }

   static class CrossBowReloadGoal extends Goal {
      protected final Elite_Draugr_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final float attackrange;
      private Elite_Draugr_Entity.CrossbowState crossbowState = Elite_Draugr_Entity.CrossbowState.UNCHARGED;

      public CrossBowReloadGoal(
         Elite_Draugr_Entity entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange
      ) {
         this.entity = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackseetick = attackseetick;
         this.attackrange = attackrange;
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && this.entity.m_217043_().m_188501_() * 100.0F < 22.0F
            && target.m_6084_()
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.isHoldingCrossbow()
            && !this.entity.isChargingCrossbow();
      }

      public void m_8056_() {
         this.entity.setAttackState(this.attackstate);
      }

      public void m_8041_() {
         this.entity.setAttackState(this.attackendstate);
      }

      public boolean m_8045_() {
         return this.entity.getAttackState() == this.attackstate && this.entity.attackTicks <= this.attackMaxtick;
      }

      private boolean isHoldingCrossbow() {
         return this.entity.m_21093_(is -> is.m_41720_() instanceof CrossbowItem);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.attackTicks == 5) {
            this.entity.m_6672_(ProjectileUtil.getWeaponHoldingHand(this.entity, item -> item instanceof CrossbowItem));
         }

         int i = this.entity.m_21252_();
         ItemStack itemstack = this.entity.m_21211_();
         if (i >= CrossbowItem.m_40939_(itemstack)) {
            this.entity.m_21253_();
            this.entity.m_6136_(true);
         }
      }

      public boolean m_183429_() {
         return true;
      }
   }

   static class CrossBowShootGoal extends Goal {
      protected final Elite_Draugr_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final float attackrange;

      public CrossBowShootGoal(
         Elite_Draugr_Entity entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange
      ) {
         this.entity = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackseetick = attackseetick;
         this.attackrange = attackrange;
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && this.entity.m_217043_().m_188501_() * 100.0F < 22.0F
            && target.m_6084_()
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.isHoldingCrossbow()
            && this.entity.isChargingCrossbow();
      }

      public void m_8056_() {
         this.entity.setAttackState(this.attackstate);
      }

      public void m_8041_() {
         this.entity.setAttackState(this.attackendstate);
      }

      public boolean m_8045_() {
         return this.entity.getAttackState() == this.attackstate && this.entity.attackTicks <= this.attackMaxtick;
      }

      private boolean isHoldingCrossbow() {
         return this.entity.m_21093_(is -> is.m_41720_() instanceof CrossbowItem);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (target != null && this.entity.attackTicks == 11) {
            this.entity.m_6504_(target, 1.0F);
            ItemStack itemstack1 = this.entity.m_21120_(ProjectileUtil.getWeaponHoldingHand(this.entity, item -> item instanceof CrossbowItem));
            CrossbowItem.m_40884_(itemstack1, false);
            this.entity.m_6136_(false);
         }
      }

      public boolean m_183429_() {
         return true;
      }
   }

   private static enum CrossbowState {
      UNCHARGED,
      CHARGING,
      CHARGED,
      READY_TO_ATTACK;
   }

   static class ReloadedGoal extends Goal {
      protected final Elite_Draugr_Entity entity;
      private final int getattackstate;
      private final int attackendstate;
      private final int attackseetick;
      private final float attackrange;

      public ReloadedGoal(Elite_Draugr_Entity entity, int getattackstate, int attackendstate, int attackseetick, float attackrange) {
         this.entity = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackendstate = attackendstate;
         this.attackseetick = attackseetick;
         this.attackrange = attackrange;
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && target.m_6084_()
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.isHoldingCrossbow()
            && this.entity.isChargingCrossbow();
      }

      public void m_8056_() {
         LivingEntity livingentity = this.entity.m_5448_();
         boolean flag = true;
         if (livingentity != null) {
            float f = livingentity.m_20205_();
            float dis = f * 2.5F * f * 2.5F + livingentity.m_20205_();
            double d0 = this.entity.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
            if (d0 <= (double)dis) {
               flag = false;
            }
         }

         if (flag) {
            this.entity.setAttackState(2);
         } else {
            this.entity.setAttackState(3);
         }
      }

      public void m_8041_() {
         this.entity.setAttackState(this.attackendstate);
      }

      public boolean m_8045_() {
         return (this.entity.getAttackState() == 2 || this.entity.getAttackState() == 3) && this.entity.attackTicks <= 30;
      }

      private boolean isHoldingCrossbow() {
         return this.entity.m_21093_(is -> is.m_41720_() instanceof CrossbowItem);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAttackState() == 2 && target != null && this.entity.attackTicks == 10) {
            this.entity.m_6504_(target, 1.0F);
            ItemStack itemstack1 = this.entity.m_21120_(ProjectileUtil.getWeaponHoldingHand(this.entity, item -> item instanceof CrossbowItem));
            CrossbowItem.m_40884_(itemstack1, false);
            this.entity.m_6136_(false);
         }

         if (this.entity.getAttackState() == 3 && target != null && this.entity.attackTicks == 11) {
            DamageSource damagesource = DamageSource.m_19370_(this.entity);
            target.m_6469_(damagesource, (float)this.entity.m_21133_(Attributes.f_22281_));
         }
      }

      public boolean m_183429_() {
         return true;
      }

      protected double getAttackReachSqr(LivingEntity p_25556_) {
         float f = p_25556_.m_20205_();
         return (double)(f * 2.5F * f * 2.5F + p_25556_.m_20205_());
      }
   }
}
