package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.partentity.Cm_Part_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Lava_Bomb_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMMathUtil;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
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
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;

public class Netherite_Monstrosity_Entity extends IABoss_monster {
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.m_5446_(), BossBarColor.RED, false, 0);
   public int frame;
   public float LayerBrightness;
   public float oLayerBrightness;
   public int LayerTicks;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState sleepAnimationState = new AnimationState();
   public AnimationState awakeAnimationState = new AnimationState();
   public AnimationState smashsAnimationState = new AnimationState();
   public AnimationState phaseAnimationState = new AnimationState();
   public AnimationState fireAnimationState = new AnimationState();
   public AnimationState drainAnimationState = new AnimationState();
   public AnimationState shouldercheckAnimationState = new AnimationState();
   public AnimationState overpowerAnimationState = new AnimationState();
   public AnimationState flareshotAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public final Netherite_Monstrosity_Part headPart;
   public final Netherite_Monstrosity_Part[] monstrosityParts;
   private static final EntityDataAccessor<Boolean> IS_BERSERK = SynchedEntityData.m_135353_(
      Netherite_Monstrosity_Entity.class, EntityDataSerializers.f_135035_
   );
   private static final EntityDataAccessor<Boolean> IS_AWAKEN = SynchedEntityData.m_135353_(Netherite_Monstrosity_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> MAGAZINE = SynchedEntityData.m_135353_(Netherite_Monstrosity_Entity.class, EntityDataSerializers.f_135028_);
   public boolean Blocking = CMConfig.NetheritemonstrosityBodyBloking;
   private int blockBreakCounter;
   public static final int NATURE_HEAL_COOLDOWN = 200;
   private int timeWithoutTarget;
   private int shoot_cooldown = 0;
   public static final int SHOOT_COOLDOWN = 240;
   private boolean onLava = false;
   private int check_cooldown = 0;
   public static final int CHECK_COOLDOWN = 80;
   private int overpower_cooldown = 0;
   public static final int OVERPOWER_COOLDOWN = 160;
   private int flare_shoot_cooldown = 0;
   public static final int FLARE_SHOOT_COOLDOWN = 120;

   public Netherite_Monstrosity_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 500;
      this.headPart = new Netherite_Monstrosity_Part(this, 1.6F, 2.5F);
      this.monstrosityParts = new Netherite_Monstrosity_Part[]{this.headPart};
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.LAVA, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      setConfigattribute(this, CMConfig.MonstrosityHealthMultiplier, CMConfig.MonstrosityDamageMultiplier);
   }

   public float getStepHeight() {
      return 4.5F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.f_21345_.m_25352_(4, new InternalMoveGoal(this, false, 1.0) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Netherite_Monstrosity_Entity.this.getAttackState() == 0;
         }
      });
      this.f_21345_.m_25352_(3, new InternalAttackGoal(this, 0, 3, 0, 58, 12, 6.0F) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Netherite_Monstrosity_Entity.this.m_217043_().m_188501_() * 100.0F < 32.0F;
         }
      });
      this.f_21345_.m_25352_(2, new InternalStateGoal(this, 1, 1, 2, 0, 0) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && !Netherite_Monstrosity_Entity.this.getIsAwaken();
         }

         @Override
         public void m_8037_() {
            this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            Netherite_Monstrosity_Entity.this.setIsAwaken(true);
         }
      });
      this.f_21345_.m_25352_(1, new InternalStateGoal(this, 2, 2, 0, 40, 0) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && Netherite_Monstrosity_Entity.this.getIsAwaken();
         }

         @Override
         public void m_8037_() {
            this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         }
      });
      this.f_21345_.m_25352_(0, new InternalAttackGoal(this, 1, 2, 0, 40, 0, 15.0F) {
         @Override
         public boolean m_8036_() {
            LivingEntity target = this.entity.m_5448_();
            return super.m_8036_() && target != null && this.entity.m_21574_().m_148306_(target);
         }

         @Override
         public void m_8056_() {
            super.m_8056_();
            Netherite_Monstrosity_Entity.this.setIsAwaken(true);
         }
      });
      this.f_21345_.m_25352_(0, new Netherite_Monstrosity_Entity.MonstrosityPhaseChangeGoal(this, 0, 4, 0, 54));
      this.f_21345_.m_25352_(3, new Netherite_Monstrosity_Entity.Magmashoot(this, 0, 6, 0, 44, 20, 40.0F, 19, 16.0F));
      this.f_21345_.m_25352_(3, new Netherite_Monstrosity_Entity.Flareshoot(this, 0, 10, 0, 60, 35, 26.0F, 35, 18.0F));
      this.f_21345_
         .m_25352_(
            3,
            new InternalAttackGoal(this, 0, 7, 0, 60, 25, 1.0F) {
               @Override
               public boolean m_8036_() {
                  LivingEntity target = this.entity.m_5448_();
                  return target != null
                     && target.m_6084_()
                     && this.entity.getAttackState() == 0
                     && this.entity.m_20077_()
                     && Netherite_Monstrosity_Entity.this.getMagazine() >= CMConfig.Lavabombmagazine;
               }
            }
         );
      this.f_21345_.m_25352_(3, new Netherite_Monstrosity_Entity.ShoulderCheck(this, 0, 8, 0, 70, 19, 16.0F, 19, 49, 12.0F));
      this.f_21345_
         .m_25352_(
            3,
            new InternalAttackGoal(this, 0, 9, 0, 75, 7, 10.0F) {
               @Override
               public boolean m_8036_() {
                  return super.m_8036_()
                     && Netherite_Monstrosity_Entity.this.m_217043_().m_188501_() * 100.0F < 40.0F
                     && Netherite_Monstrosity_Entity.this.overpower_cooldown <= 0;
               }

               @Override
               public void m_8041_() {
                  super.m_8041_();
                  Netherite_Monstrosity_Entity.this.overpower_cooldown = 160;
               }
            }
         );
   }

   public static Builder netherite_monstrosity() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 50.0)
         .m_22268_(Attributes.f_22279_, 0.25)
         .m_22268_(Attributes.f_22281_, 25.0)
         .m_22268_(Attributes.f_22276_, 600.0)
         .m_22268_(Attributes.f_22284_, 12.0)
         .m_22268_(Attributes.f_22285_, 5.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   public boolean attackEntityFromPart(Netherite_Monstrosity_Part netherite_monstrosity_part, DamageSource source, float amount) {
      return this.m_6469_(source, amount);
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if (this.getAttackState() == 4 && !source.m_19378_()) {
         return false;
      } else {
         double range = this.calculateRange(source);
         if (range > CMConfig.MonstrosityLongRangelimit * CMConfig.MonstrosityLongRangelimit && !source.m_19378_()) {
            return false;
         } else {
            Entity entity = source.m_7640_();
            if (entity instanceof AbstractGolem) {
               damage *= 0.5F;
            }

            boolean attack = super.m_6469_(source, damage);
            if (attack && !this.getIsAwaken() && this.m_6084_()) {
               this.setIsAwaken(true);
            }

            return attack;
         }
      }
   }

   protected int m_7302_(int air) {
      return air;
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.MonstrosityDamageCap;
   }

   public boolean m_5829_() {
      return this.m_6084_() && this.Blocking && this.getAttackState() != 8;
   }

   public boolean m_6094_() {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "sleep") {
         return this.sleepAnimationState;
      } else if (input == "awake") {
         return this.awakeAnimationState;
      } else if (input == "smash") {
         return this.smashsAnimationState;
      } else if (input == "phase_two") {
         return this.phaseAnimationState;
      } else if (input == "fire") {
         return this.fireAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else if (input == "drain") {
         return this.drainAnimationState;
      } else if (input == "shoulder_check") {
         return this.shouldercheckAnimationState;
      } else if (input == "overpower") {
         return this.overpowerAnimationState;
      } else {
         return input == "flare_shot" ? this.flareshotAnimationState : new AnimationState();
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_BERSERK, false);
      this.f_19804_.m_135372_(IS_AWAKEN, false);
      this.f_19804_.m_135372_(MAGAZINE, 0);
   }

   public boolean isSleep() {
      return this.getAttackState() == 1 || this.getAttackState() == 2;
   }

   public boolean m_203441_(FluidState p_230285_1_) {
      return p_230285_1_.m_205070_(FluidTags.f_13132_);
   }

   public void setIsBerserk(boolean isBerserk) {
      this.f_19804_.m_135381_(IS_BERSERK, isBerserk);
   }

   public boolean getIsBerserk() {
      return (Boolean)this.f_19804_.m_135370_(IS_BERSERK);
   }

   public void setOnLava(boolean lava) {
      this.onLava = lava;
   }

   public boolean getOnLava() {
      return this.onLava;
   }

   public void setIsAwaken(boolean isAwaken) {
      this.f_19804_.m_135381_(IS_AWAKEN, isAwaken);
      this.bossInfo.m_8321_(isAwaken);
      if (!isAwaken) {
         this.setAttackState(1);
      }
   }

   public boolean getIsAwaken() {
      return (Boolean)this.f_19804_.m_135370_(IS_AWAKEN);
   }

   public void setMagazine(int isAwaken) {
      this.f_19804_.m_135381_(MAGAZINE, isAwaken);
   }

   public int getMagazine() {
      return (Integer)this.f_19804_.m_135370_(MAGAZINE);
   }

   public void m_6593_(@Nullable Component name) {
      super.m_6593_(name);
      this.bossInfo.m_6456_(this.m_5446_());
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_
   ) {
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
               this.smashsAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.phaseAnimationState.m_216982_(this.f_19797_);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.deathAnimationState.m_216982_(this.f_19797_);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.fireAnimationState.m_216982_(this.f_19797_);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.drainAnimationState.m_216982_(this.f_19797_);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.shouldercheckAnimationState.m_216982_(this.f_19797_);
               break;
            case 9:
               this.stopAllAnimationStates();
               this.overpowerAnimationState.m_216982_(this.f_19797_);
               break;
            case 10:
               this.stopAllAnimationStates();
               this.flareshotAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.sleepAnimationState.m_216973_();
      this.awakeAnimationState.m_216973_();
      this.smashsAnimationState.m_216973_();
      this.phaseAnimationState.m_216973_();
      this.deathAnimationState.m_216973_();
      this.fireAnimationState.m_216973_();
      this.drainAnimationState.m_216973_();
      this.overpowerAnimationState.m_216973_();
      this.shouldercheckAnimationState.m_216973_();
      this.flareshotAnimationState.m_216973_();
   }

   @Override
   public void m_6667_(DamageSource p_21014_) {
      super.m_6667_(p_21014_);
      this.setAttackState(5);
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("is_Berserk", this.getIsBerserk());
      compound.m_128379_("is_Awaken", this.getIsAwaken());
      compound.m_128405_("Magazine", this.getMagazine());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setIsBerserk(compound.m_128471_("is_Berserk"));
      this.setIsAwaken(compound.m_128471_("is_Awaken"));
      this.setMagazine(compound.m_128451_("Magazine"));
      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   private void floatStrider() {
      if (this.m_20077_()) {
         CollisionContext lvt_1_1_ = CollisionContext.m_82750_(this);
         if (lvt_1_1_.m_6513_(LiquidBlock.f_54690_, this.m_20183_().m_7495_(), true)
            && !this.f_19853_.m_6425_(this.m_20183_().m_7494_()).m_205070_(FluidTags.f_13132_)) {
            this.setOnLava(true);
         }
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_MONSTROSITY) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.floatStrider();
      if (this.f_19853_.m_5776_()) {
         this.animateWhen(this.idleAnimationState, this.getAttackState() == 0, this.f_19797_);
      }

      this.frame++;
      float moveX = (float)(this.m_20185_() - this.f_19854_);
      float moveZ = (float)(this.m_20189_() - this.f_19856_);
      float speed = Mth.m_14116_(moveX * moveX + moveZ * moveZ);
      if (!this.m_20067_() && this.frame % 25 == 1 && (double)speed > 0.05 && this.getIsAwaken() && this.getAttackState() != 8) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYSTEP.get(), 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.08F, 0, 5);
      }

      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
      this.BlockBreaking();
      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      }

      if (this.shoot_cooldown > 0) {
         this.shoot_cooldown--;
      }

      if (this.overpower_cooldown > 0) {
         this.overpower_cooldown--;
      }

      if (this.check_cooldown > 0) {
         this.check_cooldown--;
      }

      if (this.flare_shoot_cooldown > 0) {
         this.flare_shoot_cooldown--;
      }

      LivingEntity target = this.m_5448_();
      if (!this.f_19853_.f_46443_) {
         if (this.timeWithoutTarget > 0) {
            this.timeWithoutTarget--;
         }

         if (target != null) {
            this.timeWithoutTarget = 200;
         }

         if (this.timeWithoutTarget <= 0 && !this.m_21525_() && CMConfig.MonstrosityNatureHealing > 0.0 && this.f_19797_ % 20 == 0) {
            this.m_5634_((float)CMConfig.MonstrosityNatureHealing);
         }
      }

      if (!this.m_21525_() && !this.getIsAwaken() && this.f_19797_ % 4 == 0) {
         this.m_5634_((float)CMConfig.MonstrosityNatureHealing);
      }

      this.setHeadPart();
      if (this.f_19853_.f_46443_) {
         this.LayerTicks++;
         this.LayerBrightness = this.LayerBrightness + (0.0F - this.LayerBrightness) * 0.8F;
      }
   }

   public void animateWhen(AnimationState state, boolean p_252220_, int p_249486_) {
      if (p_252220_) {
         state.m_216982_(p_249486_);
      } else {
         state.m_216973_();
      }
   }

   private void setHeadPart() {
      if (!this.m_21525_()) {
         float f17 = this.f_20883_ * (float) (Math.PI / 180.0);
         float pitch = this.m_146909_() * (float) (Math.PI / 180.0);
         float f3 = Mth.m_14031_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         float f18 = Mth.m_14089_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         Vec3[] avector3d = new Vec3[this.monstrosityParts.length];

         for (int j = 0; j < this.monstrosityParts.length; j++) {
            avector3d[j] = new Vec3(this.monstrosityParts[j].m_20185_(), this.monstrosityParts[j].m_20186_(), this.monstrosityParts[j].m_20189_());
         }

         float headY = 0.0F;
         float headxz = 0.0F;
         if (this.getAttackState() == 3) {
            int end = 40;
            float f = 0.0F;
            if (this.attackTicks > end) {
               f = CMMathUtil.cullAnimationTick(this.attackTicks, 1.2F, 1.0F, 13, end);
            } else {
               f = CMMathUtil.cullAnimationTick(this.attackTicks, 2.0F, 1.0F, 13, end);
            }

            headxz = -1.6F * f;
            headY = -2.2F * f;
         }

         if (this.getAttackState() == 6) {
            float f = CMMathUtil.cullAnimationTick(this.attackTicks, 0.5F, 1.0F, 0, 40);
            headxz = 4.0F * f;
            headY = 1.2F * f;
         }

         if (this.getAttackState() == 8) {
            float f = CMMathUtil.cullAnimationTick(this.attackTicks, 2.0F, 1.0F, 0, 30);
            headxz = -4.0F * f;
         }

         this.setPartPosition(this.headPart, (double)(f3 * -1.65F + f3 * headxz), (double)(pitch + 2.4F + headY), (double)(-f18 * -1.65F - f18 * headxz));

         for (int l = 0; l < this.monstrosityParts.length; l++) {
            this.monstrosityParts[l].f_19854_ = avector3d[l].f_82479_;
            this.monstrosityParts[l].f_19855_ = avector3d[l].f_82480_;
            this.monstrosityParts[l].f_19856_ = avector3d[l].f_82481_;
            this.monstrosityParts[l].f_19790_ = avector3d[l].f_82479_;
            this.monstrosityParts[l].f_19791_ = avector3d[l].f_82480_;
            this.monstrosityParts[l].f_19792_ = avector3d[l].f_82481_;
         }
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAttackState() == 2 && this.attackTicks == 2) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYAWAKEN.get(), 10.0F, 1.0F);
      }

      if (this.getAttackState() == 3 && this.attackTicks == 19) {
         this.EarthQuake(6.25);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
         this.Makeparticle(4.75F, 2.5F);
         this.Makeparticle(4.75F, -2.5F);
      }

      if (this.getAttackState() == 4) {
         if (this.attackTicks == 10) {
            this.m_5496_((SoundEvent)ModSounds.MONSTROSITYGROWL.get(), 3.0F, 1.0F);
         }

         if (this.attackTicks == 17) {
            this.berserkBlockBreaking(8, 8, 8);
            this.EarthQuake(6.25);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.Makeparticle(4.4F, 2.0F);
            this.Makeparticle(4.4F, -2.0F);
         }
      }

      if (this.getAttackState() == 5 && this.attackTicks == 26) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYLAND.get(), 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
      }

      if (this.getAttackState() == 6 && this.attackTicks == 19) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYSHOOT.get(), 3.0F, 0.75F);
      }

      if (this.getAttackState() == 7) {
         if (this.attackTicks == 24) {
            this.setMagazine(0);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.doAbsorptionEffects(4, 1, 4);
            this.m_5496_(SoundEvents.f_11783_, 6.0F, 0.5F);
            this.m_5634_(15.0F * (float)CMConfig.MonstrosityHealingMultiplier);
         }

         if (this.attackTicks == 26) {
            this.doAbsorptionEffects(8, 2, 8);
            this.m_5634_(15.0F * (float)CMConfig.MonstrosityHealingMultiplier);
         }

         if (this.attackTicks == 28) {
            this.doAbsorptionEffects(16, 4, 16);
            this.m_5634_(15.0F * (float)CMConfig.MonstrosityHealingMultiplier);
         }
      }

      if (this.getAttackState() == 8) {
         if (this.attackTicks == 22
            || this.attackTicks == 27
            || this.attackTicks == 32
            || this.attackTicks == 37
            || this.attackTicks == 42
            || this.attackTicks == 47) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.15F, 0, 6);
            this.m_5496_((SoundEvent)ModSounds.MONSTROSITYSTEP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks > 19 && this.attackTicks < 49) {
            if (!this.f_19853_.f_46443_) {
               if (CMConfig.MonstrosityBlockBreaking) {
                  this.ChargeBlockBreaking();
               } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
                  this.ChargeBlockBreaking();
               }
            }

            double yaw = Math.toRadians((double)(this.m_146908_() + 90.0F));
            double xExpand = 2.0 * Math.cos(yaw);
            double zExpand = 2.0 * Math.sin(yaw);
            AABB attackRange = this.m_20191_().m_82377_(0.75, 0.75, 0.75).m_82363_(xExpand, 0.0, zExpand);

            for (LivingEntity Lentity : this.f_19853_.m_45976_(LivingEntity.class, attackRange)) {
               if (!this.m_7307_(Lentity) && !(Lentity instanceof Netherite_Monstrosity_Entity) && Lentity != this) {
                  boolean flag = Lentity.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_) * 0.4F);
                  if (flag) {
                     double theta = (double)this.f_20883_ * (Math.PI / 180.0);
                     theta++;
                     double vec = -2.5;
                     double vecX = Math.cos(theta);
                     double vecZ = Math.sin(theta);
                     double d0 = Lentity.m_20185_() - (this.m_20185_() + vec * vecX);
                     double d1 = Lentity.m_20189_() - (this.m_20189_() + vec * vecZ);
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.05);
                     double vel = 4.0;
                     Lentity.m_5997_(d0 / d2 * vel, 0.3, d1 / d2 * vel);
                     Lentity.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTBONE_FRACTURE.get(), 100));
                  }
               }
            }
         }
      }

      if (this.getAttackState() == 9) {
         if (this.attackTicks == 9) {
            this.OverPowerKnockBack(7.0);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.Makeparticle(-0.3F, 3.4F);
            this.Makeparticle(-0.3F, -3.4F);
            this.CircleFlameJet(-0.3F, 3.4F, this.getIsBerserk() ? 14 : 7, this.getIsBerserk() ? 8 : 4, 3.0);
            this.CircleFlameJet(-0.3F, -3.4F, this.getIsBerserk() ? 14 : 7, this.getIsBerserk() ? 8 : 4, 3.0);
            this.m_5496_((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 0.7F);
         }

         if (this.attackTicks == 26) {
            this.m_5496_((SoundEvent)ModSounds.MONSTROSITYGROWL.get(), 3.0F, 1.0F);
         }

         for (int l = 31; l <= 41; l += 2) {
            if (this.attackTicks == l) {
               int d = l - 27;
               int d2 = l - 26;
               float ds = (float)((d + d2) / 2);
               this.StompDamage(0.6F, d, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
               this.StompDamage(0.6F, d2, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
               this.Stompsound(ds, 0.0F);
            }
         }
      }

      if (this.getAttackState() == 10 && this.attackTicks == 35) {
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.08F, 0, 10);
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYSHOOT.get(), 3.0F, 0.75F);
      }
   }

   private void CircleFlameJet(float vec, float math, int vertexrune, int rune, double time) {
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);

      for (int i = 0; i < vertexrune; i++) {
         float throwAngle = (float)i * (float) Math.PI / (float)(vertexrune / 2);

         for (int k = 0; k < rune; k++) {
            double d2 = 1.1 * (double)(k + 1);
            int d3 = (int)(time * (double)(k + 1));
            this.spawnJet(
               this.m_20185_() + (double)vec * vecX + (double)(f * math) + (double)Mth.m_14089_(throwAngle) * 1.25 * d2,
               this.m_20189_() + (double)vec * vecZ + (double)(f1 * math) + (double)Mth.m_14031_(throwAngle) * 1.25 * d2,
               this.m_20186_() - 2.0,
               this.m_20186_() + 2.0,
               throwAngle,
               d3
            );
         }
      }
   }

   private void spawnJet(double x, double z, double minY, double maxY, float rotation, int delay) {
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
            .m_7967_(new Flame_Jet_Entity(this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, delay, (float)CMConfig.FlameJetDamage, this));
      }
   }

   private void Stompsound(float distance, float math) {
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      this.f_19853_
         .m_6263_(
            (Player)null,
            this.m_20185_() + (double)distance * vecX + (double)(f * math),
            this.m_20186_(),
            this.m_20189_() + (double)distance * vecZ + (double)(f1 * math),
            (SoundEvent)ModSounds.REMNANT_STOMP.get(),
            this.m_5720_(),
            0.6F,
            1.0F
         );
   }

   private void StompDamage(float spreadarc, int distance, int height, float mxy, float vec, float math, int shieldbreakticks, float damage) {
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

         this.spawnBlocks(hitX, hitY + height, hitZ, (int)(this.m_20186_() - (double)height), block, px, pz, mxy, vx, vz, factor, shieldbreakticks, damage);
      }
   }

   private boolean notLavaCliff(double distance) {
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      double px = this.m_20185_() + vecX * distance;
      double pz = this.m_20189_() + vecZ * distance;
      double checkHeight = -2.5;
      Vec3 forwardPosition = new Vec3(px, this.m_20186_() + checkHeight, pz);
      BlockState blockStateBelow = this.f_19853_.m_8055_(new BlockPos(forwardPosition));
      return !blockStateBelow.m_60795_();
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
      float damage
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
      double b0 = (double)hitX - this.m_20185_();
      double b1 = (double)hitZ - this.m_20189_();
      double b2 = Math.max(b0 * b0 + b1 * b1, 0.001);
      fallingBlockEntity.m_5997_(b0 / b2 * 1.5, 0.2 + this.m_217043_().m_188583_() * 0.04, b1 / b2 * 1.5);
      this.f_19853_.m_7967_(fallingBlockEntity);
      AABB selection = new AABB(
         px - 0.5, (double)blockpos.m_123342_() + d0 - 1.0, pz - 0.5, px + 0.5, (double)blockpos.m_123342_() + d0 + (double)mxy, pz + 0.5
      );

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, selection)) {
         if (!this.m_7307_(entity) && !(entity instanceof Netherite_Monstrosity_Entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag) {
               double magnitude = 10.0;
               double x = vx * Math.max((double)factor, 0.2) * magnitude;
               double y = 0.0;
               if (entity.m_20096_()) {
                  y += 0.15;
               }

               double z = vz * Math.max((double)factor, 0.2) * magnitude;
               entity.m_20256_(entity.m_20184_().m_82520_(x, y, z));
            }
         }
      }
   }

   private void doAbsorptionEffects(int x, int y, int z) {
      int MthX = Mth.m_14107_(this.m_20185_());
      int MthY = Mth.m_14107_(this.m_20186_());
      int MthZ = Mth.m_14107_(this.m_20189_());

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = -y; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               this.doAbsorptionEffect(blockpos);
            }
         }
      }
   }

   private void doAbsorptionEffect(BlockPos pos) {
      BlockState state = this.f_19853_.m_8055_(pos);
      if (!this.f_19853_.f_46443_ && state.m_60713_(Blocks.f_49991_)) {
         this.f_19853_.m_46597_(pos, Blocks.f_50016_.m_49966_());
      }
   }

   private void EarthQuake(double area) {
      this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(area))) {
         if (!this.m_7307_(entity) && !(entity instanceof Netherite_Monstrosity_Entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(
               damagesource,
               (float)(
                  (double)((float)this.m_21133_(Attributes.f_22281_))
                     + Math.min(this.m_21133_(Attributes.f_22281_), (double)entity.m_21233_() * CMConfig.MonstrositysHpdamage)
               )
            );
            if (entity.m_21275_(damagesource) && entity instanceof Player player) {
               this.disableShield(player, 120);
            }

            if (flag) {
               this.launch(entity, 2.0, 0.6);
               if (this.getIsBerserk()) {
                  entity.m_20254_(6);
               }
            }
         }
      }
   }

   private void OverPowerKnockBack(double area) {
      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(area))) {
         if (!this.m_7307_(entity) && !(entity instanceof Netherite_Monstrosity_Entity) && entity != this) {
            this.launch(entity, 3.0, 0.35);
         }
      }
   }

   private void Makeparticle(float vec, float math) {
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
            double extraX = (double)(2.0F * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(2.0F * Mth.m_14089_(angle));
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            if (this.getIsBerserk()) {
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123744_,
                     this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            } else if (block.m_60799_() != RenderShape.INVISIBLE) {
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

         if (this.getIsBerserk()) {
            this.f_19853_
               .m_7106_(
                  new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 35, 0.8F, 0.305F, 0.02F, 1.0F, 30.0F, false, RingParticle.EnumRingBehavior.GROW),
                  this.m_20185_() + (double)vec * vecX + (double)(f * math),
                  this.m_20186_() + 0.2F,
                  this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
                  0.0,
                  0.0,
                  0.0
               );
         } else {
            this.f_19853_
               .m_7106_(
                  new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 35, 1.0F, 1.0F, 1.0F, 1.0F, 30.0F, false, RingParticle.EnumRingBehavior.GROW),
                  this.m_20185_() + (double)vec * vecX + (double)(f * math),
                  this.m_20186_() + 0.2F,
                  this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
                  0.0,
                  0.0,
                  0.0
               );
         }
      }
   }

   private void launch(Entity e, double XZpower, double Ypower) {
      double d0 = e.m_20185_() - this.m_20185_();
      double d1 = e.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      e.m_5997_(d0 / d2 * XZpower, Ypower, d1 / d2 * XZpower);
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
            && !blockstate.m_204336_(ModTag.NETHERITE_MONSTROSITY_IMMUNE)
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

   private void berserkBlockBreaking(int x, int y, int z) {
      int MthX = Mth.m_14107_(this.m_20185_());
      int MthY = Mth.m_14107_(this.m_20186_());
      int MthZ = Mth.m_14107_(this.m_20189_());
      if (!this.f_19853_.f_46443_ && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
         for (int k2 = -x; k2 <= x; k2++) {
            for (int l2 = -z; l2 <= z; l2++) {
               for (int j = 0; j <= y; j++) {
                  int i3 = MthX + k2;
                  int k = MthY + j;
                  int l = MthZ + l2;
                  BlockPos blockpos = new BlockPos(i3, k, l);
                  BlockState block = this.f_19853_.m_8055_(blockpos);
                  BlockEntity tileEntity = this.f_19853_.m_7702_(blockpos);
                  if (!block.m_60795_() && !block.m_204336_(ModTag.NETHERITE_MONSTROSITY_IMMUNE)) {
                     if (tileEntity == null && this.f_19796_.m_188503_(4) + 1 == 4) {
                        this.f_19853_.m_7471_(blockpos, true);
                        Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                           this.f_19853_, (double)i3 + 0.5, (double)k + 0.5, (double)l + 0.5, block, 5
                        );
                        this.f_19853_.m_7731_(blockpos, block.m_60819_().m_76188_(), 3);
                        fallingBlockEntity.m_20256_(
                           fallingBlockEntity.m_20184_()
                              .m_82549_(
                                 this.m_20182_()
                                    .m_82546_(fallingBlockEntity.m_20182_())
                                    .m_82542_(
                                       (-1.2 + this.f_19796_.m_188500_()) / 3.0,
                                       (-1.1 + this.f_19796_.m_188500_()) / 3.0,
                                       (-1.2 + this.f_19796_.m_188500_()) / 3.0
                                    )
                              )
                        );
                        this.f_19853_.m_7967_(fallingBlockEntity);
                     } else {
                        this.f_19853_.m_46961_(new BlockPos(i3, k, l), this.shouldDropItem(tileEntity));
                     }
                  }
               }
            }
         }
      }
   }

   private void BlockBreaking() {
      if (!this.m_21525_() && !this.f_19853_.f_46443_ && this.blockBreakCounter == 0 && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
         for (int a = (int)Math.round(this.m_20191_().f_82288_); a <= (int)Math.round(this.m_20191_().f_82291_); a++) {
            for (int b = (int)Math.round(this.m_20191_().f_82289_); b <= (int)Math.round(this.m_20191_().f_82292_) + 1 && b <= 127; b++) {
               for (int c = (int)Math.round(this.m_20191_().f_82290_); c <= (int)Math.round(this.m_20191_().f_82293_); c++) {
                  BlockPos blockpos = new BlockPos(a, b, c);
                  BlockState block = this.f_19853_.m_8055_(blockpos);
                  BlockEntity tileEntity = this.f_19853_.m_7702_(blockpos);
                  if (!block.m_60795_() && !block.m_204336_(ModTag.NETHERITE_MONSTROSITY_IMMUNE)) {
                     boolean flag = this.f_19853_.m_46961_(new BlockPos(a, b, c), this.shouldDropItem(tileEntity));
                     if (flag) {
                        this.blockBreakCounter = 10;
                     }
                  }
               }
            }
         }
      }
   }

   private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
      return offset.m_82496_(-xRot * (float) (Math.PI / 180.0)).m_82524_(-yRot * (float) (Math.PI / 180.0));
   }

   private boolean shouldDropItem(BlockEntity tileEntity) {
      return tileEntity == null ? this.f_19796_.m_188503_(3) + 1 == 3 : true;
   }

   public boolean isBerserk() {
      return this.m_21223_() <= this.m_21233_() / 3.0F;
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

   private void setPartPosition(Netherite_Monstrosity_Part part, double offsetX, double offsetY, double offsetZ) {
      part.m_6034_(
         this.m_20185_() + offsetX * (double)part.scale, this.m_20186_() + offsetY * (double)part.scale, this.m_20189_() + offsetZ * (double)part.scale
      );
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public PartEntity<?>[] getParts() {
      return this.monstrosityParts;
   }

   public void m_141965_(ClientboundAddEntityPacket packet) {
      super.m_141965_(packet);
      Cm_Part_Entity.assignPartIDs(this);
   }

   public void m_7023_(Vec3 travelVector) {
      this.m_7910_((float)this.m_21133_(Attributes.f_22279_) * (this.m_20077_() ? 0.2F : 1.0F));
      if (this.m_6142_() && this.m_20077_()) {
         this.m_19920_(this.m_6113_(), travelVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9));
      } else {
         super.m_7023_(travelVector);
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.MONSTROSITYHURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.MONSTROSITYDEATH.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.MONSTROSITY_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && !this.isSleep();
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

   @Override
   public boolean m_6785_(double p_21542_) {
      return false;
   }

   @Override
   protected boolean m_8028_() {
      return false;
   }

   @Override
   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }

   static class Flareshoot extends InternalAttackGoal {
      private final Netherite_Monstrosity_Entity entity;
      private final int attackshot;
      private final float random;

      public Flareshoot(
         Netherite_Monstrosity_Entity entity,
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
         return super.m_8036_()
            && target != null
            && this.entity.m_20270_(target) >= 10.0F
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.m_21574_().m_148306_(target)
            && this.entity.flare_shoot_cooldown <= 0;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.flare_shoot_cooldown = 120;
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         super.m_8037_();
         int lavabombcount = 5;
         if (target != null && this.entity.attackTicks == this.attackshot) {
            for (int i = 0; i < lavabombcount; i++) {
               float f = Mth.m_14089_(this.entity.f_20883_ * (float) (Math.PI / 180.0));
               float f1 = Mth.m_14031_(this.entity.f_20883_ * (float) (Math.PI / 180.0));
               double theta = (double)this.entity.f_20883_ * (Math.PI / 180.0);
               double vecX = Math.cos(++theta);
               double vecZ = Math.sin(theta);
               double vec = 2.2;
               double math = 3.4;
               Flare_Bomb_Entity lava = new Flare_Bomb_Entity((EntityType<Flare_Bomb_Entity>)ModEntities.FLARE_BOMB.get(), this.entity.f_19853_, this.entity);
               lava.m_20343_(
                  this.entity.m_20185_() + vec * vecX + (double)f * math, this.entity.m_20227_(0.65), this.entity.m_20189_() + vec * vecZ + (double)f1 * math
               );
               double d0 = target.m_20185_() - lava.m_20185_();
               double d1 = target.m_20191_().f_82289_ + (double)(target.m_20206_() / 3.0F) - lava.m_20186_();
               double d2 = target.m_20189_() - lava.m_20189_();
               double d3 = (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2));
               lava.m_6686_(d0, d1 + d3 * 0.2F, d2, 1.0F, (float)(1 + i * 8));
               this.entity.f_19853_.m_7967_(lava);
            }
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class Magmashoot extends InternalAttackGoal {
      private final Netherite_Monstrosity_Entity entity;
      private final int attackshot;
      private final float random;

      public Magmashoot(
         Netherite_Monstrosity_Entity entity,
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
         return super.m_8036_()
            && target != null
            && this.entity.m_20270_(target) >= 14.0F
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.m_21574_().m_148306_(target)
            && this.entity.getMagazine() < CMConfig.Lavabombmagazine
            && this.entity.shoot_cooldown <= 0;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.shoot_cooldown = 240;
         this.entity.setMagazine(this.entity.getMagazine() + 1);
      }

      @Override
      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         super.m_8037_();
         int lavabombcount = CMConfig.Lavabombamount;
         if (target != null && this.entity.attackTicks == this.attackshot) {
            for (int i = 0; i < lavabombcount; i++) {
               Lava_Bomb_Entity lava = new Lava_Bomb_Entity((EntityType<Lava_Bomb_Entity>)ModEntities.LAVA_BOMB.get(), this.entity.f_19853_, this.entity);
               double d0 = target.m_20185_() - this.entity.headPart.m_20185_();
               double d1 = target.m_20191_().f_82289_ + (double)(target.m_20206_() / 3.0F) - lava.m_20186_();
               double d2 = target.m_20189_() - this.entity.headPart.m_20189_();
               double d3 = (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2));
               lava.m_6686_(d0, d1 + d3 * 0.2F, d2, 1.0F, (float)(24 - this.entity.f_19853_.m_46791_().m_19028_() * 4));
               this.entity.f_19853_.m_7967_(lava);
            }
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }

   static class MonstrosityPhaseChangeGoal extends Goal {
      protected final Netherite_Monstrosity_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;

      public MonstrosityPhaseChangeGoal(Netherite_Monstrosity_Entity entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick) {
         this.entity = entity;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
      }

      public boolean m_8036_() {
         return !this.entity.getIsBerserk() && this.entity.getAttackState() == this.getattackstate && this.entity.isBerserk();
      }

      public void m_8056_() {
         this.entity.setIsBerserk(true);
         if (this.getattackstate != this.attackstate) {
            this.entity.setAttackState(this.attackstate);
         }
      }

      public void m_8041_() {
         this.entity.setAttackState(this.attackendstate);
      }

      public boolean m_8045_() {
         return this.attackMaxtick > 0 ? this.entity.attackTicks <= this.attackMaxtick : this.m_8036_();
      }
   }

   static class ShoulderCheck extends InternalAttackGoal {
      private final Netherite_Monstrosity_Entity entity;
      private final int attackshot;
      private final int attackendshot;
      private final float random;

      public ShoulderCheck(
         Netherite_Monstrosity_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         int attackendshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.attackendshot = attackendshot;
         this.random = random;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean m_8036_() {
         LivingEntity target = this.entity.m_5448_();
         return super.m_8036_()
            && target != null
            && this.entity.m_20270_(target) >= 5.75F
            && this.entity.m_217043_().m_188501_() * 100.0F < this.random
            && this.entity.m_21574_().m_148306_(target)
            && this.entity.check_cooldown <= 0;
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         this.entity.check_cooldown = 80;
      }

      @Override
      public void m_8037_() {
         super.m_8037_();
         if (this.entity.attackTicks > this.attackshot
            && this.entity.attackTicks < this.attackendshot
            && (this.entity.f_19861_ || this.entity.getOnLava())
            && this.entity.notLavaCliff(2.0)) {
            Vec3 vector3d = this.entity.m_20184_();
            float f = this.entity.m_146908_() * (float) (Math.PI / 180.0);
            Vec3 vector3d1 = new Vec3((double)(-Mth.m_14031_(f)), this.entity.m_20184_().f_82480_, (double)Mth.m_14089_(f))
               .m_82490_(0.5)
               .m_82549_(vector3d.m_82490_(0.5));
            this.entity.m_20334_(vector3d1.f_82479_, this.entity.m_20184_().f_82480_, vector3d1.f_82481_);
         }
      }

      @Override
      public boolean m_183429_() {
         return true;
      }
   }
}
