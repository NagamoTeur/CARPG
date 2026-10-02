package com.aizistral.enigmaticlegacy.packets.server;

import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSpellstoneKey {
   private boolean pressed;

   public PacketSpellstoneKey(boolean pressed) {
      this.pressed = pressed;
   }

   public static void encode(PacketSpellstoneKey msg, FriendlyByteBuf buf) {
      buf.writeBoolean(msg.pressed);
   }

   public static PacketSpellstoneKey decode(FriendlyByteBuf buf) {
      return new PacketSpellstoneKey(buf.readBoolean());
   }

   public static void handle(PacketSpellstoneKey msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer playerServ = ctx.get().getSender();
         if (SuperpositionHandler.hasSpellstone(playerServ)) {
            ItemStack spellstone = SuperpositionHandler.getSpellstone(playerServ);
            ISpellstone function = (ISpellstone)spellstone.m_41720_();
            function.triggerActiveAbility(playerServ.f_19853_, playerServ, spellstone);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
