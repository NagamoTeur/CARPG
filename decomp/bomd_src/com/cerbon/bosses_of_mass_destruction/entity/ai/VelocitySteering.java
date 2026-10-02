package com.cerbon.bosses_of_mass_destruction.entity.ai;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntity;
import net.minecraft.world.phys.Vec3;

public class VelocitySteering implements ISteering {
   private final IEntity entity;
   private final double maxVelocity;
   private final double inverseMass;

   public VelocitySteering(IEntity entity, double maxVelocity, double mass) {
      if (mass == 0.0) {
         throw new IllegalArgumentException("Mass cannot be zero");
      } else {
         this.entity = entity;
         this.maxVelocity = maxVelocity;
         this.inverseMass = 1.0 / mass;
      }
   }

   @Override
   public Vec3 accelerateTo(Vec3 target) {
      return target.m_82546_(this.entity.getPos()).m_82541_().m_82490_(this.maxVelocity).m_82546_(this.entity.getDeltaMovement()).m_82490_(this.inverseMass);
   }
}
