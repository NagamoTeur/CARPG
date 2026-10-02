package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.RevenantEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class DemonsoulConvertProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (entity instanceof Skeleton) {
            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }

            if (!sourceentity.f_19853_.m_5776_()) {
               sourceentity.m_146870_();
            }

            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = new RevenantEntity((EntityType<RevenantEntity>)IterRpgModEntities.REVENANT.get(), _level);
               entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y + 1.0, z, 16, 0.5, 0.5, 0.5, 0.25);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123744_, x, y + 1.0, z, 16, 0.5, 0.5, 0.5, 0.25);
            }
         } else if (entity instanceof Stray) {
            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }

            if (!sourceentity.f_19853_.m_5776_()) {
               sourceentity.m_146870_();
            }

            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = new RevenantEntity((EntityType<RevenantEntity>)IterRpgModEntities.REVENANT.get(), _level);
               entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y + 1.0, z, 16, 0.5, 0.5, 0.5, 0.25);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123744_, x, y + 1.0, z, 16, 0.5, 0.5, 0.5, 0.25);
            }
         } else if (entity instanceof WitherSkeleton) {
            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }

            if (!sourceentity.f_19853_.m_5776_()) {
               sourceentity.m_146870_();
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y + 1.0, z, 16, 0.5, 0.5, 0.5, 0.25);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123744_, x, y + 1.0, z, 16, 0.5, 0.5, 0.5, 0.25);
            }

            if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
               Entity _entityForSpawning = new RevenantEntity((EntityType<RevenantEntity>)IterRpgModEntities.REVENANT.get(), _serverLevelForEntitySpawn);
               _entityForSpawning.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               _entityForSpawning.getPersistentData().m_128379_("demonsoul", true);
               if (_entityForSpawning instanceof Mob _mobForSpawning) {
                  _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(_entityForSpawning);
            }
         }
      }
   }
}
