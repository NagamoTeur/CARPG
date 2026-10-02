package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelPoisonedChakra;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PoisonedChakraRenderer extends DynamicProjectileRenderer<PoisonedChakra> {
   public PoisonedChakraRenderer(Context context) {
      super(context, new ModelPoisonedChakra(context.m_174023_(AquamiraeLayers.POISONED_CHAKRA)));
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull PoisonedChakra poisonedChakra) {
      return new ResourceLocation("aquamirae", "textures/entity/poisoned_chakra.png");
   }

   public ResourceLocation getGlowingTextureLocation(PoisonedChakra poisonedChakra) {
      return null;
   }
}
