package com.github.L_Ender.cataclysm.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class Necklace_Of_The_Desert extends Item {
   public Necklace_Of_The_Desert(Properties group) {
      super(group);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.necklace_of_the_desert.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
