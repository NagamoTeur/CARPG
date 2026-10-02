package com.hollingsworth.arsnouveau.api.ritual.features;

import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import oshi.util.tuples.Pair;

public class ConvertBlockFeature implements IPlaceableFeature {
   public double distance;
   public double chance;
   Function<BlockState, Boolean> convertable;
   Function<BlockState, BlockState> convert;
   Pair<BlockPos, BlockPos> offsets;

   public ConvertBlockFeature(
      double distance, double chance, Function<BlockState, Boolean> convertable, Function<BlockState, BlockState> convert, Pair<BlockPos, BlockPos> offsets
   ) {
      this.distance = distance;
      this.chance = chance;
      this.convertable = convertable;
      this.convert = convert;
      this.offsets = offsets;
   }

   @Override
   public double distanceFromOthers() {
      return this.distance;
   }

   @Override
   public boolean onPlace(Level level, BlockPos pos, FeaturePlacementRitual placementRitual, RitualBrazierTile brazierTile) {
      BlockState state = level.m_8055_(pos);
      if ((double)level.f_46441_.m_188501_() < this.chance && this.convertable.apply(state)) {
         level.m_46597_(pos, this.convert.apply(state));
         level.m_5594_(null, pos, state.m_60827_().m_56777_(), SoundSource.BLOCKS, 1.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public String getFeatureName() {
      return "convert_blockstate";
   }

   @Override
   public Pair<BlockPos, BlockPos> getCustomOffsets() {
      return this.offsets;
   }
}
