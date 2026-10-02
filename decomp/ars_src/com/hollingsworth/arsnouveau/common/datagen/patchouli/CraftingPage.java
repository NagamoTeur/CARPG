package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public class CraftingPage extends AbstractPage {
   public CraftingPage(String recipe) {
      this.object.addProperty("recipe", recipe);
   }

   public CraftingPage(ItemLike itemLike) {
      this(RegistryHelper.getRegistryName(itemLike.m_5456_()).toString());
   }

   public CraftingPage(RegistryObject<? extends ItemLike> itemLike) {
      this(((ItemLike)itemLike.get()).m_5456_());
   }

   public CraftingPage withRecipe2(String recipe) {
      this.object.addProperty("recipe2", recipe);
      return this;
   }

   public CraftingPage withRecipe2(ItemLike recipe) {
      this.object.addProperty("recipe2", RegistryHelper.getRegistryName(recipe.m_5456_()).toString());
      return this;
   }

   public CraftingPage withTitle(String title) {
      this.object.addProperty("title", title);
      return this;
   }

   public CraftingPage withText(String text) {
      this.object.addProperty("text", text);
      return this;
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("patchouli:crafting");
   }
}
