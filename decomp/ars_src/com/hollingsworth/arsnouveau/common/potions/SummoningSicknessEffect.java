package com.hollingsworth.arsnouveau.common.potions;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

public class SummoningSicknessEffect extends MobEffect {
   protected SummoningSicknessEffect() {
      super(MobEffectCategory.HARMFUL, 2039587);
   }

   public List<ItemStack> getCurativeItems() {
      return new ArrayList<>();
   }
}
