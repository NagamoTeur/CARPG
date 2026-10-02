package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.renderer.world.PathfindingDebugRenderer;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.ModNode;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncPathMessage {
   public Set<ModNode> lastDebugNodesVisited = new HashSet<>();
   public Set<ModNode> lastDebugNodesNotVisited = new HashSet<>();
   public Set<ModNode> lastDebugNodesPath = new HashSet<>();

   public SyncPathMessage() {
   }

   public SyncPathMessage(Set<ModNode> lastDebugNodesVisited, Set<ModNode> lastDebugNodesNotVisited, Set<ModNode> lastDebugNodesPath) {
      this.lastDebugNodesVisited = lastDebugNodesVisited;
      this.lastDebugNodesNotVisited = lastDebugNodesNotVisited;
      this.lastDebugNodesPath = lastDebugNodesPath;
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.lastDebugNodesVisited.size());

      for (ModNode node : this.lastDebugNodesVisited) {
         node.serializeToBuf(buf);
      }

      buf.writeInt(this.lastDebugNodesNotVisited.size());

      for (ModNode node : this.lastDebugNodesNotVisited) {
         node.serializeToBuf(buf);
      }

      buf.writeInt(this.lastDebugNodesPath.size());

      for (ModNode node : this.lastDebugNodesPath) {
         node.serializeToBuf(buf);
      }
   }

   public SyncPathMessage(FriendlyByteBuf buf) {
      int size = buf.readInt();

      for (int i = 0; i < size; i++) {
         this.lastDebugNodesVisited.add(new ModNode(buf));
      }

      size = buf.readInt();

      for (int i = 0; i < size; i++) {
         this.lastDebugNodesNotVisited.add(new ModNode(buf));
      }

      size = buf.readInt();

      for (int i = 0; i < size; i++) {
         this.lastDebugNodesPath.add(new ModNode(buf));
      }
   }

   public static class Handler {
      public static void handle(final SyncPathMessage m, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get().enqueueWork(new Runnable() {
               @Override
               public void run() {
                  PathfindingDebugRenderer.lastDebugNodesVisited = m.lastDebugNodesVisited;
                  PathfindingDebugRenderer.lastDebugNodesNotVisited = m.lastDebugNodesNotVisited;
                  PathfindingDebugRenderer.lastDebugNodesPath = m.lastDebugNodesPath;
               }
            });
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
