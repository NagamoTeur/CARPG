package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.light.LightManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSyncLitEntities {
   List<Integer> entityIDs = new ArrayList<>();

   public PacketSyncLitEntities(FriendlyByteBuf buf) {
      int num = buf.readInt();

      for (int i = 0; i < num; i++) {
         this.entityIDs.add(buf.readInt());
      }
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.entityIDs.size());

      for (Integer i : this.entityIDs) {
         buf.writeInt(i);
      }
   }

   public PacketSyncLitEntities(List<Integer> entityIDs) {
      this.entityIDs = entityIDs;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (LightManager.shouldUpdateDynamicLight()) {
            LightManager.jarHoldingEntityList = this.entityIDs;
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
