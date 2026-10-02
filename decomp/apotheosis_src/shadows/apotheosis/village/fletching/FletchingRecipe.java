package shadows.apotheosis.village.fletching;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.village.VillageModule;

public class FletchingRecipe implements Recipe<CraftingContainer> {
   protected final ResourceLocation id;
   protected final ItemStack output;
   protected final NonNullList<Ingredient> inputs = NonNullList.m_122780_(3, Ingredient.f_43901_);

   public FletchingRecipe(ResourceLocation id, ItemStack output, List<Ingredient> inputs) {
      this.id = id;
      this.output = output;

      for (int i = 0; i < Math.min(3, inputs.size()); i++) {
         this.inputs.set(i, inputs.get(i));
      }
   }

   public boolean matches(CraftingContainer inv, Level world) {
      for (int i = 0; i < 3; i++) {
         if (!((Ingredient)this.inputs.get(i)).test(inv.m_8020_(i))) {
            return false;
         }
      }

      return true;
   }

   public ItemStack assemble(CraftingContainer inv) {
      return this.output.m_41777_();
   }

   public boolean m_8004_(int width, int height) {
      return width == 1 && height == 3;
   }

   public ItemStack m_8043_() {
      return this.output;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return VillageModule.FLETCHING_SERIALIZER;
   }

   public RecipeType<?> m_6671_() {
      return Apoth.RecipeTypes.FLETCHING;
   }

   public NonNullList<Ingredient> m_7527_() {
      return this.inputs;
   }

   public static class Serializer implements RecipeSerializer<FletchingRecipe> {
      public static final ResourceLocation NAME = new ResourceLocation("apotheosis", "fletching");

      public FletchingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         NonNullList<Ingredient> nonnulllist = readIngredients(GsonHelper.m_13933_(json, "ingredients"));
         if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for fletching recipe");
         } else if (nonnulllist.size() > 3) {
            throw new JsonParseException("Too many ingredients for fletching recipe, max 3");
         } else {
            ItemStack itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(json, "result"));
            return new FletchingRecipe(recipeId, itemstack, nonnulllist);
         }
      }

      private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
         NonNullList<Ingredient> nonnulllist = NonNullList.m_122779_();

         for (int i = 0; i < ingredientArray.size(); i++) {
            Ingredient ingredient = Ingredient.m_43917_(ingredientArray.get(i));
            if (!ingredient.m_43947_()) {
               nonnulllist.add(ingredient);
            }
         }

         return nonnulllist;
      }

      public FletchingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         NonNullList<Ingredient> nonnulllist = NonNullList.m_122780_(3, Ingredient.f_43901_);

         for (int j = 0; j < nonnulllist.size(); j++) {
            nonnulllist.set(j, Ingredient.m_43940_(buffer));
         }

         ItemStack itemstack = buffer.m_130267_();
         return new FletchingRecipe(recipeId, itemstack, nonnulllist);
      }

      public void toNetwork(FriendlyByteBuf buffer, FletchingRecipe recipe) {
         for (Ingredient ingredient : recipe.inputs) {
            ingredient.m_43923_(buffer);
         }

         buffer.m_130055_(recipe.output);
      }
   }
}
