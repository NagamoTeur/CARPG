package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.NethermanModel;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NethermanRenderer extends GeoEntityRenderer<NethermanEntity> {
   private final String TEXTURE_PATH = "textures/entity/";

   public NethermanRenderer(Context renderManager) {
      super(renderManager, new NethermanModel());
   }

   protected float getDeathMaxRotation(NethermanEntity animatable) {
      return 0.0F;
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull NethermanEntity animatable) {
      if (animatable.m_21224_()) {
         return new ResourceLocation("knightquest", "textures/entity/netherman_magic.png");
      } else if (animatable.getPhase() == 2 && animatable.getCounterSwitchPhase2() < 130) {
         switch (animatable.getCounterSwitchPhase2()) {
            default:
               String path = animatable.getCounterSwitchPhase2() < 50
                  ? "textures/entity/netherman_fire.png"
                  : (
                     animatable.getCounterSwitchPhase2() < 60
                        ? "textures/entity/animated/netherman_switch_phase2_0.png"
                        : (
                           animatable.getCounterSwitchPhase2() < 70
                              ? "textures/entity/animated/netherman_switch_phase2_1.png"
                              : (
                                 animatable.getCounterSwitchPhase2() < 80
                                    ? "textures/entity/animated/netherman_switch_phase2_2.png"
                                    : (
                                       animatable.getCounterSwitchPhase2() < 90
                                          ? "textures/entity/animated/netherman_switch_phase2_3.png"
                                          : (
                                             animatable.getCounterSwitchPhase2() < 100
                                                ? "textures/entity/animated/netherman_switch_phase2_4.png"
                                                : (
                                                   animatable.getCounterSwitchPhase2() < 110
                                                      ? "textures/entity/animated/netherman_switch_phase2_5.png"
                                                      : (
                                                         animatable.getCounterSwitchPhase2() < 120
                                                            ? "textures/entity/animated/netherman_switch_phase2_6.png"
                                                            : (
                                                               animatable.getCounterSwitchPhase2() < 130
                                                                  ? "textures/entity/netherman_ice.png"
                                                                  : "textures/entity/netherman_fire.png"
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  );
               return new ResourceLocation("knightquest", path);
         }
      } else if (animatable.getPhase() == 3 && animatable.getCounterSwitchPhase3() < 160) {
         switch (animatable.getCounterSwitchPhase3()) {
            default:
               String path = animatable.getCounterSwitchPhase3() < 50
                  ? "textures/entity/netherman_ice.png"
                  : (
                     animatable.getCounterSwitchPhase3() < 65
                        ? "textures/entity/animated/netherman_switch_phase3_0.png"
                        : (
                           animatable.getCounterSwitchPhase3() < 70
                              ? "textures/entity/animated/netherman_switch_phase3_1.png"
                              : (
                                 animatable.getCounterSwitchPhase3() < 85
                                    ? "textures/entity/animated/netherman_switch_phase3_2.png"
                                    : (
                                       animatable.getCounterSwitchPhase3() < 100
                                          ? "textures/entity/animated/netherman_switch_phase3_3.png"
                                          : (
                                             animatable.getCounterSwitchPhase3() < 115
                                                ? "textures/entity/animated/netherman_switch_phase3_4.png"
                                                : (
                                                   animatable.getCounterSwitchPhase3() < 130
                                                      ? "textures/entity/animated/netherman_switch_phase3_5.png"
                                                      : (
                                                         animatable.getCounterSwitchPhase3() < 145
                                                            ? "textures/entity/animated/netherman_switch_phase3_6.png"
                                                            : (
                                                               animatable.getCounterSwitchPhase3() < 160
                                                                  ? "textures/entity/animated/netherman_switch_phase3_7.png"
                                                                  : "textures/entity/netherman_magic.png"
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  );
               return new ResourceLocation("knightquest", path);
         }
      } else {
         String path = switch (animatable.getPhase()) {
            case 1 -> "textures/entity/netherman_fire.png";
            case 2 -> animatable.getCounterSwitchPhase2() == 130 ? "textures/entity/netherman_ice.png" : "textures/entity/netherman_fire.png";
            default -> animatable.getCounterSwitchPhase3() == 160 ? "textures/entity/netherman_magic.png" : "textures/entity/netherman_ice.png";
         };
         return new ResourceLocation("knightquest", path);
      }
   }

   public RenderType getRenderType(
      NethermanEntity animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return RenderType.m_110473_(this.getTextureLocation(animatable));
   }

   public void render(
      @NotNull NethermanEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight
   ) {
      poseStack.m_85841_(1.2F, 1.2F, 1.2F);
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
