package com.rolfmao.upgradednetherite.content;

import com.rolfmao.upgradedcore.helpers.TooltipHelper;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.init.ModItems;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UpgradedNetheriteHorseArmor extends HorseArmorItem {
   public UpgradedNetheriteHorseArmor(int armorValue, String namespaceIn, String pathIn, Properties properties) {
      super(armorValue, new ResourceLocation(namespaceIn, pathIn), properties);
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack itemStack = new ItemStack(this);
         if (itemStack.m_41720_() == ModItems.NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.GOLD_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.FIRE_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.ENDER_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.WATER_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.WITHER_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.POISON_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.PHANTOM_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.FEATHER_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.ECHO_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
            list.add(itemStack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(itemStack);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
      super.m_7373_(itemStack, level, tooltip, tooltipFlag);
      if (!UpgradedNetheriteConfig.DisableTooltips) {
         Item horseArmor = itemStack.m_41720_();
         if (horseArmor != ModItems.NETHERITE_ARMOR_HORSE.get()) {
            if (Screen.m_96638_()) {
               if (horseArmor != ModItems.CORRUPT_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
               }

               if (horseArmor == ModItems.GOLD_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Gold_Horse_Bonus.TT"));
               }

               if (horseArmor == ModItems.FIRE_UPGRADED_NETHERITE_ARMOR_HORSE.get()
                  && (UpgradedNetheriteConfig.EnableLavaSpeed || UpgradedNetheriteConfig.EnableFireImmune)) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnableFireImmune) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Fire_Bonus.TT"));
                  }

                  if (UpgradedNetheriteConfig.EnableLavaSpeed) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Fire_Bonus2.TT"));
                  }
               }

               if (horseArmor == ModItems.ENDER_UPGRADED_NETHERITE_ARMOR_HORSE.get() && UpgradedNetheriteConfig.EnableVoidSave) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Ender_Bonus.TT"));
               }

               if (horseArmor == ModItems.WATER_UPGRADED_NETHERITE_ARMOR_HORSE.get()
                  && (UpgradedNetheriteConfig.EnableWaterLavaWalking || UpgradedNetheriteConfig.EnableWaterSpeed)) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnableWaterBreath) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Water_Bonus.TT"));
                  }

                  if (UpgradedNetheriteConfig.EnableWaterSpeed) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Water_Bonus2.TT"));
                  }
               }

               if (horseArmor == ModItems.WITHER_UPGRADED_NETHERITE_ARMOR_HORSE.get() && UpgradedNetheriteConfig.EnableWitherImmune) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Wither_Bonus.TT"));
               }

               if (horseArmor == ModItems.POISON_UPGRADED_NETHERITE_ARMOR_HORSE.get()
                  && (UpgradedNetheriteConfig.EnablePoisonImmune || UpgradedNetheriteConfig.EnableClimbWall)) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnablePoisonImmune) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Poison_Bonus.TT"));
                  }

                  if (UpgradedNetheriteConfig.EnableClimbWall) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Poison_Bonus2.TT"));
                  }
               }

               if (horseArmor == ModItems.PHANTOM_UPGRADED_NETHERITE_ARMOR_HORSE.get() && UpgradedNetheriteConfig.EnableFallImmune) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Phantom_Bonus.TT"));
               }

               if (horseArmor == ModItems.FEATHER_UPGRADED_NETHERITE_ARMOR_HORSE.get()
                  && (UpgradedNetheriteConfig.EnableWaterLavaWalking || UpgradedNetheriteConfig.EnableReduceFallDamage)) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnableWaterLavaWalking) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bonus.TT"));
                  }

                  if (UpgradedNetheriteConfig.EnableReduceFallDamage) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Feather_Bonus3.TT"));
                  }
               }

               if (horseArmor == ModItems.CORRUPT_UPGRADED_NETHERITE_ARMOR_HORSE.get()) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Malus.TT"));
                  TooltipHelper.addTWO(tooltip, "upgradednetherite.Corrupt_Horse_Bonus.TT", new Object[]{"§6" + UpgradedNetheriteConfig.HealthMalus + "%"});
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Corrupt_Horse_Bonus2.TT"));
               }

               if (horseArmor == ModItems.ECHO_UPGRADED_NETHERITE_ARMOR_HORSE.get()
                  && (UpgradedNetheriteConfig.EnableReduceDamageEchoArmor || UpgradedNetheriteConfig.EnableHealEchoArmor)) {
                  tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
                  tooltip.add(Component.m_237115_("upgradednetherite.Bonus.TT"));
                  if (UpgradedNetheriteConfig.EnableReduceDamageEchoArmor) {
                     TooltipHelper.addTWO(tooltip, "upgradednetherite.Echo_Bonus2.TT", new Object[]{"§6" + UpgradedNetheriteConfig.ReduceDamageEchoArmor + "%"});
                  }

                  if (UpgradedNetheriteConfig.EnableHealEchoArmor) {
                     tooltip.add(Component.m_237115_("upgradednetherite.Echo_Bonus3.TT"));
                  }
               }
            } else {
               tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
            }
         }
      }
   }
}
