package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.DarkCharmBaubleIsUnequippedProcedure;
import net.cisco.procedures.DarkCharmWhileBaubleIsEquippedTickProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class DarkCharmItem extends Item implements ICurioItem {
   public DarkCharmItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41487_(1).m_41497_(Rarity.EPIC));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(
         Component.m_237113_(
            "§5A charm corrupted by something very sinister. §fWhen above 80 percent hp constantly damages the wearer. When below 80 percent hp grants a small boost to attack,speed,armor and armor toughness."
         )
      );
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      DarkCharmWhileBaubleIsEquippedTickProcedure.execute(slotContext.entity());
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      DarkCharmBaubleIsUnequippedProcedure.execute(slotContext.entity());
   }
}
