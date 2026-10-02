package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.player.ClientMagicData;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientBoundRemoveRecast {
   private final String spellId;

   public ClientBoundRemoveRecast(String spellId) {
      this.spellId = spellId;
   }

   public ClientBoundRemoveRecast(FriendlyByteBuf buf) {
      this.spellId = buf.m_130277_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130070_(this.spellId);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> ClientMagicData.getRecasts().removeRecast(this.spellId));
      return true;
   }
}
