package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Dimensional_Rift_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Dimensional_Rift_Renderer extends EntityRenderer<Dimensional_Rift_Entity> {
   private static final ResourceLocation TEXTURE_IDLE_1 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_idle1.png"
   );
   private static final ResourceLocation TEXTURE_IDLE_2 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_idle2.png"
   );
   private static final ResourceLocation TEXTURE_IDLE_3 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_idle3.png"
   );
   private static final ResourceLocation TEXTURE_IDLE_4 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_idle4.png"
   );
   private static final ResourceLocation TEXTURE_GROW_1 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_grow_0.png"
   );
   private static final ResourceLocation TEXTURE_GROW_2 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_grow_1.png"
   );
   private static final ResourceLocation TEXTURE_GROW_3 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_grow_2.png"
   );
   private static final ResourceLocation TEXTURE_GROW_4 = new ResourceLocation(
      "cataclysm", "textures/entity/leviathan/dimensional_rift/dimensional_rift_grow_3.png"
   );

   public Dimensional_Rift_Renderer(Context mgr) {
      super(mgr);
   }

   public void render(
      Dimensional_Rift_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      ResourceLocation tex = entityIn.getStage() < 1
         ? TEXTURE_GROW_1
         : (
            entityIn.getStage() < 2
               ? TEXTURE_GROW_2
               : (entityIn.getStage() < 3 ? TEXTURE_GROW_3 : (entityIn.getStage() < 4 ? TEXTURE_GROW_4 : this.getIdleTexture(entityIn.f_19797_ % 9)))
         );
      matrixStackIn.m_85845_(this.f_114476_.m_114470_());
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
      matrixStackIn.m_85841_(7.0F, 7.0F, 7.0F);
      Pose posestack$pose = matrixStackIn.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      VertexConsumer vertexconsumer = bufferIn.m_6299_(CMRenderTypes.getfullBright(tex));
      vertex(vertexconsumer, matrix4f, matrix3f, packedLightIn, 0.0F, 0, 0, 1);
      vertex(vertexconsumer, matrix4f, matrix3f, packedLightIn, 1.0F, 0, 1, 1);
      vertex(vertexconsumer, matrix4f, matrix3f, packedLightIn, 1.0F, 1, 1, 0);
      vertex(vertexconsumer, matrix4f, matrix3f, packedLightIn, 0.0F, 1, 0, 0);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private static void vertex(
      VertexConsumer p_114090_, Matrix4f p_114091_, Matrix3f p_114092_, int p_114093_, float p_114094_, int p_114095_, int p_114096_, int p_114097_
   ) {
      p_114090_.m_85982_(p_114091_, p_114094_ - 0.5F, (float)p_114095_ - 0.25F, 0.0F)
         .m_6122_(255, 255, 255, 255)
         .m_7421_((float)p_114096_, (float)p_114097_)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(p_114093_)
         .m_85977_(p_114092_, 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(Dimensional_Rift_Entity entity) {
      return TEXTURE_IDLE_1;
   }

   public ResourceLocation getIdleTexture(int age) {
      if (age < 3) {
         return TEXTURE_IDLE_1;
      } else if (age < 6) {
         return TEXTURE_IDLE_2;
      } else {
         return age < 10 ? TEXTURE_IDLE_3 : TEXTURE_IDLE_4;
      }
   }
}
