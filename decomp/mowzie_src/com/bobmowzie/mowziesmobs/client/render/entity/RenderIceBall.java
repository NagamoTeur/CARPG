package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelIceBall;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderIceBall extends EntityRenderer<EntityIceBall> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/effects/ice_ball.png");
   public ModelIceBall model = new ModelIceBall();

   public RenderIceBall(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(EntityIceBall entity) {
      return TEXTURE;
   }

   public void render(EntityIceBall entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(new Quaternion(new Vector3f(0.0F, -1.0F, 0.0F), entityYaw, true));
      matrixStackIn.m_85837_(0.0, -0.5, 0.0);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110473_(TEXTURE));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, 0.0F, 0.0F);
      this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
   }
}
