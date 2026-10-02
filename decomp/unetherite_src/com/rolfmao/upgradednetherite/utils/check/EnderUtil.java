package com.rolfmao.upgradednetherite.utils.check;

import com.rolfmao.upgradedcore.utils.SetArmorUtil;
import com.rolfmao.upgradedcore.utils.morecheck.MoreEnderUtil;
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
import net.minecraft.world.level.Level;

public class EnderUtil {
   public static int setEnderArmor() {
      return UpgradedNetheriteConfig.UpgradedPlayerEnderArmorRequireSet + MoreEnderUtil.setEnderArmor();
   }

   public static boolean isWearingEnderArmor(Player player) {
      Integer intSet = 0;
      intSet = intSet + SetArmorUtil.isWearingArmor(player, ModArmorMaterial.ENDER_UPGRADED_NETHERITE);
      intSet = intSet + MoreEnderUtil.intWearingEnderArmor(player);
      return intSet >= setEnderArmor();
   }

   public static boolean isHorseWearingEnderArmor(Horse horse) {
      return horse.m_30722_().m_41720_() == ((HorseArmorItem)ModItems.ENDER_UPGRADED_NETHERITE_ARMOR_HORSE.get()).m_5456_()
         || MoreEnderUtil.isHorseWearingEnderArmor(horse);
   }

   public static boolean isEnderArmor(ItemStack itemStack) {
      return itemStack.m_41720_() instanceof ArmorItem && ((ArmorItem)itemStack.m_41720_()).m_40401_() == ModArmorMaterial.ENDER_UPGRADED_NETHERITE
         || MoreEnderUtil.isEnderArmor(itemStack);
   }

   public static boolean isEnderToolOrWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_AXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreEnderUtil.isEnderToolOrWeapon(itemStack);
   }

   public static boolean isEnderWeapon(ItemStack itemStack) {
      return isEnderMeleeWeapon(itemStack) || isEnderRangedWeapon(itemStack);
   }

   public static boolean isEnderMeleeWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_SWORD.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_AXE.get()))
         || MoreEnderUtil.isEnderWeapon(itemStack);
   }

   public static boolean isEnderRangedWeapon(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_BOW.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_CROSSBOW.get()))
         || MoreEnderUtil.isEnderRangedWeapon(itemStack);
   }

   public static boolean isEnderTool(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_PICKAXE.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_SHOVEL.get()))
         || ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_AXE.get()))
         || MoreEnderUtil.isEnderTool(itemStack);
   }

   public static boolean isEnderProjectile(Projectile projectile) {
      return (
            projectile instanceof Arrow
               || projectile instanceof SpectralArrow
               || projectile instanceof FireworkRocketEntity
               || MoreEnderUtil.isEnderProjectile(projectile)
         )
         && projectile.m_19880_().contains("EnderUpgradedNetheriteBow");
   }

   public static boolean isEnderShield(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_SHIELD.get())) || MoreEnderUtil.isEnderShield(itemStack);
   }

   public static boolean isEnderHorseArmor(ItemStack itemStack) {
      return ItemStack.m_41758_(itemStack, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_ARMOR_HORSE.get()))
         || MoreEnderUtil.isEnderHorseArmor(itemStack);
   }

   public static boolean isVoidYLevel(Player player) {
      return player.m_9236_().m_46472_() == Level.f_46428_ && player.m_20186_() < -128.0 || player.m_20186_() < -64.0;
   }
}
