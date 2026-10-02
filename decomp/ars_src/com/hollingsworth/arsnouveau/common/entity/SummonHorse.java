package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SummonHorse extends Horse implements ISummon {
   public int ticksLeft;
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(SummonHorse.class, EntityDataSerializers.f_135041_);

   public SummonHorse(EntityType<? extends Horse> type, Level worldIn) {
      super(type, worldIn);
   }

   protected boolean m_30628_() {
      return false;
   }

   public InteractionResult m_6071_(Player p_230254_1_, InteractionHand p_230254_2_) {
      return super.m_6071_(p_230254_1_, p_230254_2_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER_UUID, Optional.of(Util.f_137441_));
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         this.ticksLeft--;
         if (this.ticksLeft <= 0) {
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
            this.onSummonDeath(this.f_19853_, null, true);
         }
      }
   }

   @Nullable
   public LivingEntity getOwner() {
      return ISummon.super.m_21826_() instanceof LivingEntity living ? living : null;
   }

   @Nullable
   @Override
   public Entity getOwner(ServerLevel world) {
      return ISummon.super.getOwner(world);
   }

   @Nullable
   @Override
   public UUID m_21805_() {
      return super.m_30615_();
   }

   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);
      this.onSummonDeath(this.f_19853_, cause, false);
   }

   public boolean m_7066_(ItemStack itemstackIn) {
      return false;
   }

   protected void m_5907_() {
   }

   public int m_213860_() {
      return 0;
   }

   public SimpleContainer getHorseInventory() {
      return this.f_30520_;
   }

   public void m_213583_(Player playerEntity) {
   }

   public boolean m_7848_(Animal otherAnimal) {
      return false;
   }

   public boolean m_35506_() {
      return false;
   }

   public boolean m_6898_(ItemStack stack) {
      return false;
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.ticksLeft = compound.m_128451_("left");
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("left", this.ticksLeft);
      this.writeOwner(compound);
   }

   @Override
   public int getTicksLeft() {
      return this.ticksLeft;
   }

   @Override
   public void setTicksLeft(int ticks) {
      this.ticksLeft = ticks;
   }

   @Nullable
   @Override
   public UUID getOwnerID() {
      return ((Optional)this.m_20088_().m_135370_(OWNER_UUID)).isEmpty() ? this.m_20148_() : (UUID)((Optional)this.m_20088_().m_135370_(OWNER_UUID)).get();
   }

   @Override
   public void setOwnerID(UUID uuid) {
      this.m_20088_().m_135381_(OWNER_UUID, Optional.ofNullable(uuid));
   }
}
