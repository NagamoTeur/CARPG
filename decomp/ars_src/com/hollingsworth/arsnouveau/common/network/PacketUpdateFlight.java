package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.ArsNouveau;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateFlight {
   public boolean canFly;
   public boolean wasFlying;

   public PacketUpdateFlight(FriendlyByteBuf buf) {
      this.canFly = buf.readBoolean();
      this.wasFlying = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeBoolean(this.canFly);
      buf.writeBoolean(this.wasFlying);
   }

   public PacketUpdateFlight(boolean canFly) {
      this.canFly = canFly;
   }

   public PacketUpdateFlight(boolean canFly, boolean wasFlying) {
      this.canFly = canFly;
      this.wasFlying = wasFlying;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ArsNouveau.proxy.getPlayer().m_150110_().f_35936_ = this.canFly;
         ArsNouveau.proxy.getPlayer().m_150110_().f_35935_ = this.wasFlying;
      });
      ctx.get().setPacketHandled(true);
   }
}
