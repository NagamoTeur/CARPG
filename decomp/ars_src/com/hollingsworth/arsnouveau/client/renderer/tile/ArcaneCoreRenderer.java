package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.tile.ArcaneCoreTile;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class ArcaneCoreRenderer extends ArsGeoBlockRenderer<ArcaneCoreTile> {
   public static AnimatedGeoModel model = new GenericModel("arcane_core");

   public ArcaneCoreRenderer(Context rendererDispatcherIn) {
      this(rendererDispatcherIn, model);
   }

   public ArcaneCoreRenderer(Context rendererDispatcherIn, AnimatedGeoModel<ArcaneCoreTile> modelProvider) {
      super(rendererDispatcherIn, modelProvider);
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model);
   }
}
