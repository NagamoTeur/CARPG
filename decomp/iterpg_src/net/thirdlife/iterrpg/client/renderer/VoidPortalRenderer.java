package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelvoid_portal;
import net.thirdlife.iterrpg.entity.VoidPortalEntity;

public class VoidPortalRenderer extends MobRenderer<VoidPortalEntity, Modelvoid_portal<VoidPortalEntity>> {
   public VoidPortalRenderer(Context context) {
      super(context, new Modelvoid_portal(context.m_174023_(Modelvoid_portal.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(VoidPortalEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/void_portal.png");
   }
}
