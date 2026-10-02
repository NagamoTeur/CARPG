package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RoseSpiritEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class RoseSpiritRenderer extends MobRenderer<RoseSpiritEntity, RoseSpiritModel> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/rose_spirit.png");
   private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
   private static final RenderType BEAM = RenderType.m_110476_(BEAM_TEXTURE);

   public RoseSpiritRenderer(Context context) {
      super(context, new RoseSpiritModel(context.m_174023_(RoseSpiritModel.MODEL)), 0.5F);
      this.m_115326_(new RoseSpiritGlowLayer(this));
   }

   public void render(RoseSpiritEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      LivingEntity owner = entityIn.getOwner();
      if (owner != null) {
         float f3 = (float)owner.m_20185_();
         float f4 = (float)owner.m_20186_();
         float f5 = (float)owner.m_20189_();
         float f6 = (float)((double)f3 - entityIn.m_20185_());
         float f7 = (float)((double)f4 - entityIn.m_20186_());
         float f8 = (float)((double)f5 - entityIn.m_20189_());
         renderCrystalBeams(f6, f7, f8, partialTicks, entityIn.f_19797_, matrixStackIn, bufferIn, packedLightIn);
      }
   }

   public static void renderCrystalBeams(
      float x, float y, float z, float partialTicks, int ticks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      float dist2D = Mth.m_14116_(x * x + z * z);
      float dist = Mth.m_14116_(x * x + y * y + z * z);
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, 1.0, 0.0);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122270_((float)(-Math.atan2((double)z, (double)x) - (Math.PI / 2))));
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122270_((float)(-Math.atan2((double)dist2D, (double)y) - (Math.PI / 2))));
      VertexConsumer vertexconsumer = bufferIn.m_6299_(BEAM);
      float f2 = 0.0F - ((float)ticks + partialTicks) * 0.01F;
      float f3 = Mth.m_14116_(x * x + y * y + z * z) / 32.0F - ((float)ticks + partialTicks) * 0.01F;
      float f4 = 0.0F;
      float f5 = 0.75F;
      float f6 = 0.0F;
      Pose posestack$pose = matrixStackIn.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      float endScale = 0.3F;
      float startScale = 0.2F;

      for (int j = 1; j <= 8; j++) {
         float f7 = Mth.m_14031_((float)((double)j * (Math.PI * 2) / 8.0)) * 0.75F;
         float f8 = Mth.m_14089_((float)((double)j * (Math.PI * 2) / 8.0)) * 0.75F;
         float f9 = (float)j / 8.0F;
         vertexconsumer.m_85982_(matrix4f, f4 * startScale, f5 * startScale, 0.0F)
            .m_6122_(205, 112, 255, 255)
            .m_7421_(f6, f2)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(packedLightIn)
            .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
            .m_5752_();
         vertexconsumer.m_85982_(matrix4f, f4 * endScale, f5 * endScale, dist)
            .m_6122_(0, 0, 0, 255)
            .m_7421_(f6, f3)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(packedLightIn)
            .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
            .m_5752_();
         vertexconsumer.m_85982_(matrix4f, f7 * endScale, f8 * endScale, dist)
            .m_6122_(0, 0, 0, 255)
            .m_7421_(f9, f3)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(packedLightIn)
            .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
            .m_5752_();
         vertexconsumer.m_85982_(matrix4f, f7 * startScale, f8 * startScale, 0.0F)
            .m_6122_(205, 112, 255, 255)
            .m_7421_(f9, f2)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(packedLightIn)
            .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
            .m_5752_();
         f4 = f7;
         f5 = f8;
         f6 = f9;
      }

      matrixStackIn.m_85849_();
   }

   public ResourceLocation getTextureLocation(RoseSpiritEntity entity) {
      return TEXTURE;
   }

   public boolean shouldRender(RoseSpiritEntity entity, Frustum frustrum, double p_114171_, double p_114172_, double p_114173_) {
      return super.m_5523_(entity, frustrum, p_114171_, p_114172_, p_114173_) || entity.getOwner() != null;
   }
}
