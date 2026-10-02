package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.EntityChimeraProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ChimeraProjectileRenderer extends GeoProjectilesRenderer<EntityChimeraProjectile> {
   public ChimeraProjectileRenderer(Context renderManager) {
      super(renderManager, new ChimeraProjectileModel());
   }

   public void render(
      EntityChimeraProjectile entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.f_19857_) - 90.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      float f9 = (float)entityIn.f_36706_ - partialTicks;
      if (f9 > 0.0F) {
         float f10 = -Mth.m_14031_(f9 * 3.0F) * f9;
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(f10));
      }

      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }
}
