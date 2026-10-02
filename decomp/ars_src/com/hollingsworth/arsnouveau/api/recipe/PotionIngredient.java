package com.hollingsworth.arsnouveau.api.recipe;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.ItemValue;

@Deprecated
public class PotionIngredient extends Ingredient {
   private final ItemStack stack;

   public PotionIngredient(ItemStack stack) {
      super(Stream.of(new ItemValue(stack)));
      this.stack = stack;
   }

   public static PotionIngredient fromPotion(Potion potion) {
      ItemStack stack = new ItemStack(Items.f_42589_);
      PotionUtils.m_43549_(stack, potion);
      return new PotionIngredient(stack);
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public boolean test(@Nullable ItemStack input) {
      return input == null
         ? false
         : this.stack.m_41720_() == input.m_41720_()
            && PotionUtils.m_43579_(input).equals(PotionUtils.m_43579_(this.stack))
            && PotionUtils.m_43571_(input).equals(PotionUtils.m_43571_(this.stack));
   }

   public boolean isSimple() {
      return false;
   }
}
