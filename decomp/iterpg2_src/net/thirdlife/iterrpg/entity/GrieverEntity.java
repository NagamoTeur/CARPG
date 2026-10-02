package net.thirdlife.iterrpg.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.GrieverScreamProcedure;
import net.thirdlife.iterrpg.procedures.GrieverScreamTimeProcedure;
import net.thirdlife.iterrpg.procedures.GrieverSpawnProcedure;
import net.thirdlife.iterrpg.procedures.SoulsReleaseProcedure;

public class GrieverEntity extends Monster {
   public GrieverEntity(SpawnEntity packet, Level world) {
      this((EntityType<GrieverEntity>)IterRpgModEntities.GRIEVER.get(), world);
   }

   public GrieverEntity(EntityType<GrieverEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 4;
      this.m_21557_(false);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new PanicGoal(this, 1.2));
      this.f_21345_.m_25352_(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:griever_ambient"));
   }

   public void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.step")), 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:griever_hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:griever_death"));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      GrieverScreamProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this, source.m_7639_());
      return source == DamageSource.f_19312_ ? false : super.m_6469_(source, amount);
   }

   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      SoulsReleaseProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), source.m_7639_());
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag
   ) {
      SpawnGroupData retval = super.m_6518_(world, difficulty, reason, livingdata, tag);
      GrieverSpawnProcedure.execute(this);
      return retval;
   }

   public void m_6075_() {
      super.m_6075_();
      GrieverScreamTimeProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public void m_6123_(Player sourceentity) {
      super.m_6123_(sourceentity);
      GrieverScreamProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this, sourceentity);
   }

   public static void init() {
      SpawnPlacements.m_21754_(
         (EntityType)IterRpgModEntities.GRIEVER.get(),
         Type.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (entityType, world, reason, pos, random) -> world.m_46791_() != Difficulty.PEACEFUL
               && Monster.m_219009_(world, pos, random)
               && Mob.m_217057_(entityType, world, reason, pos, random)
      );
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.15);
      builder = builder.m_22268_(Attributes.f_22276_, 20.0);
      builder = builder.m_22268_(Attributes.f_22284_, 4.0);
      builder = builder.m_22268_(Attributes.f_22281_, 3.0);
      builder = builder.m_22268_(Attributes.f_22277_, 16.0);
      return builder.m_22268_(Attributes.f_22278_, 0.5);
   }
}
