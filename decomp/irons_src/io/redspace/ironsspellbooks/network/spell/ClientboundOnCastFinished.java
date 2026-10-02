package io.redspace.ironsspellbooks.network.spell;

import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundOnCastFinished {
   private final String spellId;
   private final UUID castingEntityId;
   private final boolean cancelled;

   public ClientboundOnCastFinished(UUID castingEntityId, String spellId, boolean cancelled) {
      this.spellId = spellId;
      this.castingEntityId = castingEntityId;
      this.cancelled = cancelled;
   }

   public ClientboundOnCastFinished(FriendlyByteBuf buf) {
      this.spellId = buf.m_130277_();
      this.castingEntityId = buf.m_130259_();
      this.cancelled = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130070_(this.spellId);
      buf.m_130077_(this.castingEntityId);
      buf.writeBoolean(this.cancelled);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> ClientSpellCastHelper.handleClientBoundOnCastFinished(this.castingEntityId, this.spellId, this.cancelled));
      return true;
   }
}
