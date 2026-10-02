package io.redspace.ironsspellbooks.block.alchemist_cauldron;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

public class AlchemistCauldronRecipe {
   private final ItemStack inputStack;
   private final ItemStack ingredientStack;
   private final ItemStack resultStack;
   private int requiredBaseCount = 1;
   private int resultLimitCount = 4;

   public AlchemistCauldronRecipe(ItemStack inputStack, ItemStack ingredientStack, ItemStack resultStack) {
      this.inputStack = inputStack;
      this.ingredientStack = ingredientStack;
      this.resultStack = resultStack;
   }

   public AlchemistCauldronRecipe(Item input, Item ingredient, Item result) {
      this(new ItemStack(input), new ItemStack(ingredient), new ItemStack(result));
   }

   public AlchemistCauldronRecipe(Potion input, Item ingredient, Item result) {
      this(PotionUtils.m_43549_(new ItemStack(Items.f_42589_), input), new ItemStack(ingredient), new ItemStack(result));
   }

   public AlchemistCauldronRecipe setBaseRequirement(int i) {
      this.requiredBaseCount = i;
      return this;
   }

   public AlchemistCauldronRecipe setResultLimit(int i) {
      this.resultLimitCount = i;
      return this;
   }

   public ItemStack createOutput(ItemStack input, ItemStack ingredient, boolean ignoreCount, boolean consumeOnSuccess) {
      if (CauldronPlatformHelper.itemMatches(input, this.inputStack)
         && CauldronPlatformHelper.itemMatches(ingredient, this.ingredientStack)
         && (ignoreCount || input.m_41613_() >= this.requiredBaseCount)) {
         ItemStack result = this.resultStack.m_41777_();
         result.m_41764_(this.resultLimitCount);
         if (consumeOnSuccess) {
            input.m_41774_(this.requiredBaseCount);
            ingredient.m_41774_(1);
         }

         return result;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public ItemStack getInput() {
      ItemStack i = this.inputStack.m_41777_();
      i.m_41764_(this.requiredBaseCount);
      return i;
   }

   public ItemStack getIngredient() {
      return this.ingredientStack.m_41777_();
   }

   public ItemStack getResult() {
      ItemStack i = this.resultStack.m_41777_();
      i.m_41764_(this.resultLimitCount);
      return i;
   }
}
