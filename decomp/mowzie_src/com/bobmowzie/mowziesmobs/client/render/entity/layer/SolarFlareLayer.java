package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSunstrike;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy.SolarFlareAbility;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class SolarFlareLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private GeckoRenderPlayer renderPlayerAnimated;

   public SolarFlareLayer(GeckoRenderPlayer entityRendererIn) {
      super(entityRendererIn);
      this.renderPlayerAnimated = entityRendererIn;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      SolarFlareAbility ability = (SolarFlareAbility)AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.SOLAR_FLARE_ABILITY);
      if (ability != null && ability.isUsing() && ability.getTicksInUse() > 12 && ability.getTicksInUse() < 21) {
         matrixStackIn.m_85836_();
         MowzieGeoBone bone = this.renderPlayerAnimated.getAnimatedPlayerModel().getMowzieBone("Body");
         Vector4f vecTranslation = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
         vecTranslation.m_123607_(bone.getWorldSpaceXform());
         PoseStack newMatrixStack = new PoseStack();
         newMatrixStack.m_85837_((double)vecTranslation.m_123601_(), (double)vecTranslation.m_123615_(), (double)vecTranslation.m_123616_());
         newMatrixStack.m_85841_(0.8F, 0.8F, 0.8F);
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(MMRenderType.getSolarFlare(RenderSunstrike.TEXTURE));
         Pose matrixstack$entry2 = newMatrixStack.m_85850_();
         Matrix4f matrix4f2 = matrixstack$entry2.m_85861_();
         Matrix3f matrix3f = matrixstack$entry2.m_85864_();
         RenderUmvuthi.drawBurst(matrix4f2, matrix3f, ivertexbuilder, (float)(ability.getTicksInUse() - 12) + partialTicks, packedLightIn);
         matrixStackIn.m_85849_();
      }
   }
}
