package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.block.tile.ImbuementTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ImbuementRenderer extends ArsGeoBlockRenderer<ImbuementTile> {
   MultiBufferSource buffer;
   ImbuementTile tile;
   ResourceLocation text;

   public ImbuementRenderer(Context p_i226006_1_) {
      super(p_i226006_1_, new GenericModel<>("imbuement_chamber"));
   }

   public void renderEarly(
      ImbuementTile animatable,
      PoseStack stackIn,
      float ticks,
      MultiBufferSource renderTypeBuffer,
      VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      this.tile = animatable;
      this.buffer = renderTypeBuffer;
      this.text = this.getTextureLocation(animatable);
      super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
   }

   @Override
   public void m_6922_(BlockEntity tile, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int lightIn, int overlayIn) {
      super.m_6922_(tile, v, matrixStack, iRenderTypeBuffer, lightIn, overlayIn);
      ImbuementTile tileEntityIn = (ImbuementTile)tile;
      this.tile = tileEntityIn;
      double x = (double)tile.m_58899_().m_123341_();
      double y = (double)tile.m_58899_().m_123342_();
      double z = (double)tile.m_58899_().m_123343_();
      if (tileEntityIn.entity == null || !ItemStack.m_41728_(tileEntityIn.entity.m_32055_(), tileEntityIn.stack)) {
         tileEntityIn.entity = new ItemEntity(tile.m_58904_(), x, y, z, tileEntityIn.stack);
      }

      if (tileEntityIn.entity != null) {
         ItemEntity entityItem = tileEntityIn.entity;
         tileEntityIn.frames = tileEntityIn.frames + 1.5F * Minecraft.m_91087_().m_91297_();
         entityItem.m_5616_(tileEntityIn.frames);
         entityItem.f_31985_ = (int)tileEntityIn.frames;
         matrixStack.m_85836_();
         matrixStack.m_85841_(0.75F, 0.75F, 0.75F);
         float offset = 0.685F;
         Minecraft.m_91087_()
            .m_91290_()
            .m_114384_(entityItem, (double)offset, 0.3, (double)offset, entityItem.f_19857_, 0.0F, matrixStack, iRenderTypeBuffer, lightIn);
         matrixStack.m_85849_();
      }
   }
}
