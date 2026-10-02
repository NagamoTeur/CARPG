package com.github.L_Ender.cataclysm.world.structures.placements;

import com.github.L_Ender.cataclysm.init.ModStructurePlacementType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.ExclusionZone;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod;

public class CataclysmRandomSpread extends RandomSpreadStructurePlacement {
   public static final Codec<CataclysmRandomSpread> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               Vec3i.m_194650_(16).optionalFieldOf("locate_offset", Vec3i.f_123288_).forGetter(rec$ -> rec$.m_227072_()),
               FrequencyReductionMethod.f_227108_
                  .optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT)
                  .forGetter(rec$ -> rec$.m_227073_()),
               Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(rec$ -> rec$.m_227074_()),
               ExtraCodecs.f_144628_.fieldOf("salt").forGetter(rec$ -> rec$.m_227075_()),
               ExclusionZone.f_227077_.optionalFieldOf("exclusion_zone").forGetter(rec$ -> rec$.m_227076_()),
               CataclysmRandomSpread.SuperExclusionZone.CODEC.optionalFieldOf("super_exclusion_zone").forGetter(CataclysmRandomSpread::superExclusionZone),
               Codec.intRange(0, Integer.MAX_VALUE).fieldOf("spacing").forGetter(CataclysmRandomSpread::m_205003_),
               Codec.intRange(0, Integer.MAX_VALUE).fieldOf("separation").forGetter(CataclysmRandomSpread::m_205004_),
               RandomSpreadType.f_205014_.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(CataclysmRandomSpread::m_205005_),
               Codec.intRange(0, Integer.MAX_VALUE)
                  .optionalFieldOf("min_distance_from_world_origin")
                  .forGetter(CataclysmRandomSpread::minDistanceFromWorldOrigin)
            )
            .apply(instance, instance.stable(CataclysmRandomSpread::new))
   );
   private final int spacing;
   private final int separation;
   private final RandomSpreadType spreadType;
   private final Optional<Integer> minDistanceFromWorldOrigin;
   private final Optional<CataclysmRandomSpread.SuperExclusionZone> superExclusionZone;

   public CataclysmRandomSpread(
      Vec3i locationOffset,
      FrequencyReductionMethod frequencyReductionMethod,
      float frequency,
      int salt,
      Optional<ExclusionZone> exclusionZone,
      Optional<CataclysmRandomSpread.SuperExclusionZone> superExclusionZone,
      int spacing,
      int separation,
      RandomSpreadType spreadType,
      Optional<Integer> minDistanceFromWorldOrigin
   ) {
      super(locationOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, spreadType);
      this.spacing = spacing;
      this.separation = separation;
      this.spreadType = spreadType;
      this.minDistanceFromWorldOrigin = minDistanceFromWorldOrigin;
      this.superExclusionZone = superExclusionZone;
      if (spacing <= separation) {
         throw new RuntimeException(
            "    Cataclysm: Spacing cannot be less or equal to separation.\n    Please correct this error as there's no way to spawn this structure properly\n        Spacing: %s\n        Separation: %s.\n"
               .formatted(spacing, separation)
         );
      }
   }

   public int m_205003_() {
      return this.spacing;
   }

   public int m_205004_() {
      return this.separation;
   }

   public RandomSpreadType m_205005_() {
      return this.spreadType;
   }

   public Optional<Integer> minDistanceFromWorldOrigin() {
      return this.minDistanceFromWorldOrigin;
   }

   public Optional<CataclysmRandomSpread.SuperExclusionZone> superExclusionZone() {
      return this.superExclusionZone;
   }

   public boolean m_227054_(ChunkGenerator chunkGenerator, RandomState randomState, long seed, int i, int j) {
      return !super.m_227054_(chunkGenerator, randomState, seed, i, j)
         ? false
         : this.superExclusionZone.isEmpty() || !this.superExclusionZone.get().isPlacementForbidden(chunkGenerator, randomState, (long)i, j, j);
   }

   public ChunkPos m_227008_(long seed, int x, int z) {
      int regionX = Math.floorDiv(x, this.spacing);
      int regionZ = Math.floorDiv(z, this.spacing);
      WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
      worldgenrandom.m_190058_(seed, regionX, regionZ, this.m_227075_());
      int diff = this.spacing - this.separation;
      int offsetX = this.spreadType.m_227018_(worldgenrandom, diff);
      int offsetZ = this.spreadType.m_227018_(worldgenrandom, diff);
      return new ChunkPos(regionX * this.spacing + offsetX, regionZ * this.spacing + offsetZ);
   }

   protected boolean m_214090_(ChunkGenerator ChunkGenerator, RandomState randomState, long seed, int x, int z) {
      if (this.minDistanceFromWorldOrigin.isPresent()) {
         int xBlockPos = x * 16;
         int zBlockPos = z * 16;
         if (xBlockPos * xBlockPos + zBlockPos * zBlockPos < this.minDistanceFromWorldOrigin.get() * this.minDistanceFromWorldOrigin.get()) {
            return false;
         }
      }

      ChunkPos chunkpos = this.m_227008_(seed, x, z);
      return chunkpos.f_45578_ == x && chunkpos.f_45579_ == z;
   }

   public StructurePlacementType<?> m_203443_() {
      return (StructurePlacementType<?>)ModStructurePlacementType.CATACLYSM_RANDOM_SPREAD.get();
   }

   public static record SuperExclusionZone(HolderSet<StructureSet> otherSet, int chunkCount) {
      public static final Codec<CataclysmRandomSpread.SuperExclusionZone> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
                  RegistryCodecs.m_206279_(Registry.f_211073_, StructureSet.f_210001_)
                     .fieldOf("other_set")
                     .forGetter(CataclysmRandomSpread.SuperExclusionZone::otherSet),
                  Codec.intRange(1, 16).fieldOf("chunk_count").forGetter(CataclysmRandomSpread.SuperExclusionZone::chunkCount)
               )
               .apply(builder, CataclysmRandomSpread.SuperExclusionZone::new)
      );

      boolean isPlacementForbidden(ChunkGenerator chunkGenerator, RandomState randomState, long l, int i, int j) {
         for (Holder<StructureSet> holder : this.otherSet) {
            if (chunkGenerator.m_223141_(holder, randomState, l, i, j, this.chunkCount)) {
               return true;
            }
         }

         return false;
      }
   }
}
