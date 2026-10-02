package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;

public class MagmanumSwordTrickProcedure {
   public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         double trick = 0.0;
         entity.m_20254_(5);
         trick = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
         if (trick == 2.0) {
            sourceentity.m_20254_(5);
         } else if (trick == 3.0 && world instanceof Level _level && !_level.m_5776_()) {
            _level.m_46511_(null, entity.m_20185_(), entity.m_20186_() + (double)(entity.m_20206_() / 2.0F), entity.m_20189_(), 1.0F, BlockInteraction.NONE);
         }
      }
   }
}
