package com.bobmowzie.mowziesmobs.server.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;

public class MMEntityMoveHelper extends MoveControl {
   private float maxRotate = 90.0F;

   public MMEntityMoveHelper(Mob entitylivingIn, float maxRotate) {
      super(entitylivingIn);
      this.maxRotate = maxRotate;
   }

   public void m_8126_() {
      if (this.f_24981_ == Operation.STRAFE) {
         float f = (float)this.f_24974_.m_21051_(Attributes.f_22279_).m_22135_();
         float f1 = (float)this.f_24978_ * f;
         float f2 = this.f_24979_;
         float f3 = this.f_24980_;
         float f4 = Mth.m_14116_(f2 * f2 + f3 * f3);
         if (f4 < 1.0F) {
            f4 = 1.0F;
         }

         f4 = f1 / f4;
         f2 *= f4;
         f3 *= f4;
         float f5 = Mth.m_14031_(this.f_24974_.m_146908_() * (float) (Math.PI / 180.0));
         float f6 = Mth.m_14089_(this.f_24974_.m_146908_() * (float) (Math.PI / 180.0));
         float f7 = f2 * f6 - f3 * f5;
         float f8 = f3 * f6 + f2 * f5;
         PathNavigation pathnavigate = this.f_24974_.m_21573_();
         if (pathnavigate != null) {
            NodeEvaluator nodeprocessor = pathnavigate.m_26575_();
            if (nodeprocessor != null
               && nodeprocessor.m_8086_(
                     this.f_24974_.f_19853_,
                     Mth.m_14107_(this.f_24974_.m_20185_() + (double)f7),
                     Mth.m_14107_(this.f_24974_.m_20186_()),
                     Mth.m_14107_(this.f_24974_.m_20189_() + (double)f8)
                  )
                  != BlockPathTypes.WALKABLE) {
               this.f_24979_ = 1.0F;
               this.f_24980_ = 0.0F;
               f1 = f;
            }
         }

         this.f_24974_.m_7910_(f1);
         this.f_24974_.m_21564_(this.f_24979_);
         this.f_24974_.m_21570_(this.f_24980_);
         this.f_24981_ = Operation.WAIT;
      } else if (this.f_24981_ == Operation.MOVE_TO) {
         this.f_24981_ = Operation.WAIT;
         double d0 = this.f_24975_ - this.f_24974_.m_20185_();
         double d1 = this.f_24977_ - this.f_24974_.m_20189_();
         double d2 = this.f_24976_ - this.f_24974_.m_20186_();
         double d3 = d0 * d0 + d2 * d2 + d1 * d1;
         if (d3 < 2.5000003E-7F) {
            this.f_24974_.m_21564_(0.0F);
            return;
         }

         float f9 = (float)(Mth.m_14136_(d1, d0) * (180.0 / Math.PI)) - 90.0F;
         this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), f9, this.maxRotate));
         this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21051_(Attributes.f_22279_).m_22135_()));
         if (d2 > (double)this.f_24974_.f_19793_ && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.f_24974_.m_20205_())) {
            this.f_24974_.m_21569_().m_24901_();
            this.f_24981_ = Operation.JUMPING;
         }
      } else if (this.f_24981_ == Operation.JUMPING) {
         this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21051_(Attributes.f_22279_).m_22135_()));
         if (this.f_24974_.m_20096_()) {
            this.f_24981_ = Operation.WAIT;
         }
      } else {
         this.f_24974_.m_21564_(0.0F);
      }
   }
}
