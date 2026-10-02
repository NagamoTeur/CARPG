package net.thirdlife.iterrpg.world.features;

import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.thirdlife.iterrpg.procedures.SorrowSpireConfigConditionProcedure;
import net.thirdlife.iterrpg.procedures.SorrowSpirePrepareProcedure;

public class SorrowSpireFeature extends Feature<NoneFeatureConfiguration> {
   public static SorrowSpireFeature FEATURE = null;
   public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_FEATURE = null;
   public static Holder<PlacedFeature> PLACED_FEATURE = null;
   private final Set<ResourceKey<Level>> generate_dimensions = Set.of(Level.f_46429_);
   private StructureTemplate template = null;

   public static Feature<?> feature() {
      FEATURE = new SorrowSpireFeature();
      CONFIGURED_FEATURE = FeatureUtils.m_206488_("iter_rpg:sorrow_spire", FEATURE, FeatureConfiguration.f_67737_);
      PLACED_FEATURE = PlacementUtils.m_206509_("iter_rpg:sorrow_spire", CONFIGURED_FEATURE, List.of());
      return FEATURE;
   }

   public SorrowSpireFeature() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      if (!this.generate_dimensions.contains(context.m_159774_().m_6018_().m_46472_())) {
         return false;
      } else {
         if (this.template == null) {
            this.template = context.m_159774_().m_6018_().m_215082_().m_230359_(new ResourceLocation("iter_rpg", "single_vase"));
         }

         if (this.template == null) {
            return false;
         } else {
            boolean anyPlaced = false;
            if (context.m_225041_().m_188503_(1000000) + 1 <= 1000) {
               int count = context.m_225041_().m_188503_(1) + 1;

               for (int a = 0; a < count; a++) {
                  int i = context.m_159777_().m_123341_() + context.m_225041_().m_188503_(16);
                  int k = context.m_159777_().m_123343_() + context.m_225041_().m_188503_(16);
                  int j = context.m_159774_().m_6924_(Types.WORLD_SURFACE_WG, i, k);
                  j = Mth.m_216271_(context.m_225041_(), 8 + context.m_159774_().m_141937_(), Math.max(j, 9 + context.m_159774_().m_141937_()));
                  BlockPos spawnTo = new BlockPos(i + 0, j + 0, k + 0);
                  WorldGenLevel world = context.m_159774_();
                  int x = spawnTo.m_123341_();
                  int y = spawnTo.m_123342_();
                  int z = spawnTo.m_123343_();
                  if (SorrowSpireConfigConditionProcedure.execute()
                     && this.template
                        .m_230328_(
                           context.m_159774_(),
                           spawnTo,
                           spawnTo,
                           new StructurePlaceSettings()
                              .m_74377_(Mirror.NONE)
                              .m_74379_(Rotation.NONE)
                              .m_230324_(context.m_225041_())
                              .m_74383_(BlockIgnoreProcessor.f_74048_)
                              .m_74392_(false),
                           context.m_225041_(),
                           2
                        )) {
                     SorrowSpirePrepareProcedure.execute(world, (double)x, (double)z);
                     anyPlaced = true;
                  }
               }
            }

            return anyPlaced;
         }
      }
   }
}
