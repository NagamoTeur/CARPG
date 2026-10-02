package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelgriever;
import net.thirdlife.iterrpg.entity.GrieverEntity;

public class GrieverRenderer extends MobRenderer<GrieverEntity, Modelgriever<GrieverEntity>> {
   public GrieverRenderer(Context context) {
      super(context, new Modelgriever(context.m_174023_(Modelgriever.LAYER_LOCATION)), 0.4F);
   }

   public ResourceLocation getTextureLocation(GrieverEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/griever.png");
   }
}
