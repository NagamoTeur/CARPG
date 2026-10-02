package com.hollingsworth.arsnouveau.api.entity;

import com.hollingsworth.arsnouveau.api.event.SummonEvent;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public interface ISummon extends OwnableEntity {
   int getTicksLeft();

   void setTicksLeft(int var1);

   @Nullable
   default LivingEntity getLivingEntity() {
      return this instanceof LivingEntity ? (LivingEntity)this : null;
   }

   @Nullable
   default UUID m_21805_() {
      return this.getOwnerID();
   }

   @Nullable
   default Entity m_21826_() {
      return this instanceof Entity && ((Entity)this).m_20193_() instanceof ServerLevel serverLevel ? this.getOwner(serverLevel) : null;
   }

   void setOwnerID(UUID var1);

   default void onSummonDeath(Level world, @Nullable DamageSource source, boolean didExpire) {
      MinecraftForge.EVENT_BUS.post(new SummonEvent.Death(world, this, source, didExpire));
   }

   default void writeOwner(CompoundTag tag) {
      if (this.m_21805_() != null) {
         tag.m_128362_("owner", this.m_21805_());
      }
   }

   @Nullable
   default Entity readOwner(ServerLevel world, CompoundTag tag) {
      return tag.m_128441_("owner") ? world.m_8791_(tag.m_128342_("owner")) : null;
   }

   @Nullable
   @Deprecated(
      forRemoval = true
   )
   default UUID getOwnerID() {
      return null;
   }

   @Deprecated(
      forRemoval = true
   )
   @Nullable
   default Entity getOwner(ServerLevel world) {
      return this.m_21805_() != null ? world.m_8791_(this.m_21805_()) : null;
   }
}
