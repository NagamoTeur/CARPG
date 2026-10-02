package shadows.apotheosis.adventure.loot;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.placebo.codec.PlaceboCodecs;
import shadows.placebo.json.ItemAdapter;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;
import shadows.placebo.json.WeightedJsonReloadListener.ILuckyWeighted;

public final class AffixLootEntry extends TypeKeyedBase<AffixLootEntry> implements ILuckyWeighted, IDimensional, LootRarity.Clamped, GameStagesCompat.IStaged {
   public static final Codec<AffixLootEntry> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               Codec.intRange(0, Integer.MAX_VALUE).fieldOf("weight").forGetter(ILuckyWeighted::getWeight),
               Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("quality", 0.0F).forGetter(ILuckyWeighted::getQuality),
               ItemAdapter.CODEC.fieldOf("stack").forGetter(a -> a.stack),
               PlaceboCodecs.setOf(ResourceLocation.f_135803_).fieldOf("dimensions").forGetter(a -> a.dimensions),
               LootRarity.CODEC.optionalFieldOf("min_rarity", LootRarity.COMMON).forGetter(a -> a.minRarity),
               LootRarity.CODEC.optionalFieldOf("max_rarity", LootRarity.MYTHIC).forGetter(a -> a.maxRarity),
               PlaceboCodecs.setOf(Codec.STRING).optionalFieldOf("stages").forGetter(a -> Optional.ofNullable(a.stages))
            )
            .apply(inst, AffixLootEntry::new)
   );
   public static final PSerializer<AffixLootEntry> SERIALIZER = PSerializer.fromCodec("Affix Loot Entry", CODEC);
   protected final int weight;
   protected final float quality;
   protected final ItemStack stack;
   protected final Set<ResourceLocation> dimensions;
   protected final LootRarity minRarity;
   protected final LootRarity maxRarity;
   @Nullable
   protected final Set<String> stages;

   public AffixLootEntry(
      int weight, float quality, ItemStack stack, Set<ResourceLocation> dimensions, LootRarity min, LootRarity max, Optional<Set<String>> stages
   ) {
      this.weight = weight;
      this.quality = quality;
      this.stack = Objects.requireNonNull(stack);
      this.dimensions = dimensions;
      this.minRarity = min;
      this.maxRarity = max;
      this.stages = stages.orElse(null);
      Preconditions.checkArgument(min.ordinal() <= max.ordinal(), "The minimum rarity " + min + " must be lower or equal to the max rarity " + max);
   }

   public AffixLootEntry(int weight, float quality, ItemStack stack, Set<ResourceLocation> dimensions, LootRarity min, LootRarity max) {
      this(weight, quality, stack, dimensions, min, max, Optional.empty());
   }

   public int getWeight() {
      return (R)this.weight;
   }

   public float getQuality() {
      return (R)this.quality;
   }

   public ItemStack getStack() {
      return this.stack.m_41777_();
   }

   public Set<ResourceLocation> getDimensions() {
      return this.dimensions;
   }

   @Override
   public LootRarity getMinRarity() {
      return this.minRarity;
   }

   @Override
   public LootRarity getMaxRarity() {
      return this.maxRarity;
   }

   public LootCategory getType() {
      return LootCategory.forItem(this.stack);
   }

   @Override
   public Set<String> getStages() {
      return this.stages;
   }

   public PSerializer<? extends AffixLootEntry> getSerializer() {
      return SERIALIZER;
   }
}
