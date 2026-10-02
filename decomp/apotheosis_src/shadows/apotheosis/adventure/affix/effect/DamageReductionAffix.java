package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.codec.EnumCodec;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class DamageReductionAffix extends Affix {
   public static final Codec<DamageReductionAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               DamageReductionAffix.DamageType.CODEC.fieldOf("damage_type").forGetter(a -> a.type),
               GemBonus.VALUES_CODEC.fieldOf("values").forGetter(a -> a.values),
               LootCategory.SET_CODEC.fieldOf("types").forGetter(a -> a.types)
            )
            .apply(inst, DamageReductionAffix::new)
   );
   public static final PSerializer<DamageReductionAffix> SERIALIZER = PSerializer.fromCodec("Damage Reduction Affix", CODEC);
   protected final DamageReductionAffix.DamageType type;
   protected final Map<LootRarity, StepFunction> values;
   protected final Set<LootCategory> types;

   public DamageReductionAffix(DamageReductionAffix.DamageType type, Map<LootRarity, StepFunction> levelFuncs, Set<LootCategory> types) {
      super(AffixType.ABILITY);
      this.type = type;
      this.values = levelFuncs;
      this.types = types;
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return !cat.isNone() && (this.types.isEmpty() || this.types.contains(cat)) && this.values.containsKey(rarity);
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      MutableComponent comp = Component.m_237110_(
         "affix.apotheosis:damage_reduction.desc",
         new Object[]{Component.m_237115_("misc.apotheosis." + this.type.id), fmt(100.0F * this.getTrueLevel(rarity, level))}
      );
      list.accept(comp);
   }

   @Override
   public float onHurt(ItemStack stack, LootRarity rarity, float level, DamageSource src, LivingEntity ent, float amount) {
      if (src.m_19378_() || src.m_19379_()) {
         return amount;
      } else {
         return this.type.test(src) ? amount * (1.0F - this.getTrueLevel(rarity, level)) : super.onHurt(stack, rarity, level, src, ent, amount);
      }
   }

   private float getTrueLevel(LootRarity rarity, float level) {
      return this.values.get(rarity).get(level);
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }

   public static enum DamageType implements Predicate<DamageSource> {
      PHYSICAL("physical", d -> !d.m_19387_() && !d.m_19384_() && !d.m_19372_() && !d.m_146707_()),
      MAGIC("magic", DamageSource::m_19387_),
      FIRE("fire", DamageSource::m_19384_),
      FALL("fall", DamageSource::m_146707_),
      EXPLOSION("explosion", DamageSource::m_19372_);

      public static Codec<DamageReductionAffix.DamageType> CODEC = new EnumCodec(DamageReductionAffix.DamageType.class);
      private final String id;
      private final Predicate<DamageSource> predicate;

      private DamageType(String id, Predicate<DamageSource> predicate) {
         this.id = id;
         this.predicate = predicate;
      }

      public String getId() {
         return this.id;
      }

      public boolean test(DamageSource t) {
         return this.predicate.test(t);
      }
   }
}
