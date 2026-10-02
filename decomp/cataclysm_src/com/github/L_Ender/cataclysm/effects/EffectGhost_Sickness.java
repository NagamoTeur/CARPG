package com.github.L_Ender.cataclysm.effects;

import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EffectGhost_Sickness extends MobEffect {
   public EffectGhost_Sickness() {
      super(MobEffectCategory.HARMFUL, 9722673);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
   }

   public boolean m_6584_(int duration, int amplifier) {
      int k = 50 >> amplifier;
      return k > 0 ? duration % k == 0 : true;
   }

   public List<ItemStack> getCurativeItems() {
      return List.of();
   }
}
