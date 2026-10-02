package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.thirdlife.iterrpg.init.IterRpgModMobEffects;

public class AttackCursedProcedure {
   public static boolean execute(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         boolean agr = false;
         if (entity instanceof LivingEntity _livEnt && _livEnt.m_21023_((MobEffect)IterRpgModMobEffects.CURSED.get())) {
            return true;
         }

         return false;
      }
   }
}
