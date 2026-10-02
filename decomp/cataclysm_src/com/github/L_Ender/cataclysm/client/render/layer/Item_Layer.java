package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.render.RenderUtils;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Item_Layer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private AdvancedModelBox AdvancedModelBox;
   private ItemStack itemstack;
   private TransformType transformType;

   public Item_Layer(RenderLayerParent<T, M> renderer, AdvancedModelBox AdvancedModelBox, ItemStack itemstack, TransformType transformType) {
      super(renderer);
      this.itemstack = itemstack;
      this.AdvancedModelBox = AdvancedModelBox;
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
      if (this.AdvancedModelBox.showModel) {
         matrixStackIn.m_85836_();
         RenderUtils.matrixStackFromCitadelModel(matrixStackIn, this.getAdvancedModelBox());
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

   public AdvancedModelBox getAdvancedModelBox() {
      return this.AdvancedModelBox;
   }

   public void setAdvancedModelBox(AdvancedModelBox AdvancedModelBox) {
      this.AdvancedModelBox = AdvancedModelBox;
   }
}
