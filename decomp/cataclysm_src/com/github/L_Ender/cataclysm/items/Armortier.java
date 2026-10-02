package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum Armortier implements ArmorMaterial {
   IGNITIUM(new int[]{6, 11, 9, 6}, 4.0F, 45, 15, 0.15F, SoundEvents.f_11679_, ModItems.IGNITIUM_INGOT),
   CURSIUM(new int[]{5, 10, 8, 5}, 4.0F, 45, 15, 0.05F, SoundEvents.f_11679_, ModItems.CURSIUM_INGOT),
   CRAB(new int[]{3, 6, 8, 3}, 2.0F, 30, 15, 0.1F, SoundEvents.f_11677_, ModItems.AMETHYST_CRAB_SHELL),
   BONE_REPTILE(new int[]{4, 7, 11, 6}, 3.0F, 35, 15, 0.2F, SoundEvents.f_11679_, ModItems.ANCIENT_METAL_INGOT);

   private static final int[] DURABILITY_ARRAY = new int[]{13, 15, 16, 11};
   private final int durability;
   private final int enchantability;
   private final int[] dmgReduction;
   private final float toughness;
   private final float knockbackResistance;
   private final SoundEvent sound;
   private final Supplier<Item> repairMaterial;

   private Armortier(
      int[] dmgReduction, float toughness, int durability, int enchantability, float knockbackResistance, SoundEvent sound, Supplier<Item> repairMaterial
   ) {
      this.durability = durability;
      this.dmgReduction = dmgReduction;
      this.enchantability = enchantability;
      this.toughness = toughness;
      this.knockbackResistance = knockbackResistance;
      this.sound = sound;
      this.repairMaterial = repairMaterial;
   }

   public int m_7366_(EquipmentSlot type) {
      return DURABILITY_ARRAY[type.m_20749_()] * this.durability;
   }

   public int m_7365_(EquipmentSlot type) {
      return this.dmgReduction[type.m_20749_()];
   }

   public int m_6646_() {
      return this.enchantability;
   }

   public SoundEvent m_7344_() {
      return this.sound;
   }

   public Ingredient m_6230_() {
      return Ingredient.m_43929_(new ItemLike[]{(ItemLike)this.repairMaterial.get()});
   }

   public String m_6082_() {
      return this.toString().toLowerCase();
   }

   public float m_6651_() {
      return this.toughness;
   }

   public float m_6649_() {
      return this.knockbackResistance;
   }
}
