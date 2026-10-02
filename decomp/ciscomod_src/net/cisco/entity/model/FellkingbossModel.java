package net.cisco.entity.model;

import net.cisco.entity.FellkingbossEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class FellkingbossModel extends AnimatedGeoModel<FellkingbossEntity> {
   public ResourceLocation getAnimationResource(FellkingbossEntity entity) {
      return new ResourceLocation("cisco_mod", "animations/fellkingbossre.animation.json");
   }

   public ResourceLocation getModelResource(FellkingbossEntity entity) {
      return new ResourceLocation("cisco_mod", "geo/fellkingbossre.geo.json");
   }

   public ResourceLocation getTextureResource(FellkingbossEntity entity) {
      return new ResourceLocation("cisco_mod", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(FellkingbossEntity animatable, int instanceId, AnimationEvent animationEvent) {
      super.setCustomAnimations(animatable, instanceId, animationEvent);
      IBone head = this.getAnimationProcessor().getBone("Head");
      EntityModelData extraData = (EntityModelData)animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
      AnimationData manager = animatable.getFactory().getOrCreateAnimationData(instanceId);
      int unpausedMultiplier = Minecraft.m_91087_().m_91104_() && !manager.shouldPlayWhilePaused ? 0 : 1;
      head.setRotationX(head.getRotationX() + extraData.headPitch * (float) (Math.PI / 180.0) * (float)unpausedMultiplier);
      head.setRotationY(head.getRotationY() + extraData.netHeadYaw * (float) (Math.PI / 180.0) * (float)unpausedMultiplier);
   }
}
