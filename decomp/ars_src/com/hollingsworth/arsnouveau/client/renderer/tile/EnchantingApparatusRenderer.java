package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class EnchantingApparatusRenderer extends ArsGeoBlockRenderer<EnchantingApparatusTile> {
   MultiBufferSource buffer;
   EnchantingApparatusTile tile;
   ResourceLocation text;

   public EnchantingApparatusRenderer(Context p_i226006_1_) {
      super(p_i226006_1_, new GenericModel<>("enchanting_apparatus"));
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("frame_all") && this.tile.getStack() != null) {
         double x = (double)this.tile.m_58899_().m_123341_();
         double y = (double)this.tile.m_58899_().m_123342_();
         double z = (double)this.tile.m_58899_().m_123343_();
         if (this.tile.renderEntity == null || !ItemStack.m_41728_(this.tile.renderEntity.m_32055_(), this.tile.getStack())) {
            this.tile.renderEntity = new ItemEntity(this.tile.m_58904_(), x, y, z, this.tile.getStack());
         }

         stack.m_85836_();
         RenderUtils.translateMatrixToBone(stack, bone);
         stack.m_85837_(0.0, 0.35, 0.0);
         stack.m_85841_(0.75F, 0.75F, 0.75F);
         ItemStack itemstack = this.tile.renderEntity.m_32055_();
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(itemstack, TransformType.GROUND, packedLightIn, OverlayTexture.f_118083_, stack, this.buffer, (int)this.tile.m_58899_().m_121878_());
         stack.m_85849_();
         bufferIn = this.buffer.m_6299_(RenderType.m_110458_(this.text));
      }

      super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }

   public void renderEarly(
      EnchantingApparatusTile animatable,
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
      try {
         super.m_6922_(tile, v, matrixStack, iRenderTypeBuffer, lightIn, overlayIn);
         EnchantingApparatusTile tileEntityIn = (EnchantingApparatusTile)tile;
         this.tile = tileEntityIn;
      } catch (Exception var8) {
         var8.printStackTrace();
      }
   }
}
