package com.hollingsworth.arsnouveau.client.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.common.crafting.recipes.CrushRecipe;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCrush;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CrushRecipeCategory implements IRecipeCategory<CrushRecipe> {
   public IDrawable background;
   public IDrawable icon;
   private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

   public CrushRecipeCategory(final IGuiHelper helper) {
      this.background = helper.createBlankDrawable(120, 56);
      this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ArsNouveauAPI.getInstance().getGlyphItem(EffectCrush.INSTANCE).m_7968_());
      this.cachedArrows = CacheBuilder.newBuilder().maximumSize(25L).build(new CacheLoader<Integer, IDrawableAnimated>() {
         public IDrawableAnimated load(Integer cookTime) {
            return helper.drawableBuilder(JEIConstants.RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(cookTime, StartDirection.LEFT, false);
         }
      });
   }

   public RecipeType<CrushRecipe> getRecipeType() {
      return JEIArsNouveauPlugin.CRUSH_RECIPE_TYPE;
   }

   public Component getTitle() {
      return Component.m_237115_("ars_nouveau.crush_recipe");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void draw(CrushRecipe recipe, @NotNull IRecipeSlotsView slotsView, @NotNull PoseStack matrixStack, double mouseX, double mouseY) {
      IDrawableAnimated arrow = (IDrawableAnimated)this.cachedArrows.getUnchecked(40);
      arrow.draw(matrixStack, 22, 6);
      Font renderer = Minecraft.m_91087_().f_91062_;

      for (int i = 0; i < recipe.outputs.size(); i++) {
         CrushRecipe.CrushOutput output = recipe.outputs.get(i);
         renderer.m_92883_(matrixStack, Math.round(100.0F * output.chance - 0.5F) + "%", 98.0F, 11.0F + 17.0F * (float)i, 10);
         if (output.maxRange > 1) {
            renderer.m_92883_(matrixStack, "1-" + output.maxRange, 75.0F, 11.0F + 17.0F * (float)i, 10);
         }
      }
   }

   public void setRecipe(IRecipeLayoutBuilder builder, CrushRecipe recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 6, 5).addIngredients(recipe.input);

      for (int i = 0; i < recipe.outputs.size(); i++) {
         CrushRecipe.CrushOutput output = recipe.outputs.get(i);
         builder.addSlot(RecipeIngredientRole.OUTPUT, 50, 5 + 16 * i).addItemStack(output.stack);
      }
   }
}
