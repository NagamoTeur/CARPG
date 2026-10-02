package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class Anglerfish extends Monster implements IAnimated {
   public final Animation ATTACK = new Animation(1);
   public int attackTick = 0;
   public float groundMod = 0.0F;
   public float groundModLerp = 0.0F;

   public Anglerfish(SpawnEntity packet, Level world) {
      this((EntityType<Anglerfish>)AquamiraeEntities.ANGLERFISH.get(), world);
   }

   public Anglerfish(EntityType<Anglerfish> type, Level world) {
      super(type, world);
      this.f_21364_ = 12;
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_21342_ = new MoveControl(this) {
         public void m_8126_() {
            if (Anglerfish.this.m_20069_()) {
               Anglerfish.this.m_20256_(Anglerfish.this.m_20184_().m_82520_(0.0, 0.005, 0.0));
            }

            if (this.f_24981_ == Operation.MOVE_TO && !Anglerfish.this.m_21573_().m_26571_()) {
               double dx = this.f_24975_ - Anglerfish.this.m_20185_();
               double dy = this.f_24976_ - Anglerfish.this.m_20186_();
               double dz = this.f_24977_ - Anglerfish.this.m_20189_();
               float f = (float)(Mth.m_14136_(dz, dx) * (180.0 / Math.PI)) - 90.0F;
               float f1 = (float)(this.f_24978_ * Objects.requireNonNull(Anglerfish.this.m_21051_(Attributes.f_22279_)).m_22135_());
               Anglerfish.this.m_146922_(this.m_24991_(Anglerfish.this.m_146908_(), f, 10.0F));
               Anglerfish.this.f_20883_ = Anglerfish.this.m_146908_();
               Anglerfish.this.f_20885_ = Anglerfish.this.m_146908_();
               if (Anglerfish.this.m_20069_()) {
                  Anglerfish.this.m_7910_((float)Objects.requireNonNull(Anglerfish.this.m_21051_(Attributes.f_22279_)).m_22135_());
                  float f2 = -((float)(Mth.m_14136_(dy, (double)((float)Math.sqrt(dx * dx + dz * dz))) * (180.0 / Math.PI)));
                  f2 = Mth.m_14036_(Mth.m_14177_(f2), -85.0F, 85.0F);
                  Anglerfish.this.m_146926_(this.m_24991_(Anglerfish.this.m_146909_(), f2, 5.0F));
                  float f3 = Mth.m_14089_(Anglerfish.this.m_146909_() * (float) (Math.PI / 180.0));
                  Anglerfish.this.m_21564_(f3 * f1);
                  Anglerfish.this.m_21567_((float)((double)f1 * dy));
               } else {
                  Anglerfish.this.m_7910_(f1 * 0.05F);
               }
            } else {
               Anglerfish.this.m_7910_(0.0F);
               Anglerfish.this.m_21567_(0.0F);
               Anglerfish.this.m_21564_(0.0F);
            }
         }
      };
   }

   public Optional<Animation> getAnimation(byte id) {
      return id == 1 ? Optional.of(this.ATTACK) : Optional.empty();
   }

   public boolean m_7327_(@NotNull Entity entity) {
      boolean hurt = super.m_7327_(entity);
      if (hurt && entity instanceof LivingEntity living) {
         living.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 100, 0));
      }

      return hurt;
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level world) {
      return new WaterBoundPathNavigation(this, world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 3.0, false) {
         protected double m_6639_(@NotNull LivingEntity entity) {
            return 0.0;
         }
      });
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, Animal.class, false, false));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, AbstractIllager.class, false, false));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, AbstractVillager.class, false, false));
      this.f_21345_.m_25352_(5, new RandomSwimmingGoal(this, 1.0, 40) {
         protected Vec3 m_7037_() {
            return BehaviorUtils.m_147444_(this.f_25725_, 32, 7);
         }
      });
   }

   public void m_6075_() {
      AnimationHelper.handle(new Animation[]{this.ATTACK});
      this.ATTACK.sound(this, 6, AquamiraeSounds.ENTITY_EEL_BITE, SoundSource.HOSTILE, 2.0F, 1.0F);
      this.groundModLerp = this.groundMod;
      if (this.m_20069_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, -0.001F, 0.0));
         this.groundMod = this.groundMod + (0.0F - this.groundMod) * 0.2F;
      } else {
         this.groundMod = this.groundMod + (1.0F - this.groundMod) * 0.2F;
      }

      if (this.ATTACK.isPlaying()) {
         this.attackTick = 10;
         if (this.m_5448_() != null) {
            this.f_21365_.m_148051_(this.m_5448_());
         }

         if (this.ATTACK.getTick() < 18) {
            this.m_20256_(this.m_20184_().m_82490_(0.9F));
         }

         if (this.ATTACK.getTick() == 18 && this.m_5448_() != null) {
            this.m_20256_(this.m_20184_().m_82549_(this.m_20182_().m_82505_(this.m_5448_().m_20182_()).m_82490_(0.4F)));
         }

         if (this.ATTACK.getTick() == 21 && this.m_5448_() != null && this.m_5448_().m_20182_().m_82554_(this.m_20182_()) <= 2.5) {
            this.m_7327_(this.m_5448_());
         }
      } else if (this.attackTick <= 0 && this.m_5448_() != null && this.m_5448_().m_20182_().m_82554_(this.m_20182_()) <= 5.0) {
         this.ATTACK.play(this, 40);
      } else {
         this.attackTick--;
      }

      super.m_6075_();
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
   }

   public SoundEvent m_7975_(@NotNull DamageSource source) {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_HURT.get();
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_DEATH.get();
   }

   public void m_7355_(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
      this.m_5496_(SoundEvents.f_12004_, 0.15F, 1.0F);
   }

   public SpawnGroupData m_6518_(
      @NotNull ServerLevelAccessor world,
      @NotNull DifficultyInstance difficulty,
      @NotNull MobSpawnType spawnType,
      @Nullable SpawnGroupData spawnGroupData,
      @Nullable CompoundTag tag
   ) {
      if (world instanceof ServerLevel server && spawnType == MobSpawnType.NATURAL && this.f_19796_.m_216339_(1, 200) == 1) {
         MazeMother mazeMother = new MazeMother((EntityType<MazeMother>)AquamiraeEntities.MAZE_MOTHER.get(), server);
         mazeMother.m_20219_(this.m_20182_());
         mazeMother.m_6518_(server, difficulty, spawnType, null, null);
         world.m_7967_(mazeMother);
      }

      Aquamirae.loadFromConfig(this, (Attribute)ForgeMod.SWIM_SPEED.get(), (Double)AquamiraeConfig.Common.anglerfishSwimSpeed.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22276_, (Double)AquamiraeConfig.Common.anglerfishMaxHealth.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22284_, (Double)AquamiraeConfig.Common.anglerfishArmor.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22281_, (Double)AquamiraeConfig.Common.anglerfishAttackDamage.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22277_, (Double)AquamiraeConfig.Common.anglerfishFollowRange.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22282_, (Double)AquamiraeConfig.Common.anglerfishAttackKnockback.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22278_, (Double)AquamiraeConfig.Common.anglerfishKnockbackResistance.get());
      return super.m_6518_(world, difficulty, spawnType, spawnGroupData, tag);
   }

   public boolean canDrownInFluidType(FluidType type) {
      return type == ForgeMod.WATER_TYPE.get() ? false : super.canDrownInFluidType(type);
   }

   public boolean m_6914_(LevelReader world) {
      return world.m_45784_(this);
   }

   public boolean isPushedByFluid(FluidType type) {
      return type == ForgeMod.WATER_TYPE.get() ? false : super.isPushedByFluid(type);
   }

   public static SpawnPredicate<Anglerfish> getSpawnRules() {
      return (entityType, level, spawnType, pos, random) -> level.m_6425_(pos).m_205070_(FluidTags.f_13131_) && level.m_46791_() != Difficulty.PEACEFUL;
   }

   @NotNull
   public static Builder createAttributes() {
      return Mob.m_21552_()
         .m_22268_((Attribute)ForgeMod.SWIM_SPEED.get(), 3.0)
         .m_22268_(Attributes.f_22276_, 40.0)
         .m_22268_(Attributes.f_22284_, 2.0)
         .m_22268_(Attributes.f_22281_, 6.0)
         .m_22268_(Attributes.f_22277_, 48.0)
         .m_22268_(Attributes.f_22278_, 0.0)
         .m_22268_(Attributes.f_22282_, 1.0);
   }
}
