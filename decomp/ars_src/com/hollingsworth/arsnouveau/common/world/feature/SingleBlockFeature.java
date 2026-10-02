package com.hollingsworth.arsnouveau.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public abstract class SingleBlockFeature extends Feature<BlockStateConfiguration> {
   public SingleBlockFeature(Codec<BlockStateConfiguration> p_i231953_1_) {
      super(p_i231953_1_);
   }

   public boolean place(BlockStateConfiguration config, WorldGenLevel seed, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos pos) {
      while (pos.m_123342_() > 3 && (!seed.m_46859_(pos) || seed.m_46859_(pos.m_7495_()) || seed.m_46801_(pos))) {
         pos = pos.m_7495_();
      }

      if (pos.m_123342_() <= 3) {
         return false;
      } else {
         seed.m_7731_(pos, config.f_67547_, 4);
         this.onStatePlace(seed, chunkGenerator, rand, pos, config);
         return true;
      }
   }

   public abstract void onStatePlace(WorldGenLevel var1, ChunkGenerator var2, RandomSource var3, BlockPos var4, BlockStateConfiguration var5);
}
