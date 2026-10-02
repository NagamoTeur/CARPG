package software.bernie.ars_nouveau.geckolib3.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import software.bernie.ars_nouveau.geckolib3.model.provider.GeoModelProvider;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.IGeoRenderer;

public class AnimationUtils {
   public static double convertTicksToSeconds(double ticks) {
      return ticks / 20.0;
   }

   public static double convertSecondsToTicks(double seconds) {
      return seconds * 20.0;
   }

   public static <T extends Entity> EntityRenderer<T> getRenderer(T entity) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      return renderManager.m_114382_(entity);
   }

   public static <T extends Entity> GeoModelProvider getGeoModelForEntity(T entity) {
      return getRenderer(entity) instanceof IGeoRenderer geoRenderer ? geoRenderer.getGeoModelProvider() : null;
   }
}
