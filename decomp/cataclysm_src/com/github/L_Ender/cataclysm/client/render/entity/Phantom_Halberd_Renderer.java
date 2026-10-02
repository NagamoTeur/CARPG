package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Phantom_Halberd_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Phantom_Halberd_Renderer extends EntityRenderer<Phantom_Halberd_Entity> {
   private static final ResourceLocation PHANTOM_HALBERD = new ResourceLocation("cataclysm", "textures/entity/maledictus/phantom_halberd.png");
   private static final ResourceLocation PHANTOM_HALBERD_DISCARD = new ResourceLocation("cataclysm", "textures/entity/maledictus/phantom_halberd_discard.png");
   private final Phantom_Halberd_Model model = new Phantom_Halberd_Model();
   private static final RenderType DECAL = RenderType.m_110479_(PHANTOM_HALBERD);
   private static final RenderType RENDER_TYPE = RenderType.m_110458_(PHANTOM_HALBERD);

   public Phantom_Halberd_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(
      Phantom_Halberd_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F - entityIn.m_146908_()));
      matrixStackIn.m_85837_(0.0, 1.0, 0.0);
      matrixStackIn.m_85841_(-0.8F, -0.8F, 0.8F);
      VertexConsumer vertexConsumer = bufferIn.m_6299_(CMRenderTypes.getGhost(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, 0.0F, 0.0F);
      if (entityIn.lifeTicks > 0) {
         float f2 = (float)entityIn.lifeTicks / 70.0F;
         VertexConsumer vertexconsumer = bufferIn.m_6299_(CMRenderTypes.DragonDeath(PHANTOM_HALBERD_DISCARD));
         this.model.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, f2);
         VertexConsumer vertexconsumer1 = bufferIn.m_6299_(DECAL);
         this.model.m_7695_(matrixStackIn, vertexconsumer1, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         VertexConsumer vertexconsumer3 = bufferIn.m_6299_(RENDER_TYPE);
         this.model.m_7695_(matrixStackIn, vertexconsumer3, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   protected int getBlockLightLevel(Phantom_Halberd_Entity entityIn, BlockPos pos) {
      return 15;
   }

   public ResourceLocation getTextureLocation(Phantom_Halberd_Entity entity) {
      return PHANTOM_HALBERD;
   }
}
