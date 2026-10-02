package io.redspace.ironsspellbooks.entity.spells.ice_block;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class IceBlockRenderer extends GeoProjectilesRenderer<IceBlockProjectile> {
   public IceBlockRenderer(Context context) {
      super(context, new IceBlockModel());
      this.f_114477_ = 1.5F;
   }
}
