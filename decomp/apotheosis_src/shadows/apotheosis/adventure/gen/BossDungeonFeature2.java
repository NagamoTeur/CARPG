package shadows.apotheosis.adventure.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.server.ServerLifecycleHooks;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.boss.BossSpawnerBlock;

public class BossDungeonFeature2 extends Feature<NoneFeatureConfiguration> {
   public static final ResourceLocation TEMPLATE_ID = new ResourceLocation("apotheosis", "boss_1");
   public static final BossDungeonFeature2 INSTANCE = new BossDungeonFeature2();
   protected static int xRadius = 4;
   protected static int floor = -1;
   protected static int roof = 3;
   protected static int roofTop = 6;
   protected static int zRadius = 4;

   public BossDungeonFeature2() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel world = ctx.m_159774_();
      if (!AdventureConfig.canGenerateIn(world)) {
         return false;
      } else {
         BlockPos pos = ctx.m_159777_();
         RandomSource rand = ctx.m_225041_();
         BlockState[][][] states = new BlockState[9][8][9];
         int doors = 0;

         for (int x = -xRadius; x <= xRadius; x++) {
            for (int y = floor; y <= roofTop; y++) {
               for (int z = -zRadius; z <= zRadius; z++) {
                  BlockPos blockpos = pos.m_7918_(x, y, z);
                  BlockState state = world.m_8055_(blockpos);
                  Material material = state.m_60767_();
                  boolean flag = material.m_76333_();
                  if (y == floor && !flag || y == roof && !flag) {
                     return false;
                  }

                  if (y == roof + 1 && Math.abs(x) < xRadius && Math.abs(z) < zRadius && !flag) {
                     return false;
                  }

                  if (isDoorSpace(x, z) && y == 1 && state.m_60795_() && states[x + xRadius][y - 1 + 1][z + zRadius].m_60795_()) {
                     doors++;
                  }

                  states[x + xRadius][y + 1][z + zRadius] = state;
               }
            }
         }

         if (doors >= 3) {
            StructureTemplate template = (StructureTemplate)ServerLifecycleHooks.getCurrentServer().m_236738_().m_230407_(TEMPLATE_ID).get();
            template.m_230328_(world, pos.m_7918_(-4, -1, -4), pos.m_7918_(-4, -1, -4), new StructurePlaceSettings(), rand, 4);
            boolean rand1 = rand.m_188499_();
            boolean rand2 = rand.m_188499_();
            BlockPos chest1 = pos.m_7918_(rand1 ? xRadius - 1 : -xRadius + 1, 0, rand2 ? zRadius - 1 : -zRadius + 1);
            BlockPos chest2 = pos.m_7918_(!rand1 ? xRadius - 1 : -xRadius + 1, 0, !rand2 ? zRadius - 1 : -zRadius + 1);
            world.m_7731_(chest1, StructurePiece.m_73407_(world, chest1, Blocks.f_50087_.m_49966_()), 2);
            RandomizableContainerBlockEntity.m_222766_(world, rand, chest1, BuiltInLootTables.f_78742_);
            world.m_7731_(chest2, StructurePiece.m_73407_(world, chest2, Blocks.f_50087_.m_49966_()), 2);
            RandomizableContainerBlockEntity.m_222766_(world, rand, chest2, BuiltInLootTables.f_78742_);
            world.m_7731_(pos, ((BossSpawnerBlock)Apoth.Blocks.BOSS_SPAWNER.get()).m_49966_(), 2);
            AdventureModule.debugLog(pos, "Boss Dungeon (Variant 2)");
            return true;
         } else {
            return false;
         }
      }
   }

   static boolean isDoorSpace(int x, int z) {
      return Math.abs(z) == zRadius && x >= -1 && x <= 1 || Math.abs(x) == xRadius && z >= -1 && z <= 1;
   }
}
