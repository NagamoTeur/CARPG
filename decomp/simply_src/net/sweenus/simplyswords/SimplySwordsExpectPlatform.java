package net.sweenus.simplyswords;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.nio.file.Path;
import net.sweenus.simplyswords.forge.SimplySwordsExpectPlatformImpl;

public class SimplySwordsExpectPlatform {
   @ExpectPlatform
   @Transformed
   public static Path getConfigDirectory() {
      return SimplySwordsExpectPlatformImpl.getConfigDirectory();
   }

   @ExpectPlatform
   @Transformed
   public static String getVersion() {
      return SimplySwordsExpectPlatformImpl.getVersion();
   }
}
