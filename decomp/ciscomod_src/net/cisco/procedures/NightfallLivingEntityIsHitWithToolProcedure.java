package net.cisco.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class NightfallLivingEntityIsHitWithToolProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         boolean execute = false;
         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
            > (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.0F) {
            if (entity instanceof LivingEntity _entity) {
               _entity.m_21153_(
                  (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                     - (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 50.0F
               );
            }
         } else if ((entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
            < (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 4.0F) {
            entity.m_6469_(DamageSource.f_19311_, (float)((double)(entity instanceof LivingEntity _livEntxxxx ? _livEntxxxx.m_21223_() : -1.0F) / 1.2));
         }
      }
   }
}
