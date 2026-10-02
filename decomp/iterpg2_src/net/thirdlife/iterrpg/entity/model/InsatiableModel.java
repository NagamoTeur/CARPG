package net.thirdlife.iterrpg.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.entity.InsatiableEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class InsatiableModel extends AnimatedGeoModel<InsatiableEntity> {
   public ResourceLocation getAnimationResource(InsatiableEntity entity) {
      return new ResourceLocation("iter_rpg", "animations/insatiable.animation.json");
   }

   public ResourceLocation getModelResource(InsatiableEntity entity) {
      return new ResourceLocation("iter_rpg", "geo/insatiable.geo.json");
   }

   public ResourceLocation getTextureResource(InsatiableEntity entity) {
      return new ResourceLocation("iter_rpg", "textures/entities/" + entity.getTexture() + ".png");
   }

   public void setCustomAnimations(InsatiableEntity animatable, int instanceId, AnimationEvent animationEvent) {
      super.setCustomAnimations(animatable, instanceId, animationEvent);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = (EntityModelData)animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
      AnimationData manager = animatable.getFactory().getOrCreateAnimationData(instanceId);
      int unpausedMultiplier = Minecraft.m_91087_().m_91104_() && !manager.shouldPlayWhilePaused ? 0 : 1;
      head.setRotationX(head.getRotationX() + extraData.headPitch * (float) (Math.PI / 180.0) * (float)unpausedMultiplier);
      head.setRotationY(head.getRotationY() + extraData.netHeadYaw * (float) (Math.PI / 180.0) * (float)unpausedMultiplier);
   }
}
