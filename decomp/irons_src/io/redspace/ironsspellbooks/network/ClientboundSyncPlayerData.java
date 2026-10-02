package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundSyncPlayerData {
   SyncedSpellData syncedSpellData;

   public ClientboundSyncPlayerData(SyncedSpellData playerSyncedData) {
      this.syncedSpellData = playerSyncedData;
   }

   public ClientboundSyncPlayerData(FriendlyByteBuf buf) {
      this.syncedSpellData = (SyncedSpellData)SyncedSpellData.SYNCED_SPELL_DATA.m_6709_(buf);
   }

   public void toBytes(FriendlyByteBuf buf) {
      SyncedSpellData.SYNCED_SPELL_DATA.m_6856_(buf, this.syncedSpellData);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> ClientMagicData.handlePlayerSyncedData(this.syncedSpellData));
      return true;
   }
}
