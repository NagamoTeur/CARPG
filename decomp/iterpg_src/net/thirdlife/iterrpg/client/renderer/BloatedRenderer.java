package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelbloated;
import net.thirdlife.iterrpg.entity.BloatedEntity;

public class BloatedRenderer extends MobRenderer<BloatedEntity, Modelbloated<BloatedEntity>> {
   public BloatedRenderer(Context context) {
      super(context, new Modelbloated(context.m_174023_(Modelbloated.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(BloatedEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/drowned.png");
   }
}
