package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;

public abstract class AbstractAdvancedPathNavigate extends GroundPathNavigation {
   protected final Mob ourEntity;
   protected BlockPos destination;
   protected double walkSpeedFactor = 1.0;
   protected BlockPos originalDestination;
   private PathingOptions pathingOptions = new PathingOptions();

   public AbstractAdvancedPathNavigate(Mob entityLiving, Level worldIn) {
      super(entityLiving, worldIn);
      this.ourEntity = this.f_26494_;
   }

   public BlockPos getDestination() {
      return this.destination;
   }

   public abstract PathResult moveAwayFromXYZ(BlockPos var1, double var2, double var4, boolean var6);

   public abstract PathResult moveToXYZ(double var1, double var3, double var5, double var7);

   public abstract boolean tryMoveToBlockPos(BlockPos var1, double var2);

   public PathingOptions getPathingOptions() {
      return this.pathingOptions;
   }

   public Mob getOurEntity() {
      return this.ourEntity;
   }

   public abstract BlockPos getDesiredPos();

   public abstract void setStuckHandler(IStuckHandler var1);

   public abstract void setSwimSpeedFactor(double var1);

   public static enum RestrictionType {
      NONE,
      XZ,
      XYZ;
   }
}
