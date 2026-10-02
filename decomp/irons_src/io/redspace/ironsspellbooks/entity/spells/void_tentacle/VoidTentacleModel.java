package io.redspace.ironsspellbooks.entity.spells.void_tentacle;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidTentacleModel extends AnimatedGeoModel<VoidTentacle> {
   public static final ResourceLocation modelResource = new ResourceLocation("irons_spellbooks", "geo/void_tentacle.geo.json");
   public static final ResourceLocation textureResource = IronsSpellbooks.id("textures/entity/void_tentacle/void_tentacle.png");
   public static final ResourceLocation animationResource = new ResourceLocation("irons_spellbooks", "animations/void_tentacle_animations.json");

   public ResourceLocation getModelResource(VoidTentacle object) {
      return modelResource;
   }

   public ResourceLocation getTextureResource(VoidTentacle mob) {
      return textureResource;
   }

   public ResourceLocation getAnimationResource(VoidTentacle animatable) {
      return animationResource;
   }

   public void setCustomAnimations(VoidTentacle animatable, int instanceId, AnimationEvent animationEvent) {
      super.setCustomAnimations(animatable, instanceId, animationEvent);
      float seed = (float)(animatable.m_20185_() * animatable.m_20189_()) % 173.0F;
      float speed = 0.55F;
      float f = (seed + (float)animatable.f_19797_ + animationEvent.getPartialTick()) * speed;
      List<IBone> bones = List.of(
         this.getAnimationProcessor().getBone("root"),
         this.getAnimationProcessor().getBone("segment_1"),
         this.getAnimationProcessor().getBone("segment_2"),
         this.getAnimationProcessor().getBone("segment_3"),
         this.getAnimationProcessor().getBone("segment_4")
      );
      int age = animatable.f_19797_;
      float tween = Mth.m_14036_(age < 15 ? (float)(age - 5) / 10.0F : (age > 240 ? 1.0F - (float)(age - 240) / 50.0F : 1.0F), 0.0F, 1.0F);

      for (int i = 0; i < bones.size(); i++) {
         IBone bone = bones.get(i);
         float intensity = 3.0F - (float)i * 0.2F;
         bone.setRotationX(Mth.m_14179_(tween, bone.getRotationX(), intensity * (float) (Math.PI / 180.0) * shittyNoise(f + 100.0F + (float)i)));
         bone.setRotationZ(Mth.m_14179_(tween, bone.getRotationZ(), intensity * (float) (Math.PI / 180.0) * shittyNoise(f + (float)i)));
      }

      this.getAnimationProcessor()
         .getBone("root")
         .setRotationY((float) (Math.PI / 180.0) * (shittyNoise(f + 150.0F) * 0.25F + (float)animatable.f_19797_ + animationEvent.getPartialTick()));
   }

   private static float shittyNoise(float f) {
      return (Mth.m_14031_(f * 0.1F) + Mth.m_14031_(f * 0.25F) + 2.0F * Mth.m_14031_(f * 0.333F) + 3.0F * Mth.m_14031_(f * 0.5F) + 4.0F * Mth.m_14031_(f))
         * 1.5F;
   }
}
