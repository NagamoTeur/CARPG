package shadows.apotheosis.adventure.spawner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.mojang.serialization.JsonOps;
import java.lang.reflect.Type;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceLocation.Serializer;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.SpawnData;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.placebo.json.NBTAdapter;
import shadows.placebo.json.WeightedJsonReloadListener;

public class RandomSpawnerManager extends WeightedJsonReloadListener<SpawnerItem> {
   public static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting()
      .registerTypeAdapter((new TypeToken<SimpleWeightedRandomList<SpawnData>>() {
      }).getType(), new RandomSpawnerManager.SpawnDataListAdapter())
      .registerTypeAdapter(ResourceLocation.class, new Serializer())
      .registerTypeAdapter(CompoundTag.class, NBTAdapter.INSTANCE)
      .create();
   public static final RandomSpawnerManager INSTANCE = new RandomSpawnerManager();

   public RandomSpawnerManager() {
      super(AdventureModule.LOGGER, "random_spawners", false, false);
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, SpawnerItem.SERIALIZER);
   }

   private static class SpawnDataListAdapter
      implements JsonDeserializer<SimpleWeightedRandomList<SpawnData>>,
      JsonSerializer<SimpleWeightedRandomList<SpawnData>> {
      public SimpleWeightedRandomList<SpawnData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
         return (SimpleWeightedRandomList<SpawnData>)SpawnData.f_186560_.parse(JsonOps.INSTANCE, json).getOrThrow(false, s -> {
            throw new RuntimeException("Failed to parse SpawnDataList " + s);
         });
      }

      public JsonElement serialize(SimpleWeightedRandomList<SpawnData> src, Type typeOfSrc, JsonSerializationContext context) {
         return (JsonElement)SpawnData.f_186560_.encodeStart(JsonOps.INSTANCE, src).getOrThrow(false, s -> {
            throw new RuntimeException("Failed to encode SpawnListData " + s);
         });
      }
   }
}
