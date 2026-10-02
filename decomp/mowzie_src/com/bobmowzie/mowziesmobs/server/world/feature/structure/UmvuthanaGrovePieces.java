package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.FeatureHandler;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.FluidState;

public class UmvuthanaGrovePieces {
   private static final Set<Block> BLOCKS_NEEDING_POSTPROCESSING = ImmutableSet.builder()
      .add(Blocks.f_50198_)
      .add(Blocks.f_50081_)
      .add(Blocks.f_50082_)
      .add(Blocks.f_50132_)
      .add(Blocks.f_50479_)
      .add(Blocks.f_50483_)
      .add(Blocks.f_50482_)
      .add(Blocks.f_50480_)
      .add(Blocks.f_50481_)
      .add(Blocks.f_220852_)
      .add(Blocks.f_50155_)
      .add(Blocks.f_50310_)
      .build();
   public static final ResourceLocation PLATFORM_1 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_platform_1");
   public static final ResourceLocation PLATFORM_2 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_platform_2");
   public static final ResourceLocation[] PLATFORMS = new ResourceLocation[]{PLATFORM_1, PLATFORM_2};
   public static final ResourceLocation PLATFORM_EXTEND = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_platform_extend");
   public static final ResourceLocation FIREPIT = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_firepit");
   public static final ResourceLocation FIREPIT_SMALL_1 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_firepit_small_1");
   public static final ResourceLocation FIREPIT_SMALL_2 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_firepit_small_2");
   public static final ResourceLocation[] FIREPIT_SMALL = new ResourceLocation[]{FIREPIT_SMALL_1, FIREPIT_SMALL_2};
   public static final ResourceLocation TREE_1 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_tree_1");
   public static final ResourceLocation TREE_2 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_tree_2");
   public static final ResourceLocation TREE_3 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_tree_3");
   public static final ResourceLocation[] TREES = new ResourceLocation[]{TREE_1, TREE_2, TREE_3};
   public static final ResourceLocation SPIKE_1 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_spike_1");
   public static final ResourceLocation SPIKE_2 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_spike_2");
   public static final ResourceLocation SPIKE_3 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_spike_3");
   public static final ResourceLocation SPIKE_4 = new ResourceLocation("mowziesmobs", "umvuthana/umvuthana_spike_4");
   public static final ResourceLocation[] SPIKES = new ResourceLocation[]{SPIKE_1, SPIKE_2, SPIKE_3, SPIKE_4};
   public static final ResourceLocation THRONE = new ResourceLocation("mowziesmobs", "umvuthana/umvuthi_throne");
   private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.builder()
      .put(PLATFORM_1, new BlockPos(-5, 0, -5))
      .put(PLATFORM_2, new BlockPos(0, 0, -5))
      .put(PLATFORM_EXTEND, new BlockPos(8, 1, -2))
      .put(FIREPIT, new BlockPos(-3, -2, -3))
      .put(FIREPIT_SMALL_1, new BlockPos(-1, 0, -1))
      .put(FIREPIT_SMALL_2, new BlockPos(-1, 0, -1))
      .put(TREE_1, new BlockPos(-5, 1, -3))
      .put(TREE_2, new BlockPos(-3, 1, -3))
      .put(TREE_3, new BlockPos(-3, 1, -3))
      .put(SPIKE_1, new BlockPos(-1, 1, 0))
      .put(SPIKE_2, new BlockPos(0, 1, 0))
      .put(SPIKE_3, new BlockPos(0, 1, 0))
      .put(SPIKE_4, new BlockPos(0, 1, 0))
      .put(THRONE, new BlockPos(-9, 0, 0))
      .build();
   private static final Map<ResourceLocation, Pair<BlockPos, BlockPos>> BOUNDS_OFFSET = ImmutableMap.builder()
      .put(PLATFORM_1, new Pair(new BlockPos(1, 0, 0), new BlockPos(-3, 0, -3)))
      .put(PLATFORM_2, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, -3)))
      .put(PLATFORM_EXTEND, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(FIREPIT, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(FIREPIT_SMALL_1, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(FIREPIT_SMALL_2, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(TREE_1, new Pair(new BlockPos(1, 0, 1), new BlockPos(-3, 0, -3)))
      .put(TREE_2, new Pair(new BlockPos(2, 0, 1), new BlockPos(-1, 0, -3)))
      .put(TREE_3, new Pair(new BlockPos(2, 0, 2), new BlockPos(-2, 0, -2)))
      .put(SPIKE_1, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(SPIKE_2, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(SPIKE_3, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(SPIKE_4, new Pair(new BlockPos(0, 0, 0), new BlockPos(0, 0, 0)))
      .put(THRONE, new Pair(new BlockPos(4, 0, 1), new BlockPos(-4, 0, -3)))
      .build();

   public static StructurePiece addPiece(
      ResourceLocation resourceLocation, StructureTemplateManager manager, BlockPos pos, Rotation rot, StructurePieceAccessor pieces, WorldgenRandom rand
   ) {
      StructurePiece newPiece = new UmvuthanaGrovePieces.Piece(manager, resourceLocation, rot, pos);
      pieces.m_142679_(newPiece);
      return newPiece;
   }

   public static StructurePiece addPieceCheckBounds(
      ResourceLocation resourceLocation,
      StructureTemplateManager manager,
      BlockPos pos,
      Rotation rot,
      StructurePieceAccessor pieces,
      WorldgenRandom rand,
      List<StructurePiece> ignore
   ) {
      UmvuthanaGrovePieces.Piece newPiece = new UmvuthanaGrovePieces.Piece(manager, resourceLocation, rot, pos);
      StructurePiece collisionPiece = pieces.m_141921_(newPiece.getCollisionBoundingBox());
      if (collisionPiece != null && !ignore.contains(collisionPiece)) {
         return null;
      } else {
         pieces.m_142679_(newPiece);
         return newPiece;
      }
   }

   public static StructurePiece addPlatform(StructureTemplateManager manager, BlockPos pos, Rotation rot, StructurePiecesBuilder builder, WorldgenRandom rand) {
      int whichPlatform = rand.m_188503_(PLATFORMS.length);
      UmvuthanaGrovePieces.Piece newPiece = new UmvuthanaGrovePieces.Piece(manager, PLATFORMS[whichPlatform], rot, pos);
      if (findCollisionPiece(builder.f_192778_, newPiece.getCollisionBoundingBox()) != null) {
         return null;
      } else {
         builder.m_142679_(newPiece);
         if (whichPlatform == 1) {
            UmvuthanaGrovePieces.Piece extension = new UmvuthanaGrovePieces.Piece(manager, PLATFORM_EXTEND, rot, pos);
            if (findCollisionPiece(builder.f_192778_, extension.getCollisionBoundingBox(), newPiece) != null) {
               return newPiece;
            }

            builder.m_142679_(extension);
         }

         return newPiece;
      }
   }

   public static StructurePiece addPieceCheckBounds(
      ResourceLocation resourceLocation, StructureTemplateManager manager, BlockPos pos, Rotation rot, StructurePiecesBuilder pieces, WorldgenRandom rand
   ) {
      return addPieceCheckBounds(resourceLocation, manager, pos, rot, pieces, rand, Collections.emptyList());
   }

   @Nullable
   public static StructurePiece findCollisionPiece(List<StructurePiece> pieces, BoundingBox bounds, StructurePiece ignore) {
      for (StructurePiece structurePiece : pieces) {
         if (structurePiece != ignore) {
            if (structurePiece instanceof UmvuthanaGrovePieces.Piece && ((UmvuthanaGrovePieces.Piece)structurePiece).getCollisionBoundingBox().m_71049_(bounds)
               )
             {
               return structurePiece;
            }

            if (structurePiece.m_73547_().m_71049_(bounds)) {
               return structurePiece;
            }
         }
      }

      return null;
   }

   @Nullable
   public static StructurePiece findCollisionPiece(List<StructurePiece> pieces, BoundingBox bounds) {
      return findCollisionPiece(pieces, bounds, null);
   }

   public static class FirepitPiece extends UmvuthanaGrovePieces.Piece {
      public FirepitPiece(StructureTemplateManager manager, Rotation rotation, BlockPos pos) {
         super(FeatureHandler.UMVUTHANA_FIREPIT, manager, UmvuthanaGrovePieces.FIREPIT, rotation, pos);
      }

      public FirepitPiece(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super(FeatureHandler.UMVUTHANA_FIREPIT, context, tagCompound);
      }

      public BlockPos findGround(LevelAccessor worldIn, int x, int z) {
         int i = this.m_73392_(x, z);
         int k = this.m_73525_(x, z);
         int j = worldIn.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, i, k);
         return new BlockPos(i, j, k);
      }

      @Override
      public void m_213694_(
         WorldGenLevel worldIn,
         StructureManager structureManager,
         ChunkGenerator chunkGenerator,
         RandomSource randomIn,
         BoundingBox p_230383_5_,
         ChunkPos p_230383_6_,
         BlockPos p_230383_7_
      ) {
         super.m_213694_(worldIn, structureManager, chunkGenerator, randomIn, p_230383_5_, p_230383_6_, p_230383_7_);
         BlockPos centerPos = this.findGround(worldIn, 4, 4);
         int numUmvuthana = randomIn.m_188503_(5) + 5;

         for (int i = 1; i <= numUmvuthana; i++) {
            EntityUmvuthanaMinion umvuthana = new EntityUmvuthanaMinion(
               (EntityType<? extends EntityUmvuthanaMinion>)EntityHandler.UMVUTHANA_MINION.get(), worldIn.m_6018_()
            );

            for (int j = 1; j <= 20; j++) {
               int distance = randomIn.m_188503_(10) + 2;
               int angle = randomIn.m_188503_(360);
               int x = (int)((double)distance * Math.sin(Math.toRadians((double)angle))) + 4;
               int z = (int)((double)distance * Math.cos(Math.toRadians((double)angle))) + 4;
               BlockPos bPos = this.findGround(worldIn, x, z);
               umvuthana.m_6034_((double)bPos.m_123341_(), (double)bPos.m_123342_(), (double)bPos.m_123343_());
               if (bPos.m_123342_() > 0 && umvuthana.m_5545_(worldIn, MobSpawnType.STRUCTURE) && worldIn.m_45772_(umvuthana.m_20191_())) {
                  umvuthana.m_6518_(worldIn, worldIn.m_6436_(umvuthana.m_20183_()), MobSpawnType.STRUCTURE, null, null);
                  umvuthana.m_21446_(centerPos, 25);
                  worldIn.m_7967_(umvuthana);
                  break;
               }
            }
         }
      }
   }

   public static class Piece extends TemplateStructurePiece {
      protected ResourceLocation resourceLocation;
      public BoundingBox collisionBoundingBox;

      public Piece(StructurePieceType pieceType, StructureTemplateManager manager, ResourceLocation resourceLocationIn, Rotation rotation, BlockPos pos) {
         super(
            pieceType,
            0,
            manager,
            resourceLocationIn,
            resourceLocationIn.toString(),
            makeSettings(rotation, resourceLocationIn),
            makePosition(resourceLocationIn, pos, rotation)
         );
         this.resourceLocation = resourceLocationIn;
         this.collisionBoundingBox = this.makeCollisionBoundingBox();
         if (this.resourceLocation == UmvuthanaGrovePieces.THRONE || this.resourceLocation == UmvuthanaGrovePieces.FIREPIT) {
            this.f_73383_ = this.m_73547_().m_71045_(0, 1, 0);
         }
      }

      public Piece(StructurePieceType pieceType, StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super(pieceType, tagCompound, context.f_226956_(), resourceLocation -> makeSettings(Rotation.valueOf(tagCompound.m_128461_("Rot")), resourceLocation));
         this.collisionBoundingBox = this.makeCollisionBoundingBox();
         if (this.resourceLocation == UmvuthanaGrovePieces.THRONE || this.resourceLocation == UmvuthanaGrovePieces.FIREPIT) {
            this.f_73383_ = this.m_73547_().m_71045_(0, 1, 0);
         }
      }

      public Piece(StructureTemplateManager manager, ResourceLocation resourceLocationIn, Rotation rotation, BlockPos pos) {
         this(FeatureHandler.UMVUTHANA_GROVE_PIECE, manager, resourceLocationIn, rotation, pos);
      }

      public Piece(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         this(FeatureHandler.UMVUTHANA_GROVE_PIECE, context, tagCompound);
      }

      private static StructurePlaceSettings makeSettings(Rotation rotation, ResourceLocation resourceLocation) {
         return new StructurePlaceSettings().m_74379_(rotation).m_74377_(Mirror.NONE).m_74383_(BlockIgnoreProcessor.f_74046_);
      }

      private static BlockPos makePosition(ResourceLocation resourceLocation, BlockPos pos, Rotation rotation) {
         return pos.m_121955_(UmvuthanaGrovePieces.OFFSET.get(resourceLocation).m_7954_(rotation));
      }

      public BoundingBox makeCollisionBoundingBox() {
         StructureTemplate structuretemplate = this.f_73656_;
         BlockPos boundsMaxOffset;
         BlockPos boundsMinOffset = boundsMaxOffset = new BlockPos(0, 0, 0);
         Pair<BlockPos, BlockPos> boundsOffset = UmvuthanaGrovePieces.BOUNDS_OFFSET.get(this.resourceLocation);
         if (boundsOffset != null) {
            boundsMinOffset = (BlockPos)boundsOffset.getFirst();
            boundsMaxOffset = (BlockPos)boundsOffset.getSecond();
         }

         Vec3i sizeVec = structuretemplate.m_163801_().m_7918_(-1, -1, -1);
         BlockPos blockpos = StructureTemplate.m_74593_(
            BlockPos.f_121853_.m_121955_(boundsMinOffset), this.f_73657_.m_74401_(), this.f_73657_.m_74404_(), this.f_73657_.m_74407_()
         );
         BlockPos blockpos1 = StructureTemplate.m_74593_(
            BlockPos.f_121853_.m_121955_(sizeVec).m_121955_(boundsMaxOffset), this.f_73657_.m_74401_(), this.f_73657_.m_74404_(), this.f_73657_.m_74407_()
         );
         return BoundingBox.m_162375_(blockpos, blockpos1).m_162373_(this.f_73658_);
      }

      public BoundingBox getCollisionBoundingBox() {
         return this.collisionBoundingBox;
      }

      protected void m_183620_(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super.m_183620_(context, tagCompound);
         tagCompound.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      public void m_213694_(
         WorldGenLevel p_192682_,
         StructureManager p_192683_,
         ChunkGenerator p_192684_,
         RandomSource p_192685_,
         BoundingBox p_192686_,
         ChunkPos p_192687_,
         BlockPos p_192688_
      ) {
         super.m_213694_(p_192682_, p_192683_, p_192684_, p_192685_, p_192686_, p_192687_, p_192688_);
      }

      protected void m_213704_(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
         Rotation rotation = this.f_73657_.m_74404_();
         if (function.equals("support")) {
            worldIn.m_7731_(pos, Blocks.f_50132_.m_49966_(), 3);
            this.fillAirLiquidDown(worldIn, Blocks.f_50132_.m_49966_(), pos.m_7495_());
         } else if (function.equals("trunk")) {
            this.fillAirLiquidDownTrunk(worldIn, pos, rand);
         } else if (function.equals("leg")) {
            this.fillAirLiquidDown(worldIn, Blocks.f_220835_.m_49966_(), pos);
         } else if (function.equals("base")) {
            this.fillAirLiquidDownBase(worldIn, pos, rand);
         } else if (function.equals("umvuthi")) {
            this.setBlockState(worldIn, pos, Blocks.f_50016_.m_49966_());
            EntityUmvuthi barako = new EntityUmvuthi((EntityType<? extends EntityUmvuthi>)EntityHandler.UMVUTHI.get(), worldIn.m_6018_());
            barako.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
            int i = rotation.m_55949_(3, 4);
            barako.setDirection(i);
            barako.m_6518_(worldIn, worldIn.m_6436_(barako.m_20183_()), MobSpawnType.STRUCTURE, null, null);
            BlockPos offset = new BlockPos(0, 0, -18);
            offset = offset.m_7954_(rotation);
            BlockPos firePitPos = pos.m_121955_(offset);
            firePitPos = worldIn.m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, firePitPos);
            barako.m_21446_(firePitPos, -1);
            worldIn.m_7967_(barako);
         } else if ("chest".equals(function)) {
            Direction facing = Direction.NORTH;
            facing = rotation.m_55954_(facing);
            this.m_226762_(
               worldIn,
               sbb,
               rand,
               pos,
               LootTableHandler.UMVUTHANA_GROVE_CHEST,
               (BlockState)Blocks.f_50087_.m_49966_().m_61124_(BlockStateProperties.f_61374_, facing)
            );
         } else if ("skull".equals(function)) {
            BlockPos groundPos = this.getGroundPos(worldIn, pos);
            this.setBlockState(worldIn, groundPos.m_7494_(), (BlockState)Blocks.f_50310_.m_49966_().m_61124_(BlockStateProperties.f_61390_, rand.m_188503_(16)));
         } else if ("campfire".equals(function)) {
            BlockPos groundPos = this.getGroundPos(worldIn, pos);
            this.setBlockState(worldIn, groundPos.m_7494_(), Blocks.f_50683_.m_49966_());
         } else if (function.length() > 5 && "spike".equals(function.substring(0, 5))) {
            String[] split = function.split("_");
            int logCount = 2;
            int fenceCount = 1;
            int barCount = 1;
            int skullCount = 0;
            if (split.length > 1) {
               logCount = Integer.parseInt(split[1]);
            }

            if (split.length > 2) {
               fenceCount = Integer.parseInt(split[2]);
            }

            if (split.length > 3) {
               barCount = Integer.parseInt(split[3]);
            }

            if (split.length > 4) {
               skullCount = Integer.parseInt(split[4]);
            }

            this.genSpike(worldIn, pos, rand, logCount, fenceCount, barCount, skullCount);
         } else if (function.length() > 6 && "stairs".equals(function.substring(0, 6))) {
            String[] splitx = function.split("_");
            Direction stairDirection = Direction.EAST;
            Direction newDirection = null;
            if (splitx.length > 1) {
               newDirection = Direction.m_122402_(splitx[1]);
            }

            if (newDirection != null) {
               stairDirection = newDirection;
            }

            stairDirection = rotation.m_55954_(stairDirection);
            this.genStairs(worldIn, pos, rand, stairDirection);
         } else if ("chest_under".equals(function)) {
            if ((double)rand.m_188501_() < 0.5) {
               worldIn.m_7471_(pos, false);
            } else {
               BlockPos groundPos = this.getGroundPos(worldIn, pos);
               Direction facing = (double)rand.m_188501_() < 0.5 ? Direction.NORTH : Direction.EAST;
               facing = rotation.m_55954_(facing);
               this.m_226762_(
                  worldIn,
                  sbb,
                  rand,
                  groundPos.m_7494_(),
                  LootTableHandler.UMVUTHANA_GROVE_CHEST,
                  (BlockState)Blocks.f_50087_.m_49966_().m_61124_(BlockStateProperties.f_61374_, facing)
               );
            }
         } else if (function.length() > 4 && "mask".equals(function.substring(0, 4))) {
            worldIn.m_7471_(pos, false);
            String[] splitxx = function.split("_");
            Direction direction = Direction.NORTH;
            if (splitxx.length > 1) {
               direction = Direction.m_122402_(splitxx[1]);
            }

            ItemFrame itemFrame = new ItemFrame(worldIn.m_6018_(), pos, rotation.m_55954_(direction));
            int i = rand.m_188503_(MaskType.values().length);
            MaskType type = MaskType.values()[i];
            ItemUmvuthanaMask mask = ItemHandler.UMVUTHANA_MASK_FURY;
            switch (type) {
               case BLISS:
                  mask = ItemHandler.UMVUTHANA_MASK_BLISS;
                  break;
               case FEAR:
                  mask = ItemHandler.UMVUTHANA_MASK_FEAR;
                  break;
               case FURY:
                  mask = ItemHandler.UMVUTHANA_MASK_FURY;
                  break;
               case MISERY:
                  mask = ItemHandler.UMVUTHANA_MASK_MISERY;
                  break;
               case RAGE:
                  mask = ItemHandler.UMVUTHANA_MASK_RAGE;
                  break;
               case FAITH:
                  mask = ItemHandler.UMVUTHANA_MASK_FAITH;
            }

            ItemStack stack = new ItemStack(mask);
            itemFrame.m_31789_(stack, false);
            worldIn.m_7967_(itemFrame);
         } else {
            worldIn.m_7471_(pos, false);
         }
      }

      protected void setBlockState(LevelAccessor worldIn, BlockPos pos, BlockState state) {
         FluidState ifluidstate = worldIn.m_6425_(pos);
         if (!ifluidstate.m_76178_()) {
            worldIn.m_183324_().m_183588_(pos, ifluidstate.m_76152_());
            if (state.m_61138_(BlockStateProperties.f_61362_)) {
               state = (BlockState)state.m_61124_(BlockStateProperties.f_61362_, true);
            }
         }

         worldIn.m_7731_(pos, state, 2);
         if (UmvuthanaGrovePieces.BLOCKS_NEEDING_POSTPROCESSING.contains(state.m_60734_())) {
            worldIn.m_46865_(pos).m_8113_(pos);
         }
      }

      public BlockPos getGroundPos(LevelAccessor worldIn, BlockPos startPos) {
         while (!Block.m_49936_(worldIn, startPos) && startPos.m_123342_() > worldIn.m_141937_()) {
            startPos = startPos.m_7495_();
         }

         return startPos;
      }

      public void fillAirLiquidDown(LevelAccessor worldIn, BlockState state, BlockPos startPos) {
         int i = startPos.m_123341_();
         int j = startPos.m_123342_();

         for (int k = startPos.m_123343_(); !Block.m_49936_(worldIn, new BlockPos(i, j, k)) && j > 1; j--) {
            BlockPos pos = new BlockPos(i, j, k);
            this.setBlockState(worldIn, pos, state);
         }
      }

      public void fillAirLiquidDownTrunk(LevelAccessor worldIn, BlockPos startPos, RandomSource rand) {
         int i = startPos.m_123341_();
         int j = startPos.m_123342_();

         for (int k = startPos.m_123343_(); !Block.m_49936_(worldIn, new BlockPos(i, j, k)) && j > 1; j--) {
            BlockPos pos = new BlockPos(i, j, k);
            this.setBlockState(worldIn, pos, (double)rand.m_188501_() < 0.2 ? ((Block)BlockHandler.CLAWED_LOG.get()).m_49966_() : Blocks.f_50047_.m_49966_());
         }
      }

      public void fillAirLiquidDownBase(LevelAccessor worldIn, BlockPos startPos, RandomSource rand) {
         int i = startPos.m_123341_();
         int j = startPos.m_123342_();

         for (int k = startPos.m_123343_(); !Block.m_49936_(worldIn, new BlockPos(i, j, k)) && j > 1; j--) {
            BlockPos pos = new BlockPos(i, j, k);
            this.setBlockState(worldIn, pos, (double)rand.m_188501_() < 0.5 ? Blocks.f_220835_.m_49966_() : Blocks.f_50301_.m_49966_());
         }
      }

      public void genStairs(LevelAccessor worldIn, BlockPos pos, RandomSource rand, Direction direction) {
         for (int i = 1; i < 5; i++) {
            if (Block.m_49936_(worldIn, pos)) {
               return;
            }

            BlockState state = (double)rand.m_188501_() > 0.5 ? Blocks.f_50402_.m_49966_() : Blocks.f_50644_.m_49966_();
            if (i % 2 == 1) {
               state = (BlockState)state.m_61124_(SlabBlock.f_56353_, SlabType.TOP);
            }

            this.setBlockState(worldIn, pos, state);
            pos = pos.m_121945_(direction);
            if (i % 2 == 0) {
               pos = pos.m_121945_(Direction.DOWN);
            }
         }

         pos = pos.m_121945_(direction.m_122424_());
         this.fillAirLiquidDown(worldIn, Blocks.f_220835_.m_49966_(), pos);
         this.fillAirLiquidDown(worldIn, (BlockState)Blocks.f_50155_.m_49966_().m_61124_(LadderBlock.f_54337_, direction), pos.m_121945_(direction));
      }

      public void genSpike(LevelAccessor worldIn, BlockPos startPos, RandomSource rand, int numLogs, int numFence, int numBars, int numSkulls) {
         int groundPos = worldIn.m_6924_(Types.OCEAN_FLOOR_WG, startPos.m_123341_(), startPos.m_123343_());
         MutableBlockPos pos = new MutableBlockPos(startPos.m_123341_(), groundPos - 1, startPos.m_123343_());

         for (int i = 0; i < numLogs; i++) {
            this.setBlockState(worldIn, pos, Blocks.f_220835_.m_49966_());
            pos.m_122173_(Direction.UP);
         }

         for (int i = 0; i < numFence; i++) {
            this.setBlockState(worldIn, pos, Blocks.f_220852_.m_49966_());
            pos.m_122173_(Direction.UP);
         }

         for (int i = 0; i < numBars; i++) {
            this.setBlockState(worldIn, pos, Blocks.f_50183_.m_49966_());
            pos.m_122173_(Direction.UP);
         }

         if ((double)rand.m_188501_() < 0.1 && numSkulls > 0) {
            this.setBlockState(worldIn, pos, (BlockState)Blocks.f_50310_.m_49966_().m_61124_(BlockStateProperties.f_61390_, rand.m_188503_(16)));
         }
      }
   }
}
