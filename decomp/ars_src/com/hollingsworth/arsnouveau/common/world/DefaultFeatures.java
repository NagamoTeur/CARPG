package com.hollingsworth.arsnouveau.common.world;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class DefaultFeatures {
   public static void softDisks(Builder pBuilder) {
      pBuilder.m_204201_(
         Decoration.UNDERGROUND_ORES,
         BuiltinRegistries.f_194653_.m_206081_(ResourceKey.m_135785_(Registry.f_194567_, new ResourceLocation("ars_nouveau", "placed_disk_sand")))
      );
      pBuilder.m_204201_(
         Decoration.UNDERGROUND_ORES,
         BuiltinRegistries.f_194653_.m_206081_(ResourceKey.m_135785_(Registry.f_194567_, new ResourceLocation("ars_nouveau", "placed_disk_clay")))
      );
      pBuilder.m_204201_(
         Decoration.UNDERGROUND_ORES,
         BuiltinRegistries.f_194653_.m_206081_(ResourceKey.m_135785_(Registry.f_194567_, new ResourceLocation("ars_nouveau", "placed_disk_gravel")))
      );
   }
}
