package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Internal_Animation_Monster;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class InternalStateGoal extends Goal {
   protected final Internal_Animation_Monster entity;
   private final int getattackstate;
   private final int attackstate;
   protected final int attackendstate;
   private final int attackfinaltick;
   protected final int attackseetick;

   public InternalStateGoal(Internal_Animation_Monster entity, int getattackstate, int attackstate, int attackendstate, int attackfinaltick, int attackseetick) {
      this.entity = entity;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackfinaltick = attackfinaltick;
      this.attackseetick = attackseetick;
   }

   public InternalStateGoal(
      Internal_Animation_Monster entity, int getattackstate, int attackstate, int attackendstate, int attackfinaltick, int attackseetick, boolean interruptsAI
   ) {
      this.entity = entity;
      if (interruptsAI) {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackfinaltick = attackfinaltick;
      this.attackseetick = attackseetick;
   }

   public InternalStateGoal(
      Internal_Animation_Monster entity,
      int getattackstate,
      int attackstate,
      int attackendstate,
      int attackfinaltick,
      int attackseetick,
      EnumSet<Flag> interruptFlagTypes
   ) {
      this.entity = entity;
      this.m_7021_(interruptFlagTypes);
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackfinaltick = attackfinaltick;
      this.attackseetick = attackseetick;
   }

   public boolean m_8036_() {
      return this.entity.getAttackState() == this.getattackstate;
   }

   public void m_8056_() {
      if (this.getattackstate != this.attackstate) {
         this.entity.setAttackState(this.attackstate);
      }
   }

   public void m_8041_() {
      this.entity.setAttackState(this.attackendstate);
   }

   public boolean m_8045_() {
      return this.attackfinaltick > 0 ? this.entity.attackTicks <= this.attackfinaltick && this.entity.getAttackState() == this.attackstate : this.m_8036_();
   }

   public void m_8037_() {
      LivingEntity target = this.entity.m_5448_();
      if (this.entity.attackTicks < this.attackseetick && target != null) {
         this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         this.entity.m_21391_(target, 30.0F, 30.0F);
      } else {
         this.entity.m_146922_(this.entity.f_19859_);
      }
   }

   public boolean m_183429_() {
      return true;
   }
}
