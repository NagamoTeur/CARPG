package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.WaterBoulderEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WaterBoulderRenderer extends EntityRenderer<WaterBoulderEntity> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/water_boulder.png");
   private final EntityModel<WaterBoulderEntity> model;

   public WaterBoulderRenderer(Context context) {
      super(context);
      this.model = new WaterBoulderModel(context.m_174023_(WaterBoulderModel.MODEL));
      this.f_114477_ = 0.5F;
   }

   public void render(WaterBoulderEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      float f = Mth.m_14189_(partialTicks, entityIn.f_19859_, entityIn.m_146908_());
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      float scale = 3.0F;
      if (entityIn.f_19797_ < 40) {
         scale = ((float)entityIn.f_19797_ + partialTicks) * 3.0F / 40.0F;
      }

      matrixStackIn.m_85841_(scale, scale, scale);
      matrixStackIn.m_85837_(0.0, -0.5, 0.0);
      this.model.m_6973_(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(this.model.m_103119_(TEXTURE));
      this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(WaterBoulderEntity entity) {
      return TEXTURE;
   }
}
