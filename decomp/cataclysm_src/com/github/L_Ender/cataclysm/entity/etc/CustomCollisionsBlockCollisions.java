package com.github.L_Ender.cataclysm.entity.etc;

import com.google.common.collect.AbstractIterator;
import javax.annotation.Nullable;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomCollisionsBlockCollisions extends AbstractIterator<VoxelShape> {
   private final AABB box;
   private final CollisionContext context;
   private final Cursor3D cursor;
   private final MutableBlockPos pos;
   private final VoxelShape entityShape;
   private final CollisionGetter collisionGetter;
   private final boolean onlySuffocatingBlocks;
   @Nullable
   private BlockGetter cachedBlockGetter;
   private long cachedBlockGetterPos;

   public CustomCollisionsBlockCollisions(CollisionGetter p_186402_, @Nullable Entity p_186403_, AABB p_186404_) {
      this(p_186402_, p_186403_, p_186404_, false);
   }

   public CustomCollisionsBlockCollisions(CollisionGetter p_186406_, @Nullable Entity p_186407_, AABB p_186408_, boolean p_186409_) {
      this.context = p_186407_ == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_(p_186407_);
      this.pos = new MutableBlockPos();
      this.entityShape = Shapes.m_83064_(p_186408_);
      this.collisionGetter = p_186406_;
      this.box = p_186408_;
      this.onlySuffocatingBlocks = p_186409_;
      int i = Mth.m_14107_(p_186408_.f_82288_ - 1.0E-7) - 1;
      int j = Mth.m_14107_(p_186408_.f_82291_ + 1.0E-7) + 1;
      int k = Mth.m_14107_(p_186408_.f_82289_ - 1.0E-7) - 1;
      int l = Mth.m_14107_(p_186408_.f_82292_ + 1.0E-7) + 1;
      int i1 = Mth.m_14107_(p_186408_.f_82290_ - 1.0E-7) - 1;
      int j1 = Mth.m_14107_(p_186408_.f_82293_ + 1.0E-7) + 1;
      this.cursor = new Cursor3D(i, k, i1, j, l, j1);
   }

   @Nullable
   private BlockGetter getChunk(int p_186412_, int p_186413_) {
      int i = SectionPos.m_123171_(p_186412_);
      int j = SectionPos.m_123171_(p_186413_);
      long k = ChunkPos.m_45589_(i, j);
      if (this.cachedBlockGetter != null && this.cachedBlockGetterPos == k) {
         return this.cachedBlockGetter;
      } else {
         BlockGetter blockgetter = this.collisionGetter.m_7925_(i, j);
         this.cachedBlockGetter = blockgetter;
         this.cachedBlockGetterPos = k;
         return blockgetter;
      }
   }

   protected VoxelShape computeNext() {
      while (this.cursor.m_122304_()) {
         int i = this.cursor.m_122305_();
         int j = this.cursor.m_122306_();
         int k = this.cursor.m_122307_();
         int l = this.cursor.m_122308_();
         if (l != 3) {
            BlockGetter blockgetter = this.getChunk(i, k);
            if (blockgetter != null) {
               this.pos.m_122178_(i, j, k);
               BlockState blockstate = blockgetter.m_8055_(this.pos);
               if ((!this.onlySuffocatingBlocks || blockstate.m_60828_(blockgetter, this.pos))
                  && (l != 1 || blockstate.m_60779_())
                  && (l != 2 || blockstate.m_60713_(Blocks.f_50110_))) {
                  VoxelShape voxelshape = blockstate.m_60742_(this.collisionGetter, this.pos, this.context);
                  if (this.context instanceof EntityCollisionContext) {
                     Entity entity = ((EntityCollisionContext)this.context).m_193113_();
                     if (entity instanceof ICustomCollisions && ((ICustomCollisions)entity).canPassThrough(this.pos, blockstate, voxelshape)) {
                        continue;
                     }
                  }

                  if (voxelshape == Shapes.m_83144_()) {
                     if (this.box.m_82314_((double)i, (double)j, (double)k, (double)i + 1.0, (double)j + 1.0, (double)k + 1.0)) {
                        return voxelshape.m_83216_((double)i, (double)j, (double)k);
                     }
                  } else {
                     VoxelShape voxelshape1 = voxelshape.m_83216_((double)i, (double)j, (double)k);
                     if (Shapes.m_83157_(voxelshape1, this.entityShape, BooleanOp.f_82689_)) {
                        return voxelshape1;
                     }
                  }
               }
            }
         }
      }

      return (VoxelShape)this.endOfData();
   }
}
