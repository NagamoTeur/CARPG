package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.GoblinEntity;
import net.thirdlife.iterrpg.entity.GoblinWarriorEntity;
import net.thirdlife.iterrpg.entity.HobgoblinEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class MobmorphTurnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.BUILDINGDEBUG)) {
            if (entity.getPersistentData().m_128461_("TurnInto").equals("goblin")) {
               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = new GoblinEntity((EntityType<GoblinEntity>)IterRpgModEntities.GOBLIN.get(), _level);
                  entityToSpawn.m_7678_(x, y, z, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), 0.0F);
                  entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                  entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (!entity.f_19853_.m_5776_()) {
                  entity.m_146870_();
               }
            } else if (entity.getPersistentData().m_128461_("TurnInto").equals("goblin_warrior")) {
               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = new GoblinWarriorEntity((EntityType<GoblinWarriorEntity>)IterRpgModEntities.GOBLIN_WARRIOR.get(), _level);
                  entityToSpawn.m_7678_(x, y, z, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), 0.0F);
                  entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                  entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (!entity.f_19853_.m_5776_()) {
                  entity.m_146870_();
               }
            } else if (entity.getPersistentData().m_128461_("TurnInto").equals("hobgoblin")) {
               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = new HobgoblinEntity((EntityType<HobgoblinEntity>)IterRpgModEntities.HOBGOBLIN.get(), _level);
                  entityToSpawn.m_7678_(x, y, z, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), 0.0F);
                  entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                  entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (!entity.f_19853_.m_5776_()) {
                  entity.m_146870_();
               }
            }
         } else if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true).isEmpty()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_175830_, x, y, z, 1, 0.0, 0.0, 0.0, 0.01);
         }
      }
   }
}
