package shadows.apotheosis.adventure.loot;

import com.google.common.base.Preconditions;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.placebo.json.WeightedJsonReloadListener;

public class AffixLootManager extends WeightedJsonReloadListener<AffixLootEntry> {
   public static final AffixLootManager INSTANCE = new AffixLootManager();

   private AffixLootManager() {
      super(AdventureModule.LOGGER, "affix_loot_entries", false, false);
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, AffixLootEntry.SERIALIZER);
   }

   protected void validateItem(AffixLootEntry item) {
      super.validateItem(item);
      Preconditions.checkArgument(!item.stack.m_41619_());
      Preconditions.checkArgument(!item.getType().isNone());
   }
}
