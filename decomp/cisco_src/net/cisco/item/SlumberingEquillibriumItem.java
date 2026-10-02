package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class SlumberingEquillibriumItem extends SwordItem {
   public SlumberingEquillibriumItem() {
      super(new Tier() {
         public int m_6609_() {
            return 8000;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 19.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 15;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.BRIGHTSTEEL_INGOT.get())});
         }
      }, 3, -2.7F, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("This legendary sword is dormant without the divine ruby as a power source."));
   }
}
