package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.AmuletofVitalityBaubleIsEquippedProcedure;
import net.cisco.procedures.AmuletofVitalityBaubleIsUnequippedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AmuletofVitalityItem extends Item implements ICurioItem {
   public AmuletofVitalityItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41487_(1).m_41497_(Rarity.RARE));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("Grants + 10 max health when worn."));
   }

   public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
      AmuletofVitalityBaubleIsEquippedProcedure.execute(slotContext.entity());
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      AmuletofVitalityBaubleIsUnequippedProcedure.execute(slotContext.entity());
   }
}
