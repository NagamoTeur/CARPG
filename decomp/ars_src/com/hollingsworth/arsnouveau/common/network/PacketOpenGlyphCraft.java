package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.gui.book.GlyphUnlockMenu;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketOpenGlyphCraft {
   BlockPos scribePos;

   public PacketOpenGlyphCraft(FriendlyByteBuf buf) {
      this.scribePos = buf.m_130135_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130064_(this.scribePos);
   }

   public PacketOpenGlyphCraft(BlockPos scribesPos) {
      this.scribePos = scribesPos;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> GlyphUnlockMenu.open(this.scribePos));
      ctx.get().setPacketHandled(true);
   }
}
