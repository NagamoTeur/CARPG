package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus;

import com.github.L_Ender.cataclysm.blocks.Cursed_Tombstone_Block;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

public class Maledictus_Entity extends IABoss_monster implements IHoldEntity {
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState swingAnimationState = new AnimationState();
   public AnimationState shotAnimationState = new AnimationState();
   public AnimationState flyingshotAnimationState = new AnimationState();
   public AnimationState fallloopAnimationState = new AnimationState();
   public AnimationState falllendAnimationState = new AnimationState();
   public AnimationState masseffectAnimationState = new AnimationState();
   public AnimationState flyingsmash1AnimationState = new AnimationState();
   public AnimationState flyingsmash2AnimationState = new AnimationState();
   public AnimationState BackstepAnimationState = new AnimationState();
   public AnimationState BackstepRushAnimationState = new AnimationState();
   public AnimationState BackstepRushNobackstepAnimationState = new AnimationState();
   public AnimationState dash1AnimationState = new AnimationState();
   public AnimationState dash1NobackstepAnmationState = new AnimationState();
   public AnimationState dash2AnmationState = new AnimationState();
   public AnimationState dash2NobackstepAnmationState = new AnimationState();
   public AnimationState dash3AnimationState = new AnimationState();
   public AnimationState spinslashesAnimationState = new AnimationState();
   public AnimationState combofirstAnimationState = new AnimationState();
   public AnimationState combofirstendAnimationState = new AnimationState();
   public AnimationState combosecondAnimationState = new AnimationState();
   public AnimationState uppercutleftAnimationState = new AnimationState();
   public AnimationState uppercutrightAnimationState = new AnimationState();
   public AnimationState flyinghalberdsmash1AnimationState = new AnimationState();
   public AnimationState flyinghalberdsmash2AnimationState = new AnimationState();
   public AnimationState radagonAnimationState = new AnimationState();
   public AnimationState halberdswingAnimationState = new AnimationState();
   public AnimationState grab_startAnimationState = new AnimationState();
   public AnimationState grab_loopAnimationState = new AnimationState();
   public AnimationState grab_failAnimationState = new AnimationState();
   public AnimationState grab_successAnimationState = new AnimationState();
   public AnimationState grab_success_loopAnimationState = new AnimationState();
   public AnimationState grab_success_endAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   private int reducedDamageTicks;
   private boolean combo;
   private boolean grab;
   private int rageTicks;
   private int masseffect_cooldown = 0;
   private int flyattack_cooldown = 0;
   private int charge_cooldown = 0;
   private int uppercut_cooldown = 0;
   private int spin_cooldown = 0;
   private int radagon_cooldown = 0;
   private int spear_swing_cooldown = 0;
   private int grab_cooldown = 0;
   public static final int MASSEFFECT_COOLDOWN = 150;
   public static final int FLYATTACK_COOLDOWN = 100;
   public static final int CHARGE_COOLDOWN = 80;
   public static final int UPPERCUT_COOLDOWN = 80;
   public static final int SPIN_COOLDOWN = 100;
   public static final int NATURE_HEAL_COOLDOWN = 200;
   public static final int RADAGON_COOLDOWN = 250;
   public static final int SPEAR_SWING_COOLDOWN = 100;
   public static final int GRAB_COOLDOWN = 300;
   private int timeWithoutTarget;
   private int destroyBlocksTick;
   private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.m_135353_(Maledictus_Entity.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Integer> RAGE = SynchedEntityData.m_135353_(Maledictus_Entity.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Integer> WEAPON = SynchedEntityData.m_135353_(Maledictus_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<BlockPos> TOMBSTONE_POS = SynchedEntityData.m_135353_(Maledictus_Entity.class, EntityDataSerializers.f_135038_);
   private static final EntityDataAccessor<Direction> TOMBSTONE_DIRECTION = SynchedEntityData.m_135353_(
      Maledictus_Entity.class, EntityDataSerializers.f_135040_
   );
   private final CMBossInfoServer bossEvent1 = new CMBossInfoServer(this.m_5446_(), BossBarColor.GREEN, true, 9);
   private final CMBossInfoServer bossEvent2 = new CMBossInfoServer(Component.m_237115_("entity.cataclysm.rage_meter"), BossBarColor.GREEN, false, 10);

   public Maledictus_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 500;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.MaledictusHealthMultiplier, CMConfig.MaledictusDamageMultiplier);
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
      this.f_21346_.m_25352_(2, new EntityAINearestTarget3D(this, Player.class, true));
      this.f_21345_.m_25352_(3, new InternalMoveGoal(this, false, 1.0));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.MaledictusSpinSlashes(this, 0, 18, 0, 68, 15, 23, 29, 15, 29, 6.5F, 0, 0, 24.0F));
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 27, 0, 50, 22, 3.5F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Maledictus_Entity.this.m_217043_().m_188501_() * 100.0F < 32.0F && Maledictus_Entity.this.spear_swing_cooldown <= 0;
         }

         @Override
         public void m_8056_() {
            super.m_8056_();
            Maledictus_Entity.this.setWeapon(2);
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Maledictus_Entity.this.spear_swing_cooldown = 100;
            Maledictus_Entity.this.setWeapon(0);
         }
      });
      this.f_21345_.m_25352_(1, new Maledictus_Entity.MaledictusGrabGoal(this, 0, 28, 29, 26, 24, 9.0F, 3, 3, 25.0F));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.MaledictusGrabState(this, 29, 29, 30, 15, 0, 3, 3));
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 30, 30, 0, 30, 0) {
         @Override
         public void m_8041_() {
            super.m_8041_();
            Maledictus_Entity.this.setWeapon(0);
         }
      });
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusSuccessState(this, 31, 31, 32, 60, 0, 30, 3, 3, 2.0));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.MaledictusfallingState(this, 32, 32, 33, 100, 0, 3, 3));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusfallingState(this, 33, 33, 0, 35, 0, 3, 0));
      this.f_21345_
         .m_25352_(
            1,
            new InternalAttackGoal(this, 0, 19, 20, 27, 10, 6.0F) {
               @Override
               public boolean m_8036_() {
                  LivingEntity target = this.entity.m_5448_();
                  return super.m_8036_()
                     && Maledictus_Entity.this.m_217043_().m_188501_() * 100.0F < 24.0F
                     && target != null
                     && (double)this.entity.m_20270_(target) >= 1.75;
               }

               @Override
               public boolean m_8045_() {
                  return super.m_8045_() && !Maledictus_Entity.this.combo;
               }

               @Override
               public void m_8037_() {
                  super.m_8037_();
                  if (this.entity.attackTicks == 8) {
                     float f1 = (float)Math.cos(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
                     float f2 = (float)Math.sin(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
                     this.entity.m_5997_((double)f1 * 1.35, 0.0, (double)f2 * 1.35);
                  }
               }

               @Override
               public void m_8041_() {
                  if (Maledictus_Entity.this.combo) {
                     this.entity.setAttackState(21);
                     Maledictus_Entity.this.combo = false;
                  } else {
                     super.m_8041_();
                  }
               }
            }
         );
      this.f_21345_.m_25352_(1, new Maledictus_Entity.Uppercut(this, 0, 22, 0, 60, 16, 3.0F, 32.0F));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.Uppercut(this, 0, 23, 0, 60, 16, 3.0F, 32.0F));
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 20, 20, 0, 13, 0));
      this.f_21345_.m_25352_(0, new InternalStateGoal(this, 21, 21, 0, 45, 8) {
         @Override
         public void m_8037_() {
            super.m_8037_();
            if (this.entity.attackTicks == 8) {
               float f1 = (float)Math.cos(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
               float f2 = (float)Math.sin(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
               this.entity.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
            }
         }
      });
      this.f_21345_.m_25352_(1, new Maledictus_Entity.Maledictus_Swing(this, 0, 1, 0, 44, 25, 6.5F, 35.0F, 20.0F));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.Maledictus_Bow(this, 0, 2, 0, 45, 29, 8.0F, 35.0F, 29, 16.0F));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.Maledictus_Flying_Bow(this, 0, 3, 4, 68, 50, 40.0F, 50, 35.0F));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.MaledictusfallingState(this, 4, 4, 5, 100, 100, 1, 0));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusfallingState(this, 5, 5, 0, 27, 0, 0, 0));
      this.f_21345_
         .m_25352_(
            1,
            new InternalAttackGoal(this, 0, 7, 0, 66, 28, 4.5F) {
               @Override
               public boolean m_8036_() {
                  return super.m_8036_()
                     && Maledictus_Entity.this.m_217043_().m_188501_() * 100.0F < 7.0F * (float)Maledictus_Entity.this.getRageMeter()
                     && Maledictus_Entity.this.masseffect_cooldown <= 0;
               }

               @Override
               public void m_8037_() {
                  super.m_8037_();
                  this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
               }

               @Override
               public void m_8041_() {
                  super.m_8041_();
                  Maledictus_Entity.this.masseffect_cooldown = 150;
               }
            }
         );
      this.f_21345_.m_25352_(1, new Maledictus_Entity.Maledictus_Flying_Smash(this, 0, 8, 9, 100, 100, 30.0F, 56, 0, 0, 17.0F, 0.125));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusfallingState(this, 9, 9, 0, 27, 0, 0, 0));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.Maledictus_Flying_Smash(this, 0, 24, 25, 100, 100, 30.0F, 51, 2, 2, 17.0F, 0.145));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusfallingState(this, 25, 25, 0, 75, 0, 2, 0));
      this.f_21345_
         .m_25352_(
            1,
            new InternalAttackGoal(this, 0, 26, 0, 73, 43, 13.0F) {
               @Override
               public boolean m_8036_() {
                  if (Maledictus_Entity.this.isQuarterHealth()) {
                     return super.m_8036_() && Maledictus_Entity.this.m_217043_().m_188501_() * 100.0F < 35.0F && Maledictus_Entity.this.radagon_cooldown <= 0;
                  } else {
                     return !Maledictus_Entity.this.isHalfHealth()
                        ? false
                        : super.m_8036_() && Maledictus_Entity.this.m_217043_().m_188501_() * 100.0F < 25.0F && Maledictus_Entity.this.radagon_cooldown <= 0;
                  }
               }

               @Override
               public void m_8056_() {
                  super.m_8056_();
                  Maledictus_Entity.this.setWeapon(2);
               }

               @Override
               public void m_8041_() {
                  super.m_8041_();
                  Maledictus_Entity.this.radagon_cooldown = 250;
                  Maledictus_Entity.this.setWeapon(0);
               }
            }
         );
      this.f_21345_.m_25352_(1, new InternalAttackGoal(this, 0, 10, 0, 15, 15, 4.5F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Maledictus_Entity.this.m_217043_().m_188501_() * 100.0F < 18.0F && Maledictus_Entity.this.charge_cooldown <= 0;
         }

         @Override
         public void m_8056_() {
            super.m_8056_();
            float speed = -1.7F;
            float dodgeYaw = (float)Math.toRadians((double)(Maledictus_Entity.this.m_146908_() + 90.0F));
            Vec3 m = Maledictus_Entity.this.m_20184_().m_82520_((double)speed * Math.cos((double)dodgeYaw), 0.0, (double)speed * Math.sin((double)dodgeYaw));
            Maledictus_Entity.this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_JUMP.get(), 1.0F, 1.0F);
            Maledictus_Entity.this.m_20334_(m.f_82479_, 0.4, m.f_82481_);
         }

         @Override
         public void m_8041_() {
            if (Maledictus_Entity.this.isHalfHealth()) {
               this.entity.setAttackState(12);
            } else {
               this.entity.setAttackState(11);
            }
         }
      });
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusChargeState(this, 11, 11, 0, 65, 18, 31, 24, 33, 2, 0, 0));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusChargeState(this, 12, 12, 0, 55, 18, 31, 24, 0, 2, 0, 1));
      this.f_21345_.m_25352_(1, new Maledictus_Entity.MaledictusChargeGoal(this, 0, 19, 30, 24, 4.5F, 13.0F, 2, 0, 18.0F));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusChargeState(this, 15, 15, 0, 55, 10, 25, 16, 27, 2, 0, 2));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusChargeState(this, 16, 16, 0, 40, 10, 25, 16, 0, 2, 0, 2));
      this.f_21345_.m_25352_(0, new Maledictus_Entity.MaledictusChargeState(this, 17, 17, 0, 58, 10, 28, 16, 30, 2, 0, 3));
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public static Builder maledictus() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 50.0)
         .m_22268_(Attributes.f_22279_, 0.33F)
         .m_22268_(Attributes.f_22281_, 13.0)
         .m_22268_(Attributes.f_22276_, 420.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
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
      if (range > CMConfig.MaledictusLongRangelimit * CMConfig.MaledictusLongRangelimit && !source.m_19378_()) {
         return false;
      } else if ((this.getAttackState() == 31 || this.getAttackState() == 32 || this.getAttackState() == 33) && !source.m_19378_()) {
         return false;
      } else {
         if (this.reducedDamageTicks > 0) {
            float reductionFactor = 1.0F - (float)this.reducedDamageTicks / 30.0F;
            damage *= reductionFactor;
         }

         if (this.destroyBlocksTick <= 0) {
            this.destroyBlocksTick = 20;
         }

         boolean flag = super.m_6469_(source, damage);
         if (flag) {
            this.reducedDamageTicks = 30;
         }

         return flag;
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.MaledictusDamageCap;
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "swing") {
         return this.swingAnimationState;
      } else if (input == "shoot") {
         return this.shotAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else if (input == "flying_shoot") {
         return this.flyingshotAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "fall_loop") {
         return this.fallloopAnimationState;
      } else if (input == "fall_end") {
         return this.falllendAnimationState;
      } else if (input == "mass_effect") {
         return this.masseffectAnimationState;
      } else if (input == "flying_smash_1") {
         return this.flyingsmash1AnimationState;
      } else if (input == "flying_smash_2") {
         return this.flyingsmash2AnimationState;
      } else if (input == "back_step") {
         return this.BackstepAnimationState;
      } else if (input == "back_step_dash") {
         return this.BackstepRushAnimationState;
      } else if (input == "back_step_dash_no_back_step") {
         return this.BackstepRushNobackstepAnimationState;
      } else if (input == "dash") {
         return this.dash1AnimationState;
      } else if (input == "dash_no_back_step") {
         return this.dash1NobackstepAnmationState;
      } else if (input == "dash2") {
         return this.dash2AnmationState;
      } else if (input == "dash2_no_back_step") {
         return this.dash2NobackstepAnmationState;
      } else if (input == "dash3") {
         return this.dash3AnimationState;
      } else if (input == "spin_slashes") {
         return this.spinslashesAnimationState;
      } else if (input == "combo_first") {
         return this.combofirstAnimationState;
      } else if (input == "combo_first_end") {
         return this.combofirstendAnimationState;
      } else if (input == "combo_second") {
         return this.combosecondAnimationState;
      } else if (input == "uppercut_right") {
         return this.uppercutrightAnimationState;
      } else if (input == "uppercut_left") {
         return this.uppercutleftAnimationState;
      } else if (input == "flying_halberd_smash_1") {
         return this.flyinghalberdsmash1AnimationState;
      } else if (input == "flying_halberd_smash_2") {
         return this.flyinghalberdsmash2AnimationState;
      } else if (input == "radagon") {
         return this.radagonAnimationState;
      } else if (input == "halberd_swing") {
         return this.halberdswingAnimationState;
      } else if (input == "grab_start") {
         return this.grab_startAnimationState;
      } else if (input == "grab_loop") {
         return this.grab_loopAnimationState;
      } else if (input == "grab_fail") {
         return this.grab_failAnimationState;
      } else if (input == "grab_success") {
         return this.grab_successAnimationState;
      } else if (input == "grab_success_loop") {
         return this.grab_success_loopAnimationState;
      } else {
         return input == "grab_success_end" ? this.grab_success_endAnimationState : new AnimationState();
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(TOMBSTONE_POS, BlockPos.f_121853_);
      this.f_19804_.m_135372_(TOMBSTONE_DIRECTION, Direction.NORTH);
      this.f_19804_.m_135372_(WEAPON, 0);
      this.f_19804_.m_135372_(FLYING, false);
      this.f_19804_.m_135372_(RAGE, 0);
   }

   public int getWeapon() {
      return (Integer)this.f_19804_.m_135370_(WEAPON);
   }

   public void setWeapon(int weapon) {
      this.f_19804_.m_135381_(WEAPON, weapon);
   }

   public boolean isFlying() {
      return (Boolean)this.f_19804_.m_135370_(FLYING);
   }

   public void setFlying(boolean flying) {
      this.f_19804_.m_135381_(FLYING, flying);
   }

   BlockPos getTombstonePos() {
      return (BlockPos)this.f_19804_.m_135370_(TOMBSTONE_POS);
   }

   public void setTombstonePos(BlockPos p_30220_) {
      this.f_19804_.m_135381_(TOMBSTONE_POS, p_30220_);
   }

   public Direction getTombstoneDirection() {
      return (Direction)this.f_19804_.m_135370_(TOMBSTONE_DIRECTION);
   }

   public void setTombstoneDirection(Direction p_30220_) {
      this.f_19804_.m_135381_(TOMBSTONE_DIRECTION, p_30220_);
   }

   public int getRageMeter() {
      return (Integer)this.f_19804_.m_135370_(RAGE);
   }

   public void setRageMeter(int Rage) {
      this.f_19804_.m_135381_(RAGE, Rage);
   }

   public void m_7023_(Vec3 travelVector) {
      super.m_7023_(travelVector);
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   public boolean canRiderInteract() {
      return true;
   }

   public void m_7332_(Entity p_20312_) {
      this.m_19956_(p_20312_, Entity::m_6034_);
   }

   public void m_19956_(Entity passenger, MoveFunction moveFunc) {
      float f1 = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f2 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      double px = this.m_20185_() + 0.5 * vecX + (double)f1 * -0.6;
      double pz = this.m_20189_() + 0.5 * vecZ + (double)f2 * -0.6;
      double y = this.m_20186_() + passenger.m_6049_() + 2.0;
      if (this.m_20363_(passenger) && this.getAttackState() == 33) {
         y = this.m_20186_() - (double)(0.2F * (float)Mth.m_14045_(0, 0, 23));
         if (this.attackTicks == 23) {
            passenger.m_8127_();
         }
      }

      moveFunc.m_20372_(passenger, px, y, pz);
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.swingAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.shotAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.flyingshotAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.fallloopAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.falllendAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.deathAnimationState.m_216982_(this.f_19797_);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.masseffectAnimationState.m_216982_(this.f_19797_);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.flyingsmash1AnimationState.m_216982_(this.f_19797_);
               break;
            case 9:
               this.stopAllAnimationStates();
               this.flyingsmash2AnimationState.m_216982_(this.f_19797_);
               break;
            case 10:
               this.stopAllAnimationStates();
               this.BackstepAnimationState.m_216982_(this.f_19797_);
               break;
            case 11:
               this.stopAllAnimationStates();
               this.BackstepRushAnimationState.m_216982_(this.f_19797_);
               break;
            case 12:
               this.stopAllAnimationStates();
               this.BackstepRushNobackstepAnimationState.m_216982_(this.f_19797_);
               break;
            case 13:
               this.stopAllAnimationStates();
               this.dash1AnimationState.m_216982_(this.f_19797_);
               break;
            case 14:
               this.stopAllAnimationStates();
               this.dash1NobackstepAnmationState.m_216982_(this.f_19797_);
               break;
            case 15:
               this.stopAllAnimationStates();
               this.dash2AnmationState.m_216982_(this.f_19797_);
               break;
            case 16:
               this.stopAllAnimationStates();
               this.dash2NobackstepAnmationState.m_216982_(this.f_19797_);
               break;
            case 17:
               this.stopAllAnimationStates();
               this.dash3AnimationState.m_216982_(this.f_19797_);
               break;
            case 18:
               this.stopAllAnimationStates();
               this.spinslashesAnimationState.m_216982_(this.f_19797_);
               break;
            case 19:
               this.stopAllAnimationStates();
               this.combofirstAnimationState.m_216982_(this.f_19797_);
               break;
            case 20:
               this.stopAllAnimationStates();
               this.combofirstendAnimationState.m_216982_(this.f_19797_);
               break;
            case 21:
               this.stopAllAnimationStates();
               this.combosecondAnimationState.m_216982_(this.f_19797_);
               break;
            case 22:
               this.stopAllAnimationStates();
               this.uppercutrightAnimationState.m_216982_(this.f_19797_);
               break;
            case 23:
               this.stopAllAnimationStates();
               this.uppercutleftAnimationState.m_216982_(this.f_19797_);
               break;
            case 24:
               this.stopAllAnimationStates();
               this.flyinghalberdsmash1AnimationState.m_216982_(this.f_19797_);
               break;
            case 25:
               this.stopAllAnimationStates();
               this.flyinghalberdsmash2AnimationState.m_216982_(this.f_19797_);
               break;
            case 26:
               this.stopAllAnimationStates();
               this.radagonAnimationState.m_216982_(this.f_19797_);
               break;
            case 27:
               this.stopAllAnimationStates();
               this.halberdswingAnimationState.m_216982_(this.f_19797_);
               break;
            case 28:
               this.stopAllAnimationStates();
               this.grab_startAnimationState.m_216982_(this.f_19797_);
               break;
            case 29:
               this.stopAllAnimationStates();
               this.grab_loopAnimationState.m_216982_(this.f_19797_);
               break;
            case 30:
               this.stopAllAnimationStates();
               this.grab_failAnimationState.m_216982_(this.f_19797_);
               break;
            case 31:
               this.stopAllAnimationStates();
               this.grab_successAnimationState.m_216982_(this.f_19797_);
               break;
            case 32:
               this.stopAllAnimationStates();
               this.grab_success_loopAnimationState.m_216982_(this.f_19797_);
               break;
            case 33:
               this.stopAllAnimationStates();
               this.grab_success_endAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.swingAnimationState.m_216973_();
      this.shotAnimationState.m_216973_();
      this.deathAnimationState.m_216973_();
      this.flyingshotAnimationState.m_216973_();
      this.fallloopAnimationState.m_216973_();
      this.falllendAnimationState.m_216973_();
      this.masseffectAnimationState.m_216973_();
      this.flyingsmash1AnimationState.m_216973_();
      this.flyingsmash2AnimationState.m_216973_();
      this.BackstepAnimationState.m_216973_();
      this.BackstepRushAnimationState.m_216973_();
      this.BackstepRushNobackstepAnimationState.m_216973_();
      this.dash1AnimationState.m_216973_();
      this.dash1NobackstepAnmationState.m_216973_();
      this.dash2AnmationState.m_216973_();
      this.dash2NobackstepAnmationState.m_216973_();
      this.dash3AnimationState.m_216973_();
      this.spinslashesAnimationState.m_216973_();
      this.combofirstAnimationState.m_216973_();
      this.combofirstendAnimationState.m_216973_();
      this.combosecondAnimationState.m_216973_();
      this.uppercutrightAnimationState.m_216973_();
      this.uppercutleftAnimationState.m_216973_();
      this.flyinghalberdsmash1AnimationState.m_216973_();
      this.flyinghalberdsmash2AnimationState.m_216973_();
      this.radagonAnimationState.m_216973_();
      this.halberdswingAnimationState.m_216973_();
      this.grab_startAnimationState.m_216973_();
      this.grab_loopAnimationState.m_216973_();
      this.grab_failAnimationState.m_216973_();
      this.grab_successAnimationState.m_216973_();
      this.grab_success_loopAnimationState.m_216973_();
      this.grab_success_endAnimationState.m_216973_();
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(6);
      this.setFlying(false);
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (!this.f_19853_.f_46443_ && this.getTombstonePos() != BlockPos.f_121853_) {
         BlockState block = ((Block)ModBlocks.CURSED_TOMBSTONE.get()).m_49966_();
         if (this.getTombstoneDirection() != Direction.UP && this.getTombstoneDirection() != Direction.DOWN) {
            this.f_19853_.m_46597_(this.getTombstonePos(), (BlockState)block.m_61124_(Cursed_Tombstone_Block.FACING, this.getTombstoneDirection()));
         } else {
            this.f_19853_.m_46597_(this.getTombstonePos(), (BlockState)block.m_61124_(Cursed_Tombstone_Block.FACING, Direction.NORTH));
         }
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("TombstonePosX", this.getTombstonePos().m_123341_());
      compound.m_128405_("TombstonePosY", this.getTombstonePos().m_123342_());
      compound.m_128405_("TombstonePosZ", this.getTombstonePos().m_123343_());
      compound.m_128344_("Tombstone_Direction", (byte)this.getTombstoneDirection().m_122411_());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      int i = compound.m_128451_("TombstonePosX");
      int j = compound.m_128451_("TombstonePosY");
      int k = compound.m_128451_("TombstonePosZ");
      this.setTombstoneDirection(Direction.m_122376_(compound.m_128445_("Tombstone_Direction")));
      this.setTombstonePos(new BlockPos(i, j, k));
   }

   @Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_30153_, DifficultyInstance p_30154_, MobSpawnType p_30155_, @Nullable SpawnGroupData p_30156_, @Nullable CompoundTag p_30157_
   ) {
      this.setTombstonePos(this.m_20183_());
      this.setTombstoneDirection(Direction.SOUTH);
      return super.m_6518_(p_30153_, p_30154_, p_30155_, p_30156_, p_30157_);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (!this.m_20197_().isEmpty()
         && ((Entity)this.m_20197_().get(0)).m_6144_()
         && (this.getAttackState() == 31 || this.getAttackState() == 32 || this.getAttackState() == 33)) {
         ((Entity)this.m_20197_().get(0)).m_20260_(false);
      }

      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, !this.isMoving() && this.getAttackState() == 0, this.f_19797_);
      } else {
         if (this.reducedDamageTicks > 0) {
            this.reducedDamageTicks--;
         }

         if (this.rageTicks > 0) {
            this.rageTicks--;
         } else if (this.getRageMeter() > 0) {
            this.setRageMeter(this.getRageMeter() - 1);
            this.rageTicks = 200;
         }
      }

      this.bossEvent1.m_142711_(this.m_21223_() / this.m_21233_());
      this.bossEvent2.m_142711_((float)this.getRageMeter() / 5.0F);
      if (this.masseffect_cooldown > 0) {
         this.masseffect_cooldown--;
      }

      if (this.flyattack_cooldown > 0) {
         this.flyattack_cooldown--;
      }

      if (this.charge_cooldown > 0) {
         this.charge_cooldown--;
      }

      if (this.uppercut_cooldown > 0) {
         this.uppercut_cooldown--;
      }

      if (this.spin_cooldown > 0) {
         this.spin_cooldown--;
      }

      if (this.radagon_cooldown > 0) {
         this.radagon_cooldown--;
      }

      if (this.spear_swing_cooldown > 0) {
         this.spear_swing_cooldown--;
      }

      if (this.grab_cooldown > 0) {
         this.grab_cooldown--;
      }

      LivingEntity target = this.m_5448_();
      if (!this.f_19853_.f_46443_) {
         if (this.isFlying()) {
            this.m_20242_(!this.f_19861_);
         } else {
            this.m_20242_(false);
         }

         if (this.timeWithoutTarget > 0) {
            this.timeWithoutTarget--;
         }

         if (target != null) {
            this.timeWithoutTarget = 200;
         }

         if (this.getAttackState() == 0 && this.timeWithoutTarget <= 0 && !this.m_21525_() && CMConfig.MaledictusNatureHealing > 0.0 && this.f_19797_ % 20 == 0
            )
          {
            this.m_5634_((float)CMConfig.MaledictusNatureHealing);
         }
      }

      this.blockbreak();
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

   public boolean isHalfHealth() {
      float healthAmount = this.m_21223_() / this.m_21233_();
      return healthAmount <= 0.5F;
   }

   public boolean isQuarterHealth() {
      float healthAmount = this.m_21223_() / this.m_21233_();
      return healthAmount <= 0.25F;
   }

   private float DMG() {
      return (float)(this.m_21133_(Attributes.f_22281_) + this.m_21133_(Attributes.f_22281_) * (double)this.getRageMeter() * 0.2F);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAttackState() == 1) {
         this.flyingdestroy();
         if (this.attackTicks == 23) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 25) {
            this.AreaAttack(5.5F, 5.5F, 270.0F, 1.0F, (float)CMConfig.MaledictusSmashHpDamage, 200, false, false);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 20);
            this.MakeRingparticle(2.5F, 0.2F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 30.0F);
         }
      }

      if (this.getAttackState() == 2 && this.attackTicks == 8) {
         this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_BOW_PULL.get(), 1.0F, 1.0F);
      }

      if (this.getAttackState() == 3) {
         this.flyingdestroy();
         if (this.attackTicks == 6) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 28) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_BOW_PULL.get(), 1.0F, 1.0F);
         }
      }

      if (this.getAttackState() == 4 && (this.f_19861_ || !this.m_146900_().m_60819_().m_76178_())) {
         this.setAttackState(5);
      }

      if (this.getAttackState() == 6 && this.attackTicks == 30 && this.f_19853_.f_46443_) {
         for (int i = 0; i < 20 + this.f_19796_.m_188503_(2); i++) {
            float f2 = this.f_19796_.m_188501_() * (float) (Math.PI * 2);
            float f3 = Mth.m_14116_(this.f_19796_.m_188501_()) * this.m_20205_() * 0.5F;
            double d0 = this.m_20185_() + (double)(Mth.m_14089_(f2) * f3);
            double d4 = this.m_20189_() + (double)(Mth.m_14031_(f2) * f3);
            this.f_19853_
               .m_7106_(
                  (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(),
                  d0,
                  this.m_20186_() + (double)this.m_20206_() * 0.6 + (double)i * 0.05,
                  d4,
                  0.0,
                  0.1,
                  0.0
               );
         }
      }

      if (this.getAttackState() == 7) {
         if (this.attackTicks == 10) {
            this.masseffectParticle(5.0F);
         }

         if (this.attackTicks == 15) {
            this.masseffectParticle(7.0F);
         }

         if (this.attackTicks == 20) {
            this.masseffectParticle(9.0F);
         }

         if (this.attackTicks == 30) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_BATTLE_CRY.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 32) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 34) {
            this.m_5496_(SoundEvents.f_11913_, 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.2F, 0, 40);
            this.setRageMeter(0);
            if (this.f_19853_.f_46443_) {
               float vec = 1.0F;
               float math = 0.0F;
               float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
               float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
               double theta = (double)this.f_20883_ * (Math.PI / 180.0);
               double vecX = Math.cos(++theta);
               double vecZ = Math.sin(theta);
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 30, 0.337F, 0.925F, 0.8F, 1.0F, 85.0F, false, RingParticle.EnumRingBehavior.GROW),
                     this.m_20185_() + (double)vec * vecX + (double)(f * math),
                     this.m_20186_() + 0.02F,
                     this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
                     0.0,
                     0.0,
                     0.0
                  );
            }

            for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(7.0))) {
               if (!this.m_7307_(entity) && entity != this) {
                  entity.m_6469_(
                     CMDamageTypes.causeMaledictioDamage(this),
                     (float)((double)(this.DMG() * 1.6F) + Math.min((double)(this.DMG() * 1.6F), (double)entity.m_21233_() * CMConfig.MaledictusAOEHpDamage))
                  );
               }
            }
         }

         if (this.attackTicks > 34 && this.attackTicks < 44) {
            this.Sphereparticle(0.3F, 1.0F, 4.0F);
         }
      }

      if (this.getAttackState() == 8) {
         this.flyingdestroy();
         if (this.attackTicks == 23) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks >= 65 && (this.f_19861_ || !this.m_146900_().m_60819_().m_76178_())) {
            this.setAttackState(9);
         }
      }

      if (this.getAttackState() == 9) {
         this.flyingdestroy();
         if (this.attackTicks == 2) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 4) {
            this.m_5496_(SoundEvents.f_11913_, 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.3F, 0, 40);
            this.MakeRingparticle(2.5F, 0.2F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F);

            for (LivingEntity entityx : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(3.5))) {
               if (!this.m_7307_(entityx) && entityx != this) {
                  boolean flag = entityx.m_6469_(
                     CMDamageTypes.causeMaledictioDamage(this),
                     (float)(
                        (double)(this.DMG() * 1.25F)
                           + Math.min((double)(this.DMG() * 1.25F), (double)entityx.m_21233_() * CMConfig.MaledictusFlyingSmashHpDamage)
                     )
                  );
                  if (flag) {
                     this.rageTicks = 200;
                     if (this.getRageMeter() < 5) {
                        this.setRageMeter(this.getRageMeter() + 1);
                     }
                  }
               }
            }
         }

         int i = 8;

         for (int j = 2; i <= 16; j++) {
            if (this.attackTicks == i) {
               this.ShieldSmashDamage(2.0F, j, 4.0F, 2.5F, 1.0F, (float)CMConfig.MaledictusShockWaveHpDamage, 0.05F);
            }

            i += 2;
         }
      }

      if (this.getAttackState() == 11 || this.getAttackState() == 12 || this.getAttackState() == 13 || this.getAttackState() == 14) {
         if (this.attackTicks == 21) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.attackTicks == 24) {
            this.m_5496_((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.attackTicks >= 24 && this.attackTicks <= 33) {
            this.Rushattack(-0.05, 0.5, 3.25, 1.1F, (float)CMConfig.MaledictusHpDamage, 0, true);
            if (this.f_19853_.f_46443_) {
               double x = this.m_20185_();
               double y = this.m_20186_() + (double)(this.m_20206_() / 2.0F);
               double z = this.m_20189_();
               float yaw = (float)Math.toRadians((double)(-this.m_146908_()));
               float yaw2 = (float)Math.toRadians((double)(-this.m_146908_() + 180.0F));
               float pitch = (float)Math.toRadians((double)(-this.m_146909_()));
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(yaw, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                     x,
                     y,
                     z,
                     0.0,
                     0.0,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(yaw2, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                     x,
                     y,
                     z,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.getAttackState() == 15 || this.getAttackState() == 16) {
         if (this.attackTicks == 13) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.attackTicks == 16) {
            this.m_5496_((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.attackTicks >= 16 && this.attackTicks <= 25) {
            this.Rushattack(-0.035, 0.5, 3.25, 1.2F, (float)CMConfig.MaledictusHpDamage, 0, true);
            if (this.f_19853_.f_46443_) {
               double x = this.m_20185_();
               double y = this.m_20186_() + (double)(this.m_20206_() / 2.0F);
               double z = this.m_20189_();
               float yaw = (float)Math.toRadians((double)(-this.m_146908_()));
               float yaw2 = (float)Math.toRadians((double)(-this.m_146908_() + 180.0F));
               float pitch = (float)Math.toRadians((double)(-this.m_146909_()));
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(yaw, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                     x,
                     y,
                     z,
                     0.0,
                     0.0,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(yaw2, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                     x,
                     y,
                     z,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.getAttackState() == 17) {
         if (this.attackTicks == 13) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.attackTicks == 16) {
            this.m_5496_((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.attackTicks >= 16 && this.attackTicks <= 24) {
            this.Rushattack(0.0, 0.5, 3.25, 1.3F, (float)CMConfig.MaledictusHpDamage, 0, true);
            if (this.f_19853_.f_46443_) {
               double x = this.m_20185_();
               double y = this.m_20186_() + (double)(this.m_20206_() / 2.0F);
               double z = this.m_20189_();
               float yaw = (float)Math.toRadians((double)(-this.m_146908_()));
               float yaw2 = (float)Math.toRadians((double)(-this.m_146908_() + 180.0F));
               float pitch = (float)Math.toRadians((double)(-this.m_146909_()));
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(yaw, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                     x,
                     y,
                     z,
                     0.0,
                     0.0,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(yaw2, pitch, 40, 0.337F, 0.925F, 0.8F, 1.0F, 50.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                     x,
                     y,
                     z,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.getAttackState() == 18) {
         if (this.attackTicks == 18) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 20) {
            this.AreaAttack(3.25F, 3.25F, 220.0F, 1.0F, (float)CMConfig.MaledictusHpDamage, 0, false, false);
         }

         if (this.attackTicks == 32) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 34) {
            this.AreaAttack(3.25F, 3.25F, 220.0F, 1.0F, (float)CMConfig.MaledictusHpDamage, 160, false, false);
         }
      }

      if (this.getAttackState() == 19) {
         if (this.attackTicks == 10) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 12) {
            this.AreaAttack(4.0F, 4.0F, 180.0F, 1.0F, (float)CMConfig.MaledictusHpDamage, 0, false, false);
         }

         if (this.attackTicks == 22) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 24) {
            this.MakeRingparticle(2.5F, 0.2F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 30.0F);
            this.ComboAreaAttack(5.0F, 5.0F, 70.0F, 1.2F, (float)CMConfig.MaledictusHpDamage, 200, false);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 21) {
         if (this.attackTicks == 10) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 12) {
            this.AreaAttack(4.25F, 4.25F, 180.0F, 1.0F, (float)CMConfig.MaledictusHpDamage, 0, true, false);
         }

         if (this.attackTicks == 22) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 24) {
            this.AreaAttack(5.0F, 5.0F, 70.0F, 1.2F, (float)CMConfig.MaledictusHpDamage, 0, true, false);
            this.MakeRingparticle(2.5F, 0.2F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 30.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 22) {
         if (this.attackTicks == 21) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 23) {
            this.uppercut(0.2, 3.25, 1.0F, (float)CMConfig.MaledictusHpDamage, 120, true);
         }

         if (this.attackTicks == 31) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 0.5F, 1.0F);
         }

         if (this.attackTicks == 33) {
            this.uppercut(0.25, 3.8, 1.0F, (float)CMConfig.MaledictusHpDamage, 120, false);
            this.MakeRingparticle(3.5F, -0.3F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 20.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 23) {
         if (this.attackTicks == 21) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 23) {
            this.uppercut(0.2, 3.25, 1.0F, (float)CMConfig.MaledictusHpDamage, 120, true);
         }

         if (this.attackTicks == 31) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_MACE_SWING.get(), 0.5F, 1.0F);
         }

         if (this.attackTicks == 33) {
            this.uppercut(0.25, 3.8, 1.0F, (float)CMConfig.MaledictusHpDamage, 120, false);
            this.MakeRingparticle(3.5F, 0.7F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 20.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 20);
         }
      }

      if (this.getAttackState() == 24) {
         this.flyingdestroy();
         if (this.attackTicks == 23) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks >= 60 && (this.f_19861_ || !this.m_146900_().m_60819_().m_76178_())) {
            this.setAttackState(25);
         }
      }

      if (this.getAttackState() == 25) {
         this.flyingdestroy();
         if (this.attackTicks == 4) {
            this.m_5496_((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.3F, 0, 40);
            this.MakeRingparticle(2.25F, 0.3F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 30.0F);

            for (LivingEntity entityxx : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(2.0))) {
               if (!this.m_7307_(entityxx) && entityxx != this) {
                  boolean flag = entityxx.m_6469_(
                     CMDamageTypes.causeMaledictioDamage(this),
                     (float)(
                        (double)(this.DMG() * 1.25F)
                           + Math.min((double)(this.DMG() * 1.25F), (double)entityxx.m_21233_() * CMConfig.MaledictusFlyingSmashHpDamage)
                     )
                  );
                  if (flag) {
                     this.rageTicks = 200;
                     if (this.getRageMeter() < 5) {
                        this.setRageMeter(this.getRageMeter() + 1);
                     }
                  }
               }
            }

            this.StrikeWindmillHalberd(6, 10, 1.0, 0.75, 0.2, 1);
            this.StrikeWindmillHalberd(4, 10, 1.0, 1.2, 0.15, 1);
         }

         if (this.attackTicks == 37) {
            this.MakeRingparticle(2.25F, 0.3F, 40, 0.337F, 0.925F, 0.8F, 1.0F, 30.0F);

            for (LivingEntity entityxxx : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(2.0))) {
               if (!this.m_7307_(entityxxx) && entityxxx != this) {
                  boolean flag = entityxxx.m_6469_(
                     CMDamageTypes.causeMaledictioDamage(this),
                     (float)(
                        (double)(this.DMG() * 1.25F)
                           + Math.min((double)(this.DMG() * 1.25F), (double)entityxxx.m_21233_() * CMConfig.MaledictusFlyingSmashHpDamage)
                     )
                  );
                  if (flag) {
                     this.rageTicks = 200;
                     if (this.getRageMeter() < 5) {
                        this.setRageMeter(this.getRageMeter() + 1);
                     }
                  }
               }
            }
         }

         if (this.attackTicks == 39) {
            this.StrikeHalberd(14, 14.0F, (float)(7 + this.f_19796_.m_188503_(3)), 4.0, 1);
         }

         if (this.attackTicks == 40) {
            this.StrikeHalberd(16, 16.0F, (float)(10 + this.f_19796_.m_188503_(5)), 5.5, 1);
         }

         if (this.attackTicks == 42 && this.isHalfHealth()) {
            this.StrikeHalberd(18, 18.0F, (float)(13 + this.f_19796_.m_188503_(8)), 6.5, 1);
         }

         if (this.attackTicks == 43 && this.isQuarterHealth()) {
            this.StrikeHalberd(20, 20.0F, (float)(16 + this.f_19796_.m_188503_(10)), 7.5, 1);
         }
      }

      if (this.getAttackState() == 26) {
         if (this.attackTicks == 13) {
            this.m_5496_((SoundEvent)ModSounds.PHANTOM_SPEAR.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks == 15) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
            this.AreaAttack(4.5F, 4.5F, 80.0F, 1.2F, (float)CMConfig.MaledictusSmashHpDamage, 0, true, false);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 0, 10);
         }

         if (this.attackTicks == 44) {
            this.AreaAttack(5.5F, 5.5F, 110.0F, 1.0F, (float)CMConfig.MaledictusSmashHpDamage, 0, true, false);
         }

         for (int l = 44; l <= 58; l += 2) {
            if (this.attackTicks == l) {
               int d = l - 42;
               this.radagonskill(0.7F, d, 1.0F, 2);
            }
         }
      }

      if (this.getAttackState() == 27 && this.attackTicks == 20) {
         this.AreaAttack(5.25F, 5.25F, 110.0F, 1.0F, (float)CMConfig.MaledictusHpDamage, 120, false, true);
         this.m_5496_((SoundEvent)ModSounds.AXE_SWING.get(), 1.0F, 1.3F);
      }

      if (this.getAttackState() == 29) {
         if (this.attackTicks == 1) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
         }

         this.Grab(-0.025, 0.5, 1.5, 0.6F, 0.0F, 0, true);
         if (this.f_19853_.f_46443_) {
            for (int i = 0; i < 2; i++) {
               this.f_19853_
                  .m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), this.m_20208_(1.5), this.m_20187_(), this.m_20262_(1.5), 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getAttackState() == 31) {
         if (this.f_19853_.f_46443_) {
            for (int i = 0; i < 2; i++) {
               this.f_19853_
                  .m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), this.m_20208_(1.5), this.m_20187_(), this.m_20262_(1.5), 0.0, 0.0, 0.0);
            }
         }

         if (this.attackTicks == 17) {
            this.m_5496_((SoundEvent)ModSounds.MALEDICTUS_LEAP.get(), 1.0F, 1.0F);
         }
      }

      if (this.getAttackState() == 32) {
         if (this.f_19861_ || !this.m_146900_().m_60819_().m_76178_()) {
            this.setAttackState(33);
         }

         if (this.f_19853_.f_46443_) {
            for (int i = 0; i < 2; i++) {
               this.f_19853_
                  .m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), this.m_20208_(1.5), this.m_20187_(), this.m_20262_(1.5), 0.0, 0.0, 0.0);
            }
         }
      }

      if (this.getAttackState() == 33) {
         if (this.attackTicks == 2) {
            this.m_5496_(SoundEvents.f_11913_, 0.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.2F, 0, 40);
            if (this.f_19853_.f_46443_) {
               float vec = 1.0F;
               float math = 0.0F;
               float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
               float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
               double theta = (double)this.f_20883_ * (Math.PI / 180.0);
               double vecX = Math.cos(++theta);
               double vecZ = Math.sin(theta);
               this.f_19853_
                  .m_7106_(
                     new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 30, 0.337F, 0.925F, 0.8F, 1.0F, 85.0F, false, RingParticle.EnumRingBehavior.GROW),
                     this.m_20185_() + (double)vec * vecX + (double)(f * math),
                     this.m_20186_() + 0.02F,
                     this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
                     0.0,
                     0.0,
                     0.0
                  );
            }

            for (LivingEntity entityxxxx : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(7.0))) {
               if (!this.m_7307_(entityxxxx) && entityxxxx != this) {
                  entityxxxx.m_6469_(
                     CMDamageTypes.causeMaledictioDamage(this),
                     (float)(
                        (double)(this.DMG() * 2.0F) + Math.min((double)(this.DMG() * 2.0F), (double)entityxxxx.m_21233_() * CMConfig.MaledictusAOEHpDamage)
                     )
                  );
               }
            }
         }

         if (this.attackTicks > 1 && this.attackTicks < 11) {
            this.Sphereparticle(0.3F, 1.0F, 4.0F);
         }
      }
   }

   private void blockbreak() {
      if (!this.m_21525_() && !this.f_19853_.f_46443_) {
         if (CMConfig.MaledictusBlockBreaking) {
            this.blockdestroy();
         } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            this.blockdestroy();
         }
      }
   }

   private void flyingdestroy() {
      if (!this.f_19853_.f_46443_) {
         if (CMConfig.MaledictusBlockBreaking) {
            this.blockdestroy2(0.35, 2.0);
         } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            this.blockdestroy2(0.35, 2.0);
         }
      }
   }

   private void blockdestroy() {
      if (this.destroyBlocksTick > 0) {
         this.destroyBlocksTick--;
         if (this.destroyBlocksTick == 0) {
            AABB aabb = this.m_20191_().m_82400_(0.5);

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
                  && !blockstate.m_204336_(ModTag.MALEDICTUS_IMMUNE)
                  && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                  this.f_19853_.m_46953_(blockpos, true, this);
               }
            }
         }
      }

      AABB aabb = this.m_20191_().m_82377_(0.2, 0.5, 0.2);

      for (BlockPos blockposx : BlockPos.m_121976_(
         Mth.m_14107_(aabb.f_82288_),
         Mth.m_14107_(this.m_20186_()),
         Mth.m_14107_(aabb.f_82290_),
         Mth.m_14107_(aabb.f_82291_),
         Mth.m_14107_(aabb.f_82292_),
         Mth.m_14107_(aabb.f_82293_)
      )) {
         BlockState blockstate = this.f_19853_.m_8055_(blockposx);
         if (!blockstate.m_60795_()
            && blockstate.canEntityDestroy(this.f_19853_, blockposx, this)
            && blockstate.m_204336_(ModTag.FROSTED_PRISON_CHANDELIER)
            && ForgeEventFactory.onEntityDestroyBlock(this, blockposx, blockstate)) {
            this.f_19853_.m_46953_(blockposx, true, this);
         }
      }
   }

   private void blockdestroy2(double xz, double y) {
      AABB aabb = this.m_20191_().m_82377_(xz, y, xz);

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
            && !blockstate.m_204336_(ModTag.MALEDICTUS_IMMUNE)
            && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
            this.f_19853_.m_46953_(blockpos, true, this);
         }
      }
   }

   private void StrikeHalberd(int rune, float close, float radius, double range, int delay) {
      float angle2 = (float) (Math.PI / 180.0) * this.f_20883_;

      for (int k = 0; k < rune; k++) {
         float f2 = angle2 + (float)k * (float) Math.PI * 2.0F / close + (float) (Math.PI * 2) / radius;
         this.spawnHalberd(
            this.m_20185_() + (double)Mth.m_14089_(f2) * range,
            this.m_20189_() + (double)Mth.m_14031_(f2) * range,
            this.m_20186_() - 5.0,
            this.m_20186_() + 3.0,
            f2,
            delay
         );
      }

      if (this.f_19853_.f_46443_) {
         for (int k = 0; k < rune; k++) {
            float f2 = angle2 + (float)k * (float) Math.PI * 2.0F / close + (float) (Math.PI * 2) / radius;

            for (int i1 = 0; i1 < 6 + this.f_19796_.m_188503_(2); i1++) {
               double DeltaMovementX = this.m_217043_().m_188583_() * 0.007;
               double DeltaMovementY = this.m_217043_().m_188583_() * 0.007;
               double DeltaMovementZ = this.m_217043_().m_188583_() * 0.007;
               float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
               double extraX = (double)(0.5F * Mth.m_14031_((float)(Math.PI + (double)angle)));
               double extraY = 0.3F;
               double extraZ = (double)(0.5F * Mth.m_14089_(angle));
               this.f_19853_
                  .m_7106_(
                     (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(),
                     this.m_20185_() + (double)Mth.m_14089_(f2) * range + extraX,
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)Mth.m_14031_(f2) * range + extraZ,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }
      }
   }

   private void StrikeWindmillHalberd(int numberOfBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay) {
      float angleIncrement = (float)((Math.PI * 2) / (double)numberOfBranches);

      for (int branch = 0; branch < numberOfBranches; branch++) {
         float baseAngle = angleIncrement * (float)branch;

         for (int i = 0; i < particlesPerBranch; i++) {
            double currentRadius = initialRadius + (double)i * radiusIncrement;
            float currentAngle = (float)((double)baseAngle + (double)((float)i * angleIncrement) / initialRadius + (double)((float)((double)i * curveFactor)));
            double xOffset = currentRadius * Math.cos((double)currentAngle);
            double zOffset = currentRadius * Math.sin((double)currentAngle);
            double spawnX = this.m_20185_() + xOffset;
            double spawnY = this.m_20186_() + 0.3;
            double spawnZ = this.m_20189_() + zOffset;
            int d3 = delay * (i + 1);
            this.spawnHalberd(spawnX, spawnZ, this.m_20186_() - 5.0, this.m_20186_() + 3.0, currentAngle, d3);
            double deltaX = this.m_217043_().m_188583_() * 0.007;
            double deltaY = this.m_217043_().m_188583_() * 0.007;
            double deltaZ = this.m_217043_().m_188583_() * 0.007;
            if (this.f_19853_.f_46443_) {
               this.f_19853_.m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
            }
         }
      }
   }

   private void radagonskill(float spreadarc, int distance, float vec, int delay) {
      double perpFacing = (double)this.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      double spread = Math.PI * (double)spreadarc;
      int arcLen = Mth.m_14165_((double)distance * spread);

      for (int i = 0; i < arcLen; i++) {
         double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = this.m_20185_() + vx * (double)distance + (double)vec * Math.cos((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         double pz = this.m_20189_() + vz * (double)distance + (double)vec * Math.sin((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         int hitX = Mth.m_14107_(px);
         int hitZ = Mth.m_14107_(pz);
         this.spawnHalberd((double)hitX + 0.5, (double)hitZ + 0.5, this.m_20186_() - 5.0, this.m_20186_() + 3.0, (float)theta, delay);
         this.radagonparticle((double)hitX + 0.5, (double)hitZ + 0.5, this.m_20186_() - 5.0, this.m_20186_() + 3.0);
      }
   }

   private void radagonparticle(double x, double z, double minY, double maxY) {
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

      if (flag && this.f_19853_.f_46443_) {
         for (int i1 = 0; i1 < 4; i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.007;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.007;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.007;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)(0.35F * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(0.35F * Mth.m_14089_(angle));
            this.f_19853_
               .m_7106_(
                  (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(),
                  x + extraX,
                  (double)blockpos.m_123342_() + d0 + extraY,
                  z + extraZ,
                  DeltaMovementX,
                  DeltaMovementY,
                  DeltaMovementZ
               );
         }
      }
   }

   private void spawnHalberd(double x, double z, double minY, double maxY, float rotation, int delay) {
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
            .m_7967_(
               new Phantom_Halberd_Entity(
                  this.f_19853_,
                  x,
                  (double)blockpos.m_123342_() + d0,
                  z,
                  rotation,
                  delay,
                  this,
                  (float)CMConfig.MaledictusPhantomHalberddamage + (float)CMConfig.MaledictusPhantomHalberddamage * (float)this.getRageMeter() * 0.1F
               )
            );
      }
   }

   private void ComboAreaAttack(float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, boolean maledictio) {
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
            && !(entityHit instanceof Maledictus_Entity)
            && entityHit != this) {
            DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : DamageSource.m_19370_(this);
            boolean flag = entityHit.m_6469_(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entityHit.m_21233_() * hpdamage));
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               this.combo = true;
               this.rageTicks = 200;
               if (this.getRageMeter() < 5) {
                  this.setRageMeter(this.getRageMeter() + 1);
               }
            }
         }
      }
   }

   private void Grab(double inflateXZ, double inflateY, double range, float damage, float hpdamage, int shieldbreakticks, boolean maledictio) {
      double yaw = Math.toRadians((double)(this.m_146908_() + 90.0F));
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.m_20191_().m_82377_(inflateXZ, inflateY, inflateXZ).m_82363_(xExpand, 0.0, zExpand);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, attackRange)) {
         if (!this.m_7307_(entity) && entity != this) {
            DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entity.m_21233_() * hpdamage));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag) {
               this.grab = true;
               if (!entity.m_6095_().m_204039_(ModTag.IGNIS_CANT_POKE) && entity.m_6084_()) {
                  if (entity.m_6144_()) {
                     entity.m_20260_(false);
                  }

                  if (this.m_20197_().isEmpty() && !this.f_19853_.f_46443_) {
                     entity.m_7998_(this, true);
                  }
               }
            }
         }
      }
   }

   private void ShieldSmashDamage(float spreadarc, int distance, float mxy, float vec, float damage, float hpdamage, float airborne) {
      double perpFacing = (double)this.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int hitY = Mth.m_14107_(this.m_20191_().f_82289_ - 0.5);
      double spread = Math.PI * (double)spreadarc;
      int arcLen = Mth.m_14165_((double)distance * spread);
      double minY = this.m_20186_() - 1.0;
      double maxY = this.m_20186_() + (double)mxy;

      for (int i = 0; i < arcLen; i++) {
         double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = this.m_20185_() + vx * (double)distance + (double)vec * Math.cos((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         double pz = this.m_20189_() + vz * (double)distance + (double)vec * Math.sin((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         int hitX = Mth.m_14107_(px);
         int hitZ = Mth.m_14107_(pz);
         BlockPos pos = new BlockPos(hitX, hitY, hitZ);
         BlockState block = this.f_19853_.m_8055_(pos);
         int maxDepth = 30;

         for (int depthCount = 0; depthCount < maxDepth && block.m_60799_() != RenderShape.MODEL; depthCount++) {
            pos = pos.m_7495_();
            block = this.f_19853_.m_8055_(pos);
         }

         if (block.m_60799_() != RenderShape.MODEL) {
            block = Blocks.f_50016_.m_49966_();
         }

         Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
            this.f_19853_, (double)hitX + 0.5, (double)hitY + 1.0, (double)hitZ + 0.5, block, 10
         );
         fallingBlockEntity.m_5997_(0.0, 0.2 + this.m_217043_().m_188583_() * 0.15, 0.0);
         this.f_19853_.m_7967_(fallingBlockEntity);
         AABB selection = new AABB(px - 0.5, minY, pz - 0.5, px + 0.5, maxY, pz + 0.5);

         for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, selection)) {
            if (!this.m_7307_(entity) && entity != this) {
               boolean flag = entity.m_6469_(
                  CMDamageTypes.causeMaledictioDamage(this), this.DMG() * damage + Math.min(this.DMG() * damage, entity.m_21233_() * hpdamage)
               );
               if (flag) {
                  entity.m_20256_(entity.m_20184_().m_82520_(0.0, (double)(airborne * (float)distance) + this.f_19853_.f_46441_.m_188500_() * 0.15, 0.0));
               }
            }
         }
      }
   }

   private void MakeRingparticle(float vec, float math, int duration, float r, float g, float b, float a, float scale) {
      if (this.f_19853_.f_46443_) {
         float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
         float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);
         this.f_19853_
            .m_7106_(
               new RingParticle.RingData(0.0F, (float) (Math.PI / 2), duration, r, g, b, a, scale, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
               this.m_20185_() + (double)vec * vecX + (double)(f * math),
               this.m_20186_() + 0.02F,
               this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void Sphereparticle(float height, float vec, float size) {
      if (this.f_19853_.f_46443_ && this.f_19797_ % 2 == 0) {
         double d0 = this.m_20185_();
         double d1 = this.m_20186_() + (double)height;
         double d2 = this.m_20189_();
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (float i = -size; i <= size; i++) {
            for (float j = -size; j <= size; j++) {
               for (float k = -size; k <= size; k++) {
                  double d3 = (double)j + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d4 = (double)i + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d5 = (double)k + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.f_19796_.m_188583_() * 0.05;
                  this.f_19853_
                     .m_7106_((ParticleOptions)ModParticle.CURSED_FLAME.get(), d0 + (double)vec * vecX, d1, d2 + (double)vec * vecZ, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2.0F - 1.0F;
                  }
               }
            }
         }
      }
   }

   private void masseffectParticle(float radius) {
      if (this.f_19853_.f_46443_) {
         for (int j = 0; j < 70; j++) {
            float angle = (float)(Math.random() * 2.0 * Math.PI);
            double distance = Math.sqrt(Math.random()) * (double)radius;
            double extraX = this.m_20185_() + distance * (double)Mth.m_14089_(angle);
            double extraY = this.m_20186_() + 0.3F;
            double extraZ = this.m_20189_() + distance * (double)Mth.m_14031_(angle);
            this.f_19853_.m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), extraX, extraY, extraZ, 0.0, this.f_19796_.m_188583_() * 0.04, 0.0);
         }
      }
   }

   private void Rushattack(double inflateXZ, double inflateY, double range, float damage, float hpdamage, int shieldbreakticks, boolean maledictio) {
      double yaw = Math.toRadians((double)(this.m_146908_() + 90.0F));
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.m_20191_().m_82377_(inflateXZ, inflateY, inflateXZ).m_82363_(xExpand, 0.0, zExpand);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, attackRange)) {
         if (!this.m_7307_(entity) && entity != this) {
            DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entity.m_21233_() * hpdamage));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag) {
               this.rageTicks = 200;
               if (this.getRageMeter() < 5) {
                  this.setRageMeter(this.getRageMeter() + 1);
               }
            }
         }
      }
   }

   private void uppercut(double inflate, double range, float damage, float hpdamage, int shieldbreakticks, boolean airborne) {
      double yaw = Math.toRadians((double)(this.m_146908_() + 90.0F));
      double xExpand = range * Math.cos(yaw);
      double zExpand = range * Math.sin(yaw);
      AABB attackRange = this.m_20191_().m_82400_(inflate).m_82363_(xExpand, 0.0, zExpand);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, attackRange)) {
         if (!this.m_7307_(entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entity.m_21233_() * hpdamage));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag) {
               this.rageTicks = 200;
               if (this.getRageMeter() < 5) {
                  this.setRageMeter(this.getRageMeter() + 1);
               }

               if (airborne) {
                  double d0 = entity.m_21133_(Attributes.f_22278_);
                  double d1 = Math.max(0.0, 1.0 - d0);
                  entity.m_20256_(entity.m_20184_().m_82520_(0.0, 0.4F * d1, 0.0));
               }
            }
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, boolean maledictio, boolean knockback) {
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
            && !(entityHit instanceof Maledictus_Entity)
            && entityHit != this) {
            DamageSource damagesource = maledictio ? CMDamageTypes.causeMaledictioDamage(this) : DamageSource.m_19370_(this);
            boolean flag = entityHit.m_6469_(damagesource, this.DMG() * damage + Math.min(this.DMG() * damage, entityHit.m_21233_() * hpdamage));
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               this.rageTicks = 200;
               if (this.getRageMeter() < 5) {
                  this.setRageMeter(this.getRageMeter() + 1);
               }

               double d0 = entityHit.m_20185_() - this.m_20185_();
               double d1 = entityHit.m_20189_() - this.m_20189_();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               if (knockback) {
                  entityHit.m_5997_(d0 / d2 * 2.5, 0.18, d1 / d2 * 2.2);
               }
            }
         }
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_MALEDICTUS) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.MALEDICTUS_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.MALEDICTUS_DEATH.get();
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.MALEDICTUS_IDLE.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.MALEDICTUS_MUSIC.get();
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossEvent1.m_6543_(player);
      this.bossEvent2.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossEvent1.m_6539_(player);
      this.bossEvent2.m_6539_(player);
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   static class MaledictusChargeGoal extends Goal {
      private final Maledictus_Entity entity;
      private final int getattackstate;
      private final float attackrange;
      private final float attackminrange;
      private final int startweapon;
      private final int stopweapon;
      private final int attackseetick;
      private final int attackseetick2;
      private final int attackchargetick;
      private final float random;

      public MaledictusChargeGoal(
         Maledictus_Entity entity,
         int getattackstate,
         int attackseetick,
         int attackseetick2,
         int attackchargetick,
         float attackminrange,
         float attackrange,
         int startbow,
         int stopbow,
         float random
      ) {
         this.entity = entity;
         this.getattackstate = getattackstate;
         this.attackrange = attackrange;
         this.attackminrange = attackminrange;
         this.attackseetick = attackseetick;
         this.attackseetick2 = attackseetick2;
         this.attackchargetick = attackchargetick;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && target.m_6084_()
            && this.entity.m_20270_(target) > this.attackminrange
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.charge_cooldown <= 0
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.m_21574_().m_148306_(target);
      }

      public void m_8056_() {
         if (this.entity.isHalfHealth()) {
            this.entity.setAttackState(14);
         } else {
            this.entity.setAttackState(13);
         }

         this.entity.setWeapon(this.startweapon);
      }

      public boolean m_8045_() {
         return this.entity.getAttackState() == 13 ? this.entity.attackTicks <= 65 : this.entity.attackTicks <= 55;
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if ((this.entity.attackTicks >= this.attackseetick || target == null) && (this.entity.attackTicks <= this.attackseetick2 || target == null)) {
            this.entity.m_146922_(this.entity.f_19859_);
         } else {
            this.entity.m_21563_().m_24960_(target, 60.0F, 30.0F);
            this.entity.m_21391_(target, 60.0F, 30.0F);
         }

         if (this.entity.attackTicks == this.attackchargetick) {
            float f1 = (float)Math.cos(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            float f2 = (float)Math.sin(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            if (target != null) {
               float r = this.entity.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 7.0F);
               this.entity.m_5997_((double)f1 * 0.9 * (double)r, 0.0, (double)f2 * 0.9 * (double)r);
            } else {
               this.entity.m_5997_((double)f1 * 3.0, 0.0, (double)f2 * 3.0);
            }
         }

         if (this.entity.getAttackState() == 13 && this.entity.attackTicks == 34 && (this.entity.f_19861_ || this.entity.m_20077_() || this.entity.m_20069_())) {
            float speed = -1.7F;
            float dodgeYaw = (float)Math.toRadians((double)(this.entity.m_146908_() + 90.0F));
            Vec3 m = this.entity.m_20184_().m_82520_((double)speed * Math.cos((double)dodgeYaw), 0.0, (double)speed * Math.sin((double)dodgeYaw));
            this.entity.m_20334_(m.f_82479_, 0.4, m.f_82481_);
         }
      }

      public void m_8041_() {
         if (this.entity.getAttackState() == 14) {
            if (this.entity.isQuarterHealth()) {
               this.entity.setAttackState(16);
            } else {
               this.entity.setAttackState(15);
            }
         } else {
            this.entity.setAttackState(0);
            this.entity.charge_cooldown = 80;
            this.entity.setWeapon(this.stopweapon);
         }
      }

      public boolean m_183429_() {
         return true;
      }
   }

   static class MaledictusChargeState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;
      private final int attackseetick;
      private final int attackseetick2;
      private final int attackchargetick;
      private final int backsteptick;
      private final int count;

      public MaledictusChargeState(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackseetick2,
         int attackchargetick,
         int backsteptick,
         int startbow,
         int stopbow,
         int count
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.attackseetick = attackseetick;
         this.attackseetick2 = attackseetick2;
         this.attackchargetick = attackchargetick;
         this.backsteptick = backsteptick;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.count = count;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if ((this.entity.attackTicks >= this.attackseetick || target == null) && (this.entity.attackTicks <= this.attackseetick2 || target == null)) {
            this.entity.m_146922_(this.entity.f_19859_);
         } else {
            this.entity.m_21563_().m_24960_(target, 60.0F, 30.0F);
            this.entity.m_21391_(target, 60.0F, 30.0F);
         }

         if (this.entity.attackTicks == this.attackchargetick) {
            float f1 = (float)Math.cos(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            float f2 = (float)Math.sin(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            if (target != null) {
               float r = this.entity.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 7.0F);
               this.entity.m_5997_((double)(f1 * 0.9F * r), 0.0, (double)(f2 * 0.9F * r));
            } else {
               this.entity.m_5997_((double)f1 * 3.0, 0.0, (double)f2 * 3.0);
            }
         }

         if (this.backsteptick > 0
            && this.entity.attackTicks == this.backsteptick
            && (this.entity.f_19861_ || this.entity.m_20077_() || this.entity.m_20069_())) {
            float speed = -1.7F;
            float dodgeYaw = (float)Math.toRadians((double)(this.entity.m_146908_() + 90.0F));
            Vec3 m = this.entity.m_20184_().m_82520_((double)speed * Math.cos((double)dodgeYaw), 0.0, (double)speed * Math.sin((double)dodgeYaw));
            this.entity.m_5496_((SoundEvent)ModSounds.MALEDICTUS_JUMP.get(), 1.0F, 1.0F);
            this.entity.m_20334_(m.f_82479_, 0.4, m.f_82481_);
         }
      }

      @Override
      public void m_8041_() {
         if (this.count == 1) {
            if (this.entity.isQuarterHealth()) {
               this.entity.setAttackState(16);
            } else {
               this.entity.setAttackState(15);
            }
         } else if (this.count == 2 && this.entity.getAttackState() == 16) {
            this.entity.setAttackState(17);
         } else {
            super.m_8041_();
            this.entity.charge_cooldown = 80;
            this.entity.setWeapon(this.stopweapon);
         }
      }
   }

   static class MaledictusGrabGoal extends Goal {
      protected final Maledictus_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final float attackrange;
      private final float random;
      private final int startweapon;
      private final int stopweapon;

      public MaledictusGrabGoal(
         Maledictus_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int startbow,
         int stopbow,
         float random
      ) {
         this.entity = entity;
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackrange = attackrange;
         this.attackseetick = attackseetick;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && target.m_6084_()
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.grab_cooldown <= 0;
      }

      public void m_8056_() {
         this.entity.setAttackState(this.attackstate);
         this.entity.setWeapon(this.startweapon);
      }

      public void m_8041_() {
         this.entity.setAttackState(this.attackendstate);
         this.entity.grab_cooldown = 300;
         this.entity.setWeapon(this.stopweapon);
      }

      public boolean m_8045_() {
         return this.entity.getAttackState() == this.attackstate && this.entity.attackTicks <= this.attackMaxtick;
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }
      }

      public boolean m_183429_() {
         return true;
      }
   }

   static class MaledictusGrabState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;

      public MaledictusGrabState(
         Maledictus_Entity entity, int getAttackState, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, int startbow, int stopbow
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public boolean m_8045_() {
         return super.m_8045_() && !this.entity.grab;
      }

      @Override
      public void m_8037_() {
         if (this.entity.f_19861_) {
            Vec3 vector3d = this.entity.m_20184_();
            float f = this.entity.m_146908_() * (float) (Math.PI / 180.0);
            Vec3 vector3d1 = new Vec3((double)(-Mth.m_14031_(f)), this.entity.m_20184_().f_82480_, (double)Mth.m_14089_(f))
               .m_82490_(0.8)
               .m_82549_(vector3d.m_82490_(0.8));
            this.entity.m_20334_(vector3d1.f_82479_, this.entity.m_20184_().f_82480_, vector3d1.f_82481_);
         }
      }

      @Override
      public void m_8041_() {
         if (this.entity.grab) {
            this.entity.setAttackState(31);
            this.entity.grab = false;
         } else {
            super.m_8041_();
         }

         this.entity.setWeapon(this.stopweapon);
      }
   }

   static class MaledictusSpinSlashes extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;
      private final int attackseetick;
      private final int attackseetick2;
      private final int attackseetick3;
      private final int attackchargetick1;
      private final int attackchargetick2;
      private final float random;

      public MaledictusSpinSlashes(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackseetick2,
         int attackseetick3,
         int attackchargetick1,
         int attackchargetick2,
         float attackrange,
         int startbow,
         int stopbow,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackseetick = attackseetick;
         this.attackseetick2 = attackseetick2;
         this.attackseetick3 = attackseetick3;
         this.attackchargetick1 = attackchargetick1;
         this.attackchargetick2 = attackchargetick2;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         return super.m_8036_() && this.entity.m_217043_().m_188501_() * 100.0F < this.random && this.entity.spin_cooldown <= 0;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if ((this.entity.attackTicks >= this.attackseetick || target == null)
            && (this.entity.attackTicks <= this.attackseetick2 || target == null || this.entity.attackTicks >= this.attackseetick3)) {
            this.entity.m_146922_(this.entity.f_19859_);
         } else {
            this.entity.m_21563_().m_24960_(target, 60.0F, 30.0F);
            this.entity.m_21391_(target, 60.0F, 30.0F);
         }

         if (this.entity.attackTicks == this.attackchargetick1 || this.entity.attackTicks == this.attackchargetick2) {
            float f1 = (float)Math.cos(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            float f2 = (float)Math.sin(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)));
            if (target != null) {
               float r = this.entity.m_20270_(target);
               r = Mth.m_14036_(r, 2.5F, 6.5F);
               this.entity.m_5997_((double)(f1 * 0.35F * r), 0.0, (double)(f2 * 0.35F * r));
            } else {
               this.entity.m_5997_((double)f1 * 2.25, 0.0, (double)f2 * 2.25);
            }
         }
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.setWeapon(this.stopweapon);
         this.entity.spin_cooldown = 100;
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class MaledictusSuccessState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startweapon;
      private final int stopweapon;
      private final int attackstrike;
      private final double dropspeed;

      public MaledictusSuccessState(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         int attackstrike,
         int startbow,
         int stopbow,
         double dropspeed
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.attackstrike = attackstrike;
         this.startweapon = startbow;
         this.stopweapon = stopbow;
         this.dropspeed = dropspeed;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void m_8037_() {
         if (this.entity.attackTicks == 19) {
            this.entity.m_20334_(0.0, 1.2, 0.0);
            this.entity.setFlying(true);
         }

         this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         if (this.entity.attackTicks == this.attackstrike) {
            this.entity.setFlying(false);
         }
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.setWeapon(this.stopweapon);
      }
   }

   static class Maledictus_Bow extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final float attackminrange;
      private final int attackshot;
      private final float random;

      public Maledictus_Bow(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackminrange = attackminrange;
         this.attackshot = attackshot;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return super.m_8036_()
            && target != null
            && this.entity.m_20270_(target) > this.attackminrange
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.m_21574_().m_148306_(target);
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(1);
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.setWeapon(0);
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         super.m_8037_();
         if (this.entity.attackTicks == this.attackshot && target != null) {
            double arrowcount = 4.0;
            double offsetangle = Math.toRadians(6.0);
            double d1 = target.m_20185_() - this.entity.m_20185_();
            double d2 = target.m_20227_(0.3333333333333333) - this.entity.m_20186_();
            double d3 = target.m_20189_() - this.entity.m_20189_();

            for (int i = 0; (double)i <= arrowcount - 1.0; i++) {
               double angle = ((double)i - (arrowcount - 1.0) / 2.0) * offsetangle;
               double x = d1 * Math.cos(angle) + d3 * Math.sin(angle);
               double z = -d1 * Math.sin(angle) + d3 * Math.cos(angle);
               double distance = Math.sqrt(x * x + z * z);
               Phantom_Arrow_Entity throwntrident = new Phantom_Arrow_Entity(this.entity.f_19853_, this.entity, target);
               throwntrident.m_36781_(
                  CMConfig.MaledictusPhantomArrowbasedamage + CMConfig.MaledictusPhantomArrowbasedamage * (double)this.entity.getRageMeter() * 0.05F
               );
               throwntrident.m_6686_(x, d2 + distance * 0.15F, z, 1.8F, 1.0F);
               this.entity.m_5496_(SoundEvents.f_11847_, 1.0F, 1.0F / (this.entity.m_217043_().m_188501_() * 0.4F + 0.8F));
               this.entity.f_19853_.m_7967_(throwntrident);
            }
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class Maledictus_Flying_Bow extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final int attackshot;
      private final float random;

      public Maledictus_Flying_Bow(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return super.m_8036_() && target != null && this.entity.m_217043_().m_188501_() * 100.0F < this.random && this.entity.flyattack_cooldown <= 0;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(1);
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.setWeapon(0);
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackshot && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
            this.entity.m_21391_(target, 30.0F, 90.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.attackTicks == 8) {
            if (target != null) {
               double x = 0.0;
               double z = 0.0;
               double r = 0.0;
               double maxR = 3.0;
               if (this.entity.m_20280_(target) < 36.0) {
                  float dodgeYaw = (float)Math.toRadians((double)(this.entity.m_146908_() + 90.0F));
                  double dis = this.entity.m_20280_(target);
                  r = Mth.m_14008_(8.0 / (dis + 0.1), 0.0, maxR);
                  x = Math.cos((double)dodgeYaw);
                  z = Math.sin((double)dodgeYaw);
               }

               double d1 = target.m_20186_() - this.entity.m_20186_();
               this.entity.m_20334_(x * -r, 0.9 + Mth.m_14008_(d1 * 0.075, 0.0, 7.0), z * -r);
            } else {
               this.entity.m_20334_(0.0, 0.9, 0.0);
            }

            this.entity.setFlying(true);
         }

         if (this.entity.attackTicks == 20) {
            this.entity.m_20334_(0.0, 0.0, 0.0);
         }

         if (this.entity.attackTicks == 60) {
            this.entity.setFlying(false);
         }

         if (this.entity.attackTicks == this.attackshot && target != null) {
            double arrowcount = 4.0;
            double offsetangle = Math.toRadians(9.0);
            double d1 = target.m_20185_() - this.entity.m_20185_();
            double d2 = target.m_20227_(0.3333333333333333) - this.entity.m_20186_();
            double d3 = target.m_20189_() - this.entity.m_20189_();

            for (int i = 0; (double)i <= arrowcount - 1.0; i++) {
               double angle = ((double)i - (arrowcount - 1.0) / 2.0) * offsetangle;
               double x = d1 * Math.cos(angle) + d3 * Math.sin(angle);
               double z = -d1 * Math.sin(angle) + d3 * Math.cos(angle);
               double distance = Math.sqrt(x * x + z * z);
               Phantom_Arrow_Entity throwntrident = new Phantom_Arrow_Entity(this.entity.f_19853_, this.entity, target);
               throwntrident.m_36781_(
                  CMConfig.MaledictusPhantomArrowbasedamage + CMConfig.MaledictusPhantomArrowbasedamage * (double)this.entity.getRageMeter() * 0.05F
               );
               throwntrident.m_6686_(x, d2 + distance * 0.15F, z, 1.5F, 1.0F);
               this.entity.m_5496_(SoundEvents.f_11847_, 1.0F, 1.0F / (this.entity.m_217043_().m_188501_() * 0.4F + 0.8F));
               this.entity.f_19853_.m_7967_(throwntrident);
            }
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class Maledictus_Flying_Smash extends InternalAttackGoal {
      private final Maledictus_Entity entity;
      private final int attackstrike;
      private final float random;
      private final double dropspeed;
      private final int startweapon;
      private final int stopweapon;

      public Maledictus_Flying_Smash(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         int startweapon,
         int stopweapon,
         float random,
         double dropspeed
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackstrike = attackshot;
         this.random = random;
         this.dropspeed = dropspeed;
         this.startweapon = startweapon;
         this.stopweapon = stopweapon;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return super.m_8036_() && target != null && this.entity.m_217043_().m_188501_() * 100.0F < this.random && this.entity.flyattack_cooldown <= 0;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(this.startweapon);
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.setFlying(false);
         this.entity.setWeapon(this.stopweapon);
         this.entity.flyattack_cooldown = 100;
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackstrike && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 0.0F);
            this.entity.m_21391_(target, 30.0F, 0.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.attackTicks == 25) {
            this.entity.m_20334_(0.0, 0.9, 0.0);
            if (target != null) {
               double d1 = target.m_20186_() - this.entity.m_20186_();
               this.entity.m_20334_(0.0, 0.9 + Mth.m_14008_(d1 * 0.075, 0.0, 7.0), 0.0);
            } else {
               this.entity.m_20334_(0.0, 0.9, 0.0);
            }

            this.entity.setFlying(true);
         }

         if (this.entity.attackTicks == this.attackstrike) {
            this.entity.setFlying(false);
            if (target != null) {
               double Y = this.dropspeed * Math.abs(this.entity.m_20186_() - target.m_20186_());
               double Z = 0.1F * (target.m_20189_() - this.entity.m_20189_());
               double X = 0.1F * (target.m_20185_() - this.entity.m_20185_());
               this.entity.m_20256_(this.entity.m_20184_().m_82520_(X, -1.0 * Y, Z));
            }
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class Maledictus_Swing extends InternalAttackGoal {
      private final float attackminrange;
      private final float random;

      public Maledictus_Swing(
         Maledictus_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackminrange,
         float attackrange,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.attackminrange = attackminrange;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return super.m_8036_()
            && target != null
            && this.entity.m_20270_(target) > this.attackminrange
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random;
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         super.m_8037_();
         if (this.entity.attackTicks == 5) {
            if (target != null) {
               double d0 = target.m_20185_() - this.entity.m_20185_();
               double d1 = target.m_20186_() - this.entity.m_20186_();
               double d2 = target.m_20189_() - this.entity.m_20189_();
               Vec3 vec3 = new Vec3(d0, 0.7 + Mth.m_14008_(d1 * 0.075, 0.0, 10.0), d2).m_82542_(0.2, 1.0, 0.2);
               this.entity.m_20256_(vec3);
            } else {
               Vec3 vec3 = new Vec3(0.0, 0.7, 0.0);
               this.entity.m_20256_(vec3);
            }
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class MaledictusfallingState extends InternalStateGoal {
      private final Maledictus_Entity entity;
      private final int startbow;
      private final int stopbow;
      private final int attackseetick;

      public MaledictusfallingState(
         Maledictus_Entity entity, int getAttackState, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, int startbow, int stopbow
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick);
         this.entity = entity;
         this.attackseetick = attackseetick;
         this.startbow = startbow;
         this.stopbow = stopbow;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         this.entity.setWeapon(this.startbow);
         if (this.entity.isFlying()) {
            this.entity.setFlying(false);
         }
      }

      @Override
      public void m_8037_() {
         this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 0.0F);
            this.entity.m_21391_(target, 30.0F, 0.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.flyattack_cooldown = 100;
         this.entity.setWeapon(this.stopbow);
      }
   }

   static class Uppercut extends Goal {
      protected final Maledictus_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final float attackrange;
      private final float random;

      public Uppercut(
         Maledictus_Entity entity,
         int getattackstate,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         float random
      ) {
         this.entity = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackseetick = attackseetick;
         this.attackrange = attackrange;
         this.random = random;
      }

      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return target != null
            && target.m_6084_()
            && this.entity.m_20270_(target) < this.attackrange
            && this.entity.getAttackState() == this.getattackstate
            && this.entity.m_21574_().m_148306_(target)
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.uppercut_cooldown <= 0;
      }

      public void m_8056_() {
         this.entity.setAttackState(this.attackstate);
      }

      public void m_8041_() {
         this.entity.uppercut_cooldown = 80;
         this.entity.setAttackState(this.attackendstate);
      }

      public boolean m_8045_() {
         return this.entity.attackTicks <= this.attackMaxtick && this.entity.getAttackState() == this.attackstate;
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.m_21563_().m_24960_(target, 60.0F, 30.0F);
            this.entity.m_21391_(target, 60.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }
      }

      public boolean m_183429_() {
         return false;
      }
   }
}
