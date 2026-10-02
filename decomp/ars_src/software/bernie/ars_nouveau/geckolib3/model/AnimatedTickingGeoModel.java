package software.bernie.ars_nouveau.geckolib3.model;

import java.util.Collections;
import net.minecraft.client.Minecraft;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimationTickable;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.resource.GeckoLibCache;

public abstract class AnimatedTickingGeoModel<T extends IAnimatable & IAnimationTickable> extends AnimatedGeoModel<T> {
   public boolean isInitialized() {
      return !this.getAnimationProcessor().getModelRendererList().isEmpty();
   }

   @Deprecated(
      forRemoval = true
   )
   @Override
   public void setLivingAnimations(T animatable, Integer instanceId, AnimationEvent animationEvent) {
      this.setCustomAnimations(animatable, instanceId, animationEvent);
   }

   @Override
   public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
      AnimationData manager = animatable.getFactory().getOrCreateAnimationData(instanceId);
      if (manager.startTick == -1.0) {
         manager.startTick = (double)((float)animatable.tickTimer() + Minecraft.m_91087_().m_91296_());
      }

      if (!Minecraft.m_91087_().m_91104_() || manager.shouldPlayWhilePaused) {
         manager.tick = (double)((float)animatable.tickTimer() + Minecraft.m_91087_().m_91296_());
         double gameTick = manager.tick;
         double deltaTicks = gameTick - this.lastGameTickTime;
         this.seekTime += deltaTicks;
         this.lastGameTickTime = gameTick;
      }

      AnimationEvent<T> predicate;
      if (animationEvent == null) {
         predicate = new AnimationEvent<>(animatable, 0.0F, 0.0F, 0.0F, false, Collections.emptyList());
      } else {
         predicate = animationEvent;
      }

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

   @Override
   public void codeAnimations(T entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
   }
}
