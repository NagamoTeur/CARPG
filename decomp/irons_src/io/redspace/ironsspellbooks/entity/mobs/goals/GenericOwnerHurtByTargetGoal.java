package io.redspace.ironsspellbooks.entity.mobs.goals;

import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class GenericOwnerHurtByTargetGoal extends TargetGoal {
   private final Mob entity;
   private final OwnerGetter owner;
   private LivingEntity ownerLastHurtBy;
   private int timestamp;

   public GenericOwnerHurtByTargetGoal(Mob entity, OwnerGetter getOwner) {
      super(entity, false);
      this.entity = entity;
      this.owner = getOwner;
      this.m_7021_(EnumSet.of(Flag.TARGET));
   }

   public boolean m_8036_() {
      LivingEntity owner = this.owner.get();
      if (owner == null) {
         return false;
      } else {
         this.ownerLastHurtBy = owner.m_21188_();
         if (this.ownerLastHurtBy != null && !this.ownerLastHurtBy.m_7307_(this.f_26135_)) {
            int i = owner.m_21213_();
            return i != this.timestamp
               && this.m_26150_(this.ownerLastHurtBy, TargetingConditions.f_26872_)
               && (!(this.ownerLastHurtBy instanceof MagicSummon summon) || summon.getSummoner() != owner);
         } else {
            return false;
         }
      }
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.ownerLastHurtBy);
      this.f_26135_.m_6274_().m_21882_(MemoryModuleType.f_26372_, this.ownerLastHurtBy, 200L);
      LivingEntity owner = this.owner.get();
      if (owner != null) {
         this.timestamp = owner.m_21213_();
      }

      super.m_8056_();
   }
}
