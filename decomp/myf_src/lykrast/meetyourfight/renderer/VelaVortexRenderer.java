package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.VelaVortexEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class VelaVortexRenderer extends EntityRenderer<VelaVortexEntity> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/vortex.png");
   private final EntityModel<VelaVortexEntity> model;

   public VelaVortexRenderer(Context context) {
      super(context);
      this.model = new VelaVortexModel(context.m_174023_(VelaVortexModel.MODEL));
      this.f_114477_ = 0.5F;
   }

   public void render(VelaVortexEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      float f = Mth.m_14189_(partialTicks, entityIn.f_19859_, entityIn.m_146908_());
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      float scale = 2.25F;
      if (entityIn.f_19797_ < 20) {
         scale = ((float)entityIn.f_19797_ + partialTicks) * 2.25F / 20.0F;
      }

      matrixStackIn.m_85841_(scale, 1.0F, scale);
      matrixStackIn.m_85837_(0.0, -1.0, 0.0);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(((float)entityIn.f_19797_ + partialTicks) * 0.25F * 180.0F));
      this.model.m_6973_(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(this.model.m_103119_(TEXTURE));
      this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(VelaVortexEntity entity) {
      return TEXTURE;
   }
}
