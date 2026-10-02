package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AttackAniamtionGoal3<T extends LLibrary_Monster & IAnimatedEntity> extends SimpleAnimationGoal<T> {
   public AttackAniamtionGoal3(T entity, Animation animation) {
      super(entity, animation);
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public void m_8037_() {
      this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
   }
}
