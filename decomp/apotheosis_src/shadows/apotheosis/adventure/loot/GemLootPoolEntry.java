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
import java.util.function.Predicate;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.placebo.json.ListenerCallback;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class GemLootPoolEntry extends LootPoolSingletonContainer {
   public static final GemLootPoolEntry.Serializer SERIALIZER = new GemLootPoolEntry.Serializer();
   public static final LootPoolEntryType TYPE = new LootPoolEntryType(SERIALIZER);
   private static Set<GemLootPoolEntry> awaitingLoad = Collections.newSetFromMap(new WeakHashMap<>());
   private final List<ResourceLocation> gems;
   private List<Gem> resolvedGems = Collections.emptyList();

   public GemLootPoolEntry(List<ResourceLocation> gems, int weight, int quality, LootItemCondition[] conditions, LootItemFunction[] functions) {
      super(weight, quality, conditions, functions);
      this.gems = gems;
      if (!this.gems.isEmpty()) {
         awaitingLoad.add(this);
      }
   }

   protected void m_6948_(Consumer<ItemStack> list, LootContext ctx) {
      Gem gem;
      if (!this.resolvedGems.isEmpty()) {
         gem = (Gem)((Wrapper)WeightedRandom.m_216822_(ctx.m_230907_(), this.resolvedGems.stream().map(g -> g.wrap(ctx.m_78945_())).toList()).get())
            .m_146310_();
      } else {
         Player player = findPlayer(ctx);
         if (player == null) {
            return;
         }

         gem = (Gem)GemManager.INSTANCE
            .getRandomItem(ctx.m_230907_(), ctx.m_78945_(), new Predicate[]{IDimensional.matches(ctx.m_78952_()), GameStagesCompat.IStaged.matches(player)});
      }

      LootRarity.Clamped clamp = AdventureConfig.GEM_DIM_RARITIES.get(ctx.m_78952_().m_46472_().m_135782_());
      ItemStack stack = GemManager.createGemStack(gem, gem.clamp(LootRarity.random(ctx.m_230907_(), ctx.m_78945_(), clamp)));
      list.accept(stack);
   }

   public LootPoolEntryType m_6751_() {
      return TYPE;
   }

   private void resolve() {
      this.resolvedGems = this.gems.stream().map(id -> this.printErrorOnNull((Gem)GemManager.INSTANCE.getValue(id), id)).filter(Predicates.notNull()).toList();
   }

   private <T> T printErrorOnNull(T t, ResourceLocation id) {
      if (t == null) {
         AdventureModule.LOGGER.error("A GemLootPoolEntry failed to resolve the Gem {}!", id);
      }

      return t;
   }

   @Nullable
   public static Player findPlayer(LootContext ctx) {
      Object var2 = ctx.m_78953_(LootContextParams.f_81455_);
      if (var2 instanceof Player) {
         return (Player)var2;
      } else {
         var2 = ctx.m_78953_(LootContextParams.f_81459_);
         if (var2 instanceof Player) {
            return (Player)var2;
         } else {
            var2 = ctx.m_78953_(LootContextParams.f_81458_);
            if (var2 instanceof Player) {
               return (Player)var2;
            } else {
               return ctx.m_78953_(LootContextParams.f_81456_) != null ? (Player)ctx.m_78953_(LootContextParams.f_81456_) : null;
            }
         }
      }
   }

   static {
      GemManager.INSTANCE.registerCallback(ListenerCallback.reloadOnly(r -> awaitingLoad.forEach(GemLootPoolEntry::resolve)));
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Serializer<GemLootPoolEntry> {
      protected GemLootPoolEntry deserialize(
         JsonObject jsonObject,
         JsonDeserializationContext context,
         int weight,
         int quality,
         LootItemCondition[] lootConditions,
         LootItemFunction[] lootFunctions
      ) {
         List<String> gems = (List<String>)context.deserialize(GsonHelper.m_13832_(jsonObject, "gems", new JsonArray()), (new TypeToken<List<String>>() {
         }).getType());
         return new GemLootPoolEntry(gems.stream().<ResourceLocation>map(ResourceLocation::new).toList(), weight, quality, lootConditions, lootFunctions);
      }

      public void serializeCustom(JsonObject object, GemLootPoolEntry e, JsonSerializationContext ctx) {
         object.add("gems", ctx.serialize(e.gems));
         super.m_7219_(object, e, ctx);
      }
   }
}
