package net.cisco.network;

import java.util.function.Supplier;
import net.cisco.CiscoModMod;
import net.cisco.procedures.AzureAbilityActiveProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class AzureAscendantArmorAbilityMessage {
   int type;
   int pressedms;

   public AzureAscendantArmorAbilityMessage(int type, int pressedms) {
      this.type = type;
      this.pressedms = pressedms;
   }

   public AzureAscendantArmorAbilityMessage(FriendlyByteBuf buffer) {
      this.type = buffer.readInt();
      this.pressedms = buffer.readInt();
   }

   public static void buffer(AzureAscendantArmorAbilityMessage message, FriendlyByteBuf buffer) {
      buffer.writeInt(message.type);
      buffer.writeInt(message.pressedms);
   }

   public static void handler(AzureAscendantArmorAbilityMessage message, Supplier<Context> contextSupplier) {
      Context context = contextSupplier.get();
      context.enqueueWork(() -> pressAction(context.getSender(), message.type, message.pressedms));
      context.setPacketHandled(true);
   }

   public static void pressAction(Player entity, int type, int pressedms) {
      Level world = entity.f_19853_;
      double x = entity.m_20185_();
      double y = entity.m_20186_();
      double z = entity.m_20189_();
      if (world.m_46805_(entity.m_20183_())) {
         if (type == 0) {
            AzureAbilityActiveProcedure.execute(world, x, y, z, entity);
         }
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      CiscoModMod.addNetworkMessage(
         AzureAscendantArmorAbilityMessage.class,
         AzureAscendantArmorAbilityMessage::buffer,
         AzureAscendantArmorAbilityMessage::new,
         AzureAscendantArmorAbilityMessage::handler
      );
   }
}
