package com.aqutheseal.celestisynth.common.registry;

import com.aqutheseal.celestisynth.common.recipe.celestialcrafting.CelestialCraftingRecipe;
import com.aqutheseal.celestisynth.common.recipe.celestialcrafting.CelestialShapedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class CSRecipeTypes {
   public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "celestisynth");
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "celestisynth");
   public static final RegistryObject<RecipeType<CelestialCraftingRecipe>> CELESTIAL_CRAFTING_TYPE = RECIPE_TYPES.register(
      "celestial_crafting", () -> new RecipeType<CelestialCraftingRecipe>() {
            @NotNull
            @Override
            public String toString() {
               return "celestial_crafting";
            }
         }
   );
   public static final RegistryObject<RecipeSerializer<CelestialShapedRecipe>> SHAPED_CELESTIAL_CRAFTING = RECIPE_SERIALIZERS.register(
      "shaped_celestial_crafting", CelestialShapedRecipe.Serializer::new
   );
}
