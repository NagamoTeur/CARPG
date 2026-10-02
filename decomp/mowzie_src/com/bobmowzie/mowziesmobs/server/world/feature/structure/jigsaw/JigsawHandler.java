package com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public class JigsawHandler {
   public static StructurePoolElementType<MowziePoolElement> MOWZIE_ELEMENT;
   public static StructurePoolElementType<FallbackPoolElement> FALLBACK_ELEMENT;

   public static void registerJigsawElements() {
      MOWZIE_ELEMENT = register("mowzie_element", MowziePoolElement.CODEC);
      FALLBACK_ELEMENT = register("fallback_element", FallbackPoolElement.CODEC);
   }

   private static <P extends StructurePoolElement> StructurePoolElementType<P> register(String name, Codec<P> codec) {
      return (StructurePoolElementType<P>)Registry.m_122965_(
         Registry.f_122892_, new ResourceLocation("mowziesmobs", name), (StructurePoolElementType)() -> codec
      );
   }
}
