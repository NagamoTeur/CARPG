package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class ElementalSetRepairProcedure {
   public static void execute(ItemStack itemstack) {
      if (itemstack.m_41773_() >= 1 && Mth.m_216271_(RandomSource.m_216327_(), 1, 600) == 64) {
         itemstack.m_41721_(itemstack.m_41773_() - 1);
      }
   }
}
