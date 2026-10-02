package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class MobPlacerActivateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
      itemstack.m_41784_().m_128347_("xcord", x + 0.5);
      itemstack.m_41784_().m_128347_("ycord", y + 1.0);
      itemstack.m_41784_().m_128347_("zcord", z + 0.5);
      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123808_, x + 0.5, y + 1.1, z + 0.5, 4, 0.01, 0.01, 0.01, 0.01);
      }
   }
}
