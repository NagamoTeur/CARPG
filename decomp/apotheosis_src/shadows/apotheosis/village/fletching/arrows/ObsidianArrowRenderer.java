package shadows.apotheosis.village.fletching.arrows;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ObsidianArrowRenderer extends ArrowRenderer<ObsidianArrowEntity> {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/entity/obsidian_arrow.png");

   public ObsidianArrowRenderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public ResourceLocation getTextureLocation(ObsidianArrowEntity entity) {
      return TEXTURES;
   }
}
