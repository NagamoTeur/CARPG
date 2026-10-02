package dev.latvian.mods.kubejs.client;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public class GenerateClientAssetsEventJS extends EventJS {
   public final AssetJsonGenerator generator;

   public GenerateClientAssetsEventJS(AssetJsonGenerator gen) {
      this.generator = gen;
   }

   public void addLang(String key, String value) {
      ConsoleJS.CLIENT.error("Use ClientEvents.lang('en_us', event => { event.add(key, value) }) instead!");
   }

   public void add(ResourceLocation location, JsonElement json) {
      this.generator.json(location, json);
   }

   public void addModel(String type, ResourceLocation id, Consumer<ModelGenerator> consumer) {
      ModelGenerator gen = (ModelGenerator)Util.m_137469_(new ModelGenerator(), consumer);
      this.add(new ResourceLocation(id.m_135827_(), "models/%s/%s".formatted(type, id.m_135815_())), gen.toJson());
   }

   public void addBlockState(ResourceLocation id, Consumer<VariantBlockStateGenerator> consumer) {
      VariantBlockStateGenerator gen = (VariantBlockStateGenerator)Util.m_137469_(new VariantBlockStateGenerator(), consumer);
      this.add(new ResourceLocation(id.m_135827_(), "blockstates/" + id.m_135815_()), gen.toJson());
   }

   public void addMultipartBlockState(ResourceLocation id, Consumer<MultipartBlockStateGenerator> consumer) {
      MultipartBlockStateGenerator gen = (MultipartBlockStateGenerator)Util.m_137469_(new MultipartBlockStateGenerator(), consumer);
      this.add(new ResourceLocation(id.m_135827_(), "blockstates/" + id.m_135815_()), gen.toJson());
   }
}
