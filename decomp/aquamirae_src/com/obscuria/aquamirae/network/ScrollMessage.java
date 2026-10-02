package com.obscuria.aquamirae.network;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class ScrollMessage {
   int type;

   public ScrollMessage(int type) {
      this.type = type;
   }

   public ScrollMessage(FriendlyByteBuf buffer) {
      this.type = buffer.readInt();
   }

   public static void buffer(ScrollMessage message, FriendlyByteBuf buffer) {
      buffer.writeInt(message.type);
   }

   public static void handler(ScrollMessage message, Supplier<Context> contextSupplier) {
      Context context = contextSupplier.get();
      context.enqueueWork(() -> AquamiraeClient.scrollEffect(message.type));
      context.setPacketHandled(true);
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      Aquamirae.addNetworkMessage(ScrollMessage.class, ScrollMessage::buffer, ScrollMessage::new, ScrollMessage::handler);
   }
}
