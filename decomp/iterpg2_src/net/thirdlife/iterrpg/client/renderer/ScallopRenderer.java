package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelmussel;
import net.thirdlife.iterrpg.entity.ScallopEntity;

public class ScallopRenderer extends MobRenderer<ScallopEntity, Modelmussel<ScallopEntity>> {
   public ScallopRenderer(Context context) {
      super(context, new Modelmussel(context.m_174023_(Modelmussel.LAYER_LOCATION)), 0.7F);
   }

   public ResourceLocation getTextureLocation(ScallopEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/scallop.png");
   }
}
