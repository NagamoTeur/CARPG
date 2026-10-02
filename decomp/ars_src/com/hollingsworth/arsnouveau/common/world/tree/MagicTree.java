package com.hollingsworth.arsnouveau.common.world.tree;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class MagicTree extends AbstractTreeGrower {
   Supplier<Holder<ConfiguredFeature<TreeConfiguration, ?>>> configConfiguredFeature;

   public MagicTree(Supplier<Holder<ConfiguredFeature<TreeConfiguration, ?>>> configConfiguredFeature) {
      this.configConfiguredFeature = configConfiguredFeature;
   }

   protected Holder<ConfiguredFeature<TreeConfiguration, ?>> m_213888_(RandomSource randomIn, boolean largeHive) {
      return this.configConfiguredFeature.get();
   }
}
