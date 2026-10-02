package com.hollingsworth.arsnouveau.common.entity.goal.wealdwalker;

import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.hollingsworth.arsnouveau.common.entity.goal.AnimatedAttackGoal;
import java.util.function.Supplier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class SmashGoal extends AnimatedAttackGoal {
   WealdWalker walker;

   public SmashGoal(WealdWalker entity, boolean followUnseen, Supplier<Boolean> canAttack, int animationID, int animationLength, int attackRange) {
      super(entity, followUnseen, canAttack, animationID, animationLength, attackRange, 1.2F);
      this.walker = entity;
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
      this.walker.m_20088_().m_135381_(WealdWalker.SMASHING, false);
   }

   @Override
   protected void attack(LivingEntity target) {
      super.attack(target);
      target.m_147240_(
         1.2F,
         (double)Mth.m_14031_(this.walker.f_19857_ * (float) (Math.PI / 180.0)),
         (double)(-Mth.m_14089_(this.walker.f_19857_ * (float) (Math.PI / 180.0)))
      );
      this.walker.smashCooldown = 60;
   }

   @Override
   public void onArrive() {
      super.onArrive();
      this.walker.m_20088_().m_135381_(WealdWalker.SMASHING, true);
   }

   @Override
   public void look(LivingEntity entity) {
   }
}
