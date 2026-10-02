package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import com.hollingsworth.arsnouveau.common.event.FamiliarEvents;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketDispelFamiliars {
   public PacketDispelFamiliars() {
   }

   public PacketDispelFamiliars(FriendlyByteBuf buf) {
   }

   public void toBytes(FriendlyByteBuf buf) {
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (ctx.get().getSender() != null) {
            Entity owner = ctx.get().getSender();
            dispelForPlayer(owner);
         }
      });
      ctx.get().setPacketHandled(true);
   }

   public static boolean dispelForPlayer(Entity owner) {
      boolean removedFamiliar = false;

      for (FamiliarEntity familiarEntity : FamiliarEvents.getFamiliars(i -> i.getOwnerID() == null || i.getOwnerID().equals(owner.m_20148_()))) {
         familiarEntity.m_142687_(RemovalReason.DISCARDED);
         ParticleUtil.spawnPoof((ServerLevel)owner.f_19853_, familiarEntity.getThisEntity().m_20183_());
         removedFamiliar = true;
      }

      if (removedFamiliar) {
         PortUtil.sendMessage(owner, Component.m_237115_("ars_nouveau.removed_familiars"));
      }

      return removedFamiliar;
   }
}
