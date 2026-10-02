package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelgiant_spider;
import net.thirdlife.iterrpg.entity.GiantSpiderEntity;

public class GiantSpiderRenderer extends MobRenderer<GiantSpiderEntity, Modelgiant_spider<GiantSpiderEntity>> {
   public GiantSpiderRenderer(Context context) {
      super(context, new Modelgiant_spider(context.m_174023_(Modelgiant_spider.LAYER_LOCATION)), 1.2F);
      this.m_115326_(new EyesLayer<GiantSpiderEntity, Modelgiant_spider<GiantSpiderEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/giant_spider_emmissive.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(GiantSpiderEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/giant_spider.png");
   }
}
