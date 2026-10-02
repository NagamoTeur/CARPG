package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.WildenGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class WildenGuardianRenderer extends GenericRenderer<WildenGuardian> {
   public WildenGuardianRenderer(Context renderManager) {
      super(renderManager, new WildenGuardianModel());
   }
}
