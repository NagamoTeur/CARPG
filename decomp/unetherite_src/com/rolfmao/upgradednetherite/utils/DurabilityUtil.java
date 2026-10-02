package com.rolfmao.upgradednetherite.utils;

import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.init.ModItems;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite.utils.check.FeatherUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import com.rolfmao.upgradednetherite.utils.check.PoisonUtil;
import com.rolfmao.upgradednetherite.utils.check.WaterUtil;
import com.rolfmao.upgradednetherite.utils.check.WitherUtil;
import com.rolfmao.upgradednetherite.utils.enums.ModArmorMaterial;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class DurabilityUtil {
   public static void FireDurabilityLoss(Player player) {
      if (UpgradedNetheriteConfig.EnableDamageDurabilityFireArmor && !EntityDataUtil.hasFireDurabilityCooldown(player)) {
         player.getPersistentData().m_128405_("upgraded_netherite_fire_durability_loss", UpgradedNetheriteConfig.DelayDamageDurabilityFireArmor);

         for (ItemStack itemStack : player.m_6168_()) {
            if (!itemStack.m_41619_() && itemStack.m_41720_() instanceof ArmorItem && FireUtil.isFireArmor(itemStack)) {
               itemStack.m_41622_(UpgradedNetheriteConfig.DamageDurabilityFireArmor, player, e -> e.m_21166_(((ArmorItem)itemStack.m_41720_()).m_40402_()));
            }
         }
      }
   }

   public static void WaterDurabilityLoss(Player player) {
      if (UpgradedNetheriteConfig.EnableDamageDurabilityWaterArmor && !EntityDataUtil.hasWaterDurabilityCooldown(player)) {
         player.getPersistentData().m_128405_("upgraded_netherite_water_durability_loss", UpgradedNetheriteConfig.DelayDamageDurabilityWaterArmor);

         for (ItemStack itemStack : player.m_6168_()) {
            if (!itemStack.m_41619_() && itemStack.m_41720_() instanceof ArmorItem && WaterUtil.isWaterArmor(itemStack)) {
               itemStack.m_41622_(UpgradedNetheriteConfig.DamageDurabilityWaterArmor, player, e -> e.m_21166_(((ArmorItem)itemStack.m_41720_()).m_40402_()));
            }
         }
      }
   }

   public static void WitherDurabilityLoss(Player player) {
      if (UpgradedNetheriteConfig.EnableDamageDurabilityWitherArmor && !EntityDataUtil.hasWitherDurabilityCooldown(player)) {
         player.getPersistentData().m_128405_("upgraded_netherite_wither_durability_loss", UpgradedNetheriteConfig.DelayDamageDurabilityWitherArmor);

         for (ItemStack itemStack : player.m_6168_()) {
            if (!itemStack.m_41619_() && itemStack.m_41720_() instanceof ArmorItem && WitherUtil.isWitherArmor(itemStack)) {
               itemStack.m_41622_(UpgradedNetheriteConfig.DamageDurabilityWitherArmor, player, e -> e.m_21166_(((ArmorItem)itemStack.m_41720_()).m_40402_()));
            }
         }
      }
   }

   public static void PoisonDurabilityLoss(Player player) {
      if (UpgradedNetheriteConfig.EnableDamageDurabilityPoisonArmor && !EntityDataUtil.hasPoisonDurabilityCooldown(player)) {
         player.getPersistentData().m_128405_("upgraded_netherite_poison_durability_loss", UpgradedNetheriteConfig.DelayDamageDurabilityPoisonArmor);

         for (ItemStack itemStack : player.m_6168_()) {
            if (!itemStack.m_41619_() && itemStack.m_41720_() instanceof ArmorItem && PoisonUtil.isPoisonArmor(itemStack)) {
               itemStack.m_41622_(UpgradedNetheriteConfig.DamageDurabilityPoisonArmor, player, e -> e.m_21166_(((ArmorItem)itemStack.m_41720_()).m_40402_()));
            }
         }
      }
   }

   public static void FeatherDurabilityLoss(Player player) {
      if (!EntityDataUtil.hasFeatherDurabilityCooldown(player)) {
         player.getPersistentData().m_128405_("upgraded_netherite_feather_durability_loss", UpgradedNetheriteConfig.DelayDamageDurabilityFeatherArmor);

         for (ItemStack itemStack : player.m_6168_()) {
            if (!itemStack.m_41619_() && itemStack.m_41720_() instanceof ArmorItem && FeatherUtil.isFeatherArmor(itemStack)) {
               itemStack.m_41622_(UpgradedNetheriteConfig.DamageDurabilityFeatherArmor, player, e -> e.m_21166_(((ArmorItem)itemStack.m_41720_()).m_40402_()));
            }
         }
      }
   }

   public static void CorruptDurabilityGain(Player player) {
      if (!EntityDataUtil.hasCorruptDurabilityCooldown(player) && !player.f_19853_.f_46443_ && player.m_36324_().m_38702_() >= 1) {
         List<ItemStack> CorruptList = new ArrayList<>();

         for (ItemStack itemStack : player.m_6168_()) {
            if (!itemStack.m_41619_() && itemStack.m_41720_() instanceof ArmorItem) {
               ArmorItem stuff = (ArmorItem)itemStack.m_41720_();
               ArmorMaterial compareMaterial = stuff.m_40401_();
               if (compareMaterial == ModArmorMaterial.CORRUPT_UPGRADED_NETHERITE && itemStack.m_41773_() > 0) {
                  CorruptList.add(itemStack);
               }
            }
         }

         if (CorruptUtil.isCorruptToolOrWeapon(player.m_21205_()) && player.m_21205_().m_41773_() > 0) {
            CorruptList.add(player.m_21205_());
         }

         if ((
               CorruptUtil.isCorruptToolOrWeapon(player.m_21206_())
                  || ItemStack.m_41758_(player.m_21206_(), new ItemStack((ItemLike)ModItems.CORRUPT_UPGRADED_NETHERITE_SHIELD.get()))
            )
            && player.m_21206_().m_41773_() > 0) {
            CorruptList.add(player.m_21206_());
         }

         if (CorruptList.size() <= 0) {
            return;
         }

         int durability = 40;

         do {
            int nextInt = player.m_217043_().m_188503_(CorruptList.size());
            ItemStack itemStackx = CorruptList.get(nextInt);
            if (itemStackx.m_41773_() >= 1) {
               player.m_36399_(0.1F);
               itemStackx.m_41721_(itemStackx.m_41773_() - 1);
               if (itemStackx.m_41773_() == 0) {
                  CorruptList.remove(itemStackx);
               }

               durability--;
            }
         } while (durability > 0 && !CorruptList.isEmpty());

         player.getPersistentData().m_128405_("upgraded_netherite_corrupt_durability_gain", 100);
      }
   }
}
