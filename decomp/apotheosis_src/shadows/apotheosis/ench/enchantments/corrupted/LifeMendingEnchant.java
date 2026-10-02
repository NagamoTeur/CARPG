package shadows.apotheosis.ench.enchantments.corrupted;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.ModList;
import shadows.apotheosis.adventure.compat.AdventureCuriosCompat;

public class LifeMendingEnchant extends Enchantment {
   private static final EquipmentSlot[] SLOTS = EquipmentSlot.values();

   public LifeMendingEnchant() {
      super(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.values());
   }

   public int m_6183_(int level) {
      return 65 + (level - 1) * 35;
   }

   public int m_6175_(int level) {
      return this.m_6183_(level) + 50;
   }

   public int m_6586_() {
      return 3;
   }

   public boolean m_6589_() {
      return true;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return super.canApplyAtEnchantingTable(stack) || stack.canPerformAction(ToolActions.SHIELD_BLOCK);
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_RED);
   }

   private boolean lifeMend(LivingHealEvent e, ItemStack stack) {
      if (!stack.m_41619_() && stack.m_41768_()) {
         int level = stack.getEnchantmentLevel(this);
         if (level <= 0) {
            return false;
         } else {
            float cost = 1.0F / (float)(1 << level - 1);
            int maxRestore = Math.min(Mth.m_14143_(e.getAmount() / cost), stack.m_41773_());
            e.setAmount(e.getAmount() - (float)maxRestore * cost);
            stack.m_41721_(stack.m_41773_() - maxRestore);
            return true;
         }
      } else {
         return false;
      }
   }

   public void lifeMend(LivingHealEvent e) {
      if (!e.getEntity().f_19853_.f_46443_) {
         float amt = e.getAmount();
         if (!(amt <= 0.0F)) {
            for (EquipmentSlot slot : SLOTS) {
               ItemStack stack = e.getEntity().m_6844_(slot);
               if (this.lifeMend(e, stack)) {
                  return;
               }
            }

            if (ModList.get().isLoaded("curios")) {
               for (ItemStack stack : AdventureCuriosCompat.getLifeMendingCurios(e.getEntity())) {
                  if (this.lifeMend(e, stack)) {
                     return;
                  }
               }
            }
         }
      }
   }
}
