package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Ignis_Fireball_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
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
public class Ignis_Abyss_Fireball_Renderer extends EntityRenderer<Ignis_Abyss_Fireball_Entity> {
   private static final ResourceLocation IGNIS_FIRE_BALL = new ResourceLocation("cataclysm", "textures/entity/ignis_fireball_abyss.png");
   public Ignis_Fireball_Model model = new Ignis_Fireball_Model();

   public Ignis_Abyss_Fireball_Renderer(Context manager) {
      super(manager);
   }

   protected int getBlockLightLevel(Ignis_Abyss_Fireball_Entity entity, BlockPos pos) {
      return 15;
   }

   public void render(
      Ignis_Abyss_Fireball_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      float f = this.rotLerp(entityIn.f_19859_, entityIn.m_146908_(), partialTicks);
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      float f2 = (float)entityIn.f_19797_ + partialTicks;
      matrixStackIn.m_85837_(0.0, 0.3F, 0.0);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14031_(f2 * 0.1F) * 180.0F));
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14089_(f2 * 0.1F) * 180.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14031_(f2 * 0.15F) * 360.0F));
      this.model.m_6973_(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer VertexConsumer = bufferIn.m_6299_(this.model.m_103119_(this.getTextureLocation(entityIn)));
      this.model.m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Ignis_Abyss_Fireball_Entity entity) {
      return IGNIS_FIRE_BALL;
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
