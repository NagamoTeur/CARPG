package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.Lily;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class LilyRenderer extends GenericRenderer<Lily> {
   public LilyRenderer(Context renderManager) {
      super(renderManager, new LilyModel());
   }
}
