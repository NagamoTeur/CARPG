package com.hollingsworth.arsnouveau.common.entity.goal.familiar;

import com.hollingsworth.arsnouveau.api.familiar.IFamiliar;
import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class FamOwnerHurtTargetGoal extends TargetGoal {
   IFamiliar familiar;
   private LivingEntity ownerLastHurt;
   private int timestamp;

   public FamOwnerHurtTargetGoal(IFamiliar familiar) {
      super((Mob)familiar.getThisEntity(), false);
      this.familiar = familiar;
      this.m_7021_(EnumSet.of(Flag.TARGET));
   }

   public boolean m_8036_() {
      Entity owner = this.familiar.getOwnerServerside();
      LivingEntity livingentity = owner instanceof LivingEntity ? (LivingEntity)owner : null;
      if (livingentity == null) {
         return false;
      } else {
         this.ownerLastHurt = livingentity.m_21214_();
         int i = livingentity.m_21215_();
         return i != this.timestamp
            && this.m_26150_(this.ownerLastHurt, TargetingConditions.f_26872_)
            && this.familiar.wantsToAttack(this.ownerLastHurt, livingentity);
      }
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.ownerLastHurt);
      LivingEntity livingentity = (LivingEntity)this.familiar.getOwnerServerside();
      if (livingentity != null) {
         this.timestamp = livingentity.m_21215_();
      }

      super.m_8056_();
   }
}
