package shadows.apotheosis.adventure.affix.socket.gem.bonus;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.effect.DamageReductionAffix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.util.StepFunction;

public class DamageReductionBonus extends GemBonus {
   protected final DamageReductionAffix.DamageType type;
   protected final Map<LootRarity, StepFunction> values;
   public static Codec<DamageReductionBonus> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               gemClass(),
               DamageReductionAffix.DamageType.CODEC.fieldOf("damage_type").forGetter(a -> a.type),
               VALUES_CODEC.fieldOf("values").forGetter(a -> a.values)
            )
            .apply(inst, DamageReductionBonus::new)
   );

   public DamageReductionBonus(GemClass gemClass, DamageReductionAffix.DamageType type, Map<LootRarity, StepFunction> values) {
      super(Apotheosis.loc("damage_reduction"), gemClass);
      this.type = type;
      this.values = values;
   }

   @Override
   public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
      float level = this.values.get(rarity).get(0.0F);
      return Component.m_237110_(
            "affix.apotheosis:damage_reduction.desc", new Object[]{Component.m_237115_("misc.apotheosis." + this.type.getId()), Affix.fmt(100.0F * level)}
         )
         .m_130940_(ChatFormatting.YELLOW);
   }

   @Override
   public GemBonus validate() {
      Preconditions.checkNotNull(this.type, "Invalid DamageReductionBonus with null type");
      Preconditions.checkNotNull(this.values, "Invalid DamageReductionBonus with null values");
      Preconditions.checkArgument(this.values.entrySet().stream().<Object>mapMulti((entry, consumer) -> {
         consumer.accept(entry.getKey());
         consumer.accept(entry.getValue());
      }).allMatch(Objects::nonNull), "Invalid DamageReductionBonus with invalid values");
      return this;
   }

   @Override
   public boolean supports(LootRarity rarity) {
      return this.values.containsKey(rarity);
   }

   @Override
   public int getNumberOfUUIDs() {
      return 0;
   }

   public Codec<? extends GemBonus> getCodec() {
      return CODEC;
   }
}
