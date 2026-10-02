package shadows.apotheosis.adventure.affix.socket.gem.bonus;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.util.StepFunction;

public class DurabilityBonus extends GemBonus {
   public static Codec<DurabilityBonus> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(gemClass(), VALUES_CODEC.fieldOf("values").forGetter(a -> a.values)).apply(inst, DurabilityBonus::new)
   );
   protected final Map<LootRarity, StepFunction> values;

   public DurabilityBonus(GemClass gemClass, Map<LootRarity, StepFunction> values) {
      super(Apotheosis.loc("durability"), gemClass);
      this.values = values;
   }

   @Override
   public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
      float level = this.values.get(rarity).get(0.0F);
      return Component.m_237110_("bonus." + this.getId() + ".desc", new Object[]{Affix.fmt(100.0F * level)}).m_130940_(ChatFormatting.YELLOW);
   }

   @Override
   public float getDurabilityBonusPercentage(ItemStack gem, LootRarity rarity, ServerPlayer user) {
      return this.values.get(rarity).min();
   }

   @Override
   public GemBonus validate() {
      Preconditions.checkNotNull(this.values, "Invalid AttributeBonus with null values");
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
