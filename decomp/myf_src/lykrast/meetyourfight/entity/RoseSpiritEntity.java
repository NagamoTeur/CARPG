package lykrast.meetyourfight.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.ai.MoveAroundTarget;
import lykrast.meetyourfight.entity.ai.VexMoveRandomGoal;
import lykrast.meetyourfight.entity.movement.VexMovementController;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RoseSpiritEntity extends Monster {
   private static final EntityDataAccessor<Byte> STATUS = SynchedEntityData.m_135353_(RoseSpiritEntity.class, EntityDataSerializers.f_135027_);
   public static final int HIDING = 0;
   public static final int RISING = 1;
   public static final int OUT = 2;
   public static final int ATTACKING = 3;
   public static final int RETRACTING = 4;
   public static final int HURT = 5;
   public static final int RETRACTING_HURT = 6;
   @Nullable
   private Mob owner;
   public int attackCooldown;
   public int prevStatus;
   public int animDur;
   public int animProg;

   public RoseSpiritEntity(EntityType<? extends RoseSpiritEntity> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new VexMovementController(this).slowdown(0.2);
      this.f_21364_ = 5;
      this.prevStatus = this.getStatus();
      this.animDur = 1;
      this.animProg = 1;
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.owner != null && !this.owner.m_6084_()) {
         this.setOwner(null);
      }
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new RoseSpiritEntity.HideAfterHit(this));
      this.f_21345_.m_25352_(3, new RoseSpiritEntity.BurstAttack(this));
      this.f_21345_.m_25352_(6, new RoseSpiritEntity.MoveAroundOwner(this, 0.35));
      this.f_21345_.m_25352_(7, new MoveAroundTarget(this, 0.35));
      this.f_21345_.m_25352_(8, new VexMoveRandomGoal(this, 0.25));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new RoseSpiritEntity.CopyOwnerTargetGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 40.0).m_22268_(Attributes.f_22284_, 5.0).m_22268_(Attributes.f_22277_, 64.0);
   }

   public Mob getOwner() {
      return this.owner;
   }

   public void setOwner(Mob owner) {
      this.owner = owner;
   }

   public void m_6478_(MoverType typeIn, Vec3 pos) {
      super.m_6478_(typeIn, pos);
      this.m_20101_();
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (!source.m_19378_() && this.getStatus() == 0) {
         if (amount > 1.0F) {
            this.m_5496_((SoundEvent)ModSounds.aceOfIronProc.get(), 1.0F, 1.0F);
         }

         return false;
      } else if (super.m_6469_(source, amount)) {
         if (amount >= 3.0F) {
            this.setStatus(5);
         }

         return true;
      } else {
         return false;
      }
   }

   public void m_8119_() {
      this.f_19794_ = true;
      super.m_8119_();
      this.f_19794_ = false;
      this.m_20242_(true);
      if (this.f_19853_.f_46443_) {
         if (this.prevStatus != this.getStatus()) {
            this.prevStatus = this.getStatus();
            this.animProg = 0;
            if (this.prevStatus == 1 || this.prevStatus == 4 || this.prevStatus == 6) {
               this.animDur = 10;
            }
         } else if (this.animProg < this.animDur) {
            this.animProg++;
         }
      }
   }

   public float getAnimProgress(float partial) {
      return Mth.m_14036_(((float)this.animProg + partial) / (float)this.animDur, 0.0F, 1.0F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(STATUS, (byte)0);
   }

   public int getStatus() {
      return (Byte)this.f_19804_.m_135370_(STATUS);
   }

   public void setStatus(int status) {
      this.f_19804_.m_135381_(STATUS, (byte)status);
   }

   public void m_8024_() {
      if (this.attackCooldown > 0) {
         this.attackCooldown--;
      }

      super.m_8024_();
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("Cooldown")) {
         this.attackCooldown = compound.m_128451_("Cooldown");
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Cooldown", this.attackCooldown);
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.roseSpiritIdle.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.roseSpiritHurt.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.roseSpiritDeath.get();
   }

   protected ResourceLocation m_7582_() {
      return MeetYourFight.rl("rose_spirit");
   }

   private ProjectileLineEntity readyAttack() {
      ProjectileLineEntity ghost = new ProjectileLineEntity(this.f_19853_, this, 0.0, 0.0, 0.0);
      ghost.m_5602_(this);
      ghost.m_6034_(this.m_20185_(), this.m_20186_() + 0.625, this.m_20189_());
      ghost.setVariant(2);
      return ghost;
   }

   private static class BurstAttack extends Goal {
      private RoseSpiritEntity mob;
      private LivingEntity target;
      private int attackRemaining;
      private int attackDelay;
      private int phase;

      public BurstAttack(RoseSpiritEntity mob) {
         this.mob = mob;
      }

      public boolean m_8036_() {
         return this.mob.attackCooldown <= 0 && this.mob.m_5448_() != null && this.mob.m_5448_().m_6084_();
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.mob.attackCooldown = 2;
         this.attackDelay = 10;
         this.attackRemaining = 2 + this.mob.f_19796_.m_188503_(5);
         this.target = this.mob.m_5448_();
         this.phase = 0;
         this.mob.setStatus(1);
      }

      public void m_8037_() {
         this.mob.attackCooldown = 2;
         this.attackDelay--;
         if (this.attackDelay <= 0) {
            switch (this.phase) {
               case 0:
                  this.mob.setStatus(2);
                  this.phase = 1;
                  this.attackDelay = 20 + this.mob.f_19796_.m_188503_(41);
                  break;
               case 1:
                  this.mob.setStatus(3);
                  this.phase = 2;
                  this.attackDelay = 25;
                  this.mob.m_5496_((SoundEvent)ModSounds.roseSpiritWarn.get(), 1.0F, 1.0F);
                  break;
               case 2:
                  this.attackDelay = 25;
                  this.attackRemaining--;
                  this.performAttack();
                  if (this.attackRemaining <= 0) {
                     this.phase = 3;
                     this.mob.setStatus(2);
                     this.attackDelay = 40 + this.mob.f_19796_.m_188503_(41);
                  }
                  break;
               case 3:
                  this.phase = 4;
                  this.attackDelay = 10;
                  this.mob.setStatus(4);
                  break;
               case 4:
                  this.phase = 5;
                  this.mob.setStatus(0);
            }
         }
      }

      private void performAttack() {
         double sx = this.mob.m_20185_();
         double sy = this.mob.m_20186_();
         double sz = this.mob.m_20189_();
         Vec3 dir = new Vec3(this.target.m_20185_() - sx, this.target.m_20186_() + 1.0 - sy, this.target.m_20189_() - sz).m_82541_();
         ProjectileLineEntity ghost = this.mob.readyAttack();
         ghost.setUp(1, dir.f_82479_, dir.f_82480_, dir.f_82481_, sx, sy, sz);
         this.mob.f_19853_.m_7967_(ghost);
         this.mob.m_5496_((SoundEvent)ModSounds.roseSpiritShoot.get(), 1.0F, 1.0F);
      }

      public void m_8041_() {
         this.mob.attackCooldown = 60 + this.mob.f_19796_.m_188503_(41);
      }

      public boolean m_8045_() {
         return this.phase <= 4 && this.target.m_6084_() && this.mob.getStatus() != 5 && this.mob.getStatus() != 6;
      }
   }

   private static class CopyOwnerTargetGoal extends TargetGoal {
      private RoseSpiritEntity spirit;
      private final TargetingConditions copyOwnerTargeting = TargetingConditions.m_148353_().m_148355_().m_26893_();

      public CopyOwnerTargetGoal(RoseSpiritEntity spirit) {
         super(spirit, false);
         this.spirit = spirit;
      }

      public boolean m_8036_() {
         return this.spirit.owner != null && this.spirit.owner.m_5448_() != null && this.m_26150_(this.spirit.owner.m_5448_(), this.copyOwnerTargeting);
      }

      public void m_8056_() {
         this.spirit.m_6710_(this.spirit.owner.m_5448_());
         super.m_8056_();
      }
   }

   private static class HideAfterHit extends Goal {
      private RoseSpiritEntity mob;
      private int timer;

      public HideAfterHit(RoseSpiritEntity mob) {
         this.mob = mob;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.mob.attackCooldown = 100 + this.mob.f_19796_.m_188503_(61);
         this.timer = 60;
         this.mob.m_5496_((SoundEvent)ModSounds.roseSpiritHurtBig.get(), 1.0F, 1.0F);
      }

      public void m_8037_() {
         this.timer--;
         if (this.timer <= 0) {
            this.mob.setStatus(0);
         } else if (this.timer == 10) {
            this.mob.setStatus(6);
            double sx = this.mob.m_20185_();
            double sy = this.mob.m_20186_();
            double sz = this.mob.m_20189_();
            Vec3 dir = null;
            if (this.mob.m_5448_() != null) {
               dir = new Vec3(this.mob.m_5448_().m_20185_() - sx, this.mob.m_5448_().m_20186_() + 1.0 - sy, this.mob.m_5448_().m_20189_() - sz);
            } else {
               dir = new Vec3(1.0, -0.25, 0.0);
            }

            dir = dir.m_82541_();

            for (int i = 0; i < 8; i++) {
               ProjectileLineEntity ghost = this.mob.readyAttack();
               ghost.setUp(1, dir.f_82479_, dir.f_82480_, dir.f_82481_, sx, sy, sz);
               this.mob.f_19853_.m_7967_(ghost);
               dir = dir.m_82524_((float) (Math.PI / 4));
            }

            this.mob.m_5496_((SoundEvent)ModSounds.roseSpiritShoot.get(), 1.0F, 1.0F);
         }
      }

      public boolean m_8036_() {
         return this.mob.getStatus() == 5 || this.mob.getStatus() == 6;
      }
   }

   private static class MoveAroundOwner extends Goal {
      private RoseSpiritEntity mob;
      private double speed;

      public MoveAroundOwner(RoseSpiritEntity mob, double speed) {
         this.m_7021_(EnumSet.of(Flag.MOVE));
         this.mob = mob;
         this.speed = speed;
      }

      public boolean m_8036_() {
         return this.mob.owner != null && !this.mob.m_21566_().m_24995_();
      }

      public void m_8056_() {
         LivingEntity target = this.mob.owner;
         RandomSource rand = this.mob.m_217043_();
         float angle = (float)(rand.m_188503_(4) + 2) * 10.0F * (float) (Math.PI / 180.0);
         Vec3 offset = new Vec3(this.mob.m_20185_() - target.m_20185_(), 0.0, this.mob.m_20189_() - target.m_20189_()).m_82541_().m_82524_(angle);
         double distance = rand.m_188500_() * 2.0 + 4.0;
         double actSpeed = this.mob.m_20280_(this.mob.owner) > 100.0 ? 6.0 : this.speed;
         this.mob
            .m_21566_()
            .m_6849_(
               target.m_20185_() + offset.f_82479_ * distance,
               target.m_20186_() + rand.m_188500_() * 2.0,
               target.m_20189_() + offset.f_82481_ * distance,
               actSpeed
            );
      }

      public boolean m_8045_() {
         return false;
      }
   }
}
