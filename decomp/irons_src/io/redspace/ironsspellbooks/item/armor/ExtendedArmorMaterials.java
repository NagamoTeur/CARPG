package io.redspace.ironsspellbooks.item.armor;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum ExtendedArmorMaterials implements IronsExtendedArmorMaterial {
   DIAMOND(
      "diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.f_11673_, 2.0F, 0.0F, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42415_}), new HashMap<>()
   ),
   TARNISHED(
      "tarnished",
      25,
      new int[]{0, 0, 0, 0},
      15,
      SoundEvents.f_11673_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 150.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.MANA_REGEN.get(),
         new AttributeModifier("Mana Regen", 0.25, Operation.MULTIPLY_TOTAL),
         Attributes.f_22281_,
         new AttributeModifier("minus damage", -0.15, Operation.MULTIPLY_TOTAL)
      )
   ),
   DEV(
      "dev",
      25,
      new int[]{0, 0, 0, 20},
      15,
      SoundEvents.f_11673_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42417_}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 10000.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.COOLDOWN_REDUCTION.get(),
         new AttributeModifier("Mana Regen", 0.75, Operation.MULTIPLY_TOTAL),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Mana Regen", 1.0, Operation.MULTIPLY_TOTAL)
      )
   ),
   WANDERING_MAGICIAN(
      "wandering_magician",
      10,
      new int[]{2, 5, 6, 2},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42454_}),
      Map.of((Attribute)AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max Mana", 25.0, Operation.ADDITION))
   ),
   PUMPKIN(
      "pumpkin",
      33,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11680_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42129_}),
      Map.of((Attribute)AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max Mana", 75.0, Operation.ADDITION))
   ),
   PYROMANCER(
      "pyromancer",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.FIRE_SPELL_POWER.get(),
         new AttributeModifier("Fire Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   ARCHEVOKER(
      "archevoker",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.EVOCATION_SPELL_POWER.get(),
         new AttributeModifier("Evocation Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   CULTIST(
      "cultist",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.BLOOD_SPELL_POWER.get(),
         new AttributeModifier("Blood Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   PRIEST(
      "priest",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.HOLY_SPELL_POWER.get(),
         new AttributeModifier("Holy Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   CRYOMANCER(
      "cryomancer",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.ICE_SPELL_POWER.get(),
         new AttributeModifier("Ice Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   SHADOWWALKER(
      "shadowwalker",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.ENDER_SPELL_POWER.get(),
         new AttributeModifier("Ender Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   PLAGUED(
      "plagued",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.NATURE_SPELL_POWER.get(),
         new AttributeModifier("Nature Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   ELECTROMANCER(
      "electromancer",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11678_,
      0.0F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.MAGIC_CLOTH.get()}),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.LIGHTNING_SPELL_POWER.get(),
         new AttributeModifier("Lightning Power", 0.1, Operation.MULTIPLY_BASE),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   ),
   NETHERITE_BATTLEMAGE(
      "netherite",
      38,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      0.0F,
      () -> Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.INGOTS_NETHERITE),
      Map.of(
         (Attribute)AttributeRegistry.MAX_MANA.get(),
         new AttributeModifier("Max Mana", 125.0, Operation.ADDITION),
         (Attribute)AttributeRegistry.SPELL_POWER.get(),
         new AttributeModifier("Base Power", 0.05, Operation.MULTIPLY_BASE)
      )
   );

   private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
   private final String name;
   private final int durabilityMultiplier;
   private final int[] slotProtections;
   private final int enchantmentValue;
   private final SoundEvent sound;
   private final float toughness;
   private final float knockbackResistance;
   private final LazyLoadedValue<Ingredient> repairIngredient;
   private final Map<Attribute, AttributeModifier> additionalAttributes;

   private ExtendedArmorMaterials(
      String pName,
      int pDurabilityMultiplier,
      int[] pSlotProtections,
      int pEnchantmentValue,
      SoundEvent pSound,
      float pToughness,
      float pKnockbackResistance,
      Supplier<Ingredient> pRepairIngredient,
      Map<Attribute, AttributeModifier> additionalAttributes
   ) {
      this.name = pName;
      this.durabilityMultiplier = pDurabilityMultiplier;
      this.slotProtections = pSlotProtections;
      this.enchantmentValue = pEnchantmentValue;
      this.sound = pSound;
      this.toughness = pToughness;
      this.knockbackResistance = pKnockbackResistance;
      this.repairIngredient = new LazyLoadedValue(pRepairIngredient);
      this.additionalAttributes = additionalAttributes;
   }

   public int m_7366_(EquipmentSlot pSlot) {
      return HEALTH_PER_SLOT[pSlot.m_20749_()] * this.durabilityMultiplier;
   }

   public int m_7365_(EquipmentSlot pSlot) {
      return this.slotProtections[pSlot.m_20749_()];
   }

   public int m_6646_() {
      return this.enchantmentValue;
   }

   public SoundEvent m_7344_() {
      return this.sound;
   }

   public Ingredient m_6230_() {
      return (Ingredient)this.repairIngredient.m_13971_();
   }

   public String m_6082_() {
      return this.name;
   }

   public float m_6651_() {
      return this.toughness;
   }

   @Override
   public Map<Attribute, AttributeModifier> getAdditionalAttributes() {
      return this.additionalAttributes;
   }

   public float m_6649_() {
      return this.knockbackResistance;
   }
}
