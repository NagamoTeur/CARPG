package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.model.provider.GeoModelProvider;

public abstract class GeoLayerRenderer<T extends Entity & IAnimatable> {
   protected final IGeoRenderer<T> entityRenderer;

   public GeoLayerRenderer(IGeoRenderer<T> entityRendererIn) {
      this.entityRenderer = entityRendererIn;
   }

   protected void renderCopyModel(
      GeoModelProvider<T> modelProvider,
      ResourceLocation texture,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      T animatable,
      float partialTick,
      float red,
      float green,
      float blue
   ) {
      if (!animatable.m_20145_()) {
         this.renderModel(modelProvider, texture, poseStack, bufferSource, packedLight, animatable, partialTick, red, green, blue);
      }
   }

   protected void renderModel(
      GeoModelProvider<T> modelProvider,
      ResourceLocation texture,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      T animatable,
      float partialTick,
      float red,
      float green,
      float blue
   ) {
      if (animatable instanceof LivingEntity entity) {
         RenderType renderType = this.getRenderType(texture);
         this.getRenderer()
            .render(
               modelProvider.getModel(modelProvider.getModelResource(animatable)),
               animatable,
               partialTick,
               renderType,
               poseStack,
               bufferSource,
               bufferSource.m_6299_(renderType),
               packedLight,
               LivingEntityRenderer.m_115338_(entity, 0.0F),
               red,
               green,
               blue,
               1.0F
            );
      }
   }

   public RenderType getRenderType(ResourceLocation textureLocation) {
      return RenderType.m_110452_(textureLocation);
   }

   public GeoModelProvider<T> getEntityModel() {
      return this.entityRenderer.getGeoModelProvider();
   }

   public IGeoRenderer<T> getRenderer() {
      return this.entityRenderer;
   }

   protected ResourceLocation getEntityTexture(T entityIn) {
      return this.entityRenderer.getTextureLocation(entityIn);
   }

   public abstract void render(
      PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10
   );
}
