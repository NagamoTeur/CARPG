package com.cerbon.bosses_of_mass_destruction.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;

public class BMDFoods {
   public static final FoodProperties CRYSTAL_FRUIT = new Builder()
      .m_38760_(4)
      .m_38758_(1.2F)
      .effect(() -> new MobEffectInstance(MobEffects.f_19605_, 300, 1), 1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.f_19601_, 1), 1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.f_19606_, 600, 0), 1.0F)
      .m_38765_()
      .m_38767_();
}
