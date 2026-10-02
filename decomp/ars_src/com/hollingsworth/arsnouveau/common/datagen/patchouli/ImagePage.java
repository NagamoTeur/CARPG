package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class ImagePage extends AbstractPage {
   List<String> images = new ArrayList<>();

   public ImagePage withEntry(ResourceLocation file) {
      this.images.add(file.toString());
      return this;
   }

   public ImagePage withTitle(String title) {
      this.object.addProperty("title", title);
      return this;
   }

   public ImagePage withText(String text) {
      this.object.addProperty("text", text);
      return this;
   }

   public ImagePage withBorder() {
      this.object.addProperty("border", true);
      return this;
   }

   @Override
   public JsonObject build() {
      JsonArray array = new JsonArray();

      for (String s : this.images) {
         array.add(s);
      }

      this.object.add("images", array);
      return super.build();
   }

   @Override
   public ResourceLocation getType() {
      return new ResourceLocation("patchouli:image");
   }
}
