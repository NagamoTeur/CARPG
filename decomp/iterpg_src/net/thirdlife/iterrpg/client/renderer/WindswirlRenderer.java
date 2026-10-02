package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelwindswirl;
import net.thirdlife.iterrpg.entity.WindswirlEntity;

public class WindswirlRenderer extends MobRenderer<WindswirlEntity, Modelwindswirl<WindswirlEntity>> {
   public WindswirlRenderer(Context context) {
      super(context, new Modelwindswirl(context.m_174023_(Modelwindswirl.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(WindswirlEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/windswirl.png");
   }
}
