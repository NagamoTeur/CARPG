package com.rolfmao.upgradednetherite.utils.check;

import com.rolfmao.upgradedcore.utils.SetArmorUtil;
import com.rolfmao.upgradedcore.utils.morecheck.MoreEchoUtil;
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

public class EchoUtil {
   public static int setEchoArmor() {
      return UpgradedNetheriteConfig.UpgradedPlayerEchoArmorRequireSet + MoreEchoUtil.setEchoArmor();
   }

   public static boolean isWearingEchoArmor(Player player) {
      Integer intSet = 0;
      intSet = intSet + SetArmorUtil.isWearingArmor(player, ModArmorMaterial.ECHO_UPGRADED_NETHERITE);
      intSet = intSet + MoreEchoUtil.intWearingEchoArmor(player);
      return intSet >= setEchoArmor();
   }

   public static boolean isHorseWearingEchoArmor(Horse horse) {
      return horse.m_30722_().m_41720_() == ((HorseArmorItem)ModItems.ECHO_UPGRADED_NETHERITE_ARMOR_HORSE.get()).m_5456_()
         || MoreEchoUtil.isHorseWearingEchoArmor(horse);
   }

   public static boolean isEchoArmor(ItemStack itemStack) {
      return itemStack.m_41720_() instanceof ArmorItem && ((ArmorItem)itemStack.m_41720_()).m_40401_() == ModArmorMaterial.ECHO_UPGRADED_NETHERITE
         || MoreEchoUtil.isEchoArmor(itemStack);
   }

   public static boolean isEchoToolOrWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_AXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreEchoUtil.isEchoToolOrWeapon(itemStack);
   }

   public static boolean isEchoWeapon(ItemStack itemStack) {
      return isEchoMeleeWeapon(itemStack) || isEchoRangedWeapon(itemStack);
   }

   public static boolean isEchoMeleeWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_AXE.get()))
         || MoreEchoUtil.isEchoWeapon(itemStack);
   }

   public static boolean isEchoRangedWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreEchoUtil.isEchoRangedWeapon(itemStack);
   }

   public static boolean isEchoTool(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_AXE.get()))
         || MoreEchoUtil.isEchoTool(itemStack);
   }

   public static boolean isEchoProjectile(Projectile projectile) {
      return (
            projectile instanceof Arrow
               || projectile instanceof SpectralArrow
               || projectile instanceof FireworkRocketEntity
               || MoreEchoUtil.isEchoProjectile(projectile)
         )
         && projectile.m_19880_().contains("EchoUpgradedNetheriteBow");
   }

   public static boolean isEchoShield(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SHIELD.get())) || MoreEchoUtil.isEchoShield(itemStack);
   }

   public static boolean isEchoHorseArmor(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_ARMOR_HORSE.get()))
         || MoreEchoUtil.isEchoHorseArmor(itemStack);
   }

   public static boolean isEchoSoulbound(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_INGOT.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_AXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_CROSSBOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_SHIELD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_HELMET.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_CHESTPLATE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_LEGGINGS.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_BOOTS.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ECHO_UPGRADED_NETHERITE_ARMOR_HORSE.get()))
         || MoreEchoUtil.isEchoSoulbound(itemStack);
   }
}
