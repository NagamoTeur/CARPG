package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelblob;
import net.thirdlife.iterrpg.entity.BlobEntity;

public class BlobRenderer extends MobRenderer<BlobEntity, Modelblob<BlobEntity>> {
   public BlobRenderer(Context context) {
      super(context, new Modelblob(context.m_174023_(Modelblob.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(BlobEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/bubble.png");
   }
}
