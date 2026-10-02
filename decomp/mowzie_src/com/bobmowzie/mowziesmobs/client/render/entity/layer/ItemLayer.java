package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private AdvancedModelRenderer modelRenderer;
   private ItemStack itemstack;
   private TransformType transformType;

   public ItemLayer(RenderLayerParent<T, M> renderer, AdvancedModelRenderer modelRenderer, ItemStack itemstack, TransformType transformType) {
      super(renderer);
      this.itemstack = itemstack;
      this.modelRenderer = modelRenderer;
      this.transformType = transformType;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (this.modelRenderer.showModel && !this.modelRenderer.isHidden()) {
         matrixStackIn.m_85836_();
         MowzieRenderUtils.matrixStackFromModel(matrixStackIn, this.getModelRenderer());
         Minecraft.m_91087_()
            .m_91290_()
            .m_234586_()
            .m_109322_(entitylivingbaseIn, this.getItemstack(), this.transformType, false, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.m_85849_();
      }
   }

   public ItemStack getItemstack() {
      return this.itemstack;
   }

   public void setItemstack(ItemStack itemstack) {
      this.itemstack = itemstack;
   }

   public AdvancedModelRenderer getModelRenderer() {
      return this.modelRenderer;
   }

   public void setModelRenderer(AdvancedModelRenderer modelRenderer) {
      this.modelRenderer = modelRenderer;
   }
}
