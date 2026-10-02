package io.redspace.ironsspellbooks.network.spell;

import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundOnCastStarted {
   private String spellId;
   private int spellLevel;
   private UUID castingEntityId;

   public ClientboundOnCastStarted(UUID castingEntityId, String spellId, int spellLevel) {
      this.spellId = spellId;
      this.spellLevel = spellLevel;
      this.castingEntityId = castingEntityId;
   }

   public ClientboundOnCastStarted(FriendlyByteBuf buf) {
      this.spellId = buf.m_130277_();
      this.spellLevel = buf.readInt();
      this.castingEntityId = buf.m_130259_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130070_(this.spellId);
      buf.writeInt(this.spellLevel);
      buf.m_130077_(this.castingEntityId);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> ClientSpellCastHelper.handleClientBoundOnCastStarted(this.castingEntityId, this.spellId, this.spellLevel));
      return true;
   }
}
