package com.aqutheseal.celestisynth.common.world.feature;

import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public class LunarCraterFeature extends Feature<NoneFeatureConfiguration> {
   public LunarCraterFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldgenlevel = context.m_159774_();
      RandomSource randomsource = context.m_225041_();
      BlockPos blockpos = context.m_159777_();
      int[] layerRadii = new int[]{7, 7, 6, 6, 4, 2};

      for (int i = 0; i < layerRadii.length; i++) {
         int radiuss = layerRadii[i] + 3 + randomsource.m_188503_(7);
         this.createLayer(radiuss, null, worldgenlevel, randomsource, blockpos.m_6625_(i + 1));
         if (i != layerRadii.length - 1) {
            BlockState filler = context.m_159777_().m_123342_() > -50 ? Fluids.f_76193_.m_76145_().m_76188_() : Blocks.f_50016_.m_49966_();
            this.createLayer(radiuss - 1, filler, worldgenlevel, randomsource, blockpos.m_6625_(i + 1));
         }
      }

      return true;
   }

   public void createLayer(int radius, @Nullable BlockState filler, WorldGenLevel level, RandomSource random, BlockPos origin) {
      for (int sx = -radius; sx <= radius; sx++) {
         for (int sz = -radius; sz <= radius; sz++) {
            if (sx * sx + sz * sz <= radius * radius) {
               BlockPos pos = origin.m_7918_(sx, 0, sz);
               int randomInt = random.m_188503_(4);
               BlockState blockToPut;
               if (randomInt == 0) {
                  blockToPut = level.m_8055_(pos) == Blocks.f_152550_.m_49966_() ? Blocks.f_152472_.m_49966_() : Blocks.f_50059_.m_49966_();
               } else if (randomInt == 1) {
                  blockToPut = level.m_8055_(pos);
               } else if (randomInt == 2) {
                  blockToPut = ((Block)CSBlocks.LUNAR_STONE.get()).m_49966_();
               } else {
                  blockToPut = Blocks.f_50386_.m_49966_();
               }

               if (!level.m_8055_(pos).m_60795_()) {
                  if (filler == null) {
                     this.m_5974_(level, pos, blockToPut);
                  } else {
                     if (!level.m_8055_(pos.m_7495_()).m_60795_()) {
                        this.m_5974_(level, pos, filler);
                     } else {
                        this.m_5974_(level, pos, blockToPut);
                     }

                     for (Direction direction : Plane.HORIZONTAL) {
                        if (level.m_8055_(pos.m_121945_(direction)).m_60795_()) {
                           this.m_5974_(level, pos, blockToPut);
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
