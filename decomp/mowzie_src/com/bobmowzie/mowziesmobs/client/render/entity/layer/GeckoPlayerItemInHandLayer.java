package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GeckoPlayerItemInHandLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> implements IGeckoRenderLayer {
   private GeckoRenderPlayer renderPlayerAnimated;

   public GeckoPlayerItemInHandLayer(GeckoRenderPlayer entityRendererIn) {
      super(entityRendererIn);
      this.renderPlayerAnimated = entityRendererIn;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractClientPlayer entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (this.renderPlayerAnimated.getAnimatedPlayerModel().isInitialized()) {
         boolean flag = entitylivingbaseIn.m_5737_() == HumanoidArm.RIGHT;
         ItemStack mainHandStack = entitylivingbaseIn.m_21205_();
         ItemStack offHandStack = entitylivingbaseIn.m_21206_();
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(entitylivingbaseIn);
         if (abilityCapability != null
            && abilityCapability.getActiveAbility() != null
            && abilityCapability.getActiveAbility() instanceof PlayerAbility playerAbility) {
            mainHandStack = playerAbility.heldItemMainHandOverride() != null ? playerAbility.heldItemMainHandOverride() : mainHandStack;
            offHandStack = playerAbility.heldItemOffHandOverride() != null ? playerAbility.heldItemOffHandOverride() : offHandStack;
         }

         ItemStack itemstack = flag ? offHandStack : mainHandStack;
         ItemStack itemstack1 = flag ? mainHandStack : offHandStack;
         if (!itemstack.m_41619_() || !itemstack1.m_41619_()) {
            matrixStackIn.m_85836_();
            if (((PlayerModel)this.m_117386_()).f_102610_) {
               float f = 0.5F;
               matrixStackIn.m_85837_(0.0, 0.75, 0.0);
               matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
            }

            this.renderArmWithItem(
               entitylivingbaseIn, itemstack1, TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, matrixStackIn, bufferIn, packedLightIn
            );
            this.renderArmWithItem(
               entitylivingbaseIn, itemstack, TransformType.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, matrixStackIn, bufferIn, packedLightIn
            );
            matrixStackIn.m_85849_();
         }
      }
   }

   private void renderArmWithItem(
      LivingEntity entity,
      ItemStack itemStack,
      TransformType transformType,
      HumanoidArm side,
      PoseStack matrixStack,
      MultiBufferSource buffer,
      int packedLightIn
   ) {
      if (!itemStack.m_41619_()) {
         String boneName = side == HumanoidArm.RIGHT ? "RightHeldItem" : "LeftHeldItem";
         MowzieGeoBone bone = this.renderPlayerAnimated.getAnimatedPlayerModel().getMowzieBone(boneName);
         PoseStack newMatrixStack = new PoseStack();
         newMatrixStack.m_85850_().m_85864_().m_8178_(bone.getWorldSpaceNormal());
         newMatrixStack.m_85850_().m_85861_().m_27644_(bone.getWorldSpaceXform());
         newMatrixStack.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
         boolean flag = side == HumanoidArm.LEFT;
         Minecraft.m_91087_().m_91290_().m_234586_().m_109322_(entity, itemStack, transformType, flag, newMatrixStack, buffer, packedLightIn);
      }
   }
}
