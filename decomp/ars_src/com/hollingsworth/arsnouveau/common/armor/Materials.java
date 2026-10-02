package com.hollingsworth.arsnouveau.common.armor;

import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class Materials {
   public static final Materials.ModdedArmorMaterial LIGHT = new Materials.ModdedArmorMaterial(
      "an_light", 25, new int[]{1, 3, 5, 2}, 30, SoundEvents.f_11678_, 0.0F, () -> Ingredient.m_43929_(new ItemLike[]{ItemsRegistry.MAGE_FIBER})
   );
   public static final Materials.ModdedArmorMaterial MEDIUM = new Materials.ModdedArmorMaterial(
      "an_medium", 25, new int[]{2, 5, 6, 2}, 30, SoundEvents.f_11678_, 0.0F, () -> Ingredient.m_43929_(new ItemLike[]{ItemsRegistry.MAGE_FIBER})
   );
   public static final Materials.ModdedArmorMaterial HEAVY = new Materials.ModdedArmorMaterial(
      "an_heavy", 33, new int[]{3, 6, 8, 3}, 30, SoundEvents.f_11678_, 2.0F, () -> Ingredient.m_43929_(new ItemLike[]{ItemsRegistry.MAGE_FIBER})
   );

   @Deprecated(
      forRemoval = true
   )
   public static class ModdedArmorMaterial implements ArmorMaterial {
      private static final int[] Max_Damage_Array = new int[]{13, 15, 16, 11};
      private final String name;
      private final int maxDamageFactor;
      private final int[] damageReductionAmountArray;
      private final int enchantability;
      private final SoundEvent soundEvent;
      private final float toughness;
      private final LazyLoadedValue<Ingredient> repairMaterial;

      public ModdedArmorMaterial(
         String name,
         int maxDamageFactor,
         int[] damageReductionAmountArray,
         int enchantability,
         SoundEvent soundEvent,
         float toughness,
         Supplier<Ingredient> supplier
      ) {
         this.name = name;
         this.maxDamageFactor = maxDamageFactor;
         this.damageReductionAmountArray = damageReductionAmountArray;
         this.enchantability = enchantability;
         this.soundEvent = soundEvent;
         this.toughness = toughness;
         this.repairMaterial = new LazyLoadedValue(supplier);
      }

      public int m_7366_(EquipmentSlot slotIn) {
         return Max_Damage_Array[slotIn.m_20749_()] * this.maxDamageFactor;
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
         return (Ingredient)this.repairMaterial.m_13971_();
      }

      public String m_6082_() {
         return this.name;
      }

      public float m_6651_() {
         return this.toughness;
      }

      public float m_6649_() {
         return 0.0F;
      }
   }
}
