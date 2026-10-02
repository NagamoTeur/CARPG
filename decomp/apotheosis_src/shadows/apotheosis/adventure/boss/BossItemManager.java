package shadows.apotheosis.adventure.boss;

import com.google.gson.JsonElement;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.placebo.json.WeightedJsonReloadListener;

public class BossItemManager extends WeightedJsonReloadListener<BossItem> {
   public static final BossItemManager INSTANCE = new BossItemManager();

   public BossItemManager() {
      super(AdventureModule.LOGGER, "bosses", false, false);
   }

   protected Map<ResourceLocation, JsonElement> m_5944_(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
      Map<ResourceLocation, JsonElement> map = super.m_5944_(pResourceManager, pProfiler);
      map.keySet().removeIf(r -> "brutalbosses".equals(r.m_135827_()));
      return map;
   }

   protected void validateItem(BossItem item) {
      super.validateItem(item);
      item.validate();
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, BossItem.SERIALIZER);
   }
}
