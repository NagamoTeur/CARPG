package net.cisco.procedures;

import net.cisco.CiscoModMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class AzureThunderLivingEntityIsHitWithToolProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
      if (!itemstack.m_41784_().m_128471_("ability")) {
         if (world instanceof ServerLevel _level) {
            LightningBolt entityToSpawn = (LightningBolt)EntityType.f_20465_.m_20615_(_level);
            entityToSpawn.m_20219_(Vec3.m_82539_(new BlockPos(x, y, z)));
            entityToSpawn.m_20874_(true);
            _level.m_7967_(entityToSpawn);
         }

         itemstack.m_41784_().m_128379_("ability", true);
         CiscoModMod.queueServerWork(100, () -> itemstack.m_41784_().m_128379_("ability", false));
      }
   }
}
