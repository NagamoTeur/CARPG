package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.tile.PotionMelderTile;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;

public class PotionMelderRenderer extends ArsGeoBlockRenderer<PotionMelderTile> {
   public PotionMelderRenderer(Context rendererDispatcherIn) {
      super(rendererDispatcherIn, new PotionMelderModel());
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(new PotionMelderModel());
   }
}
