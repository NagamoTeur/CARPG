package com.hollingsworth.arsnouveau.client.jei;

import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
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

public class ImbuementRecipeCategory extends MultiInputCategory<ImbuementRecipe> {
   public IDrawable background;
   public IDrawable icon;

   public ImbuementRecipeCategory(IGuiHelper helper) {
      super(helper, imbuementRecipe -> new MultiInputCategory.MultiProvider(imbuementRecipe.output, imbuementRecipe.pedestalItems, imbuementRecipe.input));
      this.background = helper.createBlankDrawable(114, 108);
      this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.IMBUEMENT_BLOCK));
   }

   public RecipeType<ImbuementRecipe> getRecipeType() {
      return JEIArsNouveauPlugin.IMBUEMENT_RECIPE_TYPE;
   }

   public Component getTitle() {
      return Component.m_237115_("block.ars_nouveau.imbuement_chamber");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void draw(ImbuementRecipe recipe, @NotNull IRecipeSlotsView slotsView, PoseStack matrixStack, double mouseX, double mouseY) {
      Font renderer = Minecraft.m_91087_().f_91062_;
      renderer.m_92889_(matrixStack, Component.m_237110_("ars_nouveau.source", new Object[]{recipe.source}), 0.0F, 100.0F, 10);
   }
}
