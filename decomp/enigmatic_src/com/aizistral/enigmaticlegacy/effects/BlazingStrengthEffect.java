package com.aizistral.enigmaticlegacy.effects;

import net.minecraft.world.effect.AttackDamageMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class BlazingStrengthEffect extends AttackDamageMobEffect {
   public BlazingStrengthEffect() {
      super(MobEffectCategory.BENEFICIAL, 16732160, 3.0);
      this.m_19472_(Attributes.f_22281_, "9D86B288-C0E2-45DD-95BF-AE43ED7E2116", 0.0, Operation.ADDITION);
   }
}
