package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class WaterSetRepairProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (itemstack.m_41773_() >= 1
            && (
               entity.m_20069_()
                  || world.m_46861_(new BlockPos(x, y, z)) && world.m_6106_().m_6533_()
                  || world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:is_ocean")))
                  || world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:is_river")))
                  || world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:water")))
                  || world.m_204166_(new BlockPos(x, y, z))
                     .m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:has_structure/swamp_hut")))
            )
            && Mth.m_216271_(RandomSource.m_216327_(), 1, 400) == 64) {
            itemstack.m_41721_(itemstack.m_41773_() - 1);
         }
      }
   }
}
