package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class WhirlisprigRenderer extends TextureVariantRenderer<Whirlisprig> {
   public WhirlisprigRenderer(Context manager) {
      super(manager, new WhirlisprigModel());
   }
}
