package lykrast.meetyourfight.entity;

import java.util.EnumSet;
import java.util.List;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.ai.MoveFrontOfTarget;
import lykrast.meetyourfight.entity.ai.StationaryAttack;
import lykrast.meetyourfight.entity.ai.VexMoveRandomGoal;
import lykrast.meetyourfight.entity.movement.VexMovementController;
import lykrast.meetyourfight.registry.ModEntities;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.Vec3;

public class RosalyneEntity extends BossEntity implements PowerableMob {
   private static final EntityDataAccessor<Byte> STATUS = SynchedEntityData.m_135353_(RosalyneEntity.class, EntityDataSerializers.f_135027_);
   public static final int ENCASED = 0;
   public static final int BREAKING_OUT = 1;
   public static final int PHASE_1 = 2;
   public static final int SUMMONING = 3;
   public static final int PHASE_2 = 4;
   public static final int MADDENING = 5;
   public static final int PHASE_3 = 6;
   public static final int ANIM_NEUTRAL = 0;
   public static final int ANIM_ARM_OUT_UP = 1;
   public static final int ANIM_ARM_IN_UP = 2;
   public static final int ANIM_ARM_OUT_DN = 3;
   public static final int ANIM_ARM_IN_DN = 4;
   public static final int ANIM_PREPARE_DASH = 5;
   public static final int ANIM_BROKE_OUT = 6;
   public static final int ANIM_SUMMONING = 7;
   public static final int ANIM_MADDENING = 8;
   public static final int ANIM_PREPARE_CRASH = 9;
   public static final int ANIM_SWING_CRASH = 10;
   private static final int PHASE_MASK = 7;
   private static final int ANIMATION_MASK = -8;
   private final TargetingConditions spiritCountTargeting = TargetingConditions.m_148353_().m_26883_(32.0).m_148355_().m_26893_();
   public int attackCooldown;
   private int phase;
   private int nextAttack;
   public int clientAnim;
   public int prevAnim;
   public int animProg;
   public int animDur;

   public RosalyneEntity(EntityType<? extends RosalyneEntity> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new VexMovementController(this).slowdown(0.1);
      this.f_21364_ = 200;
      this.phase = 0;
      this.clientAnim = 0;
      this.prevAnim = 0;
      this.animProg = 1;
      this.animDur = 1;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new RosalyneEntity.PhaseTransition(this));
      this.f_21345_.m_25352_(2, new RosalyneEntity.AdvanceAndSwingAttack(this));
      this.f_21345_.m_25352_(2, new RosalyneEntity.CircleAndDashAttack(this));
      this.f_21345_.m_25352_(2, new RosalyneEntity.VerticalCrashAttack(this));
      this.f_21345_.m_25352_(7, new MoveFrontOfTarget(this, 0.5));
      this.f_21345_.m_25352_(8, new VexMoveRandomGoal(this, 0.25));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, false));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder createAttributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 500.0)
         .m_22268_(Attributes.f_22284_, 8.0)
         .m_22268_(Attributes.f_22285_, 4.0)
         .m_22268_(Attributes.f_22278_, 1.0)
         .m_22268_(Attributes.f_22281_, 24.0)
         .m_22268_(Attributes.f_22277_, 64.0);
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
      if (this.f_19853_.m_5776_()) {
         int newanim = this.getAnimation();
         if (this.clientAnim != newanim) {
            this.prevAnim = this.clientAnim;
            this.clientAnim = newanim;
            this.animProg = 0;
            this.animDur = 5;
            if (this.prevAnim == 6) {
               this.animDur = 60;
            } else if (this.clientAnim == 7) {
               this.animDur = 10;
            } else if (this.prevAnim == 7) {
               this.animDur = 20;
            }
         } else if (this.animProg < this.animDur) {
            this.animProg++;
         }
      }
   }

   public float getAnimProgress(float partial) {
      return Mth.m_14036_(((float)this.animProg + partial) / (float)this.animDur, 0.0F, 1.0F);
   }

   public static void spawn(Player player, Level world) {
      RandomSource rand = player.m_217043_();
      RosalyneEntity dame = (RosalyneEntity)((EntityType)ModEntities.ROSALYNE.get()).m_20615_(world);
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

      dame.m_6518_((ServerLevel)world, world.m_6436_(dame.m_20183_()), MobSpawnType.EVENT, null, null);
      world.m_7967_(dame);
      dame.createSpirits();
   }

   private void createSpirits() {
      for (int i = 0; i < 4; i++) {
         RoseSpiritEntity spirit = (RoseSpiritEntity)((EntityType)ModEntities.ROSE_SPIRIT.get()).m_20615_(this.f_19853_);
         spirit.m_6027_(this.m_20185_() + (double)(i / 2 * 4) - 2.0, this.m_20186_(), this.m_20189_() + (double)(i % 2 * 4) - 2.0);
         spirit.setOwner(this);
         if (this.m_5448_() != null) {
            spirit.m_6710_(this.m_5448_());
         }

         spirit.attackCooldown = 80 + 60 * i;
         spirit.m_6518_((ServerLevel)this.f_19853_, this.f_19853_.m_6436_(this.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
         this.f_19853_.m_7967_(spirit);
      }
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (!source.m_19378_() && this.getPhase() != 2 && this.getPhase() != 6) {
         if (amount > 1.0F) {
            this.m_5496_((SoundEvent)ModSounds.aceOfIronProc.get(), 1.0F, 1.0F);
         }

         return false;
      } else {
         return super.m_6469_(source, amount);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(STATUS, (byte)0);
   }

   public int getPhase() {
      return (Byte)this.f_19804_.m_135370_(STATUS) & 7;
   }

   public void setPhase(int phase) {
      int anim = (Byte)this.f_19804_.m_135370_(STATUS) & -8;
      this.f_19804_.m_135381_(STATUS, (byte)(anim | phase));
   }

   public int getAnimation() {
      return ((Byte)this.f_19804_.m_135370_(STATUS) & -8) >> 3;
   }

   public void setAnimation(int anim) {
      int phase = (Byte)this.f_19804_.m_135370_(STATUS) & 7;
      this.f_19804_.m_135381_(STATUS, (byte)(anim << 3 | phase));
   }

   public boolean m_7090_() {
      int phase = this.getPhase();
      return phase == 3 || phase == 4 || phase == 5;
   }

   public void swing() {
      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(2.0, 0.2, 2.0))) {
         if (!(entity instanceof RosalyneEntity) && !(entity instanceof RoseSpiritEntity) && entity.m_6084_()) {
            this.m_7327_(entity);
         }
      }

      this.m_5496_((SoundEvent)ModSounds.rosalyneSwing.get(), 1.0F, 1.0F);
      switch (this.getAnimation()) {
         case 1:
            this.setAnimation(4);
            break;
         case 2:
            this.setAnimation(1);
            break;
         case 3:
            this.setAnimation(2);
            break;
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         default:
            this.setAnimation(3);
            break;
         case 9:
            this.setAnimation(10);
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if ((this.phase == 0 || this.phase == 4) && this.f_19797_ % 20 == 0) {
         List<RoseSpiritEntity> list = this.f_19853_.m_45971_(RoseSpiritEntity.class, this.spiritCountTargeting, this, this.m_20191_().m_82400_(32.0));

         for (RoseSpiritEntity spirit : list) {
            spirit.setOwner(this);
         }

         if (!list.isEmpty() && !this.m_213877_()) {
            this.m_21153_(this.m_21223_() + 1.0F);
         }
      }
   }

   @Override
   public void m_8024_() {
      if (this.attackCooldown > 0) {
         this.attackCooldown--;
      }

      if (this.phase != this.getPhase()) {
         this.phase = this.getPhase();
      }

      if ((this.phase == 0 || this.phase == 4) && this.f_19797_ % 10 == 0) {
         if (this.f_19853_.m_45971_(RoseSpiritEntity.class, this.spiritCountTargeting, this, this.m_20191_().m_82400_(32.0)).isEmpty()) {
            if (this.phase == 0) {
               this.setPhase(1);
               this.phase = 1;
            } else if (this.phase == 4) {
               this.setPhase(5);
               this.phase = 5;
            }
         }
      } else if (this.phase == 2 && this.m_21223_() < this.m_21233_() / 2.0F) {
         this.setPhase(3);
         this.phase = 3;
      }

      super.m_8024_();
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("Cooldown")) {
         this.attackCooldown = compound.m_128451_("Cooldown");
      }

      if (compound.m_128441_("Phase")) {
         this.setPhase(compound.m_128451_("Phase"));
      }

      if (compound.m_128441_("NxtAt")) {
         this.nextAttack = compound.m_128451_("NxtAt");
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Cooldown", this.attackCooldown);
      compound.m_128405_("Phase", this.getPhase());
      compound.m_128405_("NxtAt", this.nextAttack);
   }

   protected SoundEvent m_7515_() {
      return null;
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.rosalyneHurt.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.rosalyneDeath.get();
   }

   @Override
   protected SoundEvent getMusic() {
      return (SoundEvent)ModSounds.musicFrogPunch.get();
   }

   protected ResourceLocation m_7582_() {
      return MeetYourFight.rl("rosalyne");
   }

   private void rollNextAttack(int ignore) {
      if (ignore >= 0) {
         this.nextAttack = this.f_19796_.m_188503_(2);
         if (this.nextAttack >= ignore) {
            this.nextAttack++;
         }
      } else {
         this.nextAttack = this.f_19796_.m_188503_(3);
      }
   }

   private void cooldownNextAttack() {
      this.attackCooldown = 60 + this.f_19796_.m_188503_(21);
      if (this.phase == 4) {
         this.attackCooldown += 20;
      } else if (this.phase == 6) {
         this.attackCooldown -= 40;
      }
   }

   private static class AdvanceAndSwingAttack extends Goal {
      private RosalyneEntity rosalyne;
      private int timer;
      private int swingsLeft;
      private int attackPhase;
      private double holdx;
      private double holdy;
      private double holdz;

      public AdvanceAndSwingAttack(RosalyneEntity rosalyne) {
         this.rosalyne = rosalyne;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.timer = 100;
         this.swingsLeft = 3 + this.rosalyne.f_19796_.m_188503_(2);
         if (this.rosalyne.phase == 6) {
            this.swingsLeft = 8 + this.rosalyne.f_19796_.m_188503_(5);
         }

         this.attackPhase = 0;
         LivingEntity target = this.rosalyne.m_5448_();
         this.rosalyne.f_21342_.m_6849_(target.m_20185_(), target.m_20186_(), target.m_20189_(), 4.0);
      }

      public void m_8037_() {
         this.timer--;
         LivingEntity target = this.rosalyne.m_5448_();
         if (this.attackPhase == 0) {
            if (this.timer > 0 && !(this.rosalyne.m_20280_(target) < 2.0)) {
               this.rosalyne.f_21342_.m_6849_(target.m_20185_(), target.m_20186_(), target.m_20189_(), 4.0);
            } else {
               this.holdx = this.rosalyne.m_20185_();
               this.holdy = target.m_20186_();
               this.holdz = this.rosalyne.m_20189_();
               this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 4.0);
               this.attackPhase = 1;
               this.timer = 25;
               this.rosalyne.setAnimation(3);
            }
         } else if (this.attackPhase == 1) {
            this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 4.0);
            if (this.timer <= 0) {
               this.attackPhase = 2;
               this.rosalyne.swing();
               this.swingsLeft--;
               this.timer = this.rosalyne.phase == 6 ? 12 : 20;
            }
         } else {
            this.rosalyne.f_21342_.m_6849_(target.m_20185_(), target.m_20186_(), target.m_20189_(), 0.5);
            if (this.timer <= 0 && this.swingsLeft > 0) {
               this.rosalyne.swing();
               this.swingsLeft--;
               this.timer = this.rosalyne.phase == 6 ? 12 : 20;
            }
         }
      }

      public void m_8041_() {
         this.rosalyne.cooldownNextAttack();
         this.rosalyne.setAnimation(0);
         this.rosalyne.rollNextAttack(0);
      }

      public boolean m_8036_() {
         return this.rosalyne.nextAttack == 0
            && (this.rosalyne.phase == 2 || this.rosalyne.phase == 4 || this.rosalyne.phase == 6)
            && this.rosalyne.m_5448_() != null
            && this.rosalyne.m_5448_().m_6084_()
            && this.rosalyne.attackCooldown <= 0;
      }

      public boolean m_8045_() {
         return this.m_8036_() && (this.swingsLeft > 0 || this.timer > 0);
      }
   }

   private static class CircleAndDashAttack extends Goal {
      private RosalyneEntity rosalyne;
      private int timer;
      private int swingsLeft;
      private int attackPhase;
      private double holdx;
      private double holdy;
      private double holdz;
      private Vec3 offset;

      public CircleAndDashAttack(RosalyneEntity rosalyne) {
         this.rosalyne = rosalyne;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.timer = 100;
         this.swingsLeft = 1 + this.rosalyne.f_19796_.m_188503_(2);
         if (this.rosalyne.phase == 6) {
            this.swingsLeft = 3 + this.rosalyne.f_19796_.m_188503_(3);
         }

         this.startCircling();
      }

      private void startCircling() {
         this.attackPhase = 0;
         LivingEntity target = this.rosalyne.m_5448_();
         this.offset = new Vec3(this.rosalyne.m_20185_() - target.m_20185_(), 1.0, this.rosalyne.m_20189_() - target.m_20189_()).m_82541_().m_82490_(4.0);
         this.rosalyne
            .f_21342_
            .m_6849_(target.m_20185_() + this.offset.f_82479_, target.m_20186_() + this.offset.f_82480_, target.m_20189_() + this.offset.f_82481_, 4.0);
         this.rosalyne.setAnimation(5);
      }

      public void m_8037_() {
         this.timer--;
         LivingEntity target = this.rosalyne.m_5448_();
         if (this.attackPhase == 0) {
            double tx = target.m_20185_() + this.offset.f_82479_;
            double ty = target.m_20186_() + this.offset.f_82480_;
            double tz = target.m_20189_() + this.offset.f_82481_;
            if (this.timer <= 0 || this.rosalyne.m_20275_(tx, ty, tz) < 1.0) {
               this.attackPhase = 1;
               this.timer = 18 + this.rosalyne.f_19796_.m_188503_(36);
            }

            this.rosalyne.f_21342_.m_6849_(tx, ty, tz, 4.0);
         } else if (this.attackPhase == 1) {
            this.offset = this.offset.m_82524_(-0.08726646F);
            double tx = target.m_20185_() + this.offset.f_82479_;
            double ty = target.m_20186_() + this.offset.f_82480_;
            double tz = target.m_20189_() + this.offset.f_82481_;
            this.rosalyne.f_21342_.m_6849_(tx, ty, tz, 4.0);
            if (this.timer <= 0) {
               this.attackPhase = 2;
               this.holdx = tx;
               this.holdy = ty;
               this.holdz = tz;
               this.timer = this.rosalyne.phase == 6 ? 15 : 20;
               this.rosalyne.setAnimation(3);
               this.rosalyne.m_5496_((SoundEvent)ModSounds.rosalyneSwingPrepare.get(), 1.0F, 1.0F);
            }
         } else if (this.attackPhase == 2) {
            if (this.timer <= 0) {
               this.attackPhase = 3;
               this.timer = 20;
               double tx = target.m_20185_();
               double ty = target.m_20186_();
               double tz = target.m_20189_();
               Vec3 tpos = new Vec3(tx - this.holdx, ty - this.holdy, tz - this.holdz).m_82541_();
               this.holdx = tx + 4.0 * tpos.f_82479_;
               this.holdy = ty + 4.0 * tpos.f_82480_;
               this.holdz = tz + 4.0 * tpos.f_82481_;
            }

            this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 4.0);
         } else if (this.attackPhase == 3) {
            if (this.timer <= 0 || this.rosalyne.m_20280_(target) < 2.0 || this.rosalyne.m_20275_(this.holdx, this.holdy, this.holdz) < 1.0) {
               this.rosalyne.swing();
               this.swingsLeft--;
               this.holdx = this.rosalyne.m_20185_();
               this.holdy = target.m_20186_();
               this.holdz = this.rosalyne.m_20189_();
               this.attackPhase = 4;
               this.timer = this.swingsLeft > 0 && this.rosalyne.phase == 6 ? 10 : 20;
            }

            this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 3.0);
         } else if (this.attackPhase == 4) {
            if (this.timer <= 0 && this.swingsLeft > 0) {
               this.startCircling();
            } else {
               this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 4.0);
            }
         }
      }

      public void m_8041_() {
         this.rosalyne.cooldownNextAttack();
         this.rosalyne.setAnimation(0);
         this.rosalyne.rollNextAttack(1);
      }

      public boolean m_8036_() {
         return this.rosalyne.nextAttack == 1
            && (this.rosalyne.phase == 2 || this.rosalyne.phase == 4 || this.rosalyne.phase == 6)
            && this.rosalyne.m_5448_() != null
            && this.rosalyne.m_5448_().m_6084_()
            && this.rosalyne.attackCooldown <= 0;
      }

      public boolean m_8045_() {
         return this.m_8036_() && (this.swingsLeft > 0 || this.timer > 0);
      }
   }

   private static class PhaseTransition extends StationaryAttack {
      private RosalyneEntity rosalyne;
      private int timer;
      private static final int DURATION = 80;

      public PhaseTransition(RosalyneEntity rosalyne) {
         super(rosalyne);
         this.rosalyne = rosalyne;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.timer = 80;
      }

      @Override
      public void m_8037_() {
         super.m_8037_();
         this.timer--;
         if (this.timer == 70) {
            switch (this.rosalyne.phase) {
               case 1:
                  this.rosalyne.setAnimation(6);
               case 2:
               case 4:
               default:
                  break;
               case 3:
                  this.rosalyne.setAnimation(7);
                  break;
               case 5:
                  this.rosalyne.setAnimation(8);
            }
         }

         if (this.timer <= 50 && this.timer % 20 == 10) {
            switch (this.rosalyne.phase) {
               case 1:
                  this.rosalyne.m_216990_(SoundEvents.f_12442_);
                  break;
               case 5:
                  this.rosalyne.m_216990_((SoundEvent)ModSounds.rosalyneCrack.get());
            }
         }

         if (this.timer <= 0) {
            switch (this.rosalyne.phase) {
               case 1:
                  this.rosalyne.setPhase(2);
                  this.rosalyne
                     .f_19853_
                     .m_46511_(this.rosalyne, this.rosalyne.m_20185_(), this.rosalyne.m_20186_(), this.rosalyne.m_20189_(), 6.0F, BlockInteraction.NONE);
               case 2:
               case 4:
               default:
                  break;
               case 3:
                  this.rosalyne.setPhase(4);
                  this.rosalyne.createSpirits();
                  break;
               case 5:
                  this.rosalyne.setPhase(6);
                  this.rosalyne
                     .f_19853_
                     .m_46511_(this.rosalyne, this.rosalyne.m_20185_(), this.rosalyne.m_20186_(), this.rosalyne.m_20189_(), 4.0F, BlockInteraction.NONE);
            }

            this.rosalyne.setAnimation(0);
            this.rosalyne.attackCooldown = 100;
         }
      }

      public boolean m_8036_() {
         return this.rosalyne.phase == 1 || this.rosalyne.phase == 3 || this.rosalyne.phase == 5;
      }
   }

   private static class VerticalCrashAttack extends Goal {
      private RosalyneEntity rosalyne;
      private int timer;
      private int swingsLeft;
      private int attackPhase;
      private double holdx;
      private double holdy;
      private double holdz;
      private Vec3 offset;

      public VerticalCrashAttack(RosalyneEntity rosalyne) {
         this.rosalyne = rosalyne;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.swingsLeft = 1 + this.rosalyne.f_19796_.m_188503_(2);
         if (this.rosalyne.phase == 6) {
            this.swingsLeft = 3 + this.rosalyne.f_19796_.m_188503_(3);
         }

         this.startJump();
      }

      private void startJump() {
         this.timer = this.rosalyne.phase == 6 ? 25 : 40;
         this.attackPhase = 0;
         LivingEntity target = this.rosalyne.m_5448_();
         this.offset = new Vec3(this.rosalyne.m_20185_() - target.m_20185_(), 0.0, this.rosalyne.m_20189_() - target.m_20189_()).m_82541_();
         this.holdx = target.m_20185_() + this.offset.f_82479_ * 3.0;
         this.holdy = target.m_20186_() + 4.0;
         this.holdz = target.m_20189_() + this.offset.f_82481_ * 3.0;
         this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 1.0);
         this.rosalyne.setAnimation(9);
      }

      public void m_8037_() {
         this.timer--;
         LivingEntity target = this.rosalyne.m_5448_();
         if (this.attackPhase == 0) {
            this.holdy += 0.1;
            if (this.timer <= 0) {
               this.attackPhase = 1;
               this.timer = 20;
               double tx = target.m_20185_();
               double ty = target.m_20186_();
               double tz = target.m_20189_();
               Vec3 tpos = new Vec3(tx - this.holdx, ty - this.holdy, tz - this.holdz).m_82541_();
               this.holdx = tx + 1.0 * tpos.f_82479_;
               this.holdy = ty;
               this.holdz = tz + 1.0 * tpos.f_82481_;
            }

            this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 1.0);
         } else if (this.attackPhase == 1) {
            if (this.timer <= 0 || this.rosalyne.m_20280_(target) < 2.0 || this.rosalyne.m_20275_(this.holdx, this.holdy, this.holdz) < 1.0) {
               this.rosalyne.swing();
               this.swingsLeft--;
               this.holdx = this.rosalyne.m_20185_();
               this.holdy = target.m_20186_();
               this.holdz = this.rosalyne.m_20189_();
               this.attackPhase = 2;
               this.timer = this.swingsLeft > 0 && this.rosalyne.phase == 6 ? 10 : 20;
            }

            this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 3.0);
         } else if (this.attackPhase == 2) {
            if (this.timer <= 0 && this.swingsLeft > 0) {
               this.startJump();
            } else {
               this.rosalyne.f_21342_.m_6849_(this.holdx, this.holdy, this.holdz, 4.0);
            }
         }
      }

      public void m_8041_() {
         this.rosalyne.cooldownNextAttack();
         this.rosalyne.setAnimation(0);
         this.rosalyne.rollNextAttack(2);
      }

      public boolean m_8036_() {
         return this.rosalyne.nextAttack == 2
            && (this.rosalyne.phase == 2 || this.rosalyne.phase == 4 || this.rosalyne.phase == 6)
            && this.rosalyne.m_5448_() != null
            && this.rosalyne.m_5448_().m_6084_()
            && this.rosalyne.attackCooldown <= 0;
      }

      public boolean m_8045_() {
         return this.m_8036_() && (this.swingsLeft > 0 || this.timer > 0);
      }
   }
}
