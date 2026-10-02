package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.GrieverEntity;
import net.thirdlife.iterrpg.entity.MournstoneEntity;
import net.thirdlife.iterrpg.entity.WeeperEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class MobspawnAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double offset = 0.0;
         double mobdecide = 0.0;
         if (entity.getPersistentData().m_128459_("attackTime") <= 50.0) {
            if (entity.getPersistentData().m_128459_("AuraDelay") <= 0.0) {
               entity.getPersistentData().m_128347_("attackTime", entity.getPersistentData().m_128459_("attackTime") + 1.0);
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     ParticleTypes.f_123762_,
                     x,
                     y + entity.getPersistentData().m_128459_("offset"),
                     z,
                     8,
                     0.25,
                     0.0 + entity.getPersistentData().m_128459_("offset") / 2.0,
                     0.25,
                     0.025
                  );
               }

               entity.getPersistentData().m_128347_("offset", entity.getPersistentData().m_128459_("offset") + 0.025);
            } else {
               entity.getPersistentData().m_128347_("AuraDelay", entity.getPersistentData().m_128459_("AuraDelay") - 1.0);
               offset = 0.0;
            }
         } else {
            mobdecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
            if (mobdecide == 1.0 && world instanceof ServerLevel _level) {
               Entity entityToSpawn = new GrieverEntity((EntityType<GrieverEntity>)IterRpgModEntities.GRIEVER.get(), _level);
               entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (mobdecide == 2.0 && world instanceof ServerLevel _level) {
               Entity entityToSpawn = new MournstoneEntity((EntityType<MournstoneEntity>)IterRpgModEntities.MOURNSTONE.get(), _level);
               entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (mobdecide == 3.0 && world instanceof ServerLevel _level) {
               Entity entityToSpawn = new WeeperEntity((EntityType<WeeperEntity>)IterRpgModEntities.WEEPER.get(), _level);
               entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         }
      }
   }
}
