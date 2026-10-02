package com.hollingsworth.arsnouveau.api.source;

import com.hollingsworth.arsnouveau.common.block.tile.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSourceMachine extends ModdedTile implements ISourceTile {
   private int source = 0;
   private int maxSource = 0;
   public static String SOURCE_TAG = "source";
   public static String MAX_SOURCE_TAG = "max_source";

   public AbstractSourceMachine(BlockEntityType<?> manaTile, BlockPos pos, BlockState state) {
      super(manaTile, pos, state);
   }

   public void m_142466_(CompoundTag tag) {
      this.source = tag.m_128451_(SOURCE_TAG);
      this.maxSource = tag.m_128451_(MAX_SOURCE_TAG);
      super.m_142466_(tag);
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128405_(SOURCE_TAG, this.getSource());
      tag.m_128405_(MAX_SOURCE_TAG, this.getMaxSource());
   }

   @Override
   public int setSource(int source) {
      if (this.source == source) {
         return this.source;
      } else {
         this.source = source;
         if (this.source > this.getMaxSource()) {
            this.source = this.getMaxSource();
         }

         if (this.source < 0) {
            this.source = 0;
         }

         this.updateBlock();
         return this.source;
      }
   }

   @Override
   public int addSource(int source) {
      return this.setSource(this.getSource() + source);
   }

   @Override
   public int getSource() {
      return this.source;
   }

   @Override
   public int removeSource(int source) {
      if (source == 0) {
         return this.getSource();
      } else {
         this.setSource(this.getSource() - source);
         this.updateBlock();
         return this.getSource();
      }
   }

   @Override
   public void setMaxSource(int max) {
      this.maxSource = max;
      this.updateBlock();
   }

   @Override
   public int getMaxSource() {
      return this.maxSource;
   }

   @Override
   public boolean canAcceptSource() {
      return this.getSource() < this.getMaxSource();
   }

   public boolean canAcceptSource(int source) {
      return this.getSource() + source <= this.getMaxSource();
   }

   public int transferSource(ISourceTile from, ISourceTile to) {
      int transferRate = this.getTransferRate(from, to);
      from.removeSource(transferRate);
      to.addSource(transferRate);
      return transferRate;
   }

   public int getTransferRate(ISourceTile from, ISourceTile to) {
      return Math.min(Math.min(from.getTransferRate(), from.getSource()), to.getMaxSource() - to.getSource());
   }

   public int transferSource(ISourceTile from, ISourceTile to, int fromTransferRate) {
      int transferRate = this.getTransferRate(from, to, fromTransferRate);
      if (transferRate == 0) {
         return 0;
      } else {
         from.removeSource(transferRate);
         to.addSource(transferRate);
         return transferRate;
      }
   }

   public int getTransferRate(ISourceTile from, ISourceTile to, int fromTransferRate) {
      return Math.min(Math.min(fromTransferRate, from.getSource()), to.getMaxSource() - to.getSource());
   }
}
