package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class AirSetRepairProcedure {
   public static void execute(double y, ItemStack itemstack) {
      if (itemstack.m_41773_() >= 1 && y >= 100.0 && Mth.m_216271_(RandomSource.m_216327_(), 1, 400) == 64) {
         itemstack.m_41721_(itemstack.m_41773_() - 1);
      }
   }
}
