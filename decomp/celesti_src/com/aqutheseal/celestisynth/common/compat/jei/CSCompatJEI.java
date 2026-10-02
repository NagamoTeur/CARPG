package com.aqutheseal.celestisynth.common.compat.jei;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.client.gui.celestialcrafting.CelestialCraftingMenu;
import com.aqutheseal.celestisynth.client.gui.celestialcrafting.CelestialCraftingScreen;
import com.aqutheseal.celestisynth.common.compat.jei.recipecategory.CelestialCraftingRecipeCategory;
import com.aqutheseal.celestisynth.common.recipe.celestialcrafting.CelestialCraftingRecipe;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.aqutheseal.celestisynth.common.registry.CSMenuTypes;
import com.aqutheseal.celestisynth.common.registry.CSRecipeTypes;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.library.plugins.vanilla.RecipeBookGuiHandler;
import mezz.jei.library.plugins.vanilla.crafting.CategoryRecipeValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class CSCompatJEI implements IModPlugin {
   public static final RecipeType<CelestialCraftingRecipe> CELESTIAL_CRAFTING = RecipeType.create(
      "celestisynth", "hunter_weapon", CelestialCraftingRecipe.class
   );
   private static final ResourceLocation ID = Celestisynth.prefix("plugin");

   @NotNull
   public ResourceLocation getPluginUid() {
      return ID;
   }

   public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
      IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
      registration.addRecipeCategories(new IRecipeCategory[]{new CelestialCraftingRecipeCategory(helper)});
   }

   public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
      registration.addGuiContainerHandler(CelestialCraftingScreen.class, new RecipeBookGuiHandler());
   }

   public void registerRecipeTransferHandlers(@NotNull IRecipeTransferRegistration registration) {
      registration.addRecipeTransferHandler(CelestialCraftingMenu.class, (MenuType)CSMenuTypes.CELESTIAL_CRAFTING.get(), CELESTIAL_CRAFTING, 1, 9, 10, 36);
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalyst(new ItemStack((ItemLike)CSBlocks.CELESTIAL_CRAFTING_TABLE.get()), new RecipeType[]{CELESTIAL_CRAFTING});
   }

   public void registerRecipes(@NotNull IRecipeRegistration registration) {
      IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
      new CategoryRecipeValidator(new CelestialCraftingRecipeCategory(helper), registration.getIngredientManager(), 9);
      List<CelestialCraftingRecipe> craftingRecipes = Minecraft.m_91087_()
         .f_91073_
         .m_7465_()
         .m_44013_((net.minecraft.world.item.crafting.RecipeType)CSRecipeTypes.CELESTIAL_CRAFTING_TYPE.get());
      Celestisynth.LOGGER.info("[CelestialCraftingRecipe] Celestial Crafting Recipe Size: " + craftingRecipes.size());
      registration.addRecipes(CELESTIAL_CRAFTING, craftingRecipes);
   }
}
