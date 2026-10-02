package com.cerbon.bosses_of_mass_destruction.entity.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MobEntitySpawnPredicate implements ISpawnPredicate {
   private final LevelReader levelReader;

   public MobEntitySpawnPredicate(LevelReader worldView) {
      this.levelReader = worldView;
   }

   @Override
   public boolean canSpawn(Vec3 pos, Entity entity) {
      BlockPos blockPos = new BlockPos(pos);
      if (!this.levelReader.m_46805_(blockPos)) {
         return false;
      } else {
         BlockState blockState = this.levelReader.m_8055_(blockPos);
         FluidState fluidState = this.levelReader.m_6425_(blockPos);
         AABB prospectiveBoundingBox = entity.m_6095_().m_20585_(pos.f_82479_, pos.f_82480_, pos.f_82481_);
         return !this.levelReader.m_46855_(prospectiveBoundingBox)
            && this.levelReader.m_45772_(prospectiveBoundingBox)
            && NaturalSpawner.m_47056_(this.levelReader, blockPos, blockState, fluidState, entity.m_6095_());
      }
   }
}
