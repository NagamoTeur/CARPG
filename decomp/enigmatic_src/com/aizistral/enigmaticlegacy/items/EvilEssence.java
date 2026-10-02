package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class EvilEssence extends ItemBase implements ICursed {
   public EvilEssence() {
      super(getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(8).m_41486_());
   }

   public boolean m_41386_(DamageSource source) {
      return !source.m_19372_();
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.evilEssence1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.evilEssence2");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(list);
   }
}
