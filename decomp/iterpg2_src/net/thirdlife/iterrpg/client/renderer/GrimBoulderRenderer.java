package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelgrim_boulder;
import net.thirdlife.iterrpg.entity.GrimBoulderEntity;

public class GrimBoulderRenderer extends MobRenderer<GrimBoulderEntity, Modelgrim_boulder<GrimBoulderEntity>> {
   public GrimBoulderRenderer(Context context) {
      super(context, new Modelgrim_boulder(context.m_174023_(Modelgrim_boulder.LAYER_LOCATION)), 0.8F);
      this.m_115326_(new EyesLayer<GrimBoulderEntity, Modelgrim_boulder<GrimBoulderEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/boulder_fall.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(GrimBoulderEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/boulder_fall.png");
   }
}
