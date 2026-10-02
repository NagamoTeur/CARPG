package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.capability.ANPlayerDataCap;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSyncPlayerCap {
   CompoundTag tag;

   public PacketSyncPlayerCap(FriendlyByteBuf buf) {
      this.tag = buf.m_130260_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130079_(this.tag);
   }

   public PacketSyncPlayerCap(CompoundTag famCaps) {
      this.tag = famCaps;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         Player playerEntity = ArsNouveau.proxy.getPlayer();
         IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(playerEntity).orElse(new ANPlayerDataCap());
         if (cap != null) {
            cap.deserializeNBT(this.tag);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
