package net.xylonity.knightquest.common.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TeleportValidator {
   public static boolean isValidTeleportPosition(Entity entity, BlockPos pos) {
      Level level = entity.f_19853_;
      if (level.m_8055_(pos).m_60734_() != Blocks.f_50016_) {
         return false;
      } else {
         BlockPos blockBelow = pos.m_7495_();
         BlockState stateBelow = level.m_8055_(blockBelow);
         if (stateBelow.m_60734_() != Blocks.f_50016_ && stateBelow.m_60734_() != Blocks.f_49991_ && stateBelow.m_60734_() != Blocks.f_49990_) {
            AABB boundingBox = new AABB(
               (double)pos.m_123341_() - 0.5,
               (double)pos.m_123342_(),
               (double)pos.m_123343_() - 0.5,
               (double)pos.m_123341_() + 0.5,
               (double)((float)pos.m_123342_() + entity.m_20206_()),
               (double)pos.m_123343_() + 0.5
            );
            return level.m_45756_(entity, boundingBox);
         } else {
            return false;
         }
      }
   }

   public static boolean isBetterPosition(Entity entity, BlockPos pos, BlockPos bestPos) {
      double currentDistance = entity.m_20182_().m_82554_(Vec3.m_82512_(pos));
      double bestDistance = entity.m_20182_().m_82554_(Vec3.m_82512_(bestPos));
      return currentDistance < bestDistance;
   }
}
