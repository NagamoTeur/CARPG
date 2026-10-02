package com.obscuria.aquamirae.common.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class StewItem extends Item {
   public StewItem(Properties properties) {
      super(properties);
   }

   public int m_8105_(@NotNull ItemStack itemstack) {
      return 40;
   }

   @NotNull
   public ItemStack m_5922_(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
      ItemStack bowl = new ItemStack(Items.f_42399_);
      super.m_5922_(itemstack, world, entity);
      if (itemstack.m_41619_()) {
         return bowl;
      } else {
         if (entity instanceof Player player && !player.m_150110_().f_35937_ && !player.m_150109_().m_36054_(bowl)) {
            player.m_36176_(bowl, false);
         }

         return itemstack;
      }
   }
}
