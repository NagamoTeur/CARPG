package software.bernie.ars_nouveau.geckolib3.renderers.geo.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.ars_nouveau.geckolib3.renderers.texture.AutoGlowingTexture;

public class LayerGlowingAreasGeo<T extends Mob & IAnimatable> extends AbstractLayerGeo<T> {
   protected final Function<ResourceLocation, RenderType> funcGetEmissiveRenderType;

   public LayerGlowingAreasGeo(
      GeoEntityRenderer<T> renderer,
      Function<T, ResourceLocation> currentTextureFunction,
      Function<T, ResourceLocation> currentModelFunction,
      Function<ResourceLocation, RenderType> emissiveRenderTypeFunction
   ) {
      super(renderer, currentTextureFunction, currentModelFunction);
      this.funcGetEmissiveRenderType = emissiveRenderTypeFunction;
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      T animatable,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      this.reRenderCurrentModelInRenderer(
         animatable,
         partialTick,
         poseStack,
         bufferSource,
         packedLight,
         this.funcGetEmissiveRenderType.apply(AutoGlowingTexture.get(this.funcGetCurrentTexture.apply(animatable)))
      );
   }
}
