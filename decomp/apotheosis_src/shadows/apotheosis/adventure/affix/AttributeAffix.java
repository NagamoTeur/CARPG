package shadows.apotheosis.adventure.affix;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.codec.EnumCodec;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class AttributeAffix extends Affix {
   public static final Codec<AttributeAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               ForgeRegistries.ATTRIBUTES.getCodec().fieldOf("attribute").forGetter(a -> a.attribute),
               new EnumCodec(Operation.class).fieldOf("operation").forGetter(a -> a.operation),
               GemBonus.VALUES_CODEC.fieldOf("values").forGetter(a -> a.values),
               LootCategory.SET_CODEC.fieldOf("types").forGetter(a -> a.types)
            )
            .apply(inst, AttributeAffix::new)
   );
   public static final PSerializer<AttributeAffix> SERIALIZER = PSerializer.fromCodec("Attribute Affix", CODEC);
   protected final Attribute attribute;
   protected final Operation operation;
   protected final Map<LootRarity, StepFunction> values;
   protected final Set<LootCategory> types;
   protected final transient Map<LootRarity, AttributeAffix.ModifierInst> modifiers;

   public AttributeAffix(Attribute attr, Operation op, Map<LootRarity, StepFunction> values, Set<LootCategory> types) {
      super(AffixType.STAT);
      this.attribute = attr;
      this.operation = op;
      this.values = values;
      this.types = types;
      this.modifiers = values.entrySet()
         .stream()
         .map(entry -> Pair.of(entry.getKey(), new AttributeAffix.ModifierInst(attr, op, entry.getValue())))
         .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
   }

   @Override
   public void addModifiers(ItemStack stack, LootRarity rarity, float level, EquipmentSlot type, BiConsumer<Attribute, AttributeModifier> map) {
      LootCategory cat = LootCategory.forItem(stack);
      if (cat.isNone()) {
         AdventureModule.LOGGER
            .debug(
               "Attempted to apply the attributes of affix {} on item {}, but it is not an affix-compatible item!", this.getId(), stack.m_41786_().getString()
            );
      } else {
         AttributeAffix.ModifierInst modif = this.modifiers.get(rarity);
         if (modif.attr == null) {
            AdventureModule.LOGGER.debug("The affix {} has attempted to apply a null attribute modifier to {}!", this.getId(), stack.m_41786_().getString());
         } else {
            for (EquipmentSlot slot : cat.getSlots()) {
               if (slot == type) {
                  map.accept(modif.attr, modif.build(stack, this.getId(), level));
               }
            }
         }
      }
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat.isNone() ? false : (this.types.isEmpty() || this.types.contains(cat)) && this.modifiers.containsKey(rarity);
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }

   public static record ModifierInst(Attribute attr, Operation op, StepFunction valueFactory) {
      private static UUID getOrCreateUUID(ItemStack stack, ResourceLocation id) {
         CompoundTag tag = stack.m_41737_("affix_data");
         return GemItem.getOrCreateUUIDs(tag, 1).get(0);
      }

      public AttributeModifier build(ItemStack stack, ResourceLocation id, float level) {
         return new AttributeModifier(getOrCreateUUID(stack, id), "affix:" + id, (double)this.valueFactory.get(level), this.op);
      }
   }
}
