package shadows.apotheosis.adventure.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.SimpleMapCodec;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.mutable.MutableInt;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.placebo.codec.EnumCodec;
import shadows.placebo.color.GradientColor;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;
import shadows.placebo.json.WeightedJsonReloadListener.ILuckyWeighted;

public class LootRarity implements ILuckyWeighted, Comparable<LootRarity> {
   public static final List<LootRarity> LIST = ImmutableList.of(
      LootRarity.COMMON, LootRarity.UNCOMMON, LootRarity.RARE, LootRarity.EPIC, LootRarity.MYTHIC, LootRarity.ANCIENT
   );
   public static final Map<String, LootRarity> BY_ID = ImmutableMap.copyOf(LIST.stream().collect(Collectors.toMap(LootRarity::id, Function.identity())));
   public static final Codec<LootRarity> CODEC = ExtraCodecs.m_184405_(LootRarity::id, LootRarity::byId);
   public static final LootRarity COMMON = new LootRarity(
      "common", 8421504, 0, 400, 0.0F, ImmutableList.of(new LootRarity.LootRule(AffixType.STAT, 1.0F), new LootRarity.LootRule(AffixType.STAT, 0.25F))
   );
   public static final LootRarity UNCOMMON = new LootRarity(
      "uncommon",
      3407667,
      1,
      320,
      1.5F,
      ImmutableList.of(
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 0.45F, new LootRarity.LootRule(AffixType.ABILITY, 0.25F)),
         new LootRarity.LootRule(AffixType.SOCKET, 0.45F)
      )
   );
   public static final LootRarity RARE = new LootRarity(
      "rare",
      5592575,
      2,
      150,
      3.0F,
      ImmutableList.of(
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.25F)),
         new LootRarity.LootRule(AffixType.ABILITY, 1.0F),
         new LootRarity.LootRule(AffixType.ABILITY, 0.33F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.65F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.45F),
         new LootRarity.LootRule(AffixType.DURABILITY, 0.1F)
      )
   );
   public static final LootRarity EPIC = new LootRarity(
      "epic",
      12255419,
      3,
      90,
      4.5F,
      ImmutableList.of(
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.45F)),
         new LootRarity.LootRule(AffixType.STAT, 0.5F, new LootRarity.LootRule(AffixType.ABILITY, 0.33F)),
         new LootRarity.LootRule(AffixType.ABILITY, 1.0F),
         new LootRarity.LootRule(AffixType.ABILITY, 0.65F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.85F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.65F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.45F),
         new LootRarity.LootRule(AffixType.DURABILITY, 0.3F)
      )
   );
   public static final LootRarity MYTHIC = new LootRarity(
      "mythic",
      15560724,
      4,
      40,
      6.0F,
      ImmutableList.of(
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.5F)),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.4F)),
         new LootRarity.LootRule(AffixType.ABILITY, 1.0F),
         new LootRarity.LootRule(AffixType.ABILITY, 1.0F),
         new LootRarity.LootRule(AffixType.ABILITY, 0.3F),
         new LootRarity.LootRule(AffixType.SOCKET, 1.0F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.85F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.65F),
         new LootRarity.LootRule(AffixType.DURABILITY, 0.5F)
      )
   );
   public static final LootRarity ANCIENT = new LootRarity(
      "ancient",
      GradientColor.RAINBOW,
      5,
      0,
      0.0F,
      ImmutableList.of(
         new LootRarity.LootRule(AffixType.ANCIENT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.7F)),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.6F)),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.5F)),
         new LootRarity.LootRule(AffixType.STAT, 1.0F, new LootRarity.LootRule(AffixType.ABILITY, 0.4F)),
         new LootRarity.LootRule(AffixType.ABILITY, 1.0F),
         new LootRarity.LootRule(AffixType.ABILITY, 1.0F),
         new LootRarity.LootRule(AffixType.ABILITY, 0.75F),
         new LootRarity.LootRule(AffixType.ABILITY, 0.45F),
         new LootRarity.LootRule(AffixType.SOCKET, 1.0F),
         new LootRarity.LootRule(AffixType.SOCKET, 0.85F),
         new LootRarity.LootRule[]{
            new LootRarity.LootRule(AffixType.SOCKET, 0.65F),
            new LootRarity.LootRule(AffixType.SOCKET, 0.45F),
            new LootRarity.LootRule(AffixType.SOCKET, 0.25F),
            new LootRarity.LootRule(AffixType.DURABILITY, 0.75F)
         }
      )
   );
   private final String id;
   private final TextColor color;
   private final int ordinal;
   private int weight;
   private float quality;
   private List<LootRarity.LootRule> rules;

   private LootRarity(String id, TextColor color, int ordinal, int weight, float quality, List<LootRarity.LootRule> rules) {
      this.id = id;
      this.color = color;
      this.ordinal = ordinal;
      this.weight = weight;
      this.quality = quality;
      this.rules = rules;
   }

   private LootRarity(String id, int color, int ordinal, int weight, float quality, List<LootRarity.LootRule> rules) {
      this(id, TextColor.m_131266_(color), ordinal, weight, quality, rules);
   }

   public String id() {
      return this.id;
   }

   public TextColor color() {
      return this.color;
   }

   public int ordinal() {
      return this.ordinal;
   }

   public int getWeight() {
      return this.weight;
   }

   public float getQuality() {
      return this.quality;
   }

   public List<LootRarity.LootRule> rules() {
      return this.rules;
   }

   public LootRarity prev() {
      return this == COMMON ? this : LIST.get(this.ordinal - 1);
   }

   public LootRarity next() {
      return this == ANCIENT ? this : LIST.get(this.ordinal + 1);
   }

   public boolean isAtMost(LootRarity other) {
      return this.ordinal() <= other.ordinal();
   }

   public boolean isAtLeast(LootRarity other) {
      return this.ordinal() >= other.ordinal();
   }

   public static LootRarity min(LootRarity a, @Nullable LootRarity b) {
      if (b == null) {
         return a;
      } else {
         return a.ordinal <= b.ordinal ? a : b;
      }
   }

   public static LootRarity max(LootRarity a, @Nullable LootRarity b) {
      if (b == null) {
         return a;
      } else {
         return a.ordinal >= b.ordinal ? a : b;
      }
   }

   public static boolean isRarityMat(ItemStack stack) {
      return AdventureModule.RARITY_MATERIALS.containsValue(stack.m_41720_());
   }

   @Nullable
   public static LootRarity getMaterialRarity(ItemStack stack) {
      return (LootRarity)AdventureModule.RARITY_MATERIALS.inverse().get(stack.m_41720_());
   }

   public ItemStack getMaterial() {
      return new ItemStack((ItemLike)AdventureModule.RARITY_MATERIALS.get(this));
   }

   public LootRarity clamp(@Nullable LootRarity lowerBound, @Nullable LootRarity upperBound) {
      return max(min(this, upperBound), lowerBound);
   }

   public Component toComponent() {
      return Component.m_237115_("rarity.apoth." + this.id).m_130948_(Style.f_131099_.m_131148_(this.color));
   }

   void update(LootRarity.RarityStub stub) {
      this.weight = stub.weight;
      this.quality = stub.quality;
      this.rules = ImmutableList.copyOf(stub.rules);
   }

   @Override
   public String toString() {
      return "LootRarity{" + this.id + "}";
   }

   @Nullable
   public static LootRarity byId(String id) {
      return BY_ID.get(id.toLowerCase(Locale.ROOT));
   }

   public static Set<String> ids() {
      return BY_ID.keySet();
   }

   public static List<LootRarity> values() {
      return LIST;
   }

   public static LootRarity random(RandomSource rand, float luck) {
      return random(rand, luck, null, null);
   }

   public static LootRarity random(RandomSource rand, float luck, @Nullable LootRarity.Clamped item) {
      return item == null ? random(rand, luck) : random(rand, luck, item.getMinRarity(), item.getMaxRarity());
   }

   public static LootRarity random(RandomSource rand, float luck, @Nullable LootRarity min, @Nullable LootRarity max) {
      List<Wrapper<LootRarity>> list = LIST.stream().filter(r -> r.clamp(min, max) == r).map(r -> r.wrap(luck)).toList();
      return WeightedRandom.m_216822_(rand, list).<LootRarity>map(Wrapper::m_146310_).get();
   }

   public static <T> SimpleMapCodec<LootRarity, T> mapCodec(Codec<T> codec) {
      return Codec.simpleMap(CODEC, codec, Keyable.forStrings(() -> values().stream().map(LootRarity::id)));
   }

   public int compareTo(LootRarity o) {
      return Integer.compare(this.ordinal, o.ordinal);
   }

   public interface Clamped {
      LootRarity getMinRarity();

      LootRarity getMaxRarity();

      default LootRarity clamp(@Nullable LootRarity rarity) {
         return rarity == null ? this.getMinRarity() : rarity.clamp(this.getMinRarity(), this.getMaxRarity());
      }

      public static record Impl(LootRarity min, LootRarity max) implements LootRarity.Clamped {
         public static final Codec<LootRarity.Clamped.Impl> STRING_CODEC = ExtraCodecs.m_184415_(() -> Codec.STRING.xmap(s -> {
               LootRarity rarity = LootRarity.byId(s);
               return new LootRarity.Clamped.Impl(rarity, rarity);
            }, simple -> simple.min().id().toString()));
         public static final Codec<LootRarity.Clamped.Impl> MIN_MAX_CODEC = ExtraCodecs.m_184415_(
            () -> RecordCodecBuilder.create(
                  inst -> inst.group(
                           LootRarity.CODEC.fieldOf("min").forGetter(LootRarity.Clamped.Impl::min),
                           LootRarity.CODEC.fieldOf("max").forGetter(LootRarity.Clamped.Impl::max)
                        )
                        .apply(inst, LootRarity.Clamped.Impl::new)
               )
         );
         public static final Codec<LootRarity.Clamped.Impl> CODEC = Codec.either(STRING_CODEC, MIN_MAX_CODEC)
            .xmap(e -> (LootRarity.Clamped.Impl)e.map(Function.identity(), Function.identity()), Either::right);

         @Override
         public LootRarity getMinRarity() {
            return this.min;
         }

         @Override
         public LootRarity getMaxRarity() {
            return this.max;
         }
      }
   }

   public static record LootRule(AffixType type, float chance, @Nullable LootRarity.LootRule backup) {
      public static final Codec<LootRarity.LootRule> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  new EnumCodec(AffixType.class).fieldOf("type").forGetter(LootRarity.LootRule::type),
                  Codec.FLOAT.fieldOf("chance").forGetter(LootRarity.LootRule::chance),
                  ExtraCodecs.m_184415_(() -> CODEC).optionalFieldOf("backup").forGetter(rule -> Optional.ofNullable(rule.backup()))
               )
               .apply(inst, LootRarity.LootRule::new)
      );
      private static Random jRand = new Random();

      public LootRule(AffixType type, float chance) {
         this(type, chance, Optional.empty());
      }

      public LootRule(AffixType type, float chance, Optional<LootRarity.LootRule> backup) {
         this(type, chance, backup.orElse(null));
      }

      public void execute(ItemStack stack, LootRarity rarity, Set<Affix> currentAffixes, MutableInt sockets, RandomSource rand) {
         if (this.type != AffixType.DURABILITY) {
            if (rand.m_188501_() <= this.chance) {
               if (this.type == AffixType.SOCKET) {
                  sockets.add(1);
                  return;
               }

               List<Affix> available = AffixHelper.byType(this.type)
                  .stream()
                  .filter(a -> a.canApplyTo(stack, LootCategory.forItem(stack), rarity) && !currentAffixes.contains(a))
                  .collect(Collectors.toList());
               if (available.size() == 0) {
                  if (this.backup != null) {
                     this.backup.execute(stack, rarity, currentAffixes, sockets, rand);
                  } else {
                     AdventureModule.LOGGER
                        .error("Failed to execute LootRule {}/{}/{}/{}!", ForgeRegistries.ITEMS.getKey(stack.m_41720_()), rarity.id(), this.type, this.chance);
                  }

                  return;
               }

               jRand.setSeed(rand.m_188505_());
               Collections.shuffle(available, jRand);
               currentAffixes.add(available.get(0));
            }
         }
      }
   }

   public static class RarityStub extends TypeKeyedBase<LootRarity.RarityStub> implements ILuckyWeighted {
      public static final Codec<LootRarity.RarityStub> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.intRange(0, Integer.MAX_VALUE).fieldOf("weight").forGetter(ILuckyWeighted::getWeight),
                  Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("quality", 0.0F).forGetter(ILuckyWeighted::getQuality),
                  new ListCodec(LootRarity.LootRule.CODEC).fieldOf("rules").forGetter(LootRarity.RarityStub::rules)
               )
               .apply(inst, LootRarity.RarityStub::new)
      );
      public static final PSerializer<LootRarity.RarityStub> SERIALIZER = PSerializer.fromCodec("Loot Rarity", CODEC);
      int weight;
      float quality;
      List<LootRarity.LootRule> rules;

      public RarityStub(int weight, float quality, List<LootRarity.LootRule> rules) {
         this.weight = weight;
         this.quality = quality;
         this.rules = rules;
      }

      public int getWeight() {
         return (R)this.weight;
      }

      public float getQuality() {
         return (R)this.quality;
      }

      public List<LootRarity.LootRule> rules() {
         return this.rules;
      }

      public PSerializer<? extends LootRarity.RarityStub> getSerializer() {
         return SERIALIZER;
      }
   }
}
