package com.bobmowzie.mowziesmobs.server.ai;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class LookAtTargetGoal extends Goal {
   protected final Mob mob;
   @Nullable
   protected Entity lookAt;
   protected final float lookDistance;
   private final boolean onlyHorizontal;

   public LookAtTargetGoal(Mob mob, float distance) {
      this(mob, distance, false);
   }

   public LookAtTargetGoal(Mob mob, float distance, boolean onlyHorizontal) {
      this.mob = mob;
      this.lookDistance = distance;
      this.onlyHorizontal = onlyHorizontal;
      this.m_7021_(EnumSet.of(Flag.LOOK));
   }

   public boolean m_8036_() {
      if (this.mob.m_5448_() != null) {
         this.lookAt = this.mob.m_5448_();
      }

      return this.lookAt != null;
   }

   public boolean m_8045_() {
      if (this.lookAt == null) {
         return false;
      } else if (this.lookAt != this.mob.m_5448_()) {
         return false;
      } else {
         return !this.lookAt.m_6084_() ? false : !(this.mob.m_20280_(this.lookAt) > (double)(this.lookDistance * this.lookDistance));
      }
   }

   public void m_8041_() {
      this.lookAt = null;
   }

   public void m_8037_() {
      if (this.lookAt.m_6084_()) {
         double d0 = this.onlyHorizontal ? this.mob.m_20188_() : this.lookAt.m_20188_();
         this.mob.m_21563_().m_24946_(this.lookAt.m_20185_(), d0, this.lookAt.m_20189_());
      }
   }
}
