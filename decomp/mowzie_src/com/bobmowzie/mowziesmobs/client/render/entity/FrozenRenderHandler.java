package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public enum FrozenRenderHandler {
   INSTANCE;

   private static final ResourceLocation FROZEN_TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/frozen.png");

   @SubscribeEvent
   public void onRenderHand(RenderHandEvent event) {
      event.getPoseStack().m_85836_();
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(player, CapabilityHandler.FROZEN_CAPABILITY);
         if (frozenCapability != null && frozenCapability.getFrozen()) {
            boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
            if (isMainHand && !player.m_20145_() && event.getItemStack().m_41619_()) {
               HumanoidArm enumhandside = isMainHand ? player.m_5737_() : player.m_5737_().m_20828_();
               this.renderArmFirstPersonFrozen(
                  event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getEquipProgress(), event.getSwingProgress(), enumhandside
               );
               event.setCanceled(true);
            }
         }
      }

      event.getPoseStack().m_85849_();
   }

   private void renderArmFirstPersonFrozen(
      PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, HumanoidArm side
   ) {
      Minecraft mc = Minecraft.m_91087_();
      EntityRenderDispatcher renderManager = mc.m_91290_();
      Minecraft.m_91087_().m_91097_().m_174784_(FROZEN_TEXTURE);
      boolean flag = side != HumanoidArm.LEFT;
      float f = flag ? 1.0F : -1.0F;
      float f1 = Mth.m_14116_(swingProgress);
      float f2 = -0.3F * Mth.m_14031_(f1 * (float) Math.PI);
      float f3 = 0.4F * Mth.m_14031_(f1 * (float) (Math.PI * 2));
      float f4 = -0.4F * Mth.m_14031_(swingProgress * (float) Math.PI);
      matrixStackIn.m_85837_((double)(f * (f2 + 0.64000005F)), (double)(f3 + -0.6F + equippedProgress * -0.6F), (double)(f4 + -0.71999997F));
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f * 45.0F));
      float f5 = Mth.m_14031_(swingProgress * swingProgress * (float) Math.PI);
      float f6 = Mth.m_14031_(f1 * (float) Math.PI);
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f * f6 * 70.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(f * f5 * -20.0F));
      AbstractClientPlayer abstractclientplayerentity = mc.f_91074_;
      mc.m_91097_().m_174784_(abstractclientplayerentity.m_108560_());
      matrixStackIn.m_85837_((double)(f * -1.0F), 3.6F, 3.5);
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(f * 120.0F));
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(200.0F));
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f * -135.0F));
      matrixStackIn.m_85837_((double)(f * 5.6F), 0.0, 0.0);
      PlayerRenderer playerrenderer = (PlayerRenderer)renderManager.m_114382_(abstractclientplayerentity);
      if (flag) {
         playerrenderer.m_117770_(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayerentity);
         matrixStackIn.m_85841_(1.02F, 1.02F, 1.02F);
         this.renderRightArm(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayerentity, (PlayerModel<AbstractClientPlayer>)playerrenderer.m_7200_());
      } else {
         playerrenderer.m_117813_(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayerentity);
         matrixStackIn.m_85841_(1.02F, 1.02F, 1.02F);
         this.renderLeftArm(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayerentity, (PlayerModel<AbstractClientPlayer>)playerrenderer.m_7200_());
      }
   }

   public void renderRightArm(
      PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, AbstractClientPlayer playerIn, PlayerModel<AbstractClientPlayer> model
   ) {
      this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, model.f_102811_, model.f_103375_, model);
   }

   public void renderLeftArm(
      PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, AbstractClientPlayer playerIn, PlayerModel<AbstractClientPlayer> model
   ) {
      this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, model.f_102812_, model.f_103374_, model);
   }

   private void renderItem(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int combinedLightIn,
      AbstractClientPlayer playerIn,
      ModelPart rendererArmIn,
      ModelPart rendererArmwearIn,
      PlayerModel<AbstractClientPlayer> model
   ) {
      this.setModelVisibilities(playerIn, model);
      model.f_102608_ = 0.0F;
      model.f_102817_ = false;
      model.f_102818_ = 0.0F;
      model.m_6973_(playerIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      rendererArmwearIn.f_104203_ = 0.0F;
      rendererArmwearIn.m_104306_(
         matrixStackIn, bufferIn.m_6299_(RenderType.m_110473_(FROZEN_TEXTURE)), combinedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.8F
      );
   }

   private void setModelVisibilities(AbstractClientPlayer clientPlayer, PlayerModel<AbstractClientPlayer> playermodel) {
      if (clientPlayer.m_5833_()) {
         playermodel.m_8009_(false);
         playermodel.f_102808_.f_104207_ = true;
         playermodel.f_102809_.f_104207_ = true;
      } else {
         playermodel.m_8009_(true);
         playermodel.f_102809_.f_104207_ = clientPlayer.m_36170_(PlayerModelPart.HAT);
         playermodel.f_103378_.f_104207_ = clientPlayer.m_36170_(PlayerModelPart.JACKET);
         playermodel.f_103376_.f_104207_ = clientPlayer.m_36170_(PlayerModelPart.LEFT_PANTS_LEG);
         playermodel.f_103377_.f_104207_ = clientPlayer.m_36170_(PlayerModelPart.RIGHT_PANTS_LEG);
         playermodel.f_103374_.f_104207_ = clientPlayer.m_36170_(PlayerModelPart.LEFT_SLEEVE);
         playermodel.f_103375_.f_104207_ = clientPlayer.m_36170_(PlayerModelPart.RIGHT_SLEEVE);
         playermodel.f_102817_ = clientPlayer.m_6047_();
      }
   }

   public static class GeckoLayerFrozen<T extends LivingEntity & IAnimatable> extends GeoLayerRenderer<T> {
      public GeckoLayerFrozen(IGeoRenderer<T> entityRendererIn, Context context) {
         super(entityRendererIn);
      }

      public void render(
         PoseStack matrixStackIn,
         MultiBufferSource bufferIn,
         int packedLightIn,
         T living,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(living, CapabilityHandler.FROZEN_CAPABILITY);
         if (frozenCapability != null && frozenCapability.getFrozen()) {
            RenderType renderType = RenderType.m_110473_(FrozenRenderHandler.FROZEN_TEXTURE);
            this.getRenderer()
               .render(
                  this.getEntityModel().getModel(this.getEntityModel().getModelResource(living)),
                  living,
                  partialTicks,
                  renderType,
                  matrixStackIn,
                  bufferIn,
                  bufferIn.m_6299_(renderType),
                  packedLightIn,
                  OverlayTexture.f_118083_,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F
               );
         }
      }
   }

   public static class LayerFrozen<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
      private final LivingEntityRenderer<T, M> renderer;

      public LayerFrozen(LivingEntityRenderer<T, M> renderer) {
         super(renderer);
         this.renderer = renderer;
      }

      public void render(
         PoseStack matrixStackIn,
         MultiBufferSource bufferIn,
         int packedLightIn,
         LivingEntity living,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(living, CapabilityHandler.FROZEN_CAPABILITY);
         if (frozenCapability != null && frozenCapability.getFrozen()) {
            EntityModel model = this.renderer.m_7200_();
            float transparency = 1.0F;
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110473_(FrozenRenderHandler.FROZEN_TEXTURE));
            model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, transparency);
         }
      }
   }
}
