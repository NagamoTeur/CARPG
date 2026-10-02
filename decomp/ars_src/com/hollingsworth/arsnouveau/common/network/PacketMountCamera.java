package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.camera.ICameraMountable;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketMountCamera {
   private BlockPos pos;

   public PacketMountCamera() {
   }

   public PacketMountCamera(BlockPos pos) {
      this.pos = pos;
   }

   public static void encode(PacketMountCamera message, FriendlyByteBuf buf) {
      buf.m_130064_(message.pos);
   }

   public static PacketMountCamera decode(FriendlyByteBuf buf) {
      return new PacketMountCamera(buf.m_130135_());
   }

   public static void onMessage(PacketMountCamera message, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         BlockPos pos = message.pos;
         ServerPlayer player = ctx.get().getSender();
         Level level = player.f_19853_;
         if (level.m_46749_(pos) && level.m_7702_(pos) instanceof ICameraMountable mountable) {
            mountable.mountCamera(level, pos, player);
         } else {
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.camera.not_loaded"));
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
