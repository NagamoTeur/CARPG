package com.hollingsworth.arsnouveau.common.items.curios;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class DiscountRing extends AbstractManaCurio {
   public abstract int getManaDiscount();

   @Override
   public int getMaxManaBoost(ItemStack i) {
      return 10;
   }

   @Override
   public int getManaRegenBonus(ItemStack i) {
      return 1;
   }

   @Override
   public int getManaDiscount(ItemStack i, Spell spell) {
      return this.getManaDiscount();
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      tooltip2.add(Component.m_237110_("tooltip.discount_item", new Object[]{this.getManaDiscount(stack)}));
   }
}
