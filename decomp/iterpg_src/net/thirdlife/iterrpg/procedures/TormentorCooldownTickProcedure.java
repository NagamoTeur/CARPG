package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.item.ItemStack;

public class TormentorCooldownTickProcedure {
   public static void execute(ItemStack itemstack) {
      if (itemstack.m_41784_().m_128459_("cooldown") > 0.0) {
         itemstack.m_41784_().m_128347_("cooldown", itemstack.m_41784_().m_128459_("cooldown") - 1.0);
      }
   }
}
