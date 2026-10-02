package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class PredictiveChargeAttackAnimationGoal<T extends LLibrary_Monster & IAnimatedEntity> extends SimpleAnimationGoal<T> {
   protected LivingEntity target;
   private final int look1;
   private final int look2;
   private final float sensing;
   private final int charge;
   private final float motionx;
   private final float motionz;
   public double prevX;
   public double prevZ;
   private int newX;
   private int newZ;

   public PredictiveChargeAttackAnimationGoal(T entity, Animation animation, int look1, int look2, float sensing, int charge, float motionx, float motionz) {
      super(entity, animation);
      this.look1 = look1;
      this.look2 = look2;
      this.sensing = sensing;
      this.charge = charge;
      this.motionx = motionx;
      this.motionz = motionz;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public void m_8056_() {
      super.m_8056_();
      this.target = this.entity.m_5448_();
      if (this.target != null) {
         this.prevX = this.target.m_20185_();
         this.prevZ = this.target.m_20189_();
      }
   }

   public void m_8037_() {
      if ((this.entity.getAnimationTick() >= this.look1 || this.target == null) && (this.entity.getAnimationTick() <= this.look2 || this.target == null)) {
         this.entity.m_146922_(this.entity.f_19859_);
      } else {
         this.entity.m_21563_().m_24960_(this.target, 30.0F, 30.0F);
         this.entity.m_146922_(this.entity.f_20883_);
      }

      if (this.entity.getAnimationTick() == this.charge - 1 && this.target != null) {
         double x = this.target.m_20185_();
         double z = this.target.m_20189_();
         double vx = (x - this.prevX) / (double)this.charge;
         double vz = (z - this.prevZ) / (double)this.charge;
         this.newX = Mth.m_14107_(x + vx * (double)this.sensing);
         this.newZ = Mth.m_14107_(z + vz * (double)this.sensing);
      }

      if (this.entity.getAnimationTick() == this.charge && this.target != null) {
         this.entity
            .m_20334_(
               ((double)this.newX - this.entity.m_20185_()) * (double)this.motionx, 0.0, ((double)this.newZ - this.entity.m_20189_()) * (double)this.motionz
            );
      }
   }
}
