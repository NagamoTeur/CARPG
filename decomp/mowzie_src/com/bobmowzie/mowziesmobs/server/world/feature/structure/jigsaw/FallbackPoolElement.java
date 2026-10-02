package com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class FallbackPoolElement extends StructurePoolElement {
   public static final Codec<FallbackPoolElement> CODEC = Codec.unit(() -> FallbackPoolElement.INSTANCE);
   public static final FallbackPoolElement INSTANCE = new FallbackPoolElement();

   private FallbackPoolElement() {
      super(Projection.TERRAIN_MATCHING);
   }

   public Vec3i m_213577_(StructureTemplateManager p_210191_, Rotation p_210192_) {
      return Vec3i.f_123288_;
   }

   public List<StructureBlockInfo> m_213638_(StructureTemplateManager p_210198_, BlockPos p_210199_, Rotation p_210200_, RandomSource p_210201_) {
      return Collections.emptyList();
   }

   public BoundingBox m_214015_(StructureTemplateManager p_210194_, BlockPos p_210195_, Rotation p_210196_) {
      throw new IllegalStateException("Invalid call to MowzieFallbackElement.getBoundingBox, filter me!");
   }

   public boolean m_213695_(
      StructureTemplateManager p_210180_,
      WorldGenLevel p_210181_,
      StructureManager p_210182_,
      ChunkGenerator p_210183_,
      BlockPos p_210184_,
      BlockPos p_210185_,
      Rotation p_210186_,
      BoundingBox p_210187_,
      RandomSource p_210188_,
      boolean p_210189_
   ) {
      return true;
   }

   public StructurePoolElementType<?> m_207234_() {
      return StructurePoolElementType.f_210545_;
   }

   public String toString() {
      return "Fallback";
   }
}
