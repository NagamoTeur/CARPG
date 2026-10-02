package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelaura_mobspawn;
import net.thirdlife.iterrpg.entity.AuraMobspawnEntity;

public class AuraMobspawnRenderer extends MobRenderer<AuraMobspawnEntity, Modelaura_mobspawn<AuraMobspawnEntity>> {
   public AuraMobspawnRenderer(Context context) {
      super(context, new Modelaura_mobspawn(context.m_174023_(Modelaura_mobspawn.LAYER_LOCATION)), 0.0F);
      this.m_115326_(new EyesLayer<AuraMobspawnEntity, Modelaura_mobspawn<AuraMobspawnEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/aura_mobspawn.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(AuraMobspawnEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/aura_mobspawn.png");
   }
}
