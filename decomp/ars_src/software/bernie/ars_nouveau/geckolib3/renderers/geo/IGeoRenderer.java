package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoCube;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoQuad;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoVertex;
import software.bernie.ars_nouveau.geckolib3.model.provider.GeoModelProvider;
import software.bernie.ars_nouveau.geckolib3.util.EModelRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.IRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public interface IGeoRenderer<T> {
   MultiBufferSource getCurrentRTB();

   GeoModelProvider getGeoModelProvider();

   ResourceLocation getTextureLocation(T var1);

   default void render(
      GeoModel model,
      T animatable,
      float partialTick,
      RenderType type,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      this.setCurrentRTB(bufferSource);
      this.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      if (bufferSource != null) {
         buffer = bufferSource.m_6299_(type);
      }

      this.renderLate(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);

      for (GeoBone group : model.topLevelBones) {
         this.renderRecursively(group, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      this.setCurrentModelRenderCycle(EModelRenderCycle.REPEATED);
   }

   default void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      poseStack.m_85836_();
      RenderUtils.prepMatrixForBone(poseStack, bone);
      this.renderCubesOfBone(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      this.renderChildBones(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      poseStack.m_85849_();
   }

   default void renderCubesOfBone(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (!bone.isHidden()) {
         for (GeoCube cube : bone.childCubes) {
            if (!bone.cubesAreHidden()) {
               poseStack.m_85836_();
               this.renderCube(cube, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
               poseStack.m_85849_();
            }
         }
      }
   }

   default void renderChildBones(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (!bone.childBonesAreHiddenToo()) {
         for (GeoBone childBone : bone.childBones) {
            this.renderRecursively(childBone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
         }
      }
   }

   default void renderCube(
      GeoCube cube, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      RenderUtils.translateToPivotPoint(poseStack, cube);
      RenderUtils.rotateMatrixAroundCube(poseStack, cube);
      RenderUtils.translateAwayFromPivotPoint(poseStack, cube);
      Matrix3f normalisedPoseState = poseStack.m_85850_().m_85864_();
      Matrix4f poseState = poseStack.m_85850_().m_85861_();

      for (GeoQuad quad : cube.quads) {
         if (quad != null) {
            Vector3f normal = quad.normal.m_122281_();
            normal.m_122249_(normalisedPoseState);
            if ((cube.size.m_122260_() == 0.0F || cube.size.m_122269_() == 0.0F) && normal.m_122239_() < 0.0F) {
               normal.m_122263_(-1.0F, 1.0F, 1.0F);
            }

            if ((cube.size.m_122239_() == 0.0F || cube.size.m_122269_() == 0.0F) && normal.m_122260_() < 0.0F) {
               normal.m_122263_(1.0F, -1.0F, 1.0F);
            }

            if ((cube.size.m_122239_() == 0.0F || cube.size.m_122260_() == 0.0F) && normal.m_122269_() < 0.0F) {
               normal.m_122263_(1.0F, 1.0F, -1.0F);
            }

            this.createVerticesOfQuad(quad, poseState, normal, buffer, packedLight, packedOverlay, red, green, blue, alpha);
         }
      }
   }

   default void createVerticesOfQuad(
      GeoQuad quad,
      Matrix4f poseState,
      Vector3f normal,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      for (GeoVertex vertex : quad.vertices) {
         Vector4f vector4f = new Vector4f(vertex.position.m_122239_(), vertex.position.m_122260_(), vertex.position.m_122269_(), 1.0F);
         vector4f.m_123607_(poseState);
         buffer.m_5954_(
            vector4f.m_123601_(),
            vector4f.m_123615_(),
            vector4f.m_123616_(),
            red,
            green,
            blue,
            alpha,
            vertex.textureU,
            vertex.textureV,
            packedOverlay,
            packedLight,
            normal.m_122239_(),
            normal.m_122260_(),
            normal.m_122269_()
         );
      }
   }

   default void renderEarly(
      T animatable,
      PoseStack poseStack,
      float partialTick,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      if (this.getCurrentModelRenderCycle() == EModelRenderCycle.INITIAL) {
         float width = this.getWidthScale(animatable);
         float height = this.getHeightScale(animatable);
         poseStack.m_85841_(width, height, width);
      }
   }

   default void renderLate(
      T animatable,
      PoseStack poseStack,
      float partialTick,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
   }

   default RenderType getRenderType(
      T animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return RenderType.m_110452_(texture);
   }

   default Color getRenderColor(
      T animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight
   ) {
      return Color.WHITE;
   }

   default int getInstanceId(T animatable) {
      return animatable.hashCode();
   }

   default void setCurrentModelRenderCycle(IRenderCycle cycle) {
   }

   @Nonnull
   default IRenderCycle getCurrentModelRenderCycle() {
      return EModelRenderCycle.INITIAL;
   }

   default void setCurrentRTB(MultiBufferSource bufferSource) {
   }

   default float getWidthScale(T animatable) {
      return 1.0F;
   }

   default float getHeightScale(T entity) {
      return 1.0F;
   }

   @Deprecated(
      forRemoval = true
   )
   default void preparePositionRotationScale(GeoBone bone, PoseStack poseStack) {
      RenderUtils.prepMatrixForBone(poseStack, bone);
   }
}
