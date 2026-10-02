package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireSetRepairProcedure {
   public static void execute(Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (itemstack.m_41773_() >= 1 && entity.f_19853_.m_46472_() == Level.f_46429_ && Mth.m_216271_(RandomSource.m_216327_(), 1, 400) == 64) {
            itemstack.m_41721_(itemstack.m_41773_() - 1);
         }
      }
   }
}
