package net.thirdlife.iterrpg.world.features;

import java.util.Set;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;

public class AbyssquartzCrystalFeature extends BlockColumnFeature {
   private final Set<ResourceKey<Level>> generateDimensions = Set.of(Level.f_46428_);

   public AbyssquartzCrystalFeature() {
      super(BlockColumnConfiguration.f_191206_);
   }

   public boolean m_142674_(FeaturePlaceContext<BlockColumnConfiguration> context) {
      WorldGenLevel world = context.m_159774_();
      return !this.generateDimensions.contains(world.m_6018_().m_46472_()) ? false : super.m_142674_(context);
   }
}
