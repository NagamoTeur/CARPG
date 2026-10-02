package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Amethyst_Cluster_Projectile_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
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
public class Amethyst_Cluster_Projectile_Renderer extends EntityRenderer<Amethyst_Cluster_Projectile_Entity> {
   private static final ResourceLocation WITHER_HOWITZER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/amethyst_cluster_projectile.png");
   private final Amethyst_Cluster_Projectile_Model model = new Amethyst_Cluster_Projectile_Model();

   public Amethyst_Cluster_Projectile_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(
      Amethyst_Cluster_Projectile_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_()) + 90.0F));
      VertexConsumer VertexConsumer = bufferIn.m_6299_(RenderType.m_110473_(this.getTextureLocation(entityIn)));
      this.model.m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   protected int getBlockLightLevel(Amethyst_Cluster_Projectile_Entity entityIn, BlockPos pos) {
      return 15;
   }

   public ResourceLocation getTextureLocation(Amethyst_Cluster_Projectile_Entity entity) {
      return WITHER_HOWITZER_TEXTURES;
   }
}
