package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.CrownOfDebilitatingDesireBaubleIsUnequippedProcedure;
import net.cisco.procedures.CrownOfDebilitatingDesireWhileBaubleIsEquippedTickProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CrownOfDebilitatingDesireItem extends Item implements ICurioItem {
   public CrownOfDebilitatingDesireItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41487_(1).m_41486_().m_41497_(Rarity.EPIC));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§dOrginally a tool for punishment,it become a sick source of pleasure for certain masochistic individuals."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§fReduces damage done to enemies,armor,speed and max hp by 80 %."));
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      CrownOfDebilitatingDesireWhileBaubleIsEquippedTickProcedure.execute(slotContext.entity());
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      CrownOfDebilitatingDesireBaubleIsUnequippedProcedure.execute(slotContext.entity());
   }
}
