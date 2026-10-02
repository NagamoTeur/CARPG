package com.aqutheseal.celestisynth.common.world.feature;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class ZephyrDepositFeature extends Feature<NoneFeatureConfiguration> {
   public ZephyrDepositFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldgenlevel = context.m_159774_();
      RandomSource randomsource = context.m_225041_();
      BlockPos pos = context.m_159777_();
      boolean large = randomsource.m_188503_(5) == 0;
      int tipMin = (int)((double)(large ? 25 : 10) * 0.6);
      int tipRand = (int)((double)(large ? 35 : 20) * 0.3);
      int radiusMin = large ? 5 : 3;
      int radiusRand = large ? 3 : 1;
      int tip = tipMin + worldgenlevel.m_213780_().m_188503_(tipRand);
      int topX = worldgenlevel.m_213780_().m_188503_(tip) - tip / 2;
      int topZ = worldgenlevel.m_213780_().m_188503_(tip) - tip / 2;
      int radius = radiusMin + worldgenlevel.m_213780_().m_188503_(radiusRand);
      Vec3 to = new Vec3((double)(pos.m_123341_() + topX), (double)(pos.m_123342_() + tip), (double)(pos.m_123343_() + topZ));

      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            double fromCenter = Math.sqrt(Math.pow((double)x, 2.0) + Math.pow((double)z, 2.0));
            if (fromCenter <= (double)radius) {
               Vec3 from = new Vec3((double)(pos.m_123341_() + x), (double)pos.m_123342_(), (double)(pos.m_123343_() + z));
               if (!worldgenlevel.m_8055_(this.posFromVec(from).m_7495_()).m_60795_()) {
                  Vec3 per = to.m_82546_(from).m_82541_();
                  Vec3 current = from.m_82520_(0.0, 0.0, 0.0);
                  double distance = from.m_82554_(to);

                  for (double i = 0.0; i < distance; i++) {
                     BlockPos targetPos = this.posFromVec(current);
                     if (i > 0.0 && i < distance / 1.3) {
                        int roll = randomsource.m_188503_(3);
                        if (roll == 0) {
                           worldgenlevel.m_7731_(targetPos, Blocks.f_152550_.m_49966_(), 3);
                        } else if (roll == 1) {
                           worldgenlevel.m_7731_(targetPos, Blocks.f_50069_.m_49966_(), 3);
                        } else if (roll == 2) {
                           worldgenlevel.m_7731_(targetPos, ((Block)CSBlocks.ZEPHYR_DEPOSIT.get()).m_49966_(), 3);
                        }
                     } else {
                        worldgenlevel.m_7731_(targetPos, ((Block)CSBlocks.ZEPHYR_DEPOSIT.get()).m_49966_(), 3);
                     }

                     if (i <= 0.0) {
                        for (BlockPos getFromTarget = targetPos; worldgenlevel.m_46859_(getFromTarget.m_7495_()); getFromTarget = getFromTarget.m_7495_()) {
                           if (randomsource.m_188499_()) {
                              worldgenlevel.m_7731_(getFromTarget, Blocks.f_50069_.m_49966_(), 3);
                           } else {
                              worldgenlevel.m_7731_(getFromTarget, Blocks.f_152550_.m_49966_(), 3);
                           }
                        }
                     }

                     current = current.m_82549_(per);
                  }
               }
            }
         }
      }

      Celestisynth.LOGGER.info("DEPOSIT GENERATED AT: " + pos.m_123341_() + " " + pos.m_123342_() + " " + pos.m_123343_());
      return true;
   }

   public BlockPos posFromVec(Vec3 vec3) {
      return new BlockPos((int)vec3.m_7096_(), (int)vec3.m_7098_(), (int)vec3.m_7094_());
   }
}
