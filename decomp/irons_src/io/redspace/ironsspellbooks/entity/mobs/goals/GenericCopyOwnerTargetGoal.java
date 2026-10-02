package io.redspace.ironsspellbooks.entity.mobs.goals;

import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class GenericCopyOwnerTargetGoal extends TargetGoal {
   private final OwnerGetter ownerGetter;

   public GenericCopyOwnerTargetGoal(PathfinderMob pMob, OwnerGetter ownerGetter) {
      super(pMob, false);
      this.ownerGetter = ownerGetter;
   }

   public boolean m_8036_() {
      if (this.ownerGetter.get() instanceof Mob owner
         && owner.m_5448_() != null
         && (!(owner.m_5448_() instanceof MagicSummon summon) || summon.getSummoner() != owner)) {
         return true;
      }

      return false;
   }

   public void m_8056_() {
      LivingEntity target = ((Mob)this.ownerGetter.get()).m_5448_();
      this.f_26135_.m_6710_(target);
      this.f_26135_.m_6274_().m_21882_(MemoryModuleType.f_26372_, target, 200L);
      super.m_8056_();
   }
}
