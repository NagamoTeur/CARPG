package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class WildenStalkerRenderer extends GenericRenderer<WildenStalker> {
   public WildenStalkerRenderer(Context renderManager) {
      super(renderManager, new WildenStalkerModel());
   }
}
