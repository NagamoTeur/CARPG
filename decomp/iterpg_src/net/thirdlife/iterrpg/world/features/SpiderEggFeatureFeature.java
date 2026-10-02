package net.thirdlife.iterrpg.world.features;

import java.util.Set;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.thirdlife.iterrpg.procedures.SpiderEggsConfigConditionProcedure;

public class SpiderEggFeatureFeature extends SimpleBlockFeature {
   private final Set<ResourceKey<Level>> generateDimensions = Set.of(Level.f_46428_);

   public SpiderEggFeatureFeature() {
      super(SimpleBlockConfiguration.f_68068_);
   }

   public boolean m_142674_(FeaturePlaceContext<SimpleBlockConfiguration> context) {
      WorldGenLevel world = context.m_159774_();
      if (!this.generateDimensions.contains(world.m_6018_().m_46472_())) {
         return false;
      } else {
         int x = context.m_159777_().m_123341_();
         int y = context.m_159777_().m_123342_();
         int z = context.m_159777_().m_123343_();
         return !SpiderEggsConfigConditionProcedure.execute() ? false : super.m_142674_(context);
      }
   }
}
