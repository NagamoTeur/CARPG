package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundOpenEldritchScreen {
   private final InteractionHand hand;

   public ClientboundOpenEldritchScreen(InteractionHand pHand) {
      this.hand = pHand;
   }

   public ClientboundOpenEldritchScreen(FriendlyByteBuf buf) {
      this.hand = (InteractionHand)buf.m_130066_(InteractionHand.class);
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130068_(this.hand);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> ClientSpellCastHelper.openEldritchResearchScreen(this.hand));
      return true;
   }
}
