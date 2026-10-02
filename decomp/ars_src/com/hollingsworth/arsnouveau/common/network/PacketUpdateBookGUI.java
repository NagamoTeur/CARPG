package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateBookGUI {
   public ItemStack bookStack;

   public PacketUpdateBookGUI(FriendlyByteBuf buf) {
      this.bookStack = buf.m_130267_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130055_(this.bookStack);
   }

   public PacketUpdateBookGUI(ItemStack stack) {
      this.bookStack = stack;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (ArsNouveau.proxy.getMinecraft().f_91080_ instanceof GuiSpellBook) {
            ((GuiSpellBook)ArsNouveau.proxy.getMinecraft().f_91080_).bookStack = this.bookStack;
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
