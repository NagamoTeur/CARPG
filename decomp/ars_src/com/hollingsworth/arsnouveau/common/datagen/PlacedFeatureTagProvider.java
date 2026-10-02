package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.common.world.WorldEvent;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class PlacedFeatureTagProvider extends TagsProvider<PlacedFeature> {
   public static TagKey<PlacedFeature> ARCHWOOD_TREES = TagKey.m_203882_(Registry.f_194567_, new ResourceLocation("ars_nouveau", "archwood_trees"));
   public static TagKey<PlacedFeature> SOURCE_BERRIES = TagKey.m_203882_(Registry.f_194567_, new ResourceLocation("ars_nouveau", "source_berries"));

   public PlacedFeatureTagProvider(DataGenerator p_211094_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(p_211094_, BuiltinRegistries.f_194653_, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.m_206424_(ARCHWOOD_TREES).m_126582_((PlacedFeature)WorldEvent.PLACED_MIXED.get());
      this.m_206424_(SOURCE_BERRIES).m_126582_((PlacedFeature)WorldEvent.BERRY_BUSH_PATCH_CONFIG.get());
   }
}
