package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import com.hollingsworth.arsnouveau.common.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import net.minecraft.world.level.pathfinder.Path;

public class PathResult<T extends Callable<Path>> {
   protected PathFindingStatus status = PathFindingStatus.IN_PROGRESS_COMPUTING;
   private volatile boolean pathReachesDestination = false;
   private Path path = null;
   private Future<Path> pathCalculation = null;
   private T job = (T)null;
   private boolean pathingDoneAndProcessed = false;

   public PathFindingStatus getStatus() {
      return this.status;
   }

   public void setStatus(PathFindingStatus s) {
      this.status = s;
   }

   public boolean isInProgress() {
      return this.isComputing() || this.status == PathFindingStatus.IN_PROGRESS_FOLLOWING;
   }

   public boolean isComputing() {
      return this.status == PathFindingStatus.IN_PROGRESS_COMPUTING;
   }

   public boolean failedToReachDestination() {
      return this.isDone() && !this.pathReachesDestination;
   }

   public boolean isPathReachingDestination() {
      return this.isDone() && this.path != null && this.pathReachesDestination;
   }

   public void setPathReachesDestination(boolean value) {
      this.pathReachesDestination = value;
   }

   public boolean isCancelled() {
      return this.status == PathFindingStatus.CANCELLED;
   }

   public int getPathLength() {
      return this.path.m_77398_();
   }

   public boolean hasPath() {
      return this.path != null;
   }

   public Path getPath() {
      return this.path;
   }

   public T getJob() {
      return this.job;
   }

   public void setJob(T job) {
      this.job = job;
   }

   public void startJob(ExecutorService executorService) {
      if (this.job != null) {
         this.pathCalculation = executorService.submit(this.job);
      }
   }

   public void processCalculationResults() {
      if (!this.pathingDoneAndProcessed) {
         try {
            this.path = this.pathCalculation.get();
            this.pathCalculation = null;
            this.setStatus(PathFindingStatus.CALCULATION_COMPLETE);
         } catch (ExecutionException | InterruptedException var2) {
            Log.getLogger().catching(var2);
         }
      }
   }

   public boolean isCalculatingPath() {
      return this.pathCalculation != null && !this.pathCalculation.isDone();
   }

   public boolean isDone() {
      if (!this.pathingDoneAndProcessed && this.pathCalculation != null && this.pathCalculation.isDone()) {
         this.processCalculationResults();
         this.pathingDoneAndProcessed = true;
      }

      return this.pathingDoneAndProcessed;
   }

   public void cancel() {
      if (this.pathCalculation != null) {
         this.pathCalculation.cancel(false);
         this.pathCalculation = null;
      }

      this.pathingDoneAndProcessed = true;
   }
}
