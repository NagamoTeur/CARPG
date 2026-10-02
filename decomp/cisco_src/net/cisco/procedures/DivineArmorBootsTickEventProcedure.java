package net.cisco.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DivineArmorBootsTickEventProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19603_, 20, 1, false, false));
         }
      }
   }
}
