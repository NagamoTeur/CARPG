package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import net.minecraft.world.item.ItemStack;

public class CasterUtil {
   public static ISpellCaster getCaster(ItemStack stack) {
      return (ISpellCaster)(stack.m_41720_() instanceof ICasterTool casterTool ? casterTool.getSpellCaster(stack) : new SpellCaster(stack));
   }
}
