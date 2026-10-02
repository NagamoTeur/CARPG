package shadows.apotheosis.ench.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.phys.Vec3;
import shadows.apotheosis.ench.EnchModule;

public class ReboundingEnchant extends Enchantment {
   public ReboundingEnchant() {
      super(Rarity.RARE, EnchModule.CORE_ARMOR, new EquipmentSlot[]{EquipmentSlot.CHEST, EquipmentSlot.LEGS});
   }

   public int m_6586_() {
      return 3;
   }

   public int m_6183_(int level) {
      return 22 + (level - 1) * 18;
   }

   public int m_6175_(int level) {
      return 200;
   }

   public void m_7675_(LivingEntity user, Entity attacker, int level) {
      if (attacker != null && user.m_20280_(attacker) <= 4.0 && attacker.m_6072_()) {
         level = EnchantmentHelper.m_44836_(this, user);
         Vec3 vec = new Vec3(attacker.m_20185_() - user.m_20185_(), attacker.m_20186_() - user.m_20186_(), attacker.m_20189_() - user.m_20189_());
         attacker.m_5997_(vec.f_82479_ * 2.0 * (double)level, vec.f_82480_ * 3.0 * (double)level, vec.f_82481_ * 2.0 * (double)level);
      }
   }
}
