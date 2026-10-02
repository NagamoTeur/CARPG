package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class TrueInvisibilityEffect extends MagicMobEffect {
   int lastHurtTimestamp;

   public TrueInvisibilityEffect(MobEffectCategory pCategory, int pColor) {
      super(pCategory, pColor);
   }

   public void m_6385_(LivingEntity livingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6385_(livingEntity, pAttributeMap, pAmplifier);
      if (livingEntity instanceof Player || livingEntity instanceof AbstractSpellCastingMob) {
         MagicData.getPlayerMagicData(livingEntity).getSyncedData().addEffects(32L);
      }

      TargetingConditions targetingCondition = TargetingConditions.m_148352_().m_148355_().m_26888_(e -> ((Mob)e).m_5448_() == livingEntity);
      livingEntity.f_19853_.m_45971_(Mob.class, targetingCondition, livingEntity, livingEntity.m_20191_().m_82400_(40.0)).forEach(entityTargetingCaster -> {
         entityTargetingCaster.m_6710_(null);
         entityTargetingCaster.m_21335_(null);
         entityTargetingCaster.m_6703_(null);
         entityTargetingCaster.f_21346_.m_148105_().forEach(WrappedGoal::m_8041_);
         entityTargetingCaster.m_6274_().m_21936_(MemoryModuleType.f_26372_);
      });
      this.lastHurtTimestamp = livingEntity.m_21215_();
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.f_46443_ && this.lastHurtTimestamp != pLivingEntity.m_21215_()) {
         pLivingEntity.m_21195_(this);
      }
   }

   public void m_6386_(LivingEntity livingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6386_(livingEntity, pAttributeMap, pAmplifier);
      if (livingEntity instanceof Player || livingEntity instanceof AbstractSpellCastingMob) {
         MagicData.getPlayerMagicData(livingEntity).getSyncedData().removeEffects(32L);
      }
   }
}
