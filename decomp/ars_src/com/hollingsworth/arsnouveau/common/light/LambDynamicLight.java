package com.hollingsworth.arsnouveau.common.light;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.Level;

public interface LambDynamicLight {
   double getDynamicLightX();

   double getDynamicLightY();

   double getDynamicLightZ();

   Level getDynamicLightWorld();

   default boolean isDynamicLightEnabled() {
      return LightManager.containsLightSource(this);
   }

   void resetDynamicLight();

   default void setDynamicLightEnabled(boolean enabled) {
      this.resetDynamicLight();
      if (enabled) {
         LightManager.addLightSource(this);
      } else {
         LightManager.removeLightSource(this);
      }
   }

   int getLuminance();

   void dynamicLightTick();

   boolean shouldUpdateDynamicLight();

   boolean lambdynlights$updateDynamicLight(LevelRenderer var1);

   void lambdynlights$scheduleTrackedChunksRebuild(LevelRenderer var1);
}
