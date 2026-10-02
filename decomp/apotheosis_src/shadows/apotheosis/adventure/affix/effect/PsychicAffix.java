package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class PsychicAffix extends Affix {
   public static final Codec<PsychicAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(GemBonus.VALUES_CODEC.fieldOf("values").forGetter(a -> a.values)).apply(inst, PsychicAffix::new)
   );
   public static final PSerializer<PsychicAffix> SERIALIZER = PSerializer.fromCodec("Psychic Affix", CODEC);
   protected final Map<LootRarity, StepFunction> values;

   public PsychicAffix(Map<LootRarity, StepFunction> values) {
      super(AffixType.ABILITY);
      this.values = values;
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      list.accept(Component.m_237110_("affix." + this.getId() + ".desc", new Object[]{fmt(100.0F * this.getTrueLevel(rarity, level))}));
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat == LootCategory.SHIELD && this.values.containsKey(rarity);
   }

   @Override
   public float onShieldBlock(ItemStack stack, LootRarity rarity, float level, LivingEntity entity, DamageSource source, float amount) {
      if (source.m_7640_() instanceof Projectile arrow && arrow.m_37282_() instanceof LivingEntity living) {
         living.m_6469_(new EntityDamageSource("player", entity).m_19389_(), amount * this.getTrueLevel(rarity, level));
      }

      return super.onShieldBlock(stack, rarity, level, entity, source, amount);
   }

   private float getTrueLevel(LootRarity rarity, float level) {
      return this.values.get(rarity).get(level);
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }
}
