package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelcaltrop;
import net.thirdlife.iterrpg.entity.CaltropThrownEntity;

public class CaltropThrownRenderer extends MobRenderer<CaltropThrownEntity, Modelcaltrop<CaltropThrownEntity>> {
   public CaltropThrownRenderer(Context context) {
      super(context, new Modelcaltrop(context.m_174023_(Modelcaltrop.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(CaltropThrownEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/caltrop.png");
   }
}
