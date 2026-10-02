package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ShipGraveyardEchoItem extends Item {
   public ShipGraveyardEchoItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(64).m_41497_(Rarity.UNCOMMON));
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(@NotNull ItemStack itemstack) {
      return true;
   }

   public void m_6883_(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
      super.m_6883_(itemstack, world, entity, slot, selected);
      if (entity instanceof Player player && player.f_19853_.m_5776_() && selected && Math.random() <= 0.01) {
         AquamiraeClient.playAmbientSounds(player, false);
      }
   }
}
