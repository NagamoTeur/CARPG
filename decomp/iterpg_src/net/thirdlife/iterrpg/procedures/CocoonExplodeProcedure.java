package net.thirdlife.iterrpg.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.SpiderlingEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class CocoonExplodeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      for (int index0 = 0; index0 < Mth.m_216271_(RandomSource.m_216327_(), 2, 4); index0++) {
         if (world instanceof ServerLevel) {
            ServerLevel _level = (ServerLevel)world;
            Entity entityToSpawn = new SpiderlingEntity((EntityType<SpiderlingEntity>)IterRpgModEntities.SPIDERLING.get(), _level);
            entityToSpawn.m_7678_(
               x + Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.7),
               y + Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.7),
               z + Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.7),
               world.m_213780_().m_188501_() * 360.0F,
               0.0F
            );
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }
      }
   }
}
