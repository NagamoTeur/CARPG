package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public interface IFilter {
   boolean shouldResolveOnBlock(BlockHitResult var1);

   default boolean shouldResolveOnBlock(BlockPos pos, Direction direction) {
      return this.shouldResolveOnBlock(
         new BlockHitResult(new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), direction, pos, false)
      );
   }

   boolean shouldResolveOnEntity(EntityHitResult var1);

   default boolean shouldResolveOnEntity(Entity entity) {
      return this.shouldResolveOnEntity(new EntityHitResult(entity));
   }

   default boolean shouldAffect(HitResult rayTraceResult) {
      if (rayTraceResult instanceof BlockHitResult block) {
         return this.shouldResolveOnBlock(block);
      } else {
         return rayTraceResult instanceof EntityHitResult entity ? this.shouldResolveOnEntity(entity) : false;
      }
   }
}
