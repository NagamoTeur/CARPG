package com.github.L_Ender.cataclysm.entity.Pet.AI;

import com.github.L_Ender.cataclysm.entity.Pet.InternalAnimationPet;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class InternalPetAttackGoal extends Goal {
   protected final InternalAnimationPet entity;
   private final int getattackstate;
   private final int attackstate;
   private final int attackendstate;
   private final int attackMaxtick;
   private final int attackseetick;
   private final float attackrange;

   public InternalPetAttackGoal(
      InternalAnimationPet entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange
   ) {
      this.entity = entity;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackMaxtick = attackMaxtick;
      this.attackseetick = attackseetick;
      this.attackrange = attackrange;
   }

   public InternalPetAttackGoal(
      InternalAnimationPet entity,
      int getattackstate,
      int attackstate,
      int attackendstate,
      int attackMaxtick,
      int attackseetick,
      float attackrange,
      EnumSet<Flag> interruptFlagTypes
   ) {
      this.entity = entity;
      this.m_7021_(interruptFlagTypes);
      this.getattackstate = getattackstate;
      this.attackstate = attackstate;
      this.attackendstate = attackendstate;
      this.attackMaxtick = attackMaxtick;
      this.attackseetick = attackseetick;
      this.attackrange = attackrange;
   }

   public boolean m_8036_() {
      LivingEntity target = this.entity.m_5448_();
      return target != null && target.m_6084_() && this.entity.m_20270_(target) < this.attackrange && this.entity.getAttackState() == this.getattackstate;
   }

   public void m_8056_() {
      this.entity.setAttackState(this.attackstate);
   }

   public void m_8041_() {
      this.entity.setAttackState(this.attackendstate);
   }

   public boolean m_8045_() {
      return this.entity.getAttackState() == this.attackstate && this.entity.attackTicks <= this.attackMaxtick;
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
      return false;
   }
}
