package com.aizistral.enigmaticlegacy.api.materials;

import com.aizistral.etherium.core.IEtheriumConfig;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum EnigmaticArmorMaterials implements ArmorMaterial {
   ETHERIUM("enigmaticlegacy:etherium", 132, new int[]{4, 7, 9, 4}, 24, SoundEvents.f_11677_, 4.0F, 0.0F, () -> getEtheriumConfig().getRepairMaterial());

   private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
   private static IEtheriumConfig etheriumConfig;
   private final String name;
   private final int maxDamageFactor;
   private final int[] damageReductionAmountArray;
   private final int enchantability;
   private final SoundEvent soundEvent;
   private final float toughness;
   private final Supplier<Ingredient> repairMaterial;
   private final float knockbackResistance;

   private EnigmaticArmorMaterials(
      String name,
      int maxDamageFactor,
      int[] damageReductionAmountArray,
      int enchantability,
      SoundEvent soundEvent,
      float toughness,
      float knockbackResistance,
      Supplier<Ingredient> repairMaterial
   ) {
      this.name = name;
      this.maxDamageFactor = maxDamageFactor;
      this.damageReductionAmountArray = damageReductionAmountArray;
      this.enchantability = enchantability;
      this.soundEvent = soundEvent;
      this.toughness = toughness;
      this.repairMaterial = repairMaterial;
      this.knockbackResistance = knockbackResistance;
   }

   public int m_7366_(EquipmentSlot slot) {
      return MAX_DAMAGE_ARRAY[slot.m_20749_()] * this.maxDamageFactor;
   }

   public int m_7365_(EquipmentSlot slot) {
      return this.damageReductionAmountArray[slot.m_20749_()];
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
      return this.toughness;
   }

   public float m_6649_() {
      return this.knockbackResistance;
   }

   public static IEtheriumConfig getEtheriumConfig() {
      return etheriumConfig;
   }

   public static void setEtheriumConfig(IEtheriumConfig config) {
      etheriumConfig = Objects.requireNonNull(config);
   }
}
