package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import net.minecraft.world.entity.LivingEntity;

public class ChargeAttackAnimationGoal<T extends LLibrary_Monster & IAnimatedEntity> extends SimpleAnimationGoal<T> {
   private final int look1;
   private final int look2;
   private final int charge;
   private final float motionx;
   private final float motionz;

   public ChargeAttackAnimationGoal(T entity, Animation animation, int look1, int look2, int charge, float motionx, float motionz) {
      super(entity, animation);
      this.look1 = look1;
      this.look2 = look2;
      this.charge = charge;
      this.motionx = motionx;
      this.motionz = motionz;
   }

   public void m_8037_() {
      LivingEntity target = this.entity.m_5448_();
      if ((this.entity.getAnimationTick() >= this.look1 || target == null) && (this.entity.getAnimationTick() <= this.look2 || target == null)) {
         this.entity.m_146922_(this.entity.f_19859_);
      } else {
         this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
      }

      if (this.entity.getAnimationTick() == this.charge && target != null) {
         this.entity
            .m_20334_(
               (target.m_20185_() - this.entity.m_20185_()) * (double)this.motionx, 0.0, (target.m_20189_() - this.entity.m_20189_()) * (double)this.motionz
            );
      }
   }
}
