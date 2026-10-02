package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Wither_Homing_Missile_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class Wither_Homing_Missile_Renderer extends EntityRenderer<Wither_Homing_Missile_Entity> {
   private static final ResourceLocation WITHER_MISSILE = new ResourceLocation("cataclysm", "textures/entity/harbinger/wither_homing_missile.png");
   public Wither_Homing_Missile_Model model = new Wither_Homing_Missile_Model();

   public Wither_Homing_Missile_Renderer(Context manager) {
      super(manager);
   }

   protected int getBlockLightLevel(Wither_Homing_Missile_Entity entity, BlockPos pos) {
      return 15;
   }

   public void render(
      Wither_Homing_Missile_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85841_(-1.5F, -1.5F, 1.5F);
      matrixStackIn.m_85837_(0.0, 0.04F, 0.0);
      float f = Mth.m_14189_(partialTicks, entityIn.f_19859_, entityIn.m_146908_());
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      VertexConsumer vertexconsumer = bufferIn.m_6299_(this.model.m_103119_(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      this.model.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Wither_Homing_Missile_Entity entity) {
      return WITHER_MISSILE;
   }
}
