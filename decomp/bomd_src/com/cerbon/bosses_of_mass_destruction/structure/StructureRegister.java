package com.cerbon.bosses_of_mass_destruction.structure;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureRegister {
   private final ResourceKey<Structure> configuredStructureKey;

   public StructureRegister(ResourceLocation structureResourceLocation) {
      this.configuredStructureKey = this.createConfigureStructureKey(structureResourceLocation);
   }

   private ResourceKey<Structure> createConfigureStructureKey(ResourceLocation resourceLocation) {
      return ResourceKey.m_135785_(Registry.f_235725_, resourceLocation);
   }

   public ResourceKey<Structure> getConfiguredStructureKey() {
      return this.configuredStructureKey;
   }
}
