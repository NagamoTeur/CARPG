package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WallTeleport {
   private final ServerLevel level;
   private final Entity entity;

   public WallTeleport(ServerLevel level, Entity entity) {
      this.level = level;
      this.entity = entity;
   }

   public boolean tryTeleport(Vec3 direction, Vec3 position) {
      return this.tryTeleport(direction, position, this::teleportTo);
   }

   public boolean tryTeleport(Vec3 direction, Vec3 position, Consumer<BlockPos> action) {
      WallTeleport.Context context = new WallTeleport.Context(direction, position);
      BlockPos teleportStart = this.getTeleportStart(context);
      if (teleportStart != null) {
         BlockPos teleportEnd = this.getTeleportEnd(context, teleportStart);
         if (teleportEnd != null) {
            action.accept(teleportEnd);
            return true;
         }
      }

      return false;
   }

   private BlockPos getTeleportStart(WallTeleport.Context context) {
      BlockPos startPos = new BlockPos(context.position);
      double startRange = 3.0;
      BlockPos endPos = new BlockPos(context.position.m_82549_(context.direction.m_82490_(startRange)));

      for (BlockPos pos : MathUtils.getBlocksInLine(startPos, endPos)) {
         if (this.level.m_8055_(pos).m_60796_(this.level, pos)) {
            return pos;
         }
      }

      return null;
   }

   private BlockPos getTeleportEnd(WallTeleport.Context context, BlockPos startPos) {
      double endRange = 20.0;
      BlockPos endPos = startPos.m_121955_(new BlockPos(context.direction.m_82490_(endRange)));

      for (BlockPos pos : MathUtils.getBlocksInLine(startPos, endPos)) {
         BlockState blockState = this.level.m_8055_(pos);
         if (blockState.m_60795_() && this.level.m_8055_(pos.m_7494_()).m_60795_()) {
            return pos;
         }

         if (blockState.m_60734_().m_155943_() < 0.0F) {
            return null;
         }
      }

      return null;
   }

   private void teleportTo(BlockPos teleportPos) {
      Vec3 pos = VecUtils.asVec3(teleportPos).m_82549_(new Vec3(0.5, 0.0, 0.5));
      this.entity.m_6021_(pos.f_82479_, pos.f_82480_, pos.f_82481_);
   }

   private static record Context(Vec3 direction, Vec3 position) {
   }
}
