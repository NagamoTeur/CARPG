package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class RelationsPage extends AbstractPage {
   List<String> entries = new ArrayList<>();

   public RelationsPage withEntry(ResourceLocation category, String fileName) {
      return this.withEntry(category.toString() + "/" + fileName);
   }

   public RelationsPage withEntry(String path) {
      this.entries.add(path);
      return this;
   }

   public RelationsPage withEntry(PatchouliProvider.PatchouliPage page) {
      return this.withEntry(page.relationPath());
   }

   public RelationsPage withEntries(List<PatchouliProvider.PatchouliPage> pages) {
      for (PatchouliProvider.PatchouliPage page : pages) {
         this.withEntry(page);
      }

      return this;
   }

   public RelationsPage withTitle(String title) {
      this.object.addProperty("title", title);
      return this;
   }

   public RelationsPage withText(String text) {
      this.object.addProperty("text", text);
      return this;
   }

   @Override
   public JsonObject build() {
      JsonArray array = new JsonArray();

      for (String s : this.entries) {
         array.add(s);
      }

      this.object.add("entries", array);
      return super.build();
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("patchouli:relations");
   }
}
