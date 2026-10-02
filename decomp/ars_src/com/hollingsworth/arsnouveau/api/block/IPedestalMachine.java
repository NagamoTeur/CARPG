package com.hollingsworth.arsnouveau.api.block;

import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public interface IPedestalMachine {
   default List<BlockPos> pedestalList(BlockPos blockPos, int offset, @NotNull Level level) {
      ArrayList<BlockPos> posList = new ArrayList<>();

      for (BlockPos b : BlockPos.m_121940_(blockPos.m_7918_(offset, -offset, offset), blockPos.m_7918_(-offset, offset, -offset))) {
         if (level.m_7702_(b) instanceof ArcanePedestalTile tile) {
            posList.add(b.m_7949_());
         }
      }

      return posList;
   }

   void lightPedestal(Level var1);
}
