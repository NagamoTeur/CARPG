package io.redspace.ironsspellbooks.entity.spells.poison_arrow;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class PoisonArrowRenderer extends EntityRenderer<PoisonArrow> {
   private static final ResourceLocation TEXTURE = IronsSpellbooks.id("textures/entity/arrow.png");

   public PoisonArrowRenderer(Context context) {
      super(context);
   }

   public void render(PoisonArrow entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      Vec3 motion = entity.m_20184_();
      float xRot = -((float)(Mth.m_14136_(motion.m_165924_(), motion.f_82480_) * 180.0F / (float)Math.PI) - 90.0F);
      float yRot = -((float)(Mth.m_14136_(motion.f_82481_, motion.f_82479_) * 180.0F / (float)Math.PI) + 90.0F);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(yRot));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(xRot));
      float f9 = (float)entity.shakeTime - partialTicks;
      if (f9 > 0.0F) {
         float f10 = -Mth.m_14031_(f9 * 3.0F) * f9;
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(f10));
      }

      renderModel(poseStack, bufferSource, light);
      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   public static void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85841_(0.125F, 0.125F, 0.125F);
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110452_(getTextureLocation()));
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
      poseStack.m_85837_(-2.0, 0.0, 0.0);

      for (int j = 0; j < 4; j++) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         vertex(poseMatrix, normalMatrix, consumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, light);
         vertex(poseMatrix, normalMatrix, consumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, light);
         vertex(poseMatrix, normalMatrix, consumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, light);
         vertex(poseMatrix, normalMatrix, consumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, light);
      }
   }

   public static void vertex(
      Matrix4f pMatrix,
      Matrix3f pNormals,
      VertexConsumer pVertexBuilder,
      int pOffsetX,
      int pOffsetY,
      int pOffsetZ,
      float pTextureX,
      float pTextureY,
      int pNormalX,
      int p_113835_,
      int p_113836_,
      int pPackedLight
   ) {
      pVertexBuilder.m_85982_(pMatrix, (float)pOffsetX, (float)pOffsetY, (float)pOffsetZ)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(pTextureX, pTextureY)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(pPackedLight)
         .m_85977_(pNormals, (float)pNormalX, (float)p_113836_, (float)p_113835_)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(PoisonArrow entity) {
      return getTextureLocation();
   }

   public static ResourceLocation getTextureLocation() {
      return TEXTURE;
   }
}
