package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class BurningDashEffect extends MobEffect {
   public BurningDashEffect(MobEffectCategory pCategory, int pColor) {
      super(pCategory, pColor);
   }

   public void m_6742_(LivingEntity livingEntity, int amplifier) {
      List<Entity> list = livingEntity.f_19853_.m_45933_(livingEntity, livingEntity.m_20191_().m_82377_(0.25, 0.5, 0.25));
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (entity instanceof LivingEntity) {
               DamageSources.applyDamage(entity, (float)amplifier, ((AbstractSpell)SpellRegistry.BURNING_DASH_SPELL.get()).getDamageSource(livingEntity));
               entity.f_19802_ = 20;
            }
         }
      } else if (livingEntity.f_19862_) {
         livingEntity.m_21195_(this);
      }

      livingEntity.f_19789_ = 0.0F;
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }

   public void m_6385_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6385_(pLivingEntity, pAttributeMap, pAmplifier);
      pLivingEntity.m_21155_(4, true);
   }

   public void m_6386_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6386_(pLivingEntity, pAttributeMap, pAmplifier);
      pLivingEntity.m_21155_(4, false);
   }
}
