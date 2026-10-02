package com.hollingsworth.arsnouveau.api.util;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FlatPortalAreaHelper extends PortalFrameTester {
   public int xSize = -1;
   public int zSize = -1;
   public final int maxXSize = 21;
   public final int maxZSize = 21;

   public FlatPortalAreaHelper init(Level world, BlockPos blockPos, Axis axis, Predicate<BlockState> foundations) {
      this.VALID_FRAME = foundations;
      this.world = world;
      this.lowerCorner = this.getLowerCorner(blockPos, Axis.X, Axis.Z);
      this.foundPortalBlocks = 0;
      if (this.lowerCorner == null) {
         this.lowerCorner = blockPos;
         this.xSize = this.zSize = 0;
      } else {
         this.xSize = this.getSize(Axis.X, 1, 21);
         if (this.xSize > 0) {
            this.zSize = this.getSize(Axis.Z, 1, 21);
            if (this.checkForValidFrame(Axis.X, Axis.Z, this.xSize, this.zSize)) {
               this.countExistingPortalBlocks(Axis.X, Axis.Z, this.xSize, this.zSize);
            } else {
               this.lowerCorner = null;
               this.xSize = this.zSize = 1;
            }
         }
      }

      return this;
   }

   @Override
   public Optional<PortalFrameTester> getNewPortal(Level worldAccess, BlockPos blockPos, Axis axis, Predicate<BlockState> foundations) {
      return this.getOrEmpty(worldAccess, blockPos, areaHelper -> areaHelper.isValidFrame() && areaHelper.foundPortalBlocks == 0, axis, foundations);
   }

   @Override
   public Optional<PortalFrameTester> getOrEmpty(
      Level worldAccess, BlockPos blockPos, Predicate<PortalFrameTester> predicate, Axis axis, Predicate<BlockState> foundations
   ) {
      return Optional.of(new FlatPortalAreaHelper().init(worldAccess, blockPos, axis, foundations)).filter(predicate);
   }

   @Override
   public boolean isAlreadyLitPortalFrame() {
      return this.isValidFrame() && this.foundPortalBlocks == this.xSize * this.zSize;
   }

   @Override
   public boolean isValidFrame() {
      return this.lowerCorner != null && this.xSize >= 1 && this.zSize >= 1 && this.xSize < 21 && this.zSize < 21;
   }

   @Override
   public void lightPortal(Block frameBlock) {
      BlockPos.m_121940_(this.lowerCorner, this.lowerCorner.m_5487_(Axis.X, this.xSize - 1).m_5487_(Axis.Z, this.zSize - 1))
         .forEach(blockPos -> this.world.m_7731_(blockPos, Blocks.f_50127_.m_49966_(), 18));
   }

   @Override
   public void createPortal(Level world, BlockPos pos, BlockState frameBlock, Axis axis) {
      for (int i = -1; i < 3; i++) {
         world.m_46597_(pos.m_5487_(Axis.X, i).m_5487_(Axis.Z, -1), frameBlock);
         world.m_46597_(pos.m_5487_(Axis.X, i).m_5487_(Axis.Z, 2), frameBlock);
         world.m_46597_(pos.m_5487_(Axis.Z, i).m_5487_(Axis.X, -1), frameBlock);
         world.m_46597_(pos.m_5487_(Axis.Z, i).m_5487_(Axis.X, 2), frameBlock);
      }

      for (int i = 0; i < 2; i++) {
         this.placeLandingPad(world, pos.m_5487_(Axis.X, i).m_7495_(), frameBlock);
         this.placeLandingPad(world, pos.m_5487_(Axis.X, i).m_5487_(Axis.Z, 1).m_7495_(), frameBlock);
         this.fillAirAroundPortal(world, pos.m_5487_(Axis.X, i).m_7494_());
         this.fillAirAroundPortal(world, pos.m_5487_(Axis.X, i).m_5487_(Axis.Z, 1).m_7494_());
         this.fillAirAroundPortal(world, pos.m_5487_(Axis.X, i).m_6630_(2));
         this.fillAirAroundPortal(world, pos.m_5487_(Axis.X, i).m_5487_(Axis.Z, 1).m_6630_(2));
      }

      this.lowerCorner = pos;
      this.xSize = this.zSize = 2;
      this.world = world;
      this.foundPortalBlocks = 4;
      this.lightPortal(frameBlock.m_60734_());
   }

   private void fillAirAroundPortal(Level world, BlockPos pos) {
      if (world.m_8055_(pos).m_60767_().m_76333_()) {
         world.m_46597_(pos, Blocks.f_50016_.m_49966_());
      }
   }

   private void placeLandingPad(Level world, BlockPos pos, BlockState frameBlock) {
      if (!world.m_8055_(pos).m_60767_().m_76333_()) {
         world.m_46597_(pos, frameBlock);
      }
   }

   @Override
   public boolean isRequestedSize(int attemptWidth, int attemptHeight) {
      return (this.xSize == attemptWidth || attemptHeight == 0) && this.zSize == attemptHeight
         || attemptWidth == 0
         || (this.xSize == attemptHeight || attemptHeight == 0) && (this.zSize == attemptWidth || attemptWidth == 0);
   }

   @Override
   public Axis getAxis1() {
      return Axis.X;
   }

   @Override
   public Axis getAxis2() {
      return Axis.Z;
   }

   @Override
   public BlockPos doesPortalFitAt(Level world, BlockPos attemptPos, Axis axis) {
      return attemptPos;
   }
}
