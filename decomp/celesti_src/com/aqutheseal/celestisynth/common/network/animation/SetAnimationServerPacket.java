package com.aqutheseal.celestisynth.common.network.animation;

import com.aqutheseal.celestisynth.manager.CSNetworkManager;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetAnimationServerPacket {
   private final boolean isOtherLayer;
   private final int animId;

   public SetAnimationServerPacket(boolean isOtherLayer, int animId) {
      this.isOtherLayer = isOtherLayer;
      this.animId = animId;
   }

   public SetAnimationServerPacket(FriendlyByteBuf buf) {
      this.isOtherLayer = buf.readBoolean();
      this.animId = buf.readInt();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeBoolean(this.isOtherLayer);
      buf.writeInt(this.animId);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context context = supplier.get();
      context.enqueueWork(
         () -> CSNetworkManager.sendToPlayersNearbyAndSelf(
               new SetAnimationToAllPacket(this.isOtherLayer, context.getSender().m_19879_(), this.animId), context.getSender()
            )
      );
      return true;
   }
}
