package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelTorturedSoul;
import com.obscuria.aquamirae.common.entities.TorturedSoul;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TorturedSoulRenderer extends MobRenderer<TorturedSoul, ModelTorturedSoul> {
   public TorturedSoulRenderer(Context context) {
      super(context, new ModelTorturedSoul(context.m_174023_(AquamiraeLayers.TORTURED_SOUL)), 0.5F);
      this.m_115326_(new EyesLayer<TorturedSoul, ModelTorturedSoul>(this) {
         @NotNull
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("aquamirae", "textures/entity/tortured_soul_overlay.png"));
         }
      });
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull TorturedSoul entity) {
      return new ResourceLocation("aquamirae", "textures/entity/tortured_soul.png");
   }

   protected boolean isShaking(@NotNull TorturedSoul entity) {
      return true;
   }
}
