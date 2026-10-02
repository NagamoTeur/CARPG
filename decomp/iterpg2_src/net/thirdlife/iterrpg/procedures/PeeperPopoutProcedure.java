package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.PeeperEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class PeeperPopoutProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 12.0, 12.0, 12.0), e -> true).isEmpty()
         && !world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.BUILDINGDEBUG)
         && world.m_46791_() != Difficulty.PEACEFUL
         && Mth.m_216271_(RandomSource.m_216327_(), 1, 16) == 8) {
         world.m_46961_(new BlockPos(x, y, z), false);
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123759_, x + 0.5, y + 0.5, z + 0.5, 8, 0.15, 0.15, 0.15, 0.025);
         }

         if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = new PeeperEntity((EntityType<PeeperEntity>)IterRpgModEntities.PEEPER.get(), _level);
            entityToSpawn.m_7678_(
               x + 0.5,
               y + 0.5,
               z + 0.5,
               (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0),
               (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0)
            );
            entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
            entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
            entityToSpawn.m_20334_(0.0, 0.32, 0.0);
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }
      }
   }
}
