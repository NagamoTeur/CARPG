package daripher.skilltree.recipe;

import com.google.gson.JsonObject;
import daripher.skilltree.init.PSTRecipeSerializers;
import java.util.Objects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ShapedSkillRequiringRecipe extends ShapedRecipe implements SkillRequiringRecipe {
   public ShapedSkillRequiringRecipe(ShapedRecipe recipe) {
      super(recipe.m_6423_(), recipe.m_6076_(), recipe.m_44220_(), recipe.m_44221_(), recipe.m_7527_(), recipe.m_8043_());
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
      return (RecipeSerializer<?>)PSTRecipeSerializers.SHAPED_CRAFTING.get();
   }

   public static class Serializer implements RecipeSerializer<ShapedSkillRequiringRecipe> {
      @NotNull
      public ShapedSkillRequiringRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
         ShapedRecipe recipe = (ShapedRecipe)f_44076_.m_6729_(id, json);
         return new ShapedSkillRequiringRecipe(recipe);
      }

      public ShapedSkillRequiringRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
         ShapedRecipe recipe = (ShapedRecipe)f_44076_.m_8005_(id, byteBuf);
         return new ShapedSkillRequiringRecipe(Objects.requireNonNull(recipe));
      }

      public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull ShapedSkillRequiringRecipe recipe) {
         f_44076_.m_6178_(byteBuf, recipe);
      }
   }
}
