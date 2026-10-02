package io.redspace.ironsspellbooks.jei;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.gui.arcane_anvil.ArcaneAnvilMenu;
import io.redspace.ironsspellbooks.gui.arcane_anvil.ArcaneAnvilScreen;
import io.redspace.ironsspellbooks.gui.scroll_forge.ScrollForgeScreen;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MenuRegistry;
import java.util.ArrayList;
import java.util.stream.IntStream;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
   public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation("irons_spellbooks", "textures/gui/gui_vanilla.png");
   public static final ResourceLocation ALCHEMIST_CAULDRON_GUI = new ResourceLocation("irons_spellbooks", "textures/gui/jei_alchemist_cauldron.png");
   public static final ResourceLocation SCROLL_FORGE_GUI = new ResourceLocation("irons_spellbooks", "textures/gui/scroll_forge.png");
   private static final IIngredientSubtypeInterpreter<ItemStack> SCROLL_INTERPRETER = (stack, context) -> {
      if (stack.m_41782_()) {
         SpellData ss = ISpellContainer.get(stack).getSpellAtIndex(0);
         return String.format("scroll:%s:%d", ss.getSpell().getSpellId(), ss.getLevel());
      } else {
         return "";
      }
   };

   public ResourceLocation getPluginUid() {
      return new ResourceLocation("irons_spellbooks", "jei_plugin");
   }

   public void registerItemSubtypes(ISubtypeRegistration registration) {
      registration.registerSubtypeInterpreter((Item)ItemRegistry.SCROLL.get(), SCROLL_INTERPRETER);
   }

   public void registerCategories(IRecipeCategoryRegistration registration) {
      IJeiHelpers jeiHelpers = registration.getJeiHelpers();
      IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
      registration.addRecipeCategories(new IRecipeCategory[]{new ArcaneAnvilRecipeCategory(guiHelper)});
      registration.addRecipeCategories(new IRecipeCategory[]{new ScrollForgeRecipeCategory(guiHelper)});
      registration.addRecipeCategories(new IRecipeCategory[]{new AlchemistCauldronRecipeCategory(guiHelper)});
   }

   public void registerRecipes(IRecipeRegistration registration) {
      IIngredientManager ingredientManager = registration.getIngredientManager();
      IVanillaRecipeFactory vanillaRecipeFactory = registration.getVanillaRecipeFactory();
      registration.addRecipes(
         ArcaneAnvilRecipeCategory.ARCANE_ANVIL_RECIPE_RECIPE_TYPE, ArcaneAnvilRecipeMaker.getRecipes(vanillaRecipeFactory, ingredientManager)
      );
      registration.addRecipes(
         ScrollForgeRecipeCategory.SCROLL_FORGE_RECIPE_RECIPE_TYPE, ScrollForgeRecipeMaker.getRecipes(vanillaRecipeFactory, ingredientManager)
      );
      registration.addRecipes(
         AlchemistCauldronRecipeCategory.ALCHEMIST_CAULDRON_RECIPE_TYPE, AlchemistCauldronRecipeMaker.getRecipes(vanillaRecipeFactory, ingredientManager)
      );
      registration.addRecipes(RecipeTypes.ANVIL, VanillaAnvilRecipeMaker.getAnvilRepairRecipes(vanillaRecipeFactory));
      SpellRegistry.REGISTRY
         .get()
         .getValues()
         .stream()
         .forEach(
            spell -> {
               if (spell.isEnabled() && spell != SpellRegistry.none()) {
                  ArrayList<ItemStack> list = new ArrayList<>();
                  IntStream.rangeClosed(spell.getMinLevel(), spell.getMaxLevel()).forEach(spellLevel -> {
                     ItemStack scrollStack = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
                     ISpellContainer.createScrollContainer(spell, spellLevel, scrollStack);
                     list.add(scrollStack);
                  });
                  registration.addIngredientInfo(
                     list, VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_(String.format("%s.guide", spell.getComponentId()))}
                  );
               }
            }
         );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.LIGHTNING_BOTTLE.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.lightning_bottle.guide")}
      );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.BLOOD_VIAL.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.blood_vial.guide")}
      );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.FROZEN_BONE_SHARD.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.frozen_bone.guide")}
      );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.HOGSKIN.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.hogskin.guide")}
      );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.DRAGONSKIN.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.dragonskin.guide")}
      );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.RUINED_BOOK.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.ruined_book.guide")}
      );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.CINDER_ESSENCE.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.cinder_essence.guide")}
      );
      registration.addItemStackInfo(
         new ItemStack((ItemLike)ItemRegistry.LIGHTNING_ROD_STAFF.get()), new Component[]{Component.m_237115_("item.irons_spellbooks.lightning_rod.guide")}
      );
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      registration.addRecipeClickArea(ArcaneAnvilScreen.class, 102, 48, 22, 15, new RecipeType[]{ArcaneAnvilRecipeCategory.ARCANE_ANVIL_RECIPE_RECIPE_TYPE});
      registration.addRecipeClickArea(ScrollForgeScreen.class, 1, 1, 76, 14, new RecipeType[]{ScrollForgeRecipeCategory.SCROLL_FORGE_RECIPE_RECIPE_TYPE});
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
      registration.addRecipeTransferHandler(
         ArcaneAnvilMenu.class, (MenuType)MenuRegistry.ARCANE_ANVIL_MENU.get(), ArcaneAnvilRecipeCategory.ARCANE_ANVIL_RECIPE_RECIPE_TYPE, 0, 2, 3, 36
      );
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalyst(
         new ItemStack((ItemLike)BlockRegistry.ARCANE_ANVIL_BLOCK.get()), new RecipeType[]{ArcaneAnvilRecipeCategory.ARCANE_ANVIL_RECIPE_RECIPE_TYPE}
      );
      registration.addRecipeCatalyst(
         new ItemStack((ItemLike)BlockRegistry.SCROLL_FORGE_BLOCK.get()), new RecipeType[]{ScrollForgeRecipeCategory.SCROLL_FORGE_RECIPE_RECIPE_TYPE}
      );
      registration.addRecipeCatalyst(
         new ItemStack((ItemLike)BlockRegistry.ALCHEMIST_CAULDRON.get()), new RecipeType[]{AlchemistCauldronRecipeCategory.ALCHEMIST_CAULDRON_RECIPE_TYPE}
      );
   }
}
