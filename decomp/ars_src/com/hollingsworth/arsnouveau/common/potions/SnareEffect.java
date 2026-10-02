package com.hollingsworth.arsnouveau.common.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class SnareEffect extends MobEffect {
   public SnareEffect() {
      super(MobEffectCategory.HARMFUL, 2039587);
      this.m_19472_(Attributes.f_22279_, "0dee8a21-f182-42c8-8361-1ad6186cac30", -1.0, Operation.MULTIPLY_TOTAL);
   }
}
