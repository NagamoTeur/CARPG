package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AttackAnimationGoal2<T extends LLibrary_Monster & IAnimatedEntity> extends SimpleAnimationGoal<T> {
   private final int look1;
   private final int look2;

   public AttackAnimationGoal2(T entity, Animation animation, int look1, int look2) {
      super(entity, animation);
      this.look1 = look1;
      this.look2 = look2;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public void m_8037_() {
      LivingEntity target = this.entity.m_5448_();
      if ((this.entity.getAnimationTick() >= this.look1 || target == null) && (this.entity.getAnimationTick() <= this.look2 || target == null)) {
         this.entity.m_146922_(this.entity.f_19859_);
      } else {
         this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         this.entity.m_21391_(target, 30.0F, 30.0F);
      }

      this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
   }
}
