package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class LeviathanSwimmingLookControl extends LookControl {
   private final int maxYRotFromCenter;
   private static final int HEAD_TILT_X = 10;
   private static final int HEAD_TILT_Y = 20;

   public LeviathanSwimmingLookControl(Mob p_148061_, int p_148062_) {
      super(p_148061_);
      this.maxYRotFromCenter = p_148062_;
   }

   public void m_8128_() {
      if (this.f_186068_ > 0) {
         this.f_186068_--;
         this.m_180896_().ifPresent(p_181130_ -> this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, p_181130_, this.f_24938_));
         this.m_180897_().ifPresent(p_181128_ -> this.f_24937_.m_146926_(this.m_24956_(this.f_24937_.m_146909_(), p_181128_, this.f_24939_)));
      } else {
         this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, 10.0F);
      }

      float f = Mth.m_14177_(this.f_24937_.f_20885_ - this.f_24937_.f_20883_);
      if (f < (float)(-this.maxYRotFromCenter)) {
         this.f_24937_.f_20883_ -= 4.0F;
      } else if (f > (float)this.maxYRotFromCenter) {
         this.f_24937_.f_20883_ += 4.0F;
      }
   }
}
