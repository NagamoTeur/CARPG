package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.client.gui.MinistrosityInventoryScreen;
import com.github.L_Ender.cataclysm.entity.Pet.Netherite_Ministrosity_Entity;
import com.github.L_Ender.cataclysm.inventory.MinistrostiyMenu;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageMiniinventory {
   private final int id;
   private final int size;
   private final int entityId;

   public MessageMiniinventory(int id, int size, int entityId) {
      this.id = id;
      this.size = size;
      this.entityId = entityId;
   }

   public static MessageMiniinventory read(FriendlyByteBuf buf) {
      return new MessageMiniinventory(buf.readUnsignedByte(), buf.m_130242_(), buf.readInt());
   }

   public static void write(MessageMiniinventory message, FriendlyByteBuf buf) {
      buf.writeByte(message.id);
      buf.m_130130_(message.size);
      buf.writeInt(message.entityId);
   }

   public int getId() {
      return this.id;
   }

   public int getSize() {
      return this.size;
   }

   public int getEntityId() {
      return this.entityId;
   }

   @OnlyIn(Dist.CLIENT)
   public static void openInventory(MessageMiniinventory packet) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null && player.f_19853_.m_6815_(packet.getEntityId()) instanceof Netherite_Ministrosity_Entity guard) {
         LocalPlayer clientplayerentity = Minecraft.m_91087_().f_91074_;
         MinistrostiyMenu container = new MinistrostiyMenu(packet.getId(), player.m_150109_(), guard.miniInventory, guard);
         clientplayerentity.f_36096_ = container;
         Minecraft.m_91087_().m_91152_(new MinistrosityInventoryScreen(container, player.m_150109_(), guard));
      }
   }

   public static class Handler {
      public static void handle(MessageMiniinventory msg, Supplier<Context> context) {
         context.get().enqueueWork(() -> MessageMiniinventory.openInventory(msg));
         context.get().setPacketHandled(true);
      }
   }
}
