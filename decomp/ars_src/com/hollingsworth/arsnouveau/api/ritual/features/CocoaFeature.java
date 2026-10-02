package com.hollingsworth.arsnouveau.api.ritual.features;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import oshi.util.tuples.Pair;

public class CocoaFeature implements IPlaceableFeature {
   double distance;
   double chance;
   List<BlockState> cocoaStates = new ArrayList<>();

   public CocoaFeature(double distance, double chance) {
      this.distance = distance;
      this.chance = chance;
      BlockState state = Blocks.f_50262_.m_49966_();
      BlockState north = (BlockState)state.m_61124_(CocoaBlock.f_54117_, Direction.NORTH);
      BlockState south = (BlockState)state.m_61124_(CocoaBlock.f_54117_, Direction.SOUTH);
      BlockState east = (BlockState)state.m_61124_(CocoaBlock.f_54117_, Direction.EAST);
      BlockState west = (BlockState)state.m_61124_(CocoaBlock.f_54117_, Direction.WEST);
      this.cocoaStates.add(north);
      this.cocoaStates.add(south);
      this.cocoaStates.add(east);
      this.cocoaStates.add(west);
   }

   @Override
   public double distanceFromOthers() {
      return this.distance;
   }

   @Override
   public boolean onPlace(Level level, BlockPos pos, FeaturePlacementRitual placementRitual, RitualBrazierTile brazierTile) {
      for (BlockState state : this.cocoaStates) {
         if ((double)level.f_46441_.m_188501_() < this.chance && state.m_60710_(level, pos)) {
            if (state.m_60734_().m_5456_() instanceof BlockItem blockItem) {
               blockItem.m_40576_(
                  new BlockPlaceContext(
                     level,
                     ANFakePlayer.getPlayer((ServerLevel)level),
                     InteractionHand.MAIN_HAND,
                     new ItemStack(blockItem),
                     new BlockHitResult(new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), Direction.DOWN, pos, false)
                  )
               );
            }

            return true;
         }
      }

      return false;
   }

   @Override
   public String getFeatureName() {
      return "cocoa";
   }

   @Override
   public Pair<BlockPos, BlockPos> getCustomOffsets() {
      return new Pair(BlockPos.f_121853_, new BlockPos(0, 10, 0));
   }
}
