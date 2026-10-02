package com.majruszsaccessories.items;

import com.majruszsaccessories.common.AccessoryHolder;
import java.util.function.Supplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class AccessoryItem extends Item {
   private final int tier;
   private final int boosterSlotsCount;

   public static Supplier<AccessoryItem> tier1() {
      return () -> new AccessoryItem(1);
   }

   public static Supplier<AccessoryItem> tier2() {
      return () -> new AccessoryItem(2);
   }

   public static Supplier<AccessoryItem> tier3() {
      return () -> new AccessoryItem(3);
   }

   protected AccessoryItem(int tier) {
      super(new Properties().m_41487_(1).m_41491_(CreativeModeTab.f_40754_));
      this.tier = tier;
      this.boosterSlotsCount = tier;
   }

   public boolean m_5812_(ItemStack itemStack) {
      return AccessoryHolder.getOrCreate(itemStack).hasMaxBonus();
   }

   public Rarity m_41460_(ItemStack itemStack) {
      return AccessoryHolder.getOrCreate(itemStack).getRarity();
   }

   public int getTier() {
      return this.tier;
   }

   public int getBoosterSlotsCount() {
      return this.boosterSlotsCount;
   }
}
