package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelsorrowsealed;
import net.thirdlife.iterrpg.entity.SorrowsealedEntity;

public class SorrowsealedRenderer extends MobRenderer<SorrowsealedEntity, Modelsorrowsealed<SorrowsealedEntity>> {
   public SorrowsealedRenderer(Context context) {
      super(context, new Modelsorrowsealed(context.m_174023_(Modelsorrowsealed.LAYER_LOCATION)), 3.0F);
   }

   public ResourceLocation getTextureLocation(SorrowsealedEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/sorrowsealed.png");
   }
}
