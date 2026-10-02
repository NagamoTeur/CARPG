package io.redspace.ironsspellbooks.block.alchemist_cauldron;

import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class CauldronPlatformHelper {
   public static final Predicate<ItemStack> IS_WATER = itemStack -> PotionUtils.m_43579_(itemStack) == Potions.f_43599_;

   public static boolean itemMatches(ItemStack a, ItemStack b) {
      return ItemStack.m_150942_(a, b);
   }

   public static boolean isBrewingIngredient(ItemStack stack, Level level) {
      return BrewingRecipeRegistry.isValidIngredient(stack);
   }

   public static ItemStack getNonDestructiveBrewingResult(ItemStack base, ItemStack reagent, Level level) {
      return BrewingRecipeRegistry.getOutput(base, reagent);
   }
}
