package shadows.apotheosis.adventure.affix.socket.gem;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.codec.PlaceboCodecs;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;
import shadows.placebo.json.WeightedJsonReloadListener.ILuckyWeighted;

public class Gem extends TypeKeyedBase<Gem> implements ILuckyWeighted, IDimensional, LootRarity.Clamped, GameStagesCompat.IStaged {
   public static final Codec<Gem> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               Codec.intRange(0, Integer.MAX_VALUE).fieldOf("weight").forGetter(ILuckyWeighted::getWeight),
               Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("quality", 0.0F).forGetter(ILuckyWeighted::getQuality),
               PlaceboCodecs.setOf(ResourceLocation.f_135803_).optionalFieldOf("dimensions", Collections.emptySet()).forGetter(IDimensional::getDimensions),
               LootRarity.CODEC.optionalFieldOf("min_rarity").forGetter(g -> Optional.of(g.getMinRarity())),
               LootRarity.CODEC.optionalFieldOf("max_rarity").forGetter(g -> Optional.of(g.getMaxRarity())),
               GemBonus.CODEC.listOf().fieldOf("bonuses").forGetter(Gem::getBonuses),
               Codec.BOOL.optionalFieldOf("unique", false).forGetter(Gem::isUnique),
               PlaceboCodecs.setOf(Codec.STRING).optionalFieldOf("stages").forGetter(gem -> Optional.ofNullable(gem.getStages()))
            )
            .apply(inst, Gem::new)
   );
   public static final PSerializer<Gem> SERIALIZER = PSerializer.fromCodec("Gem", CODEC);
   protected final int weight;
   protected final float quality;
   protected final Set<ResourceLocation> dimensions;
   protected final List<GemBonus> bonuses;
   protected final boolean unique;
   @Nullable
   protected final Set<String> stages;
   protected final transient Map<LootCategory, GemBonus> bonusMap;
   protected final transient int uuidsNeeded;
   protected final transient LootRarity minRarity;
   protected final transient LootRarity maxRarity;

   public Gem(
      int weight,
      float quality,
      Set<ResourceLocation> dimensions,
      Optional<LootRarity> minRarity,
      Optional<LootRarity> maxRarity,
      List<GemBonus> bonuses,
      boolean unique,
      Optional<Set<String>> stages
   ) {
      this.weight = weight;
      this.quality = quality;
      this.dimensions = dimensions;
      this.bonuses = bonuses;
      this.unique = unique;
      this.stages = stages.orElse(null);
      Preconditions.checkArgument(!bonuses.isEmpty(), "No bonuses were provided.");
      this.bonusMap = bonuses.stream().mapMulti((gemData, mapper) -> {
         for (LootCategory c : gemData.getGemClass().types()) {
            mapper.accept(Pair.of(c, gemData));
         }
      }).collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
      this.uuidsNeeded = this.bonuses.stream().mapToInt(GemBonus::getNumberOfUUIDs).max().orElse(0);
      if (minRarity.isPresent()) {
         this.minRarity = minRarity.get();
      } else {
         this.minRarity = LootRarity.values().stream().filter(bonuses.get(0)::supports).min(LootRarity::compareTo).get();
      }

      if (maxRarity.isPresent()) {
         this.maxRarity = maxRarity.get();
      } else {
         this.maxRarity = LootRarity.values().stream().filter(bonuses.get(0)::supports).max(LootRarity::compareTo).get();
      }

      Preconditions.checkArgument(this.minRarity.ordinal() <= this.maxRarity.ordinal(), "The min rarity must be <= the max rarity.");
   }

   public int getNumberOfUUIDs() {
      return this.uuidsNeeded;
   }

   public void addInformation(ItemStack gem, LootRarity rarity, Consumer<Component> list) {
      if (this.isUnique()) {
         list.accept(Component.m_237115_("text.apotheosis.unique").m_130948_(Style.f_131099_.m_178520_(13056274)));
      }

      list.accept(CommonComponents.f_237098_);
      Style style = Style.f_131099_.m_178520_(720650);
      list.accept(Component.m_237115_("text.apotheosis.socketable_into").m_130948_(style));
      addTypeInfo(list, this.bonusMap.keySet().toArray());
      list.accept(CommonComponents.f_237098_);
      if (this.bonuses.size() == 1) {
         list.accept(Component.m_237115_("item.modifiers.socket").m_130940_(ChatFormatting.GOLD));
         list.accept(this.bonuses.get(0).getSocketBonusTooltip(gem, rarity));
      } else {
         list.accept(Component.m_237115_("item.modifiers.socket_in").m_130940_(ChatFormatting.GOLD));

         for (GemBonus bonus : this.bonuses) {
            if (bonus.supports(rarity)) {
               Component modifComp = bonus.getSocketBonusTooltip(gem, rarity);
               Component sum = Component.m_237110_(
                     "text.apotheosis.dot_prefix",
                     new Object[]{Component.m_237110_("%s: %s", new Object[]{Component.m_237115_("gem_class." + bonus.getGemClass().key()), modifComp})}
                  )
                  .m_130940_(ChatFormatting.GOLD);
               list.accept(sum);
            }
         }
      }
   }

   public boolean canApplyTo(ItemStack socketed, ItemStack gem, LootRarity rarity) {
      if (this.isUnique()) {
         List<Gem> gems = SocketHelper.getGemInstances(socketed).map(GemInstance::gem).toList();
         if (gems.contains(this)) {
            return false;
         }
      }

      return this.isValidIn(socketed, gem, rarity);
   }

   public boolean isValidIn(ItemStack socketed, ItemStack gem, LootRarity rarity) {
      LootCategory cat = LootCategory.forItem(socketed);
      return !cat.isNone() && this.bonusMap.containsKey(cat) && this.bonusMap.get(cat).supports(rarity);
   }

   public Optional<GemBonus> getBonus(LootCategory cat, LootRarity rarity) {
      return Optional.ofNullable(this.bonusMap.get(cat)).filter(b -> b.supports(rarity));
   }

   @Override
   public String toString() {
      return String.format("Gem: %s", this.getId());
   }

   public static String fmt(float f) {
      return Affix.fmt(f);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Gem gem && gem.getId().equals(this.getId())) {
         return true;
      }

      return false;
   }

   @Override
   public int hashCode() {
      return this.getId().hashCode();
   }

   public float getQuality() {
      return (R)this.quality;
   }

   public int getWeight() {
      return (R)this.weight;
   }

   public Set<ResourceLocation> getDimensions() {
      return (R)this.dimensions;
   }

   @Override
   public LootRarity getMaxRarity() {
      return this.maxRarity;
   }

   @Override
   public LootRarity getMinRarity() {
      return this.minRarity;
   }

   public List<GemBonus> getBonuses() {
      return this.bonuses;
   }

   public boolean isUnique() {
      return this.unique;
   }

   public Gem validate() {
      Preconditions.checkArgument(this.weight >= 0, "Gem " + this.getId() + " has a negative weight");
      Preconditions.checkArgument(this.quality >= 0.0F, "Gem " + this.getId() + " has a negative quality");
      Preconditions.checkNotNull(this.dimensions);
      Preconditions.checkArgument(this.maxRarity.ordinal() >= this.minRarity.ordinal());
      LootRarity.values()
         .stream()
         .filter(r -> r.isAtLeast(this.minRarity) && r.isAtMost(this.maxRarity))
         .forEach(r -> Preconditions.checkArgument(this.bonuses.stream().allMatch(b -> b.supports(r))));
      return this;
   }

   @Override
   public Set<String> getStages() {
      return this.stages;
   }

   public PSerializer<? extends Gem> getSerializer() {
      return SERIALIZER;
   }

   public static void addTypeInfo(Consumer<Component> list, Object... types) {
      Arrays.sort(types, (c1, c2) -> ((LootCategory)c1).getName().compareTo(((LootCategory)c2).getName()));
      Style style = Style.f_131099_.m_178520_(720650);
      if (types.length != LootCategory.BY_ID.size() - 1) {
         StringBuilder sb = new StringBuilder();
         int i = 0;

         while (i < types.length) {
            int rem = Math.min(3, types.length - i);
            Object[] args = new Object[rem];

            for (int r = 0; r < rem; r++) {
               sb.append("%s, ");
               args[r] = Component.m_237115_(((LootCategory)types[i + r]).getDescIdPlural());
            }

            list.accept(
               Component.m_237110_("text.apotheosis.dot_prefix", new Object[]{Component.m_237110_(sb.substring(0, sb.length() - 2), args)}).m_130948_(style)
            );
            sb.setLength(0);
            i += rem;
         }
      } else {
         list.accept(Component.m_237110_("text.apotheosis.dot_prefix", new Object[]{Component.m_237115_("text.apotheosis.anything")}).m_130948_(style));
      }
   }
}
