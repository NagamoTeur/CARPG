package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.ConfiguredFeatureHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw.MowzieJigsawManager;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.Level;

public class MonasteryStructure extends MowzieStructure {
   public static final Set<String> MUST_CONNECT_POOLS = Set.of("mowziesmobs:monastery/path_pool", "mowziesmobs:monastery/path_connector_pool");
   public static final Set<String> REPLACE_POOLS = Set.of("mowziesmobs:monastery/path_pool");
   public static final String STRAIGHT_POOL = "mowziesmobs:monastery/dead_end_connect_pool";
   private static final Lazy<List<SpawnerData>> STRUCTURE_MONSTERS = Lazy.of(
      () -> ImmutableList.of(new SpawnerData(EntityType.f_20459_, 100, 4, 9), new SpawnerData(EntityType.f_20493_, 100, 4, 9))
   );
   private static final Lazy<List<SpawnerData>> STRUCTURE_CREATURES = Lazy.of(
      () -> ImmutableList.of(new SpawnerData(EntityType.f_20520_, 30, 10, 15), new SpawnerData(EntityType.f_20517_, 100, 1, 2))
   );

   public MonasteryStructure(StructureSettings settings) {
      super(settings, ConfigHandler.COMMON.MOBS.SCULPTOR.generationConfig, ConfiguredFeatureHandler.SCULPTOR_BIOMES, true, true, true);
   }

   @Override
   public Decoration m_226619_() {
      return Decoration.UNDERGROUND_DECORATION;
   }

   public static Optional<GenerationStub> createPiecesGenerator(Predicate<GenerationContext> canGeneratePredicate, GenerationContext context) {
      if (!canGeneratePredicate.test(context)) {
         return Optional.empty();
      } else {
         GenerationContext newContext = new GenerationContext(
            context.f_226621_(),
            context.f_226622_(),
            context.f_226623_(),
            context.f_226624_(),
            context.f_226625_(),
            context.f_226626_(),
            context.f_226627_(),
            context.f_226628_(),
            context.f_226629_(),
            context.f_226630_()
         );
         BlockPos blockpos = context.f_226628_().m_151394_(0);
         Optional<GenerationStub> structurePiecesGenerator = MowzieJigsawManager.addPieces(
            newContext,
            Holder.m_205709_(
               (StructureTemplatePool)context.f_226621_().m_206191_(Registry.f_122884_).m_7745_(new ResourceLocation("mowziesmobs", "monastery/start_pool"))
            ),
            blockpos,
            false,
            true,
            140,
            "mowziesmobs:monastery/path",
            "mowziesmobs:monastery/interior",
            MUST_CONNECT_POOLS,
            REPLACE_POOLS,
            "mowziesmobs:monastery/dead_end_connect_pool",
            23
         );
         if (structurePiecesGenerator.isPresent()) {
            MowziesMobs.LOGGER.log(Level.DEBUG, "Monastery at " + blockpos);
         }

         return structurePiecesGenerator;
      }
   }

   @Override
   public Optional<GenerationStub> m_214086_(GenerationContext context) {
      return createPiecesGenerator(t -> this.checkLocation(t), context);
   }

   public StructureType<?> m_213658_() {
      return null;
   }
}
