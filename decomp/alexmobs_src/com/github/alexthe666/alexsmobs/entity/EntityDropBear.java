package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.entity.ai.DirectPathNavigator;
import com.github.alexthe666.alexsmobs.entity.ai.EntityAINearestTarget3D;
import com.github.alexthe666.alexsmobs.entity.ai.FlightMoveController;
import com.github.alexthe666.alexsmobs.entity.ai.GroundPathNavigatorWide;
import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityDropBear extends Monster implements IAnimatedEntity {
   public static final Animation ANIMATION_BITE = Animation.create(9);
   public static final Animation ANIMATION_SWIPE_R = Animation.create(15);
   public static final Animation ANIMATION_SWIPE_L = Animation.create(15);
   public static final Animation ANIMATION_JUMPUP = Animation.create(20);
   private static final EntityDataAccessor<Boolean> UPSIDE_DOWN = SynchedEntityData.m_135353_(EntityDropBear.class, EntityDataSerializers.f_135035_);
   public float prevUpsideDownProgress;
   public float upsideDownProgress;
   public boolean fallRotation = this.f_19796_.m_188499_();
   private int animationTick;
   private boolean jumpingUp = false;
   private Animation currentAnimation;
   private int upwardsFallingTicks = 0;
   private boolean isUpsideDownNavigator;
   private boolean prevOnGround = false;

   protected EntityDropBear(EntityType type, Level world) {
      super(type, world);
      this.switchNavigator(true);
   }

   public static Builder bakeAttributes() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22276_, 22.0)
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22278_, 0.7F)
         .m_22268_(Attributes.f_22281_, 2.0)
         .m_22268_(Attributes.f_22279_, 0.25);
   }

   public static BlockPos getLowestPos(LevelAccessor world, BlockPos pos) {
      while (!world.m_8055_(pos).m_60783_(world, pos, Direction.DOWN) && pos.m_123342_() < 320) {
         pos = pos.m_7494_();
      }

      return pos;
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return AMEntityRegistry.rollSpawn(AMConfig.dropbearSpawnRolls, this.m_217043_(), spawnReasonIn);
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)AMSoundRegistry.DROPBEAR_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)AMSoundRegistry.DROPBEAR_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)AMSoundRegistry.DROPBEAR_HURT.get();
   }

   public boolean m_7327_(Entity entityIn) {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(this.f_19796_.m_188499_() ? ANIMATION_BITE : (this.f_19796_.m_188499_() ? ANIMATION_SWIPE_L : ANIMATION_SWIPE_R));
      }

      return true;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new EntityDropBear.AIDropMelee());
      this.f_21345_.m_25352_(2, new EntityDropBear.AIUpsideDownWander());
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, LivingEntity.class, 30.0F));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[]{EntityDropBear.class}));
      this.f_21346_.m_25352_(2, new EntityAINearestTarget3D(this, Player.class, true) {
         @Override
         protected AABB m_7255_(double targetDistance) {
            AABB bb = this.f_26135_.m_20191_().m_82377_(targetDistance, targetDistance, targetDistance);
            return new AABB(bb.f_82288_, 0.0, bb.f_82290_, bb.f_82291_, 256.0, bb.f_82293_);
         }
      });
      this.f_21346_.m_25352_(2, new EntityAINearestTarget3D(this, AbstractVillager.class, true) {
         @Override
         protected AABB m_7255_(double targetDistance) {
            AABB bb = this.f_26135_.m_20191_().m_82377_(targetDistance, targetDistance, targetDistance);
            return new AABB(bb.f_82288_, 0.0, bb.f_82290_, bb.f_82291_, 256.0, bb.f_82293_);
         }
      });
   }

   public boolean m_6673_(DamageSource source) {
      return super.m_6673_(source) || source == DamageSource.f_19315_ || source == DamageSource.f_19310_;
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
      super.m_7840_(y, onGroundIn, state, pos);
   }

   protected void m_21229_() {
      this.onLand();
      super.m_21229_();
   }

   private void switchNavigator(boolean rightsideUp) {
      if (rightsideUp) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new GroundPathNavigatorWide(this, this.f_19853_);
         this.isUpsideDownNavigator = false;
      } else {
         this.f_21342_ = new FlightMoveController(this, 1.1F, false);
         this.f_21344_ = new DirectPathNavigator(this, this.f_19853_);
         this.isUpsideDownNavigator = true;
      }
   }

   public void m_8119_() {
      super.m_8119_();
      AnimationHandler.INSTANCE.updateAnimations(this);
      this.prevUpsideDownProgress = this.upsideDownProgress;
      if (this.isUpsideDown() && this.upsideDownProgress < 5.0F) {
         this.upsideDownProgress++;
      }

      if (!this.isUpsideDown() && this.upsideDownProgress > 0.0F) {
         this.upsideDownProgress--;
      }

      if (!this.f_19853_.f_46443_) {
         BlockPos abovePos = this.getPositionAbove();
         BlockState aboveState = this.f_19853_.m_8055_(abovePos);
         BlockState belowState = this.f_19853_.m_8055_(this.m_20099_());
         BlockPos worldHeight = this.f_19853_.m_5452_(Types.MOTION_BLOCKING, this.m_20183_());
         boolean validAboveState = aboveState.m_60783_(this.f_19853_, abovePos, Direction.DOWN);
         boolean validBelowState = belowState.m_60783_(this.f_19853_, this.m_20099_(), Direction.UP);
         LivingEntity attackTarget = this.m_5448_();
         if (attackTarget != null && this.m_20270_(attackTarget) < attackTarget.m_20205_() + this.m_20205_() + 1.0F && this.m_142582_(attackTarget)) {
            if (this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 6) {
               attackTarget.m_147240_(
                  0.5,
                  (double)Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0)),
                  (double)(-Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0)))
               );
               this.m_5448_().m_6469_(DamageSource.m_19370_(this), (float)this.m_21051_(Attributes.f_22281_).m_22115_());
            }

            if (this.getAnimation() == ANIMATION_SWIPE_L && this.getAnimationTick() == 9) {
               float rot = this.m_146908_() + 90.0F;
               attackTarget.m_147240_(0.5, (double)Mth.m_14031_(rot * (float) (Math.PI / 180.0)), (double)(-Mth.m_14089_(rot * (float) (Math.PI / 180.0))));
               this.m_5448_().m_6469_(DamageSource.m_19370_(this), (float)this.m_21051_(Attributes.f_22281_).m_22115_());
            }

            if (this.getAnimation() == ANIMATION_SWIPE_R && this.getAnimationTick() == 9) {
               float rot = this.m_146908_() - 90.0F;
               attackTarget.m_147240_(0.5, (double)Mth.m_14031_(rot * (float) (Math.PI / 180.0)), (double)(-Mth.m_14089_(rot * (float) (Math.PI / 180.0))));
               this.m_5448_().m_6469_(DamageSource.m_19370_(this), (float)this.m_21051_(Attributes.f_22281_).m_22115_());
            }
         }

         if ((attackTarget == null || attackTarget != null && !attackTarget.m_6084_())
            && this.f_19796_.m_188503_(300) == 0
            && this.f_19861_
            && !this.isUpsideDown()
            && this.m_20186_() + 2.0 < (double)worldHeight.m_123342_()
            && this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ANIMATION_JUMPUP);
         }

         if (this.jumpingUp && this.m_20186_() > (double)worldHeight.m_123342_()) {
            this.jumpingUp = false;
         }

         if (this.f_19861_ && this.getAnimation() == ANIMATION_JUMPUP && this.getAnimationTick() > 10 || this.jumpingUp && this.getAnimation() == NO_ANIMATION) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, 2.0, 0.0));
            this.jumpingUp = true;
         }

         if (this.isUpsideDown()) {
            this.jumpingUp = false;
            this.m_20242_(!this.f_19861_);
            float f = 0.91F;
            this.m_20256_(this.m_20184_().m_82542_((double)f, 1.0, (double)f));
            if (!this.f_19863_) {
               if (!this.f_19861_ && !validBelowState && this.upwardsFallingTicks <= 5) {
                  if (!validAboveState) {
                     this.upwardsFallingTicks++;
                  }

                  this.m_20256_(this.m_20184_().m_82520_(0.0, 0.2F, 0.0));
               } else {
                  this.setUpsideDown(false);
                  this.upwardsFallingTicks = 0;
               }
            } else {
               this.upwardsFallingTicks = 0;
            }

            if (this.f_19862_) {
               this.upwardsFallingTicks = 0;
               this.m_20256_(this.m_20184_().m_82520_(0.0, -0.3F, 0.0));
            }

            if (this.m_5830_() && this.f_19853_.m_46859_(this.m_20099_())) {
               this.m_6034_(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_());
            }
         } else {
            this.m_20242_(false);
            if (validAboveState) {
               this.setUpsideDown(true);
            }
         }

         if (this.isUpsideDown() && !this.isUpsideDownNavigator) {
            this.switchNavigator(false);
         }

         if (!this.isUpsideDown() && this.isUpsideDownNavigator) {
            this.switchNavigator(true);
         }
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(UPSIDE_DOWN, false);
   }

   public boolean isUpsideDown() {
      return (Boolean)this.f_19804_.m_135370_(UPSIDE_DOWN);
   }

   public void setUpsideDown(boolean upsideDown) {
      this.f_19804_.m_135381_(UPSIDE_DOWN, upsideDown);
   }

   protected BlockPos getPositionAbove() {
      return new BlockPos(this.m_20182_().f_82479_, this.m_20191_().f_82292_ + 0.5000001, this.m_20182_().f_82481_);
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int i) {
      this.animationTick = i;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_BITE, ANIMATION_SWIPE_L, ANIMATION_SWIPE_R, ANIMATION_JUMPUP};
   }

   private boolean hasLineOfSightBlock(BlockPos destinationBlock) {
      Vec3 Vector3d = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
      Vec3 blockVec = Vec3.m_82512_(destinationBlock);
      BlockHitResult result = this.f_19853_.m_45547_(new ClipContext(Vector3d, blockVec, Block.COLLIDER, Fluid.NONE, this));
      return result.m_82425_().equals(destinationBlock);
   }

   private void doInitialPosing(LevelAccessor world) {
      BlockPos upperPos = this.getPositionAbove().m_7494_();
      BlockPos highest = getLowestPos(world, upperPos);
      this.m_6034_((double)((float)highest.m_123341_() + 0.5F), (double)highest.m_123342_(), (double)((float)highest.m_123343_() + 0.5F));
   }

   @Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag
   ) {
      if (reason == MobSpawnType.NATURAL) {
         this.doInitialPosing(worldIn);
      }

      return super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   private void onLand() {
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_7605_(this, (byte)39);

         for (Entity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(2.5))) {
            if (!this.m_7307_(entity) && !(entity instanceof EntityDropBear) && entity != this) {
               entity.m_6469_(DamageSource.m_19370_(this), 2.0F + this.f_19796_.m_188501_() * 5.0F);
               this.launch(entity, true);
            }
         }
      }
   }

   private void launch(Entity e, boolean huge) {
      if (e.m_20096_()) {
         double d0 = e.m_20185_() - this.m_20185_();
         double d1 = e.m_20189_() - this.m_20189_();
         double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
         float f = 0.5F;
         e.m_5997_(d0 / d2 * (double)f, huge ? 0.5 : 0.2F, d1 / d2 * (double)f);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      if (id == 39) {
         this.spawnGroundEffects();
      } else {
         super.m_7822_(id);
      }
   }

   public void spawnGroundEffects() {
      float radius = 2.3F;
      if (this.f_19853_.f_46443_) {
         for (int i1 = 0; i1 < 20 + this.f_19796_.m_188503_(12); i1++) {
            double motionX = this.m_217043_().m_188583_() * 0.07;
            double motionY = this.m_217043_().m_188583_() * 0.07;
            double motionZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.8F;
            double extraZ = (double)(radius * Mth.m_14089_(angle));
            BlockPos ground = this.getGroundPosition(
               new BlockPos((double)Mth.m_14107_(this.m_20185_() + extraX), this.m_20186_(), (double)Mth.m_14107_(this.m_20189_() + extraZ))
            );
            BlockState BlockState = this.f_19853_.m_8055_(ground);
            if (BlockState.m_60767_() != Material.f_76296_) {
               this.f_19853_
                  .m_6493_(
                     new BlockParticleOption(ParticleTypes.f_123794_, BlockState),
                     true,
                     this.m_20185_() + extraX,
                     (double)ground.m_123342_() + extraY,
                     this.m_20189_() + extraZ,
                     motionX,
                     motionY,
                     motionZ
                  );
            }
         }
      }
   }

   private BlockPos getGroundPosition(BlockPos in) {
      BlockPos position = new BlockPos((double)in.m_123341_(), this.m_20186_(), (double)in.m_123343_());

      while (position.m_123342_() > 2 && this.f_19853_.m_46859_(position) && this.f_19853_.m_6425_(position).m_76178_()) {
         position = position.m_7495_();
      }

      return position;
   }

   private class AIDropMelee extends Goal {
      private boolean prevOnGround = false;

      public AIDropMelee() {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         return EntityDropBear.this.m_5448_() != null;
      }

      public void m_8037_() {
         LivingEntity target = EntityDropBear.this.m_5448_();
         if (target != null) {
            double dist = (double)EntityDropBear.this.m_20270_(target);
            if (EntityDropBear.this.isUpsideDown()) {
               double d0 = EntityDropBear.this.m_20185_() - target.m_20185_();
               double d2 = EntityDropBear.this.m_20189_() - target.m_20189_();
               double xzDistSqr = d0 * d0 + d2 * d2;
               BlockPos ceilingPos = new BlockPos(
                  target.m_20185_(), EntityDropBear.this.m_20186_() - 3.0 - (double)EntityDropBear.this.f_19796_.m_188503_(3), target.m_20189_()
               );
               BlockPos lowestPos = EntityDropBear.getLowestPos(EntityDropBear.this.f_19853_, ceilingPos);
               EntityDropBear.this.m_21566_()
                  .m_6849_((double)((float)lowestPos.m_123341_() + 0.5F), (double)ceilingPos.m_123342_(), (double)((float)lowestPos.m_123343_() + 0.5F), 1.1);
               if (xzDistSqr < 2.5) {
                  EntityDropBear.this.setUpsideDown(false);
               }
            } else if (EntityDropBear.this.f_19861_) {
               EntityDropBear.this.m_21573_().m_5624_(target, 1.2);
            }

            if (dist < 3.0) {
               EntityDropBear.this.m_7327_(target);
            }
         }
      }
   }

   class AIUpsideDownWander extends RandomStrollGoal {
      public AIUpsideDownWander() {
         super(EntityDropBear.this, 1.0, 50);
      }

      @Nullable
      protected Vec3 m_7037_() {
         if (EntityDropBear.this.isUpsideDown()) {
            for (int i = 0; i < 15; i++) {
               Random rand = new Random();
               BlockPos randPos = EntityDropBear.this.m_20183_().m_7918_(rand.nextInt(16) - 8, -2, rand.nextInt(16) - 8);
               BlockPos lowestPos = EntityDropBear.getLowestPos(EntityDropBear.this.f_19853_, randPos);
               if (EntityDropBear.this.f_19853_.m_8055_(lowestPos).m_60783_(EntityDropBear.this.f_19853_, lowestPos, Direction.DOWN)) {
                  return Vec3.m_82512_(lowestPos);
               }
            }

            return null;
         } else {
            return super.m_7037_();
         }
      }

      public boolean m_8036_() {
         return super.m_8036_();
      }

      public boolean m_8045_() {
         if (EntityDropBear.this.isUpsideDown()) {
            double d0 = EntityDropBear.this.m_20185_() - this.f_25726_;
            double d2 = EntityDropBear.this.m_20189_() - this.f_25728_;
            double d4 = d0 * d0 + d2 * d2;
            return d4 > 4.0;
         } else {
            return super.m_8045_();
         }
      }

      public void m_8041_() {
         super.m_8041_();
         this.f_25726_ = 0.0;
         this.f_25727_ = 0.0;
         this.f_25728_ = 0.0;
      }

      public void m_8056_() {
         if (EntityDropBear.this.isUpsideDown()) {
            this.f_25725_.m_21566_().m_6849_(this.f_25726_, this.f_25727_, this.f_25728_, this.f_25729_ * 0.7F);
         } else {
            this.f_25725_.m_21573_().m_26519_(this.f_25726_, this.f_25727_, this.f_25728_, this.f_25729_);
         }
      }
   }
}
