package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public class ApparatusPage extends AbstractPage {
   public ApparatusPage(String recipe) {
      this.object.addProperty("recipe", recipe);
   }

   public ApparatusPage(ItemLike itemLike) {
      this(RegistryHelper.getRegistryName(itemLike.m_5456_()).toString());
   }

   public ApparatusPage(RegistryObject<? extends ItemLike> itemLike) {
      this((ItemLike)itemLike.get());
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("ars_nouveau", "apparatus_recipe");
   }
}
