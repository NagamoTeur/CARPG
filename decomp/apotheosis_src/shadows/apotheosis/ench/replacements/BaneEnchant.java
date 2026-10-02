package shadows.apotheosis.ench.replacements;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class BaneEnchant extends DamageEnchantment {
   protected final MobType attrib;

   public BaneEnchant(Rarity rarity, MobType attrib, EquipmentSlot... slots) {
      super(rarity, 0, slots);
      this.attrib = attrib;
   }

   public int m_6183_(int level) {
      return this.attrib == MobType.f_21640_ ? 1 + (level - 1) * 11 : 5 + (level - 1) * 8;
   }

   public int m_6175_(int level) {
      return this.m_6183_(level) + 20;
   }

   public int m_6586_() {
      return 5;
   }

   public float m_7335_(int level, MobType attrib) {
      if (this.attrib == MobType.f_21640_) {
         return 1.0F + (float)level * 0.5F;
      } else {
         return this.attrib == attrib ? (float)level * 1.5F : 0.0F;
      }
   }

   public boolean m_5975_(Enchantment ench) {
      if (this.attrib == MobType.f_21640_) {
         return ench != this;
      } else {
         return ench == Enchantments.f_44977_ ? ench != this : !(ench instanceof BaneEnchant);
      }
   }

   public void m_7677_(LivingEntity user, Entity target, int level) {
      if (target instanceof LivingEntity livingentity && this.attrib != MobType.f_21640_ && livingentity.m_6336_() == this.attrib) {
         int i = 20 + user.m_217043_().m_188503_(10 * level);
         livingentity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, i, 3));
      }
   }
}
