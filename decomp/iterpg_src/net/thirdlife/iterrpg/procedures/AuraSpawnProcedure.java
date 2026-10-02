package net.thirdlife.iterrpg.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.AuraBoulderEntity;
import net.thirdlife.iterrpg.entity.AuraSoulfireEntity;
import net.thirdlife.iterrpg.entity.AuraTearburstEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class AuraSpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      Entity auraselect = null;
      boolean dospawn = false;
      boolean doattack = false;
      boolean shoudattack = false;
      double auraposx = 0.0;
      double auraposz = 0.0;
      double auraposy = 0.0;
      double attackType = 0.0;
      double ycheck = 0.0;
      double specialattack = 0.0;
      double attacktrigger = 0.0;
      double ypos = 0.0;
      double distance = 0.0;
      double yfinal = 0.0;
      double mobcount = 0.0;
      double aurarand = 0.0;
      double repeatnum = 0.0;
      attackType = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
      if (attackType == 1.0) {
         if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = new AuraSoulfireEntity((EntityType<AuraSoulfireEntity>)IterRpgModEntities.AURA_SOULFIRE.get(), _level);
            entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }
      } else if (attackType == 2.0) {
         if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = new AuraTearburstEntity((EntityType<AuraTearburstEntity>)IterRpgModEntities.AURA_TEARBURST.get(), _level);
            entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }
      } else if (attackType == 3.0 && world instanceof ServerLevel _level) {
         Entity entityToSpawn = new AuraBoulderEntity((EntityType<AuraBoulderEntity>)IterRpgModEntities.AURA_BOULDER.get(), _level);
         entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
         if (entityToSpawn instanceof Mob _mobToSpawn) {
            _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
         }

         world.m_7967_(entityToSpawn);
      }
   }
}
