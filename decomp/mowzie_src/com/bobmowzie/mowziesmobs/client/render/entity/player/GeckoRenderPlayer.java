package com.bobmowzie.mowziesmobs.client.render.entity.player;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelBipedAnimated;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerThirdPerson;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelPlayerAnimated;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.FrozenRenderHandler;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoArmorLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoCapeLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoElytraLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoParrotOnShoulderLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoPlayerItemInHandLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.IGeckoRenderLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.SolarFlareLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.util.HashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.Deadmau5EarsLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

@OnlyIn(Dist.CLIENT)
public class GeckoRenderPlayer extends PlayerRenderer implements IGeoRenderer<GeckoPlayer> {
   public MultiBufferSource rtb;
   private static HashMap<Class<? extends GeckoPlayer>, GeckoRenderPlayer> modelsToLoad = new HashMap<>();
   private ModelGeckoPlayerThirdPerson modelProvider;
   private Matrix4f worldRenderMat;
   public Vec3 betweenHandsPos;
   public Vec3 particleEmitterRoot;
   private boolean isInvisible = false;

   public GeckoRenderPlayer(Context context, boolean slim, ModelGeckoPlayerThirdPerson modelProvider) {
      super(context, slim);
      ModelPlayerAnimated<AbstractClientPlayer> modelPlayerAnimated = new ModelPlayerAnimated(
         context.m_174023_(slim ? ModelLayers.f_171166_ : ModelLayers.f_171162_), slim
      );
      ModelPlayerAnimated.setUseMatrixMode(modelPlayerAnimated, true);
      this.f_115290_ = modelPlayerAnimated;
      this.f_115291_.clear();
      this.m_115326_(
         new GeckoArmorLayer(
            this,
            new ModelBipedAnimated(context.m_174023_(slim ? ModelLayers.f_171167_ : ModelLayers.f_171164_)),
            new ModelBipedAnimated(context.m_174023_(slim ? ModelLayers.f_171168_ : ModelLayers.f_171165_))
         )
      );
      this.m_115326_(new GeckoPlayerItemInHandLayer(this));
      this.m_115326_(new ArrowLayer(context, this));
      this.m_115326_(new Deadmau5EarsLayer(this));
      this.m_115326_(new GeckoCapeLayer(this));
      this.m_115326_(new CustomHeadLayer(this, context.m_174027_(), context.m_234598_()));
      this.m_115326_(new GeckoElytraLayer(this, context.m_174027_()));
      this.m_115326_(new GeckoParrotOnShoulderLayer(this, context.m_174027_()));
      this.m_115326_(new SpinAttackEffectLayer(this, context.m_174027_()));
      this.m_115326_(new BeeStingerLayer(this));
      this.m_115326_(new FrozenRenderHandler.LayerFrozen(this));
      this.m_115326_(new SolarFlareLayer(this));
      this.modelProvider = modelProvider;
      this.modelProvider.setUseSmallArms(slim);
      this.worldRenderMat = new Matrix4f();
      this.worldRenderMat.m_27624_();
   }

   public GeckoRenderPlayer getModelProvider(Class<? extends GeckoPlayer> animatable) {
      return modelsToLoad.get(animatable);
   }

   public ModelGeckoPlayerThirdPerson getGeckoModel() {
      return this.modelProvider;
   }

   public HashMap<Class<? extends GeckoPlayer>, GeckoRenderPlayer> getModelsToLoad() {
      return modelsToLoad;
   }

   public void render(
      AbstractClientPlayer entityIn,
      float entityYaw,
      float partialTicks,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      GeckoPlayer geckoPlayer
   ) {
      this.rtb = bufferIn;
      this.setModelVisibilities(entityIn);
      this.renderLiving(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, geckoPlayer);
   }

   private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
      ModelGeckoPlayerThirdPerson playermodel = (ModelGeckoPlayerThirdPerson)this.getGeoModelProvider();
      if (playermodel.isInitialized()) {
         if (clientPlayer.m_5833_()) {
            playermodel.setVisible(false);
            playermodel.bipedHead().setHidden(false);
            playermodel.bipedHeadwear().setHidden(false);
         } else {
            playermodel.setVisible(true);
            playermodel.bipedHeadwear().setHidden(!clientPlayer.m_36170_(PlayerModelPart.HAT));
            playermodel.bipedBodywear().setHidden(!clientPlayer.m_36170_(PlayerModelPart.JACKET));
            playermodel.bipedLeftLegwear().setHidden(!clientPlayer.m_36170_(PlayerModelPart.LEFT_PANTS_LEG));
            playermodel.bipedRightLegwear().setHidden(!clientPlayer.m_36170_(PlayerModelPart.RIGHT_PANTS_LEG));
            playermodel.bipedLeftArmwear().setHidden(!clientPlayer.m_36170_(PlayerModelPart.LEFT_SLEEVE));
            playermodel.bipedRightArmwear().setHidden(!clientPlayer.m_36170_(PlayerModelPart.RIGHT_SLEEVE));
            playermodel.isSneak = clientPlayer.m_6047_();
            ArmPose bipedmodel$armpose = m_117794_(clientPlayer, InteractionHand.MAIN_HAND);
            ArmPose bipedmodel$armpose1 = m_117794_(clientPlayer, InteractionHand.OFF_HAND);
            if (bipedmodel$armpose.m_102897_()) {
               bipedmodel$armpose1 = clientPlayer.m_21206_().m_41619_() ? ArmPose.EMPTY : ArmPose.ITEM;
            }

            if (clientPlayer.m_5737_() == HumanoidArm.RIGHT) {
               this.modelProvider.rightArmPose = bipedmodel$armpose;
               this.modelProvider.leftArmPose = bipedmodel$armpose1;
            } else {
               this.modelProvider.rightArmPose = bipedmodel$armpose1;
               this.modelProvider.leftArmPose = bipedmodel$armpose;
            }
         }
      }
   }

   public void renderLiving(
      AbstractClientPlayer entityIn,
      float entityYaw,
      float partialTicks,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      GeckoPlayer geckoPlayer
   ) {
      matrixStackIn.m_85836_();
      ((PlayerModel)this.f_115290_).f_102608_ = this.m_115342_(entityIn, partialTicks);
      boolean shouldSit = entityIn.m_20159_() && entityIn.m_20202_() != null && entityIn.m_20202_().shouldRiderSit();
      ((PlayerModel)this.f_115290_).f_102609_ = shouldSit;
      ((PlayerModel)this.f_115290_).f_102610_ = entityIn.m_6162_();
      float f = Mth.m_14189_(partialTicks, entityIn.f_20884_, entityIn.f_20883_);
      float f1 = Mth.m_14189_(partialTicks, entityIn.f_20886_, entityIn.f_20885_);
      float f2 = f1 - f;
      if (shouldSit && entityIn.m_20202_() instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)entityIn.m_20202_();
         f = Mth.m_14189_(partialTicks, livingentity.f_20884_, livingentity.f_20883_);
         f2 = f1 - f;
         float f3 = Mth.m_14177_(f2);
         if (f3 < -85.0F) {
            f3 = -85.0F;
         }

         if (f3 >= 85.0F) {
            f3 = 85.0F;
         }

         f = f1 - f3;
         if (f3 * f3 > 2500.0F) {
            f += f3 * 0.2F;
         }

         f2 = f1 - f;
      }

      float f6 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      if (entityIn.m_20089_() == Pose.SLEEPING) {
         Direction direction = entityIn.m_21259_();
         if (direction != null) {
            float f4 = entityIn.m_20236_(Pose.STANDING) - 0.1F;
            matrixStackIn.m_85837_((double)((float)(-direction.m_122429_()) * f4), 0.0, (double)((float)(-direction.m_122431_()) * f4));
         }
      }

      float f7 = this.m_6930_(entityIn, partialTicks);
      this.m_7546_(entityIn, matrixStackIn, partialTicks);
      float f8 = 0.0F;
      float f5 = 0.0F;
      if (!shouldSit && entityIn.m_6084_()) {
         f8 = Mth.m_14179_(partialTicks, entityIn.f_20923_, entityIn.f_20924_);
         f5 = entityIn.f_20925_ - entityIn.f_20924_ * (1.0F - partialTicks);
         if (entityIn.m_6162_()) {
            f5 *= 3.0F;
         }

         if (f8 > 1.0F) {
            f8 = 1.0F;
         }
      }

      this.modelProvider.setLivingAnimations(geckoPlayer, entityIn.m_20148_().hashCode());
      if (this.modelProvider.isInitialized()) {
         this.applyRotationsPlayerRenderer(entityIn, matrixStackIn, f7, f, partialTicks, f1);
         float bodyRotateAmount = this.modelProvider.getControllerValueInverted("BodyRotateController");
         this.modelProvider.setRotationAngles(entityIn, f5, f8, f7, Mth.m_14189_(bodyRotateAmount, 0.0F, f2), f6, partialTicks);
         MowzieGeoBone leftHeldItem = this.modelProvider.getMowzieBone("LeftHeldItem");
         MowzieGeoBone rightHeldItem = this.modelProvider.getMowzieBone("RightHeldItem");
         Matrix4f worldMatInverted = matrixStackIn.m_85850_().m_85861_().m_27658_();
         worldMatInverted.m_27657_();
         Matrix3f worldNormInverted = matrixStackIn.m_85850_().m_85864_().m_8183_();
         worldNormInverted.m_8187_();
         PoseStack toWorldSpace = new PoseStack();
         toWorldSpace.m_85845_(new Quaternion(0.0F, -entityYaw + 180.0F, 0.0F, true));
         toWorldSpace.m_85837_(0.0, -1.5, 0.0);
         toWorldSpace.m_85850_().m_85864_().m_8178_(worldNormInverted);
         toWorldSpace.m_85850_().m_85861_().m_27644_(worldMatInverted);
         Vector4f leftHeldItemPos = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
         leftHeldItemPos.m_123607_(leftHeldItem.getWorldSpaceXform());
         leftHeldItemPos.m_123607_(toWorldSpace.m_85850_().m_85861_());
         Vec3 leftHeldItemPos3 = new Vec3((double)leftHeldItemPos.m_123601_(), (double)leftHeldItemPos.m_123615_(), (double)leftHeldItemPos.m_123616_());
         Vector4f rightHeldItemPos = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
         rightHeldItemPos.m_123607_(rightHeldItem.getWorldSpaceXform());
         rightHeldItemPos.m_123607_(toWorldSpace.m_85850_().m_85861_());
         Vec3 rightHeldItemPos3 = new Vec3((double)rightHeldItemPos.m_123601_(), (double)rightHeldItemPos.m_123615_(), (double)rightHeldItemPos.m_123616_());
         this.betweenHandsPos = rightHeldItemPos3.m_82549_(leftHeldItemPos3.m_82546_(rightHeldItemPos3).m_82490_(0.5));
         MowzieGeoBone particleEmitterRootBone = this.modelProvider.getMowzieBone("ParticleEmitterRoot");
         Vector4f emitterRootPos = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
         emitterRootPos.m_123607_(particleEmitterRootBone.getWorldSpaceXform());
         emitterRootPos.m_123607_(toWorldSpace.m_85850_().m_85861_());
         this.particleEmitterRoot = new Vec3((double)emitterRootPos.m_123601_(), (double)emitterRootPos.m_123615_(), (double)emitterRootPos.m_123616_());
      }

      Minecraft minecraft = Minecraft.m_91087_();
      boolean flag = this.m_5933_(entityIn);
      boolean flag1 = !flag && !entityIn.m_20177_(minecraft.f_91074_);
      boolean flag2 = minecraft.m_91314_(entityIn);
      this.isInvisible = !flag && !flag1 && !flag2;
      RenderType rendertype = this.m_7225_(entityIn, flag, flag1, flag2);
      if (this.isInvisible) {
         rendertype = ((PlayerModel)this.f_115290_).m_103119_(this.getTextureLocation(geckoPlayer));
      }

      if (rendertype != null) {
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(rendertype);
         int i = m_115338_(entityIn, this.m_6931_(entityIn, partialTicks));
         matrixStackIn.m_85836_();
         this.worldRenderMat.m_162210_(matrixStackIn.m_85850_().m_85861_());
         this.modelProvider.setTextureFromPlayer(entityIn);
         this.render(
            this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource(geckoPlayer)),
            geckoPlayer,
            partialTicks,
            rendertype,
            matrixStackIn,
            bufferIn,
            ivertexbuilder,
            packedLightIn,
            i,
            1.0F,
            1.0F,
            1.0F,
            flag1 ? 0.15F : 1.0F
         );
         matrixStackIn.m_85849_();
         ModelBipedAnimated.copyFromGeckoModel((HumanoidModel<?>)this.f_115290_, this.modelProvider);
         ((PlayerModel)this.f_115290_).m_6973_(entityIn, f5, f8, f7, f2, f6);
      }

      if (!entityIn.m_5833_()) {
         for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layerrenderer : this.f_115291_) {
            layerrenderer.m_6494_(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
         }
      }

      matrixStackIn.m_85849_();
      this.renderEntity(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public void renderEntity(
      AbstractClientPlayer entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      RenderNameTagEvent renderNameplateEvent = new RenderNameTagEvent(entityIn, entityIn.m_5446_(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
      MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
      if (renderNameplateEvent.getResult() != Result.DENY && (renderNameplateEvent.getResult() == Result.ALLOW || this.m_6512_(entityIn))) {
         this.m_7649_(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
      }
   }

   protected void applyRotationsPlayerRenderer(
      AbstractClientPlayer entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float headYaw
   ) {
      float f = entityLiving.m_20998_(partialTicks);
      if (entityLiving.m_21255_()) {
         this.applyRotationsLivingRenderer(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks, headYaw);
         float f1 = (float)entityLiving.m_21256_() + partialTicks;
         float f2 = Mth.m_14036_(f1 * f1 / 100.0F, 0.0F, 1.0F);
         if (!entityLiving.m_21209_()) {
            matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(f2 * (-90.0F - entityLiving.m_146909_())));
         }

         Vec3 vector3d = entityLiving.m_20252_(partialTicks);
         Vec3 vector3d1 = entityLiving.m_20184_();
         double d0 = vector3d1.m_165925_();
         double d1 = vector3d.m_165925_();
         if (d0 > 0.0 && d1 > 0.0) {
            double d2 = (vector3d1.f_82479_ * vector3d.f_82479_ + vector3d1.f_82481_ * vector3d.f_82481_) / Math.sqrt(d0 * d1);
            double d3 = vector3d1.f_82479_ * vector3d.f_82481_ - vector3d1.f_82481_ * vector3d.f_82479_;
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122270_((float)(Math.signum(d3) * Math.acos(d2))));
         }
      } else if (f > 0.0F) {
         float swimController = this.modelProvider.getControllerValueInverted("SwimController");
         this.applyRotationsLivingRenderer(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks, headYaw);
         float f3 = entityLiving.m_20069_() ? -90.0F - entityLiving.m_146909_() : -90.0F;
         float f4 = Mth.m_14179_(f, 0.0F, f3) * swimController;
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(f4));
         if (entityLiving.m_6067_()) {
            matrixStackIn.m_85837_(0.0, -1.0, 0.3F);
         }
      } else {
         this.applyRotationsLivingRenderer(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks, headYaw);
      }
   }

   protected void applyRotationsLivingRenderer(
      AbstractClientPlayer entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float headYaw
   ) {
      if (this.m_5936_(entityLiving)) {
         rotationYaw += (float)(Math.cos((double)entityLiving.f_19797_ * 3.25) * Math.PI * 0.4F);
      }

      Pose pose = entityLiving.m_20089_();
      if (pose != Pose.SLEEPING) {
         float bodyRotateAmount = this.modelProvider.getControllerValueInverted("BodyRotateController");
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - Mth.m_14189_(bodyRotateAmount, headYaw, rotationYaw)));
      }

      if (entityLiving.f_20919_ > 0) {
         float f = ((float)entityLiving.f_20919_ + partialTicks - 1.0F) / 20.0F * 1.6F;
         f = Mth.m_14116_(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(f * this.m_6441_(entityLiving)));
      } else if (entityLiving.m_21209_()) {
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F - entityLiving.m_146909_()));
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(((float)entityLiving.f_19797_ + partialTicks) * -75.0F));
      } else if (pose == Pose.SLEEPING) {
         Direction direction = entityLiving.m_21259_();
         float f1 = direction != null ? getFacingAngle(direction) : rotationYaw;
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f1));
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(this.m_6441_(entityLiving)));
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
      } else if (entityLiving.m_8077_() || entityLiving instanceof Player) {
         String s = ChatFormatting.m_126649_(entityLiving.m_7755_().getString());
         if (("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof Player) || entityLiving.m_36170_(PlayerModelPart.CAPE))) {
            matrixStackIn.m_85837_(0.0, (double)(entityLiving.m_20206_() + 0.1F), 0.0);
            matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         }
      }
   }

   private static float getFacingAngle(Direction facingIn) {
      switch (facingIn) {
         case SOUTH:
            return 90.0F;
         case WEST:
            return 0.0F;
         case NORTH:
            return 270.0F;
         case EAST:
            return 180.0F;
         default:
            return 0.0F;
      }
   }

   public GeoModelProvider<GeckoPlayer> getGeoModelProvider() {
      return this.modelProvider;
   }

   public ModelGeckoPlayerThirdPerson getAnimatedPlayerModel() {
      return this.modelProvider;
   }

   public ResourceLocation getTextureLocation(GeckoPlayer geckoPlayer) {
      return this.m_5478_((AbstractClientPlayer)geckoPlayer.getPlayer());
   }

   public void renderRecursively(
      GeoBone bone, PoseStack matrixStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      matrixStack.m_85836_();
      RenderUtils.translate(bone, matrixStack);
      RenderUtils.moveToPivot(bone, matrixStack);
      RenderUtils.rotate(bone, matrixStack);
      RenderUtils.scale(bone, matrixStack);
      if (bone instanceof MowzieGeoBone mowzieBone
         && (
            mowzieBone.name.equals("LeftHeldItem")
               || mowzieBone.name.equals("RightHeldItem")
               || mowzieBone.name.equals("Head")
               || mowzieBone.name.equals("Body")
               || mowzieBone.name.equals("LeftArm")
               || mowzieBone.name.equals("RightArm")
               || mowzieBone.name.equals("RightLeg")
               || mowzieBone.name.equals("LeftLeg")
               || mowzieBone.name.equals("ParticleEmitterRoot")
         )) {
         matrixStack.m_85836_();
         if (!mowzieBone.name.equals("LeftHeldItem") && !mowzieBone.name.equals("RightHeldItem")) {
            matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         }

         if (mowzieBone.name.equals("Body")) {
            matrixStack.m_85837_(0.0, -0.75, 0.0);
         }

         if (mowzieBone.name.equals("LeftArm")) {
            matrixStack.m_85837_(-0.075, 0.0, 0.0);
         }

         if (mowzieBone.name.equals("RightArm")) {
            matrixStack.m_85837_(0.075, 0.0, 0.0);
         }

         com.mojang.blaze3d.vertex.PoseStack.Pose entry = matrixStack.m_85850_();
         mowzieBone.setWorldSpaceNormal(entry.m_85864_().m_8183_());
         mowzieBone.setWorldSpaceXform(entry.m_85861_().m_27658_());
         matrixStack.m_85849_();
      }

      RenderUtils.moveBackFromPivot(bone, matrixStack);
      if (!bone.isHidden) {
         for (GeoCube cube : bone.childCubes) {
            matrixStack.m_85836_();
            if (!this.isInvisible) {
               this.renderCube(cube, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            matrixStack.m_85849_();
         }

         for (GeoBone childBone : bone.childBones) {
            this.renderRecursively(childBone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
         }
      }

      matrixStack.m_85849_();

      for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layerrenderer : this.f_115291_) {
         if (layerrenderer instanceof IGeckoRenderLayer) {
            ((IGeckoRenderLayer)layerrenderer).renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
         }
      }
   }

   public void setCurrentRTB(MultiBufferSource rtb) {
      this.rtb = rtb;
   }

   public MultiBufferSource getCurrentRTB() {
      return this.rtb;
   }

   static {
      AnimationController.addModelFetcher(object -> {
         if (object instanceof GeckoPlayer.GeckoPlayerThirdPerson) {
            GeckoRenderPlayer render = modelsToLoad.get(object.getClass());
            return (IAnimatableModel)render.getGeoModelProvider();
         } else {
            return null;
         }
      });
   }
}
