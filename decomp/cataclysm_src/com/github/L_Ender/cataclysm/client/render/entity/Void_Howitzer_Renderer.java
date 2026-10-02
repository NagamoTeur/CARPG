package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Void_Howitzer_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Howitzer_Entity;
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
public class Void_Howitzer_Renderer extends EntityRenderer<Void_Howitzer_Entity> {
   private static final ResourceLocation VOID_HOWITZER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/void_howitzer.png");
   private final Void_Howitzer_Model model = new Void_Howitzer_Model();

   public Void_Howitzer_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(
      Void_Howitzer_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85841_(1.5F, 1.5F, 1.5F);
      matrixStackIn.m_85837_(0.0, 0.25, 0.0);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 180.0F));
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      VertexConsumer VertexConsumer = bufferIn.m_6299_(RenderType.m_110473_(this.getTextureLocation(entityIn)));
      this.model.m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   protected int getBlockLightLevel(Void_Howitzer_Entity entityIn, BlockPos pos) {
      return 15;
   }

   public ResourceLocation getTextureLocation(Void_Howitzer_Entity entity) {
      return VOID_HOWITZER_TEXTURES;
   }
}
