package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import software.bernie.ars_nouveau.geckolib3.compat.PatchouliCompat;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;
import software.bernie.ars_nouveau.geckolib3.util.EModelRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.IRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public abstract class GeoReplacedEntityRenderer<T extends IAnimatable> extends EntityRenderer implements IGeoRenderer {
   protected static final Map<Class<? extends IAnimatable>, GeoReplacedEntityRenderer> renderers = new ConcurrentHashMap<>();
   protected final AnimatedGeoModel<IAnimatable> modelProvider;
   protected T animatable;
   protected final List<GeoLayerRenderer> layerRenderers = new ObjectArrayList();
   protected IAnimatable currentAnimatable;
   protected float widthScale = 1.0F;
   protected float heightScale = 1.0F;
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   protected MultiBufferSource rtb = null;
   private IRenderCycle currentModelRenderCycle = EModelRenderCycle.INITIAL;

   public GeoReplacedEntityRenderer(Context renderManager, AnimatedGeoModel<IAnimatable> modelProvider, T animatable) {
      super(renderManager);
      this.modelProvider = modelProvider;
      this.animatable = animatable;
      renderers.putIfAbsent((Class<? extends IAnimatable>)animatable.getClass(), this);
   }

   public static GeoReplacedEntityRenderer getRenderer(Class<? extends IAnimatable> animatableClass) {
      return renderers.get(animatableClass);
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
   @Override
   public float getWidthScale(Object animatable) {
      return this.widthScale;
   }

   @AvailableSince("3.1.24")
   @Override
   public float getHeightScale(Object entity) {
      return this.heightScale;
   }

   @Override
   public void renderEarly(
      Object animatable,
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
      IGeoRenderer.super.renderEarly((T)animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlayIn, red, green, blue, alpha);
   }

   public void m_7392_(Entity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      this.render(entity, this.animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }

   public void render(
      Entity entity, IAnimatable animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
   ) {
      if (!(entity instanceof LivingEntity livingEntity)) {
         throw new IllegalStateException("Replaced renderer was not an instanceof LivingEntity");
      } else {
         this.currentAnimatable = animatable;
         this.dispatchedMat = poseStack.m_85850_().m_85861_().m_27658_();
         boolean shouldSit = entity.m_20159_() && entity.m_20202_() != null && entity.m_20202_().shouldRiderSit();
         this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
         poseStack.m_85836_();
         if (entity instanceof Mob mob) {
            Entity leashHolder = mob.m_21524_();
            if (leashHolder != null) {
               this.renderLeash(mob, partialTick, poseStack, bufferSource, leashHolder);
            }
         }

         EntityModelData entityModelData = new EntityModelData();
         entityModelData.isSitting = shouldSit;
         entityModelData.isChild = livingEntity.m_6162_();
         float lerpBodyRot = Mth.m_14189_(partialTick, livingEntity.f_20884_, livingEntity.f_20883_);
         float lerpHeadRot = Mth.m_14189_(partialTick, livingEntity.f_20886_, livingEntity.f_20885_);
         float netHeadYaw = lerpHeadRot - lerpBodyRot;
         if (shouldSit && entity.m_20202_() instanceof LivingEntity vehicle) {
            lerpBodyRot = Mth.m_14189_(partialTick, vehicle.f_20884_, vehicle.f_20883_);
            netHeadYaw = lerpHeadRot - lerpBodyRot;
            float clampedHeadYaw = Mth.m_14036_(Mth.m_14177_(netHeadYaw), -85.0F, 85.0F);
            lerpBodyRot = lerpHeadRot - clampedHeadYaw;
            if (clampedHeadYaw * clampedHeadYaw > 2500.0F) {
               lerpBodyRot += clampedHeadYaw * 0.2F;
            }

            netHeadYaw = lerpHeadRot - lerpBodyRot;
         }

         if (entity.m_20089_() == Pose.SLEEPING) {
            Direction direction = livingEntity.m_21259_();
            if (direction != null) {
               float eyeOffset = entity.m_20236_(Pose.STANDING) - 0.1F;
               poseStack.m_85837_((double)((float)(-direction.m_122429_()) * eyeOffset), 0.0, (double)((float)(-direction.m_122431_()) * eyeOffset));
            }
         }

         float lerpedAge = (float)livingEntity.f_19797_ + partialTick;
         float limbSwingAmount = 0.0F;
         float limbSwing = 0.0F;
         this.applyRotations(livingEntity, poseStack, lerpedAge, lerpBodyRot, partialTick);
         this.preRenderCallback(livingEntity, poseStack, partialTick);
         if (!shouldSit && entity.m_6084_()) {
            limbSwingAmount = Math.min(1.0F, Mth.m_14179_(partialTick, livingEntity.f_20923_, livingEntity.f_20924_));
            limbSwing = livingEntity.f_20925_ - livingEntity.f_20924_ * (1.0F - partialTick);
            if (livingEntity.m_6162_()) {
               limbSwing *= 3.0F;
            }
         }

         float headPitch = Mth.m_14179_(partialTick, entity.f_19860_, entity.m_146909_());
         entityModelData.headPitch = -headPitch;
         entityModelData.netHeadYaw = -netHeadYaw;
         GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(animatable));
         AnimationEvent predicate = new AnimationEvent<>(
            animatable,
            limbSwing,
            limbSwingAmount,
            partialTick,
            limbSwingAmount <= -this.getSwingMotionAnimThreshold() || limbSwingAmount <= this.getSwingMotionAnimThreshold(),
            Collections.singletonList(entityModelData)
         );
         this.modelProvider.setLivingAnimations(animatable, this.getInstanceId((T)entity), predicate);
         poseStack.m_85837_(0.0, 0.01F, 0.0);
         RenderSystem.m_157456_(0, this.m_5478_(entity));
         Color renderColor = this.getRenderColor((T)animatable, partialTick, poseStack, bufferSource, null, packedLight);
         RenderType renderType = this.getRenderType((T)entity, partialTick, poseStack, bufferSource, null, packedLight, this.m_5478_(entity));
         if (!entity.m_20177_(Minecraft.m_91087_().f_91074_)) {
            VertexConsumer glintBuffer = bufferSource.m_6299_(RenderType.m_110499_());
            VertexConsumer translucentBuffer = bufferSource.m_6299_(RenderType.m_110470_(this.m_5478_(entity)));
            this.render(
               model,
               (T)entity,
               partialTick,
               renderType,
               poseStack,
               bufferSource,
               glintBuffer != translucentBuffer ? VertexMultiConsumer.m_86168_(glintBuffer, translucentBuffer) : null,
               packedLight,
               getPackedOverlay(livingEntity, this.getOverlayProgress(livingEntity, partialTick)),
               (float)renderColor.getRed() / 255.0F,
               (float)renderColor.getGreen() / 255.0F,
               (float)renderColor.getBlue() / 255.0F,
               (float)renderColor.getAlpha() / 255.0F
            );
         }

         if (!entity.m_5833_()) {
            for (GeoLayerRenderer layerRenderer : this.layerRenderers) {
               layerRenderer.render(poseStack, bufferSource, packedLight, (T)entity, limbSwing, limbSwingAmount, partialTick, lerpedAge, netHeadYaw, headPitch);
            }
         }

         if (ModList.get().isLoaded("patchouli")) {
            PatchouliCompat.patchouliLoaded(poseStack);
         }

         poseStack.m_85849_();
         super.m_7392_(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      }
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.isTrackingXform()) {
         Entity entity = (Entity)this.animatable;
         Matrix4f poseState = poseStack.m_85850_().m_85861_().m_27658_();
         Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);
         bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
         localMatrix.m_27648_(new Vector3f(this.m_7860_(entity, 1.0F)));
         bone.setLocalSpaceXform(localMatrix);
         Matrix4f worldState = localMatrix.m_27658_();
         worldState.m_27648_(new Vector3f(entity.m_20182_()));
         bone.setWorldSpaceXform(worldState);
      }

      IGeoRenderer.super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   protected float getOverlayProgress(LivingEntity entity, float partialTicks) {
      return 0.0F;
   }

   protected void preRenderCallback(LivingEntity entity, PoseStack poseStack, float partialTick) {
   }

   public ResourceLocation m_5478_(Entity entity) {
      return this.modelProvider.getTextureResource(this.currentAnimatable);
   }

   public AnimatedGeoModel getGeoModelProvider() {
      return this.modelProvider;
   }

   public static int getPackedOverlay(LivingEntity entity, float u) {
      return OverlayTexture.m_118093_(OverlayTexture.m_118088_(u), OverlayTexture.m_118096_(entity.f_20916_ > 0 || entity.f_20919_ > 0));
   }

   protected void applyRotations(LivingEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
      Pose pose = entity.m_20089_();
      if (pose != Pose.SLEEPING) {
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - rotationYaw));
      }

      if (entity.f_20919_ > 0) {
         float deathRotation = ((float)entity.f_20919_ + partialTick - 1.0F) / 20.0F * 1.6F;
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_(Math.min(Mth.m_14116_(deathRotation), 1.0F) * this.getDeathMaxRotation(entity)));
      } else if (entity.m_21209_()) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F - entity.m_146909_()));
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(((float)entity.f_19797_ + partialTick) * -75.0F));
      } else if (pose == Pose.SLEEPING) {
         Direction bedOrientation = entity.m_21259_();
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(bedOrientation != null ? getFacingAngle(bedOrientation) : rotationYaw));
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_(this.getDeathMaxRotation(entity)));
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
      } else if (entity.m_8077_() || entity instanceof Player) {
         String name = entity.m_7755_().getString();
         if (entity instanceof Player player) {
            if (!player.m_36170_(PlayerModelPart.CAPE)) {
               return;
            }
         } else {
            name = ChatFormatting.m_126649_(name);
         }

         if (name != null && (name.equals("Dinnerbone") || name.equalsIgnoreCase("Grumm"))) {
            poseStack.m_85837_(0.0, (double)(entity.m_20206_() + 0.1F), 0.0);
            poseStack.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         }
      }
   }

   @Deprecated(
      forRemoval = true
   )
   public Integer getUniqueID(T animatable) {
      return this.getInstanceId(animatable);
   }

   protected boolean isVisible(LivingEntity entity) {
      return !entity.m_20145_();
   }

   private static float getFacingAngle(Direction facingIn) {
      return switch (facingIn) {
         case SOUTH -> 90.0F;
         case NORTH -> 270.0F;
         case EAST -> 180.0F;
         default -> 0.0F;
      };
   }

   protected float getDeathMaxRotation(LivingEntity entity) {
      return 90.0F;
   }

   public boolean m_6512_(Entity entity) {
      double nameRenderDistance = entity.m_20163_() ? 32.0 : 64.0;
      return this.f_114476_.m_114471_(entity) >= nameRenderDistance * nameRenderDistance
         ? false
         : entity == this.f_114476_.f_114359_ && entity.m_8077_() && Minecraft.m_91404_();
   }

   protected float getSwingProgress(LivingEntity entity, float partialTick) {
      return entity.m_21324_(partialTick);
   }

   protected float getSwingMotionAnimThreshold() {
      return 0.15F;
   }

   @Override
   public ResourceLocation getTextureLocation(Object animatable) {
      return this.modelProvider.getTextureResource((IAnimatable)animatable);
   }

   public final boolean addLayer(GeoLayerRenderer<? extends LivingEntity> layer) {
      return this.layerRenderers.add(layer);
   }

   public <E extends Entity> void renderLeash(Mob entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, E leashHolder) {
      double lerpBodyAngle = (double)(Mth.m_14179_(partialTick, entity.f_20883_, entity.f_20884_) * (float) (Math.PI / 180.0) + (float) (Math.PI / 2));
      Vec3 leashOffset = entity.m_7939_();
      double xAngleOffset = Math.cos(lerpBodyAngle) * leashOffset.f_82481_ + Math.sin(lerpBodyAngle) * leashOffset.f_82479_;
      double zAngleOffset = Math.sin(lerpBodyAngle) * leashOffset.f_82481_ - Math.cos(lerpBodyAngle) * leashOffset.f_82479_;
      double lerpOriginX = Mth.m_14139_((double)partialTick, entity.f_19854_, entity.m_20185_()) + xAngleOffset;
      double lerpOriginY = Mth.m_14139_((double)partialTick, entity.f_19855_, entity.m_20186_()) + leashOffset.f_82480_;
      double lerpOriginZ = Mth.m_14139_((double)partialTick, entity.f_19856_, entity.m_20189_()) + zAngleOffset;
      Vec3 ropeGripPosition = leashHolder.m_7398_(partialTick);
      float xDif = (float)(ropeGripPosition.f_82479_ - lerpOriginX);
      float yDif = (float)(ropeGripPosition.f_82480_ - lerpOriginY);
      float zDif = (float)(ropeGripPosition.f_82481_ - lerpOriginZ);
      float offsetMod = Mth.m_14195_(xDif * xDif + zDif * zDif) * 0.025F / 2.0F;
      float xOffset = zDif * offsetMod;
      float zOffset = xDif * offsetMod;
      VertexConsumer vertexConsumer = bufferSource.m_6299_(RenderType.m_110475_());
      BlockPos entityEyePos = new BlockPos(entity.m_20299_(partialTick));
      BlockPos holderEyePos = new BlockPos(leashHolder.m_20299_(partialTick));
      int entityBlockLight = this.m_6086_(entity, entityEyePos);
      int holderBlockLight = leashHolder.m_6060_() ? 15 : leashHolder.f_19853_.m_45517_(LightLayer.BLOCK, holderEyePos);
      int entitySkyLight = entity.f_19853_.m_45517_(LightLayer.SKY, entityEyePos);
      int holderSkyLight = entity.f_19853_.m_45517_(LightLayer.SKY, holderEyePos);
      poseStack.m_85836_();
      poseStack.m_85837_(xAngleOffset, leashOffset.f_82480_, zAngleOffset);
      Matrix4f posMatrix = poseStack.m_85850_().m_85861_();

      for (int segment = 0; segment <= 24; segment++) {
         renderLeashPiece(
            vertexConsumer,
            posMatrix,
            xDif,
            yDif,
            zDif,
            entityBlockLight,
            holderBlockLight,
            entitySkyLight,
            holderSkyLight,
            0.025F,
            0.025F,
            xOffset,
            zOffset,
            segment,
            false
         );
      }

      for (int segment = 24; segment >= 0; segment--) {
         renderLeashPiece(
            vertexConsumer,
            posMatrix,
            xDif,
            yDif,
            zDif,
            entityBlockLight,
            holderBlockLight,
            entitySkyLight,
            holderSkyLight,
            0.025F,
            0.0F,
            xOffset,
            zOffset,
            segment,
            true
         );
      }

      poseStack.m_85849_();
   }

   private static void renderLeashPiece(
      VertexConsumer buffer,
      Matrix4f positionMatrix,
      float xDif,
      float yDif,
      float zDif,
      int entityBlockLight,
      int holderBlockLight,
      int entitySkyLight,
      int holderSkyLight,
      float width,
      float yOffset,
      float xOffset,
      float zOffset,
      int segment,
      boolean isLeashKnot
   ) {
      float piecePosPercent = (float)segment / 24.0F;
      int lerpBlockLight = (int)Mth.m_14179_(piecePosPercent, (float)entityBlockLight, (float)holderBlockLight);
      int lerpSkyLight = (int)Mth.m_14179_(piecePosPercent, (float)entitySkyLight, (float)holderSkyLight);
      int packedLight = LightTexture.m_109885_(lerpBlockLight, lerpSkyLight);
      float knotColourMod = segment % 2 == (isLeashKnot ? 1 : 0) ? 0.7F : 1.0F;
      float red = 0.5F * knotColourMod;
      float green = 0.4F * knotColourMod;
      float blue = 0.3F * knotColourMod;
      float x = xDif * piecePosPercent;
      float y = yDif > 0.0F ? yDif * piecePosPercent * piecePosPercent : yDif - yDif * (1.0F - piecePosPercent) * (1.0F - piecePosPercent);
      float z = zDif * piecePosPercent;
      buffer.m_85982_(positionMatrix, x - xOffset, y + yOffset, z + zOffset).m_85950_(red, green, blue, 1.0F).m_85969_(packedLight).m_5752_();
      buffer.m_85982_(positionMatrix, x + xOffset, y + width - yOffset, z - zOffset).m_85950_(red, green, blue, 1.0F).m_85969_(packedLight).m_5752_();
   }

   @Override
   public void setCurrentRTB(MultiBufferSource bufferSource) {
      this.rtb = bufferSource;
   }

   @Override
   public MultiBufferSource getCurrentRTB() {
      return this.rtb;
   }

   @Deprecated(
      forRemoval = true
   )
   protected float handleRotationFloat(LivingEntity livingBase, float partialTicks) {
      return (float)livingBase.f_19797_ + partialTicks;
   }

   static {
      AnimationController.addModelFetcher(object -> {
         GeoReplacedEntityRenderer renderer = renderers.get(object.getClass());
         return renderer == null ? null : renderer.getGeoModelProvider();
      });
   }
}
