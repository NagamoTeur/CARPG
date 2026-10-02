package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.light.LightManager;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketToggleLight {
   boolean enabled;

   public PacketToggleLight(FriendlyByteBuf buf) {
      this.enabled = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeBoolean(this.enabled);
   }

   public PacketToggleLight(boolean stack) {
      this.enabled = stack;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> LightManager.toggleLightsAndConfig(this.enabled));
      ctx.get().setPacketHandled(true);
   }
}
