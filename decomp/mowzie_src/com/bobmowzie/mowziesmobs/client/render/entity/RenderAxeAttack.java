package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelAxeAttack;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityAxeAttack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderAxeAttack extends EntityRenderer<EntityAxeAttack> {
   public static ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/wroughtnaut.png");
   ModelAxeAttack model = new ModelAxeAttack();

   public RenderAxeAttack(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(EntityAxeAttack entity) {
      return TEXTURE;
   }

   public void render(EntityAxeAttack axe, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      if (!(Boolean)ConfigHandler.CLIENT.customPlayerAnims.get()) {
         Player player = Minecraft.m_91087_().f_91074_;
         if (player != null && player == axe.getCaster()) {
            matrixStackIn.m_85836_();
            Vec3 prevAxePos = new Vec3(axe.f_19790_, axe.f_19791_, axe.f_19792_);
            Vec3 prevPlayerPos = new Vec3(player.f_19790_, player.f_19791_, player.f_19792_);
            Vec3 axePos = prevAxePos.m_82549_(axe.m_20182_().m_82546_(prevAxePos).m_82490_((double)delta));
            Vec3 playerPos = prevPlayerPos.m_82549_(player.m_20182_().m_82546_(prevPlayerPos).m_82490_((double)delta));
            Vec3 deltaPos = axePos.m_82546_(playerPos).m_82490_(-1.0);
            matrixStackIn.m_85837_(deltaPos.m_7096_(), deltaPos.m_7098_(), deltaPos.m_7094_());
            matrixStackIn.m_85845_(new Quaternion(new Vector3f(0.0F, -1.0F, 0.0F), player.m_146908_(), true));
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110446_(TEXTURE));
            this.model.setupAnim(axe, 0.0F, 0.0F, (float)axe.f_19797_ + delta, 0.0F, 0.0F);
            this.model.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.m_85849_();
         }
      }
   }
}
