package com.rolfmao.upgradednetherite_items.packets;

import com.rolfmao.upgradednetherite_items.handlers.ResetEnderUpgradedNetheritePearlHandler;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketResetEnderUpgradedNetheritePearl {
   private ItemStack itemstack;

   public PacketResetEnderUpgradedNetheritePearl(ItemStack itemstack) {
      this.itemstack = itemstack;
   }

   public static void encode(PacketResetEnderUpgradedNetheritePearl msg, FriendlyByteBuf buf) {
      buf.m_130055_(msg.itemstack);
   }

   public static PacketResetEnderUpgradedNetheritePearl decode(FriendlyByteBuf buf) {
      ItemStack itemstack = buf.m_130267_();
      return new PacketResetEnderUpgradedNetheritePearl(itemstack);
   }

   public static void handle(PacketResetEnderUpgradedNetheritePearl msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> ResetEnderUpgradedNetheritePearlHandler.handleResetEnderUpgradedNetheritePearl(msg.itemstack));
      ctx.get().setPacketHandled(true);
   }
}
