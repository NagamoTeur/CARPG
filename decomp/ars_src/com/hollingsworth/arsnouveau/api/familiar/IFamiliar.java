package com.hollingsworth.arsnouveau.api.familiar;

import com.hollingsworth.arsnouveau.api.event.FamiliarSummonEvent;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;

public interface IFamiliar {
   ResourceLocation getHolderID();

   void setHolderID(ResourceLocation var1);

   UUID getOwnerID();

   void setOwnerID(UUID var1);

   default Entity getThisEntity() {
      return (Entity)this;
   }

   @Nullable
   default Entity getOwnerServerside() {
      return ((ServerLevel)this.getThisEntity().f_19853_).m_8791_(this.getOwnerID());
   }

   default void onFamiliarSpawned(FamiliarSummonEvent event) {
      if (event.owner.equals(this.getOwner()) && !event.getEntity().equals(this)) {
         this.getThisEntity().m_142687_(RemovalReason.DISCARDED);
      }
   }

   @Nullable
   default LivingEntity getOwner() {
      return !this.getThisEntity().f_19853_.f_46443_ && this.getOwnerID() != null
         ? (LivingEntity)((ServerLevel)this.getThisEntity().f_19853_).m_8791_(this.getOwnerID())
         : null;
   }

   default boolean wantsToAttack(LivingEntity ownerLastHurt, LivingEntity owner) {
      return true;
   }
}
