package net.thirdlife.iterrpg.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.thirdlife.iterrpg.procedures.ArcaneConductionFunctionProcedure;
import net.thirdlife.iterrpg.procedures.ArcaneConductionResetProcedure;

public class ArcaneConductionMobEffect extends MobEffect {
   public ArcaneConductionMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -3407668);
   }

   public String m_19481_() {
      return "effect.iter_rpg.arcane_conduction";
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      ArcaneConductionFunctionProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      ArcaneConductionResetProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
