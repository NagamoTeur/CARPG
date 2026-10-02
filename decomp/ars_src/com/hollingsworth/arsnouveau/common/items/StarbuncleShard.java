package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class StarbuncleShard extends ModItem {
   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (stack.m_41782_()) {
         Starbuncle.StarbuncleData data = new Starbuncle.StarbuncleData(stack.m_41784_());
         if (data.name != null) {
            tooltip2.add(data.name);
         }

         if (data.adopter != null) {
            tooltip2.add(Component.m_237110_("ars_nouveau.adopter", new Object[]{data.adopter}).m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
         }

         if (data.bio != null) {
            tooltip2.add(Component.m_237113_(data.bio).m_130948_(Style.f_131099_.m_131140_(ChatFormatting.DARK_PURPLE)));
         }
      } else {
         super.m_7373_(stack, worldIn, tooltip2, flagIn);
      }
   }
}
