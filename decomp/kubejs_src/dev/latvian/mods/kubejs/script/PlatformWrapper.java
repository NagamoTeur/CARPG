package dev.latvian.mods.kubejs.script;

import architectury_inject_KubeJS119_common_4d26140b97084d8591e25283103954b3_9c8a2dda6cea3eeb53b5c382faf702ec57e75334878c62b4a85ff37d029699f1kubejs190262build73devjar.PlatformMethods;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.platform.MiscPlatformHelper;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlatformWrapper {
   private static Map<String, PlatformWrapper.ModInfo> allMods;

   public static String getName() {
      if (isDevelopmentEnvironment()) {
         if (isForge()) {
            return "forge";
         } else {
            return isFabric() ? "fabric" : "unknown (userdev?)";
         }
      } else {
         return PlatformMethods.getCurrentTarget();
      }
   }

   public static boolean isForge() {
      return Platform.isForge();
   }

   public static boolean isFabric() {
      return Platform.isFabric();
   }

   public static String getMcVersion() {
      return "1.19.2";
   }

   public static Set<String> getList() {
      return getMods().keySet();
   }

   public static String getModVersion() {
      return KubeJS.thisMod.getVersion();
   }

   public static boolean isLoaded(String modId) {
      return getMods().containsKey(modId);
   }

   public static PlatformWrapper.ModInfo getInfo(String modID) {
      return getMods().computeIfAbsent(modID, PlatformWrapper.ModInfo::new);
   }

   public static Map<String, PlatformWrapper.ModInfo> getMods() {
      if (allMods == null) {
         allMods = new LinkedHashMap<>();

         for (Mod mod : Platform.getMods()) {
            PlatformWrapper.ModInfo info = new PlatformWrapper.ModInfo(mod.getModId());
            info.name = mod.getName();
            info.version = mod.getVersion();
            allMods.put(info.id, info);
         }
      }

      return allMods;
   }

   public static boolean isDevelopmentEnvironment() {
      return Platform.isDevelopmentEnvironment();
   }

   public static boolean isClientEnvironment() {
      return Platform.getEnvironment() == Env.CLIENT;
   }

   public static void setModName(String modId, String name) {
      getInfo(modId).setName(name);
   }

   public static int getMinecraftVersion() {
      return 1902;
   }

   public static String getMinecraftVersionString() {
      return "1.19.2";
   }

   public static boolean isGeneratingData() {
      return MiscPlatformHelper.get().isDataGen();
   }

   public static void breakpoint(Object... args) {
      KubeJS.LOGGER.info(Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(", ")));
   }

   public static class ModInfo {
      private final String id;
      private String name;
      private String version;
      private String customName;

      public ModInfo(String i) {
         this.id = i;
         this.name = this.id;
         this.version = "0.0.0";
         this.customName = "";
      }

      public String getId() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String n) {
         this.name = n;
         this.customName = n;
         MiscPlatformHelper.get().setModName(this, this.name);
      }

      public String getVersion() {
         return this.version;
      }

      public String getCustomName() {
         return this.customName;
      }
   }
}
