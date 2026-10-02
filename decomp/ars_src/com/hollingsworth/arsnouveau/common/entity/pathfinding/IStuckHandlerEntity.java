package com.hollingsworth.arsnouveau.common.entity.pathfinding;

public interface IStuckHandlerEntity {
   default boolean canBeStuck() {
      return true;
   }
}
