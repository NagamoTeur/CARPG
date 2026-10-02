package shadows.apotheosis.spawn.compat;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.spawn.SpawnerModule;
import shadows.apotheosis.spawn.modifiers.SpawnerModifier;

@JeiPlugin
public class SpawnerJEIPlugin implements IModPlugin {
   public void registerRecipes(IRecipeRegistration reg) {
      if (Apotheosis.enableSpawner) {
         List<SpawnerModifier> recipes = new ArrayList<>(Minecraft.m_91087_().f_91073_.m_7465_().m_44013_(Apoth.RecipeTypes.MODIFIER));
         recipes.sort((r1, r2) -> r1.getOffhandInput() == Ingredient.f_43901_ ? (r2.getOffhandInput() == Ingredient.f_43901_ ? 0 : -1) : 1);
         reg.addRecipes(SpawnerCategory.TYPE, recipes);
         if (SpawnerModule.spawnerSilkLevel == -1) {
            reg.addIngredientInfo(
               new ItemStack(Blocks.f_50085_), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.spawner.no_silk")}
            );
         } else if (SpawnerModule.spawnerSilkLevel == 0) {
            reg.addIngredientInfo(
               new ItemStack(Blocks.f_50085_), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.spawner.always_drop")}
            );
         } else {
            reg.addIngredientInfo(
               new ItemStack(Blocks.f_50085_),
               VanillaTypes.ITEM_STACK,
               new Component[]{
                  Component.m_237110_(
                     "info.apotheosis.spawner",
                     new Object[]{
                        ((MutableComponent)Enchantments.f_44985_.m_44700_(SpawnerModule.spawnerSilkLevel)).m_130940_(ChatFormatting.DARK_BLUE).getString()
                     }
                  )
               }
            );
         }

         for (Item i : ForgeRegistries.ITEMS) {
            if (i instanceof SpawnEggItem) {
               reg.addIngredientInfo(new ItemStack(i), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.capturing")});
            }
         }
      }
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
      if (Apotheosis.enableSpawner) {
         reg.addRecipeCatalyst(new ItemStack(Blocks.f_50085_), new RecipeType[]{SpawnerCategory.TYPE});
      }
   }

   public void registerCategories(IRecipeCategoryRegistration reg) {
      if (Apotheosis.enableSpawner) {
         reg.addRecipeCategories(new IRecipeCategory[]{new SpawnerCategory(reg.getJeiHelpers().getGuiHelper())});
      }
   }

   public ResourceLocation getPluginUid() {
      return new ResourceLocation("apotheosis", "spawner");
   }
}
