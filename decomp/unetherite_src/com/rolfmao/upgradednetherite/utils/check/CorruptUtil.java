package com.rolfmao.upgradednetherite.utils.check;

import com.rolfmao.upgradedcore.utils.SetArmorUtil;
import com.rolfmao.upgradedcore.utils.morecheck.MoreCorruptUtil;
import com.rolfmao.upgradednetherite.init.ModItems;
import com.rolfmao.upgradednetherite.utils.enums.ModArmorMaterial;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class CorruptUtil {
   public static Integer intWearingCorrupt(Player player, Boolean enableHorse) {
      Integer count = 0;
      count = count + SetArmorUtil.isWearingArmor(player, ModArmorMaterial.CORRUPT_UPGRADED_NETHERITE);
      if (enableHorse && player.m_20202_() != null && player.m_20202_() instanceof Horse && isHorseWearingCorruptArmor((Horse)player.m_20202_())) {
         count = count + 1;
      }

      return count;
   }

   public static boolean isHorseWearingCorruptArmor(Horse horse) {
      return horse.m_30722_().m_41720_() == ((HorseArmorItem)ModItems.CORRUPT_UPGRADED_NETHERITE_ARMOR_HORSE.get()).m_5456_();
   }

   public static boolean isCorruptArmor(ItemStack itemStack) {
      return itemStack.m_41720_() instanceof ArmorItem && ((ArmorItem)itemStack.m_41720_()).m_40401_() == ModArmorMaterial.CORRUPT_UPGRADED_NETHERITE;
   }

   public static boolean isCorruptToolOrWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_AXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreCorruptUtil.isCorruptToolOrWeapon(itemStack);
   }

   public static boolean isCorruptWeapon(ItemStack itemStack) {
      return isCorruptMeleeWeapon(itemStack) || isCorruptRangedWeapon(itemStack);
   }

   public static boolean isCorruptMeleeWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_AXE.get()))
         || MoreCorruptUtil.isCorruptWeapon(itemStack);
   }

   public static boolean isCorruptRangedWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreCorruptUtil.isCorruptRangedWeapon(itemStack);
   }

   public static boolean isCorruptTool(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_AXE.get()))
         || MoreCorruptUtil.isCorruptTool(itemStack);
   }

   public static boolean isCorruptProjectile(Projectile projectile) {
      return (
            projectile instanceof Arrow
               || projectile instanceof SpectralArrow
               || projectile instanceof FireworkRocketEntity
               || MoreCorruptUtil.isCorruptProjectile(projectile)
         )
         && projectile.m_19880_().contains("CorruptUpgradedNetheriteBow");
   }

   public static boolean isCorruptShield(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_SHIELD.get()))
         || MoreCorruptUtil.isCorruptShield(itemStack);
   }

   public static boolean isCorruptHorseArmor(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_ARMOR_HORSE.get()))
         || MoreCorruptUtil.isCorruptHorseArmor(itemStack);
   }
}
