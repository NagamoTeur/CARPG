package net.thirdlife.iterrpg.client.renderer;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.client.model.Modelchain_handle;
import net.thirdlife.iterrpg.entity.ChainHandleEntity;

public class ChainHandleRenderer extends MobRenderer<ChainHandleEntity, Modelchain_handle<ChainHandleEntity>> {
   public ChainHandleRenderer(Context context) {
      super(context, new Modelchain_handle(context.m_174023_(Modelchain_handle.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(ChainHandleEntity entity) {
      return new ResourceLocation("iter_rpg:textures/entities/chain_handle.png");
   }
}
