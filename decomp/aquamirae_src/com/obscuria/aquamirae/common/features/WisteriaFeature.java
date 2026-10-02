package com.obscuria.aquamirae.common.features;

import com.obscuria.aquamirae.common.blocks.WisteriaNiveisBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WisteriaFeature extends Feature<NoneFeatureConfiguration> {
   public static WisteriaFeature FEATURE = null;
   public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_FEATURE = null;
   public static Holder<PlacedFeature> PLACED_FEATURE = null;

   public WisteriaFeature() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public static Feature<?> feature() {
      FEATURE = new WisteriaFeature();
      CONFIGURED_FEATURE = FeatureUtils.m_206488_("aquamirae:wisteria", FEATURE, FeatureConfiguration.f_67737_);
      PLACED_FEATURE = PlacementUtils.m_206509_("aquamirae:wisteria", CONFIGURED_FEATURE, List.of());
      return FEATURE;
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      boolean placed = false;
      int count = context.m_225041_().m_216339_(4, 12);

      for (int i = 0; i <= count; i++) {
         int x = (int)((double)context.m_159777_().m_123341_() + context.m_225041_().m_216328_(0.0, 5.0));
         int z = (int)((double)context.m_159777_().m_123343_() + context.m_225041_().m_216328_(0.0, 5.0));
         BlockPos pos = new BlockPos(x, context.m_159774_().m_6924_(Types.WORLD_SURFACE_WG, x, z), z);
         if (((WisteriaNiveisBlock)AquamiraeBlocks.WISTERIA_NIVEIS.get()).canBePlacedOn(context.m_159774_(), pos.m_7495_())) {
            context.m_159774_()
               .m_7731_(
                  pos, (BlockState)((Block)AquamiraeBlocks.WISTERIA_NIVEIS.get()).m_49966_().m_61124_(BlockStateProperties.f_61401_, DoubleBlockHalf.LOWER), 3
               );
            context.m_159774_()
               .m_7731_(
                  pos.m_7494_(),
                  (BlockState)((Block)AquamiraeBlocks.WISTERIA_NIVEIS.get()).m_49966_().m_61124_(BlockStateProperties.f_61401_, DoubleBlockHalf.UPPER),
                  3
               );
            placed = true;
         }
      }

      return placed;
   }
}
