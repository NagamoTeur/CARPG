package lykrast.meetyourfight.entity;

import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.ai.MoveAroundTarget;
import lykrast.meetyourfight.entity.ai.StationaryAttack;
import lykrast.meetyourfight.entity.ai.VexMoveRandomGoal;
import lykrast.meetyourfight.entity.movement.VexMovementController;
import lykrast.meetyourfight.registry.ModEntities;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DameFortunaEntity extends BossEntity {
   private static final EntityDataAccessor<Byte> STATUS = SynchedEntityData.m_135353_(DameFortunaEntity.class, EntityDataSerializers.f_135027_);
   public static final int NO_ATTACK = 0;
   public static final int PROJ_ATTACK = 1;
   public static final int CLAW_ATTACK = 2;
   private static final int ATTACK_MASK = 3;
   private static final int RAGE_MASK = -4;
   public int attackCooldown;
   public int headTargetPitch;
   public int headTargetYaw;
   public int headTargetRoll;
   public int headRotationTimer;
   public float headRotationProgress;
   public float headRotationProgressLast;
   private int rage;

   public DameFortunaEntity(EntityType<? extends DameFortunaEntity> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new VexMovementController(this);
      this.f_21364_ = 100;
      this.rage = 0;
      this.headRotationTimer = 30;
      this.headTargetPitch = 0;
      this.headTargetYaw = 0;
      this.headTargetRoll = 0;
      this.headRotationProgress = 1.0F;
      this.headRotationProgressLast = 1.0F;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new DameFortunaEntity.RegularAttack(this));
      this.f_21345_.m_25352_(3, new DameFortunaEntity.RageEvokerLines(this));
      this.f_21345_.m_25352_(7, new MoveAroundTarget(this, 1.0));
      this.f_21345_.m_25352_(8, new VexMoveRandomGoal(this, 0.25));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, false));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 300.0).m_22268_(Attributes.f_22284_, 5.0).m_22268_(Attributes.f_22277_, 64.0);
   }

   public void m_6478_(MoverType typeIn, Vec3 pos) {
      super.m_6478_(typeIn, pos);
      this.m_20101_();
   }

   public void m_8119_() {
      this.f_19794_ = true;
      super.m_8119_();
      this.f_19794_ = false;
      this.m_20242_(true);
      if (this.f_19853_.f_46443_) {
         this.headRotationTimer--;
         if (this.headRotationTimer <= 0) {
            switch (this.getRage()) {
               case 0:
                  this.headRotationTimer = 20 + this.f_19796_.m_188503_(21);
                  break;
               case 1:
                  this.headRotationTimer = 15 + this.f_19796_.m_188503_(11);
                  break;
               default:
                  this.headRotationTimer = 5 + this.f_19796_.m_188503_(11);
            }

            this.rotateHead();
            this.headRotationProgress = 0.0F;
            this.headRotationProgressLast = 0.0F;
         } else {
            this.headRotationProgressLast = this.headRotationProgress;
            this.headRotationProgress = Math.min(1.0F, this.headRotationProgress + 0.07F);
         }
      }
   }

   public float getHeadRotationProgress(float partialTicks) {
      return Mth.m_14179_(partialTicks, this.headRotationProgressLast, this.headRotationProgress);
   }

   private void rotateHead() {
      boolean reverse = this.f_19796_.m_188499_();
      int axis = this.f_19796_.m_188503_(3);
      switch (axis) {
         case 0:
            if (reverse) {
               if (this.headTargetPitch <= 0) {
                  this.headTargetPitch = 3;
               } else {
                  this.headTargetPitch--;
               }
            } else {
               this.headTargetPitch = (this.headTargetPitch + 1) % 4;
            }
            break;
         case 1:
            if (reverse) {
               if (this.headTargetYaw <= 0) {
                  this.headTargetYaw = 3;
               } else {
                  this.headTargetYaw--;
               }
            } else {
               this.headTargetYaw = (this.headTargetYaw + 1) % 4;
            }
            break;
         case 2:
            if (reverse) {
               if (this.headTargetRoll <= 0) {
                  this.headTargetRoll = 3;
               } else {
                  this.headTargetRoll--;
               }
            } else {
               this.headTargetRoll = (this.headTargetRoll + 1) % 4;
            }
      }
   }

   public static void spawn(Player player, Level world) {
      RandomSource rand = player.m_217043_();
      DameFortunaEntity dame = (DameFortunaEntity)((EntityType)ModEntities.DAME_FORTUNA.get()).m_20615_(world);
      dame.m_7678_(
         player.m_20185_() + (double)rand.m_188503_(5) - 2.0,
         player.m_20186_() + (double)rand.m_188503_(3) + 3.0,
         player.m_20189_() + (double)rand.m_188503_(5) - 2.0,
         rand.m_188501_() * 360.0F - 180.0F,
         0.0F
      );
      dame.attackCooldown = 100;
      if (!player.m_150110_().f_35937_) {
         dame.m_6710_(player);
      }

      dame.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 100, 2));
      dame.m_6518_((ServerLevel)world, world.m_6436_(dame.m_20183_()), MobSpawnType.EVENT, null, null);
      world.m_7967_(dame);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(STATUS, (byte)0);
   }

   public int getAttack() {
      return (Byte)this.f_19804_.m_135370_(STATUS) & 3;
   }

   public void setAttack(int attack) {
      int rage = (Byte)this.f_19804_.m_135370_(STATUS) & -4;
      this.f_19804_.m_135381_(STATUS, (byte)(rage | attack));
   }

   public int getRage() {
      return ((Byte)this.f_19804_.m_135370_(STATUS) & -4) >> 2;
   }

   public void setRage(int rage) {
      int attack = (Byte)this.f_19804_.m_135370_(STATUS) & 3;
      this.f_19804_.m_135381_(STATUS, (byte)(rage << 2 | attack));
   }

   private int getRageTarget() {
      float health = this.m_21223_();
      float third = this.m_21233_() / 3.0F;
      if (health <= third) {
         return 2;
      } else {
         return health >= third * 2.0F ? 0 : 1;
      }
   }

   @Override
   public void m_8024_() {
      if (this.attackCooldown > 0) {
         this.attackCooldown--;
      }

      int newrage = this.getRageTarget();
      if (newrage > this.rage) {
         this.rage = newrage;
         this.setRage(this.rage);
      }

      super.m_8024_();
   }

   private ProjectileLineEntity readyLine() {
      ProjectileLineEntity proj = new ProjectileLineEntity(this.f_19853_, this, 0.0, 0.0, 0.0);
      proj.m_5602_(this);
      proj.m_6034_(this.m_20185_(), this.m_20188_() + 1.0, this.m_20189_());
      proj.setVariant(1);
      return proj;
   }

   private void spawnFangs(double posX, double posZ, double minY, double minZ, float rotationRad, int delay) {
      BlockPos blockpos = new BlockPos(posX, minZ, posZ);
      boolean success = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = this.f_19853_.m_8055_(blockpos1);
         if (blockstate.m_60783_(this.f_19853_, blockpos1, Direction.UP)) {
            if (!this.f_19853_.m_46859_(blockpos)) {
               BlockState blockstate1 = this.f_19853_.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(this.f_19853_, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            success = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14107_(minY) - 1);

      if (success) {
         this.f_19853_.m_7967_(new EvokerFangs(this.f_19853_, posX, (double)blockpos.m_123342_() + d0, posZ, rotationRad, delay, this));
      }
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("AttackCooldown")) {
         this.attackCooldown = compound.m_128451_("AttackCooldown");
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("AttackCooldown", this.attackCooldown);
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.dameFortunaIdle.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.dameFortunaHurt.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.dameFortunaDeath.get();
   }

   @Override
   protected SoundEvent getMusic() {
      return (SoundEvent)ModSounds.musicMagnum.get();
   }

   protected ResourceLocation m_7582_() {
      return MeetYourFight.rl("dame_fortuna");
   }

   private static class RageEvokerLines extends Goal {
      private DameFortunaEntity dame;
      private LivingEntity target;
      private int delay;

      public RageEvokerLines(DameFortunaEntity dame) {
         this.dame = dame;
      }

      public boolean m_183429_() {
         return true;
      }

      public boolean m_8036_() {
         return this.dame.rage >= 2 && this.dame.m_5448_() != null && this.dame.m_5448_().m_6084_();
      }

      public void m_8056_() {
         this.target = this.dame.m_5448_();
         this.delay = 80;
      }

      public void m_8037_() {
         this.delay--;
         if (this.delay <= 0) {
            double tx = this.target.m_20185_();
            double ty = this.target.m_20186_();
            double tz = this.target.m_20189_();
            double minY = Math.min(ty, this.dame.m_20186_());
            double maxY = Math.max(ty, this.dame.m_20186_()) + 1.0;
            float angle = (float)Mth.m_14136_(tz - this.dame.m_20189_(), tx - this.dame.m_20185_());

            for (int i = 0; i < 16; i++) {
               double dist = 1.25 * (double)(i + 1);
               this.dame
                  .spawnFangs(
                     this.dame.m_20185_() + (double)Mth.m_14089_(angle) * dist, this.dame.m_20189_() + (double)Mth.m_14031_(angle) * dist, minY, maxY, angle, i
                  );
            }
         }
      }

      public boolean m_8045_() {
         return this.delay > 0 && this.target.m_6084_();
      }
   }

   private static class RegularAttack extends StationaryAttack {
      private DameFortunaEntity dame;
      private LivingEntity target;
      private int attackRemaining;
      private int attackDelay;
      private int chosenAttack;

      public RegularAttack(DameFortunaEntity dame) {
         super(dame);
         this.dame = dame;
      }

      @Override
      public boolean m_183429_() {
         return true;
      }

      public boolean m_8036_() {
         return this.dame.attackCooldown <= 0 && this.dame.m_5448_() != null && this.dame.m_5448_().m_6084_();
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.dame.attackCooldown = 2;
         this.target = this.dame.m_5448_();
         this.chosenAttack = this.dame.f_19796_.m_188503_(3);
         this.dame.setAttack(this.chosenAttack == 1 ? 2 : 1);
         this.attackDelay = 30;
         this.attackRemaining = this.getAttackCount();
         this.dame.m_5496_((SoundEvent)ModSounds.dameFortunaAttack.get(), this.dame.m_6121_(), this.dame.m_6100_());
      }

      private int getAttackCount() {
         switch (this.chosenAttack) {
            case 0:
            case 2:
            default:
               return 4 + this.dame.rage;
            case 1:
               return 8 + this.dame.rage * 2;
         }
      }

      @Override
      public void m_8037_() {
         super.m_8037_();
         this.dame.attackCooldown = 2;
         this.attackDelay--;
         if (this.attackDelay <= 0) {
            this.attackRemaining--;
            this.performAttack();
            if (this.attackRemaining <= 0) {
               this.m_8041_();
            }
         }
      }

      private void performAttack() {
         double tx = this.target.m_20185_();
         double ty = this.target.m_20186_();
         double tz = this.target.m_20189_();
         switch (this.chosenAttack) {
            case 0:
            default:
               this.attackDelay = 20;
               BlockPos self = this.dame.m_20183_();
               double sx = (double)self.m_123341_();
               double sz = (double)self.m_123343_();
               Direction dir = Direction.m_122366_(tx - sx, 0.0, tz - sz);
               double cx = (double)dir.m_122429_();
               double cz = (double)dir.m_122431_();

               for (int i = -4; i <= 4; i++) {
                  if ((i + 4) % 2 == this.attackRemaining % 2) {
                     for (int y = 0; y < 3; y++) {
                        ProjectileLineEntity proj = this.dame.readyLine();
                        proj.setUp(10, cx, 0.0, cz, tx - 7.0 * cx + 1.5 * (double)i * cz, ty + 1.5 * (double)y, tz - 7.0 * cz + 1.5 * (double)i * cx);
                        this.dame.f_19853_.m_7967_(proj);
                     }
                  }
               }

               this.dame
                  .m_5496_((SoundEvent)ModSounds.dameFortunaShoot.get(), 2.0F, (this.dame.f_19796_.m_188501_() - this.dame.f_19796_.m_188501_()) * 0.2F + 1.0F);
               break;
            case 1:
               this.attackDelay = 10;
               double minY = Math.min(ty, this.dame.m_20186_());
               double maxY = Math.max(ty, this.dame.m_20186_()) + 1.0;
               float angle = (float)Mth.m_14136_(tz - this.dame.m_20189_(), tx - this.dame.m_20185_());
               this.dame.spawnFangs(tx, tz, minY, maxY, angle, 0);
               break;
            case 2:
               this.attackDelay = 20;

               for (int x = -3; x <= 3; x++) {
                  for (int z = -3; z <= 3; z++) {
                     if ((x + z + 6) % 2 == this.attackRemaining % 2) {
                        ProjectileLineEntity proj = this.dame.readyLine();
                        proj.setUp(15, 0.0, -1.0, 0.0, tx + (double)x * 1.6, ty + 7.0, tz + (double)z * 1.6);
                        this.dame.f_19853_.m_7967_(proj);
                     }
                  }
               }

               this.dame
                  .m_5496_((SoundEvent)ModSounds.dameFortunaShoot.get(), 2.0F, (this.dame.f_19796_.m_188501_() - this.dame.f_19796_.m_188501_()) * 0.2F + 1.0F);
         }
      }

      public void m_8041_() {
         this.dame.attackCooldown = 40 + this.dame.f_19796_.m_188503_(21);
         this.dame.setAttack(0);
      }

      public boolean m_8045_() {
         return this.attackRemaining > 0 && this.target.m_6084_();
      }
   }
}
