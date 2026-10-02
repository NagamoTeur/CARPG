package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.Collections;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.util.EModelRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;
import software.bernie.ars_nouveau.geckolib3.util.IRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public abstract class GeoItemRenderer<T extends Item & IAnimatable> extends BlockEntityWithoutLevelRenderer implements IGeoRenderer<T> {
   protected AnimatedGeoModel<T> modelProvider;
   protected ItemStack currentItemStack;
   protected float widthScale = 1.0F;
   protected float heightScale = 1.0F;
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   protected T animatable;
   protected MultiBufferSource rtb = null;
   private IRenderCycle currentModelRenderCycle = EModelRenderCycle.INITIAL;

   public GeoItemRenderer(AnimatedGeoModel<T> modelProvider) {
      this(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_(), modelProvider);
   }

   public GeoItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet, AnimatedGeoModel<T> modelProvider) {
      super(dispatcher, modelSet);
      this.modelProvider = modelProvider;
   }

   public void setModel(AnimatedGeoModel<T> model) {
      this.modelProvider = model;
   }

   public AnimatedGeoModel<T> getGeoModelProvider() {
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

   public void m_108829_(ItemStack stack, TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
      if (transformType == TransformType.GUI) {
         poseStack.m_85836_();
         BufferSource defaultBufferSource = bufferSource instanceof BufferSource bufferSource2 ? bufferSource2 : Minecraft.m_91087_().m_91269_().m_110104_();
         Lighting.m_84930_();
         this.render((T)stack.m_41720_(), poseStack, bufferSource, packedLight, stack);
         defaultBufferSource.m_109911_();
         RenderSystem.m_69482_();
         Lighting.m_84931_();
         poseStack.m_85849_();
      } else {
         this.render((T)stack.m_41720_(), poseStack, bufferSource, packedLight, stack);
      }
   }

   public void render(T animatable, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ItemStack stack) {
      this.currentItemStack = stack;
      GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(animatable));
      AnimationEvent animationEvent = new AnimationEvent(animatable, 0.0F, 0.0F, Minecraft.m_91087_().m_91296_(), false, Collections.singletonList(stack));
      this.dispatchedMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
      this.modelProvider.setLivingAnimations((T)animatable, this.getInstanceId(animatable), animationEvent);
      poseStack.m_85836_();
      poseStack.m_85837_(0.5, 0.51F, 0.5);
      RenderSystem.m_157456_(0, this.getTextureLocation(animatable));
      Color renderColor = this.getRenderColor(animatable, 0.0F, poseStack, bufferSource, null, packedLight);
      RenderType renderType = this.getRenderType(animatable, 0.0F, poseStack, bufferSource, null, packedLight, this.getTextureLocation(animatable));
      this.render(
         model,
         animatable,
         0.0F,
         renderType,
         poseStack,
         bufferSource,
         null,
         packedLight,
         OverlayTexture.f_118083_,
         (float)renderColor.getRed() / 255.0F,
         (float)renderColor.getGreen() / 255.0F,
         (float)renderColor.getBlue() / 255.0F,
         (float)renderColor.getAlpha() / 255.0F
      );
      poseStack.m_85849_();
   }

   public void renderEarly(
      T animatable,
      PoseStack poseStack,
      float partialTick,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      this.renderEarlyMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.animatable = animatable;
      IGeoRenderer.super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlayIn, red, green, blue, alpha);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.isTrackingXform()) {
         Matrix4f poseState = poseStack.m_85850_().m_85861_().m_27658_();
         Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);
         bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
         localMatrix.m_27648_(new Vector3f(this.getRenderOffset(this.animatable, 1.0F)));
         bone.setLocalSpaceXform(localMatrix);
      }

      RenderType renderType = this.getRenderType(this.animatable, 0.0F, poseStack, this.rtb, null, packedLight, this.getTextureLocation(this.animatable));
      buffer = ItemRenderer.m_115222_(this.rtb, renderType, false, this.currentItemStack != null && this.currentItemStack.m_41790_());
      IGeoRenderer.super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public Vec3 getRenderOffset(T animatable, float partialTick) {
      return Vec3.f_82478_;
   }

   public ResourceLocation getTextureLocation(T animatable) {
      return this.modelProvider.getTextureResource(animatable);
   }

   @Deprecated(
      forRemoval = true
   )
   public Integer getUniqueID(T animatable) {
      return this.getInstanceId(animatable);
   }

   public int getInstanceId(T animatable) {
      return GeckoLibUtil.getIDFromStack(this.currentItemStack);
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
      AnimationController.addModelFetcher(animatable -> {
         if (animatable instanceof Item item && IClientItemExtensions.of(item).getCustomRenderer() instanceof GeoItemRenderer geoItemRenderer) {
            return geoItemRenderer.getGeoModelProvider();
         }

         return null;
      });
   }
}
