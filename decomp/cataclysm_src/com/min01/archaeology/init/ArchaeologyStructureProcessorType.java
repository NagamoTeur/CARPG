package com.min01.archaeology.init;

import com.min01.archaeology.structure.processor.CappedProcessor;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ArchaeologyStructureProcessorType {
   public static final StructureProcessorType<CappedProcessor> CAPPED = register("capped", CappedProcessor.CODEC);

   private static <S extends StructureProcessor> StructureProcessorType<S> register(String name, Codec<S> codec) {
      return (StructureProcessorType<S>)Registry.m_122961_(Registry.f_122891_, name, (StructureProcessorType)() -> codec);
   }
}
