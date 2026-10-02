package com.hollingsworth.arsnouveau.client.jei;

import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;

public abstract class MultiInputCategory<T> implements IRecipeCategory<T> {
   protected Function<T, MultiInputCategory.MultiProvider> multiProvider;
   protected Vec2 point = new Vec2(48.0F, 13.0F);
   protected Vec2 center = new Vec2(48.0F, 45.0F);

   public MultiInputCategory(IGuiHelper helper, Function<T, MultiInputCategory.MultiProvider> multiProvider) {
      this.multiProvider = multiProvider;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
      MultiInputCategory.MultiProvider provider = this.multiProvider.apply(recipe);
      List<Ingredient> inputs = provider.input;
      double angleBetweenEach = 360.0 / (double)inputs.size();
      if (provider.optionalCenter != null) {
         builder.addSlot(RecipeIngredientRole.INPUT, 48, 45).addIngredients(provider.optionalCenter);
      }

      for (Ingredient input : inputs) {
         builder.addSlot(RecipeIngredientRole.INPUT, (int)this.point.f_82470_, (int)this.point.f_82471_).addIngredients(input);
         this.point = rotatePointAbout(this.point, this.center, angleBetweenEach);
      }

      builder.addSlot(RecipeIngredientRole.OUTPUT, 86, 10).addIngredient(VanillaTypes.ITEM_STACK, provider.output);
   }

   public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
      double rad = degrees * Math.PI / 180.0;
      double newX = Math.cos(rad) * (double)(in.f_82470_ - about.f_82470_) - Math.sin(rad) * (double)(in.f_82471_ - about.f_82471_) + (double)about.f_82470_;
      double newY = Math.sin(rad) * (double)(in.f_82470_ - about.f_82470_) + Math.cos(rad) * (double)(in.f_82471_ - about.f_82471_) + (double)about.f_82471_;
      return new Vec2((float)newX, (float)newY);
   }

   public static record MultiProvider(ItemStack output, List<Ingredient> input, @Nullable Ingredient optionalCenter) {
   }
}
