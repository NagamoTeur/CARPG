package shadows.apotheosis.village.fletching.arrows;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ExplosiveArrowRenderer extends ArrowRenderer<ExplosiveArrowEntity> {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/entity/explosive_arrow.png");

   public ExplosiveArrowRenderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public ResourceLocation getTextureLocation(ExplosiveArrowEntity entity) {
      return TEXTURES;
   }
}
