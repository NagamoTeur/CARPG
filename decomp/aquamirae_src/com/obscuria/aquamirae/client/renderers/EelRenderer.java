package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelEel;
import com.obscuria.aquamirae.common.entities.Eel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EelRenderer extends MobRenderer<Eel, ModelEel> {
   public EelRenderer(Context context) {
      super(context, new ModelEel(context.m_174023_(AquamiraeLayers.EEL)), 0.0F);
      this.m_115326_(new EyesLayer<Eel, ModelEel>(this) {
         @NotNull
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("aquamirae", "textures/entity/eel_overlay.png"));
         }
      });
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull Eel entity) {
      return new ResourceLocation("aquamirae", "textures/entity/eel.png");
   }
}
