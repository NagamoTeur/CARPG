package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Axe_Blade_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Axe_Blade_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Axe_Blade_Renderer extends EntityRenderer<Axe_Blade_Entity> {
   private static final ResourceLocation[] TEXTURE_PROGRESS = new ResourceLocation[5];
   public Axe_Blade_Model model = new Axe_Blade_Model();

   public Axe_Blade_Renderer(Context manager) {
      super(manager);

      for (int i = 0; i < 5; i++) {
         TEXTURE_PROGRESS[i] = new ResourceLocation("cataclysm", "textures/entity/draugar/axe_blade_" + i + ".png");
      }
   }

   protected int getBlockLightLevel(Axe_Blade_Entity entity, BlockPos pos) {
      return 15;
   }

   public void render(Axe_Blade_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixStackIn.m_85837_(0.0, 0.0, 0.0);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(entityIn.m_146908_() + 180.0F));
      VertexConsumer vertexconsumer = bufferIn.m_6299_(CMRenderTypes.getGhost(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, 0.0F, 0.0F);
      float hide = (float)entityIn.getTransparency() / 80.0F;
      float alpha = 1.0F - hide;
      this.model.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, Mth.m_14036_(alpha, 0.0F, 1.0F));
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Axe_Blade_Entity entity) {
      return this.getGrowingTexture(entity, (int)((float)entity.f_19797_ * 0.5F % 4.0F));
   }

   public ResourceLocation getGrowingTexture(Axe_Blade_Entity entity, int age) {
      return TEXTURE_PROGRESS[Mth.m_14045_(age, 0, 4)];
   }
}
