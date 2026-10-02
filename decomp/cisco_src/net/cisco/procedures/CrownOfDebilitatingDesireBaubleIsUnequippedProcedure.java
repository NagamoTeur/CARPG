package net.cisco.procedures;

import net.cisco.init.CiscoModModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CrownOfDebilitatingDesireBaubleIsUnequippedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity) {
            _entity.m_21195_((MobEffect)CiscoModModMobEffects.DEBILITATING_DESIRE.get());
         }
      }
   }
}
