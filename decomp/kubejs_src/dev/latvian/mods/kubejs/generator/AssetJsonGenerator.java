package dev.latvian.mods.kubejs.generator;

import dev.latvian.mods.kubejs.client.ModelGenerator;
import dev.latvian.mods.kubejs.client.MultipartBlockStateGenerator;
import dev.latvian.mods.kubejs.client.VariantBlockStateGenerator;
import dev.latvian.mods.kubejs.script.data.GeneratedData;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public class AssetJsonGenerator extends JsonGenerator {
   public AssetJsonGenerator(Map<ResourceLocation, GeneratedData> m) {
      super(ConsoleJS.CLIENT, m);
   }

   public void blockState(ResourceLocation id, Consumer<VariantBlockStateGenerator> consumer) {
      VariantBlockStateGenerator gen = (VariantBlockStateGenerator)Util.m_137469_(new VariantBlockStateGenerator(), consumer);
      this.json(new ResourceLocation(id.m_135827_(), "blockstates/" + id.m_135815_()), gen.toJson());
   }

   public void multipartState(ResourceLocation id, Consumer<MultipartBlockStateGenerator> consumer) {
      MultipartBlockStateGenerator gen = (MultipartBlockStateGenerator)Util.m_137469_(new MultipartBlockStateGenerator(), consumer);
      this.json(new ResourceLocation(id.m_135827_(), "blockstates/" + id.m_135815_()), gen.toJson());
   }

   public void blockModel(ResourceLocation id, Consumer<ModelGenerator> consumer) {
      ModelGenerator gen = (ModelGenerator)Util.m_137469_(new ModelGenerator(), consumer);
      this.json(new ResourceLocation(id.m_135827_(), "models/block/" + id.m_135815_()), gen.toJson());
   }

   public void itemModel(ResourceLocation id, Consumer<ModelGenerator> consumer) {
      ModelGenerator gen = (ModelGenerator)Util.m_137469_(new ModelGenerator(), consumer);
      this.json(asItemModelLocation(id), gen.toJson());
   }

   public static ResourceLocation asItemModelLocation(ResourceLocation id) {
      return new ResourceLocation(id.m_135827_(), "models/item/" + id.m_135815_());
   }
}
