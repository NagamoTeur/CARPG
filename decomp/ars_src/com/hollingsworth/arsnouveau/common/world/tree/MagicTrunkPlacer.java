package com.hollingsworth.arsnouveau.common.world.tree;

import com.google.common.collect.Lists;
import com.hollingsworth.arsnouveau.common.block.ArchfruitPod;
import com.hollingsworth.arsnouveau.common.block.SconceBlock;
import com.hollingsworth.arsnouveau.setup.ModSetup;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.ForgeRegistries;

public class MagicTrunkPlacer extends TrunkPlacer {
   boolean isWorldGen;
   ResourceLocation podID;
   public static final Codec<MagicTrunkPlacer> CODEC = RecordCodecBuilder.create(
      builder -> builder.group(
               Codec.intRange(0, 32).fieldOf("base_height").forGetter(placer -> placer.f_70263_),
               Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(placer -> placer.f_70264_),
               Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter(placer -> placer.f_70265_),
               Codec.BOOL.fieldOf("isWorldGen").forGetter(placer -> placer.isWorldGen),
               Codec.STRING.fieldOf("podID").forGetter(placer -> placer.podID.toString())
            )
            .apply(builder, MagicTrunkPlacer::new)
   );

   public MagicTrunkPlacer(int baseHeight, int height_rand_a, int height_rand_b) {
      super(baseHeight, height_rand_a, height_rand_b);
   }

   public MagicTrunkPlacer(int baseHeight, int height_rand_a, int height_rand_b, boolean isWorldGen, String podName) {
      this(baseHeight, height_rand_a, height_rand_b);
      this.isWorldGen = isWorldGen;
      this.podID = new ResourceLocation(podName);
   }

   protected TrunkPlacerType<?> m_7362_() {
      return (TrunkPlacerType<?>)ModSetup.MAGIC_TRUNK_PLACER.get();
   }

   protected static void setDirtAt(
      LevelSimulatedReader pLevel,
      BiConsumer<BlockPos, BlockState> pBlockSetter,
      RandomSource pRandom,
      BlockPos pPos,
      TreeConfiguration pConfig,
      boolean isTreeOrigin
   ) {
      if (isTreeOrigin || pLevel.m_7433_(pPos, state -> state.m_204336_(BlockTags.f_144274_) || state.m_60713_(Blocks.f_50093_))) {
         TrunkPlacer.m_226169_(pLevel, pBlockSetter, pRandom, pPos, pConfig);
      }
   }

   public List<FoliageAttachment> m_213934_(
      LevelSimulatedReader world,
      BiConsumer<BlockPos, BlockState> consumer,
      RandomSource rand,
      int foliageHeight,
      BlockPos pos,
      TreeConfiguration baseTreeFeatureConfig
   ) {
      List<FoliageAttachment> list = Lists.newArrayList();
      BlockPos blockpos = pos.m_7495_();
      setDirtAt(world, consumer, rand, blockpos, baseTreeFeatureConfig, true);
      setDirtAt(world, consumer, rand, blockpos.m_122029_(), baseTreeFeatureConfig, false);
      setDirtAt(world, consumer, rand, blockpos.m_122019_(), baseTreeFeatureConfig, false);
      setDirtAt(world, consumer, rand, blockpos.m_122019_().m_122029_(), baseTreeFeatureConfig, false);
      int x = pos.m_123341_();
      int y = pos.m_123342_();
      int z = pos.m_123343_();
      int yOffset = y + foliageHeight - 1;
      int numBranches = 0;
      int lastBranch = 0;
      double branchChance = 0.5;
      boolean northB = (double)rand.m_188501_() >= branchChance;
      boolean southB = (double)rand.m_188501_() >= branchChance;
      boolean eastB = (double)rand.m_188501_() >= branchChance;
      boolean westB = (double)rand.m_188501_() >= branchChance;

      for (int i = 0; i < foliageHeight; i++) {
         int j2 = y + i;
         BlockPos blockpos1 = new BlockPos(x, j2, z);
         if (TreeFeature.m_67267_(world, blockpos1)) {
            this.m_226187_(world, consumer, rand, blockpos1, baseTreeFeatureConfig);
            this.m_226187_(world, consumer, rand, blockpos1.m_122029_(), baseTreeFeatureConfig);
            this.m_226187_(world, consumer, rand, blockpos1.m_122019_(), baseTreeFeatureConfig);
            this.m_226187_(world, consumer, rand, blockpos1.m_122029_().m_122019_(), baseTreeFeatureConfig);
         }

         if (i == 0) {
            BlockPos abovePos = pos.m_6630_(i);
            this.addRoots(world, rand, abovePos.m_122024_(), consumer, baseTreeFeatureConfig, new Direction[]{Direction.NORTH, Direction.WEST});
            this.addRoots(world, rand, abovePos.m_122020_(2), consumer, baseTreeFeatureConfig, new Direction[]{Direction.SOUTH, Direction.WEST});
            this.addRoots(world, rand, abovePos.m_122019_().m_122024_(), consumer, baseTreeFeatureConfig, new Direction[]{Direction.WEST});
            this.addRoots(world, rand, abovePos.m_122020_(2).m_122029_(), consumer, baseTreeFeatureConfig, new Direction[]{Direction.EAST, Direction.SOUTH});
            this.addRoots(world, rand, abovePos.m_122030_(2), consumer, baseTreeFeatureConfig, new Direction[]{Direction.EAST, Direction.NORTH});
            this.addRoots(world, rand, abovePos.m_122030_(2).m_122019_(), consumer, baseTreeFeatureConfig, new Direction[]{Direction.EAST});
            this.addRoots(world, rand, abovePos.m_122029_().m_122012_(), consumer, baseTreeFeatureConfig, new Direction[]{Direction.NORTH});
            this.addRoots(world, rand, abovePos.m_122012_(), consumer, baseTreeFeatureConfig, new Direction[]{Direction.NORTH, Direction.EAST});
         }

         if (i > 1 && i > lastBranch) {
            if (northB) {
               this.addBranch(world, pos, i, Direction.NORTH, rand, baseTreeFeatureConfig, consumer);
               lastBranch = i;
               numBranches++;
               northB = false;
            } else if (southB) {
               this.addBranch(world, pos.m_121945_(Direction.SOUTH), i, Direction.SOUTH, rand, baseTreeFeatureConfig, consumer);
               lastBranch = i;
               numBranches++;
               southB = false;
            } else if (eastB) {
               this.addBranch(world, pos.m_121945_(Direction.EAST).m_122019_(), i, Direction.EAST, rand, baseTreeFeatureConfig, consumer);
               lastBranch = i;
               numBranches++;
               eastB = false;
            } else if (westB) {
               this.addBranch(world, pos, i, Direction.WEST, rand, baseTreeFeatureConfig, consumer);
               lastBranch = i;
               numBranches++;
               westB = false;
            } else if (numBranches == 0) {
               this.addBranch(world, pos, i, Direction.NORTH, rand, baseTreeFeatureConfig, consumer);
               lastBranch = i;
               numBranches++;
               this.addBranch(world, pos, i, Direction.SOUTH, rand, baseTreeFeatureConfig, consumer);
               numBranches++;
            }
         }

         if (i == foliageHeight - 2) {
            float leafChance = 0.1F;
            this.addLineLeaves(world, pos.m_122013_(4).m_6630_(i), Direction.NORTH, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122013_(4).m_6630_(i + 1), Direction.NORTH, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122013_(3).m_6630_(i - 1), Direction.NORTH, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122013_(3).m_6630_(i), Direction.NORTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122013_(3).m_6630_(i + 1), Direction.NORTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122013_(2).m_6630_(i + 1), Direction.NORTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122013_(1).m_6630_(i + 1), Direction.NORTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122013_(2).m_6630_(i + 2), Direction.NORTH, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(5).m_6630_(i), Direction.EAST, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122030_(5).m_6630_(i + 1), Direction.EAST, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122030_(4).m_6630_(i - 1), Direction.EAST, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122030_(4).m_6630_(i), Direction.EAST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(4).m_6630_(i + 1), Direction.EAST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(3).m_6630_(i + 1), Direction.EAST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(2).m_6630_(i + 1), Direction.EAST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(3).m_6630_(i + 2), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(2).m_6630_(i + 2), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(1).m_6630_(i + 2), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(0).m_6630_(i + 2), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(-1).m_6630_(i + 2), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(-2).m_6630_(i + 2), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122025_(4).m_122019_().m_6630_(i), Direction.WEST, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122025_(4).m_122019_().m_6630_(i + 1), Direction.WEST, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122025_(3).m_122019_().m_6630_(i - 1), Direction.WEST, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122025_(3).m_122019_().m_6630_(i), Direction.WEST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122025_(3).m_122019_().m_6630_(i + 1), Direction.WEST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122025_(2).m_122019_().m_6630_(i + 1), Direction.WEST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122025_(1).m_122019_().m_6630_(i + 1), Direction.WEST, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122025_(2).m_122019_().m_6630_(i + 2), Direction.WEST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122020_(4).m_122029_().m_6630_(i), Direction.SOUTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122020_(4).m_122029_().m_6630_(i + 1), Direction.SOUTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122020_(5).m_122029_().m_6630_(i), Direction.SOUTH, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122020_(5).m_122029_().m_6630_(i + 1), Direction.SOUTH, 6, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122020_(3).m_122029_().m_6630_(i + 1), Direction.SOUTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122020_(2).m_122029_().m_6630_(i + 1), Direction.SOUTH, 6, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122020_(3).m_122029_().m_6630_(i + 2), Direction.SOUTH, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(2).m_6630_(i + 3), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(1).m_6630_(i + 3), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(0).m_6630_(i + 3), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(-1).m_6630_(i + 3), Direction.EAST, 4, rand, baseTreeFeatureConfig, consumer);
            this.addLineLeaves(world, pos.m_122030_(2).m_6630_(i + 4), Direction.EAST, 4, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122030_(1).m_6630_(i + 4), Direction.EAST, 4, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122030_(0).m_6630_(i + 4), Direction.EAST, 4, rand, baseTreeFeatureConfig, leafChance, consumer);
            this.addLineLeaves(world, pos.m_122030_(-1).m_6630_(i + 4), Direction.EAST, 4, rand, baseTreeFeatureConfig, leafChance, consumer);
         }

         if (this.isWorldGen && i >= 2 && i < foliageHeight - 2) {
            this.addPod(world, blockpos1, rand, consumer, new Direction[]{Direction.NORTH, Direction.WEST});
            this.addPod(world, blockpos1.m_122029_(), rand, consumer, new Direction[]{Direction.NORTH, Direction.EAST});
            this.addPod(world, blockpos1.m_122019_(), rand, consumer, new Direction[]{Direction.SOUTH, Direction.WEST});
            this.addPod(world, blockpos1.m_122019_().m_122029_(), rand, consumer, new Direction[]{Direction.SOUTH, Direction.EAST});
         }
      }

      list.add(new FoliageAttachment(new BlockPos(x, yOffset, z), 0, true));
      return list;
   }

   public void addPod(LevelSimulatedReader world, BlockPos pos, RandomSource random, BiConsumer<BlockPos, BlockState> consumer, Direction[] validDirs) {
      if (random.m_188500_() <= 0.07) {
         Direction d = validDirs[random.m_188503_(validDirs.length)];
         int age = random.m_216332_(0, 2);
         if (world.m_7433_(pos.m_121945_(d), BlockStateBase::m_60795_)) {
            this.setBlock(
               world,
               pos.m_121945_(d).m_7949_(),
               (BlockState)((BlockState)((BlockState)this.getPodState().m_61124_(HorizontalDirectionalBlock.f_54117_, d.m_122424_()))
                     .m_61124_(SconceBlock.LIGHT_LEVEL, 6 + age))
                  .m_61124_(ArchfruitPod.f_51736_, age),
               consumer
            );
         }
      }
   }

   public BlockState getPodState() {
      return ((Block)ForgeRegistries.BLOCKS.getValue(this.podID)).m_49966_();
   }

   public void addBranch(
      LevelSimulatedReader world,
      BlockPos pos,
      int height,
      Direction d,
      RandomSource random,
      TreeConfiguration baseTreeFeatureConfig,
      BiConsumer<BlockPos, BlockState> consumer
   ) {
      pos = pos.m_6630_(height);
      this.addLog(world, pos.m_121945_(d), random, baseTreeFeatureConfig, consumer);
      this.addLog(world, pos.m_121945_(d).m_6630_(1), random, baseTreeFeatureConfig, consumer);
      this.addLog(world, pos.m_121945_(d).m_6630_(2), random, baseTreeFeatureConfig, consumer);
      this.addLog(world, pos.m_5484_(d, 2).m_6630_(2), random, baseTreeFeatureConfig, consumer);
      this.addLog(world, pos.m_5484_(d, 3).m_6630_(2), random, baseTreeFeatureConfig, consumer);
      this.addLog(world, pos.m_5484_(d, 3).m_6630_(1), random, baseTreeFeatureConfig, consumer);
      this.addLineLeaves(world, pos.m_121945_(d).m_6630_(1), d, 3, random, baseTreeFeatureConfig, consumer);
      this.addLineLeaves(world, pos.m_121945_(d).m_6630_(2), d, 3, random, baseTreeFeatureConfig, consumer);
      this.addLineLeaves(world, pos.m_121945_(d).m_6630_(3), d, 3, random, baseTreeFeatureConfig, consumer);

      for (int j = 1; j < 4; j++) {
         this.addLineLeaves(world, pos.m_5484_(d, j).m_6630_(3), d, 3, random, baseTreeFeatureConfig, consumer);
         this.addLineLeaves(world, pos.m_5484_(d, j).m_6630_(2), d, 3, random, baseTreeFeatureConfig, consumer);
         this.addLineLeaves(world, pos.m_5484_(d, j).m_6630_(4), d, 3, random, baseTreeFeatureConfig, 0.1F, consumer);
      }

      for (int i = 0; i < 2; i++) {
         this.addHollowLine(world, pos.m_5484_(d, 2 + i).m_6630_(1), d, 2, random, baseTreeFeatureConfig, consumer);
         this.addHollowLine(world, pos.m_5484_(d, 2 + i).m_6630_(2), d, 2, random, baseTreeFeatureConfig, consumer);
         this.addHollowLine(world, pos.m_5484_(d, 2 + i).m_6630_(1), d, 3, random, baseTreeFeatureConfig, 0.1F, consumer);
         this.addHollowLine(world, pos.m_5484_(d, 2 + i).m_6630_(2), d, 3, random, baseTreeFeatureConfig, 0.1F, consumer);
      }

      this.addLineLeaves(world, pos.m_5484_(d, 4).m_6630_(1), d, 3, random, baseTreeFeatureConfig, consumer);
      this.addLineLeaves(world, pos.m_5484_(d, 4).m_6630_(2), d, 3, random, baseTreeFeatureConfig, consumer);
      this.addLineLeaves(world, pos.m_5484_(d, 5).m_6630_(1), d, 3, random, baseTreeFeatureConfig, 0.1F, consumer);
      this.addLineLeaves(world, pos.m_5484_(d, 5).m_6630_(2), d, 3, random, baseTreeFeatureConfig, 0.1F, consumer);
   }

   public boolean addLog(
      LevelSimulatedReader world, BlockPos pos, RandomSource random, TreeConfiguration baseTreeFeatureConfig, BiConsumer<BlockPos, BlockState> consumer
   ) {
      return this.addBlock(world, pos, baseTreeFeatureConfig.f_68185_.m_213972_(random, pos), consumer);
   }

   public boolean addBlock(LevelSimulatedReader world, BlockPos pos, BlockState state, BiConsumer<BlockPos, BlockState> consumer) {
      if (TreeFeature.m_67272_(world, pos)) {
         this.setBlock(world, pos, state, consumer);
         return true;
      } else {
         return false;
      }
   }

   public void addHollowLine(
      LevelSimulatedReader world,
      BlockPos pos,
      Direction d,
      int length,
      RandomSource rand,
      TreeConfiguration baseTreeFeatureConfig,
      BiConsumer<BlockPos, BlockState> consumer
   ) {
      this.addHollowLine(world, pos, d, length, rand, baseTreeFeatureConfig, 1.0F, consumer);
   }

   public void addHollowLine(
      LevelSimulatedReader world,
      BlockPos pos,
      Direction d,
      int length,
      RandomSource rand,
      TreeConfiguration baseTreeFeatureConfig,
      float chance,
      BiConsumer<BlockPos, BlockState> consumer
   ) {
      Direction left = d.m_122427_();
      Direction right = left.m_122424_();
      if (rand.m_188501_() <= chance && TreeFeature.m_67272_(world, pos.m_5484_(left, length))) {
         this.setBlock(world, pos.m_5484_(left, length), baseTreeFeatureConfig.f_161213_.m_213972_(rand, pos.m_5484_(left, length)), consumer);
      }

      if (rand.m_188501_() <= chance && TreeFeature.m_67272_(world, pos.m_5484_(right, length))) {
         this.setBlock(world, pos.m_5484_(right, length), baseTreeFeatureConfig.f_161213_.m_213972_(rand, pos.m_5484_(right, length)), consumer);
      }
   }

   public void addLineLeaves(
      LevelSimulatedReader world,
      BlockPos pos,
      Direction d,
      int length,
      RandomSource rand,
      TreeConfiguration baseTreeFeatureConfig,
      BiConsumer<BlockPos, BlockState> consumer
   ) {
      if (length % 2 == 0) {
         this.addLineLeavesEven(world, pos, d, length, rand, baseTreeFeatureConfig, 1.0F, consumer);
      } else {
         this.addLineLeavesOdd(world, pos, d, length, rand, baseTreeFeatureConfig, 1.0F, consumer);
      }
   }

   public void addLineLeaves(
      LevelSimulatedReader world,
      BlockPos pos,
      Direction d,
      int length,
      RandomSource rand,
      TreeConfiguration baseTreeFeatureConfig,
      float chance,
      BiConsumer<BlockPos, BlockState> consumer
   ) {
      if (length % 2 == 0) {
         this.addLineLeavesEven(world, pos, d, length, rand, baseTreeFeatureConfig, chance, consumer);
      } else {
         this.addLineLeavesOdd(world, pos, d, length, rand, baseTreeFeatureConfig, chance, consumer);
      }
   }

   public void addLineLeavesEven(
      LevelSimulatedReader world,
      BlockPos pos,
      Direction d,
      int length,
      RandomSource rand,
      TreeConfiguration baseTreeFeatureConfig,
      float chance,
      BiConsumer<BlockPos, BlockState> consumer
   ) {
      Direction left = d.m_122427_();
      Direction right = left.m_122424_();

      for (int i = 0; i < length; i++) {
         if (rand.m_188501_() <= chance && TreeFeature.m_67272_(world, pos.m_5484_(left, i - length / 3))) {
            this.setBlock(
               world, pos.m_5484_(left, i - length / 3), baseTreeFeatureConfig.f_161213_.m_213972_(rand, pos.m_5484_(left, i - length / 3)), consumer
            );
         }
      }
   }

   public void addLineLeavesOdd(
      LevelSimulatedReader world,
      BlockPos pos,
      Direction d,
      int length,
      RandomSource rand,
      TreeConfiguration baseTreeFeatureConfig,
      float chance,
      BiConsumer<BlockPos, BlockState> consumer
   ) {
      Direction left = d.m_122427_();
      Direction right = left.m_122424_();
      length += 2;

      for (int i = 0; i < (length - 1) / 2; i++) {
         if (rand.m_188501_() <= chance && TreeFeature.m_67272_(world, pos.m_5484_(left, i))) {
            this.setBlock(world, pos.m_5484_(left, i), baseTreeFeatureConfig.f_161213_.m_213972_(rand, pos.m_5484_(left, i)), consumer);
         }

         if (rand.m_188501_() <= chance && TreeFeature.m_67272_(world, pos.m_5484_(right, i))) {
            this.setBlock(world, pos.m_5484_(right, i), baseTreeFeatureConfig.f_161213_.m_213972_(rand, pos.m_5484_(right, i)), consumer);
         }
      }
   }

   public boolean addRoots(
      LevelSimulatedReader world,
      RandomSource rand,
      BlockPos pos,
      BiConsumer<BlockPos, BlockState> consumer,
      TreeConfiguration baseTreeFeatureConfig,
      Direction[] extendedDirs
   ) {
      BlockState state = baseTreeFeatureConfig.f_68185_.m_213972_(rand, pos);
      if (rand.m_188500_() < 0.75 && TreeFeature.m_67272_(world, pos)) {
         this.setBlock(world, pos.m_7949_(), state, consumer);
         if (this.isWorldGen) {
            for (int i = 0; i < 2 && TreeFeature.m_67272_(world, pos.m_7495_()); i++) {
               pos = pos.m_7495_();
               this.setBlock(world, pos.m_7949_(), state, consumer);
            }

            for (Direction d : extendedDirs) {
               this.placeRotatedRoot(world, rand, pos.m_7495_().m_121945_(d), consumer, baseTreeFeatureConfig, d);
            }

            return true;
         }
      }

      return false;
   }

   public boolean placeRotatedRoot(
      LevelSimulatedReader world,
      RandomSource rand,
      BlockPos pos,
      BiConsumer<BlockPos, BlockState> consumer,
      TreeConfiguration baseTreeFeatureConfig,
      Direction direction
   ) {
      BlockState state = baseTreeFeatureConfig.f_68185_.m_213972_(rand, pos);
      if (state.m_61138_(RotatedPillarBlock.f_55923_)) {
         state = (BlockState)state.m_61124_(RotatedPillarBlock.f_55923_, direction.m_122434_());
      }

      if (rand.m_188500_() < 0.6 && this.validForExtendedRoot(world, pos)) {
         this.setBlock(world, pos.m_7949_(), state, consumer);
         int count = 0;

         while (rand.m_188500_() < 0.8 - (double)count * 0.3) {
            count++;
            if (rand.m_188500_() < 0.7) {
               direction = rand.m_188500_() < 0.5 ? direction.m_122427_() : direction.m_122428_();
               state = (BlockState)state.m_61124_(RotatedPillarBlock.f_55923_, direction.m_122434_());
            }

            pos = pos.m_121945_(direction);
            if (TreeFeature.m_67272_(world, pos.m_7495_())) {
               pos = pos.m_7495_();
            }

            if (!this.validForExtendedRoot(world, pos)) {
               break;
            }

            this.setBlock(world, pos.m_7949_(), state, consumer);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean validForExtendedRoot(LevelSimulatedReader world, BlockPos pos) {
      return TreeFeature.m_67272_(world, pos) || world.m_7433_(pos, b -> b.m_60734_() == Blocks.f_50493_ || b.m_60734_() == Blocks.f_50440_);
   }

   public void setBlock(LevelSimulatedReader world, BlockPos pos, BlockState state, BiConsumer<BlockPos, BlockState> consumer) {
      consumer.accept(pos, state);
   }
}
