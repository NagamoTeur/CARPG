package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.xylonity.knightquest.client.entity.model.SwampmanAxeModel;
import net.xylonity.knightquest.common.entity.entities.SwampmanAxeEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SwampmanAxeRenderer extends GeoProjectilesRenderer<SwampmanAxeEntity> {
   public SwampmanAxeRenderer(Context renderManager) {
      super(renderManager, new SwampmanAxeModel());
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull SwampmanAxeEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/swampman.png");
   }

   public void vertex(
      Matrix4f pMatrix,
      Matrix3f pNormal,
      VertexConsumer pConsumer,
      int pX,
      int pY,
      int pZ,
      float pU,
      float pV,
      int pNormalX,
      int pNormalZ,
      int pNormalY,
      int pPackedLight
   ) {
      pConsumer.m_85982_(pMatrix, (float)pX, (float)pY, (float)pZ)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(pU, pV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(pPackedLight)
         .m_85977_(pNormal, (float)pNormalX, (float)pNormalY, (float)pNormalZ)
         .m_5752_();
   }

   public void render(
      SwampmanAxeEntity entity, float entityYaw, float partialTick, PoseStack pPoseStack, @NotNull MultiBufferSource bufferSource, int packedLight
   ) {
      pPoseStack.m_85836_();
      float f9 = (float)entity.shakeTime - partialTick;
      if (f9 > 0.0F) {
         float f10 = -Mth.m_14031_(f9 * 3.0F) * f9;
         pPoseStack.m_85845_(Vector3f.f_122227_.m_122240_(f10));
      }

      super.m_7392_(entity, entityYaw, partialTick, pPoseStack, bufferSource, packedLight);
      pPoseStack.m_85849_();
   }
}
