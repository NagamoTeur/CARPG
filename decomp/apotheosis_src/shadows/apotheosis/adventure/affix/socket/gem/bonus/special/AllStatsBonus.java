package shadows.apotheosis.adventure.affix.socket.gem.bonus.special;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.codec.PlaceboCodecs;
import shadows.placebo.util.StepFunction;

public class AllStatsBonus extends GemBonus {
   public static Codec<AllStatsBonus> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               gemClass(),
               PlaceboCodecs.enumCodec(Operation.class).fieldOf("operation").forGetter(a -> a.operation),
               VALUES_CODEC.fieldOf("values").forGetter(a -> a.values),
               Registry.f_122866_.m_194605_().listOf().optionalFieldOf("attributes").forGetter(a -> Optional.of(a.attributes))
            )
            .apply(inst, AllStatsBonus::new)
   );
   protected final Operation operation;
   protected final Map<LootRarity, StepFunction> values;
   protected final List<Attribute> attributes;

   public AllStatsBonus(GemClass gemClass, Operation op, Map<LootRarity, StepFunction> values, Optional<List<Attribute>> attributes) {
      super(Apotheosis.loc("all_stats"), gemClass);
      this.operation = op;
      this.values = values;
      this.attributes = attributes.orElseGet(
         () -> Registry.f_122866_.m_123024_().filter(((AttributeSupplier)ForgeHooks.getAttributesView().get(EntityType.f_20532_))::m_22258_).toList()
      );
   }

   @Override
   public void addModifiers(ItemStack gem, LootRarity rarity, BiConsumer<Attribute, AttributeModifier> map) {
      UUID id = GemItem.getUUIDs(gem).get(0);

      for (Attribute attr : this.attributes) {
         AttributeModifier modif = new AttributeModifier(id, "apoth.gem_modifier.all_stats_buff", (double)this.values.get(rarity).min(), this.operation);
         map.accept(attr, modif);
      }
   }

   @Override
   public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
      StepFunction value = this.values.get(rarity);
      return Component.m_237110_("bonus." + this.getId() + ".desc", new Object[]{Affix.fmt(value.get(0.0F) * 100.0F)}).m_130940_(ChatFormatting.YELLOW);
   }

   public AllStatsBonus validate() {
      Preconditions.checkNotNull(this.operation, "Invalid AllStatsBonus with null operation");
      Preconditions.checkNotNull(this.values, "Invalid AllStatsBonus with null values");
      return this;
   }

   @Override
   public boolean supports(LootRarity rarity) {
      return this.values.containsKey(rarity);
   }

   @Override
   public int getNumberOfUUIDs() {
      return 1;
   }

   public Codec<? extends GemBonus> getCodec() {
      return CODEC;
   }
}
