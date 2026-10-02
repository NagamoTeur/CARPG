package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import net.minecraft.world.item.Rarity;

public class PlaceholderItem extends ItemBase {
   public PlaceholderItem(String name, Rarity rarity) {
      this(name, rarity, 1);
   }

   public PlaceholderItem(String name, Rarity rarity, int maxStackSize) {
      super(ItemBase.getDefaultProperties().m_41491_(null).m_41497_(rarity).m_41487_(maxStackSize));
   }
}
