package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelrevenant;
import net.thirdlife.iterrpg.entity.RevenantEntity;

public class RevenantRenderer extends MobRenderer<RevenantEntity, Modelrevenant<RevenantEntity>> {
   public RevenantRenderer(Context context) {
      super(context, new Modelrevenant(context.m_174023_(Modelrevenant.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(RevenantEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/revenat.png");
   }
}
