package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelaura_boulder;
import net.thirdlife.iterrpg.entity.AuraBoulderEntity;

public class AuraBoulderRenderer extends MobRenderer<AuraBoulderEntity, Modelaura_boulder<AuraBoulderEntity>> {
   public AuraBoulderRenderer(Context context) {
      super(context, new Modelaura_boulder(context.m_174023_(Modelaura_boulder.LAYER_LOCATION)), 0.0F);
      this.m_115326_(new EyesLayer<AuraBoulderEntity, Modelaura_boulder<AuraBoulderEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/boulder.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(AuraBoulderEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/boulder.png");
   }
}
