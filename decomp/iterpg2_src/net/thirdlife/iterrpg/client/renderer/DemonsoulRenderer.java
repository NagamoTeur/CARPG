package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modeldemon_soul;
import net.thirdlife.iterrpg.entity.DemonsoulEntity;

public class DemonsoulRenderer extends MobRenderer<DemonsoulEntity, Modeldemon_soul<DemonsoulEntity>> {
   public DemonsoulRenderer(Context context) {
      super(context, new Modeldemon_soul(context.m_174023_(Modeldemon_soul.LAYER_LOCATION)), 0.25F);
   }

   public ResourceLocation getTextureLocation(DemonsoulEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/demonsoul.png");
   }
}
