package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LoreFragment extends ItemBase {
   public LoreFragment() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.UNCOMMON).m_41487_(16));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (stack.m_41698_("display").m_128461_("Name").equals("") && stack.m_41698_("display").m_128437_("Lore", 8).size() == 0) {
         if (Screen.m_96638_()) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.loreFragment1");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.loreFragment2");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.loreFragment3");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.loreFragment4");
         } else {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
         }
      }
   }
}
