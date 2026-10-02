package io.redspace.ironsspellbooks.entity.spells.black_hole;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class BlackHoleRenderer extends EntityRenderer<BlackHole> {
   private static final ResourceLocation CENTER_TEXTURE = IronsSpellbooks.id("textures/entity/black_hole/black_hole.png");
   private static final ResourceLocation BEAM_TEXTURE = IronsSpellbooks.id("textures/entity/black_hole/beam.png");
   private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);

   public BlackHoleRenderer(Context pContext) {
      super(pContext);
   }

   public void render(BlackHole entity, float pEntityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int pPackedLight) {
      poseStack.m_85836_();
      poseStack.m_85837_(0.0, entity.m_20191_().m_82376_() / 2.0, 0.0);
      float entityScale = entity.m_20205_() * 0.025F;
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      poseStack.m_85841_(0.5F * entityScale, 0.5F * entityScale, 0.5F * entityScale);
      poseStack.m_85845_(this.f_114476_.m_114470_());
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
      poseStack.m_85837_(5.0, 0.0, 0.0);
      VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110473_(CENTER_TEXTURE));
      consumer.m_85982_(poseMatrix, 0.0F, -8.0F, -8.0F)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, 8.0F, -8.0F)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, 8.0F, 8.0F)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, -8.0F, 8.0F)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      poseStack.m_85849_();
      poseStack.m_85836_();
      poseStack.m_85837_(0.0, entity.m_20191_().m_82376_() / 2.0, 0.0);
      float animationProgress = ((float)entity.f_19797_ + partialTicks) / 200.0F;
      float fadeProgress = 0.5F;
      RandomSource randomSource = RandomSource.m_216335_(432L);
      VertexConsumer vertexConsumer = bufferSource.m_6299_(RenderType.m_110436_(BEAM_TEXTURE, 0.0F, 0.0F));
      float segments = Math.min(animationProgress, 0.8F);

      for (int i = 0; (float)i < (segments + segments * segments) / 2.0F * 60.0F; i++) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(randomSource.m_188501_() * 360.0F));
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(randomSource.m_188501_() * 360.0F));
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_(randomSource.m_188501_() * 360.0F));
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(randomSource.m_188501_() * 360.0F));
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(randomSource.m_188501_() * 360.0F));
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_(randomSource.m_188501_() * 360.0F + animationProgress * 90.0F));
         float size1 = (randomSource.m_188501_() * 10.0F + 5.0F + fadeProgress * 5.0F) * entityScale * 0.4F;
         Matrix4f matrix = poseStack.m_85850_().m_85861_();
         Matrix3f normalMatrix2 = poseStack.m_85850_().m_85864_();
         int alpha = (int)(255.0F * (1.0F - fadeProgress));
         drawTriangle(vertexConsumer, matrix, normalMatrix2, size1);
      }

      poseStack.m_85849_();
      super.m_7392_(entity, pEntityYaw, partialTicks, poseStack, bufferSource, pPackedLight);
   }

   public ResourceLocation getTextureLocation(BlackHole pEntity) {
      return IcicleRenderer.TEXTURE;
   }

   private static void vertex01(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
      p_114220_.m_85982_(p_114221_, 0.0F, 0.0F, 0.0F).m_6122_(255, 255, 255, p_114222_).m_5752_();
   }

   private static void vertex2(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
      p_114215_.m_85982_(p_114216_, -HALF_SQRT_3 * p_114218_, p_114217_, -0.5F * p_114218_).m_6122_(255, 0, 255, 0).m_5752_();
   }

   private static void vertex3(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
      p_114224_.m_85982_(p_114225_, HALF_SQRT_3 * p_114227_, p_114226_, -0.5F * p_114227_).m_6122_(255, 0, 255, 0).m_5752_();
   }

   private static void vertex4(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
      p_114229_.m_85982_(p_114230_, 0.0F, p_114231_, 1.0F * p_114232_).m_6122_(255, 0, 255, 0).m_5752_();
   }

   private static void drawTriangle(VertexConsumer consumer, Matrix4f poseMatrix, Matrix3f normalMatrix, float size) {
      consumer.m_85982_(poseMatrix, 0.0F, 0.0F, 0.0F)
         .m_6122_(255, 0, 255, 255)
         .m_7421_(0.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, 3.0F * size, -1.0F * size)
         .m_6122_(0, 0, 0, 0)
         .m_7421_(0.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, 3.0F * size, 1.0F * size)
         .m_6122_(0, 0, 0, 0)
         .m_7421_(1.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, 0.0F, 0.0F)
         .m_6122_(255, 0, 255, 255)
         .m_7421_(1.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }
}
