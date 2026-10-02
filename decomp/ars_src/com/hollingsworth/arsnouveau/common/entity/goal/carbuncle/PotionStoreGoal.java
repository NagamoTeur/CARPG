package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.alchemy.Potions;
import org.jetbrains.annotations.Nullable;

public class PotionStoreGoal extends GoToPosGoal<StarbyPotionBehavior> {
   public PotionStoreGoal(Starbuncle starbuncle, StarbyPotionBehavior behavior) {
      super(starbuncle, behavior, () -> behavior.getHeldPotion().getPotion() != Potions.f_43598_);
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.STORING_ITEM;
   }

   @Nullable
   @Override
   public BlockPos getDestination() {
      return this.behavior.getJarForStorage(this.behavior.getHeldPotion());
   }

   @Override
   public boolean isDestinationStillValid(BlockPos pos) {
      return this.behavior.isPositionValidStore(pos, this.behavior.getHeldPotion());
   }

   @Override
   public boolean onDestinationReached() {
      if (this.starbuncle.f_19853_.m_7702_(this.targetPos) instanceof PotionJarTile jarTile) {
         int room = jarTile.getMaxFill() - jarTile.getAmount();
         int diff = Math.min(room, this.behavior.getAmount());
         jarTile.add(this.behavior.getHeldPotion(), diff);
         this.behavior.setHeldPotion(new PotionData());
         this.starbuncle.f_19853_.m_5594_(null, this.targetPos, SoundEvents.f_11778_, SoundSource.NEUTRAL, 0.5F, 1.3F);
         this.behavior.setAmount(this.behavior.getAmount() - diff);
      }

      return true;
   }
}
