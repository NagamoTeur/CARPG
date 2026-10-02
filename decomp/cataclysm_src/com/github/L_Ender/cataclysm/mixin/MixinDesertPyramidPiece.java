package com.github.L_Ender.cataclysm.mixin;

import com.min01.archaeology.misc.IDesertPyramidPiece;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({DesertPyramidPiece.class})
public abstract class MixinDesertPyramidPiece extends ScatteredFeaturePiece implements IDesertPyramidPiece {
   @Unique
   private final List<BlockPos> archeology$potentialSuspiciousSandWorldPositions = new ArrayList<>();
   @Unique
   private BlockPos archeology$randomCollapsedRoofPos = BlockPos.f_121853_;

   protected MixinDesertPyramidPiece(StructurePieceType type, CompoundTag tag) {
      super(type, tag);
   }

   @Inject(
      method = {"postProcess"},
      at = {@At("TAIL")}
   )
   private void postProcess(
      WorldGenLevel level,
      StructureManager manager,
      ChunkGenerator generator,
      RandomSource random,
      BoundingBox boundingBox,
      ChunkPos chunkPosition,
      BlockPos position,
      CallbackInfo callback
   ) {
      this.archeology$addCellar(level, boundingBox);
   }

   @Unique
   private void archeology$addCellar(WorldGenLevel level, BoundingBox boundingBox) {
      BlockPos blockpos = new BlockPos(16, -4, 13);
      this.archeology$addCellarStairs(blockpos, level, boundingBox);
      this.archeology$addCellarRoom(blockpos, level, boundingBox);
   }

   @Unique
   private void archeology$addCellarStairs(BlockPos position, WorldGenLevel level, BoundingBox boundingBox) {
      int x = position.m_123341_();
      int y = position.m_123342_();
      int z = position.m_123343_();
      BlockState sandstoneStairs = Blocks.f_50263_.m_49966_();
      this.m_73434_(level, sandstoneStairs.m_60717_(Rotation.COUNTERCLOCKWISE_90), 13, -1, 17, boundingBox);
      this.m_73434_(level, sandstoneStairs.m_60717_(Rotation.COUNTERCLOCKWISE_90), 14, -2, 17, boundingBox);
      this.m_73434_(level, sandstoneStairs.m_60717_(Rotation.COUNTERCLOCKWISE_90), 15, -3, 17, boundingBox);
      BlockState sand = Blocks.f_49992_.m_49966_();
      BlockState sandStone = Blocks.f_50062_.m_49966_();
      boolean flag = level.m_213780_().m_188499_();
      this.m_73434_(level, sand, x - 4, y + 4, z + 4, boundingBox);
      this.m_73434_(level, sand, x - 3, y + 4, z + 4, boundingBox);
      this.m_73434_(level, sand, x - 2, y + 4, z + 4, boundingBox);
      this.m_73434_(level, sand, x - 1, y + 4, z + 4, boundingBox);
      this.m_73434_(level, sand, x, y + 4, z + 4, boundingBox);
      this.m_73434_(level, sand, x - 2, y + 3, z + 4, boundingBox);
      this.m_73434_(level, flag ? sand : sandStone, x - 1, y + 3, z + 4, boundingBox);
      this.m_73434_(level, !flag ? sand : sandStone, x, y + 3, z + 4, boundingBox);
      this.m_73434_(level, sand, x - 1, y + 2, z + 4, boundingBox);
      this.m_73434_(level, sandStone, x, y + 2, z + 4, boundingBox);
      this.m_73434_(level, sand, x, y + 1, z + 4, boundingBox);
   }

   @Unique
   private void archeology$addCellarRoom(BlockPos position, WorldGenLevel level, BoundingBox boundingBox) {
      int x = position.m_123341_();
      int y = position.m_123342_();
      int z = position.m_123343_();
      BlockState cutSandstone = Blocks.f_50064_.m_49966_();
      BlockState chiseledSandstone = Blocks.f_50063_.m_49966_();
      this.m_73441_(level, boundingBox, x - 3, y + 1, z - 3, x - 3, y + 1, z + 2, cutSandstone, cutSandstone, true);
      this.m_73441_(level, boundingBox, x + 3, y + 1, z - 3, x + 3, y + 1, z + 2, cutSandstone, cutSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, y + 1, z - 3, x + 3, y + 1, z - 2, cutSandstone, cutSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, y + 1, z + 3, x + 3, y + 1, z + 3, cutSandstone, cutSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, y + 2, z - 3, x - 3, y + 2, z + 2, chiseledSandstone, chiseledSandstone, true);
      this.m_73441_(level, boundingBox, x + 3, y + 2, z - 3, x + 3, y + 2, z + 2, chiseledSandstone, chiseledSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, y + 2, z - 3, x + 3, y + 2, z - 2, chiseledSandstone, chiseledSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, y + 2, z + 3, x + 3, y + 2, z + 3, chiseledSandstone, chiseledSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, -1, z - 3, x - 3, -1, z + 2, cutSandstone, cutSandstone, true);
      this.m_73441_(level, boundingBox, x + 3, -1, z - 3, x + 3, -1, z + 2, cutSandstone, cutSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, -1, z - 3, x + 3, -1, z - 2, cutSandstone, cutSandstone, true);
      this.m_73441_(level, boundingBox, x - 3, -1, z + 3, x + 3, -1, z + 3, cutSandstone, cutSandstone, true);
      this.archeology$placeSandBox(x - 2, y + 1, z - 2, x + 2, y + 3, z + 2);
      this.archeology$placeCollapsedRoof(level, boundingBox, x - 2, y + 4, z - 2, x + 2, z + 2);
      BlockState orangeTerracotta = Blocks.f_50288_.m_49966_();
      BlockState blueTerracotta = Blocks.f_50298_.m_49966_();
      this.m_73434_(level, blueTerracotta, x, y, z, boundingBox);
      this.m_73434_(level, orangeTerracotta, x + 1, y, z - 1, boundingBox);
      this.m_73434_(level, orangeTerracotta, x + 1, y, z + 1, boundingBox);
      this.m_73434_(level, orangeTerracotta, x - 1, y, z - 1, boundingBox);
      this.m_73434_(level, orangeTerracotta, x - 1, y, z + 1, boundingBox);
      this.m_73434_(level, orangeTerracotta, x + 2, y, z, boundingBox);
      this.m_73434_(level, orangeTerracotta, x - 2, y, z, boundingBox);
      this.m_73434_(level, orangeTerracotta, x, y, z + 2, boundingBox);
      this.m_73434_(level, orangeTerracotta, x, y, z - 2, boundingBox);
      this.m_73434_(level, orangeTerracotta, x + 3, y, z, boundingBox);
      this.archeology$placeSand(x + 3, y + 1, z);
      this.archeology$placeSand(x + 3, y + 2, z);
      this.m_73434_(level, cutSandstone, x + 4, y + 1, z, boundingBox);
      this.m_73434_(level, chiseledSandstone, x + 4, y + 2, z, boundingBox);
      this.m_73434_(level, orangeTerracotta, x - 3, y, z, boundingBox);
      this.archeology$placeSand(x - 3, y + 1, z);
      this.archeology$placeSand(x - 3, y + 2, z);
      this.m_73434_(level, cutSandstone, x - 4, y + 1, z, boundingBox);
      this.m_73434_(level, chiseledSandstone, x - 4, y + 2, z, boundingBox);
      this.m_73434_(level, orangeTerracotta, x, y, z + 3, boundingBox);
      this.archeology$placeSand(x, y + 1, z + 3);
      this.archeology$placeSand(x, y + 2, z + 3);
      this.m_73434_(level, orangeTerracotta, x, y, z - 3, boundingBox);
      this.archeology$placeSand(x, y + 1, z - 3);
      this.archeology$placeSand(x, y + 2, z - 3);
      this.m_73434_(level, cutSandstone, x, y + 1, z - 4, boundingBox);
      this.m_73434_(level, chiseledSandstone, x, -2, z - 4, boundingBox);
   }

   @Unique
   private void archeology$placeSand(int x, int y, int z) {
      this.archeology$potentialSuspiciousSandWorldPositions.add(this.m_163582_(x, y, z));
   }

   @Unique
   private void archeology$placeSandBox(int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo) {
      for (int y = yFrom; y <= yTo; y++) {
         for (int x = xFrom; x <= xTo; x++) {
            for (int z = zFrom; z <= zTo; z++) {
               this.archeology$placeSand(x, y, z);
            }
         }
      }
   }

   @Unique
   private void archeology$placeCollapsedRoofPiece(WorldGenLevel level, int x, int y, int z, BoundingBox boundingBox) {
      if (level.m_213780_().m_188501_() < 0.33F) {
         this.m_73434_(level, Blocks.f_50062_.m_49966_(), x, y, z, boundingBox);
      } else {
         this.m_73434_(level, Blocks.f_49992_.m_49966_(), x, y, z, boundingBox);
      }
   }

   @Unique
   private void archeology$placeCollapsedRoof(WorldGenLevel level, BoundingBox boundingBox, int xFrom, int y, int zFrom, int xTo, int zTo) {
      for (int x = xFrom; x <= xTo; x++) {
         for (int z = zFrom; z <= zTo; z++) {
            this.archeology$placeCollapsedRoofPiece(level, x, y, z, boundingBox);
         }
      }

      RandomSource random = RandomSource.m_216335_(level.m_7328_()).m_188582_().m_224542_(this.m_163582_(xFrom, y, zFrom));
      int x = random.m_216332_(xFrom, xTo);
      int z = random.m_216332_(zFrom, zTo);
      this.archeology$randomCollapsedRoofPos = new BlockPos(this.m_73392_(x, z), this.m_73544_(y), this.m_73525_(x, z));
   }

   @Override
   public List<BlockPos> archeology$getPotentialSuspiciousSandWorldPositions() {
      return this.archeology$potentialSuspiciousSandWorldPositions;
   }

   @Override
   public BlockPos archeology$getRandomCollapsedRoofPos() {
      return this.archeology$randomCollapsedRoofPos;
   }
}
