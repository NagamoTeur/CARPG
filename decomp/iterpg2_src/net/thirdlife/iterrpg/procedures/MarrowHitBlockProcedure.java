package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class MarrowHitBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y, z, 8, 1.0, 1.0, 1.0, 0.0);
      }

      if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = new DemonspineEntity((EntityType<DemonspineEntity>)IterRpgModEntities.DEMONSPINE.get(), _level);
         entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, world.m_213780_().m_188501_() * 360.0F, 0.0F);
         if (entityToSpawn instanceof Mob _mobToSpawn) {
            _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
         }

         world.m_7967_(entityToSpawn);
      }

      if (world instanceof ServerLevel _level) {
         _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y, z, 16, 0.0, 0.0, 0.0, 0.032);
      }
   }
}
