package lykrast.meetyourfight.entity;

import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.ai.MoveAroundTargetOrthogonal;
import lykrast.meetyourfight.entity.ai.StationaryAttack;
import lykrast.meetyourfight.entity.ai.VexMoveRandomGoal;
import lykrast.meetyourfight.entity.movement.VexMovementController;
import lykrast.meetyourfight.registry.ModEntities;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VelaEntity extends BossEntity {
   private static final EntityDataAccessor<Byte> STATUS = SynchedEntityData.m_135353_(VelaEntity.class, EntityDataSerializers.f_135027_);
   public static final int NO_ATTACK = 0;
   public static final int WATER_ATTACK = 1;
   public static final int AIR_ATTACK = 2;
   private static final int ATTACK_MASK = 3;
   private static final int RAGE_MASK = -4;
   public int waterCooldown;
   public int airCooldown;
   private int rage;

   public VelaEntity(EntityType<? extends VelaEntity> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new VexMovementController(this);
      this.f_21364_ = 150;
      this.rage = 0;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new VelaEntity.WaterAttack(this));
      this.f_21345_.m_25352_(3, new VelaEntity.AirAttack(this));
      this.f_21345_.m_25352_(7, new MoveAroundTargetOrthogonal(this, 1.0));
      this.f_21345_.m_25352_(8, new VexMoveRandomGoal(this, 0.25));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, false));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 500.0).m_22268_(Attributes.f_22284_, 5.0).m_22268_(Attributes.f_22277_, 64.0);
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
   }

   public static void spawn(Player player, Level world) {
      RandomSource rand = player.m_217043_();
      VelaEntity dame = (VelaEntity)((EntityType)ModEntities.VELA.get()).m_20615_(world);
      dame.m_7678_(
         player.m_20185_() + (double)rand.m_188503_(5) - 2.0,
         player.m_20186_() + (double)rand.m_188503_(3) + 3.0,
         player.m_20189_() + (double)rand.m_188503_(5) - 2.0,
         rand.m_188501_() * 360.0F - 180.0F,
         0.0F
      );
      dame.waterCooldown = 100;
      dame.airCooldown = 150;
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
      if (this.waterCooldown > 0) {
         this.waterCooldown--;
      }

      if (this.airCooldown > 0) {
         this.airCooldown--;
      }

      int newrage = this.getRageTarget();
      if (newrage > this.rage) {
         this.rage = newrage;
         this.setRage(this.rage);
      }

      super.m_8024_();
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("WCooldown")) {
         this.waterCooldown = compound.m_128451_("WCooldown");
      }

      if (compound.m_128441_("ACooldown")) {
         this.airCooldown = compound.m_128451_("ACooldown");
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("WCooldown", this.waterCooldown);
      compound.m_128405_("ACooldown", this.airCooldown);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12554_;
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return SoundEvents.f_12557_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12556_;
   }

   @Override
   protected SoundEvent getMusic() {
      return (SoundEvent)ModSounds.musicMagnum.get();
   }

   protected ResourceLocation m_7582_() {
      return MeetYourFight.rl("vela");
   }

   private WaterBoulderEntity readyBoulder(Entity target) {
      WaterBoulderEntity boulder = new WaterBoulderEntity(this.f_19853_, this, target);
      boulder.m_5602_(this);
      boulder.m_6034_(this.m_20185_(), this.m_20186_() + 1.0, this.m_20189_());
      return boulder;
   }

   private VelaVortexEntity readyVortex(double x, double y, double z) {
      VelaVortexEntity vortex = new VelaVortexEntity(this.f_19853_, this);
      vortex.m_5602_(this);
      vortex.m_6034_(x, y, z);
      return vortex;
   }

   private static class AirAttack extends StationaryAttack {
      private VelaEntity vela;
      private LivingEntity target;
      private int attackDelay;
      private int interval;
      private int remaining;
      private int pattern;

      public AirAttack(VelaEntity vela) {
         super(vela);
         this.vela = vela;
      }

      public boolean m_8036_() {
         return this.vela.airCooldown <= 0 && this.vela.m_5448_() != null && this.vela.m_5448_().m_6084_() && !this.vela.m_21566_().m_24995_();
      }

      @Override
      public boolean m_183429_() {
         return true;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.vela.airCooldown = 2;
         this.attackDelay = 20;
         this.target = this.vela.m_5448_();
         switch (this.vela.rage) {
            case 0:
            default:
               this.remaining = 2;
               this.pattern = 0;
               this.interval = 30;
               break;
            case 1:
               switch (this.vela.f_19796_.m_188503_(3)) {
                  case 0:
                     this.remaining = 3;
                     this.pattern = 0;
                     this.interval = 20;
                     break;
                  case 1:
                     this.remaining = 9;
                     this.pattern = 123123123;
                     this.interval = 7;
                     break;
                  case 2:
                     this.remaining = 6;
                     this.pattern = 242422;
                     this.interval = 10;
               }
         }

         this.vela.setAttack(2);
      }

      @Override
      public void m_8037_() {
         super.m_8037_();
         this.vela.airCooldown = 2;
         if (this.vela.waterCooldown <= 2) {
            this.vela.waterCooldown = 2;
         }

         this.attackDelay--;
         if (this.attackDelay <= 0) {
            this.performAttack(this.pattern % 10);
            this.remaining--;
            if (this.remaining <= 0) {
               this.m_8041_();
            } else {
               this.attackDelay = this.interval;
               this.pattern /= 10;
            }
         }
      }

      private void performAttack(int attack) {
         BlockPos tgt = this.target.m_20183_();
         double tx = (double)tgt.m_123341_() + 0.5;
         double tz = (double)tgt.m_123343_() + 0.5;
         double ty = (double)tgt.m_123342_() + 0.1;
         Vec3 perp = new Vec3(this.vela.m_20185_() - tx, 0.0, this.vela.m_20189_() - tz).m_82541_().m_82524_((float) (Math.PI / 2)).m_82490_(8.0);
         if (!this.target.m_20096_() && !this.target.m_20069_() && !this.vela.f_19853_.m_8055_(tgt.m_7495_()).m_60767_().m_76334_()) {
            ty--;
         }

         for (int i = -1; i <= 1; i++) {
            if ((i != -1 || attack != 2 && attack != 3) && (i != 0 || attack != 1 && attack != 3 && attack != 4) && (i != 1 || attack != 1 && attack != 2)) {
               VelaVortexEntity vortex = this.vela.readyVortex(this.vela.m_20185_(), this.vela.m_20186_() + 2.0, this.vela.m_20189_());
               vortex.setUpTowards(tx + (double)i * perp.f_82479_, ty + 0.5 + (double)i * 0.1, tz + (double)i * perp.f_82481_, 1.5);
               this.vela.f_19853_.m_7967_(vortex);
            }
         }
      }

      public void m_8041_() {
         this.vela.airCooldown = 60 + this.vela.f_19796_.m_188503_(21);
         this.vela.setAttack(0);
      }

      public boolean m_8045_() {
         return this.attackDelay > 0 && this.target.m_6084_();
      }
   }

   private static class WaterAttack extends StationaryAttack {
      private VelaEntity vela;
      private LivingEntity target;
      private int attackDelay;
      private int chosenAttack;

      public WaterAttack(VelaEntity vela) {
         super(vela);
         this.vela = vela;
      }

      public boolean m_8036_() {
         return this.vela.waterCooldown <= 0 && this.vela.m_5448_() != null && this.vela.m_5448_().m_6084_() && !this.vela.m_21566_().m_24995_();
      }

      @Override
      public boolean m_183429_() {
         return true;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.vela.waterCooldown = 2;
         if (this.vela.airCooldown <= 2) {
            this.vela.airCooldown = 2;
         }

         this.attackDelay = 20;
         this.target = this.vela.m_5448_();
         this.chosenAttack = this.vela.f_19796_.m_188503_(2);
         this.vela.setAttack(1);
      }

      @Override
      public void m_8037_() {
         super.m_8037_();
         this.vela.waterCooldown = 2;
         this.attackDelay--;
         if (this.attackDelay <= 0) {
            this.performAttack();
            this.m_8041_();
         }
      }

      private void performAttack() {
         switch (this.chosenAttack) {
            case 0:
               WaterBoulderEntity boulder = this.vela.readyBoulder(this.target);
               boulder.setUp(60, 0.0, -0.5, 0.0, 0.0, 5.0, 0.0);
               this.vela.f_19853_.m_7967_(boulder);
               break;
            case 1:
               Direction dir = Direction.m_122366_(this.target.m_20185_() - this.vela.m_20185_(), 0.0, this.target.m_20189_() - this.vela.m_20189_());
               switch (this.vela.f_19796_.m_188503_(3)) {
                  case 1:
                     dir = dir.m_122427_();
                     break;
                  case 2:
                     dir = dir.m_122428_();
               }

               this.horizontalBoulder(dir, 0);
         }
      }

      private void horizontalBoulder(Direction dir, int offset) {
         Direction perp = dir.m_122427_();
         WaterBoulderEntity boulder = this.vela.readyBoulder(this.target);
         boulder.setUp(
            60,
            (double)dir.m_122429_() * 0.5,
            0.0,
            (double)dir.m_122431_() * 0.5,
            (double)(perp.m_122429_() * offset * 3 - dir.m_122429_() * 5),
            0.5,
            (double)(perp.m_122431_() * offset * 3 - dir.m_122431_() * 5)
         );
         this.vela.f_19853_.m_7967_(boulder);
      }

      public void m_8041_() {
         this.vela.waterCooldown = 100 + this.vela.f_19796_.m_188503_(41);
         this.vela.setAttack(0);
      }

      public boolean m_8045_() {
         return this.attackDelay > 0 && this.target.m_6084_();
      }
   }
}
