package com.majruszsaccessories.items;

import java.util.function.Supplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class BoosterItem extends Item {
   final Rarity rarity;

   public static Supplier<BoosterItem> basic() {
      return () -> new BoosterItem(Rarity.UNCOMMON);
   }

   public static Supplier<BoosterItem> rare() {
      return () -> new BoosterItem(Rarity.RARE);
   }

   private BoosterItem(Rarity rarity) {
      super(new Properties().m_41487_(1).m_41491_(CreativeModeTab.f_40754_));
      this.rarity = rarity;
   }

   public boolean m_5812_(ItemStack itemStack) {
      return true;
   }

   public Rarity m_41460_(ItemStack itemStack) {
      return this.rarity;
   }
}
