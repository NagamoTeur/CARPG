package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.common.world.biome.ModBiomes;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BiomeTagProvider extends BiomeTagsProvider {
   public static TagKey<Biome> SUMMON_SPAWN_TAG = TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("ars_nouveau", "summon_spawn"));
   public static TagKey<Biome> ARCHWOOD_BIOME_TAG = TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("ars_nouveau", "archwood_biome"));
   public static TagKey<Biome> NO_MOB_SPAWN = TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("ars_nouveau", "no_mob_spawn"));
   public static TagKey<Biome> BERRY_SPAWN = TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("ars_nouveau", "berry_spawn"));

   public BiomeTagProvider(DataGenerator p_211094_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(p_211094_, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.addTagToTags(ARCHWOOD_BIOME_TAG, BiomeTags.f_207611_, BiomeTags.f_215817_, BiomeTags.f_207591_);
      this.m_206424_(SUMMON_SPAWN_TAG).addTags(new TagKey[]{BiomeTags.f_215817_});
      this.m_206424_(ARCHWOOD_BIOME_TAG).m_211101_(new ResourceKey[]{ModBiomes.ARCHWOOD_FOREST});
      this.m_206424_(NO_MOB_SPAWN).addTags(new TagKey[]{Biomes.IS_MUSHROOM}).m_211101_(new ResourceKey[]{net.minecraft.world.level.biome.Biomes.f_220594_});
      this.addTagToTags(BiomeTags.f_207609_, BERRY_SPAWN);
      this.m_206424_(BERRY_SPAWN).m_211101_(new ResourceKey[]{ModBiomes.ARCHWOOD_FOREST});
   }

   void addTagToTags(TagKey<Biome> biomeTag, TagKey<Biome>... tags) {
      for (TagKey<Biome> tag : tags) {
         this.m_206424_(tag).m_206428_(biomeTag);
      }
   }
}
