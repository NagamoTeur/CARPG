package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class MarrowHitEntityProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity) {
      if (entity != null && immediatesourceentity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y, z, 8, 1.0, 1.0, 1.0, 0.0);
         }

         if (entity.m_20096_()) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = new DemonspineEntity((EntityType<DemonspineEntity>)IterRpgModEntities.DEMONSPINE.get(), _level);
               entityToSpawn.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y, z, 16, 0.0, 0.0, 0.0, 0.032);
            }
         } else {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123744_, x, y, z, 8, 0.0, 0.0, 0.0, 0.032);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123756_, x, y, z, 8, 0.0, 0.0, 0.0, 0.032);
            }

            if (world instanceof Level _level && !_level.m_5776_()) {
               _level.m_46511_(null, x, y, z, 1.16F, BlockInteraction.NONE);
            }

            entity.m_20254_(6);
         }

         if (!immediatesourceentity.f_19853_.m_5776_()) {
            immediatesourceentity.m_146870_();
         }
      }
   }
}
