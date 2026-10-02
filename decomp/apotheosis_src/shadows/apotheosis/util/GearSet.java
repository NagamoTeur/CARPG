package shadows.apotheosis.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import shadows.placebo.json.ItemAdapter;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;
import shadows.placebo.json.WeightedJsonReloadListener.ILuckyWeighted;

public class GearSet extends TypeKeyedBase<GearSet> implements ILuckyWeighted {
   public static final Codec<GearSet> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               Codec.intRange(0, Integer.MAX_VALUE).fieldOf("weight").forGetter(ILuckyWeighted::getWeight),
               Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("quality", 0.0F).forGetter(ILuckyWeighted::getQuality),
               GearSet.WeightedItemStack.LIST_CODEC.fieldOf("mainhands").forGetter(g -> g.mainhands),
               GearSet.WeightedItemStack.LIST_CODEC.fieldOf("offhands").forGetter(g -> g.offhands),
               GearSet.WeightedItemStack.LIST_CODEC.fieldOf("boots").forGetter(g -> g.boots),
               GearSet.WeightedItemStack.LIST_CODEC.fieldOf("leggings").forGetter(g -> g.leggings),
               GearSet.WeightedItemStack.LIST_CODEC.fieldOf("chestplates").forGetter(g -> g.chestplates),
               GearSet.WeightedItemStack.LIST_CODEC.fieldOf("helmets").forGetter(g -> g.helmets),
               Codec.STRING.listOf().fieldOf("tags").forGetter(g -> g.tags)
            )
            .apply(inst, GearSet::new)
   );
   public static final PSerializer<GearSet> SERIALIZER = PSerializer.fromCodec("Gear Set", CODEC);
   protected final int weight;
   protected final float quality;
   protected final List<GearSet.WeightedItemStack> mainhands;
   protected final List<GearSet.WeightedItemStack> offhands;
   protected final List<GearSet.WeightedItemStack> boots;
   protected final List<GearSet.WeightedItemStack> leggings;
   protected final List<GearSet.WeightedItemStack> chestplates;
   protected final List<GearSet.WeightedItemStack> helmets;
   protected final List<String> tags;
   protected transient Map<EquipmentSlot, List<GearSet.WeightedItemStack>> slotToStacks;

   public GearSet(
      int weight,
      float quality,
      List<GearSet.WeightedItemStack> mainhands,
      List<GearSet.WeightedItemStack> offhands,
      List<GearSet.WeightedItemStack> boots,
      List<GearSet.WeightedItemStack> leggings,
      List<GearSet.WeightedItemStack> chestplates,
      List<GearSet.WeightedItemStack> helmets,
      List<String> tags
   ) {
      this.weight = weight;
      this.quality = quality;
      this.mainhands = mainhands;
      this.offhands = offhands;
      this.boots = boots;
      this.leggings = leggings;
      this.chestplates = chestplates;
      this.helmets = helmets;
      this.tags = tags;
   }

   public int getWeight() {
      return (R)this.weight;
   }

   public float getQuality() {
      return (R)this.quality;
   }

   public LivingEntity apply(LivingEntity entity) {
      for (EquipmentSlot slot : EquipmentSlot.values()) {
         getRandomStack(this.getPotentials(slot), entity.f_19796_).ifPresent(s -> s.apply(entity, slot));
      }

      return entity;
   }

   public List<GearSet.WeightedItemStack> getPotentials(EquipmentSlot slot) {
      return switch (slot) {
         case MAINHAND -> this.mainhands;
         case OFFHAND -> this.offhands;
         case FEET -> this.boots;
         case LEGS -> this.leggings;
         case CHEST -> this.chestplates;
         case HEAD -> this.helmets;
         default -> throw new IncompatibleClassChangeError();
      };
   }

   public PSerializer<? extends GearSet> getSerializer() {
      return SERIALIZER;
   }

   public static Optional<GearSet.WeightedItemStack> getRandomStack(List<GearSet.WeightedItemStack> stacks, RandomSource random) {
      return stacks.isEmpty() ? Optional.empty() : Optional.of((GearSet.WeightedItemStack)WeightedRandom.m_216822_(random, stacks).get());
   }

   public static class SetPredicate implements Predicate<GearSet> {
      public static final Codec<GearSet.SetPredicate> CODEC = ExtraCodecs.m_184405_(s -> s.key, GearSet.SetPredicate::new);
      protected final String key;
      protected final Predicate<GearSet> internal;

      public SetPredicate(String key) {
         this.key = key;
         if (key.startsWith("#")) {
            String tag = key.substring(1);
            this.internal = t -> t.tags.contains(tag);
         } else {
            ResourceLocation id = new ResourceLocation(key);
            this.internal = t -> t.id.equals(id);
         }
      }

      public boolean test(GearSet t) {
         return this.internal.test(t);
      }

      @Override
      public String toString() {
         return "SetPredicate[" + this.key + "]";
      }
   }

   public static class WeightedItemStack extends Weighted {
      public static final Codec<GearSet.WeightedItemStack> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  ItemAdapter.CODEC.fieldOf("stack").forGetter(w -> w.stack),
                  Codec.INT.fieldOf("weight").forGetter(w -> w.weight),
                  Codec.FLOAT.optionalFieldOf("drop_chance", -1.0F).forGetter(w -> w.dropChance)
               )
               .apply(inst, GearSet.WeightedItemStack::new)
      );
      public static final Codec<List<GearSet.WeightedItemStack>> LIST_CODEC = CODEC.listOf();
      final ItemStack stack;
      final float dropChance;

      public WeightedItemStack(ItemStack stack, int weight, float dropChance) {
         super(weight);
         this.stack = stack;
         this.dropChance = dropChance;
      }

      public ItemStack getStack() {
         return this.stack;
      }

      @Override
      public String toString() {
         return "Stack: " + this.stack.toString() + " @ Weight: " + this.weight;
      }

      public void apply(LivingEntity entity, EquipmentSlot slot) {
         entity.m_8061_(slot, this.stack.m_41777_());
         if (this.dropChance >= 0.0F && entity instanceof Mob mob) {
            mob.m_21409_(slot, this.dropChance);
         }
      }
   }
}
