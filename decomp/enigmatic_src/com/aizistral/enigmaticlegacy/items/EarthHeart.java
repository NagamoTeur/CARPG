package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.ITaintable;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class EarthHeart extends ItemBase implements ITaintable, Vanishable {
   public EarthHeart() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.UNCOMMON).m_41487_(1));
   }

   public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof Player && !entityIn.f_19853_.f_46443_) {
         Player player = (Player)entityIn;
         this.handleTaintable(stack, player);
      }
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (this.isTainted(stack)) {
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.tainted1");
      }
   }
}
