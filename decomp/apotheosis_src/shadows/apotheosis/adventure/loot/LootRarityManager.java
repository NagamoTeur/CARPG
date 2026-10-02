package shadows.apotheosis.adventure.loot;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixManager;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.placebo.json.DynamicRegistryObject;
import shadows.placebo.json.PlaceboJsonReloadListener;

public class LootRarityManager extends PlaceboJsonReloadListener<LootRarity.RarityStub> {
   public static final LootRarityManager INSTANCE = new LootRarityManager();
   public static final DynamicRegistryObject<LootRarity.RarityStub> COMMON = INSTANCE.makeObj(Apotheosis.loc("common"));
   public static final DynamicRegistryObject<LootRarity.RarityStub> UNCOMMON = INSTANCE.makeObj(Apotheosis.loc("uncommon"));
   public static final DynamicRegistryObject<LootRarity.RarityStub> RARE = INSTANCE.makeObj(Apotheosis.loc("rare"));
   public static final DynamicRegistryObject<LootRarity.RarityStub> EPIC = INSTANCE.makeObj(Apotheosis.loc("epic"));
   public static final DynamicRegistryObject<LootRarity.RarityStub> MYTHIC = INSTANCE.makeObj(Apotheosis.loc("mythic"));
   public static final DynamicRegistryObject<LootRarity.RarityStub> ANCIENT = INSTANCE.makeObj(Apotheosis.loc("ancient"));
   protected Map<String, LootRarity> byId = new HashMap<>();
   protected List<LootRarity> list = new ArrayList<>(6);

   private LootRarityManager() {
      super(AdventureModule.LOGGER, "rarities", true, false);
   }

   protected void onReload() {
      super.onReload();
      Preconditions.checkArgument(COMMON.get() != null, "Common rarity not registered!");
      Preconditions.checkArgument(UNCOMMON.get() != null, "Uncommon rarity not registered!");
      Preconditions.checkArgument(RARE.get() != null, "Rare rarity not registered!");
      Preconditions.checkArgument(EPIC.get() != null, "Epic rarity not registered!");
      Preconditions.checkArgument(MYTHIC.get() != null, "Mythic rarity not registered!");
      Preconditions.checkArgument(ANCIENT.get() != null, "Ancient rarity not registered!");
      Preconditions.checkArgument(this.registry.size() == 6, "Registration of additional rarity levels is not supported!");
      Preconditions.checkArgument(
         this.registry.values().stream().mapToInt(LootRarity.RarityStub::getWeight).sum() > 0, "The total weight of all rarities must be above 0"
      );
      LootRarity.COMMON.update((LootRarity.RarityStub)COMMON.get());
      LootRarity.UNCOMMON.update((LootRarity.RarityStub)UNCOMMON.get());
      LootRarity.RARE.update((LootRarity.RarityStub)RARE.get());
      LootRarity.EPIC.update((LootRarity.RarityStub)EPIC.get());
      LootRarity.MYTHIC.update((LootRarity.RarityStub)MYTHIC.get());

      for (LootRarity rarity : LootRarity.values()) {
         if (rarity != LootRarity.ANCIENT) {
            Map<AffixType, List<LootRarity.LootRule>> sorted = new HashMap<>();
            rarity.rules().stream().filter(r -> r.type().needsValidation()).forEach(rule -> {
               sorted.computeIfAbsent(rule.type(), r -> new ArrayList<>());
               sorted.get(rule.type()).add(rule);
            });
            sorted.forEach(
               (type, rules) -> {
                  for (LootCategory cat : LootCategory.VALUES) {
                     if (!cat.isNone()) {
                        List<Affix> affixes = AffixManager.INSTANCE
                           .getValues()
                           .stream()
                           .filter(a -> a.canApplyTo(ItemStack.f_41583_, cat, rarity) && a.getType() == type)
                           .toList();
                        if (affixes.size() < rules.size()) {
                           StringBuilder errMsg = new StringBuilder();
                           errMsg.append(
                              "Insufficient number of affixes to satisfy the loot rules (ignoring backup rules) of rarity "
                                 + rarity.id()
                                 + " for category "
                                 + cat.getName()
                           );
                           errMsg.append("Required: " + rules.size());
                           errMsg.append("; Provided: " + affixes.size());
                           AdventureModule.LOGGER.error(errMsg.toString());
                        }
                     }
                  }
               }
            );
         }
      }
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, LootRarity.RarityStub.SERIALIZER);
   }

   protected void validateItem(LootRarity.RarityStub item) {
      super.validateItem(item);
      Preconditions.checkArgument(item.getWeight() >= 0, "A rarity may not have negative weight!");
      Preconditions.checkArgument(item.getQuality() >= 0.0F, "A rarity may not have negative quality!");
      Preconditions.checkArgument(!item.rules().isEmpty(), "A rarity may not have no rules!");
   }
}
