package com.github.alexthe666.alexsmobs.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class AMDamageTypes {
   public static final DamageSource BEAR_FREDDY = new DamageSource("freddy");

   public static final DamageSource causeFarseerDamage(LivingEntity attacker) {
      return new AMDamageTypes.DamageSourceRandomMessages("farseer", attacker).m_19386_().m_19380_().m_238403_().m_19389_();
   }

   private static class DamageSourceRandomMessages extends EntityDamageSource {
      public DamageSourceRandomMessages(String message, Entity entity) {
         super(message, entity);
      }

      public Component m_6157_(LivingEntity attacked) {
         int type = attacked.m_217043_().m_188503_(3);
         LivingEntity livingentity = attacked.m_21232_();
         String s = "death.attack." + this.f_19326_ + "_" + type;
         String s1 = s + ".player";
         return livingentity != null
            ? Component.m_237110_(s1, new Object[]{attacked.m_5446_(), livingentity.m_5446_()})
            : Component.m_237110_(s, new Object[]{attacked.m_5446_()});
      }
   }
}
