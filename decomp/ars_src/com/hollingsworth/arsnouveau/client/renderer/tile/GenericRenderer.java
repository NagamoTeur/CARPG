package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import java.util.function.Supplier;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;

public class GenericRenderer extends ArsGeoBlockRenderer {
   public static GenericModel model = new GenericModel("source_relay");

   public GenericRenderer(Context rendererDispatcherIn, String loc) {
      super(rendererDispatcherIn, new GenericModel(loc));
   }

   public static Supplier<BlockEntityWithoutLevelRenderer> getISTER(String loc) {
      return () -> new GenericItemBlockRenderer(new GenericModel(loc));
   }
}
