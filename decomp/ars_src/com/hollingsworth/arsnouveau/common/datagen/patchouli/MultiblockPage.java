package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class MultiblockPage extends AbstractPage {
   List<MultiblockPage.Mapping> mappings = new ArrayList<>();
   String[][] pattern;

   public MultiblockPage(String name, String[][] pattern) {
      this.object.addProperty("name", name);
      this.pattern = pattern;
   }

   public MultiblockPage withText(String text) {
      this.object.addProperty("text", text);
      return this;
   }

   public MultiblockPage withMapping(String letter, String object) {
      this.mappings.add(new MultiblockPage.Mapping(letter, object));
      return this;
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("patchouli:multiblock");
   }

   @Override
   public JsonObject build() {
      JsonObject multiblock = new JsonObject();
      JsonArray pattern = new JsonArray();

      for (String[] obj : this.pattern) {
         JsonArray array = new JsonArray();

         for (String s : obj) {
            array.add(s);
         }

         pattern.add(array);
      }

      multiblock.add("pattern", pattern);
      JsonObject mapping = new JsonObject();

      for (MultiblockPage.Mapping m : this.mappings) {
         mapping.addProperty(m.letter, m.object);
      }

      multiblock.add("mapping", mapping);
      this.object.add("multiblock", multiblock);
      return super.build();
   }

   public static record Mapping(String letter, String object) {
   }
}
