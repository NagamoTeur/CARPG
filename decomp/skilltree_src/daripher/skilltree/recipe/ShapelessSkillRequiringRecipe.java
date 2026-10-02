package daripher.skilltree.recipe;

import com.google.gson.JsonObject;
import daripher.skilltree.init.PSTRecipeSerializers;
import java.util.Objects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ShapelessSkillRequiringRecipe extends ShapelessRecipe implements SkillRequiringRecipe {
   public ShapelessSkillRequiringRecipe(ShapelessRecipe recipe) {
      super(recipe.m_6423_(), recipe.m_6076_(), recipe.m_8043_(), recipe.m_7527_());
   }

   public boolean m_5818_(@NotNull CraftingContainer container, @NotNull Level level) {
      return this.isUncraftable(container, this) ? false : super.m_5818_(container, level);
   }

   @NotNull
   public ItemStack m_5874_(@NotNull CraftingContainer container) {
      return this.isUncraftable(container, this) ? ItemStack.f_41583_ : super.m_5874_(container);
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)PSTRecipeSerializers.SHAPELESS_CRAFTING.get();
   }

   public static class Serializer implements RecipeSerializer<ShapelessSkillRequiringRecipe> {
      @NotNull
      public ShapelessSkillRequiringRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
         ShapelessRecipe recipe = (ShapelessRecipe)f_44077_.m_6729_(id, json);
         return new ShapelessSkillRequiringRecipe(recipe);
      }

      public ShapelessSkillRequiringRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
         ShapelessRecipe recipe = (ShapelessRecipe)f_44077_.m_8005_(id, byteBuf);
         return new ShapelessSkillRequiringRecipe(Objects.requireNonNull(recipe));
      }

      public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull ShapelessSkillRequiringRecipe recipe) {
         f_44077_.m_6178_(byteBuf, recipe);
      }
   }
}
