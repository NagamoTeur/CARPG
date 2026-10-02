package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelfire_elemental;
import net.thirdlife.iterrpg.entity.FireElementalEntity;

public class FireElementalRenderer extends MobRenderer<FireElementalEntity, Modelfire_elemental<FireElementalEntity>> {
   public FireElementalRenderer(Context context) {
      super(context, new Modelfire_elemental(context.m_174023_(Modelfire_elemental.LAYER_LOCATION)), 0.0F);
      this.m_115326_(new EyesLayer<FireElementalEntity, Modelfire_elemental<FireElementalEntity>>(this) {
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("iter_rpg:textures/entities/fire_elemental_emmissive.png"));
         }
      });
   }

   public ResourceLocation getTextureLocation(FireElementalEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/fire_elemental.png");
   }
}
