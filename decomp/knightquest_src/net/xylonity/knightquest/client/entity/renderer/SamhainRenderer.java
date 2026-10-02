package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.xylonity.knightquest.client.entity.model.SamhainModel;
import net.xylonity.knightquest.common.entity.entities.SamhainEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

public class SamhainRenderer extends ExtendedGeoEntityRenderer<SamhainEntity> {
   protected ItemStack mainHandItem;
   protected ItemStack offHandItem;

   public SamhainRenderer(Context renderManager) {
      super(renderManager, new SamhainModel());
   }

   public void renderEarly(
      SamhainEntity animatable,
      PoseStack poseStack,
      float partialTick,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
      this.mainHandItem = animatable.m_6844_(EquipmentSlot.MAINHAND);
      this.offHandItem = animatable.m_6844_(EquipmentSlot.OFFHAND);
   }

   @Nullable
   protected ItemStack getHeldItemForBone(String s, SamhainEntity samhainEntity) {
      return switch (s) {
         case "bipedHandLeft" -> samhainEntity.m_21526_() ? this.mainHandItem : this.offHandItem;
         case "bipedHandRight" -> samhainEntity.m_21526_() ? this.offHandItem : this.mainHandItem;
         default -> null;
      };
   }

   protected TransformType getCameraTransformForItemAtBone(ItemStack itemStack, String s) {
      return switch (s) {
         case "bipedHandLeft", "bipedHandRight" -> TransformType.THIRD_PERSON_RIGHT_HAND;
         default -> TransformType.NONE;
      };
   }

   @Nullable
   protected BlockState getHeldBlockForBone(String s, SamhainEntity samhainEntity) {
      return null;
   }

   protected void preRenderItem(PoseStack poseStack, ItemStack itemStack, String s, SamhainEntity samhainEntity, IBone iBone) {
      AnimationController<?> controller = (AnimationController<?>)((SamhainEntity)this.animatable)
         .getFactory()
         .getOrCreateAnimationData(((SamhainEntity)this.animatable).m_19879_())
         .getAnimationControllers()
         .get("controller");
      if (controller != null && controller.getCurrentAnimation() != null) {
         String currentAnimationName = controller.getCurrentAnimation().animationName.toLowerCase();
         if (currentAnimationName.contains("sit")) {
            poseStack.m_85841_(0.0F, 0.0F, 0.0F);
            return;
         }
      }

      if (itemStack == this.mainHandItem) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
         poseStack.m_85837_(0.05, 0.1, -0.45);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(0.0F));
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_(0.0F));
      } else if (itemStack == this.offHandItem) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
         poseStack.m_85837_(0.05, 0.1, -0.45);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_(0.0F));
      }
   }

   protected void preRenderBlock(PoseStack poseStack, BlockState blockState, String s, SamhainEntity samhainEntity) {
   }

   protected void postRenderItem(PoseStack poseStack, ItemStack itemStack, String s, SamhainEntity samhainEntity, IBone iBone) {
   }

   protected void postRenderBlock(PoseStack poseStack, BlockState blockState, String s, SamhainEntity samhainEntity) {
   }

   public ResourceLocation getTextureLocation(SamhainEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/samhain.png");
   }

   protected boolean isArmorBone(GeoBone geoBone) {
      return geoBone.getName().startsWith("armor");
   }

   @Nullable
   protected ResourceLocation getTextureForBone(String s, SamhainEntity samhainEntity) {
      return null;
   }

   public void render(SamhainEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.35F, 0.35F, 0.35F);
      } else {
         poseStack.m_85841_(0.7F, 0.7F, 0.7F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
