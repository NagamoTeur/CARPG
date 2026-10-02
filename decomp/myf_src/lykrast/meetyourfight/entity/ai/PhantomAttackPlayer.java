package lykrast.meetyourfight.entity.ai;

import java.util.Comparator;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class PhantomAttackPlayer extends Goal {
   private final TargetingConditions predicate = TargetingConditions.m_148352_().m_148355_();
   public static final TargetingConditions DEFAULT_BUT_THROUGH_WALLS = TargetingConditions.m_148352_().m_148355_();
   private int tickDelay = 20;
   private Mob entity;

   public PhantomAttackPlayer(Mob entity) {
      this.entity = entity;
   }

   public boolean m_8036_() {
      if (this.tickDelay > 0) {
         this.tickDelay--;
         return false;
      } else {
         this.tickDelay = 60;
         List<Player> list = this.entity.f_19853_.m_45955_(this.predicate, this.entity, this.entity.m_20191_().m_82377_(16.0, 64.0, 16.0));
         if (!list.isEmpty()) {
            list.sort(Comparator.comparing(Entity::m_20186_).reversed());

            for (Player playerentity : list) {
               if (this.entity.m_21040_(playerentity, DEFAULT_BUT_THROUGH_WALLS)) {
                  this.entity.m_6710_(playerentity);
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean m_8045_() {
      LivingEntity livingentity = this.entity.m_5448_();
      return livingentity != null ? this.entity.m_21040_(livingentity, DEFAULT_BUT_THROUGH_WALLS) : false;
   }
}
