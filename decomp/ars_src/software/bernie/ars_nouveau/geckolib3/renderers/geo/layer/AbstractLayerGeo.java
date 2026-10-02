package software.bernie.ars_nouveau.geckolib3.renderers.geo.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoLayerRenderer;

public abstract class AbstractLayerGeo<T extends LivingEntity & IAnimatable> extends GeoLayerRenderer<T> {
   protected final Function<T, ResourceLocation> funcGetCurrentTexture;
   protected final Function<T, ResourceLocation> funcGetCurrentModel;
   protected GeoEntityRenderer<T> geoRendererInstance;

   public AbstractLayerGeo(
      GeoEntityRenderer<T> renderer, Function<T, ResourceLocation> currentTextureFunction, Function<T, ResourceLocation> currentModelFunction
   ) {
      super(renderer);
      this.geoRendererInstance = renderer;
      this.funcGetCurrentTexture = currentTextureFunction;
      this.funcGetCurrentModel = currentModelFunction;
   }

   protected void reRenderCurrentModelInRenderer(
      T animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, RenderType renderType
   ) {
      poseStack.m_85836_();
      this.getRenderer()
         .render(
            this.getEntityModel().getModel(this.funcGetCurrentModel.apply(animatable)),
            animatable,
            partialTick,
            renderType,
            poseStack,
            bufferSource,
            bufferSource.m_6299_(renderType),
            packedLight,
            OverlayTexture.f_118083_,
            1.0F,
            1.0F,
            1.0F,
            1.0F
         );
      poseStack.m_85849_();
   }
}
