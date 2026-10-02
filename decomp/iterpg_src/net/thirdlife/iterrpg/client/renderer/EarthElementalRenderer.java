package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelearth_elemental;
import net.thirdlife.iterrpg.entity.EarthElementalEntity;

public class EarthElementalRenderer extends MobRenderer<EarthElementalEntity, Modelearth_elemental<EarthElementalEntity>> {
   public EarthElementalRenderer(Context context) {
      super(context, new Modelearth_elemental(context.m_174023_(Modelearth_elemental.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(EarthElementalEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/earth_elemental.png");
   }
}
