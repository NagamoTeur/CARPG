package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class SummonSpines8Procedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double attack = 0.0;
      double timer = 0.0;
      double particle = 0.0;
      double fireforce = 0.0;
      double xpos = 0.0;
      double ypos = 0.0;
      double zpos = 0.0;
      double yspawn = 0.0;
      boolean shouldtick = false;
      boolean shouldspawn = false;

      for (int index0 = 0; index0 < 16; index0++) {
         xpos = Mth.m_216263_(RandomSource.m_216327_(), -8.0, 8.0);
         ypos = -4.0;
         zpos = Mth.m_216263_(RandomSource.m_216327_(), -8.0, 8.0);
         shouldspawn = false;

         for (int index1 = 0; index1 < 8; index1++) {
            if (world.m_46859_(new BlockPos(x + xpos, (double)Math.round(y + ypos), z + zpos))
               && world.m_8055_(new BlockPos(x + xpos, (double)Math.round(y + ypos - 1.0), z + zpos)).m_60815_()) {
               yspawn = ypos;
               shouldspawn = true;
            }

            ypos++;
         }

         if (shouldspawn && world instanceof ServerLevel _level) {
            Entity entityToSpawn = new DemonspineEntity((EntityType<DemonspineEntity>)IterRpgModEntities.DEMONSPINE.get(), _level);
            entityToSpawn.m_7678_(x + xpos, (double)Math.round(y + yspawn), z + zpos, world.m_213780_().m_188501_() * 360.0F, 0.0F);
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }
      }
   }
}
