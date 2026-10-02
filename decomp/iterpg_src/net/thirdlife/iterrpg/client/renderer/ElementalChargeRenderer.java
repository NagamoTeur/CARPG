package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelchain_handle;
import net.thirdlife.iterrpg.entity.ElementalChargeEntity;

public class ElementalChargeRenderer extends MobRenderer<ElementalChargeEntity, Modelchain_handle<ElementalChargeEntity>> {
   public ElementalChargeRenderer(Context context) {
      super(context, new Modelchain_handle(context.m_174023_(Modelchain_handle.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(ElementalChargeEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/empty.png");
   }
}
