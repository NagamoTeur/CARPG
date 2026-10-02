package dev.latvian.mods.kubejs.misc;

import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class BasicEnchantment extends Enchantment {
   public final EnchantmentBuilder enchantmentBuilder;

   public BasicEnchantment(EnchantmentBuilder b) {
      super(b.rarity, b.category, b.slots);
      this.enchantmentBuilder = b;
   }

   public int m_44702_() {
      return this.enchantmentBuilder.minLevel;
   }

   public int m_6586_() {
      return this.enchantmentBuilder.maxLevel;
   }

   public int m_6183_(int i) {
      return this.enchantmentBuilder.minCost != null ? this.enchantmentBuilder.minCost.get(i) : super.m_6183_(i);
   }

   public int m_6175_(int i) {
      return this.enchantmentBuilder.maxCost != null ? this.enchantmentBuilder.maxCost.get(i) : super.m_6175_(i);
   }

   public int m_7205_(int i, DamageSource damageSource) {
      return this.enchantmentBuilder.damageProtection != null
         ? this.enchantmentBuilder.damageProtection.getDamageProtection(i, damageSource)
         : super.m_7205_(i, damageSource);
   }

   public float m_7335_(int i, MobType mobType) {
      return this.enchantmentBuilder.damageBonus != null
         ? this.enchantmentBuilder.damageBonus.getDamageBonus(i, UtilsJS.getMobTypeId(mobType))
         : super.m_7335_(i, mobType);
   }

   protected boolean m_5975_(Enchantment enchantment) {
      if (enchantment == this) {
         return false;
      } else {
         return this.enchantmentBuilder.checkCompatibility != null
            ? (Boolean)this.enchantmentBuilder.checkCompatibility.apply(KubeJSRegistries.enchantments().getId(enchantment))
            : true;
      }
   }

   public boolean m_6081_(ItemStack itemStack) {
      if (super.m_6081_(itemStack)) {
         return true;
      } else {
         return this.enchantmentBuilder.canEnchant != null ? (Boolean)this.enchantmentBuilder.canEnchant.apply(itemStack) : false;
      }
   }

   public void m_7677_(LivingEntity entity, Entity target, int level) {
      if (this.enchantmentBuilder.postAttack != null) {
         this.enchantmentBuilder.postAttack.apply(entity, target, level);
      }
   }

   public void m_7675_(LivingEntity entity, Entity target, int level) {
      if (this.enchantmentBuilder.postHurt != null) {
         this.enchantmentBuilder.postHurt.apply(entity, target, level);
      }
   }

   public boolean m_6591_() {
      return this.enchantmentBuilder.treasureOnly;
   }

   public boolean m_6589_() {
      return this.enchantmentBuilder.curse;
   }

   public boolean m_6594_() {
      return this.enchantmentBuilder.tradeable;
   }

   public boolean m_6592_() {
      return this.enchantmentBuilder.discoverable;
   }
}
