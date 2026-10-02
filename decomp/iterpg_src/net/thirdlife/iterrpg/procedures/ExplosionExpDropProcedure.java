package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ExplosionExpDropProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      for (int index0 = 0; index0 < (int)Mth.m_216263_(RandomSource.m_216327_(), 1.0, 2.0); index0++) {
         if (world instanceof Level) {
            Level _level = (Level)world;
            if (!_level.m_5776_()) {
               _level.m_7967_(new ExperienceOrb(_level, x, y, z, (int)Mth.m_216263_(RandomSource.m_216327_(), 1.0, 3.0)));
            }
         }
      }
   }
}
