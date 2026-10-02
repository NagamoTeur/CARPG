package com.cerbon.bosses_of_mass_destruction.structure.structure_repair;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.mojang.datafixers.util.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class LichStructureRepair implements StructureRepair {
   @Override
   public ResourceKey<Structure> associatedStructure() {
      return BMDStructures.LICH_STRUCTURE_REGISTRY.getConfiguredStructureKey();
   }

   @Override
   public void repairStructure(ServerLevel level, StructureStart structureStart) {
      BlockPos pos = this.altarCenter(level, structureStart);
      BlockState altar = ((Block)BMDBlocks.CHISELED_STONE_ALTAR.get()).m_49966_();

      for (BlockPos altarPos : List.of(pos.m_122025_(6), pos.m_122030_(6), pos.m_122013_(6), pos.m_122020_(6))) {
         level.m_46597_(altarPos, altar);
         BMDUtils.spawnParticle(
            level, (ParticleOptions)BMDParticles.SOUL_FLAME.get(), VecUtils.asVec3(altarPos).m_82520_(0.5, 1.0, 0.5), VecUtils.unit, 10, 0.0
         );
      }
   }

   @Override
   public boolean shouldRepairStructure(ServerLevel level, StructureStart structureStart) {
      BlockPos pos = this.altarCenter(level, structureStart);
      boolean hasAltar = level.m_8055_(pos.m_122025_(6)).m_60734_() == BMDBlocks.CHISELED_STONE_ALTAR.get();
      boolean noBoss = level.m_143280_((EntityTypeTest)BMDEntities.LICH.get(), lichEntity -> lichEntity.m_20238_(VecUtils.asVec3(pos)) < 10000.0).isEmpty();
      return !hasAltar && noBoss;
   }

   private BlockPos altarCenter(ServerLevel level, StructureStart structureStart) {
      BoundingBox boundingBox = structureStart.m_73601_();
      int yPos = boundingBox.m_162394_().m_6625_(16).m_123342_();
      int centerX = boundingBox.m_162394_().m_123341_();
      int centerZ = boundingBox.m_162394_().m_123343_();
      Optional<Pair<Integer, Integer>> gridPos = IntStream.rangeClosed(-2, 2)
         .boxed()
         .flatMap(x -> IntStream.rangeClosed(-2, 2).mapToObj(z -> new Pair(x + centerX, z + centerZ)))
         .max(Comparator.comparingInt(xzPair -> this.countChestsInColumn(boundingBox, level, (Pair<Integer, Integer>)xzPair)));
      return gridPos.<BlockPos>map(xzPair -> new BlockPos((Integer)xzPair.getFirst(), yPos, (Integer)xzPair.getSecond())).orElse(null);
   }

   private int countChestsInColumn(BoundingBox boundingBox, ServerLevel level, Pair<Integer, Integer> xzPair) {
      return (int)IntStream.rangeClosed(boundingBox.m_162396_(), boundingBox.m_162400_())
         .filter(y -> level.m_8055_(new BlockPos((Integer)xzPair.getFirst(), y, (Integer)xzPair.getSecond())).m_60734_() == Blocks.f_50087_)
         .count();
   }
}
