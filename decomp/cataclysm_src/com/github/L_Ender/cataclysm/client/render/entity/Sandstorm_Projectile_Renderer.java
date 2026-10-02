package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Sandstorm_Projectile_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Sandstorm_Projectile_Renderer extends EntityRenderer<Sandstorm_Projectile> {
   private static final ResourceLocation SANDSTORM = new ResourceLocation("cataclysm", "textures/entity/koboleton/sandstorm.png");
   public Sandstorm_Projectile_Model model = new Sandstorm_Projectile_Model();

   public Sandstorm_Projectile_Renderer(Context manager) {
      super(manager);
   }

   public void render(
      Sandstorm_Projectile entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85841_(-0.5F, -0.5F, 0.5F);
      matrixStackIn.m_85837_(0.0, -1.5, 0.0);
      float f = Mth.m_14189_(partialTicks, entityIn.f_19859_, entityIn.m_146908_());
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      VertexConsumer vertexconsumer = bufferIn.m_6299_(this.model.m_103119_(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, f, f1);
      this.model.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Sandstorm_Projectile entity) {
      return SANDSTORM;
   }
}
