package com.hollingsworth.arsnouveau.api.ritual.features;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class PlaceBlockFeature implements IPlaceableFeature {
   public double distance;
   public Supplier<BlockState> block;
   public double chance;

   public PlaceBlockFeature(double distance, double chance, Supplier<BlockState> block) {
      this.distance = distance;
      this.block = block;
      this.chance = chance;
   }

   @Override
   public double distanceFromOthers() {
      return this.distance;
   }

   @Override
   public boolean onPlace(Level level, BlockPos pos, FeaturePlacementRitual placementRitual, RitualBrazierTile brazierTile) {
      BlockState state = this.block.get();
      if ((double)level.f_46441_.m_188501_() < this.chance && !level.m_8055_(pos.m_7495_()).m_60795_() && state.m_60710_(level, pos)) {
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
      } else {
         return false;
      }
   }

   @Override
   public String getFeatureName() {
      return "block";
   }
}
