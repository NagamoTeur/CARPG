package com.github.L_Ender.cataclysm.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.common.world.ModifiableStructureInfo.StructureInfo.Builder;
import net.minecraftforge.common.world.StructureModifier.Phase;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class CMMobSpawnStructureModifier implements StructureModifier {
   private static final RegistryObject<Codec<? extends StructureModifier>> SERIALIZER = RegistryObject.create(
      new ResourceLocation("cataclysm", "cataclysm_structure_spawns"), Keys.STRUCTURE_MODIFIER_SERIALIZERS, "cataclysm"
   );

   public void modify(Holder<Structure> structure, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         CMWorldRegistry.modifyStructure(structure, builder);
      }
   }

   public Codec<? extends StructureModifier> codec() {
      return (Codec<? extends StructureModifier>)SERIALIZER.get();
   }

   public static Codec<CMMobSpawnStructureModifier> makeCodec() {
      return Codec.unit(CMMobSpawnStructureModifier::new);
   }
}
