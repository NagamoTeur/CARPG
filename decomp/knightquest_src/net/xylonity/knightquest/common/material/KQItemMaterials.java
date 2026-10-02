package net.xylonity.knightquest.common.material;

import dev.xylonity.knightlib.compat.registry.KnightLibItems;
import java.util.function.Supplier;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public enum KQItemMaterials implements Tier {
   PALADIN(4, 2350, 0.5F, 10.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   NAIL(4, 2120, 0.5F, 9.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   UCHIGATANA(4, 2031, 0.5F, 8.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   KUKRI(4, 400, 0.5F, 4.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   KHOPESH(4, 2120, 0.5F, 9.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   CLEAVER(4, 2031, 0.5F, 12.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   WATER_SWORD(4, 850, 0.5F, 1.8F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   STEEL_SWORD(4, 300, 0.5F, 1.5F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   WATER_AXE(4, 850, 6.0F, 1.3F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})),
   STEEL_AXE(4, 300, 6.5F, 4.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()}));

   private final int miningLevel;
   private final int itemDurability;
   private final float miningSpeed;
   private final float attackDamage;
   private final int enchantability;
   private final Supplier<Ingredient> repairIngredient;

   private KQItemMaterials(
      int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient
   ) {
      this.miningLevel = miningLevel;
      this.itemDurability = itemDurability;
      this.miningSpeed = miningSpeed;
      this.attackDamage = attackDamage;
      this.enchantability = enchantability;
      this.repairIngredient = repairIngredient;
   }

   public int m_6609_() {
      return this.itemDurability;
   }

   public float m_6624_() {
      return this.miningSpeed;
   }

   public float m_6631_() {
      return this.attackDamage;
   }

   public int m_6604_() {
      return this.miningLevel;
   }

   public int m_6601_() {
      return this.enchantability;
   }

   @NotNull
   public Ingredient m_6282_() {
      return this.repairIngredient.get();
   }
}
