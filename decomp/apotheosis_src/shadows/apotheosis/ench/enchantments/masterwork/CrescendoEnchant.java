package shadows.apotheosis.ench.enchantments.masterwork;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;

public class CrescendoEnchant extends Enchantment {
   private static ThreadLocal<ListTag> nbt = new ThreadLocal<>();

   public CrescendoEnchant() {
      super(Rarity.VERY_RARE, EnchantmentCategory.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6586_() {
      return 5;
   }

   public int m_6183_(int level) {
      return 55 + (level - 1) * 30;
   }

   public int m_6175_(int level) {
      return 200;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_GREEN);
   }

   public static void preArrowFired(ItemStack crossbow) {
      if (Apotheosis.enableEnch) {
         int level = crossbow.getEnchantmentLevel((Enchantment)Apoth.Enchantments.CRESCENDO.get());
         if (level > 0) {
            nbt.set(crossbow.m_41783_().m_128437_("ChargedProjectiles", 10).m_6426_());
         }
      }
   }

   public static void onArrowFired(ItemStack crossbow) {
      if (Apotheosis.enableEnch) {
         int level = crossbow.getEnchantmentLevel((Enchantment)Apoth.Enchantments.CRESCENDO.get());
         if (level > 0 && nbt.get() != null) {
            int shots = crossbow.m_41783_().m_128451_("shots");
            if (shots < level) {
               crossbow.m_41783_().m_128405_("shots", shots + 1);
               CrossbowItem.m_40884_(crossbow, true);
               crossbow.m_41783_().m_128365_("ChargedProjectiles", (Tag)nbt.get());
            } else {
               crossbow.m_41783_().m_128473_("shots");
            }

            nbt.set(null);
         }
      }
   }

   public static void markGeneratedArrows(Projectile arrow, ItemStack crossbow) {
      if (Apotheosis.enableEnch) {
         if (crossbow.m_41783_().m_128451_("shots") > 0 && arrow instanceof AbstractArrow arr) {
            arr.f_36705_ = Pickup.CREATIVE_ONLY;
         }
      }
   }
}
