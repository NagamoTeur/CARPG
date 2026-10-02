package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.google.gson.JsonObject;

public abstract class AbstractPage implements IPatchouliPage {
   JsonObject object = new JsonObject();

   @Override
   public JsonObject build() {
      this.object.addProperty("type", this.getType().toString());
      return this.object;
   }
}
