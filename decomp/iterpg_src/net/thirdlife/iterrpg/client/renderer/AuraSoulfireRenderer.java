package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelaura_soulfire;
import net.thirdlife.iterrpg.entity.AuraSoulfireEntity;

public class AuraSoulfireRenderer extends MobRenderer<AuraSoulfireEntity, Modelaura_soulfire<AuraSoulfireEntity>> {
   public AuraSoulfireRenderer(Context context) {
      super(context, new Modelaura_soulfire(context.m_174023_(Modelaura_soulfire.LAYER_LOCATION)), 0.0F);
      this.m_115326_(new EyesLayer<AuraSoulfireEntity, Modelaura_soulfire<AuraSoulfireEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/soulfire_aura.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(AuraSoulfireEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/soulfire_aura.png");
   }
}
