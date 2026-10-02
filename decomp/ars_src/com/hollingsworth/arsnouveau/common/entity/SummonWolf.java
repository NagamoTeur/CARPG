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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SummonWolf extends Wolf implements ISummon {
   public int ticksLeft;
   public boolean isWildenSummon;
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(SummonWolf.class, EntityDataSerializers.f_135041_);

   public SummonWolf(EntityType<? extends Wolf> type, Level worldIn) {
      super(type, worldIn);
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

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER_UUID, Optional.of(Util.f_137441_));
   }

   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);
      this.onSummonDeath(this.f_19853_, cause, false);
   }

   public boolean m_35506_() {
      return false;
   }

   public boolean m_7848_(Animal pOtherAnimal) {
      return false;
   }

   public boolean m_6898_(ItemStack stack) {
      return false;
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.ticksLeft = compound.m_128451_("left");
      this.isWildenSummon = compound.m_128471_("wildenSummon");
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("left", this.ticksLeft);
      compound.m_128379_("wildenSummon", this.isWildenSummon);
   }

   public int m_213860_() {
      return 0;
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
