package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Flare_Bomb_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Flare_Bomb_Renderer extends EntityRenderer<Flare_Bomb_Entity> {
   private static final ResourceLocation OUTER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/monstrosity/flare_bomb_outer.png");
   private static final ResourceLocation INNER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/monstrosity/flare_bomb_inner.png");
   private final Flare_Bomb_Model model;

   public Flare_Bomb_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
      this.model = new Flare_Bomb_Model(renderManagerIn.m_174023_(CMModelLayers.FLARE_BOMB_MODEL));
   }

   public void render(Flare_Bomb_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(new Quaternion(new Vector3f(0.0F, -1.0F, 0.0F), entityYaw, true));
      VertexConsumer VertexConsumer = bufferIn.m_6299_(CMRenderTypes.CMEyes(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, 0.0F, 0.0F);
      this.model.m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      VertexConsumer VertexConsumer2 = bufferIn.m_6299_(CMRenderTypes.CMEyes(OUTER_TEXTURES));
      this.model.m_7695_(matrixStackIn, VertexConsumer2, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.4F);
      matrixStackIn.m_85849_();
   }

   protected int getBlockLightLevel(Flare_Bomb_Entity entityIn, BlockPos pos) {
      return 15;
   }

   public ResourceLocation getTextureLocation(Flare_Bomb_Entity entity) {
      return INNER_TEXTURES;
   }
}
