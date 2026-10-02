package net.cisco.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EquillibriumLivingEntityIsHitWithToolProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
            > (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 10.0F) {
            if (entity instanceof LivingEntity _entity) {
               _entity.m_21153_(
                  (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                     - (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 25.0F
               );
            }
         } else if ((entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
            < (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 10.0F) {
            entity.m_6469_(DamageSource.f_19318_, 1.0F);
         }
      }
   }
}
