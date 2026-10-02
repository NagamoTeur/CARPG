package software.bernie.ars_nouveau.geckolib3.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;

public class ResourceListener {
   public static void registerReloadListener() {
      if (Minecraft.m_91087_() != null) {
         if (Minecraft.m_91087_().m_91098_() == null) {
            throw new RuntimeException("GeckoLib was initialized too early! If you are on fabric, please read the wiki on when to initialize!");
         }

         ReloadableResourceManager reloadable = (ReloadableResourceManager)Minecraft.m_91087_().m_91098_();
         reloadable.m_7217_(GeckoLibCache.getInstance()::reload);
      } else {
         GeckoLib.LOGGER.warn("Minecraft.getInstance() was null, could not register reload listeners. Ignore if datagenning.");
      }
   }
}
