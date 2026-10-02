package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.AI.MobAIFindWater;
import com.github.L_Ender.cataclysm.entity.AI.MobAILeaveWater;
import com.github.L_Ender.cataclysm.entity.AI.SemiAquaticAIRandomSwimming;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Portal_Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.Pet.AI.PetSimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.Pet.AI.TameableAIFollowOwnerWater;
import com.github.L_Ender.cataclysm.entity.etc.ISemiAquatic;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.GroundPathNavigatorWide;
import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.entity.projectile.Mini_Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class The_Baby_Leviathan_Entity extends LLibraryAnimationPet implements ISemiAquatic, Bucketable {
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.m_135353_(The_Baby_Leviathan_Entity.class, EntityDataSerializers.f_135035_);
   public static final Animation BABY_LEVIATHAN_BITE = Animation.create(14);
   public static final Animation BABY_LEVIATHAN_ABYSS_BLAST = Animation.create(157);
   public float sitProgress;
   public float prevSitProgress;
   public float SwimProgress;
   public float prevSwimProgress;
   private int fishFeedings;
   private boolean isLandNavigator;
   private The_Baby_Leviathan_Entity.AttackMode mode = The_Baby_Leviathan_Entity.AttackMode.CIRCLE;
   private int blast_cooldown = 0;
   public static final int BLAST_HUNTING_COOLDOWN = 100;
   public double endPosX;
   public double endPosY;
   public double endPosZ;
   public double collidePosX;
   public double collidePosY;
   public double collidePosZ;

   public The_Baby_Leviathan_Entity(EntityType type, Level world) {
      super(type, world);
      this.f_21364_ = 0;
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.m_21441_(BlockPathTypes.WATER_BORDER, 0.0F);
      this.switchNavigator(false);
      setConfigattribute(this, CMConfig.BabyLeviathanHealthMultiplier, CMConfig.BabyLeviathanDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.0F;
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.LEVIATHAN_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.LEVIATHAN_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.LEVIATHAN_DEFEAT.get();
   }

   public float m_6100_() {
      return (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2F + 2.0F;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      Entity entity = source.m_7640_();
      return !(entity instanceof Mini_Abyss_Blast_Entity) && !(entity instanceof Abyss_Blast_Entity) && !(entity instanceof Portal_Abyss_Blast_Entity)
         ? super.m_6469_(source, amount)
         : false;
   }

   public static Builder babyleviathan() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 120.0)
         .m_22268_(Attributes.f_22278_, 0.5)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22277_, 32.0)
         .m_22268_(Attributes.f_22281_, 4.0)
         .m_22268_(Attributes.f_22279_, 0.2F);
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(2, new TameableAIFollowOwnerWater(this, 1.3, 4.0F, 2.0F, true));
      this.f_21345_.m_25352_(3, new The_Baby_Leviathan_Entity.BabyLeviathanAttackGoal(this));
      this.f_21345_.m_25352_(0, new The_Baby_Leviathan_Entity.BabyLeviathanBiteAttackGoal(this, BABY_LEVIATHAN_BITE));
      this.f_21345_.m_25352_(0, new The_Baby_Leviathan_Entity.BabyLeviathanBlastAttackGoal(this, BABY_LEVIATHAN_ABYSS_BLAST));
      this.f_21345_.m_25352_(4, new MobAIFindWater(this, 1.0));
      this.f_21345_.m_25352_(4, new MobAILeaveWater(this));
      this.f_21345_.m_25352_(6, new TemptGoal(this, 1.0, Ingredient.m_43929_(new ItemLike[]{Items.f_42528_}), false));
      this.f_21345_.m_25352_(7, new SemiAquaticAIRandomSwimming(this, 1.0, 30));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
      this.f_21346_
         .m_25352_(
            3, new EntityAINearestTarget3D(this, LivingEntity.class, 120, false, true, ModEntities.buildPredicateFromTag(ModTag.BABY_LEVIATHAN_TARGET)) {
               public boolean m_8036_() {
                  return The_Baby_Leviathan_Entity.this.getCommand() != 2 && !The_Baby_Leviathan_Entity.this.isSitting() && super.m_8036_();
               }
            }
         );
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new GroundPathNavigatorWide(this, this.f_19853_);
         this.isLandNavigator = true;
      } else {
         this.f_21342_ = new The_Baby_Leviathan_Entity.BabyLeviathanMoveController(this, 3.0F, 1.0F, 10.0F);
         this.f_21344_ = new SemiAquaticPathNavigator(this, this.f_19853_);
         this.isLandNavigator = false;
      }
   }

   public void m_7023_(Vec3 travelVector) {
      if (this.isSitting()) {
         if (this.m_21573_().m_26570_() != null) {
            this.m_21573_().m_26573_();
         }

         travelVector = Vec3.f_82478_;
         super.m_7023_(travelVector);
      } else {
         if (this.m_6142_() && this.m_20069_()) {
            this.m_19920_(this.m_6113_(), travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
            if (this.m_5448_() == null && this.getAnimation() == NO_ANIMATION) {
               this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
         } else {
            super.m_7023_(travelVector);
         }
      }
   }

   public boolean m_6673_(DamageSource source) {
      return source == DamageSource.f_19310_ || source == DamageSource.f_19322_ || super.m_6673_(source);
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
      this.m_7380_(platTag);
      CompoundTag compound = bucket.m_41784_();
      Bucketable.m_148822_(this, bucket);
      compound.m_128365_("BabyLeviathanData", platTag);
   }

   public void m_142278_(CompoundTag p_148832_) {
      Bucketable.m_148825_(this, p_148832_);
      if (p_148832_.m_128441_("BabyLeviathanData")) {
         this.m_7378_(p_148832_.m_128469_("BabyLeviathanData"));
      }
   }

   @Nonnull
   public ItemStack m_28282_() {
      return new ItemStack((ItemLike)ModItems.THE_BABY_LEVIATHAN_BUCKET.get());
   }

   public SoundEvent m_142623_() {
      return SoundEvents.f_11782_;
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      Item item = itemstack.m_41720_();
      InteractionResult type = super.m_6071_(player, hand);
      if (!this.m_21824_() && item == Items.f_42528_) {
         this.m_142075_(player, hand, itemstack);
         this.m_146850_(GameEvent.f_157806_);
         this.fishFeedings++;
         if ((this.fishFeedings > 10 && this.m_217043_().m_188503_(6) == 0 || this.fishFeedings > 30) && !ForgeEventFactory.onAnimalTame(this, player)) {
            this.m_21828_(player);
            this.f_19853_.m_7605_(this, (byte)7);
         } else {
            this.f_19853_.m_7605_(this, (byte)6);
         }

         return InteractionResult.SUCCESS;
      } else if (this.m_21824_() && itemstack.m_204117_(ItemTags.f_13156_)) {
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
            Optional<InteractionResult> result = Bucketable.m_148828_(player, hand, this);
            if (result.isPresent()) {
               return result.get();
            }
         }

         InteractionResult interactionresult = itemstack.m_41647_(player, this, hand);
         if (interactionresult != InteractionResult.SUCCESS
            && type != InteractionResult.SUCCESS
            && this.m_21824_()
            && this.m_21830_(player)
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

   public void m_8107_() {
      super.m_8107_();
      if (this.isSitting() && this.m_21573_().m_26571_()) {
         this.m_21573_().m_26573_();
      }

      boolean swim = this.m_20069_();
      this.prevSwimProgress = this.SwimProgress;
      this.prevSitProgress = this.sitProgress;
      if (this.SwimProgress < 5.0F && swim) {
         this.SwimProgress++;
      }

      if (this.SwimProgress > 0.0F && !swim) {
         this.SwimProgress--;
      }

      if (this.isSitting() && this.sitProgress < 5.0F) {
         this.sitProgress++;
      }

      if (!this.isSitting() && this.sitProgress > 0.0F) {
         this.sitProgress--;
      }

      if (this.m_20069_() && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!this.m_20069_() && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (this.blast_cooldown > 0) {
         this.blast_cooldown--;
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.getAnimation() == BABY_LEVIATHAN_BITE) {
         if (this.getAnimationTick() == 7) {
            this.m_5496_((SoundEvent)ModSounds.LEVIATHAN_BITE.get(), 0.5F, 2.0F);
         }

         if (this.getAnimationTick() == 9) {
            this.biteattack(1.5F, 0.5, 0.5, 0.5);
         }
      }
   }

   private void biteattack(float radius, double inflateX, double inflateY, double inflateZ) {
      double renderYaw = (double)(this.f_20885_ + 90.0F) * Math.PI / 180.0;
      double renderPitch = (double)((float)((double)(-this.m_146909_()) * Math.PI / 180.0));
      this.endPosX = this.m_20185_() + (double)radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.m_20189_() + (double)radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.m_20186_() + (double)radius * Math.sin(renderPitch);
      if (!this.f_19853_.f_46443_) {
         for (LivingEntity target : this.raytraceEntities(
               this.f_19853_,
               inflateX,
               inflateY,
               inflateZ,
               new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_()),
               new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities) {
            if (!this.m_7307_(target) && !(target instanceof The_Baby_Leviathan_Entity) && target != this) {
               target.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_));
            }
         }
      }
   }

   private The_Baby_Leviathan_Entity.BiteHitResult raytraceEntities(Level world, double inflateX, double inflateY, double inflateZ, Vec3 from, Vec3 to) {
      The_Baby_Leviathan_Entity.BiteHitResult result = new The_Baby_Leviathan_Entity.BiteHitResult();
      this.collidePosX = this.endPosX;
      this.collidePosY = this.endPosY;
      this.collidePosZ = this.endPosZ;

      for (LivingEntity entity : world.m_45976_(
         LivingEntity.class,
         new AABB(
               Math.min(this.m_20185_(), this.collidePosX),
               Math.min(this.m_20186_(), this.collidePosY),
               Math.min(this.m_20189_(), this.collidePosZ),
               Math.max(this.m_20185_(), this.collidePosX),
               Math.max(this.m_20186_(), this.collidePosY),
               Math.max(this.m_20189_(), this.collidePosZ)
            )
            .m_82377_(inflateX, inflateY, inflateZ)
      )) {
         float pad = 2.5F;
         AABB aabb = entity.m_20191_().m_82377_((double)pad, (double)pad, (double)pad);
         Optional<Vec3> hit = aabb.m_82371_(from, to);
         if (aabb.m_82390_(from)) {
            result.addEntityHit(entity);
         } else if (hit.isPresent()) {
            result.addEntityHit(entity);
         }
      }

      return result;
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

   public boolean m_6063_() {
      return false;
   }

   @Nullable
   @Override
   public AgeableMob m_142606_(ServerLevel serverWorld, AgeableMob ageableEntity) {
      return null;
   }

   @Override
   public boolean shouldEnterWater() {
      return !this.isSitting();
   }

   @Override
   public boolean shouldLeaveWater() {
      return false;
   }

   @Override
   public boolean shouldStopMoving() {
      return this.isSitting();
   }

   @Override
   public int getWaterSearchRange() {
      return 32;
   }

   @Override
   public boolean shouldFollow() {
      return this.getCommand() == 1;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, BABY_LEVIATHAN_ABYSS_BLAST, BABY_LEVIATHAN_BITE};
   }

   private static enum AttackMode {
      CIRCLE,
      MELEE,
      RANGE;
   }

   class BabyLeviathanAttackGoal extends Goal {
      private final The_Baby_Leviathan_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private float circleDistance = 4.0F;
      private boolean clockwise = false;
      private float MeleeModeTime = 0.0F;
      private static final int MELEE_MODE_TIME = 100;

      public BabyLeviathanAttackGoal(The_Baby_Leviathan_Entity mob) {
         this.mob = mob;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         this.target = this.mob.m_5448_();
         return this.target != null && this.target.m_6084_() && this.mob.getAnimation() == IAnimatedEntity.NO_ANIMATION;
      }

      public boolean m_8045_() {
         this.target = this.mob.m_5448_();
         return this.target != null;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = (float)(8 + this.mob.f_19796_.m_188503_(2));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.mob.m_21561_(true);
      }

      public void m_8041_() {
         this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = (float)(8 + this.mob.f_19796_.m_188503_(2));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.target = this.mob.m_5448_();
         if (!EntitySelector.f_20406_.test(this.target)) {
            this.mob.m_6710_((LivingEntity)null);
         }

         this.mob.m_21573_().m_26573_();
         if (this.mob.m_5448_() == null) {
            this.mob.m_21561_(false);
            this.mob.m_21573_().m_26573_();
         }
      }

      public void m_8037_() {
         LivingEntity target = this.mob.m_5448_();
         if (target != null) {
            if (this.mob.mode == The_Baby_Leviathan_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               The_Baby_Leviathan_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (0 >= this.mob.blast_cooldown) {
                  this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.RANGE;
               } else {
                  this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.MELEE;
               }
            } else if (this.mob.mode == The_Baby_Leviathan_Entity.AttackMode.RANGE) {
               if (this.mob.m_217043_().m_188501_() * 100.0F < 3.0F) {
                  this.mob.setAnimation(The_Baby_Leviathan_Entity.BABY_LEVIATHAN_ABYSS_BLAST);
               }
            } else if (this.mob.mode == The_Baby_Leviathan_Entity.AttackMode.MELEE) {
               this.MeleeModeTime++;
               this.mob.m_21573_().m_5624_(target, 1.0);
               this.mob.m_21563_().m_24960_(target, 30.0F, 90.0F);
               if (this.MeleeModeTime >= 100.0F) {
                  this.mob.mode = The_Baby_Leviathan_Entity.AttackMode.RANGE;
               } else if (this.mob.m_20280_(target) <= 4.0) {
                  this.mob.setAnimation(The_Baby_Leviathan_Entity.BABY_LEVIATHAN_BITE);
               }
            }
         }
      }
   }

   static class BabyLeviathanBiteAttackGoal extends PetSimpleAnimationGoal<The_Baby_Leviathan_Entity> {
      public BabyLeviathanBiteAttackGoal(The_Baby_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 9 && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8041_() {
         super.m_8041_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }
      }
   }

   static class BabyLeviathanBlastAttackGoal extends PetSimpleAnimationGoal<The_Baby_Leviathan_Entity> {
      public BabyLeviathanBlastAttackGoal(The_Baby_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.f_19853_.m_6269_((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST.get(), SoundSource.NEUTRAL, 1.0F, 2.0F);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
            this.entity.m_21391_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.blast_cooldown = 100;
      }

      public void m_8037_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
            this.entity.m_21391_(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if (this.entity.getAnimationTick() == 37 && !this.entity.f_19853_.f_46443_) {
            Mini_Abyss_Blast_Entity DeathBeam = new Mini_Abyss_Blast_Entity(
               (EntityType<? extends Mini_Abyss_Blast_Entity>)ModEntities.MINI_ABYSS_BLAST.get(),
               this.entity.f_19853_,
               this.entity,
               this.entity.m_20185_(),
               this.entity.m_20186_(),
               this.entity.m_20189_(),
               (float)((double)(this.entity.f_20885_ + dir) * Math.PI / 180.0),
               (float)((double)(-this.entity.m_146909_()) * Math.PI / 180.0),
               80,
               dir
            );
            this.entity.f_19853_.m_7967_(DeathBeam);
         }
      }
   }

   static class BabyLeviathanMoveController extends MoveControl {
      private final The_Baby_Leviathan_Entity entity;
      private final float speedMulti;
      private final float ySpeedMod;
      private final float yawLimit;
      private int stillTicks = 0;

      public BabyLeviathanMoveController(The_Baby_Leviathan_Entity entity, float speedMulti, float ySpeedMod, float yawLimit) {
         super(entity);
         this.entity = entity;
         this.speedMulti = speedMulti;
         this.ySpeedMod = ySpeedMod;
         this.yawLimit = yawLimit;
      }

      public void m_8126_() {
         if (this.entity.m_20069_() && this.entity.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            if (Math.abs(this.entity.f_19854_ - this.entity.m_20185_()) < 0.01F
               && Math.abs(this.entity.f_19855_ - this.entity.m_20186_()) < 0.01F
               && Math.abs(this.entity.f_19856_ - this.entity.m_20189_()) < 0.01F) {
               this.stillTicks++;
            } else {
               this.stillTicks = 0;
            }

            if (this.stillTicks > 40) {
               this.entity.m_20256_(this.entity.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
         }

         if (this.entity.shouldStopMoving()) {
            this.entity.m_7910_(0.0F);
         } else {
            if (this.f_24981_ == Operation.MOVE_TO && !this.entity.m_21573_().m_26571_()) {
               double lvt_1_1_ = this.f_24975_ - this.entity.m_20185_();
               double lvt_3_1_ = this.f_24976_ - this.entity.m_20186_();
               double lvt_5_1_ = this.f_24977_ - this.entity.m_20189_();
               double lvt_7_1_ = lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_;
               if (lvt_7_1_ < 2.5000003E-7F) {
                  this.f_24974_.m_21564_(0.0F);
               } else {
                  float lvt_9_1_ = (float)(Mth.m_14136_(lvt_5_1_, lvt_1_1_) * 180.0F / (float)Math.PI) - 90.0F;
                  this.entity.m_146922_(this.m_24991_(this.entity.m_146908_(), lvt_9_1_, this.yawLimit));
                  this.entity.f_20883_ = this.entity.m_146908_();
                  this.entity.f_20885_ = this.entity.m_146908_();
                  float lvt_10_1_ = (float)(this.f_24978_ * (double)this.speedMulti * 3.0 * this.entity.m_21133_(Attributes.f_22279_));
                  if (this.entity.m_20069_()) {
                     if (lvt_3_1_ > 0.0 && this.entity.f_19862_) {
                        this.entity.m_20256_(this.entity.m_20184_().m_82520_(0.0, 0.08F, 0.0));
                     } else {
                        this.entity
                           .m_20256_(this.entity.m_20184_().m_82520_(0.0, (double)this.entity.m_6113_() * lvt_3_1_ * 0.6 * (double)this.ySpeedMod, 0.0));
                     }

                     this.entity.m_7910_(lvt_10_1_ * 0.02F);
                     float lvt_11_1_ = -(
                        (float)(Mth.m_14136_(lvt_3_1_, (double)Mth.m_14116_((float)(lvt_1_1_ * lvt_1_1_ + lvt_5_1_ * lvt_5_1_))) * 180.0F / (float)Math.PI)
                     );
                     lvt_11_1_ = Mth.m_14036_(Mth.m_14177_(lvt_11_1_), -85.0F, 85.0F);
                     this.entity.m_146926_(this.m_24991_(this.entity.m_146909_(), lvt_11_1_, 5.0F));
                     float lvt_12_1_ = Mth.m_14089_(this.entity.m_146909_() * (float) (Math.PI / 180.0));
                     float lvt_13_1_ = Mth.m_14031_(this.entity.m_146909_() * (float) (Math.PI / 180.0));
                     this.entity.f_20902_ = lvt_12_1_ * lvt_10_1_;
                     this.entity.f_20901_ = -lvt_13_1_ * lvt_10_1_;
                  } else {
                     this.entity.m_7910_(lvt_10_1_ * 0.1F);
                  }
               }
            } else {
               this.entity.m_7910_(0.0F);
               this.entity.m_21570_(0.0F);
               this.entity.m_21567_(0.0F);
               this.entity.m_21564_(0.0F);
            }
         }
      }
   }

   public static class BiteHitResult {
      private final List<LivingEntity> entities = new ArrayList<>();

      public void addEntityHit(LivingEntity entity) {
         this.entities.add(entity);
      }
   }
}
