package com.cerbon.bosses_of_mass_destruction.mixin;

import com.cerbon.bosses_of_mass_destruction.block.custom.MobWardBlock;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({NaturalSpawner.class})
public class NaturalSpawnerMixin {
   @Inject(
      method = {"isValidSpawnPostitionForType"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void canSpawn(
      ServerLevel level,
      MobCategory category,
      StructureManager structureManager,
      ChunkGenerator generator,
      SpawnerData data,
      MutableBlockPos pos,
      double distance,
      CallbackInfoReturnable<Boolean> cir
   ) {
      MobWardBlock.canSpawn(level, pos, cir);
   }
}
