package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelpeeper;
import net.thirdlife.iterrpg.entity.PeeperEntity;

public class PeeperRenderer extends MobRenderer<PeeperEntity, Modelpeeper<PeeperEntity>> {
   public PeeperRenderer(Context context) {
      super(context, new Modelpeeper(context.m_174023_(Modelpeeper.LAYER_LOCATION)), 0.8F);
   }

   public ResourceLocation getTextureLocation(PeeperEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/peeper.png");
   }
}
