package com.hollingsworth.arsnouveau.common.world.biome;

import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.world.DefaultFeatures;
import com.hollingsworth.arsnouveau.common.world.WorldEvent;
import com.hollingsworth.arsnouveau.setup.SoundRegistry;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class ModBiomes {
   public static final ResourceKey<Biome> ARCHWOOD_FOREST = register("archwood_forest");
   @Nullable
   static final Music NORMAL_MUSIC = null;

   public static ResourceKey<Biome> register(String name) {
      return ResourceKey.m_135785_(Registry.f_122885_, new ResourceLocation("ars_nouveau", name));
   }

   public static void registerBiomes(IForgeRegistry<Biome> registry) {
      registry.register(ARCHWOOD_FOREST.m_135782_(), archwoodForest());
   }

   public static Biome biome(
      Precipitation precipitation,
      float temperature,
      float downfall,
      int waterColor,
      int waterFogColor,
      int skyColor,
      int grassColor,
      int foliageColor,
      Builder spawnBuilder,
      net.minecraft.world.level.biome.BiomeGenerationSettings.Builder biomeBuilder,
      Supplier<Music> music
   ) {
      return new BiomeBuilder()
         .m_47597_(precipitation)
         .m_47609_(temperature)
         .m_47611_(downfall)
         .m_47603_(
            new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
               .m_48045_(grassColor)
               .m_48043_(foliageColor)
               .m_48034_(waterColor)
               .m_48037_(waterFogColor)
               .m_48019_(12638463)
               .m_48040_(skyColor)
               .m_48027_(AmbientMoodSettings.f_47387_)
               .m_48021_(music.get())
               .m_48018_()
         )
         .m_47605_(spawnBuilder.m_48381_())
         .m_47601_(biomeBuilder.m_47831_())
         .m_47592_();
   }

   public static void globalOverworldGeneration(net.minecraft.world.level.biome.BiomeGenerationSettings.Builder builder) {
      BiomeDefaultFeatures.m_194720_(builder);
      BiomeDefaultFeatures.m_176857_(builder);
      BiomeDefaultFeatures.m_126806_(builder);
      BiomeDefaultFeatures.m_126810_(builder);
      BiomeDefaultFeatures.m_126765_(builder);
      BiomeDefaultFeatures.m_126771_(builder);
   }

   public static Biome archwoodForest() {
      Builder spawnBuilder = new Builder();
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData((EntityType)ModEntities.STARBUNCLE_TYPE.get(), 2, 3, 5));
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData((EntityType)ModEntities.ENTITY_DRYGMY.get(), 2, 1, 3));
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData((EntityType)ModEntities.WHIRLISPRIG_TYPE.get(), 2, 1, 3));
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData((EntityType)ModEntities.ENTITY_BLAZING_WEALD.get(), 3, 1, 1));
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData((EntityType)ModEntities.ENTITY_CASCADING_WEALD.get(), 3, 1, 1));
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData((EntityType)ModEntities.ENTITY_FLOURISHING_WEALD.get(), 3, 1, 1));
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData((EntityType)ModEntities.ENTITY_VEXING_WEALD.get(), 3, 1, 1));
      spawnBuilder.m_48376_(MobCategory.CREATURE, new SpawnerData(EntityType.f_20499_, 5, 4, 4));
      BiomeDefaultFeatures.m_126734_(spawnBuilder);
      BiomeDefaultFeatures.m_126788_(spawnBuilder);
      net.minecraft.world.level.biome.BiomeGenerationSettings.Builder biomeBuilder = new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder();
      globalOverworldGeneration(biomeBuilder);
      BiomeDefaultFeatures.m_126826_(biomeBuilder);
      BiomeDefaultFeatures.m_126706_(biomeBuilder);
      BiomeDefaultFeatures.m_126828_(biomeBuilder);
      BiomeDefaultFeatures.m_126814_(biomeBuilder);
      BiomeDefaultFeatures.m_126816_(biomeBuilder);
      DefaultFeatures.softDisks(biomeBuilder);
      biomeBuilder.m_204201_(Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195421_);
      BiomeDefaultFeatures.m_126730_(biomeBuilder);
      BiomeDefaultFeatures.m_126745_(biomeBuilder);
      biomeBuilder.m_204201_(
         Decoration.LOCAL_MODIFICATIONS,
         BuiltinRegistries.f_194653_.m_206081_(ResourceKey.m_135785_(Registry.f_194567_, new ResourceLocation("ars_nouveau", "placed_lights")))
      );
      biomeBuilder.m_204201_(Decoration.VEGETAL_DECORATION, WorldEvent.COMMON_ARCHWOOD);
      biomeBuilder.m_204201_(Decoration.VEGETAL_DECORATION, WorldEvent.ArtisanalMojangGrassTM);
      biomeBuilder.m_204201_(Decoration.VEGETAL_DECORATION, WorldEvent.ArtisanalMojangFlowersTM);
      return biome(
         Precipitation.RAIN,
         0.7F,
         0.8F,
         7978751,
         329011,
         7978751,
         2031567,
         2210437,
         spawnBuilder,
         biomeBuilder,
         () -> Musics.m_11653_((SoundEvent)SoundRegistry.ARIA_BIBLIO.get())
      );
   }
}
