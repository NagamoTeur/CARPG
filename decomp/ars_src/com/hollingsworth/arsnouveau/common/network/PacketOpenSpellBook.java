package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketOpenSpellBook {
   public boolean isMainHand;

   public PacketOpenSpellBook(FriendlyByteBuf buf) {
      this.isMainHand = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeBoolean(this.isMainHand);
   }

   public PacketOpenSpellBook(InteractionHand hand) {
      this.isMainHand = hand == InteractionHand.MAIN_HAND;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> GuiSpellBook.open(this.isMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND));
      ctx.get().setPacketHandled(true);
   }
}
