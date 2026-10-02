package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import com.hollingsworth.arsnouveau.common.entity.goal.ExtendedRangeGoal;
import java.util.EnumSet;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public abstract class GoToPosGoal<T extends StarbyBehavior> extends ExtendedRangeGoal {
   public Starbuncle starbuncle;
   public T behavior;
   Supplier<Boolean> canUse;
   Supplier<Boolean> canContinue;
   public boolean isDone;
   public BlockPos targetPos;

   public GoToPosGoal(Starbuncle starbuncle, T behavior, Supplier<Boolean> canUse, Supplier<Boolean> canContinue) {
      super(30);
      this.starbuncle = starbuncle;
      this.behavior = behavior;
      this.canUse = canUse;
      this.canContinue = canContinue;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public GoToPosGoal(Starbuncle starbuncle, T behavior, Supplier<Boolean> canUse) {
      this(starbuncle, behavior, canUse, canUse);
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
      this.isDone = false;
      this.targetPos = null;
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.NONE;
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.isDone = false;
      this.targetPos = this.getDestination();
      if (this.targetPos == null) {
         this.starbuncle.setBackOff(60 + this.starbuncle.f_19853_.f_46441_.m_188503_(60));
      } else {
         this.starbuncle.addGoalDebug(this, new DebugEvent("StartedGoal", "Started goal "));
      }
   }

   @Override
   public void m_8037_() {
      super.m_8037_();
      if (this.targetPos != null) {
         if (this.ticksRunning % 100 == 0 && !this.isDestinationStillValid(this.targetPos)) {
            this.starbuncle.addDebugEvent(new DebugEvent("became_invalid", "Invalid position " + this.targetPos.toString()));
            this.isDone = true;
         } else if (BlockUtil.distanceFrom(
                  this.starbuncle.m_20182_().m_82520_(0.0, 0.5, 0.0),
                  new Vec3((double)this.targetPos.m_123341_() + 0.5, (double)this.targetPos.m_123342_() + 0.5, (double)this.targetPos.m_123343_() + 0.5)
               )
               <= 2.5 + this.extendedRange
            && this.isDestinationStillValid(this.targetPos)) {
            this.isDone = this.onDestinationReached();
         } else {
            if (this.targetPos != null) {
               this.setPath(this.targetPos);
            }
         }
      }
   }

   @Override
   public boolean m_8036_() {
      return this.canUse.get() && this.starbuncle.getBackOff() <= 0;
   }

   public boolean m_8045_() {
      return this.targetPos != null && !this.isDone && this.canContinue.get();
   }

   public void setPath(BlockPos pos) {
      this.starbuncle.getNavigation().tryMoveToBlockPos(pos, 1.3);
      this.starbuncle.addGoalDebug(this, new DebugEvent("path_set", "path set to " + this.targetPos.toString()));
      if (this.starbuncle.getNavigation().m_26570_() != null && !this.starbuncle.getNavigation().m_26570_().m_77403_()) {
         this.isDone = true;
         this.starbuncle.addGoalDebug(this, new DebugEvent("unreachable", this.targetPos.toString()));
      }
   }

   @Nullable
   public abstract BlockPos getDestination();

   public abstract boolean onDestinationReached();

   public boolean isDestinationStillValid(BlockPos pos) {
      return true;
   }
}
