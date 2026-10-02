package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.DemonsoulEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class CarcassDeathProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         Entity entityToSpawn = new DemonsoulEntity((EntityType<DemonsoulEntity>)IterRpgModEntities.DEMONSOUL.get(), _level);
         entityToSpawn.m_7678_(x, y + 2.0, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
         if (entityToSpawn instanceof Mob _mobToSpawn) {
            _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
         }

         world.m_7967_(entityToSpawn);
      }

      if (world instanceof ServerLevel _level) {
         _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y, z, 16, 0.5, 0.5, 0.5, 0.25);
      }

      SummonSpinesProcedure.execute(world, x, y, z);
      if (Mth.m_216271_(RandomSource.m_216327_(), 1, 12) == 8 && world instanceof Level _level && !_level.m_5776_()) {
         ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModItems.BLOODTHIRST.get()));
         entityToSpawn.m_32010_(10);
         _level.m_7967_(entityToSpawn);
      }
   }
}
