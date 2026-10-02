package com.hollingsworth.arsnouveau.client.patchouli;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ITextOutput;
import java.util.Arrays;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class NoOutputApparatusProcessor implements IComponentProcessor {
   EnchantingApparatusRecipe recipe;

   public void setup(IVariableProvider variables) {
      RecipeManager manager = Minecraft.m_91087_().f_91073_.m_7465_();
      String recipeID = variables.get("recipe").asString();
      this.recipe = (EnchantingApparatusRecipe)manager.m_44043_(new ResourceLocation(recipeID)).orElse(null);
   }

   public IVariable process(String key) {
      if (this.recipe == null) {
         return null;
      } else if (key.equals("reagent")) {
         return IVariable.wrapList(Arrays.stream(this.recipe.reagent.m_43908_()).map(IVariable::from).collect(Collectors.toList()));
      } else if (key.equals("recipe")) {
         return IVariable.wrap(this.recipe.m_6423_().toString());
      } else {
         if (this.recipe instanceof ITextOutput textOutput && key.equals("output")) {
            return IVariable.wrap(textOutput.getOutputComponent().getString());
         }

         return key.equals("footer") ? IVariable.wrap(this.recipe.result.m_41720_().m_5524_()) : null;
      }
   }
}
