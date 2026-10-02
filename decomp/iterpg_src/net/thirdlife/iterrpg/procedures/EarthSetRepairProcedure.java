package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class EarthSetRepairProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
      if (itemstack.m_41773_() >= 1
         && (
            world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:is_forest")))
               || world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:lush")))
               || world.m_204166_(new BlockPos(x, y, z))
                  .m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:has_structure/village_plains")))
               || world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:is_jungle")))
         )
         && Mth.m_216271_(RandomSource.m_216327_(), 1, 400) == 64) {
         itemstack.m_41721_(itemstack.m_41773_() - 1);
      }
   }
}
