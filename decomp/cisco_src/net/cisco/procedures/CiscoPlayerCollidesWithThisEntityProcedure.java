package net.cisco.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CiscoPlayerCollidesWithThisEntityProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof Player _plr && _plr.m_150110_().f_35937_) {
            return;
         }

         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 60, 1));
         }

         entity.m_20254_(25);
      }
   }
}
