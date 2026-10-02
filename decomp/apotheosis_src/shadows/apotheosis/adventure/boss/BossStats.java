package shadows.apotheosis.adventure.boss;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import shadows.apotheosis.util.ChancedEffectInstance;
import shadows.placebo.json.RandomAttributeModifier;

public record BossStats(float enchantChance, int[] enchLevels, List<ChancedEffectInstance> effects, List<RandomAttributeModifier> modifiers) {
   public static final Codec<BossStats> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               Codec.FLOAT.fieldOf("enchant_chance").forGetter(BossStats::enchantChance),
               Codec.INT
                  .listOf()
                  .xmap(l -> l.stream().mapToInt(Integer::intValue).toArray(), arr -> Arrays.stream(arr).boxed().toList())
                  .fieldOf("enchantment_levels")
                  .forGetter(BossStats::enchLevels),
               ChancedEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(BossStats::effects),
               RandomAttributeModifier.CODEC.listOf().fieldOf("attribute_modifiers").forGetter(BossStats::modifiers)
            )
            .apply(inst, BossStats::new)
   );
}
