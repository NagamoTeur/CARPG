package immersive_armors.config;

import immersive_armors.config.configEntries.FloatConfigEntry;
import immersive_armors.config.configEntries.IntegerConfigEntry;
import java.util.HashMap;
import java.util.Map;

public final class Config extends JsonConfig {
   private static final Config INSTANCE = loadOrCreate();
   public boolean hideSecondLayerUnderArmor = true;
   public boolean enableEffects = true;
   public boolean enableEnchantmentGlint = true;
   public String _documentation = "https://github.com/Luke100000/ImmersiveArmors/blob/HEAD/config.md";
   @IntegerConfigEntry(-111)
   public int hudClockX;
   @IntegerConfigEntry(-140)
   public int hudClockXOffhand;
   @IntegerConfigEntry(-20)
   public int hudClockY;
   @IntegerConfigEntry(95)
   public int hudCompassX;
   @IntegerConfigEntry(124)
   public int hudCompassXOffhand;
   @IntegerConfigEntry(-20)
   public int hudCompassY;
   @FloatConfigEntry(0.025F)
   public float lootChance;
   @FloatConfigEntry(0.5F)
   public float mobEntityUseImmersiveArmorChance;
   public Map<String, Float> overwriteValues = new HashMap<>();
   public HashMap<String, Boolean> enabledArmors = new HashMap<>();

   public static Config getInstance() {
      return INSTANCE;
   }
}
