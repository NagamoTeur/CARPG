package io.redspace.ironsspellbooks.entity.mobs.goals;

import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class GenericOwnerHurtTargetGoal extends TargetGoal {
   private final Mob entity;
   private final OwnerGetter owner;
   private LivingEntity ownerLastHurt;
   private int timestamp;

   public GenericOwnerHurtTargetGoal(Mob entity, OwnerGetter ownerGetter) {
      super(entity, false);
      this.entity = entity;
      this.owner = ownerGetter;
      this.m_7021_(EnumSet.of(Flag.TARGET));
   }

   public boolean m_8036_() {
      LivingEntity owner = this.owner.get();
      if (owner == null) {
         return false;
      } else {
         this.ownerLastHurt = owner.m_21214_();
         int i = owner.m_21215_();
         return i != this.timestamp
            && this.m_26150_(this.ownerLastHurt, TargetingConditions.f_26872_)
            && (!(this.ownerLastHurt instanceof MagicSummon summon) || summon.getSummoner() != owner);
      }
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.ownerLastHurt);
      LivingEntity owner = this.owner.get();
      if (owner != null) {
         this.timestamp = owner.m_21215_();
      }

      super.m_8056_();
   }
}
