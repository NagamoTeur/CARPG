package lykrast.meetyourfight.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemGroupMeetYourFight extends CreativeModeTab {
   public static final CreativeModeTab INSTANCE = new ItemGroupMeetYourFight(CreativeModeTab.getGroupCountSafe(), "meetyourfight");

   public ItemGroupMeetYourFight(int index, String label) {
      super(index, label);
   }

   public ItemStack m_6976_() {
      return new ItemStack((ItemLike)ModItems.hauntedBell.get());
   }
}
