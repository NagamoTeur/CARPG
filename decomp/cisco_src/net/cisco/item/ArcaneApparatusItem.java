package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ArcaneApparatusItem extends Item {
   public ArcaneApparatusItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41503_(50).m_41497_(Rarity.EPIC));
   }

   public boolean m_41470_() {
      return true;
   }

   public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
      ItemStack retval = new ItemStack(this);
      retval.m_41721_(itemstack.m_41773_() + 1);
      return retval.m_41773_() >= retval.m_41776_() ? ItemStack.f_41583_ : retval;
   }

   public boolean isRepairable(ItemStack itemstack) {
      return false;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("A magical tool used to extract the essence from certain arcane materials."));
   }
}
