package lykrast.meetyourfight.entity.movement;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.phys.Vec3;

public class VexMovementController extends MoveControl {
   private double slowdown = 0.5;

   public VexMovementController(Mob mob) {
      super(mob);
   }

   public VexMovementController slowdown(double slowdown) {
      this.slowdown = slowdown;
      return this;
   }

   public void m_8126_() {
      if (this.f_24981_ == Operation.MOVE_TO) {
         Vec3 vector3d = new Vec3(this.f_24975_ - this.f_24974_.m_20185_(), this.f_24976_ - this.f_24974_.m_20186_(), this.f_24977_ - this.f_24974_.m_20189_());
         double d0 = vector3d.m_82553_();
         if (d0 < this.f_24974_.m_20191_().m_82309_()) {
            this.f_24981_ = Operation.WAIT;
            this.f_24974_.m_20256_(this.f_24974_.m_20184_().m_82490_(this.slowdown));
         } else {
            this.f_24974_.m_20256_(this.f_24974_.m_20184_().m_82549_(vector3d.m_82490_(this.f_24978_ * 0.05 / d0)));
            if (this.f_24974_.m_5448_() == null) {
               Vec3 vector3d1 = this.f_24974_.m_20184_();
               this.f_24974_.m_146922_(-((float)Mth.m_14136_(vector3d1.f_82479_, vector3d1.f_82481_)) * (180.0F / (float)Math.PI));
               this.f_24974_.f_20883_ = this.f_24974_.m_146908_();
            } else {
               double d2 = this.f_24974_.m_5448_().m_20185_() - this.f_24974_.m_20185_();
               double d1 = this.f_24974_.m_5448_().m_20189_() - this.f_24974_.m_20189_();
               this.f_24974_.m_146922_(-((float)Mth.m_14136_(d2, d1)) * (180.0F / (float)Math.PI));
               this.f_24974_.f_20883_ = this.f_24974_.m_146908_();
            }
         }
      }
   }
}
