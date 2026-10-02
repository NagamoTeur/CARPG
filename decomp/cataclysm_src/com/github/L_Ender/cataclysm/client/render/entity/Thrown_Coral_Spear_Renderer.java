package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Coral_Spear_Model;
import com.github.L_Ender.cataclysm.entity.projectile.ThrownCoral_Spear_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Thrown_Coral_Spear_Renderer extends EntityRenderer<ThrownCoral_Spear_Entity> {
   private static final ResourceLocation VOID_HOWITZER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/coral_spear.png");
   private final Coral_Spear_Model model = new Coral_Spear_Model();

   public Thrown_Coral_Spear_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(
      ThrownCoral_Spear_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_()) + 90.0F));
      VertexConsumer vertexconsumer = ItemRenderer.m_115222_(bufferIn, this.model.m_103119_(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
      this.model.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(ThrownCoral_Spear_Entity entity) {
      return VOID_HOWITZER_TEXTURES;
   }
}
