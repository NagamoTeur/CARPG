package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.api.utils.Icons;
import com.obscuria.obscureapi.util.PlayerUtils;
import com.obscuria.obscureapi.util.TextUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class MazeMother extends Monster {
   public MazeMother(SpawnEntity packet, Level world) {
      this((EntityType<MazeMother>)AquamiraeEntities.MAZE_MOTHER.get(), world);
   }

   public MazeMother(EntityType<MazeMother> type, Level world) {
      super(type, world);
      this.f_21364_ = 6;
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_21342_ = new MoveControl(this) {
         public void m_8126_() {
            if (MazeMother.this.m_20069_()) {
               MazeMother.this.m_20256_(MazeMother.this.m_20184_().m_82520_(0.0, 0.005, 0.0));
            }

            if (this.f_24981_ == Operation.MOVE_TO && !MazeMother.this.m_21573_().m_26571_()) {
               double dx = this.f_24975_ - MazeMother.this.m_20185_();
               double dy = this.f_24976_ - MazeMother.this.m_20186_();
               double dz = this.f_24977_ - MazeMother.this.m_20189_();
               float f = (float)(Mth.m_14136_(dz, dx) * (180.0 / Math.PI)) - 90.0F;
               float f1 = (float)(this.f_24978_ * Objects.requireNonNull(MazeMother.this.m_21051_(Attributes.f_22279_)).m_22135_());
               MazeMother.this.m_146922_(this.m_24991_(MazeMother.this.m_146908_(), f, 10.0F));
               MazeMother.this.f_20883_ = MazeMother.this.m_146908_();
               MazeMother.this.f_20885_ = MazeMother.this.m_146908_();
               if (MazeMother.this.m_20069_()) {
                  MazeMother.this.m_7910_((float)Objects.requireNonNull(MazeMother.this.m_21051_(Attributes.f_22279_)).m_22135_());
                  float f2 = -((float)(Mth.m_14136_(dy, (double)((float)Math.sqrt(dx * dx + dz * dz))) * (180.0 / Math.PI)));
                  f2 = Mth.m_14036_(Mth.m_14177_(f2), -85.0F, 85.0F);
                  MazeMother.this.m_146926_(this.m_24991_(MazeMother.this.m_146909_(), f2, 5.0F));
                  float f3 = Mth.m_14089_(MazeMother.this.m_146909_() * (float) (Math.PI / 180.0));
                  MazeMother.this.m_21564_(f3 * f1);
                  MazeMother.this.m_21567_((float)((double)f1 * dy));
               } else {
                  MazeMother.this.m_7910_(f1 * 0.05F);
               }
            } else {
               MazeMother.this.m_7910_(0.0F);
               MazeMother.this.m_21567_(0.0F);
               MazeMother.this.m_21564_(0.0F);
            }
         }
      };
   }

   @NotNull
   public AABB m_6921_() {
      return super.m_6921_().m_82400_(10.0);
   }

   public boolean m_6785_(double distance) {
      return distance > 200.0;
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level world) {
      return new WaterBoundPathNavigation(this, world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(@NotNull LivingEntity entity) {
            return 44.0;
         }
      });
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, AbstractIllager.class, false, false));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, AbstractVillager.class, false, false));
      this.f_21345_.m_25352_(6, new RandomSwimmingGoal(this, 1.0, 40));
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public double m_6048_() {
      return super.m_6048_() + 0.8;
   }

   public SoundEvent m_7515_() {
      return SoundEvents.f_11878_;
   }

   public SoundEvent m_7975_(@NotNull DamageSource source) {
      return SoundEvents.f_11884_;
   }

   public SoundEvent m_5592_() {
      return SoundEvents.f_11881_;
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      return source == DamageSource.f_19312_ ? false : super.m_6469_(source, amount);
   }

   public SpawnGroupData m_6518_(
      @NotNull ServerLevelAccessor world,
      @NotNull DifficultyInstance difficulty,
      @NotNull MobSpawnType reason,
      @Nullable SpawnGroupData livingdata,
      @Nullable CompoundTag tag
   ) {
      Aquamirae.loadFromConfig(this, (Attribute)ForgeMod.SWIM_SPEED.get(), (Double)AquamiraeConfig.Common.motherSwimSpeed.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22276_, (Double)AquamiraeConfig.Common.motherMaxHealth.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22284_, (Double)AquamiraeConfig.Common.motherArmor.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22281_, (Double)AquamiraeConfig.Common.motherAttackDamage.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22277_, (Double)AquamiraeConfig.Common.motherFollowRange.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22282_, (Double)AquamiraeConfig.Common.motherAttackKnockback.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22278_, (Double)AquamiraeConfig.Common.motherKnockbackResistance.get());
      Vec3 center = this.m_20182_();
      List<Player> players = this.m_9236_()
         .m_6443_(Player.class, new AABB(center, center).m_82400_(100.0), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(ent -> ent.m_20238_(center)))
         .toList();
      if ((Boolean)AquamiraeConfig.Common.notifications.get()) {
         players.forEach(player -> PlayerUtils.sendMessage(player, Icons.INFO + TextUtils.translation("info.maze_mother_spawn")));
      }

      return super.m_6518_(world, difficulty, reason, livingdata, tag);
   }

   protected void m_7562_() {
      Vec3 center = this.m_20182_();
      List<Player> players = this.m_9236_()
         .m_6443_(Player.class, new AABB(center, center).m_82400_(128.0), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(ent -> ent.m_20238_(center)))
         .toList();
      if (!players.isEmpty()) {
         this.m_21310_(0);
      }

      super.m_7562_();
   }

   public void m_6075_() {
      if (this.m_20069_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, -0.001, 0.0));
      }

      if (!this.f_19853_.f_46443_) {
         this.getPersistentData().m_128347_("breakIce", this.getPersistentData().m_128459_("breakIce") + 1.0);
         if (this.getPersistentData().m_128459_("breakIce") > 10.0) {
            this.getPersistentData().m_128347_("breakIce", 0.0);
            this.breakIce(-1);
            this.breakIce(4);
            this.breakIce(5);
         }
      }

      super.m_6075_();
   }

   private void breakIce(int offset) {
      for (int ix = -6; ix <= 6; ix++) {
         for (int iz = -6; iz <= 6; iz++) {
            BlockPos pos = new BlockPos(this.m_146903_() + ix, this.m_146904_() + offset, this.m_146907_() + iz);
            if (this.f_19853_.m_8055_(pos.m_7494_()).m_60767_() == Material.f_76296_
               && this.f_19853_.m_8055_(pos).m_204336_(Aquamirae.MAZE_MOTHER_DESTROY)
               && this.f_19796_.m_188499_()) {
               if (!this.f_19853_.m_8055_(pos).m_60713_(Blocks.f_50126_) && !this.f_19853_.m_8055_(pos).m_60713_(Blocks.f_50449_)) {
                  this.f_19853_.m_46961_(pos, true);
               } else {
                  this.f_19853_.m_46961_(pos, true);
                  this.f_19853_.m_7731_(pos, Blocks.f_49990_.m_49966_(), 3);
               }
            }
         }
      }
   }

   public boolean canDrownInFluidType(FluidType type) {
      return type == ForgeMod.WATER_TYPE.get() ? false : super.canDrownInFluidType(type);
   }

   public boolean m_6914_(@NotNull LevelReader world) {
      return world.m_45784_(this);
   }

   public boolean isPushedByFluid(FluidType type) {
      return type == ForgeMod.WATER_TYPE.get() ? false : super.isPushedByFluid(type);
   }

   @NotNull
   public static Builder createAttributes() {
      return Mob.m_21552_()
         .m_22268_((Attribute)ForgeMod.SWIM_SPEED.get(), 3.0)
         .m_22268_(Attributes.f_22276_, 100.0)
         .m_22268_(Attributes.f_22284_, 6.0)
         .m_22268_(Attributes.f_22281_, 5.0)
         .m_22268_(Attributes.f_22277_, 128.0)
         .m_22268_(Attributes.f_22278_, 0.2)
         .m_22268_(Attributes.f_22282_, 0.5);
   }
}
