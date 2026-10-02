package shadows.apotheosis.adventure.gen;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.spawner.RandomSpawnerManager;
import shadows.apotheosis.adventure.spawner.SpawnerItem;

public class RogueSpawnerFeature extends Feature<NoneFeatureConfiguration> {
   public static final RogueSpawnerFeature INSTANCE = new RogueSpawnerFeature();
   public static final Predicate<BlockState> STONE_TEST = b -> OreFeatures.f_195071_.m_213865_(b, null);

   public RogueSpawnerFeature() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel world = ctx.m_159774_();
      if (!AdventureConfig.canGenerateIn(world)) {
         return false;
      } else {
         BlockPos pos = ctx.m_159777_();
         RandomSource rand = ctx.m_225041_();
         if (rand.m_188503_(100) == 0) {
            BlockState state = world.m_8055_(pos);
            BlockState downState = world.m_8055_(pos.m_7495_());
            BlockState upState = world.m_8055_(pos.m_7494_());
            if (STONE_TEST.test(downState) && upState.m_60795_() && (state.m_60795_() || STONE_TEST.test(state))) {
               SpawnerItem item = (SpawnerItem)RandomSpawnerManager.INSTANCE.getRandomItem(rand);
               if (item == null) {
                  return false;
               }

               item.place(world, pos, rand);
               AdventureModule.debugLog(pos, "Rogue Spawner - " + item.getId());
               return true;
            }
         }

         return false;
      }
   }
}
