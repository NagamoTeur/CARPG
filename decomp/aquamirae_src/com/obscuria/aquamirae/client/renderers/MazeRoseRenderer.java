package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMazeRose;
import com.obscuria.aquamirae.common.entities.projectiles.MazeRose;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MazeRoseRenderer extends DynamicProjectileRenderer<MazeRose> {
   public MazeRoseRenderer(Context context) {
      super(context, new ModelMazeRose(context.m_174023_(AquamiraeLayers.MAZE_ROSE)));
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull MazeRose mazeRose) {
      return new ResourceLocation("aquamirae", "textures/entity/maze_rose.png");
   }

   public ResourceLocation getGlowingTextureLocation(MazeRose mazeRose) {
      return new ResourceLocation("aquamirae", "textures/entity/maze_rose_overlay.png");
   }
}
