package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Void_Rune_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Void_Rune_Renderer extends EntityRenderer<Void_Rune_Entity> {
   private static final ResourceLocation VOID_RUNE = new ResourceLocation("cataclysm", "textures/entity/void_rune.png");
   private final Void_Rune_Model model = new Void_Rune_Model();
   private final Random rnd = new Random();

   public Void_Rune_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public void render(Void_Rune_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F - entityIn.m_146908_()));
      matrixStackIn.m_85837_(0.0, 3.0, 0.0);
      matrixStackIn.m_85841_(-2.0F, -2.0F, 2.0F);
      VertexConsumer vertexConsumer = bufferIn.m_6299_(CMRenderTypes.getBright(this.getTextureLocation(entityIn)));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, (float)entityIn.f_19797_ + partialTicks, 0.0F, 0.0F);
      this.model.m_7695_(matrixStackIn, vertexConsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public Vec3 getRenderOffset(Void_Rune_Entity entityIn, float partialTicks) {
      if (entityIn.activateProgress == 10.0F) {
         return super.m_7860_(entityIn, partialTicks);
      } else {
         double d0 = 0.02;
         return new Vec3(this.rnd.nextGaussian() * d0, 0.0, this.rnd.nextGaussian() * d0);
      }
   }

   protected int getBlockLightLevel(Void_Rune_Entity entityIn, BlockPos pos) {
      return 15;
   }

   public ResourceLocation getTextureLocation(Void_Rune_Entity entity) {
      return VOID_RUNE;
   }
}
