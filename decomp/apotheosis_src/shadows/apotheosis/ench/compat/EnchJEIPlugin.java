package shadows.apotheosis.ench.compat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.table.EnchantingRecipe;

@JeiPlugin
public class EnchJEIPlugin implements IModPlugin {
   public ResourceLocation getPluginUid() {
      return new ResourceLocation("apotheosis", "enchantment");
   }

   public void registerRecipes(IRecipeRegistration reg) {
      if (Apotheosis.enableEnch) {
         ItemStack enchDiaSword = new ItemStack(Items.f_42388_);
         EnchantmentHelper.m_44865_(ImmutableMap.of(Enchantments.f_44977_, 1), enchDiaSword);
         ItemStack cursedDiaSword = new ItemStack(Items.f_42388_);
         EnchantmentHelper.m_44865_(ImmutableMap.of(Enchantments.f_44975_, 1), cursedDiaSword);
         ItemStack enchBook = new ItemStack(Items.f_42690_);
         EnchantmentHelper.m_44865_(ImmutableMap.of(Enchantments.f_44977_, 1), enchBook);
         IVanillaRecipeFactory factory = reg.getVanillaRecipeFactory();
         reg.addRecipes(
            RecipeTypes.ANVIL,
            ImmutableList.of(
               factory.createAnvilRecipe(enchDiaSword, ImmutableList.of(new ItemStack(Blocks.f_50033_)), ImmutableList.of(new ItemStack(Items.f_42388_))),
               factory.createAnvilRecipe(
                  cursedDiaSword, ImmutableList.of(new ItemStack((ItemLike)Apoth.Items.PRISMATIC_WEB.get())), ImmutableList.of(new ItemStack(Items.f_42388_))
               ),
               factory.createAnvilRecipe(enchDiaSword, ImmutableList.of(new ItemStack((ItemLike)Apoth.Items.SCRAP_TOME.get())), ImmutableList.of(enchBook)),
               factory.createAnvilRecipe(
                  new ItemStack(Blocks.f_50324_), ImmutableList.of(new ItemStack(Blocks.f_50075_)), ImmutableList.of(new ItemStack(Blocks.f_50322_))
               )
            )
         );
         reg.addIngredientInfo(new ItemStack(Blocks.f_50201_), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.enchanting")});
         reg.addIngredientInfo(
            new ItemStack((ItemLike)Apoth.Blocks.LIBRARY.get()), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.library")}
         );
         List<EnchantingRecipe> recipes = new ArrayList<>(Minecraft.m_91087_().f_91073_.m_7465_().m_44013_(Apoth.RecipeTypes.INFUSION));
         recipes.sort((r1, r2) -> Float.compare(r1.getRequirements().eterna(), r2.getRequirements().eterna()));
         reg.addRecipes(EnchantingCategory.TYPE, recipes);
      }
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
      if (Apotheosis.enableEnch) {
         reg.addRecipeCatalyst(new ItemStack(Blocks.f_50201_), new RecipeType[]{EnchantingCategory.TYPE});
      }
   }

   public void registerCategories(IRecipeCategoryRegistration reg) {
      if (Apotheosis.enableEnch) {
         reg.addRecipeCategories(new IRecipeCategory[]{new EnchantingCategory(reg.getJeiHelpers().getGuiHelper())});
      }
   }
}
