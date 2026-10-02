package shadows.apotheosis.ench.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import shadows.apotheosis.ench.EnchModule;

public class ReflectiveEnchant extends Enchantment {
   public ReflectiveEnchant() {
      super(Rarity.RARE, EnchModule.SHIELD, new EquipmentSlot[]{EquipmentSlot.OFFHAND, EquipmentSlot.MAINHAND});
   }

   public int m_6183_(int enchantmentLevel) {
      return enchantmentLevel * 18;
   }

   public int m_6175_(int enchantmentLevel) {
      return 200;
   }

   public int m_6586_() {
      return 5;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return super.canApplyAtEnchantingTable(stack) || stack.canPerformAction(ToolActions.SHIELD_BLOCK);
   }

   public void reflect(ShieldBlockEvent e) {
      LivingEntity user = e.getEntity();
      Entity attacker = e.getDamageSource().m_7640_();
      ItemStack shield = user.m_21211_();
      int level = shield.getEnchantmentLevel(this);
      if (level > 0 && user.f_19853_.f_46441_.m_188503_(Math.max(2, 7 - level)) == 0) {
         DamageSource src = user instanceof Player plr ? DamageSource.m_19344_(plr).m_19389_().m_19380_() : DamageSource.f_19319_;
         if (attacker instanceof LivingEntity livingAttacker) {
            livingAttacker.m_6469_(src, (float)level * 0.15F * e.getBlockedDamage());
            shield.m_41622_(10, user, ent -> ent.m_21166_(EquipmentSlot.OFFHAND));
         }
      }
   }
}
