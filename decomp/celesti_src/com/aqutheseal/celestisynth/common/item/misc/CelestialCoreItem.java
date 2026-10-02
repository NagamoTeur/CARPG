package com.aqutheseal.celestisynth.common.item.misc;

import com.aqutheseal.celestisynth.common.registry.CSItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class CelestialCoreItem extends Item {
   public CelestialCoreItem(Properties pProperties) {
      super(pProperties);
   }

   public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
      return new ItemStack((ItemLike)CSItems.CELESTIAL_CORE.get());
   }

   public boolean hasCraftingRemainingItem(ItemStack stack) {
      return true;
   }

   public void m_6883_(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
      super.m_6883_(pStack, pLevel, pEntity, pSlotId, pIsSelected);
      if (this == CSItems.CELESTIAL_CORE_HEATED.get()) {
         if (pEntity instanceof Player player && (player.m_7500_() || player.m_5833_())) {
            return;
         }

         pEntity.m_20254_(3);
      }
   }
}
