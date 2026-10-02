package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.cataclysm.blocks.Altar_Of_Abyss_Block;
import com.github.L_Ender.cataclysm.client.model.block.Altar_of_Abyss_Model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RendererAltar_of_Abyss<T extends AltarOfAbyss_Block_Entity> implements BlockEntityRenderer<T> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/block/altar_of_abyss.png");
   private static final Altar_of_Abyss_Model MODEL = new Altar_of_Abyss_Model();

   public RendererAltar_of_Abyss(Context rendererDispatcherIn) {
   }

   public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      matrixStackIn.m_85836_();
      Direction dir = (Direction)tileEntityIn.m_58900_().m_61143_(Altar_Of_Abyss_Block.FACING);
      if (dir == Direction.NORTH) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else if (dir == Direction.EAST) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else if (dir == Direction.SOUTH) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else if (dir == Direction.WEST) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      }

      matrixStackIn.m_85845_(dir.m_122424_().m_122406_());
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      matrixStackIn.m_85836_();
      MODEL.animate(tileEntityIn, partialTicks);
      MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
      this.renderItem(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
   }

   public void renderItem(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn) {
      ItemStack stack = tileEntityIn.m_8020_(0);
      float f2 = (float)tileEntityIn.tickCount + partialTicks;
      if (!stack.m_41619_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.9F, 0.5);
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f2));
         BakedModel ibakedmodel = Minecraft.m_91087_().m_91291_().m_174264_(stack, tileEntityIn.m_58904_(), (LivingEntity)null, 0);
         boolean flag = ibakedmodel.m_7539_();
         if (!flag) {
            matrixStackIn.m_85837_(0.0, 0.0, 0.0);
         }

         Minecraft.m_91087_()
            .m_91291_()
            .m_115143_(stack, TransformType.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, OverlayTexture.f_118083_, ibakedmodel);
         matrixStackIn.m_85849_();
      }
   }
}
