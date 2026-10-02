package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBow;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;

public class SpellBowRenderer extends FixedGeoItemRenderer<SpellBow> {
   public SpellBowRenderer() {
      super(new SpellBowModel());
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("gem")) {
         super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      } else {
         super.renderRecursively(
            bone,
            poseStack,
            buffer,
            packedLight,
            packedOverlay,
            (float)Color.WHITE.getRed() / 255.0F,
            (float)Color.WHITE.getGreen() / 255.0F,
            (float)Color.WHITE.getBlue() / 255.0F,
            (float)Color.WHITE.getAlpha() / 255.0F
         );
      }
   }

   @Override
   public Color getRenderColor(
      Object animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight
   ) {
      ParticleColor color = ParticleColor.defaultParticleColor();
      if (this.currentItemStack.m_41782_()) {
         color = ((SpellBow)animatable).getSpellCaster(this.currentItemStack).getColor();
      }

      return Color.ofRGBA(color.toWrapper().r, color.toWrapper().g, color.toWrapper().b, 200);
   }

   @Override
   public void m_108829_(ItemStack itemStack, TransformType transformType, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
      if (transformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
         Player player = Minecraft.m_91087_().f_91074_;
         Vec3 playerPos = player.m_20182_().m_82520_(0.0, (double)player.m_20192_(), 0.0);
         Vec3 look = player.m_20154_();
         Vec3 right = new Vec3(-look.f_82481_, 0.0, look.f_82479_).m_82541_();
         Vec3 down = right.m_82537_(look);
         right = right.m_82490_(0.2 - (double)player.f_20921_);
         Vec3 forward = look.m_82490_(0.45F);
         down = down.m_82490_(-0.1 - (double)player.f_20921_);
         Vec3 laserPos = playerPos.m_82549_(right);
         laserPos = laserPos.m_82549_(forward);
         laserPos = laserPos.m_82549_(down);
         ISpellCaster tool = CasterUtil.getCaster(itemStack);
         int timeHeld = 72000 - Minecraft.m_91087_().f_91074_.m_21212_();
         if (timeHeld > 0 && timeHeld != 72000) {
            float scaleAge = (float)ParticleUtil.inRange(0.05, 0.1);
            if (player.f_19853_.f_46441_.m_188503_(6) == 0) {
               for (int i = 0; i < 1; i++) {
                  Vec3 particlePos = new Vec3(laserPos.f_82479_, laserPos.f_82480_, laserPos.f_82481_);
                  particlePos = particlePos.m_82549_(ParticleUtil.pointInSphere().m_82490_(0.3F));
                  player.f_19853_
                     .m_7106_(
                        ParticleLineData.createData(tool.getColor(), scaleAge, 5 + player.f_19853_.f_46441_.m_188503_(20)),
                        particlePos.m_7096_(),
                        particlePos.m_7098_(),
                        particlePos.m_7094_(),
                        laserPos.m_7096_(),
                        laserPos.m_7098_(),
                        laserPos.m_7094_()
                     );
               }
            }
         }
      }

      super.m_108829_(itemStack, transformType, stack, bufferIn, combinedLightIn, p_239207_6_);
   }

   @Override
   public void render(
      GeoModel model,
      Object animatable,
      float partialTicks,
      RenderType type,
      PoseStack matrixStackIn,
      @javax.annotation.Nullable MultiBufferSource renderTypeBuffer,
      @javax.annotation.Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      IBone top = model.getBone("bow_top").get();
      IBone gem = model.getBone("gem").get();
      IBone bottom = model.getBone("bow_bot").get();
      float outerAngle = ((float)ClientInfo.ticksInGame + partialTicks) / 10.0F % 360.0F;
      top.setRotationZ((float)Math.toRadians(-10.0));
      top.setRotationY(0.0F);
      top.setRotationX(0.0F);
      bottom.setRotationZ((float)Math.toRadians(10.0));
      bottom.setRotationY(0.0F);
      bottom.setRotationX((float)Math.toRadians(-180.0));
      if (Minecraft.m_91087_().f_91074_.m_21205_().equals(this.currentItemStack)) {
         int timeHeld = (int)((float)(72000 - Minecraft.m_91087_().f_91074_.m_21212_()) + partialTicks);
         if (timeHeld != 0 && timeHeld != 72000) {
            top.setRotationZ((float)(Math.toRadians(-10.0) - Math.toRadians((double)timeHeld) * 2.0));
            bottom.setRotationZ((float)(Math.toRadians(-10.0) + Math.toRadians((double)timeHeld) * 2.0));
            outerAngle = ((float)ClientInfo.ticksInGame + partialTicks) / 5.0F % 360.0F;
            if (timeHeld >= 19) {
               top.setRotationZ((float)(Math.toRadians(-10.0) - Math.toRadians(19.0) * 2.0));
               bottom.setRotationZ((float)(Math.toRadians(-10.0) + Math.toRadians(19.0) * 2.0));
               outerAngle = ((float)ClientInfo.ticksInGame + partialTicks) / 3.0F % 360.0F;
            }
         }
      }

      gem.setRotationX(outerAngle);
      gem.setRotationY(outerAngle);
      super.render(
         model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha
      );
   }

   @Override
   public RenderType getRenderType(
      Object animatable,
      float partialTicks,
      PoseStack stack,
      @javax.annotation.Nullable MultiBufferSource renderTypeBuffer,
      @javax.annotation.Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110473_(textureLocation);
   }
}
