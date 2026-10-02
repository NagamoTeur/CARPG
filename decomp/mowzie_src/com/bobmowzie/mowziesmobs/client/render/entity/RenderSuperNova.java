package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelSuperNova;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderSuperNova extends EntityRenderer<EntitySuperNova> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/effects/super_nova.png");
   public static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_1.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_2.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_3.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_4.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_5.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_6.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_7.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_8.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_9.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_10.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_11.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_12.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_13.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_14.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_15.png"),
      new ResourceLocation("mowziesmobs", "textures/effects/super_nova_16.png")
   };
   public ModelSuperNova<EntitySuperNova> model = new ModelSuperNova<>();

   public RenderSuperNova(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(EntitySuperNova entity) {
      int index = entity.f_19797_ % TEXTURES.length;
      return TEXTURES[index];
   }

   public void render(EntitySuperNova entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(MMRenderType.getGlowingEffect(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, 0.0F, 0.0F);
      this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
   }
}
