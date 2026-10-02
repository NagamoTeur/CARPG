package com.hollingsworth.arsnouveau.client.jei;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantingApparatusRecipeCategory<T extends EnchantingApparatusRecipe> extends MultiInputCategory<T> {
   public IDrawable background;
   public IDrawable icon;

   public EnchantingApparatusRecipeCategory(IGuiHelper helper) {
      super(
         helper,
         enchantingApparatusRecipe -> new MultiInputCategory.MultiProvider(
               enchantingApparatusRecipe.result, enchantingApparatusRecipe.pedestalItems, enchantingApparatusRecipe.reagent
            )
      );
      this.background = helper.createBlankDrawable(114, 108);
      this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.ENCHANTING_APP_BLOCK));
   }

   public RecipeType<T> getRecipeType() {
      return (RecipeType<T>)JEIArsNouveauPlugin.ENCHANTING_APP_RECIPE_TYPE;
   }

   public Component getTitle() {
      return Component.m_237115_("ars_nouveau.enchanting_apparatus");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void draw(EnchantingApparatusRecipe recipe, @NotNull IRecipeSlotsView slotsView, PoseStack matrixStack, double mouseX, double mouseY) {
      Font renderer = Minecraft.m_91087_().f_91062_;
      if (recipe.consumesSource()) {
         renderer.m_92889_(matrixStack, Component.m_237110_("ars_nouveau.source", new Object[]{recipe.sourceCost}), 0.0F, 100.0F, 10);
      }
   }
}
