package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Sandstorm_Model;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
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

public class Sandstorm_Renderer extends EntityRenderer<Sandstorm_Entity> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/entity/ancient_remnant/sandstorm.png");
   private final Sandstorm_Model model = new Sandstorm_Model();

   public Sandstorm_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(Sandstorm_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, 3.0, 0.0);
      matrixStackIn.m_85841_(-2.0F, -2.0F, 2.0F);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110473_(TEXTURE));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, 0.0F, 0.0F);
      this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Sandstorm_Entity entity) {
      return TEXTURE;
   }
}
