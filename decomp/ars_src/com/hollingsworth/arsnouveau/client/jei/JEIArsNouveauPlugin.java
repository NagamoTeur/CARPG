package com.hollingsworth.arsnouveau.client.jei;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ArmorUpgradeRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantmentRecipe;
import com.hollingsworth.arsnouveau.client.container.IAutoFillTerminal;
import com.hollingsworth.arsnouveau.common.crafting.recipes.CrushRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.DyeRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCrush;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class JEIArsNouveauPlugin implements IModPlugin {
   public static final RecipeType<GlyphRecipe> GLYPH_RECIPE_TYPE = RecipeType.create("ars_nouveau", "glyph_recipe", GlyphRecipe.class);
   public static final RecipeType<EnchantingApparatusRecipe> ENCHANTING_APP_RECIPE_TYPE = RecipeType.create(
      "ars_nouveau", "enchanting_apparatus", EnchantingApparatusRecipe.class
   );
   public static final RecipeType<EnchantmentRecipe> ENCHANTING_RECIPE_TYPE = RecipeType.create("ars_nouveau", "enchantment_apparatus", EnchantmentRecipe.class);
   public static final RecipeType<ArmorUpgradeRecipe> ARMOR_RECIPE_TYPE = RecipeType.create("ars_nouveau", "armor_upgrade", ArmorUpgradeRecipe.class);
   public static final RecipeType<ImbuementRecipe> IMBUEMENT_RECIPE_TYPE = RecipeType.create("ars_nouveau", "imbuement", ImbuementRecipe.class);
   public static final RecipeType<CrushRecipe> CRUSH_RECIPE_TYPE = RecipeType.create("ars_nouveau", "crush", CrushRecipe.class);
   private static IJeiRuntime jeiRuntime;

   public ResourceLocation getPluginUid() {
      return new ResourceLocation("ars_nouveau", "main");
   }

   public void registerCategories(IRecipeCategoryRegistration registry) {
      registry.addRecipeCategories(
         new IRecipeCategory[]{
            new GlyphRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new CrushRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new ImbuementRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new EnchantingApparatusRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new ApparatusEnchantingRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new ArmorUpgradeRecipeCategory(registry.getJeiHelpers().getGuiHelper())
         }
      );
   }

   public void registerRecipes(IRecipeRegistration registry) {
      List<GlyphRecipe> recipeList = new ArrayList<>();
      List<EnchantingApparatusRecipe> apparatus = new ArrayList<>();
      List<EnchantmentRecipe> enchantments = new ArrayList<>();
      List<CrushRecipe> crushRecipes = new ArrayList<>();
      List<ArmorUpgradeRecipe> armorUpgrades = new ArrayList<>();
      List<ImbuementRecipe> imbuementRecipes = Minecraft.m_91087_()
         .f_91073_
         .m_7465_()
         .m_44013_((net.minecraft.world.item.crafting.RecipeType)RecipeRegistry.IMBUEMENT_TYPE.get());
      RecipeManager manager = Minecraft.m_91087_().f_91073_.m_7465_();

      for (Recipe<?> i : manager.m_44051_()) {
         if (i instanceof GlyphRecipe glyphRecipe) {
            recipeList.add(glyphRecipe);
         }

         if (i instanceof EnchantmentRecipe enchantmentRecipe) {
            enchantments.add(enchantmentRecipe);
         } else if (i instanceof ArmorUpgradeRecipe upgradeRecipe) {
            armorUpgrades.add(upgradeRecipe);
         } else if (i instanceof EnchantingApparatusRecipe enchantingApparatusRecipe && !enchantingApparatusRecipe.excludeJei()) {
            apparatus.add(enchantingApparatusRecipe);
         }

         if (i instanceof CrushRecipe crushRecipe) {
            crushRecipes.add(crushRecipe);
         }
      }

      registry.addRecipes(GLYPH_RECIPE_TYPE, recipeList);
      registry.addRecipes(CRUSH_RECIPE_TYPE, crushRecipes);
      registry.addRecipes(ENCHANTING_APP_RECIPE_TYPE, apparatus);
      registry.addRecipes(ENCHANTING_RECIPE_TYPE, enchantments);
      registry.addRecipes(IMBUEMENT_RECIPE_TYPE, imbuementRecipes);
      registry.addRecipes(ARMOR_RECIPE_TYPE, armorUpgrades);
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
      registry.addRecipeCatalyst(new ItemStack(BlockRegistry.SCRIBES_BLOCK), new RecipeType[]{GLYPH_RECIPE_TYPE});
      registry.addRecipeCatalyst(new ItemStack(EffectCrush.INSTANCE.glyphItem), new RecipeType[]{CRUSH_RECIPE_TYPE});
      registry.addRecipeCatalyst(new ItemStack(BlockRegistry.IMBUEMENT_BLOCK), new RecipeType[]{IMBUEMENT_RECIPE_TYPE});
      registry.addRecipeCatalyst(new ItemStack(BlockRegistry.ENCHANTING_APP_BLOCK), new RecipeType[]{ENCHANTING_APP_RECIPE_TYPE});
      registry.addRecipeCatalyst(new ItemStack(BlockRegistry.ENCHANTING_APP_BLOCK), new RecipeType[]{ENCHANTING_RECIPE_TYPE});
      registry.addRecipeCatalyst(new ItemStack(BlockRegistry.ENCHANTING_APP_BLOCK), new RecipeType[]{ARMOR_RECIPE_TYPE});
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
      CraftingTerminalTransferHandler.registerTransferHandlers(registration);
   }

   public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
      registration.getCraftingCategory().addCategoryExtension(DyeRecipe.class, DyeRecipeCategory::new);
   }

   public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
      JEIArsNouveauPlugin.jeiRuntime = jeiRuntime;
   }

   static {
      IAutoFillTerminal.updateSearch
         .add(
            new IAutoFillTerminal.ISearchHandler() {
               @Override
               public void setSearch(String text) {
                  if (JEIArsNouveauPlugin.jeiRuntime != null && JEIArsNouveauPlugin.jeiRuntime.getIngredientFilter() != null) {
                     JEIArsNouveauPlugin.jeiRuntime.getIngredientFilter().setFilterText(text);
                  }
               }

               @Override
               public String getSearch() {
                  return JEIArsNouveauPlugin.jeiRuntime != null && JEIArsNouveauPlugin.jeiRuntime.getIngredientFilter() != null
                     ? JEIArsNouveauPlugin.jeiRuntime.getIngredientFilter().getFilterText()
                     : "";
               }

               @Override
               public String getName() {
                  return "JEI";
               }
            }
         );
   }
}
