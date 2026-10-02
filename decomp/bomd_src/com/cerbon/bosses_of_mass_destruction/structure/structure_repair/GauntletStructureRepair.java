package com.cerbon.bosses_of_mass_destruction.structure.structure_repair;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class GauntletStructureRepair implements StructureRepair {
   @Override
   public ResourceKey<Structure> associatedStructure() {
      return BMDStructures.GAUNTLET_STRUCTURE_REGISTRY.getConfiguredStructureKey();
   }

   @Override
   public void repairStructure(ServerLevel level, StructureStart structureStart) {
      BlockPos pos = this.runeCenter(structureStart);
      BMDUtils.spawnParticle(
         level, (ParticleOptions)BMDParticles.GAUNTLET_REVIVE_SPARKLES.get(), VecUtils.asVec3(pos.m_6630_(5)), VecUtils.unit.m_82490_(3.0), 100, 0.0
      );
      this.spawnBlocks(level, pos);
   }

   @Override
   public boolean shouldRepairStructure(ServerLevel level, StructureStart structureStart) {
      BlockPos pos = this.runeCenter(structureStart);
      boolean hasSealAlready = level.m_8055_(pos.m_7494_()).m_60734_() == BMDBlocks.GAUNTLET_BLACKSTONE.get();
      boolean noBoss = level.m_143280_((EntityTypeTest)BMDEntities.GAUNTLET.get(), entity -> entity.m_20238_(VecUtils.asVec3(pos)) < 10000.0).isEmpty();
      return !hasSealAlready && noBoss;
   }

   private void spawnBlocks(ServerLevel level, BlockPos pos) {
      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            for (int y = 0; y <= 4; y++) {
               level.m_46597_(pos.m_7918_(x, y, z), ((Block)BMDBlocks.SEALED_BLACKSTONE.get()).m_49966_());
            }
         }
      }

      BlockPos up = pos.m_7494_();
      BlockState seal = ((Block)BMDBlocks.GAUNTLET_BLACKSTONE.get()).m_49966_();
      level.m_46597_(up, seal);
      level.m_46597_(up.m_122029_(), seal);
      level.m_46597_(up.m_122012_(), seal);
      level.m_46597_(up.m_122024_(), seal);
      level.m_46597_(up.m_122019_(), seal);
   }

   private BlockPos runeCenter(StructureStart structureStart) {
      return structureStart.m_73601_().m_162394_().m_6625_(10);
   }
}
