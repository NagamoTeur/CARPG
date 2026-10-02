package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.items.PotionFlask;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketConsumePotion {
   int inventorySlot;

   public PacketConsumePotion(FriendlyByteBuf buf) {
      this.inventorySlot = buf.readInt();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.inventorySlot);
   }

   public PacketConsumePotion(int inventorySlot) {
      this.inventorySlot = inventorySlot;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get()
         .enqueueWork(
            () -> {
               ServerPlayer player = ctx.get().getSender();
               if (player != null) {
                  if (this.inventorySlot < player.f_36093_.m_6643_()) {
                     ItemStack stack = player.f_36093_.m_8020_(this.inventorySlot);
                     if (stack.m_41720_() instanceof PotionItem) {
                        PotionData data = new PotionData(stack);
                        data.applyEffects(player, player, player);
                        stack.m_41774_(1);
                        player.f_36093_.m_36054_(new ItemStack(Items.f_42590_));
                        player.f_19853_
                           .m_5594_(
                              null, player.m_20183_(), SoundEvents.f_11911_, SoundSource.PLAYERS, 0.5F, player.f_19853_.f_46441_.m_188501_() * 0.1F + 0.9F
                           );
                     } else if (stack.m_41720_() instanceof PotionFlask) {
                        PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);
                        if (data.getPotion().isEmpty() || data.getCount() <= 0) {
                           return;
                        }

                        data.getPotion().applyEffects(player, player, player);
                        data.setCount(data.getCount() - 1);
                        player.f_19853_
                           .m_5594_(
                              null, player.m_20183_(), SoundEvents.f_11911_, SoundSource.PLAYERS, 0.5F, player.f_19853_.f_46441_.m_188501_() * 0.1F + 0.9F
                           );
                     }
                  }
               }
            }
         );
      ctx.get().setPacketHandled(true);
   }
}
