package net.thirdlife.iterrpg.network;

import java.util.HashMap;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.procedures.MobPlacerGoblinCycleProcedure;
import net.thirdlife.iterrpg.procedures.MobPlacerSpawnProcedure;
import net.thirdlife.iterrpg.world.inventory.MobPlacerGUIMenu;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class MobPlacerGUIButtonMessage {
   private final int buttonID;
   private final int x;
   private final int y;
   private final int z;

   public MobPlacerGUIButtonMessage(FriendlyByteBuf buffer) {
      this.buttonID = buffer.readInt();
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
   }

   public MobPlacerGUIButtonMessage(int buttonID, int x, int y, int z) {
      this.buttonID = buttonID;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public static void buffer(MobPlacerGUIButtonMessage message, FriendlyByteBuf buffer) {
      buffer.writeInt(message.buttonID);
      buffer.writeInt(message.x);
      buffer.writeInt(message.y);
      buffer.writeInt(message.z);
   }

   public static void handler(MobPlacerGUIButtonMessage message, Supplier<Context> contextSupplier) {
      Context context = contextSupplier.get();
      context.enqueueWork(() -> {
         Player entity = context.getSender();
         int buttonID = message.buttonID;
         int x = message.x;
         int y = message.y;
         int z = message.z;
         handleButtonAction(entity, buttonID, x, y, z);
      });
      context.setPacketHandled(true);
   }

   public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
      Level world = entity.f_19853_;
      HashMap guistate = MobPlacerGUIMenu.guistate;
      if (world.m_46805_(new BlockPos(x, y, z))) {
         if (buttonID == 0) {
            MobPlacerSpawnProcedure.execute(world, entity);
         }

         if (buttonID == 1) {
            MobPlacerGoblinCycleProcedure.execute(entity);
         }
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      IterRpgMod.addNetworkMessage(
         MobPlacerGUIButtonMessage.class, MobPlacerGUIButtonMessage::buffer, MobPlacerGUIButtonMessage::new, MobPlacerGUIButtonMessage::handler
      );
   }
}
