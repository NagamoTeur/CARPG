package com.bobmowzie.mowziesmobs.server.entity.grottol;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;

public final class BlackPinkRailLine implements Consumer<AbstractMinecart> {
   private final BlackPinkInYourArea action;
   private BlackPinkRailLine.State state = new BlackPinkRailLine.StateAcquireVertex();

   private BlackPinkRailLine(BlackPinkInYourArea action) {
      this.action = action;
   }

   public void accept(AbstractMinecart minecart) {
      this.state = this.next(minecart.f_19853_, minecart);
   }

   private BlackPinkRailLine.State next(Level world, AbstractMinecart minecart) {
      BlockPos pos = getRailPosition(world, new BlockPos(minecart.m_20182_()));
      return BaseRailBlock.m_49416_(world.m_8055_(pos)) ? this.state.apply(world, minecart, pos) : this.state.derail();
   }

   private static BlockPos getRailPosition(Level world, BlockPos pos) {
      BlockPos below = pos.m_7495_();
      return BaseRailBlock.m_49364_(world, below) ? below : pos;
   }

   public static BlackPinkRailLine create() {
      return new BlackPinkRailLine(BlackPinkInYourArea.create());
   }

   private abstract class State {
      abstract BlackPinkRailLine.State apply(Level var1, AbstractMinecart var2, BlockPos var3);

      abstract BlackPinkRailLine.State derail();
   }

   private final class StateAcquireEdge extends BlackPinkRailLine.State {
      private final BlockPos vertex;

      private StateAcquireEdge(BlockPos vertex) {
         this.vertex = vertex;
      }

      @Override
      public BlackPinkRailLine.State apply(Level world, AbstractMinecart minecart, BlockPos vertex) {
         return BlackPinkRailLine.this.new StateSearch(vertex.m_121996_(this.vertex), vertex);
      }

      @Override
      BlackPinkRailLine.State derail() {
         return BlackPinkRailLine.this.new StateAcquireVertex();
      }
   }

   private final class StateAcquireVertex extends BlackPinkRailLine.State {
      @Override
      public BlackPinkRailLine.State apply(Level world, AbstractMinecart minecart, BlockPos vertex) {
         return BlackPinkRailLine.this.new StateAcquireEdge(vertex);
      }

      @Override
      BlackPinkRailLine.State derail() {
         return this;
      }
   }

   private final class StateSearch extends BlackPinkRailLine.State {
      private final long[] mask = new long[]{1151706154984090261L, 1149919144941647226L, 1152921487426969583L, 1152635631447310335L};
      private final long test = 4503599627370496L;
      private Vec3i edge;
      private BlockPos vertex;
      private int ordinal;
      private long state = 4503599627370494L;

      private StateSearch(Vec3i edge, BlockPos vertex) {
         this.edge = edge;
         this.vertex = vertex;
      }

      @Override
      public BlackPinkRailLine.State apply(Level world, AbstractMinecart minecart, BlockPos vertex) {
         if (!this.vertex.equals(vertex)) {
            Vec3i edge = vertex.m_121996_(this.vertex);
            int ordinal = this.getOrdinal(this.edge, edge);
            if (ordinal >= 0
               && ordinal < 4
               && (ordinal != 1 || ordinal != this.ordinal)
               && ((this.state = (this.state | this.mask[ordinal]) << 1) & 4503599627370496L) == 0L) {
               BlackPinkRailLine.this.action.accept(world, minecart);
            }

            this.ordinal = ordinal;
            this.vertex = vertex;
            this.edge = edge;
         }

         return this;
      }

      private int getOrdinal(Vec3i v0, Vec3i v1) {
         return 1
            + (v1.m_123343_() * v0.m_123341_() - v1.m_123341_() * v0.m_123343_())
            + (v0.m_123341_() * v1.m_123341_() + v0.m_123343_() * v1.m_123343_() & 2);
      }

      @Override
      BlackPinkRailLine.State derail() {
         return BlackPinkRailLine.this.new StateAcquireVertex();
      }
   }
}
