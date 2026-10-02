package com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class CanMoveThrough implements IValidDirection {
   private final Entity entity;
   private final double reactionDistance;

   public CanMoveThrough(Entity entity, double reactionDistance) {
      this.entity = entity;
      this.reactionDistance = reactionDistance;
   }

   @Override
   public boolean isValidDirection(Vec3 normedDirection) {
      Vec3 reactionDirection = normedDirection.m_82490_(this.reactionDistance).m_82549_(this.entity.m_20184_());
      Vec3 target = this.entity.m_20182_().m_82549_(reactionDirection);
      boolean noBlockCollisions = MathUtils.willAABBFit(this.entity.m_20191_(), reactionDirection, box -> !this.entity.f_19853_.m_45756_(this.entity, box));
      ClipContext context = new ClipContext(this.entity.m_20182_().m_82549_(normedDirection.m_82490_(1.0)), target, Block.COLLIDER, Fluid.ANY, this.entity);
      HitResult blockCollision = this.entity.f_19853_.m_45547_(context);
      boolean noFluidCollisions = blockCollision.m_6662_() == Type.MISS;
      return noFluidCollisions && noBlockCollisions;
   }
}
