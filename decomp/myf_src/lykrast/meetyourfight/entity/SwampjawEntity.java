package lykrast.meetyourfight.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.ai.PhantomAttackPlayer;
import lykrast.meetyourfight.registry.ModEntities;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class SwampjawEntity extends BossFlyingEntity {
   private int behavior;
   private static final int CIRCLE = 0;
   private static final int BOMB = 1;
   private static final int SWOOP = 2;
   private Vec3 orbitOffset = Vec3.f_82478_;
   private BlockPos orbitPosition = BlockPos.f_121853_;
   public float tailYaw;
   public float tailPitch;

   public SwampjawEntity(EntityType<? extends SwampjawEntity> type, Level worldIn) {
      super(type, worldIn);
      this.f_21364_ = 30;
      this.f_21342_ = new SwampjawEntity.MoveHelperController(this);
      this.tailYaw = this.m_146908_();
      this.tailPitch = this.m_146909_();
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 100.0).m_22268_(Attributes.f_22281_, 12.0);
   }

   public static void spawn(Player player, Level world) {
      RandomSource rand = player.m_217043_();
      SwampjawEntity fish = (SwampjawEntity)((EntityType)ModEntities.SWAMPJAW.get()).m_20615_(world);
      fish.m_7678_(
         player.m_20185_() + (double)rand.m_188503_(5) - 2.0,
         player.m_20186_() + (double)rand.m_188503_(10) + 5.0,
         player.m_20189_() + (double)rand.m_188503_(5) - 2.0,
         rand.m_188501_() * 360.0F - 180.0F,
         0.0F
      );
      if (!player.m_150110_().f_35937_) {
         fish.m_6710_(player);
      }

      fish.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 100, 2));
      fish.m_6518_((ServerLevel)world, world.m_6436_(fish.m_20183_()), MobSpawnType.EVENT, null, null);
      world.m_7967_(fish);
   }

   public void m_8119_() {
      this.f_19794_ = true;
      super.m_8119_();
      this.f_19794_ = false;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new SwampjawEntity.PickAttackGoal(this));
      this.f_21345_.m_25352_(2, new SwampjawEntity.SweepAttackGoal(this));
      this.f_21345_.m_25352_(3, new SwampjawEntity.BombMovementGoal(this));
      this.f_21345_.m_25352_(4, new SwampjawEntity.OrbitPointGoal(this));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
      this.f_21346_.m_25352_(1, new PhantomAttackPlayer(this));
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag
   ) {
      this.orbitPosition = this.m_20183_().m_6630_(5);
      return super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   public boolean m_6549_(EntityType<?> typeIn) {
      return true;
   }

   public float getTailYaw(float partialTick) {
      return Mth.m_14148_(this.tailYaw, this.m_146908_(), 6.0F * partialTick);
   }

   public float getTailPitch(float partialTick) {
      return Mth.m_14148_(this.tailPitch, this.m_146909_(), 2.0F * partialTick);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.f_19853_.f_46443_) {
         this.tailYaw = Mth.m_14148_(this.tailYaw, this.m_146908_(), 6.0F);
         this.tailPitch = Mth.m_14148_(this.tailPitch, this.m_146909_(), 2.0F);
      }
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("AX")) {
         this.orbitPosition = new BlockPos(compound.m_128451_("AX"), compound.m_128451_("AY"), compound.m_128451_("AZ"));
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("AX", this.orbitPosition.m_123341_());
      compound.m_128405_("AY", this.orbitPosition.m_123342_());
      compound.m_128405_("AZ", this.orbitPosition.m_123343_());
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.swampjawIdle.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.swampjawHurt.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.swampjawDeath.get();
   }

   @Override
   protected SoundEvent getMusic() {
      return (SoundEvent)ModSounds.musicMagnum.get();
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected ResourceLocation m_7582_() {
      return MeetYourFight.rl("swampjaw");
   }

   private abstract static class BaseMoveGoal extends Goal {
      protected SwampjawEntity swampjaw;

      public BaseMoveGoal(SwampjawEntity swampjaw) {
         this.swampjaw = swampjaw;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      protected boolean isCloseToOffset() {
         return this.swampjaw.orbitOffset.m_82531_(this.swampjaw.m_20185_(), this.swampjaw.m_20186_(), this.swampjaw.m_20189_()) < 4.0;
      }

      public boolean m_183429_() {
         return true;
      }
   }

   private static class BombMovementGoal extends SwampjawEntity.BaseMoveGoal {
      public BombMovementGoal(SwampjawEntity swampjaw) {
         super(swampjaw);
      }

      public boolean m_8036_() {
         return this.swampjaw.m_5448_() != null && this.swampjaw.behavior == 1;
      }

      public void m_8056_() {
         this.updateOffset();
      }

      public void m_8037_() {
         if (this.isCloseToOffset()) {
            this.updateOffset();
         }
      }

      private void updateOffset() {
         if (BlockPos.f_121853_.equals(this.swampjaw.orbitPosition)) {
            this.swampjaw.orbitPosition = this.swampjaw.m_20183_();
         }

         LivingEntity target = this.swampjaw.m_5448_();
         if (target != null) {
            double difX = target.m_20185_() - this.swampjaw.orbitOffset.f_82479_;
            double difZ = target.m_20189_() - this.swampjaw.orbitOffset.f_82481_;
            Vec3 overshoot = new Vec3(difX, 0.0, difZ).m_82541_();
            Vec3 vec = target.m_20182_();
            this.swampjaw.orbitOffset = new Vec3(
               vec.f_82479_ + overshoot.f_82479_ * 7.0, (double)(this.swampjaw.orbitPosition.m_123342_() - 4), vec.f_82481_ + overshoot.f_82481_ * 7.0
            );
         }
      }
   }

   private static class MoveHelperController extends MoveControl {
      private float speedFactor = 0.1F;
      private SwampjawEntity swampjaw;

      public MoveHelperController(SwampjawEntity entityIn) {
         super(entityIn);
         this.swampjaw = entityIn;
      }

      public void m_8126_() {
         float targetX = (float)(this.swampjaw.orbitOffset.f_82479_ - this.swampjaw.m_20185_());
         float targetY = (float)(this.swampjaw.orbitOffset.f_82480_ - this.swampjaw.m_20186_());
         float targetZ = (float)(this.swampjaw.orbitOffset.f_82481_ - this.swampjaw.m_20189_());
         double horizontalDist = (double)Mth.m_14116_(targetX * targetX + targetZ * targetZ);
         double verticalAdjust = 1.0 - (double)Mth.m_14154_(targetY * 0.7F) / horizontalDist;
         targetX = (float)((double)targetX * verticalAdjust);
         targetZ = (float)((double)targetZ * verticalAdjust);
         horizontalDist = (double)Mth.m_14116_(targetX * targetX + targetZ * targetZ);
         double totalDist = (double)Mth.m_14116_(targetX * targetX + targetZ * targetZ + targetY * targetY);
         float prevYaw = this.swampjaw.m_146908_();
         float targetYaw = (float)Mth.m_14136_((double)targetZ, (double)targetX);
         float startYaw = Mth.m_14177_(this.swampjaw.m_146908_() + 90.0F);
         targetYaw = Mth.m_14177_(targetYaw * (180.0F / (float)Math.PI));
         this.swampjaw.m_146922_(Mth.m_14148_(startYaw, targetYaw, 10.0F) - 90.0F);
         this.swampjaw.f_20883_ = this.swampjaw.m_146908_();
         if (Mth.m_14145_(prevYaw, this.swampjaw.m_146908_()) < 3.0F) {
            float maxSpeed = this.swampjaw.behavior != 0 ? 3.0F : 1.2F;
            float multiplier = this.speedFactor > maxSpeed ? 10.0F : maxSpeed / this.speedFactor;
            this.speedFactor = Mth.m_14121_(this.speedFactor, maxSpeed, 0.005F * multiplier);
         } else {
            this.speedFactor = Mth.m_14121_(this.speedFactor, this.swampjaw.behavior == 1 ? 0.7F : 0.4F, 0.05F);
         }

         float finalPitch = (float)(-(Mth.m_14136_((double)(-targetY), horizontalDist) * 180.0F / (float)Math.PI));
         this.swampjaw.m_146926_(finalPitch);
         float adjustedYaw = this.swampjaw.m_146908_() + 90.0F;
         double finalX = (double)(this.speedFactor * Mth.m_14089_(adjustedYaw * (float) (Math.PI / 180.0))) * Math.abs((double)targetX / totalDist);
         double finalZ = (double)(this.speedFactor * Mth.m_14031_(adjustedYaw * (float) (Math.PI / 180.0))) * Math.abs((double)targetZ / totalDist);
         double finalY = (double)(this.speedFactor * Mth.m_14031_(finalPitch * (float) (Math.PI / 180.0))) * Math.abs((double)targetY / totalDist);
         Vec3 vector3d = this.swampjaw.m_20184_();
         this.swampjaw.m_20256_(vector3d.m_82549_(new Vec3(finalX, finalY, finalZ).m_82546_(vector3d).m_82490_(0.2)));
      }
   }

   private static class OrbitPointGoal extends SwampjawEntity.BaseMoveGoal {
      private float angle;
      private float radius;
      private float height;
      private float direction;

      public OrbitPointGoal(SwampjawEntity swampjaw) {
         super(swampjaw);
      }

      public boolean m_8036_() {
         return this.swampjaw.m_5448_() == null || this.swampjaw.behavior == 0;
      }

      public void m_8056_() {
         this.radius = 6.0F + this.swampjaw.f_19796_.m_188501_() * 6.0F;
         this.height = -4.0F + this.swampjaw.f_19796_.m_188501_() * 6.0F;
         this.direction = this.swampjaw.f_19796_.m_188499_() ? 1.0F : -1.0F;
         this.updateOffset();
      }

      public void m_8037_() {
         if (this.swampjaw.f_19796_.m_188503_(350) == 0) {
            this.height = -4.0F + this.swampjaw.f_19796_.m_188501_() * 6.0F;
         }

         if (this.swampjaw.f_19796_.m_188503_(250) == 0) {
            this.radius--;
            if (this.radius < 6.0F) {
               this.radius = 12.0F;
               this.direction = -this.direction;
            }
         }

         if (this.swampjaw.f_19796_.m_188503_(450) == 0) {
            this.angle = this.swampjaw.f_19796_.m_188501_() * 2.0F * (float) Math.PI;
            this.updateOffset();
         }

         if (this.isCloseToOffset()) {
            this.updateOffset();
         }

         if (this.swampjaw.orbitOffset.f_82480_ < this.swampjaw.m_20186_() && !this.swampjaw.f_19853_.m_46859_(this.swampjaw.m_20183_().m_6625_(1))) {
            this.height = Math.max(1.0F, this.height);
            this.updateOffset();
         }

         if (this.swampjaw.orbitOffset.f_82480_ > this.swampjaw.m_20186_() && !this.swampjaw.f_19853_.m_46859_(this.swampjaw.m_20183_().m_6630_(1))) {
            this.height = Math.min(-1.0F, this.height);
            this.updateOffset();
         }
      }

      private void updateOffset() {
         if (BlockPos.f_121853_.equals(this.swampjaw.orbitPosition)) {
            this.swampjaw.orbitPosition = this.swampjaw.m_20183_();
         }

         this.angle = this.angle + this.direction * 20.0F * (float) (Math.PI / 180.0);
         this.swampjaw.orbitOffset = Vec3.m_82528_(this.swampjaw.orbitPosition)
            .m_82520_((double)(this.radius * Mth.m_14089_(this.angle)), (double)(-4.0F + this.height), (double)(this.radius * Mth.m_14031_(this.angle)));
      }
   }

   private static class PickAttackGoal extends Goal {
      private int tickDelay;
      private int bombLeft;
      private SwampjawEntity swampjaw;

      public PickAttackGoal(SwampjawEntity swampjaw) {
         this.swampjaw = swampjaw;
      }

      public boolean m_183429_() {
         return true;
      }

      public boolean m_8036_() {
         LivingEntity livingentity = this.swampjaw.m_5448_();
         return livingentity != null ? this.swampjaw.m_21040_(this.swampjaw.m_5448_(), PhantomAttackPlayer.DEFAULT_BUT_THROUGH_WALLS) : false;
      }

      public void m_8056_() {
         this.tickDelay = 100;
         this.bombLeft = 3;
         this.swampjaw.behavior = 0;
         this.updateOrbit();
      }

      public void m_8041_() {
      }

      public void m_8037_() {
         if (this.swampjaw.behavior == 0 || this.swampjaw.behavior == 1) {
            this.tickDelay--;
            if (this.tickDelay <= 0) {
               if (this.bombLeft <= 0) {
                  this.bombLeft = 3;
                  this.swampjaw.behavior = 2;
                  this.updateOrbit();
                  this.tickDelay = (4 + this.swampjaw.f_19796_.m_188503_(4)) * 20;
                  this.swampjaw.m_5496_((SoundEvent)ModSounds.swampjawCharge.get(), 10.0F, 0.95F + this.swampjaw.f_19796_.m_188501_() * 0.1F);
               } else if (this.swampjaw.behavior == 0) {
                  this.swampjaw.behavior = 1;
                  this.tickDelay = 20;
               } else if (this.tickDelay <= -120 || this.isTargetClose()) {
                  this.bombLeft--;
                  if (this.bombLeft <= 0) {
                     this.tickDelay = 30 + this.swampjaw.f_19796_.m_188503_(30);
                  } else {
                     this.tickDelay = 20;
                  }

                  this.updateOrbit();
                  this.swampjaw.m_5496_((SoundEvent)ModSounds.swampjawBomb.get(), 10.0F, 0.95F + this.swampjaw.f_19796_.m_188501_() * 0.1F);
                  SwampMineEntity tntentity = new SwampMineEntity(
                     this.swampjaw.f_19853_, this.swampjaw.m_20185_() + 0.5, this.swampjaw.m_20186_(), this.swampjaw.m_20189_() + 0.5, this.swampjaw
                  );
                  Vec3 motion = this.swampjaw.m_20184_();
                  tntentity.m_20256_(tntentity.m_20184_().m_82520_(motion.f_82479_ * 0.5, 0.0, motion.f_82481_ * 0.5));
                  this.swampjaw.f_19853_.m_7967_(tntentity);
               }
            }
         }
      }

      private boolean isTargetClose() {
         LivingEntity target = this.swampjaw.m_5448_();
         if (target == null) {
            return false;
         } else {
            double dx = target.m_20185_() - (this.swampjaw.m_20185_() + this.swampjaw.m_20184_().f_82479_);
            double dz = target.m_20189_() - (this.swampjaw.m_20189_() + this.swampjaw.m_20184_().f_82481_);
            return dx * dx + dz * dz < 12.0;
         }
      }

      private void updateOrbit() {
         this.swampjaw.orbitPosition = this.swampjaw.m_5448_().m_20183_().m_6630_(14 + this.swampjaw.f_19796_.m_188503_(6));
      }
   }

   private static class SweepAttackGoal extends SwampjawEntity.BaseMoveGoal {
      public SweepAttackGoal(SwampjawEntity swampjaw) {
         super(swampjaw);
      }

      public boolean m_8036_() {
         return this.swampjaw.m_5448_() != null && this.swampjaw.behavior == 2;
      }

      public boolean m_8045_() {
         LivingEntity livingentity = this.swampjaw.m_5448_();
         if (livingentity == null) {
            return false;
         } else if (!livingentity.m_6084_()) {
            return false;
         } else {
            return livingentity instanceof Player && (((Player)livingentity).m_5833_() || ((Player)livingentity).m_7500_()) ? false : this.m_8036_();
         }
      }

      public void m_8041_() {
         this.swampjaw.behavior = 0;
      }

      public void m_8037_() {
         LivingEntity livingentity = this.swampjaw.m_5448_();
         this.swampjaw.orbitOffset = new Vec3(livingentity.m_20185_(), livingentity.m_20227_(0.5), livingentity.m_20189_());
         if (this.swampjaw.m_20191_().m_82400_(0.2).m_82381_(livingentity.m_20191_())) {
            this.swampjaw.m_7327_(livingentity);
            this.swampjaw.behavior = 0;
         } else if (this.swampjaw.f_20916_ > 0) {
            this.swampjaw.behavior = 0;
         }
      }
   }
}
