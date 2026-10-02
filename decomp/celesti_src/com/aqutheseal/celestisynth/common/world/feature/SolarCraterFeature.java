package com.aqutheseal.celestisynth.common.world.feature;

import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SolarCraterFeature extends Feature<NoneFeatureConfiguration> {
   public SolarCraterFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldgenlevel = context.m_159774_();
      RandomSource randomsource = context.m_225041_();
      BlockPos blockpos = context.m_159777_();
      int[] layerRadii = new int[]{7, 7, 6, 6, 4, 2};

      for (int i = 0; i < layerRadii.length; i++) {
         int radiuss = layerRadii[i] + randomsource.m_188503_(5);
         this.createLayer(radiuss, null, worldgenlevel, randomsource, blockpos.m_6625_(i + 1));
         if (i != layerRadii.length - 1) {
            this.createLayer(radiuss - 1, Blocks.f_50016_.m_49966_(), worldgenlevel, randomsource, blockpos.m_6625_(i + 1));
         }

         this.createCrystalLayer(radiuss, worldgenlevel, randomsource, blockpos.m_6625_(i));
      }

      return true;
   }

   public void createLayer(int radius, @Nullable BlockState block, WorldGenLevel level, RandomSource random, BlockPos origin) {
      for (int sx = -radius; sx <= radius; sx++) {
         for (int sz = -radius; sz <= radius; sz++) {
            if (sx * sx + sz * sz <= radius * radius) {
               BlockPos pos = origin.m_7918_(sx, 0, sz);
               if (!level.m_8055_(pos).m_60795_()) {
                  if (block == null) {
                     int randomInt = random.m_188503_(3);
                     BlockState blockToPut;
                     if (randomInt == 0) {
                        blockToPut = Blocks.f_50141_.m_49966_();
                     } else if (randomInt == 1) {
                        blockToPut = level.m_8055_(pos);
                     } else {
                        blockToPut = Blocks.f_49998_.m_49966_();
                     }

                     this.m_5974_(level, pos, blockToPut);
                  } else {
                     this.m_5974_(level, pos, block);
                  }
               }
            }
         }
      }
   }

   public void createCrystalLayer(int radius, WorldGenLevel level, RandomSource random, BlockPos origin) {
      for (int sx = -radius; sx <= radius; sx++) {
         for (int sz = -radius; sz <= radius; sz++) {
            if (sx * sx + sz * sz <= radius * radius) {
               BlockPos pos = origin.m_7918_(sx, 0, sz);
               if (!level.m_8055_(pos.m_7495_()).m_60795_() && level.m_8055_(pos).m_60795_()) {
                  int randomInt = random.m_188503_(5);
                  if (randomInt == 0) {
                     this.m_5974_(level, pos, ((Block)CSBlocks.SOLAR_CRYSTAL.get()).m_49966_());
                  }
               }
            }
         }
      }
   }
}
