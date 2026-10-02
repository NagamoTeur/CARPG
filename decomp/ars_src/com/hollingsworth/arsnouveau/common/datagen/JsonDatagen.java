package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.JsonElement;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.common.crafting.recipes.DyeRecipe;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.ItemLike;

public class JsonDatagen implements DataProvider {
   List<JsonDatagen.FileObj> files = new ArrayList<>();
   protected final DataGenerator generator;

   public JsonDatagen(DataGenerator generatorIn) {
      this.generator = generatorIn;
      this.addDyeRecipe(ItemsRegistry.APPRENTICE_SPELLBOOK.m_5456_());
      this.addDyeRecipe(ItemsRegistry.NOVICE_SPELLBOOK.m_5456_());
      this.addDyeRecipe(ItemsRegistry.ARCHMAGE_SPELLBOOK.m_5456_());
      this.addDyeRecipe(ItemsRegistry.NOVICE_BOOTS);
      this.addDyeRecipe(ItemsRegistry.NOVICE_LEGGINGS);
      this.addDyeRecipe(ItemsRegistry.NOVICE_ROBES);
      this.addDyeRecipe(ItemsRegistry.NOVICE_HOOD);
      this.addDyeRecipe(ItemsRegistry.APPRENTICE_BOOTS);
      this.addDyeRecipe(ItemsRegistry.APPRENTICE_LEGGINGS);
      this.addDyeRecipe(ItemsRegistry.APPRENTICE_ROBES);
      this.addDyeRecipe(ItemsRegistry.APPRENTICE_HOOD);
      this.addDyeRecipe(ItemsRegistry.ARCHMAGE_BOOTS);
      this.addDyeRecipe(ItemsRegistry.ARCHMAGE_LEGGINGS);
      this.addDyeRecipe(ItemsRegistry.ARCHMAGE_ROBES);
      this.addDyeRecipe(ItemsRegistry.ARCHMAGE_HOOD);
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      for (JsonDatagen.FileObj fileObj : this.files) {
         DataProvider.m_236072_(cache, fileObj.element, fileObj.path);
      }
   }

   public void add(JsonDatagen.FileObj fileObj) {
      this.files.add(fileObj);
   }

   public void addDyeRecipe(ItemLike inputItem) {
      JsonElement dyeRecipe = DyeRecipe.asRecipe(inputItem.m_5456_());
      this.add(
         new JsonDatagen.FileObj(
            this.generator.m_123916_().resolve("data/ars_nouveau/recipes/dye_" + RegistryHelper.getRegistryName(inputItem.m_5456_()).m_135815_() + ".json"),
            dyeRecipe
         )
      );
   }

   public String m_6055_() {
      return "ArsNouveau: Json Datagen";
   }

   public static record FileObj(Path path, JsonElement element) {
   }
}
