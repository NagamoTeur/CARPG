package shadows.apotheosis.adventure.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.boss.BossSpawnerBlock;

public class BossDungeonFeature extends Feature<NoneFeatureConfiguration> {
   private static final BlockState CAVE_AIR = Blocks.f_50627_.m_49966_();
   private static final BlockState BRICK = Blocks.f_50222_.m_49966_();
   private static final BlockState MOSSY_BRICK = Blocks.f_50223_.m_49966_();
   private static final BlockState CRACKED_BRICK = Blocks.f_50224_.m_49966_();
   private static final BlockState[] BRICKS = new BlockState[]{BRICK, MOSSY_BRICK, CRACKED_BRICK};
   public static final BossDungeonFeature INSTANCE = new BossDungeonFeature();

   public BossDungeonFeature() {
      super(NoneFeatureConfiguration.f_67815_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
      WorldGenLevel world = ctx.m_159774_();
      if (!AdventureConfig.canGenerateIn(world)) {
         return false;
      } else {
         BlockPos pos = ctx.m_159777_();
         RandomSource rand = ctx.m_225041_();
         int xRadius = 3 + rand.m_188503_(3);
         int floor = -1;
         int roof = 4;
         int zRadius = 3 + rand.m_188503_(3);
         int doors = 0;
         BlockState[][][] states = new BlockState[xRadius * 2 + 1][6][zRadius * 2 + 1];

         for (int x = -xRadius; x <= xRadius; x++) {
            for (int y = floor; y <= roof; y++) {
               for (int z = -zRadius; z <= zRadius; z++) {
                  BlockPos blockpos = pos.m_7918_(x, y, z);
                  BlockState state = world.m_8055_(blockpos);
                  Material material = state.m_60767_();
                  boolean flag = material.m_76333_();
                  if (y == floor && !flag || y == roof && !flag) {
                     return false;
                  }

                  if ((x == -xRadius || x == xRadius || z == -zRadius || z == zRadius)
                     && y == 1
                     && state.m_60795_()
                     && states[x + xRadius][y - 1 + 1][z + zRadius].m_60795_()) {
                     doors++;
                  }

                  states[x + xRadius][y + 1][z + zRadius] = state;
               }
            }
         }

         if (doors >= 1 && doors <= 5) {
            for (int x = -xRadius; x <= xRadius; x++) {
               for (int y = roof - 1; y >= floor; y--) {
                  for (int z = -zRadius; z <= zRadius; z++) {
                     BlockPos blockposx = pos.m_7918_(x, y, z);
                     BlockState statex = states[x + xRadius][y + 1][z + zRadius];
                     if (x != -xRadius && y != floor && z != -zRadius && x != xRadius && y != roof && z != zRadius) {
                        if (!statex.m_60713_(Blocks.f_50087_)) {
                           world.m_7731_(blockposx, CAVE_AIR, 2);
                        }
                     } else if (y > floor && !states[x + xRadius][y - 1 + 1][z + zRadius].m_60767_().m_76333_()) {
                        world.m_7731_(blockposx, CAVE_AIR, 2);
                     } else if (statex.m_60767_().m_76333_() && !statex.m_60713_(Blocks.f_50087_)) {
                        if (y == floor) {
                           world.m_7731_(blockposx, BRICKS[rand.m_188503_(3)], 2);
                        } else {
                           world.m_7731_(blockposx, rand.m_188499_() ? BRICK : BRICKS[rand.m_188503_(3)], 2);
                        }
                     }
                  }
               }
            }

            int xChestRadius = xRadius - 1;
            int zChestRadius = zRadius - 1;

            for (int chests = 0; chests < 2; chests++) {
               for (int attempts = 0; attempts < 3; attempts++) {
                  boolean wall = rand.m_188499_();
                  int x = wall ? (rand.m_188499_() ? -xChestRadius : xChestRadius) : rand.m_188503_(xChestRadius * 2 + 1) - xChestRadius;
                  int y = 0;
                  int zx = !wall ? (rand.m_188499_() ? -zChestRadius : zChestRadius) : rand.m_188503_(zChestRadius * 2 + 1) - zChestRadius;
                  BlockPos blockpos2 = pos.m_7918_(x, y, zx);
                  if (world.m_8055_(blockpos2).m_60795_()) {
                     int nearbySolids = 0;

                     for (Direction dir : Plane.HORIZONTAL) {
                        if (world.m_8055_(blockpos2.m_121945_(dir)).m_60767_().m_76333_()) {
                           nearbySolids++;
                        }
                     }

                     if (nearbySolids == 1) {
                        world.m_7731_(blockpos2, StructurePiece.m_73407_(world, blockpos2, Blocks.f_50087_.m_49966_()), 2);
                        RandomizableContainerBlockEntity.m_222766_(world, rand, blockpos2, BuiltInLootTables.f_78742_);
                        break;
                     }
                  }
               }
            }

            world.m_7731_(pos, ((BossSpawnerBlock)Apoth.Blocks.BOSS_SPAWNER.get()).m_49966_(), 2);
            AdventureModule.debugLog(pos, "Boss Dungeon");
            return true;
         } else {
            return false;
         }
      }
   }
}
