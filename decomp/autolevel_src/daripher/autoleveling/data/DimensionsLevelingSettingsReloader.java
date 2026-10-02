package daripher.autoleveling.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import daripher.autoleveling.config.Config;
import daripher.autoleveling.settings.DimensionLevelingSettings;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.Deserializers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class DimensionsLevelingSettingsReloader extends SimpleJsonResourceReloadListener {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Gson GSON = Deserializers.m_78800_().create();
   private static final Map<ResourceLocation, DimensionLevelingSettings> SETTINGS = new HashMap<>();

   public DimensionsLevelingSettingsReloader() {
      super(GSON, "leveling_settings/dimensions");
   }

   @Nonnull
   public static DimensionLevelingSettings getSettingsForDimension(ResourceKey<Level> dimension) {
      return SETTINGS.getOrDefault(dimension.m_135782_(), createDefaultSettings());
   }

   private static DimensionLevelingSettings createDefaultSettings() {
      return new DimensionLevelingSettings(
         (Integer)Config.COMMON.defaultStartingLevel.get(),
         (Integer)Config.COMMON.defaultMaxLevel.get(),
         ((Double)Config.COMMON.defaultLevelsPerDistance.get()).floatValue(),
         ((Double)Config.COMMON.defaultLevelsPerDeepness.get()).floatValue(),
         (Integer)Config.COMMON.defaultRandomLevelBonus.get(),
         Optional.empty(),
         ((Double)Config.COMMON.defaultLevelsPerDay.get()).floatValue(),
         ((Double)Config.COMMON.defaultLevelPowerPerDistance.get()).floatValue(),
         ((Double)Config.COMMON.defaultLevelPowerPerDeepness.get()).floatValue()
      );
   }

   protected void apply(Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
      SETTINGS.clear();
      map.forEach(this::loadSettings);
   }

   private void loadSettings(ResourceLocation fileId, JsonElement jsonElement) {
      try {
         LOGGER.info("Loading leveling settings {}", fileId);
         DimensionLevelingSettings settings = DimensionLevelingSettings.load(jsonElement.getAsJsonObject());
         SETTINGS.put(fileId, settings);
      } catch (Exception var4) {
         LOGGER.error("Couldn't parse leveling settings {}", fileId, var4);
      }
   }
}
