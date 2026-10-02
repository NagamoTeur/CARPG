package net.thirdlife.iterrpg.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.EarthElementalAttackProcedure;
import net.thirdlife.iterrpg.procedures.EarthElementalSpawnConditionProcedure;
import net.thirdlife.iterrpg.procedures.ElementalSlowFallingAssignProcedure;

public class EarthElementalEntity extends Monster {
   public EarthElementalEntity(SpawnEntity packet, Level world) {
      this((EntityType<EarthElementalEntity>)IterRpgModEntities.EARTH_ELEMENTAL.get(), world);
   }

   public EarthElementalEntity(EntityType<EarthElementalEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 6;
      this.m_21557_(false);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected PathNavigation m_6037_(Level world) {
      return new FlyingPathNavigation(this, world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_
         .m_25352_(
            1,
            new Goal() {
               {
                  this.m_7021_(EnumSet.of(Flag.MOVE));
               }

               public boolean m_8036_() {
                  return EarthElementalEntity.this.m_5448_() != null && !EarthElementalEntity.this.m_21566_().m_24995_();
               }

               public boolean m_8045_() {
                  return EarthElementalEntity.this.m_21566_().m_24995_()
                     && EarthElementalEntity.this.m_5448_() != null
                     && EarthElementalEntity.this.m_5448_().m_6084_();
               }

               public void m_8056_() {
                  LivingEntity livingentity = EarthElementalEntity.this.m_5448_();
                  Vec3 vec3d = livingentity.m_20299_(1.0F);
                  EarthElementalEntity.this.f_21342_.m_6849_(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, 2.0);
               }

               public void m_8037_() {
                  LivingEntity livingentity = EarthElementalEntity.this.m_5448_();
                  if (EarthElementalEntity.this.m_20191_().m_82381_(livingentity.m_20191_())) {
                     EarthElementalEntity.this.m_7327_(livingentity);
                  } else {
                     double d0 = EarthElementalEntity.this.m_20280_(livingentity);
                     if (d0 < 8.0) {
                        Vec3 vec3d = livingentity.m_20299_(1.0F);
                        EarthElementalEntity.this.f_21342_.m_6849_(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, 2.0);
                     }
                  }
               }
            }
         );
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21345_.m_25352_(3, new RandomStrollGoal(this, 1.0, 20) {
         protected Vec3 m_7037_() {
            RandomSource random = EarthElementalEntity.this.m_217043_();
            double dir_x = EarthElementalEntity.this.m_20185_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            double dir_y = EarthElementalEntity.this.m_20186_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            double dir_z = EarthElementalEntity.this.m_20189_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            return new Vec3(dir_x, dir_y, dir_z);
         }
      });
      this.f_21345_.m_25352_(4, new RandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(7, new FloatGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.azalea.step"));
   }

   public void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.moss.place")), 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.cave_vines.break"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.azalea_leaves.break"));
   }

   public boolean m_142535_(float l, float d, DamageSource source) {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source == DamageSource.f_19315_) {
         return false;
      } else {
         return source == DamageSource.f_19314_ ? false : super.m_6469_(source, amount);
      }
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag
   ) {
      SpawnGroupData retval = super.m_6518_(world, difficulty, reason, livingdata, tag);
      ElementalSlowFallingAssignProcedure.execute(this);
      return retval;
   }

   public void m_6075_() {
      super.m_6075_();
      EarthElementalAttackProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public void m_20242_(boolean ignored) {
      super.m_20242_(true);
   }

   public void m_8107_() {
      super.m_8107_();
      this.m_20242_(true);
   }

   public static void init() {
      SpawnPlacements.m_21754_(
         (EntityType)IterRpgModEntities.EARTH_ELEMENTAL.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
            int x = pos.m_123341_();
            int y = pos.m_123342_();
            int z = pos.m_123343_();
            return EarthElementalSpawnConditionProcedure.execute(world, (double)x, (double)y, (double)z);
         }
      );
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.25);
      builder = builder.m_22268_(Attributes.f_22276_, 40.0);
      builder = builder.m_22268_(Attributes.f_22284_, 6.0);
      builder = builder.m_22268_(Attributes.f_22281_, 3.0);
      builder = builder.m_22268_(Attributes.f_22277_, 32.0);
      return builder.m_22268_(Attributes.f_22280_, 0.25);
   }
}
