package com.bobmowzie.mowziesmobs.client.render.entity.player;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerFirstPerson;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

@OnlyIn(Dist.CLIENT)
public class GeckoFirstPersonRenderer extends ItemInHandRenderer implements IGeoRenderer<GeckoPlayer> {
   public MultiBufferSource rtb;
   public static GeckoPlayer.GeckoPlayerFirstPerson GECKO_PLAYER_FIRST_PERSON;
   private static HashMap<Class<? extends GeckoPlayer>, GeckoFirstPersonRenderer> modelsToLoad = new HashMap<>();
   private ModelGeckoPlayerFirstPerson modelProvider;
   boolean mirror;
   public Vec3 particleEmitterRoot;

   public GeckoFirstPersonRenderer(Minecraft mcIn, ModelGeckoPlayerFirstPerson modelProvider) {
      super(mcIn, mcIn.m_91290_(), mcIn.m_91291_());
      this.modelProvider = modelProvider;
   }

   public GeckoFirstPersonRenderer getModelProvider(Class<? extends GeckoPlayer> animatable) {
      return modelsToLoad.get(animatable);
   }

   public HashMap<Class<? extends GeckoPlayer>, GeckoFirstPersonRenderer> getModelsToLoad() {
      return modelsToLoad;
   }

   public void renderItemInFirstPerson(
      AbstractClientPlayer player,
      float pitch,
      float partialTicks,
      InteractionHand handIn,
      float swingProgress,
      ItemStack stack,
      float equippedProgress,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int combinedLightIn,
      GeckoPlayer geckoPlayer
   ) {
      this.rtb = bufferIn;
      boolean flag = handIn == InteractionHand.MAIN_HAND;
      HumanoidArm handside = flag ? player.m_5737_() : player.m_5737_().m_20828_();
      this.mirror = player.m_5737_() == HumanoidArm.LEFT;
      if (flag) {
         this.modelProvider.setTextureFromPlayer(player);
         this.modelProvider.setLivingAnimations(geckoPlayer, player.m_20148_().hashCode());
         RenderType rendertype = RenderType.m_110467_(this.getTextureLocation(geckoPlayer));
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(rendertype);
         matrixStackIn.m_85837_(0.0, -2.0, -1.0);
         this.render(
            this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource(geckoPlayer)),
            geckoPlayer,
            partialTicks,
            rendertype,
            matrixStackIn,
            bufferIn,
            ivertexbuilder,
            combinedLightIn,
            OverlayTexture.f_118083_,
            1.0F,
            1.0F,
            1.0F,
            1.0F
         );
      }

      PlayerAbility.HandDisplay handDisplay = PlayerAbility.HandDisplay.DEFAULT;
      float offHandEquipProgress = 0.0F;
      AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
      if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
         Ability ability = abilityCapability.getActiveAbility();
         if (ability instanceof PlayerAbility playerAbility) {
            ItemStack stackOverride = flag ? playerAbility.heldItemMainHandOverride() : playerAbility.heldItemOffHandOverride();
            if (stackOverride != null) {
               stack = stackOverride;
            }

            handDisplay = flag ? playerAbility.getFirstPersonMainHandDisplay() : playerAbility.getFirstPersonOffHandDisplay();
         }

         if (ability.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            offHandEquipProgress = Mth.m_14036_(1.0F - ((float)ability.getTicksInSection() + partialTicks) / 5.0F, 0.0F, 1.0F);
         } else if (ability.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY
            && ability.getCurrentSection() instanceof AbilitySection.AbilitySectionDuration) {
            offHandEquipProgress = Mth.m_14036_(
               ((float)ability.getTicksInSection() + partialTicks - (float)((AbilitySection.AbilitySectionDuration)ability.getCurrentSection()).duration + 5.0F)
                  / 5.0F,
               0.0F,
               1.0F
            );
         }
      }

      if (this.modelProvider.isInitialized()) {
         if (handDisplay != PlayerAbility.HandDisplay.DONT_RENDER) {
            int sideMult = handside == HumanoidArm.RIGHT ? -1 : 1;
            if (this.mirror) {
               handside = handside.m_20828_();
            }

            String sideName = handside == HumanoidArm.RIGHT ? "Right" : "Left";
            String boneName = sideName + "Arm";
            MowzieGeoBone bone = this.modelProvider.getMowzieBone(boneName);
            PoseStack newMatrixStack = new PoseStack();
            float fixedPitchController = 1.0F - this.modelProvider.getControllerValueInverted("FixedPitchController" + sideName);
            newMatrixStack.m_85845_(new Quaternion(Vector3f.f_122223_, pitch * fixedPitchController, true));
            newMatrixStack.m_85850_().m_85864_().m_8178_(bone.getWorldSpaceNormal());
            newMatrixStack.m_85850_().m_85861_().m_27644_(bone.getWorldSpaceXform());
            newMatrixStack.m_85837_((double)sideMult * 0.547, 0.7655, 0.625);
            if (this.mirror) {
               handside = handside.m_20828_();
            }

            if (stack.m_41619_() && !flag && handDisplay == PlayerAbility.HandDisplay.FORCE_RENDER && !player.m_20145_()) {
               newMatrixStack.m_85837_(0.0, (double)(-1.0F * offHandEquipProgress), 0.0);
               super.m_109346_(newMatrixStack, bufferIn, combinedLightIn, 0.0F, 0.0F, handside);
            } else {
               super.m_109371_(player, partialTicks, pitch, handIn, 0.0F, stack, 0.0F, newMatrixStack, bufferIn, combinedLightIn);
            }
         }

         PoseStack toWorldSpace = new PoseStack();
         toWorldSpace.m_85837_(player.m_20185_(), player.m_20186_() + (double)player.m_20192_(), player.m_20189_());
         toWorldSpace.m_85845_(new Quaternion(0.0F, -player.m_146908_() + 180.0F, 0.0F, true));
         toWorldSpace.m_85845_(new Quaternion(-player.m_146909_(), 0.0F, 0.0F, true));
         MowzieGeoBone particleEmitterRootBone = this.modelProvider.getMowzieBone("ParticleEmitterRoot");
         Vector4f emitterRootPos = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
         emitterRootPos.m_123607_(particleEmitterRootBone.getWorldSpaceXform());
         emitterRootPos.m_123607_(toWorldSpace.m_85850_().m_85861_());
         this.particleEmitterRoot = new Vec3((double)emitterRootPos.m_123601_(), (double)emitterRootPos.m_123615_(), (double)emitterRootPos.m_123616_());
      }
   }

   public void setSmallArms() {
      this.modelProvider.setUseSmallArms(true);
   }

   public GeoModelProvider<GeckoPlayer> getGeoModelProvider() {
      return this.modelProvider;
   }

   public ModelGeckoPlayerFirstPerson getAnimatedPlayerModel() {
      return this.modelProvider;
   }

   public ResourceLocation getTextureLocation(GeckoPlayer geckoPlayer) {
      return ((AbstractClientPlayer)geckoPlayer.getPlayer()).m_108560_();
   }

   public void renderRecursively(
      GeoBone bone, PoseStack matrixStack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      matrixStack.m_85836_();
      if (this.mirror) {
         MowzieRenderUtils.translateMirror(bone, matrixStack);
         MowzieRenderUtils.moveToPivotMirror(bone, matrixStack);
         MowzieRenderUtils.rotateMirror(bone, matrixStack);
         RenderUtils.scale(bone, matrixStack);
      } else {
         RenderUtils.translate(bone, matrixStack);
         RenderUtils.moveToPivot(bone, matrixStack);
         RenderUtils.rotate(bone, matrixStack);
         RenderUtils.scale(bone, matrixStack);
      }

      if (bone instanceof MowzieGeoBone mowzieBone
         && (mowzieBone.name.equals("LeftArm") || mowzieBone.name.equals("RightArm") || mowzieBone.name.equals("ParticleEmitterRoot"))) {
         matrixStack.m_85836_();
         Pose entry = matrixStack.m_85850_();
         mowzieBone.setWorldSpaceNormal(entry.m_85864_().m_8183_());
         mowzieBone.setWorldSpaceXform(entry.m_85861_().m_27658_());
         matrixStack.m_85849_();
      }

      if (this.mirror) {
         MowzieRenderUtils.moveBackFromPivotMirror(bone, matrixStack);
      } else {
         RenderUtils.moveBackFromPivot(bone, matrixStack);
      }

      if (!bone.isHidden) {
         for (GeoCube cube : bone.childCubes) {
            matrixStack.m_85836_();
            this.renderCube(cube, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStack.m_85849_();
         }

         for (GeoBone childBone : bone.childBones) {
            this.renderRecursively(childBone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
         }
      }

      matrixStack.m_85849_();
   }

   public void setCurrentRTB(MultiBufferSource rtb) {
      this.rtb = rtb;
   }

   public MultiBufferSource getCurrentRTB() {
      return this.rtb;
   }

   static {
      AnimationController.addModelFetcher(object -> {
         if (object instanceof GeckoPlayer.GeckoPlayerFirstPerson) {
            GeckoFirstPersonRenderer render = modelsToLoad.get(object.getClass());
            return (IAnimatableModel)render.getGeoModelProvider();
         } else {
            return null;
         }
      });
   }
}
