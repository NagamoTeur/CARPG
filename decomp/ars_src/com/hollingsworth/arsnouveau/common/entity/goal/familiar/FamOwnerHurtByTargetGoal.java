package com.hollingsworth.arsnouveau.common.entity.goal.familiar;

import com.hollingsworth.arsnouveau.api.familiar.IFamiliar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class FamOwnerHurtByTargetGoal extends TargetGoal {
   IFamiliar familiar;
   private LivingEntity ownerLastHurtBy;
   private int timestamp;

   public FamOwnerHurtByTargetGoal(IFamiliar familiar) {
      super((Mob)familiar.getThisEntity(), false);
      this.familiar = familiar;
   }

   public boolean m_8036_() {
      Entity owner = this.familiar.getOwnerServerside();
      LivingEntity livingentity = owner instanceof LivingEntity ? (LivingEntity)owner : null;
      if (livingentity == null) {
         return false;
      } else {
         this.ownerLastHurtBy = livingentity.m_21188_();
         int i = livingentity.m_21213_();
         return i != this.timestamp
            && this.m_26150_(this.ownerLastHurtBy, TargetingConditions.f_26872_)
            && this.familiar.wantsToAttack(this.ownerLastHurtBy, livingentity);
      }
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.ownerLastHurtBy);
      LivingEntity livingentity = (LivingEntity)this.familiar.getOwnerServerside();
      if (livingentity != null) {
         this.timestamp = livingentity.m_21213_();
      }

      super.m_8056_();
   }
}
