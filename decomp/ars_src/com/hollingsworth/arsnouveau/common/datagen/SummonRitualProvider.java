package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.api.recipe.SummonRitualRecipe;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

public class SummonRitualProvider implements DataProvider {
   public List<SummonRitualRecipe> recipes = new ArrayList<>();
   public final DataGenerator generator;

   public SummonRitualProvider(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      this.addEntries();
      Path output = this.generator.m_123916_();

      for (SummonRitualRecipe recipe : this.recipes) {
         Path path = getRecipePath(output, recipe.m_6423_().m_135815_());
         DataProvider.m_236072_(cache, recipe.asRecipe(), path);
      }
   }

   protected void addEntries() {
   }

   protected static Path getRecipePath(Path path, String id) {
      return path.resolve("data/ars_nouveau/recipes/summon_ritual/" + id + ".json");
   }

   public String m_6055_() {
      return "Summon Ritual Datagen";
   }
}
