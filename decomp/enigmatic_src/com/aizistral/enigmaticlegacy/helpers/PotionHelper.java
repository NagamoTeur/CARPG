package com.aizistral.enigmaticlegacy.helpers;

import com.aizistral.enigmaticlegacy.brewing.ComplexBrewingRecipe;
import com.aizistral.enigmaticlegacy.objects.AdvancedPotion;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.enigmaticlegacy.registries.EnigmaticPotions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class PotionHelper {
   public static int getColor(ItemStack stack) {
      return isAdvancedPotion(stack) && getEffects(stack) != null && getEffects(stack).size() > 0
         ? PotionUtils.m_43564_(getEffects(stack))
         : PotionUtils.m_43575_(stack);
   }

   public static boolean isAdvancedPotion(ItemStack stack) {
      return ItemNBTHelper.verifyExistance(stack, "EnigmaticPotion");
   }

   public static List<MobEffectInstance> getEffects(ItemStack stack) {
      if (isAdvancedPotion(stack)) {
         AdvancedPotion potion = getAdvancedPotion(ItemNBTHelper.getString(stack, "EnigmaticPotion", "nothing"));
         if (potion != null) {
            return potion.getEffects();
         }
      }

      return new ArrayList<>();
   }

   public static AdvancedPotion getAdvancedPotion(ItemStack stack) {
      return isAdvancedPotion(stack) ? getAdvancedPotion(ItemNBTHelper.getString(stack, "EnigmaticPotion", "nothing")) : EnigmaticPotions.EMPTY_POTION;
   }

   public static ItemStack setAdvancedPotion(ItemStack stack, AdvancedPotion potion) {
      ItemNBTHelper.setString(stack, "EnigmaticPotion", potion.getId());
      return stack;
   }

   public static ItemStack setAdvancedPotion(ItemStack stack, String id) {
      ItemNBTHelper.setString(stack, "EnigmaticPotion", id);
      return stack;
   }

   public static AdvancedPotion getAdvancedPotion(String identifier) {
      for (AdvancedPotion potion : EnigmaticPotions.ULTIMATE_POTIONS) {
         if (potion.getId().equals(identifier)) {
            return potion;
         }
      }

      for (AdvancedPotion potionx : EnigmaticPotions.COMMON_POTIONS) {
         if (potionx.getId().equals(identifier)) {
            return potionx;
         }
      }

      return EnigmaticPotions.EMPTY_POTION;
   }

   public static HashMap<Ingredient, Ingredient> constructIngredientMap(Ingredient... ingredients) {
      HashMap<Ingredient, Ingredient> returnMap = new HashMap<>();
      if (ingredients.length % 2 != 0) {
         throw new IllegalArgumentException("Uneven number of ingredients passed. This must not be!");
      } else {
         for (int counter = 0; counter < ingredients.length; counter += 2) {
            returnMap.put(ingredients[counter], ingredients[counter + 1]);
         }

         return returnMap;
      }
   }

   public static ItemStack createAdvancedPotion(Item item, AdvancedPotion potion) {
      return setAdvancedPotion(new ItemStack(item), potion);
   }

   public static ItemStack createVanillaPotion(Item item, Potion potion) {
      return PotionUtils.m_43549_(new ItemStack(item), potion);
   }

   public static void registerCommonPotions() {
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43602_)}), Ingredient.m_43929_(new ItemLike[]{Items.f_42692_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42451_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.LONG_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42525_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.STRONG_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43602_)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42692_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42451_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.LONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.LONG_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42525_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.STRONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.STRONG_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43602_)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42692_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42451_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.LONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.LONG_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42525_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.STRONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.STRONG_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43600_)}), Ingredient.m_43929_(new ItemLike[]{Items.f_42585_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.MOLTEN_HEART)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42451_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.LONG_MOLTEN_HEART)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43600_)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42585_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.MOLTEN_HEART)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42451_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.LONG_MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.LONG_MOLTEN_HEART)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43600_)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42585_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.MOLTEN_HEART)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42451_}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.LONG_MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.LONG_MOLTEN_HEART)
         )
      );
   }

   public static void registerBasicUltimatePotions() {
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43604_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_NIGHT_VISION)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43606_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_INVISIBILITY)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43611_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_FIRE_RESISTANCE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43581_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_HEALING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43583_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_HARMING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43597_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTMATE_SLOW_FALLING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43594_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_WEAKNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43622_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_WATER_BREATHING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43591_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43592_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_STRENGTH)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43608_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43609_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_LEAPING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43613_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43614_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_SWIFTNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43616_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43617_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_SLOWNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43619_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43620_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_TURTLE_MASTER)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43585_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43586_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_POISON)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43588_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42589_, Potions.f_43589_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_REGENERATION)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.LONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.STRONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.LONG_MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_MOLTEN_HEART)
         )
      );
   }

   public static void registerSplashUltimatePotions() {
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43604_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_NIGHT_VISION)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_NIGHT_VISION)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43606_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_INVISIBILITY)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_INVISIBILITY)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43611_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_FIRE_RESISTANCE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_FIRE_RESISTANCE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43581_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_HEALING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_HEALING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43583_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_HARMING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_HARMING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43597_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTMATE_SLOW_FALLING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTMATE_SLOW_FALLING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43594_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_WEAKNESS)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_WEAKNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43622_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_WATER_BREATHING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_WATER_BREATHING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43591_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43592_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_STRENGTH)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_STRENGTH)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43608_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43609_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_LEAPING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_LEAPING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43613_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43614_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_SWIFTNESS)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_SWIFTNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43616_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43617_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_SLOWNESS)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_SLOWNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43619_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43620_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_TURTLE_MASTER)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_TURTLE_MASTER)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43585_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43586_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_POISON)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_POISON)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43588_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42736_, Potions.f_43589_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_REGENERATION)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_REGENERATION)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.LONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.STRONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_SPLASH, EnigmaticPotions.LONG_MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION, EnigmaticPotions.ULTIMATE_MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42403_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_MOLTEN_HEART)
         )
      );
   }

   public static void registerLingeringUltimatePotions() {
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43604_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_NIGHT_VISION)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_NIGHT_VISION)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43606_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_INVISIBILITY)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_INVISIBILITY)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43611_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_FIRE_RESISTANCE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_FIRE_RESISTANCE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43581_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_HEALING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_HEALING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43583_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_HARMING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_HARMING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43597_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTMATE_SLOW_FALLING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTMATE_SLOW_FALLING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43594_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_WEAKNESS)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_WEAKNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43622_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_WATER_BREATHING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_WATER_BREATHING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43591_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43592_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_STRENGTH)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_STRENGTH)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43608_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43609_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_LEAPING)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_LEAPING)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43613_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43614_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_SWIFTNESS)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_SWIFTNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43616_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43617_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_SLOWNESS)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_SLOWNESS)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43619_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43620_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_TURTLE_MASTER)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_TURTLE_MASTER)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43585_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43586_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_POISON)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_POISON)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43588_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createVanillaPotion(Items.f_42739_, Potions.f_43589_)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_REGENERATION)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_REGENERATION)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.LONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.STRONG_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_HASTE)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_HASTE)
         )
      );
      BrewingRecipeRegistry.addRecipe(
         new ComplexBrewingRecipe(
            constructIngredientMap(
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.COMMON_POTION_LINGERING, EnigmaticPotions.LONG_MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{EnigmaticItems.ASTRAL_DUST}),
               Ingredient.m_43927_(new ItemStack[]{createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_SPLASH, EnigmaticPotions.ULTIMATE_MOLTEN_HEART)}),
               Ingredient.m_43929_(new ItemLike[]{Items.f_42735_})
            ),
            createAdvancedPotion(EnigmaticItems.ULTIMATE_POTION_LINGERING, EnigmaticPotions.ULTIMATE_MOLTEN_HEART)
         )
      );
   }
}
