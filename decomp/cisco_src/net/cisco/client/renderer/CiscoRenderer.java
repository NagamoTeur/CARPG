package net.cisco.client.renderer;

import net.cisco.entity.CiscoEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class CiscoRenderer extends HumanoidMobRenderer<CiscoEntity, HumanoidModel<CiscoEntity>> {
   public CiscoRenderer(Context context) {
      super(context, new HumanoidModel(context.m_174023_(ModelLayers.f_171162_)), 0.5F);
      this.m_115326_(
         new HumanoidArmorLayer(this, new HumanoidModel(context.m_174023_(ModelLayers.f_171164_)), new HumanoidModel(context.m_174023_(ModelLayers.f_171165_)))
      );
   }

   public ResourceLocation getTextureLocation(CiscoEntity entity) {
      return new ResourceLocation("cisco_mod:textures/entities/ciscoentity.png");
   }
}
