package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.SorrowsealedEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class SorrowsealedSpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (!world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.BUILDINGDEBUG)
         && !world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y - 3.0, z), 24.0, 24.0, 24.0), e -> true).isEmpty()) {
         if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = new SorrowsealedEntity((EntityType<SorrowsealedEntity>)IterRpgModEntities.SORROWSEALED.get(), _level);
            entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }

         world.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 3);
      }
   }
}
