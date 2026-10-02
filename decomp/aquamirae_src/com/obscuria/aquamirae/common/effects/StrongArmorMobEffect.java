package com.obscuria.aquamirae.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.NotNull;

public class StrongArmorMobEffect extends MobEffect {
   public StrongArmorMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -3407668);
      this.m_19472_(Attributes.f_22284_, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CC", 0.25, Operation.MULTIPLY_BASE);
      this.m_19472_(Attributes.f_22285_, "5D6F0BA2-1286-46AC-B896-C61C5CAE92CC", 4.0, Operation.ADDITION);
   }

   @NotNull
   public String m_19481_() {
      return "effect.aquamirae.strong_armor";
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
