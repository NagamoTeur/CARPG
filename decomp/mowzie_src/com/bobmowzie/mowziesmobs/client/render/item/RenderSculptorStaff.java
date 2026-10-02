package com.bobmowzie.mowziesmobs.client.render.item;

import com.bobmowzie.mowziesmobs.client.model.item.ModelSculptorStaff;
import com.bobmowzie.mowziesmobs.server.item.ItemSculptorStaff;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RenderSculptorStaff extends GeoItemRenderer<ItemSculptorStaff> {
   public RenderSculptorStaff() {
      super(new ModelSculptorStaff());
   }

   public void m_108829_(
      ItemStack itemStack, TransformType transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      super.m_108829_(itemStack, transformType, matrixStack, bufferIn, combinedLightIn, combinedOverlayIn);
   }
}
