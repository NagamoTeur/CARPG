package com.hollingsworth.arsnouveau.setup.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import java.nio.file.Path;
import java.util.function.Function;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.ConfigFileTypeHandler;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;

public class ANModConfig extends ModConfig {
   private static final ANModConfig.ANConfigFileTypeHandler AN_TOML = new ANModConfig.ANConfigFileTypeHandler();

   public ANModConfig(Type type, IConfigSpec<?> iConfigSpec, ModContainer container, String fileName) {
      super(type, iConfigSpec, container, fileName + ".toml");
   }

   public ConfigFileTypeHandler getHandler() {
      return AN_TOML;
   }

   private static class ANConfigFileTypeHandler extends ConfigFileTypeHandler {
      private static Path getPath(Path configBasePath) {
         return configBasePath.endsWith("serverconfig") ? FMLPaths.CONFIGDIR.get() : configBasePath;
      }

      public Function<ModConfig, CommentedFileConfig> reader(Path configBasePath) {
         return super.reader(getPath(configBasePath));
      }

      public void unload(Path configBasePath, ModConfig config) {
         super.unload(getPath(configBasePath), config);
      }
   }
}
