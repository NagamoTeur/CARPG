package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelAncientDart;
import com.github.alexthe666.alexsmobs.entity.EntityTossedItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderTossedItem extends EntityRenderer<EntityTossedItem> {
   public static final ResourceLocation DART_TEXTURE = new ResourceLocation("alexsmobs:textures/entity/ancient_dart.png");
   public static final ModelAncientDart DART_MODEL = new ModelAncientDart();

   public RenderTossedItem(Context renderManager) {
      super(renderManager);
   }

   public ResourceLocation getTextureLocation(EntityTossedItem entity) {
      return TextureAtlas.f_118259_;
   }

   public void render(EntityTossedItem entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      if (entityIn.isDart()) {
         matrixStackIn.m_85837_(0.0, -0.15F, 0.0);
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 180.0F));
         matrixStackIn.m_85836_();
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
         matrixStackIn.m_85837_(0.0, 0.5, 0.0);
         matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(DART_MODEL.m_103119_(DART_TEXTURE));
         DART_MODEL.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      } else {
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
         matrixStackIn.m_85837_(0.0, 0.5, 0.0);
         matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85845_(new Quaternion(Vector3f.f_122225_, 0.0F, true));
         matrixStackIn.m_85845_(new Quaternion(Vector3f.f_122226_, ((float)entityIn.f_19797_ + partialTicks) * 30.0F, true));
         matrixStackIn.m_85837_(0.0, -0.15F, 0.0);
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(entityIn.m_7846_(), TransformType.GROUND, packedLightIn, OverlayTexture.f_118083_, matrixStackIn, bufferIn, 0);
      }

      matrixStackIn.m_85849_();
   }
}
