package net.thirdlife.iterrpg.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.LiberatorGiveProcedure;
import net.thirdlife.iterrpg.procedures.SorrowSealedAttackProcedure;
import net.thirdlife.iterrpg.procedures.SorrowsealedPositionRememberProcedure;

public class SorrowsealedEntity extends Monster {
   private final ServerBossEvent bossInfo = new ServerBossEvent(this.m_5446_(), BossBarColor.PURPLE, BossBarOverlay.NOTCHED_10);

   public SorrowsealedEntity(SpawnEntity packet, Level world) {
      this((EntityType<SorrowsealedEntity>)IterRpgModEntities.SORROWSEALED.get(), world);
   }

   public SorrowsealedEntity(EntityType<SorrowsealedEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 20;
      this.m_21557_(false);
      this.m_21530_();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new LookAtPlayerGoal(this, Player.class, 16.0F));
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.step"));
   }

   public void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.step")), 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.break"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.beacon.deactivate"));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source == DamageSource.f_19315_) {
         return false;
      } else {
         return source == DamageSource.f_19314_ ? false : super.m_6469_(source, amount);
      }
   }

   public void m_6667_(DamageSource source) {
      super.m_6667_(source);
      LiberatorGiveProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag
   ) {
      SpawnGroupData retval = super.m_6518_(world, difficulty, reason, livingdata, tag);
      SorrowsealedPositionRememberProcedure.execute(this);
      return retval;
   }

   public void m_6075_() {
      super.m_6075_();
      SorrowSealedAttackProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public boolean m_7337_(Entity entity) {
      return true;
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6072_() {
      return false;
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   public void m_8024_() {
      super.m_8024_();
      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.0);
      builder = builder.m_22268_(Attributes.f_22276_, 512.0);
      builder = builder.m_22268_(Attributes.f_22284_, 12.0);
      builder = builder.m_22268_(Attributes.f_22281_, 3.0);
      builder = builder.m_22268_(Attributes.f_22277_, 16.0);
      return builder.m_22268_(Attributes.f_22278_, 1.0);
   }
}
