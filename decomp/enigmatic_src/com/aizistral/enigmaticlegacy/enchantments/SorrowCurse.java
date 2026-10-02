package com.aizistral.enigmaticlegacy.enchantments;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class SorrowCurse extends Enchantment {
   private final List<MobEffect> debuffList = new ArrayList<>();

   public SorrowCurse(EquipmentSlot... slots) {
      super(Rarity.RARE, EnchantmentCategory.ARMOR, slots);
      this.debuffList.add(MobEffects.f_19610_);
      this.debuffList.add(MobEffects.f_19604_);
      this.debuffList.add(MobEffects.f_19613_);
      this.debuffList.add(MobEffects.f_19597_);
      this.debuffList.add(MobEffects.f_19599_);
      this.debuffList.add(MobEffects.f_19612_);
   }

   public void maybeApplyDebuff(Player player, float damage) {
      if (player.m_217043_().m_188500_() < 0.1) {
         float severity = damage > 4.0F ? damage / 4.0F : 1.0F;
         severity *= 0.5F + player.m_217043_().m_188501_();
         int amplifier = (int)(severity / 2.0F);
         if (amplifier > 3) {
            amplifier = 3;
         }

         MobEffect debuff = this.debuffList.get(player.m_217043_().m_188503_(this.debuffList.size()));
         MobEffectInstance instance = new MobEffectInstance(debuff, (int)(300.0F * severity), amplifier, false, true);
         player.m_7292_(instance);
      }
   }

   public int m_6183_(int enchantmentLevel) {
      return 25;
   }

   public int m_6175_(int enchantmentLevel) {
      return 50;
   }

   public int m_44702_() {
      return 1;
   }

   public int m_6586_() {
      return 1;
   }

   public boolean m_6081_(ItemStack stack) {
      return OmniconfigHandler.isItemEnabled(this) && super.m_6081_(stack);
   }

   public boolean m_6591_() {
      return true;
   }

   public boolean m_6589_() {
      return true;
   }

   public boolean isAllowedOnBooks() {
      return OmniconfigHandler.isItemEnabled(this);
   }

   public boolean m_6592_() {
      return true;
   }

   protected boolean m_5975_(Enchantment ench) {
      return super.m_5975_(ench);
   }
}
