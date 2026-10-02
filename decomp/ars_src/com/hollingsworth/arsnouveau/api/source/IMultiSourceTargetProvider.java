package com.hollingsworth.arsnouveau.api.source;

import java.util.List;
import net.minecraft.core.BlockPos;

public interface IMultiSourceTargetProvider {
   List<BlockPos> getFromList();

   List<BlockPos> getToList();
}
