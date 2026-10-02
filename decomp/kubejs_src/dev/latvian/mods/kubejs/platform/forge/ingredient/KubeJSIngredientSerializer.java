package dev.latvian.mods.kubejs.platform.forge.ingredient;

import com.google.gson.JsonObject;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public record KubeJSIngredientSerializer<T extends KubeJSIngredient>(Function<JsonObject, T> fromJson, Function<FriendlyByteBuf, T> fromNet)
   implements IIngredientSerializer<T> {
   public T parse(JsonObject json) {
      return this.fromJson.apply(json);
   }

   public T parse(FriendlyByteBuf buf) {
      return this.fromNet.apply(buf);
   }

   public void write(FriendlyByteBuf buf, T ingredient) {
      ingredient.write(buf);
   }
}
