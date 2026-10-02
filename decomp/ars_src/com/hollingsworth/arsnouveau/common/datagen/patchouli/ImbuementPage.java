package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class ImbuementPage extends AbstractPage {
   public ImbuementPage(String recipe) {
      this.object.addProperty("recipe", recipe);
   }

   public ImbuementPage(ItemLike itemLike) {
      this(RegistryHelper.getRegistryName(itemLike.m_5456_()).toString());
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("ars_nouveau:imbuement_recipe");
   }
}
