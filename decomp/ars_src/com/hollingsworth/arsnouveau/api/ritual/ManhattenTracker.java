package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.api.ritual.features.IBlockPosProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;

public class ManhattenTracker implements IBlockPosProvider {
   public int i;
   public int j;
   public int k;
   public int l;
   public int pXSize;
   public int pYSize;
   public int pZSize;
   public boolean done;
   public final MutableBlockPos cursor = new MutableBlockPos();
   public int currentDepth;
   public int maxX;
   public int maxY;
   public int x;
   public int y;
   public boolean zMirror;

   public ManhattenTracker(BlockPos pPos, int pXSize, int pYSize, int pZSize) {
      this.i = pXSize + pYSize + pZSize;
      this.j = pPos.m_123341_();
      this.k = pPos.m_123342_();
      this.l = pPos.m_123343_();
      this.pXSize = pXSize;
      this.pYSize = pYSize;
      this.pZSize = pZSize;
   }

   @Override
   public CompoundTag serialize(CompoundTag tag) {
      tag.m_128405_("i", this.i);
      tag.m_128405_("j", this.j);
      tag.m_128405_("k", this.k);
      tag.m_128405_("l", this.l);
      tag.m_128405_("pXSize", this.pXSize);
      tag.m_128405_("pYSize", this.pYSize);
      tag.m_128405_("pZSize", this.pZSize);
      tag.m_128379_("done", this.done);
      tag.m_128405_("currentDepth", this.currentDepth);
      tag.m_128405_("maxX", this.maxX);
      tag.m_128405_("maxY", this.maxY);
      tag.m_128405_("x", this.x);
      tag.m_128405_("y", this.y);
      tag.m_128379_("zMirror", this.zMirror);
      tag.m_128405_("cursorX", this.cursor.m_123341_());
      tag.m_128405_("cursorY", this.cursor.m_123342_());
      tag.m_128405_("cursorZ", this.cursor.m_123343_());
      return tag;
   }

   public ManhattenTracker(CompoundTag tag) {
      this.i = tag.m_128451_("i");
      this.j = tag.m_128451_("j");
      this.k = tag.m_128451_("k");
      this.l = tag.m_128451_("l");
      this.pXSize = tag.m_128451_("pXSize");
      this.pYSize = tag.m_128451_("pYSize");
      this.pZSize = tag.m_128451_("pZSize");
      this.done = tag.m_128471_("done");
      this.currentDepth = tag.m_128451_("currentDepth");
      this.maxX = tag.m_128451_("maxX");
      this.maxY = tag.m_128451_("maxY");
      this.x = tag.m_128451_("x");
      this.y = tag.m_128451_("y");
      this.zMirror = tag.m_128471_("zMirror");
      this.cursor.m_122178_(tag.m_128451_("cursorX"), tag.m_128451_("cursorY"), tag.m_128451_("cursorZ"));
   }

   @Override
   public BlockPos computeNext() {
      if (this.done) {
         return null;
      } else if (this.zMirror) {
         this.zMirror = false;
         this.cursor.m_142443_(this.l - (this.cursor.m_123343_() - this.l));
         return this.cursor;
      } else {
         BlockPos blockpos;
         for (blockpos = null; blockpos == null; this.y++) {
            if (this.y > this.maxY) {
               this.x++;
               if (this.x > this.maxX) {
                  this.currentDepth++;
                  if (this.currentDepth > this.i) {
                     this.done = true;
                     return null;
                  }

                  this.maxX = Math.min(this.pXSize, this.currentDepth);
                  this.x = -this.maxX;
               }

               this.maxY = Math.min(this.pYSize, this.currentDepth - Math.abs(this.x));
               this.y = -this.maxY;
            }

            int i1 = this.x;
            int j1 = this.y;
            int k1 = this.currentDepth - Math.abs(i1) - Math.abs(j1);
            if (k1 <= this.pZSize) {
               this.zMirror = k1 != 0;
               blockpos = this.cursor.m_122178_(this.j + i1, this.k + j1, this.l + k1);
            }
         }

         return blockpos;
      }
   }
}
