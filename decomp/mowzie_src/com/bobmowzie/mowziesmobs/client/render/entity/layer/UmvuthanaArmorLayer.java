package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import java.util.Optional;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class UmvuthanaArmorLayer extends GeoLayerRenderer<EntityUmvuthana> {
   private final HumanoidModel defaultBipedModel;
   private MowzieGeckoEntity entity;

   public UmvuthanaArmorLayer(IGeoRenderer<EntityUmvuthana> entityRendererIn, Context context) {
      super(entityRendererIn);
      this.defaultBipedModel = new HumanoidModel(context.m_174023_(ModelLayers.f_171164_));
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferIn,
      int packedLightIn,
      EntityUmvuthana entityLivingBaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      poseStack.m_85836_();
      this.entity = entityLivingBaseIn;
      GeoModel model = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelResource(this.entity));
      String boneName = "maskTwitcher";
      String handBoneName = "maskHand";
      Optional<GeoBone> bone = model.getBone(boneName);
      if (bone.isPresent() && !bone.get().isHidden()) {
         Matrix4f matrix4f = bone.get().getModelSpaceXform();
         poseStack.m_166854_(matrix4f);
         this.renderArmor(entityLivingBaseIn, bufferIn, poseStack, packedLightIn);
      }

      Optional<GeoBone> handBone = model.getBone(handBoneName);
      if (handBone.isPresent() && !handBone.get().isHidden()) {
         Matrix4f matrix4f = handBone.get().getModelSpaceXform();
         poseStack.m_166854_(matrix4f);
         this.renderArmor(entityLivingBaseIn, bufferIn, poseStack, packedLightIn);
      }

      poseStack.m_85849_();
   }

   private void renderArmor(LivingEntity entityLivingBaseIn, MultiBufferSource bufferIn, PoseStack poseStack, int packedLightIn) {
      ItemStack itemStack = entityLivingBaseIn.m_6844_(EquipmentSlot.HEAD);
      if (itemStack.m_41720_() instanceof ArmorItem) {
         ArmorItem armoritem = (ArmorItem)itemStack.m_41720_();
         if (armoritem.m_40402_() == EquipmentSlot.HEAD) {
            boolean glintIn = itemStack.m_41790_();
            HumanoidModel a = this.defaultBipedModel;
            a = this.getArmorModelHook(entityLivingBaseIn, itemStack, EquipmentSlot.HEAD, a);
            String armorTexture = armoritem.getArmorTexture(itemStack, entityLivingBaseIn, EquipmentSlot.HEAD, null);
            if (armorTexture != null) {
               VertexConsumer ivertexbuilder = ItemRenderer.m_115211_(bufferIn, RenderType.m_110458_(new ResourceLocation(armorTexture)), false, glintIn);
               poseStack.m_85845_(Quaternion.m_175228_(0.0F, 0.0F, (float) Math.PI));
               poseStack.m_85841_(1.111F, 1.111F, 1.111F);
               poseStack.m_85837_(0.0, 0.25, 0.15);
               a.m_7695_(poseStack, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   protected HumanoidModel<?> getArmorModelHook(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
      Model basicModel = ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
      return basicModel instanceof HumanoidModel ? (HumanoidModel)basicModel : model;
   }
}
