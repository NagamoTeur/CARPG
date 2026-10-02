package lykrast.meetyourfight.entity;

import java.util.List;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.ai.MoveFrontOfTarget;
import lykrast.meetyourfight.entity.ai.VexMoveRandomGoal;
import lykrast.meetyourfight.entity.movement.VexMovementController;
import lykrast.meetyourfight.registry.ModEntities;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BellringerEntity extends BossEntity {
   public int attackCooldown;
   private int rageAttacks = 0;

   public BellringerEntity(EntityType<? extends BellringerEntity> type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new VexMovementController(this);
      this.f_21364_ = 50;
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

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new BellringerEntity.RageAttack(this));
      this.f_21345_.m_25352_(2, new BellringerEntity.BurstAttack(this));
      this.f_21345_.m_25352_(7, new MoveFrontOfTarget(this, 1.0));
      this.f_21345_.m_25352_(8, new VexMoveRandomGoal(this, 0.25));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, false));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 200.0).m_22268_(Attributes.f_22277_, 64.0);
   }

   public static void spawn(Player player, Level world) {
      RandomSource rand = player.m_217043_();
      BellringerEntity bellringer = (BellringerEntity)((EntityType)ModEntities.BELLRINGER.get()).m_20615_(world);
      bellringer.m_7678_(
         player.m_20185_() + (double)rand.m_188503_(15) - 7.0,
         player.m_20186_() + (double)rand.m_188503_(9) - 1.0,
         player.m_20189_() + (double)rand.m_188503_(15) - 7.0,
         rand.m_188501_() * 360.0F - 180.0F,
         0.0F
      );
      bellringer.attackCooldown = 100;
      if (!player.m_150110_().f_35937_) {
         bellringer.m_6710_(player);
      }

      bellringer.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 100, 2));
      bellringer.m_6518_((ServerLevel)world, world.m_6436_(bellringer.m_20183_()), MobSpawnType.EVENT, null, null);
      world.m_7967_(bellringer);
      world.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11699_, SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   @Override
   public void m_8024_() {
      if (this.attackCooldown > 0) {
         this.attackCooldown--;
      }

      super.m_8024_();
   }

   private void dingDong() {
      this.m_6674_(InteractionHand.MAIN_HAND);
      this.m_5496_(SoundEvents.f_11699_, 2.0F, 1.0F);
   }

   private ProjectileLineEntity readyAttack() {
      ProjectileLineEntity ghost = new ProjectileLineEntity(this.f_19853_, this, 0.0, 0.0, 0.0);
      ghost.m_5602_(this);
      ghost.m_6034_(
         this.m_20185_() - 2.0 + this.f_19796_.m_188500_() * 4.0,
         this.m_20186_() - 2.0 + this.f_19796_.m_188500_() * 4.0,
         this.m_20189_() - 2.0 + this.f_19796_.m_188500_() * 4.0
      );
      ghost.setVariant(0);
      return ghost;
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("AttackCooldown")) {
         this.attackCooldown = compound.m_128451_("AttackCooldown");
      }

      this.rageAttacks = compound.m_128451_("Rage");
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("AttackCooldown", this.attackCooldown);
      compound.m_128405_("Rage", this.rageAttacks);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.bellringerIdle.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.bellringerHurt.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.bellringerDeath.get();
   }

   @Override
   protected SoundEvent getMusic() {
      return (SoundEvent)ModSounds.musicMagnum.get();
   }

   protected ResourceLocation m_7582_() {
      return MeetYourFight.rl("bellringer");
   }

   private static class BurstAttack extends Goal {
      private BellringerEntity ringer;
      private LivingEntity target;
      private int attackRemaining;
      private int attackDelay;
      private int chosenAttack;

      public BurstAttack(BellringerEntity ringer) {
         this.ringer = ringer;
      }

      public boolean m_8036_() {
         return this.ringer.attackCooldown <= 0 && this.ringer.m_5448_() != null && this.ringer.m_5448_().m_6084_();
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.ringer.attackCooldown = 2;
         this.attackDelay = 20;
         this.attackRemaining = 3 + this.ringer.rageAttacks;
         this.target = this.ringer.m_5448_();
         this.chosenAttack = this.ringer.f_19796_.m_188503_(2);
      }

      public void m_8037_() {
         this.ringer.attackCooldown = 2;
         this.attackDelay--;
         if (this.attackDelay <= 0) {
            this.attackDelay = 20;
            this.attackRemaining--;
            this.ringer.dingDong();
            this.performAttack();
            if (this.attackRemaining <= 0) {
               this.m_8041_();
            }
         }
      }

      private void performAttack() {
         BlockPos tgt = this.target.m_20183_();
         double tx = (double)tgt.m_123341_() + 0.5;
         double tz = (double)tgt.m_123343_() + 0.5;
         double ty = (double)tgt.m_123342_() + 0.1;
         if (!this.target.m_20096_() && !this.target.m_20069_() && !this.ringer.f_19853_.m_8055_(tgt.m_7495_()).m_60767_().m_76334_()) {
            ty--;
         }

         switch (this.chosenAttack) {
            case 0:
            default:
               BlockPos self = this.ringer.m_20183_();
               double sx = (double)self.m_123341_();
               double sz = (double)self.m_123343_();
               Direction dir = Direction.m_122366_(tx - sx, 0.0, tz - sz);
               double cx = (double)dir.m_122429_();
               double cz = (double)dir.m_122431_();

               for (int i = -4; i <= 4; i++) {
                  ProjectileLineEntity ghost = this.ringer.readyAttack();
                  ghost.setUp(20, cx, 0.0, cz, tx - 7.0 * cx + (double)i * cz, ty, tz - 7.0 * cz + (double)i * cx);
                  this.ringer.f_19853_.m_7967_(ghost);
               }
               break;
            case 1:
               for (int x = -1; x <= 1; x++) {
                  for (int z = -1; z <= 1; z++) {
                     ProjectileLineEntity ghost = this.ringer.readyAttack();
                     ghost.setUp(20, 0.0, -1.0, 0.0, tx + (double)x, ty + 7.0, tz + (double)z);
                     this.ringer.f_19853_.m_7967_(ghost);
                  }
               }
         }
      }

      public void m_8041_() {
         this.ringer.attackCooldown = 40 + this.ringer.f_19796_.m_188503_(21);
      }

      public boolean m_8045_() {
         return this.attackRemaining > 0 && this.target.m_6084_();
      }
   }

   private static class RageAttack extends Goal {
      private BellringerEntity ringer;
      private LivingEntity target;
      private int attackRemaining;
      private int attackDelay;
      private Direction dir;

      public RageAttack(BellringerEntity ringer) {
         this.ringer = ringer;
      }

      public boolean m_8036_() {
         return this.ringer.attackCooldown <= 0
            && this.ringer.rageAttacks == 0
            && this.ringer.m_21223_() <= this.ringer.m_21233_() / 2.0F
            && this.ringer.m_5448_() != null
            && this.ringer.m_5448_().m_6084_();
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8056_() {
         this.ringer.attackCooldown = 2;
         this.ringer.rageAttacks = 1;
         this.attackDelay = 30;
         this.attackRemaining = 20;
         this.target = this.ringer.m_5448_();
         BlockPos self = this.ringer.m_20183_();
         double sx = (double)self.m_123341_();
         double sz = (double)self.m_123343_();
         BlockPos tgt = this.target.m_20183_();
         double tx = (double)tgt.m_123341_();
         double tz = (double)tgt.m_123343_();
         this.dir = Direction.m_122366_(tx - sx, 0.0, tz - sz);
         List<Entity> list = this.ringer
            .f_19853_
            .m_6249_(this.ringer, this.ringer.m_20191_().m_82400_(16.0), ex -> ex instanceof LivingEntity && ex.m_6084_() && ex.m_6072_());
         list.add(this.target);

         for (Entity e : list) {
            ((LivingEntity)e).m_7292_(new MobEffectInstance(MobEffects.f_19597_, 300, 1));
         }

         this.ringer.dingDong();
         this.ringer.m_5496_(SoundEvents.f_11700_, 2.0F, 1.0F);
      }

      public void m_8037_() {
         this.ringer.attackCooldown = 2;
         this.attackDelay--;
         if (this.attackDelay <= 0) {
            this.attackDelay = 12;
            this.attackRemaining--;
            this.ringer.dingDong();
            BlockPos tgt = this.target.m_20183_();
            double tx = (double)tgt.m_123341_() + 0.5;
            double tz = (double)tgt.m_123343_() + 0.5;
            double ty = (double)tgt.m_123342_() + 0.1;
            if (!this.target.m_20096_() && !this.target.m_20069_() && !this.ringer.f_19853_.m_8055_(tgt.m_7495_()).m_60767_().m_76334_()) {
               ty--;
            }

            double cx = (double)this.dir.m_122429_();
            double cz = (double)this.dir.m_122431_();
            int off = this.attackRemaining % 2 == 0 ? 1 : -1;

            for (int i = -5; i <= 5; i++) {
               ProjectileLineEntity ghost = this.ringer.readyAttack();
               ghost.setUp(15 + off * i, cx, 0.0, cz, tx - 7.0 * cx + (double)i * cz, ty, tz - 7.0 * cz + (double)i * cx);
               this.ringer.f_19853_.m_7967_(ghost);
            }

            if (this.attackRemaining <= 0) {
               this.m_8041_();
            }
         }
      }

      public void m_8041_() {
         this.ringer.attackCooldown = 40 + this.ringer.f_19796_.m_188503_(21);
      }

      public boolean m_8045_() {
         return this.attackRemaining > 0 && this.target.m_6084_();
      }
   }
}
