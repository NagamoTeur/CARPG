package net.cisco.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class CiscoModModTabs {
   public static CreativeModeTab TAB_CISCO_MOD;

   public static void load() {
      TAB_CISCO_MOD = (new CreativeModeTab("tabcisco_mod") {
         public ItemStack m_6976_() {
            return new ItemStack((ItemLike)CiscoModModItems.EQUILLIBRIUM.get());
         }

         public boolean hasSearchBar() {
            return true;
         }
      }).m_40779_("item_search.png");
   }
}
