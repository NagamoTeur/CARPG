package com.min01.archaeology.init;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ArchaeologyStructureProcessor {
   public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR = DeferredRegister.create(Registry.f_122854_, "minecraft");
   public static final RegistryObject<StructureProcessorType<?>> CAPPED_PROCESSOR = STRUCTURE_PROCESSOR.register(
      "capped", () -> ArchaeologyStructureProcessorType.CAPPED
   );
}
