package com.bobmowzie.mowziesmobs.server.world.spawn;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.world.BiomeChecker;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class SpawnHandler {
   public static BiomeChecker FOLIAATH_BIOME_CHECKER;
   public static BiomeChecker UMVUTHANA_RAPTOR_BIOME_CHECKER;
   public static BiomeChecker GROTTOL_BIOME_CHECKER;
   public static BiomeChecker LANTERN_BIOME_CHECKER;
   public static BiomeChecker NAGA_BIOME_CHECKER;
   public static final Map<EntityType<?>, ConfigHandler.SpawnConfig> spawnConfigs = new HashMap<>();

   public static void registerSpawnPlacementTypes() {
      Type.create(
         "MMSPAWN",
         new TriPredicate<LevelReader, BlockPos, EntityType<? extends Mob>>() {
            public boolean test(LevelReader t, BlockPos pos, EntityType<? extends Mob> entityType) {
               BlockState block = t.m_8055_(pos.m_7495_());
               if (block.m_60734_() != Blocks.f_50752_ && block.m_60734_() != Blocks.f_50375_ && block.m_60767_().m_76334_()) {
                  BlockState iblockstateUp = t.m_8055_(pos);
                  BlockState iblockstateUp2 = t.m_8055_(pos.m_7494_());
                  return NaturalSpawner.m_47056_(t, pos, iblockstateUp, iblockstateUp.m_60819_(), entityType)
                     && NaturalSpawner.m_47056_(t, pos.m_7494_(), iblockstateUp2, iblockstateUp2.m_60819_(), entityType);
               } else {
                  return false;
               }
            }
         }
      );
      Type mmSpawn = Type.valueOf("MMSPAWN");
      if (mmSpawn != null) {
         SpawnPlacements.m_21754_((EntityType)EntityHandler.FOLIAATH.get(), mmSpawn, Types.MOTION_BLOCKING, MowzieEntity::spawnPredicate);
         SpawnPlacements.m_21754_((EntityType)EntityHandler.LANTERN.get(), mmSpawn, Types.MOTION_BLOCKING, MowzieEntity::spawnPredicate);
         SpawnPlacements.m_21754_((EntityType)EntityHandler.UMVUTHANA_RAPTOR.get(), mmSpawn, Types.MOTION_BLOCKING_NO_LEAVES, MowzieEntity::spawnPredicate);
         SpawnPlacements.m_21754_((EntityType)EntityHandler.NAGA.get(), Type.NO_RESTRICTIONS, Types.MOTION_BLOCKING, MowzieEntity::spawnPredicate);
         SpawnPlacements.m_21754_((EntityType)EntityHandler.GROTTOL.get(), mmSpawn, Types.MOTION_BLOCKING_NO_LEAVES, MowzieEntity::spawnPredicate);
         SpawnPlacements.m_21754_((EntityType)EntityHandler.UMVUTHANA_CRANE.get(), mmSpawn, Types.MOTION_BLOCKING_NO_LEAVES, MowzieEntity::spawnPredicate);
      }
   }

   public static void addBiomeSpawns(Holder<Biome> biomeKey, Builder builder) {
      if (FOLIAATH_BIOME_CHECKER == null) {
         FOLIAATH_BIOME_CHECKER = new BiomeChecker(ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig.biomeConfig);
      }

      if ((Integer)ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig.spawnRate.get() > 0 && FOLIAATH_BIOME_CHECKER.isBiomeInConfig(biomeKey)) {
         registerEntityWorldSpawn(builder, (EntityType<?>)EntityHandler.FOLIAATH.get(), ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig, MobCategory.MONSTER);
      }

      if (UMVUTHANA_RAPTOR_BIOME_CHECKER == null) {
         UMVUTHANA_RAPTOR_BIOME_CHECKER = new BiomeChecker(ConfigHandler.COMMON.MOBS.UMVUTHANA.spawnConfig.biomeConfig);
      }

      if ((Integer)ConfigHandler.COMMON.MOBS.UMVUTHANA.spawnConfig.spawnRate.get() > 0 && UMVUTHANA_RAPTOR_BIOME_CHECKER.isBiomeInConfig(biomeKey)) {
         registerEntityWorldSpawn(
            builder, (EntityType<?>)EntityHandler.UMVUTHANA_RAPTOR.get(), ConfigHandler.COMMON.MOBS.UMVUTHANA.spawnConfig, MobCategory.MONSTER
         );
      }

      if (GROTTOL_BIOME_CHECKER == null) {
         GROTTOL_BIOME_CHECKER = new BiomeChecker(ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig.biomeConfig);
      }

      if ((Integer)ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig.spawnRate.get() > 0 && GROTTOL_BIOME_CHECKER.isBiomeInConfig(biomeKey)) {
         registerEntityWorldSpawn(builder, (EntityType<?>)EntityHandler.GROTTOL.get(), ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig, MobCategory.MONSTER);
      }

      if (LANTERN_BIOME_CHECKER == null) {
         LANTERN_BIOME_CHECKER = new BiomeChecker(ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig.biomeConfig);
      }

      if ((Integer)ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig.spawnRate.get() > 0 && LANTERN_BIOME_CHECKER.isBiomeInConfig(biomeKey)) {
         registerEntityWorldSpawn(builder, (EntityType<?>)EntityHandler.LANTERN.get(), ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig, MobCategory.AMBIENT);
      }

      if (NAGA_BIOME_CHECKER == null) {
         NAGA_BIOME_CHECKER = new BiomeChecker(ConfigHandler.COMMON.MOBS.NAGA.spawnConfig.biomeConfig);
      }

      if ((Integer)ConfigHandler.COMMON.MOBS.NAGA.spawnConfig.spawnRate.get() > 0 && NAGA_BIOME_CHECKER.isBiomeInConfig(biomeKey)) {
         registerEntityWorldSpawn(builder, (EntityType<?>)EntityHandler.NAGA.get(), ConfigHandler.COMMON.MOBS.NAGA.spawnConfig, MobCategory.MONSTER);
      }
   }

   private static void registerEntityWorldSpawn(Builder builder, EntityType<?> entity, ConfigHandler.SpawnConfig spawnConfig, MobCategory classification) {
      builder.getMobSpawnSettings()
         .getSpawner(classification)
         .add(new SpawnerData(entity, (Integer)spawnConfig.spawnRate.get(), (Integer)spawnConfig.minGroupSize.get(), (Integer)spawnConfig.maxGroupSize.get()));
   }

   static {
      spawnConfigs.put((EntityType<?>)EntityHandler.FOLIAATH.get(), ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig);
      spawnConfigs.put((EntityType<?>)EntityHandler.UMVUTHANA_RAPTOR.get(), ConfigHandler.COMMON.MOBS.UMVUTHANA.spawnConfig);
      spawnConfigs.put((EntityType<?>)EntityHandler.LANTERN.get(), ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig);
      spawnConfigs.put((EntityType<?>)EntityHandler.NAGA.get(), ConfigHandler.COMMON.MOBS.NAGA.spawnConfig);
      spawnConfigs.put((EntityType<?>)EntityHandler.GROTTOL.get(), ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig);
   }
}
