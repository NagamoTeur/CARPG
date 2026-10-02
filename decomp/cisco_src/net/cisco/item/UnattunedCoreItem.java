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

public class UnattunedCoreItem extends Item {
   public UnattunedCoreItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41487_(16).m_41497_(Rarity.RARE));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(
         Component.m_237113_(
            "A special core which is highly sensitive to and can contain the power of the elements. Can also take on any shape the user wishes granting it unmatched  utility in the forging of powerful equipment."
         )
      );
   }
}
