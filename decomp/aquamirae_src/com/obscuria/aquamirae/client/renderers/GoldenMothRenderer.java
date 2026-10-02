package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelGoldenMoth;
import com.obscuria.aquamirae.common.entities.GoldenMoth;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GoldenMothRenderer extends MobRenderer<GoldenMoth, ModelGoldenMoth<GoldenMoth>> {
   public GoldenMothRenderer(Context context) {
      super(context, new ModelGoldenMoth(context.m_174023_(AquamiraeLayers.GOLDEN_MOTH)), 0.2F);
      this.m_115326_(new EyesLayer<GoldenMoth, ModelGoldenMoth<GoldenMoth>>(this) {
         @NotNull
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("aquamirae", "textures/entity/golden_moth_overlay.png"));
         }
      });
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull GoldenMoth entity) {
      return new ResourceLocation("aquamirae", "textures/entity/golden_moth.png");
   }
}
