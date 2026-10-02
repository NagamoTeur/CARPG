package net.thirdlife.iterrpg.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.thirdlife.iterrpg.client.model.Modelmarrow;
import net.thirdlife.iterrpg.entity.MarrowEntity;

public class MarrowRenderer extends EntityRenderer<MarrowEntity> {
   private static final ResourceLocation texture = new ResourceLocation("iter_rpg:textures/entities/marrow_entity.png");
   private final Modelmarrow model;

   public MarrowRenderer(Context context) {
      super(context);
      this.model = new Modelmarrow(context.m_174023_(Modelmarrow.LAYER_LOCATION));
   }

   public void render(MarrowEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
      VertexConsumer vb = bufferIn.m_6299_(RenderType.m_110452_(this.getTextureLocation(entityIn)));
      poseStack.m_85836_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      poseStack.m_85845_(Vector3f.f_122227_.m_122240_(90.0F + Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      this.model.m_7695_(poseStack, vb, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.0625F);
      poseStack.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(MarrowEntity entity) {
      return texture;
   }
}
