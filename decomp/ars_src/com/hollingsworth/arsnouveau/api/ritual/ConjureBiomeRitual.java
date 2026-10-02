package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class ConjureBiomeRitual extends AbstractRitual {
   public int radius = 7;
   public int blocksPlaced;
   public int blocksBeforeSourceNeeded = 5;
   public ResourceKey<Biome> biome;
   public ManhattenTracker tracker;

   public ConjureBiomeRitual(ResourceKey<Biome> biome) {
      this.biome = biome;
   }

   @Override
   public void onStart() {
      super.onStart();
      if (!this.getWorld().f_46443_) {
         for (ItemStack i : this.getConsumedItems()) {
            if (i.m_204117_(ItemTagProvider.SOURCE_GEM_TAG)) {
               this.radius = this.radius + i.m_41613_();
            }
         }

         this.tracker = new ManhattenTracker(this.getPos().m_6625_(3), this.radius, 2, this.radius);
      }
   }

   @Override
   protected void tick() {
      if (!this.getWorld().f_46443_) {
         for (int i = 0; i < this.radius; i++) {
            BlockPos pos = this.getPos();
            BlockPos nextPos = this.tracker.computeNext();
            if (nextPos == null) {
               this.setFinished();
               return;
            }

            double x = (double)nextPos.m_123341_() + 0.5;
            double y = (double)nextPos.m_123342_() + 0.5;
            double z = (double)nextPos.m_123343_() + 0.5;
            double dist = BlockUtil.distanceFrom(
               new Vec3(x, y, z), new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5)
            );
            if (dist <= (double)this.radius && this.getWorld().m_8055_(nextPos).m_60767_().m_76336_()) {
               BlockState state = this.stateForPos(nextPos);
               this.setState(nextPos, state);
               RitualUtil.changeBiome(this.getWorld(), nextPos, this.biome);
               this.getWorld().m_5594_(null, nextPos, state.m_60827_().m_56777_(), SoundSource.BLOCKS, 1.0F, 1.0F);
               this.blocksPlaced++;
               if (this.blocksPlaced >= this.blocksBeforeSourceNeeded) {
                  this.blocksPlaced = 0;
                  this.setNeedsSource(true);
               }

               return;
            }
         }
      }
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return stack.m_204117_(ItemTagProvider.SOURCE_GEM_TAG);
   }

   @Override
   public int getSourceCost() {
      return 50;
   }

   public abstract BlockState stateForPos(BlockPos var1);

   public void setState(BlockPos pos, BlockState state) {
      this.getWorld().m_7731_(pos, state, 2);
   }

   @Override
   public void read(CompoundTag tag) {
      super.read(tag);
      if (tag.m_128441_("tracker")) {
         this.tracker = new ManhattenTracker(tag.m_128469_("tracker"));
      }

      this.radius = tag.m_128451_("radius");
   }

   @Override
   public void write(CompoundTag tag) {
      super.write(tag);
      if (this.tracker != null) {
         tag.m_128365_("tracker", this.tracker.serialize(new CompoundTag()));
      }

      tag.m_128405_("radius", this.radius);
   }
}
