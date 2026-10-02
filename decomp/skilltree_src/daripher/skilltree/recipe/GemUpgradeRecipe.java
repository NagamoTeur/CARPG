package daripher.skilltree.recipe;

import com.google.gson.JsonObject;
import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.compat.apotheosis.ApotheosisCompatibility;
import daripher.skilltree.data.reloader.GemTypesReloader;
import daripher.skilltree.init.PSTRecipeSerializers;
import daripher.skilltree.item.gem.GemItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GemUpgradeRecipe implements CraftingRecipe {
   private final ResourceLocation ingredient;
   private final ResourceLocation result;
   private final ResourceLocation id;

   public GemUpgradeRecipe(ResourceLocation id, ResourceLocation ingredient, ResourceLocation result) {
      this.id = id;
      this.ingredient = ingredient;
      this.result = result;
   }

   @NotNull
   public ResourceLocation m_6423_() {
      return this.id;
   }

   @NotNull
   public String m_6076_() {
      return "gem_upgrades";
   }

   @NotNull
   public ItemStack m_8043_() {
      return SkillTreeMod.apotheosisEnabled()
         ? ApotheosisCompatibility.INSTANCE.getGemStack(this.result)
         : GemItem.getDefaultGemStack(GemTypesReloader.getGemTypeById(this.result));
   }

   @NotNull
   public NonNullList<Ingredient> m_7527_() {
      Ingredient ingredient = this.getIngredient();
      return NonNullList.m_122780_(9, ingredient);
   }

   private Ingredient getIngredient() {
      ItemStack gemStack;
      if (SkillTreeMod.apotheosisEnabled()) {
         gemStack = ApotheosisCompatibility.INSTANCE.getGemStack(this.ingredient);
      } else {
         gemStack = GemItem.getDefaultGemStack(GemTypesReloader.getGemTypeById(this.ingredient));
      }

      return Ingredient.m_43927_(new ItemStack[]{gemStack});
   }

   public boolean matches(CraftingContainer container, @NotNull Level level) {
      int gemsCount = 0;

      for (int j = 0; j < container.m_6643_(); j++) {
         ItemStack stack = container.m_8020_(j);
         if (!this.isIngredient(stack)) {
            return false;
         }

         gemsCount++;
      }

      return gemsCount == 9;
   }

   private boolean isIngredient(ItemStack stack) {
      return SkillTreeMod.apotheosisEnabled()
         ? this.ingredient.equals(ApotheosisCompatibility.INSTANCE.getGemId(stack))
         : GemTypesReloader.getGemTypeById(this.ingredient) == GemItem.getGemType(stack);
   }

   @NotNull
   public ItemStack assemble(@NotNull CraftingContainer container) {
      return this.m_8043_().m_41777_();
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 9;
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)PSTRecipeSerializers.GEM_UPGRADE.get();
   }

   public static class Serializer implements RecipeSerializer<GemUpgradeRecipe> {
      @NotNull
      public GemUpgradeRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
         ResourceLocation ingredient = new ResourceLocation(json.get("ingredient").getAsString());
         ResourceLocation result = new ResourceLocation(json.get("result").getAsString());
         return new GemUpgradeRecipe(id, ingredient, result);
      }

      public GemUpgradeRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
         ResourceLocation ingredient = new ResourceLocation(buf.m_130277_());
         ResourceLocation result = new ResourceLocation(buf.m_130277_());
         return new GemUpgradeRecipe(id, ingredient, result);
      }

      public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull GemUpgradeRecipe recipe) {
         buf.m_130070_(recipe.ingredient.toString());
         buf.m_130070_(recipe.result.toString());
      }
   }
}
