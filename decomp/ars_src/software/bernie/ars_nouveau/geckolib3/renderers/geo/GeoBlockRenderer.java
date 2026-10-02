package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.util.EModelRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.IRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public abstract class GeoBlockRenderer<T extends BlockEntity & IAnimatable> implements IGeoRenderer<T>, BlockEntityRenderer {
   protected final AnimatedGeoModel<T> modelProvider;
   protected float widthScale = 1.0F;
   protected float heightScale = 1.0F;
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   protected T animatable;
   protected MultiBufferSource rtb = null;
   private IRenderCycle currentModelRenderCycle = EModelRenderCycle.INITIAL;

   public GeoBlockRenderer(Context rendererProvider, AnimatedGeoModel<T> modelProvider) {
      this.modelProvider = modelProvider;
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

   public void m_6922_(BlockEntity tile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
      this.render((T)tile, partialTicks, poseStack, bufferSource, packedLight);
   }

   public void render(T tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(tile));
      this.modelProvider.setLivingAnimations(tile, Integer.valueOf(this.getInstanceId(tile)));
      this.dispatchedMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
      poseStack.m_85836_();
      poseStack.m_85837_(0.0, 0.01F, 0.0);
      poseStack.m_85837_(0.5, 0.0, 0.5);
      this.rotateBlock(this.getFacing(tile), poseStack);
      RenderSystem.m_157456_(0, this.getTextureLocation(tile));
      Color renderColor = this.getRenderColor(tile, partialTick, poseStack, bufferSource, null, packedLight);
      RenderType renderType = this.getRenderType(tile, partialTick, poseStack, bufferSource, null, packedLight, this.getTextureLocation(tile));
      this.render(
         model,
         tile,
         partialTick,
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

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.isTrackingXform()) {
         Matrix4f poseState = poseStack.m_85850_().m_85861_().m_27658_();
         Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);
         BlockPos pos = this.animatable.m_58899_();
         bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
         localMatrix.m_27648_(new Vector3f(this.getRenderOffset(this.animatable, 1.0F)));
         bone.setLocalSpaceXform(localMatrix);
         Matrix4f worldState = localMatrix.m_27658_();
         worldState.m_27648_(new Vector3f((float)pos.m_123341_(), (float)pos.m_123342_(), (float)pos.m_123343_()));
         bone.setWorldSpaceXform(worldState);
      }

      IGeoRenderer.super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public Vec3 getRenderOffset(T animatable, float partialTick) {
      return Vec3.f_82478_;
   }

   @Deprecated(
      forRemoval = true
   )
   public Integer getUniqueID(T animatable) {
      return this.getInstanceId(animatable);
   }

   public int getInstanceId(T animatable) {
      return animatable.m_58899_().hashCode();
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

   protected void rotateBlock(Direction facing, PoseStack poseStack) {
      switch (facing) {
         case SOUTH:
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            break;
         case WEST:
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
            break;
         case NORTH:
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(0.0F));
            break;
         case EAST:
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
            break;
         case UP:
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
            break;
         case DOWN:
            poseStack.m_85845_(Vector3f.f_122222_.m_122240_(90.0F));
      }
   }

   private Direction getFacing(T tile) {
      BlockState blockState = tile.m_58900_();
      if (blockState.m_61138_(HorizontalDirectionalBlock.f_54117_)) {
         return (Direction)blockState.m_61143_(HorizontalDirectionalBlock.f_54117_);
      } else {
         return blockState.m_61138_(DirectionalBlock.f_52588_) ? (Direction)blockState.m_61143_(DirectionalBlock.f_52588_) : Direction.NORTH;
      }
   }

   public ResourceLocation getTextureLocation(T animatable) {
      return this.modelProvider.getTextureResource(animatable);
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
      AnimationController.addModelFetcher(object -> {
         if (object instanceof BlockEntity tile && Minecraft.m_91087_().m_167982_().m_112265_(tile) instanceof GeoBlockRenderer blockRenderer) {
            return blockRenderer.getGeoModelProvider();
         }

         return null;
      });
   }
}
