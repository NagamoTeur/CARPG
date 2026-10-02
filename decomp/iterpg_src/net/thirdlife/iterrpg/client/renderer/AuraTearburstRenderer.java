package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelaura_tearburst;
import net.thirdlife.iterrpg.entity.AuraTearburstEntity;

public class AuraTearburstRenderer extends MobRenderer<AuraTearburstEntity, Modelaura_tearburst<AuraTearburstEntity>> {
   public AuraTearburstRenderer(Context context) {
      super(context, new Modelaura_tearburst(context.m_174023_(Modelaura_tearburst.LAYER_LOCATION)), 0.0F);
      this.m_115326_(new EyesLayer<AuraTearburstEntity, Modelaura_tearburst<AuraTearburstEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/tearburst_aura.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(AuraTearburstEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/tearburst_aura.png");
   }
}
