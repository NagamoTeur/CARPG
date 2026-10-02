package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.Collections;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatableModel;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.GeoModelProvider;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;
import software.bernie.ars_nouveau.geckolib3.util.AnimationUtils;
import software.bernie.ars_nouveau.geckolib3.util.EModelRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.IRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class GeoProjectilesRenderer<T extends Entity & IAnimatable> extends EntityRenderer<T> implements IGeoRenderer<T> {
   protected final AnimatedGeoModel<T> modelProvider;
   protected float widthScale = 1.0F;
   protected float heightScale = 1.0F;
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   protected T animatable;
   private IRenderCycle currentModelRenderCycle = EModelRenderCycle.INITIAL;
   protected MultiBufferSource rtb = null;

   public GeoProjectilesRenderer(Context renderManager, AnimatedGeoModel<T> modelProvider) {
      super(renderManager);
      this.modelProvider = modelProvider;
   }

   public void m_7392_(T animatable, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(animatable));
      this.dispatchedMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
      poseStack.m_85836_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTick, animatable.f_19859_, animatable.m_146908_()) - 90.0F));
      poseStack.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTick, animatable.f_19860_, animatable.m_146909_())));
      AnimationEvent<T> predicate = new AnimationEvent<>(animatable, 0.0F, 0.0F, partialTick, false, Collections.singletonList(new EntityModelData()));
      this.modelProvider.setLivingAnimations((T)animatable, this.getInstanceId(animatable), predicate);
      RenderSystem.m_157456_(0, this.m_5478_(animatable));
      Color renderColor = this.getRenderColor(animatable, partialTick, poseStack, bufferSource, null, packedLight);
      RenderType renderType = this.getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLight, this.m_5478_(animatable));
      if (!animatable.m_20177_(Minecraft.m_91087_().f_91074_)) {
         this.render(
            model,
            animatable,
            partialTick,
            renderType,
            poseStack,
            bufferSource,
            null,
            packedLight,
            getPackedOverlay(animatable, 0.0F),
            (float)renderColor.getRed() / 255.0F,
            (float)renderColor.getGreen() / 255.0F,
            (float)renderColor.getBlue() / 255.0F,
            (float)renderColor.getAlpha() / 255.0F
         );
      }

      poseStack.m_85849_();
      super.m_7392_(animatable, yaw, partialTick, poseStack, bufferSource, packedLight);
   }

   public void renderEarly(
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
      this.renderEarlyMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.animatable = animatable;
      IGeoRenderer.super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.isTrackingXform()) {
         Matrix4f poseState = poseStack.m_85850_().m_85861_().m_27658_();
         Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);
         bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
         localMatrix.m_27648_(new Vector3f(this.m_7860_(this.animatable, 1.0F)));
         bone.setLocalSpaceXform(localMatrix);
         Matrix4f worldState = localMatrix.m_27658_();
         worldState.m_27648_(new Vector3f(this.animatable.m_20182_()));
         bone.setWorldSpaceXform(worldState);
      }

      IGeoRenderer.super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public static int getPackedOverlay(Entity entity, float uIn) {
      return OverlayTexture.m_118093_(OverlayTexture.m_118088_(uIn), OverlayTexture.m_118096_(false));
   }

   @Override
   public GeoModelProvider<T> getGeoModelProvider() {
      return this.modelProvider;
   }

   @Nonnull
   @AvailableSince("3.1.24")
   @Override
   public IRenderCycle getCurrentModelRenderCycle() {
      return this.currentModelRenderCycle;
   }

   @AvailableSince("3.1.24")
   @Override
   public void setCurrentModelRenderCycle(IRenderCycle currentModelRenderCycle) {
      this.currentModelRenderCycle = currentModelRenderCycle;
   }

   @AvailableSince("3.1.24")
   public float getWidthScale(T animatable) {
      return this.widthScale;
   }

   @AvailableSince("3.1.24")
   public float getHeightScale(T entity) {
      return this.heightScale;
   }

   public ResourceLocation m_5478_(T animatable) {
      return this.modelProvider.getTextureResource(animatable);
   }

   @Deprecated(
      forRemoval = true
   )
   public Integer getUniqueID(T animatable) {
      return this.getInstanceId(animatable);
   }

   public int getInstanceId(T animatable) {
      return animatable.m_20148_().hashCode();
   }

   @Override
   public void setCurrentRTB(MultiBufferSource bufferSource) {
      this.rtb = bufferSource;
   }

   @Override
   public MultiBufferSource getCurrentRTB() {
      return this.rtb;
   }

   static {
      AnimationController.addModelFetcher(
         animatable -> animatable instanceof Entity entity ? (IAnimatableModel)AnimationUtils.getGeoModelForEntity(entity) : null
      );
   }
}
