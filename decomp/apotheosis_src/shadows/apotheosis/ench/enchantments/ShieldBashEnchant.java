package shadows.apotheosis.ench.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.common.ToolActions;
import shadows.apotheosis.ench.EnchModule;

public class ShieldBashEnchant extends Enchantment {
   public ShieldBashEnchant() {
      super(Rarity.RARE, EnchModule.SHIELD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6183_(int enchantmentLevel) {
      return 1 + (enchantmentLevel - 1) * 17;
   }

   public int m_6175_(int enchantmentLevel) {
      return this.m_6183_(enchantmentLevel) + 40;
   }

   public int m_6586_() {
      return 4;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return super.canApplyAtEnchantingTable(stack) || stack.canPerformAction(ToolActions.SHIELD_BLOCK);
   }

   public float m_7335_(int pLevel, MobType pType) {
      return 3.5F * (float)pLevel;
   }

   public void m_7677_(LivingEntity user, Entity target, int level) {
      if (target instanceof LivingEntity) {
         ItemStack stack = user.m_21205_();
         if (stack.getEnchantmentLevel(this) == level) {
            stack.m_41622_(Math.max(1, 20 - level), user, e -> e.m_21166_(EquipmentSlot.OFFHAND));
         }
      }
   }

   protected boolean m_5975_(Enchantment pOther) {
      return super.m_5975_(pOther) && !(pOther instanceof DamageEnchantment);
   }
}
