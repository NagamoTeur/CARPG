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
import net.thirdlife.iterrpg.procedures.GenericDungeonsConditionProcedure;

public class VaseRoomMediumFeature extends Feature<NoneFeatureConfiguration> {
   public static VaseRoomMediumFeature FEATURE = null;
   public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_FEATURE = null;
   public static Holder<PlacedFeature> PLACED_FEATURE = null;
   private final Set<ResourceKey<Level>> generate_dimensions = Set.of(Level.f_46428_);
   private StructureTemplate template = null;

   public static Feature<?> feature() {
      FEATURE = new VaseRoomMediumFeature();
      CONFIGURED_FEATURE = FeatureUtils.m_206488_("iter_rpg:vase_room_medium", FEATURE, FeatureConfiguration.f_67737_);
      PLACED_FEATURE = PlacementUtils.m_206509_("iter_rpg:vase_room_medium", CONFIGURED_FEATURE, List.of());
      return FEATURE;
   }

   public VaseRoomMediumFeature() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      if (!this.generate_dimensions.contains(context.m_159774_().m_6018_().m_46472_())) {
         return false;
      } else {
         if (this.template == null) {
            this.template = context.m_159774_().m_6018_().m_215082_().m_230359_(new ResourceLocation("iter_rpg", "vase_room_medium"));
         }

         if (this.template == null) {
            return false;
         } else {
            boolean anyPlaced = false;
            if (context.m_225041_().m_188503_(1000000) + 1 <= 7500) {
               int count = context.m_225041_().m_188503_(1) + 1;

               for (int a = 0; a < count; a++) {
                  int i = context.m_159777_().m_123341_() + context.m_225041_().m_188503_(16);
                  int k = context.m_159777_().m_123343_() + context.m_225041_().m_188503_(16);
                  int j = context.m_159774_().m_6924_(Types.OCEAN_FLOOR_WG, i, k);
                  j = Mth.m_216271_(context.m_225041_(), 8 + context.m_159774_().m_141937_(), Math.max(j, 9 + context.m_159774_().m_141937_()));
                  BlockPos spawnTo = new BlockPos(i + -3, j + 0, k + -3);
                  WorldGenLevel world = context.m_159774_();
                  int x = spawnTo.m_123341_();
                  int y = spawnTo.m_123342_();
                  int z = spawnTo.m_123343_();
                  if (GenericDungeonsConditionProcedure.execute(world, (double)x, (double)y, (double)z)
                     && this.template
                        .m_230328_(
                           context.m_159774_(),
                           spawnTo,
                           spawnTo,
                           new StructurePlaceSettings()
                              .m_74377_(Mirror.values()[context.m_225041_().m_188503_(2)])
                              .m_74379_(Rotation.values()[context.m_225041_().m_188503_(3)])
                              .m_230324_(context.m_225041_())
                              .m_74383_(BlockIgnoreProcessor.f_74046_)
                              .m_74392_(false),
                           context.m_225041_(),
                           2
                        )) {
                     anyPlaced = true;
                  }
               }
            }

            return anyPlaced;
         }
      }
   }
}
