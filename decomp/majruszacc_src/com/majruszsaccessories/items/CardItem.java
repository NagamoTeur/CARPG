package com.majruszsaccessories.items;

import com.majruszlibrary.events.OnItemTooltip;
import com.majruszsaccessories.common.AccessoryHolder;
import java.util.List;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public abstract class CardItem extends Item {
   public CardItem() {
      super(new Properties().m_41497_(Rarity.UNCOMMON).m_41487_(16).m_41491_(CreativeModeTab.f_40754_));
   }

   public boolean m_5812_(ItemStack itemStack) {
      return true;
   }

   public abstract void apply(AccessoryHolder var1);

   public abstract void addTooltip(OnItemTooltip var1);

   public List<ItemStack> getCraftingRemainder(AccessoryHolder holder) {
      return List.of();
   }

   private static void tryToAddTooltip(OnItemTooltip data) {
      if (data.itemStack.m_41720_() instanceof CardItem card) {
         card.addTooltip(data);
      }
   }

   static {
      OnItemTooltip.listen(CardItem::tryToAddTooltip);
   }
}
