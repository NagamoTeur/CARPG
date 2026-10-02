package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelSpinefish;
import com.obscuria.aquamirae.common.entities.Spinefish;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpinefishRenderer extends MobRenderer<Spinefish, ModelSpinefish<Spinefish>> {
   public SpinefishRenderer(Context context) {
      super(context, new ModelSpinefish(context.m_174023_(AquamiraeLayers.SPINEFISH)), 0.3F);
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull Spinefish entity) {
      return new ResourceLocation("aquamirae", "textures/entity/spinefish.png");
   }
}
