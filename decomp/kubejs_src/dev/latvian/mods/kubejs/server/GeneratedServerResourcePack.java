package dev.latvian.mods.kubejs.server;

import dev.latvian.mods.kubejs.generator.DataJsonGenerator;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.data.GeneratedData;
import dev.latvian.mods.kubejs.script.data.GeneratedResourcePack;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public class GeneratedServerResourcePack extends GeneratedResourcePack {
   public GeneratedServerResourcePack() {
      super(PackType.SERVER_DATA);
   }

   @Override
   public void generate(Map<ResourceLocation, GeneratedData> map) {
      DataJsonGenerator generator = new DataJsonGenerator(map);

      for (BuilderBase<?> builder : RegistryInfo.ALL_BUILDERS) {
         builder.generateDataJsons(generator);
      }

      KubeJSPlugins.forEachPlugin(p -> p.generateDataJsons(generator));
   }
}
