package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelspider_hatchling;
import net.thirdlife.iterrpg.entity.SpiderlingEntity;

public class SpiderlingRenderer extends MobRenderer<SpiderlingEntity, Modelspider_hatchling<SpiderlingEntity>> {
   public SpiderlingRenderer(Context context) {
      super(context, new Modelspider_hatchling(context.m_174023_(Modelspider_hatchling.LAYER_LOCATION)), 0.4F);
   }

   public ResourceLocation getTextureLocation(SpiderlingEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/spider_hatchling.png");
   }
}
