package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.structures.jisaw.element.CataclysmJigsawSinglePoolElement;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public class ModJigsaw {
   public static StructurePoolElementType<CataclysmJigsawSinglePoolElement> CATACLYSM_ELEMENT;

   public static void registerJigsawElements() {
      CATACLYSM_ELEMENT = register("cataclysm_element", CataclysmJigsawSinglePoolElement.CODEC);
   }

   private static <P extends StructurePoolElement> StructurePoolElementType<P> register(String name, Codec<P> codec) {
      return (StructurePoolElementType<P>)Registry.m_122965_(Registry.f_122892_, new ResourceLocation("cataclysm", name), (StructurePoolElementType)() -> codec);
   }
}
