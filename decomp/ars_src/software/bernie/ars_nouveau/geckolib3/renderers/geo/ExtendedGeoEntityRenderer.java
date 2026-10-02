package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelPart.Cube;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import org.apache.commons.lang3.StringUtils;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoCube;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoQuad;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoVertex;
import software.bernie.ars_nouveau.geckolib3.item.GeoArmorItem;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.util.EModelRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

@OnlyIn(Dist.CLIENT)
public abstract class ExtendedGeoEntityRenderer<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {
   protected static Map<ResourceLocation, IntIntPair> TEXTURE_DIMENSIONS_CACHE = new Object2ObjectOpenHashMap();
   protected static Map<ResourceLocation, Tuple<Integer, Integer>> TEXTURE_SIZE_CACHE = new Object2ObjectOpenHashMap();
   private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = new Object2ObjectOpenHashMap();
   protected static final HumanoidModel<LivingEntity> DEFAULT_BIPED_ARMOR_MODEL_INNER = new HumanoidModel(
      Minecraft.m_91087_().m_167973_().m_171103_(ModelLayers.f_171164_)
   );
   protected static final HumanoidModel<LivingEntity> DEFAULT_BIPED_ARMOR_MODEL_OUTER = new HumanoidModel(
      Minecraft.m_91087_().m_167973_().m_171103_(ModelLayers.f_171165_)
   );
   protected float widthScale;
   protected float heightScale;
   protected T currentEntityBeingRendered;
   private float currentPartialTicks;
   protected ResourceLocation textureForBone = null;
   protected final Queue<Tuple<GeoBone, ItemStack>> HEAD_QUEUE = new ArrayDeque<>();

   protected ExtendedGeoEntityRenderer(Context renderManager, AnimatedGeoModel<T> modelProvider) {
      this(renderManager, modelProvider, 1.0F, 1.0F, 0.0F);
   }

   protected ExtendedGeoEntityRenderer(Context renderManager, AnimatedGeoModel<T> modelProvider, float widthScale, float heightScale, float shadowSize) {
      super(renderManager, modelProvider);
      this.f_114477_ = shadowSize;
      this.widthScale = widthScale;
      this.heightScale = heightScale;
   }

   protected void renderHeads(PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      while (!this.HEAD_QUEUE.isEmpty()) {
         Tuple<GeoBone, ItemStack> entry = this.HEAD_QUEUE.poll();
         GeoBone bone = (GeoBone)entry.m_14418_();
         ItemStack itemStack = (ItemStack)entry.m_14419_();
         GameProfile skullOwnerProfile = null;
         poseStack.m_85836_();
         RenderUtils.translateAndRotateMatrixForBone(poseStack, bone);
         if (itemStack.m_41782_()) {
            Tag skullOwnerTag = itemStack.m_41783_().m_128423_("SkullOwner");
            if (skullOwnerTag != null) {
               if (skullOwnerTag instanceof CompoundTag tag) {
                  skullOwnerProfile = NbtUtils.m_129228_(tag);
               } else if (skullOwnerTag instanceof StringTag tag) {
                  String skullOwner = tag.m_7916_();
                  if (!StringUtils.isBlank(skullOwner)) {
                     SkullBlockEntity.m_155738_(
                        new GameProfile(null, skullOwner), name -> itemStack.m_41783_().m_128365_("SkullOwner", NbtUtils.m_129230_(new CompoundTag(), name))
                     );
                  }
               }
            }
         }

         float relativeScaleX = 1.1875F;
         float relativeScaleY = 1.1875F;
         float relativeScaleZ = 1.1875F;
         if (bone.childCubes.size() > 0) {
            GeoCube firstCube = bone.childCubes.get(0);
            relativeScaleX *= firstCube.size.m_122239_() / 8.0F;
            relativeScaleY *= firstCube.size.m_122260_() / 8.0F;
            relativeScaleZ *= firstCube.size.m_122269_() / 8.0F;
         }

         poseStack.m_85841_(relativeScaleX, relativeScaleY, relativeScaleZ);
         poseStack.m_85837_(-0.5, 0.0, -0.5);
         Type skullBlockType = ((AbstractSkullBlock)((BlockItem)itemStack.m_41720_()).m_40614_()).m_48754_();
         SkullModelBase skullmodelbase = (SkullModelBase)SkullBlockRenderer.m_173661_(Minecraft.m_91087_().m_167973_()).get(skullBlockType);
         RenderType rendertype = SkullBlockRenderer.m_112523_(skullBlockType, skullOwnerProfile);
         SkullBlockRenderer.m_173663_(null, 0.0F, 0.0F, poseStack, buffer, packedLight, skullmodelbase, rendertype);
         poseStack.m_85849_();
      }
   }

   public void render(
      GeoModel model,
      T animatable,
      float partialTick,
      RenderType type,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      this.renderHeads(poseStack, bufferSource, packedLight);
   }

   @Override
   public ResourceLocation getTextureLocation(T animatable) {
      return this.modelProvider.getTextureResource(animatable);
   }

   public void renderLate(
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
      float partialTicks
   ) {
      super.renderLate(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
      this.currentEntityBeingRendered = animatable;
      this.currentPartialTicks = partialTicks;
   }

   protected abstract boolean isArmorBone(GeoBone var1);

   protected void handleArmorRenderingForBone(
      GeoBone bone, PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, ResourceLocation currentTexture
   ) {
      ItemStack armorForBone = this.getArmorForBone(bone.getName(), this.currentEntityBeingRendered);
      EquipmentSlot boneSlot = this.getEquipmentSlotForArmorBone(bone.getName(), this.currentEntityBeingRendered);
      if (armorForBone != null && boneSlot != null) {
         Item armorItem = armorForBone.m_41720_();
         if (armorForBone.m_41720_() instanceof BlockItem blockItem && blockItem.m_40614_() instanceof AbstractSkullBlock) {
            this.HEAD_QUEUE.add(new Tuple(bone, armorForBone));
            return;
         }

         if (armorItem instanceof GeoArmorItem geoArmorItem) {
            GeoArmorRenderer<? extends GeoArmorItem> geoArmorRenderer = GeoArmorRenderer.getRenderer(
               (Class<? extends ArmorItem>)geoArmorItem.getClass(), this.currentEntityBeingRendered
            );
            if (geoArmorRenderer == null) {
               return;
            }

            ModelPart sourceLimb = this.getArmorPartForBone(bone.getName(), geoArmorRenderer);
            if (sourceLimb == null) {
               return;
            }

            List<Cube> cubeList = sourceLimb.f_104212_;
            if (cubeList.isEmpty()) {
               return;
            }

            VertexConsumer ivb = ItemRenderer.m_115184_(
               this.rtb,
               RenderType.m_110431_(
                  GeoArmorRenderer.getRenderer((Class<? extends ArmorItem>)geoArmorItem.getClass(), this.currentEntityBeingRendered)
                     .getTextureLocation((T)geoArmorItem)
               ),
               false,
               armorForBone.m_41790_()
            );
            stack.m_85836_();
            stack.m_85841_(-1.0F, -1.0F, 1.0F);
            this.prepareArmorPositionAndScale(bone, cubeList, sourceLimb, stack, true, boneSlot == EquipmentSlot.CHEST);
            geoArmorRenderer.setCurrentItem(this.currentEntityBeingRendered, armorForBone, boneSlot);
            geoArmorRenderer.applySlot(boneSlot);
            this.setLimbBoneVisible(geoArmorRenderer, sourceLimb, geoArmorRenderer, boneSlot);
            geoArmorRenderer.render(this.currentPartialTicks, stack, ivb, packedLight);
            stack.m_85849_();
         } else if (armorForBone.m_41720_() instanceof ArmorItem armor) {
            HumanoidModel<?> armorModel = (HumanoidModel<?>)ForgeHooksClient.getArmorModel(
               this.currentEntityBeingRendered,
               armorForBone,
               boneSlot,
               boneSlot == EquipmentSlot.LEGS ? DEFAULT_BIPED_ARMOR_MODEL_INNER : DEFAULT_BIPED_ARMOR_MODEL_OUTER
            );
            if (armorModel == null) {
               return;
            }

            ModelPart limb = this.getArmorPartForBone(bone.getName(), armorModel);
            if (limb == null) {
               return;
            }

            List<Cube> cubeList = limb.f_104212_;
            if (cubeList.isEmpty()) {
               return;
            }

            ResourceLocation armorResource = this.getArmorResource(this.currentEntityBeingRendered, armorForBone, boneSlot, null);
            stack.m_85836_();
            stack.m_85841_(-1.0F, -1.0F, 1.0F);
            this.prepareArmorPositionAndScale(bone, cubeList, limb, stack);
            this.renderArmorOfItem(armor, armorForBone, boneSlot, armorResource, limb, stack, packedLight, packedOverlay);
            stack.m_85849_();
         }
      }
   }

   protected void setLimbBoneVisible(GeoArmorRenderer<? extends GeoArmorItem> armorRenderer, ModelPart limb, HumanoidModel<?> armorModel, EquipmentSlot slot) {
      IBone gbHead = armorRenderer.getAndHideBone(armorRenderer.headBone);
      IBone gbBody = armorRenderer.getAndHideBone(armorRenderer.bodyBone);
      IBone gbArmL = armorRenderer.getAndHideBone(armorRenderer.leftArmBone);
      IBone gbArmR = armorRenderer.getAndHideBone(armorRenderer.rightArmBone);
      IBone gbLegL = armorRenderer.getAndHideBone(armorRenderer.leftLegBone);
      IBone gbLegR = armorRenderer.getAndHideBone(armorRenderer.rightLegBone);
      IBone gbBootL = armorRenderer.getAndHideBone(armorRenderer.leftBootBone);
      IBone gbBootR = armorRenderer.getAndHideBone(armorRenderer.rightBootBone);
      if (limb == armorModel.f_102808_ || limb == armorModel.f_102809_) {
         gbHead.setHidden(false);
      } else if (limb == armorModel.f_102810_) {
         gbBody.setHidden(false);
      } else if (limb == armorModel.f_102812_) {
         gbArmL.setHidden(false);
      } else if (limb == armorModel.f_102814_) {
         if (slot == EquipmentSlot.FEET) {
            gbBootL.setHidden(false);
         } else {
            gbLegL.setHidden(false);
         }
      } else if (limb == armorModel.f_102811_) {
         gbArmR.setHidden(false);
      } else if (limb == armorModel.f_102813_) {
         if (slot == EquipmentSlot.FEET) {
            gbBootR.setHidden(false);
         } else {
            gbLegR.setHidden(false);
         }
      }
   }

   @Deprecated(
      forRemoval = true
   )
   protected void handleGeoArmorBoneVisibility(
      GeoArmorRenderer<? extends GeoArmorItem> geoArmorRenderer, ModelPart sourceLimb, HumanoidModel<?> armorModel, EquipmentSlot slot
   ) {
      this.setLimbBoneVisible(geoArmorRenderer, sourceLimb, armorModel, slot);
   }

   protected void renderArmorOfItem(
      ArmorItem armorItem,
      ItemStack armorForBone,
      EquipmentSlot boneSlot,
      ResourceLocation armorResource,
      ModelPart sourceLimb,
      PoseStack poseStack,
      int packedLight,
      int packedOverlay
   ) {
      if (armorItem instanceof DyeableArmorItem dyableArmor) {
         int color = dyableArmor.m_41121_(armorForBone);
         this.renderArmorPart(
            poseStack,
            sourceLimb,
            packedLight,
            packedOverlay,
            (float)(color >> 16 & 0xFF) / 255.0F,
            (float)(color >> 8 & 0xFF) / 255.0F,
            (float)(color & 0xFF) / 255.0F,
            1.0F,
            armorForBone,
            armorResource
         );
         this.renderArmorPart(
            poseStack,
            sourceLimb,
            packedLight,
            packedOverlay,
            1.0F,
            1.0F,
            1.0F,
            1.0F,
            armorForBone,
            this.getArmorResource(this.currentEntityBeingRendered, armorForBone, boneSlot, "overlay")
         );
      } else {
         this.renderArmorPart(poseStack, sourceLimb, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F, armorForBone, armorResource);
      }
   }

   protected void prepareArmorPositionAndScale(GeoBone bone, List<Cube> cubeList, ModelPart sourceLimb, PoseStack stack) {
      this.prepareArmorPositionAndScale(bone, cubeList, sourceLimb, stack, false, false);
   }

   protected void prepareArmorPositionAndScale(
      GeoBone bone, List<Cube> cubeList, ModelPart sourceLimb, PoseStack poseStack, boolean geoArmor, boolean modMatrixRot
   ) {
      GeoCube firstCube = bone.childCubes.get(0);
      Cube armorCube = cubeList.get(0);
      float targetSizeX = firstCube.size.m_122239_();
      float targetSizeY = firstCube.size.m_122260_();
      float targetSizeZ = firstCube.size.m_122269_();
      float sourceSizeX = Math.abs(armorCube.f_104338_ - armorCube.f_104335_);
      float sourceSizeY = Math.abs(armorCube.f_104339_ - armorCube.f_104336_);
      float sourceSizeZ = Math.abs(armorCube.f_104340_ - armorCube.f_104337_);
      float scaleX = targetSizeX / sourceSizeX;
      float scaleY = targetSizeY / sourceSizeY;
      float scaleZ = targetSizeZ / sourceSizeZ;
      sourceLimb.m_104227_(
         -(bone.getPivotX() - (bone.getPivotX() * scaleX - bone.getPivotX()) / scaleX),
         -(bone.getPivotY() - (bone.getPivotY() * scaleY - bone.getPivotY()) / scaleY),
         bone.getPivotZ() - (bone.getPivotZ() * scaleZ - bone.getPivotZ()) / scaleZ
      );
      if (!geoArmor) {
         sourceLimb.f_104203_ = -bone.getRotationX();
         sourceLimb.f_104204_ = -bone.getRotationY();
         sourceLimb.f_104205_ = bone.getRotationZ();
      } else {
         float xRot = bone.getRotationX() * -2.0F;
         float yRot = bone.getRotationY() * -2.0F;
         float zRot = bone.getRotationZ() * 2.0F;

         for (GeoBone parentBone = bone.parent; parentBone != null; parentBone = parentBone.parent) {
            xRot -= parentBone.getRotationX();
            yRot -= parentBone.getRotationY();
            zRot += parentBone.getRotationZ();
         }

         if (modMatrixRot) {
            poseStack.m_85845_(new Quaternion(0.0F, 0.0F, (float)Math.toRadians((double)zRot), false));
            poseStack.m_85845_(new Quaternion(0.0F, (float)Math.toRadians((double)yRot), 0.0F, false));
            poseStack.m_85845_(new Quaternion((float)Math.toRadians((double)xRot), 0.0F, 0.0F, false));
         } else {
            sourceLimb.f_104203_ = xRot;
            sourceLimb.f_104204_ = yRot;
            sourceLimb.f_104205_ = zRot;
         }
      }

      poseStack.m_85841_(scaleX, scaleY, scaleZ);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      MultiBufferSource bufferSource = this.getCurrentRTB();
      if (bufferSource == null) {
         throw new NullPointerException("Can't render with a null RenderTypeBuffer! (GeoEntityRenderer.rtb is null)");
      } else if (this.getCurrentModelRenderCycle() != EModelRenderCycle.INITIAL) {
         super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      } else {
         this.textureForBone = this.getCurrentModelRenderCycle() != EModelRenderCycle.INITIAL
            ? null
            : this.getTextureForBone(bone.getName(), this.currentEntityBeingRendered);
         boolean useCustomTexture = this.textureForBone != null;
         ResourceLocation currentTexture = this.getTextureLocation(this.currentEntityBeingRendered);
         RenderType renderType = useCustomTexture
            ? this.getRenderTypeForBone(
               bone, this.currentEntityBeingRendered, this.currentPartialTicks, poseStack, buffer, bufferSource, packedLight, this.textureForBone
            )
            : this.getRenderType(this.currentEntityBeingRendered, this.currentPartialTicks, poseStack, bufferSource, buffer, packedLight, currentTexture);
         buffer = bufferSource.m_6299_(renderType);
         if (this.getCurrentModelRenderCycle() == EModelRenderCycle.INITIAL) {
            poseStack.m_85836_();
            if (this.isArmorBone(bone)) {
               this.handleArmorRenderingForBone(bone, poseStack, buffer, packedLight, packedOverlay, currentTexture);
            } else {
               ItemStack boneItem = this.getHeldItemForBone(bone.getName(), this.currentEntityBeingRendered);
               BlockState boneBlock = this.getHeldBlockForBone(bone.getName(), this.currentEntityBeingRendered);
               if (boneItem != null || boneBlock != null) {
                  this.handleItemAndBlockBoneRendering(poseStack, bone, boneItem, boneBlock, packedLight, packedOverlay);
                  buffer = bufferSource.m_6299_(RenderType.m_110473_(currentTexture));
               }
            }

            poseStack.m_85849_();
         }

         this.customBoneSpecificRenderingHook(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, useCustomTexture, currentTexture);
         poseStack.m_85836_();
         RenderUtils.prepMatrixForBone(poseStack, bone);
         super.renderCubesOfBone(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
         if (useCustomTexture) {
            buffer = bufferSource.m_6299_(
               this.getRenderType(this.currentEntityBeingRendered, this.currentPartialTicks, poseStack, bufferSource, buffer, packedLight, currentTexture)
            );
            this.textureForBone = null;
         }

         super.renderChildBones(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
         poseStack.m_85849_();
      }
   }

   protected void customBoneSpecificRenderingHook(
      GeoBone bone,
      PoseStack poseStack,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha,
      boolean customTextureMarker,
      ResourceLocation currentTexture
   ) {
   }

   protected void handleItemAndBlockBoneRendering(
      PoseStack poseStack, GeoBone bone, @Nullable ItemStack boneItem, @Nullable BlockState boneBlock, int packedLight, int packedOverlay
   ) {
      RenderUtils.prepMatrixForBone(poseStack, bone);
      RenderUtils.translateAndRotateMatrixForBone(poseStack, bone);
      if (boneItem != null) {
         this.preRenderItem(poseStack, boneItem, bone.getName(), this.currentEntityBeingRendered, bone);
         this.renderItemStack(poseStack, this.getCurrentRTB(), packedLight, boneItem, bone.getName());
         this.postRenderItem(poseStack, boneItem, bone.getName(), this.currentEntityBeingRendered, bone);
      }

      if (boneBlock != null) {
         this.preRenderBlock(poseStack, boneBlock, bone.getName(), this.currentEntityBeingRendered);
         this.renderBlock(poseStack, this.getCurrentRTB(), packedLight, boneBlock);
         this.postRenderBlock(poseStack, boneBlock, bone.getName(), this.currentEntityBeingRendered);
      }
   }

   protected void renderItemStack(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ItemStack stack, String boneName) {
      Minecraft.m_91087_()
         .m_91291_()
         .m_174242_(
            this.currentEntityBeingRendered,
            stack,
            this.getCameraTransformForItemAtBone(stack, boneName),
            false,
            poseStack,
            bufferSource,
            null,
            packedLight,
            LivingEntityRenderer.m_115338_(this.currentEntityBeingRendered, 0.0F),
            this.currentEntityBeingRendered.m_19879_()
         );
   }

   protected RenderType getRenderTypeForBone(
      GeoBone bone,
      T animatable,
      float partialTick,
      PoseStack poseStack,
      VertexConsumer buffer,
      MultiBufferSource bufferSource,
      int packedLight,
      ResourceLocation texture
   ) {
      return this.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
   }

   protected void renderArmorPart(
      PoseStack poseStack,
      ModelPart sourceLimb,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha,
      ItemStack armorStack,
      ResourceLocation texture
   ) {
      VertexConsumer buffer = ItemRenderer.m_115184_(this.getCurrentRTB(), RenderType.m_110431_(texture), false, armorStack.m_41790_());
      sourceLimb.m_104306_(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   @Nullable
   protected abstract ResourceLocation getTextureForBone(String var1, T var2);

   @Nullable
   protected abstract ItemStack getHeldItemForBone(String var1, T var2);

   protected abstract TransformType getCameraTransformForItemAtBone(ItemStack var1, String var2);

   @Nullable
   protected abstract BlockState getHeldBlockForBone(String var1, T var2);

   protected abstract void preRenderItem(PoseStack var1, ItemStack var2, String var3, T var4, IBone var5);

   protected abstract void preRenderBlock(PoseStack var1, BlockState var2, String var3, T var4);

   protected abstract void postRenderItem(PoseStack var1, ItemStack var2, String var3, T var4, IBone var5);

   protected abstract void postRenderBlock(PoseStack var1, BlockState var2, String var3, T var4);

   @Nullable
   protected ItemStack getArmorForBone(String boneName, T animatable) {
      return null;
   }

   @Nullable
   protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, T animatable) {
      return null;
   }

   @Nullable
   protected ModelPart getArmorPartForBone(String name, HumanoidModel<?> armorModel) {
      return null;
   }

   protected ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nonnull String type) {
      String path = ((ArmorItem)stack.m_41720_()).m_40401_().m_6082_();
      String domain = "minecraft";
      String[] materialNameSplit = path.split(":", 2);
      if (materialNameSplit.length > 1) {
         domain = materialNameSplit[0];
         path = materialNameSplit[1];
      }

      String texture = String.format(
         "%s:textures/models/armor/%s_layer_%d%s.png", domain, path, slot == EquipmentSlot.LEGS ? 2 : 1, type == null ? "" : String.format("_%s", type)
      );
      texture = ForgeHooksClient.getArmorTexture(entity, stack, texture, slot, type);
      return ARMOR_TEXTURE_RES_MAP.computeIfAbsent(texture, ResourceLocation::new);
   }

   @Override
   public void createVerticesOfQuad(
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
      if (this.textureForBone == null) {
         super.createVerticesOfQuad(quad, poseState, normal, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      IntIntPair boneTextureSize = this.computeTextureSize(this.textureForBone);
      IntIntPair entityTextureSize = this.computeTextureSize(this.getTextureLocation(this.currentEntityBeingRendered));
      if (boneTextureSize != null && entityTextureSize != null) {
         for (GeoVertex vertex : quad.vertices) {
            Vector4f vector4f = new Vector4f(vertex.position.m_122239_(), vertex.position.m_122260_(), vertex.position.m_122269_(), 1.0F);
            float texU = vertex.textureU * (float)entityTextureSize.firstInt() / (float)boneTextureSize.firstInt();
            float texV = vertex.textureV * (float)entityTextureSize.secondInt() / (float)boneTextureSize.secondInt();
            vector4f.m_123607_(poseState);
            buffer.m_5954_(
               vector4f.m_123601_(),
               vector4f.m_123615_(),
               vector4f.m_123616_(),
               red,
               green,
               blue,
               alpha,
               texU,
               texV,
               packedOverlay,
               packedLight,
               normal.m_122239_(),
               normal.m_122260_(),
               normal.m_122269_()
            );
         }
      } else {
         super.createVerticesOfQuad(quad, poseState, normal, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      }
   }

   protected IntIntPair computeTextureSize(ResourceLocation texture) {
      return TEXTURE_DIMENSIONS_CACHE.computeIfAbsent(texture, RenderUtils::getTextureDimensions);
   }

   protected void renderBlock(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BlockState state) {
      if (state.m_60799_() == RenderShape.MODEL) {
         poseStack.m_85836_();
         poseStack.m_85837_(-0.25, -0.25, -0.25);
         poseStack.m_85841_(0.5F, 0.5F, 0.5F);
         Minecraft.m_91087_().m_91289_().m_110912_(state, poseStack, bufferSource, packedLight, OverlayTexture.f_118083_);
         poseStack.m_85849_();
      }
   }

   @Deprecated(
      forRemoval = true
   )
   protected Tuple<Integer, Integer> getSizeOfTexture(ResourceLocation texture) {
      IntIntPair dimensions = RenderUtils.getTextureDimensions(texture);
      return dimensions == null ? null : new Tuple(dimensions.firstInt(), dimensions.secondInt());
   }

   @Deprecated(
      forRemoval = true
   )
   protected void moveAndRotateMatrixToMatchBone(PoseStack stack, GeoBone bone) {
      RenderUtils.translateAndRotateMatrixForBone(stack, bone);
   }

   @Deprecated(
      forRemoval = true
   )
   protected Tuple<Integer, Integer> getOrCreateTextureSize(ResourceLocation texture) {
      return TEXTURE_SIZE_CACHE.computeIfAbsent(texture, key -> this.getSizeOfTexture(texture));
   }
}
