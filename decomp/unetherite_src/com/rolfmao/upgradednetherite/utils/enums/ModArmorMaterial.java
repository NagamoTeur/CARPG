package com.rolfmao.upgradednetherite.utils.enums;

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum ModArmorMaterial implements ArmorMaterial {
   GOLD_UPGRADED_NETHERITE(
      "upgradednetherite:gold_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   FIRE_UPGRADED_NETHERITE(
      "upgradednetherite:fire_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   ENDER_UPGRADED_NETHERITE(
      "upgradednetherite:ender_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   WATER_UPGRADED_NETHERITE(
      "upgradednetherite:water_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   WITHER_UPGRADED_NETHERITE(
      "upgradednetherite:wither_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   POISON_UPGRADED_NETHERITE(
      "upgradednetherite:poison_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   PHANTOM_UPGRADED_NETHERITE(
      "upgradednetherite:phantom_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   FEATHER_UPGRADED_NETHERITE(
      "upgradednetherite:feather_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   CORRUPT_UPGRADED_NETHERITE(
      "upgradednetherite:corrupt_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   ),
   ECHO_UPGRADED_NETHERITE(
      "upgradednetherite:echo_upgraded_netherite",
      37,
      new int[]{3, 6, 8, 3},
      15,
      SoundEvents.f_11679_,
      3.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
      0.1F
   );

   private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
   private final String name;
   private final int maxDamageFactor;
   private final int[] damageReductionAmountArray;
   private final int enchantability;
   private final SoundEvent soundEvent;
   private final float thoughness;
   private final Supplier<Ingredient> repairMaterial;
   private final float knockbackResistance;

   private ModArmorMaterial(
      String name,
      int maxDamageFactor,
      int[] damageReductionAmountArray,
      int enchantability,
      SoundEvent soundEvent,
      float thoughness,
      Supplier<Ingredient> repairMaterial,
      float knockbackResistance
   ) {
      this.name = name;
      this.maxDamageFactor = maxDamageFactor;
      this.damageReductionAmountArray = damageReductionAmountArray;
      this.enchantability = enchantability;
      this.soundEvent = soundEvent;
      this.thoughness = thoughness;
      this.repairMaterial = repairMaterial;
      this.knockbackResistance = knockbackResistance;
   }

   public int m_7366_(EquipmentSlot slotIn) {
      return MAX_DAMAGE_ARRAY[slotIn.m_20749_()] * this.maxDamageFactor;
   }

   public int m_7365_(EquipmentSlot slotIn) {
      return this.damageReductionAmountArray[slotIn.m_20749_()];
   }

   public int m_6646_() {
      return this.enchantability;
   }

   public SoundEvent m_7344_() {
      return this.soundEvent;
   }

   public Ingredient m_6230_() {
      return this.repairMaterial.get();
   }

   public String m_6082_() {
      return this.name;
   }

   public float m_6651_() {
      return this.thoughness;
   }

   public float m_6649_() {
      return this.knockbackResistance;
   }
}
