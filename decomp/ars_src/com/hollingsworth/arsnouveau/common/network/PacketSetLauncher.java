package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.items.FlaskCannon;
import com.hollingsworth.arsnouveau.common.items.PotionFlask;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSetLauncher extends AbstractPacket {
   public int inventorySlot;

   public PacketSetLauncher(FriendlyByteBuf buf) {
      this.inventorySlot = buf.readInt();
   }

   public PacketSetLauncher(int slot) {
      this.inventorySlot = slot;
   }

   @Override
   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.inventorySlot);
   }

   @Override
   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (player != null) {
            if (this.inventorySlot < player.f_36093_.m_6643_()) {
               ItemStack stack = player.f_36093_.m_8020_(this.inventorySlot);
               ItemStack launcherStack = ItemStack.f_41583_;
               if (player.m_21205_().m_41720_() instanceof FlaskCannon) {
                  launcherStack = player.m_21205_();
               } else if (player.m_21206_().m_41720_() instanceof FlaskCannon) {
                  launcherStack = player.m_21206_();
               }

               if (!launcherStack.m_41619_()) {
                  FlaskCannon.PotionLauncherData data = new FlaskCannon.PotionLauncherData(launcherStack);
                  if (stack.m_41720_() instanceof PotionFlask) {
                     PotionFlask.FlaskData flaskData = new PotionFlask.FlaskData(stack);
                     data.setLastDataForRender(flaskData.getPotion());
                     data.setLastSlot(this.inventorySlot);
                     data.setAmountLeft(flaskData.getCount());
                  } else if (stack.m_41720_() instanceof PotionItem) {
                     PotionData potionData = new PotionData(stack);
                     data.setLastDataForRender(potionData);
                     data.setLastSlot(this.inventorySlot);
                     data.setAmountLeft(stack.m_41613_());
                  }
               }
            }
         }
      });
   }
}
