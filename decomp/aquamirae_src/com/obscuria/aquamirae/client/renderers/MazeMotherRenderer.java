package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMazeMother;
import com.obscuria.aquamirae.common.entities.MazeMother;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MazeMotherRenderer extends MobRenderer<MazeMother, ModelMazeMother> {
   public MazeMotherRenderer(Context context) {
      super(context, new ModelMazeMother(context.m_174023_(AquamiraeLayers.MAZE_MOTHER)), 1.0F);
      this.m_115326_(new EyesLayer<MazeMother, ModelMazeMother>(this) {
         @NotNull
         public RenderType m_5708_() {
            return RenderType.m_110488_(new ResourceLocation("aquamirae", "textures/entity/maze_mother_overlay.png"));
         }
      });
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull MazeMother entity) {
      return new ResourceLocation("aquamirae", "textures/entity/maze_mother.png");
   }
}
