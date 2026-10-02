package com.hollingsworth.arsnouveau.client.renderer.entity.familiar;

import com.hollingsworth.arsnouveau.client.renderer.entity.BookwyrmModel;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarBookwyrm;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class FamiliarBookwyrmRenderer<T extends FamiliarBookwyrm> extends GenericFamiliarRenderer<T> {
   public FamiliarBookwyrmRenderer(Context renderManager) {
      super(renderManager, new BookwyrmModel());
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      stack.m_85836_();
      stack.m_85841_(0.5F, 0.5F, 0.5F);
      super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
      stack.m_85849_();
   }
}
