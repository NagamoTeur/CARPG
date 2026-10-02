package com.hollingsworth.arsnouveau.common.entity.goal.stalker;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FlyHelper extends MoveControl {
   private float speedFactor = 0.4F;

   public FlyHelper(WildenStalker entityIn) {
      super(entityIn);
   }

   public void m_8126_() {
      WildenStalker mob = (WildenStalker)this.f_24974_;
      if (!mob.isFlying()) {
         super.m_8126_();
      } else {
         if (mob.f_19862_) {
            mob.f_19857_ += 180.0F;
         }

         float f = (float)(mob.orbitOffset.f_82479_ - mob.m_20185_());
         float f1 = (float)(mob.orbitOffset.f_82480_ - mob.m_20186_());
         float f2 = (float)(mob.orbitOffset.f_82481_ - mob.m_20189_());
         double d0 = (double)Mth.m_14116_(f * f + f2 * f2);
         double d1 = 1.0 - (double)Mth.m_14154_(f1 * 0.7F) / d0;
         f = (float)((double)f * d1);
         f2 = (float)((double)f2 * d1);
         d0 = (double)Mth.m_14116_(f * f + f2 * f2);
         double d2 = (double)Mth.m_14116_(f * f + f2 * f2 + f1 * f1);
         float f3 = mob.f_19857_;
         float f4 = (float)Mth.m_14136_((double)f2, (double)f);
         float f5 = Mth.m_14177_(mob.f_19857_ + 90.0F);
         float f6 = Mth.m_14177_(f4 * (180.0F / (float)Math.PI));
         mob.f_19857_ = Mth.m_14148_(f5, f6, 4.0F) - 90.0F;
         mob.f_20883_ = mob.f_19857_;
         if (Mth.m_14145_(f3, mob.f_19857_) < 3.0F) {
            this.speedFactor = Mth.m_14121_(this.speedFactor, 1.8F, 0.005F * (1.8F / this.speedFactor));
         } else {
            this.speedFactor = Mth.m_14121_(this.speedFactor, 0.2F, 0.025F);
         }

         float f7 = (float)(-(Mth.m_14136_((double)(-f1), d0) * 180.0F / (float)Math.PI));
         mob.m_146926_(f7);
         float f8 = mob.m_146908_() + 90.0F;
         double d3 = (double)(this.speedFactor * Mth.m_14089_(f8 * (float) (Math.PI / 180.0))) * Math.abs((double)f / d2);
         double d4 = (double)(this.speedFactor * Mth.m_14031_(f8 * (float) (Math.PI / 180.0))) * Math.abs((double)f2 / d2);
         double d5 = (double)(this.speedFactor * Mth.m_14031_(f7 * (float) (Math.PI / 180.0))) * Math.abs((double)f1 / d2);
         Vec3 vector3d = mob.m_20184_();
         mob.m_20256_(vector3d.m_82549_(new Vec3(d3, d5, d4).m_82546_(vector3d).m_82490_(0.2)));
      }
   }
}
