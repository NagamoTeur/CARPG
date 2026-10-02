package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.IEldritch;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbyssalHeart extends ItemBase implements IEldritch {
   public AbyssalHeart() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.indicateWorthyOnesOnly(list);
      } else {
         list.add(Component.m_237115_("tooltip.enigmaticlegacy.abyssalHeart1"));
         list.add(Component.m_237115_("tooltip.enigmaticlegacy.abyssalHeart2"));
         list.add(Component.m_237115_("tooltip.enigmaticlegacy.abyssalHeart3"));
         list.add(Component.m_237115_("tooltip.enigmaticlegacy.abyssalHeart4"));
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.indicateCursedOnesOnly(list);
      }
   }
}
