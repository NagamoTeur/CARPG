package lykrast.meetyourfight.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.SwampMineEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SwampMineRenderer extends EntityRenderer<SwampMineEntity> {
   private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/swampmine.png");
   private final EntityModel<SwampMineEntity> model;

   public SwampMineRenderer(Context context) {
      super(context);
      this.model = new SwampMineModel(context.m_174023_(SwampMineModel.MODEL));
      this.f_114477_ = 0.5F;
   }

   public void render(SwampMineEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      float f = Mth.m_14189_(partialTicks, entityIn.f_19859_, entityIn.m_146908_());
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      matrixStackIn.m_85837_(0.0, -0.5, 0.0);
      this.model.m_6973_(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(this.model.m_103119_(TEXTURE));
      int overlay = entityIn.f_19797_ / 5 % 2 == 0 ? OverlayTexture.m_118093_(OverlayTexture.m_118088_(1.0F), 10) : OverlayTexture.f_118083_;
      this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(SwampMineEntity entity) {
      return TEXTURE;
   }
}
