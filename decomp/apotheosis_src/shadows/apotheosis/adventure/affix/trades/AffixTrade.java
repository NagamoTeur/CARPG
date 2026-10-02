package shadows.apotheosis.adventure.affix.trades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ServerLevelAccessor;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.loot.AffixLootEntry;
import shadows.apotheosis.adventure.loot.AffixLootManager;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.apotheosis.village.wanderer.JsonTrade;
import shadows.placebo.json.ItemAdapter;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;

public class AffixTrade extends TypeKeyedBase<JsonTrade> implements JsonTrade {
   public static final Codec<AffixTrade> CODEC = ExtraCodecs.m_184415_(
      () -> RecordCodecBuilder.create(
            inst -> inst.group(
                     ItemAdapter.CODEC.fieldOf("input_1").forGetter(a -> a.price),
                     ItemAdapter.CODEC.optionalFieldOf("input_2", ItemStack.f_41583_).forGetter(a -> a.price2),
                     LootRarity.Clamped.Impl.CODEC.fieldOf("rarities").forGetter(a -> a.rarities),
                     ResourceLocation.f_135803_.listOf().fieldOf("entries").forGetter(a -> a.entries),
                     Codec.BOOL.optionalFieldOf("rare", false).forGetter(a -> a.rare)
                  )
                  .apply(inst, AffixTrade::new)
         )
   );
   public static final PSerializer<AffixTrade> SERIALIZER = PSerializer.fromCodec("Affix Trade", CODEC);
   protected final ItemStack price;
   protected final ItemStack price2;
   protected final LootRarity.Clamped.Impl rarities;
   protected final List<ResourceLocation> entries;
   protected final boolean rare;

   public AffixTrade(ItemStack price, ItemStack price2, LootRarity.Clamped.Impl rarities, List<ResourceLocation> entries, boolean rare) {
      this.price = price;
      this.price2 = price2;
      this.rarities = rarities;
      this.entries = entries;
      this.rare = rare;
   }

   @Nullable
   public MerchantOffer m_213663_(Entity trader, RandomSource rand) {
      if (trader.f_19853_.f_46443_) {
         return null;
      } else {
         Player player = trader.f_19853_.m_45930_(trader, -1.0);
         if (player == null) {
            return null;
         } else {
            ItemStack affixItem;
            if (this.entries.isEmpty()) {
               LootRarity selectedRarity = LootRarity.random(rand, player.m_36336_(), this.rarities);
               affixItem = LootController.createRandomLootItem(rand, selectedRarity, player, (ServerLevelAccessor)trader.f_19853_);
            } else {
               List<Wrapper<AffixLootEntry>> resolved = this.entries
                  .stream()
                  .map(this::unwrap)
                  .filter(Objects::nonNull)
                  .map(e -> e.wrap(player.m_36336_()))
                  .toList();
               AffixLootEntry entry = (AffixLootEntry)((Wrapper)WeightedRandom.m_216822_(rand, resolved).get()).m_146310_();
               LootRarity selectedRarity = LootRarity.random(rand, player.m_36336_(), this.rarities);
               affixItem = LootController.createLootItem(entry.getStack().m_41777_(), selectedRarity, rand);
            }

            if (affixItem.m_41619_()) {
               return null;
            } else {
               affixItem.m_41783_().m_128379_("apoth_merchant", true);
               return new MerchantOffer(this.price, this.price2, affixItem, 1, 100, 1.0F);
            }
         }
      }
   }

   @Override
   public boolean isRare() {
      return this.rare;
   }

   public PSerializer<? extends JsonTrade> getSerializer() {
      return SERIALIZER;
   }

   private AffixLootEntry unwrap(ResourceLocation holder) {
      AffixLootEntry entry = (AffixLootEntry)AffixLootManager.INSTANCE.getValue(holder);
      if (entry == null) {
         AdventureModule.LOGGER.error("An AffixTrade failed to resolve the Affix Loot Entry {}!", holder);
         return null;
      } else {
         return entry;
      }
   }
}
