package com.cerbon.bosses_of_mass_destruction.entity.ai.action;

import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import net.minecraft.world.entity.PathfinderMob;

public class ThrowProjectileAction implements IAction {
   private final PathfinderMob entity;
   private final ProjectileThrower projectileThrower;

   public ThrowProjectileAction(PathfinderMob entity, ProjectileThrower projectileThrower) {
      this.entity = entity;
      this.projectileThrower = projectileThrower;
   }

   @Override
   public void perform() {
      if (this.entity.m_5448_() != null) {
         this.projectileThrower.throwProjectile(this.entity.m_5448_().m_20191_().m_82399_());
      }
   }
}
