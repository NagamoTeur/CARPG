package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.IRendererWithModel;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ObsidilithArmorRenderer implements IRendererWithModel, IRenderer<ObsidilithEntity> {
   private final AnimatedGeoModel<ObsidilithEntity> geoModel;
   private final Context context;
   private final ResourceLocation armorTexture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/obsidilith_armor.png");
   private ObsidilithArmorRenderer.RenderHelper geoModelProvider;
   private ObsidilithEntity obsidilithEntity;
   private RenderType type;

   public ObsidilithArmorRenderer(AnimatedGeoModel<ObsidilithEntity> geoModel, Context context) {
      this.geoModel = geoModel;
      this.context = context;
   }

   public void render(ObsidilithEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float renderAge = (float)entity.f_19797_ + partialTicks;
      float textureOffset = renderAge * new Random().nextFloat();
      if (this.geoModelProvider == null) {
         this.geoModelProvider = new ObsidilithArmorRenderer.RenderHelper(this.geoModel, this.context);
      }

      this.obsidilithEntity = entity;
      this.type = RenderType.m_110436_(this.armorTexture, textureOffset, textureOffset);
   }

   @Override
   public void render(
      GeoModel model,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      VertexConsumer energyBuffer = buffer.m_6299_(this.type);
      if (this.obsidilithEntity != null) {
         if (this.type != null) {
            if (this.obsidilithEntity.isShielded()) {
               Vec3 color = this.getColor().m_82549_(VecUtils.unit).m_82541_().m_82490_(0.6);
               if (this.geoModelProvider == null) {
                  return;
               }

               this.geoModelProvider
                  .render(
                     model,
                     this.obsidilithEntity,
                     partialTicks,
                     this.type,
                     poseStack,
                     buffer,
                     energyBuffer,
                     packedLightIn,
                     OverlayTexture.f_118083_,
                     (float)color.f_82479_,
                     (float)color.f_82480_,
                     (float)color.f_82481_,
                     1.0F
                  );
            }
         }
      }
   }

   private Vec3 getColor() {
      return switch (this.obsidilithEntity.currentAttack) {
         case 5 -> BMDColors.ORANGE;
         case 6 -> BMDColors.RED;
         case 7 -> BMDColors.COMET_BLUE;
         case 8 -> BMDColors.ENDER_PURPLE;
         case 9 -> BMDColors.WHITE;
         default -> BMDColors.WHITE;
      };
   }

   private static class RenderHelper extends GeoEntityRenderer<ObsidilithEntity> {
      public RenderHelper(AnimatedGeoModel<ObsidilithEntity> parentModel, Context context) {
         super(context, parentModel);
      }

      public void renderCube(
         GeoCube cube, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
      ) {
         poseStack.m_85836_();
         poseStack.m_85841_(1.08F, 1.05F, 1.08F);
         super.renderCube(cube, poseStack, buffer, 15728880, packedOverlay, red, green, blue, alpha);
         poseStack.m_85849_();
      }
   }
}
