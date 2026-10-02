package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.renderer.world.PathfindingDebugRenderer;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.ModNode;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncPathReachedMessage {
   public Set<BlockPos> reached = new HashSet<>();

   public SyncPathReachedMessage() {
   }

   public SyncPathReachedMessage(Set<BlockPos> reached) {
      this.reached = reached;
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.reached.size());

      for (BlockPos node : this.reached) {
         buf.m_130064_(node);
      }
   }

   public SyncPathReachedMessage(FriendlyByteBuf buf) {
      int size = buf.readInt();

      for (int i = 0; i < size; i++) {
         this.reached.add(buf.m_130135_());
      }
   }

   public static class Handler {
      public static void handle(final SyncPathReachedMessage m, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get().enqueueWork(new Runnable() {
               @Override
               public void run() {
                  for (ModNode node : PathfindingDebugRenderer.lastDebugNodesPath) {
                     if (m.reached.contains(node.pos)) {
                        node.setReachedByWorker(true);
                     }
                  }
               }
            });
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
