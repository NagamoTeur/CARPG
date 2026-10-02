package com.rolfmao.upgradednetherite_items.handlers;

import com.rolfmao.upgradednetherite_items.UpgradedNetherite_ItemsMod;
import com.rolfmao.upgradednetherite_items.init.ModItems;
import com.rolfmao.upgradednetherite_items.packets.PacketResetEnderUpgradedNetheritePearl;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

public class ResetEnderUpgradedNetheritePearlHandler {
   public static void ResetClientPearl(ServerPlayer player, ItemStack itemstack) {
      UpgradedNetherite_ItemsMod.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketResetEnderUpgradedNetheritePearl(itemstack));
   }

   @OnlyIn(Dist.CLIENT)
   public static void handleResetEnderUpgradedNetheritePearl(ItemStack itemstack) {
      if (itemstack.m_41720_() == ModItems.ENDER_UPGRADED_NETHERITE_PEARL.get() && itemstack.m_41784_().m_128441_("EnderUpgradedNetheritePearlTarget")) {
         itemstack.m_41784_().m_128473_("EnderUpgradedNetheritePearlTarget");
         itemstack.m_41784_().m_128473_("EnderUpgradedNetheritePearlTargetName");
      }
   }
}
