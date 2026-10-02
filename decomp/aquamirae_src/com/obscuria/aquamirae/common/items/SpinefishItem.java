package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpinefishItem extends Item {
   public SpinefishItem(Properties properties) {
      super(properties);
   }

   @NotNull
   public ItemStack m_5922_(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
      ItemStack bone = new ItemStack((ItemLike)AquamiraeItems.SHARP_BONES.get());
      super.m_5922_(itemstack, world, entity);
      if (itemstack.m_41619_()) {
         return bone;
      } else {
         if (entity instanceof Player player && !player.m_150110_().f_35937_ && !player.m_150109_().m_36054_(bone)) {
            player.m_36176_(bone, false);
         }

         return itemstack;
      }
   }
}
