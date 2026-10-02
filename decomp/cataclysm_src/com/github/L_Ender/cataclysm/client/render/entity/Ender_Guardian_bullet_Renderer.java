package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Ender_Guardian_Bullet_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Ender_Guardian_Bullet_Entity;
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
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ender_Guardian_bullet_Renderer extends EntityRenderer<Ender_Guardian_Bullet_Entity> {
   private static final ResourceLocation ENDER_GUARDIAN_TEXTURE = new ResourceLocation("cataclysm", "textures/entity/shulkerbullet.png");
   private static final RenderType ENDER_GUARDIAN_RENDER_TYPE = RenderType.m_110473_(ENDER_GUARDIAN_TEXTURE);
   public Ender_Guardian_Bullet_Model model = new Ender_Guardian_Bullet_Model();

   public Ender_Guardian_bullet_Renderer(Context manager) {
      super(manager);
   }

   protected int getBlockLightLevel(Ender_Guardian_Bullet_Entity entity, BlockPos pos) {
      return 15;
   }

   public void render(
      Ender_Guardian_Bullet_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      float f = this.rotLerp(entityIn.f_19859_, entityIn.m_146908_(), partialTicks);
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      float f2 = (float)entityIn.f_19797_ + partialTicks;
      matrixStackIn.m_85837_(0.0, 0.15F, 0.0);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14031_(f2 * 0.1F) * 180.0F));
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14089_(f2 * 0.1F) * 180.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14031_(f2 * 0.15F) * 360.0F));
      matrixStackIn.m_85841_(-0.5F, -0.5F, 0.5F);
      this.model.m_6973_(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer VertexConsumer = bufferIn.m_6299_(this.model.m_103119_(ENDER_GUARDIAN_TEXTURE));
      this.model.m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85841_(1.5F, 1.5F, 1.5F);
      VertexConsumer VertexConsumer1 = bufferIn.m_6299_(ENDER_GUARDIAN_RENDER_TYPE);
      this.model.m_7695_(matrixStackIn, VertexConsumer1, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.15F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Ender_Guardian_Bullet_Entity entity) {
      return ENDER_GUARDIAN_TEXTURE;
   }

   private float rotLerp(float prevRotation, float rotation, float partialTicks) {
      float f = rotation - prevRotation;

      while (f < -180.0F) {
         f += 360.0F;
      }

      while (f >= 180.0F) {
         f -= 360.0F;
      }

      return prevRotation + partialTicks * f;
   }
}
