package io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import java.util.List;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelPart.Cube;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.example.client.EntityResources;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class GeoHumanoidRenderer<T extends Mob & IAnimatable> extends ExtendedGeoEntityRenderer<T> {
   private ResourceLocation textureResource;
   private static final EquipmentSlot[] SLOTS = new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};

   public GeoHumanoidRenderer(Context renderManager, AnimatedGeoModel<T> model) {
      super(renderManager, model);
      this.f_114477_ = 0.5F;
   }

   @Nullable
   protected ResourceLocation getTextureForBone(String boneName, T animatable) {
      return "bipedCape".equals(boneName) ? EntityResources.EXTENDED_CAPE_TEXTURE : this.modelProvider.getTextureResource(animatable);
   }

   protected boolean isArmorBone(GeoBone bone) {
      return bone.getName().startsWith("armor");
   }

   protected ModelPart getArmorPartForBone(String name, HumanoidModel<?> armorModel) {
      return switch (name) {
         case "armorBipedLeftFoot", "armorBipedLeftLeg", "armorBipedLeftFoot2", "armorBipedLeftLeg2" -> armorModel.f_102814_;
         case "armorBipedRightFoot", "armorBipedRightLeg", "armorBipedRightFoot2", "armorBipedRightLeg2" -> armorModel.f_102813_;
         case "armorBipedRightArm" -> armorModel.f_102811_;
         case "armorBipedLeftArm" -> armorModel.f_102812_;
         case "armorBipedBody" -> armorModel.f_102810_;
         case "armorBipedHead" -> armorModel.f_102808_;
         default -> null;
      };
   }

   protected void prepareArmorPositionAndScale(
      GeoBone bone, List<Cube> cubeList, ModelPart sourceLimb, PoseStack poseStack, boolean geoArmor, boolean modMatrixRot
   ) {
      if (bone.getName().equals("armorLeggingTorsoLayer")) {
         super.prepareArmorPositionAndScale((GeoBone)this.modelProvider.getBone("armorBipedBody"), cubeList, sourceLimb, poseStack, false, modMatrixRot);
      } else {
         super.prepareArmorPositionAndScale(bone, cubeList, sourceLimb, poseStack, false, modMatrixRot);
      }
   }

   protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, T currentEntity) {
      return switch (boneName) {
         case "armorBipedLeftFoot", "armorBipedRightFoot", "armorBipedLeftFoot2", "armorBipedRightFoot2" -> EquipmentSlot.FEET;
         case "armorBipedLeftLeg", "armorBipedRightLeg", "armorBipedLeftLeg2", "armorBipedRightLeg2" -> EquipmentSlot.LEGS;
         case "armorBipedRightArm" -> !currentEntity.m_21526_() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
         case "armorBipedLeftArm" -> currentEntity.m_21526_() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
         case "armorBipedBody" -> EquipmentSlot.CHEST;
         case "armorBipedHead" -> EquipmentSlot.HEAD;
         default -> null;
      };
   }

   protected ItemStack getArmorForBone(String boneName, T currentEntity) {
      return switch (boneName) {
         case "armorBipedLeftFoot", "armorBipedRightFoot", "armorBipedLeftFoot2", "armorBipedRightFoot2" -> currentEntity.m_6844_(EquipmentSlot.FEET);
         case "armorBipedLeftLeg", "armorBipedRightLeg", "armorBipedLeftLeg2", "armorBipedRightLeg2" -> currentEntity.m_6844_(EquipmentSlot.LEGS);
         case "armorBipedBody", "armorBipedRightArm", "armorBipedLeftArm" -> currentEntity.m_6844_(EquipmentSlot.CHEST);
         case "armorBipedHead" -> currentEntity.m_6844_(EquipmentSlot.HEAD);
         default -> null;
      };
   }

   protected void handleArmorRenderingForBone(
      GeoBone bone, PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, ResourceLocation currentTexture
   ) {
      super.handleArmorRenderingForBone(bone, stack, buffer, packedLight, packedOverlay, currentTexture);

      for (EquipmentSlot slot : SLOTS) {
         Item var12 = ((Mob)this.currentEntityBeingRendered).m_6844_(slot).m_41720_();
         if (var12 instanceof GeoArmorItem) {
            GeoArmorItem geoArmorItem = (GeoArmorItem)var12;
            if (GeoArmorRenderer.getRenderer(geoArmorItem.getClass(), this.currentEntityBeingRendered) instanceof GenericCustomArmorRenderer var14) {
               ;
            }
         }
      }
   }

   @Nullable
   protected ItemStack getHeldItemForBone(String boneName, T entity) {
      return switch (boneName) {
         case "bipedHandLeft" -> entity.m_21526_() ? entity.m_21205_() : entity.m_21206_();
         case "bipedHandRight" -> entity.m_21526_() ? entity.m_21206_() : entity.m_21205_();
         default -> null;
      };
   }

   protected TransformType getCameraTransformForItemAtBone(ItemStack stack, String boneName) {
      byte var4 = -1;
      switch (boneName.hashCode()) {
         case -374084450:
            if (boneName.equals("bipedHandLeft")) {
               var4 = 0;
            }
         default:
            return switch (var4) {
               case 0 -> TransformType.THIRD_PERSON_LEFT_HAND;
               default -> TransformType.THIRD_PERSON_RIGHT_HAND;
            };
      }
   }

   @Nullable
   protected BlockState getHeldBlockForBone(String boneName, T animatable) {
      return null;
   }

   protected void preRenderItem(PoseStack poseStack, ItemStack itemStack, String boneName, T animatable, IBone bone) {
      ItemStack mainHandItem = animatable.m_21205_();
      ItemStack offHandItem = animatable.m_21206_();
      poseStack.m_85837_(0.0, 0.0, -0.0625);
      poseStack.m_85837_(0.0, -0.0625, 0.0);
      if (itemStack == mainHandItem) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
         if (itemStack.m_41720_() instanceof ShieldItem) {
            poseStack.m_85837_(0.0, 0.125, -0.25);
         }
      } else if (itemStack == offHandItem) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
         if (itemStack.m_41720_() instanceof ShieldItem) {
            poseStack.m_85837_(0.0, 0.125, 0.25);
            poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
         }
      }
   }

   protected void preRenderBlock(PoseStack poseStack, BlockState state, String boneName, T animatable) {
   }

   protected void postRenderItem(PoseStack poseStack, ItemStack stack, String boneName, T animatable, IBone bone) {
   }

   protected void postRenderBlock(PoseStack poseStack, BlockState state, String boneName, T animatable) {
   }

   public Color getRenderColor(
      T animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight
   ) {
      return animatable.m_20145_()
         ? Color.ofRGBA(1.0F, 1.0F, 1.0F, 0.3F)
         : super.getRenderColor(animatable, partialTick, poseStack, bufferSource, buffer, packedLight);
   }

   public RenderType getRenderType(
      T animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return animatable.m_20145_() ? RenderType.m_110473_(texture) : RenderType.m_110452_(texture);
   }
}
