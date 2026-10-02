package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.EarthQuake_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import com.github.L_Ender.lionfishapi.server.animation.LegSolver;
import com.github.L_Ender.lionfishapi.server.animation.LegSolver.Leg;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

public class Ancient_Ancient_Remnant_Entity extends LLibrary_Boss_Monster {
   public static final Animation REMNANT_BITE1 = Animation.create(61);
   public static final Animation REMNANT_BITE2 = Animation.create(67);
   public static final Animation REMNANT_CHARGE_PREPARE = Animation.create(125);
   public static final Animation REMNANT_TAIL_ATTACK1 = Animation.create(57);
   public static final Animation REMNANT_TAIL_ATTACK2 = Animation.create(55);
   public static final Animation REMNANT_LEFT_STOMP = Animation.create(47);
   public static final Animation REMNANT_RIGHT_STOMP = Animation.create(47);
   public static final Animation REMNANT_LEFT_STOMP_EXTRA = Animation.create(38);
   public static final Animation REMNANT_RIGHT_STOMP_EXTRA = Animation.create(38);
   public static final Animation REMNANT_ROAR = Animation.create(70);
   public static final Animation REMNANT_ROAR2 = Animation.create(100);
   public static final Animation REMNANT_PHASE_ROAR = Animation.create(100);
   public static final Animation REMNANT_TAIL_THREE = Animation.create(104);
   public static final Animation REMNANT_DEATH = Animation.create(158);
   private static final EntityDataAccessor<Boolean> CHARGE = SynchedEntityData.m_135353_(Ancient_Ancient_Remnant_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> IS_ACT = SynchedEntityData.m_135353_(Ancient_Ancient_Remnant_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> POWER = SynchedEntityData.m_135353_(Ancient_Ancient_Remnant_Entity.class, EntityDataSerializers.f_135035_);
   public final LegSolver legSolver = new LegSolver(new Leg[]{new Leg(0.0F, 0.75F, 4.0F, false), new Leg(0.0F, -0.75F, 4.0F, false)});
   private Ancient_Ancient_Remnant_Entity.AttackMode mode = Ancient_Ancient_Remnant_Entity.AttackMode.CIRCLE;
   public float chargeProgress;
   public float prevchargeProgress;
   public float activeProgress;
   public float prevactiveProgress;
   private int hunting_cooldown = 160;
   private int charge_cooldown = 0;
   private int roar_cooldown = 0;
   private int roar2_cooldown = 0;
   private int earthquake_cooldown = 0;
   private int stomp_cooldown = 0;
   public static final int CHARGE_COOLDOWN = 250;
   public static final int ROAR_COOLDOWN = 500;
   public static final int ROAR2_COOLDOWN = 200;
   public static final int EARTHQUAKE_COOLDOWN = 160;
   public static final int STOMP_COOLDOWN = 200;
   private final CMBossInfoServer bossEvent = new CMBossInfoServer(this.m_5446_(), BossBarColor.WHITE, false, 7);
   public static final int NATURE_HEAL_COOLDOWN = 200;
   private int timeWithoutTarget;
   public int frame;
   public static final int MINE_COOLDOWN = 100;

   public Ancient_Ancient_Remnant_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 500;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.AncientRemnantHealthMultiplier, CMConfig.AncientRemnantDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.5F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         NO_ANIMATION,
         REMNANT_BITE1,
         REMNANT_CHARGE_PREPARE,
         REMNANT_BITE2,
         REMNANT_TAIL_ATTACK1,
         REMNANT_TAIL_ATTACK2,
         REMNANT_LEFT_STOMP,
         REMNANT_RIGHT_STOMP,
         REMNANT_ROAR,
         REMNANT_TAIL_THREE,
         REMNANT_ROAR2,
         REMNANT_LEFT_STOMP_EXTRA,
         REMNANT_RIGHT_STOMP_EXTRA,
         REMNANT_PHASE_ROAR,
         REMNANT_DEATH
      };
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(3, new Ancient_Ancient_Remnant_Entity.RemnantAttackGoal(this));
      this.f_21345_.m_25352_(0, new Ancient_Ancient_Remnant_Entity.AwakenGoal());
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantChargeAttackGoal(this, REMNANT_CHARGE_PREPARE));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_BITE1, 29));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_BITE2, 25));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_LEFT_STOMP, 24));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_RIGHT_STOMP, 24));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_LEFT_STOMP_EXTRA, 19));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_RIGHT_STOMP_EXTRA, 19));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_TAIL_ATTACK1, 13));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_TAIL_ATTACK2, 11));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_ROAR, 11));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_TAIL_THREE, 20));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantSteleAttackGoal(this, REMNANT_ROAR2, 29));
      this.f_21345_.m_25352_(1, new Ancient_Ancient_Remnant_Entity.RemnantAnimationAttackGoal(this, REMNANT_PHASE_ROAR, 29));
      this.f_21345_.m_25352_(1, new SimpleAnimationGoal<>(this, REMNANT_DEATH));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_
         .m_25352_(
            3, new NearestAttackableTargetGoal(this, LivingEntity.class, 120, true, true, ModEntities.buildPredicateFromTag(ModTag.ANCIENT_REMNANT_TARGET))
         );
   }

   public static Builder ancient_remnant() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 70.0)
         .m_22268_(Attributes.f_22279_, 0.33F)
         .m_22268_(Attributes.f_22281_, 25.0)
         .m_22268_(Attributes.f_22276_, 400.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public boolean m_142066_() {
      return this.getIsAct() && super.m_142066_();
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      double range = this.calculateRange(source);
      if (this.getAnimation() == REMNANT_PHASE_ROAR && !source.m_19378_()) {
         return false;
      } else if (range > CMConfig.AncientRemnantLongRangelimit * CMConfig.AncientRemnantLongRangelimit && !source.m_19378_()) {
         return false;
      } else {
         Entity entity = source.m_7640_();
         if (entity instanceof AbstractArrow) {
            return false;
         } else {
            return this.activeProgress > 0.0F && !source.m_19378_() ? false : super.m_6469_(source, damage);
         }
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.AncientRemnantDamageCap;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(CHARGE, false);
      this.f_19804_.m_135372_(IS_ACT, true);
      this.f_19804_.m_135372_(POWER, false);
   }

   public void setIsAct(boolean isAct) {
      this.f_19804_.m_135381_(IS_ACT, isAct);
   }

   public boolean getIsAct() {
      return (Boolean)this.f_19804_.m_135370_(IS_ACT);
   }

   public void m_6593_(@Nullable Component name) {
      super.m_6593_(name);
      this.bossEvent.m_6456_(this.m_5446_());
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("Is_Act", this.getIsAct());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setIsAct(compound.m_128471_("Is_Act"));
      if (this.m_8077_()) {
         this.bossEvent.m_6456_(this.m_5446_());
      }
   }

   public void setIsCharge(boolean isAnger) {
      this.f_19804_.m_135381_(CHARGE, isAnger);
   }

   public boolean getIsCharge() {
      return (Boolean)this.f_19804_.m_135370_(CHARGE);
   }

   public void setIsPower(boolean isPower) {
      this.f_19804_.m_135381_(POWER, isPower);
   }

   public boolean getIsPower() {
      return (Boolean)this.f_19804_.m_135370_(POWER);
   }

   public boolean m_203441_(FluidState p_204067_) {
      return p_204067_.m_205070_(FluidTags.f_13131_);
   }

   public boolean m_6063_() {
      return false;
   }

   private void floatRemnant() {
      if (this.m_20069_()) {
         CollisionContext collisioncontext = CollisionContext.m_82750_(this);
         if (collisioncontext.m_6513_(LiquidBlock.f_54690_, this.m_20183_(), true)
            && !this.f_19853_.m_6425_(this.m_20183_().m_7494_()).m_205070_(FluidTags.f_13131_)) {
            this.m_6853_(true);
         } else {
            this.m_20256_(this.m_20184_().m_82490_(0.5).m_82520_(0.0, 0.05, 0.0));
         }
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.prevchargeProgress = this.chargeProgress;
      this.prevactiveProgress = this.activeProgress;
      this.bossEvent.m_142711_(this.m_21223_() / this.m_21233_());
      if (this.getIsCharge() && this.chargeProgress < 3.0F) {
         this.chargeProgress++;
      }

      if (!this.getIsCharge() && this.chargeProgress > 0.0F) {
         this.chargeProgress--;
      }

      if (!this.getIsAct() && this.activeProgress < 20.0F) {
         this.activeProgress++;
      }

      if (this.getIsAct() && this.activeProgress > 0.0F) {
         this.activeProgress--;
      }

      this.legSolver.update(this, this.f_20883_, this.m_6134_());
      if (this.hunting_cooldown > 0) {
         this.hunting_cooldown--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (this.roar_cooldown > 0) {
         this.roar_cooldown--;
      }

      if (this.roar2_cooldown > 0) {
         this.roar2_cooldown--;
      }

      if (this.earthquake_cooldown > 0) {
         this.earthquake_cooldown--;
      }

      if (this.stomp_cooldown > 0) {
         this.stomp_cooldown--;
      }

      LivingEntity target = this.m_5448_();
      if (!this.f_19853_.f_46443_) {
         if (this.timeWithoutTarget > 0) {
            this.timeWithoutTarget--;
         }

         if (target != null) {
            this.timeWithoutTarget = 200;
         }

         if (this.getAnimation() == NO_ANIMATION
            && this.timeWithoutTarget <= 0
            && !this.m_21525_()
            && CMConfig.AncientRemnantNatureHealing > 0.0
            && this.f_19797_ % 20 == 0) {
            this.m_5634_((float)CMConfig.AncientRemnantNatureHealing);
         }
      }

      if (this.getIsPower() && this.f_19797_ % 20 == 0) {
         this.m_5634_(2.0F);
      }

      this.floatRemnant();
      this.Charge();
      this.frame++;
      float moveX = (float)(this.m_20185_() - this.f_19854_);
      float moveZ = (float)(this.m_20189_() - this.f_19856_);
      float speed = Mth.m_14116_(moveX * moveX + moveZ * moveZ);
      if (!this.m_20067_() && this.frame % 8 == 1 && (double)speed > 0.05 && this.getIsCharge() && this.m_20096_()) {
         this.m_5496_((SoundEvent)ModSounds.REMNANT_CHARGE_STEP.get(), 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
      }

      if (this.m_6084_() && this.getIsAct() && !this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.isPower() && !this.getIsPower()) {
         this.setAnimation(REMNANT_PHASE_ROAR);
      }
   }

   public boolean isPower() {
      return this.m_21223_() <= this.m_21233_() / 2.0F;
   }

   private void Charge() {
      if (this.getIsCharge()) {
         if (!this.f_19853_.f_46443_) {
            if (CMConfig.AncientRemnantBlockBreaking) {
               this.ChargeBlockBreaking();
            } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
               this.ChargeBlockBreaking();
            }
         }

         if (this.f_19797_ % 4 == 0) {
            for (LivingEntity Lentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(1.5))) {
               if (!this.m_7307_(Lentity) && !(Lentity instanceof Ancient_Ancient_Remnant_Entity) && Lentity != this) {
                  boolean flag = Lentity.m_6469_(
                     DamageSource.m_19370_(this),
                     (float)(
                        (double)((float)this.m_21133_(Attributes.f_22281_) * 1.5F)
                           + Math.min(this.m_21133_(Attributes.f_22281_) * 1.5, (double)Lentity.m_21233_() * CMConfig.RemnantChargeHpDamage)
                     )
                  );
                  if (flag && Lentity.m_20096_()) {
                     double d0 = Lentity.m_20185_() - this.m_20185_();
                     double d1 = Lentity.m_20189_() - this.m_20189_();
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                     float f = 1.5F;
                     Lentity.m_5997_(d0 / d2 * (double)f, 0.5, d1 / d2 * (double)f);
                  }
               }
            }
         }
      }
   }

   private void ChargeBlockBreaking() {
      boolean flag = false;
      AABB aabb = this.m_20191_().m_82377_(0.5, 0.2, 0.5);

      for (BlockPos blockpos : BlockPos.m_121976_(
         Mth.m_14107_(aabb.f_82288_),
         Mth.m_14107_(this.m_20186_()),
         Mth.m_14107_(aabb.f_82290_),
         Mth.m_14107_(aabb.f_82291_),
         Mth.m_14107_(aabb.f_82292_),
         Mth.m_14107_(aabb.f_82293_)
      )) {
         BlockState blockstate = this.f_19853_.m_8055_(blockpos);
         if (!blockstate.m_60795_()
            && blockstate.canEntityDestroy(this.f_19853_, blockpos, this)
            && !blockstate.m_204336_(ModTag.REMNANT_IMMUNE)
            && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
            if (this.f_19796_.m_188503_(6) == 0 && !blockstate.m_155947_()) {
               Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                  this.f_19853_, (double)blockpos.m_123341_() + 0.5, (double)blockpos.m_123342_() + 0.5, (double)blockpos.m_123343_() + 0.5, blockstate, 20
               );
               flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
               fallingBlockEntity.m_20256_(
                  fallingBlockEntity.m_20184_()
                     .m_82549_(
                        this.m_20182_()
                           .m_82546_(fallingBlockEntity.m_20182_())
                           .m_82542_(
                              (-1.2 + this.f_19796_.m_188500_()) / 3.0, 0.2 + this.m_217043_().m_188583_() * 0.15, (-1.2 + this.f_19796_.m_188500_()) / 3.0
                           )
                     )
               );
               this.f_19853_.m_7967_(fallingBlockEntity);
            } else {
               flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
            }
         }
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAnimation() == REMNANT_BITE1) {
         if (this.getAnimationTick() == 5) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_BITE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         if (this.getAnimationTick() == 28) {
            this.AreaAttack(8.0F, 8.0F, 70.0F, 1.35F, (float)CMConfig.RemnantHpDamage, 160, 0);
         }
      }

      if (this.getAnimation() == REMNANT_BITE2) {
         if (this.getAnimationTick() == 1) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_BITE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         if (this.getAnimationTick() == 24) {
            this.AreaAttack(8.0F, 8.0F, 70.0F, 1.35F, (float)CMConfig.RemnantHpDamage, 160, 0);
         }
      }

      if (this.getAnimation() == REMNANT_TAIL_ATTACK1) {
         if (this.getAnimationTick() == 3) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_TAIL_SWING.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
         }

         if (this.getAnimationTick() == 16) {
            this.TailAreaAttack(8.0F, 8.0F, 1.05F, 120.0F, 1.0F, (float)CMConfig.RemnantHpDamage, 200, 100);
         }
      }

      if (this.getAnimation() == REMNANT_TAIL_ATTACK2) {
         if (this.getAnimationTick() == 1) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_TAIL_SWING.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
         }

         if (this.getAnimationTick() == 14) {
            this.TailAreaAttack(8.0F, 8.0F, 1.05F, 120.0F, 1.0F, (float)CMConfig.RemnantHpDamage, 200, 100);
         }
      }

      if (this.getAnimation() == REMNANT_CHARGE_PREPARE) {
         if (this.getAnimationTick() == 1) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_CHARGE_PREPARE.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
         }

         if (this.getAnimationTick() == 15) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.StompParticle(-0.1F, -0.75F);
         }

         if (this.getAnimationTick() == 41) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.StompParticle(-0.1F, 0.75F);
         }

         if (this.getAnimationTick() == 62) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_CHARGE_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
         }

         if (this.getAnimationTick() == 132) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_BREATHING.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }
      }

      if (this.getAnimation() == REMNANT_ROAR) {
         if (this.getAnimationTick() == 14) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 60);
         }

         if (this.getAnimationTick() == 55) {
            for (int i = 0; i < 4; i++) {
               float angle = (float)i * (float) Math.PI / 2.0F;
               double sx = this.m_20185_() + (double)(Mth.m_14089_(angle) * 8.0F);
               double sy = this.m_20186_();
               double sz = this.m_20189_() + (double)(Mth.m_14031_(angle) * 8.0F);
               Sandstorm_Entity projectile = new Sandstorm_Entity(this.f_19853_, sx, sy, sz, 300, angle, this.m_20148_());
               this.f_19853_.m_7967_(projectile);
            }
         }
      }

      if (this.getAnimation() == REMNANT_ROAR2 && this.getAnimationTick() == 23) {
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 60);
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
      }

      if (this.getAnimation() == REMNANT_PHASE_ROAR && this.getAnimationTick() == 23) {
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 60);
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
         this.setIsPower(true);
      }

      if (this.getAnimation() == REMNANT_TAIL_THREE) {
         if (this.getAnimationTick() == 1) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.REMNANT_TAIL_SLAM.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         if (this.getAnimationTick() == 37) {
            this.AreaAttack(10.0F, 10.0F, 50.0F, 1.0F, (float)CMConfig.RemnantHpDamage, 160, 0);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.EarthQuakeSummon(5.5F, 20 + this.f_19796_.m_188503_(10), -0.75F);
         }

         if (this.getAnimationTick() == 55) {
            this.AreaAttack(10.0F, 10.0F, 50.0F, 1.0F, (float)CMConfig.RemnantHpDamage, 160, 0);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.EarthQuakeSummon(5.5F, 20 + this.f_19796_.m_188503_(10), -0.75F);
         }

         if (this.getAnimationTick() == 73) {
            this.AreaAttack(10.0F, 10.0F, 50.0F, 1.0F, (float)CMConfig.RemnantHpDamage, 160, 0);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.EarthQuakeSummon(5.5F, 20 + this.f_19796_.m_188503_(10), -0.75F);
         }
      }

      if (this.getAnimation() == REMNANT_LEFT_STOMP) {
         if (this.getAnimationTick() == 28) {
            this.StompParticle(0.9F, 1.3F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
            if (this.getIsPower()) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, REMNANT_LEFT_STOMP_EXTRA);
            }
         }

         for (int l = 28; l <= 45; l += 2) {
            if (this.getAnimationTick() == l) {
               int d = l - 26;
               int d2 = l - 25;
               float ds = (float)((d + d2) / 2);
               this.StompDamage(0.4F, d, 6, 0.9F, 0.0F, 1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.StompDamage(0.4F, d2, 6, 0.9F, 0.0F, 1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.Stompsound(ds, 1.3F);
            }
         }
      }

      if (this.getAnimation() == REMNANT_LEFT_STOMP_EXTRA) {
         for (int lx = 2; lx <= 19; lx += 2) {
            if (this.getAnimationTick() == lx) {
               int d2 = lx + 1;
               float ds = (float)(lx + d2) / 2.0F;
               this.StompDamage(0.4F, lx, 6, 0.9F, 0.0F, 1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.StompDamage(0.4F, d2, 6, 0.9F, 0.0F, 1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.Stompsound(ds, 1.3F);
            }
         }

         if (this.getAnimationTick() == 19) {
            this.StompParticle(0.9F, 1.3F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         for (int lxx = 19; lxx <= 36; lxx += 2) {
            if (this.getAnimationTick() == lxx) {
               int d = lxx - 17;
               int d2 = lxx - 16;
               float ds = (float)((d + d2) / 2);
               this.StompDamage(0.4F, d, 6, 0.9F, 0.0F, 1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.StompDamage(0.4F, d2, 6, 0.9F, 0.0F, 1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.Stompsound(ds, 1.3F);
            }
         }
      }

      if (this.getAnimation() == REMNANT_RIGHT_STOMP) {
         if (this.getAnimationTick() == 28) {
            this.StompParticle(0.9F, -1.3F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
            if (this.getIsPower()) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, REMNANT_RIGHT_STOMP_EXTRA);
            }
         }

         for (int lxxx = 28; lxxx <= 45; lxxx += 2) {
            if (this.getAnimationTick() == lxxx) {
               int d = lxxx - 26;
               int d2 = lxxx - 25;
               float ds = (float)((d + d2) / 2);
               this.StompDamage(0.4F, d, 6, 0.9F, 0.0F, -1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.StompDamage(0.4F, d2, 6, 0.9F, 0.0F, -1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.Stompsound(ds, -1.3F);
            }
         }
      }

      if (this.getAnimation() == REMNANT_RIGHT_STOMP_EXTRA) {
         for (int lxxxx = 2; lxxxx <= 19; lxxxx += 2) {
            if (this.getAnimationTick() == lxxxx) {
               int d2 = lxxxx + 1;
               float ds = (float)(lxxxx + d2) / 2.0F;
               this.StompDamage(0.4F, lxxxx, 6, 0.9F, 0.0F, -1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.StompDamage(0.4F, d2, 6, 0.9F, 0.0F, -1.3F, 80, 0.85F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.Stompsound(ds, -1.3F);
            }
         }

         if (this.getAnimationTick() == 19) {
            this.StompParticle(0.9F, -1.3F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
            this.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 1.0F);
         }

         for (int lxxxxx = 19; lxxxxx <= 36; lxxxxx += 2) {
            if (this.getAnimationTick() == lxxxxx) {
               int d = lxxxxx - 17;
               int d2 = lxxxxx - 16;
               float ds = (float)((d + d2) / 2);
               this.StompDamage(0.4F, d, 6, 0.9F, 0.0F, -1.3F, 80, 1.0F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.StompDamage(0.4F, d2, 6, 0.9F, 0.0F, -1.3F, 80, 1.0F, (float)CMConfig.RemnantStompHpDamage, 0.1F);
               this.Stompsound(ds, -1.3F);
            }
         }
      }

      if (this.getAnimation() == REMNANT_DEATH && (this.getAnimationTick() == 52 || this.getAnimationTick() == 62 || this.getAnimationTick() == 77)) {
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.05F, 0, 8);
      }
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      Item item = itemstack.m_41720_();
      if (item == ModItems.NECKLACE_OF_THE_DESERT.get() && !this.getIsAct()) {
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         this.setIsAct(true);
         return InteractionResult.SUCCESS;
      } else {
         return super.m_6071_(player, hand);
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, int stunticks) {
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
            && !(entityHit instanceof Ancient_Ancient_Remnant_Entity)
            && entityHit != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entityHit.m_6469_(
               damagesource,
               (float)(
                  this.m_21133_(Attributes.f_22281_) * (double)damage
                     + Math.min(this.m_21133_(Attributes.f_22281_) * (double)damage, (double)(entityHit.m_21233_() * hpdamage))
               )
            );
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag && stunticks > 0) {
               entityHit.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), stunticks));
            }
         }
      }
   }

   private void TailAreaAttack(float range, float height, float height2, float arc, float damage, float hpdamage, int shieldbreakticks, int stunticks) {
      for (LivingEntity entityHit : this.getTailEntityLivingBaseNearby((double)range, (double)height, (double)height2, (double)range, (double)range)) {
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
            && !(entityHit instanceof Ancient_Ancient_Remnant_Entity)
            && entityHit != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entityHit.m_6469_(
               damagesource,
               (float)(
                  this.m_21133_(Attributes.f_22281_) * (double)damage
                     + Math.min(this.m_21133_(Attributes.f_22281_) * (double)damage, (double)(entityHit.m_21233_() * hpdamage))
               )
            );
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               if (stunticks > 0) {
                  entityHit.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), stunticks));
               }

               double d0 = entityHit.m_20185_() - this.m_20185_();
               double d1 = entityHit.m_20189_() - this.m_20189_();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               entityHit.m_5997_(d0 / d2 * 4.0, 0.2, d1 / d2 * 4.0);
            }
         }
      }
   }

   private void StompDamage(
      float spreadarc, int distance, int height, float mxy, float vec, float math, int shieldbreakticks, float damage, float hpdamage, float airborne
   ) {
      double perpFacing = (double)this.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int hitY = Mth.m_14107_(this.m_20191_().f_82289_ - 0.5);
      double spread = Math.PI * (double)spreadarc;
      int arcLen = Mth.m_14165_((double)distance * spread);
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));

      for (int i = 0; i < arcLen; i++) {
         double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = this.m_20185_() + vx * (double)distance + (double)vec * Math.cos((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0) + (double)(f * math);
         double pz = this.m_20189_() + vz * (double)distance + (double)vec * Math.sin((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0 + (double)(f1 * math));
         float factor = 1.0F - (float)distance / 12.0F;
         int hitX = Mth.m_14107_(px);
         int hitZ = Mth.m_14107_(pz);
         BlockPos pos = new BlockPos(hitX, hitY + height, hitZ);
         BlockState block = this.f_19853_.m_8055_(pos);
         int maxDepth = 30;

         for (int depthCount = 0; depthCount < maxDepth && block.m_60799_() != RenderShape.MODEL; depthCount++) {
            pos = pos.m_7495_();
            block = this.f_19853_.m_8055_(pos);
         }

         if (block.m_60799_() != RenderShape.MODEL) {
            block = Blocks.f_50016_.m_49966_();
         }

         this.spawnBlocks(
            hitX, hitY + height, hitZ, (int)(this.m_20186_() - (double)height), block, px, pz, mxy, vx, vz, factor, shieldbreakticks, damage, hpdamage
         );
      }
   }

   private void spawnBlocks(
      int hitX,
      int hitY,
      int hitZ,
      int lowestYCheck,
      BlockState blockState,
      double px,
      double pz,
      float mxy,
      double vx,
      double vz,
      float factor,
      int shieldbreakticks,
      float damage,
      float hpdamage
   ) {
      BlockPos blockpos = new BlockPos(hitX, hitY, hitZ);
      BlockState block = this.f_19853_.m_8055_(blockpos);
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
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14143_((float)lowestYCheck) - 1);

      Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
         this.f_19853_, (double)hitX + 0.5, (double)blockpos.m_123342_() + d0 + 0.5, (double)hitZ + 0.5, blockState, 10
      );
      fallingBlockEntity.m_5997_(0.0, 0.2 + this.m_217043_().m_188583_() * 0.04, 0.0);
      this.f_19853_.m_7967_(fallingBlockEntity);
      AABB selection = new AABB(
         px - 0.5, (double)blockpos.m_123342_() + d0 - 1.0, pz - 0.5, px + 0.5, (double)blockpos.m_123342_() + d0 + (double)mxy, pz + 0.5
      );

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, selection)) {
         if (!this.m_7307_(entity) && !(entity instanceof Ancient_Ancient_Remnant_Entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage + (double)(entity.m_21233_() * hpdamage)));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag) {
               double magnitude = -4.0;
               double x = vx * (double)(1.0F - factor) * magnitude;
               double y = 0.0;
               if (entity.m_20096_()) {
                  y += 0.15;
               }

               double z = vz * (double)(1.0F - factor) * magnitude;
               entity.m_20256_(entity.m_20184_().m_82520_(x, y, z));
            }
         }
      }
   }

   private void Stompsound(float distance, float math) {
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      this.f_19853_
         .m_7785_(
            this.m_20185_() + (double)distance * vecX + (double)(f * math),
            this.m_20186_(),
            this.m_20189_() + (double)distance * vecZ + (double)(f1 * math),
            (SoundEvent)ModSounds.REMNANT_SHOCKWAVE.get(),
            this.m_5720_(),
            1.5F,
            0.8F + this.m_217043_().m_188501_() * 0.1F,
            false
         );
   }

   public List<LivingEntity> getTailEntityLivingBaseNearby(double distanceX, double distanceMinY, double distanceMaxY, double distanceZ, double radius) {
      return this.getTailEntitiesNearby(LivingEntity.class, distanceX, distanceMinY, distanceMaxY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getTailEntitiesNearby(Class<T> entityClass, double dX, double dY, double pY, double dZ, double r) {
      return this.f_19853_
         .m_6443_(
            entityClass,
            new AABB(this.m_20185_() - dX, this.m_20186_() - dY, this.m_20189_() - dZ, this.m_20185_() + dX, this.m_20186_() + pY, this.m_20189_() + dZ),
            e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F) && e.m_20186_() <= this.m_20186_() + dY
         );
   }

   private void StompParticle(float vec, float math) {
      if (this.f_19853_.f_46443_) {
         float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
         float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = 0.5 * (double)Mth.m_14031_((float)(Math.PI + (double)angle));
            double extraY = 0.3F;
            double extraZ = 0.5 * (double)Mth.m_14089_(angle);
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            this.f_19853_
               .m_7106_(
                  new BlockParticleOption(ParticleTypes.f_123794_, block),
                  this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                  this.m_20186_() + extraY,
                  this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                  DeltaMovementX,
                  DeltaMovementY,
                  DeltaMovementZ
               );
         }

         this.f_19853_
            .m_7106_(
               new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 20, 1.0F, 1.0F, 1.0F, 1.0F, 25.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
               this.m_20185_() + (double)vec * vecX + (double)(f * math),
               this.m_20186_() + 0.2F,
               this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void EarthQuakeSummon(float vec, int quake, float math) {
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      float angle = 360.0F / (float)quake;

      for (int i = 0; i < quake; i++) {
         EarthQuake_Entity peq = new EarthQuake_Entity(this.f_19853_, this);
         peq.setDamage((float)CMConfig.AncientRemnantEarthQuakeDamage);
         peq.m_37251_(this, 0.0F, angle * (float)i, 0.0F, 0.45F, 0.0F);
         peq.m_6034_(this.m_20185_() + (double)vec * vecX + (double)(f * math), this.m_20186_(), this.m_20189_() + (double)vec * vecZ + (double)(f1 * math));
         this.f_19853_.m_7967_(peq);
      }
   }

   protected SoundEvent m_7515_() {
      return this.getIsAct() ? (SoundEvent)ModSounds.REMNANT_IDLE.get() : super.m_7515_();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.REMNANT_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.REMNANT_DEATH.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.REMNANT_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && this.getIsAct();
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return REMNANT_DEATH;
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossEvent.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossEvent.m_6539_(player);
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   private static enum AttackMode {
      CIRCLE,
      MELEE;
   }

   class AwakenGoal extends Goal {
      public AwakenGoal() {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean m_8036_() {
         return Ancient_Ancient_Remnant_Entity.this.activeProgress > 0.0F;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         Ancient_Ancient_Remnant_Entity.this.m_20334_(0.0, Ancient_Ancient_Remnant_Entity.this.m_20184_().f_82480_, 0.0);
      }
   }

   static class RemnantAnimationAttackGoal extends SimpleAnimationGoal<Ancient_Ancient_Remnant_Entity> {
      private final int look;

      public RemnantAnimationAttackGoal(Ancient_Ancient_Remnant_Entity entity, Animation animation, int look) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.look = look;
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < this.look && target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }
      }
   }

   class RemnantAttackGoal extends Goal {
      private final Ancient_Ancient_Remnant_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private final float huntingTime = 0.0F;
      private float circleDistance = 9.0F;
      private boolean clockwise = false;

      public RemnantAttackGoal(Ancient_Ancient_Remnant_Entity mob) {
         this.mob = mob;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         this.target = this.mob.m_5448_();
         return this.target != null && this.target.m_6084_() && this.mob.getAnimation() == IAnimatedEntity.NO_ANIMATION;
      }

      public boolean m_8045_() {
         this.target = this.mob.m_5448_();
         return this.target != null;
      }

      public void m_8056_() {
         this.mob.mode = Ancient_Ancient_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.circleDistance = (float)(18 + this.mob.f_19796_.m_188503_(10));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.mob.m_21561_(true);
      }

      public void m_8041_() {
         this.mob.mode = Ancient_Ancient_Remnant_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.circleDistance = (float)(18 + this.mob.f_19796_.m_188503_(10));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.target = this.mob.m_5448_();
         if (!EntitySelector.f_20406_.test(this.target)) {
            this.mob.m_6710_(null);
         }

         this.mob.m_21573_().m_26573_();
         if (this.mob.m_5448_() == null) {
            this.mob.m_21561_(false);
            this.mob.m_21573_().m_26573_();
         }
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         LivingEntity target = this.mob.m_5448_();
         if (target != null) {
            if (this.mob.mode == Ancient_Ancient_Remnant_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               Ancient_Ancient_Remnant_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (0.0F >= (float)this.mob.hunting_cooldown) {
                  this.mob.mode = Ancient_Ancient_Remnant_Entity.AttackMode.MELEE;
               } else if ((double)this.mob.m_20270_(target) < 4.0) {
                  this.mob.mode = Ancient_Ancient_Remnant_Entity.AttackMode.MELEE;
               }
            } else if (this.mob.mode == Ancient_Ancient_Remnant_Entity.AttackMode.MELEE) {
               this.mob.m_21573_().m_5624_(target, 1.0);
               this.mob.m_21563_().m_24960_(target, 30.0F, 30.0F);
               if ((this.mob.roar2_cooldown > 0 || !(this.mob.m_217043_().m_188501_() * 100.0F < 12.0F) || !(target.m_20186_() >= this.mob.m_20186_() + 8.0))
                  && (this.mob.roar2_cooldown > 0 || !(this.mob.m_217043_().m_188501_() * 100.0F < 3.0F) || !((double)this.mob.m_20270_(target) > 12.0))) {
                  if (this.mob.roar_cooldown <= 0 && this.mob.m_217043_().m_188501_() * 100.0F < 3.0F) {
                     this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_ROAR);
                     this.mob.roar_cooldown = 500;
                  } else if (this.mob.earthquake_cooldown <= 0
                     && this.mob.m_217043_().m_188501_() * 100.0F < 7.0F
                     && (double)this.mob.m_20270_(target) < 12.0
                     && target.m_20096_()) {
                     this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_TAIL_THREE);
                     this.mob.earthquake_cooldown = 160;
                  } else if (this.mob.charge_cooldown <= 0
                     && this.mob.m_217043_().m_188501_() * 100.0F < 9.0F
                     && (double)this.mob.m_20270_(target) > 7.0
                     && (double)this.mob.m_20270_(target) < 20.0
                     && target.m_20186_() <= this.mob.m_20186_() + 1.0) {
                     if (this.mob.f_19796_.m_188499_()) {
                        this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_RIGHT_STOMP);
                     } else {
                        this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_LEFT_STOMP);
                     }
                  } else if (this.mob.charge_cooldown <= 0 && this.mob.m_217043_().m_188501_() * 100.0F < 3.0F && (double)this.mob.m_20270_(target) > 12.0) {
                     this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_CHARGE_PREPARE);
                     this.mob.charge_cooldown = 250;
                  } else if (this.mob.charge_cooldown <= 0
                     && this.mob.m_217043_().m_188501_() * 100.0F < 9.0F
                     && (double)this.mob.m_20270_(target) > 7.0
                     && (double)this.mob.m_20270_(target) < 20.0
                     && target.m_20186_() <= this.mob.m_20186_() + 4.5) {
                     if (this.mob.f_19796_.m_188499_()) {
                        this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_RIGHT_STOMP);
                     } else {
                        this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_LEFT_STOMP);
                     }

                     this.mob.stomp_cooldown = 200;
                  } else if (this.mob.m_217043_().m_188501_() * 100.0F < 10.0F
                     && (double)this.mob.m_20270_(target) < 7.0
                     && target.m_20186_() < this.mob.m_20186_() + 1.0) {
                     this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_TAIL_ATTACK1);
                  } else if (this.mob.m_217043_().m_188501_() * 100.0F < 10.0F && (double)this.mob.m_20270_(target) < 6.0) {
                     if (this.mob.f_19796_.m_188499_()) {
                        this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_BITE1);
                     } else {
                        this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_BITE2);
                     }
                  }
               } else {
                  this.mob.setAnimation(Ancient_Ancient_Remnant_Entity.REMNANT_ROAR2);
                  this.mob.roar2_cooldown = 200;
               }
            }
         }
      }
   }

   static class RemnantChargeAttackGoal extends SimpleAnimationGoal<Ancient_Ancient_Remnant_Entity> {
      public RemnantChargeAttackGoal(Ancient_Ancient_Remnant_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 100;
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 62 && target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() < 122 && this.entity.getAnimationTick() > 62 && this.entity.m_20096_()) {
            Vec3 vector3d = this.entity.m_20184_();
            float f = this.entity.m_146908_() * (float) (Math.PI / 180.0);
            Vec3 vector3d1 = new Vec3((double)(-Mth.m_14031_(f)), this.entity.m_20184_().f_82480_, (double)Mth.m_14089_(f))
               .m_82490_(1.0)
               .m_82549_(vector3d.m_82490_(0.5));
            this.entity.m_20334_(vector3d1.f_82479_, this.entity.m_20184_().f_82480_, vector3d1.f_82481_);
         }

         if (this.entity.getAnimationTick() == 62) {
            this.entity.setIsCharge(true);
         }

         if (this.entity.getAnimationTick() == 122) {
            this.entity.setIsCharge(false);
         }
      }
   }

   static class RemnantSteleAttackGoal extends SimpleAnimationGoal<Ancient_Ancient_Remnant_Entity> {
      private final int look;

      public RemnantSteleAttackGoal(Ancient_Ancient_Remnant_Entity entity, Animation animation, int look) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.look = look;
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < this.look && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == this.look && target != null) {
            double d1 = target.m_20186_();
            float f = (float)Mth.m_14136_(target.m_20189_() - this.entity.m_20189_(), target.m_20185_() - this.entity.m_20185_());

            for (int k = 0; k < 10; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 10.0F + 0.41887903F;
               this.spawnSpikeLine(this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 4.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 4.5, d1, f4, 10);
            }

            for (int k = 0; k < 12; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 12.0F + (float) (Math.PI / 10);
               this.spawnSpikeLine(this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 7.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 7.5, d1, f4, 15);
            }

            for (int k = 0; k < 14; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 17.0F + (float) (Math.PI / 15);
               this.spawnSpikeLine(
                  this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 10.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 10.5, d1, f4, 20
               );
            }

            for (int k = 0; k < 16; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 16.0F + (float) (Math.PI / 20);
               this.spawnSpikeLine(
                  this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 13.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 13.5, d1, f4, 25
               );
            }

            for (int k = 0; k < 18; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 18.0F + 0.10471976F;
               this.spawnSpikeLine(
                  this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 16.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 16.5, d1, f4, 30
               );
            }

            for (int k = 0; k < 20; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 20.0F + 0.07853982F;
               this.spawnSpikeLine(
                  this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 19.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 19.5, d1, f4, 35
               );
            }

            for (int k = 0; k < 22; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 22.0F + 0.05235988F;
               this.spawnSpikeLine(
                  this.entity.m_20185_() + (double)Mth.m_14089_(f4) * 22.5, this.entity.m_20189_() + (double)Mth.m_14031_(f4) * 22.5, d1, f4, 40
               );
            }

            for (int l = 0; l < 16; l++) {
               double d2 = 1.25 * (double)(l + 1);
               int j = (int)(5.0F + 1.5F * (float)l);
               this.spawnSpikeLine(this.entity.m_20185_() + (double)Mth.m_14089_(f) * d2, this.entity.m_20189_() + (double)Mth.m_14031_(f) * d2, d1, f, j);
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
         } while (blockpos.m_123342_() < Math.min(this.entity.f_19853_.m_151558_(), this.entity.m_146904_() + 20));

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
