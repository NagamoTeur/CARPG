package com.aizistral.enigmaticlegacy.packets.server;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent.Open;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketEnderRingKey {
   private boolean pressed;

   public PacketEnderRingKey(boolean pressed) {
      this.pressed = pressed;
   }

   public static void encode(PacketEnderRingKey msg, FriendlyByteBuf buf) {
      buf.writeBoolean(msg.pressed);
   }

   public static PacketEnderRingKey decode(FriendlyByteBuf buf) {
      return new PacketEnderRingKey(buf.readBoolean());
   }

   public static void handle(PacketEnderRingKey msg, Supplier<Context> ctx) {
      ctx.get()
         .enqueueWork(
            () -> {
               final ServerPlayer playerServ = ctx.get().getSender();
               if (SuperpositionHandler.hasCurio(playerServ, EnigmaticItems.ENDER_RING)
                  || SuperpositionHandler.hasCurio(playerServ, EnigmaticItems.CURSED_RING)) {
                  ChestMenu container = new ChestMenu(MenuType.f_39959_, playerServ.f_8940_ + 1, playerServ.m_150109_(), playerServ.m_36327_(), 3) {
                     public void m_6877_(Player playerIn) {
                        super.m_6877_(playerIn);
                        if (!playerIn.f_19853_.f_46443_) {
                           playerIn.f_19853_
                              .m_5594_(null, playerServ.m_20183_(), SoundEvents.f_11888_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
                        }
                     }
                  };
                  playerServ.f_8940_ = container.f_38840_;
                  playerServ.f_8906_
                     .m_9829_(new ClientboundOpenScreenPacket(container.f_38840_, container.m_6772_(), Component.m_237115_("container.enderchest")));
                  playerServ.m_143399_(container);
                  playerServ.f_36096_ = container;
                  MinecraftForge.EVENT_BUS.post(new Open(playerServ, playerServ.f_36096_));
                  playerServ.f_19853_
                     .m_5594_(null, playerServ.m_20183_(), SoundEvents.f_11889_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
               }
            }
         );
      ctx.get().setPacketHandled(true);
   }
}
