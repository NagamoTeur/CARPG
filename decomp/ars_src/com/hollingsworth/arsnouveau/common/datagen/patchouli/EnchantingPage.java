package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import net.minecraft.resources.ResourceLocation;

public class EnchantingPage extends AbstractPage {
   public EnchantingPage(String recipe) {
      this.object.addProperty("recipe", recipe);
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("ars_nouveau", "enchanting_recipe");
   }
}
