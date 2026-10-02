package com.hollingsworth.arsnouveau.client.jei;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantmentRecipe;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class ApparatusEnchantingRecipeCategory extends EnchantingApparatusRecipeCategory<EnchantmentRecipe> {
   public ApparatusEnchantingRecipeCategory(IGuiHelper helper) {
      super(helper);
   }

   public void setRecipe(IRecipeLayoutBuilder builder, EnchantmentRecipe recipe, IFocusGroup focuses) {
      List<Ingredient> inputs = this.multiProvider.apply(recipe).input();
      double angleBetweenEach = 360.0 / (double)inputs.size();
      ItemStack dummy = recipe.enchantLevel > 1
         ? EnchantedBookItem.m_41161_(new EnchantmentInstance(recipe.enchantment, recipe.enchantLevel - 1))
         : Items.f_42517_.m_7968_();
      Component message = recipe.enchantLevel == 1 ? Component.m_237113_("Any compatible item") : Component.m_237113_("Needs lower level enchantment");
      dummy.m_41714_(message);
      builder.addSlot(RecipeIngredientRole.INPUT, 48, 45).addItemStack(dummy);

      for (Ingredient input : inputs) {
         builder.addSlot(RecipeIngredientRole.INPUT, (int)this.point.f_82470_, (int)this.point.f_82471_).addIngredients(input);
         this.point = rotatePointAbout(this.point, this.center, angleBetweenEach);
      }

      builder.addSlot(RecipeIngredientRole.OUTPUT, 86, 10)
         .addItemStack(EnchantedBookItem.m_41161_(new EnchantmentInstance(recipe.enchantment, recipe.enchantLevel)));
   }

   @Override
   public Component getTitle() {
      return Component.m_237115_("ars_nouveau.enchanting");
   }

   @Override
   public RecipeType<EnchantmentRecipe> getRecipeType() {
      return JEIArsNouveauPlugin.ENCHANTING_RECIPE_TYPE;
   }
}
