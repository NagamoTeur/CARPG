package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.api.ritual.features.IPlaceableFeature;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import oshi.util.tuples.Pair;

public abstract class FeaturePlacementRitual extends AbstractRitual {
   public Map<String, List<BlockPos>> featureMap = new HashMap<>();
   public int checkRadius = 7;
   public int featureIndex = 0;
   public int positionIndex = 0;
   public List<BlockPos> targetPositions = new ArrayList<>();
   public List<IPlaceableFeature> features = new ArrayList<>();
   public BlockPos lowerOffset = BlockPos.f_121853_;
   public BlockPos upperOffset = BlockPos.f_121853_;

   public abstract void addFeatures(List<IPlaceableFeature> var1);

   @Override
   public void onStart() {
      super.onStart();

      for (ItemStack i : this.getConsumedItems()) {
         if (i.m_204117_(ItemTagProvider.SOURCE_GEM_TAG)) {
            this.checkRadius = this.checkRadius + i.m_41613_();
         }
      }

      this.setup();
   }

   public void setup() {
      this.addFeatures(this.features);

      for (IPlaceableFeature feature : this.features) {
         this.featureMap.put(feature.getFeatureName(), new ArrayList<>());
      }

      this.targetPositions = this.getTargetPositions();
   }

   public List<BlockPos> getTargetPositions() {
      List<BlockPos> positions = new ArrayList<>();
      BlockPos pos = this.getPos();
      Pair<BlockPos, BlockPos> offsets = this.features.get(this.featureIndex).getCustomOffsets();
      BlockPos lowerBound = this.getPos().m_7918_(-this.checkRadius, 0, -this.checkRadius).m_121955_(this.lowerOffset).m_121955_((Vec3i)offsets.getA());
      BlockPos upperBound = this.getPos().m_7918_(this.checkRadius, 0, this.checkRadius).m_121955_(this.upperOffset).m_121955_((Vec3i)offsets.getB());

      for (BlockPos nextPos : BlockPos.m_121940_(lowerBound, upperBound)) {
         double x = (double)nextPos.m_123341_() + 0.5;
         double y = (double)nextPos.m_123342_() + 0.5;
         double z = (double)nextPos.m_123343_() + 0.5;
         double dist = BlockUtil.distanceFrom(
            new Vec3(x, y, z), new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5)
         );
         if (dist <= (double)this.checkRadius) {
            positions.add(nextPos.m_7949_());
         }
      }

      Collections.shuffle(positions);
      return positions;
   }

   @Override
   public void tick() {
      if (!this.getWorld().f_46443_) {
         while (true) {
            if (this.positionIndex >= this.targetPositions.size()) {
               this.featureIndex++;
               if (this.featureIndex >= this.features.size()) {
                  this.setFinished();
                  return;
               }

               this.targetPositions = this.getTargetPositions();
               this.positionIndex = 0;
            }

            BlockPos targetPos = this.targetPositions.get(this.positionIndex);
            IPlaceableFeature feature = this.features.get(this.featureIndex);
            if (this.isEnoughBlocksFrom(feature.getFeatureName(), targetPos, feature.distanceFromOthers())
               && feature.onPlace(this.getWorld(), targetPos, this, this.tile)) {
               this.featureMap.computeIfAbsent(feature.getFeatureName(), k -> new ArrayList<>()).add(targetPos.m_7949_());
               return;
            }

            this.positionIndex++;
         }
      }
   }

   public boolean isEnoughBlocksFrom(String feature, BlockPos targetPos, double dist) {
      if (!this.featureMap.containsKey(feature)) {
         return true;
      } else {
         for (BlockPos pos : this.featureMap.get(feature)) {
            if (BlockUtil.distanceFrom(
                  new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5),
                  new Vec3((double)targetPos.m_123341_() + 0.5, (double)targetPos.m_123342_() + 0.5, (double)targetPos.m_123343_() + 0.5)
               )
               <= dist) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      return stack.m_204117_(ItemTagProvider.SOURCE_GEM_TAG);
   }

   @Override
   public void read(CompoundTag tag) {
      super.read(tag);
      this.featureIndex = tag.m_128451_("featureIndex");
      this.positionIndex = tag.m_128451_("positionIndex");
      this.checkRadius = tag.m_128451_("checkRadius");
      this.setup();
   }

   @Override
   public void write(CompoundTag tag) {
      super.write(tag);
      tag.m_128405_("featureIndex", this.featureIndex);
      tag.m_128405_("positionIndex", this.positionIndex);
      tag.m_128405_("checkRadius", this.checkRadius);
   }
}
