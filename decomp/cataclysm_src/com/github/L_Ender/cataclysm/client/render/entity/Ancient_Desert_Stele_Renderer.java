package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Ancient_Desert_Stele_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ancient_Desert_Stele_Renderer extends EntityRenderer<Ancient_Desert_Stele_Entity> {
   private static final ResourceLocation ANCIENT_DESERT_STELE = new ResourceLocation("cataclysm", "textures/entity/ancient_desert_stele.png");
   private final Ancient_Desert_Stele_Model model = new Ancient_Desert_Stele_Model();

   public Ancient_Desert_Stele_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(
      Ancient_Desert_Stele_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F - entityIn.m_146908_()));
      matrixStackIn.m_85837_(0.0, 1.5, 0.0);
      matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
      VertexConsumer vertexconsumer = bufferIn.m_6299_(this.model.m_103119_(this.getTextureLocation(entityIn)));
      this.model.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Ancient_Desert_Stele_Entity entity) {
      return ANCIENT_DESERT_STELE;
   }
}
