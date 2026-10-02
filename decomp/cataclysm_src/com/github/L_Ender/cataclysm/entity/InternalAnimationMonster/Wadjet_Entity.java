package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.client.model.tools.DynamicChain;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Wadjet_Entity extends Internal_Animation_Monster {
   @OnlyIn(Dist.CLIENT)
   public DynamicChain dc;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState sleepAnimationState = new AnimationState();
   public AnimationState awakeAnimationState = new AnimationState();
   public AnimationState stabnswingAnimationState = new AnimationState();
   public AnimationState doublswingAnimationState = new AnimationState();
   public AnimationState spearchargeAnimationState = new AnimationState();
   public AnimationState magicAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public AnimationState blockAnimationState = new AnimationState();
   public static final EntityDataAccessor<Boolean> STAB = SynchedEntityData.m_135353_(Wadjet_Entity.class, EntityDataSerializers.f_135035_);
   private float prevAttackProgress;
   private float AttackProgress;
   private int charge_cooldown = 0;
   public static final int CHARGE_COOLDOWN = 160;
   private int magic_cooldown = 0;
   public static final int MAGIC_COOLDOWN = 160;

   public Wadjet_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 35;
      if (world.f_46443_) {
         this.dc = new DynamicChain(this);
      }

      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.WadjetHealthMultiplier, CMConfig.WadjetDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.25F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(2, new InternalMoveGoal(this, false, 1.0));
      this.f_21345_.m_25352_(1, new Wadjet_Entity.ChargeAttackGoal(this, 0, 3, 0, 45, 15, 20, 5.5F, 16.0F));
      this.f_21345_.m_25352_(1, new Wadjet_Entity.MagicAttackGoal(this, 0, 4, 0, 35, 15, 3.5F, 12.0F));
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 5, 0, 60, 60, 5.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Wadjet_Entity.this.getStab();
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Wadjet_Entity.this.setStab(Wadjet_Entity.this.f_19796_.m_188499_());
         }
      });
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 6, 0, 55, 55, 5.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && !Wadjet_Entity.this.getStab();
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Wadjet_Entity.this.setStab(Wadjet_Entity.this.f_19796_.m_188499_());
         }
      });
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 1, 1, 0, 0, 0) {
         @Override
         public void m_8037_() {
            this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         }
      });
      this.f_21345_.m_25352_(0, new InternalAttackGoal(this, 1, 2, 0, 70, 0, 18.0F));
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 8, 8, 0, 20, 0, false));
   }

   public static Builder wadjet() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 30.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 11.0)
         .m_22268_(Attributes.f_22276_, 150.0)
         .m_22268_(Attributes.f_22284_, 5.0)
         .m_22268_(Attributes.f_22278_, 0.7);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_6469_(DamageSource source, float damage) {
      Entity entity = source.m_7640_();
      if (entity instanceof Poison_Dart_Entity) {
         return false;
      } else if (this.isSleep() && !source.m_19378_()) {
         return false;
      } else if (this.canBlockDamageSource(source)) {
         if (entity instanceof AbstractArrow) {
            float f = 170.0F + this.f_19796_.m_188501_() * 80.0F;
            entity.m_20256_(entity.m_20184_().m_82490_(1.0));
            entity.m_146922_(entity.m_146908_() + f);
            entity.f_19864_ = true;
         }

         if (this.getAttackState() == 0) {
            this.m_5496_(SoundEvents.f_11668_, 1.0F, 2.0F);
            this.setAttackState(8);
         }

         return false;
      } else {
         return super.m_6469_(source, damage);
      }
   }

   private boolean canBlockDamageSource(DamageSource damageSourceIn) {
      boolean flag = false;
      if (!this.m_21525_() && damageSourceIn.m_19360_() && !flag && (this.getAttackState() == 0 || this.getAttackState() == 8)) {
         Vec3 vector3d2 = damageSourceIn.m_7270_();
         if (vector3d2 != null) {
            Vec3 vector3d = this.m_20252_(1.0F);
            Vec3 vector3d1 = vector3d2.m_82505_(this.m_20182_()).m_82541_();
            vector3d1 = new Vec3(vector3d1.f_82479_, 0.0, vector3d1.f_82481_);
            return vector3d1.m_82526_(vector3d) < 0.0;
         }
      }

      return false;
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "sleep") {
         return this.sleepAnimationState;
      } else if (input == "awake") {
         return this.awakeAnimationState;
      } else if (input == "charge") {
         return this.spearchargeAnimationState;
      } else if (input == "magic") {
         return this.magicAnimationState;
      } else if (input == "stabnswing") {
         return this.stabnswingAnimationState;
      } else if (input == "doubleswing") {
         return this.doublswingAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else {
         return input == "block" ? this.blockAnimationState : new AnimationState();
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(STAB, false);
   }

   public boolean isSleep() {
      return this.getAttackState() == 1 || this.getAttackState() == 2;
   }

   public void setSleep(boolean sleep) {
      this.setAttackState(sleep ? 1 : 0);
   }

   public void setStab(boolean stab) {
      this.f_19804_.m_135381_(STAB, stab);
   }

   public boolean getStab() {
      return (Boolean)this.f_19804_.m_135370_(STAB);
   }

   public boolean m_142066_() {
      return !this.isSleep() && super.m_142066_();
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_
   ) {
      this.setSleep(true);
      return super.m_6518_(p_29678_, p_29679_, p_29680_, p_29681_, p_29682_);
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.sleepAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.awakeAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.spearchargeAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.magicAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.stabnswingAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.doublswingAnimationState.m_216982_(this.f_19797_);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.deathAnimationState.m_216982_(this.f_19797_);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.blockAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.sleepAnimationState.m_216973_();
      this.awakeAnimationState.m_216973_();
      this.blockAnimationState.m_216973_();
      this.spearchargeAnimationState.m_216973_();
      this.magicAnimationState.m_216973_();
      this.stabnswingAnimationState.m_216973_();
      this.doublswingAnimationState.m_216973_();
      this.deathAnimationState.m_216973_();
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(7);
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("is_Sleep", this.isSleep());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setSleep(compound.m_128471_("is_Sleep"));
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, !this.isMoving() && this.getAttackState() == 0, this.f_19797_);
      }

      this.prevAttackProgress = this.AttackProgress;
      if (this.m_5912_() && this.AttackProgress < 10.0F) {
         this.AttackProgress++;
      }

      if (!this.m_5912_() && this.AttackProgress > 0.0F) {
         this.AttackProgress--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (this.magic_cooldown > 0) {
         this.magic_cooldown--;
      }
   }

   public boolean isMoving() {
      return this.f_20924_ > 1.0E-5F;
   }

   public void animateWhen(AnimationState state, boolean p_252220_, int p_249486_) {
      if (p_252220_) {
         state.m_216982_(p_249486_);
      } else {
         state.m_216973_();
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAttackState() == 3) {
         if (this.attackTicks == 18) {
            this.m_5496_((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.attackTicks == 20) {
            this.AreaAttack(9.0F, 6.0F, 45.0F, 1.0F, 90, false);
         }
      }

      if (this.getAttackState() == 4 && this.attackTicks == 15) {
         this.m_5496_(SoundEvents.f_11867_, 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
      }

      if (this.getAttackState() == 5) {
         if (this.attackTicks == 14) {
            this.m_5496_((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(8.0F, 6.0F, 45.0F, 1.0F, 90, false);
         }

         if (this.attackTicks == 37) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.0F, 4.0F, 220.0F, 1.0F, 70, true);
         }
      }

      if (this.getAttackState() == 6) {
         if (this.attackTicks == 14) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.0F, 4.0F, 220.0F, 1.0F, 60, true);
         }

         if (this.attackTicks == 28) {
            this.m_5496_((SoundEvent)ModSounds.SWING.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.0F, 4.0F, 220.0F, 1.0F, 60, true);
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks, boolean knockback) {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, (double)height, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.f_20883_ % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
               + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
         );
         if ((
               entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && !this.m_7307_(entityHit)
            && !(entityHit instanceof Wadjet_Entity)
            && entityHit != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean hurt = entityHit.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            double d0 = entityHit.m_20185_() - this.m_20185_();
            double d1 = entityHit.m_20189_() - this.m_20189_();
            double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
            if (hurt && knockback) {
               entityHit.m_5997_(d0 / d2 * 2.25, 0.15, d1 / d2 * 2.25);
            }
         }
      }
   }

   public float getAttackProgress(float partialTicks) {
      return this.prevAttackProgress + (this.AttackProgress - this.prevAttackProgress) * partialTicks;
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_ANCIENT_REMNANT) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.WADJET_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.WADJET_DEATH.get();
   }

   protected SoundEvent m_7515_() {
      return this.isSleep() ? super.m_7515_() : (SoundEvent)ModSounds.WADJET_AMBIENT.get();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public boolean m_7301_(MobEffectInstance p_34192_) {
      return p_34192_.m_19544_() != ModEffect.EFFECTSTUN.get() && p_34192_.m_19544_() != ModEffect.EFFECTABYSSAL_CURSE.get() && super.m_7301_(p_34192_);
   }

   public boolean m_6785_(double p_21542_) {
      return false;
   }

   protected boolean m_8028_() {
      return false;
   }

   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }

   static class ChargeAttackGoal extends Goal {
      protected final Wadjet_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final int attackshottick;
      private final float attackminrange;
      private final float attackrange;

      public ChargeAttackGoal(
         Wadjet_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackshottick,
         float attackminrange,
         float attackrange
      ) {
         this.entity = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackseetick = attackseetick;
         this.attackshottick = attackshottick;
         this.attackminrange = attackminrange;
         this.attackrange = attackrange;
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && this.entity.m_20270_(target) > this.attackminrange
            && target.m_6084_()
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.m_217043_().m_188501_() * 100.0F < 16.0F
            && this.entity.charge_cooldown <= 0;
      }

      public void m_8056_() {
         this.entity.setAttackState(this.attackstate);
      }

      public void m_8041_() {
         this.entity.setAttackState(this.attackendstate);
         this.entity.attackCooldown = 0;
         this.entity.charge_cooldown = 160;
      }

      public boolean m_8045_() {
         return this.entity.attackTicks < this.attackMaxtick;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_146922_(this.entity.f_20883_);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.attackTicks == this.attackseetick) {
            float f1 = (float)Math.cos(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            float f2 = (float)Math.sin(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            if (target != null) {
               float r = this.entity.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 4.0F);
               this.entity.m_5997_((double)f1 * 0.3 * (double)r, 0.0, (double)f2 * 0.3 * (double)r);
            } else {
               this.entity.m_5997_((double)f1 * 2.0, 0.0, (double)f2 * 2.0);
            }
         }

         if (this.entity.attackTicks == this.attackshottick && target != null) {
            double d1 = 5.0;
            Vec3 vec3 = this.entity.m_20252_(1.0F);
            double d2 = target.m_20185_() - (this.entity.m_20185_() + vec3.f_82479_ * d1);
            double d3 = target.m_20227_(0.5) - this.entity.m_20227_(0.15);
            double d4 = target.m_20189_() - (this.entity.m_20189_() + vec3.f_82481_ * d1);
            Sandstorm_Projectile largefireball = new Sandstorm_Projectile(this.entity, d2, d3, d4, this.entity.f_19853_, 6.0F);
            largefireball.setState(1);
            largefireball.m_6034_(this.entity.m_20185_() + vec3.f_82479_ * d1, this.entity.m_20227_(0.15), largefireball.m_20189_() + vec3.f_82481_ * d1);
            this.entity.f_19853_.m_7967_(largefireball);
         }
      }
   }

   static class MagicAttackGoal extends Goal {
      protected final Wadjet_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final float attackminrange;
      private final float attackrange;

      public MagicAttackGoal(
         Wadjet_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange
      ) {
         this.entity = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackseetick = attackseetick;
         this.attackminrange = attackminrange;
         this.attackrange = attackrange;
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && this.entity.m_20270_(target) > this.attackminrange
            && target.m_6084_()
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.m_217043_().m_188501_() * 100.0F < 24.0F
            && this.entity.magic_cooldown <= 0;
      }

      public void m_8056_() {
         this.entity.setAttackState(this.attackstate);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }
      }

      public void m_8041_() {
         this.entity.setAttackState(this.attackendstate);
         this.entity.attackCooldown = 0;
         this.entity.magic_cooldown = 160;
      }

      public boolean m_8045_() {
         return this.entity.attackTicks < this.attackMaxtick;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            if (this.entity.attackTicks == this.attackseetick) {
               double d1 = target.m_20186_();
               float f = (float)Mth.m_14136_(target.m_20189_() - this.entity.m_20189_(), target.m_20185_() - this.entity.m_20185_());

               for (int k = 0; k < 8; k++) {
                  float f2 = f + (float)k * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
                  this.spawnSpikeLine(
                     this.entity.m_20185_() + (double)Mth.m_14089_(f2) * 4.5, this.entity.m_20189_() + (double)Mth.m_14031_(f2) * 4.5, d1, f2, 3
                  );
               }

               for (int k = 0; k < 13; k++) {
                  float f3 = f + (float)k * (float) Math.PI * 2.0F / 13.0F + (float) (Math.PI / 5);
                  this.spawnSpikeLine(
                     this.entity.m_20185_() + (double)Mth.m_14089_(f3) * 6.5, this.entity.m_20189_() + (double)Mth.m_14031_(f3) * 6.5, d1, f3, 10
                  );
               }

               for (int k = 0; k < 16; k++) {
                  float f4 = f + (float)k * (float) Math.PI * 2.0F / 16.0F + (float) (Math.PI / 10);
                  this.spawnSpikeLine(
                     this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 8.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 8.5, d1, f4, 15
                  );
               }

               for (int k = 0; k < 19; k++) {
                  float f5 = f + (float)k * (float) Math.PI * 2.0F / 19.0F + (float) (Math.PI / 20);
                  this.spawnSpikeLine(
                     this.entity.m_20185_() + (double)Mth.m_14089_(f5) * 10.5, this.entity.m_20189_() + (double)Mth.m_14031_(f5) * 10.5, d1, f5, 20
                  );
               }

               for (int k = 0; k < 24; k++) {
                  float f6 = f + (float)k * (float) Math.PI * 2.0F / 24.0F + 0.07853982F;
                  this.spawnSpikeLine(
                     this.entity.m_20185_() + (double)Mth.m_14089_(f6) * 12.5, this.entity.m_20189_() + (double)Mth.m_14031_(f6) * 12.5, d1, f6, 30
                  );
               }
            }
         }
      }

      private void spawnSpikeLine(double posX, double posZ, double posY, float rotation, int delay) {
         BlockPos blockpos = new BlockPos(posX, posY, posZ);
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.m_7494_();
            BlockState blockstate = this.entity.f_19853_.m_8055_(blockpos1);
            if (blockstate.m_60783_(this.entity.f_19853_, blockpos1, Direction.DOWN)) {
               if (!this.entity.f_19853_.m_46859_(blockpos)) {
                  BlockState blockstate1 = this.entity.f_19853_.m_8055_(blockpos);
                  VoxelShape voxelshape = blockstate1.m_60812_(this.entity.f_19853_, blockpos);
                  if (!voxelshape.m_83281_()) {
                     d0 = voxelshape.m_83297_(Axis.Y);
                  }
               }
               break;
            }

            blockpos = blockpos.m_7494_();
         } while (blockpos.m_123342_() < Math.min(this.entity.f_19853_.m_151558_(), this.entity.m_146904_() + 12));

         this.entity
            .f_19853_
            .m_7967_(
               new Ancient_Desert_Stele_Entity(
                  this.entity.f_19853_,
                  posX,
                  (double)blockpos.m_123342_() + d0 - 3.0,
                  posZ,
                  rotation,
                  delay,
                  (float)CMConfig.AncientDesertSteledamage,
                  this.entity
               )
            );
      }
   }
}
