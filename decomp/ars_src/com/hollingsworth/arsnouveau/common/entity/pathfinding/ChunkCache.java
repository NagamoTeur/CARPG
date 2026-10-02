package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import com.hollingsworth.arsnouveau.common.util.WorldUtil;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunk.EntityCreationType;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChunkCache implements LevelReader {
   private final DimensionType dimType;
   protected int chunkX;
   protected int chunkZ;
   protected LevelChunk[][] chunkArray;
   protected boolean empty;
   protected Level world;

   public ChunkCache(Level worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn, DimensionType type) {
      this.world = worldIn;
      this.chunkX = posFromIn.m_123341_() - subIn >> 4;
      this.chunkZ = posFromIn.m_123343_() - subIn >> 4;
      int i = posToIn.m_123341_() + subIn >> 4;
      int j = posToIn.m_123343_() + subIn >> 4;
      this.chunkArray = new LevelChunk[i - this.chunkX + 1][j - this.chunkZ + 1];
      this.empty = true;

      for (int k = this.chunkX; k <= i; k++) {
         for (int l = this.chunkZ; l <= j; l++) {
            if (WorldUtil.isEntityChunkLoaded(this.world, new ChunkPos(k, l))) {
               this.chunkArray[k - this.chunkX][l - this.chunkZ] = (LevelChunk)worldIn.m_6522_(k, l, ChunkStatus.f_62326_, false);
            }
         }
      }

      this.dimType = type;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isEmpty() {
      return this.empty;
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos pos) {
      return this.getTileEntity(pos, EntityCreationType.CHECK);
   }

   @Nullable
   public BlockEntity getTileEntity(BlockPos pos, EntityCreationType createType) {
      int i = (pos.m_123341_() >> 4) - this.chunkX;
      int j = (pos.m_123343_() >> 4) - this.chunkZ;
      return !this.withinBounds(i, j) ? null : this.chunkArray[i][j].m_5685_(pos, createType);
   }

   public BlockState m_8055_(BlockPos pos) {
      if (pos.m_123342_() >= this.m_141937_() && pos.m_123342_() < this.m_151558_()) {
         int i = (pos.m_123341_() >> 4) - this.chunkX;
         int j = (pos.m_123343_() >> 4) - this.chunkZ;
         if (i >= 0 && i < this.chunkArray.length && j >= 0 && j < this.chunkArray[i].length) {
            LevelChunk chunk = this.chunkArray[i][j];
            if (chunk != null) {
               return chunk.m_8055_(pos);
            }
         }
      }

      return Blocks.f_50016_.m_49966_();
   }

   public FluidState m_6425_(BlockPos pos) {
      if (pos.m_123342_() >= this.m_141937_() && pos.m_123342_() < this.m_151558_()) {
         int i = (pos.m_123341_() >> 4) - this.chunkX;
         int j = (pos.m_123343_() >> 4) - this.chunkZ;
         if (i >= 0 && i < this.chunkArray.length && j >= 0 && j < this.chunkArray[i].length) {
            LevelChunk chunk = this.chunkArray[i][j];
            if (chunk != null) {
               return chunk.m_6425_(pos);
            }
         }
      }

      return Fluids.f_76191_.m_76145_();
   }

   public Holder<Biome> m_203675_(int x, int y, int z) {
      return null;
   }

   public boolean m_46859_(BlockPos pos) {
      BlockState state = this.m_8055_(pos);
      return state.m_60795_();
   }

   @Nullable
   public ChunkAccess m_6522_(int x, int z, ChunkStatus requiredStatus, boolean nonnull) {
      int i = x - this.chunkX;
      int j = z - this.chunkZ;
      return i >= 0 && i < this.chunkArray.length && j >= 0 && j < this.chunkArray[i].length ? this.chunkArray[i][j] : null;
   }

   public boolean m_7232_(int chunkX, int chunkZ) {
      return false;
   }

   public BlockPos m_5452_(Types heightmapType, BlockPos pos) {
      return null;
   }

   public int m_6924_(Types heightmapType, int x, int z) {
      return 0;
   }

   public int m_7445_() {
      return 0;
   }

   public BiomeManager m_7062_() {
      return null;
   }

   public WorldBorder m_6857_() {
      return null;
   }

   public boolean m_5450_(@Nullable Entity entityIn, VoxelShape shape) {
      return false;
   }

   public List<VoxelShape> m_183134_(@org.jetbrains.annotations.Nullable Entity p_186427_, AABB p_186428_) {
      return null;
   }

   public int m_46852_(BlockPos pos, Direction direction) {
      return this.m_8055_(pos).m_60775_(this, pos, direction);
   }

   public boolean m_5776_() {
      return false;
   }

   public int m_5736_() {
      return 0;
   }

   public DimensionType m_6042_() {
      return this.dimType;
   }

   private boolean withinBounds(int x, int z) {
      return x >= 0 && x < this.chunkArray.length && z >= 0 && z < this.chunkArray[x].length && this.chunkArray[x][z] != null;
   }

   public float m_7717_(Direction direction, boolean b) {
      return 0.0F;
   }

   public LevelLightEngine m_5518_() {
      return null;
   }
}
