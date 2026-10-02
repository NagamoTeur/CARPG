package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AI.AnimalAIRandomSwimming;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.AI.MobAIFindWater;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.LLibrary_Boss_Monster;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.etc.ISemiAquatic;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.partentity.Cm_Part_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.message.MessageMusic;
import com.github.L_Ender.cataclysm.world.data.CMWorldData;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;

public class The_Leviathan_Entity extends LLibrary_Boss_Monster implements ISemiAquatic, IHoldEntity {
   public static final Animation LEVIATHAN_GRAB = Animation.create(160);
   public static final Animation LEVIATHAN_GRAB_BITE = Animation.create(13);
   public static final Animation LEVIATHAN_BITE = Animation.create(24);
   public static final Animation LEVIATHAN_ABYSS_BLAST = Animation.create(184);
   public static final Animation LEVIATHAN_ABYSS_BLAST_FIRE = Animation.create(216);
   public static final Animation LEVIATHAN_RUSH = Animation.create(157);
   public static final Animation LEVIATHAN_STUN = Animation.create(90);
   public static final Animation LEVIATHAN_ABYSS_BLAST_PORTAL = Animation.create(142);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_UPPER_R = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_LOWER_R = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_UPPER_L = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_LOWER_L = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_HOLD = Animation.create(63);
   public static final Animation LEVIATHAN_TENTACLE_HOLD_BLAST = Animation.create(189);
   public static final Animation LEVIATHAN_TAIL_WHIPS = Animation.create(42);
   public static final Animation LEVIATHAN_BREAK_DIMENSION = Animation.create(156);
   public static final Animation LEVIATHAN_PHASE2 = Animation.create(200);
   public static final Animation LEVIATHAN_DEATH = Animation.create(210);
   public static final Animation LEVIATHAN_MINE = Animation.create(68);
   public final The_Leviathan_Part headPart;
   public final The_Leviathan_Part tailPart1;
   public final The_Leviathan_Part tailPart2;
   public final The_Leviathan_Part[] leviathanParts;
   public boolean blocksBylefttentacle = true;
   public boolean blocksByrighttentacle = true;
   public boolean CanGrab = true;
   public boolean CanAbyss_Blast = true;
   public boolean CanRush = true;
   public boolean CanTailWhips = true;
   public boolean CanBite = true;
   public boolean CanAbyss_Blast_Portal = true;
   public boolean CanCrackDimension = true;
   public boolean CanMine = true;
   public float NoSwimProgress = 0.0F;
   public float prevNoSwimProgress = 0.0F;
   public float LeftTentacleProgress = 0.0F;
   public float prevLeftTentacleProgress = 0.0F;
   public float RightTentacleProgress = 0.0F;
   public float prevRightTentacleProgress = 0.0F;
   private boolean isLandNavigator;
   public Vec3 teleportPos = null;
   public Abyss_Portal_Entity portalTarget = null;
   public boolean fullyThrough = true;
   public static final int GRAB_HUNTING_COOLDOWN = 70;
   public static final int RUSH_HUNTING_COOLDOWN = 100;
   public static final int BLAST_HUNTING_COOLDOWN = 140;
   public static final int CRACK_HUNTING_COOLDOWN = 250;
   public static final int BLAST_PORTAL_HUNTING_COOLDOWN = 120;
   public static final int TAIL_WHIPS_HUNTING_COOLDOWN = 100;
   public static final int BITE_COOLDOWN = 100;
   public static final int MELEE_COOLDOWN = 40;
   public static final int MINE_COOLDOWN = 100;
   public static final int NATURE_HEAL_COOLDOWN = 200;
   private int timeWithoutTarget;
   private The_Leviathan_Entity.AttackMode mode = The_Leviathan_Entity.AttackMode.CIRCLE;
   private int hunting_cooldown = 160;
   private int makePortalCooldown = 0;
   private int bite_cooldown = 0;
   private int melee_cooldown = 0;
   private int waterstillTicks = 0;
   public double endPosX;
   public double endPosY;
   public double endPosZ;
   public double collidePosX;
   public double collidePosY;
   public double collidePosZ;
   private int destroyBlocksTick;
   private int blockBreakCounter;
   public float LayerBrightness;
   private Vec3 prevTailPos = new Vec3(0.0, 0.0, 0.0);
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.m_5446_(), BossBarColor.PURPLE, false, 5);
   private static final EntityDataAccessor<Integer> BLAST_CHANCE = SynchedEntityData.m_135353_(The_Leviathan_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> MODE_CHANCE = SynchedEntityData.m_135353_(The_Leviathan_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> MELT_DOWN = SynchedEntityData.m_135353_(The_Leviathan_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Optional<UUID>> TONGUE_UUID = SynchedEntityData.m_135353_(
      The_Leviathan_Entity.class, EntityDataSerializers.f_135041_
   );
   private static final EntityDataAccessor<Integer> TONGUE_ID = SynchedEntityData.m_135353_(The_Leviathan_Entity.class, EntityDataSerializers.f_135028_);

   public The_Leviathan_Entity(EntityType type, Level worldIn) {
      super(type, worldIn);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_21364_ = 500;
      if (worldIn.f_46443_) {
         this.socketPosArray = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }

      this.headPart = new The_Leviathan_Part(this, 2.8F, 2.8F);
      this.tailPart1 = new The_Leviathan_Part(this, 1.5F, 2.4F);
      this.tailPart2 = new The_Leviathan_Part(this, 1.3F, 2.4F);
      this.leviathanParts = new The_Leviathan_Part[]{this.headPart, this.tailPart1, this.tailPart2};
      this.switchNavigator(false);
      setConfigattribute(this, CMConfig.LeviathanHealthMultiplier, CMConfig.LeviathanDamageMultiplier);
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public static Builder leviathan() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22276_, 400.0)
         .m_22268_(Attributes.f_22277_, 64.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22281_, 15.0)
         .m_22268_(Attributes.f_22278_, 1.0)
         .m_22268_(Attributes.f_22279_, 0.15);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new WaterBoundPathNavigation(this, worldIn);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(BLAST_CHANCE, 0);
      this.f_19804_.m_135372_(MODE_CHANCE, 0);
      this.f_19804_.m_135372_(MELT_DOWN, false);
      this.f_19804_.m_135372_(TONGUE_UUID, Optional.empty());
      this.f_19804_.m_135372_(TONGUE_ID, -1);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new MobAIFindWater(this, 2.0));
      this.f_21345_.m_25352_(3, new The_Leviathan_Entity.LeviathanAttackGoal(this));
      this.f_21345_.m_25352_(4, new AnimalAIRandomSwimming(this, 1.0, 3, 15));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanGrabAttackGoal(this, LEVIATHAN_GRAB));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanAbyssDimensionAttackGoal(this, LEVIATHAN_BREAK_DIMENSION));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanStunGoal(this, LEVIATHAN_STUN));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanGrabBiteAttackGoal(this, LEVIATHAN_GRAB_BITE));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanBiteAttackGoal(this, LEVIATHAN_BITE));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanPhase2Goal(this, LEVIATHAN_PHASE2));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanTailWhipsAttackGoal(this, LEVIATHAN_TAIL_WHIPS));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanTentacleAttackGoal(this));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanBlastAttackGoal(this, LEVIATHAN_ABYSS_BLAST));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanBlastFireAttackGoal(this, LEVIATHAN_ABYSS_BLAST_FIRE));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanAbyssBlastPortalAttackGoal(this, LEVIATHAN_ABYSS_BLAST_PORTAL));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanRushAttackGoal(this, LEVIATHAN_RUSH));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanTentacleHoldAttackGoal(this, LEVIATHAN_TENTACLE_HOLD));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanTentacleHoldBlastAttackGoal(this, LEVIATHAN_TENTACLE_HOLD_BLAST));
      this.f_21345_.m_25352_(0, new The_Leviathan_Entity.LeviathanMineAttackGoal(this, LEVIATHAN_MINE));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new EntityAINearestTarget3D(this, Player.class, false, true));
      this.f_21346_
         .m_25352_(
            3,
            new EntityAINearestTarget3D<LivingEntity>(this, LivingEntity.class, 160, false, true, ModEntities.buildPredicateFromTag(ModTag.LEVIATHAN_TARGET))
         );
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new CMPathNavigateGround(this, this.f_19853_);
         this.isLandNavigator = true;
      } else {
         this.f_21342_ = new The_Leviathan_Entity.LeviathanMoveController(this, 10.0F, 1.0F, 6.0F);
         this.f_21344_ = new WaterBoundPathNavigation(this, this.f_19853_);
         this.isLandNavigator = false;
      }
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         LEVIATHAN_PHASE2,
         LEVIATHAN_BITE,
         LEVIATHAN_BREAK_DIMENSION,
         LEVIATHAN_GRAB,
         LEVIATHAN_TAIL_WHIPS,
         LEVIATHAN_ABYSS_BLAST_PORTAL,
         LEVIATHAN_GRAB_BITE,
         LEVIATHAN_ABYSS_BLAST,
         LEVIATHAN_RUSH,
         LEVIATHAN_TENTACLE_STRIKE_UPPER_R,
         LEVIATHAN_TENTACLE_STRIKE_UPPER_L,
         LEVIATHAN_TENTACLE_STRIKE_LOWER_L,
         LEVIATHAN_TENTACLE_STRIKE_LOWER_R,
         LEVIATHAN_STUN,
         LEVIATHAN_DEATH,
         LEVIATHAN_TENTACLE_HOLD,
         LEVIATHAN_TENTACLE_HOLD_BLAST,
         LEVIATHAN_ABYSS_BLAST_FIRE,
         LEVIATHAN_MINE
      };
   }

   public void m_7023_(Vec3 travelVector) {
      if (this.m_6142_() && this.m_20069_()) {
         this.m_19920_(this.m_6113_(), travelVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9));
         if (this.m_5448_() == null && this.getAnimation() == NO_ANIMATION) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
         }
      } else {
         super.m_7023_(travelVector);
      }
   }

   private static Animation getRandomTantalcleStrike(RandomSource rand) {
      switch (rand.m_188503_(4)) {
         case 0:
            return LEVIATHAN_TENTACLE_STRIKE_LOWER_L;
         case 1:
            return LEVIATHAN_TENTACLE_STRIKE_LOWER_R;
         case 2:
            return LEVIATHAN_TENTACLE_STRIKE_UPPER_L;
         case 3:
            return LEVIATHAN_TENTACLE_STRIKE_UPPER_R;
         default:
            return LEVIATHAN_TENTACLE_STRIKE_UPPER_R;
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_THE_LEVIATHAN) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      Entity entity = source.m_7640_();
      if (!(entity instanceof Abyss_Blast_Entity) && !(entity instanceof Portal_Abyss_Blast_Entity)) {
         if (this.destroyBlocksTick <= 0) {
            this.destroyBlocksTick = 20;
         }

         double range = this.calculateRange(source);
         if (range > CMConfig.LeviathanLongRangelimit * CMConfig.LeviathanLongRangelimit && !source.m_19378_()) {
            return false;
         } else {
            boolean flag1 = this.canInFluidType(this.getEyeInFluidType());
            if (!flag1 && !source.m_19378_() && CMConfig.LeviathanImmuneOutofWater) {
               if (entity instanceof Player player) {
                  player.m_5661_(Component.m_237115_("entity.cataclysm.the_leviathan_immune"), true);
               }

               return false;
            } else if (this.getAnimation() == LEVIATHAN_PHASE2 && !source.m_19378_()) {
               return false;
            } else {
               boolean attack = super.m_6469_(source, damage);
               if (this.getAnimation() == LEVIATHAN_RUSH && this.getAnimationTick() >= 38 && this.getAnimationTick() <= 54 && attack) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, LEVIATHAN_STUN);
               }

               return attack;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.LeviathanDamageCap;
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   private boolean canInFluidType(FluidType type) {
      ForgeMod.WATER_TYPE.get();
      return type.canSwim(this.self());
   }

   public void m_20321_(boolean p_20322_) {
   }

   public void m_6845_(boolean p_20313_) {
   }

   public boolean m_6673_(DamageSource source) {
      return source == DamageSource.f_19310_ || source == DamageSource.f_19322_ || super.m_6673_(source) || source.m_146707_();
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public boolean attackEntityFromPart(The_Leviathan_Part leviathan_part, DamageSource source, float amount) {
      return this.m_6469_(source, amount);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.m_146922_(this.f_20885_);
      if (this.m_20069_() && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!this.m_20069_() && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (this.getTongue() instanceof The_Leviathan_Tongue_Entity magneticWeapon) {
         this.f_19804_.m_135381_(TONGUE_ID, magneticWeapon.m_19879_());
         magneticWeapon.setControllerUUID(this.m_20148_());
      }

      if (!this.m_20197_().isEmpty() && ((Entity)this.m_20197_().get(0)).m_6144_() && this.getAnimation() == LEVIATHAN_TENTACLE_HOLD_BLAST) {
         ((Entity)this.m_20197_().get(0)).m_20260_(false);
      }

      LivingEntity target = this.m_5448_();
      if (!this.f_19853_.f_46443_) {
         float halfHealth = this.m_21233_() / 2.0F;
         if (!this.getMeltDown()) {
            this.bossInfo.m_142711_((this.m_21223_() - halfHealth) / (this.m_21233_() - halfHealth));
         } else {
            this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
            this.bossInfo.setRenderType(6);
         }

         if (this.timeWithoutTarget > 0) {
            this.timeWithoutTarget--;
         }

         if (target != null) {
            this.timeWithoutTarget = 200;
         }

         if (this.getAnimation() == NO_ANIMATION
            && this.timeWithoutTarget <= 0
            && !this.m_21525_()
            && CMConfig.LeviathanNatureHealing > 0.0
            && this.f_19797_ % 20 == 0) {
            this.m_5634_((float)CMConfig.LeviathanNatureHealing);
         }
      }

      boolean groundAnimate = !this.m_20069_();
      this.prevNoSwimProgress = this.NoSwimProgress;
      if (groundAnimate) {
         if (this.NoSwimProgress < 10.0F) {
            this.NoSwimProgress++;
         }
      } else if (this.NoSwimProgress > 0.0F) {
         this.NoSwimProgress--;
      }

      this.prevLeftTentacleProgress = this.LeftTentacleProgress;
      this.prevRightTentacleProgress = this.RightTentacleProgress;
      if (this.blocksByrighttentacle) {
         if (this.RightTentacleProgress < 10.0F) {
            this.RightTentacleProgress++;
         }
      } else if (this.RightTentacleProgress > 0.0F) {
         this.RightTentacleProgress--;
      }

      if (this.blocksBylefttentacle) {
         if (this.LeftTentacleProgress < 10.0F) {
            this.LeftTentacleProgress++;
         }
      } else if (this.LeftTentacleProgress > 0.0F) {
         this.LeftTentacleProgress--;
      }

      if (this.f_19797_ % 10 == 0) {
         this.blocksByrighttentacle = this.checkBlocksByTentacle(1.0F, -3.0F)
            || this.checkBlocksByTentacle(2.0F, -3.0F)
            || this.checkBlocksByTentacle(3.0F, -3.0F)
            || this.checkBlocksByTentacle(4.0F, -3.0F)
            || this.checkBlocksByTentacle(5.0F, -3.0F);
         this.blocksBylefttentacle = this.checkBlocksByTentacle(1.0F, 3.0F)
            || this.checkBlocksByTentacle(2.0F, 3.0F)
            || this.checkBlocksByTentacle(3.0F, 3.0F)
            || this.checkBlocksByTentacle(4.0F, 3.0F)
            || this.checkBlocksByTentacle(5.0F, 3.0F);
      }

      if (this.portalTarget != null && this.portalTarget.getLifespan() < 5) {
         this.portalTarget = null;
      }

      if (this.teleportPos != null) {
         this.m_6034_(this.teleportPos.f_82479_, this.teleportPos.f_82480_, this.teleportPos.f_82481_);
         this.teleportPos = null;
      }

      if (this.makePortalCooldown > 0) {
         this.makePortalCooldown--;
      }

      if (this.hunting_cooldown > 0) {
         this.hunting_cooldown--;
      }

      if (this.bite_cooldown > 0) {
         this.bite_cooldown--;
      }

      if (this.melee_cooldown > 0) {
         this.melee_cooldown--;
      }

      if (!this.m_21525_()) {
         if (this.getAnimation() == NO_ANIMATION && !this.getMeltDown() && this.isMeltDown() && this.m_6084_()) {
            this.setAnimation(LEVIATHAN_PHASE2);
         }

         if (this.getAnimation() == NO_ANIMATION && this.m_5448_() != null) {
            if (Math.abs(this.f_19854_ - this.m_20185_()) < 0.01F
               && Math.abs(this.f_19855_ - this.m_20186_()) < 0.01F
               && Math.abs(this.f_19856_ - this.m_20189_()) < 0.01F) {
               this.waterstillTicks++;
            } else {
               this.waterstillTicks = 0;
            }

            if (this.waterstillTicks > 40 && this.makePortalCooldown == 0) {
               this.createStuckPortal();
            }
         }

         if (!this.f_19853_.f_46443_) {
            if (this.destroyBlocksTick > 0) {
               this.destroyBlocksTick--;
               if (this.destroyBlocksTick == 0) {
                  if (CMConfig.LeviathanBlockBreaking) {
                     this.blockbreak(0.5, 0.5, 0.5);
                  } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
                     this.blockbreak(0.5, 0.5, 0.5);
                  }
               }
            }

            if (this.mode == The_Leviathan_Entity.AttackMode.MELEE) {
               if (CMConfig.LeviathanBlockBreaking) {
                  this.blockbreak2();
               } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
                  this.blockbreak2();
               }
            }
         }

         float f17 = this.m_146908_() * (float) Math.PI / 180.0F;
         float pitch = this.m_146909_() * (float) Math.PI / 180.0F;
         float f3 = Mth.m_14031_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         float f18 = Mth.m_14089_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         this.setPartPosition(this.headPart, (double)(f3 * -3.8F), (double)(-pitch * 3.0F), (double)(-f18 * -3.8F));
         this.setPartPosition(this.tailPart1, (double)(f3 * 3.3F), (double)(-pitch * -5.0F), (double)(-f18 * 3.3F));
         this.setPartPosition(this.tailPart2, (double)(f3 * 4.7F), (double)(-pitch * -8.0F), (double)(-f18 * 4.7F));
         Vec3[] avector3d = new Vec3[this.leviathanParts.length];

         for (int j = 0; j < this.leviathanParts.length; j++) {
            avector3d[j] = new Vec3(this.leviathanParts[j].m_20185_(), this.leviathanParts[j].m_20186_(), this.leviathanParts[j].m_20189_());
         }

         for (int l = 0; l < this.leviathanParts.length; l++) {
            this.leviathanParts[l].f_19854_ = avector3d[l].f_82479_;
            this.leviathanParts[l].f_19855_ = avector3d[l].f_82480_;
            this.leviathanParts[l].f_19856_ = avector3d[l].f_82481_;
            this.leviathanParts[l].f_19790_ = avector3d[l].f_82479_;
            this.leviathanParts[l].f_19791_ = avector3d[l].f_82480_;
            this.leviathanParts[l].f_19792_ = avector3d[l].f_82481_;
         }
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAnimation() == LEVIATHAN_ABYSS_BLAST) {
         if (this.getAnimationTick() < 30 && this.f_19853_.f_46443_) {
            for (int i = 0; i < 20; i++) {
               float f = -Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.m_146909_() * (float) (Math.PI / 180.0));
               float f2 = -Mth.m_14031_(this.m_146909_() * (float) (Math.PI / 180.0));
               float f3 = Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.m_146909_() * (float) (Math.PI / 180.0));
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123760_,
                     this.m_20185_() + (double)f * 4.0,
                     this.m_20186_() + (double)f2 * 3.5,
                     this.m_20189_() + (double)f3 * 4.0,
                     (this.f_19796_.m_188500_() - 0.5) * 2.0,
                     -this.f_19796_.m_188500_(),
                     (this.f_19796_.m_188500_() - 0.5) * 2.0
                  );
            }
         }

         if (this.getAnimationTick() >= 82) {
            this.m_146926_(this.f_19860_);
         }

         if (this.getAnimationTick() == 84 && this.getMeltDown()) {
            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, -1.25, 0.5).m_82524_(-((float)(120 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, 0.0, 1.0).m_82524_(-((float)(60 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, 1.25, 0.5).m_82524_(-((float)(120 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }
         }

         int i = 44;

         for (int j = 0; i <= 84; j++) {
            float l = (float)j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 144;

         for (int j = 1; i <= 184; j++) {
            float l = (float)j * -0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         if (this.getAnimationTick() == 84) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 90, 10);
         }
      }

      if (this.getAnimation() == LEVIATHAN_ABYSS_BLAST_FIRE) {
         if (this.getAnimationTick() < 30 && this.f_19853_.f_46443_) {
            for (int i = 0; i < 20; i++) {
               float f = -Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.m_146909_() * (float) (Math.PI / 180.0));
               float f2 = -Mth.m_14031_(this.m_146909_() * (float) (Math.PI / 180.0));
               float f3 = Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.m_146909_() * (float) (Math.PI / 180.0));
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123760_,
                     this.m_20185_() + (double)f * 4.0,
                     this.m_20186_() + (double)f2 * 3.5,
                     this.m_20189_() + (double)f3 * 4.0,
                     (this.f_19796_.m_188500_() - 0.5) * 2.0,
                     -this.f_19796_.m_188500_(),
                     (this.f_19796_.m_188500_() - 0.5) * 2.0
                  );
            }
         }

         if (this.getAnimationTick() == 84 || this.getAnimationTick() == 129 || this.getAnimationTick() == 174) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.ABYSS_BLAST_ONLY_SHOOT.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         }

         if (this.getAnimationTick() >= 80 && this.getAnimationTick() <= 118
            || this.getAnimationTick() >= 125 && this.getAnimationTick() <= 163
            || this.getAnimationTick() >= 170 && this.getAnimationTick() <= 198) {
            this.m_146926_(this.f_19860_);
         }

         int i = 44;

         for (int j = 0; i <= 84; j++) {
            float l = (float)j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 176;

         for (int j = 1; i <= 216; j++) {
            float l = (float)j * -0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         if (this.getAnimationTick() == 84 || this.getAnimationTick() == 129 || this.getAnimationTick() == 174) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 20, 10);
         }
      }

      if (this.getAnimation() == LEVIATHAN_RUSH) {
         if (this.getAnimationTick() > 54 && this.getAnimationTick() < 137) {
            this.chargeDamage();
            if (!this.f_19853_.f_46443_) {
               if (CMConfig.LeviathanBlockBreaking) {
                  this.chargeblockbreaking();
               } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
                  this.chargeblockbreaking();
               }
            }
         }

         if (this.getAnimationTick() == 54) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_ROAR.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 60, 10);
            this.roarDarkness(48.0, 48.0, 48.0, 48.0, 20);
         }
      }

      if (this.getAnimation() == LEVIATHAN_GRAB_BITE && this.getAnimationTick() == 2) {
         this.biteattack(6.5F, 1.5, 1.5, 1.5, 100);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_UPPER_R) {
         this.Tentacleattack(24, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_LOWER_R) {
         this.Tentacleattack(28, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_UPPER_L) {
         this.Tentacleattack(26, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_LOWER_L) {
         this.Tentacleattack(21, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_STUN && this.getAnimationTick() == 52) {
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_STUN_ROAR.get(), SoundSource.HOSTILE, 4.0F, 0.8F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 40, 10);
         if (this.getMeltDown()) {
            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, -1.25, 0.5).m_82524_(-((float)(120 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, 0.0, 1.0).m_82524_(-((float)(60 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, 1.25, 0.5).m_82524_(-((float)(120 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }
         }
      }

      if (this.getAnimation() == LEVIATHAN_ABYSS_BLAST_PORTAL) {
         if (this.getAnimationTick() == 56) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_ROAR.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 60, 10);
            this.roarDarkness(48.0, 48.0, 48.0, 48.0, 80);
         }

         int i = 16;

         for (int j = 0; i <= 56; j++) {
            float l = (float)j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 102;

         for (int j = 1; i <= 142; j++) {
            float l = (float)j * -0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }
      }

      this.SwingParticles();
      if (this.getAnimation() == LEVIATHAN_TAIL_WHIPS && this.getAnimationTick() == 18) {
         this.TailWhips();
      }

      if (this.getAnimation() == LEVIATHAN_BREAK_DIMENSION) {
         if (this.getAnimationTick() == 24) {
            this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12555_, SoundSource.HOSTILE, 1.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.05F, 10, 10);
         }

         if (this.getAnimationTick() == 62) {
            this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12555_, SoundSource.HOSTILE, 2.0F, 1.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 10, 10);

            for (Entity entity : this.f_19853_.m_45933_(this, this.m_20191_().m_82400_(15.0))) {
               if (entity instanceof Dimensional_Rift_Entity) {
                  Dimensional_Rift_Entity rift = (Dimensional_Rift_Entity)entity;
                  if (rift.getStage() < 4) {
                     rift.setStage(rift.getStage() + 1);
                  }
               }
            }
         }

         if (this.getAnimationTick() == 94) {
            this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12555_, SoundSource.HOSTILE, 3.0F, 1.2F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.15F, 10, 10);

            for (Entity entityx : this.f_19853_.m_45933_(this, this.m_20191_().m_82400_(15.0))) {
               if (entityx instanceof Dimensional_Rift_Entity) {
                  Dimensional_Rift_Entity rift = (Dimensional_Rift_Entity)entityx;
                  if (rift.getStage() < 4) {
                     rift.setStage(rift.getStage() + 1);
                  }
               }
            }
         }

         if (this.getAnimationTick() == 126) {
            this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12555_, SoundSource.HOSTILE, 3.0F, 1.3F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.2F, 10, 10);

            for (Entity entityxx : this.f_19853_.m_45933_(this, this.m_20191_().m_82400_(15.0))) {
               if (entityxx instanceof Dimensional_Rift_Entity) {
                  Dimensional_Rift_Entity rift = (Dimensional_Rift_Entity)entityxx;
                  if (rift.getStage() < 4) {
                     rift.setStage(rift.getStage() + 1);
                  }
               }
            }
         }
      }

      if (this.getAnimation() == LEVIATHAN_BITE) {
         if (this.getAnimationTick() == 11) {
            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_BITE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         if (this.getAnimationTick() == 13) {
            this.biteattack(6.0F, 1.5, 1.5, 1.5, 100);
         }
      }

      if (this.getAnimation() == LEVIATHAN_PHASE2) {
         if (this.getAnimationTick() == 1 && !this.f_19853_.f_46443_ && this.getBossMusic() != null) {
            Cataclysm.sendMSGToAll(new MessageMusic(this.m_19879_(), false));
         }

         if (this.getAnimationTick() == 90) {
            if (!this.getMeltDown()) {
               this.setMeltDown(true);
            }

            if (!this.f_19853_.f_46443_ && this.getBossMusic() != null) {
               Cataclysm.sendMSGToAll(new MessageMusic(this.m_19879_(), true));
            }

            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, -1.25, 0.5).m_82524_(-((float)(120 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, -0.75, 1.0).m_82524_(-((float)(60 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, 0.0, 1.0).m_82524_(-((float)(60 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, 0.75, 1.0).m_82524_(-((float)(60 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, 1.25, 0.5).m_82524_(-((float)(120 * i)) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }

            this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_STUN_ROAR.get(), SoundSource.HOSTILE, 3.0F, 0.8F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.1F, 40, 10);
         }

         if (this.getAnimationTick() >= 70 && this.getAnimationTick() <= 80) {
            this.Sphereparticle(this.m_20192_(), 0.0F, 8.0F);
         }
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_HOLD && this.getAnimationTick() == 32) {
         this.TentacleHoldattack(7.0F, 2.5, 2.5, 2.5, 80);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_HOLD_BLAST) {
         int i = 44;

         for (int j = 0; i <= 84; j++) {
            float l = (float)j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 149;

         for (int j = 1; i <= 189; j++) {
            float l = (float)j * -0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }
      }

      if (this.getAnimation() == LEVIATHAN_MINE && this.getAnimationTick() == 31) {
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_STUN_ROAR.get(), SoundSource.HOSTILE, 3.0F, 0.8F);
      }
   }

   private void roarDarkness(double x, double y, double z, double radius, int time) {
      for (LivingEntity inRange : this.getEntityLivingBaseNearby(x, y, z, radius)) {
         if ((!(inRange instanceof Player) || !((Player)inRange).m_150110_().f_35934_) && !this.m_7307_(inRange)) {
            inRange.m_7292_(new MobEffectInstance(MobEffects.f_216964_, time));
         }
      }
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (living != null) {
         CMWorldData worldData = CMWorldData.get(this.f_19853_, Level.f_46428_);
         if (worldData != null) {
            boolean prev = worldData.isLeviathanDefeatedOnce();
            if (!prev) {
               worldData.setLeviathanDefeatedOnce(true);
               if (this.f_19853_ instanceof ServerLevel serverLevel) {
                  serverLevel.m_8795_(EntitySelector.f_20408_)
                     .forEach(
                        serverPlayer -> serverPlayer.m_5661_(
                              Component.m_237115_("entity.cataclysm.the_leviathan.defeat_message").m_130940_(ChatFormatting.DARK_PURPLE), true
                           )
                     );
               }
            }
         }
      }
   }

   private void TailWhips() {
      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(7.0, 7.0, 7.0))) {
         if (!this.m_7307_(entity) && !(entity instanceof The_Leviathan_Entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(
               damagesource,
               (float)(
                  (double)((float)this.m_21133_(Attributes.f_22281_))
                     + Math.min(this.m_21133_(Attributes.f_22281_), (double)entity.m_21233_() * CMConfig.LeviathanTailSwingHpdamage)
               )
            );
            if (entity.m_21275_(damagesource) && entity instanceof Player player) {
               this.disableShield(player, 120);
            }

            if (flag) {
               this.launch(entity, true);
               entity.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTBONE_FRACTURE.get(), 200));
            }
         }
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
                  this.f_19853_.m_7106_(ParticleTypes.f_123789_, d0 + (double)vec * vecX, d1, d2 + (double)vec * vecZ, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2.0F - 1.0F;
                  }
               }
            }
         }
      }
   }

   public boolean isMeltDown() {
      return this.m_21223_() <= this.m_21233_() / 2.0F;
   }

   public void shootAbyssOrb(double xMotion, double yMotion, double zMotion) {
      if (this.m_5448_() != null) {
         Abyss_Orb_Entity fireball = new Abyss_Orb_Entity(this, xMotion, yMotion, zMotion, this.f_19853_, (float)CMConfig.AbyssOrbdamage, this.m_5448_());
         fireball.m_6034_(fireball.m_20185_(), this.m_20188_(), fireball.m_20189_());
         fireball.setUp(40);
         if (!this.f_19853_.f_46443_) {
            this.f_19853_.m_7967_(fireball);
         }
      } else {
         Abyss_Orb_Entity fireball = new Abyss_Orb_Entity(this, xMotion, yMotion, zMotion, this.f_19853_, (float)CMConfig.AbyssOrbdamage, null);
         fireball.m_6034_(fireball.m_20185_(), this.m_20188_(), fireball.m_20189_());
         fireball.setUp(40);
         if (!this.f_19853_.f_46443_) {
            this.f_19853_.m_7967_(fireball);
         }
      }
   }

   private void launch(Entity e, boolean huge) {
      double d0 = e.m_20185_() - this.m_20185_();
      double d1 = e.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      float f = huge ? 10.0F : 5.0F;
      e.m_5997_(d0 / d2 * (double)f, huge ? 1.0 : 0.2F, d1 / d2 * (double)f);
   }

   private void Tailswing() {
      Vec3 bladePos = this.socketPosArray[0];
      double length = this.prevTailPos.m_82546_(bladePos).m_82553_();
      int numClouds = (int)Math.floor(2.0 * length);

      for (int i = 0; i < numClouds; i++) {
         double x = this.prevTailPos.f_82479_ + (double)i * (bladePos.f_82479_ - this.prevTailPos.f_82479_) / (double)numClouds;
         double y = this.prevTailPos.f_82480_ + (double)i * (bladePos.f_82480_ - this.prevTailPos.f_82480_) / (double)numClouds;
         double z = this.prevTailPos.f_82481_ + (double)i * (bladePos.f_82481_ - this.prevTailPos.f_82481_) / (double)numClouds;
         ParticleOptions type = ParticleTypes.f_123813_;
         this.f_19853_.m_7106_(type, x, y, z, 0.0, 0.0, 0.0);
      }
   }

   private void SwingParticles() {
      if (this.f_19853_.f_46443_) {
         Vec3 bladePos = this.socketPosArray[0];
         if (this.getAnimation() == LEVIATHAN_TAIL_WHIPS && this.getAnimationTick() >= 10 && this.getAnimationTick() <= 34) {
            this.Tailswing();
         }

         this.prevTailPos = bladePos;
      }
   }

   private void blockbreak(double x, double y, double z) {
      boolean flag = false;
      AABB aabb = this.m_20191_().m_82377_(x, y, z);

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
            && !blockstate.m_204336_(ModTag.LEVIATHAN_IMMUNE)
            && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
            flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
         }
      }
   }

   private void blockbreak2() {
      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      } else {
         boolean flag = false;
         AABB aabb = this.m_20191_().m_82400_(0.2);

         for (BlockPos pos : BlockPos.m_121976_(
            Mth.m_14107_(aabb.f_82288_),
            Mth.m_14107_(this.m_20186_()),
            Mth.m_14107_(aabb.f_82290_),
            Mth.m_14107_(aabb.f_82291_),
            Mth.m_14107_(aabb.f_82292_),
            Mth.m_14107_(aabb.f_82293_)
         )) {
            BlockState blockstate = this.f_19853_.m_8055_(pos);
            if (!blockstate.m_60795_() && blockstate.canEntityDestroy(this.f_19853_, pos, this) && !blockstate.m_204336_(ModTag.LEVIATHAN_IMMUNE)) {
               if (this.f_19796_.m_188503_(5) == 0 && !blockstate.m_155947_()) {
                  Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                     this.f_19853_, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, blockstate, 20
                  );
                  flag = this.f_19853_.m_46953_(pos, false, this) || flag;
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
                  flag = this.f_19853_.m_46953_(pos, false, this) || flag;
                  this.m_20256_(this.m_20184_().m_82542_(0.6F, 1.0, 0.6F));
               }
            }
         }

         if (flag) {
            this.blockBreakCounter = 10;
         }
      }
   }

   private void chargeblockbreaking() {
      boolean flag = false;
      AABB aabb = this.m_20191_().m_82377_(4.5, 0.5, 4.5);
      double yblockbreak = this.m_20069_() ? aabb.f_82289_ : this.m_20186_();

      for (BlockPos blockpos : BlockPos.m_121976_(
         Mth.m_14107_(aabb.f_82288_),
         Mth.m_14107_(yblockbreak),
         Mth.m_14107_(aabb.f_82290_),
         Mth.m_14107_(aabb.f_82291_),
         Mth.m_14107_(aabb.f_82292_),
         Mth.m_14107_(aabb.f_82293_)
      )) {
         BlockState blockstate = this.f_19853_.m_8055_(blockpos);
         if (!blockstate.m_60795_()
            && blockstate.canEntityDestroy(this.f_19853_, blockpos, this)
            && !blockstate.m_204336_(ModTag.LEVIATHAN_IMMUNE)
            && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
            flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
         }
      }
   }

   private void chargeDamage() {
      if (this.f_19797_ % 4 == 0) {
         for (LivingEntity Lentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(3.0))) {
            if (!this.m_7307_(Lentity) && !(Lentity instanceof The_Leviathan_Entity) && Lentity != this) {
               DamageSource damagesource = DamageSource.m_19370_(this);
               boolean flag = Lentity.m_6469_(
                  damagesource,
                  (float)(
                     (double)((float)this.m_21133_(Attributes.f_22281_))
                        + Math.min(this.m_21133_(Attributes.f_22281_), (double)Lentity.m_21233_() * CMConfig.LeviathanRushHpdamage)
                  )
               );
               if (Lentity instanceof Player) {
                  Player player = (Player)Lentity;
                  if (Lentity.m_21275_(damagesource)) {
                     this.disableShield(player, 120);
                  }
               }

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

   private void biteattack(float radius, double inflateX, double inflateY, double inflateZ, int stuntick) {
      double renderYaw = (double)(this.f_20885_ + 90.0F) * Math.PI / 180.0;
      double renderPitch = (double)((float)((double)(-this.m_146909_()) * Math.PI / 180.0));
      this.endPosX = this.m_20185_() + (double)radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.m_20189_() + (double)radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.m_20186_() + (double)radius * Math.sin(renderPitch);
      if (!this.f_19853_.f_46443_) {
         for (LivingEntity target : this.raytraceEntities(
               this.f_19853_,
               inflateX,
               inflateY,
               inflateZ,
               new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_()),
               new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities) {
            if (!this.m_7307_(target) && !(target instanceof The_Leviathan_Entity) && target != this) {
               DamageSource damagesource = DamageSource.m_19370_(this);
               boolean flag = target.m_6469_(
                  damagesource,
                  (float)(
                     (double)((float)this.m_21133_(Attributes.f_22281_)) * 1.5
                        + Math.min(this.m_21133_(Attributes.f_22281_) * 1.5, (double)target.m_21233_() * CMConfig.LeviathanbiteHpdamage)
                  )
               );
               if (target instanceof Player) {
                  Player player = (Player)target;
                  if (target.m_21275_(damagesource)) {
                     this.disableShield(player, 200);
                  }
               }

               if (flag && stuntick > 0) {
                  target.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), stuntick));
               }
            }
         }
      }
   }

   private void Tentacleattack(int anime, float radius, double inflateX, double inflateY, double inflateZ) {
      double renderYaw = (double)(this.f_20885_ + 90.0F) * Math.PI / 180.0;
      double renderPitch = (double)((float)((double)(-this.m_146909_()) * Math.PI / 180.0));
      this.endPosX = this.m_20185_() + (double)radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.m_20189_() + (double)radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.m_20186_() + (double)radius * Math.sin(renderPitch);
      if (this.getAnimationTick() == anime) {
         this.m_5496_((SoundEvent)ModSounds.LEVIATHAN_TENTACLE_STRIKE.get(), 1.0F, 1.0F);

         for (LivingEntity target : this.raytraceEntities(
               this.f_19853_,
               inflateX,
               inflateY,
               inflateZ,
               new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_()),
               new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities) {
            if (!this.m_7307_(target) && !(target instanceof The_Leviathan_Entity) && target != this) {
               DamageSource damagesource = DamageSource.m_19370_(this);
               boolean flag = target.m_6469_(
                  damagesource,
                  (float)(
                     (double)((float)this.m_21133_(Attributes.f_22281_))
                        + Math.min(this.m_21133_(Attributes.f_22281_), (double)target.m_21233_() * CMConfig.LeviathanTentacleHpdamage)
                  )
               );
               if (target instanceof Player) {
                  Player player = (Player)target;
                  if (target.m_21275_(damagesource)) {
                     this.disableShield(player, 90);
                  }
               }

               if (flag) {
                  double d0 = target.m_20185_() - this.m_20185_();
                  double d1 = target.m_20189_() - this.m_20189_();
                  double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                  target.m_5997_(d0 / d2 * 7.0, 0.2, d1 / d2 * 7.0);
               }
            }
         }
      }
   }

   private void TentacleHoldattack(float radius, double inflateX, double inflateY, double inflateZ, int shieldbreakticks) {
      double renderYaw = (double)(this.f_20885_ + 90.0F) * Math.PI / 180.0;
      double renderPitch = (double)((float)((double)(-this.m_146909_()) * Math.PI / 180.0));
      this.endPosX = this.m_20185_() + (double)radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.m_20189_() + (double)radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.m_20186_() + (double)radius * Math.sin(renderPitch);

      for (LivingEntity target : this.raytraceEntities(
            this.f_19853_,
            inflateX,
            inflateY,
            inflateZ,
            new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_()),
            new Vec3(this.endPosX, this.endPosY, this.endPosZ)
         )
         .entities) {
         if (!this.m_7307_(target) && !(target instanceof The_Leviathan_Entity) && target != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = target.m_6469_(damagesource, (float)this.m_21133_(Attributes.f_22281_) + target.m_21233_() * 0.1F);
            if (target instanceof Player) {
               Player player = (Player)target;
               if (target.m_21275_(damagesource) && shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag && !target.m_6095_().m_204039_(ModTag.IGNIS_CANT_POKE) && target.m_6084_()) {
               if (target.m_6144_()) {
                  target.m_20260_(false);
               }

               if (!this.f_19853_.f_46443_) {
                  target.m_7998_(this, true);
               }

               AnimationHandler.INSTANCE.sendAnimationMessage(this, LEVIATHAN_TENTACLE_HOLD_BLAST);
            }
         }
      }
   }

   public void m_7332_(Entity p_20312_) {
      this.m_19956_(p_20312_, Entity::m_6034_);
   }

   public void m_19956_(Entity passenger, MoveFunction moveFunc) {
      if (this.m_20363_(passenger)) {
         if (this.getAnimation() == LEVIATHAN_TENTACLE_HOLD_BLAST && this.getAnimationTick() == 169) {
            passenger.m_8127_();
         }

         this.m_146926_(this.f_19860_);
         this.f_20883_ = this.m_146908_();
         this.f_20885_ = this.m_146908_();
         float f17 = this.m_146908_() * (float) Math.PI / 180.0F;
         float pitch = this.m_146909_() * (float) Math.PI / 180.0F;
         float f3 = Mth.m_14031_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         float f18 = Mth.m_14089_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         moveFunc.m_20372_(
            passenger, this.m_20185_() + (double)(f3 * -8.25F), this.m_20186_() + (double)(-pitch * 6.0F), this.m_20189_() + (double)(-f18 * -8.25F)
         );
      }
   }

   public boolean shouldRiderSit() {
      return false;
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   @Override
   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }

   @Override
   public boolean m_7301_(MobEffectInstance p_31495_) {
      return p_31495_.m_19544_() != ModEffect.EFFECTABYSSAL_BURN.get() && super.m_7301_(p_31495_);
   }

   private The_Leviathan_Entity.BiteHitResult raytraceEntities(Level world, double inflateX, double inflateY, double inflateZ, Vec3 from, Vec3 to) {
      The_Leviathan_Entity.BiteHitResult result = new The_Leviathan_Entity.BiteHitResult();
      this.collidePosX = this.endPosX;
      this.collidePosY = this.endPosY;
      this.collidePosZ = this.endPosZ;

      for (LivingEntity entity : world.m_45976_(
         LivingEntity.class,
         new AABB(
               Math.min(this.m_20185_(), this.collidePosX),
               Math.min(this.m_20186_(), this.collidePosY),
               Math.min(this.m_20189_(), this.collidePosZ),
               Math.max(this.m_20185_(), this.collidePosX),
               Math.max(this.m_20186_(), this.collidePosY),
               Math.max(this.m_20189_(), this.collidePosZ)
            )
            .m_82377_(inflateX, inflateY, inflateZ)
      )) {
         float pad = 2.5F;
         AABB aabb = entity.m_20191_().m_82377_((double)pad, (double)pad, (double)pad);
         Optional<Vec3> hit = aabb.m_82371_(from, to);
         if (aabb.m_82390_(from)) {
            result.addEntityHit(entity);
         } else if (hit.isPresent()) {
            result.addEntityHit(entity);
         }
      }

      return result;
   }

   protected int m_7305_(int currentAir) {
      return this.m_6062_();
   }

   protected float m_6431_(Pose poseIn, EntityDimensions sizeIn) {
      return sizeIn.f_20378_ * 0.45F;
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6063_() {
      return false;
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("BlastChance", this.getBlastChance());
      compound.m_128379_("MeltDown", this.getMeltDown());
      compound.m_128405_("ModeChance", this.getModeChance());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setBlastChance(compound.m_128451_("BlastChance"));
      this.setModeChance(compound.m_128451_("ModeChance"));
      this.setMeltDown(compound.m_128471_("MeltDown"));
      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   public void m_6593_(@Nullable Component name) {
      super.m_6593_(name);
      this.bossInfo.m_6456_(this.m_5446_());
   }

   public boolean getMeltDown() {
      return (Boolean)this.f_19804_.m_135370_(MELT_DOWN);
   }

   public void setMeltDown(boolean chance) {
      this.f_19804_.m_135381_(MELT_DOWN, chance);
   }

   public int getBlastChance() {
      return (Integer)this.f_19804_.m_135370_(BLAST_CHANCE);
   }

   public void setBlastChance(int chance) {
      this.f_19804_.m_135381_(BLAST_CHANCE, chance);
   }

   public int getModeChance() {
      return (Integer)this.f_19804_.m_135370_(MODE_CHANCE);
   }

   public void setModeChance(int chance) {
      this.f_19804_.m_135381_(MODE_CHANCE, chance);
   }

   @Nullable
   public UUID getTongueUUID() {
      return (UUID)((Optional)this.f_19804_.m_135370_(TONGUE_UUID)).orElse(null);
   }

   public void setTongueUUID(@Nullable UUID uniqueId) {
      this.f_19804_.m_135381_(TONGUE_UUID, Optional.ofNullable(uniqueId));
   }

   public Entity getTongue() {
      if (!this.f_19853_.f_46443_) {
         UUID id = this.getTongueUUID();
         return id == null ? null : ((ServerLevel)this.f_19853_).m_8791_(id);
      } else {
         int id = (Integer)this.f_19804_.m_135370_(TONGUE_ID);
         return id == -1 ? null : this.f_19853_.m_6815_(id);
      }
   }

   public void createStuckPortal() {
      if (this.m_5448_() != null) {
         Vec3 to = new Vec3(this.m_5448_().m_20185_(), this.m_5448_().m_20186_(), this.m_5448_().m_20189_());
         this.createPortal2(this.m_20185_(), this.m_20186_() + 0.1, this.m_20189_(), to);
      }
   }

   public void createPortal2(double x, double y, double z, Vec3 to) {
      if (!this.f_19853_.f_46443_ && this.portalTarget == null) {
         Abyss_Portal_Entity portal = (Abyss_Portal_Entity)((EntityType)ModEntities.ABYSS_PORTAL.get()).m_20615_(this.f_19853_);
         portal.m_6034_(x, y, z);
         portal.setLifespan(10000);
         portal.setEntrance(true);
         if (!this.f_19853_.f_46443_) {
            this.f_19853_.m_7967_(portal);
         }

         this.portalTarget = portal;
         portal.setDestination(new BlockPos(to.f_82479_, to.f_82480_, to.f_82481_));
         this.makePortalCooldown = 300;
      }
   }

   public void resetPortalLogic() {
      this.portalTarget = null;
   }

   public void teleportTo(Vec3 vec) {
      this.teleportPos = vec;
      this.fullyThrough = false;
   }

   private boolean checkBlocksByTentacle(float vec, float math) {
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      float f = Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0));
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      BlockPos pos1 = new BlockPos(
         Mth.m_14107_(this.m_20185_() + (double)vec * vecX + (double)(f * math)),
         Math.round((float)(this.m_20186_() - 2.0)),
         Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + (double)(f1 * math))
      );
      return this.f_19853_.m_8055_(pos1).m_60767_().m_76334_();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   @Override
   public boolean shouldEnterWater() {
      return true;
   }

   @Override
   public boolean shouldLeaveWater() {
      return false;
   }

   @Override
   public boolean shouldStopMoving() {
      return false;
   }

   @Override
   public int getWaterSearchRange() {
      return 32;
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.LEVIATHAN_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.LEVIATHAN_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.LEVIATHAN_DEFEAT.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return this.getMeltDown() ? (SoundEvent)ModSounds.LEVIATHAN_MUSIC_2.get() : (SoundEvent)ModSounds.LEVIATHAN_MUSIC_1.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return this.getAnimation() != LEVIATHAN_PHASE2 ? super.canPlayMusic() : this.getAnimationTick() > 90 && super.canPlayMusic();
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return LEVIATHAN_DEATH;
   }

   protected float m_6121_() {
      return this.m_20067_() ? 0.0F : 2.0F;
   }

   private void setPartPosition(The_Leviathan_Part part, double offsetX, double offsetY, double offsetZ) {
      part.m_6034_(
         this.m_20185_() + offsetX * (double)part.scale, this.m_20186_() + offsetY * (double)part.scale, this.m_20189_() + offsetZ * (double)part.scale
      );
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public PartEntity<?>[] getParts() {
      return this.leviathanParts;
   }

   public void m_141965_(ClientboundAddEntityPacket packet) {
      super.m_141965_(packet);
      Cm_Part_Entity.assignPartIDs(this);
   }

   public Vec3 getTonguePosition() {
      float f1 = -Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.m_146909_() * (float) (Math.PI / 180.0));
      float f2 = -Mth.m_14031_(this.m_146909_() * (float) (Math.PI / 180.0));
      float f3 = Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0)) * Mth.m_14089_(this.m_146909_() * (float) (Math.PI / 180.0));
      return new Vec3(this.m_20185_() + (double)f1 * 3.0, this.m_20186_() + (double)f2 * 3.5, this.m_20189_() + (double)f3 * 3.0);
   }

   private static enum AttackMode {
      CIRCLE,
      MELEE,
      RANGE;
   }

   public static class BiteHitResult {
      private final List<LivingEntity> entities = new ArrayList<>();

      public void addEntityHit(LivingEntity entity) {
         this.entities.add(entity);
      }
   }

   static class LeviathanAbyssBlastPortalAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanAbyssBlastPortalAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.CanAbyss_Blast_Portal = false;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanMine = true;
         super.m_8056_();
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
         this.entity.hunting_cooldown = 120;
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
            double d0 = Math.min(target.m_20186_(), this.entity.m_20186_()) - 50.0;
            double d1 = Math.max(target.m_20186_(), this.entity.m_20186_()) + 3.0;
            float f = (float)Mth.m_14136_(target.m_20189_() - this.entity.m_20189_(), target.m_20185_() - this.entity.m_20185_());
            if (this.entity.getAnimationTick() == 56) {
               for (int l = 0; l < 9; l++) {
                  int j = (int)(5.0F * (float)l);
                  double randomNearbyX = target.m_20185_() + this.entity.f_19796_.m_188583_() * 12.0;
                  double randomNearbyZ = target.m_20189_() + this.entity.f_19796_.m_188583_() * 12.0;
                  this.spawnUnderPortal(randomNearbyX, randomNearbyZ, d0, d1, f, j);
               }
            }

            if (this.entity.getAnimationTick() == 56 || this.entity.getAnimationTick() == 76 || this.entity.getAnimationTick() == 96) {
               this.spawnUnderPortal(target.m_20185_(), target.m_20189_(), d0, d1, f, 0);
            }
         }
      }

      private void spawnUnderPortal(double x, double z, double minY, double maxY, float rotation, int delay) {
         BlockPos blockpos = new BlockPos(x, maxY, z);
         boolean flag = false;
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = this.entity.f_19853_.m_8055_(blockpos1);
            if (blockstate.m_60783_(this.entity.f_19853_, blockpos1, Direction.UP)) {
               if (!this.entity.f_19853_.m_46859_(blockpos)) {
                  BlockState blockstate1 = this.entity.f_19853_.m_8055_(blockpos);
                  VoxelShape voxelshape = blockstate1.m_60812_(this.entity.f_19853_, blockpos);
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
            this.entity
               .f_19853_
               .m_7967_(
                  new Abyss_Blast_Portal_Entity(
                     this.entity.f_19853_,
                     x,
                     (double)blockpos.m_123342_() + d0,
                     z,
                     rotation,
                     delay,
                     (float)CMConfig.AbyssBlastdamage,
                     (float)CMConfig.AbyssBlastHpdamage,
                     this.entity
                  )
               );
         }
      }

      private void spawnUpperPortal(double x, double z, double maxY, float rotation, int delay) {
         BlockPos blockpos = new BlockPos(x, maxY, z);
         boolean flag = false;
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

               flag = true;
               break;
            }

            blockpos = blockpos.m_7494_();
         } while (
            blockpos.m_123342_() < Math.min(this.entity.f_19853_.m_151558_(), this.entity.m_146904_() + 100)
               && !this.entity.f_19853_.m_8055_(blockpos).m_60767_().m_76333_()
         );

         if (flag) {
            this.entity
               .f_19853_
               .m_7967_(
                  new Abyss_Blast_Portal_Entity(
                     this.entity.f_19853_,
                     x,
                     (double)blockpos.m_123342_() + d0 + 0.5,
                     z,
                     rotation,
                     delay,
                     (float)CMConfig.AbyssBlastdamage,
                     (float)CMConfig.AbyssBlastHpdamage,
                     this.entity
                  )
               );
         }
      }
   }

   static class LeviathanAbyssDimensionAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanAbyssDimensionAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanCrackDimension = false;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 0.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
         this.entity.hunting_cooldown = 250;
         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }

      public void m_8037_() {
         if (this.entity.getAnimationTick() == 24 && !this.entity.f_19853_.f_46443_) {
            double theta = (double)this.entity.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            double vecZ = Math.sin(theta);
            Dimensional_Rift_Entity portal = new Dimensional_Rift_Entity(
               this.entity.f_19853_, this.entity.m_20185_() + vecX * 12.0, this.entity.m_20188_(), this.entity.m_20189_() + vecZ * 12.0, this.entity
            );
            portal.setStage(1);
            portal.setLifespan(600);
            if (!this.entity.f_19853_.f_46443_) {
               this.entity.f_19853_.m_7967_(portal);
            }
         }

         Dimensional_Rift_Entity rift = this.getClosestDimensionalRift();
         if (rift != null) {
            this.entity.m_21563_().m_24960_(rift, 30.0F, 90.0F);
         }
      }

      private Dimensional_Rift_Entity getClosestDimensionalRift() {
         List<Dimensional_Rift_Entity> list = this.entity.f_19853_.m_45976_(Dimensional_Rift_Entity.class, this.entity.m_20191_().m_82377_(15.0, 15.0, 15.0));
         Dimensional_Rift_Entity closest = null;
         if (!list.isEmpty()) {
            for (Dimensional_Rift_Entity entity : list) {
               if (closest == null || closest.m_20270_(entity) > entity.m_20270_(entity)) {
                  closest = entity;
               }
            }
         }

         return closest;
      }
   }

   class LeviathanAttackGoal extends Goal {
      private final The_Leviathan_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private final int huntingTime = 0;
      private float MeleeModeTime = 0.0F;
      private static final int MELEE_MODE_TIME = 160;
      private float circleDistance = 18.0F;
      private boolean clockwise = false;

      public LeviathanAttackGoal(The_Leviathan_Entity mob) {
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
         this.mob.mode = The_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = (float)(36 + this.mob.f_19796_.m_188503_(20));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.mob.m_21561_(true);
      }

      public void m_8041_() {
         this.mob.mode = The_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = (float)(36 + this.mob.f_19796_.m_188503_(20));
         this.clockwise = this.mob.f_19796_.m_188499_();
         this.target = this.mob.m_5448_();
         if (!EntitySelector.f_20406_.test(this.target)) {
            this.mob.m_6710_((LivingEntity)null);
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
            if (this.mob.mode == The_Leviathan_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               The_Leviathan_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (0 >= this.mob.hunting_cooldown) {
                  int i = Math.max(this.mob.getModeChance(), 2);
                  if (this.mob.f_19796_.m_188503_(i) == 0) {
                     this.mob.mode = The_Leviathan_Entity.AttackMode.RANGE;
                  } else {
                     this.mob.mode = The_Leviathan_Entity.AttackMode.MELEE;
                  }
               }

               if (this.mob.m_217043_().m_188501_() * 100.0F < 12.0F && this.mob.m_20280_(target) <= 49.0 && this.mob.melee_cooldown <= 0) {
                  Animation animation = The_Leviathan_Entity.getRandomTantalcleStrike(this.mob.f_19796_);
                  this.mob.setAnimation(animation);
               }
            } else if (this.mob.mode == The_Leviathan_Entity.AttackMode.RANGE) {
               if (this.mob.m_217043_().m_188501_() * 100.0F < 3.0F && this.mob.CanAbyss_Blast_Portal) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST_PORTAL);
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 2.0F * (float)this.mob.getBlastChance() && this.mob.CanAbyss_Blast) {
                  if (this.mob.f_19796_.m_188503_(2) == 0) {
                     this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST);
                  } else {
                     this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST_FIRE);
                  }
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 8.0F && this.mob.m_20280_(target) >= 700.0 && this.mob.CanGrab) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_GRAB);
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 4.0F
                  && this.mob.m_20280_(target) < 900.0
                  && this.mob.m_20280_(target) >= 49.0
                  && this.mob.CanRush) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_RUSH);
               }
            } else if (this.mob.mode == The_Leviathan_Entity.AttackMode.MELEE) {
               this.mob.m_21573_().m_5624_(target, 1.0);
               this.MeleeModeTime++;
               this.mob.m_21563_().m_24960_(target, 30.0F, 90.0F);
               if (this.MeleeModeTime >= 160.0F) {
                  this.mob.mode = The_Leviathan_Entity.AttackMode.RANGE;
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 15.0F && this.mob.m_20280_(target) < 49.0 && this.mob.CanTailWhips) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_TAIL_WHIPS);
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 8.0F && this.mob.m_20280_(target) < 36.0 && this.mob.CanGrab) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_TENTACLE_HOLD);
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 20.0F && this.mob.m_20280_(target) <= 30.0 && this.mob.CanBite) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_BITE);
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 3.0F
                  && this.mob.CanCrackDimension
                  && this.mob.m_20280_(target) < 256.0
                  && this.mob.m_20280_(target) > 49.0) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_BREAK_DIMENSION);
               } else if (this.mob.m_217043_().m_188501_() * 100.0F < 5.0F
                  && this.mob.CanMine
                  && this.mob.m_20280_(target) < 256.0
                  && this.mob.m_20280_(target) > 100.0) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_MINE);
               }
            }
         }
      }
   }

   static class LeviathanBiteAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanBiteAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = false;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 13 && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }
   }

   static class LeviathanBlastAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanBlastAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.f_19853_.m_6269_((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = false;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 140;
         this.entity.setBlastChance(0);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void m_8037_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null && this.entity.getAnimationTick() < 82) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if (this.entity.getAnimationTick() == 64 && !this.entity.f_19853_.f_46443_) {
            Abyss_Blast_Entity DeathBeam = new Abyss_Blast_Entity(
               (EntityType<? extends Abyss_Blast_Entity>)ModEntities.ABYSS_BLAST.get(),
               this.entity.f_19853_,
               this.entity,
               this.entity.m_20185_(),
               this.entity.m_20186_(),
               this.entity.m_20189_(),
               (float)((double)(this.entity.f_20885_ + dir) * Math.PI / 180.0),
               (float)((double)(-this.entity.m_146909_()) * Math.PI / 180.0),
               80,
               dir,
               (float)CMConfig.AbyssBlastdamage,
               (float)CMConfig.AbyssBlastHpdamage
            );
            this.entity.f_19853_.m_7967_(DeathBeam);
         }
      }
   }

   static class LeviathanBlastFireAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanBlastFireAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.f_19853_.m_6269_((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST_ONLY_CHARGE.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = false;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 140;
         this.entity.setBlastChance(0);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void m_8037_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null
            && (
               this.entity.getAnimationTick() < 80
                  || this.entity.getAnimationTick() > 118 && this.entity.getAnimationTick() < 125
                  || this.entity.getAnimationTick() > 163 && this.entity.getAnimationTick() < 170
            )) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if ((this.entity.getAnimationTick() == 64 || this.entity.getAnimationTick() == 109 || this.entity.getAnimationTick() == 154)
            && !this.entity.f_19853_.f_46443_) {
            Abyss_Blast_Entity DeathBeam = new Abyss_Blast_Entity(
               (EntityType<? extends Abyss_Blast_Entity>)ModEntities.ABYSS_BLAST.get(),
               this.entity.f_19853_,
               this.entity,
               this.entity.m_20185_(),
               this.entity.m_20186_(),
               this.entity.m_20189_(),
               (float)((double)(this.entity.f_20885_ + dir) * Math.PI / 180.0),
               (float)((double)(-this.entity.m_146909_()) * Math.PI / 180.0),
               28,
               dir,
               (float)CMConfig.AbyssBlastdamage,
               (float)CMConfig.AbyssBlastHpdamage
            );
            this.entity.f_19853_.m_7967_(DeathBeam);
         }
      }
   }

   static class LeviathanGrabAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanGrabAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = false;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
         this.entity.hunting_cooldown = 70;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         Entity weapon = this.entity.getTongue();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         if (this.entity.getAnimationTick() == 25 && target != null && this.entity.getTongue() == null && !this.entity.f_19853_.f_46443_) {
            The_Leviathan_Tongue_Entity segment = (The_Leviathan_Tongue_Entity)((EntityType)ModEntities.THE_LEVIATHAN_TONGUE.get())
               .m_20615_(this.entity.f_19853_);
            segment.m_20359_(this.entity);
            segment.m_146884_(this.entity.getTonguePosition());
            segment.setControllerUUID(this.entity.m_20148_());
            segment.setMaxDuration(120);
            this.entity.setTongueUUID(segment.m_20148_());
            this.entity.f_19853_.m_7967_(segment);
         }

         if (this.entity.getAnimationTick() > 25
            && this.entity.getAnimationTick() <= 145
            && target != null
            && target.m_6084_()
            && this.entity.m_20270_(target) < 8.5F) {
            if (weapon instanceof The_Leviathan_Tongue_Entity magneticWeapon && magneticWeapon.getComingBack()) {
               magneticWeapon.m_142687_(RemovalReason.DISCARDED);
            }

            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Leviathan_Entity.LEVIATHAN_GRAB_BITE);
         }

         if (this.entity.getAnimationTick() > 155 && weapon instanceof The_Leviathan_Tongue_Entity magneticWeapon) {
            magneticWeapon.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   static class LeviathanGrabBiteAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanGrabBiteAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         this.entity.f_19853_.m_6269_((Player)null, this.entity, (SoundEvent)ModSounds.LEVIATHAN_BITE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         this.entity.m_146922_(this.entity.f_19859_);
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
         this.entity.hunting_cooldown = 70;
      }
   }

   static class LeviathanMineAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanMineAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanBite = true;
         this.entity.CanMine = false;
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8037_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
            float f = (float)Mth.m_14136_(target.m_20189_() - this.entity.m_20189_(), target.m_20185_() - this.entity.m_20185_());
            if (this.entity.getAnimationTick() == 31) {
               for (int l = 0; l < 35; l++) {
                  int j = (int)(2.0F * (float)l);
                  double randomNearbyX = target.m_20185_() + this.entity.f_19796_.m_188583_() * 12.0;
                  double randomNearbyY = target.m_20186_() + this.entity.f_19796_.m_188583_() * 8.0;
                  double randomNearbyZ = target.m_20189_() + this.entity.f_19796_.m_188583_() * 12.0;
                  this.spawnMines(randomNearbyX, randomNearbyY, randomNearbyZ, f, j);
               }
            }
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }

      private void spawnMines(double x, double y, double z, float rotation, int delay) {
         Abyss_Mine_Entity mine = new Abyss_Mine_Entity(this.entity.f_19853_, x, y, z, rotation, delay, this.entity);
         if (mine.f_19853_.m_45786_(mine)) {
            this.entity.f_19853_.m_7967_(mine);
         }
      }
   }

   static class LeviathanMoveController extends MoveControl {
      private final The_Leviathan_Entity entity;
      private final float speedMulti;
      private final float ySpeedMod;
      private final float yawLimit;
      private int stillTicks = 0;

      public LeviathanMoveController(The_Leviathan_Entity entity, float speedMulti, float ySpeedMod, float yawLimit) {
         super(entity);
         this.entity = entity;
         this.speedMulti = speedMulti;
         this.ySpeedMod = ySpeedMod;
         this.yawLimit = yawLimit;
      }

      public void m_8126_() {
         if (this.entity.m_20069_() && this.entity.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            if (Math.abs(this.entity.f_19854_ - this.entity.m_20185_()) < 0.01F
               && Math.abs(this.entity.f_19855_ - this.entity.m_20186_()) < 0.01F
               && Math.abs(this.entity.f_19856_ - this.entity.m_20189_()) < 0.01F) {
               this.stillTicks++;
            } else {
               this.stillTicks = 0;
            }

            if (this.stillTicks > 40) {
               this.entity.m_20256_(this.entity.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
         }

         if (this.entity.shouldStopMoving()) {
            this.entity.m_7910_(0.0F);
         } else {
            if (this.f_24981_ == Operation.MOVE_TO && !this.entity.m_21573_().m_26571_()) {
               double lvt_1_1_ = this.f_24975_ - this.entity.m_20185_();
               double lvt_3_1_ = this.f_24976_ - this.entity.m_20186_();
               double lvt_5_1_ = this.f_24977_ - this.entity.m_20189_();
               double lvt_7_1_ = lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_;
               if (lvt_7_1_ < 2.5000003E-7F) {
                  this.f_24974_.m_21564_(0.0F);
               } else {
                  float lvt_9_1_ = (float)(Mth.m_14136_(lvt_5_1_, lvt_1_1_) * 180.0F / (float)Math.PI) - 90.0F;
                  this.entity.m_146922_(this.m_24991_(this.entity.m_146908_(), lvt_9_1_, this.yawLimit));
                  this.entity.f_20883_ = this.entity.m_146908_();
                  this.entity.f_20885_ = this.entity.m_146908_();
                  float lvt_10_1_ = (float)(this.f_24978_ * (double)this.speedMulti * 3.0 * this.entity.m_21133_(Attributes.f_22279_));
                  if (this.entity.m_20069_()) {
                     if (lvt_3_1_ > 0.0 && this.entity.f_19862_) {
                        this.entity.m_20256_(this.entity.m_20184_().m_82520_(0.0, 0.08F, 0.0));
                     } else {
                        this.entity
                           .m_20256_(this.entity.m_20184_().m_82520_(0.0, (double)this.entity.m_6113_() * lvt_3_1_ * 0.6 * (double)this.ySpeedMod, 0.0));
                     }

                     this.entity.m_7910_(lvt_10_1_ * 0.02F);
                     float lvt_11_1_ = -(
                        (float)(Mth.m_14136_(lvt_3_1_, (double)Mth.m_14116_((float)(lvt_1_1_ * lvt_1_1_ + lvt_5_1_ * lvt_5_1_))) * 180.0F / (float)Math.PI)
                     );
                     lvt_11_1_ = Mth.m_14036_(Mth.m_14177_(lvt_11_1_), -85.0F, 85.0F);
                     this.entity.m_146926_(this.m_24991_(this.entity.m_146909_(), lvt_11_1_, 5.0F));
                     float lvt_12_1_ = Mth.m_14089_(this.entity.m_146909_() * (float) (Math.PI / 180.0));
                     float lvt_13_1_ = Mth.m_14031_(this.entity.m_146909_() * (float) (Math.PI / 180.0));
                     this.entity.f_20902_ = lvt_12_1_ * lvt_10_1_;
                     this.entity.f_20901_ = -lvt_13_1_ * lvt_10_1_;
                  } else {
                     this.entity.m_7910_(lvt_10_1_ * 0.1F);
                  }
               }
            } else {
               this.entity.m_7910_(0.0F);
               this.entity.m_21570_(0.0F);
               this.entity.m_21567_(0.0F);
               this.entity.m_21564_(0.0F);
            }
         }
      }
   }

   static class LeviathanPhase2Goal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanPhase2Goal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() >= 90 && target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
      }
   }

   static class LeviathanRushAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanRushAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         super.m_8056_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = false;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanTailWhips = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
            if (this.entity.getAnimationTick() > 53 && this.entity.getAnimationTick() < 134) {
               double d0 = target.m_20185_() - this.entity.m_20185_();
               double d1 = target.m_20188_() - this.entity.m_20188_();
               double d2 = target.m_20189_() - this.entity.m_20189_();
               double d3 = (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2));
               float targetYaw = (float)(Mth.m_14136_(d2, d0) * 180.0 / (float) Math.PI - 90.0);
               float targetPitch = (float)(-(Mth.m_14136_(d1, d3) * 180.0 / (float) Math.PI));
               this.entity.m_146926_(this.entity.m_146909_() + Mth.m_14036_(targetPitch - this.entity.m_146909_(), -2.0F, 2.0F));
               if (d0 * d0 + d2 * d2 >= 9.0) {
                  this.entity.m_146922_(this.entity.m_146908_() + Mth.m_14036_(targetYaw - this.entity.m_146908_(), -2.0F, 2.0F));
                  this.entity.f_20883_ = this.entity.m_146908_();
               }

               if (Math.abs(Mth.m_14177_(targetYaw) - Mth.m_14177_(this.entity.m_146908_())) < 4.0F) {
                  double distSq = d0 * d0 + d2 * d2;
                  if (distSq < 9.0) {
                     this.entity.m_146922_(this.entity.f_19859_);
                     this.entity.f_20883_ = this.entity.f_19859_;
                     this.entity.m_20256_(this.entity.m_20184_().m_82542_(0.8, 1.0, 0.8));
                  } else {
                     if (this.entity.m_20069_() && target.m_20069_()) {
                        Vec3 vector3d = this.entity.m_20184_();
                        Vec3 vector3d1 = new Vec3(
                           target.m_20185_() - this.entity.m_20185_(), target.m_20186_() - this.entity.m_20186_(), target.m_20189_() - this.entity.m_20189_()
                        );
                        if (vector3d1.m_82556_() > 1.0E-7) {
                           vector3d1 = vector3d1.m_82541_().m_82490_(0.5).m_82549_(vector3d.m_82490_(0.5));
                        }

                        this.entity.m_20334_(vector3d1.f_82479_, vector3d1.f_82480_, vector3d1.f_82481_);
                     }

                     this.entity.m_21566_().m_6849_(target.m_20185_(), target.m_20186_(), target.m_20189_(), 1.0);
                     this.entity.m_21573_().m_5624_(target, 1.0);
                  }
               }
            }
         }
      }
   }

   static class LeviathanStunGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanStunGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         super.m_8056_();
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null && this.entity.getAnimationTick() > 28) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }
   }

   static class LeviathanTailWhipsAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanTailWhipsAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = false;
         this.entity.CanBite = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }
      }

      public void m_8037_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }
   }

   static class LeviathanTentacleAttackGoal extends AnimationGoal<The_Leviathan_Entity> {
      public LeviathanTentacleAttackGoal(The_Leviathan_Entity entity) {
         super(entity);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_UPPER_R
            || animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_LOWER_R
            || animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_UPPER_L
            || animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_LOWER_L;
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         super.m_8056_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8041_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8041_();
         this.entity.melee_cooldown = 40;
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }
   }

   static class LeviathanTentacleHoldAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanTentacleHoldAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = false;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanBite = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 70;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8037_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }
   }

   static class LeviathanTentacleHoldBlastAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanTentacleHoldBlastAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         this.entity.m_21573_().m_26573_();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = false;
         this.entity.CanTailWhips = true;
         this.entity.CanBite = true;
         this.entity.CanMine = true;
         this.entity.f_19853_.m_6269_((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
         this.entity.hunting_cooldown = 140;
         this.entity.setBlastChance(0);
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }
      }

      public void m_8037_() {
         this.entity.m_21573_().m_26573_();
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if (this.entity.getAnimationTick() == 64 && !this.entity.f_19853_.f_46443_) {
            Abyss_Blast_Entity DeathBeam = new Abyss_Blast_Entity(
               (EntityType<? extends Abyss_Blast_Entity>)ModEntities.ABYSS_BLAST.get(),
               this.entity.f_19853_,
               this.entity,
               this.entity.m_20185_(),
               this.entity.m_20186_(),
               this.entity.m_20189_(),
               (float)((double)(this.entity.f_20885_ + dir) * Math.PI / 180.0),
               (float)((double)(-this.entity.m_146909_()) * Math.PI / 180.0),
               80,
               dir,
               (float)CMConfig.AbyssBlastdamage,
               (float)CMConfig.AbyssBlastHpdamage
            );
            this.entity.f_19853_.m_7967_(DeathBeam);
         }
      }
   }
}
