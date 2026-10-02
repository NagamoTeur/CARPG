package com.hollingsworth.arsnouveau.common.datagen;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class StructureTagProvider extends StructureTagsProvider {
   public static TagKey<Structure> WILDEN_DEN = TagKey.m_203882_(Registry.f_235725_, new ResourceLocation("ars_nouveau", "wilden_den"));
   public static final ResourceKey<Structure> HUNTER_DEN = register("hunter_wilden_den");
   public static final ResourceKey<Structure> STALKER_DEN = register("stalker_wilden_den");
   public static final ResourceKey<Structure> GUARDIAN_DEN = register("guardian_wilden_den");

   public StructureTagProvider(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(pGenerator, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.m_206424_(WILDEN_DEN).m_211101_(new ResourceKey[]{HUNTER_DEN, STALKER_DEN, GUARDIAN_DEN});
   }

   public static ResourceKey<Structure> register(String name) {
      return ResourceKey.m_135785_(Registry.f_235725_, new ResourceLocation("ars_nouveau", name));
   }
}
