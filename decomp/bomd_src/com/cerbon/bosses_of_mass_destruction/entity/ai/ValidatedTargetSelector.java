package com.cerbon.bosses_of_mass_destruction.entity.ai;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.IRandom;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction.IValidDirection;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntity;
import net.minecraft.world.phys.Vec3;

public class ValidatedTargetSelector implements ITargetSelector {
   private final IEntity entity;
   private final IValidDirection validator;
   private final IRandom random;
   private Vec3 previousDirection;

   public ValidatedTargetSelector(IEntity entity, IValidDirection validator, IRandom random) {
      this.entity = entity;
      this.validator = validator;
      this.random = random;
      this.previousDirection = random.getVector();
   }

   @Override
   public Vec3 getTarget() {
      Vec3 pos = this.entity.getPos();

      for (int i = 5; i < 200; i += 20) {
         Vec3 newDirection = VecUtils.rotateVector(this.previousDirection, this.random.getVector(), this.random.getDouble() * (double)i);
         if (this.validator.isValidDirection(newDirection.m_82541_())) {
            this.previousDirection = newDirection;
            return pos.m_82549_(newDirection);
         }
      }

      this.previousDirection = VecUtils.negateServer(this.previousDirection);
      return pos.m_82549_(this.previousDirection);
   }
}
