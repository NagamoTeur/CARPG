package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAnimationGoal1;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAnimationGoal2;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackMoveGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ender_Guardian_Bullet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity;

public class Ender_Guardian_Entity extends LLibrary_Boss_Monster {
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.m_5446_(), BossBarColor.PURPLE, false, 1);
   private static final EntityDataAccessor<Boolean> IS_HELMETLESS = SynchedEntityData.m_135353_(Ender_Guardian_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> USED_MASS_DESTRUCTION = SynchedEntityData.m_135353_(
      Ender_Guardian_Entity.class, EntityDataSerializers.f_135035_
   );
   private static final EntityDataAccessor<Optional<BlockPos>> TELEPORT_POS = SynchedEntityData.m_135353_(
      Ender_Guardian_Entity.class, EntityDataSerializers.f_135039_
   );
   public static final Animation GUARDIAN_RIGHT_STRONG_ATTACK = Animation.create(60);
   public static final Animation GUARDIAN_LEFT_STRONG_ATTACK = Animation.create(60);
   public static final Animation GUARDIAN_RIGHT_ATTACK = Animation.create(40);
   public static final Animation GUARDIAN_LEFT_ATTACK = Animation.create(40);
   public static final Animation GUARDIAN_BURST_ATTACK = Animation.create(53);
   public static final Animation GUARDIAN_UPPERCUT_AND_BULLET = Animation.create(100);
   public static final Animation GUARDIAN_RAGE_UPPERCUT = Animation.create(120);
   public static final Animation GUARDIAN_STOMP = Animation.create(48);
   public static final Animation GUARDIAN_RAGE_STOMP = Animation.create(83);
   public static final Animation GUARDIAN_MASS_DESTRUCTION = Animation.create(75);
   public static final Animation GUARDIAN_FALLEN = Animation.create(196);
   public static final Animation GUARDIAN_HUG_ME = Animation.create(76);
   public static final Animation GUARDIAN_AIR_STRIKE1 = Animation.create(123);
   public static final Animation GUARDIAN_AIR_STRIKE2 = Animation.create(39);
   public static final Animation GUARDIAN_RIGHT_SWING = Animation.create(42);
   public static final Animation GUARDIAN_LEFT_SWING = Animation.create(42);
   public static final Animation GUARDIAN_BLACKHOLE = Animation.create(62);
   public static final Animation GUARDIAN_ROCKETPUNCH = Animation.create(58);
   public static final int STOMP_COOLDOWN = 400;
   public static final int UPPERCUT_COOLDOWN = 200;
   public static final int TELEPORT_COOLDOWN = 280;
   public static final int TELEPORT_SMASH_COOLDOWN = 600;
   public static final int VORTEX_COOLDOWN = 280;
   public static final int NATURE_HEAL_COOLDOWN = 200;
   public boolean Breaking = CMConfig.EnderguardianBlockBreaking;
   private int timeWithoutTarget;
   private int stomp_cooldown = 0;
   private int teleport_cooldown = 0;
   private int teleport_smash_cooldown = 0;
   private int uppercut_cooldown = 0;
   private int vortexcooldown = 0;

   public Ender_Guardian_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 300;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0F);
      setConfigattribute(this, CMConfig.EnderguardianHealthMultiplier, CMConfig.EnderguardianDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.75F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         NO_ANIMATION,
         GUARDIAN_RIGHT_STRONG_ATTACK,
         GUARDIAN_LEFT_STRONG_ATTACK,
         GUARDIAN_BURST_ATTACK,
         GUARDIAN_UPPERCUT_AND_BULLET,
         GUARDIAN_STOMP,
         GUARDIAN_RIGHT_ATTACK,
         GUARDIAN_LEFT_ATTACK,
         GUARDIAN_MASS_DESTRUCTION,
         GUARDIAN_RAGE_STOMP,
         GUARDIAN_RAGE_UPPERCUT,
         GUARDIAN_FALLEN,
         GUARDIAN_HUG_ME,
         GUARDIAN_AIR_STRIKE1,
         GUARDIAN_AIR_STRIKE2,
         GUARDIAN_LEFT_SWING,
         GUARDIAN_RIGHT_SWING,
         GUARDIAN_BLACKHOLE,
         GUARDIAN_ROCKETPUNCH
      };
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new AttackMoveGoal(this, true, 1.0));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.PunchAttackGoal(this));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal2<Ender_Guardian_Entity>(this, GUARDIAN_MASS_DESTRUCTION, 39, 50) {
         public void m_8056_() {
            super.m_8056_();
            this.entity.setUsedMassDestruction(true);
         }
      });
      this.f_21345_.m_25352_(1, new AttackAnimationGoal2<>(this, GUARDIAN_BURST_ATTACK, 27, 47));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.VoidVortexGoal(this, GUARDIAN_BLACKHOLE));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.RocketPunchGoal(this, GUARDIAN_ROCKETPUNCH));
      this.f_21345_.m_25352_(1, new SimpleAnimationGoal<>(this, GUARDIAN_AIR_STRIKE2));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, GUARDIAN_RIGHT_SWING, 26, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, GUARDIAN_LEFT_SWING, 26, true));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.HugmeGoal(this, GUARDIAN_HUG_ME, 30.0F, 20));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.TeleportStrikeGoal(this, GUARDIAN_AIR_STRIKE1));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.StompAttackGoal(this));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.UppercutAndBulletGoal(this, GUARDIAN_UPPERCUT_AND_BULLET));
      this.f_21345_.m_25352_(1, new Ender_Guardian_Entity.RageUppercut(this, GUARDIAN_RAGE_UPPERCUT));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public static Builder ender_guardian() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 50.0)
         .m_22268_(Attributes.f_22279_, 0.27F)
         .m_22268_(Attributes.f_22281_, 16.0)
         .m_22268_(Attributes.f_22276_, 333.0)
         .m_22268_(Attributes.f_22284_, 20.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_HELMETLESS, false);
      this.f_19804_.m_135372_(TELEPORT_POS, Optional.empty());
      this.f_19804_.m_135372_(USED_MASS_DESTRUCTION, false);
   }

   private static Animation getRandomAttack(RandomSource rand) {
      switch (rand.m_188503_(4)) {
         case 0:
            return GUARDIAN_RIGHT_STRONG_ATTACK;
         case 1:
            return GUARDIAN_LEFT_STRONG_ATTACK;
         case 2:
            return GUARDIAN_RIGHT_ATTACK;
         case 3:
            return GUARDIAN_LEFT_ATTACK;
         default:
            return GUARDIAN_RIGHT_STRONG_ATTACK;
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      Optional<BlockPos> restPos = this.getTeleportPos();
      if (restPos.isPresent()) {
         compound.m_128365_("TeleportPos", NbtUtils.m_129224_(this.getTeleportPos().get()));
      }

      compound.m_128379_("is_Helmetless", this.getIsHelmetless());
      compound.m_128379_("used_mass_destruction", this.getUsedMassDestruction());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("TeleportPos")) {
         this.setTeleportPos(NbtUtils.m_129239_(compound.m_128469_("TeleportPos")));
      }

      this.setIsHelmetless(compound.m_128471_("is_Helmetless"));
      this.setUsedMassDestruction(compound.m_128471_("used_mass_destruction"));
      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   public void setIsHelmetless(boolean isHelmetless) {
      if (isHelmetless) {
         this.m_21051_(Attributes.f_22284_).m_22100_(15.0);
         this.m_21051_(Attributes.f_22279_).m_22100_(0.29F);
      } else {
         this.m_21051_(Attributes.f_22284_).m_22100_(20.0);
         this.m_21051_(Attributes.f_22279_).m_22100_(0.27F);
      }

      this.f_19804_.m_135381_(IS_HELMETLESS, isHelmetless);
   }

   public boolean getIsHelmetless() {
      return (Boolean)this.f_19804_.m_135370_(IS_HELMETLESS);
   }

   public void setUsedMassDestruction(boolean usedMassDestruction) {
      this.f_19804_.m_135381_(USED_MASS_DESTRUCTION, usedMassDestruction);
   }

   public boolean getUsedMassDestruction() {
      return (Boolean)this.f_19804_.m_135370_(USED_MASS_DESTRUCTION);
   }

   public void setTeleportPos(BlockPos p_30220_) {
      this.f_19804_.m_135381_(TELEPORT_POS, Optional.of(p_30220_));
   }

   public Optional<BlockPos> getTeleportPos() {
      return (Optional<BlockPos>)this.f_19804_.m_135370_(TELEPORT_POS);
   }

   public void m_6593_(@Nullable Component name) {
      super.m_6593_(name);
      this.bossInfo.m_6456_(this.m_5446_());
   }

   protected int m_7302_(int air) {
      return air;
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if (this.getAnimation() == GUARDIAN_MASS_DESTRUCTION && !source.m_19378_()) {
         return false;
      } else {
         double range = this.calculateRange(source);
         if (range > CMConfig.EnderguardianLongRangelimit * CMConfig.EnderguardianLongRangelimit && !source.m_19378_()) {
            return false;
         } else {
            Entity entity = source.m_7640_();
            if (!this.getIsHelmetless() && entity instanceof AbstractArrow) {
               return false;
            } else if (!(entity instanceof ShulkerBullet) && !(entity instanceof Ender_Guardian_Bullet_Entity)) {
               if (entity instanceof AbstractGolem) {
                  damage = (float)((double)damage * 0.5);
               }

               boolean attack = super.m_6469_(source, damage);
               if (attack && this.getIsHelmetless()) {
                  this.m_5496_(SoundEvents.f_12414_, 1.0F, 0.8F);
               }

               return attack;
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.EnderguardianDamageCap;
   }

   public boolean m_6673_(DamageSource source) {
      return source == DamageSource.f_19310_ || super.m_6673_(source) || source.m_146707_();
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.repelEntities(1.8F, 4.0F, 1.8F, 1.8F);
      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
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
            && CMConfig.EnderguardianNatureHealing > 0.0
            && this.f_19797_ % 20 == 0) {
            this.m_5634_((float)CMConfig.EnderguardianNatureHealing);
         }
      }

      Animation animation = getRandomAttack(this.f_19796_);
      Animation animation2 = this.getIsHelmetless() ? GUARDIAN_RAGE_UPPERCUT : GUARDIAN_UPPERCUT_AND_BULLET;
      if (this.m_6084_()) {
         if (!this.getIsHelmetless() && this.isHelmetless()) {
            this.setIsHelmetless(true);
            this.BrokenHelmet();
         }

         if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && !this.getUsedMassDestruction() && this.isHelmetless()) {
            this.setAnimation(GUARDIAN_MASS_DESTRUCTION);
         } else if (target != null && target.m_6084_()) {
            if (!this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.m_20280_(target) >= 256.0
               && this.m_20280_(target) <= 1024.0
               && target.m_20096_()
               && this.getIsHelmetless()
               && this.m_217043_().m_188501_() * 100.0F < 20.0F
               && this.teleport_smash_cooldown <= 0) {
               this.teleport_smash_cooldown = 600;
               this.setAnimation(GUARDIAN_AIR_STRIKE1);
            } else if (this.vortexcooldown > 0
               || this.m_21525_()
               || this.getAnimation() != NO_ANIMATION
               || !(this.m_20280_(target) <= 1024.0)
               || (!(this.m_20280_(target) >= 35.0) || !(this.m_217043_().m_188501_() * 100.0F < 2.0F))
                  && (!(this.m_217043_().m_188501_() * 100.0F < 60.0F) || !(this.m_20186_() + 3.0 <= target.m_20186_()))) {
               if (!this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && (double)this.m_20270_(target) >= 4.3
                  && (double)this.m_20270_(target) <= 16.0
                  && target.m_20096_()
                  && this.m_217043_().m_188501_() * 100.0F < 4.0F
                  && this.teleport_cooldown <= 0) {
                  this.teleport_cooldown = 280;
                  this.setAnimation(GUARDIAN_HUG_ME);
               } else if (!this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && (double)this.m_20270_(target) < 4.3
                  && target.m_20096_()
                  && this.m_217043_().m_188501_() * 100.0F < 0.7F
                  && this.teleport_cooldown <= 0) {
                  this.teleport_cooldown = 280;
                  this.setAnimation(GUARDIAN_HUG_ME);
               } else if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.m_20270_(target) < 2.75F) {
                  if (this.f_19796_.m_188503_(6) == 0) {
                     this.setAnimation(GUARDIAN_BURST_ATTACK);
                  } else {
                     this.setAnimation(animation);
                  }
               } else if (this.m_21525_()
                  || this.getAnimation() != NO_ANIMATION
                  || !(this.m_20270_(target) > 2.75F)
                  || !(this.m_20270_(target) < 4.3F)
                  || !target.m_21023_(MobEffects.f_19620_) && !target.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
                  if (this.stomp_cooldown <= 0
                     && !this.m_21525_()
                     && this.getAnimation() == NO_ANIMATION
                     && target.m_20096_()
                     && (
                        this.m_20270_(target) > 6.0F && this.m_20270_(target) < 13.0F && this.m_217043_().m_188501_() * 100.0F < 10.0F
                           || this.m_20270_(target) > 2.75F && this.m_20270_(target) < 4.3F && this.m_217043_().m_188501_() * 100.0F < 2.0F
                     )) {
                     this.stomp_cooldown = 400;
                     Animation animation3 = this.getIsHelmetless() ? GUARDIAN_RAGE_STOMP : GUARDIAN_STOMP;
                     this.setAnimation(animation3);
                  } else if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.m_20270_(target) > 2.75F && this.m_20270_(target) < 4.3F) {
                     if (this.f_19796_.m_188503_(4) == 0) {
                        this.uppercut_cooldown = 200;
                        this.setAnimation(animation2);
                     } else {
                        this.setAnimation(animation);
                     }
                  }
               } else {
                  this.uppercut_cooldown = 200;
                  this.setAnimation(animation2);
               }
            } else {
               this.vortexcooldown = 280;
               this.setAnimation(GUARDIAN_BLACKHOLE);
            }
         }
      }

      if (this.stomp_cooldown > 0) {
         this.stomp_cooldown--;
      }

      if (this.teleport_cooldown > 0) {
         this.teleport_cooldown--;
      }

      if (this.teleport_smash_cooldown > 0) {
         this.teleport_smash_cooldown--;
      }

      if (this.uppercut_cooldown > 0) {
         this.uppercut_cooldown--;
      }

      if (this.vortexcooldown > 0) {
         this.vortexcooldown--;
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAnimation() == GUARDIAN_LEFT_STRONG_ATTACK) {
         if (this.getAnimationTick() < 2) {
            this.GravityPullparticle();
         }

         if (this.getAnimationTick() < 29) {
            this.GravityPull();
         }

         if (this.getAnimationTick() == 34) {
            this.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(5.15F, 5.0F, 70.0F, 1.0F, (float)CMConfig.EnderguardianGravityPunchHpdamage, 100, 0, 0.0F, false);
            this.Attackparticle(2.2F, 0.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.2F, 0, 10);
         }
      }

      if (this.getAnimation() == GUARDIAN_RIGHT_STRONG_ATTACK) {
         if (this.getAnimationTick() < 2) {
            this.GravityPullparticle();
         }

         if (this.getAnimationTick() < 24) {
            this.GravityPull();
         }

         if (this.getAnimationTick() == 29) {
            this.AreaAttack(5.15F, 5.0F, 70.0F, 1.0F, (float)CMConfig.EnderguardianGravityPunchHpdamage, 100, 0, 0.0F, false);
            this.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.Attackparticle(2.2F, 0.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.2F, 0, 10);
         }
      }

      if (this.getAnimation() == GUARDIAN_RIGHT_ATTACK && this.getAnimationTick() == 22) {
         this.AreaAttack(5.85F, 5.0F, 80.0F, 1.0F, 0.0F, 80, 0, 0.0F, false);
         this.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.Attackparticle(2.75F, 0.5F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_LEFT_ATTACK && this.getAnimationTick() == 19) {
         this.AreaAttack(5.85F, 5.0F, 80.0F, 1.0F, 0.0F, 80, 0, 0.0F, false);
         this.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.Attackparticle(2.75F, -0.5F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_BURST_ATTACK) {
         if (this.getAnimationTick() == 15) {
            this.Burstparticle();
         }

         if (this.getAnimationTick() == 27) {
            this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(7.5F, 6.0F, 100.0F, 1.0F, (float)CMConfig.EnderguardianKnockbackHpdamage, 0, 0, 0.0F, true);
         }
      }

      if ((this.getAnimation() == GUARDIAN_UPPERCUT_AND_BULLET || this.getAnimation() == GUARDIAN_RAGE_UPPERCUT) && this.getAnimationTick() == 27) {
         this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(6.25F, 7.0F, 60.0F, 1.4F, (float)CMConfig.EnderguardianUppercutHpdamage, 150, 60, 0.5F, false);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.3F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_STOMP && this.getAnimationTick() == 32) {
         this.StompAttack();
         this.Attackparticle(0.4F, 0.8F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 10.0F, 0.1F, 0, 5);
      }

      if (this.getAnimation() == GUARDIAN_RAGE_STOMP && (this.getAnimationTick() == 32 || this.getAnimationTick() == 53 || this.getAnimationTick() == 62)) {
         this.StompAttack();
         this.Attackparticle(0.4F, 0.8F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 10.0F, 0.1F, 0, 5);
      }

      if (this.getAnimation() == GUARDIAN_RAGE_UPPERCUT && this.getAnimationTick() == 84) {
         this.RageAttack();
         this.AreaAttack(5.5F, 5.0F, 120.0F, 1.2F, (float)CMConfig.EnderguardianAreaAttackHpdamage, 100, 0, 0.0F, false);
         this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.2F, 0, 10);
      }

      if (this.getAnimation() == GUARDIAN_MASS_DESTRUCTION && this.getAnimationTick() == 39) {
         this.Attackparticle(2.75F, 2.25F);
         this.Attackparticle(2.75F, -2.25F);
         this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.MassDestruction(5.0F, 1.1F, 150);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.3F, 0, 10);
         if (!this.f_19853_.f_46443_) {
            if (this.Breaking) {
               this.BlockBreaking(CMConfig.EnderguardianBlockBreakingX, CMConfig.EnderguardianBlockBreakingY, CMConfig.EnderguardianBlockBreakingZ);
            } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
               this.BlockBreaking(CMConfig.EnderguardianBlockBreakingX, CMConfig.EnderguardianBlockBreakingY, CMConfig.EnderguardianBlockBreakingZ);
            }
         }
      }

      if (this.getAnimation() == GUARDIAN_HUG_ME) {
         if (this.getAnimationTick() == 15) {
            this.Teleportparticle();
         }

         if (this.getAnimationTick() == 38) {
            this.AreaAttack(6.0F, 6.0F, 120.0F, 1.0F, (float)CMConfig.EnderguardianTeleportAttackHpdamage, 80, 60, 0.6F, false);
            this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.2F, 0, 10);
         }
      }

      if (this.getAnimation() == GUARDIAN_AIR_STRIKE1) {
         if (this.getAnimationTick() == 20) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.15F, 0, 20);
            this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.getAnimationTick() == 40) {
            this.m_5496_(SoundEvents.f_12418_, 1.0F, 1.0F);
         }
      }

      if (this.getAnimation() == GUARDIAN_AIR_STRIKE2 && this.getAnimationTick() == 3) {
         this.MassDestruction(3.0F, 1.5F, 200);
         this.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.Attackparticle(2.5F, 1.25F);
         this.Attackparticle(2.5F, -0.5F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
         switch (this.f_19796_.m_188503_(3)) {
            case 0:
               this.StrikeRune(8, 0.5);
               break;
            case 1:
               this.StrikeRune(10, 0.75);
               break;
            case 2:
               this.StrikeRune(12, 1.0);
         }
      }

      if ((this.getAnimation() == GUARDIAN_RIGHT_SWING || this.getAnimation() == GUARDIAN_LEFT_SWING) && this.getAnimationTick() == 24) {
         this.m_5496_(SoundEvents.f_12317_, 2.0F, 0.5F);
         this.AreaAttack(4.25F, 4.25F, 80.0F, 1.0F, 0.0F, 40, 40, 0.0F, true);
      }

      if (this.getAnimation() == GUARDIAN_BLACKHOLE && this.getAnimationTick() == 26) {
         this.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.3F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.Attackparticle(1.0F, 0.2F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 10.0F, 0.1F, 0, 5);
      }

      if (this.getAnimation() == GUARDIAN_ROCKETPUNCH) {
         if (this.getAnimationTick() == 24) {
            this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.getAnimationTick() == 28) {
            this.AreaAttack(7.0F, 7.0F, 120.0F, 1.25F, (float)CMConfig.EnderguardianRocketPunchHpdamage, 200, 0, 0.0F, true);
         }
      }
   }

   private void StrikeRune(int rune, double time) {
      for (int i = 0; i < rune; i++) {
         float throwAngle = (float)i * (float) Math.PI / (float)(rune / 2);

         for (int k = 0; k < 8; k++) {
            double d2 = 1.15 * (double)(k + 1);
            int d3 = (int)(time * (double)(k + 1));
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(throwAngle) * 1.25 * d2,
               this.m_20189_() + (double)Mth.m_14031_(throwAngle) * 1.25 * d2,
               this.m_20186_(),
               this.m_20186_() + 2.0,
               throwAngle,
               d3
            );
         }
      }
   }

   public boolean isHelmetless() {
      return this.m_21223_() <= this.m_21233_() / 2.0F;
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
      if (this.f_20919_ == 50) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYLAND.get(), 1.0F, 1.0F);
      }

      if (this.f_20919_ == 100) {
         this.m_5496_(SoundEvents.f_12418_, 1.0F, 1.0F);
      }
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return GUARDIAN_FALLEN;
   }

   private void AreaAttack(
      float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, int stunticks, float airborne, boolean knockback
   ) {
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
            && !(entityHit instanceof Ender_Guardian_Entity)) {
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

            if (airborne > 0.0F) {
               entityHit.m_20256_(entityHit.m_20184_().m_82520_(0.0, (double)airborne, 0.0));
            }

            if (flag && stunticks > 0) {
               entityHit.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), stunticks));
            }

            if (knockback) {
               this.launch(entityHit);
            }
         }
      }
   }

   private void MassDestruction(float grow, float damage, int ticks) {
      for (LivingEntity entityHit : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_((double)grow))) {
         if (!this.m_7307_(entityHit) && !(entityHit instanceof Ender_Guardian_Entity) && entityHit != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            entityHit.m_6469_(damagesource, (float)this.m_21133_(Attributes.f_22281_) * damage);
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player) {
               Player player = (Player)entityHit;
               if (ticks > 0) {
                  this.disableShield(player, ticks);
               }
            }

            this.launch(entityHit);
         }
      }
   }

   private void BlockBreaking(int x, int y, int z) {
      int MthX = Mth.m_14107_(this.m_20185_());
      int MthY = Mth.m_14107_(this.m_20186_());
      int MthZ = Mth.m_14107_(this.m_20189_());
      boolean flag = false;

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = -y; j <= -1; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState block = this.f_19853_.m_8055_(blockpos);
               if (block != Blocks.f_50016_.m_49966_()
                  && block.m_204336_(ModTag.ENDER_GUARDIAN_CAN_DESTROY)
                  && block.canEntityDestroy(this.f_19853_, blockpos, this)
                  && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, block)) {
                  flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
               }
            }
         }
      }
   }

   private void Burstparticle() {
      if (this.f_19853_.f_46443_) {
         double d0 = this.m_20185_();
         double d1 = this.m_20186_() + 2.0;
         double d2 = this.m_20189_();
         int size = 5;

         for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
               for (int k = -size; k <= size; k++) {
                  double d3 = (double)j + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d4 = (double)i + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d5 = (double)k + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.f_19796_.m_188583_() * 0.05;
                  this.f_19853_.m_7106_(ParticleTypes.f_123789_, d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2 - 1;
                  }
               }
            }
         }
      }
   }

   private void Teleportparticle() {
      if (this.f_19853_.f_46443_ && this.getTeleportPos().isPresent()) {
         double d0 = (double)this.getTeleportPos().get().m_123341_();
         double d1 = this.m_20186_();
         double d2 = (double)this.getTeleportPos().get().m_123343_();
         if (this.f_19853_.f_46443_) {
            for (int i1 = 0; i1 < 40 + this.f_19796_.m_188503_(12); i1++) {
               double DeltaMovementY = this.m_217043_().m_188583_() * 0.07;
               float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
               double extraX = (double)(2.0F * Mth.m_14031_((float)(Math.PI + (double)angle)));
               double extraY = 0.3F;
               double extraZ = (double)(2.0F * Mth.m_14089_(angle));
               this.f_19853_.m_7106_(ParticleTypes.f_123810_, d0 + extraX, d1 + extraY, d2 + extraZ, 0.0, DeltaMovementY, 0.0);
            }
         }
      }
   }

   private void launch(Entity entityHit) {
      double d0 = entityHit.m_20185_() - this.m_20185_();
      double d1 = entityHit.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      entityHit.m_5997_(d0 / d2 * 4.0, 0.2, d1 / d2 * 4.0);
   }

   private void GravityPull() {
      for (LivingEntity inRange : this.getEntityLivingBaseNearby(12.0, 12.0, 12.0, 12.0)) {
         if ((!(inRange instanceof Player) || !((Player)inRange).m_150110_().f_35934_) && !this.m_7307_(inRange)) {
            float angle = (float) (Math.PI / 180.0) * this.f_20883_;
            double extraX = (double)Mth.m_14031_((float)(Math.PI + (double)angle));
            double extraZ = (double)Mth.m_14089_(angle);
            double theta = (double)this.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            double vecZ = Math.sin(theta);
            Vec3 diff = inRange.m_20182_().m_82546_(this.m_20182_().m_82520_(2.0 * vecX + extraX * 0.25, 0.0, 2.0 * vecZ + extraZ * 0.25));
            diff = diff.m_82541_().m_82490_(0.085);
            inRange.m_20256_(inRange.m_20184_().m_82546_(diff));
         }
      }
   }

   private void GravityPullparticle() {
      if (this.f_19853_.f_46443_) {
         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)Mth.m_14031_((float)(Math.PI + (double)angle));
            double extraY = 0.3F;
            double extraZ = (double)Mth.m_14089_(angle);
            double theta = (double)this.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            double vecZ = Math.sin(theta);
            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123760_,
                  this.m_20185_() + 2.2 * vecX + extraX * 0.75,
                  this.m_20186_() + extraY,
                  this.m_20189_() + 2.2 * vecZ + extraZ * 0.75,
                  (this.f_19796_.m_188500_() - 0.5) * 12.0,
                  -this.f_19796_.m_188500_(),
                  (this.f_19796_.m_188500_() - 0.5) * 12.0
               );
         }
      }
   }

   private void Attackparticle(float vec, float math) {
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
            double extraX = 1.2 * (double)Mth.m_14031_((float)(Math.PI + (double)angle));
            double extraY = 0.3F;
            double extraZ = 1.2 * (double)Mth.m_14089_(angle);
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            if (block.m_60799_() != RenderShape.INVISIBLE) {
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
         }

         this.f_19853_
            .m_7106_(
               new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 25, 1.0F, 1.0F, 1.0F, 1.0F, 25.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
               this.m_20185_() + (double)vec * vecX + (double)(f * math),
               this.m_20186_() + 0.3F,
               this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void StompAttack() {
      this.m_5496_((SoundEvent)ModSounds.ENDER_GUARDIAN_FIST.get(), 0.3F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
      LivingEntity target = this.m_5448_();
      if (target != null) {
         double d0 = Math.min(target.m_20186_(), this.m_20186_());
         double d1 = Math.max(target.m_20186_(), this.m_20186_()) + 1.0;
         float f = (float)Mth.m_14136_(target.m_20189_() - this.m_20189_(), target.m_20185_() - this.m_20185_());
         float angle2 = (float) (Math.PI / 180.0) * this.f_20883_;

         for (int k = 0; k < 6; k++) {
            float f2 = angle2 + (float)k * (float) Math.PI * 2.0F / 6.0F + (float) (Math.PI * 2.0 / 5.0);
            this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(f2) * 2.5, this.m_20189_() + (double)Mth.m_14031_(f2) * 2.5, d0, d1, f2, 3);
         }

         for (int k = 0; k < 11; k++) {
            float f3 = angle2 + (float)k * (float) Math.PI * 2.0F / 11.0F + (float) (Math.PI / 5);
            this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(f3) * 3.5, this.m_20189_() + (double)Mth.m_14031_(f3) * 3.5, d0, d1, f3, 10);
         }

         for (int k = 0; k < 14; k++) {
            float f4 = angle2 + (float)k * (float) Math.PI * 2.0F / 14.0F + (float) (Math.PI / 10);
            this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(f4) * 4.5, this.m_20189_() + (double)Mth.m_14031_(f4) * 4.5, d0, d1, f4, 15);
         }

         float[] all = new float[]{f - 0.4F, f, f + 0.4F};
         float[] all2 = new float[]{f - 0.1F, f + 0.1F};
         switch (this.f_19796_.m_188503_(3)) {
            case 0:
               for (float angle : all) {
                  for (int l = 0; l < 13; l++) {
                     double d2 = 1.25 * (double)(l + 1);
                     int j = (int)(0.75F * (float)l);
                     this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(angle) * d2, this.m_20189_() + (double)Mth.m_14031_(angle) * d2, d0, d1, angle, j);
                  }
               }
               break;
            case 1:
               for (float angle : all2) {
                  for (int l = 0; l < 13; l++) {
                     double d2 = 1.25 * (double)(l + 1);
                     int j = (int)(0.25F * (float)l);
                     this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(angle) * d2, this.m_20189_() + (double)Mth.m_14031_(angle) * d2, d0, d1, angle, j);
                  }
               }
               break;
            case 2:
               for (int l = 0; l < 13; l++) {
                  double d2 = 1.25 * (double)(l + 1);
                  int j = (int)(0.5F * (float)l);
                  this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(f) * d2, this.m_20189_() + (double)Mth.m_14031_(f) * d2, d0, d1, f, j);
               }
         }
      }
   }

   private void RageAttack() {
      LivingEntity target = this.m_5448_();
      if (target != null) {
         double d0 = Math.min(target.m_20186_(), this.m_20186_());
         double d1 = Math.max(target.m_20186_(), this.m_20186_()) + 1.0;
         float f = (float)Mth.m_14136_(target.m_20189_() - this.m_20189_(), target.m_20185_() - this.m_20185_());
         float[] all = new float[]{f - 0.3F, f - 0.6F, f - 0.9F, f, f + 0.3F, f + 0.6F, f + 0.9F};

         for (float angle : all) {
            for (int l = 0; l < 10; l++) {
               double d2 = 1.25 * (double)(l + 1);
               int j = (int)(0.75F * (float)l);
               this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(angle) * d2, this.m_20189_() + (double)Mth.m_14031_(angle) * d2, d0, d1, angle, j);
            }
         }
      }
   }

   private void spawnFangs(double x, double z, double minY, double maxY, float rotation, int delay) {
      BlockPos blockpos = new BlockPos(x, maxY, z);
      boolean flag = false;
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

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14107_(minY) - 1);

      if (flag) {
         this.f_19853_
            .m_7967_(new Void_Rune_Entity(this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, delay, (float)CMConfig.Voidrunedamage, this));
      }
   }

   private void spawnVortex(double x, double z, double minY, double maxY, float rotation) {
      BlockPos blockpos = new BlockPos(x, maxY, z);
      boolean flag = false;
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

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14107_(minY) - 1);

      if (flag) {
         this.f_19853_.m_7967_(new Void_Vortex_Entity(this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, this, 300));
      }
   }

   private void BrokenHelmet() {
      if (!this.f_19853_.f_46443_) {
         double xx = (double)(Mth.m_14089_(this.m_146908_() % 360.0F / 180.0F * (float) Math.PI) * 0.75F);
         double zz = (double)(Mth.m_14031_(this.m_146908_() % 360.0F / 180.0F * (float) Math.PI) * 0.75F);
         this.f_19853_.m_46511_(this, this.m_20185_() + xx, this.m_20186_() + (double)this.m_20192_(), this.m_20189_() + zz, 2.0F, BlockInteraction.NONE);
      }
   }

   private void Bulletpattern() {
      LivingEntity target = this.m_5448_();
      if (target != null) {
         BlockPos tgt = target.m_20183_();
         double tx = (double)tgt.m_123341_();
         double tz = (double)tgt.m_123343_();
         double ty = target.m_20186_() + 0.1;
         if (this.getAnimationTick() == 54) {
            if (!target.m_20096_() && !target.m_20069_() && !this.f_19853_.m_8055_(tgt.m_7495_()).m_60767_().m_76334_()) {
               ty--;
            }

            BlockPos Pos = this.m_20183_();
            double sx = (double)Pos.m_123341_();
            double sz = (double)Pos.m_123343_();
            Direction dir = Direction.m_122366_(tx - sx, 0.0, tz - sz);
            double cx = (double)dir.m_122429_();
            double cz = (double)dir.m_122431_();
            double offsetangle = Math.toRadians(6.0);

            for (int i = -4; i <= 4; i++) {
               double angle = (double)(i - 2) * offsetangle;
               double x = cx * Math.cos(angle) + cz * Math.sin(angle);
               double z = -cx * Math.sin(angle) + cz * Math.cos(angle);
               Ender_Guardian_Bullet_Entity bullet = new Ender_Guardian_Bullet_Entity(this.f_19853_, this, x, this.m_20186_() + 2.0, z);
               bullet.m_5602_(this);
               bullet.m_6034_(this.m_20185_(), this.m_20186_() - 2.0 + this.f_19796_.m_188500_() * 4.0, this.m_20189_());
               bullet.setUp(30, cx, 0.0, cz, tx - 7.0 * cx + (double)i * cz, ty, tz - 7.0 * cz + (double)i * cx);
               this.f_19853_.m_7967_(bullet);
            }
         }
      }
   }

   private boolean teleport(double p_32544_, double p_32545_, double p_32546_) {
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos(p_32544_, p_32545_, p_32546_);

      while (blockpos$mutableblockpos.m_123342_() > this.f_19853_.m_141937_() && !this.f_19853_.m_8055_(blockpos$mutableblockpos).m_60767_().m_76334_()) {
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
      }

      BlockState blockstate = this.f_19853_.m_8055_(blockpos$mutableblockpos);
      boolean flag = blockstate.m_60767_().m_76334_();
      if (flag) {
         EnderEntity event = ForgeEventFactory.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
         if (event.isCanceled()) {
            return false;
         } else {
            Vec3 vec3 = this.m_20182_();
            boolean flag2 = this.ProperTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2) {
               this.f_19853_.m_214171_(GameEvent.f_238175_, vec3, Context.m_223717_(this));
               if (!this.m_20067_()) {
                  this.m_5496_(SoundEvents.f_12418_, 1.0F, 1.0F);
               }
            }

            return flag2;
         }
      } else {
         return false;
      }
   }

   public boolean ProperTeleport(double p_20985_, double p_20986_, double p_20987_, boolean p_20988_) {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      double d3 = p_20986_;
      boolean flag = false;
      BlockPos blockpos = new BlockPos(p_20985_, p_20986_, p_20987_);
      Level level = this.f_19853_;
      if (level.m_46805_(blockpos)) {
         boolean flag1 = false;

         while (!flag1 && blockpos.m_123342_() > level.m_141937_()) {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = level.m_8055_(blockpos1);
            if (blockstate.m_60767_().m_76334_()) {
               flag1 = true;
            } else {
               d3--;
               blockpos = blockpos1;
            }
         }

         if (flag1) {
            this.m_6021_(p_20985_, d3, p_20987_);
            if (level.m_45786_(this) && !level.m_46855_(this.m_20191_())) {
               flag = true;
            }
         }
      }

      if (!flag) {
         this.m_6021_(d0, d1, d2);
         return false;
      } else {
         if (p_20988_) {
            level.m_7605_(this, (byte)46);
         }

         this.m_21573_().m_26573_();
         return true;
      }
   }

   public boolean m_6063_() {
      return false;
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_20256_(itementity.m_20184_().m_82542_(0.0, 3.5, 0.0));
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_ENDER_GUARDIAN) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.ENDERGUARDIANHURT.get();
   }

   protected SoundEvent m_7515_() {
      return this.getIsHelmetless() ? SoundEvents.f_12407_ : super.m_7515_();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.ENDERGUARDIANDEATH.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.ENDERGUARDIAN_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   class HugmeGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      private final float sensing;
      private final int teleport;
      public double prevX;
      public double prevZ;
      private int newX;
      private int newZ;

      public HugmeGoal(Ender_Guardian_Entity entity, Animation animation, float sensing, int teleport) {
         super(entity, animation);
         this.sensing = sensing;
         this.teleport = teleport;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         super.m_8056_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.prevX = target.m_20185_();
            this.prevZ = target.m_20189_();
         }
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (Ender_Guardian_Entity.this.getAnimationTick() < 40 && target != null) {
            Ender_Guardian_Entity.this.m_21391_(target, 30.0F, 30.0F);
            Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
         }

         if (this.entity.getAnimationTick() == this.teleport - 6 && target != null) {
            double x = target.m_20185_();
            double z = target.m_20189_();
            double vx = (x - this.prevX) / (double)this.teleport;
            double vz = (z - this.prevZ) / (double)this.teleport;
            this.newX = Mth.m_14107_(x + vx * (double)this.sensing);
            this.newZ = Mth.m_14107_(z + vz * (double)this.sensing);
            this.entity.setTeleportPos(new BlockPos((double)this.newX, target.m_20186_(), (double)this.newZ));
         }

         if (this.entity.getAnimationTick() == this.teleport && target != null && this.entity.getTeleportPos().isPresent()) {
            this.entity
               .teleport((double)this.entity.getTeleportPos().get().m_123341_(), target.m_20186_(), (double)this.entity.getTeleportPos().get().m_123343_());
         }
      }
   }

   class PunchAttackGoal extends AnimationGoal<Ender_Guardian_Entity> {
      public PunchAttackGoal(Ender_Guardian_Entity entity) {
         super(entity);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Ender_Guardian_Entity.GUARDIAN_LEFT_ATTACK
            || animation == Ender_Guardian_Entity.GUARDIAN_RIGHT_ATTACK
            || animation == Ender_Guardian_Entity.GUARDIAN_LEFT_STRONG_ATTACK
            || animation == Ender_Guardian_Entity.GUARDIAN_RIGHT_STRONG_ATTACK;
      }

      public void m_8037_() {
         Ender_Guardian_Entity.this.m_20334_(0.0, Ender_Guardian_Entity.this.m_20184_().f_82480_, 0.0);
         LivingEntity target = Ender_Guardian_Entity.this.m_5448_();
         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_LEFT_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 17 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 27 || target == null)) {
               Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
            } else {
               Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }

            if (Ender_Guardian_Entity.this.getAnimationTick() == 24
               && target != null
               && Ender_Guardian_Entity.this.f_19796_.m_188503_(2) == 0
               && Ender_Guardian_Entity.this.m_20270_(target) <= 4.0F) {
               AnimationHandler.INSTANCE.sendAnimationMessage(Ender_Guardian_Entity.this, Ender_Guardian_Entity.GUARDIAN_LEFT_SWING);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_RIGHT_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 22 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 32 || target == null)) {
               Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
            } else {
               Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }

            if (Ender_Guardian_Entity.this.getAnimationTick() == 26
               && target != null
               && Ender_Guardian_Entity.this.f_19796_.m_188503_(2) == 0
               && Ender_Guardian_Entity.this.m_20270_(target) <= 4.0F) {
               AnimationHandler.INSTANCE.sendAnimationMessage(Ender_Guardian_Entity.this, Ender_Guardian_Entity.GUARDIAN_RIGHT_SWING);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_LEFT_STRONG_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 34 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 44 || target == null)) {
               Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
            } else {
               Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_RIGHT_STRONG_ATTACK) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 29 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 39 || target == null)) {
               Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
            } else {
               Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }
      }
   }

   class RageUppercut extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public RageUppercut(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ender_Guardian_Entity.this.m_5448_();
         if ((Ender_Guardian_Entity.this.getAnimationTick() >= 29 || target == null)
            && (Ender_Guardian_Entity.this.getAnimationTick() <= 54 || Ender_Guardian_Entity.this.getAnimationTick() >= 84 || target == null)
            && (Ender_Guardian_Entity.this.getAnimationTick() <= 104 || target == null)) {
            Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
         } else {
            Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_20883_);
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() == 26) {
            float f1 = (float)Math.cos(Math.toRadians((double)(Ender_Guardian_Entity.this.m_146908_() + 90.0F)));
            float f2 = (float)Math.sin(Math.toRadians((double)(Ender_Guardian_Entity.this.m_146908_() + 90.0F)));
            if (target != null) {
               float r = Ender_Guardian_Entity.this.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 7.0F);
               Ender_Guardian_Entity.this.m_5997_((double)f1 * 0.3 * (double)r, 0.0, (double)f2 * 0.3 * (double)r);
            } else {
               Ender_Guardian_Entity.this.m_5997_((double)f1 * 2.0, 0.0, (double)f2 * 2.0);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() > 32 || Ender_Guardian_Entity.this.getAnimationTick() < 26) {
            Ender_Guardian_Entity.this.m_20334_(0.0, Ender_Guardian_Entity.this.m_20184_().f_82480_, 0.0);
         }

         Ender_Guardian_Entity.this.Bulletpattern();
      }
   }

   static class RocketPunchGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public RocketPunchGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 24 && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == 24 && target != null) {
            this.entity.m_20334_((target.m_20185_() - this.entity.m_20185_()) * 0.3F, 0.0, (target.m_20189_() - this.entity.m_20189_()) * 0.3F);
         }
      }
   }

   class StompAttackGoal extends AnimationGoal<Ender_Guardian_Entity> {
      public StompAttackGoal(Ender_Guardian_Entity entity) {
         super(entity);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Ender_Guardian_Entity.GUARDIAN_STOMP || animation == Ender_Guardian_Entity.GUARDIAN_RAGE_STOMP;
      }

      public void m_8037_() {
         Ender_Guardian_Entity.this.m_20334_(0.0, Ender_Guardian_Entity.this.m_20184_().f_82480_, 0.0);
         LivingEntity target = Ender_Guardian_Entity.this.m_5448_();
         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_STOMP) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 32 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 42 || target == null)) {
               Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
            } else {
               Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimation() == Ender_Guardian_Entity.GUARDIAN_RAGE_STOMP) {
            if ((Ender_Guardian_Entity.this.getAnimationTick() >= 32 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 42 || Ender_Guardian_Entity.this.getAnimationTick() >= 53 || target == null)
               && (Ender_Guardian_Entity.this.getAnimationTick() <= 58 || target == null)) {
               Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
            } else {
               Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }
      }
   }

   static class TeleportStrikeGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public TeleportStrikeGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 40) {
            this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         }

         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 40) {
               float y = this.entity.getIsHelmetless() ? 8.0F : 4.0F;
               this.entity.m_6021_(target.m_20185_(), target.m_20186_() + (double)y, target.m_20189_());
            }
         }

         if (this.entity.getAnimationTick() > 48 && this.entity.m_20096_()) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, Ender_Guardian_Entity.GUARDIAN_AIR_STRIKE2);
         }
      }
   }

   class UppercutAndBulletGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public UppercutAndBulletGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ender_Guardian_Entity.this.m_5448_();
         if ((Ender_Guardian_Entity.this.getAnimationTick() >= 29 || target == null) && (Ender_Guardian_Entity.this.getAnimationTick() <= 54 || target == null)
            )
          {
            Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_19859_);
         } else {
            Ender_Guardian_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ender_Guardian_Entity.this.m_146922_(Ender_Guardian_Entity.this.f_20883_);
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() == 26) {
            float f1 = (float)Math.cos(Math.toRadians((double)(Ender_Guardian_Entity.this.m_146908_() + 90.0F)));
            float f2 = (float)Math.sin(Math.toRadians((double)(Ender_Guardian_Entity.this.m_146908_() + 90.0F)));
            if (target != null) {
               float r = Ender_Guardian_Entity.this.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 7.0F);
               Ender_Guardian_Entity.this.m_5997_((double)f1 * 0.3 * (double)r, 0.0, (double)f2 * 0.3 * (double)r);
            } else {
               Ender_Guardian_Entity.this.m_5997_((double)f1 * 2.0, 0.0, (double)f2 * 2.0);
            }
         }

         if (Ender_Guardian_Entity.this.getAnimationTick() > 32 || Ender_Guardian_Entity.this.getAnimationTick() < 26) {
            Ender_Guardian_Entity.this.m_20334_(0.0, Ender_Guardian_Entity.this.m_20184_().f_82480_, 0.0);
         }

         Ender_Guardian_Entity.this.Bulletpattern();
      }
   }

   static class VoidVortexGoal extends SimpleAnimationGoal<Ender_Guardian_Entity> {
      public VoidVortexGoal(Ender_Guardian_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 26 && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == 26 && target != null) {
            double tx = target.m_20185_();
            double ty = target.m_20186_();
            double tz = target.m_20189_();
            double minY = Math.min(ty, this.entity.m_20186_());
            double maxY = Math.max(ty, this.entity.m_20186_()) + 1.0;
            float angle = (float)Mth.m_14136_(tz - this.entity.m_20189_(), tx - this.entity.m_20185_());
            this.entity.spawnVortex(tx, tz, minY, maxY, angle);
         }

         if (this.entity.getAnimationTick() == 37 && target != null && this.entity.m_20280_(target) >= 25.0) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, Ender_Guardian_Entity.GUARDIAN_ROCKETPUNCH);
         }
      }
   }
}
