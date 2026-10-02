package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.item.ItemStack;

public class BloodCooldownProcedure {
   public static void execute(ItemStack itemstack) {
      if (itemstack.m_41784_().m_128459_("charge") <= 0.0 && itemstack.m_41784_().m_128459_("CustomModelData") > 0.0) {
         itemstack.m_41784_().m_128347_("CustomModelData", itemstack.m_41784_().m_128459_("CustomModelData") - 1.0);
         itemstack.m_41784_().m_128347_("charge", 5.0);
      } else {
         itemstack.m_41784_().m_128347_("charge", itemstack.m_41784_().m_128459_("charge") - 1.0);
      }
   }
}
