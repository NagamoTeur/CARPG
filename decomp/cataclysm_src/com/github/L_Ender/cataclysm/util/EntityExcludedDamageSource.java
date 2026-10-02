package com.github.L_Ender.cataclysm.util;

import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class EntityExcludedDamageSource extends DamageSource {
   protected final List<EntityType<?>> entities;

   public EntityExcludedDamageSource(String msgId, EntityType<?>... entities) {
      super(msgId);
      this.entities = Arrays.stream(entities).toList();
   }

   public Component m_6157_(LivingEntity living) {
      LivingEntity livingentity = living.m_21232_();
      String s = "death.attack." + this.f_19326_;
      String s1 = s + ".player";
      if (livingentity != null) {
         for (EntityType<?> entity : this.entities) {
            if (livingentity.m_6095_() == entity) {
               return Component.m_237110_(s, new Object[]{living.m_5446_()});
            }
         }
      }

      return livingentity != null
         ? Component.m_237110_(s1, new Object[]{living.m_5446_(), livingentity.m_5446_()})
         : Component.m_237110_(s, new Object[]{living.m_5446_()});
   }
}
