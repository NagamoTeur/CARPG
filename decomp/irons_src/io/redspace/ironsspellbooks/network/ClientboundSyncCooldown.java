package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.player.ClientMagicData;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundSyncCooldown {
   private final String spellId;
   private final int duration;

   public ClientboundSyncCooldown(String spellId, int duration) {
      this.spellId = spellId;
      this.duration = duration;
   }

   public ClientboundSyncCooldown(FriendlyByteBuf buf) {
      this.spellId = buf.m_130277_();
      this.duration = buf.readInt();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130070_(this.spellId);
      buf.writeInt(this.duration);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> ClientMagicData.getCooldowns().addCooldown(this.spellId, this.duration));
      return true;
   }
}
