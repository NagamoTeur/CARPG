package com.rolfmao.upgradednetherite.utils.check;

import com.rolfmao.upgradedcore.utils.SetArmorUtil;
import com.rolfmao.upgradedcore.utils.morecheck.MoreGoldUtil;
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

public class GoldUtil {
   public static int setGoldArmor() {
      return UpgradedNetheriteConfig.UpgradedPlayerGoldArmorRequireSet + MoreGoldUtil.setGoldArmor();
   }

   public static boolean isWearingGoldArmor(Player player) {
      Integer intSet = 0;
      intSet = intSet + SetArmorUtil.isWearingArmor(player, ModArmorMaterial.GOLD_UPGRADED_NETHERITE);
      intSet = intSet + MoreGoldUtil.intWearingGoldArmor(player);
      return intSet >= setGoldArmor();
   }

   public static boolean isHorseWearingGoldArmor(Horse horse) {
      return horse.m_30722_().m_41720_() == ((HorseArmorItem)ModItems.GOLD_UPGRADED_NETHERITE_ARMOR_HORSE.get()).m_5456_()
         || MoreGoldUtil.isHorseWearingGoldArmor(horse);
   }

   public static boolean isGoldArmor(ItemStack itemStack) {
      return itemStack.m_41720_() instanceof ArmorItem && ((ArmorItem)itemStack.m_41720_()).m_40401_() == ModArmorMaterial.GOLD_UPGRADED_NETHERITE
         || MoreGoldUtil.isGoldArmor(itemStack);
   }

   public static boolean isGoldToolOrWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_AXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreGoldUtil.isGoldToolOrWeapon(itemStack);
   }

   public static boolean isGoldWeapon(ItemStack itemStack) {
      return isGoldMeleeWeapon(itemStack) || isGoldRangedWeapon(itemStack);
   }

   public static boolean isGoldMeleeWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_AXE.get()))
         || MoreGoldUtil.isGoldWeapon(itemStack);
   }

   public static boolean isGoldRangedWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreGoldUtil.isGoldRangedWeapon(itemStack);
   }

   public static boolean isGoldTool(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_AXE.get()))
         || MoreGoldUtil.isGoldTool(itemStack);
   }

   public static boolean isGoldProjectile(Projectile projectile) {
      return (
            projectile instanceof Arrow
               || projectile instanceof SpectralArrow
               || projectile instanceof FireworkRocketEntity
               || MoreGoldUtil.isGoldProjectile(projectile)
         )
         && projectile.m_19880_().contains("GoldUpgradedNetheriteBow");
   }

   public static boolean isGoldShield(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_SHIELD.get())) || MoreGoldUtil.isGoldShield(itemStack);
   }

   public static boolean isGoldHorseArmor(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_ARMOR_HORSE.get()))
         || MoreGoldUtil.isGoldHorseArmor(itemStack);
   }
}
