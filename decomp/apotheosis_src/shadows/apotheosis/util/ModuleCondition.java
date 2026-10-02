package shadows.apotheosis.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;
import shadows.apotheosis.Apotheosis;

public class ModuleCondition implements ICondition {
   static ResourceLocation id = new ResourceLocation("apotheosis", "module");
   static Map<String, Supplier<Boolean>> types = new HashMap<>();
   final String name;

   public ModuleCondition(String name) {
      this.name = name;
   }

   public ResourceLocation getID() {
      return id;
   }

   public boolean test(IContext context) {
      return types.get(this.name).get();
   }

   static {
      types.put("spawner", () -> Apotheosis.enableSpawner);
      types.put("garden", () -> Apotheosis.enableGarden);
      types.put("deadly", () -> Apotheosis.enableAdventure);
      types.put("adventure", () -> Apotheosis.enableAdventure);
      types.put("enchantment", () -> Apotheosis.enableEnch);
      types.put("potion", () -> Apotheosis.enablePotion);
      types.put("village", () -> Apotheosis.enableVillage);
      types.put("book", () -> Apotheosis.giveBook);
   }

   public static class Serializer implements IConditionSerializer<ModuleCondition> {
      public void write(JsonObject json, ModuleCondition value) {
         json.addProperty("field", value.name);
      }

      public ModuleCondition read(JsonObject json) {
         if (json.has("module") && ModuleCondition.types.containsKey(json.get("module").getAsString())) {
            return new ModuleCondition(json.get("module").getAsString());
         } else {
            throw new JsonParseException("Invalid module condition!");
         }
      }

      public ResourceLocation getID() {
         return ModuleCondition.id;
      }
   }
}
