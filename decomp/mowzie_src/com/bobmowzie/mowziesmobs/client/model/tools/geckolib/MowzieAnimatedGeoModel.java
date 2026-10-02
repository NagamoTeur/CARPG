package com.bobmowzie.mowziesmobs.client.model.tools.geckolib;

import com.bobmowzie.mowziesmobs.server.entity.IAnimationTickable;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;

public abstract class MowzieAnimatedGeoModel<T extends IAnimatable & IAnimationTickable> extends AnimatedGeoModel<T> {
   public MowzieGeoBone getMowzieBone(String boneName) {
      IBone bone = this.getBone(boneName);
      return (MowzieGeoBone)bone;
   }

   public boolean isInitialized() {
      return !this.getAnimationProcessor().getModelRendererList().isEmpty();
   }

   public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
      if (!(animatable instanceof MowzieEntity) || !((MowzieEntity)animatable).renderingInGUI) {
         Minecraft mc = Minecraft.m_91087_();
         AnimationData manager = animatable.getFactory().getOrCreateAnimationData(instanceId);
         double currentTick = (double)animatable.tickTimer();
         if (manager.startTick == -1.0) {
            manager.startTick = currentTick + (double)mc.m_91296_();
         }

         if (!Minecraft.m_91087_().m_91104_() || manager.shouldPlayWhilePaused) {
            manager.tick = currentTick + (double)mc.m_91296_();
            double gameTick = manager.tick;
            double deltaTicks = gameTick - this.lastGameTickTime;
            this.seekTime += deltaTicks;
            this.lastGameTickTime = gameTick;
         }

         AnimationEvent<T> predicate = animationEvent == null
            ? new AnimationEvent(animatable, 0.0F, 0.0F, (float)(manager.tick - this.lastGameTickTime), false, Collections.emptyList())
            : animationEvent;
         predicate.animationTick = this.seekTime;
         this.getAnimationProcessor().preAnimationSetup(predicate.getAnimatable(), this.seekTime);
         if (!this.getAnimationProcessor().getModelRendererList().isEmpty()) {
            this.getAnimationProcessor()
               .tickAnimation(animatable, instanceId, this.seekTime, predicate, GeckoLibCache.getInstance().parser, this.shouldCrashOnMissing);
         }

         if (!Minecraft.m_91087_().m_91104_() || manager.shouldPlayWhilePaused) {
            this.codeAnimations(animatable, instanceId, animationEvent);
         }
      }
   }

   public void codeAnimations(T entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
   }

   public float getControllerValueInverted(String controllerName) {
      return !this.isInitialized() ? 1.0F : 1.0F - this.getBone(controllerName).getPositionX();
   }

   public float getControllerValue(String controllerName) {
      return !this.isInitialized() ? 0.0F : this.getBone(controllerName).getPositionX();
   }
}
