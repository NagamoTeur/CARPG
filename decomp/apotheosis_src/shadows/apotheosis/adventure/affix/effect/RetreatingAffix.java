package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;

public class RetreatingAffix extends Affix {
   public static final Codec<RetreatingAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(LootRarity.CODEC.fieldOf("min_rarity").forGetter(a -> a.minRarity)).apply(inst, RetreatingAffix::new)
   );
   public static final PSerializer<RetreatingAffix> SERIALIZER = PSerializer.fromCodec("Retreating Affix", CODEC);
   protected LootRarity minRarity;

   public RetreatingAffix(LootRarity minRarity) {
      super(AffixType.ABILITY);
      this.minRarity = minRarity;
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat == LootCategory.SHIELD && rarity.isAtLeast(this.minRarity);
   }

   @Override
   public float onShieldBlock(ItemStack stack, LootRarity rarity, float level, LivingEntity entity, DamageSource source, float amount) {
      Entity tSource = source.m_7639_();
      if (tSource != null && tSource.m_20280_(entity) <= 9.0) {
         Vec3 look = entity.m_20154_();
         entity.m_20256_(new Vec3(1.0 * -look.f_82479_, 0.25, 1.0 * -look.f_82481_));
         entity.f_19864_ = true;
         entity.m_6853_(false);
      }

      return super.onShieldBlock(stack, rarity, level, entity, source, amount);
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }
}
