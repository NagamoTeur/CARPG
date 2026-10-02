package com.aizistral.enigmaticlegacy.enchantments;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.objects.RegisteredMeleeAttack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class SlayerEnchantment extends Enchantment {
   public SlayerEnchantment(EquipmentSlot... slots) {
      super(Rarity.COMMON, EnchantmentCategory.WEAPON, slots);
   }

   public int m_6183_(int enchantmentLevel) {
      return 5 + (enchantmentLevel - 1) * 8;
   }

   public int m_6175_(int enchantmentLevel) {
      return this.m_6183_(enchantmentLevel) + 20;
   }

   public int m_6586_() {
      return 5;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return this.m_6081_(stack) && super.canApplyAtEnchantingTable(stack);
   }

   public boolean m_5975_(Enchantment ench) {
      return !(ench instanceof DamageEnchantment) && super.m_5975_(ench);
   }

   public boolean m_6081_(ItemStack stack) {
      return OmniconfigHandler.isItemEnabled(this) && (stack.m_41720_() instanceof AxeItem || stack.canApplyAtEnchantingTable(this));
   }

   public boolean isAllowedOnBooks() {
      return OmniconfigHandler.isItemEnabled(this);
   }

   public boolean m_6592_() {
      return OmniconfigHandler.isItemEnabled(this);
   }

   public float bonusDamageByCreature(LivingEntity attacker, LivingEntity living, int level) {
      float calculated = living instanceof Monster ? (float)level * 1.5F : 0.0F;
      return calculated * RegisteredMeleeAttack.getRegisteredAttackStregth(attacker);
   }
}
