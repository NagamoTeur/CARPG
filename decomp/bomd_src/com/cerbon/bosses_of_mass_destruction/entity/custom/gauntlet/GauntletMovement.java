package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.ModRandom;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.entity.ai.ValidatedTargetSelector;
import com.cerbon.bosses_of_mass_destruction.entity.ai.VelocitySteering;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.VelocityGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction.CanMoveThrough;
import com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction.InDesiredRange;
import com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction.ValidDirectionAnd;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityAdapter;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GauntletMovement {
   private final GauntletEntity entity;
   private final double reactionDistance = 4.0;
   private final EntityAdapter iEntity;
   private final double tooFarFromTargetDistance = 25.0;
   private final double tooCloseToTargetDistance = 5.0;

   public GauntletMovement(GauntletEntity entity) {
      this.entity = entity;
      this.iEntity = new EntityAdapter(entity);
   }

   public VelocityGoal buildAttackMovement() {
      Supplier<Vec3> targetPos = this.entity::safeGetTargetPos;
      ValidDirectionAnd canMoveTowardsPositionValidator = this.getValidDirectionAnd(targetPos);
      ValidatedTargetSelector targetSelector = new ValidatedTargetSelector(this.iEntity, canMoveTowardsPositionValidator, new ModRandom());
      return new VelocityGoal(this::moveAndLookAtTarget, new VelocitySteering(this.iEntity, this.entity.m_21133_(Attributes.f_22280_), 120.0), targetSelector);
   }

   @NotNull
   private ValidDirectionAnd getValidDirectionAnd(Supplier<Vec3> targetPos) {
      Function<Vec3, Boolean> tooCloseToTarget = vec3 -> this.getWithinDistancePredicate(5.0, targetPos).apply(vec3);
      Function<Vec3, Boolean> tooFarFromTarget = vec3 -> !this.getWithinDistancePredicate(25.0, targetPos).apply(vec3);
      Function<Vec3, Boolean> movingToTarget = vec3 -> MathUtils.movingTowards(this.entity.safeGetTargetPos(), this.entity.m_20182_(), vec3);
      return new ValidDirectionAnd(Arrays.asList(new CanMoveThrough(this.entity, 4.0), new InDesiredRange(tooCloseToTarget, tooFarFromTarget, movingToTarget)));
   }

   private void moveAndLookAtTarget(Vec3 velocity) {
      BMDUtils.addDeltaMovement(this.entity, velocity);
      LivingEntity target = this.entity.m_5448_();
      if (target != null) {
         this.entity.m_21563_().m_24964_(MobUtils.eyePos(target));
         this.entity.m_21391_(target, (float)this.entity.m_21529_(), (float)this.entity.m_8132_());
      }
   }

   private Function<Vec3, Boolean> getWithinDistancePredicate(double distance, Supplier<Vec3> targetPos) {
      return vec3 -> {
         Vec3 target = this.entity.m_20182_().m_82549_(vec3.m_82490_(4.0));
         return MathUtils.withinDistance(target, targetPos.get(), distance);
      };
   }
}
