package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.GrimBoulderEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class BoulderAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean boulderdecide = false;
         double offset = 0.0;
         double mobdecide = 0.0;
         double ypos = 0.0;
         double yfall = 0.0;
         if (entity.getPersistentData().m_128459_("attackTime") <= 64.0) {
            if (entity.getPersistentData().m_128459_("AuraDelay") <= 0.0) {
               entity.getPersistentData().m_128347_("attackTime", entity.getPersistentData().m_128459_("attackTime") + 1.0);
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     ParticleTypes.f_123746_,
                     x,
                     y + entity.getPersistentData().m_128459_("offset"),
                     z,
                     4,
                     entity.getPersistentData().m_128459_("offset"),
                     0.25,
                     entity.getPersistentData().m_128459_("offset"),
                     0.025
                  );
               }

               entity.getPersistentData().m_128347_("offset", entity.getPersistentData().m_128459_("offset") - 0.01);
               if (entity.getPersistentData().m_128459_("attackTime") == 50.0) {
                  ypos = y + 1.0;
                  boulderdecide = false;

                  for (int index0 = 0; index0 < 16; index0++) {
                     if (!world.m_46859_(new BlockPos(x, ypos, z))) {
                        if (!boulderdecide) {
                           boulderdecide = true;
                           yfall = ypos - 2.0;
                        }
                     } else if (!boulderdecide) {
                        ypos++;
                     }
                  }

                  if (!boulderdecide) {
                     yfall = y + 16.0;
                  }

                  if (world instanceof ServerLevel _level) {
                     Entity entityToSpawn = new GrimBoulderEntity((EntityType<GrimBoulderEntity>)IterRpgModEntities.GRIM_BOULDER.get(), _level);
                     entityToSpawn.m_7678_(x, yfall, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }
               }
            } else {
               entity.getPersistentData().m_128347_("AuraDelay", entity.getPersistentData().m_128459_("AuraDelay") - 1.0);
               offset = 2.0;
            }
         } else if (!entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }
      }
   }
}
