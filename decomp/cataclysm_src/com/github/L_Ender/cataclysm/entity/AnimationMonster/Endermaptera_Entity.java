package com.github.L_Ender.cataclysm.entity.AnimationMonster;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Golem_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Guardian_Entity;
import com.github.L_Ender.cataclysm.entity.etc.FlightMoveController;
import com.github.L_Ender.cataclysm.entity.etc.path.DirectPathNavigator;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Shard_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Endermaptera_Entity extends Monster implements IAnimatedEntity {
   public static final ResourceLocation HAS_JAWS_LOOT = new ResourceLocation("cataclysm", "entities/endermaptera_has_jaws");
   private int animationTick;
   private Animation currentAnimation;
   public static final Animation JAW_ATTACK = Animation.create(13);
   public static final Animation HEADBUTT_ATTACK = Animation.create(13);
   public float attachChangeProgress = 0.0F;
   public float prevAttachChangeProgress = 0.0F;
   private static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.m_135353_(Endermaptera_Entity.class, EntityDataSerializers.f_135040_);
   private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.m_135353_(Endermaptera_Entity.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Boolean> HAS_JAWS = SynchedEntityData.m_135353_(Endermaptera_Entity.class, EntityDataSerializers.f_135035_);
   private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   private Direction prevAttachDir = Direction.DOWN;
   private boolean isUpsideDownNavigator;

   public Endermaptera_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.f_21364_ = 6;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(3, new MeleeAttackGoal(this, 1.0, false));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   private void switchNavigator(boolean rightsideUp) {
      if (rightsideUp) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new WallClimberNavigation(this, this.f_19853_);
         this.isUpsideDownNavigator = false;
      } else {
         this.f_21342_ = new FlightMoveController(this, 0.6F, false);
         this.f_21344_ = new DirectPathNavigator(this, this.f_19853_);
         this.isUpsideDownNavigator = true;
      }
   }

   public static Builder endermaptera() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22279_, 0.27F)
         .m_22268_(Attributes.f_22281_, 4.0)
         .m_22268_(Attributes.f_22276_, 16.0)
         .m_22268_(Attributes.f_22284_, 6.0)
         .m_22268_(Attributes.f_22278_, 0.25);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(CLIMBING, (byte)0);
      this.f_19804_.m_135372_(ATTACHED_FACE, Direction.DOWN);
      this.f_19804_.m_135372_(HAS_JAWS, true);
   }

   public Direction getAttachmentFacing() {
      return (Direction)this.f_19804_.m_135370_(ATTACHED_FACE);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new WallClimberNavigation(this, worldIn);
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return this.getHasJaws() ? HAS_JAWS_LOOT : super.m_7582_();
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("Has_Jaws", this.getHasJaws());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.f_19804_.m_135381_(ATTACHED_FACE, Direction.m_122376_(compound.m_128445_("AttachFace")));
      this.setHasJaw(compound.m_128471_("Has_Jaws"));
   }

   public void setHasJaw(boolean HasJaws) {
      this.f_19804_.m_135381_(HAS_JAWS, HasJaws);
   }

   public boolean getHasJaws() {
      return (Boolean)this.f_19804_.m_135370_(HAS_JAWS);
   }

   public boolean m_142535_(float p_149683_, float p_149684_, DamageSource p_149685_) {
      return false;
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.ENDERMAPTERA_AMBIENT.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.ENDERMAPTERA_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.ENDERMAPTERA_DEATH.get();
   }

   protected void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ModSounds.ENDERMAPTERA_STEP.get(), 0.15F, 0.6F);
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, JAW_ATTACK, HEADBUTT_ATTACK};
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !(entityIn instanceof Ender_Guardian_Entity)
               && !(entityIn instanceof Ender_Golem_Entity)
               && !(entityIn instanceof Shulker)
               && !(entityIn instanceof Endermaptera_Entity)
            ? false
            : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   public boolean m_7327_(Entity entityIn) {
      if (this.getHasJaws()) {
         this.setAnimation(JAW_ATTACK);
      } else {
         this.setAnimation(HEADBUTT_ATTACK);
      }

      return true;
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevAttachChangeProgress = this.attachChangeProgress;
      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.attachChangeProgress > 0.0F) {
         this.attachChangeProgress -= 0.25F;
      }

      Vec3 Vec3 = this.m_20184_();
      if (!this.f_19853_.f_46443_) {
         this.setBesideClimbableBlock(this.f_19862_ || this.f_19863_ && !this.m_20096_());
         if (this.m_20096_() || this.m_20072_() || this.m_20077_()) {
            this.f_19804_.m_135381_(ATTACHED_FACE, Direction.DOWN);
         } else if (this.f_19863_) {
            this.f_19804_.m_135381_(ATTACHED_FACE, Direction.UP);
         } else {
            Direction closestDirection = Direction.DOWN;
            double closestDistance = 100.0;

            for (Direction dir : HORIZONTALS) {
               BlockPos antPos = new BlockPos(Mth.m_14107_(this.m_20185_()), Mth.m_14107_(this.m_20186_()), Mth.m_14107_(this.m_20189_()));
               BlockPos offsetPos = antPos.m_121945_(dir);
               Vec3 offset = Vec3.m_82512_(offsetPos);
               if (closestDistance > this.m_20182_().m_82554_(offset) && this.f_19853_.m_46578_(offsetPos, this, dir.m_122424_())) {
                  closestDistance = this.m_20182_().m_82554_(offset);
                  closestDirection = dir;
               }
            }

            this.f_19804_.m_135381_(ATTACHED_FACE, closestDirection);
         }
      }

      boolean flag = false;
      if (this.getAttachmentFacing() != Direction.DOWN) {
         if (this.getAttachmentFacing() == Direction.UP) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, 1.0, 0.0));
         } else {
            if (!this.f_19862_ && this.getAttachmentFacing() != Direction.UP) {
               Vec3 vec = Vec3.m_82528_(this.getAttachmentFacing().m_122436_());
               this.m_20256_(this.m_20184_().m_82549_(vec.m_82541_().m_82542_(0.1F, 0.1F, 0.1F)));
            }

            if (!this.m_20096_() && Vec3.f_82480_ < 0.0) {
               this.m_20256_(this.m_20184_().m_82542_(1.0, 0.5, 1.0));
               flag = true;
            }
         }
      }

      if (this.getAttachmentFacing() == Direction.UP) {
         this.m_20242_(true);
         this.m_20256_(Vec3.m_82542_(0.7, 1.0, 0.7));
      } else {
         this.m_20242_(false);
      }

      if (!flag && this.m_6147_()) {
         this.m_20256_(Vec3.m_82542_(1.0, 0.4, 1.0));
      }

      if (this.prevAttachDir != this.getAttachmentFacing()) {
         this.attachChangeProgress = 1.0F;
      }

      this.prevAttachDir = this.getAttachmentFacing();
      if (!this.f_19853_.f_46443_) {
         if (this.getAttachmentFacing() == Direction.UP && !this.isUpsideDownNavigator) {
            this.switchNavigator(false);
         }

         if (this.getAttachmentFacing() != Direction.UP && this.isUpsideDownNavigator) {
            this.switchNavigator(true);
         }

         LivingEntity target = this.m_5448_();
         if (target != null && this.m_20270_(target) < target.m_20205_() + this.m_20205_() && this.m_142582_(target)) {
            float damage = (float)((int)this.m_21133_(Attributes.f_22281_));
            if (this.getAnimation() == JAW_ATTACK && this.getAnimationTick() == 11) {
               target.m_6469_(DamageSource.m_19370_(this), damage);
               if (this.f_19796_.m_188503_(6) == 0) {
                  this.BrokenJaws();
               }
            }

            if (this.getAnimation() == HEADBUTT_ATTACK && this.getAnimationTick() == 6) {
               target.m_6469_(DamageSource.m_19370_(this), damage * 0.75F);
            }
         }
      } else {
         for (int i = 0; i < 2; i++) {
            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123760_,
                  this.m_20208_(0.5),
                  this.m_20187_(),
                  this.m_20262_(0.5),
                  (this.f_19796_.m_188500_() - 0.5) * 2.0,
                  -this.f_19796_.m_188500_(),
                  (this.f_19796_.m_188500_() - 0.5) * 2.0
               );
         }
      }
   }

   private void BrokenJaws() {
      this.m_5496_(SoundEvents.f_12018_, 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
      this.setHasJaw(false);
      int shardCount = 8 + this.f_19796_.m_188503_(4);
      if (!this.f_19853_.f_46443_) {
         for (int i = 0; i < shardCount; i++) {
            float f = (float)(i + 1) / (float)shardCount * 360.0F;
            Void_Shard_Entity shard = new Void_Shard_Entity((EntityType)ModEntities.VOID_SHARD.get(), this.f_19853_, this);
            shard.m_37251_(this, this.m_146909_() - (float)this.f_19796_.m_188503_(40), f, 0.0F, 0.15F + this.f_19796_.m_188501_() * 0.2F, 1.0F);
            this.f_19853_.m_7967_(shard);
         }
      }
   }

   protected void m_6763_(BlockState state) {
   }

   public boolean m_6147_() {
      return this.isBesideClimbableBlock();
   }

   public boolean isBesideClimbableBlock() {
      return ((Byte)this.f_19804_.m_135370_(CLIMBING) & 1) != 0;
   }

   public void setBesideClimbableBlock(boolean climbing) {
      byte b0 = (Byte)this.f_19804_.m_135370_(CLIMBING);
      if (climbing) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.f_19804_.m_135381_(CLIMBING, b0);
   }

   public static boolean canSpawn(EntityType<Endermaptera_Entity> entity, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
      return !worldIn.m_8055_(pos.m_7495_()).m_204336_(ModTag.ENDERMAPTERA_CAN_NOT_SPAWN);
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }
}
