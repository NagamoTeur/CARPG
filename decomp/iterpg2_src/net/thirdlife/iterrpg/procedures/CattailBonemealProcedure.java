package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class CattailBonemealProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof Level _level && !_level.m_5776_()) {
         ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModBlocks.CATTAIL.get()));
         entityToSpawn.m_32010_(10);
         _level.m_7967_(entityToSpawn);
      }
   }
}
