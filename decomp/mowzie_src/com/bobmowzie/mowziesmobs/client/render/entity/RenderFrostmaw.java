package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelFrostmaw;
import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ItemLayer;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class RenderFrostmaw extends MobRenderer<EntityFrostmaw, ModelFrostmaw<EntityFrostmaw>> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/frostmaw.png");

   public RenderFrostmaw(Context mgr) {
      super(mgr, new ModelFrostmaw(), 3.5F);
      this.m_115326_(new ItemLayer(this, ((ModelFrostmaw)this.m_7200_()).iceCrystalHand, ItemHandler.ICE_CRYSTAL.m_7968_(), TransformType.GROUND));
      this.m_115326_(new ItemLayer(this, ((ModelFrostmaw)this.m_7200_()).iceCrystal, ItemHandler.ICE_CRYSTAL.m_7968_(), TransformType.GROUND));
   }

   protected float getFlipDegrees(EntityFrostmaw entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityFrostmaw entity) {
      return TEXTURE;
   }

   public void render(EntityFrostmaw entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (entity.getAnimation() == EntityFrostmaw.SWIPE_ANIMATION
         || entity.getAnimation() == EntityFrostmaw.SWIPE_TWICE_ANIMATION
         || entity.getAnimation() == EntityFrostmaw.ICE_BREATH_ANIMATION
         || entity.getAnimation() == EntityFrostmaw.ICE_BALL_ANIMATION
         || !entity.getActive()) {
         Vec3 rightHandPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, ((ModelFrostmaw)this.f_115290_).rightHandSocket);
         Vec3 leftHandPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, ((ModelFrostmaw)this.f_115290_).leftHandSocket);
         Vec3 mouthPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, ((ModelFrostmaw)this.f_115290_).mouthSocket);
         entity.setSocketPosArray(0, rightHandPos);
         entity.setSocketPosArray(1, leftHandPos);
         entity.setSocketPosArray(2, mouthPos);
      }
   }
}
