package shadows.apotheosis.adventure.loot;

import com.google.common.base.Predicates;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.placebo.json.ListenerCallback;

public class AffixLootPoolEntry extends LootPoolSingletonContainer {
   public static final AffixLootPoolEntry.Serializer SERIALIZER = new AffixLootPoolEntry.Serializer();
   public static final LootPoolEntryType TYPE = new LootPoolEntryType(SERIALIZER);
   private static Set<AffixLootPoolEntry> awaitingLoad = Collections.newSetFromMap(new WeakHashMap<>());
   @Nullable
   private final LootRarity.Clamped rarity;
   private final List<ResourceLocation> entries;
   private List<AffixLootEntry> resolvedEntries = Collections.emptyList();

   public AffixLootPoolEntry(
      @Nullable LootRarity.Clamped rarity,
      List<ResourceLocation> entries,
      int weight,
      int quality,
      LootItemCondition[] conditions,
      LootItemFunction[] functions
   ) {
      super(weight, quality, conditions, functions);
      this.rarity = rarity;
      this.entries = entries;
      if (!this.entries.isEmpty()) {
         awaitingLoad.add(this);
      }
   }

   protected void m_6948_(Consumer<ItemStack> list, LootContext ctx) {
      ItemStack stack;
      if (this.resolvedEntries.isEmpty()) {
         Player player = GemLootPoolEntry.findPlayer(ctx);
         if (player == null) {
            return;
         }

         LootRarity selectedRarity = LootRarity.random(ctx.m_230907_(), ctx.m_78945_(), this.rarity);
         stack = LootController.createRandomLootItem(ctx.m_230907_(), selectedRarity, player, ctx.m_78952_());
      } else {
         AffixLootEntry entry = (AffixLootEntry)((Wrapper)WeightedRandom.m_216822_(
                  ctx.m_230907_(), this.resolvedEntries.stream().map(e -> e.wrap(ctx.m_78945_())).toList()
               )
               .get())
            .m_146310_();
         LootRarity selectedRarity = LootRarity.random(ctx.m_230907_(), ctx.m_78945_(), (LootRarity.Clamped)(this.rarity == null ? entry : this.rarity));
         stack = LootController.createLootItem(entry.getStack().m_41777_(), selectedRarity, ctx.m_230907_());
      }

      if (!stack.m_41619_()) {
         list.accept(stack);
      }
   }

   public LootPoolEntryType m_6751_() {
      return TYPE;
   }

   private void resolve() {
      this.resolvedEntries = this.entries
         .stream()
         .map(id -> this.printErrorOnNull((AffixLootEntry)AffixLootManager.INSTANCE.getValue(id), id))
         .filter(Predicates.notNull())
         .toList();
   }

   private <T> T printErrorOnNull(T t, ResourceLocation id) {
      if (t == null) {
         AdventureModule.LOGGER.error("An AffixLootPoolEntry failed to resolve the Affix Entry {}!", id);
      }

      return t;
   }

   static {
      AffixLootManager.INSTANCE.registerCallback(ListenerCallback.reloadOnly(r -> awaitingLoad.forEach(AffixLootPoolEntry::resolve)));
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Serializer<AffixLootPoolEntry> {
      protected AffixLootPoolEntry deserialize(
         JsonObject obj, JsonDeserializationContext context, int weight, int quality, LootItemCondition[] lootConditions, LootItemFunction[] lootFunctions
      ) {
         LootRarity.Clamped rarity;
         if (obj.has("rarity")) {
            LootRarity lRarity = LootRarity.byId(GsonHelper.m_13906_(obj, "rarity"));
            rarity = new LootRarity.Clamped.Impl(lRarity, lRarity);
            AdventureModule.LOGGER.error("Use of the \"rarity\" key in affix loot pool entries is deprecated and will be removed in a future release.");
         } else if (!obj.has("min_rarity") && !obj.has("max_rarity")) {
            rarity = null;
         } else {
            LootRarity minRarity = LootRarity.byId(GsonHelper.m_13851_(obj, "min_rarity", "common"));
            LootRarity maxRarity = LootRarity.byId(GsonHelper.m_13851_(obj, "max_rarity", "mythic"));
            rarity = new LootRarity.Clamped.Impl(minRarity, maxRarity);
         }

         List<String> entries = (List<String>)context.deserialize(GsonHelper.m_13832_(obj, "entries", new JsonArray()), (new TypeToken<List<String>>() {
         }).getType());
         return new AffixLootPoolEntry(
            rarity, entries.stream().<ResourceLocation>map(ResourceLocation::new).toList(), weight, quality, lootConditions, lootFunctions
         );
      }

      public void serializeCustom(JsonObject object, AffixLootPoolEntry e, JsonSerializationContext ctx) {
         if (e.rarity != null) {
            object.addProperty("min_rarity", e.rarity.getMinRarity().id());
            object.addProperty("max_rarity", e.rarity.getMaxRarity().id());
         }

         object.add("entries", ctx.serialize(e.entries));
         super.m_7219_(object, e, ctx);
      }
   }
}
