package com.hollingsworth.arsnouveau.client.jei;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ArmorUpgradeRecipe;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkProvider;
import com.hollingsworth.arsnouveau.common.armor.AnimatedMagicArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ArmorUpgradeRecipeCategory extends EnchantingApparatusRecipeCategory<ArmorUpgradeRecipe> {
   public ArmorUpgradeRecipeCategory(IGuiHelper helper) {
      super(helper);
   }

   public void setRecipe(IRecipeLayoutBuilder builder, ArmorUpgradeRecipe recipe, IFocusGroup focuses) {
      MultiInputCategory.MultiProvider provider = this.multiProvider.apply(recipe);
      List<Ingredient> inputs = provider.input();
      double angleBetweenEach = 360.0 / (double)inputs.size();
      List<ItemStack> stacks = ArsNouveauAPI.getInstance().getPerkProviderItems().stream().filter(item -> {
         if (item instanceof AnimatedMagicArmor ama && ama.getMinTier() < recipe.tier) {
            return true;
         }

         return false;
      }).<ItemStack>map(Item::m_7968_).toList();
      List<ItemStack> outputs = new ArrayList<>();
      if (!focuses.isEmpty()) {
         List<ItemStack> list = focuses.getItemStackFocuses(RecipeIngredientRole.CATALYST)
            .map(i -> ((ItemStack)i.getTypedValue().getIngredient()).m_41777_())
            .filter(i -> i.m_41720_() instanceof AnimatedMagicArmor)
            .toList();
         List<ItemStack> list2 = focuses.getItemStackFocuses(RecipeIngredientRole.OUTPUT)
            .map(i -> ((ItemStack)i.getTypedValue().getIngredient()).m_41777_())
            .filter(i -> i.m_41720_() instanceof AnimatedMagicArmor)
            .toList();
         if (!list.isEmpty()) {
            stacks = list;
         } else if (!list2.isEmpty()) {
            stacks = list2;
         }
      }

      for (ItemStack stack : stacks) {
         ItemStack copy = stack.m_41777_();
         IPerkProvider<ItemStack> perkProvider = ArsNouveauAPI.getInstance().getPerkProvider(stack.m_41720_());
         if (perkProvider != null) {
            if (perkProvider.getPerkHolder(stack) instanceof ArmorPerkHolder armorPerkHolder) {
               armorPerkHolder.setTier(recipe.tier - 1);
            }

            if (perkProvider.getPerkHolder(copy) instanceof ArmorPerkHolder armorPerkHolder) {
               armorPerkHolder.setTier(recipe.tier);
            }
         }

         outputs.add(copy);
      }

      builder.addSlot(RecipeIngredientRole.INPUT, 48, 45).addItemStacks(stacks);
      builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 10).addItemStacks(outputs);

      for (Ingredient input : inputs) {
         builder.addSlot(RecipeIngredientRole.INPUT, (int)this.point.f_82470_, (int)this.point.f_82471_).addIngredients(input);
         this.point = rotatePointAbout(this.point, this.center, angleBetweenEach);
      }
   }

   @Override
   public Component getTitle() {
      return Component.m_237115_("ars_nouveau.armor_upgrade");
   }

   @Override
   public RecipeType<ArmorUpgradeRecipe> getRecipeType() {
      return JEIArsNouveauPlugin.ARMOR_RECIPE_TYPE;
   }

   public void draw(ArmorUpgradeRecipe recipe, @NotNull IRecipeSlotsView slotsView, PoseStack matrixStack, double mouseX, double mouseY) {
      Font renderer = Minecraft.m_91087_().f_91062_;
      renderer.m_92889_(matrixStack, Component.m_237110_("ars_nouveau.tier", new Object[]{1 + recipe.tier}), 0.0F, 0.0F, 10);
      if (recipe.consumesSource()) {
         renderer.m_92889_(matrixStack, Component.m_237110_("ars_nouveau.source", new Object[]{recipe.sourceCost}), 0.0F, 100.0F, 10);
      }
   }
}
