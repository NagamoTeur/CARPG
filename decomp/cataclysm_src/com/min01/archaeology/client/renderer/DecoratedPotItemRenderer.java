package com.min01.archaeology.client.renderer;

import com.min01.archaeology.block.DecoratedPotBlock;
import com.min01.archaeology.blockentity.DecoratedPotBlockEntity;
import com.min01.archaeology.init.ArchaeologyBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DecoratedPotItemRenderer extends BlockEntityWithoutLevelRenderer {
   private final BlockEntityRenderDispatcher dispatcher;

   public DecoratedPotItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
      super(dispatcher, modelSet);
      this.dispatcher = dispatcher;
   }

   public void m_108829_(
      @NotNull ItemStack stack, @NotNull TransformType transformType, PoseStack pose, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay
   ) {
      pose.m_85836_();
      DecoratedPotBlockEntity decoratedPot = new DecoratedPotBlockEntity(
         BlockPos.f_121853_, ((DecoratedPotBlock)ArchaeologyBlocks.DECORATED_POT.get()).m_49966_()
      );
      decoratedPot.setFromItem(stack);
      this.dispatcher.m_112272_(decoratedPot, pose, buffer, 240, OverlayTexture.f_118083_);
      pose.m_85849_();
   }
}
