package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class SpotlightPage extends AbstractPage {
   public SpotlightPage(String itemString) {
      this.object.addProperty("item", itemString);
   }

   public SpotlightPage(ItemLike itemLike) {
      this(RegistryHelper.getRegistryName(itemLike.m_5456_()).toString());
   }

   public SpotlightPage withTitle(String title) {
      this.object.addProperty("title", title);
      return this;
   }

   public SpotlightPage linkRecipe(boolean link) {
      this.object.addProperty("link_recipe", link);
      return this;
   }

   public SpotlightPage withText(String text) {
      this.object.addProperty("text", text);
      return this;
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("patchouli:spotlight");
   }
}
