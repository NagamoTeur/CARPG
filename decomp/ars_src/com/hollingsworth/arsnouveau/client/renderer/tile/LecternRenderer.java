package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.tile.CraftingLecternTile;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class LecternRenderer extends ArsGeoBlockRenderer<CraftingLecternTile> {
   public static AnimatedGeoModel model = new GenericModel("book_wyrm_lectern");

   public LecternRenderer(Context rendererDispatcherIn) {
      super(rendererDispatcherIn, model);
   }

   public LecternRenderer(Context rendererDispatcherIn, AnimatedGeoModel<CraftingLecternTile> modelProvider) {
      super(rendererDispatcherIn, modelProvider);
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model);
   }
}
