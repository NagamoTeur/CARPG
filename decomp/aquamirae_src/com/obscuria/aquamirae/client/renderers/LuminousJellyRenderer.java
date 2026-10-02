package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelLuminousJelly;
import com.obscuria.aquamirae.common.entities.LuminousJelly;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LuminousJellyRenderer extends MobRenderer<LuminousJelly, ModelLuminousJelly<LuminousJelly>> {
   public LuminousJellyRenderer(Context context) {
      super(context, new ModelLuminousJelly(context.m_174023_(AquamiraeLayers.LUMINOUS_JELLY)), 1.5F);
      this.m_115326_(new EyesLayer<LuminousJelly, ModelLuminousJelly<LuminousJelly>>(this) {
         @NotNull
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("aquamirae", "textures/entity/luminous_jelly_overlay.png"));
         }
      });
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull LuminousJelly entity) {
      return new ResourceLocation("aquamirae", "textures/entity/luminous_jelly.png");
   }
}
