package com.github.L_Ender.cataclysm.inventory;

import com.github.L_Ender.cataclysm.init.ModTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MinistrositySlot extends Slot {
   public MinistrositySlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
      super(p_40223_, p_40224_, p_40225_, p_40226_);
   }

   public boolean m_5857_(ItemStack stack) {
      return !stack.m_204117_(ModTag.MINISTROSITY_BLACKLIST);
   }
}
