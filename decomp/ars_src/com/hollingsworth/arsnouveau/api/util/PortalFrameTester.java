package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.common.block.PortalBlock;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PortalFrameTester {
   protected Predicate<BlockState> VALID_FRAME = null;
   protected int foundPortalBlocks;
   public BlockPos lowerCorner;
   protected Level world;

   public abstract PortalFrameTester init(Level var1, BlockPos var2, Axis var3, Predicate<BlockState> var4);

   public abstract Optional<PortalFrameTester> getNewPortal(Level var1, BlockPos var2, Axis var3, Predicate<BlockState> var4);

   public abstract Optional<PortalFrameTester> getOrEmpty(Level var1, BlockPos var2, Predicate<PortalFrameTester> var3, Axis var4, Predicate<BlockState> var5);

   public abstract boolean isAlreadyLitPortalFrame();

   public abstract boolean isValidFrame();

   public abstract void lightPortal(Block var1);

   public abstract void createPortal(Level var1, BlockPos var2, BlockState var3, Axis var4);

   public abstract boolean isRequestedSize(int var1, int var2);

   public abstract Axis getAxis1();

   public abstract Axis getAxis2();

   public abstract BlockPos doesPortalFitAt(Level var1, BlockPos var2, Axis var3);

   protected BlockPos getLowerCorner(BlockPos blockPos, Axis axis1, Axis axis2) {
      if (!validStateInsidePortal(this.world.m_8055_(blockPos), this.VALID_FRAME)) {
         return null;
      } else {
         int offsetX = 1;

         while (validStateInsidePortal(this.world.m_8055_(blockPos.m_5487_(axis1, -offsetX)), this.VALID_FRAME)) {
            if (++offsetX > 20) {
               return null;
            }
         }

         blockPos = blockPos.m_5487_(axis1, -(offsetX - 1));
         int offsetY = 1;

         while (blockPos.m_123342_() - offsetY > 0 && validStateInsidePortal(this.world.m_8055_(blockPos.m_5487_(axis2, -offsetY)), this.VALID_FRAME)) {
            if (++offsetY > 20) {
               return null;
            }
         }

         return blockPos.m_5487_(axis2, -(offsetY - 1));
      }
   }

   protected int getSize(Axis axis, int minSize, int maxSize) {
      for (int i = 1; i <= maxSize; i++) {
         BlockState blockState = this.world.m_8055_(this.lowerCorner.m_5487_(axis, i));
         if (!validStateInsidePortal(blockState, this.VALID_FRAME)) {
            if (this.VALID_FRAME.test(blockState)) {
               return i >= minSize ? i : 0;
            }
            break;
         }
      }

      return 0;
   }

   protected boolean checkForValidFrame(Axis axis1, Axis axis2, int size1, int size2) {
      BlockPos checkPos = this.lowerCorner.m_122032_();

      for (int i = 0; i < size1; i++) {
         if (!this.VALID_FRAME.test(this.world.m_8055_(checkPos.m_5487_(axis2, -1)))
            || !this.VALID_FRAME.test(this.world.m_8055_(checkPos.m_5487_(axis2, size2)))) {
            return false;
         }

         checkPos = checkPos.m_5487_(axis1, 1);
      }

      checkPos = this.lowerCorner.m_122032_();

      for (int i = 0; i < size2; i++) {
         if (!this.VALID_FRAME.test(this.world.m_8055_(checkPos.m_5487_(axis1, -1)))
            || !this.VALID_FRAME.test(this.world.m_8055_(checkPos.m_5487_(axis1, size1)))) {
            return false;
         }

         checkPos = checkPos.m_5487_(axis2, 1);
      }

      return true;
   }

   protected void countExistingPortalBlocks(Axis axis1, Axis axis2, int size1, int size2) {
      for (int i = 0; i < size1; i++) {
         for (int j = 0; j < size2; j++) {
            if (this.world.m_8055_(this.lowerCorner.m_5487_(axis1, i).m_5487_(axis2, j)).m_60734_() == BlockRegistry.PORTAL_BLOCK) {
               this.foundPortalBlocks++;
            }
         }
      }
   }

   public static boolean validStateInsidePortal(BlockState blockState, Predicate<BlockState> foundations) {
      return blockState.m_60795_() || blockState.m_60767_().m_76336_() || blockState.m_60734_() instanceof PortalBlock;
   }
}
