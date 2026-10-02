package com.obscuria.aquamirae.mixin;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(
   value = {OverworldBiomes.class},
   priority = 1287
)
public class OverworldBiomesMixin {
   @Shadow
   private static void m_194869_(Builder p_194870_) {
   }

   @Shadow
   protected static int m_194843_(float p_194844_) {
      return 0;
   }

   @Overwrite
   @NotNull
   public static Biome m_194908_(boolean deep) {
      net.minecraft.world.level.biome.MobSpawnSettings.Builder mobspawnsettings$builder = new net.minecraft.world.level.biome.MobSpawnSettings.Builder()
         .m_48376_(MobCategory.WATER_CREATURE, new SpawnerData(EntityType.f_20480_, 1, 1, 4))
         .m_48376_(MobCategory.WATER_AMBIENT, new SpawnerData(EntityType.f_20519_, 15, 1, 5))
         .m_48376_(MobCategory.CREATURE, new SpawnerData(EntityType.f_20514_, 1, 1, 2));
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.MONSTER, new SpawnerData(EntityType.f_20562_, 5, 1, 1));
      float temperature = deep ? -1.0F : 0.0F;
      Builder biomegenerationsettings$builder = new Builder();
      BiomeDefaultFeatures.m_126767_(biomegenerationsettings$builder);
      m_194869_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126769_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126840_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      return new BiomeBuilder()
         .m_47597_(Precipitation.SNOW)
         .m_47609_(temperature)
         .m_47599_(deep ? TemperatureModifier.NONE : TemperatureModifier.FROZEN)
         .m_47611_(0.5F)
         .m_47603_(
            new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
               .m_48034_(3750089)
               .m_48037_(329011)
               .m_48019_(12638463)
               .m_48040_(m_194843_(temperature))
               .m_48027_(AmbientMoodSettings.f_47387_)
               .m_48018_()
         )
         .m_47605_(mobspawnsettings$builder.m_48381_())
         .m_47601_(biomegenerationsettings$builder.m_47831_())
         .m_47592_();
   }
}
