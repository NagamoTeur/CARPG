package shadows.apotheosis.village.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.village.fletching.FletchingContainer;
import shadows.apotheosis.village.fletching.FletchingRecipe;

@JeiPlugin
public class VillageJEIPlugin implements IModPlugin {
   public ResourceLocation getPluginUid() {
      return new ResourceLocation("apotheosis", "village_module");
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
      if (Apotheosis.enableVillage) {
         reg.addRecipeCatalyst(new ItemStack(Blocks.f_50622_), new RecipeType[]{FletchingCategory.TYPE});
      }
   }

   public void registerCategories(IRecipeCategoryRegistration reg) {
      if (Apotheosis.enableVillage) {
         reg.addRecipeCategories(new IRecipeCategory[]{new FletchingCategory(reg.getJeiHelpers().getGuiHelper())});
      }
   }

   public void registerRecipes(IRecipeRegistration reg) {
      if (Apotheosis.enableVillage) {
         reg.addRecipes(
            FletchingCategory.TYPE,
            Minecraft.m_91087_()
               .f_91073_
               .m_7465_()
               .m_44051_()
               .stream()
               .filter(r -> r.m_6671_() == Apoth.RecipeTypes.FLETCHING)
               .map(r -> (FletchingRecipe)r)
               .toList()
         );
      }
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration reg) {
      if (Apotheosis.enableVillage) {
         reg.addRecipeTransferHandler(FletchingContainer.class, (MenuType)Apoth.Menus.FLETCHING.get(), FletchingCategory.TYPE, 1, 3, 4, 36);
      }
   }
}
