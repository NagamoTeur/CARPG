package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.Nullable;

public class PotionTakeGoal extends GoToPosGoal<StarbyPotionBehavior> {
   public PotionTakeGoal(Starbuncle starbuncle, StarbyPotionBehavior behavior) {
      super(starbuncle, behavior, () -> behavior.getHeldPotion().getPotion() == Potions.f_43598_);
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.TAKING_ITEM;
   }

   @Override
   public boolean m_8036_() {
      boolean superCan = super.m_8036_();
      if (!superCan) {
         return false;
      } else if (this.behavior.isBedPowered()) {
         this.starbuncle.setBackOff(20);
         this.starbuncle.addDebugEvent(new DebugEvent("BED_POWERED", "Cannot take potion, bed is powered"));
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   @Override
   public BlockPos getDestination() {
      return this.behavior.getJarForTake();
   }

   @Override
   public boolean isDestinationStillValid(BlockPos pos) {
      return this.behavior.isPositionValidTake(pos);
   }

   @Override
   public boolean onDestinationReached() {
      if (this.starbuncle.f_19853_.m_7702_(this.targetPos) instanceof PotionJarTile jarTile) {
         BlockPos pos = this.behavior.getJarForStorage(jarTile.getData());
         if (pos == null) {
            return true;
         }

         if (this.starbuncle.f_19853_.m_7702_(pos) instanceof PotionJarTile destJar) {
            int maxRoom = destJar.getMaxFill() - destJar.getAmount();
            if (maxRoom <= 0) {
               return true;
            }

            this.behavior.setHeldPotion(jarTile.getData());
            int takeAmount = Math.min(jarTile.getAmount(), Math.min(maxRoom, 300));
            this.starbuncle.f_19853_.m_5594_(null, this.targetPos, SoundEvents.f_11781_, SoundSource.NEUTRAL, 0.5F, 1.3F);
            jarTile.remove(takeAmount);
            this.behavior.setAmount(takeAmount);
         }
      }

      return true;
   }
}
