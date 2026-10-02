package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.NethermanCloneModel;
import net.xylonity.knightquest.common.entity.boss.NethermanCloneEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NethermanCloneRenderer extends GeoEntityRenderer<NethermanCloneEntity> {
   public NethermanCloneRenderer(Context renderManager) {
      super(renderManager, new NethermanCloneModel());
   }

   protected float getDeathMaxRotation(NethermanCloneEntity animatable) {
      return 0.0F;
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull NethermanCloneEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/netherman_clone.png");
   }

   public RenderType getRenderType(
      NethermanCloneEntity animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return RenderType.m_110473_(this.getTextureLocation(animatable));
   }

   protected void applyRotations(NethermanCloneEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
      rotationYaw += (float)(Math.sin((double)(3 * animatable.m_217043_().m_216339_(-100, 100))) * 2.5);
      super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
   }

   public void render(
      @NotNull NethermanCloneEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight
   ) {
      poseStack.m_85841_(1.2F, 1.2F, 1.2F);
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
