package com.rolfmao.upgradednetherite.utils.check;

import com.rolfmao.upgradedcore.utils.SetArmorUtil;
import com.rolfmao.upgradedcore.utils.morecheck.MorePoisonUtil;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
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

public class PoisonUtil {
   public static int setPoisonArmor() {
      return UpgradedNetheriteConfig.UpgradedPlayerPoisonArmorRequireSet + MorePoisonUtil.setPoisonArmor();
   }

   public static boolean isWearingPoisonArmor(Player player) {
      Integer intSet = 0;
      intSet = intSet + SetArmorUtil.isWearingArmor(player, ModArmorMaterial.POISON_UPGRADED_NETHERITE);
      intSet = intSet + MorePoisonUtil.intWearingPoisonArmor(player);
      return intSet >= setPoisonArmor();
   }

   public static boolean isHorseWearingPoisonArmor(Horse horse) {
      return horse.m_30722_().m_41720_() == ((HorseArmorItem)ModItems.POISON_UPGRADED_NETHERITE_ARMOR_HORSE.get()).m_5456_()
         || MorePoisonUtil.isHorseWearingPoisonArmor(horse);
   }

   public static boolean isPoisonArmor(ItemStack itemStack) {
      return itemStack.m_41720_() instanceof ArmorItem && ((ArmorItem)itemStack.m_41720_()).m_40401_() == ModArmorMaterial.POISON_UPGRADED_NETHERITE
         || MorePoisonUtil.isPoisonArmor(itemStack);
   }

   public static boolean isPoisonToolOrWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_AXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MorePoisonUtil.isPoisonToolOrWeapon(itemStack);
   }

   public static boolean isPoisonWeapon(ItemStack itemStack) {
      return isPoisonMeleeWeapon(itemStack) || isPoisonRangedWeapon(itemStack);
   }

   public static boolean isPoisonMeleeWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_AXE.get()))
         || MorePoisonUtil.isPoisonWeapon(itemStack);
   }

   public static boolean isPoisonRangedWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MorePoisonUtil.isPoisonRangedWeapon(itemStack);
   }

   public static boolean isPoisonTool(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_AXE.get())) || MorePoisonUtil.isPoisonTool(itemStack);
   }

   public static boolean isPoisonProjectile(Projectile projectile) {
      return (
            projectile instanceof Arrow
               || projectile instanceof SpectralArrow
               || projectile instanceof FireworkRocketEntity
               || MorePoisonUtil.isPoisonProjectile(projectile)
         )
         && projectile.m_19880_().contains("PoisonUpgradedNetheriteBow");
   }

   public static boolean isPoisonShield(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_SHIELD.get()))
         || MorePoisonUtil.isPoisonShield(itemStack);
   }

   public static boolean isPoisonHorseArmor(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.POISON_UPGRADED_NETHERITE_ARMOR_HORSE.get()))
         || MorePoisonUtil.isPoisonHorseArmor(itemStack);
   }
}
