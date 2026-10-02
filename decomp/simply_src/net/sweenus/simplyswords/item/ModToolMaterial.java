package net.sweenus.simplyswords.item;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.sweenus.simplyswords.registry.ItemsRegistry;

public enum ModToolMaterial implements Tier {
   RUNIC(4, 2031, 9.0F, 5.0F, 25, Items.f_42418_),
   UNIQUE(4, 3270, 15.0F, 5.0F, 30, (Item)ItemsRegistry.RUNIC_TABLET.get()),
   ADAMANTITE(4, 1024, 7.0F, 5.0F, 16, Items.f_42415_),
   AEGIS_RED(4, 2170, 8.0F, 6.0F, 25, Items.f_42415_),
   AEGIS_WHITE(4, 2070, 10.0F, 5.0F, 25, Items.f_42415_),
   AQUARIUM(2, 455, 6.5F, 2.0F, 12, Items.f_42415_),
   BANGLUM(2, 260, 11.0F, 2.0F, 1, Items.f_42415_),
   BRONZE(2, 354, 5.5F, 2.5F, 14, Items.f_42415_),
   CARMOT(3, 1130, 11.5F, 3.0F, 42, Items.f_42415_),
   CELESTIUM(5, 2270, 16.9F, 6.0F, 26, Items.f_42415_),
   COPPER(1, 125, 4.5F, 1.0F, 8, Items.f_151052_),
   DURASTEEL(3, 800, 7.1F, 3.5F, 12, Items.f_42415_),
   GILDED_MIDAS_GOLD(3, 999, 13.0F, 4.0F, 30, Items.f_42415_),
   HALLOWED(4, 1984, 12.0F, 5.0F, 20, Items.f_42415_),
   KYBER(3, 889, 7.0F, 2.5F, 20, Items.f_42415_),
   LEGENDARY_BANGLUM(3, 1040, 12.0F, 4.0F, 2, Items.f_42415_),
   METALLURGIUM(5, 3000, 15.0F, 8.0F, 30, Items.f_42415_),
   MIDAS_GOLD(3, 300, 13.0F, 3.0F, 30, Items.f_42415_),
   MYTHRIL(4, 1564, 13.0F, 3.0F, 22, Items.f_42415_),
   ORICHALCUM(4, 2048, 6.0F, 4.0F, 16, Items.f_42415_),
   OSMIUM(2, 584, 7.0F, 2.0F, 13, Items.f_42415_),
   PALLADIUM(4, 1234, 8.0F, 3.5F, 16, Items.f_42415_),
   PROMETHEUM(3, 1472, 6.0F, 4.0F, 15, Items.f_42415_),
   QUADRILLUM(2, 321, 5.0F, 2.5F, 8, Items.f_42415_),
   RUNITE(3, 1337, 8.9F, 3.3F, 17, Items.f_42415_),
   STAR_PLATINUM(4, 1300, 9.0F, 4.0F, 18, Items.f_42415_),
   STEEL(2, 600, 6.5F, 2.5F, 12, Items.f_42415_),
   STORMYX(3, 1305, 8.0F, 3.5F, 20, Items.f_42415_),
   GOBBER(5, 3800, 9.0F, 9.0F, 20, Items.f_42415_),
   GOBBER_NETHER(6, 5200, 12.0F, 9.0F, 25, Items.f_42415_),
   GOBBER_END(7, 8000, 14.0F, 9.0F, 30, Items.f_42415_);

   private final int miningLevel;
   private final int itemDurability;
   private final float miningSpeed;
   private final float attackDamage;
   private final int enchantability;
   private final Supplier<Ingredient> repairIngredient;

   private ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Item... repairIngredient) {
      this.miningLevel = miningLevel;
      this.itemDurability = itemDurability;
      this.miningSpeed = miningSpeed;
      this.attackDamage = attackDamage;
      this.enchantability = enchantability;
      this.repairIngredient = Suppliers.memoize(() -> Ingredient.m_43929_(repairIngredient));
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

   public Ingredient m_6282_() {
      return this.repairIngredient.get();
   }
}
