package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelBipedAnimated;
import com.bobmowzie.mowziesmobs.client.render.entity.MowzieGeoArmorRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class GeckoArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
   public GeckoArmorLayer(RenderLayerParent<T, M> layerParent, A innerModel, A outerModel) {
      super(layerParent, innerModel, outerModel);
   }

   protected void m_117118_(PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_, A p_117124_) {
      ItemStack itemstack = p_117121_.m_6844_(p_117122_);
      if (itemstack.m_41720_() instanceof ArmorItem) {
         ArmorItem armoritem = (ArmorItem)itemstack.m_41720_();
         if (armoritem.m_40402_() == p_117122_) {
            Model model = this.getArmorModelHook(p_117121_, itemstack, p_117122_, p_117124_);
            if (model instanceof HumanoidModel<T> humanoidModel) {
               ((HumanoidModel)this.m_117386_()).m_102872_(humanoidModel);
               this.m_117125_(p_117124_, p_117122_);
               this.m_117125_(humanoidModel, p_117122_);
               boolean flag = this.m_117128_(p_117122_);
               boolean flag1 = itemstack.m_41790_();
               if (armoritem instanceof DyeableLeatherItem) {
                  int i = ((DyeableLeatherItem)armoritem).m_41121_(itemstack);
                  float f = (float)(i >> 16 & 0xFF) / 255.0F;
                  float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
                  float f2 = (float)(i & 0xFF) / 255.0F;
                  this.renderModel(p_117119_, p_117120_, p_117123_, flag1, model, f, f1, f2, this.getArmorResource(p_117121_, itemstack, p_117122_, null));
                  ModelBipedAnimated.setUseMatrixMode(humanoidModel, true);
                  this.renderModel(
                     p_117119_, p_117120_, p_117123_, flag1, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_117121_, itemstack, p_117122_, "overlay")
                  );
               } else {
                  this.renderModel(
                     p_117119_, p_117120_, p_117123_, flag1, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_117121_, itemstack, p_117122_, null)
                  );
               }
            }
         }
      }
   }

   private void renderModel(
      PoseStack p_117107_,
      MultiBufferSource p_117108_,
      int p_117109_,
      boolean p_117111_,
      Model p_117112_,
      float p_117114_,
      float p_117115_,
      float p_117116_,
      ResourceLocation armorResource
   ) {
      VertexConsumer vertexconsumer = ItemRenderer.m_115184_(p_117108_, RenderType.m_110431_(armorResource), false, p_117111_);
      if (p_117112_ instanceof MowzieGeoArmorRenderer) {
         ((MowzieGeoArmorRenderer)p_117112_).usingCustomPlayerAnimations = true;
      }

      p_117112_.m_7695_(p_117107_, vertexconsumer, p_117109_, OverlayTexture.f_118083_, p_117114_, p_117115_, p_117116_, 1.0F);
   }
}
