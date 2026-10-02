package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.entity.ai.DirectPathNavigator;
import com.github.alexthe666.alexsmobs.entity.ai.EndergradeAIBreakFlowers;
import com.github.alexthe666.alexsmobs.entity.ai.EndergradeAITargetItems;
import com.github.alexthe666.alexsmobs.entity.ai.TameableAIRide;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class EntityEndergrade extends Animal implements FlyingAnimal {
   private static final EntityDataAccessor<Integer> BITE_TICK = SynchedEntityData.m_135353_(EntityEndergrade.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.m_135353_(EntityEndergrade.class, EntityDataSerializers.f_135035_);
   public float tartigradePitch = 0.0F;
   public float prevTartigradePitch = 0.0F;
   public float biteProgress = 0.0F;
   public float prevBiteProgress = 0.0F;
   public boolean stopWandering = false;
   public boolean hasItemTarget = false;

   protected EntityEndergrade(EntityType type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new EntityEndergrade.MoveHelperController(this);
   }

   public static Builder bakeAttributes() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22276_, 20.0)
         .m_22268_(Attributes.f_22284_, 0.0)
         .m_22268_(Attributes.f_22281_, 2.0)
         .m_22268_(Attributes.f_22279_, 0.15F);
   }

   public static boolean canEndergradeSpawn(EntityType<? extends Animal> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
      return !worldIn.m_8055_(pos.m_7495_()).m_60795_();
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new DirectPathNavigator(this, worldIn);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("Saddled", this.isSaddled());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setSaddled(compound.m_128471_("Saddled"));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(BITE_TICK, 0);
      this.f_19804_.m_135372_(SADDLED, false);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new TameableAIRide(this, 1.2));
      this.f_21345_.m_25352_(1, new EndergradeAIBreakFlowers(this));
      this.f_21345_.m_25352_(2, new BreedGoal(this, 1.2) {
         public void m_8056_() {
            super.m_8056_();
            EntityEndergrade.this.stopWandering = true;
         }

         public void m_8041_() {
            super.m_8041_();
            EntityEndergrade.this.stopWandering = false;
         }
      });
      this.f_21345_.m_25352_(3, new TemptGoal(this, 1.1, Ingredient.m_43929_(new ItemLike[]{Items.f_42730_}), false) {
         public void m_8056_() {
            super.m_8056_();
            EntityEndergrade.this.stopWandering = true;
         }

         public void m_8041_() {
            super.m_8041_();
            EntityEndergrade.this.stopWandering = false;
         }
      });
      this.f_21345_.m_25352_(4, new EntityEndergrade.RandomFlyGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new EndergradeAITargetItems(this, true));
   }

   @Nullable
   public Entity m_6688_() {
      for (Entity passenger : this.m_20197_()) {
         if (passenger instanceof Player player
            && (
               player.m_21205_().m_41720_() == AMItemRegistry.CHORUS_ON_A_STICK.get() || player.m_21206_().m_41720_() == AMItemRegistry.CHORUS_ON_A_STICK.get()
            )) {
            return player;
         }
      }

      return null;
   }

   public boolean m_6109_() {
      return false;
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)AMSoundRegistry.ENDERGRADE_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)AMSoundRegistry.ENDERGRADE_HURT.get();
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      Item item = itemstack.m_41720_();
      if (item == Items.f_42450_ && !this.isSaddled()) {
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         this.setSaddled(true);
         return InteractionResult.SUCCESS;
      } else if (item == Items.f_42730_ && this.m_21023_((MobEffect)AMEffectRegistry.ENDER_FLU.get())) {
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         this.m_5634_(8.0F);
         this.m_21195_((MobEffect)AMEffectRegistry.ENDER_FLU.get());
         return InteractionResult.SUCCESS;
      } else {
         InteractionResult type = super.m_6071_(player, hand);
         if (type != InteractionResult.SUCCESS && !this.m_6898_(itemstack) && !player.m_6144_() && this.isSaddled()) {
            player.m_20329_(this);
            return InteractionResult.SUCCESS;
         } else {
            return type;
         }
      }
   }

   public boolean m_6898_(ItemStack stack) {
      return stack.m_41720_() == Items.f_42730_;
   }

   public void m_7332_(Entity passenger) {
      if (this.m_20363_(passenger)) {
         float radius = -0.25F;
         float angle = (float) (Math.PI / 180.0) * this.f_20883_;
         double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         passenger.m_6034_(this.m_20185_() + extraX, this.m_20186_() + this.m_6048_() + passenger.m_6049_(), this.m_20189_() + extraZ);
      }
   }

   public double m_6048_() {
      float f = Math.min(0.25F, this.f_20924_);
      float f1 = this.f_20925_;
      return (double)this.m_20206_() - 0.1 + (double)(0.12F * Mth.m_14089_(f1 * 0.7F) * 0.7F * f);
   }

   public boolean m_20068_() {
      return true;
   }

   public boolean isSaddled() {
      return (Boolean)this.f_19804_.m_135370_(SADDLED);
   }

   public void setSaddled(boolean saddled) {
      this.f_19804_.m_135381_(SADDLED, saddled);
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevTartigradePitch = this.tartigradePitch;
      this.prevBiteProgress = this.biteProgress;
      float f2 = (float)(-((double)((float)this.m_20184_().f_82480_ * 3.0F) * 180.0F / (float)Math.PI));
      this.tartigradePitch = f2;
      if (this.m_20184_().m_82556_() > 0.005F) {
         float angleMotion = (float) (Math.PI / 180.0) * this.f_20883_;
         double extraXMotion = (double)(-0.2F * Mth.m_14031_((float)(Math.PI + (double)angleMotion)));
         double extraZMotion = (double)(-0.2F * Mth.m_14089_(angleMotion));
         this.f_19853_.m_7106_(ParticleTypes.f_123810_, this.m_20208_(0.5), this.m_20186_() + 0.3, this.m_20262_(0.5), extraXMotion, 0.0, extraZMotion);
      }

      int tick = (Integer)this.f_19804_.m_135370_(BITE_TICK);
      if (tick > 0) {
         this.f_19804_.m_135381_(BITE_TICK, tick - 1);
         this.biteProgress++;
      } else if (this.biteProgress > 0.0F) {
         this.biteProgress--;
      }
   }

   public boolean causeFallDamage(float distance, float damageMultiplier) {
      return false;
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   private BlockPos getGroundPosition(BlockPos radialPos) {
      while (radialPos.m_123342_() > 1 && this.f_19853_.m_46859_(radialPos)) {
         radialPos = radialPos.m_7495_();
      }

      return radialPos.m_123342_() <= 1 ? new BlockPos(radialPos.m_123341_(), this.f_19853_.m_5736_(), radialPos.m_123343_()) : radialPos;
   }

   public boolean isTargetBlocked(Vec3 target) {
      Vec3 Vector3d = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
      return this.f_19853_.m_45547_(new ClipContext(Vector3d, target, Block.COLLIDER, Fluid.NONE, this)).m_6662_() != Type.MISS;
   }

   public boolean canTargetItem(ItemStack stack) {
      return stack.m_41720_() == Items.f_42730_ || stack.m_41720_() == Items.f_42003_;
   }

   public void onGetItem(ItemEntity targetEntity) {
      this.m_146850_(GameEvent.f_157806_);
      this.m_5496_(SoundEvents.f_11788_, this.m_6121_(), this.m_6100_());
      this.m_5634_(5.0F);
   }

   public void bite() {
      this.f_19804_.m_135381_(BITE_TICK, 5);
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return AMEntityRegistry.rollSpawn(AMConfig.endergradeSpawnRolls, this.m_217043_(), spawnReasonIn);
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
      return (AgeableMob)((EntityType)AMEntityRegistry.ENDERGRADE.get()).m_20615_(p_241840_1_);
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.isSaddled() && !this.f_19853_.f_46443_) {
         this.m_19998_(Items.f_42450_);
      }
   }

   public boolean m_29443_() {
      return true;
   }

   static class MoveHelperController extends MoveControl {
      private final EntityEndergrade parentEntity;

      public MoveHelperController(EntityEndergrade sunbird) {
         super(sunbird);
         this.parentEntity = sunbird;
      }

      public void m_8126_() {
         if (this.f_24981_ == Operation.STRAFE) {
            Vec3 vector3d = new Vec3(
               this.f_24975_ - this.parentEntity.m_20185_(), this.f_24976_ - this.parentEntity.m_20186_(), this.f_24977_ - this.parentEntity.m_20189_()
            );
            double d0 = vector3d.m_82553_();
            this.parentEntity.m_20256_(this.parentEntity.m_20184_().m_82520_(0.0, vector3d.m_82490_(this.f_24978_ * 0.05 / d0).m_7098_(), 0.0));
            float f = (float)this.f_24974_.m_21133_(Attributes.f_22279_);
            float f1 = (float)this.f_24978_ * f;
            float f2 = this.f_24979_;
            float f3 = this.f_24980_;
            float f4 = Mth.m_14116_(f2 * f2 + f3 * f3);
            if (f4 < 1.0F) {
               f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 *= f4;
            f3 *= f4;
            float f5 = Mth.m_14031_(this.f_24974_.m_146908_() * (float) (Math.PI / 180.0));
            float f6 = Mth.m_14089_(this.f_24974_.m_146908_() * (float) (Math.PI / 180.0));
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            this.f_24979_ = 1.0F;
            this.f_24980_ = 0.0F;
            this.f_24974_.m_7910_(f1);
            this.f_24974_.m_21564_(this.f_24979_);
            this.f_24974_.m_21570_(this.f_24980_);
            this.f_24981_ = Operation.WAIT;
         } else if (this.f_24981_ == Operation.MOVE_TO) {
            Vec3 vector3d = new Vec3(
               this.f_24975_ - this.parentEntity.m_20185_(), this.f_24976_ - this.parentEntity.m_20186_(), this.f_24977_ - this.parentEntity.m_20189_()
            );
            double d0 = vector3d.m_82553_();
            if (d0 < this.parentEntity.m_20191_().m_82309_()) {
               this.f_24981_ = Operation.WAIT;
               this.parentEntity.m_20256_(this.parentEntity.m_20184_().m_82490_(0.5));
            } else {
               double localSpeed = this.f_24978_;
               if (this.parentEntity.m_20160_()) {
                  localSpeed *= 1.5;
               }

               this.parentEntity.m_20256_(this.parentEntity.m_20184_().m_82549_(vector3d.m_82490_(localSpeed * 0.005 / d0)));
               if (this.parentEntity.m_5448_() == null) {
                  Vec3 vector3d1 = this.parentEntity.m_20184_();
                  this.parentEntity.m_146922_(-((float)Mth.m_14136_(vector3d1.f_82479_, vector3d1.f_82481_)) * (180.0F / (float)Math.PI));
                  this.parentEntity.f_20883_ = this.parentEntity.m_146908_();
               } else {
                  double d2 = this.parentEntity.m_5448_().m_20185_() - this.parentEntity.m_20185_();
                  double d1 = this.parentEntity.m_5448_().m_20189_() - this.parentEntity.m_20189_();
                  this.parentEntity.m_146922_(-((float)Mth.m_14136_(d2, d1)) * (180.0F / (float)Math.PI));
                  this.parentEntity.f_20883_ = this.parentEntity.m_146908_();
               }
            }
         }
      }

      private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
         AABB axisalignedbb = this.parentEntity.m_20191_();

         for (int i = 1; i < p_220673_2_; i++) {
            axisalignedbb = axisalignedbb.m_82383_(p_220673_1_);
            if (!this.parentEntity.f_19853_.m_45756_(this.parentEntity, axisalignedbb)) {
               return false;
            }
         }

         return true;
      }
   }

   static class RandomFlyGoal extends Goal {
      private final EntityEndergrade parentEntity;
      private BlockPos target = null;

      public RandomFlyGoal(EntityEndergrade mosquito) {
         this.parentEntity = mosquito;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         MoveControl movementcontroller = this.parentEntity.m_21566_();
         if (this.parentEntity.stopWandering || this.parentEntity.hasItemTarget) {
            return false;
         } else if (movementcontroller.m_24995_() && this.target != null) {
            return false;
         } else {
            this.target = this.getBlockInViewEndergrade();
            if (this.target != null) {
               this.parentEntity
                  .m_21566_()
                  .m_6849_((double)this.target.m_123341_() + 0.5, (double)this.target.m_123342_() + 0.5, (double)this.target.m_123343_() + 0.5, 1.0);
            }

            return true;
         }
      }

      public boolean m_8045_() {
         return this.target != null
            && !this.parentEntity.stopWandering
            && !this.parentEntity.hasItemTarget
            && this.parentEntity.m_20238_(Vec3.m_82512_(this.target)) > 2.4
            && this.parentEntity.m_21566_().m_24995_()
            && !this.parentEntity.f_19862_;
      }

      public void m_8041_() {
         this.target = null;
      }

      public void m_8037_() {
         if (this.target == null) {
            this.target = this.getBlockInViewEndergrade();
         }

         if (this.target != null) {
            this.parentEntity
               .m_21566_()
               .m_6849_((double)this.target.m_123341_() + 0.5, (double)this.target.m_123342_() + 0.5, (double)this.target.m_123343_() + 0.5, 1.0);
            if (this.parentEntity.m_20238_(Vec3.m_82512_(this.target)) < 2.5) {
               this.target = null;
            }
         }
      }

      public BlockPos getBlockInViewEndergrade() {
         float radius = (float)(1 + this.parentEntity.m_217043_().m_188503_(5));
         float neg = this.parentEntity.m_217043_().m_188499_() ? 1.0F : -1.0F;
         float renderYawOffset = this.parentEntity.f_20883_;
         float angle = (float) (Math.PI / 180.0) * renderYawOffset + 3.15F + this.parentEntity.m_217043_().m_188501_() * neg;
         double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         BlockPos radialPos = new BlockPos(this.parentEntity.m_20185_() + extraX, this.parentEntity.m_20186_() + 2.0, this.parentEntity.m_20189_() + extraZ);
         BlockPos ground = this.parentEntity.getGroundPosition(radialPos);
         BlockPos newPos = ground.m_6630_(1 + this.parentEntity.m_217043_().m_188503_(6));
         return !this.parentEntity.isTargetBlocked(Vec3.m_82512_(newPos)) && this.parentEntity.m_20238_(Vec3.m_82512_(newPos)) > 6.0 ? newPos : null;
      }
   }
}
