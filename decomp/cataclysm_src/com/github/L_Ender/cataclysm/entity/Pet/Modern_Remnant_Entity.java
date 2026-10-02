package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.Pet.AI.TameableAIFollowOwner;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import com.min01.archaeology.init.ArchaeologyItems;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Modern_Remnant_Entity extends LLibraryAnimationPet implements Bucketable {
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.m_135353_(Modern_Remnant_Entity.class, EntityDataSerializers.f_135035_);
   public float sitProgress;
   public float prevSitProgress;
   private Modern_Remnant_Entity.AttackMode mode = Modern_Remnant_Entity.AttackMode.CIRCLE;
   public static final Animation MODERN_REMNANT_BITE = Animation.create(11);

   public Modern_Remnant_Entity(EntityType type, Level world) {
      super(type, world);
      this.f_21364_ = 0;
      setConfigattribute(this, CMConfig.ModernRemnantHealthMultiplier, CMConfig.ModernRemnantDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.0F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.REMNANT_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.REMNANT_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.MODERN_REMNANT_DEATH.get();
   }

   public float m_6100_() {
      return (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2F + 2.0F;
   }

   public static Builder modernremnant() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 150.0)
         .m_22268_(Attributes.f_22278_, 0.5)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22277_, 32.0)
         .m_22268_(Attributes.f_22281_, 6.0)
         .m_22268_(Attributes.f_22279_, 0.4F);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(3, new Modern_Remnant_Entity.ModernRemnantAIMelee(this));
      this.f_21345_.m_25352_(6, new TameableAIFollowOwner(this, 1.3, 6.0F, 2.0F, true));
      this.f_21345_.m_25352_(6, new TemptGoal(this, 1.0, Ingredient.m_43929_(new ItemLike[]{(ItemLike)ArchaeologyItems.BRUSH.get()}), false));
      this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0, 60));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(5, new NonTameRandomTargetGoal(this, LivingEntity.class, false, ModEntities.buildPredicateFromTag(ModTag.MODERN_REMNANT_TARGET)));
   }

   public void m_7023_(Vec3 vec3d) {
      if (this.isSitting()) {
         if (this.m_21573_().m_26570_() != null) {
            this.m_21573_().m_26573_();
         }

         vec3d = Vec3.f_82478_;
      }

      super.m_7023_(vec3d);
   }

   public boolean m_6673_(DamageSource source) {
      return source == DamageSource.f_19310_ || source == DamageSource.f_19322_ || super.m_6673_(source) || source.m_146707_();
   }

   public boolean m_6040_() {
      return true;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(FROM_BUCKET, false);
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("FromBucket", this.m_27487_());
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.m_27497_(compound.m_128471_("FromBucket"));
   }

   public boolean m_27487_() {
      return (Boolean)this.f_19804_.m_135370_(FROM_BUCKET);
   }

   public void m_27497_(boolean sit) {
      this.f_19804_.m_135381_(FROM_BUCKET, sit);
   }

   public void m_6872_(@Nonnull ItemStack bucket) {
      CompoundTag platTag = new CompoundTag();
      CompoundTag compound = bucket.m_41784_();
      this.m_7380_(platTag);
      Bucketable.m_148822_(this, bucket);
      compound.m_128365_("ModernRemnantData", platTag);
   }

   public void m_142278_(CompoundTag p_148832_) {
      Bucketable.m_148825_(this, p_148832_);
      if (p_148832_.m_128441_("ModernRemnantData")) {
         this.m_7378_(p_148832_.m_128469_("ModernRemnantData"));
      }
   }

   @Nonnull
   public ItemStack m_28282_() {
      return new ItemStack((ItemLike)ModItems.MODERN_REMNANT_BUCKET.get());
   }

   public SoundEvent m_142623_() {
      return (SoundEvent)ModSounds.MODERN_REMNANT_FILL_BUCKET.get();
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      Item item = itemstack.m_41720_();
      InteractionResult type = super.m_6071_(player, hand);
      if (item == ArchaeologyItems.BRUSH.get()) {
         player.m_6672_(hand);
      }

      if (this.m_21824_() && itemstack.m_204117_(ModTag.BONE_ITEM)) {
         if (this.m_21223_() < this.m_21233_()) {
            this.m_142075_(player, hand, itemstack);
            this.m_146850_(GameEvent.f_157806_);
            this.m_5634_(5.0F);
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.PASS;
         }
      } else {
         if (this.m_21824_()) {
            Optional<InteractionResult> result = emptybucketMobPickup(player, hand, this);
            if (result.isPresent()) {
               return result.get();
            }
         }

         InteractionResult interactionresult = itemstack.m_41647_(player, this, hand);
         if (interactionresult != InteractionResult.SUCCESS
            && type != InteractionResult.SUCCESS
            && this.m_21824_()
            && this.m_21830_(player)
            && item != ArchaeologyItems.BRUSH.get()
            && !player.m_6144_()) {
            this.setCommand(this.getCommand() + 1);
            if (this.getCommand() == 3) {
               this.setCommand(0);
            }

            player.m_5661_(Component.m_237110_("entity.cataclysm.all.command_" + this.getCommand(), new Object[]{this.m_7755_()}), true);
            boolean sit = this.getCommand() == 2;
            if (sit) {
               this.m_21839_(true);
               return InteractionResult.SUCCESS;
            } else {
               this.m_21839_(false);
               return InteractionResult.SUCCESS;
            }
         } else {
            return type;
         }
      }
   }

   private static <T extends LivingEntity & Bucketable> Optional<InteractionResult> emptybucketMobPickup(
      Player p_148829_, InteractionHand p_148830_, T p_148831_
   ) {
      ItemStack itemstack = p_148829_.m_21120_(p_148830_);
      if (itemstack.m_41720_() == Items.f_42446_ && p_148831_.m_6084_()) {
         p_148831_.m_5496_(p_148831_.m_142623_(), 1.0F, 1.0F);
         ItemStack itemstack1 = p_148831_.m_28282_();
         p_148831_.m_6872_(itemstack1);
         ItemStack itemstack2 = ItemUtils.m_41817_(itemstack, p_148829_, itemstack1, false);
         p_148829_.m_21008_(p_148830_, itemstack2);
         Level level = p_148831_.f_19853_;
         if (!level.f_46443_) {
            CriteriaTriggers.f_10576_.m_38772_((ServerPlayer)p_148829_, itemstack1);
         }

         p_148831_.m_146870_();
         return Optional.of(InteractionResult.m_19078_(level.f_46443_));
      } else {
         return Optional.empty();
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.isSitting() && this.m_21573_().m_26571_()) {
         this.m_21573_().m_26573_();
      }

      this.prevSitProgress = this.sitProgress;
      if (this.isSitting() && this.sitProgress < 10.0F) {
         this.sitProgress++;
      }

      if (!this.isSitting() && this.sitProgress > 0.0F) {
         this.sitProgress--;
      }

      if (this.getAnimation() == MODERN_REMNANT_BITE && this.getAnimationTick() == 3) {
         this.m_5496_((SoundEvent)ModSounds.MODERN_REMNANT_BITE.get(), 0.5F, 2.0F);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   public boolean m_7307_(Entity entityIn) {
      if (this.m_21824_()) {
         LivingEntity livingentity = this.m_21826_();
         if (entityIn == livingentity) {
            return true;
         }

         if (entityIn instanceof TamableAnimal) {
            return ((TamableAnimal)entityIn).m_21830_(livingentity);
         }

         if (livingentity != null) {
            return livingentity.m_7307_(entityIn);
         }
      }

      return super.m_7307_(entityIn);
   }

   @Nullable
   @Override
   public AgeableMob m_142606_(ServerLevel serverWorld, AgeableMob ageableEntity) {
      return null;
   }

   @Override
   public boolean shouldFollow() {
      return this.getCommand() == 1;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, MODERN_REMNANT_BITE};
   }

   private static enum AttackMode {
      CIRCLE,
      MELEE;
   }

   class ModernRemnantAIMelee extends Goal {
      private final Modern_Remnant_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private int maxcirclingTime = 0;
      private float circleDistance = 13.0F;
      private boolean clockwise = false;
      private float MeleeModeTime = 0.0F;
      private static final int MELEE_MODE_TIME = 120;

      public ModernRemnantAIMelee(Modern_Remnant_Entity mob) {
         this.mob = mob;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         this.target = this.mob.m_5448_();
         return this.target != null && this.target.m_6084_();
      }

      public boolean m_8045_() {
         this.target = this.mob.m_5448_();
         return this.target != null;
      }

      public void m_8056_() {
         this.mob.mode = Modern_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.maxcirclingTime = 120 + this.mob.f_19796_.m_188503_(40);
         this.circleDistance = (float)(8 + this.mob.f_19796_.m_188503_(4));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.MeleeModeTime = 0.0F;
         this.mob.m_21561_(true);
      }

      public void m_8041_() {
         this.mob.mode = Modern_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.maxcirclingTime = 120 + this.mob.f_19796_.m_188503_(40);
         this.circleDistance = (float)(8 + this.mob.f_19796_.m_188503_(4));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.target = this.mob.m_5448_();
         if (!EntitySelector.f_20406_.test(this.target)) {
            this.mob.m_6710_(null);
         }

         this.MeleeModeTime = 0.0F;
         this.mob.m_21573_().m_26573_();
         if (this.mob.m_5448_() == null) {
            this.mob.m_21561_(false);
            this.mob.m_21573_().m_26573_();
         }
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         LivingEntity target = this.mob.m_5448_();
         if (target != null) {
            if (this.mob.mode == Modern_Remnant_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               Modern_Remnant_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (this.circlingTime >= this.maxcirclingTime) {
                  this.mob.mode = Modern_Remnant_Entity.AttackMode.MELEE;
               }

               if (target.m_20270_(this.mob) < 5.0F) {
                  this.mob.mode = Modern_Remnant_Entity.AttackMode.MELEE;
               }
            } else if (this.mob.mode == Modern_Remnant_Entity.AttackMode.MELEE) {
               this.MeleeModeTime++;
               this.mob.m_21573_().m_5624_(target, 1.0);
               this.mob.m_21563_().m_24960_(target, 30.0F, 30.0F);
               if (this.MeleeModeTime >= 120.0F) {
                  this.m_8056_();
               } else if (this.mob.m_20280_(target) <= 4.0 && this.mob.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
                  this.mob.setAnimation(Modern_Remnant_Entity.MODERN_REMNANT_BITE);
               }
            }

            if (this.mob.getAnimation() == Modern_Remnant_Entity.MODERN_REMNANT_BITE
               && this.mob.getAnimationTick() == 5
               && this.mob.m_20270_(target) < this.mob.m_20205_() * 2.5F * this.mob.m_20205_() * 2.5F + target.m_20205_()) {
               float damage = (float)this.mob.m_21133_(Attributes.f_22281_);
               target.m_6469_(DamageSource.m_19370_(this.mob), damage);
            }
         }
      }
   }
}
