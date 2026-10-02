package com.hollingsworth.arsnouveau.api.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SummonUtil {
   public static boolean canSummonTakeDamage(DamageSource source) {
      return source.m_19378_() || source.m_7639_() != null && source.m_7639_() instanceof Player;
   }

   public static void healOverTime(LivingEntity entity) {
      if (!entity.f_19853_.f_46443_ && entity.f_19853_.m_46467_() % 20L == 0L && !entity.m_21224_()) {
         entity.m_5634_(1.0F);
      }
   }
}
