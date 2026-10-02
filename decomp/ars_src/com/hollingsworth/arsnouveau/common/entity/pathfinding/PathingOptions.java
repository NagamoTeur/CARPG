package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import java.util.function.Function;
import net.minecraft.world.level.block.state.BlockState;

public class PathingOptions {
   public double jumpCost = 1.1;
   public double dropCost = 1.1;
   public double onPathCost = 0.5;
   public double onRailCost = 0.1;
   public double railsExitCost = 2.0;
   public double swimCost = 1.5;
   public double swimCostEnter = 25.0;
   public double vineCost = 2.0;
   public double traverseToggleAbleCost = 2.0;
   private boolean canUseRails = false;
   private boolean canSwim = false;
   private boolean enterDoors = false;
   private boolean canOpenDoors = false;
   private boolean canClimbVines = false;
   private boolean canFitInOneCube = false;
   private Function<BlockState, Boolean> isRoad = blockState -> false;

   public boolean canOpenDoors() {
      return this.canOpenDoors;
   }

   public void setCanOpenDoors(boolean canOpenDoors) {
      this.canOpenDoors = canOpenDoors;
   }

   public boolean canUseRails() {
      return this.canUseRails;
   }

   public void setCanUseRails(boolean canUseRails) {
      this.canUseRails = canUseRails;
   }

   public boolean canSwim() {
      return this.canSwim;
   }

   public void setCanSwim(boolean canSwim) {
      this.canSwim = canSwim;
   }

   public boolean canEnterDoors() {
      return this.enterDoors;
   }

   public void setEnterDoors(boolean enterDoors) {
      this.enterDoors = enterDoors;
   }

   public void setCanFitInOneCube(boolean canFitInOneCube) {
      this.canFitInOneCube = canFitInOneCube;
   }

   public boolean canFitInOneCube() {
      return this.canFitInOneCube;
   }

   public Function<BlockState, Boolean> getIsRoad() {
      return this.isRoad;
   }

   public PathingOptions withStartSwimCost(double startSwimCost) {
      this.swimCostEnter = startSwimCost;
      return this;
   }

   public PathingOptions withSwimCost(double swimCost) {
      this.swimCost = swimCost;
      return this;
   }

   public PathingOptions withJumpCost(double jumpCost) {
      this.jumpCost = jumpCost;
      return this;
   }

   public PathingOptions withDropCost(double dropCost) {
      this.dropCost = dropCost;
      return this;
   }

   public PathingOptions withOnPathCost(double onPathCost) {
      this.onPathCost = onPathCost;
      return this;
   }

   public PathingOptions withOnRailCost(double onRailCost) {
      this.onRailCost = onRailCost;
      return this;
   }

   public PathingOptions withRailExitCost(double railExitCost) {
      this.railsExitCost = railExitCost;
      return this;
   }

   public PathingOptions withToggleCost(double toggleCost) {
      this.traverseToggleAbleCost = toggleCost;
      return this;
   }

   public void setCanClimbVines(boolean canClimbVines) {
      this.canClimbVines = canClimbVines;
   }

   public PathingOptions withRoadState(Function<BlockState, Boolean> function) {
      this.isRoad = function;
      return this;
   }

   public boolean canClimbVines() {
      return this.canClimbVines;
   }

   public PathingOptions withCanSwim(boolean canswim) {
      this.setCanSwim(canswim);
      return this;
   }

   public PathingOptions withCanEnterDoors(boolean canEnter) {
      this.setEnterDoors(canEnter);
      return this;
   }
}
