package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelmournstone;
import net.thirdlife.iterrpg.entity.MournstoneEntity;

public class MournstoneRenderer extends MobRenderer<MournstoneEntity, Modelmournstone<MournstoneEntity>> {
   public MournstoneRenderer(Context context) {
      super(context, new Modelmournstone(context.m_174023_(Modelmournstone.LAYER_LOCATION)), 0.6F);
      this.m_115326_(new EyesLayer<MournstoneEntity, Modelmournstone<MournstoneEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/mournstone_emmissive.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(MournstoneEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/mournstone.png");
   }
}
