package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NethermanModel extends AnimatedGeoModel<NethermanEntity> {
   private final String TEXTURE_PATH = "textures/entity/";

   public ResourceLocation getModelResource(NethermanEntity animatable) {
      if (animatable.m_21224_()) {
         return new ResourceLocation("knightquest", "geo/netherman_magic.geo.json");
      } else {
         String path = switch (animatable.getPhase()) {
            case 1 -> "geo/netherman_fire.geo.json";
            case 2 -> animatable.getCounterSwitchPhase2() == 130 ? "geo/netherman_ice.geo.json" : "geo/netherman_fire.geo.json";
            default -> animatable.getCounterSwitchPhase3() == 160 ? "geo/netherman_magic.geo.json" : "geo/netherman_ice.geo.json";
         };
         return new ResourceLocation("knightquest", path);
      }
   }

   public ResourceLocation getTextureResource(NethermanEntity animatable) {
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
            default -> animatable.getCounterSwitchPhase3() == 160 ? "textures/entity/netherman_magic.json" : "textures/entity/netherman_ice.json";
         };
         return new ResourceLocation("knightquest", path);
      }
   }

   public ResourceLocation getAnimationResource(NethermanEntity animatable) {
      if (animatable.m_21224_()) {
         return new ResourceLocation("knightquest", "animations/netherman_magic.animation.json");
      } else if ((animatable.getCounterSwitchPhase2() != 130 || animatable.getPhase() != 2)
         && (animatable.getCounterSwitchPhase3() == 160 || animatable.getPhase() != 3)) {
         return animatable.getCounterSwitchPhase3() == 160 && animatable.getPhase() == 3
            ? new ResourceLocation("knightquest", "animations/netherman_magic.animation.json")
            : new ResourceLocation("knightquest", "animations/netherman_fire.animation.json");
      } else {
         return new ResourceLocation("knightquest", "animations/netherman_ice.animation.json");
      }
   }
}
