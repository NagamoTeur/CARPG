package com.hollingsworth.arsnouveau.client.renderer.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoItemRenderer;

public class FixedGeoItemRenderer<T extends Item & IAnimatable> extends GeoItemRenderer {
   public FixedGeoItemRenderer(AnimatedGeoModel modelProvider) {
      super(modelProvider);
   }

   @Override
   public void m_108829_(ItemStack itemStack, TransformType transformType, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
      if (transformType == TransformType.GUI) {
         stack.m_85836_();
         BufferSource irendertypebuffer$impl = Minecraft.m_91087_().m_91269_().m_110104_();
         Lighting.m_84930_();
         this.render(itemStack.m_41720_(), stack, bufferIn, 15728880, itemStack, transformType);
         irendertypebuffer$impl.m_109911_();
         RenderSystem.m_69482_();
         Lighting.m_84931_();
         stack.m_85849_();
      } else {
         this.render(itemStack.m_41720_(), stack, bufferIn, combinedLightIn, itemStack, transformType);
      }
   }

   public void render(Item animatable, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn, ItemStack itemStack, TransformType transformType) {
      this.currentItemStack = itemStack;
      GeoModel model = this.modelProvider instanceof TransformAnimatedModel
         ? this.modelProvider.getModel(((TransformAnimatedModel)this.modelProvider).getModelResource((T)animatable, transformType))
         : this.modelProvider.getModel(this.modelProvider.getModelResource((T)animatable));
      AnimationEvent itemEvent = new AnimationEvent((T)animatable, 0.0F, 0.0F, Minecraft.m_91087_().m_91296_(), false, Collections.singletonList(itemStack));
      if (this.modelProvider != null) {
         this.modelProvider.setCustomAnimations((T)animatable, this.getInstanceId((T)animatable), itemEvent);
         stack.m_85836_();
         stack.m_85837_(0.0, 0.01F, 0.0);
         stack.m_85837_(0.5, 0.5, 0.5);
         RenderSystem.m_157456_(0, this.getTextureLocation((T)animatable));
         Color renderColor = this.getRenderColor((T)animatable, 0.0F, stack, bufferIn, null, packedLightIn);
         RenderType renderType = this.getRenderType((T)animatable, 0.0F, stack, bufferIn, null, packedLightIn, this.getTextureLocation((T)animatable));
         this.render(
            model,
            (T)animatable,
            0.0F,
            renderType,
            stack,
            bufferIn,
            null,
            packedLightIn,
            OverlayTexture.f_118083_,
            (float)renderColor.getRed() / 255.0F,
            (float)renderColor.getGreen() / 255.0F,
            (float)renderColor.getBlue() / 255.0F,
            (float)renderColor.getAlpha() / 255.0F
         );
         stack.m_85849_();
      }
   }
}
