package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundCastErrorMessage {
   public final ClientboundCastErrorMessage.ErrorType errorType;
   public final String spellId;

   public ClientboundCastErrorMessage(ClientboundCastErrorMessage.ErrorType errorType, AbstractSpell spell) {
      this.spellId = spell.getSpellId();
      this.errorType = errorType;
   }

   public ClientboundCastErrorMessage(FriendlyByteBuf buf) {
      this.errorType = (ClientboundCastErrorMessage.ErrorType)buf.m_130066_(ClientboundCastErrorMessage.ErrorType.class);
      this.spellId = buf.m_130277_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130068_(this.errorType);
      buf.m_130070_(this.spellId);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> ClientSpellCastHelper.handleCastErrorMessage(this));
      return true;
   }

   public static enum ErrorType {
      COOLDOWN,
      MANA;
   }
}
