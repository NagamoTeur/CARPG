package net.cisco.procedures;

import net.cisco.init.CiscoModModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CrownOfDebilitatingDesireWhileBaubleIsEquippedTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance((MobEffect)CiscoModModMobEffects.DEBILITATING_DESIRE.get(), 200, 1, false, false));
         }
      }
   }
}
