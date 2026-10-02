package shadows.apotheosis.ench.replacements;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.ProtectionEnchantment.Type;

public class DefenseEnchant extends ProtectionEnchantment {
   public DefenseEnchant(Rarity rarity, Type type, EquipmentSlot... slots) {
      super(rarity, type, slots);
   }

   public int m_7205_(int level, DamageSource source) {
      if (source.m_19378_()) {
         return 0;
      } else if (this.f_45124_ == Type.ALL) {
         return level;
      } else if (this.f_45124_ == Type.FIRE && source.m_19384_()) {
         return level;
      } else if (this.f_45124_ == Type.FALL && source == DamageSource.f_19315_) {
         return level * 3;
      } else if (this.f_45124_ == Type.EXPLOSION && source.m_19372_()) {
         return level * 2;
      } else {
         return this.f_45124_ == Type.PROJECTILE && source.m_19360_() ? level : 0;
      }
   }

   public boolean m_5975_(Enchantment ench) {
      if (this == Enchantments.f_44967_ || this == Enchantments.f_44965_) {
         return ench != this;
      } else if (ench instanceof ProtectionEnchantment pEnch) {
         return ench == this ? false : pEnch.f_45124_ == Type.ALL || pEnch.f_45124_ == Type.FALL;
      } else {
         return ench != this;
      }
   }
}
