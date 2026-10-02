package net.sweenus.simplyswords.forge;

import java.nio.file.Path;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

public class SimplySwordsExpectPlatformImpl {
   public static Path getConfigDirectory() {
      return FMLPaths.CONFIGDIR.get();
   }

   public static String getVersion() {
      return ModList.get().getModContainerById("simplyswords").map(it -> it.getModInfo().getVersion().toString()).orElseThrow();
   }
}
