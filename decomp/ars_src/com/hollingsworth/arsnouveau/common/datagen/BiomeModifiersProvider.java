package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.JsonElement;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.mojang.serialization.JsonOps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags.Biomes;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveSpawnsBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import org.jetbrains.annotations.NotNull;

public class BiomeModifiersProvider {
   static final ResourceLocation STARBUNCLE_SPAWN = prefix("starbuncle_spawn");
   static final ResourceLocation GIFT_STARBUNCLE_SPAWN = prefix("gift_starbuncle_spawn");
   static final ResourceLocation DRYGMY_SPAWN = prefix("drygmy_spawn");
   static final ResourceLocation WHIRLISPRIG_SPAWN = prefix("whirlisprig_spawn");
   static final ResourceLocation WILDEN_HUNTER_SPAWN = prefix("wilden_hunter_spawn");
   static final ResourceLocation WILDEN_STALKER_SPAWN = prefix("wilden_stalker_spawn");
   static final ResourceLocation WILDEN_GUARDIAN_SPAWN = prefix("wilden_guardian_spawn");
   static final ResourceLocation NO_SPAWN = prefix("no_spawn");
   static final ResourceLocation ARCHWOOD_MIX_COMMON = prefix("common_archwood_mix");
   static final ResourceLocation ARCHWOOD_MIX_RARE = prefix("rare_archwood_mix");
   static final ResourceLocation BERRY_COMMON = prefix("common_source_berry");

   static void datagenModifiers(GatherDataEvent event) {
      RegistryOps<JsonElement> ops = RegistryOps.m_206821_(JsonOps.INSTANCE, RegistryAccess.m_206197_());
      Map<ResourceLocation, BiomeModifier> modifierMap = new HashMap<>();
      new Named((Registry)ops.m_206826_(Registry.f_122885_).orElseThrow(), BiomeTags.f_215817_);
      Named<Biome> OVERWORLD_TAG = new Named((Registry)ops.m_206826_(Registry.f_122885_).orElseThrow(), BiomeTags.f_215817_);
      Named<Biome> NO_SPAWN_HOSTILE = new Named((Registry)ops.m_206826_(Registry.f_122885_).orElseThrow(), BiomeTagProvider.NO_MOB_SPAWN);
      new Named((Registry)ops.m_206826_(Registry.f_122885_).orElseThrow(), Biomes.IS_COLD_OVERWORLD);
      Named<Biome> BERRY_BIOMES = new Named((Registry)ops.m_206826_(Registry.f_122885_).orElseThrow(), BiomeTagProvider.BERRY_SPAWN);
      Named<EntityType<?>> HOSTILE = new Named((Registry)ops.m_206826_(Registry.f_122903_).orElseThrow(), EntityTags.HOSTILE_MOBS);
      modifierMap.put(
         STARBUNCLE_SPAWN, AddSpawnsBiomeModifier.singleSpawn(OVERWORLD_TAG, new SpawnerData((EntityType)ModEntities.STARBUNCLE_TYPE.get(), 5, 1, 2))
      );
      modifierMap.put(
         GIFT_STARBUNCLE_SPAWN, AddSpawnsBiomeModifier.singleSpawn(OVERWORLD_TAG, new SpawnerData((EntityType)ModEntities.GIFT_STARBY.get(), 1, 1, 1))
      );
      modifierMap.put(DRYGMY_SPAWN, AddSpawnsBiomeModifier.singleSpawn(OVERWORLD_TAG, new SpawnerData((EntityType)ModEntities.ENTITY_DRYGMY.get(), 3, 1, 2)));
      modifierMap.put(
         WHIRLISPRIG_SPAWN, AddSpawnsBiomeModifier.singleSpawn(OVERWORLD_TAG, new SpawnerData((EntityType)ModEntities.WHIRLISPRIG_TYPE.get(), 5, 1, 2))
      );
      modifierMap.put(NO_SPAWN, new RemoveSpawnsBiomeModifier(NO_SPAWN_HOSTILE, HOSTILE));
      HolderSet<PlacedFeature> TREESET = new Named((Registry)ops.m_206826_(Registry.f_194567_).orElseThrow(), PlacedFeatureTagProvider.ARCHWOOD_TREES);
      modifierMap.put(ARCHWOOD_MIX_RARE, new AddFeaturesBiomeModifier(OVERWORLD_TAG, TREESET, Decoration.VEGETAL_DECORATION));
      HolderSet<PlacedFeature> BERRY_SET = new Named((Registry)ops.m_206826_(Registry.f_194567_).orElseThrow(), PlacedFeatureTagProvider.SOURCE_BERRIES);
      modifierMap.put(BERRY_COMMON, new AddFeaturesBiomeModifier(BERRY_BIOMES, BERRY_SET, Decoration.VEGETAL_DECORATION));
      event.getGenerator()
         .m_236039_(
            event.includeServer(),
            JsonCodecProvider.forDatapackRegistry(event.getGenerator(), event.getExistingFileHelper(), "ars_nouveau", ops, Keys.BIOME_MODIFIERS, modifierMap)
         );
   }

   @NotNull
   private static ResourceLocation prefix(String path) {
      return new ResourceLocation("ars_nouveau", path);
   }
}
