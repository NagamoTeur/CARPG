package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;

public class UmvuthanaHurtByTargetAI extends HurtByTargetGoal {
   public UmvuthanaHurtByTargetAI(PathfinderMob entity, Class<?>... p_26040_) {
      super(entity, p_26040_);
      this.m_26044_(new Class[0]);
   }

   protected void m_26047_() {
      double d0 = this.m_7623_();
      AABB aabb = AABB.m_82333_(this.f_26135_.m_20182_()).m_82377_(d0, 10.0, d0);
      List<? extends PathfinderMob> listUmvuthana = this.f_26135_
         .f_19853_
         .m_6443_(EntityUmvuthana.class, aabb, EntitySelector.f_20408_.and(e -> ((EntityUmvuthana)e).isUmvuthiDevoted()));
      List<? extends PathfinderMob> listUmvuthi = this.f_26135_.f_19853_.m_6443_(EntityUmvuthi.class, aabb, EntitySelector.f_20408_);
      List<PathfinderMob> list = new ArrayList<>();
      list.addAll(listUmvuthana);
      list.addAll(listUmvuthi);

      label56:
      for (Mob mob : list) {
         if (this.f_26135_ != mob
            && mob.m_5448_() == null
            && (!(this.f_26135_ instanceof TamableAnimal) || ((TamableAnimal)this.f_26135_).m_21826_() == ((TamableAnimal)mob).m_21826_())
            && !mob.m_7307_(this.f_26135_.m_21188_())) {
            if (this.f_26036_ != null) {
               boolean flag = false;
               Class[] var10 = this.f_26036_;
               int var11 = var10.length;
               int var12 = 0;

               while (true) {
                  if (var12 < var11) {
                     Class<?> oclass = var10[var12];
                     if (mob.getClass() != oclass) {
                        var12++;
                        continue;
                     }

                     flag = true;
                  }

                  if (!flag) {
                     break;
                  }
                  continue label56;
               }
            }

            this.m_5766_(mob, this.f_26135_.m_21188_());
         }
      }
   }

   protected double m_7623_() {
      return super.m_7623_() * 1.7;
   }
}
