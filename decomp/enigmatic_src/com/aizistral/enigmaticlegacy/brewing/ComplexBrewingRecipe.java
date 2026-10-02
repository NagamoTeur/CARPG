package com.aizistral.enigmaticlegacy.brewing;

import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.helpers.PotionHelper;
import java.util.HashMap;
import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;

public class ComplexBrewingRecipe extends AbstractBrewingRecipe {
   @Nonnull
   private final HashMap<Ingredient, Ingredient> processingMappings;
   @Nonnull
   private final ItemStack output;

   public ComplexBrewingRecipe(HashMap<Ingredient, Ingredient> ingredientCompliances, ItemStack output) {
      this(ingredientCompliances, output, new ResourceLocation("enigmaticlegacy", ItemNBTHelper.getString(output, "EnigmaticPotion", "unknown")));
   }

   public ComplexBrewingRecipe(HashMap<Ingredient, Ingredient> ingredientCompliances, ItemStack output, ResourceLocation name) {
      super(name);
      this.processingMappings = ingredientCompliances;
      this.output = output;
   }

   public boolean isInput(@Nonnull ItemStack stack) {
      for (Ingredient ing : this.processingMappings.keySet()) {
         if (this.isInput(stack, ing)) {
            return true;
         }
      }

      return false;
   }

   public boolean isInput(@Nonnull ItemStack stack, Ingredient ingredient) {
      for (ItemStack testStack : ingredient.m_43908_()) {
         if (testStack.m_41720_().equals(stack.m_41720_())
            && PotionUtils.m_43579_(testStack).equals(PotionUtils.m_43579_(stack))
            && PotionHelper.getAdvancedPotion(testStack).equals(PotionHelper.getAdvancedPotion(stack))) {
            return true;
         }
      }

      return false;
   }

   public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
      for (Ingredient ing : this.processingMappings.keySet()) {
         if (this.isInput(input, ing) && this.processingMappings.get(ing).test(ingredient)) {
            return this.output.m_41777_();
         }
      }

      return ItemStack.f_41583_;
   }

   public boolean isIngredient(ItemStack stack) {
      if (stack != null) {
         for (Ingredient ing : this.processingMappings.values()) {
            if (ing.test(stack)) {
               return true;
            }
         }
      }

      return false;
   }

   public HashMap<Ingredient, Ingredient> getProcessingMappings() {
      return this.processingMappings;
   }

   public ItemStack getOutput() {
      return this.output;
   }
}
