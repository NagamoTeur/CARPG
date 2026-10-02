package immersive_armors;

import immersive_armors.config.Config;
import immersive_armors.network.NetworkManager;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public final class Main {
   public static final String SHORT_MOD_ID = "ic_ia";
   public static final String MOD_ID = "immersive_armors";
   public static NetworkManager networkManager;
   public static boolean FORGE = false;
   public static Config sharedConfig = Config.getInstance();
   private static Map<String, Float> backup = new HashMap<>();

   public static void setSharedConfig(Config config) {
      sharedConfig = config;
      ItemPropertyOverwrite.applyItemOverwrite(backup);
      backup = ItemPropertyOverwrite.applyItemOverwrite(config.overwriteValues);
   }

   public static ResourceLocation locate(String path) {
      return new ResourceLocation("immersive_armors", path);
   }
}
